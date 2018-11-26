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
		map.put("occupation_area", researcher.getOccupation_area());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			researcher.setId_employee(id);
		}
	}

	@Override
	public boolean remove(long id) {
		String query = "match(r:researcher) where id(r)=" + String.valueOf(id) + " delete r";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
			return true;			
		}
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
		map.put("occupation_area", newResearcher.getOccupation_area());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			newResearcher.setId_employee(id); // RM: precisa setar um id num update?
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
					researcher.setBirthday(record.get("r").get("occupation_area").asString());
				if(!record.get("id(r)").isNull())
					researcher.setId_employee(record.get("id(r)").asLong());				
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
					researcher.setOccupation_area(record.get("r").get("occupation_area").asString());
				if(!record.get("id(r)").isNull())
					researcher.setId_employee(record.get("id(r)").asLong());
			}
		}
		return researcher;
	}

	@Override
	public void relationshipToProject(long id_project, long id_researcher) {
		String query = "MATCH (p:project), (r:researcher) WHERE id(p)=" + id_project + 
				" AND id(r)=" + id_researcher + " CREATE (r)-[w:work]->(p) RETURN w";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
		}
	}
}
