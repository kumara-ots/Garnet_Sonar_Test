package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCountryMapping;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorCountryMappingRequest;

public interface DistributorCountryMappingDAO {

	String addDistributorCountryMapping(AddDistributorCountryMappingRequest addDistributorCountryMappingRequest);

	List<DistributorCountryMapping> getCountriesMappedToDistributor(String distributorId);

	String deleteDistributorCountryMappingByDistributorId(String distributorId);
	
}
