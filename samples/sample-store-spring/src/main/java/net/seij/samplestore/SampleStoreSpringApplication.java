package net.seij.samplestore;

import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import net.seij.samplestore.resources.ProductResourceDescriptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleStoreSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleStoreSpringApplication.class, args);
    }

    @Bean
    ProductResourceDescriptor productResourceDescriptor() {
        return new ProductResourceDescriptor();
    }

    @Bean
    ResourceRegistry resourceRegistry(ProductResourceDescriptor pd) {
        return ResourceRegistry.builder().addDescriptor(pd).build();
    }

}
