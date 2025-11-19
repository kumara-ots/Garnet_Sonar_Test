package com.fuso.enterprise.ots.srv.api.model.domain;

public class Productcart 
{
	 String productId;
	 String productQty;
	 String productExpectDate;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductQty() {
		return productQty;
	}
	public void setProductQty(String productQty) {
		this.productQty = productQty;
	}
	public String getProductExpectDate() {
		return productExpectDate;
	}
	public void setProductExpectDate(String productExpectDate) {
		this.productExpectDate = productExpectDate;
	}

}