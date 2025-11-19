package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class DeleteProductLocationMap {
	private String distributorId;
	private String productId;
	private List<String> locationId;
	
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<String> getLocationId() {
		return locationId;
	}
	public void setLocationId(List<String> locationId) {
		this.locationId = locationId;
	}

}
