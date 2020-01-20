package jakarta.ws.rs.ext.hateoas;

import jakarta.ws.rs.ext.hateoas.exceptions.LinkEmbeddableHasNoRelException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Link;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LinksTest {

    /**
     * given a classic JaxRS link without rel, new {@link Links} builder will throw an exception
     */
    @Test
    void given_jaxrsLinkWithoutRel_when_fromLink_then_exception() {
        assertThatThrownBy(() -> {
            Links.fromLink(Link.fromUri("http://localhost:8080").build());
        }).isExactlyInstanceOf(LinkEmbeddableHasNoRelException.class);
    }

}
