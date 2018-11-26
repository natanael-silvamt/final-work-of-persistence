package br.ufc.qxd.dao;

import java.util.List;

import br.ufc.qxd.entities.Employee;

public interface EmployeeDAO {
	
	public void insert(Employee employee);
	public boolean remove(long id);
	public boolean update(long idOldEmployee, Employee newEmployee);
	public List<Employee> findAll();
	public Employee findById(long id);
	
}
