package com.fuso.enterprise.ots.srv.api.service.request;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductAttributesMapping;

public class AddProductAttributeMappingRequest {
	private List<ProductAttributesMapping> request;

	public List<ProductAttributesMapping> getRequest() {
		return request;
	}

	public void setRequest(List<ProductAttributesMapping> request) {
		this.request = request;
	}

}