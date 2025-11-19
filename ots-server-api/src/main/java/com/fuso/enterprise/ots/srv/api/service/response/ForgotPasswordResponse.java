package com.fuso.enterprise.ots.srv.api.service.response;

public class ForgotPasswordResponse {

	private String userId;
	
	private String otp;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
