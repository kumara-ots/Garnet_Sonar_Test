package com.fuso.enterprise.ots.srv.api.model.domain;

public class AddOrUpdateCategory {
	private String productId;
	private String productName;
	private String productLevelId;
	private String createdUser;
	private String productImage;
	private String nutritionalFlag;
	private String categoryId;

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductLevelId() {
		return productLevelId;
	}
	public void setProductLevelId(String productLevelId) {
		this.productLevelId = productLevelId;
	}
	
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getNutritionalFlag() {
		return nutritionalFlag;
	}
	public void setNutritionalFlag(String nutritionalFlag) {
		this.nutritionalFlag = nutritionalFlag;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
