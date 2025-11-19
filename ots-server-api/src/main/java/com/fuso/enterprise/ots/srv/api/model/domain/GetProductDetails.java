package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetProductDetails {
	private String searchKey;
	private String searchvalue;
	private String status;
	private String dataSize;
	private String pageNumber;
	private String productType;
	private String productLevel;
	private String productCountryCode;
	
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getSearchvalue() {
		return searchvalue;
	}
	public void setSearchvalue(String searchvalue) {
		this.searchvalue = searchvalue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDataSize() {
		return dataSize;
	}
	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductLevel() {
		return productLevel;
	}
	public void setProductLevel(String productLevel) {
		this.productLevel = productLevel;
	}
	public String getProductCountryCode() {
		return productCountryCode;
	}
	public void setProductCountryCode(String productCountryCode) {
		this.productCountryCode = productCountryCode;
	}

}
