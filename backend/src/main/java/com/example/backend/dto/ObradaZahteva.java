package com.example.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Builder
public class ObradaZahteva {
    private String brojPrijave;
    private SimpleUser sluzbenik;
    private String datumObrade;
    private boolean odbijen;
    private String razlogOdbijanja;
    private String sifra;
}
