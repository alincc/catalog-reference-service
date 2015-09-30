package no.nb.microservices.catalogreference.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "catalog-reference-service")
public class ApplicationSettings {
    private String urnbase;

    public String getUrnbase() {
        return urnbase;
    }

    public void setUrnbase(String urnbase) {
        this.urnbase = urnbase;
    }
}
