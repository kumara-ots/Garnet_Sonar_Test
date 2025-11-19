package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class OrderDetailsRequest {

	private String DistributorId;
	private String customerId;
	private String OrderNumber;
	private String AssignedId;
	private String OrderCost;
	private String OrderDate;
	private String DelivaryDate;
	private String DeliverdDate;
	private String OrderStatus;
	private String CustomerName;
	private String userLat;
	private String userLong;
	private String customerLat;
	private String paymentId;
	private String paymentStatus;
	private String paymentFlowStatus;
	private List<OrderedProductDetails> ProductList;
	private String customerChangeAddressId;
	private String customerContactNo;
	private String otsHouseNo;
	private String otsBuildingName;
	private String otsStreetName;
	private String otsCityName;
	private String otsPinCode;
	private String orderState;
	private String orderDistrict;
	private String otsOrderGps;
	private String orderTransactionId;
	private String otsOndcOrderId;
	
	public String getDistributorId() {
		return DistributorId;
	}
	public void setDistributorId(String distributorId) {
		DistributorId = distributorId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getUserLat() {
		return userLat;
	}
	public void setUserLat(String userLat) {
		this.userLat = userLat;
	}
	public String getUserLong() {
		return userLong;
	}
	public void setUserLong(String userLong) {
		this.userLong = userLong;
	}
	public String getCustomerLat() {
		return customerLat;
	}
	public void setCustomerLat(String customerLat) {
		this.customerLat = customerLat;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentFlowStatus() {
		return paymentFlowStatus;
	}
	public void setPaymentFlowStatus(String paymentFlowStatus) {
		this.paymentFlowStatus = paymentFlowStatus;
	}
	public List<OrderedProductDetails> getProductList() {
		return ProductList;
	}
	public void setProductList(List<OrderedProductDetails> productList) {
		ProductList = productList;
	}
	public String getCustomerChangeAddressId() {
		return customerChangeAddressId;
	}
	public void setCustomerChangeAddressId(String customerChangeAddressId) {
		this.customerChangeAddressId = customerChangeAddressId;
	}
	public String getCustomerContactNo() {
		return customerContactNo;
	}
	public void setCustomerContactNo(String customerContactNo) {
		this.customerContactNo = customerContactNo;
	}
	public String getOtsHouseNo() {
		return otsHouseNo;
	}
	public void setOtsHouseNo(String otsHouseNo) {
		this.otsHouseNo = otsHouseNo;
	}
	public String getOtsBuildingName() {
		return otsBuildingName;
	}
	public void setOtsBuildingName(String otsBuildingName) {
		this.otsBuildingName = otsBuildingName;
	}
	public String getOtsStreetName() {
		return otsStreetName;
	}
	public void setOtsStreetName(String otsStreetName) {
		this.otsStreetName = otsStreetName;
	}
	public String getOtsCityName() {
		return otsCityName;
	}
	public void setOtsCityName(String otsCityName) {
		this.otsCityName = otsCityName;
	}
	public String getOtsPinCode() {
		return otsPinCode;
	}
	public void setOtsPinCode(String otsPinCode) {
		this.otsPinCode = otsPinCode;
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
	public String getOtsOrderGps() {
		return otsOrderGps;
	}
	public void setOtsOrderGps(String otsOrderGps) {
		this.otsOrderGps = otsOrderGps;
	}
	public String getOrderTransactionId() {
		return orderTransactionId;
	}
	public void setOrderTransactionId(String orderTransactionId) {
		this.orderTransactionId = orderTransactionId;
	}
	public String getOtsOndcOrderId() {
		return otsOndcOrderId;
	}
	public void setOtsOndcOrderId(String otsOndcOrderId) {
		this.otsOndcOrderId = otsOndcOrderId;
	}
	
}
