package net.seij.samplestore;

import net.seij.samplestore.resources.ProductApiModelInitializer;
import net.seij.samplestore.services.ProductCategoryRepository;
import net.seij.samplestore.services.ProductRepository;
import net.seij.samplestore.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleStoreSpringApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductCategoryRepository productCategoryRepository;

    @Inject
    private ProductService productService;

    @BeforeEach
    public void beforeEach() {
        productService.reset();
    }

    @Test
    public void contexLoads() {
        assertThat(productRepository).isNotNull();
        assertThat(productCategoryRepository).isNotNull();
        assertThat(productService).isNotNull();
    }
	@Test
	public void shouldGetListOfProductJson() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/products", String.class)).contains("_embedded");
	}
	@Test
	public void shouldPostProductJson() throws Exception {
        String actual = this.restTemplate.postForObject("http://localhost:" + port + "/products", new ProductApiModelInitializer("product1", "description1"), String.class);
        assertThat(actual).contains("_links");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/products", String.class)).contains("1");
        String actual2 = this.restTemplate.postForObject("http://localhost:" + port + "/products", new ProductApiModelInitializer("product2", "description2"), String.class);
        assertThat(actual2).contains("_links");
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/products", String.class)).contains("2");
	}
}


