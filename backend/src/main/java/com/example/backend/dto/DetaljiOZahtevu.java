package com.example.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Builder
public class DetaljiOZahtevu {
    private ZahtevDTO zahtev;
    private ObradaZahteva obrada;
}
