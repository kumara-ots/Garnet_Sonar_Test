package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCountryMapping;

public class DistributorCountryResponse {
	
	private List<DistributorCountryMapping> distributorCountry;

	public List<DistributorCountryMapping> getDistributorCountry() {
		return distributorCountry;
	}

	public void setDistributorCountry(List<DistributorCountryMapping> distributorCountry) {
		this.distributorCountry = distributorCountry;
	}

}
