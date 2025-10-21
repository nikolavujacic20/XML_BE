package com.example.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SimpleZahtevDTO {
    private String brojPrijave;
    private String datumPodnosenja;
    private String podnosiocEmail;
    private boolean obradjen;
}