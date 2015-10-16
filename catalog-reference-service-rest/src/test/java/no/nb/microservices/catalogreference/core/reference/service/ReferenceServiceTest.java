package no.nb.microservices.catalogreference.core.reference.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogreference.config.ApplicationSettings;
import no.nb.microservices.catalogreference.core.item.repository.CatalogItemRepository;
import no.nb.microservices.catalogreference.core.reference.ReferenceFactory;
import no.nb.microservices.catalogreference.model.Reference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReferenceServiceTest {
    @Mock
    private CatalogItemRepository catalogItemRepository;

    private ReferenceFactory referenceFactory;
    private IReferenceService referenceService;

    @Before
    public void setup() throws Exception {
        ApplicationSettings settings = new ApplicationSettings();
        settings.setUrnbase("http://urn.nb.no/");
        referenceFactory = new ReferenceFactory(settings);
        referenceService = new ReferenceService(catalogItemRepository, referenceFactory);
    }

    @Test
    public void testGetWikipediaBookReference() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ItemResource item = mapper.readValue(new File(Paths.get(getClass().getResource("/item1.json").toURI()).toString()), ItemResource.class);
        when(catalogItemRepository.getItem("41a7fb4e94aab9a88be23745a1504a92")).thenReturn(item);

        Reference reference = referenceService.getWikipediaReference("41a7fb4e94aab9a88be23745a1504a92");

        assertNotNull(reference);
        assertEquals("{{ Kilde bok | forfatter = Hagerup, Inger | utgivelsesår = [2014] | tittel = Så rart | isbn = 1234567890 | utgivelsessted = [Mo i Rana] | forlag = Nordland teater | url = http://urn.nb.no/URN:NBN:no-nb_digibok_2014070158006 | side = }}",reference.generateReferenceString());

    }

    @Test
    public void testGetWikipediaFilmReference() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ItemResource item = mapper.readValue(new File(Paths.get(getClass().getResource("/item2.json").toURI()).toString()), ItemResource.class);
        when(catalogItemRepository.getItem("abcdef1234567890abcdef1234567890")).thenReturn(item);

        Reference reference = referenceService.getWikipediaReference("abcdef1234567890abcdef1234567890");
        assertNotNull(reference);
        assertEquals("{{ Kilde video | personer = Kvæle, Alfrida,Kakestein, Ramona | dato = 2015 | tittel = Last Action Hero | utgivelsessted = Newton | utgiver = Team Newton | url = http://urn.nb.no/URN:NBN:no-nb_1234 }}",reference.generateReferenceString());
    }

    @Test
    public void testGetRISReference() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ItemResource item = mapper.readValue(new File(Paths.get(getClass().getResource("/item2.json").toURI()).toString()), ItemResource.class);
        when(catalogItemRepository.getItem("abcdef1234567890abcdef1234567890")).thenReturn(item);

        Reference reference = referenceService.getRisAndEnwReference("abcdef1234567890abcdef1234567890");
        assertNotNull(reference);
    }
}
