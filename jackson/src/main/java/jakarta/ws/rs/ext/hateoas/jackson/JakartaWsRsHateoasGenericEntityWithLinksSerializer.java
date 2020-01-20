package jakarta.ws.rs.ext.hateoas.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.NameTransformer;
import jakarta.ws.rs.ext.hateoas.GenericEntityWithLinks;
import jakarta.ws.rs.ext.hateoas.LinkEmbeddable;
import jakarta.ws.rs.ext.hateoas.impl.GenericEntityWithLinksImpl;
import kotlin.text.StringsKt;

import javax.ws.rs.core.Link;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JakartaWsRsHateoasGenericEntityWithLinksSerializer extends StdSerializer<GenericEntityWithLinks<?>> {

    public static final JakartaWsRsHateoasGenericEntityWithLinksSerializer Instance = new JakartaWsRsHateoasGenericEntityWithLinksSerializer();

    private JakartaWsRsHateoasGenericEntityWithLinksSerializer() {
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
        // Partitions the list to keed embedded links separated from regular links
        Map<Boolean, List<Link>> linkTypeSegregation = genericEntityWithLinks.getLinks().stream().collect(Collectors.partitioningBy(it -> it instanceof LinkEmbeddable && ((LinkEmbeddable<?>) it).embedded));
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
        validateLink(link);
        jsonGenerator.writeObjectField(link.getRel(), link);
    }

    private void adaptStandardLink(JsonGenerator jsonGenerator, Link link) throws IOException {
        validateLink(link);
        jsonGenerator.writeObjectField(link.getRel(), link);
    }

    private void validateLink(Link link) {
        String rel = link.getRel();
        if (rel == null || StringsKt.isBlank(rel))
            throw new RuntimeException("Can not evaluate link " + link.toString() + ", rel property not defined");
    }
}
