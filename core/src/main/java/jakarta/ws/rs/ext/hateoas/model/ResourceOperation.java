package jakarta.ws.rs.ext.hateoas.model;

import jakarta.lang.NotNull;

import java.lang.reflect.Method;

public class ResourceOperation {
    private final ResourceOperationRole role;
    private final String name;
    private final Method method;

    public ResourceOperation(@NotNull ResourceOperationRole role, @NotNull String name, @NotNull Method method) {
        this.role = role;
        this.name = name;
        this.method = method;
    }

    @NotNull
    public ResourceOperationRole getRole() {
        return role;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Method getMethod() {
        return method;
    }
}
