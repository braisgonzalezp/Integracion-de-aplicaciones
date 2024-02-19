package es.udc.rs.deliveries.model.shipment;

import java.time.LocalDateTime;

public class Shipment {

	private Long shipmentId;
	private Long customerId;
	private Long packageReference;
	private String address;
	private ShipmentStatus status;
	private LocalDateTime creationDate;
	private LocalDateTime maxDeliveryDate;
	private LocalDateTime deliveryDate;

	public Shipment(Long customerId, Long packageReference, String address) {
		this.customerId = customerId;
		this.packageReference = packageReference;
		this.address = address;
	}

	public Shipment(Long shipmentId, Long customerId, Long packageReference, String address,
					ShipmentStatus status, LocalDateTime creationDate, LocalDateTime maxDeliveryDate) {
		this(customerId, packageReference, address);
		this.shipmentId = shipmentId;
		this.status = status;
		this.creationDate = creationDate;
		this.maxDeliveryDate = maxDeliveryDate;
	}

	public Shipment(Long shipmentId, Long customerId, Long packageReference, String address,
					LocalDateTime creationDate, LocalDateTime maxDeliveryDate) {
		this(customerId, packageReference, address);
		this.shipmentId = shipmentId;
		this.status = ShipmentStatus.PENDING;
		this.creationDate = creationDate;
		this.maxDeliveryDate = maxDeliveryDate;
	}

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getPackageReference() {
		return packageReference;
	}

	public void setPackageReference(Long packageReference) {
		this.packageReference = packageReference;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ShipmentStatus getStatus() {
		return status;
	}

	public void setStatus(ShipmentStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getMaxDeliveryDate() {
		return maxDeliveryDate;
	}

	public void setMaxDeliveryDate(LocalDateTime maxDeliveryDate) {
		this.maxDeliveryDate = maxDeliveryDate;
	}

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
}