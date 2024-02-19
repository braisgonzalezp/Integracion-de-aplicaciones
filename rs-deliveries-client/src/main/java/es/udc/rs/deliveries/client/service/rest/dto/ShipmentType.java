//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.3 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package es.udc.rs.deliveries.client.service.rest.dto;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para shipmentType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>{@code
 * <complexType name="shipmentType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="customerId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="packageReference" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="creationDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="estimatedHours" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="deliveryDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element ref="{http://www.w3.org/2005/Atom}link" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *       <attribute name="shipmentId" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shipmentType", propOrder = {
    "customerId",
    "packageReference",
    "address",
    "status",
    "creationDate",
    "estimatedHours",
    "deliveryDate",
    "link"
})
public class ShipmentType {

    protected long customerId;
    protected long packageReference;
    @XmlElement(required = true)
    protected String address;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected String creationDate;
    protected int estimatedHours;
    @XmlElement(required = true)
    protected String deliveryDate;
    @XmlElement(namespace = "http://www.w3.org/2005/Atom")
    protected List<AtomLinkType> link;
    @XmlAttribute(name = "shipmentId", required = true)
    protected long shipmentId;

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
     * Obtiene el valor de la propiedad packageReference.
     * 
     */
    public long getPackageReference() {
        return packageReference;
    }

    /**
     * Define el valor de la propiedad packageReference.
     * 
     */
    public void setPackageReference(long value) {
        this.packageReference = value;
    }

    /**
     * Obtiene el valor de la propiedad address.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Define el valor de la propiedad address.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Obtiene el valor de la propiedad status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define el valor de la propiedad status.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Obtiene el valor de la propiedad creationDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Define el valor de la propiedad creationDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreationDate(String value) {
        this.creationDate = value;
    }

    /**
     * Obtiene el valor de la propiedad estimatedHours.
     * 
     */
    public int getEstimatedHours() {
        return estimatedHours;
    }

    /**
     * Define el valor de la propiedad estimatedHours.
     * 
     */
    public void setEstimatedHours(int value) {
        this.estimatedHours = value;
    }

    /**
     * Obtiene el valor de la propiedad deliveryDate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Define el valor de la propiedad deliveryDate.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryDate(String value) {
        this.deliveryDate = value;
    }

    /**
     * Gets the value of the link property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a {@code set} method for the link property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AtomLinkType }
     * 
     * 
     * @return
     *     The value of the link property.
     */
    public List<AtomLinkType> getLink() {
        if (link == null) {
            link = new ArrayList<>();
        }
        return this.link;
    }

    /**
     * Obtiene el valor de la propiedad shipmentId.
     * 
     */
    public long getShipmentId() {
        return shipmentId;
    }

    /**
     * Define el valor de la propiedad shipmentId.
     * 
     */
    public void setShipmentId(long value) {
        this.shipmentId = value;
    }

}
