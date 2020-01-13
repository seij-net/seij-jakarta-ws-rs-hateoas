package jakarta.ws.rs.ext.hateoas.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import jakarta.ws.rs.ext.hateoas.HAL;
import jakarta.ws.rs.ext.hateoas.LinkEmbeddable;

import javax.ws.rs.core.Link;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JakartaWsRsHateoasHalSerializer extends StdSerializer<HAL<?>> {

    public static final JakartaWsRsHateoasHalSerializer Instance = new JakartaWsRsHateoasHalSerializer();

    private JakartaWsRsHateoasHalSerializer() {
        super(HAL.class, true);
    }

    @Override
    public void serialize(HAL<?> hal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        Object entity = hal.getEntity();
        jsonGenerator.writeStartObject(entity);
        Class<?> entityClass = entity.getClass();
        JsonSerializer<Object> valueSerializer = serializerProvider.findValueSerializer(entityClass).unwrappingSerializer(NameTransformer.NOP);
        valueSerializer.serialize(entity, jsonGenerator, serializerProvider);
        if (hal.getLinks().size() > 0) {
            adaptLinks(hal, jsonGenerator);
        }
        jsonGenerator.writeEndObject();
    }

    private void adaptLinks(HAL<?> hal, JsonGenerator jsonGenerator) throws IOException {
        // Partitions the list to keed embedded links separated from regular links
        Map<Boolean, List<Link>> linkTypeSegregation = hal.getLinks().stream().collect(Collectors.partitioningBy(it -> it instanceof LinkEmbeddable && ((LinkEmbeddable<?>) it).embedded));
        List<Link> standard = linkTypeSegregation.get(false);
        List<LinkEmbeddable<?>> embedded = linkTypeSegregation.get(true).stream().map(it -> ((LinkEmbeddable<?>) it)).collect(Collectors.toList());
        if (!standard.isEmpty()) {
            jsonGenerator.writeObjectFieldStart("_links");
            for (Link link : standard) {
                adaptStandardLink(jsonGenerator, link);
            }
            jsonGenerator.writeEndObject();
        }
        if (!embedded.isEmpty()) {
            jsonGenerator.writeObjectFieldStart("_embedded");
            for (LinkEmbeddable<?> link : embedded) {
                adaptEmbeddedLink(jsonGenerator, link);
            }
            jsonGenerator.writeEndObject();
        }
    }

    private void adaptEmbeddedLink(JsonGenerator jsonGenerator, LinkEmbeddable<?> link) throws IOException {
        jsonGenerator.writeObjectField(link.getRel(), link);
    }

    private void adaptStandardLink(JsonGenerator jsonGenerator, Link link) throws IOException {
        jsonGenerator.writeObjectField(link.getRel(), link);
    }
}
