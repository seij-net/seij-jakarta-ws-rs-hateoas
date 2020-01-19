package net.seij.samplestore;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

/**
 * Defines integration test with Spring and our own extension
 *
 * <ul>
 * <li>Tomcat server is launched by Spring,</li>
 * <li>Then {@link JaxRsITExtension} is called to initialize rest-assured and give him Spring http port </li>
 * <li>Resets product repository and other in memory data</li>
 * </ul>
 */
@SpringBootTest(
        // Defines test main class to use during tests, allow overriding main app beans
        classes = ApplicationIntegrationTestConfiguration.class,
        // Allow Spring to override main context beans
        properties = "spring.main.allow-bean-definition-overriding=true",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource(locations = {"/application-test.yml"})
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Inherited
// Our extension must run after Spring, be careful to order
@ExtendWith({SpringExtension.class, JaxRsITExtension.class})
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JaxRsIT {
}



