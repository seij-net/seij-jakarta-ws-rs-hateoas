package jakarta.ws.rs.ext.hateoas;

import jakarta.lang.NotNull;

public interface ResourceRegistryBuilder {

    @NotNull
    ResourceRegistryBuilder addDescriptor(ResourceDescriptor res);

    @NotNull
    ResourceRegistry build();
}
