package br.ufc.qxd.entities;

public class ClearEmployee extends Employee{
	private static final long serialVersionUID = 1L;
	private String workingDays;
	
	public ClearEmployee(long employeeId, String name, String sex, String birthday, double salary, String workingDays) {
		super(employeeId, name, sex, birthday, salary);
		this.workingDays = workingDays;
	}

	public ClearEmployee(String name, String sex, String birthday, double salary, String workingDays) {
		super(name, sex, birthday, salary);
		this.workingDays = workingDays;
	}
	
	public ClearEmployee() {}

	public String getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(String workingDays) {
		this.workingDays = workingDays;
	}
}
