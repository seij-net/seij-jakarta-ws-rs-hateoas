# Jakarta RESTful Web Services (Jax-RS) HATEOAS extensions

## Introduction

** Project in active structuration, not ready for daily use  **

JakartaEE / Jax-rs currently manages HATEOAS links as header links.

This project provides tooling that fits into JakartaEE philosophy and help developers in managing HATEOAS, especially its HAL representation (with _links and _embedded in Json) - the easy way of course. 

The goal is to achieve a variety of formats but is currently focused on 
* Using standard WS-RS that enriches existing links with embeddable links
* Provide HAL representation of links
  * as simple links : `"_links": { "operation": { "href": "..." }, ... }`
  * as embedded links   : `"_embedded": { "operation": { _operation_data_ }, ... }`
* Using Jackson (at first, Jsonb should be next)
* Shall work using
  * JavaEE 8 / JakartaEE 8 (tested with Wildfly 17, with embedds Resteasy)
  * Spring(-Boot) with a JaxRS implementation (tested with Resteasy)



## Code Samples

### Installation

Configure Jackson to add module (Spring-way or JavaEE way)
```
ObjectMapper om = (new ObjectMapper())
om.addModule(JakartaWsRsHateoasModule.Instance)
```
In Rest endpoints, return a HAL object with additional links :

```
@GET
@Path("{id}")
@Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
public Response getById(@PathParam("id") UUID uid, @Context UriInfo uriInfo) {
    Link selfLink = Link.fromUriBuilder(uriInfo.getRequestUriBuilder()).rel("self").build();
    Link otherLink = Link.fromUriBuilder(uriInfo.getRequestUriBuilder()...).rel("operation1").build();
    Link embeddedLink = new LinkEmbedded().fromUriBuilder(uriInfo.getRequestUriBuilder()...)
.embedded(true)
.resolve(()-> getTheContentHere)
.rel("operation2").build();
    return Response.ok(new HAL<>(myobjectByUid, Arrays.asList(selfLink, otherLink, embeddedLink))).build();
}```

## Installation

Project aimed to Maven Central soon. Or else you can checkout it and `mvn clean install` it.
