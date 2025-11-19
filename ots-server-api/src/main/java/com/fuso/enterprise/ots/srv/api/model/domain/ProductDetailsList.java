package com.fuso.enterprise.ots.srv.api.model.domain;

public class ProductDetailsList {

	private String productId;
	private String productName;
	private String productqty;
	private String productPrice;
	private String TotalProductPrice;
	private String flag;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductqty() {
		return productqty;
	}
	public void setProductqty(String productqty) {
		this.productqty = productqty;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getTotalProductPrice() {
		return TotalProductPrice;
	}
	public void setTotalProductPrice(String totalProductPrice) {
		TotalProductPrice = totalProductPrice;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
