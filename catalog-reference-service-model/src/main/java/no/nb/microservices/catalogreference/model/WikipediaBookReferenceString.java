package no.nb.microservices.catalogreference.model;

public class WikipediaBookReferenceString implements ReferenceString {

    @Override
    public String getReferenceString(Reference reference) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{{ Kilde bok");

        boolean dateIssued = false;
        for (ReferenceData referenceData : reference.getData()) {
            if (referenceData.getTag().equalsIgnoreCase("utgivelsesår")) {
                dateIssued = true;
            }
            stringBuilder.append(" | ");
            stringBuilder.append(referenceData.getTag());
            stringBuilder.append(" = ");
            stringBuilder.append(referenceData.getValue());
        }
        if (!dateIssued) {
            stringBuilder.append(" | utgivelsesår = -");
        }
        stringBuilder.append(" | side = }}");
        return stringBuilder.toString();
    }
}
