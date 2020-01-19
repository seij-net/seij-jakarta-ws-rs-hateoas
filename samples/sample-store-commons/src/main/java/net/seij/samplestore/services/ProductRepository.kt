package net.seij.samplestore.services

import net.seij.samplestore.resources.ProductApiModelUpdater
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProductRepository {

    private val products = mutableMapOf<UUID, Product>()
    private val categories = mutableMapOf<UUID, ProductCategory>()

    fun create(name: String, description: String?):UUID {
        val id = UUID.randomUUID()
        products[id] = Product(id, name, description, emptyList())
        return id
    }

    fun updateDescription(productId: UUID, value: String?) {
        products[productId]!!.description = value
    }

    fun updateName(productId: UUID, value: String) {
        products[productId]!!.name = value
    }

    fun delete(productId: UUID) {
        products.remove(productId)
    }

    fun list(): Collection<Product> {
        return products.values
    }

    fun findById(id: UUID): Product {
        return products[id]!!

    }

    fun findByName(name: String): Product {
        return products.entries.firstOrNull { it -> it.value.name == name }!!.value
    }

    fun update(id: UUID, update: ProductUpdater): Product {
        products[id]!!.name = update.name
        products[id]!!.description = update.description
        return products[id]!!
    }

    fun reset() {
        products.clear()
    }
}
