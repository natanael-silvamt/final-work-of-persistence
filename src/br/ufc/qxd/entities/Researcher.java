package br.ufc.qxd.entities;

public class Researcher extends Employee{
	private static final long serialVersionUID = 1L;
	private String occupation_area;
	
	public Researcher(long id_employee, String name, String sex, String birthday, double salary, String occupation_area) {
		super(id_employee, name, sex, birthday, salary);
		this.occupation_area = occupation_area;
	}
	
	public Researcher(String name, String sex, String birthday, double salary, String occupation_area) {
		super(name, sex, birthday, salary);
		this.occupation_area = occupation_area;
	}
	
	public Researcher() {}

	public String getOccupation_area() {
		return occupation_area;
	}

	public void setOccupation_area(String occupation_area) {
		this.occupation_area = occupation_area;
	}

	@Override
	public String toString() {
		return "Researcher [occupation_area=" + occupation_area + ", id_employee=" + id_employee + ", name=" + name
				+ ", sex=" + sex + ", birthday=" + birthday + ", salary=" + salary + "]";
	}
}
