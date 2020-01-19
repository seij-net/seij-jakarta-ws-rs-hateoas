package net.seij.samplestore.resources;

import jakarta.ws.rs.ext.hateoas.EntityLinked;
import jakarta.ws.rs.ext.hateoas.Links;
import jakarta.ws.rs.ext.hateoas.MediaTypeHateoas;
import net.seij.samplestore.services.Product;
import net.seij.samplestore.services.ProductService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.seij.samplestore.resources.ModelsKt.toProductApiModel;
import static net.seij.samplestore.resources.ModelsKt.toProductUpdater;

@Path("/products")
public class ProductResource {

    @Inject
    private ProductService productService;

    @GET
    @Path("/")
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response list(@Context UriInfo uriInfo) {
        List<Product> products = productService.listProducts();
        // self link
        Link selfLink = Links.fromUriBuilder("self", uriInfo.getRequestUriBuilder()).build();

        // TODO this is too complicated and confusing to build as is. Need additional tooling to do that
        // TODO unsafe calls ProductResource.class#findById, we need a typesafe way to do this

        // TODO list of items shall be embedded
        Link embeddedListLink = Links.builder("items", () -> products.stream()
                .map(ModelsKt::toProductApiModel)
                .map(it -> {
                    Link selfProductLink = Links.fromUriBuilder("self",
                            uriInfo.getBaseUriBuilder()
                                    .path(ProductResource.class)
                                    .path(ProductResource.class, "findById")
                                    .resolveTemplate("id", it.getId().toString()))
                            .build();
                    return EntityLinked.build(it, Arrays.asList(selfProductLink));
                })
                .collect(Collectors.toList())
        )
                .uriBuilder(uriInfo.getRequestUriBuilder())
                .build();
        List<Link> links = Arrays.asList(selfLink, embeddedListLink);
        return Response.ok(EntityLinked.build(new ProductListResult(products.size()), links)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response findById(@PathParam("id") UUID id, @Context UriInfo uriInfo) {
        // self link
        Link selfLink = Links.fromUriBuilder("self",uriInfo.getRequestUriBuilder()).build();
        Product product = productService.findProductById(id);
        Link deleteLink = Links.fromUriBuilder("delete", uriInfo.getBaseUriBuilder()
                .path(ProductResource.class)
                .path(ProductResource.class, "delete")
                .resolveTemplate("id", product.getId()))
                .type("DELETE")
                .build();
        Link patchLink = Links.fromUriBuilder("patch",uriInfo.getBaseUriBuilder()
                .path(ProductResource.class)
                .path(ProductResource.class, "update")
                .resolveTemplate("id", product.getId()))
                .type("PATCH")
                .build();
        // TODO Need a simpler way to build HAL, remove explicit new, create builder
        EntityLinked entity = EntityLinked.build(toProductApiModel(product), Arrays.asList(selfLink, deleteLink, patchLink));
        return Response.ok(entity).build();
    }

    @GET
    @Path("/name={name}")
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response findByName(@PathParam("name") String name, @Context UriInfo uriInfo) {
        // self link
        Link selfLink = Links.fromUriBuilder("self",uriInfo.getRequestUriBuilder()).build();
        Product product = productService.findProductByName(name);
        // TODO we shall not put them here since it's basic operations for Rest, but how shall we specify that those operations are available or not, especially delete? it's like "can we push the delete button?"
        Link deleteLink = Links.fromUriBuilder("delete",uriInfo.getBaseUriBuilder()
                .path(ProductResource.class)
                .path(ProductResource.class, "delete")
                .resolveTemplate("id", product.getId()))
                .type("DELETE").build();
        Link patchLink = Links.fromUriBuilder("patch",uriInfo.getBaseUriBuilder()
                .path(ProductResource.class)
                .path(ProductResource.class, "update")
                .resolveTemplate("id", product.getId()))
                .type("PATCH").build();
        // TODO Need a simpler way to build HAL, remove explicit new, create builder
        EntityLinked entity = EntityLinked.build(toProductApiModel(product), Arrays.asList(selfLink, deleteLink, patchLink));
        return Response.ok(entity).build();
    }

    @POST
    @Path("/")
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response create(ProductApiModelInitializer initializer, @Context UriInfo uriInfo) {
        // self link
        UUID createdId = productService.createProduct(initializer.getName(), initializer.getDescription());
        // TODO dangerous and error prone
        Link createdLink = Links.fromUriBuilder("self", uriInfo.getBaseUriBuilder()
                .path(ProductResource.class)
                .path(ProductResource.class, "findById")
                .resolveTemplate("id", createdId))
                .build();
        // TODO how to represent an empty object with only links ?
        return Response.ok(EntityLinked.build(Arrays.asList(createdLink))).build();
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response updateViaPatch(@PathParam("id") UUID id, ProductApiModelPatch patch, @Context UriInfo uriInfo) {
        // self link
        Link selfLink = Links.fromUriBuilder("self", uriInfo.getRequestUriBuilder()).build();
        ProductApiModelPatchResult patchResult = new ProductApiModelPatchResult(
                patch.getName().apply(value -> productService.updateProductName(id, value)),
                patch.getDescription().apply(value -> productService.updateProductDescription(id, value))
        );
        // TODO dangerous and error prone
        Link createdLink = Links.fromUriBuilder("entity", uriInfo.getBaseUriBuilder()
                .path(ProductResource.class)
                .path(ProductResource.class, "findById")
                .resolveTemplate("id", id))
                .build();
        // TODO how to represent an empty object with only links ?
        return Response.ok(EntityLinked.build(patchResult, Arrays.asList(selfLink, createdLink))).build();
    }

    @POST
    @Path("/{id}")
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response updateFull(@PathParam("id") UUID id, ProductApiModelUpdater update, @Context UriInfo uriInfo) {
        // self link
        Link selfLink = Links.fromUriBuilder("self", uriInfo.getRequestUriBuilder()).build();
        Product product = productService.updateProduct(id, toProductUpdater(update));
        // TODO dangerous and error prone
        Link createdLink = Links.fromUriBuilder("entity", uriInfo.getBaseUriBuilder()
                .path(ProductResource.class)
                .path(ProductResource.class, "findById")
                .resolveTemplate("id", product.getId()))
                .build();
        // TODO how to represent an empty object with only links ?
        return Response.ok(EntityLinked.build(toProductApiModel(product), Arrays.asList(selfLink, createdLink))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        productService.deleteProduct(id);
        return Response.noContent().build();
    }
}
