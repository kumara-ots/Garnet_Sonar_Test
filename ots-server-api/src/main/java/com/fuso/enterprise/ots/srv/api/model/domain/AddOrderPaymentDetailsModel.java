package com.fuso.enterprise.ots.srv.api.model.domain;

public class AddOrderPaymentDetailsModel {

	private String PaymentId;
	private String OrderTransactionId;
	
	public String getOrderTransactionId() {
		return OrderTransactionId;
	}
	public void setOrderTransactionId(String orderTransactionId) {
		OrderTransactionId = orderTransactionId;
	}
	public String getPaymentId() {
		return PaymentId;
	}
	public void setPaymentId(String paymentId) {
		PaymentId = paymentId;
	}
	
}
