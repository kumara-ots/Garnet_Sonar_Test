package com.fuso.enterprise.ots.srv.api.service.response;

public class GetProductBOStockResponse {

	private String ProductStockId;
	private String StockQuantity;
	private String ProductStockStatus;
	private String UserId;
	private String ProductId;
	
	public String getProductStockId() {
		return ProductStockId;
	}
	public void setProductStockId(String productStockId) {
		ProductStockId = productStockId;
	}
	public String getStockQuantity() {
		return StockQuantity;
	}
	public void setStockQuantity(String stockQuantity) {
		StockQuantity = stockQuantity;
	}
	public String getProductStockStatus() {
		return ProductStockStatus;
	}
	public void setProductStockStatus(String productStockStatus) {
		ProductStockStatus = productStockStatus;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	
	
}
