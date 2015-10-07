package no.nb.microservices.catalogreference.core.reference;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogreference.model.Reference;
import no.nb.microservices.catalogreference.model.WikipediaBookReferenceString;
import no.nb.microservices.catalogreference.util.ItemExtractor;

import java.util.List;

public class WikipediaBookReference implements IReference {
    private final ItemResource item;
    private final String url;

    public WikipediaBookReference(ItemResource item, String url) {
        this.item = item;
        this.url = url;
    }


    @Override
    public Reference createReference() {
        Reference reference = new Reference();
        reference.setReferenceString(new WikipediaBookReferenceString());
        reference.setUrl(url);

        List<String> creators = ItemExtractor.extractCreators(item);
        if (!creators.isEmpty()) {
            reference.addData("forfatter", String.join(",",creators));
        }
        reference.addData("utgivelsesår", ItemExtractor.extractDateIssued(item));
        List<String> titles = ItemExtractor.extractTitles(item);
        for (String title : titles) {
            reference.addData("tittel", title);
        }
        List<String> isbns = ItemExtractor.extractIsbn(item);
        for (String isbn : isbns) {
            reference.addData("isbn", isbn);
        }
        reference.addData("utgivelsessted", ItemExtractor.extractPlace(item));
        reference.addData("forlag", ItemExtractor.extractPublisher(item));
        if (item.getMetadata().getIdentifiers().getUrns() != null && !item.getMetadata().getIdentifiers().getUrns().isEmpty()) {
            reference.addData("url",url + item.getMetadata().getIdentifiers().getUrns().get(0));
        }


        return reference;
    }
}
