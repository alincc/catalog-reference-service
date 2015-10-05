package no.nb.microservices.catalogreference.core.reference;

import no.nb.microservices.catalogmetadata.model.mods.v3.Identifier;
import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.catalogreference.model.Reference;
import no.nb.microservices.catalogreference.model.WikipediaBookReferenceString;
import no.nb.microservices.catalogreference.util.ModsExtractor;

import java.util.List;

public class WikipediaBookReference implements IReference {
    private final Mods mods;
    private final String url;


    public WikipediaBookReference(Mods mods, String url) {
        this.mods = mods;
        this.url = url;
    }

    @Override
    public Reference createReference() {
        Reference reference = new Reference();
        ModsExtractor extractor = new ModsExtractor(mods);
        reference.setReferenceString(new WikipediaBookReferenceString());
        reference.setUrl(url);

        List<String> creators = extractor.extractCreators();
        if (!creators.isEmpty()) {
            reference.addData("forfatter", String.join(",", creators));
        }
        reference.addData("utgivelsesår", extractor.extractDateIssued());
        List<String> titles = extractor.extractTitles();
        for (String title : titles) {
            reference.addData("tittel", title);
        }
        List<Identifier> identifiers = extractor.extractIdentifiers("isbn");
        for (Identifier identifier : identifiers) {
            reference.addData("isbn", identifier.getValue());
        }

        reference.addData("utgivelsessted", extractor.extractPlace());
        reference.addData("forlag", extractor.extractPublisher());

        reference.addData("url", url);

        return reference;
    }
}
