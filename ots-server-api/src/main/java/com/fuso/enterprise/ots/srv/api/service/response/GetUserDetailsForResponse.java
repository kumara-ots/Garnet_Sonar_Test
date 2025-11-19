package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.RequestProductDetails;

public class GetUserDetailsForResponse {
	
	private List<RequestProductDetails> requestProductDetails;

	public List<RequestProductDetails> getRequestProductDetails() {
		return requestProductDetails;
	}

	public void setRequestProductDetails(List<RequestProductDetails> requestProductDetails) {
		this.requestProductDetails = requestProductDetails;
	}

	
}
