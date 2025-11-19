package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetOrderByOrderIdAndStatusModel {
	private String orderId;
	private String status;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
