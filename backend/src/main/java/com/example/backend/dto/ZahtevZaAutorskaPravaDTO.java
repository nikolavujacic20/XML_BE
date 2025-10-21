package com.example.backend.dto;


import com.example.backend.model.AutorskoDelo;
import com.example.backend.model.OsnovniLicniPodaci;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ZahtevZaAutorskaPravaDTO {
    private PodnosilacDTO podnosilacZahteva;
    private OsnovniLicniPodaci podaciOPunomocniku;
    private AutorDTO autor;
    private AutorskoDelo autorskoDelo;
}
