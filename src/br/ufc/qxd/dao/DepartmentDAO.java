package br.ufc.qxd.dao;

import java.util.List;

import br.ufc.qxd.entities.Department;

public interface DepartmentDAO {
	public void insert(Department department);
	public boolean remove(long id);
	public boolean update(long idOldDepartment, Department newDepartment);
	public List<Department> findAll();
	public Department findById(long id);
}
