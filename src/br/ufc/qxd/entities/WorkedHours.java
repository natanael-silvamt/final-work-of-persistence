package br.ufc.qxd.entities;

public class WorkedHours {
	private long id;
	private double amountOfHours;

	public WorkedHours(double amount_of_hours) {
		this.amountOfHours = amount_of_hours;
	}

	public WorkedHours(long id, double amount_of_hours) {
		this.id = id;
		this.amountOfHours = amount_of_hours;
	}

	public WorkedHours() {}

	public double getAmount_of_hours() {
		return amountOfHours;
	}

	public void setAmount_of_hours(double amount_of_hours) {
		this.amountOfHours = amount_of_hours;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "WorkedHours [id=" + id + ", amount_of_hours=" + amountOfHours + "]";
	}
}
