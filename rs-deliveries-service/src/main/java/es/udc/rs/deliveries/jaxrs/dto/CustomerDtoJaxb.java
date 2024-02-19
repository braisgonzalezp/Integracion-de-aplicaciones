package es.udc.rs.deliveries.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlRootElement(name = "customer")
@XmlType(name="customerType", propOrder = {"id", "name", "cif", "address", "links" })
public class CustomerDtoJaxb {

    @XmlAttribute(name = "customerId", required = true)
    private Long id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String cif;
    @XmlElement(required = true)
    private String address;
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private List<AtomLinkDtoJaxb> links;                                        // ---> tengo que cambiarlo a una lista de links (serán 3 o 2 links "self" "los 10 primeros
                                                                                // shipments" opcional sin envíos: "borrar customer")
    public CustomerDtoJaxb() {
    }

    public CustomerDtoJaxb(Long customerId, String name, String cif, String address, List<AtomLinkDtoJaxb> links) {
        this.id = customerId;
        this.name = name;
        this.cif = cif;
        this.address = address;
        this.links = links;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AtomLinkDtoJaxb> getLinks() {
        return links;
    }

    public void setLinks(List<AtomLinkDtoJaxb> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "CustomerDtoJaxb [" +
                "customerId=" + id +
                ", name='" + name + '\'' +
                ", cif='" + cif + '\'' +
                ", address='" + address + '\'' +
                ", self=" + links +
                ']';
    }
}
