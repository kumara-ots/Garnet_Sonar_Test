package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductLocationMapping;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductLocationMappingRequest;

public interface ProductLocationMappingDAO {

	String addProductLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest);

	List<ProductLocationMapping> getLocationsMappedToProduct(String productId);

	List<ProductLocationMapping> getProductsMappedToLocation(String locationId);

	List<ProductLocationMapping> getLocationsMappedToDistributorOnly(String distributorId);
	
	List<ProductLocationMapping> getLocationsMappedToDistributorAndProduct(String distributorId, String productId);

	List<ProductLocationMapping> addProductLocationMappings(AddProductLocationMappingRequest addProductLocationMappingRequest);

}
