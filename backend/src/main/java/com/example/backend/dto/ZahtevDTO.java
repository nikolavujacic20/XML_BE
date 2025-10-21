package com.example.backend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
@Builder
public class ZahtevDTO {
    private String brojPrijave;
    private SimpleUser podnosioc;
    private String datumPodnosenja;
    private boolean obradjen;
}
