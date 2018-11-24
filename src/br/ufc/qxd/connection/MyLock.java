package br.ufc.qxd.connection;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

public class MyLock implements AutoCloseable{
	private final Driver driver;
	
	public MyLock() {
		this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "12345"));
	}

	@Override
	public void close() throws Exception {
		this.driver.close();		
	}
	
	public Driver gerDriver() {
		return this.driver;
	}

}
