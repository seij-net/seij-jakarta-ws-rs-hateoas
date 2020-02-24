package jakarta.ws.rs.ext.hateoas.exceptions;

public class ResourceIdentifierNotFoundException extends RuntimeException {
    public ResourceIdentifierNotFoundException(Class clazz) {
        super("Could not find resource identifier on class " + clazz);
    }
}
