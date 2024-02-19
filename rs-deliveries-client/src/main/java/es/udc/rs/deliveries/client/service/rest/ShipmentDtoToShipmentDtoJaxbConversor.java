package es.udc.rs.deliveries.client.service.rest;

import es.udc.rs.deliveries.client.service.dto.ClientShipmentDto;
import es.udc.rs.deliveries.client.service.dto.ClientShipmentStatusDto;
import es.udc.rs.deliveries.client.service.rest.dto.ObjectFactory;
import es.udc.rs.deliveries.client.service.rest.dto.ShipmentType;
import jakarta.xml.bind.JAXBElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDtoToShipmentDtoJaxbConversor {

    public static JAXBElement<ShipmentType> toJaxbShipment(ClientShipmentDto shipmentDto) {
        ShipmentType shipment = new ShipmentType();
        shipment.setShipmentId(shipmentDto.getShipmentId() != null ? shipmentDto.getShipmentId() : -1);
        shipment.setStatus(shipmentDto.getStatus().toString());
        shipment.setAddress(shipmentDto.getAddress());
        shipment.setCreationDate(shipmentDto.getCreationDate().toString());
        shipment.setCustomerId(shipmentDto.getCustomerId());
        shipment.setDeliveryDate(shipmentDto.getDeliveryDate().toString());
        shipment.setEstimatedHours(shipmentDto.getEstimatedHours());
        shipment.setPackageReference(shipmentDto.getPackageReference());
        shipment.setCustomerId(shipmentDto.getCustomerId());
        //shipment.setLinks(shipmentDto.getLinks());
        JAXBElement<ShipmentType> jaxbElement = new ObjectFactory().createShipment(shipment);
        return jaxbElement;
    }

    public static ClientShipmentDto toClientShipmentDto(ShipmentType shipment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime deliveryDate, creationDate;

        creationDate = LocalDateTime.parse(shipment.getCreationDate(), formatter);

        if (shipment.getDeliveryDate().isEmpty()) {
            deliveryDate = null;
        } else {
            deliveryDate = LocalDateTime.parse(shipment.getDeliveryDate(), formatter);
        }

        return new ClientShipmentDto(shipment.getShipmentId(), shipment.getCustomerId(), shipment.getPackageReference(),
                shipment.getAddress(), ClientShipmentStatusDto.valueOf(shipment.getStatus()), creationDate,
                  shipment.getEstimatedHours(), deliveryDate/*,shipment.getLinks()*/);
    }

    public static List<ClientShipmentDto> toClientShipmentDtos(List<ShipmentType> shipmentListDto) {
        List<ClientShipmentDto> shipmentDtos = new ArrayList<>(shipmentListDto.size());
        for (ShipmentType shipmentDtoType : shipmentListDto) {
            shipmentDtos.add(toClientShipmentDto(shipmentDtoType));
        }
        return shipmentDtos;
    }
}
