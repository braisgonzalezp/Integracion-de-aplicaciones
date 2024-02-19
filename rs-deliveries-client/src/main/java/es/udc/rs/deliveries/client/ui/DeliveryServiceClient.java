package es.udc.rs.deliveries.client.ui;

import es.udc.rs.deliveries.client.service.ClientDeliveryService;
import es.udc.rs.deliveries.client.service.ClientDeliveryServiceFactory;
import es.udc.rs.deliveries.client.service.dto.ClientCustomerDto;
import es.udc.rs.deliveries.client.service.dto.ClientShipmentDto;
import es.udc.rs.deliveries.client.service.dto.ClientShipmentStatusDto;
import es.udc.rs.deliveries.client.service.exceptions.ClientCustomerWithShipmentsException;
import es.udc.rs.deliveries.client.service.exceptions.ClientInvalidUpdateStatusException;
import es.udc.ws.util.exceptions.InputValidationException;

import javax.management.InstanceNotFoundException;
import java.util.List;

public class DeliveryServiceClient {

	public static void main(String[] args) {

		if (args.length == 0) {
			printUsageAndExit();
		}
		ClientDeliveryService clientDeliveryService = ClientDeliveryServiceFactory.getService();
		if ("-addCustomer".equalsIgnoreCase(args[0])) {
			validateArgs(args, 4, new int[] {});

			// [-addCustomer]  DeliveryServiceClient -addCustomer <name> <cif> <address>

			try {
				ClientCustomerDto customer = clientDeliveryService.addCustomer(
						new ClientCustomerDto(args[1], args[2], args[3])
				);
				System.out.println("Customer " + args[1] + " " + "created sucessfully");
			}catch (InputValidationException ex){
				ex.printStackTrace(System.err);
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		} else if ("-deleteCustomer".equalsIgnoreCase(args[0])) {
			validateArgs(args, 2, new int[]{ 1 });

			//[-deleteCustomer]   DeliveryServiceClient -deleteCustomer <customerId>

			try {
				clientDeliveryService.deleteCustomer(Long.valueOf(args[1]));
				System.out.println("Customer with id: " + args[1] + " deleted sucessfully");

			}catch (InstanceNotFoundException | ClientCustomerWithShipmentsException ex){
				ex.printStackTrace(System.err);
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		} else if ("-updateShipment".equalsIgnoreCase(args[0])) {
			validateArgs(args, 3, new int[]{ 1 });

			// [-updateShipment] ClientDeliveryServiceClient -updateShipment <shipmentId> <Status>

			try {
				clientDeliveryService.updateShipment(Long.parseLong(args[1]),
						ClientShipmentStatusDto.valueOf(args[2]));

				System.out.println("Shipment with id " + args[1] + " updated sucessfully");

			} catch (IllegalArgumentException e) {
				System.out.println("Shipment Status " + args[2] + " not found");
			}catch (InstanceNotFoundException | ClientInvalidUpdateStatusException ex){
				ex.printStackTrace(System.err);
			}catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		} else if ("-findShipments".equalsIgnoreCase(args[0])) {
			validateArgs(args, 5, new int[]{ 1, 3, 4 });

			// [-findShipments] ClientDeliveryServiceClient -findShipments <customerId> <status> <index> <elements>

			try {
				ClientShipmentStatusDto status = ClientShipmentStatusDto.valueOf(args[2]);
				List<ClientShipmentDto> shipments = clientDeliveryService.findShipments(
						Long.parseLong(args[1]), status, Integer.parseInt(args[3]), Integer.parseInt(args[4]));;
				for (ClientShipmentDto shipmentsDto : shipments) {
					System.out.println("ShipmentId: " + shipmentsDto.getShipmentId() + ", CustomerId: " + shipmentsDto.getCustomerId()
							+ ", PackageReference: " + shipmentsDto.getPackageReference() + ", Address: " + shipmentsDto.getAddress()
							+ ", Status: " + shipmentsDto.getStatus() + ", CreationDate: " + shipmentsDto.getCreationDate()
							+ ", EstimatesHours" + shipmentsDto.getEstimatedHours() + ", DeliveryDate: " + shipmentsDto.getDeliveryDate())
						/*	+ ", Links" + shipmentsDto.getLinks()*/;
				}
			}catch (InstanceNotFoundException | InputValidationException ex){
				ex.printStackTrace(System.err);
			}catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		}

	}

	public static void validateArgs(String[] args, int expectedArgs, int[] numericArguments) {
		if (expectedArgs != args.length) {
			printUsageAndExit();
		}
		for (int i = 0; i < numericArguments.length; i++) {
			int position = numericArguments[i];
			try {
				Double.parseDouble(args[position]);
			} catch (NumberFormatException n) {
				printUsageAndExit();
			}
		}
	}

	public static void printUsageAndExit() {
		printUsage();
		System.exit(-1);
	}

	public static void printUsage() {
		System.err.println(
				"Usage:\n" + "    [-addCustomer]    DeliveryServiceClient -addCustomer <name> <cif> <address> \n" +
		                     "    [-deleteCustomer]   DeliveryServiceClient -deleteCustomer <customerId>\n" +
							 "    [-updateShipment]   DeliveryServiceClient -updateShipment <shipmentId> <status>\n"+
							 "    [-findShipments]   DeliveryServiceClient -findShipments <customerId> <status> <index> <elements>\n"
		);
	}

}
