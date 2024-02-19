package es.udc.rs.deliveries.jaxrs.resources;

import es.udc.rs.deliveries.jaxrs.dto.AtomLinkDtoJaxb;
import es.udc.rs.deliveries.jaxrs.dto.ShipmentDtoJaxb;
import es.udc.rs.deliveries.jaxrs.dto.ShipmentDtoJaxbList;
import es.udc.rs.deliveries.jaxrs.util.ServiceUtil;
import es.udc.rs.deliveries.jaxrs.util.ShipmentToShipmentDtoConversor;
import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryServiceFactory;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.InvalidUpdateStatusException;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;
import es.udc.ws.util.exceptions.InputValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

import javax.management.InstanceNotFoundException;
import java.net.URI;
import java.util.List;

@Path("shipments")
public class ShipmentResource {

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Operation(summary = "add a shipment" , responses = {
            @ApiResponse(responseCode = "200" , description = "Shipment added"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public Response addShipment(final ShipmentDtoJaxb shipmentDto, @Context final UriInfo ui, @Context HttpHeaders headers) throws InstanceNotFoundException, InputValidationException {

        Shipment shipment = ShipmentToShipmentDtoConversor.toShipment(shipmentDto);
        shipment = DeliveryServiceFactory.getService().addShipment(shipment);
        final ShipmentDtoJaxb resultShipmentDto = ShipmentToShipmentDtoConversor.toShipmentDtoJaxb(shipment, ui,
                ServiceUtil.getTypeAsStringFromHeaders(headers));

        final String requestUri = ui.getRequestUri().toString();
        return Response.created(URI.create(requestUri + (requestUri.endsWith("/") ? "" : "/") + shipment.getShipmentId()))
                .entity(resultShipmentDto).build();
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/updateShipment")
    @Operation(summary = "update a shipment" , responses = {
            @ApiResponse(responseCode = "200" , description = "Shipment updated"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public void updateShipment(@PathParam("id") final String id, @FormParam("status") final String status)
            throws InputValidationException, InstanceNotFoundException, InvalidUpdateStatusException {

        if (status == null){
            throw new InputValidationException(
                    "Invalid Request: parameter 'status' is mandatory"
            );
        }

        Long shipmentId;
        try {
            shipmentId = Long.valueOf(id);
        }catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: unable to parse shipment id '" + id + "'");
        }

        DeliveryServiceFactory.getService().updateShipment(shipmentId, toShipmentStatus(status));
    }

    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/cancelShipment")
    @Operation(summary = "cancel a shipment" , responses = {
            @ApiResponse(responseCode = "200" , description = "Shipment cancelled"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public void cancelShipment(@PathParam("id") final String id)
            throws InputValidationException, InstanceNotFoundException, InvalidUpdateStatusException {

        Long shipmentId;
        try {
            shipmentId = Long.valueOf(id);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: unable to parse shipment id '" + id + "'");
        }

        DeliveryServiceFactory.getService().cancelShipment(shipmentId);
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Operation(summary = "find a shipment" , responses = {
            @ApiResponse(responseCode = "200" , description = "Shipment found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ShipmentDtoJaxb findById(@PathParam("id") String id,
                                    @Context UriInfo ui, @Context HttpHeaders headers) throws InputValidationException, InstanceNotFoundException {

        Long shipmentId;
        try {
            shipmentId = Long.valueOf(id);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: unable to parse shipment id '" + id + "'");
        }

        Shipment shipment = DeliveryServiceFactory.getService().findShipment(shipmentId);
        return ShipmentToShipmentDtoConversor.toShipmentDtoJaxb(shipment, ui,
                ServiceUtil.getTypeAsStringFromHeaders(headers));
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Operation(summary = "find shipments" , responses = {
            @ApiResponse(responseCode = "200" , description = "Shipments founded"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public Response findShipments (
            @QueryParam("customerId") final String customerId,
            @QueryParam("status") final String status,
            @DefaultValue("0") @QueryParam("index") final String index,
            @DefaultValue("3") @QueryParam("elements") final String elements,@Context UriInfo ui, @Context HttpHeaders headers
    ) throws InputValidationException, InstanceNotFoundException {

        Long id;
        try {
            id = Long.valueOf(customerId);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: unable to parse customer id '" + customerId + "'");
        }

        final List<Shipment> shipments = DeliveryServiceFactory.getService().findShipments(id, toShipmentStatus(status),
                Integer.parseInt(index), Integer.parseInt(elements));

        String type = ServiceUtil.getTypeAsStringFromHeaders(headers);

        List<ShipmentDtoJaxb> shipmentDtos = ShipmentToShipmentDtoConversor
                .toShipmentDtoJaxb(shipments, ui, type);

        Link selfLink = getSelfLink(ui, Long.valueOf(customerId), type);
        Link nextLink = getNextLink(ui, Long.valueOf(customerId), status, Integer.parseInt(index),
                shipmentDtos.size(), type);
        Link previousLink = getPreviousLink(ui, Long.valueOf(customerId), status, Integer.parseInt(index),
                Integer.parseInt(elements), type);

        ResponseBuilder response = Response.ok(
                new ShipmentDtoJaxbList(shipmentDtos)).links(selfLink);
        if (nextLink != null) {
            response.links(nextLink);
        }
        if (previousLink != null) {
            response.links(previousLink);
        }
        return response.build();

       // return ShipmentToShipmentDtoConversor.toShipmentDtoJaxb(shipments,ui.getBaseUri(),ServiceUtil.getTypeAsStringFromHeaders(headers));
    }

    private ShipmentStatus toShipmentStatus(String status) throws InputValidationException {
        try {
            return ShipmentStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new InputValidationException("Invalid Request: unable to parse status '" + status + "'");
        }
    }

    private static Link getSelfLink(UriInfo uriInfo, Long customerId, String type) {
        return ServiceUtil.getShipmentsIntervalLink(uriInfo.getBaseUri(), customerId,
                null, 1, 10, "shipments", "Shipments link", type);
    }

    private static Link getNextLink(UriInfo uriInfo, Long customerId, String status,
                                    int startIndex, int count, String type) {

        if (10 < count) {
            return null;
        }

        return ServiceUtil.getShipmentsIntervalLink(
                uriInfo.getBaseUri(),
                customerId,
                status,
                startIndex + count,
                count,
                "next",
                "Next interval of products",
                type
        );
    }

    private Link getPreviousLink(UriInfo uriInfo, Long customerId, String status,
                                 int startIndex, int count, String type) {
        if (startIndex <= 0) {
            return null;
        }

        startIndex = startIndex - count;

        if (startIndex < 0) {
            startIndex = 0;
        }

        return ServiceUtil.getShipmentsIntervalLink(
                uriInfo.getBaseUri(),
                customerId,
                status,
                startIndex,
                count,
                "previous",
                "Previous interval of products",
                type
        );
    }
}
