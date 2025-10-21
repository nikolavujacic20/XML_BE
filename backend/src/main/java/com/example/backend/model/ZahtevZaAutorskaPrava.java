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
@XmlRootElement(
        name = "Zahtev_za_unosenje_u_evidenciju_i_deponovanje_autorskih_dela"
)
@XmlType(
        name = "",
        propOrder = {"informacijeOZahtevu", "sadrzajZahteva", "status", "putanjaDoOpisa", "putanjaDoPrimera"}
)
public class ZahtevZaAutorskaPrava {
    @XmlElement(
            name = "Informacije_o_zahtevu",
            required = true
    )
    private InformacijeOZahtevu informacijeOZahtevu;
    @XmlElement(
            name = "Sadrzaj_zahteva",
            required = true
    )
    private SadrzajZahteva sadrzajZahteva;
    @XmlElement(name = "Status", required = true)
    private EStatus status;
    @XmlElement(name="Putanja_do_opisa")
    private String putanjaDoOpisa;
    @XmlElement(name="Putanja_do_primera")
    private String putanjaDoPrimera;

    @Override
    public String toString() {
        return "ZahtevZaUnosenjeUEvidencijuIDeponovanjeAutorskihDela {" +
                "\ninformacijeOZahtevu=" + informacijeOZahtevu +
                ",\nsadrzajZahteva=" + sadrzajZahteva +
                "\n}";
    }
}
