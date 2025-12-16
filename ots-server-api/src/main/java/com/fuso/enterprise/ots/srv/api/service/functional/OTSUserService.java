package com.fuso.enterprise.ots.srv.api.service.functional;

import java.util.List;
import java.util.Map;

import com.fuso.enterprise.ots.srv.api.model.domain.CustomerChangeAddress;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorBanner;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.GetDistributorCompleteDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.SubadminDetailsModel;
import com.fuso.enterprise.ots.srv.api.model.domain.SubadminValidity;
import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
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
import com.fuso.enterprise.ots.srv.api.service.request.DistributorActiveInactiveRequest;
import com.fuso.enterprise.ots.srv.api.service.request.DistributorAndProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ForgotPasswordRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetReviewRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetReviewsAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetUserStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.LoginAuthenticationBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.MapUsersDataBORequest;
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

public interface OTSUserService {
	
	UserDataBOResponse getUserIDUsers(String userId);

	UserDataBOResponse addNewUser(AddUserDataBORequest addUserDataBORequest);
	
	UserDataBOResponse updateUser(AddUserDataBORequest addUserDataBORequest);

	String mappUser(MapUsersDataBORequest mapUsersDataBORequest);

	UserDataBOResponse getUserDetails(RequestBOUserBySearch requestBOUserBySearch);
	
	LoginUserResponse otsLoginAuthentication(LoginAuthenticationBOrequest  loginAuthenticationBOrequest);
	
	UserDataBOResponse getDistributorByCreatedUser(String CreatedUser);

	UserDataBOResponse getUserDetailsByMapped(String mappedTo);
	
    ForgotPasswordResponse sendOTP(ForgotPasswordRequest forgotPasswordRequest);

	String changePassword(ChangePasswordRequest changePasswordRequest);

	String updatePassword(UpdatePasswordRequest updatePasswordRequest);
	
	String addWishList(AddWishListRequest addWishListRequest) ;
	
	List<GetwishListResponse> getWishList(String customerId);
	
	String removeFromWishList(AddWishListRequest addWishListRequest) ;
	
	String addToCart(AddToCartRequest addToCartRequest) ;
	
	GetCartResponse getCartList(String customerId);
	
	String removeFromCart(AddToCartRequest addToCartRequest) ;

	String emptyCart(AddToCartRequest addToCartRequest);
	
/* Shreekant Rathod 29-1-2021 */
	String addReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest);

	List<GetReviewAndRatingResponse> getReviewAndRating(GetReviewRatingRequest getReviewRatingRequest);

	UserDetails loginWithOtp(LoginAuthenticationBOrequest loginAuthenticationBOrequest);
	
	/* sachin */
	List<Map<String, Object>> getBannerinfo(RequestBOUserBySearch requestBOUserBySearch);
	
	List<Map<String, Object>> getAccountFooterDetails(RequestBOUserBySearch requestBOUserBySearch);
	/*Neha*/
	String addDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest);
	DistributerBOResponse getActiveDistributerList();
	List<Map<String, Object>> getAllBannerinfo();
	List<DistributorBanner> getDistributorBanner(String distributorId);

	String updateDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest);

	String deleteDistributorBanner(String bannerId);

	DistributorBanner getBannerDetailsByBannerId(String bannerId);

	UserDataBOResponse CheckForExists(AddUserDataBORequest addUserDataBORequest);		//G1
	DistributerResponse getDistributerDetails();

	String addCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest);

	List<CustomerChangeAddress> getCustomerChangeAddressByCustomerId(String customerId);

	String deleteCustomerChangeAddress(String customerChangeAddressId);
	List<DistributorCompanyDetails> getDistributorCompanyDetails(String distributorId);

	List<CustomerChangeAddress> getCustomerChangeAddressById(String customerChangeAddressId);
	String addDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest);
	String generateDistributorRegistrationInvoicePdf(String distributorId, String TransactionId);
	String updateDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest);

	String updateUserStatus(UpdateUserStatusRequest updateUserStatusRequest);

	String addDistributorRegistrationDetails(AddDistributorRegistrationDetailsRequest addDistributorRegistrationDetailsRequest);

	AverageReviewRatingResponse getAverageRatingOfProduct(String productId);

	String updateCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest);

	UserDataBOResponse getEmployeesDetailsByDistributor(RequestBOUserBySearch requestBOUserBySearch);

	List<GetReviewAndRatingResponse> getReviewAndRatingByOrderId(GetReviewsAndRatingRequest getReviewsAndRatingRequest);

	UserDataBOResponse getUserDetailsByContactNo(String contactNo);

	ServiceStateResponse getAllStates();

	ServiceDistrictResponse getDistrictByStateId(String stateId);

	ProductLocationResponse addProductLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest);

	String checkProductLocationAvailability(CheckProductAvailabilityRequest checkProductAvailabilityRequest);

	ServiceDistrictResponse getAllDistricts();

	StateDistrictResponse getAllStatesAndDistrictsList();

	DistributorPaymentDetailsResponse getDistributorPaymentDetails(String distributorId);

	String updateReviewStatus(UpdateReviewStatusRequest updateReviewStatusRequest);

	String updateReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest);

	ProductLocationResponse clearAndAddProductLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest);

	List<ProductDetails> getProductsByDistributorWithReviewAndRating(String distributorId);

	ServicePincodeResponse getPincodeByDistrict(String districtId);

	ServicePincodeResponse getPincodeDetails(String pincode);
	
	ServicePincodeResponse getPincodeByMultipleDistricts(List<String> districtId);

	ServiceDistrictResponse getDistrictsByMultipleStates(List<String> stateId);

	UserDataBOResponse getDistributorsWithActiveProductsByCountry(String countryCode);

	String updateUserContact(UpdateUserContactRequest UpdateUserContactRequest);

	String updateUserEmailId(UpdateUserEmailIdRequest updateUserEmailIdRequest);

	String checkEmailContactExistOrNot(AddUserDataBORequest addUserDataBORequest);

	String checkCartStockAvailability(String customerId);
	
	String checkEmailContactExistOrNotForUpdate(AddUserDataBORequest addUserDataBORequest);

	List<Map<String, Object>> checkDistributorRegistrationSteps(String distributorContactNo);

	UserAccountsResponse getSubAdminByStatus(String accountStatus);
	
	String addSubAdminValidity(AddUpdateSubadminValidity AddUpdateSubadminValidity);

	List<SubadminDetailsModel> getApprovedSubAdmins();

	String updateSubAdminValidity(AddUpdateSubadminValidity addUpdateSubadminValidity);

	SubadminValidity getSubAdminValidity(String subAdminId);

	ProductLocationResponse clearAndAddDistributorLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest);

	String distributorActiveInactive(DistributorActiveInactiveRequest distributorActiveInactiveRequest);

	GetServiceableLocationResponse getServiceableLocationByDistributorOnly(DistributorAndProductRequest distributorAndProductRequest);

	ProductLocationResponse getLocationsMappedToDistributorOnly(String distributorId);

	String addRegistrationTransactionCancelRecord(AddRegistrationTransactionCancelRecord addRegistrationTransactionCancelRecord);

	ProductLocationResponse getLocationsMappedToProduct(String productID);

	String addHoliday(AddHolidayRequest addHolidayRequest);
	
	GetHolidayListResponse getHolidayListByProviderId(String providerId);

	UserDataBOResponse getProviderByCreatedUser(String createdUser);

	UserDataBOResponse getActiveProviderList();

	String addDistributorCountryMapping(AddDistributorCountryMappingRequest addDistributorCountryMappingRequest);

	DistributorCountryResponse getCountriesMappedToDistributor(String distributorId);

	String clearAndAddDistributorCountryMapping(AddDistributorCountryMappingRequest addDistributorCountryMappingRequest);

	ServiceCountryResponse getAllCountry();

	UserDetailsPageloaderResponse getPageloaderDistributorsWithActiveProducts();

	GetDistributorCompleteDetails getDistributorCompleteDetails(String distributorId);

	UserDataBOResponse getUserDetailsByRoleAndStatus(GetUserStatusRequest getUserStatusRequest);
	
	ForgotPasswordResponse sendOTPToAdmin(String emailId);

	String addToCartRequestValidation(AddToCartRequest addToCartRequest);

	String deleteDistributor(String distributorId);

	List<UserDetails> sendIncompleteSellerRegistrationReminders();

	ServiceCountryResponse getCountriesWithActiveProducts();

}
