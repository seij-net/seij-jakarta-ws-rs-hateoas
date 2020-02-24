package jakarta.ws.rs.ext.hateoas.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.GenericEntityWithLinks;
import jakarta.ws.rs.ext.hateoas.ResourceEntity;
import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import jakarta.ws.rs.ext.hateoas.jackson.serializers.support.LinksSerializerUtils;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;

import static jakarta.ws.rs.ext.hateoas.jackson.HateaoasJacksonContext.extractBaseUriBuilder;

public class ResourceEntitySerializer extends StdSerializer<ResourceEntity<?, ?>> {

    private final ResourceRegistry resourceRegistry;
    private final LinksSerializerUtils linksSerializerUtils;
    private final GenericEntityWithLinksSerializer entityWithLinksSerializer;


    public ResourceEntitySerializer(@NotNull ResourceRegistry resourceRegistry) {
        super(ResourceEntity.class, true);
        this.resourceRegistry = resourceRegistry;
        this.linksSerializerUtils = new LinksSerializerUtils();
        this.entityWithLinksSerializer = GenericEntityWithLinksSerializer.Instance;
    }

    @Override
    public void serialize(@NotNull ResourceEntity<?, ?> resourceEntity, @NotNull JsonGenerator jsonGenerator, @NotNull SerializerProvider serializerProvider) throws IOException {
        UriBuilder baseUriBuilder = extractBaseUriBuilder(serializerProvider);
        Object entity = resourceEntity.getEntity();
        List<? extends Link> links = resourceRegistry.findLinks(baseUriBuilder, resourceEntity.getEntity());
        GenericEntityWithLinks<?> g = GenericEntityWithLinks.build(entity, links);
        entityWithLinksSerializer.serialize(g, jsonGenerator, serializerProvider);
    }
}
