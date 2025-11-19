package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.SubCategoryAttributeMapping;

public class SubCategoryAttributeResponse {

	private List<SubCategoryAttributeMapping> subcategoryAttributeDetails;

	public List<SubCategoryAttributeMapping> getSubcategoryAttributeDetails() {
		return subcategoryAttributeDetails;
	}

	public void setSubcategoryAttributeDetails(List<SubCategoryAttributeMapping> subcategoryAttributeDetails) {
		this.subcategoryAttributeDetails = subcategoryAttributeDetails;
	}
	
}
