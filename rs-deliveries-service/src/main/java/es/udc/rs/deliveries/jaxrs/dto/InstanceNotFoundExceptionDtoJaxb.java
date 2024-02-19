package es.udc.rs.deliveries.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "instanceNotFoundException")
@XmlType(name="instanceNotFoundExceptionType")
public class InstanceNotFoundExceptionDtoJaxb {

    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private String message;

    public InstanceNotFoundExceptionDtoJaxb() {
    }

    public InstanceNotFoundExceptionDtoJaxb(String message) {
        this.errorType = "InstanceNotFound";
        this.message = message;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "InstanceNotFoundExceptionDtoJaxb [" +
                "errorType='" + errorType + '\'' +
                ", message='" + message + '\'' +
                ']';
    }
}
