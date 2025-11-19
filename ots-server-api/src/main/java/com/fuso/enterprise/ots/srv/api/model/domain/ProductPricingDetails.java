package com.fuso.enterprise.ots.srv.api.model.domain;

public class ProductPricingDetails {
	
	private String productId;
	
	private String productStatus;
	
	private String productSellerPrice;
	 
	private String productBasePrice;
	
	private String productPrice;
	
	private String productDiscountPercentage;
	 
	private String productDiscountPrice;
	 
	private String gst;
	
	private String gstPrice;
	
	private String productFinalPriceWithGst;
		
	private String productDeliveryCharge;
	
	private String productReturnDeliveryCharge;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
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

	public String getProductDiscountPercentage() {
		return productDiscountPercentage;
	}

	public void setProductDiscountPercentage(String productDiscountPercentage) {
		this.productDiscountPercentage = productDiscountPercentage;
	}

	public String getProductDiscountPrice() {
		return productDiscountPrice;
	}

	public void setProductDiscountPrice(String productDiscountPrice) {
		this.productDiscountPrice = productDiscountPrice;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getGstPrice() {
		return gstPrice;
	}

	public void setGstPrice(String gstPrice) {
		this.gstPrice = gstPrice;
	}

	
	public String getProductFinalPriceWithGst() {
		return productFinalPriceWithGst;
	}

	public void setProductFinalPriceWithGst(String productFinalPriceWithGst) {
		this.productFinalPriceWithGst = productFinalPriceWithGst;
	}

	public String getProductDeliveryCharge() {
		return productDeliveryCharge;
	}

	public void setProductDeliveryCharge(String productDeliveryCharge) {
		this.productDeliveryCharge = productDeliveryCharge;
	}

	public String getProductReturnDeliveryCharge() {
		return productReturnDeliveryCharge;
	}

	public void setProductReturnDeliveryCharge(String productReturnDeliveryCharge) {
		this.productReturnDeliveryCharge = productReturnDeliveryCharge;
	}
	
}
