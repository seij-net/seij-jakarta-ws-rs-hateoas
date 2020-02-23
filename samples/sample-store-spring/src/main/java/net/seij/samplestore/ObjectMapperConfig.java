package net.seij.samplestore;

import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import jakarta.ws.rs.ext.hateoas.jackson.HateoasModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * According to Jackson auto-configurer, you need to implement {@link Jackson2ObjectMapperBuilderCustomizer}
 * to be able to add a new module.
 * <p>
 * Here we add the {@link HateoasModule} so Jackson knows how to handle links and embedded links
 */
@Configuration
public class ObjectMapperConfig implements Jackson2ObjectMapperBuilderCustomizer {
    @Autowired
    private ResourceRegistry resourceRegistry;
    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.modules(new HateoasModule(resourceRegistry));
    }
}
