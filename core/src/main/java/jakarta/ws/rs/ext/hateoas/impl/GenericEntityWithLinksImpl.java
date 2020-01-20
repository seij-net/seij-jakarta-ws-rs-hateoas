package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.GenericEntityWithLinks;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import java.lang.reflect.Type;
import java.util.List;

/**
 * See {@link GenericEntity} documentation for usages.
 *
 * @param <T>
 */
public class GenericEntityWithLinksImpl<T> extends GenericEntity<T> implements GenericEntityWithLinks<T> {

    private final List<Link> links;

    public GenericEntityWithLinksImpl(@NotNull T entity, @NotNull List<Link> links) {
        super(entity);
        this.links = links;
    }

    public GenericEntityWithLinksImpl(@NotNull T entity, @NotNull Type type, @NotNull List<Link> links) {
        super(entity, type);
        this.links = links;
    }

    @Override
    @NotNull
    public List<Link> getLinks() {
        return links;
    }
}
