package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductLocationMapping;

public class ProductLocationResponse {
	private List<ProductLocationMapping> productLocation;
	
	public List<ProductLocationMapping> getProductLocation() {
		return productLocation;
	}

	public void setProductLocation(List<ProductLocationMapping> productLocation) {
		this.productLocation = productLocation;
	}
	
}
