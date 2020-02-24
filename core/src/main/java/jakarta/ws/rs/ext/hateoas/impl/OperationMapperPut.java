package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.ws.rs.ext.hateoas.annotations.ResourceOperationPut;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperationRole;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class OperationMapperPut implements OperationMapper<ResourceOperationPut> {

    @Override
    public Class<ResourceOperationPut> annotation() {
        return ResourceOperationPut.class;
    }

    @Override
    public ResourceOperation map(Annotation a, Method method) {
        return new ResourceOperation(ResourceOperationRole.PUT, annotation().getName(), method);

    }
}
