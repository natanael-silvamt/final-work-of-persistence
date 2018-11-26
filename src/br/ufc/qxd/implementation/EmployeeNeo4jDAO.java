package br.ufc.qxd.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import br.ufc.qxd.connection.ConnectionNeo4j;
import br.ufc.qxd.dao.EmployeeDAO;
import br.ufc.qxd.entities.Employee;
import br.ufc.qxd.util.MyTransactionWork;

public class EmployeeNeo4jDAO implements EmployeeDAO {

	@Override
	public void insert(Employee employee) {
		String query = "create(e:employee) set e.name=$name, e.sex=$sex, e.birthday=$birthday,"
				+ "e.salary=$salary e.type=$type return id(e)";
		Map<String, Object> map = new HashMap<>();
		map.put("name", employee.getName());
		map.put("sex", employee.getSex());
		map.put("birthday", employee.getBirthday());
		map.put("salary", employee.getSalary());
		map.put("type", employee.getType());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			employee.setEmployeeId(id);
		}
	}

	@Override
	public boolean remove(long id) {
		String query = "match(e:employee) where id(e)=" + String.valueOf(id) + " delete e";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
			return true;			
		}
	}

	@Override
	public boolean update(long idOldEmployee, Employee newEmployee) {
		String query = "match(e:employee) where id(e)=" + idOldEmployee + 
				" set e.name=$name, e.sex=$sex, e.birthday=$birthday, e.salary = salary return id(e)";
		Map<String, Object> map = new HashMap<>();
		map.put("name", newEmployee.getName());
		map.put("sex", newEmployee.getSex());
		map.put("birthday", newEmployee.getBirthday());
		map.put("salary", newEmployee.getSalary());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			newEmployee.setEmployeeId(id);
			return true;
		}
	}

	@Override
	public List<Employee> findAll() {
		List<Employee> employees = new ArrayList<>();
		String query = "match(n:employee) return n, id(n)";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				//Record record = sr.next();

			}
		}
		return employees;
	}

	@Override
	public Employee findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
