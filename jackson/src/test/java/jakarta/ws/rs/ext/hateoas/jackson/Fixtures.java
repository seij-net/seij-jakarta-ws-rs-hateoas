package jakarta.ws.rs.ext.hateoas.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import org.jboss.resteasy.spi.ResteasyUriInfo;
import org.jetbrains.annotations.NotNull;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class Fixtures {
    public static String writeForTest(Object anything) {
        return writeForTest(anything, configureEmptyResourceRegistry(), configureDefaultUriInfo());
    }

    public static UriInfo configureDefaultUriInfo() {
        return new ResteasyUriInfo(URI.create("https://local.test:8081/api"));
    }

    public static String writeForTest(Object anything, ResourceRegistry resourceRegistry, UriInfo uriInfo) {
        try {
            return configuredOm(resourceRegistry)
                    .writerWithDefaultPrettyPrinter()
                    .withAttribute(UriInfo.class, uriInfo)
                    .writeValueAsString(anything);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Configures an instance of Object mapper for tests
     */
    @NotNull
    private static ObjectMapper configuredOm(ResourceRegistry resourceRegistry) {

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new HateoasModule(resourceRegistry));
        return om;
    }

    @NotNull
    private static ResourceRegistry configureEmptyResourceRegistry() {
        ResourceRegistry resourceRegistry = ResourceRegistry.builder().build();
        return resourceRegistry;
    }

}
