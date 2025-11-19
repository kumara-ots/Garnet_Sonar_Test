package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.SearchProductByNamePagination;

public class SearchProductByNamePaginationRequest {
	
	private SearchProductByNamePagination request;

	public SearchProductByNamePagination getRequest() {
		return request;
	}

	public void setRequest(SearchProductByNamePagination request) {
		this.request = request;
	}

}
