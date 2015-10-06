package no.nb.microservices.catalogreference.core.reference.service;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.Metadata;
import no.nb.microservices.catalogmetadata.model.fields.FieldResource;
import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.catalogreference.config.ApplicationSettings;
import no.nb.microservices.catalogreference.core.item.repository.CatalogItemRepository;
import no.nb.microservices.catalogreference.core.metadata.repository.CatalogMetadataRepository;
import no.nb.microservices.catalogreference.core.reference.ReferenceFactory;
import no.nb.microservices.catalogreference.model.Reference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReferenceServiceTest {
    @Mock
    private CatalogMetadataRepository catalogMetadataRepository;

    @Mock
    private CatalogItemRepository catalogItemRepository;

    private ReferenceFactory referenceFactory;
    private IReferenceService referenceService;
    private Mods modsBook;
    private Mods modsFilm;

    @Before
    public void setup() throws Exception {
        ApplicationSettings settings = new ApplicationSettings();
        settings.setUrnbase("http://urn.nb.no/");
        referenceFactory = new ReferenceFactory(settings);
        referenceService = new ReferenceService(catalogMetadataRepository,catalogItemRepository, referenceFactory);

        File xmlbook = new File(Paths.get(getClass().getResource("/mods3.xml").toURI()).toString());
        File xmlfilm = new File(Paths.get(getClass().getResource("/mods9.xml").toURI()).toString());
        JAXBContext context = JAXBContext.newInstance(Mods.class);
        Unmarshaller u = context.createUnmarshaller();
        modsBook = (Mods) u.unmarshal(xmlbook);
        modsFilm = (Mods) u.unmarshal(xmlfilm);
    }

    @Test
    public void testGetWikipediaBookReference() throws Exception {
        Metadata metadata = new Metadata();
        metadata.setMediaTypes(Arrays.asList("Bøker"));
        FieldResource fields = new FieldResource();
        fields.setUrns(Arrays.asList("URN:NBN:no-nb_digibok_2014070158006"));

        ItemResource itemResource = new ItemResource();
        itemResource.setMetadata(metadata);

        when(catalogItemRepository.getItem("508e84edfd13adc9a2b4275c16dea59a")).thenReturn(itemResource);
        when(catalogMetadataRepository.getFields("508e84edfd13adc9a2b4275c16dea59a")).thenReturn(fields);
        when(catalogMetadataRepository.getMods("508e84edfd13adc9a2b4275c16dea59a")).thenReturn(modsBook);

        Reference wikipediaReference = referenceService.getWikipediaReference("508e84edfd13adc9a2b4275c16dea59a");
        assertNotNull(wikipediaReference);
        assertEquals("{{ Kilde bok | forfatter = Tolkien, J.R.R. | utgivelsesår = 1992 | tittel = Ringenes herre | isbn = 8210035789 | utgivelsessted = [Oslo] | forlag = Tiden | url = http://urn.nb.no/URN:NBN:no-nb_digibok_2014070158006 | side = }}", wikipediaReference.generateReferenceString());
    }

    @Test
    public void testGetWikipediaFilmReference() throws Exception {
        Metadata metadata = new Metadata();
        metadata.setMediaTypes(Arrays.asList("Film"));
        FieldResource fields = new FieldResource();
        fields.setUrns(Arrays.asList("URN:NBN:no-nb_video_5882"));
        ItemResource itemResource = new ItemResource();
        itemResource.setMetadata(metadata);

        when(catalogItemRepository.getItem("8186575e69b7d331bc4b7b92d9b504f6")).thenReturn(itemResource);
        when(catalogMetadataRepository.getFields("8186575e69b7d331bc4b7b92d9b504f6")).thenReturn(fields);
        when(catalogMetadataRepository.getMods("8186575e69b7d331bc4b7b92d9b504f6")).thenReturn(modsFilm);

        Reference wikipediaReference = referenceService.getWikipediaReference("8186575e69b7d331bc4b7b92d9b504f6");
        assertNotNull(wikipediaReference);
    }

    @Test
    public void testGetRISReference() throws Exception {
        when(catalogMetadataRepository.getMods("508e84edfd13adc9a2b4275c16dea59a")).thenReturn(modsBook);
        when(catalogMetadataRepository.getFields("508e84edfd13adc9a2b4275c16dea59a")).thenReturn(new FieldResource());

        Reference risReference = referenceService.getRISReference("508e84edfd13adc9a2b4275c16dea59a");
        assertNotNull(risReference);

    }
}
