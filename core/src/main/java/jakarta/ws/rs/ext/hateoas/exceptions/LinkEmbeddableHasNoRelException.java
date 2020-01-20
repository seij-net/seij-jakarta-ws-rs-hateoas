package jakarta.ws.rs.ext.hateoas.exceptions;

import jakarta.lang.NotNull;

import javax.ws.rs.core.Link;

public class LinkEmbeddableHasNoRelException extends IllegalStateException {
    public LinkEmbeddableHasNoRelException(@NotNull Link link) {
        super("Link " + link.toString() + " has no defined rel");
    }
}