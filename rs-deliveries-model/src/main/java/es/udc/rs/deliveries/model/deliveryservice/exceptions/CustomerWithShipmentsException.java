package es.udc.rs.deliveries.model.deliveryservice.exceptions;

public class CustomerWithShipmentsException extends Exception{
    private Long customerId;

    public CustomerWithShipmentsException(Long customerId){
        super("Customer with id=\"" + customerId + "\" has one or more shipments!");
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
