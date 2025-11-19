package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetSiblingVariantProductsByAttribute {
	private String productId;
	private String primaryAttributeKey;
	private String primaryAttributeValue;
	private String secondaryAttributeKey;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPrimaryAttributeKey() {
		return primaryAttributeKey;
	}
	public void setPrimaryAttributeKey(String primaryAttributeKey) {
		this.primaryAttributeKey = primaryAttributeKey;
	}
	public String getPrimaryAttributeValue() {
		return primaryAttributeValue;
	}
	public void setPrimaryAttributeValue(String primaryAttributeValue) {
		this.primaryAttributeValue = primaryAttributeValue;
	}
	public String getSecondaryAttributeKey() {
		return secondaryAttributeKey;
	}
	public void setSecondaryAttributeKey(String secondaryAttributeKey) {
		this.secondaryAttributeKey = secondaryAttributeKey;
	}
	

}
