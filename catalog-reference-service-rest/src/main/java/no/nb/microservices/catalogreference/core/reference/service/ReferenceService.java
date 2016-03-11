package no.nb.microservices.catalogreference.core.reference.service;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogreference.core.item.repository.CatalogItemRepository;
import no.nb.microservices.catalogreference.core.reference.IReference;
import no.nb.microservices.catalogreference.core.reference.ReferenceFactory;
import no.nb.microservices.catalogreference.model.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceService implements IReferenceService {
    private final CatalogItemRepository catalogItemRepository;
    private final ReferenceFactory referenceFactory;

    @Autowired
    public ReferenceService(CatalogItemRepository catalogItemRepository, ReferenceFactory referenceFactory) {
        this.catalogItemRepository = catalogItemRepository;
        this.referenceFactory = referenceFactory;
    }

    @Override
    public Reference getWikipediaReference(String id) {
        ItemResource item = catalogItemRepository.getItem(id);
        List<String> mediaTypes = item.getMetadata().getMediaTypes();
        for (String mediaType : mediaTypes) {
            if ("film".equalsIgnoreCase(mediaType)) {
                IReference reference = referenceFactory.createReference(ReferenceFactory.WIKIPEDIA_FILM, item);
                return reference.createReference();
            }
        }
        IReference reference = referenceFactory.createReference(ReferenceFactory.WIKIPEDIA_BOOK, item);
        return reference.createReference();
    }

    @Override
    public Reference getRisAndEnwReference(String id) {
        ItemResource item = catalogItemRepository.getItem(id);

        IReference reference = referenceFactory.createReference(ReferenceFactory.RIS, item);
        return reference.createReference();
    }
}
