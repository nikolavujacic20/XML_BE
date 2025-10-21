package com.example.backend.repository;

import org.xmldb.api.base.XMLDBException;
import com.example.backend.db.ResenjeZahtevaDB;
import com.example.backend.resenje.ResenjeZahteva;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class ResenjeZahtevaRepository {

    public void kreiraj(ResenjeZahteva resenjeZahteva) throws XMLDBException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ResenjeZahtevaDB.write(resenjeZahteva);
    }

    public ResenjeZahteva dobaviPoBrojuPrijave(String brojPrijave) throws XMLDBException, IOException, JAXBException {
        return ResenjeZahtevaDB.dobaviPoBrojuPrijave(brojPrijave);
    }
}
