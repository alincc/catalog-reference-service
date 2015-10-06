package no.nb.microservices.catalogreference.core.reference;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.catalogreference.model.RISReferenceString;
import no.nb.microservices.catalogreference.model.Reference;
import no.nb.microservices.catalogreference.util.ModsExtractor;

import java.util.List;

public class RISReference implements IReference {
    private final Mods mods;

    public RISReference(Mods mods) {
        this.mods = mods;
    }

    @Override
    public Reference createReference() {
        Reference reference = new Reference();
        ModsExtractor extractor = new ModsExtractor(mods);
        reference.setReferenceString(new RISReferenceString());

        reference.addData("TY", extractor.extractTypeOfResource());
        reference.addData("ID", mods.getRecordInfo().getRecordIdentifier().getValue());
        reference.addData("T1", String.join(",", extractor.extractTitles()));
        List<String> persons = extractor.extractPersons();
        reference.addData("A1", String.join(",", persons));
        reference.addData("Y1", extractor.extractDateIssued());

        extractor.extractTOC()
                .forEach(toc -> reference.addData("N1", toc));

        extractor.extractNotes()
                .forEach(note -> reference.addData("N1", note));

        reference.addData("IS",extractor.extractEdition());
        reference.addData("SP",extractor.extractExtent());
        reference.addData("PB", extractor.extractPublisher());
        reference.addData("CY", extractor.extractPlace());

        extractor.extractIdentifiers("isbn")
                .forEach(isbn -> reference.addData("SN", isbn.getValue()));

        extractor.extractIdentifiers("issn")
                .forEach(issn -> reference.addData("SN", issn.getValue()));

        extractor.extractSubjects()
                .forEach(subject -> reference.addData("KW", subject));

        return reference;
    }
}
