package com.fuso.enterprise.ots.srv.api.service.request;

public class GetProductSellerRequest
{
	String CreatedUser;
	
	String ProductId;
	String SellerId;
	
	
	public String getCreatedUser() {
		return CreatedUser;
	}
	public void setCreatedUser(String createdUser) {
		CreatedUser = createdUser;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getSellerId() {
		return SellerId;
	}
	public void setSellerId(String sellerId) {
		SellerId = sellerId;
	}
	

}
