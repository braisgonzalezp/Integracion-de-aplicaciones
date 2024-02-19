package es.udc.rs.deliveries.client.service.rest;

import es.udc.rs.deliveries.client.service.dto.ClientCustomerDto;
import es.udc.rs.deliveries.client.service.rest.dto.CustomerType;
import es.udc.rs.deliveries.client.service.rest.dto.ObjectFactory;
import jakarta.xml.bind.JAXBElement;

import java.util.ArrayList;
import java.util.List;

public class CustomerDtoToCustomerDtoJaxbConversor {

    public static JAXBElement<CustomerType> toJaxbCustomer(ClientCustomerDto customerDto) {
        CustomerType customer = new CustomerType();
        customer.setCustomerId(customerDto.getCustomerId() != null ? customerDto.getCustomerId() : -1);
        customer.setAddress(customerDto.getAddress());
        customer.setCif(customerDto.getCif());
        customer.setName(customerDto.getName());
        //customer.setLinks(customerDto.getLinks());
        return new ObjectFactory().createCustomer(customer);
    }

    public static ClientCustomerDto toClientCustomerDto(CustomerType customer) {
        return new ClientCustomerDto(customer.getCustomerId(), customer.getAddress(), customer.getCif(), customer.getName()/*,customer.getLinks()*/);
    }

    public static List<ClientCustomerDto> toClientCustomerDtos(List<CustomerType> customerListDto) {
        List<ClientCustomerDto> customerDtos = new ArrayList<>(customerListDto.size());
        for (CustomerType customerDtoType : customerListDto) {
            customerDtos.add(toClientCustomerDto(customerDtoType));
        }
        return customerDtos;
    }

}

