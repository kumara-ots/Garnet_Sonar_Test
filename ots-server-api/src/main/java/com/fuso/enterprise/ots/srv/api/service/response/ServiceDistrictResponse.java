package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDistrict;

public class ServiceDistrictResponse {

	private List<ServiceDistrict> districtDetails;
	
	public List<ServiceDistrict> getDistrictDetails() {
		return districtDetails;
	}
	
	public void setDistrictDetails(List<ServiceDistrict> districtDetails) {
		this.districtDetails = districtDetails;
	}

}


