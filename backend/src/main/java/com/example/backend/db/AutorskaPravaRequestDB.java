package com.example.backend.db;


import com.example.backend.marshal.Marshal;
import com.example.backend.model.EStatus;
import com.example.backend.model.ZahtevZaAutorskaPrava;
import com.example.backend.utils.AuthenticationUtilities;
import com.example.backend.utils.DBSetup;
import org.exist.xmldb.EXistResource;
import org.exist.xmldb.RemoteXMLResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.backend.utils.Utils.formatNameOfRequest;


public class AutorskaPravaRequestDB {

    private static final String TARGET_NAMESPACE = "com.example.backend.model";

    public static ZahtevZaAutorskaPrava getZahtev(String brojPrijave) {
        try {
            AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();
            String collectionId = DBSetup.setupDBConnection(conn);
            Collection col = getOrCreateCollection(collectionId, conn);
            XMLResource res = (XMLResource) col.getResource(formatNameOfRequest(brojPrijave, ".xml"));
            if (res != null) {
                System.out.println(res.getContent());
                JAXBContext context = JAXBContext.newInstance(TARGET_NAMESPACE);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                return (ZahtevZaAutorskaPrava) unmarshaller.unmarshal(res.getContentAsDOM());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static org.xmldb.api.base.Collection getOrCreateCollection(String collectionUri, AuthenticationUtilities.ConnectionProperties conn) throws XMLDBException {
        return getOrCreateCollection(collectionUri, 0, conn);
    }

    public static int getNumberOfRequests() {
        try {
            AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();
            String collectionId = DBSetup.setupDBConnection(conn);
            Collection col = getOrCreateCollection(collectionId, conn);
            return col.getResourceCount();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void save(ZahtevZaAutorskaPrava zahtev) {
        OutputStream marshaled = Marshal.marshal(zahtev);
        try {
            AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();
            String collectionId = DBSetup.setupDBConnection(conn);
            String documentId = formatNameOfRequest(zahtev.getInformacijeOZahtevu().getBrojPrijave(), ".xml");
            Collection col = getOrCreateCollection(collectionId, conn);
            XMLResource res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
            res.setContent(marshaled);
            col.storeResource(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<XMLResource> getAllByStatus(EStatus status) {
        String xpathExp = "//aut:Status[text()='" + status + "']/ancestor::aut:Zahtev_za_unosenje_u_evidenciju_i_deponovanje_autorskih_dela";
        return getAllByFilter(xpathExp);
    }

    private static XPathQueryService getXPathQueryService(Collection col) throws XMLDBException {
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");
        xpathService.setNamespace("aut", "http://www.ftn.uns.ac.rs/autorskoDelo");

        return xpathService;
    }

    private static Collection getCollection() throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();
        String collectionId = DBSetup.setupDBConnection(conn);
        Collection col = getOrCreateCollection(collectionId, conn);
        col.setProperty(OutputKeys.INDENT, "yes");

        return col;
    }

    public static List<XMLResource> searchResourcesForText(List<String> words, boolean matchCase) throws Exception {

        try (Collection col = getCollection()) {
            List<XMLResource> resources = new ArrayList<>();
            XPathQueryService xPathQueryService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xPathQueryService.setProperty("indent", "yes");
            String xPathExp = createXPathExpressionForTextSearch(words, matchCase);
            ResourceIterator iter = xPathQueryService.query(xPathExp).getIterator();

            while (iter.hasMoreResources()) {
                Resource res = iter.nextResource();
                System.out.println(res.getContent());
                RemoteXMLResource resxml = (RemoteXMLResource) res;
                resources.add(resxml);
            }
            return resources;
        }
    }

    private static void collectXMLResourcesFromResult(ResourceSet result, List<XMLResource> resources, Collection col) throws XMLDBException {
        ResourceIterator iter = result.getIterator();
        XMLResource resource = null;

        try {
            while (iter.hasMoreResources()) {
                resource = (XMLResource) iter.nextResource();
                resources.add(resource);
            }
        } finally {
            if (resource != null) {
                ((EXistResource) resource).freeResources();
            }
            if (col != null) {
                col.close();
            }
        }
    }

    private static String createXPathExpressionForTextSearch(List<String> words, boolean matchCase) {
        int wordsDone = 0;
        String xpath = "/*[";

        for (String word : words) {
            xpath = xpath.concat("contains(");

            if (!matchCase) {
                xpath = xpath.concat("lower-case(.)");
                word = word.toLowerCase();
            } else {
                xpath = xpath.concat(".");
            }

            xpath = xpath.concat(", ").concat("\"").concat(word).concat("\"");
            xpath = xpath.concat(")");

            wordsDone++;
            if (wordsDone != words.size()) {
                xpath = xpath.concat(" and ");
            }
        }

        xpath = xpath.concat("]");

        return xpath;
    }

    private static List<XMLResource> getAllByFilter(String xpathExp) {
        List<XMLResource> resources = new ArrayList<>();

        try {
            Collection col = getCollection();
            XPathQueryService xpathService = getXPathQueryService(col);
            ResourceSet result = xpathService.query(xpathExp);
            collectXMLResourcesFromResult(result, resources, col);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resources;
    }

    private static org.xmldb.api.base.Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset, AuthenticationUtilities.ConnectionProperties conn) throws XMLDBException {

        org.xmldb.api.base.Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);
        // create the collection if it does not exist
        if (col == null) {

            if (collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }

            String[] pathSegments = collectionUri.split("/");

            if (pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();

                for (int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/").append(pathSegments[i]);
                }

                org.xmldb.api.base.Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);

                if (startCol == null) {

                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    org.xmldb.api.base.Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user, conn.password);

                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");

                    System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);

                    col.close();
                    parentCol.close();

                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset, conn);
        } else {
            return col;
        }
    }
}
