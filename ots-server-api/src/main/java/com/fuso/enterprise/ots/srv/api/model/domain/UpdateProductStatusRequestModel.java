package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateProductStatusRequestModel {

	private String ProductId;
	
	private String Status;
	 
	private String Reason;

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}
	
	
}
