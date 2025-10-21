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
@XmlRootElement(name = "TLice")
@XmlType(name = "TLice", propOrder = {"brojTelefona", "email"})
public class TLice {

    @XmlElement(name = "Broj_telefona", required = true)
    private String brojTelefona;
    @XmlElement(name = "Email", required = true)
    private String email;

    @Override
    public String toString() {
        return "TLice {" +
                "\n\tbrojTelefona='" + brojTelefona + '\'' +
                ",\n\temail='" + email + '\'' +
                "\n}";
    }
}
