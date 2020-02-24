package net.seij.samplestore.resources;

import jakarta.ws.rs.ext.hateoas.ResourceDescriptor;
import jakarta.ws.rs.ext.hateoas.ResourceIdentifierExtractor;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ProductResourceDescriptor implements ResourceDescriptor<ProductApiModel, UUID> {
    @Override
    public Class<ProductApiModel> getEntityClass() {
        return ProductApiModel.class;
    }

    @Override
    public Class getResourceClass() {
        return ProductResource.class;
    }

    @Override
    public ResourceIdentifierExtractor<ProductApiModel, UUID> identifierExtractor() {
        return ProductApiModel::getId;
    }

    @Override
    public List<ResourceOperation> operations() {
        return Collections.emptyList();
    }
}
