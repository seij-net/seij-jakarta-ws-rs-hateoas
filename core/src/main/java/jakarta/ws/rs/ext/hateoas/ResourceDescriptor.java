package jakarta.ws.rs.ext.hateoas;

import jakarta.ws.rs.ext.hateoas.model.ResourceOperation;

import java.util.List;

public interface ResourceDescriptor<RES extends Resource<ID>, ID> {

    Class<RES> getEntityClass();

    Class getResourceClass();

    ResourceIdentifierExtractor<RES,ID> identifierExtractor();

    List<ResourceOperation> operations();
}
