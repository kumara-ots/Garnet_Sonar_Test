package com.fuso.enterprise.ots.srv.api.model.domain;

import javax.validation.constraints.Size;

public class GetEmpLatLong {
	
	@Size(max = 20)
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
