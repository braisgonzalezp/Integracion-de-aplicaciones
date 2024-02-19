package es.udc.rs.deliveries.jaxrs.exceptions;

import es.udc.rs.deliveries.jaxrs.dto.CustomerWithShipmentsExceptionDtoJaxb;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.CustomerWithShipmentsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerWithShipmentsExceptionMapper implements
        ExceptionMapper<CustomerWithShipmentsException> {

    @Override
    public Response toResponse(CustomerWithShipmentsException ex) {
        return Response.status(Response.Status.GONE)
                .entity(new CustomerWithShipmentsExceptionDtoJaxb(ex.getCustomerId()))
                .build();
    }
}
