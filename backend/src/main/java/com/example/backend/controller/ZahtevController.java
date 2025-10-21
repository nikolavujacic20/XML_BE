package com.example.backend.controller;


import com.example.backend.dto.*;
import com.example.backend.mapper.Mapper;
import com.example.backend.model.ZahtevZaAutorskaPrava;
import com.example.backend.service.AutorskaPravaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/autorskaPrava")
public class ZahtevController {

    private AutorskaPravaService autorskaPravaService;

    @PostMapping(path="/getRequest", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<ZahtevZaAutorskaPrava> getZahtevByBrojPrijave(@RequestBody BrojPrijaveDTO brojPrijave) {
        return ResponseEntity.ok().body(autorskaPravaService.getZahtev(brojPrijave.getBroj()));
    }

    @PostMapping(consumes = "application/xml", produces = "application/xml")
    public String createZahtev(@RequestBody ZahtevZaAutorskaPravaDTO dto) {
        return autorskaPravaService.createZahtevZaAutorskaPrava(dto).getInformacijeOZahtevu().getBrojPrijave();
    }

    @GetMapping(path = "/applied", produces = "application/xml")
    public ResponseEntity<List<ZahtevZaAutorskaPrava>> getAllApplied() throws JAXBException, XMLDBException {
        return ResponseEntity.ok(autorskaPravaService.getAllApplied());
    }

    @GetMapping(path = "/approved", produces = "application/xml")
    public ResponseEntity<List<ZahtevZaAutorskaPrava>> getAllApproved() throws JAXBException, XMLDBException {
        return ResponseEntity.ok(autorskaPravaService.getAllApproved());
    }

    @GetMapping(path = "/canceled", produces = "application/xml")
    public ResponseEntity<List<ZahtevZaAutorskaPrava>> getAllCanceled() throws JAXBException, XMLDBException {
        return ResponseEntity.ok(autorskaPravaService.getAllCanceled());
    }

    @GetMapping(path = "/denied", produces = "application/xml")
    public ResponseEntity<List<ZahtevZaAutorskaPrava>> getAllDenied() throws JAXBException, XMLDBException {
        return ResponseEntity.ok(autorskaPravaService.getAllDenied());
    }

    @PutMapping(path = "/metadata-search", produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<List<SimpleZahtevDTO>> getZahteviByMetadata(@RequestBody MetadataSearchParamsDTO metadataSearchParamsDTO) throws IOException {
        List<MetadataSearchParams> parsedSearchParams = autorskaPravaService.parseMetadataDTO(metadataSearchParamsDTO);

        if (parsedSearchParams == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ZahtevZaAutorskaPrava> zahtevi = autorskaPravaService.getByMetadata(parsedSearchParams, metadataSearchParamsDTO.getStatus());
        List<SimpleZahtevDTO> simpleZahtevDTOs = Mapper.mapToSimpleZahtevs(zahtevi);

        return ResponseEntity.ok(simpleZahtevDTOs);
    }

    @PutMapping(path = "/text-search", produces = "application/xml", consumes = "application/xml")
    public ResponseEntity<List<SimpleZahtevDTO>> getZahteviByTextSearch(@RequestBody TextSearchDTO textSearchDTO) throws Exception {
        String stripped = textSearchDTO.getTextSearch().trim();
        List<ZahtevZaAutorskaPrava> zahtevi = autorskaPravaService.getByText(stripped, textSearchDTO.isCasesensitive(), textSearchDTO.getStatus());

        if (zahtevi == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<SimpleZahtevDTO> simpleZahtevDTOs = Mapper.mapToSimpleZahtevs(zahtevi);
        return ResponseEntity.ok(simpleZahtevDTOs);
    }

    @PostMapping("/file-upload/{godinaPrijave}/{brojPrijave}/{tipPriloga}")
    public ResponseEntity<String> uploadPrilog(@PathVariable("godinaPrijave") String godinaPrijave,
                                               @PathVariable("brojPrijave") String brojPrijave,
                                               @PathVariable("tipPriloga") String tipPriloga,
                                               @RequestParam("file") MultipartFile file) {
        try {
            String brojPrijaveA = godinaPrijave + "/" + brojPrijave;

            if (tipPriloga.trim().equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            boolean isOkay = autorskaPravaService.addPrilog(brojPrijaveA, tipPriloga, file);

            if (!isOkay) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/save/{godina}-{broj}")
    public ResponseEntity<String> saveAfterPrilogAddition(@PathVariable("godina") String godina,
                                                          @PathVariable("broj") String broj) {
        try {
            String brojPrijave = godina.trim().concat("/").concat(broj.trim());

            if (!autorskaPravaService.saveZahtevAfterPrilogAddition(brojPrijave)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}