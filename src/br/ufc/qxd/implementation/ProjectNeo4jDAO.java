package br.ufc.qxd.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import br.ufc.qxd.connection.ConnectionNeo4j;
import br.ufc.qxd.dao.ProjectDAO;
import br.ufc.qxd.entities.Project;
import br.ufc.qxd.util.MyTransactionWork;

public class ProjectNeo4jDAO implements ProjectDAO{

	@Override
	public void insert(Project project) {
		String query = "create(p:project) set p.name=$name, p.number=$number, p.term=$term return id(p)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", project.getName());
		map.put("number", project.getNumber());
		map.put("term", project.getTerm());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			project.setProjectId(session.writeTransaction(new MyTransactionWork(query, map)));
		}
	}

	@Override
	public boolean remove(long id) {
		String query = "MATCH(p:project) WHERE id(p)=" + String.valueOf(id) + " DETACH DELETE p";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
			return true;
		}
	}

	@Override
	public boolean update(long idOldProject, Project newProject) {
		String query = "MATCH(p:project) WHERE id(p)=" + idOldProject + "SET p.name=$name, p.number=$number, p.term=$term RETURN id(p)";
		Map<String, Object> map = new HashMap<>();
		map.put("name", newProject.getName());
		map.put("number", newProject.getNumber());
		map.put("term", newProject.getTerm());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			newProject.setProjectId(session.writeTransaction(new MyTransactionWork(query, map)));
			return true;
		}
	}

	@Override
	public List<Project> findAll() {
		List<Project> projects = new ArrayList<>();
		String query = "MATCH(p:project) RETURN p, id(p)";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record rec = sr.next();
				Project proj = new Project();
				if(!rec.get("p").get("name").isNull())
					proj.setName(rec.get("p").get("name").asString());
				if(!rec.get("p").get("number").isNull())
					proj.setNumber(rec.get("p").get("number").asInt());
				if(!rec.get("p").get("term").isNull())
					proj.setTerm(rec.get("p").get("term").asString());
				if(!rec.get("id(p)").isNull())
					proj.setProjectId(rec.get("id(p)").asLong());
				projects.add(proj);
			}
		}
		return projects;
	}

	@Override
	public Project findById(long id) {
		String query = "match(p:project) where id(p)=" + id + " return p, id(p)";
		Project proj = new Project();
		try(Session session = ConnectionNeo4j.getDriver().session()){
			StatementResult sr = session.run(query);
			while(sr.hasNext()) {
				Record rec = sr.next();
				if(!rec.get("p").get("name").isNull())
					proj.setName(rec.get("p").get("name").asString());
				if(!rec.get("p").get("number").isNull())
					proj.setNumber(rec.get("p").get("number").asInt());
				if(!rec.get("id(p)").isNull())
					proj.setProjectId(rec.get("id(p)").asLong());
				if(!rec.get("p").get("term").isNull())
					proj.setTerm(rec.get("p").get("term").asString());
			}
		}
		return proj;
	}

	@Override
	public void relationshipTodepartment(long id_project, long id_department) {
		String query = "MATCH (p:project), (d:department) WHERE id(p)=" + id_project + 
				" AND id(d)=" + id_department + " CREATE (p)-[m:managed]->(d) RETURN m";
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
		}		
	}

}
