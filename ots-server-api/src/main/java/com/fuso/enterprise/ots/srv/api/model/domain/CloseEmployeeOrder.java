package com.fuso.enterprise.ots.srv.api.model.domain;

public class CloseEmployeeOrder {
	private String OrderId;

	private String ProductId;
	
//	private String EmployeeId;
	
	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}

//	public String getEmployeeId() {
//		return EmployeeId;
//	}
//
//	public void setEmployeeId(String employeeId) {
//		EmployeeId = employeeId;
//	}
}

