package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.Date;

public class GetDonationReportBydateModelRequest {

	private String userId;
	
	private String status;
	
	private Date startDate;
	
	private Date endDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
