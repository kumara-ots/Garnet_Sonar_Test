package com.fuso.enterprise.ots.srv.api.model.domain;

public class Slot {
	private String start;
	private String end;
	private String employee;

	public Slot() {
	}

	public Slot(String start, String end, String employee) {
		this.start = start;
		this.end = end;
		this.employee = employee;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	// Getter and Setter for employee
	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Slot{" + "start='" + start + '\'' + ", end='" + end + '\'' + ", employee='" + employee + '\'' + '}';
	}
}
