package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetProductsByDistributerPagination;

public class GetProductsByDistributerPaginationRequest {
	private GetProductsByDistributerPagination request;

	public GetProductsByDistributerPagination getRequest() {
		return request;
	}

	public void setRequest(GetProductsByDistributerPagination request) {
		this.request = request;
	}
	
}
