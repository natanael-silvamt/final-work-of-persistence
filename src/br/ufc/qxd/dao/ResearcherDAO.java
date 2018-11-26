package br.ufc.qxd.dao;

import java.util.List;

import br.ufc.qxd.entities.Researcher;

public interface ResearcherDAO {

	public void relationshipToProject(long id_project, long id_researcher);
	public void insert(Researcher researcher);
	public boolean remove(long id);
	public boolean update(long idOldResearcher, Researcher newResearcher);
	public List<Researcher> findAll();
	public Researcher findById(long id);
	
}
