package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.ws.rs.ext.hateoas.annotations.ResourceOperationPatch;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperationRole;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class OperationMapperPatch implements OperationMapper<ResourceOperationPatch> {

    @Override
    public Class<ResourceOperationPatch> annotation() {
        return ResourceOperationPatch.class;
    }

    @Override
    public ResourceOperation map(Annotation a, Method method) {
        return new ResourceOperation(ResourceOperationRole.PATCH, annotation().getName(), method);

    }
}
