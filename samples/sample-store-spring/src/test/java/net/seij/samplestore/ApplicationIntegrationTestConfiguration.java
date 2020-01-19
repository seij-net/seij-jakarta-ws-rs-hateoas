package net.seij.samplestore;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SampleStoreSpringApplication.class)
public class ApplicationIntegrationTestConfiguration {
    // Later we'll be able to override some things here and declare new beans only for tests
    // @Bean
    //  @Primary
    // @SuppressWarnings("unused")
    // public ApplicationFilesystem filesystem(SpringContextProvider springContextProvider) {
    // }
}
