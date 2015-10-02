package no.nb.microservices.catalogreference.model;

import java.util.ArrayList;
import java.util.List;

public class Reference {
    private String url;
    private List<ReferenceData> data;
    private ReferenceString referenceString;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ReferenceData> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<ReferenceData> data) {
        this.data = data;
    }

    public void setReferenceString(ReferenceString referenceString) {
        this.referenceString = referenceString;
    }

    public void addData(String tag, String data) {
        if (data != null && !data.isEmpty()) {
            getData().add(new ReferenceData(tag, data));
        }
    }

    public String generateReferenceString() {
        return referenceString.getReferenceString(this);
    }
}
