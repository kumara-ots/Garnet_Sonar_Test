package com.fuso.enterprise.ots.srv.rest.ws.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.fuso.enterprise.ots.srv.api.model.domain.CustomerChangeAddress;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorBanner;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCountryCodeName;
import com.fuso.enterprise.ots.srv.api.model.domain.GetDistributorCompleteDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductLocationIDName;
import com.fuso.enterprise.ots.srv.api.model.domain.SubadminDetailsModel;
import com.fuso.enterprise.ots.srv.api.model.domain.SubadminValidity;
import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSUserService;
import com.fuso.enterprise.ots.srv.api.service.request.AddCustomerChangeAddressRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorBannerRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorCompanyDetailsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorCountryMappingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorRegistrationDetailsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddHolidayRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductLocationMappingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddRegistrationTransactionCancelRecord;
import com.fuso.enterprise.ots.srv.api.service.request.AddReviewAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddToCartRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddUpdateSubadminValidity;
import com.fuso.enterprise.ots.srv.api.service.request.AddUserDataBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddWishListRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ChangePasswordRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CheckProductAvailabilityRequest;
import com.fuso.enterprise.ots.srv.api.service.request.DeleteDistributorBannerRequest;
import com.fuso.enterprise.ots.srv.api.service.request.DistributorActiveInactiveRequest;
import com.fuso.enterprise.ots.srv.api.service.request.DistributorAndProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ForgotAdminPasswordRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ForgotPasswordRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GenerateDistributorRegistrationInvoiceRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetDistrictsByMultipleStates;
import com.fuso.enterprise.ots.srv.api.service.request.GetPincodeByMultipleDistricts;
import com.fuso.enterprise.ots.srv.api.service.request.GetReviewRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetReviewsAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetUserDetailsByContactNoRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetUserStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.LoginAuthenticationBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.MapUsersDataBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.MappedToBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.RequestBOUserBySearch;
import com.fuso.enterprise.ots.srv.api.service.request.UpdatePasswordRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateReviewStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateUserContactRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateUserEmailIdRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateUserStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.response.AverageReviewRatingResponse;
import com.fuso.enterprise.ots.srv.api.service.response.DistributerBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.DistributerResponse;
import com.fuso.enterprise.ots.srv.api.service.response.DistributorCountryResponse;
import com.fuso.enterprise.ots.srv.api.service.response.DistributorPaymentDetailsResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ForgotPasswordResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetCartResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetHolidayListResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetReviewAndRatingResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceableLocationResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetwishListResponse;
import com.fuso.enterprise.ots.srv.api.service.response.LoginUserResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ProductLocationResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceCountryResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceDistrictResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServicePincodeResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceStateResponse;
import com.fuso.enterprise.ots.srv.api.service.response.StateDistrictResponse;
import com.fuso.enterprise.ots.srv.api.service.response.UserAccountsResponse;
import com.fuso.enterprise.ots.srv.api.service.response.UserDataBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.UserDetailsPageloaderResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.util.ResponseWrapper;

public class OTSUsersV18_1WsImpl implements OTSUsersV18_1Ws{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	ResponseWrapper responseWrapper ;
	
	@Inject
	private OTSUserService otsUserService;
	
	@Value("${product.percentage.price}")
	public String productPercentage;

	@Value("${ots.latitude}")
	public String latitude;
	
	@Value("${ots.longitude}")
	public String longitude;
	
	@Value("${ots.adminRoleId}")
	public String adminRoleId;
	
	@Value("${ots.distributorRoleId}")
	public String distributorRoleId;
	
	@Value("${ots.employeeRoleId}")
	public String employeeRoleId;
	
	@Value("${ots.customerRoleId}")
	public String customerRoleId;
	
	public Response buildResponse(Object data,String description) {
		ResponseWrapper wrapper = new ResponseWrapper(200,description, data);
		return Response.ok(wrapper).build();
	}
	
	public Response buildResponse(int code,String description) {
		ResponseWrapper wrapper = new ResponseWrapper(code,description);
		return Response.ok(wrapper).build();
	}
	
	public Response buildResponse(int code,Object data,String description) {
		ResponseWrapper wrapper = new ResponseWrapper(code,description, data);
		return Response.ok(wrapper).build();
	}
	
	@Override
	public Response getUserIDUsers(String userId) {
		Response response =null;
		try {
			UserDataBOResponse userDataBOResponse = otsUserService.getUserIDUsers(userId);
			if(userDataBOResponse.getUserDetails().size() != 0) {
				response = responseWrapper.buildResponse(200,userDataBOResponse,"Successful");
			}else{
				response = responseWrapper.buildResponse(404,"No Data Available");
			}
		} catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response addNewUser(AddUserDataBORequest addUserDataBORequest) {
		Response response = null;
		try{
			if(addUserDataBORequest.getRequestData().getFirstName()==null || addUserDataBORequest.getRequestData().getFirstName().equals("")
					|| addUserDataBORequest.getRequestData().getLastName() == null || addUserDataBORequest.getRequestData().getLastName().equals("")
					|| addUserDataBORequest.getRequestData().getUsrPassword() == null || addUserDataBORequest.getRequestData().getUsrPassword().equals("")
					|| addUserDataBORequest.getRequestData().getUserRoleId() == null || addUserDataBORequest.getRequestData().getUserRoleId().equals("")
					|| addUserDataBORequest.getRequestData().getUsrStatus() == null || addUserDataBORequest.getRequestData().getUsrStatus().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			
			if(addUserDataBORequest.getRequestData().getEmailId()==null || addUserDataBORequest.getRequestData().getEmailId().equals("")
					|| addUserDataBORequest.getRequestData().getContactNo() == null || addUserDataBORequest.getRequestData().getContactNo().equals("")) {
				return response = buildResponse(400,"Email Id or Contact Number should not be Empty");
			}
			
		    String userRoleId = addUserDataBORequest.getRequestData().getUserRoleId();

	        // Optional: validate that createdBy is 'self' or 'admin' when roleId = 2
	        if("2".equalsIgnoreCase(userRoleId)) {
	            String createdBy = addUserDataBORequest.getRequestData().getUsersCreatedBy();
	            if(createdBy == null ||createdBy.trim().isEmpty() || (!createdBy.equalsIgnoreCase("self") && !createdBy.equalsIgnoreCase("admin"))) {
	                return buildResponse(400,"Invalid value for createdBy, Must be 'self' or 'admin' for role 2");
	            }
	        }    
			    
			//checking role id for user if user has role as distributor and admin we setting lat and long as 0 to avoid code break in getting lat and lon by nearest distance distributor.
			if(!(addUserDataBORequest.getRequestData().getUserRoleId().equalsIgnoreCase(distributorRoleId)||addUserDataBORequest.getRequestData().getUserRoleId().equalsIgnoreCase(adminRoleId))) {
				addUserDataBORequest.getRequestData().setUserLat(latitude);
				addUserDataBORequest.getRequestData().setUserLong(longitude);
			}
			if(addUserDataBORequest.requestData.getUserRoleId().equalsIgnoreCase("4")) {
				addUserDataBORequest.requestData.setMappedTo("1");
			}
			
			//Mandatory Request fields Required for Adding Customer
		    if("4".equalsIgnoreCase(userRoleId)) {
		    	if(addUserDataBORequest.getRequestData().getCompanyName()==null || addUserDataBORequest.getRequestData().getCompanyName().equals("")
		    			|| addUserDataBORequest.getRequestData().getHouseNo()==null || addUserDataBORequest.getRequestData().getHouseNo().equals("")
						|| addUserDataBORequest.getRequestData().getBuildingName() == null || addUserDataBORequest.getRequestData().getBuildingName().equals("")
						|| addUserDataBORequest.getRequestData().getStreetName() == null || addUserDataBORequest.getRequestData().getStreetName().equals("")
						|| addUserDataBORequest.getRequestData().getCityName() == null || addUserDataBORequest.getRequestData().getCityName().equals("")
						|| addUserDataBORequest.getRequestData().getPincode() == null || addUserDataBORequest.getRequestData().getPincode().equals("")
						|| addUserDataBORequest.getRequestData().getStateName() == null || addUserDataBORequest.getRequestData().getStateName().equals("")
						|| addUserDataBORequest.getRequestData().getDistrictName() == null
						|| addUserDataBORequest.getRequestData().getCountryName() == null || addUserDataBORequest.getRequestData().getCountryName().equals("")
						|| addUserDataBORequest.getRequestData().getCompanyRegistration() == null || addUserDataBORequest.getRequestData().getCompanyRegistration().equals("")
						|| addUserDataBORequest.getRequestData().getTaxCard() == null || addUserDataBORequest.getRequestData().getTaxCard().equals("")
						|| addUserDataBORequest.getRequestData().getComputerCard() == null || addUserDataBORequest.getRequestData().getComputerCard().equals("")
						|| addUserDataBORequest.getRequestData().getSignatoryIdCard() == null || addUserDataBORequest.getRequestData().getSignatoryIdCard().equals("")
						|| addUserDataBORequest.getRequestData().getTradeLicense() == null || addUserDataBORequest.getRequestData().getTradeLicense().equals("")) {
					return response = buildResponse(400,"Please Enter Required Inputs");
				}
	        }
			
			//To check email & contact exist or not for New User
			String checkUser = otsUserService.checkEmailContactExistOrNot(addUserDataBORequest);
			//comparing response of procedure & handling response
			if(checkUser.equalsIgnoreCase("Add User")) {
				UserDataBOResponse userDataBOResponse = otsUserService.addNewUser(addUserDataBORequest);
				if(userDataBOResponse.getUserDetails().size() == 0) {
					response = responseWrapper.buildResponse(404,"Unable To Register User");
				}else {
					response = responseWrapper.buildResponse(200,userDataBOResponse,"Successful");
				}
			}
			else if(checkUser.equalsIgnoreCase("Email And Contact Exist")) {
				response = responseWrapper.buildResponse(404,"Email Id And Phone Number Already Exists");
			}
			else if(checkUser.equalsIgnoreCase("Contact Exist")) {
				response = responseWrapper.buildResponse(404,"Phone Number Already Exists");
			}
			else if(checkUser.equalsIgnoreCase("Email Exist")) {
				response = responseWrapper.buildResponse(404,"Email Id Already Exists");
			}
			else {
				response = responseWrapper.buildResponse(404,"Unable To Register User");
			}
		} catch(Exception e){
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateUser(AddUserDataBORequest addUserDataBORequest) {
		Response response = null;
		try{
			if(addUserDataBORequest.getRequestData().getUserId() == null || addUserDataBORequest.getRequestData().getUserId().equals("")
					|| addUserDataBORequest.getRequestData().getFirstName() == null || addUserDataBORequest.getRequestData().getFirstName().equals("")
					|| addUserDataBORequest.getRequestData().getLastName() == null || addUserDataBORequest.getRequestData().getLastName().equals("")
					|| addUserDataBORequest.getRequestData().getEmailId()==null || addUserDataBORequest.getRequestData().getEmailId().equals("")
					|| addUserDataBORequest.getRequestData().getContactNo() == null || addUserDataBORequest.getRequestData().getContactNo().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			
			//To check email & contact exist or not for other users
			String checkUser = otsUserService.checkEmailContactExistOrNotForUpdate(addUserDataBORequest);
			
			//comparing response & handling response
			if(checkUser.equalsIgnoreCase("Update User")) {
				
				UserDataBOResponse userDataBOResponse = otsUserService.updateUser(addUserDataBORequest);
				if(userDataBOResponse.getUserDetails().size() == 0) {
					response = responseWrapper.buildResponse(404,"User Details Not Updated");
				}else {
					response = responseWrapper.buildResponse(200,userDataBOResponse,"Successful");
				}
			}
			else if(checkUser.equalsIgnoreCase("Email And Contact Exist")) {
				response = responseWrapper.buildResponse(404,"Email Id And Phone Number Already Exists");
			}
			else if(checkUser.equalsIgnoreCase("Contact Exist")) {
				response = responseWrapper.buildResponse(404,"Phone Number Already Exists");
			}
			else if(checkUser.equalsIgnoreCase("Email Exist")) {
				response = responseWrapper.buildResponse(404,"Email Id Already Exists");
			}
			else {
				response = responseWrapper.buildResponse(404,"User Details Not Updated");
			}
		} catch(Exception e){
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response mappUser(MapUsersDataBORequest mapUsersDataBORequest) {
		Response response;
		try {
			String responseData = otsUserService.mappUser(mapUsersDataBORequest);
			if (responseData != null) {
				logger.info("Inside Event=1005,Class:OTSUsersV18_1WsImpl,Method:mappUser, " + "successful");
			}
			response = buildResponse(200,responseData);
	  	} catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getUserDetails(RequestBOUserBySearch requestBOUserBySearch) {		
		Response response =null;
		try {
			if(requestBOUserBySearch.getRequestData().getSearchKey() == null || requestBOUserBySearch.getRequestData().getSearchKey().equals("")
					|| requestBOUserBySearch.getRequestData().getSearchvalue() == null || requestBOUserBySearch.getRequestData().getSearchvalue().equals("")
					|| !(requestBOUserBySearch.getRequestData().getSearchKey().equalsIgnoreCase("UsersFirstname")
					|| requestBOUserBySearch.getRequestData().getSearchKey().equalsIgnoreCase("UsersLastname")
					|| requestBOUserBySearch.getRequestData().getSearchKey().equalsIgnoreCase("UsersEmailid")
					|| requestBOUserBySearch.getRequestData().getSearchKey().equalsIgnoreCase("UserRoleId")
					|| requestBOUserBySearch.getRequestData().getSearchKey().equalsIgnoreCase("pending"))) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
				
			UserDataBOResponse userDataBOResponse = otsUserService.getUserDetails(requestBOUserBySearch);
			if(userDataBOResponse.getUserDetails().size() == 0) {
				response = buildResponse(404,"User Does Not Exists");
			}else{
				response = buildResponse(200,userDataBOResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getEmployeesDetailsByDistributor(RequestBOUserBySearch requestBOUserBySearch) {		
		Response response =null;
		try {
			if(requestBOUserBySearch.getRequestData().getSearchKey() == null || requestBOUserBySearch.getRequestData().getSearchKey().equals("")
					|| requestBOUserBySearch.getRequestData().getSearchvalue() == null || requestBOUserBySearch.getRequestData().getSearchvalue().equals("")
					|| requestBOUserBySearch.getRequestData().getDistributorId() == null || requestBOUserBySearch.getRequestData().getDistributorId().equals("")) 
			{
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			UserDataBOResponse userDataBOResponse = otsUserService.getEmployeesDetailsByDistributor(requestBOUserBySearch);
			if(userDataBOResponse.getUserDetails().size() == 0) {
				response = buildResponse(404,"No Data found");
			}else{
				response = buildResponse(200,userDataBOResponse,"successful");
			}	
			return response;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
	}

	@Override
	public Response otsLoginAuthentication(LoginAuthenticationBOrequest loginAuthenticationBOrequest) {
		Response response = null;
		try {
			// checking request validation
			if (loginAuthenticationBOrequest.getRequestData().getPhoneNumber() == null || loginAuthenticationBOrequest.getRequestData().getPhoneNumber().equals("")
					|| loginAuthenticationBOrequest.getRequestData().getPassword() == null || loginAuthenticationBOrequest.getRequestData().getPassword().equals("")
					|| loginAuthenticationBOrequest.getRequestData().getRole() == null || loginAuthenticationBOrequest.getRequestData().getRole().equalsIgnoreCase("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			
			//Predefined user status
			String[] VALID_ROLE = {"2", "Customer"};

		    // Validate input user status
		    String userRole = loginAuthenticationBOrequest.getRequestData().getRole();
		    boolean isValidRole = Arrays.stream(VALID_ROLE)
		                                  .anyMatch(status -> status.equalsIgnoreCase(userRole));

		    //If input User Role not matching predefined Role's
		    if (!isValidRole) {
		        return response = responseWrapper.buildResponse(400, "Invalid Input User Role");
		    }  
			
		    //Calling Login Authentication Api
		    LoginUserResponse loginUserResponse = otsUserService.otsLoginAuthentication(loginAuthenticationBOrequest);
		    if (loginUserResponse == null ) {
				return response = buildResponse(401, "This Phone Number Is Not Registered");
			}
			
			//To Allow login for Distributor when request role = 2 & userRoleId = 2. When request role = Customer, Allow for all User
			if (loginAuthenticationBOrequest.getRequestData().getRole().equals(loginUserResponse.getUserDetails().getUserRoleId()) || loginAuthenticationBOrequest.getRequestData().getRole().equalsIgnoreCase("Customer")) {
				if (BCrypt.checkpw(loginAuthenticationBOrequest.getRequestData().getPassword(),
						loginUserResponse.getUserDetails().getUsrPassword())) {
					// Check user account status
					String userStatus = loginUserResponse.getUserDetails().getUsrStatus();
					if ("pending".equalsIgnoreCase(userStatus)) {
						response = buildResponse(404, "Please Wait Or Ask For Admin Approval");
					} else if ("rejected".equalsIgnoreCase(userStatus)) {
						
						response = buildResponse(404, loginUserResponse.getUserDetails().getUserId(), "Your Approval Process Is Rejected for" + loginUserResponse.getUserDetails().getUsersRejectionReason() + ". Do You Want To Continue The Process?");
					} else if ("inactive".equalsIgnoreCase(userStatus)) {
						response = buildResponse(404, "Your Account Is Inactive");
					} else {
						// Successfully logged in
						response = buildResponse(200, loginUserResponse, "Login Successful");
					}
				} else {
					response = buildResponse(401, "Invalid Password. Please Provide A Valid Password.");
				}
			} else {
				// If the role doesn't match distributor or customer, reject the login attempt
				response = buildResponse(400, "Invalid User");
			}

		} catch (BusinessException e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getUserDetailsByMapped(MappedToBORequest mappedToBORequest) {
		Response response =null;
		try {	
			UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
			//getting mapped user details and adding to response to send UI
			userDataBOResponse = otsUserService.getUserDetailsByMapped(mappedToBORequest.getRequestData().getMappedTo());
			if(userDataBOResponse!=null) {
				response = responseWrapper.buildResponse(userDataBOResponse);
			}else {
				response = responseWrapper.buildResponse("No One is Mapped");
			}
			return response;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
	    	e.printStackTrace();
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
	}
	@Override
	public Response forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
		Response response = null;
		try {
			//Predefined user status
			String[] VALID_ROLE = {"2", "Customer"};

		    // Validate input user status
		    String userRole = forgotPasswordRequest.getRequest().getRole();
		    boolean isValidRole = Arrays.stream(VALID_ROLE).anyMatch(status -> status.equalsIgnoreCase(userRole));

		    //If input User Role not matching predefined Role's
		    if (!isValidRole) {
		        return response = responseWrapper.buildResponse(400, "Invalid Input User Role");
		    }  
			//To validate contact no is present in DB or not
		    UserDataBOResponse userDetails = otsUserService.getUserDetailsByContactNo(forgotPasswordRequest.getRequest().getMobileNumber());
			if(userDetails.getUserDetails().size()== 0) {
				return	response = buildResponse(404,"This Phone Number Is Not Registered");
			}
			if(forgotPasswordRequest.getRequest().getRole().equals(userDetails.getUserDetails().get(0).getUserRoleId()) || forgotPasswordRequest.getRequest().getRole().equalsIgnoreCase("Customer")) {
			
				ForgotPasswordResponse forgotPasswordResponse = otsUserService.sendOTP(forgotPasswordRequest);		

				//To mask response emailId 
				String maskEmail = maskEmail(userDetails.getUserDetails().get(0).getEmailId());
				if(forgotPasswordResponse==null) {
					response = buildResponse(404,"This Phone Number Is Not Registered");
				}else {
					response =  buildResponse(200,forgotPasswordResponse,"OTP Sent To Your Registered Email ID "+maskEmail);
				}
			}else {
				// If the role doesn't match distributor or customer, reject the login attempt
				response = buildResponse(400, "Invalid User");			
			}
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}catch (Throwable e){
			logger.error("Exception while Fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	public String  maskEmail(String email) {
		//To mask emailId with ***** in between after 2 digit of @ 
	    int atIndex = email.indexOf('@');
	    if (atIndex > 0) {
	        String maskedPart = email.substring(0,3) + "*****";
	        
	        return maskedPart + email.substring(atIndex-2);
	    } else {
	        // Handle the case where the email format is unexpected
	        return email;
	    }
	}
    
	@Override
	public Response changePassword(ChangePasswordRequest changePasswordRequest) {
		Response response = null;
		try {
			// Predefined user status
			String[] VALID_ROLE = { "2", "Customer" };

			// Validate input user status
			String userRole = changePasswordRequest.getRequest().getRole();
			boolean isValidRole = Arrays.stream(VALID_ROLE).anyMatch(status -> status.equalsIgnoreCase(userRole));

			// If input User Role not matching predefined Role's
			if (!isValidRole) {
				return response = responseWrapper.buildResponse(400, "Invalid Input User Role");
			}
			UserDataBOResponse userDetails = otsUserService.getUserIDUsers(changePasswordRequest.getRequest().getUserID());
			if (changePasswordRequest.getRequest().getRole().equals(userDetails.getUserDetails().get(0).getUserRoleId()) || changePasswordRequest.getRequest().getRole().equalsIgnoreCase("Customer")) {
				
				String changePassword = otsUserService.changePassword(changePasswordRequest);
				if (changePassword == null) {
					response = buildResponse(404, "Password Not Updated");
				} else {
					response = buildResponse(200, changePassword, "Password Updated.Please login");
				}
			} else {
				// If the role doesn't match distributor or customer, reject the login attempt
				response = buildResponse(400, "Invalid User");
			}
		} catch (Exception e) {
			logger.error("Exception while inserting data into DB:" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB:" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		Response response = null;
		try {
			if(updatePasswordRequest.getUpdatePassword().getNewPassword() == null || updatePasswordRequest.getUpdatePassword().getNewPassword().equals("")
						|| updatePasswordRequest.getUpdatePassword().getOldPassword() == null || updatePasswordRequest.getUpdatePassword().getOldPassword().equals("")
						|| updatePasswordRequest.getUpdatePassword().getUserId() == null || updatePasswordRequest.getUpdatePassword().getUserId().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			UserDataBOResponse userDataBOResponse = otsUserService.getUserIDUsers(updatePasswordRequest.getUpdatePassword().getUserId());
			if(userDataBOResponse.getUserDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,"User Not Present");
			}else {
				String updateResponse = otsUserService.updatePassword(updatePasswordRequest);
				if(updateResponse.equalsIgnoreCase("Password Updated Successfully")) {
					response = buildResponse(200,updateResponse,"Successful");	
				}else {
					response = buildResponse(401,"Please Check Your Old Password");
				}
			}
		}catch(Exception e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}catch (Throwable e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}
		return response;
	}

	@Override
	public Response addWishList(AddWishListRequest addWishListRequest) {
		Response response = null;
		try {
			String addWishList	=otsUserService.addWishList(addWishListRequest);
			if(addWishList == null)
			{
				response = buildResponse(404,"Product Not Added To Wishlist");
			}
			else if(addWishList.equalsIgnoreCase("Product Already Added To Wishlist")){
				response = buildResponse(206,"Product Already Added To Wishlist");
			}
			else {
				response = buildResponse(200,addWishList,"Successfull");
			}
		}catch(Exception e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}catch (Throwable e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}
		return response;
	}

	@Override
	public Response getWishList(String customerId) {
		Response response = null;
		try{
			List<GetwishListResponse> getwishListResponse = otsUserService.getWishList(customerId);
			if(getwishListResponse.size()==0)
			{
				response = buildResponse(404,"Empty WishList");
			}else
			{
				response = buildResponse(200,getwishListResponse,"Successful");
			}
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}
		return response;
	}

	@Override
	public Response addToCart(AddToCartRequest addToCartRequest) {
		Response response = null;
		try {
			String addcart = otsUserService.addToCart(addToCartRequest);
			if (addcart == null) {
				response = responseWrapper.buildResponse(404, "Product Not Added To Cart"); 
			} else if (addcart.equalsIgnoreCase("Invalid Product Details")) { 
				response = responseWrapper.buildResponse(409, "Invalid Product Details"); // 409: Conflict
			} else if (addcart.equalsIgnoreCase("Out Of Stock")) {
				response = responseWrapper.buildResponse(404, "Out Of Stock");
			} else if (addcart.equalsIgnoreCase("Product Added To Cart")) {
				response = responseWrapper.buildResponse(200, addcart, "Successful");
			} else {
				response = responseWrapper.buildResponse(404, addcart);
			}
		} catch (Exception e) {
			logger.error("Exception while inserting data into DB:" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB:" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getCartList(String customerId) {
		Response response = null;
		try{
			GetCartResponse getcartListResponse = otsUserService.getCartList(customerId);
			if(getcartListResponse.getCartList().size() == 0)
			{
				response = responseWrapper.buildResponse(404,"Cart is Empty,Please continue shopping...");
			}else{
				response = responseWrapper.buildResponse(200,getcartListResponse,"Successfull");
			}
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}
		return response;
	}

	@Override
	public Response removeFromCartList(AddToCartRequest addToCartRequest) {
		Response response = null;
		try {
			if(addToCartRequest.getRequestData().getCustomerId() == null || addToCartRequest.getRequestData().getCustomerId().equals("")
					|| addToCartRequest.getRequestData().getProductId() == null || addToCartRequest.getRequestData().getProductId().equals("")
					|| addToCartRequest.getRequestData().getOtsCartQty() == null || addToCartRequest.getRequestData().getOtsCartQty().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			
			String removeCart = otsUserService.removeFromCart(addToCartRequest);
			if(removeCart.equalsIgnoreCase("No Data Found")) {
				response = buildResponse(404,removeCart);
			}else {
				response = buildResponse(200,removeCart);
			}
		}catch(Exception e) {
			logger.error("Exception while fetching data from DB:"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB:"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
		
	@Override
	public Response removeFromWishList(AddWishListRequest addWishListRequest) {
		Response response = null;
		try {
			if(addWishListRequest.getRequestData().getCustomerId() == null || addWishListRequest.getRequestData().getCustomerId().equals("")
					|| addWishListRequest.getRequestData().getProductId() == null || addWishListRequest.getRequestData().getProductId().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			String removeFromWishList = otsUserService.removeFromWishList(addWishListRequest);
			if(removeFromWishList.equalsIgnoreCase("success")) {
				response = buildResponse(200,removeFromWishList);
			}else {
				response = buildResponse(404,"WishList Not Removed");
			}
		}catch(Exception e) {
			logger.error("Exception while fetching data from DB:"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB:"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response emptyCartList(AddToCartRequest addToCartRequest) {
		Response response = null;
		try {
			String responseValue = otsUserService.emptyCart(addToCartRequest);
			if (responseValue.equalsIgnoreCase("No Data Found")) {
				return response = buildResponse(404, "No Data Found");
			} else {
				return response = buildResponse(200,"Success");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}

	}

	@Override
	public Response addReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest) {
		Response response = null;
		try {
			if(addReviewAndRatingRequest.getRequestData().getCustomerId()==null || addReviewAndRatingRequest.getRequestData().getCustomerId().equals("")
					|| addReviewAndRatingRequest.getRequestData().getProductId()==null || addReviewAndRatingRequest.getRequestData().getProductId().equals("")
					|| addReviewAndRatingRequest.getRequestData().getOrderId()==null || addReviewAndRatingRequest.getRequestData().getOrderId().equals("")
					|| addReviewAndRatingRequest.getRequestData().getOtsRatingReviewTitle()==null || addReviewAndRatingRequest.getRequestData().getOtsRatingReviewTitle().equals("")
					|| addReviewAndRatingRequest.getRequestData().getOtsRatingReviewComment()==null || addReviewAndRatingRequest.getRequestData().getOtsRatingReviewComment().equals("")
					|| addReviewAndRatingRequest.getRequestData().getOtsRatingReviewRating()==null || addReviewAndRatingRequest.getRequestData().getOtsRatingReviewRating().equals("")) 
			{
				return response = buildResponse(400,"Please Enter required inputs");
			}	
			String ResponseValue = otsUserService.addReviewAndRating(addReviewAndRatingRequest);
			if(ResponseValue == "Review Rating Already Added") {
				response = buildResponse(206,"Review Rating Already Added");
			}
			else if(ResponseValue == "Inserted") {
				response = buildResponse(200,ResponseValue,"Successful");
			}
			else  {
				response = buildResponse(404,ResponseValue,"Not Inserted");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getReviewAndRating(GetReviewRatingRequest getReviewRatingRequest) {
		Response response = null;
		try{
			//checking input validation
			if(getReviewRatingRequest.getRequest().getSearchvalue()==null || getReviewRatingRequest.getRequest().getSearchvalue().equals("")
					|| getReviewRatingRequest.getRequest().getSearchKey()==null || getReviewRatingRequest.getRequest().getSearchKey().equals("")) 
			{
				return response = buildResponse(400,"Please Enter required inputs");
			}	
			List<GetReviewAndRatingResponse> getReviewAndRating=otsUserService.getReviewAndRating(getReviewRatingRequest);
			if(getReviewAndRating == null || getReviewAndRating.size()==0)
			{
				response =responseWrapper.buildResponse(404,getReviewAndRating,"No Review And Rating");
			}else{
				response =responseWrapper.buildResponse(200,getReviewAndRating,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getReviewAndRatingByOrderId(GetReviewsAndRatingRequest getReviewsAndRatingRequest) {
		Response response = null;
		try{
			//checking input validation
			if(getReviewsAndRatingRequest.getRequest().getOrderId()==null || getReviewsAndRatingRequest.getRequest().getOrderId().equals("")
					|| getReviewsAndRatingRequest.getRequest().getProductId()==null || getReviewsAndRatingRequest.getRequest().getProductId().equals("")
					|| getReviewsAndRatingRequest.getRequest().getCustomerId()==null || getReviewsAndRatingRequest.getRequest().getCustomerId().equals("")) 
			{
				return response = buildResponse(400,"Please Enter required inputs");
			}	
			List<GetReviewAndRatingResponse> getReviewAndRating = otsUserService.getReviewAndRatingByOrderId(getReviewsAndRatingRequest);
			if(getReviewAndRating.size()==0)
			{
				response =responseWrapper.buildResponse(404,getReviewAndRating,"No Review and Rating");
			}else{
				response =responseWrapper.buildResponse(200,getReviewAndRating,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
    
    @Override
	public Response getProductsByDistributorWithReviewAndRating(String distributorId) {
		Response response = null;
		try{
			List<ProductDetails> getReviewAndRating = otsUserService.getProductsByDistributorWithReviewAndRating(distributorId);
			if(getReviewAndRating.size()==0)
			{
				response =responseWrapper.buildResponse(404,"No Products Available");
			}else{
				response =responseWrapper.buildResponse(200,getReviewAndRating,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getAverageRatingOfProduct(String productId) {
		Response response = null;
		try{
			AverageReviewRatingResponse averageReviewRatingResponse = otsUserService.getAverageRatingOfProduct(productId);
			if(averageReviewRatingResponse == null)
			{
				response = responseWrapper.buildResponse(404,"No Review and Rating");
			}else{
				response = responseWrapper.buildResponse(200,averageReviewRatingResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateReviewStatus(UpdateReviewStatusRequest updateReviewStatusRequest) {
		Response response = null;
		try {
			if(updateReviewStatusRequest.getRequest().getOtsReviewRatingId() == null || updateReviewStatusRequest.getRequest().getOtsReviewRatingId().equals("")
					|| updateReviewStatusRequest.getRequest().getOtsReviewRatingStatus() == null || updateReviewStatusRequest.getRequest().getOtsReviewRatingStatus().equals("")) {
				return response = buildResponse(400,"Please Enter required inputs");
			}
			String updateReview = otsUserService.updateReviewStatus(updateReviewStatusRequest);
			if(updateReview != null) {
				response = buildResponse(200,updateReview,"Successful");
				
			}else {
				response = buildResponse(404,"Not Updated");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest) {
		Response response = null;
		try {
			if(addReviewAndRatingRequest.getRequestData().getOtsRatingReviewId() == null || addReviewAndRatingRequest.getRequestData().getOtsRatingReviewId().equals("")
					|| addReviewAndRatingRequest.getRequestData().getOtsRatingReviewTitle() == null || addReviewAndRatingRequest.getRequestData().getOtsRatingReviewTitle().equals("")
					|| addReviewAndRatingRequest.getRequestData().getOtsRatingReviewComment() == null || addReviewAndRatingRequest.getRequestData().getOtsRatingReviewComment().equals("")
					|| addReviewAndRatingRequest.getRequestData().getOtsRatingReviewRating() == null || addReviewAndRatingRequest.getRequestData().getOtsRatingReviewRating().equals("")) {
				return response = buildResponse(400,"Please Enter required inputs");
			}
			String updateReview = otsUserService.updateReviewAndRating(addReviewAndRatingRequest);
			if(updateReview != null) {
				response = buildResponse(200,updateReview,"Successful");
				
			}else {
				response = buildResponse(404,"Not Updated");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response loginWithOtp(LoginAuthenticationBOrequest loginAuthenticationBOrequest) {
		Response response = null;
		try {
			UserDetails userDetails = new UserDetails();
			userDetails = otsUserService.loginWithOtp(loginAuthenticationBOrequest);
			response = buildResponse(200,userDetails,"successful");
		}catch(Exception e) {
			response = responseWrapper.buildResponse(404,"user not found");
		}
		return response;
	}

	// * Sachin *// 
	@Override
	public Response getBannerinfo(RequestBOUserBySearch requestBOUserBySearch) {
		Response response = null;
		try {
			if(otsUserService.getBannerinfo(requestBOUserBySearch).isEmpty())
			{
				response = responseWrapper.buildResponse(404,"account not found");
				return response; 	
			}
			else{
				response = buildResponse(200,otsUserService.getBannerinfo(requestBOUserBySearch),"successful");
			}
		}
		catch(Exception e) {
			response = responseWrapper.buildResponse(404,"Account Id not found");
		}
		return response;
	}
	
	@Override
	public Response getAccountFooterDetails(RequestBOUserBySearch requestBOUserBySearch) {
		Response response = null;
		try {
			List<Map<String, Object>> accountfooter = otsUserService.getAccountFooterDetails(requestBOUserBySearch);
			if(accountfooter.size() == 0)
			{
				response = responseWrapper.buildResponse(404,"Account Id not found");
				return response; 
			}
			else{
				response = buildResponse(200,accountfooter,"successful");
			}
		}
		catch(Exception e) {
			response = responseWrapper.buildResponse(404,"Account Id not found");
		}
		return response;
	}

	@Override
	public Response getActiveDistributerList()
	{
		Response response = null;
		try {
		
			DistributerBOResponse DistributerBOResponse = new DistributerBOResponse();
			DistributerBOResponse=otsUserService.getActiveDistributerList();
				response = responseWrapper.buildResponse(200,DistributerBOResponse,"successful");
		}
		catch(Exception e) {
			response = responseWrapper.buildResponse(404,"List not found");
		}
		return response;
	}
	
	@Override
	public Response getDistributorByCreatedUser(String CreatedUser) {
		Response response =null;
		UserDataBOResponse UserDataBOResponse = new UserDataBOResponse();
		try {
			UserDataBOResponse = otsUserService.getDistributorByCreatedUser(CreatedUser);
			if(UserDataBOResponse.getUserDetails().size() != 0) {
				response = responseWrapper.buildResponse(200,UserDataBOResponse,"successful");
			}else
			{
				response = responseWrapper.buildResponse(404,UserDataBOResponse,"No data available");
			}
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getAllBannerinfo()
	{
		Response response = null;
		try {
			List<Map<String, Object>> getBanner = otsUserService.getAllBannerinfo();
			if(getBanner.isEmpty()){
				return response = responseWrapper.buildResponse(404,"List not found");
			}
			else{
				response = responseWrapper.buildResponse(200,getBanner,"successful");
			}
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getDistributerDetails() {
		Response response = null;
		try {
			DistributerResponse distributerResponse = otsUserService.getDistributerDetails();
			if(distributerResponse.getDistributerList().size() == 0) {
				response = responseWrapper.buildResponse(404,"No Data Found");
			}else {
				response = responseWrapper.buildResponse(200,distributerResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest) {
		Response response = null;
		try {
			if(addDistributorBannerRequest.getRequest().getBannerImage() == null || addDistributorBannerRequest.getRequest().getBannerImage().equals("")
					|| addDistributorBannerRequest.getRequest().getBannerContent() == null || addDistributorBannerRequest.getRequest().getBannerContent().equals("")
					|| addDistributorBannerRequest.getRequest().getDistributorId() == null || addDistributorBannerRequest.getRequest().getDistributorId().equals("")) 
			{
				return response = responseWrapper.buildResponse(400,"Please Enter required inputs");
			}
			String responseValue = otsUserService.addDistributorBanner(addDistributorBannerRequest);
			if(responseValue==null) {
				response = responseWrapper.buildResponse(404,"Not Inserted");
			}else {
				response = responseWrapper.buildResponse(200,responseValue,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getDistributorBanner(String distributorId){
		Response response = null;
		try {
			List<DistributorBanner> bannerDetails = otsUserService.getDistributorBanner(distributorId);
			if(bannerDetails.size()==0) {
				response = responseWrapper.buildResponse(404,"No Data Found");
			}else {
				response = responseWrapper.buildResponse(200,bannerDetails,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest) {
		Response response = null;
		try {
			if(addDistributorBannerRequest.getRequest().getBannerId() == null || addDistributorBannerRequest.getRequest().getBannerId().equals("")) 
			{
				return response = responseWrapper.buildResponse(400,"Please Enter Banner Id");
			}
			String responseValue = otsUserService.updateDistributorBanner(addDistributorBannerRequest);
			if(responseValue==null) {
				response = responseWrapper.buildResponse(404,"Not Updated");
			}else {
				response = responseWrapper.buildResponse(200,responseValue,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response deleteDistributorBanner(DeleteDistributorBannerRequest deleteDistributorBannerRequest) {
		Response response = null;
		try {
			if(deleteDistributorBannerRequest.getBannerId() == null|| deleteDistributorBannerRequest.getBannerId().equals("")) 
			{
				return response = responseWrapper.buildResponse(400,"Please Enter Banner Id");
			}
			String bannerId = deleteDistributorBannerRequest.getBannerId();
			
			DistributorBanner banner = otsUserService.getBannerDetailsByBannerId(bannerId);
			if(banner.getBannerId() == null) {
				response = responseWrapper.buildResponse(404,"Banner Id does not exists");
				
			}else {
				if(otsUserService.deleteDistributorBanner(bannerId)==null) {
					response = responseWrapper.buildResponse(404,"Not Deleted");
				}else {
					response = responseWrapper.buildResponse(200,otsUserService.deleteDistributorBanner(bannerId),"Successful");
				}
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest) {
		Response response = null;
		try {
			if(addCustomerChangeAddressRequest.getRequest().getCustomerId() == null || addCustomerChangeAddressRequest.getRequest().getCustomerId().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getCustomerFirstName() == null || addCustomerChangeAddressRequest.getRequest().getCustomerFirstName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getCustomerSecondName() == null || addCustomerChangeAddressRequest.getRequest().getCustomerSecondName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getCustomerContactNo() == null || addCustomerChangeAddressRequest.getRequest().getCustomerContactNo().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsHouseNo() == null || addCustomerChangeAddressRequest.getRequest().getOtsHouseNo().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsBuildingName() == null || addCustomerChangeAddressRequest.getRequest().getOtsBuildingName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsStreetName() == null || addCustomerChangeAddressRequest.getRequest().getOtsStreetName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsCityName() == null || addCustomerChangeAddressRequest.getRequest().getOtsCityName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsPinCode() == null || addCustomerChangeAddressRequest.getRequest().getOtsPinCode().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsStateName() == null || addCustomerChangeAddressRequest.getRequest().getOtsStateName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsDistrictName() == null
					|| addCustomerChangeAddressRequest.getRequest().getOtsCountryName() == null || addCustomerChangeAddressRequest.getRequest().getOtsCountryName().equals(""))
			{
				return response = responseWrapper.buildResponse(400,"Please Enter Required Input");
			}
			String responseValue = otsUserService.addCustomerChangeAddress(addCustomerChangeAddressRequest);
			if(responseValue==null) {
				response = responseWrapper.buildResponse(404,"Delivery Address Not Added");
			}else if(responseValue.equalsIgnoreCase("Only Five Delivery Address Can Be Added")) 
			{
				response = responseWrapper.buildResponse(404,"Only Five Delivery Address Can Be Added");
			}else {
				response = buildResponse(200,"Delivery Address Added Successfully");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest) {
		Response response = null;
		try {
			if(addCustomerChangeAddressRequest.getRequest().getCustomerChangeAddressId() == null || addCustomerChangeAddressRequest.getRequest().getCustomerChangeAddressId().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getCustomerId() == null || addCustomerChangeAddressRequest.getRequest().getCustomerId().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getCustomerFirstName() == null || addCustomerChangeAddressRequest.getRequest().getCustomerFirstName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getCustomerSecondName() == null || addCustomerChangeAddressRequest.getRequest().getCustomerSecondName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getCustomerContactNo() == null || addCustomerChangeAddressRequest.getRequest().getCustomerContactNo().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsHouseNo() == null || addCustomerChangeAddressRequest.getRequest().getOtsHouseNo().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsBuildingName() == null || addCustomerChangeAddressRequest.getRequest().getOtsBuildingName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsStreetName() == null || addCustomerChangeAddressRequest.getRequest().getOtsStreetName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsCityName() == null || addCustomerChangeAddressRequest.getRequest().getOtsCityName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsPinCode() == null || addCustomerChangeAddressRequest.getRequest().getOtsPinCode().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsStateName() == null || addCustomerChangeAddressRequest.getRequest().getOtsStateName().equals("")
					|| addCustomerChangeAddressRequest.getRequest().getOtsDistrictName() == null
					|| addCustomerChangeAddressRequest.getRequest().getOtsCountryName() == null || addCustomerChangeAddressRequest.getRequest().getOtsCountryName().equals("")) 
			{
				return response = responseWrapper.buildResponse(400,"Please Enter Customer Change Address ID");
			}
			String responseValue = otsUserService.updateCustomerChangeAddress(addCustomerChangeAddressRequest);
			if(responseValue==null) {
				response = responseWrapper.buildResponse(404,"Not Inserted");
			}else {
				response = buildResponse(200,responseValue,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getCustomerChangeAddressByCustomerId(String customerId){
		Response response = null;
		try {
			List<CustomerChangeAddress> customerDetailsList = otsUserService.getCustomerChangeAddressByCustomerId(customerId);
			if(customerDetailsList.size()==0) {
				response = responseWrapper.buildResponse(404, "No Data Found");
			}else {
				response = responseWrapper.buildResponse(200,customerDetailsList,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getCustomerChangeAddressById(String customerChangeAddressId){
		Response response = null;
		try {
			List<CustomerChangeAddress> customerDetailsList = otsUserService.getCustomerChangeAddressById(customerChangeAddressId);
			if(customerDetailsList.size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Found");
			}else {
				response = responseWrapper.buildResponse(200,customerDetailsList,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response deleteCustomerChangeAddress(String customerChangeAddressId) {
		Response response = null;
		try {
			List<CustomerChangeAddress> customerDetailsList = otsUserService.getCustomerChangeAddressById(customerChangeAddressId);
			if(customerDetailsList.size() != 0) {
				String deleteCustAddress = otsUserService.deleteCustomerChangeAddress(customerChangeAddressId);
				if(deleteCustAddress==null) {
					response = responseWrapper.buildResponse(404,"Not Deleted");
				}else {
					response = responseWrapper.buildResponse(200,deleteCustAddress,"Successful");
				}
			}else {
				response = responseWrapper.buildResponse(404,"ID Does Not Exists");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest) {
		Response response = null;
		try {
			if(addDistributorCompanyDetailsRequest.getRequest().getDistributorId() == null || addDistributorCompanyDetailsRequest.getRequest().getDistributorId().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyName() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyName().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyAddress() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyAddress().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyPincode() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyPincode().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyContactNo() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyContactNo().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyEmailId() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyEmailId().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyTaxNumber() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyTaxNumber().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyBusinessRegistration() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyBusinessRegistration().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getAuthorizedSignatoryProof() == null || addDistributorCompanyDetailsRequest.getRequest().getAuthorizedSignatoryProof().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getBankConfirmationOnBankAccount() == null || addDistributorCompanyDetailsRequest.getRequest().getBankConfirmationOnBankAccount().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getTaxCard() == null || addDistributorCompanyDetailsRequest.getRequest().getTaxCard().equals("")){
				return response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			String responseValue = otsUserService.addDistributorCompanyDetails(addDistributorCompanyDetailsRequest);
			if(responseValue==null) {
				response = responseWrapper.buildResponse(404,"Not Inserted");
			}else if(responseValue == "Company Details Already Registered For This User") {
				response = responseWrapper.buildResponse(404,"Company Details Already Registered For This User");
			}else {
				response = responseWrapper.buildResponse(200,responseValue,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest) {
		Response response = null;
		try {
			if(addDistributorCompanyDetailsRequest.getRequest().getDistributorCompanyId() == null || addDistributorCompanyDetailsRequest.getRequest().getDistributorCompanyId().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyName() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyName().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyAddress() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyAddress().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyPincode() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyPincode().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyContactNo() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyContactNo().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyEmailId() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyEmailId().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyTaxNumber() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyTaxNumber().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getCompanyBusinessRegistration() == null || addDistributorCompanyDetailsRequest.getRequest().getCompanyBusinessRegistration().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getAuthorizedSignatoryProof() == null || addDistributorCompanyDetailsRequest.getRequest().getAuthorizedSignatoryProof().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getBankConfirmationOnBankAccount() == null || addDistributorCompanyDetailsRequest.getRequest().getBankConfirmationOnBankAccount().equals("")
					|| addDistributorCompanyDetailsRequest.getRequest().getTaxCard() == null || addDistributorCompanyDetailsRequest.getRequest().getTaxCard().equals("")){
				return response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			String responseValue = otsUserService.updateDistributorCompanyDetails(addDistributorCompanyDetailsRequest);
			if(responseValue.equalsIgnoreCase("Company Details Updated Successfully")) {
				response = responseWrapper.buildResponse(200,responseValue,"Successful");
			}else {
				response = responseWrapper.buildResponse(404,responseValue);
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getDistributorCompanyDetails(String distributorId){
		Response response = null;
		try {
			List<DistributorCompanyDetails> companyDetailsList = otsUserService.getDistributorCompanyDetails(distributorId);
			if(companyDetailsList.size()== 0) {
				response = responseWrapper.buildResponse(404, "No Data Found");
			}else {
				response = responseWrapper.buildResponse(200,companyDetailsList,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response generateDistributorRegistrationInvoicePdf(GenerateDistributorRegistrationInvoiceRequest generateDistributorRegistrationInvoiceRequest){
		Response response = null;
		try {
			if(generateDistributorRegistrationInvoiceRequest.getRequest().getDistributorId() == null || generateDistributorRegistrationInvoiceRequest.getRequest().getDistributorId().equals("")
					|| generateDistributorRegistrationInvoiceRequest.getRequest().getRegistrationPaymentId() == null || generateDistributorRegistrationInvoiceRequest.getRequest().getRegistrationPaymentId().equals("")) 
			{
				return response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			String registrationInvoice = otsUserService.generateDistributorRegistrationInvoicePdf(generateDistributorRegistrationInvoiceRequest.getRequest().getDistributorId(),generateDistributorRegistrationInvoiceRequest.getRequest().getRegistrationPaymentId());
			if(registrationInvoice == null) {
				response = responseWrapper.buildResponse(404, "Invoice Not Generated");
			}
			else if(registrationInvoice == "Distributor Not Added Company Details") {
				response = responseWrapper.buildResponse(404, "Distributor Not Added Company Details");
			}
			else {
				response = responseWrapper.buildResponse(200,registrationInvoice,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateUserStatus(UpdateUserStatusRequest updateUserStatusRequest) {
	    Response response = null;
	    try {
	        // Validate that usersId and usersStatus are not null or empty
	        if(updateUserStatusRequest.getRequest().getUsersId() == null || updateUserStatusRequest.getRequest().getUsersId().trim().isEmpty()
	                || updateUserStatusRequest.getRequest().getUsersStatus() == null || updateUserStatusRequest.getRequest().getUsersStatus().trim().isEmpty()) {
	            return response = responseWrapper.buildResponse(400, "Please Enter Required Input");
	        }

	        // Predefined user status values
	        String[] VALID_STATUSES = {"active", "rejected", "delete", "pending"};

	        // Validate input user status
	        String userStatus = updateUserStatusRequest.getRequest().getUsersStatus();
	        boolean isValidStatus = Arrays.stream(VALID_STATUSES)
	                                      .anyMatch(status -> status.equalsIgnoreCase(userStatus));
	      
	        //If input status not matching predefined status
	        if (!isValidStatus) {
	            return response = responseWrapper.buildResponse(400, "Invalid User Status");
	        }

	        // If status is 'rejected', ensure rejection reason is provided
	        if ("rejected".equalsIgnoreCase(userStatus)) {
	            String rejectionReason = updateUserStatusRequest.getRequest().getUsersRejectionReason();
	            if (rejectionReason == null || rejectionReason.trim().isEmpty()) {
	                return response = responseWrapper.buildResponse(400, "Please Enter Rejection Reason");
	            }
	        }

	        // Call the service method to update the status
	        String responseValue = otsUserService.updateUserStatus(updateUserStatusRequest);

	        // Handle response from service
	        if(responseValue.equalsIgnoreCase("Inactive All The Products & Close All The Orders To Delete Seller")) {
	            response = responseWrapper.buildResponse(206, "Inactive All The Products & Close All The Orders To Delete Seller");
	        } else if(responseValue.equalsIgnoreCase("Not Updated")) {
	            response = responseWrapper.buildResponse(404, "Status Not Updated");
	        } else {
	            response = responseWrapper.buildResponse(200, "Status Updated", "Successful");
	        }

	    } catch(Exception e) {
	        logger.error("Exception while updating user status  :" + e.getMessage());
	        e.printStackTrace();
	        return response = buildResponse(500, "Something Went Wrong");
	    } catch (Throwable e) {
	        logger.error("Exception while updating user status  :" + e.getMessage());
	        e.printStackTrace();
	        return response = buildResponse(500, "Something Went Wrong");
	    }
	    return response;
	}
	
	@Override
	public Response  addDistributorRegistrationDetails(AddDistributorRegistrationDetailsRequest addDistributorRegistrationDetailsRequest)  {
		Response response = null;
		try {
			if(addDistributorRegistrationDetailsRequest.getRequest().getRegistrationAmount() == null || addDistributorRegistrationDetailsRequest.getRequest().getRegistrationAmount().equals("")
					|| addDistributorRegistrationDetailsRequest.getRequest().getRegistrationPaymentId() == null || addDistributorRegistrationDetailsRequest.getRequest().getRegistrationPaymentId().equals("") 
					|| addDistributorRegistrationDetailsRequest.getRequest().getRegistrationPaymentStatus() == null || addDistributorRegistrationDetailsRequest.getRequest().getRegistrationPaymentStatus().equals("")
					|| addDistributorRegistrationDetailsRequest.getRequest().getUserId() == null || addDistributorRegistrationDetailsRequest.getRequest().getUserId().equals(""))
			{
				return response = responseWrapper.buildResponse(400,"Please Enter Required Input");
			}
			String responseValue = otsUserService.addDistributorRegistrationDetails(addDistributorRegistrationDetailsRequest);
			if(responseValue==null) {
				response = responseWrapper.buildResponse(404,"Not Inserted");
			}else {
				response = responseWrapper.buildResponse(200,responseValue,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getUserDetailsByContactNo(GetUserDetailsByContactNoRequest contactNoRequest) {
		Response response =null;
		try {
			if(contactNoRequest.getRequest().getContactNo() == null || contactNoRequest.getRequest().getContactNo().equals("")){
				return response = responseWrapper.buildResponse(400,"Please Enter Required Input");
			}
			UserDataBOResponse userDataBOResponse = otsUserService.getUserDetailsByContactNo(contactNoRequest.getRequest().getContactNo());
			if(userDataBOResponse.getUserDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,"No data available");
			}else
			{
				response = responseWrapper.buildResponse(200,userDataBOResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getAllStates() {
		Response response =null;
		try {
			ServiceStateResponse serviceStateResponse = otsUserService.getAllStates();
			if(serviceStateResponse.getStateDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,serviceStateResponse,"No data available");
			}else
			{
				response = responseWrapper.buildResponse(200,serviceStateResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getDistrictByStateId(String stateId) {
		Response response =null;
		try {
			ServiceDistrictResponse serviceDistrictResponse = otsUserService.getDistrictByStateId(stateId);
			if(serviceDistrictResponse.getDistrictDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,serviceDistrictResponse,"No data available");
			}else
			{
				response = responseWrapper.buildResponse(200,serviceDistrictResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response addProductLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest) {
	    Response response = null;
	    try {
	        // Validate distributorId
	        if (addProductLocationMappingRequest.getRequest().getDistributorId() == null  || addProductLocationMappingRequest.getRequest().getDistributorId().isEmpty()) {
	            return buildResponse(400, "Please Enter Required Inputs");
	        }

	        // Validate productLocationIDName list
	        if (addProductLocationMappingRequest.getRequest().getProductLocationIDName().size() == 0) {
	            return buildResponse(400, "Please Enter locationId");
	        }

	        // Validate each location in productLocationIDName
	        for (int i = 0; i < addProductLocationMappingRequest.getRequest().getProductLocationIDName().size(); i++) {
	            ProductLocationIDName productLocation = addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(i);

	            if (productLocation.getLocationId() == null || productLocation.getLocationId().isEmpty()) {
	                return buildResponse(400, "Please Enter locationId");
	            }

	            if (productLocation.getLocationName() == null || productLocation.getLocationName().isEmpty()) {
	                return buildResponse(400, "Please Enter locationName");
	            }
	        }
	        
	        //To Check if data already Added for Distributor level or Product level based on request parameters
	        if(addProductLocationMappingRequest.getRequest().getProductId() != null && !addProductLocationMappingRequest.getRequest().getProductId().equals("")) {
	        	ProductLocationResponse existingProductMappings = otsUserService.getLocationsMappedToProduct(addProductLocationMappingRequest.getRequest().getProductId());
	        	if(existingProductMappings.getProductLocation().size() != 0) {
	        		return responseWrapper.buildResponse(400, "Locations Have Already Been Added For This Seller. Please Try To Update.");
	        	}
	        }else {
	        	ProductLocationResponse existingDistributorMappings = otsUserService.getLocationsMappedToDistributorOnly(addProductLocationMappingRequest.getRequest().getDistributorId());
	        	if(existingDistributorMappings.getProductLocation().size() != 0) {
	        		return responseWrapper.buildResponse(400, "Locations Have Already Been Added For This Seller. Please Try To Update.");
	        	}
	        }
	        
	        //To add Service availability for Distributor role or Product level
	        ProductLocationResponse productLocationResponse = otsUserService.addProductLocationMapping(addProductLocationMappingRequest);
	        if (productLocationResponse.getProductLocation().size()== 0) {
	            response = responseWrapper.buildResponse(404, "Not Inserted");
	        } else {
	            response = responseWrapper.buildResponse(productLocationResponse, "Successful");
	        }

	    } catch (Exception e) {
	        logger.error("Exception while inserting data into DB: " + e.getMessage(), e);
	        response = buildResponse(500, "Something Went Wrong");
	    } catch (Throwable e) {
	        logger.error("Unexpected error occurred: " + e.getMessage(), e);
	        response = buildResponse(500, "Something Went Wrong");
	    }
	    return response;
	}
	
	@Override
	public Response clearAndAddProductLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest) {
		Response response = null;
		try {
			if(addProductLocationMappingRequest.getRequest().getDistributorId() == null || addProductLocationMappingRequest.getRequest().getDistributorId().equals("")
					|| addProductLocationMappingRequest.getRequest().getProductId() == null || addProductLocationMappingRequest.getRequest().getProductId().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			if(addProductLocationMappingRequest.getRequest().getProductLocationIDName().size() == 0) {
				return response = buildResponse(400,"Please Enter locationId");
			}
			if(addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(0).getLocationId() == null || addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(0).getLocationId().equals("") || addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(0).getLocationId().equalsIgnoreCase("string")
					|| addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(0).getLocationName() == null || addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(0).getLocationName().equals("") || addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(0).getLocationName().equalsIgnoreCase("string")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			ProductLocationResponse productLocationResponse = otsUserService.clearAndAddProductLocationMapping(addProductLocationMappingRequest);
			if(productLocationResponse == null || productLocationResponse.getProductLocation().size() == 0) {
				response = responseWrapper.buildResponse(404,"Not Inserted");
			}
			else{
				response = responseWrapper.buildResponse(productLocationResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		 return response;
	}
	
	@Override
	public Response checkProductLocationAvailability(CheckProductAvailabilityRequest checkProductAvailabilityRequest) {
		Response response = null;
		try {
			if(checkProductAvailabilityRequest.getRequest().getPincode() == null || checkProductAvailabilityRequest.getRequest().getPincode().equals("")
					|| checkProductAvailabilityRequest.getRequest().getProductId() == null || checkProductAvailabilityRequest.getRequest().getProductId().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			String productLocation = otsUserService.checkProductLocationAvailability(checkProductAvailabilityRequest);
			if(productLocation == null) {
				response = responseWrapper.buildResponse(404,"Invalid Inputs");
			}
			else if(productLocation.equalsIgnoreCase("Product is Available in this location")) {
				response = responseWrapper.buildResponse(200,productLocation,"Successful");
			}
			else {
				response = responseWrapper.buildResponse(206,"Product is Not Available in this location");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getAllDistricts() {
		Response response =null;
		try {
			ServiceDistrictResponse serviceDistrictResponse = otsUserService.getAllDistricts();
			if(serviceDistrictResponse.getDistrictDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,serviceDistrictResponse,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,serviceDistrictResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getAllStatesAndDistrictsList() {
		Response response =null;
		try {
			StateDistrictResponse statesDistrictsResponse = otsUserService.getAllStatesAndDistrictsList();
			if(statesDistrictsResponse == null) {
				response = responseWrapper.buildResponse(404,statesDistrictsResponse,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,statesDistrictsResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getDistributorPaymentDetails(String distributorId) {
		Response response =null;
		try {
			DistributorPaymentDetailsResponse distributorPaymentDetailsResponse = otsUserService.getDistributorPaymentDetails(distributorId);
			if(distributorPaymentDetailsResponse.getDistributorPaymentDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,distributorPaymentDetailsResponse,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,distributorPaymentDetailsResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getPincodeByDistrict(String districtId) {
		Response response =null;
		try {
			ServicePincodeResponse servicePincodeResponse	= otsUserService.getPincodeByDistrict(districtId);
			if(servicePincodeResponse.getPincodeDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,"No data available");
			}else
			{
				response = responseWrapper.buildResponse(200,servicePincodeResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getPincodeDetails(String pincode) {
		Response response =null;
		try {
			if(pincode == null || pincode.isEmpty() || !pincode.matches("\\d{6}")) {  // Assuming a 6-digit pincode pattern
	            return buildResponse(400, "Invalid Pincode");
	        }
			ServicePincodeResponse servicePincodeResponse	= otsUserService.getPincodeDetails(pincode);
			if(servicePincodeResponse.getPincodeDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,"Pincode Not Found");
			}else
			{
				response = responseWrapper.buildResponse(200,servicePincodeResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getPincodeByMultipleDistricts(GetPincodeByMultipleDistricts getPincodeByMultipleDistricts) {
		Response response =null;
		try {
			ServicePincodeResponse servicePincodeResponse	= otsUserService.getPincodeByMultipleDistricts(getPincodeByMultipleDistricts.getDistrictId());
			if(servicePincodeResponse.getPincodeDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,servicePincodeResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getDistrictsByMultipleStates(GetDistrictsByMultipleStates getDistrictsByMultipleStates) {
		Response response =null;
		try {
			ServiceDistrictResponse serviceDistrictResponse	= otsUserService.getDistrictsByMultipleStates(getDistrictsByMultipleStates.getStateId());
			if(serviceDistrictResponse.getDistrictDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,serviceDistrictResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getDistributorsWithActiveProductsByCountry(String countryCode) {
		Response response =null;
		try {
			UserDataBOResponse userDataBOResponse = otsUserService.getDistributorsWithActiveProductsByCountry(countryCode);
			if(userDataBOResponse.getUserDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,"No data available");
			}else
			{
				response = responseWrapper.buildResponse(200,userDataBOResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response updateUserContact(UpdateUserContactRequest UpdateUserContactRequest) {
		Response response = null;
		try {

			if (UpdateUserContactRequest.getRequest().getContactNo() == null|| UpdateUserContactRequest.getRequest().getContactNo().equals("")
				|| UpdateUserContactRequest.getRequest().getUserId() == null|| UpdateUserContactRequest.getRequest().getUserId().equals("")) {
				return response = responseWrapper.buildResponse(400, "Please Enter Required Inputs");

			}
			String Responsevalue = otsUserService.updateUserContact(UpdateUserContactRequest);
			if (Responsevalue.equalsIgnoreCase("This Phone Number Already Exists")) {
				response = buildResponse(206, "This Phone Number Already Exists");
			}
			else if(Responsevalue.equalsIgnoreCase("Phone Number Not Updated")) {
				response = buildResponse(206, "Phone Number Not Updated");
			}
			else if(Responsevalue.equalsIgnoreCase("Phone Number Updated Successfully")) {
				response = buildResponse(200, "Phone Number Updated Successfully","Successful");
			}
			else {
				response = buildResponse(408,"Unexpected Response");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateUserEmailId(UpdateUserEmailIdRequest updateUserEmailIdRequest) {
		Response response = null;
		try {
			if (updateUserEmailIdRequest.getRequest().getEmailID() == null|| updateUserEmailIdRequest.getRequest().getEmailID().equals("")
				|| updateUserEmailIdRequest.getRequest().getUserId() == null|| updateUserEmailIdRequest.getRequest().getUserId().equals("")) {
				return response = responseWrapper.buildResponse(400, "Please Enter Required Inputs");

			}
			String Responsevalue = otsUserService.updateUserEmailId(updateUserEmailIdRequest);
			
			if (Responsevalue.equalsIgnoreCase("This Email Already Exists")) {
				response = buildResponse(206, "This Email Already Exists");
			}
			else if(Responsevalue.equalsIgnoreCase("Email Not Updated")) {
				response = buildResponse(206, "Email Not Updated");
			}
			else if(Responsevalue.equalsIgnoreCase("Email Updated Successfully")) {
				response = buildResponse(200, "Email Updated Successfully","Successful");
			}
			else {
				response = buildResponse(408,"Unexpected Response");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response checkCartStockAvailability(String customerId) {
		Response response = null;
		try {
			String Responsevalue = otsUserService.checkCartStockAvailability(customerId);
			
			if(Responsevalue.equalsIgnoreCase("Stock Available")) {
				response = buildResponse(200,"Stock Available","Successful");
			}
			else if (Responsevalue.equalsIgnoreCase("Stock Not Available")) {
				response = buildResponse(206, "Stock Not Available");
			}
			else if (Responsevalue.equalsIgnoreCase("No Data Available")) {
				response = buildResponse(404, "No Data Available");
			}
			else {
				response = buildResponse(408,"Unexpected Response");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getServiceableLocationByDistributorOnly(DistributorAndProductRequest distributorAndProductRequest) {
		Response response = null;
		try {
			String distributorId = distributorAndProductRequest.getRequest().getDistributorId();

			if (distributorId != null) {
				UserDataBOResponse distributor = otsUserService.getUserIDUsers(distributorId);
				if (distributor.getUserDetails().size()==0) {
					return responseWrapper.buildResponse(404, "Invalid Seller");
				}
			}

			// Fetch serviceable location details
			GetServiceableLocationResponse getServiceableLocationResponse = otsUserService.getServiceableLocationByDistributorOnly(distributorAndProductRequest);
			if (getServiceableLocationResponse.getServiceableLocation().getStatelist().size() == 0 
				&& getServiceableLocationResponse.getServiceableLocation().getDistrictList().size() == 0
				&& getServiceableLocationResponse.getServiceableLocation().getPincodeList().size() == 0) {
				return response = responseWrapper.buildResponse(404, getServiceableLocationResponse, "No Serviceable Location Has Been Added");
			} else {
				return response = responseWrapper.buildResponse(200, getServiceableLocationResponse, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
			return buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			return buildResponse(500, "Something Went Wrong");
		}
	}

	@Override
	public Response checkDistributorRegistrationSteps(String distributorContactNo) {
		Response response =null;
		try {
			List<Map<String, Object>> distributorDetails = otsUserService.checkDistributorRegistrationSteps(distributorContactNo);
			if(distributorDetails == null) {
				response = responseWrapper.buildResponse(404,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,distributorDetails,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getSubAdminByStatus(String accountStatus) {
		Response response =null;
		try {
			UserAccountsResponse userAccountsResponse = otsUserService.getSubAdminByStatus(accountStatus);
			if(userAccountsResponse.getUserAccounts().size() == 0) {
				response = responseWrapper.buildResponse(404,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,userAccountsResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addSubAdminValidity(AddUpdateSubadminValidity addUpdateSubadminValidity) {
		Response response = null;
		try {
			if(addUpdateSubadminValidity.getRequest().getOtsAccountId() == null|| addUpdateSubadminValidity.getRequest().getOtsAccountId().equals("")
					|| addUpdateSubadminValidity.getRequest().getOtsValidityStart() == null|| addUpdateSubadminValidity.getRequest().getOtsValidityStart().equals("")
					|| addUpdateSubadminValidity.getRequest().getOtsValidityEnd() == null|| addUpdateSubadminValidity.getRequest().getOtsValidityEnd().equals("")
					|| addUpdateSubadminValidity.getRequest().getOtsDistributorCount() == null|| addUpdateSubadminValidity.getRequest().getOtsDistributorCount().equals("")
					|| addUpdateSubadminValidity.getRequest().getOtsTransactionCharges() == null|| addUpdateSubadminValidity.getRequest().getOtsTransactionCharges().equals("")) {
					return response = responseWrapper.buildResponse(400, "Please Enter Required Inputs");
			}
			SubadminValidity subadminValidity = otsUserService.getSubAdminValidity(addUpdateSubadminValidity.getRequest().getOtsAccountId());
			if(subadminValidity == null) {
				String subAdmindetails = otsUserService.addSubAdminValidity(addUpdateSubadminValidity);
				if(subAdmindetails == null)
				{
					response = buildResponse(404,"Validity Not Inserted");
				}
				else {
					response = buildResponse(200,subAdmindetails,"Successful");
				}
			}
			else {
				response = buildResponse(404,"Data Already Exists");
			}
		}catch(Exception e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}catch (Throwable e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}
		return response;
	}
	@Override
	public Response getApprovedSubAdmins() {
		Response response = null;
		try {
			List<SubadminDetailsModel> subAdmindetails = otsUserService.getApprovedSubAdmins();
			if(subAdmindetails.size() == 0)
			{
				response = buildResponse(404,"No data Available");
			}
			else {
				response = buildResponse(200,subAdmindetails,"Successful");
			}
		}catch(Exception e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}catch (Throwable e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		return response = buildResponse(500,"Something Went Wrong");
    	}
		return response;
	
	}

	@Override
	public Response updateSubAdminValidity(AddUpdateSubadminValidity addUpdateSubadminValidity) {
		Response response = null;
		try {
			if(addUpdateSubadminValidity.getRequest().getOtsValidityId() == null|| addUpdateSubadminValidity.getRequest().getOtsValidityId().equals("")
					|| addUpdateSubadminValidity.getRequest().getOtsValidityStart() == null|| addUpdateSubadminValidity.getRequest().getOtsValidityStart().equals("")
					|| addUpdateSubadminValidity.getRequest().getOtsValidityEnd() == null|| addUpdateSubadminValidity.getRequest().getOtsValidityEnd().equals("")
					|| addUpdateSubadminValidity.getRequest().getOtsDistributorCount() == null|| addUpdateSubadminValidity.getRequest().getOtsDistributorCount().equals("")
					|| addUpdateSubadminValidity.getRequest().getOtsTransactionCharges() == null|| addUpdateSubadminValidity.getRequest().getOtsTransactionCharges().equals("")) {
					return response = responseWrapper.buildResponse(400, "Please Enter Required Inputs");
			}
			String responseValue = otsUserService.updateSubAdminValidity(addUpdateSubadminValidity);
			if(responseValue==null) {
				response = responseWrapper.buildResponse(404,"Validity Not Updated");
			}else {
				response = responseWrapper.buildResponse(200,responseValue,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response distributorActiveInactive(DistributorActiveInactiveRequest distributorActiveInactiveRequest) {
		Response response = null;
		try {
			if (distributorActiveInactiveRequest.getRequest().getDistributorId() == null|| distributorActiveInactiveRequest.getRequest().getDistributorId().equals("")
				|| distributorActiveInactiveRequest.getRequest().getDistributorStatus() == null|| distributorActiveInactiveRequest.getRequest().getDistributorStatus().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}

			String productUpdate = otsUserService.distributorActiveInactive(distributorActiveInactiveRequest);
			if (productUpdate.equalsIgnoreCase("Activated")) {
				response = responseWrapper.buildResponse(200, "Seller Activated Successful");
			} else if (productUpdate.equalsIgnoreCase("Inactivated")) {
				response = responseWrapper.buildResponse(200, "Seller Inactivated Successful");
			} else if (productUpdate.equalsIgnoreCase("Not Updated")) {
				response = responseWrapper.buildResponse(404, "Seller Status Not Updated");
			}
			else {
				response = responseWrapper.buildResponse(500, "Something Went Wrong");
			}
			return response;
		} catch (Exception e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
	}

	@Override
	public Response clearAndAddDistributorLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest) {
		Response response = null;
		try {
			if (addProductLocationMappingRequest.getRequest().getDistributorId() == null|| addProductLocationMappingRequest.getRequest().getDistributorId().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			if (addProductLocationMappingRequest.getRequest().getProductLocationIDName().size() == 0) {
				return response = buildResponse(400, "Please Enter locationId");
			}
			for (int i = 0; i < addProductLocationMappingRequest.getRequest().getProductLocationIDName().size(); i++) {
				ProductLocationIDName productLocation = addProductLocationMappingRequest.getRequest().getProductLocationIDName().get(i);

				if (productLocation.getLocationId() == null || productLocation.getLocationId().equals("")) {
					return response = buildResponse(400, "Please Enter LocationId");
				}
				if (productLocation.getLocationName() == null || productLocation.getLocationName().equals("")) {
					return response = buildResponse(400, "Please Enter LocationName");
				}
			}
			ProductLocationResponse productLocationResponse = otsUserService.clearAndAddDistributorLocationMapping(addProductLocationMappingRequest);
			if (productLocationResponse == null || productLocationResponse.getProductLocation().size() == 0) {
				response = responseWrapper.buildResponse(404, "Not Inserted");
			} else {
				response = responseWrapper.buildResponse(productLocationResponse, "Successful");
			}

		} catch (Exception e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addRegistrationTransactionCancelRecord(AddRegistrationTransactionCancelRecord addRegistrationTransactionCancelRecord)  {
		Response response = null;
		try {
			if(addRegistrationTransactionCancelRecord.getRequest().getOtsUsersId() == null || addRegistrationTransactionCancelRecord.getRequest().getOtsUsersId().equals("") 
			  ||addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationAmount() == null || addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationAmount().equals("")
			  ||addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationTrackingId() == null || addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationTrackingId().equals("")
			  ||addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationTransactionId() == null || addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationTransactionId().equals("")
			  ||addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationTransactionStatus() == null || addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationTransactionStatus().equals("")){
				
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			
			String responseValue = otsUserService.addRegistrationTransactionCancelRecord(addRegistrationTransactionCancelRecord);
			if(responseValue==null) {
				response = responseWrapper.buildResponse(404,"Not Inserted");
			}else {
				response = responseWrapper.buildResponse(200,responseValue,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response buildInfo() {
		Response response =null;
		Properties properties = new Properties();
	    Map<String, String> buildInfo = new HashMap<>();
		try {
			properties.load(OTSUsersV18_1WsImpl.class.getClassLoader().getResourceAsStream("build-info.properties"));

	        String buildTimestamp = properties.getProperty("build.timestamp");
	        String buildVersion = properties.getProperty("build.version");
	        String buildBy = properties.getProperty("built.by");
	        
	        System.out.println("Build Timestamp: " + buildTimestamp);
	        System.out.println("Build Version: " + buildVersion);
	        System.out.println("Build By: " + buildBy);
	        System.out.println("JVM TimeZone: " + TimeZone.getDefault().getID());
	        
	        buildInfo.put("buildTimestamp", buildTimestamp);
	        buildInfo.put("buildVersion", buildVersion);
	        buildInfo.put("buildBy", buildBy);

			response = responseWrapper.buildResponse(buildInfo,"Successful");
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response addProviderHolidaysList(AddHolidayRequest addHolidayRequest) {
		Response response = null;
		try {
			String addHoliday = otsUserService.addHoliday(addHolidayRequest);
			if (addHoliday == null) {
				response = responseWrapper.buildResponse(404, "Not Inserted");
			} else {
				response = responseWrapper.buildResponse(200, addHoliday, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while Inserting data to DB  :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getHolidayListByProviderId(String providerId) {
		Response response = null;
		try {
			GetHolidayListResponse getHolidayListResponse = otsUserService.getHolidayListByProviderId(providerId);
			if (getHolidayListResponse == null) {
				response = responseWrapper.buildResponse(404, "Not Data Found");
			}else {
				response = responseWrapper.buildResponse(200, getHolidayListResponse, "Successful");
			}
			return response;
		} catch (Exception e) {
			logger.error("Exception while Inserting data to DB  :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
	}

	@Override
	public Response getProviderByCreatedUser(String createdUser) {
		Response response = null;
		try {
			UserDataBOResponse userDataBOResponse = otsUserService.getProviderByCreatedUser(createdUser);
			if (userDataBOResponse.getUserDetails().size() != 0) {
				response = responseWrapper.buildResponse(200, userDataBOResponse, "Successful");
			} else {
				response = responseWrapper.buildResponse(404, "No Data Found");
			}
		} catch (Exception e) {
			logger.error("Exception while Fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getActiveProviderList(){
		Response response = null;
		try {
			UserDataBOResponse userDataBOResponse=otsUserService.getActiveProviderList();
			 if(userDataBOResponse.getUserDetails().size() == 0) {
				 response = responseWrapper.buildResponse(404,"Data not found");
			 }else {
					response = responseWrapper.buildResponse(200,userDataBOResponse,"successful");
			 }
		}
		catch (Exception e) {
			logger.error("Exception while Fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addDistributorCountryMapping(AddDistributorCountryMappingRequest addDistributorCountryMappingRequest) {
	    Response response = null;
	    try {
	        // Validate distributorId
	        if (addDistributorCountryMappingRequest.getRequest().getDistributorId() == null  || addDistributorCountryMappingRequest.getRequest().getDistributorId().isEmpty()) {
	            return buildResponse(400, "Please Enter Required Inputs");
	        }

	        // Validate DistributorCountryCodeName list
	        if (addDistributorCountryMappingRequest.getRequest().getDistributorCountryCodeName().size() == 0) {
	            return buildResponse(400, "Please Enter Country Code");
	        }

	        // Validate each location in DistributorCountryCodeName
	        for (int i = 0; i < addDistributorCountryMappingRequest.getRequest().getDistributorCountryCodeName().size(); i++) {
	        	DistributorCountryCodeName distributorCountry = addDistributorCountryMappingRequest.getRequest().getDistributorCountryCodeName().get(i);

	            if (distributorCountry.getCountryCode() == null || distributorCountry.getCountryCode().isEmpty()) {
	                return buildResponse(400, "Please Enter Country Code");
	            }

	            if (distributorCountry.getCountryName() == null || distributorCountry.getCountryName().isEmpty()) {
	                return buildResponse(400, "Please Enter Country Name");
	            }
	        }
	        
	        //To Check if Countries already Mapped to Distributor
        	DistributorCountryResponse existingDistributorMappings = otsUserService.getCountriesMappedToDistributor(addDistributorCountryMappingRequest.getRequest().getDistributorId());
        	if(existingDistributorMappings.getDistributorCountry().size() != 0) {
        		return responseWrapper.buildResponse(400, "Locations Have Already Been Added For This Seller. Please Try To Update.");
        	}
	        
	        //To add Service availability for Distributor role or Product level
	        String addDistributorCountryMapping = otsUserService.addDistributorCountryMapping(addDistributorCountryMappingRequest);
	        if (addDistributorCountryMapping == null) {
	            response = responseWrapper.buildResponse(404, "Not Inserted");
	        } else {
	            response = responseWrapper.buildResponse(200,addDistributorCountryMapping, "Successful");
	        }

	    } catch (Exception e) {
	        logger.error("Exception while inserting data into DB: " + e.getMessage(), e);
	        response = buildResponse(500, "Something Went Wrong");
	    } catch (Throwable e) {
	        logger.error("Unexpected error occurred: " + e.getMessage(), e);
	        response = buildResponse(500, "Something Went Wrong");
	    }
	    return response;
	}
	
	@Override
	public Response clearAndAddDistributorCountryMapping(AddDistributorCountryMappingRequest addDistributorCountryMappingRequest) {
		Response response = null;
		try {
			// Validate distributorId
	        if (addDistributorCountryMappingRequest.getRequest().getDistributorId() == null  || addDistributorCountryMappingRequest.getRequest().getDistributorId().isEmpty()) {
	            return buildResponse(400, "Please Enter Required Inputs");
	        }

	        // Validate DistributorCountryCodeName list
	        if (addDistributorCountryMappingRequest.getRequest().getDistributorCountryCodeName().size() == 0) {
	            return buildResponse(400, "Please Enter Country Code");
	        }

	        // Validate each location in DistributorCountryCodeName
	        for (int i = 0; i < addDistributorCountryMappingRequest.getRequest().getDistributorCountryCodeName().size(); i++) {
	        	DistributorCountryCodeName distributorCountry = addDistributorCountryMappingRequest.getRequest().getDistributorCountryCodeName().get(i);

	            if (distributorCountry.getCountryCode() == null || distributorCountry.getCountryCode().isEmpty()) {
	                return buildResponse(400, "Please Enter Country Code");
	            }

	            if (distributorCountry.getCountryName() == null || distributorCountry.getCountryName().isEmpty()) {
	                return buildResponse(400, "Please Enter Country Name");
	            }
	        }
	        
			String distributorCountryMapping = otsUserService.clearAndAddDistributorCountryMapping(addDistributorCountryMappingRequest);
			if (distributorCountryMapping.equalsIgnoreCase("Updated Successfully")) {
				response = responseWrapper.buildResponse(200,distributorCountryMapping, "Successful");
			} else {
				response = responseWrapper.buildResponse(404, distributorCountryMapping);
			}

		} catch (Exception e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getServiceableCountriesByDistributor(String distributorId) {
		Response response =null;
		try {
			DistributorCountryResponse serviceableCountries = otsUserService.getCountriesMappedToDistributor(distributorId);
			if(serviceableCountries.getDistributorCountry().size() == 0) {
				response = responseWrapper.buildResponse(404,"No Serviceable Countries Has Been Added");
			}else
			{
				response = responseWrapper.buildResponse(200,serviceableCountries,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getAllCountry() {
		Response response =null;
		try {
			ServiceCountryResponse serviceCountry = otsUserService.getAllCountry();
			if(serviceCountry.getCountryDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,serviceCountry,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,serviceCountry,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getPageloaderDistributorsWithActiveProducts() {
		Response response =null;
		try {
			UserDetailsPageloaderResponse userDetailsResponse = otsUserService.getPageloaderDistributorsWithActiveProducts();
			if(userDetailsResponse.getUserDetails().isEmpty()) {
				response = responseWrapper.buildResponse(404,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,userDetailsResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getDistributorCompleteDetails(String distributorId)
	{
		Response response = null;
		try {
			GetDistributorCompleteDetails distributorDetails = otsUserService.getDistributorCompleteDetails(distributorId);
			if(distributorDetails == null) {
				response = responseWrapper.buildResponse(404, "Invalid Id");
			}else {
				response = responseWrapper.buildResponse(200,distributorDetails,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getUserDetailsByRoleAndStatus(GetUserStatusRequest getUserStatusRequest) {
		Response response = null;
		try {
			if (getUserStatusRequest.getRequest().getUserRoleId() == null
					|| getUserStatusRequest.getRequest().getUserRoleId().equals("")
					|| getUserStatusRequest.getRequest().getUserStatus() == null
					|| getUserStatusRequest.getRequest().getUserStatus().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			UserDataBOResponse userDataBOResponse = otsUserService.getUserDetailsByRoleAndStatus(getUserStatusRequest);
			if (userDataBOResponse.getUserDetails().size() == 0) {
				response = buildResponse(404, "No Data found");
			} else {
				response = buildResponse(200, userDataBOResponse, "Successful");
			}
			return response;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
	}
	
	@Override
	public Response forgotAdminPassword(ForgotAdminPasswordRequest forgotAdminPasswordRequest) {
		Response response = null;
		try {
			if(forgotAdminPasswordRequest.getRequest().getEmailId() == null || forgotAdminPasswordRequest.getRequest().getEmailId().trim().isEmpty()) {
				return response = buildResponse(400, "Please Enter Email ID");
			}
			ForgotPasswordResponse forgotPasswordResponse = otsUserService.sendOTPToAdmin(forgotAdminPasswordRequest.getRequest().getEmailId());

			// To mask response emailId
			String maskEmail = maskEmail(forgotAdminPasswordRequest.getRequest().getEmailId());
			if (forgotPasswordResponse == null) {
				response = buildResponse(404, "This Email ID Is Not Registered");
			} else {
				response = buildResponse(200, forgotPasswordResponse, "OTP Sent To Your Registered Email ID " + maskEmail);
			}
		} catch (Exception e) {
			logger.error("Exception while Fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}

}

