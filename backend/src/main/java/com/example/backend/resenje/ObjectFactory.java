package com.example.backend.resenje;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.autorskapravabackend.resenje
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResenjeZahteva }
     */
    public ResenjeZahteva createResenjeZahteva() {
        return new ResenjeZahteva();
    }
}
