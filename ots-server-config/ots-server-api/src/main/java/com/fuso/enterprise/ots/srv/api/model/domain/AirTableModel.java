package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.Date;
import java.util.List;

public class AirTableModel {

	private String airtabelId;
	
	private String productId;
	
	private String productStock;

	private String productImage;
	
	private String productGst;
	
	private String productName;
	
	private String productPrice;
	
	private String productProducerName;
	
	private String productCategoryId;
	
	private String productCategoryname;
	
	private String productSubCategoryId;
	
	private String productSubCategoryName;
	
	private Date addedDate;
	
	private String previousDayFlag;
	
	private String transactionId;
	
	private String airTableProductCaluclatedStock;
	
	private String newCategoryId;
	
	private String subCategoryId;
	
	private String newProductId;

	private String tempLevelId;
	
	private String currentDayStock;
	
	private List<String> productImageList;
	
	public String getNewCategoryId() {
		return newCategoryId;
	}

	public void setNewCategoryId(String newCategoryId) {
		this.newCategoryId = newCategoryId;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getNewProductId() {
		return newProductId;
	}

	public void setNewProductId(String newProductId) {
		this.newProductId = newProductId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductStock() {
		return productStock;
	}

	public void setProductStock(String productStock) {
		this.productStock = productStock;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductGst() {
		return productGst;
	}

	public void setProductGst(String productGst) {
		this.productGst = productGst;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductProducerName() {
		return productProducerName;
	}

	public void setProductProducerName(String productProducerName) {
		this.productProducerName = productProducerName;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getProductCategoryname() {
		return productCategoryname;
	}

	public void setProductCategoryname(String productCategoryname) {
		this.productCategoryname = productCategoryname;
	}

	public String getProductSubCategoryId() {
		return productSubCategoryId;
	}

	public void setProductSubCategoryId(String productSubCategoryId) {
		this.productSubCategoryId = productSubCategoryId;
	}

	public String getProductSubCategoryName() {
		return productSubCategoryName;
	}

	public void setProductSubCategoryName(String productSubCategoryName) {
		this.productSubCategoryName = productSubCategoryName;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public String getAirtabelId() {
		return airtabelId;
	}

	public void setAirtabelId(String airtabelId) {
		this.airtabelId = airtabelId;
	}

	public String getPreviousDayFlag() {
		return previousDayFlag;
	}

	public void setPreviousDayFlag(String previousDayFlag) {
		this.previousDayFlag = previousDayFlag;
	}

	public String getAirTableProductCaluclatedStock() {
		return airTableProductCaluclatedStock;
	}

	public void setAirTableProductCaluclatedStock(String airTableProductCaluclatedStock) {
		this.airTableProductCaluclatedStock = airTableProductCaluclatedStock;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTempLevelId() {
		return tempLevelId;
	}

	public void setTempLevelId(String tempLevelId) {
		this.tempLevelId = tempLevelId;
	}

	public List<String> getProductImageList() {
		return productImageList;
	}

	public void setProductImageList(List<String> productImageList) {
		this.productImageList = productImageList;
	}

	public String getCurrentDayStock() {
		return currentDayStock;
	}

	public void setCurrentDayStock(String currentDayStock) {
		this.currentDayStock = currentDayStock;
	}
	
	
}
