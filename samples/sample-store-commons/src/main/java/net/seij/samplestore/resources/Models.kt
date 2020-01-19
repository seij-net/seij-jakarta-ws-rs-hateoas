package net.seij.samplestore.resources

import net.seij.samplestore.services.PatchResult
import net.seij.samplestore.services.PatchValue
import net.seij.samplestore.services.Product
import net.seij.samplestore.services.ProductUpdater
import java.util.*

annotation class ApiModel

data class ProductListResult(
        val total: Int
)

data class ProductApiModel(
        val id: UUID,
        val name: String,
        val description: String?
)

@ApiModel
class ProductApiModelInitializer(
        val name: String,
        val description: String?
)

data class ProductApiModelUpdater(
        val name: String,
        val description: String?
)

data class ProductApiModelPatch(
        val name: PatchValue<String>,
        val description: PatchValue<String?>
)

data class ProductApiModelPatchResult(
        val name: PatchResult<String>,
        val description: PatchResult<String?>
)

fun toProductApiModel(product: Product): ProductApiModel {
    return ProductApiModel(id = product.id, name = product.name, description = product.description)
}

fun toProductUpdater(apiModel: ProductApiModelUpdater): ProductUpdater {
    return ProductUpdater(apiModel.name, apiModel.description)
}