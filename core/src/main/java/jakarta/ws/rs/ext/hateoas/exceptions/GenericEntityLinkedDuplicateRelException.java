package jakarta.ws.rs.ext.hateoas.exceptions;

import jakarta.lang.NotNull;

import javax.ws.rs.core.Link;

public class GenericEntityLinkedDuplicateRelException extends IllegalStateException {
    public GenericEntityLinkedDuplicateRelException(@NotNull Link it, @NotNull String rel) {
        super("Link " + it.toString() + " has duplicate rel name: " + rel);
    }
}
