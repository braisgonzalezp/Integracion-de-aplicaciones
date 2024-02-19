package es.udc.rs.deliveries.model.deliveryservice;

import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.CustomerWithShipmentsException;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.InvalidUpdateStatusException;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;
import es.udc.ws.util.exceptions.InputValidationException;

import javax.management.InstanceNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MockDeliveryService implements DeliveryService {

	private Map<Long,Customer> customersMap = new LinkedHashMap<Long,Customer>();
	private Map<Long,Shipment> shipmentsMap = new LinkedHashMap<Long,Shipment>();
	private Map<Long,List<Shipment>> shipmentsByUserMap = new LinkedHashMap<Long,List<Shipment>>();

	private long lastCustomerId = 0;
	private long lastShippingId = 0;
	public void removeShipment(Long customerId,Shipment shipment){
		shipmentsByUserMap.remove(customerId);
	}
	private synchronized long getNextCustomertId() {
		return ++lastCustomerId;
	}
	
	private synchronized long getNextShippingId() {
		return ++lastShippingId;
	}

	// Dar de alta un cliente (Nombre empresa, CIF, dirección y crear automáticamente la fecha de alta y un id)
	public Customer addCustomer(Customer customer) throws InputValidationException {

		if (customer.getName() == null) {
			throw new InputValidationException("Invalid customer name.");
		}
		if (customer.getCif() == null || !customer.getCif().matches("^[A-Z,a-z][0-9]{8}$")) {
			throw new InputValidationException("Invalid customer cif.");
		}
		if (customer.getAddress() == null) {
			throw new InputValidationException("Invalid customer address.");
		}

		// Obtenemos el identificador del cliente para usarlo en su creación y para usarlo de clave en la lista
		Long customerId = getNextCustomertId();

		// Creamos el cliente
		Customer newCustomer = new Customer(customerId, customer.getName(), customer.getCif(), customer.getAddress(),
				LocalDateTime.now().withNano(0));

		// Introducimos el cliente en la lista
		customersMap.put(customerId, newCustomer);

		return new Customer(newCustomer.getCustomerId(), newCustomer.getName(), newCustomer.getCif(), newCustomer.getAddress(),
				newCustomer.getCreationDate());
	}

	// Eliminar un cliente (solo aquellos que no tengan asociado ningún envío)
	public void deleteCustomer(Long customerId) throws InstanceNotFoundException, CustomerWithShipmentsException{

		Customer customer = customersMap.get(customerId);
		if(customer == null){
			throw new InstanceNotFoundException("Customer with id " + customerId + " not found");
		}

		// Primero tenemos que obtener los envíos del cliente
		List<Shipment> shipments = shipmentsByUserMap.get(customerId);

		// Comprobamos si la lista de envíos de ese cliente está vacía. En caso de que lo esté eliminamos el cliente.
		if(shipments == null){
			customersMap.remove(customerId);
		}else{
			throw new CustomerWithShipmentsException(customerId);
		}
	}

	// Modificar los datos de un cliente (solo modificar los datos que se proporcionan a la hora de crearlos)
	public void updateCustomer(Long customerId, String company, String cif, String address) throws InputValidationException, InstanceNotFoundException {

		if (customerId == null) {
			throw new InputValidationException("Invalid customer id.");
		}
		if (cif != null && !cif.matches("^[A-Z,a-z][0-9]{8}$")) {
			throw new InputValidationException("Invalid customer cif.");
		}
		if (!customersMap.containsKey(customerId)) {
			throw new InstanceNotFoundException("Customer with id " + customerId + " not found");
		}

		// Primero obtenemos el cliente que queremos modificar.
		Customer customer = customersMap.get(customerId);

		// Cambiamos los valores del cliente.
		if (company != null){
			customer.setName(company);
		}
		if (cif != null) {
			customer.setCif(cif);
		}
		if (address != null) {
			customer.setAddress(address);
		}
	}

	// Consultar todos los datos de un cliente.
	public Customer findCustomer(Long customerId) throws InstanceNotFoundException {

		// Obtenemos el cliente de la lista
		Customer customer = customersMap.get(customerId);
		if(customer == null){
			throw new InstanceNotFoundException("Customer with id: " + customerId + " not found");
		}else {
			return customer;
		}
	}

	// Consultar todos los datos de clientes dado un texto. (texto se refiere a parte del nombre de la empresa (no distingue mayus y minus))
	public List<Customer> findCustomers(String keywords) throws InputValidationException {

		if(keywords == null){
			throw new InputValidationException("Keywords can not be null");
		}

		// Declaramos la lista de Customers que devolveremos
		List<Customer> foundCustomers = new ArrayList<>();

		customersMap.forEach((key, customer) -> {
			//Verificamos si el nombre del customer creado contiene text
			if (customer.getName().toLowerCase().contains(keywords.toLowerCase())) {
				foundCustomers.add(customer);
			}
		});

		return foundCustomers;
	}

	//-------------------------//

	// Dar de alta un envío (ID Cliente, RefPaquete, Dirección, [Automáticamente se crea la fecha de alta del envío, y
	// un ID para utilizar como clave de ese envío y la fecha estimada de entrega (48h más después de la fecha de alta)], Estado automático (PENDING))
	public Shipment addShipment(Shipment shipment) throws InputValidationException, InstanceNotFoundException {

		if (shipment.getCustomerId() == null) {
			throw new InputValidationException("Invalid customer id");
		}
		if (shipment.getPackageReference() == null) {
			throw new InputValidationException("Invalid package reference");
		}
		if (shipment.getAddress() == null) {
			throw new InputValidationException("Invalid shipment address");
		}
		if (!customersMap.containsKey(shipment.getCustomerId())) {
			throw new InstanceNotFoundException("No customer with id " + shipment.getCustomerId());
		}

		// Obtenemos el identificador del envío para usarlo en su creación y para usarlo de clave en la lista
		Long shipmentId = getNextShippingId();

		// Creamos el envío
		Shipment newShipment = new Shipment(shipmentId, shipment.getCustomerId(), shipment.getPackageReference(),
				shipment.getAddress(), LocalDateTime.now().withNano(0),
				LocalDateTime.now().withNano(0).plusHours(48));

		// Introducimos el envío en la lista
		shipmentsMap.put(shipmentId, newShipment);

		// Obtenemos la lista de envíos del cliente
		List<Shipment> customerShipments = shipmentsByUserMap.get(shipment.getCustomerId());

		// Introducimos el envío en la lista de envíos del cliente
		if (customerShipments == null) {
			customerShipments = new ArrayList<>();
			customerShipments.add(newShipment);
		} else {
			customerShipments.add(newShipment);
		}

		// Actualizamos la lista
		shipmentsByUserMap.put(shipment.getCustomerId(), customerShipments);

		return newShipment;
	}

	// Modificar un envío (Pendiente --> Enviado/Cancelado; Enviado --> Entregado/Rechazado; Cuando el envío está en Entregado se almacena la fecha de entrega.
	// Tener en cuenta la desviación de tiempo entre la fecha de entrega estimada y la real)
	public void updateShipment(Long shipmentId, ShipmentStatus status) throws InvalidUpdateStatusException, InstanceNotFoundException {

		if (!shipmentsMap.containsKey(shipmentId)) {
			throw new InstanceNotFoundException("No shipment with id " + shipmentId);
		}

		// Obtenemos el envío que queremos modificar
		Shipment shipment = shipmentsMap.get(shipmentId);

		// Pendiente --> Enviado/Cancelado
		if (shipment.getStatus() == ShipmentStatus.PENDING && (status == ShipmentStatus.SENT || status == ShipmentStatus.CANCELLED)) {
			shipment.setStatus(status);
			// Enviado --> Entregado/Rechazado
		}else if (shipment.getStatus() == ShipmentStatus.SENT && (status == ShipmentStatus.DELIVERED || status == ShipmentStatus.REJECTED)){
			if (status == ShipmentStatus.DELIVERED){
				shipment.setDeliveryDate(LocalDateTime.now().withNano(0));
			}
			shipment.setStatus(status);
		}else{
			throw new InvalidUpdateStatusException(shipmentId);// Faltan excepciones
		}
	}

	// Solo se puede cancelar un pedido cuando un pedido está pendiente.
	public void cancelShipment(Long shipmentId) throws InstanceNotFoundException, InvalidUpdateStatusException {

		if (!shipmentsMap.containsKey(shipmentId)) {
			throw new InstanceNotFoundException("No shipment with id " + shipmentId);
		}

		// Obtenemos el envío que queremos cancelar
		Shipment shipment = shipmentsMap.get(shipmentId);

		// Comprobamos que el estado del pedido actualmente sea el correcto
		if (shipment.getStatus() != ShipmentStatus.PENDING) {
			throw new InvalidUpdateStatusException(shipmentId);
		}

		// Actualizamos el estado
		shipment.setStatus(ShipmentStatus.CANCELLED);
	}

	// Consultar todos los datos de un envío en concreto (Pasar el ID del envío)
	public Shipment findShipment(Long shipmentId) throws InstanceNotFoundException{

		// Obtenemos el envío de la lista
		Shipment shipment = shipmentsMap.get(shipmentId);

		if(shipment == null){
			throw new InstanceNotFoundException();
		}
		return shipment;
	}

	// Consultar todos los envíos de un cliente (opcionalmente se puede filtrar por estado. Pasar el id de cliente. Tiene que soportar paginación, es decir, el usuario tendrá
	// que especificar el número de elementos que quiere obtener (especificando el índice del primer elemento que se desea obtener y el número máximo de elementos))
	public List<Shipment> findShipments(Long customerId, ShipmentStatus status, int index, int elements) throws InstanceNotFoundException, InputValidationException {

		if (customerId == null) {
			throw new InputValidationException("Invalid customer id");
		}
		if (!customersMap.containsKey(customerId)) {
			throw new InstanceNotFoundException("No customer with id " + customerId);
		}

		// Declaración de variables
		int indexCounter = 0;
		List<Shipment> foundShipments = new ArrayList<>();

		// Obtenemos la lista de envíos del cliente.
		List<Shipment> customerShipments = shipmentsByUserMap.get(customerId);

		// Comprobamos que se haya especificado el rango de elementos de la lista.
		if(elements < 0 || index < 0){
			throw new InstanceNotFoundException();
		}
		if(elements != 0){

			if (status != null) {
				// Recorremos toda la lista de envíos
				for (Shipment shipment : customerShipments) {
					// Comprobamos que el estado es el deseado
					if (shipment.getStatus() == status){
						if(indexCounter < index) { // Comprobamos que el índice es el deseado
							indexCounter++;
						} else {
							if (elements > foundShipments.size()) { //Comprobamos que el número de elementos es el deseado
								foundShipments.add(shipment);
							} else {
								break;
							}
						}
					}
				}
			} else {
				for (Shipment shipment : customerShipments) {
					if(indexCounter < index) { // Comprobamos que el índice es el deseado
						indexCounter++;
					} else {
						if (elements > foundShipments.size()) { //Comprobamos que el número de elementos es el deseado
							foundShipments.add(shipment);
						} else {
							break;
						}
					}
				}
			}

		} else { // En caso de que no se haya especificado obtendremos todos los envíos

			if (status != null) {
				// Recorremos toda la lista
				for (Shipment shipment : customerShipments) {
					// Comprobamos que el estado es el deseado
					if (shipment.getStatus() == status){
						foundShipments.add(shipment);
					}
				}
			} else {
					foundShipments = customerShipments;
			}
		}
		return foundShipments;
	}
}
