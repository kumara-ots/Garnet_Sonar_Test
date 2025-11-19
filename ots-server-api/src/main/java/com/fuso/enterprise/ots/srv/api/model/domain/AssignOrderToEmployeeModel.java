package com.fuso.enterprise.ots.srv.api.model.domain;

public class AssignOrderToEmployeeModel {
	private String OrderId;
	private String DistributorId;
	private String AssignedId;
	private String OrderStatus;
	private String ProductId;
	private String CustomerId;
	private String BillOfSupply;
	
	public String getBillOfSupply() {
		return BillOfSupply;
	}
	public void setBillOfSupply(String billOfSupply) {
		BillOfSupply = billOfSupply;
	}
	public String getCustomerId() {
		return CustomerId;
	}
	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getDistributorId() {
		return DistributorId;
	}
	public void setDistributorId(String distributorId) {
		DistributorId = distributorId;
	}
	public String getAssignedId() {
		return AssignedId;
	}
	public void setAssignedId(String assignedId) {
		AssignedId = assignedId;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	
}
