package net.seij.samplestore;

import io.restassured.RestAssured;
import net.seij.samplestore.services.ProductService;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.env.Environment;


/**
 * Extension to mark tests that need to run JaxRS IT
 * Cleansup data stores before each tests
 */
public class JaxRsITExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        ProductService repository = SpringContext.getBean(ProductService.class);
        repository.reset();

        // Configure rest assured en fonction de l'état de Spring en cours avant chaque test
        String port = SpringContext.getBean(Environment.class).getProperty("local.server.port");
        String contextPath = SpringContext.getBean(Environment.class).getProperty("server.servlet.context-path");
        RestAssured.port = Integer.valueOf(port);
        RestAssured.basePath = contextPath==null ? "/" : contextPath;

    }
}

