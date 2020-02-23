package jakarta.ws.rs.ext.hateoas.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import org.jetbrains.annotations.NotNull;

public class Fixtures {

    public static String writeForTest(Object anything) {
        try {
            return configuredOm()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(anything);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Configures an instance of Object mapper for tests
     */
    @NotNull
    private static ObjectMapper configuredOm() {
        ResourceRegistry resourceRegistry = configureResourceRegistry();
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new HateoasModule(resourceRegistry));
        return om;
    }

    @NotNull
    private static ResourceRegistry configureResourceRegistry() {
        ResourceRegistry resourceRegistry = ResourceRegistry.builder().build();
        return resourceRegistry;
    }
}
