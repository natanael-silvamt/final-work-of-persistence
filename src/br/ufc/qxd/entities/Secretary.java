package br.ufc.qxd.entities;

public class Secretary extends Employee{
	private static final long serialVersionUID = 1L;
	private String degree_of_schooling;
	
	public Secretary(long id_employee, String name, String sex, String birthday, double salary, String degree_of_schooling) {
		super(id_employee, name, sex, birthday, salary);
		this.degree_of_schooling = degree_of_schooling;
	}
	
	public Secretary(String name, String sex, String birthday, double salary, String degree_of_schooling) {
		super(name, sex, birthday, salary);
		this.degree_of_schooling = degree_of_schooling;
	}
	
	public Secretary() {}

	public String getDegree_of_schooling() {
		return degree_of_schooling;
	}

	public void setDegree_of_schooling(String degree_of_schooling) {
		this.degree_of_schooling = degree_of_schooling;
	}

	@Override
	public String toString() {
		return "Secretary [degree_of_schooling=" + degree_of_schooling + ", id_employee=" + id_employee + ", name="
				+ name + ", sex=" + sex + ", birthday=" + birthday + ", salary=" + salary + "]";
	}
}
