//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v4.0.3 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
//


package es.udc.rs.deliveries.client.service.rest.dto;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the es.udc.rs.deliveries.client.service.rest.dto package. 
 * <p>An ObjectFactory allows you to programmatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _Link_QNAME = new QName("http://www.w3.org/2005/Atom", "link");
    private static final QName _Customer_QNAME = new QName("http://ws.udc.es/deliveries/xml", "customer");
    private static final QName _CustomerWithShipmentsException_QNAME = new QName("http://ws.udc.es/deliveries/xml", "customerWithShipmentsException");
    private static final QName _InputValidationException_QNAME = new QName("http://ws.udc.es/deliveries/xml", "inputValidationException");
    private static final QName _InstanceNotFoundException_QNAME = new QName("http://ws.udc.es/deliveries/xml", "instanceNotFoundException");
    private static final QName _InvalidUpdateStatusException_QNAME = new QName("http://ws.udc.es/deliveries/xml", "invalidUpdateStatusException");
    private static final QName _Shipment_QNAME = new QName("http://ws.udc.es/deliveries/xml", "shipment");
    private static final QName _Shipments_QNAME = new QName("http://ws.udc.es/deliveries/xml", "shipments");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: es.udc.rs.deliveries.client.service.rest.dto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AtomLinkType }
     * 
     * @return
     *     the new instance of {@link AtomLinkType }
     */
    public AtomLinkType createAtomLinkType() {
        return new AtomLinkType();
    }

    /**
     * Create an instance of {@link CustomerType }
     * 
     * @return
     *     the new instance of {@link CustomerType }
     */
    public CustomerType createCustomerType() {
        return new CustomerType();
    }

    /**
     * Create an instance of {@link CustomerWithShipmentsExceptionType }
     * 
     * @return
     *     the new instance of {@link CustomerWithShipmentsExceptionType }
     */
    public CustomerWithShipmentsExceptionType createCustomerWithShipmentsExceptionType() {
        return new CustomerWithShipmentsExceptionType();
    }

    /**
     * Create an instance of {@link InputValidationExceptionType }
     * 
     * @return
     *     the new instance of {@link InputValidationExceptionType }
     */
    public InputValidationExceptionType createInputValidationExceptionType() {
        return new InputValidationExceptionType();
    }

    /**
     * Create an instance of {@link InstanceNotFoundExceptionType }
     * 
     * @return
     *     the new instance of {@link InstanceNotFoundExceptionType }
     */
    public InstanceNotFoundExceptionType createInstanceNotFoundExceptionType() {
        return new InstanceNotFoundExceptionType();
    }

    /**
     * Create an instance of {@link InvalidUpdateStatusExceptionType }
     * 
     * @return
     *     the new instance of {@link InvalidUpdateStatusExceptionType }
     */
    public InvalidUpdateStatusExceptionType createInvalidUpdateStatusExceptionType() {
        return new InvalidUpdateStatusExceptionType();
    }

    /**
     * Create an instance of {@link ShipmentType }
     * 
     * @return
     *     the new instance of {@link ShipmentType }
     */
    public ShipmentType createShipmentType() {
        return new ShipmentType();
    }

    /**
     * Create an instance of {@link ShipmentListType }
     * 
     * @return
     *     the new instance of {@link ShipmentListType }
     */
    public ShipmentListType createShipmentListType() {
        return new ShipmentListType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AtomLinkType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AtomLinkType }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/Atom", name = "link")
    public JAXBElement<AtomLinkType> createLink(AtomLinkType value) {
        return new JAXBElement<>(_Link_QNAME, AtomLinkType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CustomerType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/deliveries/xml", name = "customer")
    public JAXBElement<CustomerType> createCustomer(CustomerType value) {
        return new JAXBElement<>(_Customer_QNAME, CustomerType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerWithShipmentsExceptionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CustomerWithShipmentsExceptionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/deliveries/xml", name = "customerWithShipmentsException")
    public JAXBElement<CustomerWithShipmentsExceptionType> createCustomerWithShipmentsException(CustomerWithShipmentsExceptionType value) {
        return new JAXBElement<>(_CustomerWithShipmentsException_QNAME, CustomerWithShipmentsExceptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InputValidationExceptionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InputValidationExceptionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/deliveries/xml", name = "inputValidationException")
    public JAXBElement<InputValidationExceptionType> createInputValidationException(InputValidationExceptionType value) {
        return new JAXBElement<>(_InputValidationException_QNAME, InputValidationExceptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InstanceNotFoundExceptionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InstanceNotFoundExceptionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/deliveries/xml", name = "instanceNotFoundException")
    public JAXBElement<InstanceNotFoundExceptionType> createInstanceNotFoundException(InstanceNotFoundExceptionType value) {
        return new JAXBElement<>(_InstanceNotFoundException_QNAME, InstanceNotFoundExceptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidUpdateStatusExceptionType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InvalidUpdateStatusExceptionType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/deliveries/xml", name = "invalidUpdateStatusException")
    public JAXBElement<InvalidUpdateStatusExceptionType> createInvalidUpdateStatusException(InvalidUpdateStatusExceptionType value) {
        return new JAXBElement<>(_InvalidUpdateStatusException_QNAME, InvalidUpdateStatusExceptionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShipmentType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ShipmentType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/deliveries/xml", name = "shipment")
    public JAXBElement<ShipmentType> createShipment(ShipmentType value) {
        return new JAXBElement<>(_Shipment_QNAME, ShipmentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShipmentListType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ShipmentListType }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws.udc.es/deliveries/xml", name = "shipments")
    public JAXBElement<ShipmentListType> createShipments(ShipmentListType value) {
        return new JAXBElement<>(_Shipments_QNAME, ShipmentListType.class, null, value);
    }

}
