package jakarta.ws.rs.ext.hateoas;

import jakarta.ws.rs.ext.hateoas.impl.ResourceRegistryBuilderImpl;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

public interface ResourceRegistry {
    static ResourceRegistryBuilder builder() {
        // TODO replace with java service extension
        return new ResourceRegistryBuilderImpl();
    }

    List<? extends Link> findLinks(UriBuilder uriBuilder, Resource<?> entity);

    Link collectionLink(Class<?> resourceClass);

    Link identityGetLink(Class<?> resourceClass, Object identifier);


    ResourceIdentifierExtractor identifierExtractor(Class<?> resourceClass);
}
