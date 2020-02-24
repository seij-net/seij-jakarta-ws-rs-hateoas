package jakarta.ws.rs.ext.hateoas;

import jakarta.ws.rs.ext.hateoas.exceptions.GenericEntityLinkedDuplicateRelException;
import jakarta.ws.rs.ext.hateoas.exceptions.GenericEntityWithLinkHasNoRelException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Link;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GenericEntityWithLinksTest {


    /**
     * Makes sure that when creating a new entity, if one of the provided links has no rel an exception is thrown
     */
    @Test
    void given_jaxrsLinkWithoutRel_when_buildEntity_then_throwException() {
        // We explicitly use classic JaxRS builder here to trigger error
        Link jaxrsLinkWithoutRel = Link.fromUri("http://localhost:8080").build();
        assertThatThrownBy(() -> {
            GenericEntityWithLinks.build(jaxrsLinkWithoutRel, jaxrsLinkWithoutRel);
        }).isExactlyInstanceOf(GenericEntityWithLinkHasNoRelException.class);
    }

    /**
     * When creating entity, makes sure that there is no duplicate rel for this entity
     */
    @Test
    void given_jaxrsLinksWithDuplicateRel_when_buildEntity_then_throwException() {
        Link l1 = Links.fromUri("self", "http://localhost:8080").build();
        Link l2 = Links.fromUri("self", "http://localhost:8081").build();
        assertThatThrownBy(() -> {
            GenericEntityWithLinks.buildEmpty(l1, l2);
        }).isExactlyInstanceOf(GenericEntityLinkedDuplicateRelException.class);
    }


}