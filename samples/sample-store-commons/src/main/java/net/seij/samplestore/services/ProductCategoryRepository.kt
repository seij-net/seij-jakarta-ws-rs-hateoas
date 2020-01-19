package net.seij.samplestore.services

import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProductCategoryRepository {

    private val categories = mutableMapOf<UUID, ProductCategory>()

    fun create(name: String, description: String) {
        val id = UUID.randomUUID()
        categories[id] = ProductCategory(id, name)
    }

    fun updateName(productId: UUID, value: String) {
        categories[productId]!!.name = value
    }

    fun delete(productId: UUID) {
        categories.remove(productId)
    }
}
