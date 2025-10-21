package com.example.backend.resenje;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.Date;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "brojPrijave",
        "imeSluzbenika",
        "prezimeSluzbenika",
        "emailSluzbenika",
        "datumObrade",
        "odbijen",
        "razlogOdbijanja",
        "sifra"
})
@XmlRootElement(name = "resenje_zahteva")
public class ResenjeZahteva {
    @XmlElement(name = "broj_prijave", required = true)
    private String brojPrijave;
    @XmlElement(name = "ime_sluzbenika")
    private String imeSluzbenika;
    @XmlElement(name = "prezime_sluzbenika")
    private String prezimeSluzbenika;
    @XmlElement(name = "email_sluzbenika")
    private String emailSluzbenika;
    @XmlElement(name = "datum_obrade")
    @XmlSchemaType(name = "date")
    private Date datumObrade;
    @XmlElement(name = "odbijen")
    private boolean odbijen;
    @XmlElement(name = "razlog_odbijanja")
    private String razlogOdbijanja;
    @XmlElement(name = "sifra")
    private String sifra;
}
