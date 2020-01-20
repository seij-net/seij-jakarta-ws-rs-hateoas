package jakarta.ws.rs.ext.hateoas.exceptions;

import jakarta.lang.NotNull;

import javax.ws.rs.core.Link;

public class GenericEntityWithLinkHasNoRelException extends IllegalStateException{
    public GenericEntityWithLinkHasNoRelException(@NotNull Link link) {
        super("Link " + link.toString() + " has no defined rel");
    }
}
