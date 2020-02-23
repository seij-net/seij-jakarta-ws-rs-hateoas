package jakarta.ws.rs.ext.hateoas;

public interface ResourceIdentifierExtractor<R extends Resource<I>, I> {
    I fetch(R resource);
}
