package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class BillOrderModelDetails {

	private String OrderId;
	
	private String OrderNumber;
	
	private UserDetails  DistriutorDetails;
	
	private UserDetails CustomerDetails;
	
	private UserDetails EmployeeDetails;
	
	private String OrderCost;
	
	private String OrderDate;
	
	private String DelivaryDate;
	
	private String DeliverdDate;
	
	private String OrderStatus;
	
	private List<OrderProductDetails> orderProductDetails;


	public UserDetails getDistriutorDetails() {
		return DistriutorDetails;
	}

	public void setDistriutorDetails(UserDetails distriutorDetails) {
		DistriutorDetails = distriutorDetails;
	}

	public UserDetails getCustomerDetails() {
		return CustomerDetails;
	}

	public void setCustomerDetails(UserDetails customerDetails) {
		CustomerDetails = customerDetails;
	}

	public UserDetails getEmployeeDetails() {
		return EmployeeDetails;
	}

	public void setEmployeeDetails(UserDetails employeeDetails) {
		EmployeeDetails = employeeDetails;
	}

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}


	public String getOrderNumber() {
		return OrderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		OrderNumber = orderNumber;
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

	public String getDelivaryDate() {
		return DelivaryDate;
	}

	public void setDelivaryDate(String delivaryDate) {
		DelivaryDate = delivaryDate;
	}

	public String getDeliverdDate() {
		return DeliverdDate;
	}

	public void setDeliverdDate(String deliverdDate) {
		DeliverdDate = deliverdDate;
	}

	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}

	public List<OrderProductDetails> getOrderProductDetails() {
		return orderProductDetails;
	}

	public void setOrderProductDetails(List<OrderProductDetails> orderProductDetails) {
		this.orderProductDetails = orderProductDetails;
	}
}