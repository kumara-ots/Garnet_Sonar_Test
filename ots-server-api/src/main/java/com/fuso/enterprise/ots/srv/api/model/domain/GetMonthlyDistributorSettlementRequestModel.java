package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetMonthlyDistributorSettlementRequestModel {
	private String month;
	private String year;
	private String distributorId;
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
}
