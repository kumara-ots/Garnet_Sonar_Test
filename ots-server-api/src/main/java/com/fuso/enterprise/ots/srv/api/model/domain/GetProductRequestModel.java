package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetProductRequestModel {

	private String DistributorId;
	private String ProductId;
	public String getDistributorId() {
		return DistributorId;
	}
	public void setDistributorId(String distributorId) {
		DistributorId = distributorId;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	
	
}
