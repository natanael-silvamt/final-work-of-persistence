package br.ufc.qxd.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import br.ufc.qxd.connection.ConnectionNeo4j;
import br.ufc.qxd.dao.ResearcherDAO;
import br.ufc.qxd.entities.Dependent;
import br.ufc.qxd.entities.Researcher;
import br.ufc.qxd.util.MyTransactionWork;

public class ResearcherNeo4jDAO implements ResearcherDAO {
	
	@Override
	public void insert(Researcher researcher) {
		String query = "create(r:researcher) set r.name=$name, r.sex=$sex, r.birthday=$birthday, "
				+ "r.salary=$salary, r.occupation_area=$occupation_area return id(r)";
		Map<String, Object> map = new HashMap<>();
		map.put("name",  researcher.getName());
		map.put("sex", researcher.getSex());
		map.put("birthday", researcher.getBirthday());
		map.put("salary", researcher.getSalary());
		map.put("occupation_area", researcher.getOccupationArea());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			researcher.setEmployeeId(id);
		}
	}

	@Override
	public boolean remove(long id) {
		String query = "MATCH(r:researcher) WHERE id(r)=" + String.valueOf(id) + " DETACH DELETE r";
		transaction(query);
		return true;
	}

	@Override
	public boolean update(long idOldResearcher, Researcher newResearcher) {
		String query = "match(r:researcher) where id(r)=" + idOldResearcher + 
				" set r.name=$name, r.sex=$sex, r.birthday=$birthday, r.salary=$salary, "
				+ "r.occupation_area=$occupation_area return id(r)";
		Map<String, Object> map = new HashMap<>();
		map.put("name",  newResearcher.getName());
		map.put("sex", newResearcher.getSex());
		map.put("birthday", newResearcher.getBirthday());
		map.put("salary", newResearcher.getSalary());
		map.put("occupation_area", newResearcher.getOccupationArea());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			newResearcher.setEmployeeId(id); // RM: precisa setar um id num update?
			return true;
		}
	}

	@Override
	public List<Researcher> findAll() {
		List<Researcher> researchers = new ArrayList<>();
		String query = "match(r:researcher) return r, id(r)";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record record = sr.next();
				Researcher researcher = new Researcher();
				if(!record.get("r").get("name").isNull())
					researcher.setName(record.get("r").get("name").asString());
				if(!record.get("r").get("sex").isNull())
					researcher.setSex(record.get("r").get("sex").asString());
				if(!record.get("r").get("birthday").isNull())
					researcher.setBirthday(record.get("r").get("birthday").asString());
				if(!record.get("r").get("salary").isNull())
					researcher.setSalary(record.get("r").get("salary").asDouble());
				if(!record.get("r").get("occupation_area").isNull())
					researcher.setOccupationArea(record.get("r").get("occupation_area").asString());
				if(!record.get("id(r)").isNull())
					researcher.setEmployeeId(record.get("id(r)").asLong());				
				researchers.add(researcher);
			}
		}
		return researchers;
	}

	@Override
	public Researcher findById(long id) {
		String query = "match(r:researcher) where id(r)=" + id + " return r, id(r)";
		Researcher researcher = new Researcher();
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record record = sr.next();
				if(!record.get("r").get("name").isNull())
					researcher.setName(record.get("r").get("name").asString());
				if(!record.get("r").get("sex").isNull())
					researcher.setSex(record.get("r").get("sex").asString());
				if(!record.get("r").get("birthday").isNull())
					researcher.setBirthday(record.get("r").get("birthday").asString());
				if(!record.get("r").get("salary").isNull())
					researcher.setSalary(record.get("r").get("salary").asDouble());
				if(!record.get("r").get("occupation_area").isNull())
					researcher.setOccupationArea(record.get("r").get("occupation_area").asString());
				if(!record.get("id(r)").isNull())
					researcher.setEmployeeId(record.get("id(r)").asLong());
			}
		}
		return researcher;
	}

	@Override
	public boolean relationshipToProject(long projectId, long researcherId) {
		String query = "MATCH (p:project), (r:researcher) WHERE id(p)=" + projectId + 
				" AND id(r)=" + researcherId + " CREATE (r)-[w:work]->(p) RETURN w";
		transaction(query);
		return true;
	}

	@Override
	public boolean relationshipToDepartament(long departamentId, long researcherId) {
		String query = "MATCH (d:department), (r:researcher) WHERE id(d)=" + departamentId + 
				" AND id(r)=" + researcherId + " CREATE (r)-[a:associated_with]->(d) RETURN a";
		transaction(query);
		return true;
	}
	
	private void transaction(String query) {
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
		}
	}

	@Override
	public List<Dependent> findAllDependets(long idEmployee) {
		List<Dependent> dependents = new ArrayList<>();
		String query = "MATCH (researcher)-[:dependent]->(d:dependent) WHERE id(researcher)=" + idEmployee + " RETURN d, id(d)";
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
