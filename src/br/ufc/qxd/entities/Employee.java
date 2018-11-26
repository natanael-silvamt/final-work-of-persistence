package br.ufc.qxd.entities;

import java.io.Serializable;

public abstract class Employee implements Serializable{
	private static final long serialVersionUID = 1L;
	protected long employeeId;
	protected String name;
	protected String sex;
	protected String birthday;
	protected double salary;
	
	public Employee(long employeeId, String name, String sex, String birthday, double salary) {
		this.employeeId = employeeId;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.salary = salary;
	}

	public Employee(String name, String sex, String birthday, double salary) {
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.salary = salary;
	}
	
	public Employee() {}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}
