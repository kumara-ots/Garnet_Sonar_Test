package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.GetAttributeKeysAndAttributeValues;

public class GetAttributeKeysAndAttributeValuesResponse {
	private List<GetAttributeKeysAndAttributeValues> response;

	public List<GetAttributeKeysAndAttributeValues> getResponse() {
		return response;
	}

	public void setResponse(List<GetAttributeKeysAndAttributeValues> response) {
		this.response = response;
	}
	
}
