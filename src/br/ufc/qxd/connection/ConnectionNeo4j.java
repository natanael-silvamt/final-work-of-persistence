package br.ufc.qxd.connection;

import org.neo4j.driver.v1.Driver;

public class ConnectionNeo4j {
	private static final MyLock connection = new MyLock();	
	public static Driver getDriver() {
		return connection.gerDriver();
	}
}
