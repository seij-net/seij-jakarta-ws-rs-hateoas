package jakarta.ws.rs.ext.hateoas.jackson.serializers.support;

import com.fasterxml.jackson.core.JsonGenerator;
import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.LinkEmbeddable;
import kotlin.text.StringsKt;

import javax.ws.rs.core.Link;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LinksSerializerUtils {

    public void adaptLinks(@NotNull List<? extends Link> links, @NotNull JsonGenerator jsonGenerator) throws IOException {
        // Partitions the list to keed embedded links separated from regular links
        Map<Boolean, List<Link>> linkTypeSegregation = links.stream().collect(Collectors.partitioningBy(it -> it instanceof LinkEmbeddable && ((LinkEmbeddable<?>) it).embedded));
        List<? extends Link> standard = linkTypeSegregation.get(false);

        List<? extends LinkEmbeddable<?>> embedded = linkTypeSegregation.get(true).stream().map(it -> ((LinkEmbeddable<?>) it)).collect(Collectors.toList());

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

    private void adaptEmbeddedLink(@NotNull JsonGenerator jsonGenerator, @NotNull LinkEmbeddable<?> link) throws IOException {
        validateLink(link);
        jsonGenerator.writeObjectField(link.getRel(), link);
    }

    private void adaptStandardLink(@NotNull JsonGenerator jsonGenerator, @NotNull Link link) throws IOException {
        validateLink(link);
        jsonGenerator.writeObjectField(link.getRel(), link);
    }

    private void validateLink(@NotNull Link link) {
        String rel = link.getRel();
        if (rel == null || StringsKt.isBlank(rel))
            throw new RuntimeException("Can not evaluate link " + link.toString() + ", rel property not defined");
    }
}
