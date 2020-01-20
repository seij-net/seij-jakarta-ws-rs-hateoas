package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.GenericEntityWithLinks;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * See {@link GenericEntity} documentation for usages.
 * <p>
 * Note that we can not extend {@link GenericEntity} because some implementations (Jersey) unwraps
 * the entity and discards it before going through serialization.
 * <p>
 * Because of that, serializers are unaware that it was a generic entity, so we can not make
 * serializers detect this type properly.
 * <p>
 * Because of that, we need to "copy" Generic entity code to make this work so that our own
 * serializers can be called.
 *
 * @param <T>
 */
public class GenericEntityWithLinksImpl<T> implements GenericEntityWithLinks<T> {
    private final GenericEntity<T> delegate;
    private final List<Link> links;

    protected GenericEntityWithLinksImpl(final T entity) {
        this.delegate = new GenericEntity<>(entity, entity.getClass());
        this.links = Collections.emptyList();
    }

    public GenericEntityWithLinksImpl(@NotNull T entity, @NotNull List<Link> links) {
        this.delegate = new GenericEntity<>(entity, entity.getClass());
        this.links = links;
    }

    public GenericEntityWithLinksImpl(@NotNull T entity, @NotNull Type type, @NotNull List<Link> links) {
        this.delegate = new GenericEntity<>(entity, type);
        this.links = links;
    }

    /**
     * Gets the raw type of the enclosed entity. Note that this is the raw type of
     * the instance, not the raw type of the type parameter. I.e. in the example
     * in the introduction, the raw type is {@code ArrayList} not {@code List}.
     *
     * @return the raw type.
     */
    @Override
    @NotNull
    public final Class<?> getRawType() {
        return delegate.getRawType();
    }

    /**
     * Gets underlying {@code Type} instance. Note that this is derived from the
     * type parameter, not the enclosed instance. I.e. in the example
     * in the introduction, the type is {@code List<String>} not
     * {@code ArrayList<String>}.
     *
     * @return the type
     */
    @Override
    @NotNull
    public final Type getType() {
        return delegate.getType();
    }

    /**
     * Get the enclosed entity.
     *
     * @return the enclosed entity.
     */
    @Override
    @NotNull
    public final T getEntity() {
        return delegate.getEntity();
    }

    @Override
    @NotNull
    public List<Link> getLinks() {
        return links;
    }


    @Override
    public boolean equals(Object obj) {
        boolean result = this == obj;
        if (!result && obj instanceof GenericEntityWithLinksImpl) {
            // Compare inner type for equality
            GenericEntityWithLinksImpl<?> that = (GenericEntityWithLinksImpl<?>) obj;
            return this.delegate.getType().equals(that.getType())
                    && this.delegate.getEntity().equals(that.delegate.getEntity())
                    && that.links.containsAll(this.links)
                    ;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(delegate, links);
    }

    @Override
    public String toString() {
        return "GenericEntity{" + delegate.getEntity().toString() + ", " + delegate.getType().toString() + ", " + links + "}";
    }
}
