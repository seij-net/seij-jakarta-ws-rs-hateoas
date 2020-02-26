# Jakarta RESTful Web Services (Jax-RS) HATEOAS extensions

## Introduction

*Project in active structuration, not ready for daily use*

![](https://github.com/seij-net/seij-jakarta-ws-rs-hateoas/workflows/Java%20CI/badge.svg)

[Check the Wiki](https://github.com/seij-net/seij-jakarta-ws-rs-hateoas/wiki) for more details!

### Goal

Write easily REST APIs with [HATEOAS](http://stateless.co/hal_specification.html) style with JAX-RS in JakartaEE (JavaEE)
or Spring environments. Provide server and client support based on JAX-RS principles.

### Why

* You can do it manually but it's awful to write and error prone. 
* JaxRS only supports natively header links.
* Spring Hateoas is ...meh, only supports Spring Webmvc, doesn't support Jax-RS
* There is no easy client library

# Sample code

Target code we want to reach with automatic detection:

![](https://img.shields.io/badge/status-todo-red)

```java
@Path("products")
@ResourceHateaos
class ProductsController {
    @GET
    @Path("{id}") @ResourceOperationGet
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    Product getById(@PathParam("id") String id) {  }

    @GET
    @Path("/") @ResourceOperationList
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    ResourceCollection<Product> list(ResourceCollectionFilter filter) {  }

    @POST
    @Path("/") @ResourceOperationOther("special")
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    Product special() {  }

}
```

Project provides tooling to do it manually

![](https://img.shields.io/badge/status-ok-green)

```
@GET
@Path("{id}")
@Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
public Response getById(@PathParam("id") UUID uid, @Context UriInfo uriInfo) {
    Link selfLink = Links.fromUriBuilder("self", uriInfo.getRequestUriBuilder()).rel("self").build();
    Link otherLink = Links.fromUriBuilder("operation1", uriInfo.getRequestUriBuilder()...).build();
    Link embeddedLink = Links.fromUriBuilder("operation2", ()-> getTheContentHere, uriInfo.getRequestUriBuilder()).build();
    return Response.ok(new EntityLinked<>(myobjectByUid, Arrays.asList(selfLink, otherLink, embeddedLink))).build();
}
```

# Stories

Shall produce

* ![](https://img.shields.io/badge/status-todo-red) simple links : `"_links": { "operation": { "href": "..." }, ... }`
* ![](https://img.shields.io/badge/status-todo-red) embedded links   : `"_embedded": { "operation": { _operation_data_ }, ... }`
* ![](https://img.shields.io/badge/status-todo-red) extensions for OpenAPI documentation when using Eclipse Microprofile OpenAPI

Shall work using the following configurations

* ![](https://img.shields.io/badge/status-todo-red) JavaEE 8 / JakartaEE (tests on Wildfly 17 + Resteasy + Jaxb)
* ![](https://img.shields.io/badge/status-todo-red) JavaEE 8 / JakartaEE (tests on Wildfly 17 + Resteasy + Jackson)
* ![](https://img.shields.io/badge/status-todo-red) Spring(-Boot) + Jackson + Resteasy
* ![](https://img.shields.io/badge/status-ok-green) Spring(-Boot) + Jackson + Jersey 
* ![](https://img.shields.io/badge/status-ok-green) Java 8

# Installation

Maven

![](https://img.shields.io/badge/status-todo-red) : publish Maven packages on Github

![](https://img.shields.io/badge/status-todo-red) : publish Maven packages on Maven Central

```xml
<dependencies>
    <dependency>
        <!-- core module required -->
        <groupId>net.seij.jakarta-ws-rs-hateoas</groupId>
        <artifactId>core</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    <dependency>
        <!-- if using jackson -->
        <groupId>net.seij.jakarta-ws-rs-hateoas</groupId>
        <artifactId>jackson</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

![](https://img.shields.io/badge/status-ok-green) If using Jackson, configure Jackson to add module (Spring-way or JavaEE way)

```
ObjectMapper om = (new ObjectMapper())
om.addModule(JakartaWsRsHateoasModule.Instance)
```

## Code Samples

* see samples/sample-store-spring

## Development installation

![](https://img.shields.io/badge/status-ok-green)

* checkout project
* `mvn clean install`

[Documentation in the Wiki](https://github.com/seij-net/seij-jakarta-ws-rs-hateoas/wiki) for more details!

