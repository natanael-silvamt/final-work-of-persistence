package br.ufc.qxd.entities;

public class Researcher extends Employee{
	private static final long serialVersionUID = 1L;
	private String occupationArea;
	
	public Researcher(long employeeId, String name, String sex, String birthday, double salary, String occupationArea) {
		super(employeeId, name, sex, birthday, salary);
		this.occupationArea = occupationArea;
	}
	
	public Researcher(String name, String sex, String birthday, double salary, String occupationArea) {
		super(name, sex, birthday, salary);
		this.occupationArea = occupationArea;
	}
	
	public Researcher() {}

	public String getOccupationArea() {
		return occupationArea;
	}

	public void setOccupationArea(String occupationArea) {
		this.occupationArea = occupationArea;
	}

	@Override
	public String toString() {
		return "Researcher [occupationArea=" + occupationArea + ", employeeId=" + employeeId + ", name=" + name
				+ ", sex=" + sex + ", birthday=" + birthday + ", salary=" + salary + "]";
	}
}
