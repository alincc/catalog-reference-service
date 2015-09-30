package no.nb.microservices.catalogreference.util;

import no.nb.microservices.catalogmetadata.model.mods.v3.Identifier;
import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ModsExtractorTest {
    private ModsExtractor modsExtractor;

    @Before
    public void setup() throws Exception {
        File xmlFile = new File(Paths.get(getClass().getResource("/mods3.xml").toURI()).toString());
        JAXBContext context = JAXBContext.newInstance(Mods.class);
        Unmarshaller u = context.createUnmarshaller();
        Mods mods = (Mods) u.unmarshal(xmlFile);
        modsExtractor = new ModsExtractor(mods);
    }

    @Test
    public void whenCreatorsIsFoundResultShouldBeNotEmpty() {
        List<String> creators = modsExtractor.extractCreators();
        assertTrue("Creator list should not be empty",!creators.isEmpty());
    }

    @Test
    public void whenPersonsIsFoundResultShouldBeNotEmpty() {
        List<String> persons = modsExtractor.extractPersons();
        assertTrue("Persons list should not be empty",!persons.isEmpty());
    }

    @Test
    public void whenDateIssuedIsFoundResultShouldBeNotEmpty() {
        String dateIssued = modsExtractor.extractDateIssued();
        assertTrue("DateIssued should not be empty",!dateIssued.isEmpty());
    }

    @Test
    public void whenEditionIsFoundResultShouldBeNotEmpty() {
        String edition = modsExtractor.extractEdition();
        assertTrue("Edition should not be empty", !edition.isEmpty());
    }

    @Test
    public void whenTitlesIsFoundResultShouldBeNotEmpty() {
        List<String> titles = modsExtractor.extractTitles();
        assertTrue("Titles list should not be empty", !titles.isEmpty());
    }

    @Test
    public void whenIdentifiersIsFoundResultShouldBeNotEmpty() {
        List<Identifier> identifiers = modsExtractor.extractIdentifiers("isbn");
        assertTrue("Identifiers list should not be empty", !identifiers.isEmpty());
    }

    @Test
    public void whenPlaceIsFoundResultShouldBeNotEmpty() {
        String place = modsExtractor.extractPlace();
        assertTrue("Place should not be empty", !place.isEmpty());
    }

    @Test
    public void whenPublisherIsFoundResultShouldBeNotEmpty() {
        String publisher = modsExtractor.extractPublisher();
        assertTrue("Publisher should not be empty", !publisher.isEmpty());
    }

    @Test
    public void whenNotesIsFoundResultShouldBeNotEmpty() {
        List<String> notes = modsExtractor.extractNotes();
        assertTrue("Notes list should not be empty", !notes.isEmpty());
    }

    @Test
    public void whenTOCIsFoundResultShouldBeNotEmpty() {
        List<String> toc = modsExtractor.extractTOC();
        assertTrue("TOC list should not be empty", !toc.isEmpty());
    }

    @Test
    public void whenSubjectsIsFoundResultShouldBeNotEmpty() {
        List<String> subjects = modsExtractor.extractSubjects();
        assertTrue("Subjects list should not be empty", !subjects.isEmpty());
    }

}
