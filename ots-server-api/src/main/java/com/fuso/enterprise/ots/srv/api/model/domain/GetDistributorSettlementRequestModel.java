package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.Date;

public class GetDistributorSettlementRequestModel {
	private Date FromDate;
	private Date ToDate;
	private String DistributorId;
	private String pdf;
	private boolean GstAvailability;
	
	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	public Date getFromDate() {
		return FromDate;
	}
	public void setFromDate(Date fromDate) {
		FromDate = fromDate;
	}
	public Date getToDate() {
		return ToDate;
	}
	public void setToDate(Date toDate) {
		ToDate = toDate;
	}
	public String getDistributorId() {
		return DistributorId;
	}
	public void setDistributorId(String distributorId) {
		DistributorId = distributorId;
	}
	public boolean getGstAvailability() {
		return GstAvailability;
	}
	public void setGstAvailability(boolean gstAvailability) {
		GstAvailability = gstAvailability;
	}
	
	
	
}
