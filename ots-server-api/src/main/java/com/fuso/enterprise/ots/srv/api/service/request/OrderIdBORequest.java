package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderId;

public class OrderIdBORequest {

	private OrderId orderId;

	public OrderId getOrderId() {
		return orderId;
	}

	public void setOrderId(OrderId orderId) {
		this.orderId = orderId;
	}
}
