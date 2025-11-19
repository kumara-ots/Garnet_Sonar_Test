package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateServiceStatus {

	private String serviceId;
	private String status;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
