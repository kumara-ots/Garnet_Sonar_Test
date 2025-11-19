package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetProductsByDistributerPagination {
	private String distributerId;
	private String subCategoryId;
	private String pageNumber;
	private String dataSize;
	private String productCountryCode;
	
	public String getDistributerId() {
		return distributerId;
	}
	public void setDistributerId(String distributerId) {
		this.distributerId = distributerId;
	}
	public String getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
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
