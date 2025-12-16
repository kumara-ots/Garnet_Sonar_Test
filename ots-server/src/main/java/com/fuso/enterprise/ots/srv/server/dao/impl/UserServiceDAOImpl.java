package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributerList;
import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorRegistrationDetailsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddHolidayRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddUserDataBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.ChangePasswordRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetUserStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.LoginAuthenticationBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.RequestBOUserBySearch;
import com.fuso.enterprise.ots.srv.api.service.request.UpdatePasswordRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateUserStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.response.DistributerResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetHolidayListResponse;
import com.fuso.enterprise.ots.srv.api.service.response.LoginUserResponse;
import com.fuso.enterprise.ots.srv.api.service.response.UserDataBOResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.UserServiceDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUserRole;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.model.entity.Useraccounts;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;
import com.fuso.enterprise.ots.srv.server.util.EmailUtil;

@Repository
public class UserServiceDAOImpl extends AbstractIptDao<OtsUsers, String> implements UserServiceDAO {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${ots.defaultPasswordString}")
	public String defaultPasswordString;
	
	@Value("${ots.company.name}")
	public String companyName;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private EmailUtil emailUtil;
	
	public UserServiceDAOImpl() {
        super(OtsUsers.class);
    }
	
	private String getValueOrNull(String value) {
	    return (value == null || value.equals("")) ? null : value;
	}
    
    @Override
    public UserDetails getUserIdUsers(String userId) {
        UserDetails userDetails = new UserDetails();
        try {
            OtsUsers otsUsers = null;
            Map<String, Object> queryParameter = new HashMap<>();
            queryParameter.put("otsUsersId", UUID.fromString(userId));
            try {
                otsUsers = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
            } catch (NoResultException e) {
                // No record found — return null is fine
                logger.debug("No user found for userId: {}", userId);
                return null;
            }
            userDetails = convertUserDetailsFromEntityToDomain(otsUsers);
        } catch (Exception e) {
            logger.error("Exception while fetching user details for userId: {}", userId, e);
            throw new BusinessException("Failed to fetch user details: " + e.getMessage(), e);
        }
        return userDetails;
    }
    
    public List<UserDetails> getEmailIdUsers(String emailId) {
        try {
            Map<String, Object> queryParameter = new HashMap<>();
            queryParameter.put("otsUsersEmailid", emailId);

            List<OtsUsers> userList =
                    super.getResultListByNamedQuery("OtsUsers.findByOtsUsersEmailid", queryParameter);

            return userList.stream()
                    .map(this::convertUserDetailsFromEntityToDomainForResponse)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Error fetching users for emailId: {}", emailId, e);
            throw new BusinessException("Failed to fetch users by email", e);
        }
    }
    
    @Override
	public UserDataBOResponse updateUser(AddUserDataBORequest addUserDataBORequest) {
    	List<UserDetails> userDetails = new ArrayList<UserDetails>();
    	UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
    	try{
    		//if user id is present we are directly updating the user details
    		Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId", UUID.fromString(addUserDataBORequest.getRequestData().getUserId()));
			OtsUsers userEntity  = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
			
			userEntity.setOtsUsersFirstname(addUserDataBORequest.getRequestData().getFirstName());
			userEntity.setOtsUsersLastname(addUserDataBORequest.getRequestData().getLastName());
			userEntity.setOtsUsersEmailid(addUserDataBORequest.getRequestData().getEmailId());
			userEntity.setOtsUsersContactNo(addUserDataBORequest.getRequestData().getContactNo());
			userEntity.setOtsUsersImage(addUserDataBORequest.getRequestData().getUserImage());
			
			save(userEntity);
	    	super.getEntityManager().merge(userEntity);
			super.getEntityManager().flush();
			
			userDetails.add(convertUserDetailsFromEntityToDomainForResponse(userEntity));
			userDataBOResponse.setUserDetails(userDetails);
    	}catch(Exception e){
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
    	return  userDataBOResponse;
	}

    @Override
	public UserDataBOResponse addNewUser(AddUserDataBORequest addUserDataBORequest) {
    	String password = defaultPasswordString;
//		if user doesn't contain password we are making password as "password" as default
    	if(addUserDataBORequest.getRequestData().getUsrPassword()!= null) {
    		password = encryptPassword(addUserDataBORequest.getRequestData().getUsrPassword());
    	}else {
    		password = encryptPassword(password);
    	}
    	OtsUsers userEntity = new OtsUsers();
    	List<UserDetails> userDetails = new ArrayList<UserDetails>();
    	UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
    	System.out.println("Inside addNewUser");
 		try{
			userEntity.setOtsUsersFirstname(addUserDataBORequest.getRequestData().getFirstName());
			userEntity.setOtsUsersLastname(addUserDataBORequest.getRequestData().getLastName());
			userEntity.setOtsUsersPassword(password);
			userEntity.setOtsUsersEmailid(addUserDataBORequest.getRequestData().getEmailId());
			userEntity.setOtsUsersContactNo(addUserDataBORequest.getRequestData().getContactNo());
			OtsUserRole otsUserRole = new OtsUserRole();
			otsUserRole.setOtsUserRoleId(Integer.parseInt(addUserDataBORequest.getRequestData().getUserRoleId()));
			userEntity.setOtsUserRoleId(otsUserRole);
			if(addUserDataBORequest.getRequestData().getOtsServiceProvider() != null) {
				userEntity.setOtsServiceProvider(addUserDataBORequest.getRequestData().getOtsServiceProvider());
			}
		    userEntity.setOtsUsersStatus(addUserDataBORequest.getRequestData().getUsrStatus());
			userEntity.setOtsUsersLat(addUserDataBORequest.getRequestData().getUserLat());
			userEntity.setOtsUsersLong(addUserDataBORequest.getRequestData().getUserLong());
			userEntity.setOtsUsersAdminFlag(addUserDataBORequest.getRequestData().getUserAdminFlag());		
			userEntity.setOtsUsersImage(getValueOrNull(addUserDataBORequest.getRequestData().getUserImage()));
			
			//Adding below Company Details for Buyer/Customer Only
			if(otsUserRole.getOtsUserRoleId().toString().equalsIgnoreCase("4")) {
				userEntity.setOtsCompanyName(addUserDataBORequest.getRequestData().getCompanyName());
				userEntity.setOtsHouseNo(addUserDataBORequest.getRequestData().getHouseNo());
				userEntity.setOtsBuildingName(addUserDataBORequest.getRequestData().getBuildingName());
				userEntity.setOtsStreetName(addUserDataBORequest.getRequestData().getStreetName());
				userEntity.setOtsCityName(addUserDataBORequest.getRequestData().getCityName());		
				userEntity.setOtsPincode(addUserDataBORequest.getRequestData().getPincode());
				userEntity.setOtsDistrictName(getValueOrNull(addUserDataBORequest.getRequestData().getDistrictName()));
				userEntity.setOtsStateName(addUserDataBORequest.getRequestData().getStateName());
				userEntity.setOtsCountryName(addUserDataBORequest.getRequestData().getCountryName());
				userEntity.setOtsCompanyRegistration(addUserDataBORequest.getRequestData().getCompanyRegistration());	
				userEntity.setOtsTaxCard(addUserDataBORequest.getRequestData().getTaxCard());
				userEntity.setOtsComputerCard(addUserDataBORequest.getRequestData().getComputerCard());
				userEntity.setOtsSignatoryIdCard(addUserDataBORequest.getRequestData().getSignatoryIdCard());		
				userEntity.setOtsTradeLicense(addUserDataBORequest.getRequestData().getTradeLicense());	
			}
			   	
			if(otsUserRole.getOtsUserRoleId().toString().equalsIgnoreCase("1")||otsUserRole.getOtsUserRoleId().toString().equalsIgnoreCase("2"))
			{					
				Useraccounts accountentity= new Useraccounts();
				accountentity.setAccountId(UUID.fromString(addUserDataBORequest.getRequestData().getCreatedUser()));
				userEntity.setCreatedUser(accountentity);
			}
			else if(otsUserRole.getOtsUserRoleId().toString().equalsIgnoreCase("3")) {
				Useraccounts accountentity= new Useraccounts();
				accountentity.setAccountId(UUID.fromString(addUserDataBORequest.getRequestData().getCreatedUser()));
				userEntity.setCreatedUser(accountentity);
				userEntity.setOtsDistributorId(UUID.fromString(addUserDataBORequest.getRequestData().getDistributorId()));
			}
			else {
				userEntity.setOtsDistributorId(null);
			}
			
		    // Set createdBy only for roleId = "2"
	        if(otsUserRole.getOtsUserRoleId().toString().equalsIgnoreCase("2")) {
	            userEntity.setOtsUsersCreatedBy(getValueOrNull(addUserDataBORequest.getRequestData().getUsersCreatedBy()));
	        }

			//To generate random UUID   
			UUID uuid = UUID.randomUUID();
			userEntity.setOtsUsersId(uuid);
			
			System.out.println("Generated UUID: " + userEntity.getOtsUsersId());
			
			save(userEntity);
			super.getEntityManager().flush();
			
			//Adding UserNumber only for Admin, Distributor & Employee But we are not Adding UserNumber for Customer
			if(!addUserDataBORequest.getRequestData().getUserRoleId().equalsIgnoreCase("4") ) {
				String userNumber = addUserDataBORequest.getRequestData().getCreatedUser()+"-"+userEntity.getOtsUsersId();
				System.out.println("userNumber = "+userNumber);
				userEntity.setOtsUsersNumber(userNumber);
				
				super.getEntityManager().merge(userEntity);
			}
			userDetails.add(convertUserDetailsFromEntityToDomainForResponse(userEntity));
			userDataBOResponse.setUserDetails(userDetails);
		}catch(Exception e){
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return  userDataBOResponse;
	}

    private UserDetails convertUserDetailsFromEntityToDomain(OtsUsers otsUsers) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(otsUsers.getOtsUsersId()==null?null:otsUsers.getOtsUsersId().toString());
        userDetails.setUsersNumber(otsUsers.getOtsUsersNumber() ==null?"":otsUsers.getOtsUsersNumber());
        userDetails.setFirstName(otsUsers.getOtsUsersFirstname()==null?null:otsUsers.getOtsUsersFirstname());
        userDetails.setLastName(otsUsers.getOtsUsersLastname()==null?null:otsUsers.getOtsUsersLastname());
		userDetails.setContactNo(otsUsers.getOtsUsersContactNo()==null?null:otsUsers.getOtsUsersContactNo());
        userDetails.setUserRoleId(otsUsers.getOtsUserRoleId().getOtsUserRoleId()==null?null:otsUsers.getOtsUserRoleId().getOtsUserRoleId().toString());
        userDetails.setEmailId(otsUsers.getOtsUsersEmailid()==null?null:otsUsers.getOtsUsersEmailid());
        userDetails.setUsrStatus(otsUsers.getOtsUsersStatus()==null?null:otsUsers.getOtsUsersStatus());
        userDetails.setOtsServiceProvider(otsUsers.getOtsServiceProvider()?null:otsUsers.getOtsServiceProvider());
        userDetails.setUsrPassword(otsUsers.getOtsUsersPassword()==null?null:otsUsers.getOtsUsersPassword());
        userDetails.setMappedTo(otsUsers.getOtsUserMapping()==null?null:otsUsers.getOtsUserMapping().getOtsMappedTo().toString());
        userDetails.setUserAdminFlag(otsUsers.getOtsUsersAdminFlag()==null?null:otsUsers.getOtsUsersAdminFlag());
        userDetails.setUserLat(otsUsers.getOtsUsersLat() ==null?null:otsUsers.getOtsUsersLat());
        userDetails.setUserLong(otsUsers.getOtsUsersLong() ==null?null:otsUsers.getOtsUsersLong());
        userDetails.setRegistrationAmount(otsUsers.getOtsUsersRegistrationAmount() ==null?null:otsUsers.getOtsUsersRegistrationAmount());
        userDetails.setRegistrationPaymentId(otsUsers.getOtsUsersRegistrationPaymentId() ==null?null:otsUsers.getOtsUsersRegistrationPaymentId().toString());
        userDetails.setRegistrationPaymentStatus(otsUsers.getOtsUsersRegistrationPaymentStatus() ==null?null:otsUsers.getOtsUsersRegistrationPaymentStatus());
        userDetails.setRegistrationPaymentDate(otsUsers.getOtsUsersRegistrationPaymentDate() ==null?null:otsUsers.getOtsUsersRegistrationPaymentDate().toString());
        userDetails.setUserRegistrationInvoice(otsUsers.getOtsUsersRegistrationInvoice() ==null?null:otsUsers.getOtsUsersRegistrationInvoice());
        userDetails.setCreatedUser(otsUsers.getCreatedUser()==null?null:otsUsers.getCreatedUser().getAccountId().toString());
        userDetails.setUserImage(otsUsers.getOtsUsersImage()==null?"":otsUsers.getOtsUsersImage());
        userDetails.setDistributorId(otsUsers.getOtsDistributorId() ==null?null:otsUsers.getOtsDistributorId().toString());
        userDetails.setUsersCreatedBy(otsUsers.getOtsUsersCreatedBy()==null?null:otsUsers.getOtsUsersCreatedBy());
        userDetails.setUsersRejectionReason(otsUsers.getOtsUsersRejectionReason()==null?null:otsUsers.getOtsUsersRejectionReason());
        
        return userDetails;
    }
    
    private UserDetails convertUserDetailsFromEntityToDomainForResponse(OtsUsers otsUsers) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(otsUsers.getOtsUsersId()==null?null:otsUsers.getOtsUsersId().toString());
        userDetails.setUsersNumber(otsUsers.getOtsUsersNumber() ==null?"":otsUsers.getOtsUsersNumber());
        userDetails.setFirstName(otsUsers.getOtsUsersFirstname()==null?"":otsUsers.getOtsUsersFirstname());
        userDetails.setLastName(otsUsers.getOtsUsersLastname()==null?"":otsUsers.getOtsUsersLastname());
		userDetails.setContactNo(otsUsers.getOtsUsersContactNo()==null?"":otsUsers.getOtsUsersContactNo());
        userDetails.setUserRoleId(otsUsers.getOtsUserRoleId().getOtsUserRoleId()==null?"":otsUsers.getOtsUserRoleId().getOtsUserRoleId().toString());
        userDetails.setEmailId(otsUsers.getOtsUsersEmailid()==null?"":otsUsers.getOtsUsersEmailid());
        userDetails.setUsrStatus(otsUsers.getOtsUsersStatus()==null?"":otsUsers.getOtsUsersStatus());
        userDetails.setOtsServiceProvider(otsUsers.getOtsServiceProvider()?null:otsUsers.getOtsServiceProvider());
        userDetails.setUsrPassword(otsUsers.getOtsUsersPassword()==null?"":otsUsers.getOtsUsersPassword());
        userDetails.setMappedTo(otsUsers.getOtsUserMapping()==null?"":otsUsers.getOtsUserMapping().getOtsMappedTo().toString());
        userDetails.setUserAdminFlag(otsUsers.getOtsUsersAdminFlag()==null?"":otsUsers.getOtsUsersAdminFlag());
        userDetails.setUserLat(otsUsers.getOtsUsersLat() ==null?"":otsUsers.getOtsUsersLat());
        userDetails.setUserLong(otsUsers.getOtsUsersLong() ==null?"":otsUsers.getOtsUsersLong());
        userDetails.setRegistrationAmount(otsUsers.getOtsUsersRegistrationAmount() ==null?null:otsUsers.getOtsUsersRegistrationAmount());
        userDetails.setRegistrationPaymentId(otsUsers.getOtsUsersRegistrationPaymentId() ==null?"":otsUsers.getOtsUsersRegistrationPaymentId().toString());
        userDetails.setRegistrationPaymentStatus(otsUsers.getOtsUsersRegistrationPaymentStatus() ==null?"":otsUsers.getOtsUsersRegistrationPaymentStatus());
        userDetails.setRegistrationPaymentDate(otsUsers.getOtsUsersRegistrationPaymentDate() ==null?"":otsUsers.getOtsUsersRegistrationPaymentDate().toString());
        userDetails.setUserRegistrationInvoice(otsUsers.getOtsUsersRegistrationInvoice() ==null?"":otsUsers.getOtsUsersRegistrationInvoice());
        userDetails.setCreatedUser(otsUsers.getCreatedUser()==null?"":otsUsers.getCreatedUser().getAccountId().toString());
        userDetails.setUserImage(otsUsers.getOtsUsersImage()==null?"":otsUsers.getOtsUsersImage());
        userDetails.setUsersCreatedBy(otsUsers.getOtsUsersCreatedBy()==null?"":otsUsers.getOtsUsersCreatedBy());
        userDetails.setUsersRejectionReason(otsUsers.getOtsUsersRejectionReason()==null?"":otsUsers.getOtsUsersRejectionReason());
        userDetails.setDistributorId(otsUsers.getOtsDistributorId() ==null?"":otsUsers.getOtsDistributorId().toString());  
        userDetails.setCompanyName(otsUsers.getOtsCompanyName()==null?"":otsUsers.getOtsCompanyName());
        userDetails.setHouseNo(otsUsers.getOtsHouseNo()==null?"":otsUsers.getOtsHouseNo());
        userDetails.setBuildingName(otsUsers.getOtsBuildingName()==null?"":otsUsers.getOtsBuildingName());
        userDetails.setStreetName(otsUsers.getOtsStreetName() ==null?"":otsUsers.getOtsStreetName());
        userDetails.setCityName(otsUsers.getOtsCityName() ==null?"":otsUsers.getOtsCityName());
        userDetails.setPincode(otsUsers.getOtsPincode() ==null?"":otsUsers.getOtsPincode());
        userDetails.setStateName(otsUsers.getOtsStateName() ==null?null:otsUsers.getOtsStateName());
        userDetails.setDistrictName(otsUsers.getOtsDistrictName() ==null?"":otsUsers.getOtsDistrictName());
        userDetails.setCountryName(otsUsers.getOtsCountryName() ==null?"":otsUsers.getOtsCountryName());
        userDetails.setCompanyRegistration(otsUsers.getOtsCompanyRegistration() ==null?"":otsUsers.getOtsCompanyRegistration());
        userDetails.setTaxCard(otsUsers.getOtsTaxCard() ==null?"":otsUsers.getOtsTaxCard());
        userDetails.setComputerCard(otsUsers.getOtsComputerCard()==null?"":otsUsers.getOtsComputerCard().toString());
        userDetails.setSignatoryIdCard(otsUsers.getOtsSignatoryIdCard()==null?"":otsUsers.getOtsSignatoryIdCard());
        userDetails.setTradeLicense(otsUsers.getOtsTradeLicense()==null?"":otsUsers.getOtsTradeLicense());
        
        return userDetails;
    }

    @Override
   	public LoginUserResponse otsLoginAuthentication(LoginAuthenticationBOrequest loginAuthenticationBOrequest) {
   		String phnum = loginAuthenticationBOrequest.getRequestData().getPhoneNumber();
   		LoginUserResponse loginUserResponse = new LoginUserResponse();
       	try{
   			//get user details for contact number
       		Map<String, Object> queryParameter = new HashMap<>();
   			queryParameter.put("otsUsersContactNo",phnum);
   			List<OtsUsers> userList  = super.getResultListByNamedQuery("OtsUsers.findByOtsUsersContactNo", queryParameter);
   			if(userList.size()== 0) {
   				return null;
   			}else {
   				// setting device login if user exists for sending the notification
   	            //converting entity to model
   				List<UserDetails> userDetails =  userList.stream().map(otsUsers -> convertUserDetailsFromEntityToDomain(otsUsers)).collect(Collectors.toList());
   	            loginUserResponse.setUserDetails(userDetails.get(0));
   			}
       	}catch (BusinessException e){
   			logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }catch (Throwable e) {
   	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }
       	return loginUserResponse;
   	}
	
	@Override
	public List<UserDetails> CheckForExists(AddUserDataBORequest addUserDataBORequest) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
    	try {
	    	List<OtsUsers> userList = null;
        	Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("emailid", addUserDataBORequest.getRequestData().getEmailId());
			queryParameter.put("contact",addUserDataBORequest.getRequestData().getContactNo());
			userList  = super.getResultListByNamedQuery("OtsUsers.findByContactNoOrEmailId", queryParameter);
			System.out.println("UserList size = "+userList.size());

            userDetails =  userList.stream().map(otsUsers -> convertUserDetailsFromEntityToDomain(otsUsers)).collect(Collectors.toList());
    	}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
    	return userDetails;
	}
	
	@Override
	public UserDetails getUserDetails(String userId) {
		try {
			UserDetails userDetails = new UserDetails();
			OtsUsers userList = null;
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId", UUID.fromString(userId));
			try {
				userList = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
			}catch(Exception e) {
				return null;
			}
			userDetails = convertUserDetailsFromEntityToDomain(userList);
			
			return userDetails;
		}catch (BusinessException e){
   			logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }catch (Throwable e) {
   	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }
	}
	
	@Override
	public UserDetails checkForOTP(String mobilenumber) {
		try {
			UserDetails userDetails = new UserDetails();
			OtsUsers userList = null;
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersContactNo", mobilenumber);
			try {
				userList = super.getResultByNamedQuery("OtsUsers.findByOtsUsersContactNo", queryParameter);
			}catch(Exception e) {
				return null;
			}
			userDetails = convertUserDetailsFromEntityToDomain(userList);
			
			return userDetails;
		}catch (BusinessException e){
   			logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }catch (Throwable e) {
   	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }
	}
	
	@Override
	public String changePassword(ChangePasswordRequest changePasswordRequest) {
		try {
        	OtsUsers userData = new OtsUsers();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId",UUID.fromString(changePasswordRequest.getRequest().getUserID()));
			try {
				userData = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
			}catch(Exception e) {
				return null;
			}
			
			String pwd = encryptPassword(changePasswordRequest.getRequest().getPasword());
			userData.setOtsUsersPassword(pwd);
			super.getEntityManager().merge(userData);
			
			try {
				String userMsg="<p>Hi "+userData.getOtsUsersFirstname()+" "+userData.getOtsUsersLastname()+",<br><br>" + 
						"Your password has been changed. Kindly Login<br><br>" + 
						"Thanks And Regards,<br>" + 
						companyName+" Support Team </p>";
				emailUtil.sendDistributermail(userData.getOtsUsersEmailid(), "", "Password Has Been Updated", userMsg);
			}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
			}
		}catch (BusinessException e){
   			logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }catch (Throwable e) {
   	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }
		return "updated";
	}

	@Override
	public UserDetails getUserDetailsForEmployee(String userId) {
		try {
			UserDetails userDetails = new UserDetails();
			OtsUsers userList = null;
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId", UUID.fromString(userId));
			
			OtsUserRole roleId = new OtsUserRole();
			roleId.setOtsUserRoleId(3);
			queryParameter.put("RoleId", roleId);
			queryParameter.put("otsUsersId", userId);
			try {
				userList = super.getResultByNamedQuery("OtsUsers.findByOtsUsersIdAndRoleId", queryParameter);
			}catch(Exception e) {
				return null;
			}
			userDetails = convertUserDetailsFromEntityToDomain(userList);
			
			return userDetails;
		}catch (BusinessException e){
   			logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }catch (Throwable e) {
   	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
           	throw new BusinessException(e.getMessage(), e);
   	    }
	}

	@Override
	public String updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		try {
			OtsUsers userData = new OtsUsers();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId",UUID.fromString(updatePasswordRequest.getUpdatePassword().getUserId()));
			userData = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
			
			if (BCrypt.checkpw(updatePasswordRequest.getUpdatePassword().getOldPassword(), userData.getOtsUsersPassword())) {
				userData.setOtsUsersPassword(encryptPassword(updatePasswordRequest.getUpdatePassword().getNewPassword()));
				super.getEntityManager().merge(userData);
				return "Password Updated Successfully";
			}else {
				return "Please Check Your Old Password";
			}
		}catch(Exception e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
	}

	public String encryptPassword(String password) {
		String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt());
		return pw_hash;
	}
    
    @Override
	public List<UserDetails> getDistributorByCreatedUser(String CreatedUser){
    	List<UserDetails> userDetails = new ArrayList<UserDetails>();
    	try {
            List<OtsUsers> userList = null;
        	Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("createdUser", UUID.fromString(CreatedUser));
			userList  = super.getResultListByNamedQuery("OtsUsers.getDistributorByCreatedUser", queryParameter);
            userDetails =  userList.stream().map(otsUsers -> convertUserDetailsFromEntityToDomainForResponse(otsUsers)).collect(Collectors.toList());
    	}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
    	return userDetails;
    }
	
	@Override
	public DistributerResponse getDistributerDetails()
	{
		DistributerResponse DistributerResponse = new DistributerResponse();
		List<DistributerList> list = new ArrayList<DistributerList>();
		try
		{
			List<OtsUsers> distributerList = null;
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUserRole roleId = new OtsUserRole();
			roleId.setOtsUserRoleId(1);
			queryParameter.put("RoleId", roleId);
			OtsUserRole roleId2 = new OtsUserRole();
			roleId2.setOtsUserRoleId(2);
			queryParameter.put("RoleId2", roleId2);
		
			distributerList = super.getResultListByNamedQuery("OtsUsers.getDistributorList", queryParameter);
			list =  distributerList.stream().map(OtsUsers -> convertDistributerDetailsFromEntityToDomain(OtsUsers)).collect(Collectors.toList());
			
			DistributerResponse.setDistributerList(list);
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
		return DistributerResponse;
		 
	}
	
	private DistributerList convertDistributerDetailsFromEntityToDomain(OtsUsers OtsUsers)
    {
		DistributerList DistributerList = new DistributerList();
		DistributerList.setDistributerId(OtsUsers.getOtsUsersId()==null?null:OtsUsers.getOtsUsersId().toString());
		DistributerList.setDistributerName(OtsUsers.getOtsUsersFirstname()==null?null:OtsUsers.getOtsUsersFirstname());
		DistributerList.setDistributerImage(OtsUsers.getOtsUsersImage()==null?null:OtsUsers.getOtsUsersImage());
		
		return DistributerList;
    }
	
	@Override
	public String addDistributorRegistrationInvoiceToDB(String distributorId,String invoice) {
		OtsUsers userData = new OtsUsers();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId",UUID.fromString(distributorId));
			userData = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
			
			userData.setOtsUsersRegistrationInvoice(invoice);
			super.getEntityManager().merge(userData);
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return "Invoice Added";
	}
	
	@Override
	public List<UserDetails> updateUserStatus(UpdateUserStatusRequest updateUserStatusRequest) {
    	List<UserDetails> userDetails = new ArrayList<UserDetails>();
    	try{
    		//if user id is present we are directly updating the user details
    		Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId", UUID.fromString(updateUserStatusRequest.getRequest().getUsersId()));
			OtsUsers userEntity  = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
	    	
			userEntity.setOtsUsersStatus(updateUserStatusRequest.getRequest().getUsersStatus());
			save(userEntity);
			userDetails.add(convertUserDetailsFromEntityToDomain(userEntity));
    	}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
    	return userDetails;
	}
	 
	@Override
	public String addDistributorRegistrationDetails(AddDistributorRegistrationDetailsRequest addDistributorRegistrationDetailsRequest) {
    	List<UserDetails> userDetails = new ArrayList<UserDetails>();
    	java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(utilDate.getTime());
		System.out.println("current date = "+currentDateTime);
    	try{
    		//if user id is present we are directly updating the user details
    		Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId", UUID.fromString(addDistributorRegistrationDetailsRequest.getRequest().getUserId()));
			OtsUsers userEntity  = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
	    	
			userEntity.setOtsUsersRegistrationAmount(addDistributorRegistrationDetailsRequest.getRequest().getRegistrationAmount());
			userEntity.setOtsUsersRegistrationPaymentId(addDistributorRegistrationDetailsRequest.getRequest().getRegistrationPaymentId());
			userEntity.setOtsUsersRegistrationPaymentStatus(addDistributorRegistrationDetailsRequest.getRequest().getRegistrationPaymentStatus());
			userEntity.setOtsUsersRegistrationPaymentDate(currentDateTime);
			userEntity.setOtsUsersStatus("pending");
			save(userEntity);
			userDetails.add(convertUserDetailsFromEntityToDomain(userEntity));
    	}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
    	return "Inserted";
	}
	
	@Override
	public List<UserDetails> getUserDetailsByContactNo(String contactNo) {
    	List<UserDetails> userDetails = new ArrayList<UserDetails>();
    	List<OtsUsers> userList = new ArrayList<OtsUsers>();
    	try {
        	Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersContactNo",contactNo);
			userList  = super.getResultListByNamedQuery("OtsUsers.findByOtsUsersContactNo", queryParameter);
            userDetails =  userList.stream().map(otsUsers -> convertUserDetailsFromEntityToDomainForResponse(otsUsers)).collect(Collectors.toList());
    	}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
    	return userDetails;
	}
	
	@Override
	public List<UserDetails> getDistributorsWithActiveProductsByCountry(String countryCode) {
	   	List<UserDetails> userDetails = new ArrayList<UserDetails>();
	   	try {
           	List<OtsUsers> userList = null;
       		Map<String, Object> queryParameter = new HashMap<>();
       		queryParameter.put("otsProductCountryCode", countryCode);
			userList  = super.getResultListByNamedQuery("OtsUsers.getDistributorsWithActiveProductsByCountry", queryParameter);
			userDetails =  userList.stream().map(otsUsers -> convertUserDetailsFromEntityToDomainForResponse(otsUsers)).collect(Collectors.toList());
	   	}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	   	return userDetails;
   }
	
	@Override
	public String addHoliday(AddHolidayRequest addHolidayRequest) {
		OtsUsers otsUsers = new OtsUsers();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId", UUID.fromString(addHolidayRequest.getProviderId()));
			try {
				otsUsers = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonData = objectMapper.writeValueAsString(addHolidayRequest.getHolidays());
			System.out.println("holiday = "+jsonData);
			otsUsers.setOtsHolidaysList(jsonData);

			save(otsUsers);
			super.getEntityManager().flush();
			return "Holidays Inserted";
		} catch (Exception e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public GetHolidayListResponse getHolidayListByProviderId(String providerId) {
		OtsUsers otsUsers = new OtsUsers();
		GetHolidayListResponse getHolidayListResponse = new GetHolidayListResponse();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId", UUID.fromString(providerId));
			try {
				otsUsers = super.getResultByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
			} catch (NoResultException e) {
				return null;
			}
			
			if (otsUsers.getOtsHolidaysList() == null) {
				return null;
			}
			
			ObjectMapper objectMapper = new ObjectMapper();

			// Assuming getOtsHolidaysList() returns a JSON string like "[\"2025-04-13\",\"2025-06-30\"]"
			String json = otsUsers.getOtsHolidaysList(); // Already a JSON string
			List<String> holidays = objectMapper.readValue(json, new TypeReference<List<String>>() {});
			
			getHolidayListResponse.setHolidays(holidays);

			return getHolidayListResponse;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<UserDetails> getProviderByCreatedUser(String createdUser) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
    	try {
            List<OtsUsers> userList = null;
        	Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("createdUser", UUID.fromString(createdUser));
			userList  = super.getResultListByNamedQuery("OtsUsers.getProviderByCreatedUser", queryParameter);
            userDetails =  userList.stream().map(otsUsers -> convertUserDetailsFromEntityToDomain(otsUsers)).collect(Collectors.toList());
    	} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
    	return userDetails;
	}
	
	@Override
	public List<UserDetails> getActiveProviderList() {
    	try {
        	Map<String, Object> queryParameter = new HashMap<>();
        	List<OtsUsers> userList  = super.getResultListByNamedQuery("OtsUsers.getActiveProviderList", queryParameter);
			List<UserDetails> userDetails =  userList.stream().map(otsUsers -> convertUserDetailsFromEntityToDomain(otsUsers)).collect(Collectors.toList());
			return userDetails;
    	} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public Map<String, List<UserDetails>> getPageloaderDistributorsWithActiveProducts() {
		Map<String, List<UserDetails>> countryDistributorMap = new HashMap<>();
	   	try {
	   		Map<String, Object> inParamMap = new HashMap<String, Object>();
			
	   		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
		            .withFunctionName("get_pageloader_distributor_with_active_products")
		            .withSchemaName("public")
		            .withoutProcedureColumnMetaDataAccess()
		            .declareParameters(new SqlOutParameter("return", Types.OTHER));
			
			//calling stored procedure and getting response
			Map<String, Object> result = simpleJdbcCall.execute(inParamMap);
			
	        if (result == null || result.isEmpty() || result.get("return") == null) {
	            return countryDistributorMap;
	        }

	        String jsonResponse = result.get("return").toString();

	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);

	        // Iterate through each country code key
	        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
	        while (fields.hasNext()) {
	            Map.Entry<String, JsonNode> entry = fields.next();
	            String countryCode = entry.getKey();
	            JsonNode distributorArray = entry.getValue();

	            List<UserDetails> distributorList = new ArrayList<>();
	            if (distributorArray.isArray()) {
	                for (JsonNode distributorNode : distributorArray) {
	                    Map<String, Object> distributorMap = objectMapper.convertValue(
	                    	distributorNode,
	                        new TypeReference<Map<String, Object>>() {}
	                    );
	                    distributorList.add(convertUserDetailsFromProcedureToDomain(distributorMap));
	                }
	            }
	            countryDistributorMap.put(countryCode, distributorList);
	        }
	   	}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	   	return countryDistributorMap;
    }
	
	private UserDetails convertUserDetailsFromProcedureToDomain(Map<String, Object> outputResult) {
	    UserDetails userDetails = new UserDetails();
	    userDetails.setUserId(outputResult.get("ots_users_id")==null?"":outputResult.get("ots_users_id").toString());
		userDetails.setUsersNumber(outputResult.get("ots_users_number")==null?"":outputResult.get("ots_users_number").toString());
		userDetails.setFirstName(outputResult.get("ots_users_firstname")==null?"":outputResult.get("ots_users_firstname").toString());
		userDetails.setLastName(outputResult.get("ots_users_lastname")==null?"":outputResult.get("ots_users_lastname").toString());
		userDetails.setContactNo(outputResult.get("ots_users_contact_no")==null?"":outputResult.get("ots_users_contact_no").toString());
		userDetails.setUserImage(outputResult.get("ots_users_image")==null?"":outputResult.get("ots_users_image").toString());
		userDetails.setEmailId(outputResult.get("ots_users_emailid")==null?"":outputResult.get("ots_users_emailid").toString());
	    
	    return userDetails;
	}
	
	@Override
	public List<UserDetails> getUserDetails(RequestBOUserBySearch requestBOUserBySearch) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		String createdUserId = requestBOUserBySearch.getRequestData().getCreatedUser();
		String searchKey 	= requestBOUserBySearch.getRequestData().getSearchKey();
		String searchvalue  = requestBOUserBySearch.getRequestData().getSearchvalue();
		String Distributerid = requestBOUserBySearch.getRequestData().getDistributorId();
        Map<String, Object> queryParameter = new HashMap<>();
        List<OtsUsers> userList = null;
		try {
            switch(searchKey){
	            case "UsersId":
	            					queryParameter.put("otsUsersId", searchvalue);
	            					queryParameter.put("DistributorId", UUID.fromString(requestBOUserBySearch.getRequestData().getDistributorId()));
	            					userList  = super.getResultListByNamedQuery("OtsUsers.findByOtsUsersId", queryParameter);
	            				    break;
	            case "UsersFirstname":
									queryParameter.put("otsUsersFirstname","%"+searchvalue+"%");
									queryParameter.put("otsDistributerId",UUID.fromString(Distributerid));
									queryParameter.put("createdUser",UUID.fromString(createdUserId));
									userList  = super.getResultListByNamedQuery("OtsUsers.findByPattrenMatchingotsUsersFirstname", queryParameter);
								    break;
	            case "UsersLastname":
									queryParameter.put("otsUsersLastname","%"+searchvalue+"%");
									queryParameter.put("otsDistributerId",UUID.fromString(Distributerid));
									queryParameter.put("createdUser",UUID.fromString(createdUserId));
									userList  = super.getResultListByNamedQuery("OtsUsers.findByPattrenMatchingotsUsersLastname", queryParameter);
									break;
	            case "UsersEmailid":
									queryParameter.put("otsUsersEmailid","%"+searchvalue+"%");
									queryParameter.put("otsDistributerId",UUID.fromString(Distributerid));
									
									userList  = super.getResultListByNamedQuery("OtsUsers.findByPattrenMatchingotsUsersEmailid", queryParameter);
								    break;
	            case "UserRoleId":
                                    OtsUserRole otsUserRole = new OtsUserRole();	
                                    otsUserRole.setOtsUserRoleId(Integer.parseInt(searchvalue));	
                                    if(requestBOUserBySearch.getRequestData().getSearchvalue().equalsIgnoreCase("2")) 
                                    {			
                                        queryParameter.put("createdUser",UUID.fromString(createdUserId));
                                        userList  = super.getResultListByNamedQuery("OtsUsers.getDistributorByCreatedUser", queryParameter);
                                    }
                                    else if(requestBOUserBySearch.getRequestData().getSearchvalue().equalsIgnoreCase("3")) {
                                        queryParameter.put("otsDistributorId",UUID.fromString(requestBOUserBySearch.getRequestData().getDistributorId()));
                                        userList  = super.getResultListByNamedQuery("OtsUsers.getEmployeesMappedToDistributor", queryParameter);
                                    }
                                    else 
                                    {		     
                                        queryParameter.put("otsUserRoleId", otsUserRole);		
                                        queryParameter.put("createdUser",UUID.fromString(createdUserId));				
                                        userList  = super.getResultListByNamedQuery("OtsUsers.findByOtsUsersIdAndRoleIdAndCreatedUser", queryParameter);
                                    }
                                    break;

               case "pending":
	        					queryParameter.put("otsUsersStatus", requestBOUserBySearch.getRequestData().getSearchKey());
	        					queryParameter.put("user", requestBOUserBySearch.getRequestData().getCreatedUser());
	        					
								userList  = super.getResultListByNamedQuery("OtsUsers.findByStatusforAdmin", queryParameter);
								break;
	           
	            default:
	            				return null;

            }

            userDetails =  userList.stream().map(OtsUsers -> convertUserDetailsFromEntityToDomainForResponse(OtsUsers)).collect(Collectors.toList());

            return userDetails;
    	}catch (NoResultException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
        }  
	}
	
	@Override
	public List<UserDetails> getEmployeeDetailsByDistributor(RequestBOUserBySearch requestBOUserBySearch) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		String searchKey 	= requestBOUserBySearch.getRequestData().getSearchKey();
		String searchvalue  = requestBOUserBySearch.getRequestData().getSearchvalue();
		String Distributorid = requestBOUserBySearch.getRequestData().getDistributorId();
        Map<String, Object> queryParameter = new HashMap<>();
        List<OtsUsers> userList = null;
		try {
            switch(searchKey){
	            case "UsersFirstname":
									queryParameter.put("otsUsersFirstname","%"+searchvalue+"%");
									queryParameter.put("otsDistributorId",UUID.fromString(Distributorid));
									userList  = super.getResultListByNamedQuery("OtsUsers.findByEmployeeFirstNameUnderDistributor", queryParameter);
								    break;
	            case "UsersLastname":
									queryParameter.put("otsUsersLastname","%"+searchvalue+"%");
									queryParameter.put("otsDistributorId",UUID.fromString(Distributorid));
									userList  = super.getResultListByNamedQuery("OtsUsers.findByEmployeeLastNameUnderDistributor", queryParameter);
									break;
	            case "UserRoleId":
					            	OtsUserRole otsUserRole = new OtsUserRole();	
					            	otsUserRole.setOtsUserRoleId(Integer.parseInt(searchvalue));	
					            	if(requestBOUserBySearch.getRequestData().getSearchvalue().equalsIgnoreCase("3")) {
					            		queryParameter.put("otsDistributorId",UUID.fromString(Distributorid));
					                    userList  = super.getResultListByNamedQuery("OtsUsers.getEmployeesMappedToDistributor", queryParameter);
					            	}
					            	break;

	            default:			return null;

            }
            userDetails =  userList.stream().map(OtsUsers -> convertUserDetailsFromEntityToDomainForResponse(OtsUsers)).collect(Collectors.toList());
            return userDetails;
    	}catch (NoResultException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
        }  
	}
	
	@Override
	public List<UserDetails> getUserDetailsByMapped(String MappedTo) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		try {
			List<OtsUsers> userList = null;
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("MappedTo", Integer.parseInt(MappedTo));
			userList = super.getResultListByNamedQuery("OtsUsers.findByUserOtsbyMappedTo", queryParameter);
			userDetails = userList.stream().map(otsUsers -> convertUserDetailsFromEntityToDomain(otsUsers)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return userDetails;
	}
	
	@Override
	public UserDetails getDistributorDetails(String distributorId) {
    	UserDetails userDetails = new UserDetails();
    	try {
            OtsUsers otsUsers = null;
        	Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsDistributorId", UUID.fromString(distributorId));
			try {
				otsUsers  = super.getResultByNamedQuery("OtsUsers.getDistributorDetails", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
	
			userDetails =  convertUserDetailsFromEntityToDomainForResponse(otsUsers);
    	}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
    	return userDetails;
	}
	
	@Override
	public List<UserDetails> getUserDetailsByRoleAndStatus(GetUserStatusRequest getUserStatusRequest) {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		try {
			OtsUserRole userRoleId = new OtsUserRole();
			userRoleId.setOtsUserRoleId(Integer.parseInt(getUserStatusRequest.getRequest().getUserRoleId()));
			
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersStatus", getUserStatusRequest.getRequest().getUserStatus());
			queryParameter.put("otsUserRoleId", userRoleId);
			
			List<OtsUsers> userList = super.getResultListByNamedQuery("OtsUsers.findByRoleIdAndUserStatus", queryParameter);
			userDetails = userList.stream().map(otsUsers -> convertUserDetailsFromEntityToDomainForResponse(otsUsers))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return userDetails;
	}
	
	@Override
	public String deleteDistributor(String distributorId) {
	    String sellerResponse = null;
	    try {

	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("ots_distributor_id", UUID.fromString(distributorId));

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withFunctionName("delete_distributor")
	                .withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("ots_distributor_id", Types.OTHER));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
	        List<Map<String, Object>> outputResult =
	                (List<Map<String, Object>>) queryResult.get("#result-set-1");

	        // converting output of procedure to String
	        String response = outputResult.get(0).values().toString();

	        // comparing response of procedure & handling response
	        if (response.equalsIgnoreCase("[No User Found]")) {
	            sellerResponse = "No User Found";
	        } 
	        else if (response.equalsIgnoreCase("[Pending Orders Or Products]")) {
	            sellerResponse = "Pending Orders Or Products";
	        } 
	        else if (response.equalsIgnoreCase("[Unable To Delete]")) {
	            sellerResponse = "Unable To Delete";
	        } 
	        else if (response.equalsIgnoreCase("[Deleted Successfully]")) {   // ✔ FIXED
	            sellerResponse = "Deleted Successfully";
	        } 
	        else {
	            sellerResponse = "Unknown Response";
	        }

	    } catch (Exception e) {
	        logger.error("Exception while Inserting data into DB :" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);

	    } catch (Throwable e) {
	        logger.error("Exception while Inserting data into DB :" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
	    }

	    return sellerResponse;
	}

	
	@Override
	public List<UserDetails> getIncompleteSellerRegistrations() {
	    String sql = "SELECT * FROM public.get_incomplete_seller_registrations()";
	    try {
	        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

	        List<UserDetails> userDetailsList = new ArrayList<>();
	        for (Map<String, Object> row : rows) {
	            userDetailsList.add(convertUserDetailsFromProcedureToDomain(row));
	        }

	        return userDetailsList;
	    }catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
