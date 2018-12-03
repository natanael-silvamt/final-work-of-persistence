package br.ufc.qxd.implementation;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.v1.Session;

import br.ufc.qxd.connection.ConnectionNeo4j;
import br.ufc.qxd.dao.AddressDAO;
import br.ufc.qxd.dao.ClearEmployeeDAO;
import br.ufc.qxd.dao.ResearcherDAO;
import br.ufc.qxd.dao.SecretaryDAO;
import br.ufc.qxd.entities.Address;
import br.ufc.qxd.util.MyTransactionWork;

public class AddressNeo4jDAO implements AddressDAO{
	private SecretaryDAO secretaryDAO = new SecretaryNeo4jDAO();
	private ResearcherDAO researcherDAO = new ResearcherNeo4jDAO();
	private ClearEmployeeDAO clearEmployeeDAO = new ClearEmployeeNeo4jDAO();

	@Override
	public void insert(Address address) {
		String query = "CREATE (a:address) SET a.street=$street, a.number=$number, a.cep=$cep, "
				+ "a.neighborhood=$neighborhood RETURN id(a)";
		Map<String, Object> map = new HashMap<>();
		map.put("street", address.getStreet());
		map.put("number", address.getNumber());
		map.put("cep", address.getCep());
		map.put("neighborhood", address.getNeighborhood());
		try(Session session = ConnectionNeo4j.getDriver().session()){
			long id = session.writeTransaction(new MyTransactionWork(query, map));
			address.setAddressId(id);
		}
	}

	@Override
	public boolean remove(long idEmployee) {
		String query = "";
		if(this.secretaryDAO.findById(idEmployee).getName() != null) {
			query = "MATCH (secretary)-[:live]->(address) WHERE id(secretary)=" + idEmployee + " DETACH DELETE address";
			transaction(query);
			return true;
		}
		else if(this.researcherDAO.findById(idEmployee).getName() != null) {
			query = "MATCH (researcher)-[:live]->(address) WHERE id(researcher)=" + idEmployee + " DETACH DELETE address";
			transaction(query);
			return true;
		}
		else if(this.clearEmployeeDAO.findById(idEmployee).getName() != null) {
			query = "MATCH (clearEmployee)-[:live]->(address) WHERE id(clearEmployee)=" + idEmployee + " DETACH DELETE address";
			transaction(query);
			return true;
		}
		return false;
	}

	@Override
	public boolean update(long idEmployee, Address newAddress) {
		String query = "";
		if(this.secretaryDAO.findById(idEmployee).getName() != null) {
			query = "MATCH (secretary)-[:live]->(a:address) WHERE id(secretary)=" + idEmployee + 
					" set a.street, a.number, a.cep, a.neighborhood RETURN id(a)";
		}		
		else if(this.researcherDAO.findById(idEmployee).getName() != null) {
			query = "MATCH (researcher)-[:live]->(a:address) WHERE id(researcher)=" + idEmployee + 
					" set a.street, a.number, a.cep, a.neighborhood RETURN id(a)";
		}
		else if(this.clearEmployeeDAO.findById(idEmployee).getName() != null) {
			query = "MATCH (clearEmployee)-[:live]->(a:address) WHERE id(clearEmployee)=" + idEmployee + 
					" set a.street, a.number, a.cep, a.neighborhood RETURN id(a)";
		}
		if(!query.equals("")) {
			Map<String, Object> map = new HashMap<>();
			map.put("street", newAddress.getStreet());
			map.put("number", newAddress.getNumber());
			map.put("cep", newAddress.getCep());
			map.put("neighborhood", newAddress.getNeighborhood());
			try(Session session = ConnectionNeo4j.getDriver().session()){
				long id = session.writeTransaction(new MyTransactionWork(query, map));
				newAddress.setAddressId(id);
				return true;
			}
		}		
		return false;
	}

	@Override
	public void relationshipToEmployee(long idAdress, long idEmployee) {
		if(this.secretaryDAO.findById(idEmployee).getName() != null) {
			String query = "MATCH (a:address), (s:secretary) WHERE id(s)=" + idEmployee +
					" AND id(a)=" + idAdress + " CREATE (s)-[l:live]->(a) RETURN l";
			transaction(query);
		}
		else if(this.researcherDAO.findById(idEmployee).getName() != null) {
			String query = "MATCH (a:address), (r:researcher) WHERE id(r)=" + idEmployee +
					" AND id(a)=" + idAdress + " CREATE (r)-[l:live]->(a) RETURN l";
			transaction(query);
		}
		else if(this.clearEmployeeDAO.findById(idEmployee).getName() != null) {
			String query = "MATCH (c:clearEmployee), (a:address) WHERE id(c)=" + idEmployee +
					" AND id(a)=" + idAdress + " CREATE (c)-[l:live]->(a) RETURN l";
			transaction(query);
		}		
	}
	
	private void transaction(String query) {
		try(Session session = ConnectionNeo4j.getDriver().session()){
			session.run(query);
		}
	}

}
