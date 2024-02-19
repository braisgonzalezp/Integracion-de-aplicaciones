package es.udc.rs.deliveries.client.service;

import es.udc.rs.deliveries.client.service.dto.ClientCustomerDto;
import es.udc.rs.deliveries.client.service.dto.ClientShipmentDto;
import es.udc.rs.deliveries.client.service.dto.ClientShipmentStatusDto;
import es.udc.rs.deliveries.client.service.exceptions.ClientCustomerWithShipmentsException;
import es.udc.rs.deliveries.client.service.exceptions.ClientInvalidUpdateStatusException;
import es.udc.ws.util.exceptions.InputValidationException;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface ClientDeliveryService {
    ClientCustomerDto addCustomer(ClientCustomerDto customerDto) throws InputValidationException;

    void deleteCustomer(Long customerId) throws InstanceNotFoundException, ClientCustomerWithShipmentsException;

    //void updateCustomer(ClientCustomerDto customerDto) throws InputValidationException, InstanceNotFoundException;

    //ClientCustomerDto findCustomer(Long customerId) throws InstanceNotFoundException;

    //List<ClientCustomerDto> findCustomers(String keywords) throws InputValidationException;

    //ClientShipmentDto addShipment(ClientShipmentDto shipment) throws InputValidationException, InstanceNotFoundException;

    void updateShipment(Long shipmentId, ClientShipmentStatusDto status) throws ClientInvalidUpdateStatusException, InstanceNotFoundException;

    //void cancelShipment(ClientShipmentDto shipment) throws InstanceNotFoundException, ClientInvalidUpdateStatusException;

    //ClientShipmentDto findShipment(Long shipmentId)throws InstanceNotFoundException;

    public List<ClientShipmentDto> findShipments(Long customerId, ClientShipmentStatusDto status, int index, int elements) throws InstanceNotFoundException, InputValidationException;
}
