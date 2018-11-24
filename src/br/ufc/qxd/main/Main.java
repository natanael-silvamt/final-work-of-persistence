package br.ufc.qxd.main;

import java.util.List;

import br.ufc.qxd.connection.MyLock;
import br.ufc.qxd.dao.DepartmentDAO;
import br.ufc.qxd.entities.Department;
import br.ufc.qxd.implementation.DepartmentNeo4jDAO;

public class Main {
	public static MyLock myLock;
	private static DepartmentDAO departmentDAO = new DepartmentNeo4jDAO();

	public static void main(String[] args) {
		Department depart = new Department("Ciênciass", 03);
	//	departmentDAO.insert(depart);
	//	System.out.println(depart);
	//	System.out.println("----------------------");
		
	//	departmentDAO.remove(21);
	//	departmentDAO.update(22, depart);
		
		System.out.println(departmentDAO.findById(22));
		
		List<Department> list = departmentDAO.findAll();
		for(Department d : list)
			System.out.println(d);
		
		System.exit(0);
	}

}
