package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;


import com.fuso.enterprise.ots.srv.api.model.domain.UserAccounts;

public class UserAccountsResponse {

	private List<UserAccounts> userAccounts;

	public List<UserAccounts> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<UserAccounts> userAccounts) {
		this.userAccounts = userAccounts;
	}
	
}
