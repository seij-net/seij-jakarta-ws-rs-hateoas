package jakarta.ws.rs.ext.hateoas;

public interface ResourceEntity<RES extends Resource<ID>, ID> {

    RES getEntity();
}
