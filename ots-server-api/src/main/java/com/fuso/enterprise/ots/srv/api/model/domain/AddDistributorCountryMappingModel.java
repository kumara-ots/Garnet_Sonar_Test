package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class AddDistributorCountryMappingModel {
	
	private String distributorId;
	private List<DistributorCountryCodeName> distributorCountryCodeName;
	
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public List<DistributorCountryCodeName> getDistributorCountryCodeName() {
		return distributorCountryCodeName;
	}
	public void setDistributorCountryCodeName(List<DistributorCountryCodeName> distributorCountryCodeName) {
		this.distributorCountryCodeName = distributorCountryCodeName;
	}

}
