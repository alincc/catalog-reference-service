package no.nb.microservices.catalogreference.core.item.repository;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("catalog-item-service")
public interface CatalogItemRepository {
    @RequestMapping(value = "/catalog/items/{id}", method = RequestMethod.GET)
    ItemResource getItem(@PathVariable("id") String id);
}
