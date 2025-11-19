package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceLocationMapping;

public class ServiceLocationResponse {

	private List<ServiceLocationMapping> serviceLocation;

	public List<ServiceLocationMapping> getServiceLocation() {
		return serviceLocation;
	}

	public void setServiceLocation(List<ServiceLocationMapping> serviceLocation) {
		this.serviceLocation = serviceLocation;
	}

}
