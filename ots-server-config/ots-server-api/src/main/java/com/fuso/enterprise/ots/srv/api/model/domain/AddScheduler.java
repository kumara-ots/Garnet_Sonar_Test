package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.Date;
import java.util.List;

public class AddScheduler {

	private String prodcutId;
	
	private String prodcutQty;
	
	private Date startDate;
	
	private Date endDate;
	
	private List<String> schduleweekDays;


	public Date getEndDate() {
		return endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProdcutId() {
		return prodcutId;
	}

	public void setProdcutId(String prodcutId) {
		this.prodcutId = prodcutId;
	}

	public String getProdcutQty() {
		return prodcutQty;
	}

	public void setProdcutQty(String prodcutQty) {
		this.prodcutQty = prodcutQty;
	}

	public List<String> getSchduleweekDays() {
		return schduleweekDays;
	}


	public void setSchduleweekDays(List<String> schduleweekDays) {
		this.schduleweekDays = schduleweekDays;
	}
	
}
