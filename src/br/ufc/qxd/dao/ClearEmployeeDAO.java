package br.ufc.qxd.dao;

import java.util.List;

import br.ufc.qxd.entities.ClearEmployee;
import br.ufc.qxd.entities.Dependent;

public interface ClearEmployeeDAO {
	
	public void relationshipToClearEmployee(long id_supervisor, long id_clear_employee);
	public void insert(ClearEmployee clearEmployee);
	public boolean remove(long id);
	public boolean update(long idOldClearEmployee, ClearEmployee newClearEmployee);
	public List<ClearEmployee> findAll();
	public ClearEmployee findById(long id);
	public boolean relationshipToProject(long idEmployee, long idProject);
	public boolean relationshipToDepartment(long idEmployee, long idDepartment);
	public List<Dependent> findAllDependets(long idEmployee);
}
