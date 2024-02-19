package es.udc.rs.deliveries.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlRootElement(name="shipment")
@XmlType(name="shipmentType", propOrder =
        {"id", "customerId", "packageReference", "address", "status", "creationDate", "estimatedHours", "deliveryDate", "links" })
public class ShipmentDtoJaxb {

    @XmlAttribute(name = "shipmentId", required = true)
    private Long id;
    @XmlElement(required = true)
    private Long customerId;
    @XmlElement(required = true)
    private Long packageReference;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private String creationDate;
    @XmlElement(required = true)
    private int estimatedHours;
    @XmlElement(required = true)
    private String deliveryDate;
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private List<AtomLinkDtoJaxb> links;

    public ShipmentDtoJaxb() {
    }

    public ShipmentDtoJaxb(Long customerId, Long packageReference, String address, List<AtomLinkDtoJaxb> links) {
        this.customerId = customerId;
        this.packageReference = packageReference;
        this.address = address;
        this.links = links;
    }

    public ShipmentDtoJaxb(Long shipmentId, Long customerId, Long packageReference, String address,
                           String status, String creationDate, int estimatedHours, String deliveryDate, List<AtomLinkDtoJaxb> links) {
        this(customerId, packageReference, address, links);
        this.id = shipmentId;
        this.creationDate = creationDate;
        this.status = status;
        this.estimatedHours = estimatedHours;
        this.deliveryDate = deliveryDate;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPackageReference() {
        return packageReference;
    }

    public void setPackageReference(Long packageReference) {
        this.packageReference = packageReference;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<AtomLinkDtoJaxb> getLinks() {
        return links;
    }

    public void setLinks(List<AtomLinkDtoJaxb> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "ShipmentDtoJaxb [" +
                "shipmentId=" + id +
                ", customerId=" + customerId +
                ", packageReference=" + packageReference +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", estimatedHours=" + estimatedHours +
                ", deliveryDate=" + deliveryDate +
                ", self=" + links +
                ']';
    }
}
