package es.udc.rs.deliveries.client.service.dto;

import java.util.List;

public class ClientCustomerDto {

    private Long customerId;
    private String name;
    private String cif;
    private String address;

    private List<AtomLinkDtoJaxb> links;

    public ClientCustomerDto() {
    }

    public ClientCustomerDto(String name, String cif, String address/*, List<AtomLinkDtoJaxb> links*/) {
        this.name = name;
        this.cif = cif;
        this.address = address;
        //this.links = links;
    }

    public ClientCustomerDto(Long customerId, String name, String cif, String address/*,List<AtomLinkDtoJaxb> links*/) {
        this(name, cif, address/*,links*/);
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    /*public List<AtomLinkDtoJaxb> getLinks() {
        return links;
    }

    public void setLinks(List<AtomLinkDtoJaxb> links) {
        this.links = links;
    }*/

    @Override
    public String toString() {
        return "ClientCustomerDto [" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", cif='" + cif + '\'' +
                ", address='" + address + '\'' +
                ']';
    }

}

