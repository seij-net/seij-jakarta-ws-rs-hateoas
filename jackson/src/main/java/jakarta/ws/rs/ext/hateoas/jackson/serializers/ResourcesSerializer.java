package jakarta.ws.rs.ext.hateoas.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.GenericEntityWithLinks;
import jakarta.ws.rs.ext.hateoas.Links;
import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import jakarta.ws.rs.ext.hateoas.Resources;
import jakarta.ws.rs.ext.hateoas.jackson.HateaoasJacksonContext;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResourcesSerializer extends StdSerializer<Resources<?, ?>> {
    private final ResourceRegistry resourceRegistry;

    public ResourcesSerializer(@NotNull ResourceRegistry resourceRegistry) {
        super(Resources.class, true);
        this.resourceRegistry = resourceRegistry;
    }

    @Override
    public void serialize(@NotNull Resources<?, ?> resources, @NotNull JsonGenerator jsonGenerator, @NotNull SerializerProvider serializerProvider) throws IOException {
        // self link
        Link selfLink = resourceRegistry.collectionLink(resources.getResourceClass());

        UriBuilder uriBuilder = HateaoasJacksonContext.extractBaseUriBuilder(serializerProvider);
        // TODO list of items shall be embedded
        Link embeddedListLink = Links.builder("items", () -> resources.stream()
                .map(it -> resourceRegistry.identityGetLink(resources.getResourceClass(), resourceRegistry.identifierExtractor(resources.getResourceClass()).fetch(it)))
                .collect(Collectors.toList())
        )
                .uriBuilder(uriBuilder)
                .build();
        List<Link> links = Arrays.asList(selfLink, embeddedListLink);
        @NotNull GenericEntityWithLinks<Object> build = GenericEntityWithLinks.build(resources, links);
        GenericEntityWithLinksSerializer.Instance.serialize(build, jsonGenerator, serializerProvider);
    }

}
