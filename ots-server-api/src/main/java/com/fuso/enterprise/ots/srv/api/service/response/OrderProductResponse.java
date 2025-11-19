package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderProductDetails;

public class OrderProductResponse {
	private List<OrderProductDetails> OrderProductDetails;

	public List<OrderProductDetails> getOrderProductDetails() {
		return OrderProductDetails;
	}

	public void setOrderProductDetails(List<OrderProductDetails> orderProductDetails) {
		OrderProductDetails = orderProductDetails;
	}
	
}
