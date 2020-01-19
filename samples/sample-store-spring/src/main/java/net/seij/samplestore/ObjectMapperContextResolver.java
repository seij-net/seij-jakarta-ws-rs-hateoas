package net.seij.samplestore;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper;

    public ObjectMapperContextResolver(ObjectMapper objectMapper) {
        mapper = objectMapper;
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
