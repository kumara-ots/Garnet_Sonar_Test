package com.fuso.enterprise.ots.srv.api.model.domain;

public class CustomerProductDetails {
	
	String userId;
	String productId;
	String productPrice;
	String productname;
	String customerProductId;
	String ProductDefault;
	String CustomerBalanceCan;
	
	public String getProductDefault() {
		return ProductDefault;
	}
	public void setProductDefault(String productDefault) {
		ProductDefault = productDefault;
	}
	public String getCustomerBalanceCan() {
		return CustomerBalanceCan;
	}
	public void setCustomerBalanceCan(String customerBalanceCan) {
		CustomerBalanceCan = customerBalanceCan;
	}
	public String getCustomerProductId() {
		return customerProductId;
	}
	public void setCustomerProductId(String customerProductId) {
		this.customerProductId = customerProductId;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	
}
