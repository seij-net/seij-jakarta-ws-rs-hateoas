package net.seij.samplestore;

import net.seij.samplestore.services.ProductCategoryRepository;
import net.seij.samplestore.services.ProductRepository;
import net.seij.samplestore.services.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
