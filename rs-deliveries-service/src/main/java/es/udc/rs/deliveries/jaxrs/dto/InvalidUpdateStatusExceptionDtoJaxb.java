package es.udc.rs.deliveries.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="invalidUpdateStatusException")
@XmlType(name="invalidUpdateStatusExceptionType")
public class InvalidUpdateStatusExceptionDtoJaxb {

    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private Long shipmentId;

    public InvalidUpdateStatusExceptionDtoJaxb() {
    }

    public InvalidUpdateStatusExceptionDtoJaxb(Long shipmentId) {
        this.errorType = "InvalidUpdateStatus";
        this.shipmentId = shipmentId;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public String toString() {
        return "InvalidUpdateStatusExceptionDtoJaxb [" +
                "errorType='" + errorType + '\'' +
                ", shipmentId='" + shipmentId +
                ']';
    }
}
