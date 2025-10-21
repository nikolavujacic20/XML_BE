package com.example.backend.mapper;

import com.example.backend.dto.AutorDTO;
import com.example.backend.dto.PodnosilacDTO;
import com.example.backend.dto.SimpleZahtevDTO;
import com.example.backend.dto.ZahtevZaAutorskaPravaDTO;
import com.example.backend.model.*;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static ZahtevZaAutorskaPrava dtoToZahtev(ZahtevZaAutorskaPravaDTO dto) {
        ZahtevZaAutorskaPrava z = new ZahtevZaAutorskaPrava();
        SadrzajZahteva sadrzaj = new SadrzajZahteva();
        sadrzaj.setAutor(dtoToAutor(dto.getAutor()));
        sadrzaj.setAutorskoDelo(dto.getAutorskoDelo());
        sadrzaj.setPodnosilacZahteva(dtoToPodnosilac(dto.getPodnosilacZahteva()));
        sadrzaj.setPodaciOPunomocniku(dto.getPodaciOPunomocniku());
        z.setSadrzajZahteva(sadrzaj);
        return z;
    }

    public static List<SimpleZahtevDTO> mapToSimpleZahtevs(List<ZahtevZaAutorskaPrava> zahtevs) {
        List<SimpleZahtevDTO> simpleZahtevDTOs = new ArrayList<>();

        for (ZahtevZaAutorskaPrava zahtev : zahtevs) {
            SimpleZahtevDTO simpleZahtevDTO = mapToSimpleZahtev(zahtev);
            simpleZahtevDTOs.add(simpleZahtevDTO);
        }

        return simpleZahtevDTOs;
    }

    public static SimpleZahtevDTO mapToSimpleZahtev(ZahtevZaAutorskaPrava zahtev) {
        SimpleZahtevDTO simpleZahtevDTO = new SimpleZahtevDTO();
        simpleZahtevDTO.setBrojPrijave(zahtev.getInformacijeOZahtevu().getBrojPrijave());

        String datumPodnosenja = zahtev.getInformacijeOZahtevu().getDatumPodnosenja().toString();
        simpleZahtevDTO.setDatumPodnosenja(datumPodnosenja);

        simpleZahtevDTO.setPodnosiocEmail(zahtev.getSadrzajZahteva().getPodnosilacZahteva().getPodaciOPodnosiocu().getEmail());
        simpleZahtevDTO.setObradjen(zahtev.getStatus() != EStatus.PREDATO);

        return simpleZahtevDTO;
    }

    private static PodnosilacZahteva dtoToPodnosilac(PodnosilacDTO podnosilacZahteva) {
        TLice lice;
        if (podnosilacZahteva.getPoslovnoIme() != null | podnosilacZahteva.getPoslovnoIme().equals(""))
            lice = new TFizickoLice(podnosilacZahteva.getDrzavljanstvo(), OsnovniLicniPodaci.builder()
                    .ime(podnosilacZahteva.getIme())
                    .prezime(podnosilacZahteva.getPrezime())
                    .adresa(podnosilacZahteva.getAdresa())
                    .build());
        else
            lice = new TPravnoLice(podnosilacZahteva.getPoslovnoIme(), podnosilacZahteva.getSediste());

        lice.setBrojTelefona(podnosilacZahteva.getBrojTelefona());
        lice.setEmail(podnosilacZahteva.getEmail());

        return PodnosilacZahteva.builder()
                .podaciOPodnosiocu(lice)
                .build();
    }

    private static Autor dtoToAutor(AutorDTO autor) {
        return Autor.builder()
                .anoniman(autor.isAnoniman())
                .podaciOAutoru(PodaciOAutoru.builder()
                        .pseudonim(autor.getPseudonim())
                        .lice(new TFizickoLice(autor.getDrzavljanstvo(),
                                OsnovniLicniPodaci.builder()
                                        .ime(autor.getIme())
                                        .prezime(autor.getPrezime())
                                        .adresa(autor.getAdresa())
                                        .build(),
                                autor.getGodinaSmrti()))
                        .build())
                .build();
    }
}
