package es.udc.rs.deliveries.jaxrs.resources;

import es.udc.rs.deliveries.jaxrs.dto.CustomerDtoJaxb;
import es.udc.rs.deliveries.jaxrs.util.CustomerToCustomerDtoJaxbConversor;
import es.udc.rs.deliveries.jaxrs.util.ServiceUtil;
import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryServiceFactory;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.CustomerWithShipmentsException;
import es.udc.ws.util.exceptions.InputValidationException;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import javax.management.InstanceNotFoundException;
import java.net.URI;
import java.util.List;

@Path("customers")
@OpenAPIDefinition(
        info = @Info(
                title = "rs-deliveries",
                version = "3.7.0"),
        servers = {
                @Server(
                        url = "http://localhost:8080/rs-deliveries-service")
        })
public class CustomerResource {

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Operation(summary = "add a customer" , responses = {
            @ApiResponse(responseCode = "200" , description = "customer added"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public Response addCustomer(final CustomerDtoJaxb customerDto, @Context final UriInfo ui, @Context HttpHeaders headers) throws InputValidationException, InstanceNotFoundException {

        Customer customer = CustomerToCustomerDtoJaxbConversor.toCustomer(customerDto);
        customer = DeliveryServiceFactory.getService().addCustomer(customer);
        final CustomerDtoJaxb resultCustomerDto = CustomerToCustomerDtoJaxbConversor.toCustomerDtoJaxb(customer,
                ui, ServiceUtil.getTypeAsStringFromHeaders(headers));

        final String requestUri = ui.getRequestUri().toString();
        return Response.created(URI.create(requestUri + (requestUri.endsWith("/") ? "" : "/") + customer.getCustomerId()))
                .entity(resultCustomerDto).build();
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    @Operation(summary = "delete a customer" , responses = {
            @ApiResponse(responseCode = "200" , description = "Customer deleted"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "410", description = "Gone")
    })
    public void deleteCustomer(@PathParam("id") final String id)
            throws InputValidationException, CustomerWithShipmentsException, InstanceNotFoundException {

        Long customerId;
        try {
            customerId = Long.valueOf(id);
        }catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: unable to parse customer id '" + id + "'");
        }

        DeliveryServiceFactory.getService().deleteCustomer(customerId);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    @Operation(summary = "update a customer" , responses = {
            @ApiResponse(responseCode = "200" , description = "customer updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public void updateCustomer(final CustomerDtoJaxb customerDto, @PathParam("id") final String id)
            throws InputValidationException, InstanceNotFoundException {

        Long customerId;
        try {
            customerId = Long.valueOf(id);
        }catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: unable to parse customer id '" + id + "'");
        }

        if (!customerId.equals(customerDto.getId())){
            throw new InputValidationException(
                    "Invalid Request: invalid customer Id '" + customerDto.getId() + "' for customer '" + customerId + "'"
            );
        }

        final Customer customer = CustomerToCustomerDtoJaxbConversor.toCustomer(customerDto);
        DeliveryServiceFactory.getService().updateCustomer(customerId, customer.getName(), customer.getCif(), customer.getAddress());
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Operation(summary = "find a customer" , responses = {
            @ApiResponse(responseCode = "200" , description = "customer found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public CustomerDtoJaxb findById(@PathParam("id") final String id,
                                    @Context UriInfo ui, @Context HttpHeaders headers)
            throws InputValidationException, InstanceNotFoundException {

        Long customerId;
        try {
            customerId = Long.valueOf(id);
        }catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse customer id '" + id + "'");
        }

        return CustomerToCustomerDtoJaxbConversor.toCustomerDtoJaxb(DeliveryServiceFactory.getService().findCustomer(customerId),
                ui, ServiceUtil.getTypeAsStringFromHeaders(headers));
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Operation(summary = "find customers" , responses = {
            @ApiResponse(responseCode = "200" , description = "customers found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public List<CustomerDtoJaxb> findCustomers(@QueryParam("keywords") final String keywords,
                                               @Context UriInfo ui, @Context HttpHeaders headers)
            throws InputValidationException, InstanceNotFoundException {

        final List<Customer> customers = DeliveryServiceFactory.getService().findCustomers(keywords);
        return CustomerToCustomerDtoJaxbConversor.toCustomerDtoJaxb(customers,
                ui, ServiceUtil.getTypeAsStringFromHeaders(headers));
    }
}
