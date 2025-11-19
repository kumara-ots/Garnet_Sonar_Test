package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.RequestUserProductDetailsRequest;

public class GetUserDetailsForRequest {
	
	private RequestUserProductDetailsRequest requestId;

	public RequestUserProductDetailsRequest getRequestId() {
		return requestId;
	}

	public void setRequestId(RequestUserProductDetailsRequest requestId) {
		this.requestId = requestId;
	}

}
