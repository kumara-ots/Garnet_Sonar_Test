package com.fuso.enterprise.ots.srv.api.model.domain;

import java.math.BigDecimal;

public class AddDistributorRegistrationDetailsModel {
	
	 private String userId;
	
	 private BigDecimal registrationAmount;
	 
	 private String registrationPaymentId;
	 
	 private String registrationPaymentStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public BigDecimal getRegistrationAmount() {
		return registrationAmount;
	}

	public void setRegistrationAmount(BigDecimal registrationAmount) {
		this.registrationAmount = registrationAmount;
	}

	public String getRegistrationPaymentId() {
		return registrationPaymentId;
	}

	public void setRegistrationPaymentId(String registrationPaymentId) {
		this.registrationPaymentId = registrationPaymentId;
	}

	public String getRegistrationPaymentStatus() {
		return registrationPaymentStatus;
	}

	public void setRegistrationPaymentStatus(String registrationPaymentStatus) {
		this.registrationPaymentStatus = registrationPaymentStatus;
	}
	
}