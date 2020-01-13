package jakarta.ws.rs.ext.hateoas

import javax.ws.rs.core.Link

class HAL<T>(
        val entity: T,
        val links: MutableList<Link>
)
