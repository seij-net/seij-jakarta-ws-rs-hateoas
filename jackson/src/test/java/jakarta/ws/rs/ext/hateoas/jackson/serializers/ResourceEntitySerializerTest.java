package jakarta.ws.rs.ext.hateoas.jackson.serializers;

import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.Resource;
import jakarta.ws.rs.ext.hateoas.ResourceDescriptor;
import jakarta.ws.rs.ext.hateoas.ResourceIdentifierExtractor;
import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import jakarta.ws.rs.ext.hateoas.annotations.*;
import jakarta.ws.rs.ext.hateoas.impl.ResourceEntityImpl;
import jakarta.ws.rs.ext.hateoas.jackson.Fixtures;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

class ResourceEntitySerializerTest {

    UUID productId1 = UUID.randomUUID();

    @Test
    void shouldBeRegistered() {
        String result = Fixtures.writeForTest(ResourceEntityImpl.of(sampleProduct()), createResourceRegistry(), Fixtures.configureDefaultUriInfo());
        // System.out.println(result);
    }

    @NotNull
    private ResourceRegistry createResourceRegistry() {
        return ResourceRegistry.builder().addDescriptor(MyProduct.class, MyProductResource.class).build();
    }

    MyProduct sampleProduct() {
        return new MyProduct(productId1, "product1", 1000L);
    }

    class MyProduct implements Resource<UUID> {
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

    @Path("myproduct")
    class MyProductResource {
        @GET @ResourceOperationList
        public List<MyProduct> list(){ throw new NotImplementedException("MyProductResource list not implemented");
        }
        @GET @Path("{id}") @ResourceOperationGet
        public MyProduct findById(@PathParam("id") UUID id) { throw new NotImplementedException("MyProductResource get by id not implemented");}
        @POST @ResourceOperationPost
        public void create(MyProductResource r) { throw new NotImplementedException("MyProductResource create not implemented"); }
        @PUT @Path("{id}") @ResourceOperationPut
        public void put(@PathParam("id") UUID id) { throw new NotImplementedException("MyProductResource put not implemented"); }
        @DELETE @Path("{id}") @ResourceOperationDelete
        public void delete(@PathParam("id") UUID id) { throw new NotImplementedException("MyProductResource delete not implemented"); }
        @PATCH @Path("{id}") @ResourceOperationPatch
        public void patch(@PathParam("id") UUID id, MyProductResource patch) { throw new NotImplementedException("MyProductResource patch not implemented"); }

    }

}