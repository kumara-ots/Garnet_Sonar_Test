package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetDonationModelRequest {

	private String status;

	private String assgineId;
	
	private String productId;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssgineId() {
		return assgineId;
	}

	public void setAssgineId(String assgineId) {
		this.assgineId = assgineId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
