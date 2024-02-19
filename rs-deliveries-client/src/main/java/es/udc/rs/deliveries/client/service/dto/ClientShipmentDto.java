package es.udc.rs.deliveries.client.service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ClientShipmentDto {


    private Long shipmentId;
    private Long customerId;
    private Long packageReference;
    private String address;
    private ClientShipmentStatusDto status;
    private LocalDateTime creationDate;
    private int estimatedHours;
    private LocalDateTime deliveryDate;

    //private List<AtomLinkDtoJaxb> links;

    public ClientShipmentDto() {
    }

    public ClientShipmentDto(Long shipmentId, ClientShipmentStatusDto status) {
        this.shipmentId = shipmentId;
        this.status = status;
    }
    public ClientShipmentDto(Long shipmentId, Long customerId, Long packageReference, String address,
                                  ClientShipmentStatusDto status, LocalDateTime creationDate, int estimatedHours,
                                  LocalDateTime deliveryDate/*,List<AtomLinkDtoJaxb> links*/) {
        this(shipmentId, status);
        this.customerId = customerId;
        this.packageReference = packageReference;
        this.address = address;
        this.creationDate = creationDate;
        this.estimatedHours = estimatedHours;
        this.deliveryDate = deliveryDate;
        //this.links = links;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
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

    public ClientShipmentStatusDto getStatus() {
        return status;
    }

    public void setStatus(ClientShipmentStatusDto status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /*public List<AtomLinkDtoJaxb> getLinks() {
        return links;
    }

    public void setLinks(List<AtomLinkDtoJaxb> links) {
        this.links = links;
    }*/

    @Override
    public String toString() {
        return "ClientShipmentDto [" +
                "shipmentId=" + shipmentId +
                ", customerId=" + customerId +
                ", packageReference=" + packageReference +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", estimatedHours=" + estimatedHours +
                ", deliveryDate=" + deliveryDate +
                ']';
    }
}
