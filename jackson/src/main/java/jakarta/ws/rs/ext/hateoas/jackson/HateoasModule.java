package jakarta.ws.rs.ext.hateoas.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.lang.NotNull;
import jakarta.ws.rs.ext.hateoas.ResourceListOfList;
import jakarta.ws.rs.ext.hateoas.ResourceRegistry;
import jakarta.ws.rs.ext.hateoas.impl.GenericEntityWithLinksImpl;
import jakarta.ws.rs.ext.hateoas.impl.ResourceEntityImpl;
import jakarta.ws.rs.ext.hateoas.impl.ResourceReferenceImpl;
import jakarta.ws.rs.ext.hateoas.jackson.serializers.*;

public class HateoasModule extends SimpleModule {

    public static final Version VERSION = new Version(1, 0, 0, "", "jakarta-ws-rs", "hateoas");

    public HateoasModule(@NotNull ResourceRegistry resourceRegistry) {
        super(VERSION);

        registerSubtypes(GenericEntityWithLinksImpl.class);
        registerSubtypes(ResourceReferenceImpl.class);
        registerSubtypes(ResourceListOfList.class);
        registerSubtypes(ResourceEntityImpl.class);

        addSerializer(GenericEntityWithLinksSerializer.Instance);
        addSerializer(LinkSerializer.Instance);
        addSerializer(HateoasLinkEmbeddedSerializer.Instance);
        addSerializer(new ResourceEntitySerializer(resourceRegistry));
        addSerializer(new ResourcesSerializer(resourceRegistry));
        addSerializer(new ResourceReferenceSerializer(resourceRegistry));
    }
}
