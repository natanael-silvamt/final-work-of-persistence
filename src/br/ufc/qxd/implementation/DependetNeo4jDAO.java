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
import br.ufc.qxd.dao.DependentDAO;
import br.ufc.qxd.dao.ResearcherDAO;
import br.ufc.qxd.dao.SecretaryDAO;
import br.ufc.qxd.entities.Dependent;
import br.ufc.qxd.util.MyTransactionWork;

public class DependetNeo4jDAO implements DependentDAO {
	private SecretaryDAO secretaryDAO = new SecretaryNeo4jDAO();
	private ResearcherDAO researcherDAO = new ResearcherNeo4jDAO();
	private ClearEmployeeDAO clearEmployeeDAO = new ClearEmployeeNeo4jDAO();

	@Override
	public boolean relationshipToEmployee(long employeeId, long dependentId) {
		if(this.secretaryDAO.findById(employeeId).getName() != null) {
			String query = "MATCH (d:dependent), (s:secretary) WHERE id(s)=" + employeeId +
					" AND id(d)=" + dependentId + " CREATE (s)-[dep:dependent]->(d) RETURN dep";
			transaction(query);
			return true;
		}
		else if(this.researcherDAO.findById(employeeId).getName() != null) {
			String query = "OPTIONAL MATCH (d:dependent), (r:researcher) WHERE id(d)=" + dependentId +
					" AND id(r)=" + employeeId + " CREATE (r)-[dep:dependent]->(d) RETURN dep";
			transaction(query);
			return true;
		}
		else if(this.clearEmployeeDAO.findById(employeeId).getName() != null) {
			String query = "MATCH (d:dependent), (c:clearEmployee) WHERE id(d)=" + dependentId +
					" AND id(c)=" + employeeId + " CREATE (c)-[dep:dependent]->(d) RETURN dep";
			transaction(query);
			return true;
		}
		return false;
	}

	@Override
	public void insert(Dependent dependent) {
		String query = "create(d:dependent) set d.name=$name, d.sex=$sex, d.birthday=$birthday, "
				+ "d.degree_of_kinship=$degree_of_kinship return id(d)";
		Map<String, Object> map = new HashMap<>();
		map.put("name",  dependent.getName());
		map.put("sex", dependent.getSex());
		map.put("birthday", dependent.getBirthday());
		map.put("degree_of_kinship", dependent.getDegreeOfKinship());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			dependent.setDependentId(id);
		}
	}

	@Override
	public boolean remove(long id) {
		String query = "MATCH(d:dependent) WHERE id(d)=" + String.valueOf(id) + " DETACH DELETE d";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
			return true;			
		}
	}

	@Override
	public boolean update(long idOldDependent, Dependent newDependent) {
		String query = "match(d:dependent) where id(d)=" + idOldDependent + 
				" set d.name=$name, d.sex=$sex, d.birthday=$birthday, d.degree_of_kinship=$degree_of_kinship, "
				+ "return id(r)";
		Map<String, Object> map = new HashMap<>();
		map.put("name",  newDependent.getName());
		map.put("sex", newDependent.getSex());
		map.put("birthday", newDependent.getBirthday());
		map.put("salary", newDependent.getDegreeOfKinship());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			newDependent.setDependentId(id);
			return true;
		}
	}

	@Override
	public List<Dependent> findAll() {
		List<Dependent> dependents = new ArrayList<>();
		String query = "match(d:dependent) return d, id(d)";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record record = sr.next();
				Dependent dependent = new Dependent();
				if(!record.get("d").get("name").isNull())
					dependent.setName(record.get("d").get("name").asString());
				if(!record.get("d").get("sex").isNull())
					dependent.setSex(record.get("d").get("sex").asString());
				if(!record.get("d").get("birthday").isNull())
					dependent.setBirthday(record.get("d").get("birthday").asString());
				if(!record.get("d").get("degree_of_kinship").isNull())
					dependent.setDegreeOfKinship(record.get("d").get("degree_of_kinship").asString());
				if(!record.get("id(d)").isNull())
					dependent.setDependentId(record.get("id(d)").asLong());				
				dependents.add(dependent);
			}
		}
		return dependents;
	}

	@Override
	public Dependent findById(long id) {
		String query = "match(d:dependent) where id(d)=" + id + " return d, id(d)";
		Dependent dependent = new Dependent();
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record record = sr.next();
				if(!record.get("d").get("name").isNull())
					dependent.setName(record.get("d").get("name").asString());
				if(!record.get("d").get("sex").isNull())
					dependent.setSex(record.get("d").get("sex").asString());
				if(!record.get("d").get("birthday").isNull())
					dependent.setBirthday(record.get("d").get("birthday").asString());
				if(!record.get("d").get("degree_of_kinship").isNull())
					dependent.setDegreeOfKinship(record.get("r").get("degree_of_kinship").asString());
				if(!record.get("id(r)").isNull())
					dependent.setDependentId(record.get("id(r)").asLong());
			}
		}
		return dependent;
	}

	private void transaction(String query) {
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
		}
	}

	@Override
	public boolean removeRelationshipToEmployee(long employeeId) {
		String query = "";
		if(this.secretaryDAO.findById(employeeId).getName() != null) {
			query = "MATCH (secretary)-[:dependent]->(dependent) WHERE id(secretary)=" + employeeId + " DETACH DELETE dependent"; 
			transaction(query);
			return true;
		}
		else if(this.researcherDAO.findById(employeeId).getName() != null) {
			query = "MATCH (researcher)-[:dependent]->(dependent) WHERE id(researcher)=" + employeeId + " DETACH DELETE dependent";
			transaction(query);
			return true;
		}
		else if(this.clearEmployeeDAO.findById(employeeId).getName() != null) {
			query = "MATCH (clearEmployee)-[:dependent]->(dependent) WHERE id(clearEmployee)=" + employeeId + " DETACH DELETE dependent";
			transaction(query);
			return true;
		}
		return false;
	}
}
