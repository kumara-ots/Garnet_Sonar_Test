package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class GetAttributeKeysAndAttributeValues {
	private String attributeKeyId ;
	private String attributeKeyName;
	private String attributeKeyDescription;
	private List<AttributeValue> attributeValues;
	
	public String getAttributeKeyId() {
		return attributeKeyId;
	}
	public void setAttributeKeyId(String attributeKeyId) {
		this.attributeKeyId = attributeKeyId;
	}
	public String getAttributeKeyName() {
		return attributeKeyName;
	}
	public void setAttributeKeyName(String attributeKeyName) {
		this.attributeKeyName = attributeKeyName;
	}
	public String getAttributeKeyDescription() {
		return attributeKeyDescription;
	}
	public void setAttributeKeyDescription(String attributeKeyDescription) {
		this.attributeKeyDescription = attributeKeyDescription;
	}
	public List<AttributeValue> getAttributeValues() {
		return attributeValues;
	}
	public void setAttributeValues(List<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}
	
}
