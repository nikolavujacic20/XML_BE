package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Adresa", propOrder = {"ulica", "brojUUlici", "postanskiBroj", "mesto", "drzava"})
public class Adresa {

    @XmlElement(name = "Ulica", required = true)
    private String ulica;
    @XmlElement(name = "Broj_u_ulici", required = true)
    private int brojUUlici;
    @XmlElement(name = "Postanski_broj", required = true)
    private int postanskiBroj;
    @XmlElement(name = "Mesto", required = true)
    private String mesto;
    @XmlElement(name = "Drzava", required = true)
    private String drzava;

    @Override
    public String toString() {
        return "Adresa {" +
                "\n\t\t\tulica='" + ulica + '\'' +
                ",\n\t\t\tbrojUUlici=" + brojUUlici +
                ",\n\t\t\tpostanskiBroj=" + postanskiBroj +
                ",\n\t\t\tmesto='" + mesto + '\'' +
                ",\n\t\t\tdrzava='" + drzava + '\'' +
                "\n\t\t}";
    }
}
