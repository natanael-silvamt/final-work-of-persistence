package br.ufc.qxd.dao;

import java.util.List;

import br.ufc.qxd.entities.Dependent;

public interface DependentDAO {

	public boolean relationshipToEmployee(long employeeId, long dependentId);
	public void insert(Dependent dependent);
	public boolean remove(long id);
	public boolean removeRelationshipToEmployee(long employeeId);
	public boolean update(long idOldDependent, Dependent newDependent);
	public List<Dependent> findAll();
	public Dependent findById(long id);
	
}
