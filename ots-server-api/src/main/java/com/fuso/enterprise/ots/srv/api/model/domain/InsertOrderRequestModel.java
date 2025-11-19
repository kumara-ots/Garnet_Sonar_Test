package com.fuso.enterprise.ots.srv.api.model.domain;

public class InsertOrderRequestModel {
	private String customerId;
	private String customerChangeAddressId;
	private String couponId;
	private String paymentStatus;
	private String orderTransactionId;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerChangeAddressId() {
		return customerChangeAddressId;
	}
	public void setCustomerChangeAddressId(String customerChangeAddressId) {
		this.customerChangeAddressId = customerChangeAddressId;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getOrderTransactionId() {
		return orderTransactionId;
	}
	public void setOrderTransactionId(String orderTransactionId) {
		this.orderTransactionId = orderTransactionId;
	}
	
	
}
