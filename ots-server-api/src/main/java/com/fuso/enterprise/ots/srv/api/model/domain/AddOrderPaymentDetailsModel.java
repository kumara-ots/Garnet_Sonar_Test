package com.fuso.enterprise.ots.srv.api.model.domain;

import java.sql.Date;

public class AddOrderPaymentDetailsModel {

	private String orderTransactionId;
	private String paymentId;
	private Date paymentDate;
	
	public String getOrderTransactionId() {
		return orderTransactionId;
	}
	public void setOrderTransactionId(String orderTransactionId) {
		this.orderTransactionId = orderTransactionId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
