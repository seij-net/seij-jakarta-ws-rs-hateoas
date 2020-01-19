package jakarta.ws.rs.ext.hateoas;

import javax.ws.rs.core.Link;
import java.util.List;

public class EntityLinkedEmpty implements EntityLinked<EntityLinkedUnit> {

    private final List<Link> links;

    EntityLinkedEmpty(List<Link> links) {
        this.links = links;
    }

    @Override
    public EntityLinkedUnit getEntity() {
        return ENTITY_LINKED_UNIT;
    }

    @Override
    public List<Link> getLinks() {
        return links;
    }
}
