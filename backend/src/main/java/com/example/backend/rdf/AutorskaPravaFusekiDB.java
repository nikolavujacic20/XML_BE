package com.example.backend.rdf;

import com.example.backend.dto.IzvestajRequest;
import com.example.backend.dto.MetadataSearchParams;
import com.example.backend.model.EStatus;
import com.example.backend.model.ZahtevZaAutorskaPrava;
import com.example.backend.repository.AutorskaPravaRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.jena.rdf.model.Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class AutorskaPravaFusekiDB {

    private static final String REPORTS_FOLDER = "src/main/resources/gen/izvestaji/";

    public static void save(ZahtevZaAutorskaPrava zahtev) {
        Model metadataModel = AutorskaPravaMetadataExtractor.extract(zahtev);
        try {
            FusekiWriter.saveRdfGraphToDatabase(metadataModel);
            FusekiReader.readAllDataFromDatabase();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save RDF to Fuseki", e);
        }
    }

    public static List<String> findByMetadata(MetadataSearchParams param) throws IOException {
        return FusekiReader.findByMetadata(param);
    }

    public static List<String> findByMultipleMetadata(List<MetadataSearchParams> params) throws IOException {
        return FusekiReader.findByMetadata(params);
    }

    public static String getRdfString(String brojPrijave) throws Exception {
        return FusekiReader.getRdfString(brojPrijave);
    }

    public static String getJsonString(String brojPrijave) throws Exception {
        return FusekiReader.getJsonString(brojPrijave);
    }

    public static ByteArrayInputStream generateReport(IzvestajRequest izvestajRequest) throws IOException, DocumentException {
        int ukupno = 0;
        int prihvaceni = 0;
        int odbijeni = 0;

        try {
            AutorskaPravaRepository repo = new AutorskaPravaRepository();
            LocalDate start = LocalDate.parse(izvestajRequest.getStart());
            LocalDate end = LocalDate.parse(izvestajRequest.getEnd());
            Instant from = start.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant to = end.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

            for (ZahtevZaAutorskaPrava z : repo.getAllByStatus(EStatus.ODBIJENO)) {
                Date d = z.getInformacijeOZahtevu().getDatumPodnosenja();
                if (isBetweenInclusive(d, from, to)) odbijeni++;
            }
            for (ZahtevZaAutorskaPrava z : repo.getAllByStatus(EStatus.PRIHVACENO)) {
                Date d = z.getInformacijeOZahtevu().getDatumPodnosenja();
                if (isBetweenInclusive(d, from, to)) prihvaceni++;
            }
            for (ZahtevZaAutorskaPrava z : repo.getAllByStatus(EStatus.PREDATO)) {
                Date d = z.getInformacijeOZahtevu().getDatumPodnosenja();
                if (isBetweenInclusive(d, from, to)) ukupno++;
            }
            ukupno += prihvaceni + odbijeni;
        } catch (Exception e) {
            throw new RuntimeException("Failed to compute report stats", e);
        }

        ensureDir(REPORTS_FOLDER);
        String fileName = "izvestaj_" + izvestajRequest + ".pdf";
        Path outPath = Path.of(REPORTS_FOLDER, fileName);

        byte[] pdfBytes = buildPdfBytes(izvestajRequest, ukupno, prihvaceni, odbijeni);
        try (FileOutputStream fos = new FileOutputStream(outPath.toFile())) {
            fos.write(pdfBytes);
        }
        return new ByteArrayInputStream(pdfBytes);
    }

    private static boolean isBetweenInclusive(Date date, Instant fromInclusive, Instant toExclusiveNextDay) {
        if (date == null) return false;
        Instant t = date.toInstant();
        return !t.isBefore(fromInclusive) && t.isBefore(toExclusiveNextDay);
    }

    private static byte[] buildPdfBytes(IzvestajRequest req, int brojZahteva, int prihvaceni, int odbijeni)
            throws DocumentException, IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("IZVEŠTAJ"));
            document.add(new Paragraph("\nPočetni datum: " + req.getStart()));
            document.add(new Paragraph("\nKrajnji datum: " + req.getEnd()));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(3);
            addTableHeader(table);
            addRows(table, brojZahteva, prihvaceni, odbijeni);
            document.add(table);

            document.close();
            return baos.toByteArray();
        }
    }

    private static void addRows(PdfPTable table, int zahtevi, int prihvaceniZahtevi, int odbijeniZahtevi) {
        table.addCell(String.valueOf(zahtevi));
        table.addCell(String.valueOf(prihvaceniZahtevi));
        table.addCell(String.valueOf(odbijeniZahtevi));
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Broj podnetih zahteva", "Broj prihvaćenih zahteva", "Broj odbijenih zahteva")
                .forEach(title -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.WHITE);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(title));
                    table.addCell(header);
                });
    }

    private static void ensureDir(String folder) throws IOException {
        Files.createDirectories(Path.of(folder));
    }
}
