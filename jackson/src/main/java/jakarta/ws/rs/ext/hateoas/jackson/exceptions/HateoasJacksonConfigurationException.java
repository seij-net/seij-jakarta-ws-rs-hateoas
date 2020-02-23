package jakarta.ws.rs.ext.hateoas.jackson.exceptions;

public class HateoasJacksonConfigurationException extends RuntimeException {
    public HateoasJacksonConfigurationException() {
        super("UriInfo not found in Jackson deserialization context");
    }
}
