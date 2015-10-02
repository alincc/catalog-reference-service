package no.nb.microservices.catalogreference.model;

public class RISReferenceString implements ReferenceString {
    @Override
    public String getReferenceString(Reference reference) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ReferenceData referenceData : reference.getData()) {
            stringBuilder.append(referenceData.getTag());
            stringBuilder.append(" - ");
            stringBuilder.append(referenceData.getValue());
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
