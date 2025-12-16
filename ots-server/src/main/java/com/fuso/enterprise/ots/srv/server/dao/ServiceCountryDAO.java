package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceCountry;

public interface ServiceCountryDAO {

	List<ServiceCountry> getAllCountry();

	ServiceCountry getCountryByCountryCode(String countryCode);
	
	List<ServiceCountry> getCountriesWithActiveProducts();

}
