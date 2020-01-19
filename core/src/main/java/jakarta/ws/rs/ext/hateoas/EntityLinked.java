package jakarta.ws.rs.ext.hateoas;

import javax.ws.rs.core.Link;
import java.util.Collections;
import java.util.List;

public interface EntityLinked<T> {

    EntityLinkedUnit ENTITY_LINKED_UNIT = new EntityLinkedUnit();

    static EntityLinkedEmpty build() {
        return new EntityLinkedEmpty(Collections.emptyList());
    }

    static EntityLinkedEmpty build(List<Link> links) {
        return new EntityLinkedEmpty(links);
    }

    static <T> EntityLinked<T> build(T entity, List<Link> links) {
        return new EntityLinkedImpl<>(entity, links);
    }

    T getEntity();

    List<Link> getLinks();
}
