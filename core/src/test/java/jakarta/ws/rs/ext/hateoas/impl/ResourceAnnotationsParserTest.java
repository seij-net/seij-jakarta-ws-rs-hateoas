package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.ws.rs.ext.hateoas.Resource;
import jakarta.ws.rs.ext.hateoas.ResourceIdentifierExtractor;
import jakarta.ws.rs.ext.hateoas.annotations.*;
import jakarta.ws.rs.ext.hateoas.exceptions.ResourceIdentifierNotFoundException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ResourceAnnotationsParserTest {

    @Test
    void shouldFailWhenNoResourceIdAnnotation() {
        ResourceAnnotationsParser ras = new ResourceAnnotationsParser();
        assertThatThrownBy(() -> ras.identifierExtractor(MyProductWithoutIdentifier.class))
                .isInstanceOf(ResourceIdentifierNotFoundException.class);
    }


    @Test
    @SuppressWarnings("unchecked")
    void shouldReturnIdentifierExtractor() {
        ResourceAnnotationsParser ras = new ResourceAnnotationsParser();
        ResourceIdentifierExtractor extractor = ras.identifierExtractor(MyProduct.class);
        UUID uid = UUID.randomUUID();
        MyProduct product1 = new MyProduct(uid, "product1", 12);
        assertThat(extractor.fetch(product1)).isEqualTo(uid);
    }

    private static class MyProduct implements Resource<UUID> {
        @ResourceIdentifier
        private final UUID id;
        private final String name;
        private final long price;

        MyProduct(UUID id, String name, long price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public long getPrice() {
            return price;
        }
    }

    private static class MyProductWithoutIdentifier implements Resource<UUID> {
        private final UUID id;
        private final String name;
        private final long price;

        MyProductWithoutIdentifier(UUID id, String name, long price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public long getPrice() {
            return price;
        }
    }

    @Path("myproduct")
    class MyProductResource {
        @GET
        @ResourceOperationList
        public List<MyProduct> list() {
            throw new UnsupportedOperationException("MyProductResource list not implemented");
        }

        @GET
        @Path("{id}")
        @ResourceOperationGet
        public MyProduct findById(@PathParam("id") UUID id) {
            throw new UnsupportedOperationException("MyProductResource get by id not implemented");
        }

        @POST
        @ResourceOperationPost
        public void create(MyProductResource r) {
            throw new UnsupportedOperationException("MyProductResource create not implemented");
        }

        @PUT
        @Path("{id}")
        @ResourceOperationPut
        public void put(@PathParam("id") UUID id) {
            throw new UnsupportedOperationException("MyProductResource put not implemented");
        }

        @DELETE
        @Path("{id}")
        @ResourceOperationDelete
        public void delete(@PathParam("id") UUID id) {
            throw new UnsupportedOperationException("MyProductResource delete not implemented");
        }

        @PATCH
        @Path("{id}")
        @ResourceOperationPatch
        public void patch(@PathParam("id") UUID id, MyProductResource patch) {
            throw new UnsupportedOperationException("MyProductResource patch not implemented");
        }

    }

}