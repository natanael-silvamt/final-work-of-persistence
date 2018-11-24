package br.ufc.qxd.entities;

public class WorkedHours {
	private double amount_of_hours;

	public WorkedHours(double amount_of_hours) {
		this.amount_of_hours = amount_of_hours;
	}

	public WorkedHours() {}

	public double getAmount_of_hours() {
		return amount_of_hours;
	}

	public void setAmount_of_hours(double amount_of_hours) {
		this.amount_of_hours = amount_of_hours;
	}

	@Override
	public String toString() {
		return "WorkedHours [amount_of_hours=" + amount_of_hours + "]";
	}
}
