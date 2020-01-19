package net.seij.samplestore;

import net.seij.samplestore.services.ProductCategoryRepository;
import net.seij.samplestore.services.ProductRepository;
import net.seij.samplestore.services.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Makes all services available to Spring so that @{@link javax.inject.Inject} works without defining them as @{@link org.springframework.stereotype.Service}
 */
@Configuration
public class IOCConfig {
    @Bean
    ProductService productService() {
        return new ProductService();
    }

    @Bean
    ProductRepository productRepository() {
        return new ProductRepository();
    }

    @Bean
    ProductCategoryRepository productCategoryRepository() {
        return new ProductCategoryRepository();
    }
}
