package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class AddAttributeKey {
	private List<String> attributeKeyName;
	private List<String> attributeKeyDiscription;
	
	public List<String> getAttributeKeyName() {
		return attributeKeyName;
	}
	public void setAttributeKeyName(List<String> attributeKeyName) {
		this.attributeKeyName = attributeKeyName;
	}
	public List<String> getAttributeKeyDiscription() {
		return attributeKeyDiscription;
	}
	public void setAttributeKeyDiscription(List<String> attributeKeyDiscription) {
		this.attributeKeyDiscription = attributeKeyDiscription;
	}
	
}
