package br.ufc.qxd.entities;

public class Project {
	private long id_project;
	private String name;
	private int number;
	private String term;
	
	public Project(long id_project, String name, int number, String term) {
		this.id_project = id_project;
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

	public long getId_project() {
		return id_project;
	}

	public void setId_project(long id_project) {
		this.id_project = id_project;
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
		return "Project [id_project=" + id_project + ", name=" + name + ", number=" + number + ", term=" + term + "]";
	}
}
