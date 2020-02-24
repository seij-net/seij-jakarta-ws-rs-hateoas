package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.lang.NotNull;
import jakarta.lang.Nullable;
import jakarta.ws.rs.ext.hateoas.ResourceIdentifierExtractor;
import jakarta.ws.rs.ext.hateoas.annotations.ResourceIdentifier;
import jakarta.ws.rs.ext.hateoas.exceptions.ResourceIdentifierNotAccessibleException;
import jakarta.ws.rs.ext.hateoas.exceptions.ResourceIdentifierNotFoundException;
import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ResourceAnnotationsParser {

    public static final Map<Class<? extends Annotation>, OperationMapper> MAPPERS = new HashMap<>();

    ResourceAnnotationsParser() {
        Arrays.asList(
                new OperationMapperGet(),
                new OperationMapperList(),
                new OperationMapperPost(),
                new OperationMapperPut(),
                new OperationMapperPatch(),
                new OperationMapperDelete(),
                new OperationMapperOther())
                .forEach(it -> MAPPERS.put(it.annotation(), it));
    }

    public List<ResourceOperation> listResourceOperations(Class resourceControllerClass) {
        return Arrays.stream(resourceControllerClass.getMethods())
                .map(this::toOperation)
                .filter(Objects::isNull)
                .collect(Collectors.toList())
                ;

    }

    @Nullable
    private ResourceOperation toOperation(@NotNull Method method) {
        return Arrays.stream(method.getDeclaredAnnotations())
                .filter(it -> MAPPERS.containsKey(it.annotationType()))
                .findFirst()
                .map(it -> MAPPERS.get(it.annotationType()))
                .map(it -> toOperation(method))
                .orElse(null);

    }

    /**
     * @param entityClass class that muse have @{@link ResourceIdentifier} annotation on one of its fields
     * @return a function that extracts an identifier from a resource
     */
    public ResourceIdentifierExtractor identifierExtractor(Class entityClass) {
        Optional<Field> fieldIdentifier = Arrays.stream(entityClass.getDeclaredFields())
                .map(this::ensureAccessible)
                .filter(this::isResourceIdentifierFIeld)
                .findFirst();
        Optional<ResourceIdentifierExtractor> resourceIdentifierExtractor = fieldIdentifier.map(it -> resource -> {
            try {
                return it.get(resource);
            } catch (IllegalAccessException e) {
                throw new ResourceIdentifierNotAccessibleException(e);
            }
        });
        return resourceIdentifierExtractor.orElseThrow(() -> new ResourceIdentifierNotFoundException(entityClass));
    }

    private boolean isResourceIdentifierFIeld(Field f) {
        return f.getAnnotation(ResourceIdentifier.class) != null;
    }

    private Field ensureAccessible(Field f) {
        if (!f.isAccessible()) f.setAccessible(true);
        return f;
    }
}
