package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class SubCategoryAttributeMapping {
	private String otsSubcategoryAttributeMappingId;
	private String otsAttributeKeyId;
	private String otsSubcategoryId;
	private String otsAttributeKeyName;
	private String otsAttributeKeyDescription;
	private List<AttributeValue>AttributeValue;
	
	public String getOtsSubcategoryAttributeMappingId() {
		return otsSubcategoryAttributeMappingId;
	}
	public void setOtsSubcategoryAttributeMappingId(String otsSubcategoryAttributeMappingId) {
		this.otsSubcategoryAttributeMappingId = otsSubcategoryAttributeMappingId;
	}
	public String getOtsAttributeKeyId() {
		return otsAttributeKeyId;
	}
	public void setOtsAttributeKeyId(String otsAttributeKeyId) {
		this.otsAttributeKeyId = otsAttributeKeyId;
	}
	public String getOtsSubcategoryId() {
		return otsSubcategoryId;
	}
	public void setOtsSubcategoryId(String otsSubcategoryId) {
		this.otsSubcategoryId = otsSubcategoryId;
	}
	public String getOtsAttributeKeyName() {
		return otsAttributeKeyName;
	}
	public void setOtsAttributeKeyName(String otsAttributeKeyName) {
		this.otsAttributeKeyName = otsAttributeKeyName;
	}
	public String getOtsAttributeKeyDescription() {
		return otsAttributeKeyDescription;
	}
	public void setOtsAttributeKeyDescription(String otsAttributeKeyDescription) {
		this.otsAttributeKeyDescription = otsAttributeKeyDescription;
	}
	public List<AttributeValue> getAttributeValue() {
		return AttributeValue;
	}
	public void setAttributeValue(List<AttributeValue> attributeValue) {
		AttributeValue = attributeValue;
	}
	
	
}