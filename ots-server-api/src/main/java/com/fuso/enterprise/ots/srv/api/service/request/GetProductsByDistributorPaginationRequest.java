package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetProductsByDistributorPagination;

public class GetProductsByDistributorPaginationRequest {
	private GetProductsByDistributorPagination request;

	public GetProductsByDistributorPagination getRequest() {
		return request;
	}

	public void setRequest(GetProductsByDistributorPagination request) {
		this.request = request;
	}
	
}
