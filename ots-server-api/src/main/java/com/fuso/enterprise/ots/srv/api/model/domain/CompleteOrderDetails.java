package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class CompleteOrderDetails {
	private String orderId;
	
	private String customerId;
	
	private String orderNumber;
	
	private String orderCost;
	
	private String orderDate;
	
	private String orderDeliverdDate;
	
	private String orderStatus;
	
	private String orderBasePrice;
	
	private String orderDeliveryDate;
	 
	private String paymentStatus;
	 
	private String paymentId;
	
	private String paymentDate;
	 
	private String UserLat;
	 
	private String UserLong;

	private String orderDeliveryCharge;
	  
	private String customerChangeAddressId;
	  
	private String orderProformaInvoice;
	 
	private String customerName;
	 
	private String deliveryAddress;
	 
	private String deliveryPincode;
	 
	private String customerContactNo;
	
	private String orderState;
	
	private String orderDistrict;
	
	List<OrderProductDetails> OrderProductDetails;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getOrderDeliverdDate() {
		return orderDeliverdDate;
	}

	public void setOrderDeliverdDate(String orderDeliverdDate) {
		this.orderDeliverdDate = orderDeliverdDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderBasePrice() {
		return orderBasePrice;
	}

	public void setOrderBasePrice(String orderBasePrice) {
		this.orderBasePrice = orderBasePrice;
	}

	public String getOrderDeliveryDate() {
		return orderDeliveryDate;
	}

	public void setOrderDeliveryDate(String orderDeliveryDate) {
		this.orderDeliveryDate = orderDeliveryDate;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getUserLat() {
		return UserLat;
	}

	public void setUserLat(String userLat) {
		UserLat = userLat;
	}

	public String getUserLong() {
		return UserLong;
	}

	public void setUserLong(String userLong) {
		UserLong = userLong;
	}

	public String getOrderDeliveryCharge() {
		return orderDeliveryCharge;
	}

	public void setOrderDeliveryCharge(String orderDeliveryCharge) {
		this.orderDeliveryCharge = orderDeliveryCharge;
	}

	public String getCustomerChangeAddressId() {
		return customerChangeAddressId;
	}

	public void setCustomerChangeAddressId(String customerChangeAddressId) {
		this.customerChangeAddressId = customerChangeAddressId;
	}

	public String getOrderProformaInvoice() {
		return orderProformaInvoice;
	}

	public void setOrderProformaInvoice(String orderProformaInvoice) {
		this.orderProformaInvoice = orderProformaInvoice;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryPincode() {
		return deliveryPincode;
	}

	public void setDeliveryPincode(String deliveryPincode) {
		this.deliveryPincode = deliveryPincode;
	}

	public String getCustomerContactNo() {
		return customerContactNo;
	}

	public void setCustomerContactNo(String customerContactNo) {
		this.customerContactNo = customerContactNo;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getOrderDistrict() {
		return orderDistrict;
	}

	public void setOrderDistrict(String orderDistrict) {
		this.orderDistrict = orderDistrict;
	}

	public List<OrderProductDetails> getOrderProductDetails() {
		return OrderProductDetails;
	}

	public void setOrderProductDetails(List<OrderProductDetails> orderProductDetails) {
		OrderProductDetails = orderProductDetails;
	}
	
	
}
