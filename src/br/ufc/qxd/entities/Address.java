package br.ufc.qxd.entities;

public class Address {
	private long id_address;
	private String street;
	private int number;
	private int cep;
	private String neighborhood;
	
	public Address(long id_address, String street, int number, int cep, String neighborhood) {
		this.id_address = id_address;
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

	public long getId_address() {
		return id_address;
	}
	public void setId_address(long id_address) {
		this.id_address = id_address;
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
		return "Address [id_address=" + id_address + ", street=" + street + ", number=" + number + ", cep=" + cep
				+ ", neighborhood=" + neighborhood + "]";
	}	
}
