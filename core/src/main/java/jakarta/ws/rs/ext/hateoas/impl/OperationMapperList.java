package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.ws.rs.ext.hateoas.annotations.ResourceOperationList;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperationRole;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class OperationMapperList implements OperationMapper<ResourceOperationList> {

    @Override
    public Class<ResourceOperationList> annotation() {
        return ResourceOperationList.class;
    }

    @Override
    public ResourceOperation map(Annotation a, Method method) {
        return new ResourceOperation(ResourceOperationRole.LIST, annotation().getName(), method);

    }
}
