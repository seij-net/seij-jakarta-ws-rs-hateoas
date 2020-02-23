package jakarta.ws.rs.ext.hateoas.impl;

import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.Resource;
import jakarta.ws.rs.ext.hateoas.ResourceEntity;

public class ResourceEntityImpl<RES extends Resource<ID>, ID> implements ResourceEntity<RES, ID> {

    private final RES entity;

    private ResourceEntityImpl(@NotNull RES entity) {
        this.entity = entity;
    }

    @NotNull
    public static <RES extends Resource<ID>, ID> ResourceEntityImpl of(@NotNull RES entity) {
        return new ResourceEntityImpl<>(entity);
    }

    @Override
    @NotNull
    public RES getEntity() {
        return entity;
    }
}
