package com.fuso.enterprise.ots.srv.api.model.domain;

public class ProductAttributesMapping {
	private String productAttributeMappingId;
	private String attributeKeyId;
	private String attributeValueId;
	private String productId;
	
	public String getProductAttributeMappingId() {
		return productAttributeMappingId;
	}
	public void setProductAttributeMappingId(String productAttributeMappingId) {
		this.productAttributeMappingId = productAttributeMappingId;
	}
	public String getAttributeKeyId() {
		return attributeKeyId;
	}
	public void setAttributeKeyId(String attributeKeyId) {
		this.attributeKeyId = attributeKeyId;
	}
	public String getAttributeValueId() {
		return attributeValueId;
	}
	public void setAttributeValueId(String attributeValueId) {
		this.attributeValueId = attributeValueId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}

}
