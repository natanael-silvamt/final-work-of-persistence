package br.ufc.qxd.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import br.ufc.qxd.connection.ConnectionNeo4j;
import br.ufc.qxd.dao.SecretaryDAO;
import br.ufc.qxd.entities.Dependent;
import br.ufc.qxd.entities.Secretary;
import br.ufc.qxd.util.MyTransactionWork;

public class SecretaryNeo4jDAO implements SecretaryDAO {

	@Override
	public void insert(Secretary secretary) {
		String query = "create(s:secretary) set s.name=$name, s.sex=$sex, s.birthday=$birthday, "
				+ "s.salary=$salary, s.degree_of_schooling=$degree_of_schooling return id(s)";
		Map<String, Object> map = new HashMap<>();
		map.put("name",  secretary.getName());
		map.put("sex", secretary.getSex());
		map.put("birthday", secretary.getBirthday());
		map.put("salary", secretary.getSalary());
		map.put("degree_of_schooling", secretary.getDegreeOfSchooling());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			secretary.setEmployeeId(id);
		}
	}

	@Override
	public boolean remove(long id) {
		String query = "MATCH(s:secretary) WHERE id(s)=" + String.valueOf(id) + " DETACH DELETE s";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
			return true;			
		}
	}

	@Override
	public boolean update(long idOldSecretary, Secretary newSecretary) {
		String query = "match(s:secretary) where id(s)=" + idOldSecretary + 
				" set s.name=$name, s.sex=$sex, s.birthday=$birthday, s.salary=$salary, "
				+ "s.degree_of_schooling=$degree_of_schooling return id(s)";
		Map<String, Object> map = new HashMap<>();
		map.put("name",  newSecretary.getName());
		map.put("sex", newSecretary.getSex());
		map.put("birthday", newSecretary.getBirthday());
		map.put("salary", newSecretary.getSalary());
		map.put("degree_of_schooling", newSecretary.getDegreeOfSchooling());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			newSecretary.setEmployeeId(id);
			return true;
		}
	}

	@Override
	public List<Secretary> findAll() {
		List<Secretary> secretaries = new ArrayList<>();
		String query = "match(s:secretary) return s, id(s)";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record record = sr.next();
				Secretary secretary = new Secretary();
				if(!record.get("s").get("name").isNull())
					secretary.setName(record.get("s").get("name").asString());
				if(!record.get("s").get("sex").isNull())
					secretary.setSex(record.get("s").get("sex").asString());
				if(!record.get("s").get("birthday").isNull())
					secretary.setBirthday(record.get("s").get("birthday").asString());
				if(!record.get("s").get("salary").isNull())
					secretary.setSalary(record.get("s").get("salary").asDouble());
				if(!record.get("s").get("degree_of_schooling").isNull())
					secretary.setBirthday(record.get("s").get("degree_of_schooling").asString());
				if(!record.get("id(s)").isNull())
					secretary.setEmployeeId(record.get("id(s)").asLong());				
				secretaries.add(secretary);
			}
		}
		return secretaries;
	}

	@Override
	public Secretary findById(long id) {
		String query = "match(s:secretary) where id(s)=" + id + " return s, id(s)";
		Secretary secretary = new Secretary();
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record record = sr.next();
				if(!record.get("s").get("name").isNull()) {
					secretary.setName(record.get("s").get("name").asString());
				}
				else {
					System.out.println("sim ok");
				}
				if(!record.get("s").get("sex").isNull())
					secretary.setSex(record.get("s").get("sex").asString());
				if(!record.get("s").get("birthday").isNull())
					secretary.setBirthday(record.get("s").get("birthday").asString());
				if(!record.get("s").get("salary").isNull())
					secretary.setSalary(record.get("s").get("salary").asDouble());
				if(!record.get("s").get("degree_of_schooling").isNull())
					secretary.setDegreeOfSchooling(record.get("s").get("degree_of_schooling").asString());
				if(!record.get("id(s)").isNull())
					secretary.setEmployeeId(record.get("id(s)").asLong());
			}
		}
		return secretary;
	}

	@Override
	public boolean relationshipToProject(long idEmployee, long idProject) {
		String query = "MATCH (s:secretary), (p:project) WHERE id(s)=" + idEmployee +
				" AND id(p)=" + idProject + " CREATE (s)-[w:work]->(p) RETURN w";
		transaction(query);
		return true;
	}
	
	private void transaction(String query) {
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
		}
	}

	@Override
	public boolean relationshipToDepartment(long idEmployee, long idDepartment) {
		String query = "MATCH (s:secretary), (d:department) WHERE id(s)=" + idEmployee +
				" AND id(d)=" + idDepartment + " CREATE (s)-[a:associated_with]->(d) RETURN a";
		transaction(query);
		return true;
	}

	@Override
	public List<Dependent> findAllDependets(long idEmployee) {
		List<Dependent> dependents = new ArrayList<>();
		String query = "MATCH (secretary)-[:dependent]->(d:dependent) WHERE id(secretary)=" + idEmployee + " RETURN d, id(d)";
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
