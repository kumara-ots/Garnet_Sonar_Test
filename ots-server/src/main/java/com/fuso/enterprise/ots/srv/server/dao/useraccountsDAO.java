package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.UserAccounts;

public interface useraccountsDAO 
{
	UserAccounts getUseraccountDetail(String accountId);
	
	List<UserAccounts> getSubAdminByStatus(String accountStatus);
	
	UserAccounts getUseraccountDetailByEmail(String emailId);

	List<UserAccounts> getAllActiveAdmin();

}
