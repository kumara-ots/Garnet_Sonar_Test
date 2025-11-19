package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductAttributesMapping;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductAttributeMappingRequest;

public interface ProductAttributesMappingDAO {

	List<ProductAttributesMapping> addProductAttributesMapping(List<ProductAttributesMapping> addProductAttributeMappingRequest);

}