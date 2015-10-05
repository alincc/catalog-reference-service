package no.nb.microservices.catalogreference.core.reference;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.catalogreference.config.ApplicationSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReferenceFactory {
    public static final String WIKIPEDIA_FILM = "wikifilm";
    public static final String WIKIPEDIA_BOOK = "wikibook";
    public static final String RIS = "ris";
    public final ApplicationSettings settings;

    @Autowired
    public ReferenceFactory(ApplicationSettings settings) {
        this.settings = settings;
    }

    public IReference createReference(String type, Mods mods, String urn) {

        if (type.equalsIgnoreCase(WIKIPEDIA_BOOK)) {
            return new WikipediaBookReference(mods, settings.getUrnbase() + urn);
        } else if (type.equalsIgnoreCase(WIKIPEDIA_FILM)) {
            return new WikipediaFilmReference(mods, settings.getUrnbase() + urn);
        } else if (type.equalsIgnoreCase(RIS)) {
            return new RISReference(mods);
        }

        return null;
    }
}
