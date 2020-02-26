package net.seij.samplestore;

import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import net.seij.samplestore.resources.ProductApiModel;
import net.seij.samplestore.resources.ProductResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleStoreSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleStoreSpringApplication.class, args);
    }

    @Bean
    ResourceRegistry resourceRegistry() {
        return ResourceRegistry.builder()
                .addDescriptor(ProductApiModel.class, ProductResource.class)
                .build();
    }

}
