package com.example.backend.controller;


import com.example.backend.dto.BrojPrijaveDTO;
import com.example.backend.dto.IzvestajRequest;
import com.example.backend.service.AutorskaPravaService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/autorskaPrava/download")
public class DownloadController {

    private AutorskaPravaService autorskaPravaService;

    @PostMapping(path = "/html", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public ResponseEntity<InputStreamResource> generateHTML(@RequestBody BrojPrijaveDTO brojPrijave) {
        ByteArrayInputStream byteFile = autorskaPravaService.generateHTML(brojPrijave.getBroj());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=review_" + brojPrijave.getBroj() + ".html");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_XHTML_XML).body(new InputStreamResource(byteFile));
    }

    @PostMapping(path = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePDF(@RequestBody BrojPrijaveDTO brojPrijave) {
        ByteArrayInputStream byteFile = autorskaPravaService.generatePDF(brojPrijave.getBroj());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=review_" + brojPrijave.getBroj() + ".pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteFile));
    }

    @PostMapping(path = "/rdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateRDF(@RequestBody BrojPrijaveDTO brojPrijave) {
        ByteArrayInputStream byteFile = autorskaPravaService.generateRDF(brojPrijave.getBroj());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=metadata_" + brojPrijave.getBroj() + ".rdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteFile));
    }

    @PostMapping(path = "/json", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateJSON(@RequestBody BrojPrijaveDTO brojPrijave) {
        ByteArrayInputStream byteFile = autorskaPravaService.generateJSON(brojPrijave.getBroj());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=metadata_" + brojPrijave.getBroj() + ".json");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteFile));
    }

    @GetMapping(path = "/prilog/{fileName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getPrilogFile(@PathVariable("fileName") String fileName) throws IOException {
        ByteArrayInputStream byteFile = autorskaPravaService.getPrilog(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + fileName);

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteFile));
    }

    @GetMapping(path = "/zahtev/{godinaPrijave}/{broj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> zahtev(@PathVariable String godinaPrijave, @PathVariable String broj) {
        ByteArrayInputStream byteFile = autorskaPravaService.generatePDF(godinaPrijave + "/" + broj);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=zahtev" + godinaPrijave + "/" + broj + ".pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteFile));
    }


    @PostMapping(path = "/izvestaj", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateIzvestaj(@RequestBody IzvestajRequest izvestajRequest) throws FileNotFoundException {
        ByteArrayInputStream byteFile = autorskaPravaService.generateIzvestaj(izvestajRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=izvestaj_" + izvestajRequest.toString() + ".pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteFile));
    }
}
