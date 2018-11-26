package br.ufc.qxd.entities;

public class Department {
	private long departmentId;
	private String name;
	private int number;
	
	public Department(long departmentId, String name, int number) {
		this.departmentId = departmentId;
		this.name = name;
		this.number = number;
	}

	public Department(String name, int number) {
		this.name = name;
		this.number = number;
	}

	public Department() {}

	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
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
		return "Department [departmentId=" + departmentId + ", name=" + name + ", number=" + number + "]";
	}
}
