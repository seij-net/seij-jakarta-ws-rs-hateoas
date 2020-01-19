= Sample store commons

Contains sample elements, container agnostic

* a product store (services and repository)
* a product API JAX-RS format with regular and embedded links in HATEOAS+HAL format


Writing of REST APIs with links is still a mess and error prone. Main causes:
* there is no meta-data bindings, no strong linking on Java classes and methods.
* creating links and embedded links causes headaches
* HAL providing shall not be explicit, since we shall not stick to HAL only (there are many alternatives) 