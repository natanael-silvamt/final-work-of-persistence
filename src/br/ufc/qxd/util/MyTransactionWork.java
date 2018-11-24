package br.ufc.qxd.util;

import java.util.Map;

import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

public class MyTransactionWork implements TransactionWork<Integer>{
	private String instruction;
	private Map<String, Object> map;
	
	public MyTransactionWork(String instruction, Map<String, Object> map) {
		this.instruction = instruction;
		this.map = map;
	}

	@Override
	public Integer execute(Transaction tx) {
		StatementResult sr = tx.run(this.instruction, this.map);
		return sr.single().get(0).asInt();
	}
}
