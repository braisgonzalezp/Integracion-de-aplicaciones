package es.udc.rs.deliveries.client.service.rest;

import es.udc.rs.deliveries.client.service.ClientDeliveryService;
import es.udc.rs.deliveries.client.service.dto.ClientCustomerDto;
import es.udc.rs.deliveries.client.service.dto.ClientShipmentDto;
import es.udc.rs.deliveries.client.service.dto.ClientShipmentStatusDto;
import es.udc.rs.deliveries.client.service.exceptions.ClientCustomerWithShipmentsException;
import es.udc.rs.deliveries.client.service.exceptions.ClientInvalidUpdateStatusException;
import es.udc.rs.deliveries.client.service.rest.dto.*;
import es.udc.rs.deliveries.client.service.rest.json.JaxbJsonContextResolver;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.management.InstanceNotFoundException;
import java.util.List;

public abstract class RestClientDeliveryService implements ClientDeliveryService {

	private static jakarta.ws.rs.client.Client client = null;

	private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientDeliveryService.endpointAddress";
	private WebTarget endPointWebTarget = null;

	/*
	 * Client instances are expensive resources. It is recommended a configured
	 * instance is reused for the creation of Web resources. The creation of Web
	 * resources, the building of requests and receiving of responses are
	 * guaranteed to be thread safe. Thus a Client instance and WebTarget
	 * instances may be shared between multiple threads.
	 */
	private static Client getClient() {
		if (client == null) {
			client = ClientBuilder.newClient();
			client.register(JacksonFeature.class);
			client.register(JaxbJsonContextResolver.class);
		}
		return client;
	}

	private WebTarget getEndpointWebTarget() {
		if (endPointWebTarget == null) {
			endPointWebTarget = getClient()
					.target(ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER));
		}
		return endPointWebTarget;
	}
	
	protected abstract MediaType getMediaType();

	@Override
	public ClientCustomerDto addCustomer(ClientCustomerDto customerDto) throws InputValidationException {

		WebTarget wt = getEndpointWebTarget().path("customers");
		try (Response response = wt.request()
				.accept(this.getMediaType())
				.post(Entity.entity(
						CustomerDtoToCustomerDtoJaxbConversor.toJaxbCustomer(customerDto),
						this.getMediaType())
				)
		) {
			validateResponse(Response.Status.CREATED.getStatusCode(), response);
			CustomerType customer = response.readEntity(CustomerType.class);
			return CustomerDtoToCustomerDtoJaxbConversor.toClientCustomerDto(customer);
		} catch (InputValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

	@Override
	public void deleteCustomer(Long customerId) throws InstanceNotFoundException, ClientCustomerWithShipmentsException {

		WebTarget wt = getEndpointWebTarget().path("customers/{id}").resolveTemplate("id", customerId);
		try (Response response = wt.request()
				.accept(this.getMediaType())
				.delete()
		){
			validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
		} catch (InstanceNotFoundException | ClientCustomerWithShipmentsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//	@Override
//	public void updateCustomer(ClientCustomerDto customerDto) throws InputValidationException, InstanceNotFoundException {
//
//		WebTarget wt = getEndpointWebTarget().path("movies/{id}").resolveTemplate("id", customerDto.getCustomerId());
//		try (Response response = wt.request()
//				.accept(this.getMediaType())
//				.put(Entity.entity(
//						CustomerDtoToCustomerDtoJaxbConversor.toJaxbCustomer(customerDto), this.getMediaType())
//				)
//		){
//			validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
//		} catch (InputValidationException | InstanceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

//	@Override
//	public ClientCustomerDto findCustomer(Long customerId) throws InstanceNotFoundException {
//		WebTarget wt = getEndpointWebTarget().path("customers/{id}").resolveTemplate("id", customerId);
//		try (Response response = wt.request()
//				.accept(this.getMediaType())
//				.get()
//		){
//			validateResponse(Response.Status.OK.getStatusCode(), response);
//			CustomerType customer = response.readEntity(CustomerType.class);
//			return CustomerDtoToCustomerDtoJaxbConversor.toClientCustomerDto(customer);
//		} catch (InstanceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

//	@Override
//	public List<ClientCustomerDto> findCustomers(String keywords) throws InputValidationException {
//		WebTarget wt = getEndpointWebTarget().path("customers").queryParam("keywords", keywords);
//		try (Response response = wt.request().accept(this.getMediaType()).get()) {
//			validateResponse(Response.Status.OK.getStatusCode(), response);
//			List<CustomerType> customers = response.readEntity(new GenericType<List<CustomerType>>() {});
//			return CustomerDtoToCustomerDtoJaxbConversor.toClientCustomerDtos(customers);
//		} catch (InputValidationException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

//	@Override
//	public ClientShipmentDto addShipment(ClientShipmentDto shipment) throws InputValidationException, InstanceNotFoundException {
//		WebTarget wt = getEndpointWebTarget().path("shipments");
//		try (Response response = wt.request()
//				.accept(this.getMediaType())
//				.post(Entity.entity(
//						ShipmentDtoToShipmentDtoJaxbConversor.toJaxbShipment(shipment),
//						this.getMediaType())
//				)
//		) {
//			validateResponse(Response.Status.CREATED.getStatusCode(), response);
//			ShipmentType resultShipment = response.readEntity(ShipmentType.class);
//			return ShipmentDtoToShipmentDtoJaxbConversor.toClientShipmentDto(resultShipment);
//		} catch (InputValidationException | InstanceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	@Override
	public void updateShipment(Long shipmentId, ClientShipmentStatusDto status)
			throws ClientInvalidUpdateStatusException, InstanceNotFoundException {
		WebTarget wt = getEndpointWebTarget().path("shipments/{id}/updateShipment").resolveTemplate("id", shipmentId);
		Form form = new Form();
		form.param("status", status.toString());

		try (Response response = wt.request()
				.accept(this.getMediaType())
				.post(Entity.form(form))
		){
			validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
		} catch (ClientInvalidUpdateStatusException | InstanceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

//	@Override
//	public void cancelShipment(ClientShipmentDto shipment) throws InstanceNotFoundException, ClientInvalidUpdateStatusException {
//		WebTarget wt = getEndpointWebTarget().path("shipments/{id}").resolveTemplate("id", shipment.getShipmentId());
//		try (Response response = wt.request()
//				.accept(this.getMediaType())
//				.put(Entity.entity(
//						ShipmentDtoToShipmentDtoJaxbConversor.toJaxbShipment(shipment), this.getMediaType()
//				))
//		){
//			validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
//		} catch (InstanceNotFoundException | ClientInvalidUpdateStatusException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

//	@Override
//	public ClientShipmentDto findShipment(Long shipmentId)throws InstanceNotFoundException {
//		WebTarget wt = getEndpointWebTarget().path("shipments/{id}").resolveTemplate("id", shipmentId);
//		try (Response response = wt.request()
//				.accept(this.getMediaType())
//				.get()
//		){
//			validateResponse(Response.Status.OK.getStatusCode(), response);
//			ShipmentType shipment = response.readEntity(ShipmentType.class);
//			return ShipmentDtoToShipmentDtoJaxbConversor.toClientShipmentDto(shipment);
//		} catch (InstanceNotFoundException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	@Override
	public List<ClientShipmentDto> findShipments(Long customerId, ClientShipmentStatusDto status, int index, int elements)
			throws InstanceNotFoundException, InputValidationException {
		WebTarget wt = getEndpointWebTarget().path("shipments")
				.queryParam("customerId", customerId)
				.queryParam("status", status)
				.queryParam("index", index)
				.queryParam("elements", elements);

		try (Response response = wt.request().accept(this.getMediaType()).get()) {
			validateResponse(Response.Status.OK.getStatusCode(), response);
			List<ShipmentType> shipments = response.readEntity(new GenericType<List<ShipmentType>>() {});
			return ShipmentDtoToShipmentDtoJaxbConversor.toClientShipmentDtos(shipments);
		} catch (InstanceNotFoundException | InputValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void validateResponse(int expectedStatusCode, Response response)
			throws InstanceNotFoundException, ClientCustomerWithShipmentsException, InputValidationException,
			ClientInvalidUpdateStatusException {

		Response.Status statusCode = Response.Status.fromStatusCode(response.getStatus());

		if (statusCode.getStatusCode() == expectedStatusCode) {
			return;
		}

		String contentType = response.getMediaType() != null ? response.getMediaType().toString() : null;
		boolean expectedContentType = this.getMediaType().toString().equalsIgnoreCase(contentType);

		if (!expectedContentType && (statusCode != Response.Status.NO_CONTENT)) {
			throw new RuntimeException("HTTP error; status code = " + statusCode);
		}

		switch (statusCode) {
			case NOT_FOUND: {
				InstanceNotFoundExceptionType exDto = response.readEntity(InstanceNotFoundExceptionType.class);
				throw JaxbExceptionConversor.toInstanceNotFoundException(exDto);
			}
			case BAD_REQUEST: {
				InputValidationExceptionType exDto = response.readEntity(InputValidationExceptionType.class);
				throw JaxbExceptionConversor.toInputValidationException(exDto);
			}
			case GONE: {
				CustomerWithShipmentsExceptionType exDto = response.readEntity(CustomerWithShipmentsExceptionType.class);
				throw JaxbExceptionConversor.toCustomerWithShipmentsExceptionType(exDto);
			}
			case FORBIDDEN: {
				InvalidUpdateStatusExceptionType exDto = response.readEntity(InvalidUpdateStatusExceptionType.class);
				throw JaxbExceptionConversor.toInvalidUpdateStatusException(exDto);
			}
			default:
				throw new RuntimeException("HTTP error; status code = " + statusCode);
		}
	}
}
