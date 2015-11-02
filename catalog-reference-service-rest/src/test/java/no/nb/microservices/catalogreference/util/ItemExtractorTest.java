package no.nb.microservices.catalogreference.util;

import no.nb.microservices.catalogitem.rest.model.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ItemExtractorTest {

    @Test
    public void whenCreatorsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        Person p1 = new Person();
        Person p2 = new Person();
        Role role = new Role();
        role.setName("creator");
        p1.setRoles(Arrays.asList(role));
        p1.setName("Ola Nordmann");
        item.getMetadata().setPeople(Arrays.asList(p1,p2));

        List<String> creators = ItemExtractor.extractCreators(item);
        assertTrue("Creator list should not be empty", !creators.isEmpty());
    }

    @Test
    public void whenCreatorsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());

        List<String> creators = ItemExtractor.extractCreators(item);
        assertTrue("Creators should be empty", creators.isEmpty());
    }

    @Test
    public void whenPersonsIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        Person person = new Person();
        person.setName("Ola Nordmann");
        item.getMetadata().setPeople(Arrays.asList(person));

        List<String> persons = ItemExtractor.extractPersons(item);
        assertTrue("Persons should not be empty", !persons.isEmpty());
    }

    @Test
    public void whenPersonsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());

        List<String> persons = ItemExtractor.extractPersons(item);
        assertTrue("Persons should be empty", persons.isEmpty());
    }

    @Test
    public void whenDateIssuedIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setOriginInfo(new OriginInfo());
        item.getMetadata().getOriginInfo().setIssued("2010-10-10");

        String dateIssued = ItemExtractor.extractDateIssued(item);
        assertTrue("DateIssued should not be empty", !dateIssued.isEmpty());
    }

    @Test
    public void whenDateIssuedIsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setOriginInfo(new OriginInfo());
        String dateIssued = ItemExtractor.extractDateIssued(item);
        assertTrue("DateIssued should be empty", dateIssued.isEmpty());
    }

    @Test
    public void whenEditionIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setOriginInfo(new OriginInfo());
        item.getMetadata().getOriginInfo().setEdition("edition");

        String edition = ItemExtractor.extractEdition(item);
        assertTrue("Edition should not be empty", !edition.isEmpty());
    }

    @Test
    public void whenEditionIsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setOriginInfo(new OriginInfo());
        String edition = ItemExtractor.extractEdition(item);
        assertTrue("Edition should be empty", edition.isEmpty());
    }

    @Test
    public void whenTitlesIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        TitleInfo titleInfo = new TitleInfo();
        titleInfo.setTitle("Tittel");
        item.getMetadata().setTitleInfos(Arrays.asList(titleInfo));

        List<String> titles = ItemExtractor.extractTitles(item);
        
        assertTrue("Titles should not be empty", !titles.isEmpty());
    }

    @Test
    public void whenTitlesIsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());

        List<String> titles = ItemExtractor.extractTitles(item);
        assertTrue("Titles should be empty", titles.isEmpty());
    }

    @Test
    public void whenIsbnIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setIdentifiers(new Identifiers());
        item.getMetadata().getIdentifiers().setIsbn13(Arrays.asList("1111111111111"));

        List<String> isbns = ItemExtractor.extractIsbn(item);
        assertTrue("ISBNs should not be empty", !isbns.isEmpty());

    }

    @Test
    public void whenIsbnIsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setIdentifiers(new Identifiers());

        List<String> isbns = ItemExtractor.extractIsbn(item);
        assertTrue("ISBNs should be empty", isbns.isEmpty());
    }

    @Test
    public void whenIssnIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setIdentifiers(new Identifiers());
        item.getMetadata().getIdentifiers().setIssn(Arrays.asList("1111-1111"));

        List<String> issns = ItemExtractor.extractIssn(item);
        assertTrue("ISSNs should not be empty", !issns.isEmpty());

    }

    @Test
    public void whenIssnIsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setIdentifiers(new Identifiers());

        List<String> issns = ItemExtractor.extractIssn(item);
        assertTrue("ISSNs should be empty", issns.isEmpty());
    }

    @Test
    public void whenPublisherIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setOriginInfo(new OriginInfo());
        item.getMetadata().getOriginInfo().setPublisher("Test");

        String publisher = ItemExtractor.extractPublisher(item);
        assertTrue("Publisher should not be empty", !publisher.isEmpty());
    }

    @Test
    public void whenPublisherIsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setOriginInfo(new OriginInfo());

        String publisher = ItemExtractor.extractPublisher(item);
        assertTrue("Publisher should be empty", publisher.isEmpty());
    }

    @Test
    public void whenPlaceIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setGeographic(new Geographic());
        item.getMetadata().getGeographic().setPlaceString("Evenes");

        String place = ItemExtractor.extractPlace(item);
        assertTrue("Place should not be empty", !place.isEmpty());
    }

    @Test
    public void whenPlaceIsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());

        String place = ItemExtractor.extractPlace(item);
        assertTrue("Place should be empty", place.isEmpty());
    }

    @Test
    public void whenNotesIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setNotes(Arrays.asList("hej", "notat her"));

        List<String> notes = ItemExtractor.extractNotes(item);
        assertTrue("Notes should not be empty", !notes.isEmpty());
    }

    @Test
    public void whenNotesIsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());

        List<String> notes = ItemExtractor.extractNotes(item);
        assertTrue("Notes should be empty", notes.isEmpty());
    }

    @Test
    public void whenSubjectsIsFoundResultShouldBeNotEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setSubject(new Subject());
        item.getMetadata().getSubject().setTopics(Arrays.asList("bil", "båt"));

        List<String> subjects = ItemExtractor.extractSubjects(item);
        assertTrue("Subjects should not be empty", !subjects.isEmpty());
    }

    @Test
    public void whenSubjectsIsNotFoundResultShouldBeEmpty() {
        ItemResource item = new ItemResource();
        item.setMetadata(new Metadata());
        item.getMetadata().setSubject(new Subject());

        List<String> subjects = ItemExtractor.extractSubjects(item);
        assertTrue("Subjects should be empty", subjects.isEmpty());
    }

}
