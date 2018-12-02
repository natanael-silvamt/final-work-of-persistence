package br.ufc.qxd.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import br.ufc.qxd.connection.ConnectionNeo4j;
import br.ufc.qxd.dao.DepartmentDAO;
import br.ufc.qxd.entities.Department;
import br.ufc.qxd.util.MyTransactionWork;

public class DepartmentNeo4jDAO implements DepartmentDAO{

	@Override
	public void insert(Department department) {
		String query = "create(d:department) set d.name=$name, d.number=$number return id(d)";
		Map<String, Object> map = new HashMap<>();
		map.put("name", department.getName());
		map.put("number", department.getNumber());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			department.setDepartmentId(id);
		}
	}

	@Override
	public boolean remove(long id) {
		String query = "MATCH(d:department) WHERE id(d)=" + String.valueOf(id) + " DETACH DELETE d";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
			return true;			
		}
	}

	@Override
	public boolean update(long idOldDepartment, Department newDepartment) {
		String query = "match(d:department) where id(d)=" + idOldDepartment + " set d.name=$name, d.number=$number return id(d)";
		Map<String, Object> map = new HashMap<>();
		map.put("name", newDepartment.getName());
		map.put("number", newDepartment.getNumber());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			newDepartment.setDepartmentId(id);
			return true;
		}
	}

	@Override
	public List<Department> findAll() {
		List<Department> departments = new ArrayList<>();
		String query = "match(n:department) return n, id(n)";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record record = sr.next();
				Department depart = new Department();
				if(!record.get("n").get("name").isNull())
					depart.setName(record.get("n").get("name").asString());
				if(!record.get("n").get("number").isNull())
					depart.setNumber(record.get("n").get("number").asInt());
				if(!record.get("id(n)").isNull())
					depart.setDepartmentId(record.get("id(n)").asLong());				
				departments.add(depart);
			}
		}
		return departments;
	}

	@Override
	public Department findById(long id) {
		String query = "match(d:department) where id(d)=" + id + " return d, id(d)";
		Department department = new Department();
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record rec = sr.next();
				if(!rec.get("d").get("name").isNull())
					department.setName(rec.get("d").get("name").asString());
				if(!rec.get("d").get("number").isNull())
					department.setNumber(rec.get("d").get("number").asInt());
				if(!rec.get("id(d)").isNull())
					department.setDepartmentId(rec.get("id(d)").asLong());
			}
		}
		return department;
	}

}
