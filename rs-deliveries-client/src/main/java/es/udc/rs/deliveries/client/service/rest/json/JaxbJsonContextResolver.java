package es.udc.rs.deliveries.client.service.rest.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationModule;

import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.JAXBElement;

@Provider
public class JaxbJsonContextResolver<T> implements ContextResolver<ObjectMapper> {
    private final ObjectMapper mapper;

    public JaxbJsonContextResolver() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JakartaXmlBindAnnotationModule());
        this.mapper.addMixIn(JAXBElement.class, JAXBElementMixin.class);
    }

    @Override
    public ObjectMapper getContext(Class<?> objectType) {
        return mapper;
    }

}
