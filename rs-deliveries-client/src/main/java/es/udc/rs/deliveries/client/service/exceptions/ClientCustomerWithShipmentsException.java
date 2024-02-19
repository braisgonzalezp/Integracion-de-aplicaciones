package es.udc.rs.deliveries.client.service.exceptions;

public class ClientCustomerWithShipmentsException extends Exception{
    private Long customerId;

    public ClientCustomerWithShipmentsException(Long customerId){
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
