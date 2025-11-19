package com.fuso.enterprise.ots.srv.api.model.domain;

public class SearchProductByNamePagination {
	
	private String pageNumber;
	private String dataSize;
	private String productName;
	private String productCountryCode;
	
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getDataSize() {
		return dataSize;
	}
	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCountryCode() {
		return productCountryCode;
	}
	public void setProductCountryCode(String productCountryCode) {
		this.productCountryCode = productCountryCode;
	}

}
