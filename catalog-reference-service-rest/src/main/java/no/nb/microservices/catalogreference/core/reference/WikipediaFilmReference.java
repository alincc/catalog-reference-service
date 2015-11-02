package no.nb.microservices.catalogreference.core.reference;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogreference.model.Reference;
import no.nb.microservices.catalogreference.model.WikipediaFilmReferenceString;
import no.nb.microservices.catalogreference.util.ItemExtractor;

import java.util.List;

public class WikipediaFilmReference implements IReference {
    private final ItemResource item;
    private final String url;

    public WikipediaFilmReference(ItemResource item, String url) {
        this.item = item;
        this.url = url;
    }


    @Override
    public Reference createReference() {
        Reference reference = new Reference();
        reference.setReferenceString(new WikipediaFilmReferenceString());
        reference.setUrl(url);

        List<String> persons = ItemExtractor.extractPersons(item);
        if (!persons.isEmpty()) {
            reference.addData("personer", String.join(",", persons));
        }
        reference.addData("dato", ItemExtractor.extractDateIssued(item));
        List<String> titles = ItemExtractor.extractTitles(item);
        for (String title : titles) {
            reference.addData("tittel", title);
        }
        reference.addData("utgivelsessted", ItemExtractor.extractPlace(item));
        reference.addData("utgiver", ItemExtractor.extractPublisher(item));
        if (item.getMetadata().getIdentifiers().getUrn() != null && !item.getMetadata().getIdentifiers().getUrn().isEmpty()) {
            reference.addData("url",url + item.getMetadata().getIdentifiers().getUrn());
        }

        return reference;
    }
}
