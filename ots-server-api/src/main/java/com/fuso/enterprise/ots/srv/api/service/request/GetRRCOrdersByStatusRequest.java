package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetRRCOrdersByStatus;

public class GetRRCOrdersByStatusRequest {
	private GetRRCOrdersByStatus request;

	public GetRRCOrdersByStatus getRequest() {
		return request;
	}

	public void setRequest(GetRRCOrdersByStatus request) {
		this.request = request;
	}
	
}
