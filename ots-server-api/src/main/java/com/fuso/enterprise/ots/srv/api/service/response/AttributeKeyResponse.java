package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.AttributeKey;

public class AttributeKeyResponse {
	
	private List<AttributeKey> attributeKeyDetails;

	public List<AttributeKey> getAttributeKeyDetails() {
		return attributeKeyDetails;
	}

	public void setAttributeKeyDetails(List<AttributeKey> attributeKeyDetails) {
		this.attributeKeyDetails = attributeKeyDetails;
	}

}
