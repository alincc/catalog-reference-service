package no.nb.microservices.catalogreference.core.reference.service;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogmetadata.model.fields.FieldResource;
import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.catalogreference.core.item.repository.CatalogItemRepository;
import no.nb.microservices.catalogreference.core.metadata.repository.CatalogMetadataRepository;
import no.nb.microservices.catalogreference.core.reference.IReference;
import no.nb.microservices.catalogreference.core.reference.ReferenceFactory;
import no.nb.microservices.catalogreference.model.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceService implements IReferenceService {
    private final CatalogMetadataRepository catalogMetadataRepository;
    private final CatalogItemRepository catalogItemRepository;
    private final ReferenceFactory referenceFactory;

    @Autowired
    public ReferenceService(CatalogMetadataRepository catalogMetadataRepository, CatalogItemRepository catalogItemRepository, ReferenceFactory referenceFactory) {
        this.catalogMetadataRepository = catalogMetadataRepository;
        this.catalogItemRepository = catalogItemRepository;
        this.referenceFactory = referenceFactory;
    }

    @Override
    public Reference getWikipediaReference(String id) {
        Mods mods = catalogMetadataRepository.getMods(id);
        FieldResource fields = catalogMetadataRepository.getFields(id);
        List<String> urns = fields.getUrns();
        String urn = "";
        if (!urns.isEmpty()) {
            urn = urns.get(0);
        }
        ItemResource item = catalogItemRepository.getItem(id);
        List<String> mediaTypes = item.getMetadata().getMediaTypes();
        for (String mediaType : mediaTypes) {
            if (mediaType.equalsIgnoreCase("film")) {
                IReference reference = referenceFactory.createReference(ReferenceFactory.WIKIPEDIA_FILM, mods, urn);
                return reference.createReference();
            } else {
                IReference reference = referenceFactory.createReference(ReferenceFactory.WIKIPEDIA_BOOK, mods , urn);
                return reference.createReference();
            }
        }

        return null;
    }

    @Override
    public Reference getRISReference(String id) {
        Mods mods = catalogMetadataRepository.getMods(id);
        FieldResource fields = catalogMetadataRepository.getFields(id);
        List<String> urns = fields.getUrns();
        String urn = "";
        if (!urns.isEmpty()) {
            urn = urns.get(0);
        }
        IReference reference = referenceFactory.createReference(ReferenceFactory.RIS, mods, urn);
        return reference.createReference();
    }
}
