package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetOrderByStatusAndId;

public class GetOrderByStatusRequest {

	private GetOrderByStatusAndId request;

	public GetOrderByStatusAndId getRequest() {
		return request;
	}

	public void setRequest(GetOrderByStatusAndId request) {
		this.request = request;
	}
	
}
