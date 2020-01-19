package jakarta.ws.rs.ext.hateoas;

import javax.ws.rs.core.Link;
import java.util.List;

public class EntityLinkedImpl<T> implements EntityLinked<T> {

    private final T entity;
    private final List<Link> links;

    EntityLinkedImpl(T entity, List<Link> links) {
        this.entity = entity;
        this.links = links;
    }

    @Override
    public T getEntity() {
        return entity;
    }

    @Override
    public List<Link> getLinks() {
        return links;
    }
}
