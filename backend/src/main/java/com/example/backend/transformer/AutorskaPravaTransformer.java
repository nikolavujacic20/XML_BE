package com.example.backend.transformer;

import com.example.backend.model.ZahtevZaAutorskaPrava;
import com.example.backend.resenje.ResenjeZahteva;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class AutorskaPravaTransformer {

    private static final TransformerFactory transformerFactory;

    public static final String XSL_RESENJE_FILE = "src/main/java/com/example/backend/transformer/Resenje.xsl";
    public static final String XSL_FILE = "src/main/java/com/example/backend/transformer/Zahtev.xsl";

    public static final String HTML_FOLDER = "src/main/resources/gen/xhtml/";

    public static final String PDF_FOLDER = "src/main/resources/gen/pdf/";
    public static final String HTML_RESENJA_FOLDER = "src/main/resources/gen/resenjaHTML/";
    public static final String PDF_RESENJA_FOLDER = "src/main/resources/gen/resenjaPDF/";

    static {
        /* Inicijalizacija DOM fabrike */
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);

        /* Inicijalizacija Transformer fabrike */
        transformerFactory = TransformerFactory.newInstance();
    }

    private static String getFileTitle(ZahtevZaAutorskaPrava zahtev) {
        return "autorskaPrava_" + zahtev.getInformacijeOZahtevu().getBrojPrijave().replace('/', '_');
    }

    public static void generateZahtevPDF(ZahtevZaAutorskaPrava zahtev) {
        try {
            AutorskaPravaTransformer.generateZahtevHTML(zahtev, true);
            String title = getFileTitle(zahtev);
            String titlePdf = title + ".pdf";
            String titleHTML = title + "_for_pdf.html";
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PDF_FOLDER + titlePdf));
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(HTML_FOLDER + titleHTML));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateResenjePDF(ResenjeZahteva resenjeZahteva, String title) {
        try {
            AutorskaPravaTransformer.generateResenjeHTML(resenjeZahteva, title);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PDF_RESENJA_FOLDER + title + ".pdf"));
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(HTML_RESENJA_FOLDER + title + ".html"));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateZahtevHTML(ZahtevZaAutorskaPrava zahtev, boolean forPdf) {
        try {
            // Initialize Transformer instance
            StreamSource transformSource = new StreamSource(new File(XSL_FILE));
            Transformer transformer = transformerFactory.newTransformer(transformSource);
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Generate XHTML
            transformer.setOutputProperty(OutputKeys.METHOD, "gen/xhtml");

            // Transform DOM to HTML
            JAXBContext context = JAXBContext.newInstance("com.example.backend.model");
            JAXBSource source = new JAXBSource(context, zahtev);
            StreamResult result;

            if (forPdf) {
                result = new StreamResult(new FileOutputStream(HTML_FOLDER + getFileTitle(zahtev) + "_for_pdf" + ".html"));
            } else {
                result = new StreamResult(new FileOutputStream(HTML_FOLDER + getFileTitle(zahtev) + ".html"));
            }

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateResenjeHTML(ResenjeZahteva resenjeZahteva, String title) {
        try {
            // Initialize Transformer instance
            StreamSource transformSource = new StreamSource(new File(XSL_RESENJE_FILE));
            Transformer transformer = transformerFactory.newTransformer(transformSource);
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Generate XHTML
            transformer.setOutputProperty(OutputKeys.METHOD, "gen/xhtml");

            // Transform DOM to HTML
            JAXBContext context = JAXBContext.newInstance("com.example.backend.resenje");
            JAXBSource source = new JAXBSource(context, resenjeZahteva);
            StreamResult result = new StreamResult(new FileOutputStream(HTML_RESENJA_FOLDER + title + ".html"));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
