package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateRRCStatusModel {
	
	private String orderId;
	private String productId;
	private String RRCOrderStatus;
	
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
	public String getRRCOrderStatus() {
		return RRCOrderStatus;
	}
	public void setRRCOrderStatus(String rRCOrderStatus) {
		RRCOrderStatus = rRCOrderStatus;
	}
	
}
