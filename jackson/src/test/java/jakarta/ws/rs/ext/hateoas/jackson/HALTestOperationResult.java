package jakarta.ws.rs.ext.hateoas.jackson;

import java.util.UUID;

public class HALTestOperationResult {
    private final UUID uid;
    private final String name;

    public HALTestOperationResult(UUID uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public UUID getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }
}
