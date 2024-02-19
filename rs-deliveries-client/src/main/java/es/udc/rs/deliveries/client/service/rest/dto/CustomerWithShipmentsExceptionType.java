//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.3 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package es.udc.rs.deliveries.client.service.rest.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para customerWithShipmentsExceptionType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="customerWithShipmentsExceptionType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="customerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       </sequence>
 *       <attribute name="errorType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerWithShipmentsExceptionType", propOrder = {
    "customerId"
})
public class CustomerWithShipmentsExceptionType {

    protected long customerId;
    @XmlAttribute(name = "errorType", required = true)
    protected String errorType;

    /**
     * Obtiene el valor de la propiedad customerId.
     * 
     */
    public long getCustomerId() {
        return customerId;
    }

    /**
     * Define el valor de la propiedad customerId.
     * 
     */
    public void setCustomerId(long value) {
        this.customerId = value;
    }

    /**
     * Obtiene el valor de la propiedad errorType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorType() {
        return errorType;
    }

    /**
     * Define el valor de la propiedad errorType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorType(String value) {
        this.errorType = value;
    }

}
