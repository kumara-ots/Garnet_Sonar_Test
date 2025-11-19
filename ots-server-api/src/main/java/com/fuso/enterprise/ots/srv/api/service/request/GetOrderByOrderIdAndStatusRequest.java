package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetOrderByOrderIdAndStatusModel;

public class GetOrderByOrderIdAndStatusRequest {
	private GetOrderByOrderIdAndStatusModel request;

	public GetOrderByOrderIdAndStatusModel getRequest() {
		return request;
	}

	public void setRequest(GetOrderByOrderIdAndStatusModel request) {
		this.request = request;
	}
	
}
