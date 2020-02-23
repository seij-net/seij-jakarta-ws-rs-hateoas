package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.ws.rs.ext.hateoas.Resource;
import jakarta.ws.rs.ext.hateoas.ResourceReference;

public class ResourceReferenceImpl<T extends Resource<I>, I> implements ResourceReference<T, I> {
    private final Class<T> resourceClass;
    private final I identifier;

    private ResourceReferenceImpl(Class<T> resourceClass, I identifier) {

        this.resourceClass = resourceClass;
        this.identifier = identifier;
    }

    public static <T extends Resource<I>, I> ResourceReferenceImpl<T, I> of(Class<T> resourceClass, I identifier) {
        return new ResourceReferenceImpl<>(resourceClass, identifier);
    }

    @Override
    public I getResourceIdentifier() {
        return identifier;
    }

    @Override
    public Class<T> getResourceClass() {
        return resourceClass;
    }
}
