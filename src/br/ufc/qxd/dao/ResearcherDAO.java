package br.ufc.qxd.dao;

import java.util.List;

import br.ufc.qxd.entities.Dependent;
import br.ufc.qxd.entities.Researcher;

public interface ResearcherDAO {

	public boolean relationshipToProject(long projectId, long researcherId);
	public boolean relationshipToDepartament(long departamentId, long researcherId);
	public void insert(Researcher researcher);
	public boolean remove(long id);
	public boolean update(long idOldResearcher, Researcher newResearcher);
	public List<Researcher> findAll();
	public Researcher findById(long id);
	public List<Dependent> findAllDependets(long idEmployee);
	
}
