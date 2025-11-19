package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetCustomerOrderModelRequest {

	private String customerId;
	
	private String status;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
