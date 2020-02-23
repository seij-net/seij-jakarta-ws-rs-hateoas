package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.ResourceDescriptor;
import jakarta.ws.rs.ext.hateoas.ResourceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class ResourceRegistryBuilderImpl implements ResourceRegistryBuilder {

    private final List<ResourceDescriptor> descriptors = new ArrayList<>();

    @Override
    @NotNull
    public ResourceRegistryBuilderImpl addDescriptor(ResourceDescriptor res) {
        descriptors.add(res);
        return this;
    }

    @Override
    @NotNull
    public ResourceRegistryImpl build() {
        return new ResourceRegistryImpl(descriptors);
    }
}
