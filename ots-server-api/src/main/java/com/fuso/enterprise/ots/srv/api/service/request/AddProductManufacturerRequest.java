package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductManufacturerDetails;

public class AddProductManufacturerRequest {

	private ProductManufacturerDetails request;

	public ProductManufacturerDetails getRequest() {
		return request;
	}

	public void setRequest(ProductManufacturerDetails request) {
		this.request = request;
	}
	
}
