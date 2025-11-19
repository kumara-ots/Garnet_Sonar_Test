package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;

public class AddDistributorCompanyDetailsRequest {
	private DistributorCompanyDetails request;

	public DistributorCompanyDetails getRequest() {
		return request;
	}

	public void setRequest(DistributorCompanyDetails request) {
		this.request = request;
	}
	
}
