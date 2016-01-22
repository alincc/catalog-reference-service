package no.nb.microservices.catalogreference.rest.controller;

import no.nb.microservices.catalogreference.core.reference.service.IReferenceService;
import no.nb.microservices.catalogreference.model.RISReferenceString;
import no.nb.microservices.catalogreference.model.Reference;
import no.nb.microservices.catalogreference.model.WikipediaBookReferenceString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ReferenceControllerTest {
    private MockMvc mockMvc;
    private ReferenceController controller;

    @Mock
    IReferenceService referenceService;

    @Before
    public void setup() {

        controller = new ReferenceController(referenceService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testCreateWikiReference() throws Exception {
        Reference reference = new Reference();
        reference.setReferenceString(new WikipediaBookReferenceString());
        when(referenceService.getWikipediaReference("41a7fb4e94aab9a88be23745a1504a92")).thenReturn(reference);

        mockMvc.perform(get("/catalog/v1/reference/41a7fb4e94aab9a88be23745a1504a92/wiki")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testCreateRISReference() throws Exception {
        Reference reference = new Reference();
        reference.setReferenceString(new RISReferenceString());

        when(referenceService.getRisAndEnwReference("41a7fb4e94aab9a88be23745a1504a92")).thenReturn(reference);

        mockMvc.perform(get("/catalog/v1/reference/41a7fb4e94aab9a88be23745a1504a92/ris")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testCreateENWReference() throws Exception {
        Reference reference = new Reference();
        reference.setReferenceString(new RISReferenceString());

        when(referenceService.getRisAndEnwReference("41a7fb4e94aab9a88be23745a1504a92")).thenReturn(reference);

        mockMvc.perform(get("/catalog/v1/reference/41a7fb4e94aab9a88be23745a1504a92/enw")).andExpect(status().is2xxSuccessful());
    }
}
