package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class AddProductCategoryAndProductModelRequest {
	
	private String userId;
	
	private String key;
	
	private String productCategoryId;
	
	private List<ProductDetails> productDetails;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public List<ProductDetails> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetails> productDetails) {
		this.productDetails = productDetails;
	}

	
}
