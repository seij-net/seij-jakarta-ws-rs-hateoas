package net.seij.samplestore.resources;

import jakarta.ws.rs.ext.hateoas.MediaTypeHateoas;
import jakarta.ws.rs.ext.hateoas.ResourceListOfList;
import jakarta.ws.rs.ext.hateoas.annotations.*;
import jakarta.ws.rs.ext.hateoas.impl.ResourceEntityImpl;
import jakarta.ws.rs.ext.hateoas.impl.ResourceReferenceImpl;
import net.seij.samplestore.services.Product;
import net.seij.samplestore.services.ProductService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.UUID;

import static net.seij.samplestore.resources.ModelsKt.toProductApiModel;
import static net.seij.samplestore.resources.ModelsKt.toProductUpdater;

@Path("/products")
public class ProductResource {

    @Inject
    private ProductService productService;

    @GET
    @Path("/") @ResourceOperationList
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response list(@Context UriInfo uriInfo) {
        List<Product> products = productService.listProducts();
        return Response.ok(ResourceListOfList.of(
                ProductApiModel.class,
                products.stream().map(ModelsKt::toProductApiModel)))
                .build();
    }

    @GET
    @Path("{id}") @ResourceOperationGet
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response findById(@PathParam("id") UUID id, @Context UriInfo uriInfo) {
        Product product = productService.findProductById(id);
        return Response.ok(ResourceEntityImpl.of(toProductApiModel(product))).build();
    }

    @GET
    @Path("/name={name}") @ResourceOperationOther(name="getByName")
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response findByName(@PathParam("name") String name, @Context UriInfo uriInfo) {
        Product product = productService.findProductByName(name);
        return Response.ok(ResourceEntityImpl.of(toProductApiModel(product))).build();
    }

    @POST
    @Path("/") @ResourceOperationPost
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response create(ProductApiModelInitializer initializer, @Context UriInfo uriInfo) {
        UUID createdId = productService.createProduct(initializer.getName(), initializer.getDescription());
        return Response.ok(ResourceReferenceImpl.of(ProductApiModel.class, createdId)).build();
    }

    @PATCH
    @Path("/{id}") @ResourceOperationPatch
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response updateViaPatch(@PathParam("id") UUID id, ProductApiModelPatch patch, @Context UriInfo uriInfo) {
        ProductApiModelPatchResult patchResult = new ProductApiModelPatchResult(
                patch.getName().apply(value -> productService.updateProductName(id, value)),
                patch.getDescription().apply(value -> productService.updateProductDescription(id, value))
        );
        return Response.ok(patchResult).build();
    }

    @PUT
    @Path("/{id}") @ResourceOperationPut
    @Produces(MediaTypeHateoas.APPLICATION_HAL_JSON)
    public Response updateFull(@PathParam("id") UUID id, ProductApiModelUpdater update, @Context UriInfo uriInfo) {
        Product product = productService.updateProduct(id, toProductUpdater(update));
        return Response.ok(ResourceEntityImpl.of(toProductApiModel(product))).build();
    }

    @DELETE @ResourceOperationDelete
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        productService.deleteProduct(id);
        return Response.noContent().build();
    }

}
