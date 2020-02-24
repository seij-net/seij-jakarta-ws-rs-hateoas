package jakarta.ws.rs.ext.hateoas.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.*;
import jakarta.ws.rs.ext.hateoas.jackson.HateaoasJacksonContext;
import jakarta.ws.rs.ext.hateoas.jackson.exceptions.HateoasJacksonConfigurationException;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.Arrays;

public class ResourceReferenceSerializer extends StdSerializer<ResourceReference<?, ?>> {
    private final ResourceRegistry resourceRegistry;

    public ResourceReferenceSerializer(@NotNull ResourceRegistry resourceRegistry) {
        super(Resources.class, true);
        this.resourceRegistry = resourceRegistry;
    }

    @Override
    public void serialize(@NotNull ResourceReference<?, ?> resources, @NotNull JsonGenerator jsonGenerator, @NotNull SerializerProvider serializerProvider) throws IOException {
        UriBuilder uriBuilder = HateaoasJacksonContext.extractBaseUriBuilder(serializerProvider);

        // self link
        Link selfLink = resourceRegistry.identityGetLink(resources.getResourceClass(), resources.getResourceIdentifier());

        @NotNull GenericEntityWithLinks<GenericEntityUnit> g = GenericEntityWithLinks.buildEmpty(Arrays.asList(selfLink));
        GenericEntityWithLinksSerializer.Instance.serialize(g, jsonGenerator, serializerProvider);

    }

}
