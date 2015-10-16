package no.nb.microservices.catalogreference.core.reference;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogreference.model.RISReferenceString;
import no.nb.microservices.catalogreference.model.Reference;
import no.nb.microservices.catalogreference.model.ReferenceData;
import no.nb.microservices.catalogreference.util.ItemExtractor;

public class RISReference implements IReference {
    private final ItemResource item;

    public RISReference(ItemResource item) {
        this.item = item;
    }

    @Override
    public Reference createReference() {
        Reference reference = new Reference();
        reference.setReferenceString(new RISReferenceString());

        reference.addData("TY", ItemExtractor.extractTypeOfResource(item));
        reference.addData("ID", item.getMetadata().getIdentifiers().getOaiId());
        reference.addData("T1", String.join(",", ItemExtractor.extractTitles(item)));
        ItemExtractor.extractPersons(item)
                .forEach(person -> reference.addData("A1", person));
        reference.addData("Y1", ItemExtractor.extractDateIssued(item));
        ItemExtractor.extractNotes(item)
                .forEach(note -> reference.addData("N1", note));
        reference.addData("IS", ItemExtractor.extractEdition(item));
        reference.addData("PB", ItemExtractor.extractPublisher(item));
        reference.addData("CY", ItemExtractor.extractPlace(item));
        ItemExtractor.extractIsbn(item)
                .forEach(isbn -> reference.addData("SN", isbn));
        ItemExtractor.extractIssn(item)
                .forEach(issn -> reference.addData("SN", issn));
        ItemExtractor.extractSubjects(item)
                .forEach(subject -> reference.addData("KW", subject));
        reference.getData().add(new ReferenceData("ER",""));
        return reference;
    }
}
