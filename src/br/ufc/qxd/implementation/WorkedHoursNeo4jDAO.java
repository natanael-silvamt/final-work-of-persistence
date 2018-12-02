package br.ufc.qxd.implementation;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.v1.Session;

import br.ufc.qxd.connection.ConnectionNeo4j;
import br.ufc.qxd.dao.WorkedHoursDAO;
import br.ufc.qxd.entities.WorkedHours;
import br.ufc.qxd.util.MyTransactionWork;

public class WorkedHoursNeo4jDAO implements WorkedHoursDAO{

	@Override
	public void insert(WorkedHours workedHours) {
		String query = "CREATE(w:workeHours) SET w.amountOfHours=$amountOfHours RETURN id(w)";
		Map<String, Object> map = new HashMap<>();
		map.put("amountOfHours", workedHours.getAmount_of_hours());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			workedHours.setId(id);
		}		
	}

	@Override
	public void relationshipToresearcher(long idResearcher, long idWorkedHours) {
		String query = "MATCH (r:researcher), (w:workedHours) WHERE id(r)=" + idResearcher +
				" AND id(w)=" + idWorkedHours + " CREATE (r)-[w:worked]->(w) RETURN w";
		transaction(query);
	}

	@Override
	public void relationshioToProject(long idWorkedHours, long idProject) {
		String query = "MATCH (w:workedHours), (p:project) WHERE id(w)=" + idWorkedHours + 
				" AND id(p)=" + idProject + " CREATE (w)-[b:belong]->(p) RETURN b";
		transaction(query);
	}

	@Override
	public boolean remove(long idEmployee) {
		String query = "MATCH (researcher)-[:worked]->(workedHours) WHERE id(researcher)=" + idEmployee + " DETACH DELETE workedHours";
		transaction(query);		
		return true;
	}
	
	private void transaction(String query) {
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
		}
	}
	

}
