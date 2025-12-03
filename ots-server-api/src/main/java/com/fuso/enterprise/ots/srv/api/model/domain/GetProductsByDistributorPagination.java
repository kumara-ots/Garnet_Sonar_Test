package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetProductsByDistributorPagination {
	private String searchKey;
	private String searchValue;
	private String distributorId;
	private String pageNumber;
	private String dataSize;
	private String productCountryCode;
	
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}
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
	public String getProductCountryCode() {
		return productCountryCode;
	}
	public void setProductCountryCode(String productCountryCode) {
		this.productCountryCode = productCountryCode;
	}
	
}
