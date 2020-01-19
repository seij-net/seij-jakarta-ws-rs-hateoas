package jakarta.ws.rs.ext.hateoas;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.net.URI;
import java.util.*;
import java.util.function.Supplier;

public class LinkEmbeddable<T> extends Link {

    protected static final RuntimeDelegate.HeaderDelegate<Link> delegate =
            RuntimeDelegate.getInstance().createHeaderDelegate(Link.class);
    public final Supplier<T> resolver;
    public final boolean embedded;
    protected final URI uri;
    /**
     * A map for all the link parameters such as "rel", "type", etc.
     */
    protected final Map<String, String> map;
    LinkEmbeddable(final URI uri, final Map<String, String> map, Supplier<T> resolver, boolean embedded) {
        this.uri = uri;
        this.map = map.isEmpty() ? Collections.<String, String>emptyMap() : Collections
                .unmodifiableMap(new HashMap<String, String>(map));
        this.resolver = resolver;
        this.embedded = embedded;
    }

    public static Link valueOf(String value) {
        return delegate.fromString(value);
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public UriBuilder getUriBuilder() {
        return UriBuilder.fromUri(uri);
    }

    @Override
    public String getRel() {
        return map.get(REL);
    }

    @Override
    public List<String> getRels() {
        final String rels = map.get(REL);
        return rels == null ? Collections.<String>emptyList() : Arrays.asList(rels.split(" +"));
    }

    @Override
    public String getTitle() {
        return map.get(TITLE);
    }

    @Override
    public String getType() {
        return map.get(TYPE);
    }

    @Override
    public Map<String, String> getParams() {
        return map;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof LinkEmbeddable) {
            final LinkEmbeddable otherLink = (LinkEmbeddable) other;
            return uri.equals(otherLink.uri) && map.equals(otherLink.map) && embedded == otherLink.embedded;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.uri != null ? this.uri.hashCode() : 0);
        hash = 89 * hash + (this.map != null ? this.map.hashCode() : 0);
        hash = 89 * hash + (embedded ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return delegate.toString(this);
    }

}
