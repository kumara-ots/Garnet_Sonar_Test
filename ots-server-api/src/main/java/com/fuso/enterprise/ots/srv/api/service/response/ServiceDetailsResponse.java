package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDetailsData;

public class ServiceDetailsResponse {

	List<ServiceDetailsData> serviceDetails;

	public List<ServiceDetailsData> getServiceDetails() {
		return serviceDetails;
	}

	public void setServiceDetails(List<ServiceDetailsData> serviceDetails) {
		this.serviceDetails = serviceDetails;
	}

	
}