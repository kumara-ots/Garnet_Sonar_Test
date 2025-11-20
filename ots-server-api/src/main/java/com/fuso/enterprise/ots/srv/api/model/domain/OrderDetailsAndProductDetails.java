package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class OrderDetailsAndProductDetails {

	private String orderId;
	private String customerId;
	private String orderNumber;
	private String orderCost;
	private String orderDate;
	private String delivaryDate;
	private String delivaredDate;
	private String orderStatus;
	private String orderedQty;  
    private String payementStatus;
    private String paymentId;
    private String paymentDate;
    private String customerOrderInvoice;
    private UserDetails distributorDetails;
    private UserDetails employeeDetails;
    private UserDetails customerDetails;
    private UserDetails donarDetails;
    private List<OrderProductDetails> orderdProducts; 
    private String orderdeliveryCharge;
    private List<CustomerChangeAddress> customerSecondaryAddress;
    private String customerName;
    private String deliveryAddress;
    private String deliveryPincode;
    private String deliveryState;
    private String deliveryDistrict;
    private String customerContactNo;
    private String customerEmailId;
    
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
	public String getDelivaryDate() {
		return delivaryDate;
	}
	public void setDelivaryDate(String delivaryDate) {
		this.delivaryDate = delivaryDate;
	}
	public String getDelivaredDate() {
		return delivaredDate;
	}
	public void setDelivaredDate(String delivaredDate) {
		this.delivaredDate = delivaredDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderedQty() {
		return orderedQty;
	}
	public void setOrderedQty(String orderedQty) {
		this.orderedQty = orderedQty;
	}
	public String getPayementStatus() {
		return payementStatus;
	}
	public void setPayementStatus(String payementStatus) {
		this.payementStatus = payementStatus;
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
	public String getCustomerOrderInvoice() {
		return customerOrderInvoice;
	}
	public void setCustomerOrderInvoice(String customerOrderInvoice) {
		this.customerOrderInvoice = customerOrderInvoice;
	}
	public UserDetails getDistributorDetails() {
		return distributorDetails;
	}
	public void setDistributorDetails(UserDetails distributorDetails) {
		this.distributorDetails = distributorDetails;
	}
	public UserDetails getEmployeeDetails() {
		return employeeDetails;
	}
	public void setEmployeeDetails(UserDetails employeeDetails) {
		this.employeeDetails = employeeDetails;
	}
	public UserDetails getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(UserDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	public UserDetails getDonarDetails() {
		return donarDetails;
	}
	public void setDonarDetails(UserDetails donarDetails) {
		this.donarDetails = donarDetails;
	}
	public List<OrderProductDetails> getOrderdProducts() {
		return orderdProducts;
	}
	public void setOrderdProducts(List<OrderProductDetails> orderdProducts) {
		this.orderdProducts = orderdProducts;
	}
	public String getOrderdeliveryCharge() {
		return orderdeliveryCharge;
	}
	public void setOrderdeliveryCharge(String orderdeliveryCharge) {
		this.orderdeliveryCharge = orderdeliveryCharge;
	}
	public List<CustomerChangeAddress> getCustomerSecondaryAddress() {
		return customerSecondaryAddress;
	}
	public void setCustomerSecondaryAddress(List<CustomerChangeAddress> customerSecondaryAddress) {
		this.customerSecondaryAddress = customerSecondaryAddress;
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
	public String getDeliveryState() {
		return deliveryState;
	}
	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}
	public String getDeliveryDistrict() {
		return deliveryDistrict;
	}
	public void setDeliveryDistrict(String deliveryDistrict) {
		this.deliveryDistrict = deliveryDistrict;
	}
	public String getCustomerContactNo() {
		return customerContactNo;
	}
	public void setCustomerContactNo(String customerContactNo) {
		this.customerContactNo = customerContactNo;
	}
	public String getCustomerEmailId() {
		return customerEmailId;
	}
	public void setCustomerEmailId(String customerEmailId) {
		this.customerEmailId = customerEmailId;
	}
   
}
