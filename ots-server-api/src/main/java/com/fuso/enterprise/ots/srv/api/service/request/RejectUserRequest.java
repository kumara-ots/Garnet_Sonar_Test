package com.fuso.enterprise.ots.srv.api.service.request;

public class RejectUserRequest {

	private String registrationId;
	
	private String Status;

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	
}
