package com.fuso.enterprise.ots.srv.api.model.domain;

public class ProductLocationMapping {
	
	private String productLocationMappingId ;
	private String  productId;
	private String distributorId;
	private String locationId;
	private String locationName;
	private String productName;
	private String productImage;

	
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductLocationMappingId() {
		return productLocationMappingId;
	}
	public void setProductLocationMappingId(String productLocationMappingId) {
		this.productLocationMappingId = productLocationMappingId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocatioId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	
}
