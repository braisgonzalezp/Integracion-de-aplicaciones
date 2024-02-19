package es.udc.rs.deliveries.jaxrs.exceptions;

import es.udc.rs.deliveries.jaxrs.dto.InvalidUpdateStatusExceptionDtoJaxb;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.InvalidUpdateStatusException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidUpdateStatusExceptionMapper implements
        ExceptionMapper<InvalidUpdateStatusException> {

    @Override
    public Response toResponse(InvalidUpdateStatusException ex) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(new InvalidUpdateStatusExceptionDtoJaxb(ex.getShipmentId()))
                .build();
    }
}
