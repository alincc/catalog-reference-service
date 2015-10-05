package no.nb.microservices.catalogreference.core.reference.service;

import no.nb.microservices.catalogreference.model.Reference;

public interface IReferenceService {
    Reference getWikipediaReference(String id);
    Reference getRISReference(String id);
}
