package jakarta.ws.rs.ext.hateoas.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jakarta.ws.rs.ext.hateoas.LinkEmbeddable;

import javax.ws.rs.core.Link;
import java.io.IOException;

public class HateoasLinkEmbeddedSerializer extends StdSerializer<LinkEmbeddable<?>> {
    public static final HateoasLinkEmbeddedSerializer Instance = new HateoasLinkEmbeddedSerializer();

    protected HateoasLinkEmbeddedSerializer() {
        super(LinkEmbeddable.class, true);
    }

    @Override
    public void serialize(LinkEmbeddable<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value.embedded) {
            Object resolved = value.resolver.get();
            if (resolved == null) {
                gen.writeNull();
            } else {
                gen.writeObject(resolved);
            }
        } else {
            gen.writeObject(Link.fromLink(value).build());
        }
    }
}
