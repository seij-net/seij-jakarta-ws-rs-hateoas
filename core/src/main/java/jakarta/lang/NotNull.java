package jakarta.lang;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
public @interface NotNull {
    /**
     * @return Custom message
     */
    String value() default "";

    Class<? extends Exception> exception() default Exception.class;
}

