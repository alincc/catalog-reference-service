package no.nb.microservices.catalogreference.core.reference.service;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.catalogreference.model.Reference;

import java.io.InputStream;

public interface IReferenceService {
    Reference getWikipediaReference(String id);
    Reference getRISReference(String id);
}
