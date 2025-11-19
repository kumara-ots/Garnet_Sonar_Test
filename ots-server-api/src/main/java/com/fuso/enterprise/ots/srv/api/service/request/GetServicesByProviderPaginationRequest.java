package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetServicesByProviderPagination;

public class GetServicesByProviderPaginationRequest {

	private GetServicesByProviderPagination request;

	public GetServicesByProviderPagination getRequest() {
		return request;
	}

	public void setRequest(GetServicesByProviderPagination request) {
		this.request = request;
	}

}
