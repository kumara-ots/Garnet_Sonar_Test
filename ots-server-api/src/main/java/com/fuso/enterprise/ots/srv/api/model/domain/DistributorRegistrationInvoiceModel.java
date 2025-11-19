package com.fuso.enterprise.ots.srv.api.model.domain;

public class DistributorRegistrationInvoiceModel {
	private String distributorId;
	private String registrationPaymentId;
	
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getRegistrationPaymentId() {
		return registrationPaymentId;
	}
	public void setRegistrationPaymentId(String registrationPaymentId) {
		this.registrationPaymentId = registrationPaymentId;
	}
	
}
