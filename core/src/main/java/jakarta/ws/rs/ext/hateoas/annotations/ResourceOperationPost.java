package jakarta.ws.rs.ext.hateoas.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResourceOperationPost {
    String name() default "post";
}
