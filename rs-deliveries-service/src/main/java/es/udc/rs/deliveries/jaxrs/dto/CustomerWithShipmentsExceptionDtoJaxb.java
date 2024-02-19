package es.udc.rs.deliveries.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="customerWithShipmentsException")
@XmlType(name="customerWithShipmentsExceptionType")
public class CustomerWithShipmentsExceptionDtoJaxb {

    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private Long customerId;

    public CustomerWithShipmentsExceptionDtoJaxb() {
    }

    public CustomerWithShipmentsExceptionDtoJaxb(Long customerId) {
        this.errorType = "CustomerWithShipments";
        this.customerId = customerId;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerWithShipmentsExceptionDtoJaxb [" +
                "errorType='" + errorType + '\'' +
                ", customerId='" + customerId +
                ']';
    }
}
