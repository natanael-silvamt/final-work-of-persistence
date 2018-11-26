package br.ufc.qxd.entities;

public class Dependent {
	private long dependentId;
	private String name;
	private String sex;
	private String birthday;
	private String degreeOfKinship;
	
	public Dependent(long dependentId, String name, String sex, String birthday, String degreeOfKinship) {
		this.dependentId = dependentId;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.degreeOfKinship = degreeOfKinship;
	}

	public Dependent(String name, String sex, String birthday, String degreeOfKinship) {
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.degreeOfKinship = degreeOfKinship;
	}

	public Dependent() {}

	public long getDependentId() {
		return dependentId;
	}

	public void setDependentId(long dependentId) {
		this.dependentId = dependentId;
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

	public String getDegreeOfKinship() {
		return degreeOfKinship;
	}

	public void setDegreeOfKinship(String degreeOfKinship) {
		this.degreeOfKinship = degreeOfKinship;
	}

	@Override
	public String toString() {
		return "Dependent [dependentId=" + dependentId + ", name=" + name + ", sex=" + sex + ", birthday=" + birthday
				+ ", degreeOfKinship=" + degreeOfKinship + "]";
	}
}
