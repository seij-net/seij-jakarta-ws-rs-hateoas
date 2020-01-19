package net.seij.samplestore

import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import net.seij.samplestore.services.ProductCategoryRepository
import net.seij.samplestore.services.ProductRepository
import net.seij.samplestore.services.ProductService
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.json.Json
import javax.ws.rs.core.MediaType

@JaxRsIT
internal class SampleStoreSpringApplicationTests {

    @Inject
    private lateinit var productRepository: ProductRepository

    @Inject
    private lateinit var productCategoryRepository: ProductCategoryRepository

    @Inject
    private lateinit var productService: ProductService


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
        Given {
            contentType(MediaType.APPLICATION_JSON)
        } When {
            get("/products")
        } Then {
            body(Matchers.containsString("_embedded"))
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldPostProductJson() {
        Given {
            contentType(MediaType.APPLICATION_JSON)
            body(Json.createObjectBuilder()
                    .add("name", "product1")
                    .add("description", "description1")
                    .build().toString())
        } When {
            post("/products")
        } Then {
            body(Matchers.containsString("_links"))
        }

        Given {
            contentType(MediaType.APPLICATION_JSON)
            body(Json.createObjectBuilder()
                    .add("name", "product2")
                    .add("description", "description2")
                    .build().toString())
        } When {
            post("/products")
        } Then {
            body(Matchers.containsString("_links"))
        }

        Given {
            contentType(MediaType.APPLICATION_JSON)
        } When {
            get("/products")
        } Then {
            body(Matchers.containsString("\"total\":2"))
        }

    }
}


