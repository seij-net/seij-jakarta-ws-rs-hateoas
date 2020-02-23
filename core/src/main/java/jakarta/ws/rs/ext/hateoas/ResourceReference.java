package jakarta.ws.rs.ext.hateoas;

public interface ResourceReference<T extends Resource<I>, I> {
    Class<T> getResourceClass();

    I getResourceIdentifier();
}
