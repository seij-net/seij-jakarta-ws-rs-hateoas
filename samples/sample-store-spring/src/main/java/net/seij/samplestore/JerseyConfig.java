package net.seij.samplestore;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.seij.samplestore.resources.ProductResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 * Jersey needs manual registration of resources (unlike Resteasy who can autodetect quite everything)
 */
@Configuration
public class JerseyConfig extends ResourceConfig {
    /**
     * Injects the globally autoconfigured Spring {@link ObjectMapper} to be able to register it with Jersey or else
     * Jersey won't use it and will have its own
     */
    @Inject
    private ObjectMapper objectMapper;

    public JerseyConfig() {
        // Register resources
        register(ProductResource.class);
        // Register global object mapper
        register(new ObjectMapperContextResolver(objectMapper));
    }
}
