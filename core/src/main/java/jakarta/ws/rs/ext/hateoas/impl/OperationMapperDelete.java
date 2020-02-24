package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.ws.rs.ext.hateoas.annotations.ResourceOperationDelete;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperationRole;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class OperationMapperDelete implements OperationMapper<ResourceOperationDelete> {

    @Override
    public Class<ResourceOperationDelete> annotation() {
        return ResourceOperationDelete.class;
    }

    @Override
    public ResourceOperation map(Annotation a, Method method) {
        return new ResourceOperation(ResourceOperationRole.DELETE, annotation().getName(), method);

    }
}
