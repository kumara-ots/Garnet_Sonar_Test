package com.fuso.enterprise.ots.srv.api.service.request;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValue;

public class AddAttributeValueRequest {
	private List<AttributeValue> request;

	public List<AttributeValue> getRequest() {
		return request;
	}

	public void setRequest(List<AttributeValue> request) {
		this.request = request;
	}
	
}