package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;

public class UserDataBOResponse {
	
	List<UserDetails> userDetails;

	public List<UserDetails> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(List<UserDetails> userDetails) {
		this.userDetails = userDetails;
	}

	
	
}
