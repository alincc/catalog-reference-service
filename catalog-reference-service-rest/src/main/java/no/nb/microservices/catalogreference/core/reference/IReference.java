package no.nb.microservices.catalogreference.core.reference;

import no.nb.microservices.catalogmetadata.model.mods.v3.Mods;
import no.nb.microservices.catalogreference.model.Reference;

public interface IReference {
    Reference createReference();
}
