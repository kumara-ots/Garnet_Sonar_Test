package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;
import java.util.Map;

import com.fuso.enterprise.ots.srv.api.service.response.GetReviewAndRatingResponse;

public class OrderProductDetails {
	
	private String otsOrderProductId;
	 
    private String otsOrderedQty;
  
    private String otsDeliveredQty;

    private String otsOrderProductCost;
  
    private String otsOrderProductStatus;

    private String otsOrderId;
    
    private String otsOrderNumber;

    private String otsProductId;
    
    private String otsSubOrderId;
    
    private String productName;
    
    private String productImage;
    
    private List<Map<String, Object>> productAttribute;
    
    private String userLat;
    
    private String userLong;
    //G1
    private String distributorId;	
    
    private String assignedId;
    
    private String subOrderDeliveredDate;
    
    private String subOrderPickupDate;
    
    private String subOrderOfdDate;
    
    private String subOrderAssignedDate;

    private String otsOrderDate;

	private String distributorFirstName;
    
    private String distributorLastName;
    
    private String distributorEmailId;
    
    private String billOfSupply;
    
    private Boolean otsProductCancellationAvailability;
    
    private Boolean otsProductReplacementAvailability;
    
    private String otsProductReplacementDays;
    
    private Boolean otsProductReturnAvailability;
    
    private String otsProductReturnDays;
    
    private String employeeFirstName;
    
    private String employeeSecondName;
    
    private String employeeEmailId;
    
    private String orderCancelledBy;
    
    private String rrcOrderStatus;
    
    private String rrcCustomerInitiatedDate;
    
    private String rrcDistributorInitiatedDate;
    
    private String productSellerPrice;
    
    private String productBasePrice;
   
    private String productPrice;
    
    private String productPriceWithoutGst;
    
    private Double tenPercTranscation;
    
    private Double onePerTdsOnProductPrice;
    
    private Double eighteenPercGstOnTenPercTranscation;
    
    private String customerId;
    
    private List<GetReviewAndRatingResponse> productReviewRating;
    
    private String customerName;
    
    private String deliveryAddress;
    
    private String customerContactNo;
    
    private String customerEmailId;
    
    private String orderProductCustomerInvoice;
    
    private String otsTrackingLogistics;
    
    private String otsTrackingUrl;
    
    private String otsTrackingId;
    
    private String otsProductReturnDeliveryCharge;
    
    private String otsProductName;
    
    private String otsProductImage;
    
    private String otsProductDeliveryCharge;
    
    private String otsProductGst;
    
    private String otsProductGstPrice;
    
    private String otsProductPercentage;
    
    private String otsProductDiscountPrice;
    
    private String otsOrderProductUpdated;
    
    private String otsDistributorRrcUpdatedDate;
    
    private String otsDistributorRrcPickupDate;
    
    private String otsSettlementStatus;
    
    private String otsSettlementDate;
    
    private String otsOndcFulfillmentId;
    
    private String otsOndcFulfillmentStatus;
    
    private String otsProductCountry;

    private String otsProductCountryCode;

    private String otsProductCurrency;

    private String otsProductCurrencySymbol;

	public String getOtsOrderProductId() {
		return otsOrderProductId;
	}

	public void setOtsOrderProductId(String otsOrderProductId) {
		this.otsOrderProductId = otsOrderProductId;
	}

	public String getOtsOrderedQty() {
		return otsOrderedQty;
	}

	public void setOtsOrderedQty(String otsOrderedQty) {
		this.otsOrderedQty = otsOrderedQty;
	}

	public String getOtsDeliveredQty() {
		return otsDeliveredQty;
	}

	public void setOtsDeliveredQty(String otsDeliveredQty) {
		this.otsDeliveredQty = otsDeliveredQty;
	}

	public String getOtsOrderProductCost() {
		return otsOrderProductCost;
	}

	public void setOtsOrderProductCost(String otsOrderProductCost) {
		this.otsOrderProductCost = otsOrderProductCost;
	}

	public String getOtsOrderProductStatus() {
		return otsOrderProductStatus;
	}

	public void setOtsOrderProductStatus(String otsOrderProductStatus) {
		this.otsOrderProductStatus = otsOrderProductStatus;
	}

	public String getOtsOrderId() {
		return otsOrderId;
	}

	public void setOtsOrderId(String otsOrderId) {
		this.otsOrderId = otsOrderId;
	}

	public String getOtsOrderNumber() {
		return otsOrderNumber;
	}

	public void setOtsOrderNumber(String otsOrderNumber) {
		this.otsOrderNumber = otsOrderNumber;
	}

	public String getOtsProductId() {
		return otsProductId;
	}

	public void setOtsProductId(String otsProductId) {
		this.otsProductId = otsProductId;
	}

	public String getOtsSubOrderId() {
		return otsSubOrderId;
	}

	public void setOtsSubOrderId(String otsSubOrderId) {
		this.otsSubOrderId = otsSubOrderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public List<Map<String, Object>> getProductAttribute() {
		return productAttribute;
	}

	public void setProductAttribute(List<Map<String, Object>> productAttribute) {
		this.productAttribute = productAttribute;
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

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getAssignedId() {
		return assignedId;
	}

	public void setAssignedId(String assignedId) {
		this.assignedId = assignedId;
	}

	public String getSubOrderDeliveredDate() {
		return subOrderDeliveredDate;
	}

	public void setSubOrderDeliveredDate(String subOrderDeliveredDate) {
		this.subOrderDeliveredDate = subOrderDeliveredDate;
	}

	public String getSubOrderPickupDate() {
		return subOrderPickupDate;
	}

	public void setSubOrderPickupDate(String subOrderPickupDate) {
		this.subOrderPickupDate = subOrderPickupDate;
	}

	public String getSubOrderOfdDate() {
		return subOrderOfdDate;
	}

	public void setSubOrderOfdDate(String subOrderOfdDate) {
		this.subOrderOfdDate = subOrderOfdDate;
	}

	public String getSubOrderAssignedDate() {
		return subOrderAssignedDate;
	}

	public void setSubOrderAssignedDate(String subOrderAssignedDate) {
		this.subOrderAssignedDate = subOrderAssignedDate;
	}

	public String getOtsOrderDate() {
		return otsOrderDate;
	}

	public void setOtsOrderDate(String otsOrderDate) {
		this.otsOrderDate = otsOrderDate;
	}

	public String getDistributorFirstName() {
		return distributorFirstName;
	}

	public void setDistributorFirstName(String distributorFirstName) {
		this.distributorFirstName = distributorFirstName;
	}

	public String getDistributorLastName() {
		return distributorLastName;
	}

	public void setDistributorLastName(String distributorLastName) {
		this.distributorLastName = distributorLastName;
	}

	public String getDistributorEmailId() {
		return distributorEmailId;
	}

	public void setDistributorEmailId(String distributorEmailId) {
		this.distributorEmailId = distributorEmailId;
	}

	public String getBillOfSupply() {
		return billOfSupply;
	}

	public void setBillOfSupply(String billOfSupply) {
		this.billOfSupply = billOfSupply;
	}

	public Boolean getOtsProductCancellationAvailability() {
		return otsProductCancellationAvailability;
	}

	public void setOtsProductCancellationAvailability(Boolean otsProductCancellationAvailability) {
		this.otsProductCancellationAvailability = otsProductCancellationAvailability;
	}

	public Boolean getOtsProductReplacementAvailability() {
		return otsProductReplacementAvailability;
	}

	public void setOtsProductReplacementAvailability(Boolean otsProductReplacementAvailability) {
		this.otsProductReplacementAvailability = otsProductReplacementAvailability;
	}

	public String getOtsProductReplacementDays() {
		return otsProductReplacementDays;
	}

	public void setOtsProductReplacementDays(String otsProductReplacementDays) {
		this.otsProductReplacementDays = otsProductReplacementDays;
	}

	public Boolean getOtsProductReturnAvailability() {
		return otsProductReturnAvailability;
	}

	public void setOtsProductReturnAvailability(Boolean otsProductReturnAvailability) {
		this.otsProductReturnAvailability = otsProductReturnAvailability;
	}

	public String getOtsProductReturnDays() {
		return otsProductReturnDays;
	}

	public void setOtsProductReturnDays(String otsProductReturnDays) {
		this.otsProductReturnDays = otsProductReturnDays;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeSecondName() {
		return employeeSecondName;
	}

	public void setEmployeeSecondName(String employeeSecondName) {
		this.employeeSecondName = employeeSecondName;
	}

	public String getEmployeeEmailId() {
		return employeeEmailId;
	}

	public void setEmployeeEmailId(String employeeEmailId) {
		this.employeeEmailId = employeeEmailId;
	}

	public String getOrderCancelledBy() {
		return orderCancelledBy;
	}

	public void setOrderCancelledBy(String orderCancelledBy) {
		this.orderCancelledBy = orderCancelledBy;
	}

	public String getRrcOrderStatus() {
		return rrcOrderStatus;
	}

	public void setRrcOrderStatus(String rrcOrderStatus) {
		this.rrcOrderStatus = rrcOrderStatus;
	}

	public String getRrcCustomerInitiatedDate() {
		return rrcCustomerInitiatedDate;
	}

	public void setRrcCustomerInitiatedDate(String rrcCustomerInitiatedDate) {
		this.rrcCustomerInitiatedDate = rrcCustomerInitiatedDate;
	}

	public String getRrcDistributorInitiatedDate() {
		return rrcDistributorInitiatedDate;
	}

	public void setRrcDistributorInitiatedDate(String rrcDistributorInitiatedDate) {
		this.rrcDistributorInitiatedDate = rrcDistributorInitiatedDate;
	}

	public String getProductSellerPrice() {
		return productSellerPrice;
	}

	public void setProductSellerPrice(String productSellerPrice) {
		this.productSellerPrice = productSellerPrice;
	}

	public String getProductBasePrice() {
		return productBasePrice;
	}

	public void setProductBasePrice(String productBasePrice) {
		this.productBasePrice = productBasePrice;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductPriceWithoutGst() {
		return productPriceWithoutGst;
	}

	public void setProductPriceWithoutGst(String productPriceWithoutGst) {
		this.productPriceWithoutGst = productPriceWithoutGst;
	}

	public Double getTenPercTranscation() {
		return tenPercTranscation;
	}

	public void setTenPercTranscation(Double tenPercTranscation) {
		this.tenPercTranscation = tenPercTranscation;
	}

	public Double getOnePerTdsOnProductPrice() {
		return onePerTdsOnProductPrice;
	}

	public void setOnePerTdsOnProductPrice(Double onePerTdsOnProductPrice) {
		this.onePerTdsOnProductPrice = onePerTdsOnProductPrice;
	}

	public Double getEighteenPercGstOnTenPercTranscation() {
		return eighteenPercGstOnTenPercTranscation;
	}

	public void setEighteenPercGstOnTenPercTranscation(Double eighteenPercGstOnTenPercTranscation) {
		this.eighteenPercGstOnTenPercTranscation = eighteenPercGstOnTenPercTranscation;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<GetReviewAndRatingResponse> getProductReviewRating() {
		return productReviewRating;
	}

	public void setProductReviewRating(List<GetReviewAndRatingResponse> productReviewRating) {
		this.productReviewRating = productReviewRating;
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

	public String getOrderProductCustomerInvoice() {
		return orderProductCustomerInvoice;
	}

	public void setOrderProductCustomerInvoice(String orderProductCustomerInvoice) {
		this.orderProductCustomerInvoice = orderProductCustomerInvoice;
	}

	public String getOtsTrackingLogistics() {
		return otsTrackingLogistics;
	}

	public void setOtsTrackingLogistics(String otsTrackingLogistics) {
		this.otsTrackingLogistics = otsTrackingLogistics;
	}

	public String getOtsTrackingUrl() {
		return otsTrackingUrl;
	}

	public void setOtsTrackingUrl(String otsTrackingUrl) {
		this.otsTrackingUrl = otsTrackingUrl;
	}

	public String getOtsTrackingId() {
		return otsTrackingId;
	}

	public void setOtsTrackingId(String otsTrackingId) {
		this.otsTrackingId = otsTrackingId;
	}

	public String getOtsProductReturnDeliveryCharge() {
		return otsProductReturnDeliveryCharge;
	}

	public void setOtsProductReturnDeliveryCharge(String otsProductReturnDeliveryCharge) {
		this.otsProductReturnDeliveryCharge = otsProductReturnDeliveryCharge;
	}

	public String getOtsProductName() {
		return otsProductName;
	}

	public void setOtsProductName(String otsProductName) {
		this.otsProductName = otsProductName;
	}

	public String getOtsProductImage() {
		return otsProductImage;
	}

	public void setOtsProductImage(String otsProductImage) {
		this.otsProductImage = otsProductImage;
	}

	public String getOtsProductDeliveryCharge() {
		return otsProductDeliveryCharge;
	}

	public void setOtsProductDeliveryCharge(String otsProductDeliveryCharge) {
		this.otsProductDeliveryCharge = otsProductDeliveryCharge;
	}

	public String getOtsProductGst() {
		return otsProductGst;
	}

	public void setOtsProductGst(String otsProductGst) {
		this.otsProductGst = otsProductGst;
	}

	public String getOtsProductGstPrice() {
		return otsProductGstPrice;
	}

	public void setOtsProductGstPrice(String otsProductGstPrice) {
		this.otsProductGstPrice = otsProductGstPrice;
	}

	public String getOtsProductPercentage() {
		return otsProductPercentage;
	}

	public void setOtsProductPercentage(String otsProductPercentage) {
		this.otsProductPercentage = otsProductPercentage;
	}

	public String getOtsProductDiscountPrice() {
		return otsProductDiscountPrice;
	}

	public void setOtsProductDiscountPrice(String otsProductDiscountPrice) {
		this.otsProductDiscountPrice = otsProductDiscountPrice;
	}

	public String getOtsOrderProductUpdated() {
		return otsOrderProductUpdated;
	}

	public void setOtsOrderProductUpdated(String otsOrderProductUpdated) {
		this.otsOrderProductUpdated = otsOrderProductUpdated;
	}

	public String getOtsDistributorRrcUpdatedDate() {
		return otsDistributorRrcUpdatedDate;
	}

	public void setOtsDistributorRrcUpdatedDate(String otsDistributorRrcUpdatedDate) {
		this.otsDistributorRrcUpdatedDate = otsDistributorRrcUpdatedDate;
	}

	public String getOtsDistributorRrcPickupDate() {
		return otsDistributorRrcPickupDate;
	}

	public void setOtsDistributorRrcPickupDate(String otsDistributorRrcPickupDate) {
		this.otsDistributorRrcPickupDate = otsDistributorRrcPickupDate;
	}

	public String getOtsSettlementStatus() {
		return otsSettlementStatus;
	}

	public void setOtsSettlementStatus(String otsSettlementStatus) {
		this.otsSettlementStatus = otsSettlementStatus;
	}

	public String getOtsSettlementDate() {
		return otsSettlementDate;
	}

	public void setOtsSettlementDate(String otsSettlementDate) {
		this.otsSettlementDate = otsSettlementDate;
	}

	public String getOtsOndcFulfillmentId() {
		return otsOndcFulfillmentId;
	}

	public void setOtsOndcFulfillmentId(String otsOndcFulfillmentId) {
		this.otsOndcFulfillmentId = otsOndcFulfillmentId;
	}

	public String getOtsOndcFulfillmentStatus() {
		return otsOndcFulfillmentStatus;
	}

	public void setOtsOndcFulfillmentStatus(String otsOndcFulfillmentStatus) {
		this.otsOndcFulfillmentStatus = otsOndcFulfillmentStatus;
	}

	public String getOtsProductCountry() {
		return otsProductCountry;
	}

	public void setOtsProductCountry(String otsProductCountry) {
		this.otsProductCountry = otsProductCountry;
	}

	public String getOtsProductCountryCode() {
		return otsProductCountryCode;
	}

	public void setOtsProductCountryCode(String otsProductCountryCode) {
		this.otsProductCountryCode = otsProductCountryCode;
	}

	public String getOtsProductCurrency() {
		return otsProductCurrency;
	}

	public void setOtsProductCurrency(String otsProductCurrency) {
		this.otsProductCurrency = otsProductCurrency;
	}

	public String getOtsProductCurrencySymbol() {
		return otsProductCurrencySymbol;
	}

	public void setOtsProductCurrencySymbol(String otsProductCurrencySymbol) {
		this.otsProductCurrencySymbol = otsProductCurrencySymbol;
	}
  
}
