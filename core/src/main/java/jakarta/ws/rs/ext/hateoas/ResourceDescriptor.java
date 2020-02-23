package jakarta.ws.rs.ext.hateoas;

public interface ResourceDescriptor<RES extends Resource<ID>, ID> {
    Class<RES> getResourceClass();

    ResourceIdentifierExtractor<RES,ID> identifierExtractor();
}
