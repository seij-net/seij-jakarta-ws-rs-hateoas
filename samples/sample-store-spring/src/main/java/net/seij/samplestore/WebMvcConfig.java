package net.seij.samplestore;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.ext.hateoas.jackson.JakartaWsRsHateoasModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper webObjectMapper = objectMapper.copy();
        webObjectMapper.registerModule(JakartaWsRsHateoasModule.Instance);
        converters.add(new MappingJackson2HttpMessageConverter(webObjectMapper));
    }
}
