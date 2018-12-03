package br.ufc.qxd.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import br.ufc.qxd.connection.ConnectionNeo4j;
import br.ufc.qxd.dao.ClearEmployeeDAO;
import br.ufc.qxd.entities.ClearEmployee;
import br.ufc.qxd.entities.Dependent;
import br.ufc.qxd.util.MyTransactionWork;

public class ClearEmployeeNeo4jDAO implements ClearEmployeeDAO {

	@Override
	public void relationshipToClearEmployee(long id_supervisor, long id_clear_employee) {
		String query = "MATCH (c:clearEmployee), (e:clearEmployee) WHERE id(c)=" + id_supervisor + 
				" AND id(e)=" + id_clear_employee + " CREATE (c)-[s:supervises]->(e) RETURN s";
		transaction(query);
	}

	@Override
	public void insert(ClearEmployee clearEmployee) {
		String query = "create(c:clearEmployee) set c.name=$name, c.sex=$sex, c.birthday=$birthday, "
				+ "c.salary=$salary, c.working_days=$working_days return id(c)";
		Map<String, Object> map = new HashMap<>();
		map.put("name",  clearEmployee.getName());
		map.put("sex", clearEmployee.getSex());
		map.put("birthday", clearEmployee.getBirthday());
		map.put("salary", clearEmployee.getSalary());
		map.put("working_days", clearEmployee.getWorkingDays());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			clearEmployee.setEmployeeId(id);
		}
	}

	@Override
	public boolean remove(long id) {
		String query = "MATCH(c:clearEmployee) WHERE id(c)=" + String.valueOf(id) + " DETACH DELETE c";
		transaction(query);
		return true;
	}

	@Override
	public boolean update(long idOldClearEmployee, ClearEmployee newClearEmployee) {
		String query = "match(c:clearEmployee) where id(c)=" + idOldClearEmployee + 
				" set c.name=$name, c.sex=$sex, c.birthday=$birthday, c.salary=$salary, "
				+ "c.working_days=$working_days return id(c)";
		Map<String, Object> map = new HashMap<>();
		map.put("name",  newClearEmployee.getName());
		map.put("sex", newClearEmployee.getSex());
		map.put("birthday", newClearEmployee.getBirthday());
		map.put("salary", newClearEmployee.getSalary());
		map.put("working_days", newClearEmployee.getWorkingDays());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			newClearEmployee.setEmployeeId(id);
			return true;
		}
	}

	@Override
	public List<ClearEmployee> findAll() {
		List<ClearEmployee> clearEMployees = new ArrayList<>();
		String query = "match(c:clearEmployee) return c, id(c)";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record record = sr.next();
				ClearEmployee clearEmployee = new ClearEmployee();
				if(!record.get("c").get("name").isNull())
					clearEmployee.setName(record.get("c").get("name").asString());
				if(!record.get("c").get("sex").isNull())
					clearEmployee.setSex(record.get("c").get("sex").asString());
				if(!record.get("c").get("birthday").isNull())
					clearEmployee.setBirthday(record.get("c").get("birthday").asString());
				if(!record.get("c").get("salary").isNull())
					clearEmployee.setSalary(record.get("c").get("salary").asDouble());
				if(!record.get("c").get("working_days").isNull())
					clearEmployee.setWorkingDays(record.get("c").get("working_days").asString());
				if(!record.get("id(c)").isNull())
					clearEmployee.setEmployeeId(record.get("id(c)").asLong());				
				clearEMployees.add(clearEmployee);
			}
		}
		return clearEMployees;
	}

	@Override
	public ClearEmployee findById(long id) {
		String query = "match(c:clearEmployee) where id(c)=" + id + " return c, id(c)";
		ClearEmployee clearEmployee = new ClearEmployee();
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record record = sr.next();
				if(!record.get("c").get("name").isNull())
					clearEmployee.setName(record.get("c").get("name").asString());
				if(!record.get("c").get("sex").isNull())
					clearEmployee.setSex(record.get("c").get("sex").asString());
				if(!record.get("c").get("birthday").isNull())
					clearEmployee.setBirthday(record.get("c").get("birthday").asString());
				if(!record.get("c").get("salary").isNull())
					clearEmployee.setSalary(record.get("c").get("salary").asDouble());
				if(!record.get("c").get("working_days").isNull())
					clearEmployee.setWorkingDays(record.get("c").get("working_days").asString());
				if(!record.get("id(c)").isNull())
					clearEmployee.setEmployeeId(record.get("id(c)").asLong());
			}
		}
		return clearEmployee;
	}
	
	private void transaction(String query) {
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
		}
	}

	@Override
	public boolean relationshipToProject(long idEmployee, long idProject) {
		String query = "MATCH (c:clearEmployee), (p:project) WHERE id(c)=" + idEmployee +
				" AND id(p)=" + idProject + " CREATE (c)-[w:work]->(p) RETURN w";
		transaction(query);
		return true;
	}

	@Override
	public boolean relationshipToDepartment(long idEmployee, long idDepartment) {
		String query = "MATCH (c:clearEmployee), (d:department) WHERE id(c)=" + idEmployee +
				" AND id(d)=" + idDepartment + " CREATE (c)-[a:associated_with]->(d) RETURN a";
		transaction(query);
		return true;
	}

	@Override
	public List<Dependent> findAllDependets(long idEmployee) {
		List<Dependent> dependents = new ArrayList<>();
		String query = "MATCH (clearEmployee)-[:dependent]->(d:dependent) WHERE id(clearEmployee)=" + idEmployee + " RETURN d, id(d)";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult rs = session.run(query);
			while(rs.hasNext()) {
				Record rec = rs.next();
				Dependent dep = new Dependent();
				if(!rec.get("d").get("name").isNull())
					dep.setName(rec.get("d").get("name").asString());
				if(!rec.get("d").get("sex").isNull())
					dep.setSex(rec.get("d").get("sex").asString());
				if(!rec.get("d").get("birthday").isNull())
					dep.setBirthday(rec.get("d").get("birthday").asString());
				if(!rec.get("d").get("degree_of_kinship").isNull())
					dep.setDegreeOfKinship(rec.get("d").get("degree_of_kinship").asString());
				if(!rec.get("id(d)").isNull())
					dep.setDependentId(rec.get("id(d)").asLong());
				dependents.add(dep);
			}
		}
		return dependents;
	}
}
