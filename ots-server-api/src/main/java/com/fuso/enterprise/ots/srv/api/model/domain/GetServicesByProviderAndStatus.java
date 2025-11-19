package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetServicesByProviderAndStatus {

	private String providerId;
	
	private String status;

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
