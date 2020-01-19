package net.seij.samplestore.services


import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class ProductService {

    @Inject
    private lateinit var productRepo: ProductRepository
    @Inject
    private lateinit var productCategoryRepository: ProductCategoryRepository

    fun createProduct(name: String, description: String?): UUID {
        return productRepo.create(name, description)
    }

    fun updateProductName(id: UUID, value: String): PatchResult<String> {
        val current = productRepo.findById(id).name
        return patch(current, value) {
            productRepo.updateDescription(id, value)
            value
        }
    }

    fun updateProductDescription(id: UUID, value: String?): PatchResult<String?> {
        val current = productRepo.findById(id).description
        return patch(current, value) {
            productRepo.updateDescription(id, value)
            value
        }
    }

    fun listProducts(): List<Product> {
        return productRepo.list().toList()
    }

    fun findProductById(id: UUID): Product {
        return productRepo.findById(id)
    }

    fun findProductByName(name: String): Product {
        return productRepo.findByName(name)
    }

    fun deleteProduct(id: UUID) {
        return productRepo.delete(id)
    }

    fun updateProduct(id: UUID, update: ProductUpdater): Product {
        productRepo.update(id, update)
        return productRepo.findById(id)
    }
}