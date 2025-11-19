package com.fuso.enterprise.ots.srv.api.service.request;

public class GetOrderForFacilitator {
	
	private Integer distributorId;
	
	private String status;

	public Integer getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Integer distributorId) {
		this.distributorId = distributorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
