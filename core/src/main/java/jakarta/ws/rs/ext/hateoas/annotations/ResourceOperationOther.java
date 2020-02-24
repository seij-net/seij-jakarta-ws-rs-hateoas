package jakarta.ws.rs.ext.hateoas.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResourceOperationOther {
    String name();
}
