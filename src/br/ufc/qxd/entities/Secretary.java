package br.ufc.qxd.entities;

public class Secretary extends Employee{
	private static final long serialVersionUID = 1L;
	private String degreeOfSchooling;
	
	public Secretary(long employeeId, String name, String sex, String birthday, double salary, String degreeOfSchooling) {
		super(employeeId, name, sex, birthday, salary);
		this.degreeOfSchooling = degreeOfSchooling;
	}
	
	public Secretary(String name, String sex, String birthday, double salary, String degreeOfSchooling) {
		super(name, sex, birthday, salary);
		this.degreeOfSchooling = degreeOfSchooling;
	}
	
	public Secretary() {}

	public String getDegreeOfSchooling() {
		return degreeOfSchooling;
	}

	public void setDegreeOfSchooling(String degreeOfSchooling) {
		this.degreeOfSchooling = degreeOfSchooling;
	}

	@Override
	public String toString() {
		return "Secretary [degreeOfSchooling=" + degreeOfSchooling + ", employeeId=" + employeeId + ", name="
				+ name + ", sex=" + sex + ", birthday=" + birthday + ", salary=" + salary + "]";
	}
}
