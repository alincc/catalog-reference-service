package no.nb.microservices.catalogreference.model;

import java.util.Objects;

public class ReferenceData {
    private final String tag;
    private final String value;

    public ReferenceData(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return tag + " - " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceData that = (ReferenceData) o;
        return Objects.equals(tag, that.tag) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, value);
    }
}
