package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetails;

public class OrderDetailsBOResponse {

	private String razorPayKey;
	
	private List<OrderDetails> OrderDetails;

	public List<OrderDetails> getOrderDetails() {
		return OrderDetails;
	}

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		OrderDetails = orderDetails;
	}

	public String getRazorPayKey() {
		return razorPayKey;
	}

	public void setRazorPayKey(String razorPayKey) {
		this.razorPayKey = razorPayKey;
	}

}
