package com.fuso.enterprise.ots.srv.api.model.domain;

public class GetServicesBySubCategoryAndProvider {

	private String SubcategoryId;
	private String ProviderId;

	public String getSubcategoryId() {
		return SubcategoryId;
	}

	public void setSubcategoryId(String subcategoryId) {
		SubcategoryId = subcategoryId;
	}

	public String getProviderId() {
		return ProviderId;
	}

	public void setProviderId(String providerId) {
		ProviderId = providerId;
	}

}
