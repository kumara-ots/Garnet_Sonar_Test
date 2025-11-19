package com.fuso.enterprise.ots.srv.api.model.domain;

public class NotifyProductForCustomerModel {
	
	private String productId;
	
	private String customerId;

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
	
}
