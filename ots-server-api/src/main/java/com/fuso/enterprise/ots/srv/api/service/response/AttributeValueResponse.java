package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValue;

public class AttributeValueResponse {
	
	private List<AttributeValue> attributeValueDetails;

	public List<AttributeValue> getAttributeValueDetails() {
		return attributeValueDetails;
	}

	public void setAttributeValueDetails(List<AttributeValue> attributeValueDetails) {
		this.attributeValueDetails = attributeValueDetails;
	}

}
