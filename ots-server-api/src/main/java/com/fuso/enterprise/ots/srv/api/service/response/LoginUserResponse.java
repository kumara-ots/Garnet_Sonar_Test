package com.fuso.enterprise.ots.srv.api.service.response;

import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;

public class LoginUserResponse {
	
	private UserDetails userDetails;

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
