package no.nb.microservices.catalogreference.core.metadata.repository;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("catalog-metadata-service")
public interface CatalogMetadataRepository {
    @RequestMapping(value = "/catalog/metadata/{id}/mods", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    Mods getMods(@PathVariable("id") String id);
}
