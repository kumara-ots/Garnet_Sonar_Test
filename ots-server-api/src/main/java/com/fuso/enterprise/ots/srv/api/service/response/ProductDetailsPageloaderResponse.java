package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;
import java.util.Map;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;

public class ProductDetailsPageloaderResponse {
	
	private Map<String, List<ProductDetails>> productDetails;

	public Map<String, List<ProductDetails>> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(Map<String, List<ProductDetails>> productDetails) {
		this.productDetails = productDetails;
	}
	
}
