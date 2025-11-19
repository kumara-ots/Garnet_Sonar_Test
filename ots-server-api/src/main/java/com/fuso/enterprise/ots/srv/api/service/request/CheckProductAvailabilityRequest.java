package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.CheckProductLocationAvailabitlity;

public class CheckProductAvailabilityRequest {
	private CheckProductLocationAvailabitlity request;

	public CheckProductLocationAvailabitlity getRequest() {
		return request;
	}

	public void setRequest(CheckProductLocationAvailabitlity request) {
		this.request = request;
	}
	
}
