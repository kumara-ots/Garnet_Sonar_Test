package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;
import java.util.Map;

import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;

public class UserDetailsPageloaderResponse {
	
	private Map<String, List<UserDetails>> userDetails;

	public Map<String, List<UserDetails>> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Map<String, List<UserDetails>> userDetails) {
		this.userDetails = userDetails;
	}

}
