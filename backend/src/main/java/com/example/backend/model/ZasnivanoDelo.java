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
@XmlRootElement(name = "Zasnivano_delo")
@XmlType(name = "Zasnivano_delo", propOrder = {"naslov", "autor"})
public class ZasnivanoDelo {

    @XmlElement(name = "naslov")
    private String naslov;
    @XmlElement(name = "autor")
    private String autor;

    @Override
    public String toString() {
        return "ZasnivanoDelo {" +
                "\n\tnaslov='" + naslov + '\'' +
                ",\n\tautor='" + autor + '\'' +
                "\n}";
    }
}
