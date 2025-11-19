package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceCountry;

public class ServiceCountryResponse {
	
	private List<ServiceCountry> countryDetails;

	public List<ServiceCountry> getCountryDetails() {
		return countryDetails;
	}

	public void setCountryDetails(List<ServiceCountry> countryDetails) {
		this.countryDetails = countryDetails;
	}

}
