package com.fuso.enterprise.ots.srv.api.model.domain;

import java.sql.Date;

public class GetSchedulerRequestModel {

	private String distributorId;
	
	private Date Date;
	
	private String status;

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}
	
}
