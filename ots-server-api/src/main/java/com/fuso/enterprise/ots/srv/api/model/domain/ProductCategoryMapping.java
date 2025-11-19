package com.fuso.enterprise.ots.srv.api.model.domain;

public class ProductCategoryMapping {
	
	private String otsProductCategorytMappingId;
    private String otsProductId;
    private String otsProductCategoryId;
    private String createdUser;
    
	public String getOtsProductCategorytMappingId() {
		return otsProductCategorytMappingId;
	}
	public void setOtsProductCategorytMappingId(String otsProductCategorytMappingId) {
		this.otsProductCategorytMappingId = otsProductCategorytMappingId;
	}
	public String getOtsProductId() {
		return otsProductId;
	}
	public void setOtsProductId(String otsProductId) {
		this.otsProductId = otsProductId;
	}
	public String getOtsProductCategoryId() {
		return otsProductCategoryId;
	}
	public void setOtsProductCategoryId(String otsProductCategoryId) {
		this.otsProductCategoryId = otsProductCategoryId;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

}
