package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetServicesBySubCategoryAndProvider;

public class GetServicesBySubCategoryAndProviderRequest {

	private GetServicesBySubCategoryAndProvider request;

	public GetServicesBySubCategoryAndProvider getRequest() {
		return request;
	}

	public void setRequest(GetServicesBySubCategoryAndProvider request) {
		this.request = request;
	}

}
