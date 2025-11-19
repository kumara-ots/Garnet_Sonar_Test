package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateOrderProductStatusModel {

	private String orderProductId ;
	private String orderProductStatus;
	
	public String getOrderProductId() {
		return orderProductId;
	}
	public void setOrderProductId(String orderProductId) {
		this.orderProductId = orderProductId;
	}
	public String getOrderProductStatus() {
		return orderProductStatus;
	}
	public void setOrderProductStatus(String orderProductStatus) {
		this.orderProductStatus = orderProductStatus;
	}
	
}
