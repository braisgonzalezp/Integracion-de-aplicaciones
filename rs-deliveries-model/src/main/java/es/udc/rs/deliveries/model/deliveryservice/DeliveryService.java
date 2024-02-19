package es.udc.rs.deliveries.model.deliveryservice;

import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.CustomerWithShipmentsException;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.InvalidUpdateStatusException;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;
import es.udc.ws.util.exceptions.InputValidationException;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface DeliveryService {

    // Dar de alta un cliente (Nombre empresa, CIF, dirección y crear automáticamente la fecha de alta y un id)
    public Customer addCustomer(Customer customer) throws InputValidationException;

    // Eliminar un cliente (solo aquellos que no tengan asociado ningún envío)
    public void deleteCustomer(Long customerId) throws InstanceNotFoundException, CustomerWithShipmentsException;

    // Modificar los datos de un cliente (solo modificar los datos que se proporcionan a la hora de crearlos)
    public void updateCustomer(Long customerId, String company, String cif, String address) throws InputValidationException, InstanceNotFoundException;

    // Consultar todos los datos de un cliente.
    public Customer findCustomer(Long customerId) throws InstanceNotFoundException;

    // Consultar todos los datos de clientes dado un texto. (texto se refiere a parte del nombre de la empresa (no distingue mayus y minus))
    public List<Customer> findCustomers(String keywords) throws InputValidationException;

    //-------------------------//

    // Dar de alta un envío (ID Cliente, RefPaquete, Dirección, [Automáticamente se crea la fecha de alta del envío, y
    // un ID para utilizar como clave de ese envío y la fecha estimada de entrega (48h más después de la fecha de alta)], Estado automático (PENDING))
    public Shipment addShipment(Shipment shipment) throws InputValidationException, InstanceNotFoundException;

    // Modificar un envío (Pendiente --> Enviado/Cancelado; Enviado --> Entregado/Rechazado; Cuando el envío está en Entregado se almacena la fecha de entrega.
    // Tener en cuenta la desviación de tiempo entre la fecha de entrega estimada y la real)
    public void updateShipment(Long shipmentId, ShipmentStatus status) throws InvalidUpdateStatusException, InstanceNotFoundException;

    // Solo se puede cancelar un pedido cuando un pedido está pendiente.
    public void cancelShipment(Long shipmentId) throws InstanceNotFoundException, InvalidUpdateStatusException;

    // Consultar todos los datos de un envío en concreto (Pasar el ID del envío)
    public Shipment findShipment(Long shipmentId)throws InstanceNotFoundException;

    // Consultar todos los envíos de un cliente (opcionalmente se puede filtrar por estado. Pasar el id de cliente. Tiene que soportar paginación, es decir, el usuario tendrá
    // que especificar el número de elementos que quiere obtener (especificando el índice del primer elemento que se desea obtener y el número máximo de elementos))
    public List<Shipment> findShipments(Long customerId, ShipmentStatus status, int index, int elements) throws InstanceNotFoundException, InputValidationException;
}
