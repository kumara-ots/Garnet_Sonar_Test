package com.fuso.enterprise.ots.srv.api.service.response;

public class GetcartListResponse {
	
	private String productId;
	private String productName;
	private String productImage;
	private String productPrice;
	private Integer otsCartQty;
	private String productGST;
	private String totalGST;
	private String priceWithGst;
	private String totalPrice;
	private Integer deliveryCharge;	//G1
	private String customerId;
	private String productStockQuantity;
	private String otsProductCountry;
    private String otsProductCountryCode;
    private String otsProductCurrency;
    private String otsProductCurrencySymbol;
    
	public String getProductStockQuantity() {
		return productStockQuantity;
	}

	public void setProductStockQuantity(String productStockQuantity) {
		this.productStockQuantity = productStockQuantity;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPriceWithGst() {
		return priceWithGst;
	}

	public void setPriceWithGst(String priceWithGst) {
		this.priceWithGst = priceWithGst;
	}

	public String getProductGST() {
		return productGST;
	}

	public String getTotalGST() {
		return totalGST;
	}

	public void setProductGST(String productGST) {
		this.productGST = productGST;
	}

	public void setTotalGST(String totalGST) {
		this.totalGST = totalGST;
	}
	
	public Integer getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(Integer deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getOtsCartQty() {
		return otsCartQty;
	}

	public void setOtsCartQty(Integer otsCartQty) {
		this.otsCartQty = otsCartQty;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
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
