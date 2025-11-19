package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class AddOrUpdateProduct {
	private AddProductDetails productDetails;
	private ProductPricingDetails productPricingDetails;
	private ProductPolicy productPolicy;
	private ProductManufactureDetails productManufactureDetails;
	private List<ProductAttributesMapping> attributeMapping;
	
	public AddProductDetails getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(AddProductDetails productDetails) {
		this.productDetails = productDetails;
	}
	public ProductPricingDetails getProductPricingDetails() {
		return productPricingDetails;
	}
	public void setProductPricingDetails(ProductPricingDetails productPricingDetails) {
		this.productPricingDetails = productPricingDetails;
	}
	public ProductPolicy getProductPolicy() {
		return productPolicy;
	}
	public void setProductPolicy(ProductPolicy productPolicy) {
		this.productPolicy = productPolicy;
	}
	public ProductManufactureDetails getProductManufactureDetails() {
		return productManufactureDetails;
	}
	public void setProductManufactureDetails(ProductManufactureDetails productManufactureDetails) {
		this.productManufactureDetails = productManufactureDetails;
	}
	public List<ProductAttributesMapping> getAttributeMapping() {
		return attributeMapping;
	}
	public void setAttributeMapping(List<ProductAttributesMapping> attributeMapping) {
		this.attributeMapping = attributeMapping;
	}

}
