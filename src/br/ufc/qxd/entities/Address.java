package br.ufc.qxd.entities;

public class Address {
	private long addressId;
	private String street;
	private int number;
	private int cep;
	private String neighborhood;
	
	public Address(long addressId, String street, int number, int cep, String neighborhood) {
		this.addressId = addressId;
		this.street = street;
		this.number = number;
		this.cep = cep;
		this.neighborhood = neighborhood;
	}
	
	public Address(String street, int number, int cep, String neighborhood) {
		this.street = street;
		this.number = number;
		this.cep = cep;
		this.neighborhood = neighborhood;
	}

	public Address() {}

	public long getAddressId() {
		return addressId;
	}
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getCep() {
		return cep;
	}
	public void setCep(int cep) {
		this.cep = cep;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", street=" + street + ", number=" + number + ", cep=" + cep
				+ ", neighborhood=" + neighborhood + "]";
	}	
}
