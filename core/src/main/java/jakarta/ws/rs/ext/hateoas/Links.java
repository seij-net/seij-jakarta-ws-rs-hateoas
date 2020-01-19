package jakarta.ws.rs.ext.hateoas;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.util.function.Supplier;

/**
 * Utilities to manage links in a simple way, includes embeddable links
 */
public interface Links {

    /**
     * Creates a simple link for an operation. Operation name is required.
     *
     * @param operationName operation name
     * @param <T>           target entity
     * @return a link builder that can be completed
     */
    static <T> LinkEmbeddableBuilder<T> builder(String operationName) {
        return new LinkEmbeddableBuilder<T>().rel(operationName);
    }

    /**
     * Creates an embeddable link with an entity supplier
     *
     * @param operationName name of operation
     * @param supplier      supplier of entity(ies)
     * @param <T>           entity
     * @return
     */
    static <T> LinkEmbeddableBuilder<T> builder(String operationName, Supplier<T> supplier) {
        return new LinkEmbeddableBuilder<T>().rel(operationName).embedded(true).resolve(supplier);
    }

    public static <T> LinkEmbeddableBuilder<T> fromUriBuilder(String operationName, UriBuilder uriBuilder) {
        return new LinkEmbeddableBuilder<T>().rel(operationName).uriBuilder(uriBuilder);
    }
    public static <T> LinkEmbeddableBuilder<T> fromUriBuilder(String operationName, Supplier<T> supplier, UriBuilder uriBuilder) {
        return new LinkEmbeddableBuilder<T>().rel(operationName).embedded(true).resolve(supplier).uriBuilder(uriBuilder);
    }
}
