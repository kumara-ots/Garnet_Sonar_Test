package com.fuso.enterprise.ots.srv.api.model.domain;

public class RequestProductDetails {

	private OrderDetailsAndProductDetails orderDetails;
	
	private UserDetails userDetails;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public OrderDetailsAndProductDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetailsAndProductDetails orderDetails) {
		this.orderDetails = orderDetails;
	}
	
}
