package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetProductsBySubCategoryAndDistributor {
	private String SubcategoryId;
	private String DistributorId;
	public String getSubcategoryId() {
		return SubcategoryId;
	}
	public void setSubcategoryId(String subcategoryId) {
		SubcategoryId = subcategoryId;
	}
	public String getDistributorId() {
		return DistributorId;
	}
	public void setDistributorId(String distributorId) {
		DistributorId = distributorId;
	}

}