package com.example.backend.dto;


import com.example.backend.model.Adresa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PodnosilacDTO {
    private String drzavljanstvo;
    private String ime;
    private String prezime;
    private Adresa adresa;
    private String brojTelefona;
    private String email;
    private String poslovnoIme;
    private Adresa sediste;
}
