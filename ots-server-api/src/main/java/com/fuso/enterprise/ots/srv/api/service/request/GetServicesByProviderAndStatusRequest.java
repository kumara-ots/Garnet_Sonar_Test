package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetServicesByProviderAndStatus;

public class GetServicesByProviderAndStatusRequest {

	private GetServicesByProviderAndStatus request;

	public GetServicesByProviderAndStatus getRequest() {
		return request;
	}

	public void setRequest(GetServicesByProviderAndStatus request) {
		this.request = request;
	}

}
