package es.udc.rs.deliveries.client.service.exceptions;

public class ClientInvalidUpdateStatusException extends Exception{
    private Long shipmentId;

    public ClientInvalidUpdateStatusException(Long shipmentId){
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
