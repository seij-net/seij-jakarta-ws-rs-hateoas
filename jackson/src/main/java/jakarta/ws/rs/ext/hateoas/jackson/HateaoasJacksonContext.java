package jakarta.ws.rs.ext.hateoas.jackson;

import com.fasterxml.jackson.databind.SerializerProvider;
import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.jackson.exceptions.HateoasJacksonConfigurationException;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

public class HateaoasJacksonContext {
    @NotNull
    public static UriBuilder extractBaseUriBuilder(@NotNull SerializerProvider serializerProvider) {
        UriInfo uriInfo = (UriInfo) serializerProvider.getAttribute(UriInfo.class);
        if (uriInfo == null) throw new HateoasJacksonConfigurationException();
        return uriInfo.getRequestUriBuilder();
    }
}
