package com.fuso.enterprise.ots.srv.api.model.domain;

public class AddWishListRequestModel {

	private String customerId;
	
	private String productId;
	
	private Integer productWishlistId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getProductWishlistId() {
		return productWishlistId;
	}

	public void setProductWishlistId(Integer productWishlistId) {
		this.productWishlistId = productWishlistId;
	}
	
	
}
