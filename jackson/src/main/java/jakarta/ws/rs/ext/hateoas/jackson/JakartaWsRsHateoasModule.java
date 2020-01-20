package jakarta.ws.rs.ext.hateoas.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.ws.rs.ext.hateoas.impl.GenericEntityWithLinksImpl;

public class JakartaWsRsHateoasModule extends SimpleModule {

    public static final Version VERSION = new Version(1, 0, 0, "", "jakarta-ws-rs", "hateoas");

    public static final JakartaWsRsHateoasModule Instance = new JakartaWsRsHateoasModule();

    JakartaWsRsHateoasModule() {
        super(VERSION);
        registerSubtypes(GenericEntityWithLinksImpl.class);
        addSerializer(JakartaWsRsHateoasGenericEntityWithLinksSerializer.Instance);
        addSerializer(JakartaWsRsHateoasHalLinkSerializer.Instance);
        addSerializer(JakartaWsRsHateoasLinkEmbeddedSerializer.Instance);
    }
}
