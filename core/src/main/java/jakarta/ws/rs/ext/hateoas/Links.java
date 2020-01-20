package jakarta.ws.rs.ext.hateoas;

import jakarta.ws.rs.ext.hateoas.exceptions.LinkEmbeddableHasNoRelException;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
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

    /**
     * @see Link#fromUriBuilder(UriBuilder)
     */

    static <T> LinkEmbeddableBuilder<T> fromUriBuilder(String operationName, UriBuilder uriBuilder) {
        return new LinkEmbeddableBuilder<T>().rel(operationName).uriBuilder(uriBuilder);
    }

    /**
     * @see Link#fromUriBuilder(UriBuilder)
     */

    static <T> LinkEmbeddableBuilder<T> fromUriBuilder(String operationName, Supplier<T> supplier, UriBuilder uriBuilder) {
        return new LinkEmbeddableBuilder<T>().rel(operationName).embedded(true).resolve(supplier).uriBuilder(uriBuilder);
    }

    /**
     * @see Link#valueOf(String)
     */

    static <T> LinkEmbeddableBuilder<T> valueOf(String operationName, String value) {
        return new LinkEmbeddableBuilder<T>().rel(operationName).link(value);
    }

    /**
     * @see Link#fromUri(URI)
     */

    static <T> LinkEmbeddableBuilder<T> fromUri(String operationName, URI uri) {
        return new LinkEmbeddableBuilder<T>().rel(operationName).uri(uri);
    }

    /**
     * @see Link#fromUriBuilder(UriBuilder)
     */

    static <T> LinkEmbeddableBuilder<T> fromUri(String operationName, String uri) {
        return new LinkEmbeddableBuilder<T>().rel(operationName).uri(uri);
    }

    /**
     * @see Link#fromLink(Link)
     */

    static <T> LinkEmbeddableBuilder<T> fromLink(Link link) {
        if (link.getRel() == null) throw new LinkEmbeddableHasNoRelException(link);
        return new LinkEmbeddableBuilder<T>().link(link);
    }

    /**
     * @see Link#fromPath(String)
     */

    static <T> LinkEmbeddableBuilder<T> fromPath(String operationName, String path) {
        return fromUriBuilder(operationName, UriBuilder.fromPath(path));
    }

    /**
     * @see Link#fromResource(Class)
     */
    static <T, R> LinkEmbeddableBuilder<T> fromResource(String operationName, Class<R> resource) {
        return fromUriBuilder(operationName, UriBuilder.fromResource(resource));
    }

    /**
     * @see Link#fromMethod(Class, String)
     */
    static <T, R> LinkEmbeddableBuilder<T> fromMethod(String operationName, Class<R> resource, String method) {
        return fromUriBuilder(operationName, UriBuilder.fromMethod(resource, method));
    }

    /**
     * Creates embeddable link from a resource class and its method name.
     *
     * This is an equivalent of
     * @param operationName
     * @param resource
     * @param method
     * @param <T>
     * @param <R>
     * @return
     */
    static <T, R> LinkEmbeddableBuilder<T> fromResourceMethod(String operationName, Class<R> resource, String method) {
        return fromUriBuilder(operationName, UriBuilder.fromResource(resource).path(resource, method));
    }
}
