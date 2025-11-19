package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

public class AddSubcategoryAttributeMapping {
	private String otsSubcategoryId;
	private String otsSubcategoryAttributeMappingId;
	private List<String> otsAttributeKeyId;
	
	public String getOtsSubcategoryId() {
		return otsSubcategoryId;
	}
	public void setOtsSubcategoryId(String otsSubcategoryId) {
		this.otsSubcategoryId = otsSubcategoryId;
	}
	public String getOtsSubcategoryAttributeMappingId() {
		return otsSubcategoryAttributeMappingId;
	}
	public void setOtsSubcategoryAttributeMappingId(String otsSubcategoryAttributeMappingId) {
		this.otsSubcategoryAttributeMappingId = otsSubcategoryAttributeMappingId;
	}
	public List<String> getOtsAttributeKeyId() {
		return otsAttributeKeyId;
	}
	public void setOtsAttributeKeyId(List<String> otsAttributeKeyId) {
		this.otsAttributeKeyId = otsAttributeKeyId;
	}

}
