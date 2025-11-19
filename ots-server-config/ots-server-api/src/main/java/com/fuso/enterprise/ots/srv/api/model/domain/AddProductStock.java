package com.fuso.enterprise.ots.srv.api.model.domain;

import java.sql.Date;

public class AddProductStock {

	private Date ProductStockAddDate;
	
	private String ProductStockQty; 
	
	private String ProductStockStatus;
	
	private String UsersId; 
	
	private String ProductId;
	
	private String OrderId;
	
	public String getProductStockQty() {
		return ProductStockQty;
	}
	public void setProductStockQty(String productStockQty) {
		ProductStockQty = productStockQty;
	}
	public String getProductStockStatus() {
		return ProductStockStatus;
	}
	public void setProductStockStatus(String productStockStatus) {
		ProductStockStatus = productStockStatus;
	}
	
	public String getUsersId() {
		return UsersId;
	}
	public void setUsersId(String usersId) {
		UsersId = usersId;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public Date getProductStockAddDate() {
		return ProductStockAddDate;
	}
	public void setProductStockAddDate(Date productStockAddDate) {
		ProductStockAddDate = productStockAddDate;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	
	
}
