package br.ufc.qxd.entities;

public class ClearEmployee extends Employee{
	private static final long serialVersionUID = 1L;
	private String working_days;
	
	public ClearEmployee(long id_employee, String name, String sex, String birthday, double salary, String working_days) {
		super(id_employee, name, sex, birthday, salary);
		this.working_days = working_days;
	}

	public ClearEmployee(String name, String sex, String birthday, double salary, String working_days) {
		super(name, sex, birthday, salary);
		this.working_days = working_days;
	}
	
	public ClearEmployee() {}

	public String getWorking_days() {
		return working_days;
	}

	public void setWorking_days(String working_days) {
		this.working_days = working_days;
	}
}
