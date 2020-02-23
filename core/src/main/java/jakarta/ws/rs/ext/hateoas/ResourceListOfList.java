package jakarta.ws.rs.ext.hateoas;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ResourceListOfList<T extends Resource<I>, I> implements Resources<T, I> {

    private final Stream<T> iterable;
    private final Class<T> resourceClass;


    private ResourceListOfList(Class<T> resourceClass, Stream<T> iterable) {
        this.iterable = iterable;
        this.resourceClass = resourceClass;

    }
    public static <T extends Resource<I>, I> ResourceListOfList<T, I> of(Class<T> resourceClass, Stream<T> iterable) {
        return new ResourceListOfList<>(resourceClass, iterable);
    }

    public Class<T> getResourceClass() {
        return resourceClass;
    }

    @Override
    public Stream<T> stream() {
        return StreamSupport.stream(iterable.spliterator(),false);
    }

}
