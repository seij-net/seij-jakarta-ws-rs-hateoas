package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.ws.rs.ext.hateoas.Resource;
import jakarta.ws.rs.ext.hateoas.ResourceDescriptor;
import jakarta.ws.rs.ext.hateoas.ResourceIdentifierExtractor;
import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.core.Link;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceRegistryImpl implements ResourceRegistry {
    private final List<ResourceDescriptor> descriptors;
    private final Map<Class, ResourceDescriptor> descriptorMap;

    public ResourceRegistryImpl(List<ResourceDescriptor> descriptors) {
        this.descriptors = descriptors;
        descriptorMap = new HashMap<>();
        descriptors.forEach(it -> descriptorMap.put(it.getResourceClass(), it));
    }

    @Override
    public List<? extends Link> findLinks(Resource<?> entity) {
        throw new NotImplementedException();
    }

    @Override
    public Link collectionLink(Class<?> resourceClass) {
        throw new NotImplementedException();
    }

    @Override
    public Link identityGetLink(Class<?> resourceClass, Object identifier) {
        throw new NotImplementedException();
    }

    @Override
    public ResourceIdentifierExtractor identifierExtractor(Class<?> resourceClass) {
        return descriptorMap.get(resourceClass).identifierExtractor();
    }
}
