package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.Resource;
import jakarta.ws.rs.ext.hateoas.ResourceDescriptor;
import jakarta.ws.rs.ext.hateoas.ResourceIdentifierExtractor;
import jakarta.ws.rs.ext.hateoas.ResourceRegistryBuilder;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;

import java.util.ArrayList;
import java.util.List;

public class ResourceRegistryBuilderImpl implements ResourceRegistryBuilder {

    private final List<ResourceDescriptor> descriptors = new ArrayList<>();

    private final ResourceAnnotationsParser resourceAnnotationsParser = new ResourceAnnotationsParser();

    @Override
    @NotNull
    public ResourceRegistryBuilderImpl addDescriptor(@NotNull ResourceDescriptor res) {
        descriptors.add(res);
        return this;
    }

    @Override
    @NotNull
    public <R extends Resource<I>, I> ResourceRegistryBuilder addDescriptor(@NotNull Class<R> entityClass, @NotNull Class resourceControllerClass) {
        final ResourceIdentifierExtractor resourceIdentifierExtractor = resourceAnnotationsParser.identifierExtractor(entityClass);
        ResourceDescriptor rd = new ResourceDescriptor<R, I>() {
            @Override
            public Class<R> getEntityClass() {
                return entityClass;
            }

            @Override
            public Class getResourceClass() {
                return resourceControllerClass;
            }

            @Override
            public ResourceIdentifierExtractor<R, I> identifierExtractor() {
                return resourceIdentifierExtractor;

            }

            @Override
            public List<ResourceOperation> operations() {
                return resourceAnnotationsParser.listResourceOperations(resourceControllerClass);
            }
        };
        descriptors.add(rd);
        return this;
    }

    @Override
    @NotNull
    public ResourceRegistryImpl build() {
        return new ResourceRegistryImpl(descriptors);
    }
}
