package com.example.backend.db;


import com.example.backend.marshal.Marshal;
import com.example.backend.resenje.ResenjeZahteva;
import com.example.backend.utils.AuthenticationUtilities;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import java.io.IOException;

public class ResenjeZahtevaDB {

    private static String collectionId = "/db/XWS-PROJECT/resenja";

    private static Collection getOrCreateCollection(String collectionUri, AuthenticationUtilities.ConnectionProperties conn) throws XMLDBException {
        return getOrCreateCollection(collectionUri, 0, conn);
    }

    private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset, AuthenticationUtilities.ConnectionProperties conn) throws XMLDBException {
        Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);

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

                Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);

                if (startCol == null) {

                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user, conn.password);

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

    public static ResenjeZahteva write(ResenjeZahteva resenjeZahteva) throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException, IOException {
        AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();

        // initialize database driver
        System.out.println("[INFO] Loading driver class: " + conn.driver);
        Class<?> cl = Class.forName(conn.driver);


        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");


        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource res;
        try {

            System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = getOrCreateCollection(collectionId, conn);
            String documentName = "resenjeAutorskoPravo_" + resenjeZahteva.getBrojPrijave().replace('/', '_');
            res = (XMLResource) col.createResource(documentName, XMLResource.RESOURCE_TYPE);
            res.setContent(Marshal.marshal(resenjeZahteva));
            System.out.println("[INFO] Storing the document: " + res.getId());
            col.storeResource(res);
            System.out.println("[INFO] Done.");
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
        return resenjeZahteva;
    }

    public static ResenjeZahteva dobaviPoBrojuPrijave(String brojPrijave) throws IOException, XMLDBException, JAXBException {
        String documentName = "resenjeAutorskoPravo_" + brojPrijave.replace('/', '_');
        AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();

        Collection col = DatabaseManager.getCollection(conn.uri + collectionId);
        col.setProperty(OutputKeys.INDENT, "yes");
        XMLResource res = (XMLResource) col.getResource(documentName);

        JAXBContext context = JAXBContext.newInstance(ResenjeZahteva.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (ResenjeZahteva) unmarshaller.unmarshal(res.getContentAsDOM());
    }
}
