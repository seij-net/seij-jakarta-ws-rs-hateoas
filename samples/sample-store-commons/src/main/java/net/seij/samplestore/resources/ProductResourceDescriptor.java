package net.seij.samplestore.resources;

import jakarta.ws.rs.ext.hateoas.ResourceDescriptor;
import jakarta.ws.rs.ext.hateoas.ResourceIdentifierExtractor;

import java.util.UUID;

public class ProductResourceDescriptor implements ResourceDescriptor<ProductApiModel, UUID> {
    @Override
    public Class<ProductApiModel> getResourceClass() {
        return ProductApiModel.class;
    }

    @Override
    public ResourceIdentifierExtractor<ProductApiModel, UUID> identifierExtractor() {
        return ProductApiModel::getId;
    }
}
