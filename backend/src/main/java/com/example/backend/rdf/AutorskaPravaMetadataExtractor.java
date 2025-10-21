package com.example.backend.rdf;

import com.example.backend.model.ZahtevZaAutorskaPrava;
import com.example.backend.utils.Utils;
import org.apache.jena.rdf.model.*;

public class AutorskaPravaMetadataExtractor {
    final static private String AUTORSKO_DELO_NAMESPACE = "http://www.ftn.uns.ac.rs/autorskoDelo";

    static public Model extract(ZahtevZaAutorskaPrava zahtev) {
        Model model = ModelFactory.createDefaultModel();
        Resource resource = model.createResource(AUTORSKO_DELO_NAMESPACE + "/" + zahtev.getInformacijeOZahtevu().getBrojPrijave());

        addRDFTripletToModel(model, resource, "podnosilac_email", zahtev.getSadrzajZahteva().getPodnosilacZahteva().getPodaciOPodnosiocu().getEmail());
        addRDFTripletToModel(model, resource, "naslov", zahtev.getSadrzajZahteva().getAutorskoDelo().getNaslovAutorskogDela());
        addRDFTripletToModel(model, resource, "vrsta_autorskog_dela", zahtev.getSadrzajZahteva().getAutorskoDelo().getVrstaAutorskogDela());
        addRDFTripletToModel(model, resource, "broj_prijave", zahtev.getInformacijeOZahtevu().getBrojPrijave());
        addRDFTripletToModel(model, resource, "datum_podnosenja", Utils.formatDate(zahtev.getInformacijeOZahtevu().getDatumPodnosenja()));

        return model;
    }

    static private void addRDFTripletToModel(Model model, Resource resource, String propertyName, String propertyValue) {
        Property prop = model.createProperty(AUTORSKO_DELO_NAMESPACE + "/predicate/" + propertyName);
        Literal val = model.createLiteral(propertyValue);
        Statement triplet = model.createStatement(resource, prop, val);

        model.add(triplet);
    }
}
