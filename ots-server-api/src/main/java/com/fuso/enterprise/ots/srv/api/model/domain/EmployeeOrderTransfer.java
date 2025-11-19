package com.fuso.enterprise.ots.srv.api.model.domain;

public class EmployeeOrderTransfer {

	private String EmployeeId;
	
	private String OrderId;

	public String getEmployeeId() {
		return EmployeeId;
	}

	public void setEmployeeId(String employeeId) {
		EmployeeId = employeeId;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	
}
