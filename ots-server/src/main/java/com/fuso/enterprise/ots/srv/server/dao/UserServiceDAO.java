package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;
import java.util.Map;

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

public interface UserServiceDAO {
	
	UserDetails getUserIdUsers(String userId);

	UserDataBOResponse addNewUser(AddUserDataBORequest addUserDataBORequest);
	
	LoginUserResponse otsLoginAuthentication(LoginAuthenticationBOrequest  loginAuthenticationBOrequest);
	
	UserDetails getUserDetails(String userId);

	UserDetails checkForOTP(String mobilenumber);

	String changePassword(ChangePasswordRequest changePasswordRequest);
	
	List<UserDetails> getDistributorByCreatedUser(String CreatedUser);

	UserDetails getUserDetailsForEmployee(String userId);

	UserDataBOResponse updateUser(AddUserDataBORequest addUserDataBORequest);

	String updatePassword(UpdatePasswordRequest updatePasswordRequest);

	List<UserDetails> CheckForExists(AddUserDataBORequest addUserDataBORequest);
	
	DistributerResponse getDistributerDetails();

	String addDistributorRegistrationInvoiceToDB(String distributorId, String invoice);

	List<UserDetails> updateUserStatus(UpdateUserStatusRequest updateUserStatusRequest);

	String addDistributorRegistrationDetails(AddDistributorRegistrationDetailsRequest addDistributorRegistrationDetailsRequest);

	List<UserDetails> getUserDetailsByContactNo(String contactNo);

	List<UserDetails> getDistributorsWithActiveProductsByCountry(String countryCode);

	String addHoliday(AddHolidayRequest addHolidayRequest);
	
	GetHolidayListResponse getHolidayListByProviderId(String providerId);
	
	List<UserDetails> getProviderByCreatedUser(String CreatedUser);

	List<UserDetails> getActiveProviderList();

	Map<String, List<UserDetails>> getPageloaderDistributorsWithActiveProducts();

	List<UserDetails> getUserDetails(RequestBOUserBySearch requestBOUserBySearch);

	List<UserDetails> getEmployeeDetailsByDistributor(RequestBOUserBySearch requestBOUserBySearch);

	List<UserDetails> getUserDetailsByMapped(String MappedTo);

	UserDetails getDistributorDetails(String distributorId);

	List<UserDetails> getUserDetailsByRoleAndStatus(GetUserStatusRequest getUserStatusRequest);

	String deleteDistributor(String distributorId);

	List<UserDetails> getIncompleteSellerRegistrations();

}
