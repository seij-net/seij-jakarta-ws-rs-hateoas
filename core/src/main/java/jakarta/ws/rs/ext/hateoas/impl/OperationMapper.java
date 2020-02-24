package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

interface OperationMapper<A> {
    Class<A> annotation();

    ResourceOperation map(Annotation a, Method method);
}
