package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServicePincode;

public class ServicePincodeResponse {

	private List<ServicePincode> pincodeDetails;

	public List<ServicePincode> getPincodeDetails() {
		return pincodeDetails;
	}

	public void setPincodeDetails(List<ServicePincode> pincodeDetails) {
		this.pincodeDetails = pincodeDetails;
	}
	
}
