package com.fuso.enterprise.ots.srv.api.model.domain;

import javax.validation.constraints.Size;

public class UserMapping {
	
	
	@Size(max = 10)
	 private String mappedTo;
	
	@Size(max = 10)
	 private String userId;
	
	public String getMappedTo() {
		return mappedTo;
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

}
