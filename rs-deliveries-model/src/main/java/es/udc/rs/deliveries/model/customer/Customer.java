package es.udc.rs.deliveries.model.customer;

import java.time.LocalDateTime;

public class Customer {

	private Long customerId;
	private String name;
	private String Cif;
	private String address;
	private LocalDateTime creationDate;

	public Customer(String name, String cif, String address) {
		this.name = name;
		this.Cif = cif;
		this.address = address;
	}

	public Customer(Long customerId, String name, String cif, String address) {
		this(name, cif, address);
		this.customerId = customerId;
	}

	public Customer(Long customerId, String name, String cif, String address, LocalDateTime creationDate) {
		this(customerId, name, cif, address);
		this.creationDate = creationDate;
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
		return Cif;
	}

	public void setCif(String cif) {
		Cif = cif;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Customer customer)) return false;

		if (!getCustomerId().equals(customer.getCustomerId())) return false;
		if (!getName().equals(customer.getName())) return false;
		if (!getCif().equals(customer.getCif())) return false;
		if (!getAddress().equals(customer.getAddress())) return false;
        return getCreationDate().equals(customer.getCreationDate());
    }
}