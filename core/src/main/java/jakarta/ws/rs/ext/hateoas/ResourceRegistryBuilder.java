package jakarta.ws.rs.ext.hateoas;

import jakarta.lang.NotNull;

public interface ResourceRegistryBuilder {

    @NotNull
    ResourceRegistryBuilder addDescriptor(@NotNull ResourceDescriptor res);

    @NotNull
    <R extends Resource<I>, I> ResourceRegistryBuilder addDescriptor(@NotNull Class<R> entityClass,@NotNull Class resourceControllerClass);

    @NotNull
    ResourceRegistry build();
}
