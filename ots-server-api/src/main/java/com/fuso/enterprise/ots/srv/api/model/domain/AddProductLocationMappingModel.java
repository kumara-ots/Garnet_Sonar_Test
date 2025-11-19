package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class AddProductLocationMappingModel {
	private String distributorId;
	private String productId;
	private List<ProductLocationIDName> productLocationIDName;
	
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
	public List<ProductLocationIDName> getProductLocationIDName() {
		return productLocationIDName;
	}
	public void setProductLocationIDName(List<ProductLocationIDName> productLocationIDName) {
		this.productLocationIDName = productLocationIDName;
	}
	      
}
