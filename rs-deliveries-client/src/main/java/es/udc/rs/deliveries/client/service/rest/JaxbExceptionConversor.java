package es.udc.rs.deliveries.client.service.rest;

import es.udc.rs.deliveries.client.service.exceptions.ClientCustomerWithShipmentsException;
import es.udc.rs.deliveries.client.service.exceptions.ClientInvalidUpdateStatusException;
import es.udc.rs.deliveries.client.service.rest.dto.CustomerWithShipmentsExceptionType;
import es.udc.rs.deliveries.client.service.rest.dto.InputValidationExceptionType;
import es.udc.rs.deliveries.client.service.rest.dto.InstanceNotFoundExceptionType;
import es.udc.rs.deliveries.client.service.rest.dto.InvalidUpdateStatusExceptionType;
import es.udc.ws.util.exceptions.InputValidationException;

import javax.management.InstanceNotFoundException;


public class JaxbExceptionConversor {
    public static ClientInvalidUpdateStatusException toInvalidUpdateStatusException(
            InvalidUpdateStatusExceptionType exDto) {
        return new ClientInvalidUpdateStatusException(exDto.getShipmentId());
    }

    public static ClientCustomerWithShipmentsException toCustomerWithShipmentsExceptionType(
            CustomerWithShipmentsExceptionType exDto) {
        return new ClientCustomerWithShipmentsException(exDto.getCustomerId());
    }

    public static InputValidationException toInputValidationException(
            InputValidationExceptionType exDto) {
        return  new InputValidationException(exDto.getMessage());
    }

    public static InstanceNotFoundException toInstanceNotFoundException(
            InstanceNotFoundExceptionType exDto) {
        return new InstanceNotFoundException(exDto.getMessage());
    }
}
