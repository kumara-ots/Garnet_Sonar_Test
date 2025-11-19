package com.fuso.enterprise.ots.srv.api.service.response;

import com.fuso.enterprise.ots.srv.api.model.domain.GetServiceableLocation;

public class GetServiceableLocationResponse {
	private GetServiceableLocation serviceableLocation;

	public GetServiceableLocation getServiceableLocation() {
		return serviceableLocation;
	}

	public void setServiceableLocation(GetServiceableLocation serviceableLocation) {
		this.serviceableLocation = serviceableLocation;
	}
	
}