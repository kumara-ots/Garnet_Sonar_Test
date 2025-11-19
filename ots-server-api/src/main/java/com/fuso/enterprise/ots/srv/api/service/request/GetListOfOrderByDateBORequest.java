package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetListOfOrderRequestModel;

public class GetListOfOrderByDateBORequest {

	private GetListOfOrderRequestModel request;

	public GetListOfOrderRequestModel getRequest() {
		return request;
	}

	public void setRequest(GetListOfOrderRequestModel request) {
		this.request = request;
	}
}
