package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceLocationMapping;
import com.fuso.enterprise.ots.srv.api.service.request.AddServiceLocationMappingRequest;

public interface ServiceLocationMappingDAO {

	String addServiceLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest );

	List<ServiceLocationMapping> addServiceLocationMappings(AddServiceLocationMappingRequest addServiceLocationMappingRequest);

    List<ServiceLocationMapping> getLocationsMappedToProviderOnly(String providerId);
	
	List<ServiceLocationMapping> getLocationsMappedToProviderAndService(String providerId, String serviceId);

	List<ServiceLocationMapping> getLocationsMappedToService(String serviceId);

}
