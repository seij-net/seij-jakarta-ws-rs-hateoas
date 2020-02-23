package jakarta.ws.rs.ext.hateoas.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import jakarta.ws.rs.ext.hateoas.GenericEntityWithLinks;
import jakarta.ws.rs.ext.hateoas.jackson.serializers.support.LinksSerializerUtils;

import java.io.IOException;

public class GenericEntityWithLinksSerializer extends StdSerializer<GenericEntityWithLinks<?>> {

    public static final GenericEntityWithLinksSerializer Instance = new GenericEntityWithLinksSerializer();

    private LinksSerializerUtils linksSerializerUtils = new LinksSerializerUtils();

    private GenericEntityWithLinksSerializer() {

        super(GenericEntityWithLinks.class, true);

    }

    @Override
    public void serialize(GenericEntityWithLinks<?> genericEntityWithLinks, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Object entity = genericEntityWithLinks.getEntity();
        jsonGenerator.writeStartObject(entity);
        Class<?> entityClass = entity.getClass();
        if (genericEntityWithLinks.getEntity() != GenericEntityWithLinks.ENTITY_LINKED_UNIT) {
            JsonSerializer<Object> valueSerializer = serializerProvider.findValueSerializer(entityClass).unwrappingSerializer(NameTransformer.NOP);
            valueSerializer.serialize(entity, jsonGenerator, serializerProvider);
        }
        if (genericEntityWithLinks.getLinks().size() > 0) {
            adaptLinks(genericEntityWithLinks, jsonGenerator);
        }
        jsonGenerator.writeEndObject();
    }

    private void adaptLinks(GenericEntityWithLinks<?> genericEntityWithLinks, JsonGenerator jsonGenerator) throws IOException {
        linksSerializerUtils.adaptLinks(genericEntityWithLinks.getLinks(), jsonGenerator);
    }

}
