package br.ufc.qxd.dao;

import java.util.List;

import br.ufc.qxd.entities.Project;

public interface ProjectDAO {
	public void relationshipTodepartment(long id_project, long id_department);
	public void insert(Project project);
	public boolean remove(long id);
	public boolean update(long idOldProject, Project newProject);
	public List<Project> findAll();
	public Project findById(long id);
}
