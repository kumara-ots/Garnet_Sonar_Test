package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

import javax.validation.constraints.Size;

public class CompleteOrderDetails {
	 @Size(max = 20)
	 private String orderId;
	 
	 @Size(max = 20)
	 private String distributorId;
	 
	 @Size(max = 20)
	 private String customerId;
	 
	 @Size(max = 20)
	 private String orderNumber;
	 
	 @Size(max = 20)
	 private String assignedId;
	 
	 @Size(max = 20)
	 private UserDetails distributorDetails;
	 
	 @Size(max = 20)
	 private UserDetails customerDetails;
	
	 @Size(max = 20)
	 private UserDetails employeeDetails;

     @Size(max = 20)
	 private String orderCost;
	 
	 @Size(max = 20)
	 private String orderDate;
	 
	 @Size(max = 20)
	 private String orderDeliveryDate;
	 
	 @Size(max = 20)
	 private String orderDeliverdDate;
	 
	 @Size(max = 20)
	 private String status;
	 
	 @Size(max = 20)
	 private String billId;
	 
	 @Size(max = 20)
	 private String CreatedBy;
	 
	 @Size(max = 20)
	 private String outstandingAmount;
	 
	 @Size(max = 20)
	 private String orderBalanceCan;
	
	 @Size(max = 20)
	 private String orderOutstandingAmount;
	 
	 @Size(max = 20)
	 private String orderAmountReceived;
	 
	public String getOrderAmountReceived() {
		return orderAmountReceived;
	}

	public void setOrderAmountReceived(String orderAmountReceived) {
		this.orderAmountReceived = orderAmountReceived;
	}

	public String getOrderOutstandingAmount() {
		return orderOutstandingAmount;
	}

	public void setOrderOutstandingAmount(String orderOutstandingAmount) {
		this.orderOutstandingAmount = orderOutstandingAmount;
	}

	public String getOrderBalanceCan() {
		return orderBalanceCan;
	}

	public void setOrderBalanceCan(String orderBalanceCan) {
		this.orderBalanceCan = orderBalanceCan;
	}

	public String getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	@Size(max = 20)
	 List<OrderProductDetails> OrderProductDetails;

	public List<OrderProductDetails> getOrderProductDetails() {
		return OrderProductDetails;
	}

	public void setOrderProductDetails(List<OrderProductDetails> orderProductDetails) {
		OrderProductDetails = orderProductDetails;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getAssignedId() {
		return assignedId;
	}

	public void setAssignedId(String assignedId) {
		this.assignedId = assignedId;
	}

	public String getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(String orderCost) {
		this.orderCost = orderCost;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDeliveryDate() {
		return orderDeliveryDate;
	}

	public void setOrderDeliveryDate(String orderDeliveryDate) {
		this.orderDeliveryDate = orderDeliveryDate;
	}

	public String getOrderDeliverdDate() {
		return orderDeliverdDate;
	}

	public void setOrderDeliverdDate(String orderDeliverdDate) {
		this.orderDeliverdDate = orderDeliverdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}
	
	 public UserDetails getDistributorDetails() {
		return distributorDetails;
	}

	public void setDistributorDetails(UserDetails distributorDetails) {
		this.distributorDetails = distributorDetails;
	}

	public UserDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(UserDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public UserDetails getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(UserDetails employeeDetails) {
		this.employeeDetails = employeeDetails;
	}
}
