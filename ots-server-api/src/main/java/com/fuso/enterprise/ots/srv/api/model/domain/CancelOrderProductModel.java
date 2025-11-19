package com.fuso.enterprise.ots.srv.api.model.domain;

public class CancelOrderProductModel {
	
	private String orderId;
	private String productId;
	private String customerId;
	private String cancelReasonId;
	private String cancelledBy;
	
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCancelReasonId() {
		return cancelReasonId;
	}
	public void setCancelReasonId(String cancelReasonId) {
		this.cancelReasonId = cancelReasonId;
	}
	public String getCancelledBy() {
		return cancelledBy;
	}
	public void setCancelledBy(String cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

}
