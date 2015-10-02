package no.nb.microservices.catalogreference.model;

public class WikipediaFilmReferenceString implements ReferenceString {

    @Override
    public String getReferenceString(Reference reference) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{{ Kilde video ");
        for (ReferenceData referenceData : reference.getData()) {
            stringBuilder.append("| ");
            stringBuilder.append(referenceData.getTag());
            stringBuilder.append(" = ");
            stringBuilder.append(referenceData.getValue());
            stringBuilder.append(" ");
        }
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }
}
