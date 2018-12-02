package br.ufc.qxd.dao;

import br.ufc.qxd.entities.WorkedHours;

public interface WorkedHoursDAO {
	public void insert(WorkedHours workedHours);
	public boolean remove(long idEmployee);
	public void relationshipToresearcher(long idResearcher, long idWorkedHours);
	public void relationshioToProject(long idWorkedHours, long idProject);
}
