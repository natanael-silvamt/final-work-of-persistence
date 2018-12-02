package br.ufc.qxd.dao;

import br.ufc.qxd.entities.Address;

public interface AddressDAO {
	public void insert(Address address);
	public boolean remove(long idEmployee);
	public boolean update(long idEmployee, Address newAddress);
	public void relationshipToEmployee(long idAdress, long idEmployee);
}
