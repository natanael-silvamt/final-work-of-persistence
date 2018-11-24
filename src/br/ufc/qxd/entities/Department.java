package br.ufc.qxd.entities;

public class Department {
	private long id_department;
	private String name;
	private int number;
	
	public Department(long id_department, String name, int number) {
		this.id_department = id_department;
		this.name = name;
		this.number = number;
	}

	public Department(String name, int number) {
		this.name = name;
		this.number = number;
	}

	public Department() {}

	public long getId_department() {
		return id_department;
	}

	public void setId_department(long id_department) {
		this.id_department = id_department;
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

	@Override
	public String toString() {
		return "Department [id_department=" + id_department + ", name=" + name + ", number=" + number + "]";
	}
}
