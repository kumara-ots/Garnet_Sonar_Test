package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderProductAndOrderDetails;

public class OrderProductAndOrderResponse {
	private List<OrderProductAndOrderDetails> orderProductList;

	public List<OrderProductAndOrderDetails> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<OrderProductAndOrderDetails> orderProductList) {
		this.orderProductList = orderProductList;
	}

}
