package jakarta.ws.rs.ext.hateoas

import javax.ws.rs.core.Link

interface HAL<T> {
    val entity: T
    val links: List<Link>
    companion object {
        val HAL_UNIT = HALUnit()

        @JvmStatic
        fun <T> build(): HALEmpty {
            return HALEmpty(HAL_UNIT, emptyList());
        }

        @JvmStatic
        fun <T> build(links: List<Link>): HALEmpty {
            return HALEmpty(HAL_UNIT, links);
        }

        @JvmStatic
        fun <T> build(entity:T, links: List<Link>): HAL<T> {
            return HALImpl(entity, links);
        }
    }
}

class HALUnit
class HALEmpty(override val entity: HALUnit, override val links: List<Link>) : HAL<HALUnit>
class HALImpl<T>(override val entity:T, override val links:List<Link>): HAL<T>
