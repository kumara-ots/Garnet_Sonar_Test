package com.fuso.enterprise.ots.srv.api.model.domain;

public class GenerateOrderProductInvoiceModel {
	private String orderId;
	private String productId;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

}
