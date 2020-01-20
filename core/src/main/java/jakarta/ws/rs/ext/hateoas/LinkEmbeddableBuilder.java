package jakarta.ws.rs.ext.hateoas;


import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LinkEmbeddableBuilder<T> implements Link.Builder {
    /**
     * A map for all the link parameters such as "rel", "type", etc.
     */
    protected final Map<String, String> map = new HashMap<String, String>();
    protected UriBuilder uriBuilder;
    protected URI baseUri;
    protected boolean embedded = false;
    protected Supplier<T> resolver = () -> null;

    protected LinkEmbeddableBuilder() {
    }

    public LinkEmbeddableBuilder<T> resolve(Supplier<T> resolver) {
        this.resolver = resolver;
        return this;
    }

    public LinkEmbeddableBuilder<T> embedded(boolean embedded) {
        this.embedded = embedded;
        return this;
    }

    @Override
    public LinkEmbeddableBuilder<T> link(Link link) {
        uriBuilder = UriBuilder.fromUri(link.getUri());
        this.map.clear();
        this.map.putAll(link.getParams());
        return this;
    }

    @Override
    public LinkEmbeddableBuilder<T> link(String link) {
        Link l = Link.valueOf(link);
        return link(l);
    }

    @Override
    public LinkEmbeddableBuilder<T> uriBuilder(UriBuilder uriBuilder) {
        this.uriBuilder = uriBuilder.clone();
        return this;
    }

    @Override
    public LinkEmbeddableBuilder<T> uri(URI uri) {
        if (uri == null) throw new IllegalArgumentException("uri param was null");
        uriBuilder = UriBuilder.fromUri(uri);
        return this;
    }

    @Override
    public LinkEmbeddableBuilder<T> uri(String uri) throws IllegalArgumentException {
        if (uri == null) throw new IllegalArgumentException("uri param was null");
        uriBuilder = UriBuilder.fromUri(uri);
        return this;
    }

    @Override
    public LinkEmbeddableBuilder<T> rel(String rel) {
        if (rel == null) throw new IllegalArgumentException("rel param was null");
        final String rels = this.map.get(Link.REL);
        param(Link.REL, rels == null ? rel : rels + " " + rel);
        return this;
    }

    @Override
    public LinkEmbeddableBuilder<T> title(String title) {
        if (title == null) throw new IllegalArgumentException("title param was null");
        param(Link.TITLE, title);
        return this;

    }

    @Override
    public LinkEmbeddableBuilder<T> type(String type) {
        if (type == null) throw new IllegalArgumentException("type param was null");
        param(Link.TYPE, type);
        return this;
    }

    @Override
    public LinkEmbeddableBuilder<T> param(String name, String value) throws IllegalArgumentException {
        if (name == null) throw new IllegalArgumentException("name param was null");
        if (value == null) throw new IllegalArgumentException("value param was null");
        this.map.put(name, value);
        return this;
    }

    @Override
    public LinkEmbeddable<T> build(Object... values) throws UriBuilderException {
        if (values == null) throw new IllegalArgumentException("values param was null");
        URI built = null;
        if (uriBuilder == null) {
            built = baseUri;
        } else {
            built = this.uriBuilder.build(values);
        }
        if (!built.isAbsolute() && baseUri != null) {
            built = baseUri.resolve(built);
        }
        return new LinkEmbeddable(built, this.map, resolver, embedded);
    }

    @Override
    public LinkEmbeddable<T> buildRelativized(URI uri, Object... values) {
        if (uri == null) throw new IllegalArgumentException("uri param was null");
        if (values == null) throw new IllegalArgumentException("value param was null");
        URI built = uriBuilder.build(values);
        URI with = built;
        if (baseUri != null) with = baseUri.resolve(built);
        return new LinkEmbeddable(uri.relativize(with), this.map, resolver, embedded);
    }

    @Override
    public LinkEmbeddableBuilder<T> baseUri(URI uri) {
        this.baseUri = uri;
        return this;
    }

    @Override
    public LinkEmbeddableBuilder<T> baseUri(String uri) {
        this.baseUri = URI.create(uri);
        return this;
    }
}
