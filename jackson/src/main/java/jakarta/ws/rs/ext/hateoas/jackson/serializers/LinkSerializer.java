package jakarta.ws.rs.ext.hateoas.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import javax.ws.rs.core.Link;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Jackson serializer for generic Links with Hateoas Hal representation mode
 */
public class LinkSerializer extends StdSerializer<Link> {

    public static final LinkSerializer Instance = new LinkSerializer();

    public static final String KEY_PARAMS = "params";
    public static final String KEY_HREF = "href";
    private static final Set<String> FILTERED_KEYS = Collections.singleton("rel");

    protected LinkSerializer() {
        super(Link.class, true);
    }

    @Override
    public void serialize(Link value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject(value);
        // Filters out unwanted params like "rel", because rel should be the key of the object
        // but keep other ones
        Map<String, String> paramMap = filterMap(value.getParams(), it -> !FILTERED_KEYS.contains(it.getKey()));
        if (!paramMap.isEmpty()) {
            gen.writeObjectField(KEY_PARAMS, paramMap);
        }
        // Writes out href and that's all
        gen.writeStringField(KEY_HREF, value.getUri().toString());
        gen.writeEndObject();
    }

    private <K, V> Map<K, V> filterMap(Map<K, V> map, Predicate<Map.Entry<K, V>> predicate) {
        return map.entrySet().stream()
                .filter(predicate)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
