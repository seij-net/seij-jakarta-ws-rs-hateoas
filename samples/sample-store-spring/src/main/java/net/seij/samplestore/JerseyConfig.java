package net.seij.samplestore;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.seij.samplestore.resources.ProductResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class JerseyConfig extends ResourceConfig {
    @Autowired
    ObjectMapper objectMapper;
    public JerseyConfig() {
        register(ProductResource.class);
        register(new ObjectMapperContextResolver(objectMapper));
    }
}
