package com.fuso.enterprise.ots.srv.rest.ws.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "OTSUsersV18_1Ws", description = "This service provides the operations for OTS users")
@Path("users")
@CrossOrigin
public interface OTSUsersV18_1Ws {
	
	@POST
    @Path("/get-userID-users")
	@ApiOperation(value = "get-userID-users", notes = "This operation will give the list of user based on userID", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getUserIDUsers(@ApiParam(value = "userId", required = true) @NotNull @Valid @QueryParam("userId") String userId);

	@POST
	@Path("/addNewUser")
	@ApiOperation(value = "addNewUser", notes = "This API is used to register the user according to role", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addNewUser(@ApiParam(value = "request", required = true) @NotNull @Valid AddUserDataBORequest addUserDataBORequest);
	
	@POST
	@Path("/updateUser")
	@ApiOperation(value = "updateUser", notes = "This API is to Update User Details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateUser(@ApiParam(value = "request", required = true) @NotNull @Valid AddUserDataBORequest addUserDataBORequest);
	
	@POST
	@Path("/mappUser")
	@ApiOperation(value = "mappUser", notes = "This operation map users(@ present we are not using this API)", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response mappUser(@ApiParam(value = "request", required = true) @NotNull @Valid MapUsersDataBORequest mapUsersDataBORequest);

	@POST
    @Path("/getUserDetails")
	@ApiOperation(value = "getUserDetails", notes = "Getting User Request Based On Name, Email and getUserDetails", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getUserDetails(@ApiParam(value = "request", required = true) @NotNull  @Valid RequestBOUserBySearch  requestBOUserBySearch);		

	@POST
    @Path("/otsLoginAuthentication")
	@ApiOperation(value = "getUserDetails", notes = "Api For Login for Users ,Using EmailId and Password", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response otsLoginAuthentication(@ApiParam(value = "request", required = true) @NotNull  @Valid LoginAuthenticationBOrequest  loginAuthenticationBOrequest);		

	@POST
    @Path("/getUserDetailsByMapped")
	@ApiOperation(value = "getUserDetailsByMapped", notes = "Getting All the details of mapped Users(@ present we are not using this API)", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getUserDetailsByMapped(@ApiParam(value = "request", required = false)  @Valid MappedToBORequest mappedToBORequest);
	
	@POST
	@Path("/forgotPassword")
	@ApiOperation(value = "forgotPassword", notes = "This Api is to send OTP to Distributor/Customer via email to change forgotten password", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response forgotPassword(@ApiParam(value = "request", required = true) @NotNull @Valid ForgotPasswordRequest forgotPasswordRequest);
	
	@POST
	@Path("/changePassword")
	@ApiOperation(value = "mappUser", notes = "This operation will change user password ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response changePassword(@ApiParam(value = "request", required = true) @NotNull @Valid ChangePasswordRequest changePasswordRequest);

	@POST
	@Path("/updatePassword")
	@ApiOperation(value = "updatePassword", notes = "This operation will update user password ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updatePassword(@ApiParam(value = "request", required = true) @NotNull @Valid UpdatePasswordRequest updatePasswordRequest);

	@POST
	@Path("/addWishList")
	@ApiOperation(value = "addWishList", notes = "This operation will add the wish list product of the customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addWishList(@ApiParam(value = "request", required = true) @NotNull @Valid AddWishListRequest addWishListRequest);

	@POST
	@Path("/getWishList")
	@ApiOperation(value = "getWishList", notes = "This operation will get the wish list product of the customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getWishList(@ApiParam(value = "request", required = true) @NotNull @Valid @QueryParam("customerId") String customerId);
	
	@POST
	@Path("/removeFromWishList")
	@ApiOperation(value = "removeFromWishList", notes = "This operation will get the cart list product of the customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response removeFromWishList(@ApiParam(value = "request", required = true) @NotNull @Valid AddWishListRequest addWishListRequest);

	@POST
	@Path("/addToCart")
	@ApiOperation(value = "addToCart", notes = "This operation will add the cart list product of the customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addToCart(@ApiParam(value = "request", required = true) @NotNull @Valid AddToCartRequest addToCartRequest);

	@POST
	@Path("/getCartList")
	@ApiOperation(value = "getCartList", notes = "This operation will get the cart list product of the customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCartList(@ApiParam(value = "customerId", required = true) @NotNull @Valid @QueryParam("customerId") String customerId);

	@POST
	@Path("/removeFromCartList")
	@ApiOperation(value = "removeFromCartList", notes = "This operation will get the cart list product of the customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response removeFromCartList(@ApiParam(value = "request", required = true) @NotNull @Valid AddToCartRequest addToCartRequest);

	@POST
	@Path("/emptyCartList")
	@ApiOperation(value = "removeFromCartList", notes = "This operation will empty the cart list ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response emptyCartList(@ApiParam(value = "request", required = true) @NotNull @Valid AddToCartRequest addToCartRequest);
	
	/*Shreekant Rathod 29-1-2021*/
	@POST
	@Path("/addReviewAndRating")
	@ApiOperation(value = "addReviewAndRating", notes = "This operation will add the review and rating of  product from the customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addReviewAndRating(@ApiParam(value = "request", required = true) @NotNull @Valid AddReviewAndRatingRequest addReviewAndRatingRequest);

	@POST
	@Path("/getReviewAndRating")
	@ApiOperation(value = "getReviewAndRating", notes = "This operation will get the review and rating list for product and customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getReviewAndRating(@ApiParam(value = "request", required = true) @NotNull @Valid GetReviewRatingRequest getReviewRatingRequest);
	
	@POST
	@Path("/getReviewAndRatingByOrderId")
	@ApiOperation(value = "getReviewAndRatingByOrderId", notes = "This operation will get the review and rating list for product and customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getReviewAndRatingByOrderId(@ApiParam(value = "request", required = true) @NotNull @Valid GetReviewsAndRatingRequest getReviewsAndRatingRequest);
	
	@POST
	@Path("/getProductsByDistributorWithReviewAndRating")
	@ApiOperation(value = "getProductsByDistributorWithReviewAndRating", notes = "This Api is to get Review And Rating for Products by Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductsByDistributorWithReviewAndRating(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@POST
    @Path("/getAverageRatingOfProduct")
	@ApiOperation(value = "getAverageRatingOfProduct", notes = "This Api is to get Average Rating of Product", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAverageRatingOfProduct(@ApiParam(value = "productId", required = true) @NotNull @Valid @QueryParam("productId") String productId);
	
	@POST
	@Path("/updateReviewAndRating")
	@ApiOperation(value = "updateReviewAndRating", notes = "This Api is to update existing Review & Ratings for ordered Product", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateReviewAndRating(@ApiParam(value = "request", required = true) @NotNull @Valid AddReviewAndRatingRequest addReviewAndRatingRequest);
	
	@POST
	@Path("/updateReviewStatus")
	@ApiOperation(value = "updateReviewStatus", notes = "This Api is to update status of Review & Rating", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateReviewStatus(@ApiParam(value = "request", required = true) @NotNull @Valid UpdateReviewStatusRequest updateReviewStatusRequest);

	@POST
	@Path("/loginWithOtp")
	@ApiOperation(value = "loginWithOtp", notes = "This API is used to get the details of the user by phone number to check whether the user present in DB or not by using the phone number", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response loginWithOtp(@ApiParam(value = "request", required = true) @NotNull @Valid LoginAuthenticationBOrequest loginAuthenticationBOrequest);

	// bannerDetails API
	@POST
    @Path("/getAllBannerinfo")
	@ApiOperation(value = "getAllBannerinfo", notes = "Getting all banner details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllBannerinfo();
	
	@POST
    @Path("/getBannerinfo")
	@ApiOperation(value = "getBannerinfo", notes = "Getting User Request Based On Name, Email and getUserDetails", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getBannerinfo(@ApiParam(value = "request", required = true) @NotNull  @Valid RequestBOUserBySearch  requestBOUserBySearch);		

	@POST
    @Path("/getAccountFooterDetails")
	@ApiOperation(value = "accountfooter", notes = "Getting User Request Based On Name, Email and getUserDetails", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAccountFooterDetails(@ApiParam(value = "request", required = true) @NotNull  @Valid RequestBOUserBySearch  requestBOUserBySearch);
	
	@POST
	@Path("/getActiveDistributerList")
	@ApiOperation(value = "getActiveDistributerList", notes = "This API is used to get active distributer List", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getActiveDistributerList();
	
	@POST
	@Path("/getDistributorByCreatedUser")
	@ApiOperation(value = "getDistributorByCreatedUser", notes = "This Api will give the list of Distributor for the CreatedUser", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributorByCreatedUser(@ApiParam(value = "CreatedUser", required = true) @NotNull @Valid @QueryParam("CreatedUser") String CreatedUser);
	
	@POST
	@Path("/getDistributerDetails")
	@ApiOperation(value = "getDistributerDetails", notes = "This API is used to get distributer details like image,id and name", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributerDetails();

	@POST
	@Path("/addDistributorBanner")
	@ApiOperation(value = "addDistributorBanner", notes = "This Api is used to add Banners by Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addDistributorBanner(@ApiParam(value = "request", required = true) @NotNull @Valid AddDistributorBannerRequest addDistributorBannerRequest);
	
	@POST
    @Path("/getDistributorBanner")
	@ApiOperation(value = "getDistributorBanner", notes = "This Api is to get Banner details for distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributorBanner(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@POST
	@Path("/updateDistributorBanner")
	@ApiOperation(value = "updateDistributorBanner", notes = "This Api is used to update Banners by Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateDistributorBanner(@ApiParam(value = "request", required = true) @NotNull @Valid AddDistributorBannerRequest addDistributorBannerRequest);
	
	@POST
	@Path("/deleteDistributorBanner")
	@ApiOperation(value = "deleteDistributorBanner", notes = "This operation is used to delete user", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response deleteDistributorBanner(@ApiParam(value = "request", required = true) @NotNull @Valid DeleteDistributorBannerRequest deleteDistributorBannerRequest);
	
	@POST
	@Path("/addCustomerChangeAddress")
	@ApiOperation(value = "addCustomerChangeAddress", notes = "This Api is used to add Secondary Address for Customer in time of placing orders", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addCustomerChangeAddress(@ApiParam(value = "request", required = true) @NotNull @Valid AddCustomerChangeAddressRequest addCustomerChangeAddressRequest);
	
	@POST
	@Path("/updateCustomerChangeAddress")
	@ApiOperation(value = "updateCustomerChangeAddress", notes = "This Api is used to Update existing Secondary Address for Customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateCustomerChangeAddress(@ApiParam(value = "request", required = true) @NotNull @Valid AddCustomerChangeAddressRequest addCustomerChangeAddressRequest);
	
	@POST
    @Path("/getCustomerChangeAddressByCustomerId")
	@ApiOperation(value = "getCustomerChangeAddressByCustomerId", notes = "This Api is to get List of Customers Secondary Address", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCustomerChangeAddressByCustomerId(@ApiParam(value = "request", required = true) @NotNull @Valid @QueryParam("customerId") String customerId);
	
	@POST
    @Path("/getCustomerChangeAddressById")
	@ApiOperation(value = "getCustomerChangeAddressById", notes = "This Api is to get Customers Secondary Address by customerChangeAddressId", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCustomerChangeAddressById(@ApiParam(value = "request", required = true) @NotNull @Valid @QueryParam("customerChangeAddressId") String customerChangeAddressId);
	
	@POST
	@Path("/deleteCustomerChangeAddress")
	@ApiOperation(value = "deleteCustomerChangeAddress", notes = "This Api is used to delete Customer Change Address Details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response deleteCustomerChangeAddress(@ApiParam(value = "request", required = true) @NotNull @Valid @QueryParam("customerChangeAddressId") String customerChangeAddressId);
	
	@POST
	@Path("/addDistributorCompanyDetails")
	@ApiOperation(value = "addDistributorCompanyDetails", notes = "This Api is used to Add Company Deatils of Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addDistributorCompanyDetails(@ApiParam(value = "request", required = true) @NotNull @Valid AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest);
	
	@POST
	@Path("/updateDistributorCompanyDetails")
	@ApiOperation(value = "updateDistributorCompanyDetails", notes = "This Api is used to Update Company Deatils of Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateDistributorCompanyDetails(@ApiParam(value = "request", required = true) @NotNull @Valid AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest);
	
	@POST
    @Path("/getDistributorCompanyDetails")
	@ApiOperation(value = "getDistributorCompanyDetails", notes = "This Api is to get Distributors Company Details for DistributorId", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributorCompanyDetails(@ApiParam(value = "request", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@POST
    @Path("/generateDistributorRegistrationInvoicePdf")
	@ApiOperation(value = "generateDistributorRegistrationInvoicePdf", notes = "This Api is to Generate Registration Invoice for Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response generateDistributorRegistrationInvoicePdf(@ApiParam(value = "request", required = true) @NotNull @Valid GenerateDistributorRegistrationInvoiceRequest generateDistributorRegistrationInvoiceRequest);

	@POST
	@Path("/updateUserStatus")
	@ApiOperation(value = "updateUserStatus", notes = "This Api is used to Update UserId Status of Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateUserStatus(@ApiParam(value = "request", required = true) @NotNull @Valid UpdateUserStatusRequest updateUserStatusRequest);
	
	@POST
	@Path("/addDistributorRegistrationDetails")
	@ApiOperation(value = "addDistributorRegistrationDetails", notes = "This Api is used to Add Registration Details of Distributors and to Generate Registration Invoice", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addDistributorRegistrationDetails(@ApiParam(value = "request", required = true) @NotNull @Valid AddDistributorRegistrationDetailsRequest addDistributorRegistrationDetailsRequest);
	
	@POST
    @Path("/getEmployeesDetailsByDistributor")
	@ApiOperation(value = "getEmployeesDetailsByDistributor", notes = "To get Employee details under distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getEmployeesDetailsByDistributor(@ApiParam(value = "request", required = true) @NotNull  @Valid RequestBOUserBySearch  requestBOUserBySearch);	
	
	@POST
    @Path("/getUserDetailsByContactNo")
	@ApiOperation(value = "getUserDetailsByContactNo", notes = "This Api is to get User Details by Registered Contact Number", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getUserDetailsByContactNo(@ApiParam(value = "request", required = true) @NotNull  @Valid GetUserDetailsByContactNoRequest contactNoRequest);	
	
	@POST
	@Path("/getAllStates")
	@ApiOperation(value = "getAllStates", notes = "This API is used to get list of All States", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllStates();

	@POST
    @Path("/getDistrictByStateId")
	@ApiOperation(value = "getDistrictByStateId", notes = "This Api is to get list of Districts by State Id", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistrictByStateId(@ApiParam(value = "stateId", required = true) @NotNull @Valid @QueryParam("stateId") String stateId);
	
	@POST
    @Path("/addProductLocationMapping")
	@ApiOperation(value = "addProductLocationMapping", notes = "To map product service availability with location", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addProductLocationMapping(@ApiParam(value = "request", required = true) @NotNull  @Valid AddProductLocationMappingRequest addProductLocationMappingRequest);

	@POST
    @Path("/checkProductLocationAvailability")
	@ApiOperation(value = "checkProductLocationAvailability", notes = "This Api is to check whether the Product is available in selected Location", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response checkProductLocationAvailability(@ApiParam(value = "request", required = true) @NotNull  @Valid CheckProductAvailabilityRequest checkProductAvailabilityRequest);
	
	@POST
    @Path("/clearAndAddProductLocationMapping")
	@ApiOperation(value = "clearAndAddProductLocationMapping", notes = "This Api is to delete all the locations mapped to product & later add product service availability with location", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response clearAndAddProductLocationMapping(@ApiParam(value = "request", required = true) @NotNull  @Valid AddProductLocationMappingRequest addProductLocationMappingRequest);
	
	@POST
	@Path("/getAllDistricts")
	@ApiOperation(value = "getAllDistricts", notes = "This API is used to get list of All Districts", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllDistricts();
	
	@POST
	@Path("/getAllStatesAndDistrictsList")
	@ApiOperation(value = "getAllStatesAndDistrictsList", notes = "This API is used to get All the States and its Districts list", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllStatesAndDistrictsList();
	
	@POST
    @Path("/getDistributorPaymentDetails")
	@ApiOperation(value = "getDistributorPaymentDetails", notes = "This Api is to get Payment Details of Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributorPaymentDetails(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@POST
    @Path("/getPincodeByDistrict")
	@ApiOperation(value = "getPincodeByDistrict", notes = "This Api is to get list of pincodes by District Id", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getPincodeByDistrict(@ApiParam(value = "districtId", required = true) @NotNull @Valid @QueryParam("districtId") String districtId);
	
	@POST
    @Path("/getPincodeDetails")
	@ApiOperation(value = "getPincodeDetails", notes = "This Api is to get list of pincodes by particular pincode", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getPincodeDetails(@ApiParam(value = "pincode", required = true) @NotNull @Valid @QueryParam("pincode") String pincode);

	@POST
    @Path("/getPincodeByMultipleDistricts")
	@ApiOperation(value = "getPincodeByMultipleDistricts", notes = "This Api is to get list of pincodes by multiple District", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getPincodeByMultipleDistricts(@ApiParam(value = "request", required = true) @NotNull @Valid GetPincodeByMultipleDistricts getPincodeByMultipleDistricts);
	
	@POST
    @Path("/getDistrictsByMultipleStates")
	@ApiOperation(value = "getDistrictsByMultipleStates", notes = "This Api is to get list of Districts by multiple States", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistrictsByMultipleStates(@ApiParam(value = "request", required = true) @NotNull @Valid GetDistrictsByMultipleStates getDistrictsByMultipleStates);

	@POST
    @Path("/getDistributorsWithActiveProductsByCountry")
	@ApiOperation(value = "getDistributorsWithActiveProductsByCountry", notes = "This Api is to get list of Distributors having Active Products", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributorsWithActiveProductsByCountry(@ApiParam(value = "countryCode", required = true) @NotNull @Valid @QueryParam("countryCode") String countryCode);
	
	@POST
    @Path("/updateUserContact")
	@ApiOperation(value = "updateUserContact", notes = "This Api is to Update Users Contact Number", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateUserContact(@ApiParam(value = "request", required = true) @NotNull @Valid UpdateUserContactRequest UpdateUserContactRequest);
	
	@POST
    @Path("/updateUserEmailId")
	@ApiOperation(value = "updateUserEmailId", notes = "This Api is to get Update Users EmailId", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateUserEmailId(@ApiParam(value = "request", required = true) @NotNull @Valid UpdateUserEmailIdRequest updateUserEmailIdRequest);
	
	@POST
    @Path("/checkCartStockAvailability")
	@ApiOperation(value = "checkCartStockAvailability", notes = "This Api is to Check Stock Availability of All Products in Customer Cart", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response checkCartStockAvailability(@ApiParam(value = "customerId", required = true) @NotNull @Valid @QueryParam("customerId") String customerId);

	@POST
    @Path("/getServiceableLocationByDistributorOnly")
	@ApiOperation(value = "getServiceableLocationByDistributorOnly", notes = "This Api is to get list of Locations mapped to distributor only without product", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getServiceableLocationByDistributorOnly(@ApiParam(value = "request", required = true) @NotNull @Valid DistributorAndProductRequest distributorAndProductRequest);
	
	@POST
    @Path("/checkDistributorRegistrationSteps")
	@ApiOperation(value = "checkDistributorRegistrationSteps", notes = "This Api is to Check Registration Steps completed by distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response checkDistributorRegistrationSteps(@ApiParam(value = "distributorContactNo", required = true) @NotNull @Valid @QueryParam("distributorContactNo") String distributorContactNo);
	
	@POST
    @Path("/getSubAdminByStatus")
	@ApiOperation(value = "getSubAdminByStatus", notes = "This Api is to get Sub Admin details by account type", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getSubAdminByStatus(@ApiParam(value = "accountStatus", required = true) @NotNull @Valid @QueryParam("accountStatus") String accountStatus);

	@POST
    @Path("/addSubAdminValidity")
	@ApiOperation(value = "addSubAdminValidity", notes = "This Api is to Add Sub Admin details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addSubAdminValidity(@ApiParam(value = "request", required = true) @NotNull @Valid AddUpdateSubadminValidity addUpdateSubadminValidity);
	
	@GET
    @Path("/getApprovedSubAdmins")
	@ApiOperation(value = "getApprovedSubAdmins", notes = "This Api is to get list of Approved Sub Admin's with Validity details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getApprovedSubAdmins();

	@POST
    @Path("/updateSubAdminValidity")
	@ApiOperation(value = "updateSubAdminValidity", notes = "This Api is to Update Validity details of Sub Admin", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateSubAdminValidity(@ApiParam(value = "request", required = true) @NotNull @Valid AddUpdateSubadminValidity addUpdateSubadminValidity);
	
	@POST
	@Path("/distributorActiveInactive")
	@ApiOperation(value = "distributorActiveInactive", notes = "This Api is to Update distributor status to Inactive or Active, all his products & "
			+ "employees will be Updated to Inactive or Active based on status and remove inactive products from cart and wishlist", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response distributorActiveInactive(@ApiParam(value = "request", required = true) @NotNull @Valid DistributorActiveInactiveRequest distributorActiveInactiveRequest);
	
	@POST
    @Path("/clearAndAddDistributorLocationMapping")
	@ApiOperation(value = "clearAndAddDistributorLocationMapping", notes = "This Api is to delete all the locations mapped to Distributor & "
			+ "later add Distributor service availability with location", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response clearAndAddDistributorLocationMapping(@ApiParam(value = "request", required = true) @NotNull @Valid AddProductLocationMappingRequest addProductLocationMappingRequest);
	
	@POST
    @Path("/addRegistrationTransactionCancelRecord")
	@ApiOperation(value = "addRegistrationTransactionCancelRecord", notes = "This Api is to Add Distributor Cancelled Registration Transaction Deatils", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addRegistrationTransactionCancelRecord(AddRegistrationTransactionCancelRecord addRegistrationTransactionCancelRecord);
	
	@GET
	@Path("/buildInfo")
	@ApiOperation(value = "buildInfo", notes = "This Api is to get Build info", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response buildInfo();

	@POST
    @Path("/addProviderHolidaysList")
	@ApiOperation(value = "addHoliday", notes = "This Api is to Add Holidays Details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addProviderHolidaysList(@ApiParam(value = "request", required = true) @NotNull @Valid AddHolidayRequest addHolidayRequest);

	@POST
	@Path("/getHolidayListByProviderId")
	@ApiOperation(value = "getHolidayListByProviderId", notes = "This Api is to Get Holiydays Deatils By Provider ID", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getHolidayListByProviderId(@ApiParam(value = "providerId", required = true) @NotNull @Valid @QueryParam("providerId")String providerId);	
	
	@POST
	@Path("/getProviderByCreatedUser")
	@ApiOperation(value = "getDistributorByCreatedUser", notes = "This Api will give the list of Provider for the CreatedUser", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProviderByCreatedUser(@ApiParam(value = "createdUser", required = true) @NotNull @Valid @QueryParam("createdUser") String createdUser);

	@GET
	@Path("/getActiveProviderList")
	@ApiOperation(value = "getActiveProviderList", notes = "This Api is used to get active provider List", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getActiveProviderList();
	
	@POST
    @Path("/addDistributorCountryMapping")
	@ApiOperation(value = "addDistributorCountryMapping", notes = "This Api is to Add Distributor Service Availability for Countries", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addDistributorCountryMapping(@ApiParam(value = "request", required = true) @NotNull  @Valid AddDistributorCountryMappingRequest addDistributorCountryMappingRequest);
	
	@POST
    @Path("/clearAndAddDistributorCountryMapping")
	@ApiOperation(value = "clearAndAddDistributorCountryMapping", notes = "This Api is to delete all the Countries mapped to Distributor & "
			+ "later add New Countries", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response clearAndAddDistributorCountryMapping(@ApiParam(value = "request", required = true) @NotNull @Valid AddDistributorCountryMappingRequest addDistributorCountryMappingRequest);
	
	@POST
    @Path("/getServiceableCountriesByDistributor")
	@ApiOperation(value = "getServiceableCountriesByDistributor", notes = "This Api is to get list of Locations mapped to distributor only without product", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getServiceableCountriesByDistributor(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@GET 
	@Path("/getAllCountry")
	@ApiOperation(value = "getAllCountry", notes = "This API is to get list of All Country", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllCountry();
	
	@GET
    @Path("/getPageloaderDistributorsWithActiveProducts")
	@ApiOperation(value = "getPageloaderDistributorsWithActiveProducts", notes = "This Api is to get list of Distributors having Active Products grouped by country code", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getPageloaderDistributorsWithActiveProducts();
	
	@POST
    @Path("/getDistributorCompleteDetails")
	@ApiOperation(value = "getDistributorCompleteDetails", notes = "This Api is to fetch Distributor's complete details including Personal Details, Company Details, Payment Details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributorCompleteDetails(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);

	@POST
    @Path("/getUserDetailsByRoleAndStatus")
	@ApiOperation(value = "getUserDetailsByRoleAndStatus", notes = "This Api is to fetch User Details by RoleId & Status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getUserDetailsByRoleAndStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid GetUserStatusRequest getUserStatusRequest);

	@POST
	@Path("/forgotAdminPassword")
	@ApiOperation(value = "mappUser", notes = "This Api is to send OTP to Admin via email to change forgotten password", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response forgotAdminPassword(@ApiParam(value = "request", required = true) @NotNull  @Valid ForgotAdminPasswordRequest forgotAdminPasswordRequest);
	
	@POST
    @Path("/deleteDistributor")
	@ApiOperation(value = "deleteDistributor", notes = "This Api is to Delete distributor details from the main table and inserts the data into deleted table.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response deleteDistributor(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@GET
    @Path("/sendIncompleteSellerRegistrationReminders")
	@ApiOperation(value = "sendIncompleteSellerRegistrationReminders", notes = "This Api is to send mail notification to incomplete registered Sellers", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response sendIncompleteSellerRegistrationReminders();
	
	@GET 
	@Path("/getCountriesWithActiveProducts")
	@ApiOperation(value = "getCountriesWithActiveProducts", notes = "This API is to get list of Countries with active products", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCountriesWithActiveProducts();
	
}
