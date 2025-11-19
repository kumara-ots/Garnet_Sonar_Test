package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.UserAccounts;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.useraccountsDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.Useraccounts;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class useraccountsDAOImpl extends AbstractIptDao<Useraccounts, String> implements useraccountsDAO 
{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public useraccountsDAOImpl() {
        super(Useraccounts.class);
    }

	@Override
	public UserAccounts getUseraccountDetail(String accountId) 
	{
		UserAccounts userAccounts=new UserAccounts();
		try
		{
			Useraccounts useraccounts=null;
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("accountId", UUID.fromString(accountId));
			try {
				useraccounts=super.getResultByNamedQuery("Useraccounts.findByAccountId", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
			userAccounts=convertUserDetailsFromEntityToDomain(useraccounts);
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
		return userAccounts;
	}
	
	private UserAccounts convertUserDetailsFromEntityToDomain(Useraccounts userAccounts)
	{
		UserAccounts userDetails = new UserAccounts();
		userDetails.setAccountId(userAccounts.getAccountId()==null?"":userAccounts.getAccountId().toString());		
		userDetails.setUsername(userAccounts.getUsername()==null?"":userAccounts.getUsername());
		userDetails.setPassword(userAccounts.getPassword()==null?"":userAccounts.getPassword());
		userDetails.setFisrtName(userAccounts.getFirstName()==null?"":userAccounts.getFirstName());
		userDetails.setLastName(userAccounts.getLastName()==null?"":userAccounts.getLastName());
		userDetails.setUserRole(userAccounts.getUserRole() == null ?"":userAccounts.getUserRole().toString());
		userDetails.setAccountType(userAccounts.getAccountType() == null ? "" :userAccounts.getAccountType());			
		userDetails.setEmail(userAccounts.getEmail()==null?"":userAccounts.getEmail());
		userDetails.setPhone(userAccounts.getPhone()==null?"":userAccounts.getPhone());
		userDetails.setDateCreated(userAccounts.getDateCreated() == null ? null : userAccounts.getDateCreated().toString());
		userDetails.setDateModified(userAccounts.getDateModified() == null ? null : userAccounts.getDateModified().toString());
		userDetails.setExportedFileName(userAccounts.getExportedFileName() == null ? null :userAccounts.getExportedFileName().toString() );
		userDetails.setExportedTime(userAccounts.getExportedTime() == null ? null : userAccounts.getExportedTime().toString());
		userDetails.setStatusOfExport(userAccounts.getStatusOfExport() == null ? null :userAccounts.getStatusOfExport().toString());
		userDetails.setCreatedUser(userAccounts.getCreatedUser() == null ? "" :userAccounts.getCreatedUser().toString());
		
        return userDetails;
    }

	@Override
	public List<UserAccounts> getSubAdminByStatus(String accountStatus) {
    	List<UserAccounts> userAccountDeatails = new ArrayList<UserAccounts>();
    	try {
    	
        	Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("accountType",accountStatus);
			List<Useraccounts> otsUserAccounts = super.getResultListByNamedQuery("Useraccounts.getSubAdminByStatus", queryParameter);	;
			userAccountDeatails =  otsUserAccounts.stream().map(Useraccounts -> convertUserDetailsFromEntityToDomain(Useraccounts)).collect(Collectors.toList());  
    	}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
    	return userAccountDeatails;
	}
	
	@Override
	public UserAccounts getUseraccountDetailByEmail(String emailId) {
		UserAccounts userAccounts = new UserAccounts();
		try
		{
			Useraccounts useraccounts=null;
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("email", emailId);
			try {
				useraccounts = super.getResultByNamedQuery("Useraccounts.findByEmail", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
			
			userAccounts=convertUserDetailsFromEntityToDomain(useraccounts);
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
		return userAccounts;
	}
	
	@Override
	public List<UserAccounts> getAllActiveAdmin() {
    	List<UserAccounts> userAccountDeatails = new ArrayList<UserAccounts>();
    	try {
        	Map<String, Object> queryParameter = new HashMap<>();
			List<Useraccounts> otsUserAccounts = super.getResultListByNamedQuery("Useraccounts.getAllActiveAdmin", queryParameter);	;
			userAccountDeatails =  otsUserAccounts.stream().map(Useraccounts -> convertUserDetailsFromEntityToDomain(Useraccounts)).collect(Collectors.toList());  
    	}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
    	return userAccountDeatails;
	}
			
}


