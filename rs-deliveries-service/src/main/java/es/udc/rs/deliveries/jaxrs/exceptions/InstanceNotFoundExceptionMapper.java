package es.udc.rs.deliveries.jaxrs.exceptions;

import es.udc.rs.deliveries.jaxrs.dto.InstanceNotFoundExceptionDtoJaxb;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import javax.management.InstanceNotFoundException;

@Provider
public class InstanceNotFoundExceptionMapper implements
        ExceptionMapper<InstanceNotFoundException> {

    @Override
    public Response toResponse(InstanceNotFoundException ex) {
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new InstanceNotFoundExceptionDtoJaxb(ex.getMessage()))
                .build();
    }
}