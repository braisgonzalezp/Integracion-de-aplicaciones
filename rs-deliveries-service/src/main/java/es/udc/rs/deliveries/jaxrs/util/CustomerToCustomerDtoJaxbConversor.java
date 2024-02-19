package es.udc.rs.deliveries.jaxrs.util;

import es.udc.rs.deliveries.jaxrs.dto.AtomLinkDtoJaxb;
import es.udc.rs.deliveries.jaxrs.dto.CustomerDtoJaxb;
import es.udc.rs.deliveries.jaxrs.resources.CustomerResource;
import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryServiceFactory;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.ws.util.exceptions.InputValidationException;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriInfo;
import org.glassfish.jersey.server.Uri;

import javax.management.InstanceNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static es.udc.rs.deliveries.model.shipment.ShipmentStatus.*;

public class CustomerToCustomerDtoJaxbConversor {

    public static List<CustomerDtoJaxb> toCustomerDtoJaxb(List<Customer> customers, UriInfo uriInfo, String type) throws InstanceNotFoundException, InputValidationException {
        List<CustomerDtoJaxb> customerDtos = new ArrayList<>(customers.size());
        for (Customer customer : customers) {
            customerDtos.add(toCustomerDtoJaxb(customer, uriInfo, type));
        }
        return customerDtos;
    }

    public static CustomerDtoJaxb toCustomerDtoJaxb(Customer customer, UriInfo uriInfo, String type)
            throws InputValidationException, InstanceNotFoundException {
        List<AtomLinkDtoJaxb> links = createLinks(customer, uriInfo, type);

        return new CustomerDtoJaxb(
                customer.getCustomerId(),
                customer.getName(),
                customer.getCif(),
                customer.getAddress(),
                links
        );
    }

    public static Customer toCustomer(CustomerDtoJaxb customer) {
        return new Customer(
                customer.getId(),
                customer.getName(),
                customer.getCif(),
                customer.getAddress()
        );
    }

    private static List<AtomLinkDtoJaxb> createLinks(Customer customer, UriInfo uriInfo, String type) throws InputValidationException, InstanceNotFoundException {
        List<AtomLinkDtoJaxb> links = new ArrayList<AtomLinkDtoJaxb>();
        AtomLinkDtoJaxb selfLink = getSelfLink(uriInfo, customer, type);

        Link shipmentsLink = ServiceUtil.getShipmentsIntervalLink(uriInfo.getBaseUri(), customer.getCustomerId(), null,
                1, 10, "shipments", "Shipments link", type);

        AtomLinkDtoJaxb shipmentsAtomLink = new AtomLinkDtoJaxb(
                shipmentsLink.getUri(), shipmentsLink.getRel(), shipmentsLink.getType(), shipmentsLink.getTitle());

        links.add(selfLink);
        links.add(shipmentsAtomLink);

        if (DeliveryServiceFactory.getService().findShipments(customer.getCustomerId(), null, 0, 0) == null) {
            AtomLinkDtoJaxb removeLink = getRemoveLink(uriInfo, customer, type);
            links.add(removeLink);
        }

        return links;
    }
    
    private static AtomLinkDtoJaxb getSelfLink(UriInfo uriInfo, Customer customer, String type) {
        return ServiceUtil.getLinkFromUri(uriInfo.getBaseUri(), CustomerResource.class,
                customer.getCustomerId(), "self", "Self link", type);
    }

    private static AtomLinkDtoJaxb getRemoveLink(UriInfo uriInfo, Customer customer, String type) {
        return ServiceUtil.getLinkFromUri(uriInfo.getBaseUri(), CustomerResource.class,
                customer.getCustomerId(), "remove", "Remove link", type);
    }

}
