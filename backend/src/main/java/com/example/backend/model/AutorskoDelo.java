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
@XmlRootElement(name = "Autorsko_delo")
@XmlType(name = "Autorsko_delo", propOrder = {"naslovAutorskogDela", "zasnivanoDelo", "vrstaAutorskogDela",
        "formaZapisaAutorskogDela", "autorskoDeloStvorenoURadnomOdnosu", "nacinKoriscenjaAutorskogDela"})
public class AutorskoDelo {

    @XmlElement(name = "Naslov_autorskog_dela", required = true)
    private String naslovAutorskogDela;
    @XmlElement(name = "Zasnivano_delo")
    private ZasnivanoDelo zasnivanoDelo;
    @XmlElement(name = "Vrsta_autorskog_dela", required = true)
    private String vrstaAutorskogDela;
    @XmlElement(name = "Forma_zapisa_autorskog_dela", required = true)
    private String formaZapisaAutorskogDela;
    @XmlElement(name = "Autorsko_delo_stvoreno_u_radnom_odnosu", required = true)
    private boolean autorskoDeloStvorenoURadnomOdnosu;
    @XmlElement(name = "Nacin_koriscenja_autorskog_dela", required = true)
    private String nacinKoriscenjaAutorskogDela;

    @Override
    public String toString() {
        return "AutorskoDelo {" +
                "\n\t\tnaslovAutorskogDela='" + naslovAutorskogDela + '\'' +
                ",\n\t\tzasnivanoDelo=" + zasnivanoDelo +
                ",\n\t\tvrstaAutorskogDela='" + vrstaAutorskogDela + '\'' +
                ",\n\t\tformaZapisaAutorskogDela='" + formaZapisaAutorskogDela + '\'' +
                ",\n\t\tautorskoDeloStvorenoURadnomOdnosu=" + autorskoDeloStvorenoURadnomOdnosu +
                ",\n\t\tnacinKoriscenjaAutorskogDela='" + nacinKoriscenjaAutorskogDela + '\'' +
                "\n\t}";
    }
}
