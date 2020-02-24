package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.ws.rs.ext.hateoas.annotations.ResourceOperationOther;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperationRole;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class OperationMapperOther implements OperationMapper<ResourceOperationOther> {

    @Override
    public Class<ResourceOperationOther> annotation() {
        return ResourceOperationOther.class;
    }

    @Override
    public ResourceOperation map(Annotation a, Method method) {
        return new ResourceOperation(ResourceOperationRole.OTHER, annotation().getName(), method);

    }
}
