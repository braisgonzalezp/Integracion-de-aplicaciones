package es.udc.rs.deliveries.test.model.deliveryservice;

import es.udc.rs.deliveries.model.customer.Customer;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryService;
import es.udc.rs.deliveries.model.deliveryservice.DeliveryServiceFactory;
import es.udc.rs.deliveries.model.deliveryservice.MockDeliveryService;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.CustomerWithShipmentsException;
import es.udc.rs.deliveries.model.deliveryservice.exceptions.InvalidUpdateStatusException;
import es.udc.rs.deliveries.model.shipment.Shipment;
import es.udc.rs.deliveries.model.shipment.ShipmentStatus;
import es.udc.ws.util.exceptions.InputValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryServiceTest {

    private static DeliveryService deliveryService = null;
    private final long NON_EXISTENT_CUSTOMER_ID = -1;
    private final String NON_EXISTENT_CUSTOMER_NAME = "NONEXISTENTCUSTOMERNAME";
    private final String VALID_CUSTOMER_NAME = "Trileuco Solutions S.L.O.";
    private final String VALID_CUSTOMER_CIF = "A12345678";
    private final String VALID_CUSTOMER_ADDRESS = "C/ Arroyo Blanco N2";
    private final long VALID_SHIPMENT_REFERENCE = 1;

    private void deleteAllCustomers(Customer... customers){
        Arrays.stream(customers).forEach(customer -> {
            try {
                deliveryService.deleteCustomer(customer.getCustomerId());
            } catch (InstanceNotFoundException | CustomerWithShipmentsException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Customer getValidCustomer(String name){
        return new Customer(name, VALID_CUSTOMER_CIF, VALID_CUSTOMER_ADDRESS);
    }

    private Shipment getValidShipment() throws InputValidationException {
        // Create the customer
        Customer customer1 = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));

        // Find customer
        long customer1Id = customer1.getCustomerId();
        return new Shipment(customer1Id, VALID_SHIPMENT_REFERENCE, VALID_CUSTOMER_ADDRESS);
    }

    private Shipment getShipment(Long shipmentReference, String address) throws InputValidationException {
        Customer customer1 = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));

        // Find customer
        long customer1Id = customer1.getCustomerId();
        return new Shipment(customer1Id, shipmentReference, address);
    }

    private void deleteShipments(long shipmentId) throws InstanceNotFoundException {
        Shipment shipment = deliveryService.findShipment(shipmentId);
        Customer customer = deliveryService.findCustomer(shipment.getCustomerId());
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment);
    }

    @BeforeAll
    public static void init() {
        deliveryService = DeliveryServiceFactory.getService();
    }

    @Test
    public void testAddCustomerAndFindCustomer() throws InputValidationException, InstanceNotFoundException, CustomerWithShipmentsException {

        // Create the customer
        Customer customer1 = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));
        Customer customer2 = deliveryService.addCustomer(getValidCustomer("Altia"));

        // Find customer
        long customer1Id = customer1.getCustomerId();
        long customer2Id = customer2.getCustomerId();
        Customer foundCustomer1 = deliveryService.findCustomer(customer1Id);
        Customer foundCustomer2 = deliveryService.findCustomer(customer2Id);

        try {
            // Test for customer 1
            assertEquals(customer1Id, foundCustomer1.getCustomerId());
            assertEquals(VALID_CUSTOMER_NAME, foundCustomer1.getName());
            assertEquals(VALID_CUSTOMER_CIF, foundCustomer1.getCif());
            assertEquals(VALID_CUSTOMER_ADDRESS, foundCustomer1.getAddress());

            // Test for customer 2
            assertEquals(customer2Id, foundCustomer2.getCustomerId());
            assertEquals("Altia", foundCustomer2.getName());
            assertEquals(VALID_CUSTOMER_CIF, foundCustomer2.getCif());
            assertEquals(VALID_CUSTOMER_ADDRESS, foundCustomer2.getAddress());
        }finally {
            deliveryService.deleteCustomer(customer1Id);
            deliveryService.deleteCustomer(customer2Id);
        }
    }

    @Test
    public void testAddInvalidCustomer() {

        Customer nullCif = new Customer(VALID_CUSTOMER_NAME,null,VALID_CUSTOMER_ADDRESS);
        Customer invalidCif = new Customer(VALID_CUSTOMER_NAME,"SADASDA",VALID_CUSTOMER_ADDRESS);
        Customer invalidName = new Customer(null,VALID_CUSTOMER_CIF,VALID_CUSTOMER_ADDRESS);
        Customer invalidAdress = new Customer(VALID_CUSTOMER_NAME,VALID_CUSTOMER_CIF,null);

        assertThrows(InputValidationException.class, () -> {
            deliveryService.addCustomer(nullCif);
        });
        assertThrows(InputValidationException.class, () -> {
            deliveryService.addCustomer(invalidCif);
        });
        assertThrows(InputValidationException.class, () -> {
            deliveryService.addCustomer(invalidName);
        });
        assertThrows(InputValidationException.class, () -> {
            deliveryService.addCustomer(invalidAdress);
        });
    }

    @Test
    public void testFindNonExistentCustomer() {
        assertThrows(InstanceNotFoundException.class, () -> deliveryService.findCustomer(NON_EXISTENT_CUSTOMER_ID));
    }

    @Test
    public void testUpdateCustomer() throws InputValidationException, InstanceNotFoundException {

        // Create the customer
        Customer customer = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));

        // Create variables
        long customerId = customer.getCustomerId();
        String otherName = "Altia";
        String otherCif = "B87654321";
        String otherAddress = "Plaza Ramón Bilbao N1";

        // Update and find customer
        // In case we only want to change the name
        deliveryService.updateCustomer(customerId, otherName, null, null);
        Customer foundCustomer = deliveryService.findCustomer(customerId);

        assertEquals(customerId, foundCustomer.getCustomerId());
        assertEquals(otherName, foundCustomer.getName());
        assertEquals(VALID_CUSTOMER_CIF, foundCustomer.getCif());
        assertEquals(VALID_CUSTOMER_ADDRESS, foundCustomer.getAddress());

        // In case we only want to change the cif
        deliveryService.updateCustomer(customerId, null, otherCif, null);
        foundCustomer = deliveryService.findCustomer(customerId);

        assertEquals(customerId, foundCustomer.getCustomerId());
        assertEquals(otherName, foundCustomer.getName());
        assertEquals(otherCif, foundCustomer.getCif());
        assertEquals(VALID_CUSTOMER_ADDRESS, foundCustomer.getAddress());

        // In case we only want to change the address
        deliveryService.updateCustomer(customerId, null, null, otherAddress);
        foundCustomer = deliveryService.findCustomer(customerId);

        assertEquals(customerId, foundCustomer.getCustomerId());
        assertEquals(otherName, foundCustomer.getName());
        assertEquals(otherCif, foundCustomer.getCif());
        assertEquals(otherAddress, foundCustomer.getAddress());

        // In case we want to change all the atributes
        deliveryService.updateCustomer(customerId, VALID_CUSTOMER_NAME, VALID_CUSTOMER_CIF, VALID_CUSTOMER_ADDRESS);
        foundCustomer = deliveryService.findCustomer(customerId);

        assertEquals(customerId, foundCustomer.getCustomerId());
        assertEquals(VALID_CUSTOMER_NAME, foundCustomer.getName());
        assertEquals(VALID_CUSTOMER_CIF, foundCustomer.getCif());
        assertEquals(VALID_CUSTOMER_ADDRESS, foundCustomer.getAddress());

        deleteAllCustomers(customer);
    }

    @Test
    public void testUpdateInvalidCustomer() {
        assertThrows(InputValidationException.class, () -> deliveryService.updateCustomer(null, null, null, null));
        assertThrows(InputValidationException.class, () -> deliveryService.updateCustomer(1L, "Altia", "8987654321", null));
        assertThrows(InstanceNotFoundException.class, () -> deliveryService.updateCustomer(-1L, "Altia", "B87654321", "C/ Fallo"));
    }

    @Test
    public void testDeleteCustomer() throws InputValidationException, InstanceNotFoundException, CustomerWithShipmentsException {

        // Create the customer
        Customer customer1 = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));
        Customer customer2 = deliveryService.addCustomer(getValidCustomer("Altia"));
        Customer customer3 = deliveryService.addCustomer(getValidCustomer("Inditex"));

        // Find customer
        long customer2Id = customer2.getCustomerId();
        Customer foundCustomer = deliveryService.findCustomer(customer2Id);

        // Test for customer 2
        assertEquals(customer2Id, foundCustomer.getCustomerId());
        assertEquals("Altia", foundCustomer.getName());
        assertEquals(VALID_CUSTOMER_CIF, foundCustomer.getCif());
        assertEquals(VALID_CUSTOMER_ADDRESS, foundCustomer.getAddress());

        deliveryService.deleteCustomer(customer2Id);
        try {
            assertThrows(InstanceNotFoundException.class, () -> deliveryService.findCustomer(customer2Id));
        }finally {
            deleteAllCustomers(customer1, customer3);
        }
    }

    @Test
    public void testDeleteNonExistentCustomer() throws InstanceNotFoundException, InputValidationException, CustomerWithShipmentsException {
        Customer customer1 = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));
        long customer1Id = customer1.getCustomerId();
        deliveryService.deleteCustomer(customer1Id);
        assertThrows(InstanceNotFoundException.class, () -> deliveryService.deleteCustomer(customer1Id));
    }

    @Test
    public void testAddCustomersAndFindCustomersByText() throws InputValidationException, InstanceNotFoundException {

        // Create the customer
        Customer customer1 = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));
        Customer customer2 = deliveryService.addCustomer(getValidCustomer("Altia Solutions S.A"));
        Customer customer3 = deliveryService.addCustomer(getValidCustomer("Inditex S.A"));

        List<Customer> customer = new ArrayList<>();
        customer.add(customer1);

        List<Customer> customers1 = new ArrayList<>();
        customers1.add(customer1);
        customers1.add(customer2);

        List<Customer> customers2 = new ArrayList<>();
        customers2.add(customer2);
        customers2.add(customer3);


        // Find customers
        List<Customer> foundCustomers = deliveryService.findCustomers(NON_EXISTENT_CUSTOMER_NAME);
        assertEquals(0, foundCustomers.size());

        foundCustomers = deliveryService.findCustomers("TrIlEuCo Solutions");
        assertEquals(customer, foundCustomers);
        assertEquals(1, foundCustomers.size());

        foundCustomers = deliveryService.findCustomers("Solutions");
        assertEquals(customers1, foundCustomers);
        assertEquals(2, foundCustomers.size());

        foundCustomers = deliveryService.findCustomers("S.A");
        assertEquals(customers2, foundCustomers);
        assertEquals(2, foundCustomers.size());

        deleteAllCustomers(customer1, customer2, customer3);
    }

    @Test
    public void testAddCustomersAndFindCustomersByInvalidText() {
        assertThrows(InputValidationException.class, () -> deliveryService.findCustomers(null));
    }

    @Test
    public void testAddCustomerAndFindShipment() throws InputValidationException, InstanceNotFoundException, CustomerWithShipmentsException {

        //Create shipments
        Shipment shipment1 = deliveryService.addShipment(getValidShipment());
        Shipment shipment2 = getValidShipment();
        //Get shipment iDs
        long shipment1Id = shipment1.getShipmentId();
        Shipment foundShipment1 = deliveryService.findShipment(shipment1Id);

        try {
            // Test for customer 1
            assertEquals(shipment1Id, foundShipment1.getShipmentId());
            assertEquals(VALID_CUSTOMER_ADDRESS, foundShipment1.getAddress());
            assertThrows(InstanceNotFoundException.class, () -> deliveryService.findShipment(shipment2.getShipmentId()));
        }finally {
            deleteShipments(shipment1Id);
            deliveryService.deleteCustomer(shipment1.getCustomerId());
            deliveryService.deleteCustomer(shipment2.getCustomerId());
        }
    }

    @Test
    public void addInvalidShipmentTest() throws InputValidationException, InstanceNotFoundException, CustomerWithShipmentsException {
        Shipment invalidRefShipment = getShipment(null, "C/ Fallo");
        Shipment invalidAddressShipment = getShipment(1L, null);
        Shipment invalidCustomerShipment = new Shipment(null, 1L, "C/ Fallo");
        Shipment notFoundCustomerShipment = new Shipment(-1L, 1L, "C/ Fallo");

        assertThrows(InputValidationException.class, () -> deliveryService.addShipment(invalidCustomerShipment));
        assertThrows(InputValidationException.class, () -> deliveryService.addShipment(invalidAddressShipment));
        assertThrows(InstanceNotFoundException.class, () -> deliveryService.addShipment(notFoundCustomerShipment));

        deliveryService.deleteCustomer(invalidRefShipment.getCustomerId());
        deliveryService.deleteCustomer(invalidAddressShipment.getCustomerId());
    }

    @Test
    public void testUpdateValidShipmentStatus() throws InputValidationException, InvalidUpdateStatusException, InstanceNotFoundException, CustomerWithShipmentsException {

        //CASO 1: PENDING -> SENT -> DELIVERED

        //Como creamos el paquete el estado es pending
        Shipment shipment1 = deliveryService.addShipment(getValidShipment());
        assertEquals(ShipmentStatus.PENDING, shipment1.getStatus());

        long shipment1Id = shipment1.getShipmentId();

        //probamos a ver si podemos cambiar el estado de pending a sent
        deliveryService.updateShipment(shipment1Id,ShipmentStatus.SENT);
        assertEquals(ShipmentStatus.SENT, shipment1.getStatus());

        //Y DE SENT A DELIVERED
        deliveryService.updateShipment(shipment1Id,ShipmentStatus.DELIVERED);
        assertEquals(ShipmentStatus.DELIVERED, shipment1.getStatus());

        //CASO 2: PENDING -> SENT -> REJECTED
        //Como creamos el paquete el estado es pending
        Shipment shipment2 = deliveryService.addShipment(getValidShipment());
        assertEquals(ShipmentStatus.PENDING, shipment2.getStatus());

        long shipment2Id = shipment2.getShipmentId();

        //CAMBIAMOS A SENT
        deliveryService.updateShipment(shipment2Id,ShipmentStatus.SENT);

        //Y DE SENT A REJECTED
        deliveryService.updateShipment(shipment2Id,ShipmentStatus.REJECTED);
        assertEquals(ShipmentStatus.REJECTED, shipment2.getStatus());


        deleteShipments(shipment1Id);
        deleteShipments(shipment2Id);

        deliveryService.deleteCustomer(shipment1.getCustomerId());
        deliveryService.deleteCustomer(shipment2.getCustomerId());

    }


    @Test
    public void testUpdateNonValidShipmentStatus() throws InputValidationException, InvalidUpdateStatusException, InstanceNotFoundException, CustomerWithShipmentsException {
        //Como creamos el paquete el estado es pending
        Shipment shipment1 = deliveryService.addShipment(getValidShipment());
        assertEquals(ShipmentStatus.PENDING, shipment1.getStatus());

        long shipment1Id = shipment1.getShipmentId();

        try {
            assertThrows(InvalidUpdateStatusException.class, () -> {
                deliveryService.updateShipment(shipment1Id, ShipmentStatus.DELIVERED);
            });
            assertThrows(InvalidUpdateStatusException.class, () -> {
                deliveryService.updateShipment(shipment1Id, ShipmentStatus.REJECTED);
            });

            deliveryService.updateShipment(shipment1Id, ShipmentStatus.SENT);

            assertThrows(InvalidUpdateStatusException.class, () -> {
                deliveryService.updateShipment(shipment1Id, ShipmentStatus.CANCELLED);
            });
            assertThrows(InvalidUpdateStatusException.class, () -> {
                deliveryService.updateShipment(shipment1Id, ShipmentStatus.PENDING);
            });
        }finally {
            deleteShipments(shipment1Id);
            deliveryService.deleteCustomer(shipment1.getCustomerId());
        }
    }

    @Test
    public void cancelShipmentTest() throws InstanceNotFoundException, InvalidUpdateStatusException, InputValidationException, CustomerWithShipmentsException {
        Shipment shipment = deliveryService.addShipment(getValidShipment());
        assertEquals(ShipmentStatus.PENDING, shipment.getStatus());

        long shipmentId = shipment.getShipmentId();

        deliveryService.updateShipment(shipmentId,ShipmentStatus.CANCELLED);
        assertEquals(ShipmentStatus.CANCELLED, shipment.getStatus());

        deleteShipments(shipmentId);
        deliveryService.deleteCustomer(shipment.getCustomerId());
    }

    @Test
    public void cancelInvalidShipmentTest() throws InputValidationException, InstanceNotFoundException, InvalidUpdateStatusException, CustomerWithShipmentsException {
        Shipment shipment = deliveryService.addShipment(getValidShipment());
        long shipmentId = shipment.getShipmentId();

        deliveryService.updateShipment(shipmentId, ShipmentStatus.SENT);

        assertThrows(InstanceNotFoundException.class, () -> deliveryService.cancelShipment(2L));
        assertThrows(InvalidUpdateStatusException.class, () -> deliveryService.cancelShipment(shipmentId));

        deleteShipments(shipmentId);
        deliveryService.deleteCustomer(shipment.getCustomerId());
    }

    @Test
    public void findShipmentsbyUserTest() throws InputValidationException, InstanceNotFoundException, CustomerWithShipmentsException, InvalidUpdateStatusException {
        Customer customer = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));

        Shipment shipment1 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 1L,"cale0"));
        Shipment shipment2 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 2L,"cale1"));
        Shipment shipment3 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 3L,"cale1"));
        Shipment shipment4 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 4L,"cale2"));
        Shipment shipment5 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 5L,"cale3"));
        deliveryService.updateShipment(shipment4.getShipmentId(),ShipmentStatus.SENT);
        deliveryService.updateShipment(shipment5.getShipmentId(),ShipmentStatus.SENT);

        List<Shipment> shipments = new ArrayList<>();
        shipments.add(shipment1);
        shipments.add(shipment2);
        shipments.add(shipment3);

        List<Shipment> shipments2 = new ArrayList<>();
        shipments2.add(shipment4);
        shipments2.add(shipment5);

        // Mirar los puntos clave: extremos izquierdo, derecho y centro
        assertEquals(shipments, deliveryService.findShipments(shipment1.getCustomerId(),ShipmentStatus.PENDING,0,3));
        assertEquals(shipments2, deliveryService.findShipments(shipment1.getCustomerId(),ShipmentStatus.SENT,0,2));
        assertTrue(deliveryService.findShipments(shipment1.getCustomerId(), ShipmentStatus.CANCELLED,0,2).isEmpty());

        assertThrows(InstanceNotFoundException.class, () -> {
            deliveryService.findShipments(shipment1.getCustomerId(), ShipmentStatus.PENDING,-1,1);
        });
        assertThrows(InstanceNotFoundException.class, () -> {
            deliveryService.findShipments(shipment1.getCustomerId(), ShipmentStatus.PENDING,0,-1);
        });

        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment1);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment2);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment3);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment4);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment5);
        deliveryService.deleteCustomer(customer.getCustomerId());
    }

    @Test
    public void findShipmentsWithoutStatusTest() throws InputValidationException, InstanceNotFoundException, InvalidUpdateStatusException, CustomerWithShipmentsException {
        Customer customer = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));

        Shipment shipment1 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 1L,"cale0"));
        Shipment shipment2 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 2L,"cale1"));
        Shipment shipment3 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 3L,"cale1"));
        Shipment shipment4 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 4L,"cale2"));
        Shipment shipment5 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 5L,"cale3"));

        deliveryService.updateShipment(shipment5.getShipmentId(),ShipmentStatus.SENT);

        List<Shipment> shipments = new ArrayList<>();
        shipments.add(shipment1);
        shipments.add(shipment2);
        List<Shipment> shipments2 = new ArrayList<>();
        shipments2.add(shipment4);
        shipments2.add(shipment5);
        //DEVUELVE TODOS LOS SHIPMENTS A PARTIR DEL ÍNDICE ORDENADOS, DA IGUAL EL STATUS
        assertEquals(shipments, deliveryService.findShipments(shipment1.getCustomerId(),null,0,2));
        assertEquals(shipments2, deliveryService.findShipments(shipment1.getCustomerId(),null,3,2));


        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment1);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment2);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment3);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment4);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment5);
        deliveryService.deleteCustomer(customer.getCustomerId());
    }

    @Test
    public void findAllShipmentsWithStatusTest() throws InputValidationException, InstanceNotFoundException, InvalidUpdateStatusException, CustomerWithShipmentsException {
        Customer customer = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));

        Shipment shipment1 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 1L,"cale0"));
        Shipment shipment2 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 2L,"cale1"));
        Shipment shipment3 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 3L,"cale1"));
        Shipment shipment4 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 4L,"cale2"));
        Shipment shipment5 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 5L,"cale3"));


        List<Shipment> shipments = new ArrayList<>();
        shipments.add(shipment1);
        shipments.add(shipment2);
        shipments.add(shipment3);
        shipments.add(shipment4);
        shipments.add(shipment5);
        List<Shipment> shipments2 = new ArrayList<>();


        //TODOS ELEMENTOS CON EL STATUS INDICADO
        assertEquals(shipments, deliveryService.findShipments(shipment1.getCustomerId(),ShipmentStatus.PENDING,0,0));
        assertEquals(shipments, deliveryService.findShipments(shipment1.getCustomerId(),ShipmentStatus.PENDING,3,0));
        assertEquals(shipments2, deliveryService.findShipments(shipment1.getCustomerId(),ShipmentStatus.SENT,0,0));


        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment1);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment2);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment3);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment4);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment5);
        deliveryService.deleteCustomer(customer.getCustomerId());
    }

    @Test
    public void findAllShipmentsTest() throws InstanceNotFoundException, InputValidationException, InvalidUpdateStatusException, CustomerWithShipmentsException {
        Customer customer = deliveryService.addCustomer(getValidCustomer(VALID_CUSTOMER_NAME));

        Shipment shipment1 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 1L,"cale0"));
        Shipment shipment2 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 2L,"cale1"));
        Shipment shipment3 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 3L,"cale1"));
        Shipment shipment4 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 4L,"cale2"));
        Shipment shipment5 = deliveryService.addShipment(new Shipment(customer.getCustomerId(), 5L,"cale3"));

        deliveryService.updateShipment(shipment4.getShipmentId(),ShipmentStatus.SENT);
        deliveryService.updateShipment(shipment5.getShipmentId(),ShipmentStatus.SENT);

        List<Shipment> shipments = new ArrayList<>();
        shipments.add(shipment1);
        shipments.add(shipment2);
        shipments.add(shipment3);
        shipments.add(shipment4);
        shipments.add(shipment5);

        //SIEMPRE DEVUELVE TODOS LOS SHIPMENTS
        assertEquals(shipments, deliveryService.findShipments(shipment1.getCustomerId(),null,0,0));
        assertEquals(shipments, deliveryService.findShipments(shipment1.getCustomerId(),null,3,0));


        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment1);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment2);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment3);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment4);
        ((MockDeliveryService)deliveryService).removeShipment(customer.getCustomerId(), shipment5);
        deliveryService.deleteCustomer(customer.getCustomerId());
    }
}
