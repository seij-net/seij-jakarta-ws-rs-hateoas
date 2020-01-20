package jakarta.ws.rs.ext.hateoas.impl;

import javax.ws.rs.core.GenericEntity;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericEntitySupport {
    public static Type getEnclosedType(GenericEntity e) {
        Type type = e.getType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            return pt.getActualTypeArguments()[0];
        } else {
            return type;
        }
    }

    public static boolean isEntityInstanceOf(GenericEntity e, Class superClass) {
        Type type = e.getRawType();
        boolean result;

        Class c = (Class) type;
        result = superClass.isAssignableFrom(c);

        return result;
    }
}
