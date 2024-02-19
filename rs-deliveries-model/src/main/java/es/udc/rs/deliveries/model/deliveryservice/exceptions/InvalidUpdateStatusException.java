package es.udc.rs.deliveries.model.deliveryservice.exceptions;

public class InvalidUpdateStatusException extends Exception{
    private Long shipmentId;

    public InvalidUpdateStatusException(Long shipmentId){
        super("Shimpent with id=\"" + shipmentId + "\" cannot change to that status");
        this.shipmentId = shipmentId;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }
}
