package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateOrderDetailsModelRequest {
	private String OrderId;
	private String distributorId;
	private String CustomerId;
	private String OrderNumber;
	private String AssignedId;
	private String OrderCost;
	private String OrderDate;
	private String DeliveryDate;
	private String DeliverdDate;
	private String OrderStatus;
	private String paymentFlowStatus;
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
	public String getCustomerId() {
		return CustomerId;
	}
	public void setCustomerId(String customerId) {
		CustomerId = customerId;
	}
	public String getOrderNumber() {
		return OrderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		OrderNumber = orderNumber;
	}
	public String getAssignedId() {
		return AssignedId;
	}
	public void setAssignedId(String assignedId) {
		AssignedId = assignedId;
	}
	public String getOrderCost() {
		return OrderCost;
	}
	public void setOrderCost(String orderCost) {
		OrderCost = orderCost;
	}
	public String getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}
	public String getDeliveryDate() {
		return DeliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		DeliveryDate = deliveryDate;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	public String getDeliverdDate() {
		return DeliverdDate;
	}
	public void setDeliverdDate(String deliverdDate) {
		DeliverdDate = deliverdDate;
	}
	public String getPaymentFlowStatus() {
		return paymentFlowStatus;
	}
	public void setPaymentFlowStatus(String paymentFlowStatus) {
		this.paymentFlowStatus = paymentFlowStatus;
	}	
	
}
