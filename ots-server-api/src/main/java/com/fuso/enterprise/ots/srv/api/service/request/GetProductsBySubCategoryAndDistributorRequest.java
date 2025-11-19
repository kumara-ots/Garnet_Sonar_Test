package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetProductsBySubCategoryAndDistributor;

public class GetProductsBySubCategoryAndDistributorRequest {
	private GetProductsBySubCategoryAndDistributor request;

	public GetProductsBySubCategoryAndDistributor getRequest() {
		return request;
	}

	public void setRequest(GetProductsBySubCategoryAndDistributor request) {
		this.request = request;
	}

}