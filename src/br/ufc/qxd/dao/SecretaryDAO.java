package br.ufc.qxd.dao;

import java.util.List;

import br.ufc.qxd.entities.Secretary;

public interface SecretaryDAO {

	public void insert(Secretary secretary);
	public boolean remove(long id);
	public boolean update(long idOldSecretary, Secretary newSecretary);
	public List<Secretary> findAll();
	public Secretary findById(long id);
	
}
