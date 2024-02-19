package es.udc.rs.deliveries.jaxrs.util;

import es.udc.rs.deliveries.jaxrs.dto.AtomLinkDtoJaxb;
import es.udc.rs.deliveries.jaxrs.dto.ShipmentDtoJaxb;
import es.udc.rs.deliveries.jaxrs.resources.CustomerResource;
import es.udc.rs.deliveries.jaxrs.resources.ShipmentResource;
import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;
import jakarta.ws.rs.core.UriInfo;
import org.glassfish.jersey.server.Uri;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static es.udc.rs.deliveries.model.shipment.ShipmentStatus.PENDING;

public class ShipmentToShipmentDtoConversor {

    public static List<ShipmentDtoJaxb> toShipmentDtoJaxb(List<Shipment> shipments, UriInfo uriInfo, String type) {
        List<ShipmentDtoJaxb> shipmentDtos = new ArrayList<>(shipments.size());

        for (Shipment shipment : shipments) {
            shipmentDtos.add(toShipmentDtoJaxb(shipment, uriInfo, type));
        }
        return shipmentDtos;
    }

    public static ShipmentDtoJaxb toShipmentDtoJaxb(Shipment shipment, UriInfo uriInfo, String type) {
        long estimatedHours = ChronoUnit.HOURS.between(LocalDateTime.now(), shipment.getMaxDeliveryDate());
        String deliveryDate;

        if (shipment.getDeliveryDate() == null) {
            deliveryDate = "";
        } else {
            deliveryDate = shipment.getDeliveryDate().toString();
        }

        if (shipment.getStatus() == ShipmentStatus.DELIVERED || shipment.getStatus() == ShipmentStatus.REJECTED) {
            estimatedHours = 0;
        }

        List<AtomLinkDtoJaxb> links = createLinks(shipment, uriInfo, type);

        return new ShipmentDtoJaxb(
                shipment.getShipmentId(),
                shipment.getCustomerId(),
                shipment.getPackageReference(),
                shipment.getAddress(),
                shipment.getStatus().toString(),
                shipment.getCreationDate().toString(),
                Math.toIntExact(estimatedHours),
                deliveryDate,
                links
        );
    }

    public static Shipment toShipment(ShipmentDtoJaxb shipment) {
        return new Shipment(
                shipment.getCustomerId(),
                shipment.getPackageReference(),
                shipment.getAddress()
        );
    }

    private  static List<AtomLinkDtoJaxb> createLinks(Shipment shipment, UriInfo uriInfo, String type) {
        List<AtomLinkDtoJaxb> links = new ArrayList<AtomLinkDtoJaxb>();
        AtomLinkDtoJaxb selfLink = getSelfLink(uriInfo, shipment, type);
        AtomLinkDtoJaxb customerLink = getCustomerLink(uriInfo, shipment, type);

        links.add(selfLink);
        links.add(customerLink);

        if(shipment.getStatus() == PENDING){
            AtomLinkDtoJaxb cancelLink = getCancelLink(uriInfo, shipment, type);
            links.add(cancelLink);
        }
        return links;
    }

    private static AtomLinkDtoJaxb getSelfLink(UriInfo uriInfo, Shipment shipment, String type) {
        return ServiceUtil.getLinkFromUri(uriInfo.getBaseUri(), ShipmentResource.class,
                shipment.getShipmentId(), "self", "Self link", type);
    }

    private static AtomLinkDtoJaxb getCustomerLink(UriInfo uriInfo, Shipment shipment, String type) {
        return ServiceUtil.getLinkFromUri(uriInfo.getBaseUri(), CustomerResource.class,
                shipment.getCustomerId(), "customer", "Customer link", type);
    }

    private static AtomLinkDtoJaxb getCancelLink(UriInfo uriInfo, Shipment shipment, String type) {
        return  ServiceUtil.getLinkFromUri(uriInfo.getBaseUri(), ShipmentResource.class,
                shipment.getShipmentId(), "cancel", "Cancel link", type);
    }
}
