package es.udc.rs.deliveries.jaxrs.util;

import es.udc.rs.deliveries.jaxrs.dto.AtomLinkDtoJaxb;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import es.udc.rs.deliveries.jaxrs.resources.ShipmentResource;
import es.udc.rs.deliveries.model.shipment.Shipment;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

public class ServiceUtil {

    private static List<MediaType> responseMediaTypes = Arrays
            .asList(new MediaType[] { MediaType.APPLICATION_XML_TYPE,
                    MediaType.APPLICATION_JSON_TYPE });

    public static String getTypeAsStringFromHeaders(HttpHeaders headers) {
        List<MediaType> mediaTypes = headers.getAcceptableMediaTypes();
        for (MediaType m : mediaTypes) {
            MediaType compatibleType = getCompatibleAcceptableMediaType(m);
            if (compatibleType != null) {
                return compatibleType.toString();
            }
        }
        return null;
    }

    private static MediaType getCompatibleAcceptableMediaType(MediaType type) {
        for (MediaType m : responseMediaTypes) {
            if (m.isCompatible(type)) {
                return m;
            }
        }
        return null;
    }

    public static AtomLinkDtoJaxb getLinkFromUri(URI baseUri, Class<?> resourceClass,
                                                 Object instanceId, String rel, String title, String type) {
        Link.Builder linkBuilder = Link
                .fromPath(
                        baseUri.toString()
                                + UriBuilder.fromResource(resourceClass)
                                .build().toString() + "/"
                                + instanceId).rel(rel).title(title);
        if (type != null) {
            linkBuilder.type(type);
        }
        Link link = linkBuilder.build();
        return new AtomLinkDtoJaxb(link.getUri(), link.getRel(), link.getType(), link.getTitle());
    }


    public static Link getShipmentsIntervalLink(URI baseUri, Long customerId,
                                               String status, int index, int elements, String rel, String title, String type) {

        UriBuilder uriBuilder = UriBuilder.fromUri(baseUri)
                .path(ShipmentResource.class)
                .queryParam("customerId", customerId)
                .queryParam("index", index)
                .queryParam("elements", elements);

        if (status != null) {
            uriBuilder.queryParam("status", status);
        }

        Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder)
                .rel(rel)
                .title(title);
        if (type!=null) {
            linkBuilder.type(type);
        }

        return linkBuilder.build();
    }
}
