package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TPravno_lice")
@XmlType(name = "TPravno_lice", propOrder = {"poslovnoIme", "sedisteNosiocaAutorskogPrava"})
public class TPravnoLice extends TLice {

    @XmlElement(name = "Poslovno_ime", required = true)
    private String poslovnoIme;
    @XmlElement(name = "Sediste_nosioca_autorskog_prava", required = true)
    private Adresa sedisteNosiocaAutorskogPrava;

    @Override
    public String toString() {
        return "TPravnoLice {" +
                "\n\tposlovnoIme='" + poslovnoIme + '\'' +
                ",\n\tsedisteNosiocaAutorskogPrava=" + sedisteNosiocaAutorskogPrava +
                "\n}";
    }
}
