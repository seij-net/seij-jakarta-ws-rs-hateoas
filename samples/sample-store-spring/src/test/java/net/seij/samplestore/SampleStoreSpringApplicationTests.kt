package net.seij.samplestore

import net.seij.samplestore.resources.ProductApiModelInitializer
import net.seij.samplestore.services.ProductCategoryRepository
import net.seij.samplestore.services.ProductRepository
import net.seij.samplestore.services.ProductService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort

import javax.inject.Inject

import org.assertj.core.api.Assertions.assertThat

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class SampleStoreSpringApplicationTests {
    @LocalServerPort
    private val port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Inject
    private lateinit var  productRepository: ProductRepository

    @Inject
    private lateinit var  productCategoryRepository: ProductCategoryRepository

    @Inject
    private lateinit var  productService: ProductService

    @BeforeEach
    fun beforeEach() {
        productService.reset()
    }

    @Test
    fun contexLoads() {
        assertThat(productRepository).isNotNull
        assertThat(productCategoryRepository).isNotNull
        assertThat(productService).isNotNull
    }

    @Test
    @Throws(Exception::class)
    fun shouldGetListOfProductJson() {
        assertThat(this.restTemplate.getForObject("http://localhost:$port/products", String::class.java)).contains("_embedded")
    }

    @Test
    @Throws(Exception::class)
    fun shouldPostProductJson() {
        val actual = this.restTemplate.postForObject("http://localhost:$port/products", ProductApiModelInitializer("product1", "description1"), String::class.java)
        assertThat(actual).contains("_links")
        assertThat(this.restTemplate.getForObject("http://localhost:$port/products", String::class.java)).contains("1")
        val actual2 = this.restTemplate.postForObject("http://localhost:$port/products", ProductApiModelInitializer("product2", "description2"), String::class.java)
        assertThat(actual2).contains("_links")
        assertThat(this.restTemplate.getForObject("http://localhost:$port/products", String::class.java)).contains("2")
    }
}


