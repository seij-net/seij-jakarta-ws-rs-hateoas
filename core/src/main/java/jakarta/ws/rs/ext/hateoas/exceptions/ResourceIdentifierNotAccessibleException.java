package jakarta.ws.rs.ext.hateoas.exceptions;

public class ResourceIdentifierNotAccessibleException extends RuntimeException {
    public ResourceIdentifierNotAccessibleException(Throwable cause) {
        super("Resource identifier not accessible", cause);
    }

}
