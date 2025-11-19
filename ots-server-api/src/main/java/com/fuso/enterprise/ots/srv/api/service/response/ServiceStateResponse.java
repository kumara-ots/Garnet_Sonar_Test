package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceState;

public class ServiceStateResponse {
	private List<ServiceState> stateDetails;

	public List<ServiceState> getStateDetails() {
		return stateDetails;
	}

	public void setStateDetails(List<ServiceState> stateDetails) {
		this.stateDetails = stateDetails;
	}
	
}
