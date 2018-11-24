package br.ufc.qxd.entities;

public class Dependent {
	private long id_dependent;
	private String name;
	private String sex;
	private String birthday;
	private String degree_of_kinship;
	
	public Dependent(long id_dependent, String name, String sex, String birthday, String degree_of_kinship) {
		this.id_dependent = id_dependent;
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.degree_of_kinship = degree_of_kinship;
	}

	public Dependent(String name, String sex, String birthday, String degree_of_kinship) {
		this.name = name;
		this.sex = sex;
		this.birthday = birthday;
		this.degree_of_kinship = degree_of_kinship;
	}

	public Dependent() {}

	public long getId_dependent() {
		return id_dependent;
	}

	public void setId_dependent(long id_dependent) {
		this.id_dependent = id_dependent;
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

	public String getDegree_of_kinship() {
		return degree_of_kinship;
	}

	public void setDegree_of_kinship(String degree_of_kinship) {
		this.degree_of_kinship = degree_of_kinship;
	}

	@Override
	public String toString() {
		return "Dependent [id_dependent=" + id_dependent + ", name=" + name + ", sex=" + sex + ", birthday=" + birthday
				+ ", degree_of_kinship=" + degree_of_kinship + "]";
	}
}
