package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductManufacturerDetails;

public class ProductManufacturersResponse {
	
	private List<ProductManufacturerDetails> productManufacturerDetails;

	public List<ProductManufacturerDetails> getProductManufacturerDetails() {
		return productManufacturerDetails;
	}

	public void setProductManufacturerDetails(List<ProductManufacturerDetails> productManufacturerDetails) {
		this.productManufacturerDetails = productManufacturerDetails;
	}

}
