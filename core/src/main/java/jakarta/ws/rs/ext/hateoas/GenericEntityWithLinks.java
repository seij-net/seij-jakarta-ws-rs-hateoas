package jakarta.ws.rs.ext.hateoas;

import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.exceptions.GenericEntityLinkedDuplicateRelException;
import jakarta.ws.rs.ext.hateoas.exceptions.GenericEntityWithLinkHasNoRelException;
import jakarta.ws.rs.ext.hateoas.impl.GenericEntityWithLinksImpl;

import javax.ws.rs.core.Link;
import java.lang.reflect.Type;
import java.util.*;

public interface GenericEntityWithLinks<T> {

    GenericEntityUnit ENTITY_LINKED_UNIT = new GenericEntityUnit();

    /**
     * Builds entity wrapper without entity and no links
     */
    @NotNull
    static GenericEntityWithLinks<GenericEntityUnit> buildEmpty() {
        return build(ENTITY_LINKED_UNIT, Collections.emptyList());
    }

    /**
     * Builds entity wrapper without entity but links
     */
    @NotNull
    static GenericEntityWithLinks<GenericEntityUnit> buildEmpty(@NotNull List<Link> links) {
        return build(ENTITY_LINKED_UNIT, links);
    }

    /**
     * Builds entity wrapper without entity but links
     */
    @NotNull
    static GenericEntityWithLinks<GenericEntityUnit> buildEmpty(@NotNull Link... links) {
        return build(ENTITY_LINKED_UNIT, Arrays.asList(links));
    }

    /**
     * Builds entity wrapper with entity and links
     */
    @NotNull
    static <T> GenericEntityWithLinks<T> build(@NotNull T entity, @NotNull Link... links) {
        return build(entity, Arrays.asList(links));
    }

    /**
     * Builds entity wrapper with entity and links
     */
    @NotNull
    static <T> GenericEntityWithLinks<T> build(@NotNull T entity, @NotNull List<Link> links) {
        // Makes sure that all links have a rel and that links have distinct rels
        Set<String> rels = new HashSet<>(links.size());
        links.forEach(it -> {
            String rel = it.getRel();
            if (rel == null) throw new GenericEntityWithLinkHasNoRelException(it);
            if (rels.contains(rel))
                throw new GenericEntityLinkedDuplicateRelException(it, rel);
            rels.add(rel);
        });
        return new GenericEntityWithLinksImpl<>(entity, entity.getClass(), links);
    }

    @NotNull Class<?> getRawType();

    @NotNull Type getType();

    @NotNull
    T getEntity();

    @NotNull
    List<Link> getLinks();


}
