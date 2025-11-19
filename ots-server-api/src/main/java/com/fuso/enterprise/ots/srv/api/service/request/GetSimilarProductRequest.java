package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetSimilarProductRequestModel;

public class GetSimilarProductRequest {

	private GetSimilarProductRequestModel request;

	public GetSimilarProductRequestModel getRequest() {
		return request;
	}

	public void setRequest(GetSimilarProductRequestModel request) {
		this.request = request;
	}

}
