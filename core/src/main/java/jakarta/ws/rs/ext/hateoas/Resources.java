package jakarta.ws.rs.ext.hateoas;

import java.util.stream.Stream;

public interface Resources<T extends Resource<I>, I> {
    Class<T> getResourceClass();



    Stream<T> stream();
}
