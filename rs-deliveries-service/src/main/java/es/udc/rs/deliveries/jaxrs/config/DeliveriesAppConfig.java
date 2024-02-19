package es.udc.rs.deliveries.jaxrs.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class DeliveriesAppConfig extends ResourceConfig {
    public DeliveriesAppConfig() {
        register(JacksonFeature.class);
    }
}
