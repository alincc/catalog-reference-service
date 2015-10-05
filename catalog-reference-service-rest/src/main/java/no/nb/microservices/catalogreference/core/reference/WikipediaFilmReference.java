package no.nb.microservices.catalogreference.core.reference;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.catalogreference.model.Reference;
import no.nb.microservices.catalogreference.model.WikipediaFilmReferenceString;
import no.nb.microservices.catalogreference.util.ModsExtractor;

import java.util.List;

public class WikipediaFilmReference implements IReference {
    private final Mods mods;
    private final String url;

    public WikipediaFilmReference(Mods mods, String url) {
        this.mods = mods;
        this.url = url;
    }

    @Override
    public Reference createReference() {
        Reference reference = new Reference();
        ModsExtractor extractor = new ModsExtractor(mods);
        reference.setReferenceString(new WikipediaFilmReferenceString());
        reference.setUrl(url);

        List<String> persons = extractor.extractPersons();
        if (!persons.isEmpty()) {
            reference.addData("personer", String.join(" , ", persons));
        }
        reference.addData("dato", extractor.extractDateIssued());
        List<String> titles = extractor.extractTitles();
        for (String title : titles) {
            reference.addData("tittel", title);
        }
        reference.addData("utgivelsessted", extractor.extractPlace());
        reference.addData("utgiver", extractor.extractPublisher());
        reference.addData("url", url);

        return reference;
    }
}
