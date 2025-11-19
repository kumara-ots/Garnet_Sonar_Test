package com.fuso.enterprise.ots.srv.api.model.domain;

import java.sql.Date;

public class GetOrderList {

	private String distributorsId;
	private Date fromTime;
	private Date toTime;
	private String status;
	private String customerId;
	private String productId;
	private String pdf;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getDistributorsId() {
		return distributorsId;
	}
	public void setDistributorsId(String distributorsId) {
		this.distributorsId = distributorsId;
	}
	public Date getFromTime() {
		return fromTime;
	}
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	public Date getToTime() {
		return toTime;
	}
	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}



}
