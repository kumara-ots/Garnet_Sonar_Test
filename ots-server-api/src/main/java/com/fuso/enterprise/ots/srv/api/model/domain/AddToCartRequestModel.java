package com.fuso.enterprise.ots.srv.api.model.domain;

public class AddToCartRequestModel 
{
	
	private Integer otsCartId;
	
	private String customerId;

	private String productId;
	
	private Integer otsCartQty;
	
	private String productPrice;

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getOtsCartQty() {
		return otsCartQty;
	}

	public void setOtsCartQty(Integer otsCartQty) {
		this.otsCartQty = otsCartQty;
	}

	public Integer getOtsCartId() {
		return otsCartId;
	}

	public void setOtsCartId(Integer otsCartId) {
		this.otsCartId = otsCartId;
	}

	
	
}
