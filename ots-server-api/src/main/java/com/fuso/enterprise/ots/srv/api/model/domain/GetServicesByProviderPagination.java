package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetServicesByProviderPagination {

	private String providerId;
	private String subCategoryId;
	private String pageNumber;
	private String dataSize;

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
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

}
