package br.ufc.qxd.entities;

public class Project {
	private long projectId;
	private String name;
	private int number;
	private String term;
	
	public Project(long projectId, String name, int number, String term) {
		this.projectId = projectId;
		this.name = name;
		this.number = number;
		this.term = term;
	}

	public Project(String name, int number, String term) {
		this.name = name;
		this.number = number;
		this.term = term;
	}

	public Project() {}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", name=" + name + ", number=" + number + ", term=" + term + "]";
	}
}
