package com.example.backend.controller;


import com.example.backend.dto.BrojPrijaveDTO;
import com.example.backend.dto.DetaljiOZahtevu;
import com.example.backend.dto.ObradaZahteva;
import com.example.backend.service.ResenjeService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/autorskaPravaResenje", produces = MediaType.APPLICATION_XML_VALUE)
public class ResenjeZahtevaController {

    private ResenjeService resenjeService;

    @PostMapping(path = "/resenjeZahteva", consumes = "application/xml", produces = "application/xml")
    public DetaljiOZahtevu getResenjeZahteva(@RequestBody BrojPrijaveDTO brojPrijave) throws XMLDBException, IOException, JAXBException {
        return resenjeService.getResenjeZahteva(brojPrijave.getBroj());
    }

    @PostMapping(value = "/obradiZahtev", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Void> obradiZahtev(@RequestBody ObradaZahteva obradaZahteva) {
        try {
            resenjeService.obradiZahtev(obradaZahteva);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/resenje", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateResenje(@RequestBody BrojPrijaveDTO brojPrijave) {
        ByteArrayInputStream byteFile = resenjeService.generateResenje(brojPrijave.getBroj());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=resenje_" + brojPrijave.getBroj() + ".pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteFile));
    }
}
