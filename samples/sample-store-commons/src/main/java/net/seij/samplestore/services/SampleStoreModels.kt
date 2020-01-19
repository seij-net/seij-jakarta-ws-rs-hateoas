package net.seij.samplestore.services


import java.util.*

data class Product(
        val id: UUID,
        var name: String,
        var description: String?,
        var categories: List<UUID>
)

data class ProductUpdater(
        var name: String,
        var description: String?
)

data class ProductCategory(
        val id: UUID,
        var name: String
)

data class PatchResult<T>(
        val effective: Boolean,
        val newValue: T
)


class PatchValue<T>(val present: Boolean, private val value: T?) {
    fun get(): T? {
        return when {
            !present -> throw Exception("Value not present")
            else -> value
        }
    }

    fun apply(fn: (value: T?) -> PatchResult<T?>):PatchResult<T?> {
        return if (present) fn.invoke(value) else PatchResult(false,value)
    }

}
fun <T> patch(current: T, next: T, fn: () -> T): PatchResult<T> {
    return if (current != next) {
        PatchResult(true, fn.invoke())
    } else PatchResult(false, current)
}