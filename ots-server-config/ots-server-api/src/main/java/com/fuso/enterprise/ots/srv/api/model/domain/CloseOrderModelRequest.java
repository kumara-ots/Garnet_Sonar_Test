package com.fuso.enterprise.ots.srv.api.model.domain;

public class CloseOrderModelRequest {

	private String OrderId;
	
	private String DeliveredDate;

	private String OrderStatus;
	
	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getDeliveredDate() {
		return DeliveredDate;
	}

	public void setDeliveredDate(String deliveredDate) {
		DeliveredDate = deliveredDate;
	}
	
	
}
