 package com.fuso.enterprise.ots.srv.functional.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuso.enterprise.ots.srv.api.model.domain.AddCustomerChangeAddressModel;
import com.fuso.enterprise.ots.srv.api.model.domain.AverageReviewRatingModel;
import com.fuso.enterprise.ots.srv.api.model.domain.CustomerChangeAddress;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorBanner;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCountryMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorPaymentDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.GetDistributorCompleteDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.GetServiceableLocation;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductLocationMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceCountry;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDistrict;
import com.fuso.enterprise.ots.srv.api.model.domain.ServicePincode;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceState;
import com.fuso.enterprise.ots.srv.api.model.domain.StateDistrictModel;
import com.fuso.enterprise.ots.srv.api.model.domain.SubadminDetailsModel;
import com.fuso.enterprise.ots.srv.api.model.domain.SubadminValidity;
import com.fuso.enterprise.ots.srv.api.model.domain.UserAccounts;
import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.UserMapping;
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
import com.fuso.enterprise.ots.srv.api.service.response.GetProductBOStockResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetReviewAndRatingResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceableLocationResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetcartListResponse;
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
import com.fuso.enterprise.ots.srv.server.dao.CartDAO;
import com.fuso.enterprise.ots.srv.server.dao.CustomerChangeAddressDAO;
import com.fuso.enterprise.ots.srv.server.dao.DistributorBannerDAO;
import com.fuso.enterprise.ots.srv.server.dao.DistributorCompanyDetailsDAO;
import com.fuso.enterprise.ots.srv.server.dao.DistributorCountryMappingDAO;
import com.fuso.enterprise.ots.srv.server.dao.DistributorPaymentDetailsDAO;
import com.fuso.enterprise.ots.srv.server.dao.OrderProductDAO;
import com.fuso.enterprise.ots.srv.server.dao.OrderServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.OtsProductWishlistDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductLocationMappingDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductStockDao;
import com.fuso.enterprise.ots.srv.server.dao.RegistrationTransactionCancelRecordsDAO;
import com.fuso.enterprise.ots.srv.server.dao.ReviewAndRatingDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServiceCountryDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServiceDistrictDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServicePincodeDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServiceStateDAO;
import com.fuso.enterprise.ots.srv.server.dao.SubadminValidityDAO;
import com.fuso.enterprise.ots.srv.server.dao.UserMapDAO;
import com.fuso.enterprise.ots.srv.server.dao.UserServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.useraccountsDAO;
import com.fuso.enterprise.ots.srv.server.util.DistributorRegisterInvoicePdf;
import com.fuso.enterprise.ots.srv.server.util.EmailUtil;
import com.fuso.enterprise.ots.srv.server.util.FcmPushNotification;

@Service
@Transactional
public class OTSUserServiceImpl implements  OTSUserService{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Value("${product.percentage.price}")
	public String productPercentage;
	
	@Value("${ots.company.name}")
	public String companyName;
	
	@Value("${ots.distributor.page.loginLink}")
	public String distributorPageLoginLink;
	
	@Value("${ots.customer.page.loginLink}")
	public String customerPageLoginLink;

	private Logger logger = LoggerFactory.getLogger(getClass());
	private UserServiceDAO userServiceDAO;
	private UserMapDAO userMapDAO;
	private FcmPushNotification fcmPushNotification;
	private ProductServiceDAO productServiceDAO;
	private OtsProductWishlistDAO otsProductWishlistDAO;
	private CartDAO cartDAO;
	private ReviewAndRatingDAO reviewAndRatingDAO;
	private DistributorBannerDAO distributorBannerDAO;
	private OrderServiceDAO orderServiceDAO;
	private CustomerChangeAddressDAO customerChangeAddressDAO;
	private useraccountsDAO useraccountsDAO;
	private DistributorCompanyDetailsDAO distributorCompanyDetailsDAO;
	private ServiceStateDAO serviceStateDAO;
	private ServiceDistrictDAO serviceDistrictDAO;
	private DistributorPaymentDetailsDAO distributorPaymentDetailsDAO;
	private ServicePincodeDAO servicePincodeDAO;
	private ProductStockDao productStockDao;
	private SubadminValidityDAO subadminValidityDAO;
	private RegistrationTransactionCancelRecordsDAO registrationTransactionCancelRecordsDAO;
	private DistributorCountryMappingDAO distributorCountryMappingDAO;
	private ServiceCountryDAO serviceCountryDAO;
	
	@Autowired
	private ProductLocationMappingDAO productLocationMappingDAO;
	
	@Autowired
    private EmailUtil emailUtil;
	
	@Inject
	public OTSUserServiceImpl(DistributorCompanyDetailsDAO distributorCompanyDetailsDAO,DistributorBannerDAO distributorBannerDAO,UserServiceDAO userServiceDAO,UserMapDAO userMapDAO,
			ProductServiceDAO productServiceDAO,OtsProductWishlistDAO otsProductWishlistDAO,CartDAO cartDAO,ReviewAndRatingDAO reviewAndRatingDAO,
			OrderServiceDAO orderServiceDAO,CustomerChangeAddressDAO customerChangeAddressDAO,useraccountsDAO useraccountsDAO,ServiceStateDAO serviceStateDAO,ServiceDistrictDAO serviceDistrictDAO,
			ProductLocationMappingDAO productLocationMappingDAO,DistributorPaymentDetailsDAO distributorPaymentDetailsDAO,OrderProductDAO orderProductDAO,ServicePincodeDAO servicePincodeDAO,
			ProductStockDao productStockDao,SubadminValidityDAO subadminValidityDAO, RegistrationTransactionCancelRecordsDAO registrationTransactionCancelRecordsDAO,
			DistributorCountryMappingDAO distributorCountryMappingDAO,ServiceCountryDAO serviceCountryDAO) {
		this.userServiceDAO=userServiceDAO;
		this.userMapDAO=userMapDAO;
		this.distributorBannerDAO=distributorBannerDAO;
		this.productServiceDAO = productServiceDAO;
		this.otsProductWishlistDAO = otsProductWishlistDAO;
		this.cartDAO=cartDAO;
		this.reviewAndRatingDAO=reviewAndRatingDAO;
		this.orderServiceDAO = orderServiceDAO ;
		this.customerChangeAddressDAO=customerChangeAddressDAO;
		this.useraccountsDAO = useraccountsDAO;
		this.distributorCompanyDetailsDAO=distributorCompanyDetailsDAO;
		this.serviceStateDAO = serviceStateDAO;
		this.serviceDistrictDAO = serviceDistrictDAO;
		this.productLocationMappingDAO = productLocationMappingDAO;
		this.distributorPaymentDetailsDAO = distributorPaymentDetailsDAO;
		this.servicePincodeDAO = servicePincodeDAO;
		this.productStockDao = productStockDao;
		this.subadminValidityDAO = subadminValidityDAO;
		this.registrationTransactionCancelRecordsDAO = registrationTransactionCancelRecordsDAO; 
		this.distributorCountryMappingDAO = distributorCountryMappingDAO;
		this.serviceCountryDAO = serviceCountryDAO;
	}

	@Override
	public UserDataBOResponse getUserIDUsers(String userId) {
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			UserDetails userDetailList = userServiceDAO.getUserIdUsers(userId);
			List<UserDetails> userList = new ArrayList<>();
			userList.add(userDetailList);
			userDataBOResponse.setUserDetails(userList);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}
	
	@Override
	public UserDataBOResponse CheckForExists(AddUserDataBORequest addUserDataBORequest) {
    	UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
        try {
        	List<UserDetails> userDetailList = userServiceDAO.CheckForExists(addUserDataBORequest);
        	userDataBOResponse.setUserDetails(userDetailList);
			logger.error("Inside Event=1004,Class:OTSUserServiceImpl,Method:CheckForExists ");
        } catch (Exception e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        	e.printStackTrace();
        	throw new BusinessException(e.getMessage(), e);
        }
    	return userDataBOResponse;
	}
	
	@Override
	public UserDataBOResponse addNewUser(AddUserDataBORequest addUserDataBORequest) {
	    System.out.println("IN add");
	    UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
	    try {    
	        // Adding new user and returning the user object
	        System.out.println("inside addNewUser -> otsUserServiceImpl");
	        userDataBOResponse = userServiceDAO.addNewUser(addUserDataBORequest);
	        System.out.println("added New User");
	        
	        if (!userDataBOResponse.getUserDetails().isEmpty()) {
	        	//The ExecutorService is used to send emails asynchronously, reducing the main thread's wait time.
	        	//Increase ThreadPool size to increase execution time if load increases
	            ExecutorService executor = Executors.newFixedThreadPool(3);
	            
	            // Send different emails based on user role
	            String userRoleId = addUserDataBORequest.getRequestData().getUserRoleId();
	            String userStatus = addUserDataBORequest.getRequestData().getUsrStatus();
	            String emailId = addUserDataBORequest.getRequestData().getEmailId();
	            String firstName = addUserDataBORequest.getRequestData().getFirstName();
	            String lastName = addUserDataBORequest.getRequestData().getLastName();
	            String createdUser = addUserDataBORequest.getRequestData().getCreatedUser();
	            String distributorId = addUserDataBORequest.getRequestData().getDistributorId();
	            String userContactNo = addUserDataBORequest.getRequestData().getContactNo();
	            String userPassword = addUserDataBORequest.getRequestData().getUsrPassword();
	            String userId = userDataBOResponse.getUserDetails().get(0).getUserId();

	            //To send mail for Distributor & Admin, when Distributor Registers & in status Pending
	            if ("2".equalsIgnoreCase(userRoleId) && "pending".equalsIgnoreCase(userStatus)) {
	            	 String distMsg = "<p>Hi,<br><br>" + 
	                         "Greetings, you have successfully registered as Seller. Please wait for Admin to approve your credentials.<br>" + 
	                         "Once approved you may use the same to login.<br><br>" +
	                         "Thanks And Regards,<br>" + 
	                         companyName+" Support Team </p>";
	            	
	            	 UserAccounts adminDetails = useraccountsDAO.getUseraccountDetail(createdUser);
	            	 String adminMsg = "<p>Hi,<br><br>" + 
	                          "A new Seller has Registered and waiting for your approval.<br>" +
	                          "Please Approve Seller, with Name: "+firstName+" "+lastName+" And Contact No: "+userContactNo+"<br><br>" +
	                          "Thanks And Regards,<br>" + 
	                          companyName+" Support Team </p>";
	            	 
	            	 executor.submit(() -> emailUtil.sendDistributermail(emailId, "", "Regarding Seller Registration", distMsg));
	            	 executor.submit(() -> emailUtil.sendAdminMail(adminDetails.getEmail(), "", "Regarding Seller Registration", adminMsg));
	            	 
	            //To send mail for Distributor, when Admin Registers the Distributor & in status active
	            } else if ("2".equalsIgnoreCase(userRoleId) && "active".equalsIgnoreCase(userStatus)) {
	            	//Link for distributor login page
					String distPageLink = "<a href= '"+distributorPageLoginLink+"'>Login</a> </p>";
            	
 	            	String distMsg = "<p>Hi,<br><br>" + 
 	                         "Greetings, you have successfully registered as Seller. Please use the below credentials to Login.<br><br>" + 
 	            			 "<b>Credentials:<b><br>"+
 	                         "Registered Phone Number = "+userContactNo+"<br>" +
 	                         "Password = "+userPassword+"<br>" +
 	                         "You can log in here  "+distPageLink+"<br>" +
 	                         "For security reasons, we recommend changing your password after your first login.<br>"+
 	                         "If you face any issues, feel free to contact our support team.<br><br>"+
 	                         "Thanks And Regards,<br>" + 
 	                         companyName+" Support Team </p>";

 	            	 executor.submit(() -> emailUtil.sendDistributermail(emailId, "", "Regarding Seller Registration", distMsg));
	            	
	            //To send mail for Employee & Distributor Regarding Employee registration
	            } else if ("3".equalsIgnoreCase(userRoleId)) {
	            	UserDetails distributor = userServiceDAO.getUserDetails(distributorId);
	            	String empMsg = "Hi,\r\n\r\n" + 
	                        "Greetings, you have been successfully registered as Employee by Seller " + distributor.getFirstName() + " " + distributor.getLastName() + ".\r\n\r\n" + 
	                        "Your Employee Id is " + userId + "\r\n\r\n" +
	                        "Thanks And Regards,\r\n" + 
	                        companyName+" Support Team </p>";
	            	
	            	String distMsg = "<p>Hi,<br><br>" + 
	                         "You have registered " + firstName + " " + lastName + " as new Employee.<br>" +
	                         "Employee Id is " + userId + "<br><br>" +
	                         "Thanks And Regards,<br>" + 
	                         companyName+" Support Team </p>";
	            	
	                executor.submit(() -> emailUtil.sendEmployeemail(emailId, "", "Regarding Employee Registration", empMsg));
	                executor.submit(() -> emailUtil.sendDistributermail(distributor.getEmailId(), "", "Regarding Employee Registration", distMsg));
	               
	             //To send mail for Customer Regarding New Registration
	            } else if ("4".equalsIgnoreCase(userRoleId) && "pending".equalsIgnoreCase(userStatus)) {
	            	AddCustomerChangeAddressRequest addCustomerChangeAddressRequest = new AddCustomerChangeAddressRequest();
	            	AddCustomerChangeAddressModel addCustomerChangeAddressModel = new AddCustomerChangeAddressModel();
	            	
	            	addCustomerChangeAddressModel.setCustomerId(userId);
	            	addCustomerChangeAddressModel.setCustomerFirstName(addUserDataBORequest.getRequestData().getFirstName());
	            	addCustomerChangeAddressModel.setCustomerSecondName(addUserDataBORequest.getRequestData().getLastName());
	            	addCustomerChangeAddressModel.setCustomerContactNo(addUserDataBORequest.getRequestData().getContactNo());
	            	addCustomerChangeAddressModel.setOtsHouseNo(addUserDataBORequest.getRequestData().getHouseNo());
	            	addCustomerChangeAddressModel.setOtsBuildingName(addUserDataBORequest.getRequestData().getBuildingName());
	    			addCustomerChangeAddressModel.setOtsStreetName(addUserDataBORequest.getRequestData().getStreetName());
	    			addCustomerChangeAddressModel.setOtsCityName(addUserDataBORequest.getRequestData().getCityName());
	    			addCustomerChangeAddressModel.setOtsPinCode(addUserDataBORequest.getRequestData().getPincode());
	    			addCustomerChangeAddressModel.setOtsStateName(addUserDataBORequest.getRequestData().getStateName());
	    			addCustomerChangeAddressModel.setOtsDistrictName(addUserDataBORequest.getRequestData().getDistrictName());
	    			addCustomerChangeAddressModel.setOtsCountryName(addUserDataBORequest.getRequestData().getCountryName());
	    			addCustomerChangeAddressRequest.setRequest(addCustomerChangeAddressModel);
	            	
	    			//Adding Default Customer Change Address at Initial stage
	    			addCustomerChangeAddress(addCustomerChangeAddressRequest);
	            	
	            	String custMsg = "<p>Hi,<br><br>" + 
	                         "Greetings, you have successfully registered as Buyer. Please wait for Admin to approve your credentials.<br>" + 
	                         "Once approved you may use the same to login.<br><br>" +
	                         "Thanks And Regards,<br>" + 
	                         companyName+" Support Team </p>";
	            	
	                executor.submit(() -> emailUtil.sendCustomermail(emailId, "", "Regarding Buyer Registration", custMsg));
	                
	                String adminMsg = "<p>Hi,<br><br>" + 
	                          "A new Buyer has Registered and waiting for your approval.<br>" +
	                          "Please Approve Buyer, with Name: "+firstName+" "+lastName+" And Contact No: "+userContactNo+"<br><br>" +
	                          "Thanks And Regards,<br>" + 
	                          companyName+" Support Team </p>";
	                
	                //To Notify All Admins regarding buyer Registration 
	                List<UserAccounts> allAdmin = useraccountsDAO.getAllActiveAdmin();
	                for(UserAccounts admins : allAdmin) {
	                	executor.submit(() -> emailUtil.sendAdminMail(admins.getEmail(), "", "Regarding Buyer Registration", adminMsg));
	                }
	            }
	            
	            //executor.shutdown() is called after all tasks are submitted to gracefully shut down the executor service.
	            executor.shutdown();
	            
	            // Mapping the child user to the parent user
	            if (addUserDataBORequest.getRequestData().getMappedTo() != null) {
	            	UserMapping userMapping = new UserMapping();
	                userMapping.setMappedTo(addUserDataBORequest.getRequestData().getMappedTo());
	                userMapping.setUserId(userId);
	                
	                MapUsersDataBORequest mapUsersDataBORequest = new MapUsersDataBORequest();
	                mapUsersDataBORequest.setRequestData(userMapping);
	                String userMappingStatus = mappUser(mapUsersDataBORequest);
	                logger.info("Inside Event=1004,Class:OTSUserServiceImpl,Method:addNewUser, User Mapping status : " + userMappingStatus);
	            }
	        }
	    } catch (Exception e) {
	        logger.error("Exception while Inserting data into DB :" + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Throwable e) {
	        logger.error("Exception while Inserting data into DB :" + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    }
	    return userDataBOResponse;
	}
	
	@Override
	public UserDataBOResponse updateUser(AddUserDataBORequest addUserDataBORequest) {
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			userDataBOResponse = userServiceDAO.updateUser(addUserDataBORequest);
		}catch(Exception e){
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}

	@Override
	public UserDataBOResponse getUserDetails(RequestBOUserBySearch requestBOUserBySearch) 
	{
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			List<UserDetails> userDetailList= userServiceDAO.getUserDetails(requestBOUserBySearch);
			userDataBOResponse.setUserDetails(userDetailList);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}
	
	@Override
	public UserDataBOResponse getEmployeesDetailsByDistributor(RequestBOUserBySearch requestBOUserBySearch) {
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			List<UserDetails> userDetailList= userServiceDAO.getEmployeeDetailsByDistributor(requestBOUserBySearch);
			userDataBOResponse.setUserDetails(userDetailList);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}

	@Override
	public String mappUser(MapUsersDataBORequest mapUsersDataBORequest) {
		String responseData;
		try {
			responseData = userMapDAO.mappUser(mapUsersDataBORequest);
		}catch (BusinessException e){
			logger.error("Exception while fetching data from DB:"+e.getMessage());
        	e.printStackTrace();
        	throw new BusinessException(e.getMessage(), e);
	    }catch (Throwable e) {
	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
        	e.printStackTrace();
        	throw new BusinessException(e.getMessage(), e);
	    }
		return responseData;
	}


	@Override
	public LoginUserResponse otsLoginAuthentication(LoginAuthenticationBOrequest loginAuthenticationBOrequest) {	
		try {
			LoginUserResponse loginUserResponse = new LoginUserResponse();
			//calling DAO and setting response
			loginUserResponse = userServiceDAO.otsLoginAuthentication(loginAuthenticationBOrequest);
			if(loginUserResponse == null) {
				return null;
			}else {
				if(loginUserResponse.getUserDetails().getUserRoleId().equals("4")||loginUserResponse.getUserDetails().getUserRoleId().equals("3")) {
					String did = userMapDAO.getMappedDistributor(loginUserResponse.getUserDetails().getUserId());
					loginUserResponse.getUserDetails().setDistributorId(did);
				}
			}
			return loginUserResponse;
		}catch (BusinessException e){
			logger.error("Exception while fetching data from DB:"+e.getMessage());
        	e.printStackTrace();
        	throw new BusinessException(e.getMessage(), e);
	    }catch (Throwable e) {
	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
        	e.printStackTrace();
        	throw new BusinessException(e.getMessage(), e);
	    }
	}


	@Override
	public UserDataBOResponse getUserDetailsByMapped(String MappedTo) {
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			List<UserDetails> userDetailList= userServiceDAO.getUserDetailsByMapped(MappedTo);
			userDataBOResponse.setUserDetails(userDetailList);
		}catch(Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}

	@Override
	public ForgotPasswordResponse sendOTP(ForgotPasswordRequest forgotPasswordRequest) {
		ForgotPasswordResponse forgotPasswordResponse = new ForgotPasswordResponse();
		UserDetails userDetails = new UserDetails();
		try {
			SecureRandom secureRandom = new SecureRandom();
//			Random rand = new Random(); 
//			int otp = 1000 +rand.nextInt(9000);
			int otp = 1000 +secureRandom.nextInt(9000);
			userDetails = userServiceDAO.checkForOTP(forgotPasswordRequest.getRequest().getMobileNumber());
			if(userDetails!=null) {
				String distMsg = "<p>Hi "+userDetails.getFirstName()+" "+userDetails.getLastName()+"<br><br>" + 
                         "Your one-time password (OTP) for accessing "+companyName+" is: "+ otp+".<br>" +
                         "Please do not share this OTP with anyone to keep your account secure<br><br>" +
                         "Thanks And Regards,<br>" + 
                         companyName+" Support Team </p>";
           	 
				emailUtil.sendOTP(userDetails.getEmailId(), "", companyName+" OTP Verification", distMsg);
				forgotPasswordResponse.setOtp(String.valueOf(otp));
				forgotPasswordResponse.setUserId(userDetails.getUserId());
				return  forgotPasswordResponse;
			}else {
				return null;
			}
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
	}
	
	@Override
	public String changePassword(ChangePasswordRequest changePasswordRequest) {
		String changeAddress;
		try {
			changeAddress = userServiceDAO.changePassword(changePasswordRequest);
		} catch (Exception e) {
			logger.error("Exception while inserting data into DB:" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB:" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return changeAddress;
	}

	@Override
	public String updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		try {
			return userServiceDAO.updatePassword(updatePasswordRequest);
		}catch(Exception e) {
			logger.error("Exception while inserting data into DB:"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}catch (Throwable e) {
			logger.error("Exception while inserting data into DB:"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public String addWishList(AddWishListRequest addWishListRequest) {
		String addWishList = null;
		try {
			addWishList = otsProductWishlistDAO.addWishList(addWishListRequest);
		}catch(Exception e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
		return addWishList;
	}

    @Override
	public List<GetwishListResponse> getWishList(String customerId) {
		List<GetwishListResponse> wishList = new ArrayList<>();
		try {
			wishList = otsProductWishlistDAO.getWishList(customerId);

			// Iterate through each product in the wishlist
			for (int i = 0; i < wishList.size(); i++) {
			    GetwishListResponse products = wishList.get(i);
			    List<GetProductBOStockResponse> productStock = productStockDao.getProductStockByProductId(products.getProductId());
			    String stockQuantity = (productStock.isEmpty()) ? "0" : productStock.get(0).getStockQuantity();
	
			    // Set the stock quantity for each product
			    products.setProductStockQuantity(stockQuantity);
			}
		}catch(Exception e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
		return wishList;
	}
    
    @Override
    public String addToCart(AddToCartRequest addToCartRequest) {
        try {
            // Fetching Product Details
            ProductDetails productDetails = productServiceDAO.getProductDetails(addToCartRequest.getRequestData().getProductId().toString());
            
            // To validate if product price is null or getting invalid request productPrice from request
            if (productDetails == null || (!productDetails.getProductPrice().equalsIgnoreCase(addToCartRequest.getRequestData().getProductPrice()))) {
                return "Invalid Product Details";
            } else {
            	// Fetching actual Product stock 
                List<GetProductBOStockResponse> productStock = productStockDao.getProductStockByProductId(addToCartRequest.getRequestData().getProductId());
                
                // Fetching cart details Based on customer and product
                GetcartListResponse getCartList = cartDAO.getCartByCustomerProduct(addToCartRequest.getRequestData().getCustomerId(),addToCartRequest.getRequestData().getProductId());

                int getStock = (getCartList != null) ? getCartList.getOtsCartQty() : 0;

                int requestQty = addToCartRequest.getRequestData().getOtsCartQty();
                int totalQty = getStock + requestQty;
                
                //If Product is Out of Stock
                if (productStock.isEmpty()) {
                    return "Out Of Stock";
                } else {
                    int availableStocks = Integer.parseInt(productStock.get(0).getStockQuantity());
                    
                    //Adding Quantity to Cart only when Total quantity is within Avilable Stock 
                    if (totalQty > availableStocks) {
                        return "Insufficient Stock. Only " + availableStocks + " Stock Available.";
                    } else {
                    	//Adding to Cart
                        return cartDAO.addToCart(addToCartRequest);
                    }
                }
            }
        }  catch (Exception e) {
			logger.error("Exception while inserting data into DB:" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB:" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public GetCartResponse getCartList(String customerId) {
		//function add to cart for the customer
		GetCartResponse list = new GetCartResponse();
		try {
			List<GetcartListResponse> cartList = cartDAO.getCartList(customerId);
			Integer totaldelivery = 0;
			//Code to set decimal point to 2 digits
			DecimalFormat decimalformat = new DecimalFormat("0.00");
			decimalformat.setRoundingMode(RoundingMode.DOWN);

			Float totalPrice = 0.00f;
			Float finalPrice = 0.00f;
			Float totalGstPrice = 0.00f;
			Float totalPriceWithoutGst = 0.00f;

			for(int i=0;i<cartList.size();i++) {
			//adding extra amount of percentage to product price from config file to get the details 
				//Float result =Float.parseFloat(cartList.get(i).getProductPrice().toString()) + ((Float.parseFloat(cartList.get(i).getProductPrice().toString())* Float.parseFloat(productPercentage))/100);
				//BigDecimal resultBigDecimal = new BigDecimal(result);
				//cartList.get(i).setProductPrice(resultBigDecimal.toString());
				Integer quantity = cartList.get(i).getOtsCartQty();
				Float productprice = Float.parseFloat(cartList.get(i).getProductPrice());
				Float gst = Float.parseFloat(cartList.get(i).getProductGST())/100;
				
				//calculating GST with product price and finding total product GST of cart product
				Float productgst = productprice * gst ;
				
				//calculating total GST
				Float totalGst = quantity * productgst;
				cartList.get(i).setTotalGST(decimalformat.format(totalGst).toString());
				
				//adding product GST with Product price
				Float priceWithGst = productprice + productgst;
				cartList.get(i).setPriceWithGst(decimalformat.format(priceWithGst).toString());
				
				//calculating total price of a product
				totalPrice = priceWithGst * quantity;
				cartList.get(i).setTotalPrice(decimalformat.format(totalPrice).toString());
				
				//Adding delivery in response
				Integer deliveryCharge = Integer.parseInt(productServiceDAO.getDeliveryChargeForProduct(cartList.get(i).getProductId()));
				cartList.get(i).setDeliveryCharge(deliveryCharge);
				//total = total.add(totalPrice);
				//totaldelivery = deliveryCharge+totaldelivery;
				
				//Adding product stock quantity in response
				List<GetProductBOStockResponse> productStock = productStockDao.getProductStockByProductId(cartList.get(i).getProductId());
				cartList.get(i).setProductStockQuantity(productStock.size() == 0 ?"0":productStock.get(0).getStockQuantity());
				
				//calculating Total Order Price without GST
				totalPriceWithoutGst = totalPriceWithoutGst + (productprice * quantity);
				
				//calculating total GST Price for Order
				totalGstPrice = totalGstPrice + totalGst;
				
				//calculating total final price of cart by adding all the product price 
				finalPrice = finalPrice + totalPrice;
			}
			//setting Final Price, Total GST Price, Total Price Without Gst in response
			list.setFinalPrice(decimalformat.format(finalPrice).toString());
			list.setTotalGstPrice(decimalformat.format(totalGstPrice).toString());
			list.setTotalPriceWithoutGst(decimalformat.format(totalPriceWithoutGst).toString());
			
			//To check first order of customer 
			List<OrderDetails> orderDetails = orderServiceDAO.checkForFirstOrderByCustomer(customerId);
			if(orderDetails.size()==0) {
				list.setFirstOrder("yes");
			}else {
				list.setFirstOrder("no");
			}
			list.setCartList(cartList);
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return list;
	}
	
	@Override
	public String removeFromCart(AddToCartRequest addToCartRequest) {
		try {
			return cartDAO.removeFromCart(addToCartRequest);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public String removeFromWishList(AddWishListRequest addWishListRequest) {
		try {
			return otsProductWishlistDAO.removeFromWishList(addWishListRequest);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public String emptyCart(AddToCartRequest addToCartRequest) {
		try {
			return cartDAO.emptyCart(addToCartRequest);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}

	}

	/*Shreekant Rathod 29-1-2021*/
	@Override
	public String addReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest) {
		List<GetReviewAndRatingResponse> reviewRating = new ArrayList<GetReviewAndRatingResponse>();
		String response = null;
		try {
			reviewRating = reviewAndRatingDAO.getReviewAndRatingByOrderId(addReviewAndRatingRequest.getRequestData().getOrderId(),addReviewAndRatingRequest.getRequestData().getProductId(),addReviewAndRatingRequest.getRequestData().getCustomerId());
			if(reviewRating.size() != 0) {
				return "Review Rating Already Added";
			}
			else {
				response = reviewAndRatingDAO.addReviewAndRating(addReviewAndRatingRequest);
			}
		}catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return response;
	}
	
	@Override
	public List<GetReviewAndRatingResponse> getReviewAndRating(GetReviewRatingRequest getReviewRatingRequest) {
		List<GetReviewAndRatingResponse> reviewRating = new ArrayList<GetReviewAndRatingResponse>();
		try {
			reviewRating = reviewAndRatingDAO.getReviewAndRating(getReviewRatingRequest);
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return reviewRating;
	}
	
	@Override
	public List<GetReviewAndRatingResponse> getReviewAndRatingByOrderId(GetReviewsAndRatingRequest getReviewsAndRatingRequest) {
		List<GetReviewAndRatingResponse> reviewRating = new ArrayList<GetReviewAndRatingResponse>();
		try {
			reviewRating = reviewAndRatingDAO.getReviewAndRatingByOrderId(getReviewsAndRatingRequest.getRequest().getOrderId(),getReviewsAndRatingRequest.getRequest().getProductId(),getReviewsAndRatingRequest.getRequest().getCustomerId());
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return reviewRating;
	}
	
	@Override
	public AverageReviewRatingResponse getAverageRatingOfProduct(String productId) {
		AverageReviewRatingResponse averageReviewRatingResponse = new AverageReviewRatingResponse();
		AverageReviewRatingModel averageReviewRatingModel = new AverageReviewRatingModel();
	    try {
	        // Step 1: Count existing reviews
	    	List<GetReviewAndRatingResponse> reviewRating = reviewAndRatingDAO.getReviewAndRatingForProduct(productId);
	        if (reviewRating.size() == 0) {
	            return null;
	        }

	        // Step 2: Call the PostgreSQL function that returns a scalar value
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	    .withFunctionName("average_rating")
	        	    .withReturnValue()
	        	    .declareParameters(
	        	        new SqlParameter("product_id", Types.OTHER),
	        	        new SqlOutParameter("return", Types.NUMERIC)
	        	    )
	        	    .withoutProcedureColumnMetaDataAccess();

	        Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("product_id", UUID.fromString(productId));

	        // Execute and get the result directly as an object
	        BigDecimal averageRating = jdbcCall.executeFunction(BigDecimal.class, paramMap);

	        // Step 3: Set response
	        averageReviewRatingModel.setAverageRating(averageRating.toString());
	        averageReviewRatingModel.setTotalReviewRating(reviewRating.size());
	        averageReviewRatingResponse.setAverageReviewRating(averageReviewRatingModel);

	        return averageReviewRatingResponse;

	    } catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public String updateReviewAndRating(AddReviewAndRatingRequest addReviewAndRatingRequest) {
		String updateReview = null;
		try {
			updateReview = reviewAndRatingDAO.updateReviewAndRating(addReviewAndRatingRequest);
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return updateReview;
	}

	@Override
	public String updateReviewStatus(UpdateReviewStatusRequest updateReviewStatusRequest) {
		String updateReview = null;
		try {
			updateReview = reviewAndRatingDAO.updateReviewStatus(updateReviewStatusRequest);
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return updateReview;
	}

	@Override
	public UserDetails loginWithOtp(LoginAuthenticationBOrequest loginAuthenticationBOrequest) {
		UserDetails userDetails = new UserDetails();
		userDetails = userServiceDAO.checkForOTP(loginAuthenticationBOrequest.getRequestData().getPhoneNumber());
		
//		Random rand = new Random(); 
//		int otp = rand.nextInt(10000); 
//		userDetails.setOtp(String.valueOf(otp));
//		SmsApi.callSms("manoj.vg@ortusolis.com", loginAuthenticationBOrequest.getRequestData().getPhoneNumber(),String.valueOf(otp));
//		
		return userDetails;
	}
	
	@Override
	public List<Map<String, Object>> getBannerinfo(RequestBOUserBySearch requestBOUserBySearch) {
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("account_id", requestBOUserBySearch.getRequestData().getSearchvalue());
			
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_account_banner_info")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("account_id", Types.OTHER));
			
			Map<String, Object> result = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> bannerInfo = (List<Map<String, Object>>) result.get("#result-set-1");
			System.out.println(bannerInfo);
			return bannerInfo;
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> getAccountFooterDetails(RequestBOUserBySearch requestBOUserBySearch)
	{
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("account_id", requestBOUserBySearch.getRequestData().getSearchvalue());
			
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_account_footer_details")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("account_id", Types.OTHER));
			
			Map<String, Object> result = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> accountfooter = (List<Map<String, Object>>) result.get("#result-set-1");
			System.out.println("data = " +accountfooter);
			return accountfooter;
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public DistributerBOResponse getActiveDistributerList() {
		DistributerBOResponse distributerBOResponse = new DistributerBOResponse();
		try {
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("distributer_list")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> distributer = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			distributerBOResponse.setDistributerList(distributer);
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return distributerBOResponse;
	}
	
	@Override
	public UserDataBOResponse getDistributorByCreatedUser(String CreatedUser) {
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			List<UserDetails> userDetailList= userServiceDAO.getDistributorByCreatedUser(CreatedUser);
			userDataBOResponse.setUserDetails(userDetailList);
		}catch(Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}

	@Override
	public List<Map<String, Object>> getAllBannerinfo() {
	    try {
	        String sql = "SELECT * FROM get_banner_info()";
	        return jdbcTemplate.queryForList(sql);
	    }catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public DistributerResponse getDistributerDetails()
	{
		DistributerResponse DistributerResponse = new DistributerResponse();
		DistributerResponse=userServiceDAO.getDistributerDetails();
		return DistributerResponse;
	}
	
	@Override
	public String addDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest) {
		try {
			distributorBannerDAO.addDistributorBanner(addDistributorBannerRequest);
			return "Inserted";
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<DistributorBanner> getDistributorBanner(String distributorId){
		List<DistributorBanner> bannerDetails = new ArrayList<DistributorBanner>();
		try {
			bannerDetails = distributorBannerDAO.getDistributorBanner(distributorId);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return bannerDetails;
	}
	
	@Override
	public String updateDistributorBanner(AddDistributorBannerRequest addDistributorBannerRequest) {
		try {
			distributorBannerDAO.updateDistributorBanner(addDistributorBannerRequest);
			return "Updated";
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public DistributorBanner getBannerDetailsByBannerId(String bannerId){
		DistributorBanner bannerDetails = new DistributorBanner();
		try {
			bannerDetails = distributorBannerDAO.getBannerDetailsByBannerId(bannerId);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return bannerDetails;
	}
		
	@Override
	public String deleteDistributorBanner(String bannerId)
	{
		try {
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("banner_id",Integer.parseInt(bannerId));

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("delete_distributor_banner")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("banner_id", Types.INTEGER)); 
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Deleted Successfully";
	}
	
	@Override
	public String addCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest) {
		List<CustomerChangeAddress> customerDetailsList = new ArrayList<CustomerChangeAddress>();
		try {
			customerDetailsList = customerChangeAddressDAO.getCustomerChangeAddressByCustomerId(addCustomerChangeAddressRequest.getRequest().getCustomerId());
			if(customerDetailsList.size() >= 5) {
				return "Only Five Delivery Address Can Be Added";
			}else {
				String addCustAddress = customerChangeAddressDAO.addCustomerChangeAddress(addCustomerChangeAddressRequest);
				return addCustAddress;
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String updateCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest) {
		try {
			String update =	customerChangeAddressDAO.updateCustomerChangeAddress(addCustomerChangeAddressRequest);
			return update;
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<CustomerChangeAddress> getCustomerChangeAddressByCustomerId(String customerId) {
		List<CustomerChangeAddress> customerDetailsList = new ArrayList<CustomerChangeAddress>();
		try {
			customerDetailsList = customerChangeAddressDAO.getCustomerChangeAddressByCustomerId(customerId);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return customerDetailsList;
	}
	
	@Override
	public List<CustomerChangeAddress> getCustomerChangeAddressById(String customerChangeAddressId) {
		List<CustomerChangeAddress> customerDetailsList = new ArrayList<CustomerChangeAddress>();
		try {
			customerDetailsList = customerChangeAddressDAO.getCustomerChangeAddressById(customerChangeAddressId);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return customerDetailsList;
	}
	
	@Override
	public String deleteCustomerChangeAddress(String customerChangeAddressId)
	{
		try {
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("customer_change_address_id",UUID.fromString(customerChangeAddressId));

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("delete_customer_change_address")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("customer_change_address_id", Types.OTHER)); 
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Deleted Successfully";
	}
	
	@Override
	public String addDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest) {
		try {
			List<DistributorCompanyDetails> distributorCompanyDetails = getDistributorCompanyDetails(addDistributorCompanyDetailsRequest.getRequest().getDistributorId());
			if(distributorCompanyDetails.size() == 0) {
				distributorCompanyDetailsDAO.addDistributorCompanyDetails(addDistributorCompanyDetailsRequest);
				return "Company Details Added Successfully";
			}else {
				return "Company Details Already Registered For This User";
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String updateDistributorCompanyDetails(AddDistributorCompanyDetailsRequest addDistributorCompanyDetailsRequest) {
		try {
			String updateDistributor = distributorCompanyDetailsDAO.updateDistributorCompanyDetails(addDistributorCompanyDetailsRequest);
			return updateDistributor;
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<DistributorCompanyDetails> getDistributorCompanyDetails(String distributorId) {
		List<DistributorCompanyDetails> companyDetailsList = new ArrayList<DistributorCompanyDetails>();
		try {
			companyDetailsList = distributorCompanyDetailsDAO.getDistributorCompanyDetails(distributorId);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return companyDetailsList;
	}
	
	@Override
	public String generateDistributorRegistrationInvoicePdf(String distributorId,String registrationPaymentId) {
		List<DistributorCompanyDetails> companyDetailsList = new ArrayList<DistributorCompanyDetails>();
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			companyDetailsList = distributorCompanyDetailsDAO.getDistributorCompanyDetails(distributorId);
			if(companyDetailsList.size() == 0) {
				return "Distributor Not Added Company Details";
			}
			else {
				//To generate registration Invoice pdf
				byte[] pdfPath = DistributorRegisterInvoicePdf.generateRegistrationInvoiceCopy(companyDetailsList,registrationPaymentId);
				//To encode byte[] to String
				String encodedString = Base64.getEncoder().encodeToString(pdfPath);
				System.out.println(encodedString);
				//To add encoded Invoice path into order table(DB)
				String addInvoice = userServiceDAO.addDistributorRegistrationInvoiceToDB(distributorId,encodedString);
				
				executor.submit(() -> {
					try {
						//To Add PDF Attachment file to mail
						String pdfName = "RegisterInvoice_"+distributorId+".pdf";	//Recreating pdf with same name
				        
				        //Creating message content
						String distMsg="<p>Hi,<br><br>" + 
								"Your Payment for Registration is Completed Successfully.<br>" + 
								"Download the Attached Registration Invoice.<br>" +
								"Please wait for Admin's approval to get your account Activated.<br><br>" +
								"Thanks And Regards,<br>" + 
								"ORTUSOLIS TECHNOLOGIES PVT LTD </p>";
				        
						//To send Email to Distributor for Successful Registration with attached Invoice
						UserDetails distributor = userServiceDAO.getUserDetails(distributorId);
						emailUtil.sendDistributermailWithAttachment(distributor.getEmailId(), "", "Regarding Registration Payment", distMsg,pdfPath,pdfName);
						
						//Creating message content
						String financeMsg="<p>Hi,<br><br>" + 
								"Payment for New Distributor Registration by Distributor ID:"+distributor.getUserId()+" is Completed Successfully.<br>" + 
								"Please Download the Attached Registration Invoice.<br><br>" +
								"Thanks And Regards,<br>" + 
								"ORTUSOLIS TECHNOLOGIES PVT LTD </p>";
						
						//To send Email to OTS Finance team for Successful Registration with attached Invoice
						emailUtil.sendFinanceMailWithAttachment("", "Regarding New Distributor Registration", financeMsg,pdfPath,pdfName);
						System.out.println("mail sent");
					}catch(Exception e) {	//added try catch block to pass the exception & continue processing
					}
				});
				return addInvoice;
			}
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String updateUserStatus(UpdateUserStatusRequest updateUserStatusRequest) {
		String updateUserResponse = null;
		try {
			Map<String, Object> queryParameters = new HashMap<>();
		       
	        queryParameters.put("distributor_id", UUID.fromString(updateUserStatusRequest.getRequest().getUsersId()));
	        queryParameters.put("user_status", updateUserStatusRequest.getRequest().getUsersStatus());

	        // Add rejection_reason only if status is 'rejected'
	        if ("rejected".equalsIgnoreCase(updateUserStatusRequest.getRequest().getUsersStatus())) {
	            queryParameters.put("rejection_reason", updateUserStatusRequest.getRequest().getUsersRejectionReason());
	        } else {
	            queryParameters.put("rejection_reason", null);
	        }

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withFunctionName("update_user_status")
	                .withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("user_status", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("rejection_reason", Types.VARCHAR));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Unable To Delete]")) {
				updateUserResponse = "Inactive All The Products & Close All The Orders To Delete Seller";
			}
			else if(response.equalsIgnoreCase("[Updated]")) {
				updateUserResponse = "Updated";
			}
			else {
				updateUserResponse = "Not Updated";
			}
			
			if(updateUserResponse.equalsIgnoreCase("Updated")) {
				UserDetails userDetails = userServiceDAO.getUserDetails(updateUserStatusRequest.getRequest().getUsersId());
				
				//The ExecutorService is used to send emails asynchronously, reducing the main thread's wait time.
				//Increase ThreadPool size to increase execution time if load increases
				ExecutorService executor = Executors.newFixedThreadPool(3);
				
				String userStatus = userDetails.getUsrStatus();
				//To send email notification for distributor if admin changes distributor status to "active" or "rejected"
				if("2".equalsIgnoreCase(userDetails.getUserRoleId()) && "active".equalsIgnoreCase(userStatus)) {
					//Link for distributor login page
					String distPageLink = "<a href= '"+distributorPageLoginLink+"'>Login</a> </p>";
					
					//Creating message content
					String distMsg="<p>Hi "+userDetails.getFirstName()+" "+userDetails.getLastName()+",<br><br>" + 
							"Greetings, admin has approved your registration, you may proceed to login.<br>" + 
							"You can log in here  "+distPageLink+"<br>" +
							"If you face any issues, feel free to contact our support team.<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					
					//sending distributor mail for successful Account Activation by Admin 
					executor.submit(() -> emailUtil.sendDistributermail(userDetails.getEmailId(), "", "Regarding Registration", distMsg));
				}
				if("2".equalsIgnoreCase(userDetails.getUserRoleId()) && "rejected".equalsIgnoreCase(userStatus)) {
					//Creating message content
					String distMsg="<p>Hi "+userDetails.getFirstName()+" "+userDetails.getLastName()+",<br><br>" + 
							"Sorry, admin rejected your application due to reason "+updateUserStatusRequest.getRequest().getUsersRejectionReason()+
							"If you face any issues, feel free to contact our support team.<br><br>" + 
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					//sending distributor mail for Account Rejection by Admin 
					executor.submit(() -> emailUtil.sendDistributermail(userDetails.getEmailId(), "", "Regarding Registration", distMsg));
				}
				
				//To send mail for Distributor & Admin, when Distributor Registers & in status Pending
	            if ("2".equalsIgnoreCase(userDetails.getUserRoleId()) && "pending".equalsIgnoreCase(userStatus)) {
	            	 String distMsg = "<p>Hi,<br><br>" + 
	                         "Greetings, you have successfully registered as Seller. Please wait for Admin to approve your credentials.<br>" + 
	                         "Once approved you may use the same to login.<br><br>" +
	                         "Thanks And Regards,<br>" + 
	                         companyName+" Support Team </p>";
	            	
	            	 UserAccounts adminDetails = useraccountsDAO.getUseraccountDetail(userDetails.getCreatedUser());
	            	 String adminMsg = "<p>Hi,<br><br>" + 
	                          "A new Seller has Registered and waiting for your approval.<br>" +
	                          "Please Approve Seller, with Name: "+userDetails.getFirstName()+" "+userDetails.getLastName()+" And Comtact No: "+userDetails.getContactNo()+"<br><br>" +
	                          "Thanks And Regards,<br>" + 
	                          companyName+" Support Team </p>";
	            	 
	            	 executor.submit(() -> emailUtil.sendDistributermail(userDetails.getEmailId(), "", "Regarding Registration", distMsg));
	            	 executor.submit(() -> emailUtil.sendAdminMail(adminDetails.getEmail(), "", "Regarding Seller Registration", adminMsg));
	            }
	            
	            //To send email notification for distributor if Admin Delete Distributor Account
				if("2".equalsIgnoreCase(userDetails.getUserRoleId()) && "delete".equalsIgnoreCase(userStatus)) { 
					//Creating message content
					String distMsg="<p>Hi "+userDetails.getFirstName()+" "+userDetails.getLastName()+",<br><br>" + 
							"Your account has been <b>permanently deleted by Admin</b>. You will no longer be able to access your account.<br><br>" +
					        "If you have any concerns, please contact our support team.<br><br>" +
					        "Thanks and Regards,<br>" +
					        companyName + " Support Team</p>";
					
					//sending distributor mail for Account Deleted by Admin 
					executor.submit(() -> emailUtil.sendDistributermail(userDetails.getEmailId(), "", "Your Seller Account Has Been Deleted", distMsg));
				}
				
				//To send email notification for distributor if admin changes distributor status to "active" or "rejected"
				if("4".equalsIgnoreCase(userDetails.getUserRoleId()) && "active".equalsIgnoreCase(userStatus)) {
					//Link for customer login page
					String custPageLink = "<a href= '"+customerPageLoginLink+"'>Login</a> </p>";
					
					//Creating message content
					String custMsg="<p>Hi "+userDetails.getFirstName()+" "+userDetails.getLastName()+",<br><br>" + 
							"Greetings, admin has approved your registration, you may proceed to login.<br>" + 
							"You can log in here  "+custPageLink+"<br>" +
							"If you face any issues, feel free to contact our support team.<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					
					//sending distributor mail for successful Account Activation by Admin 
					executor.submit(() -> emailUtil.sendCustomermail(userDetails.getEmailId(), "", "Regarding Registration", custMsg));
				}
				
				//executor.shutdown() is called after all tasks are submitted to gracefully shut down the executor service.
				executor.shutdown();
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return updateUserResponse;
	}
	
	@Override
	public String addDistributorRegistrationDetails(AddDistributorRegistrationDetailsRequest addDistributorRegistrationDetailsRequest)  {
		try {
			String registrationDetails = userServiceDAO.addDistributorRegistrationDetails(addDistributorRegistrationDetailsRequest);
			if(registrationDetails != null) {
				String registrationInvoice = generateDistributorRegistrationInvoicePdf(addDistributorRegistrationDetailsRequest.getRequest().getUserId(),addDistributorRegistrationDetailsRequest.getRequest().getRegistrationPaymentId());
			}
			return "Inserted";
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public UserDataBOResponse getUserDetailsByContactNo(String contactNo) {
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			List<UserDetails> userDetailList= userServiceDAO.getUserDetailsByContactNo(contactNo);
			userDataBOResponse.setUserDetails(userDetailList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}
	
	@Override
	public ServiceStateResponse getAllStates() {
		ServiceStateResponse serviceStateResponse = new ServiceStateResponse();
		try {
			List<ServiceState> stateList = serviceStateDAO.getAllStates();
			serviceStateResponse.setStateDetails(stateList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceStateResponse;
	}
	
	@Override
	public ServiceDistrictResponse getDistrictByStateId(String stateId) {
		ServiceDistrictResponse serviceDistrictResponse = new ServiceDistrictResponse();
		try {
			List<ServiceDistrict> districtList = serviceDistrictDAO.getDistrictByStateId(stateId);
			serviceDistrictResponse.setDistrictDetails(districtList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceDistrictResponse;
	}
	
	@Override 
	public ProductLocationResponse addProductLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest) {
		ProductLocationResponse productLocationResponse = new ProductLocationResponse();
		List<ProductLocationMapping> productLocationMap = new ArrayList<ProductLocationMapping>();
		String Response;
		try {
			Response = productLocationMappingDAO.addProductLocationMapping(addProductLocationMappingRequest);
			if(Response.equalsIgnoreCase("Inserted")) {
				if(addProductLocationMappingRequest.getRequest().getProductId() == null || addProductLocationMappingRequest.getRequest().getProductId().equals("")) {
					productLocationMap = productLocationMappingDAO.getLocationsMappedToDistributorOnly(addProductLocationMappingRequest.getRequest().getDistributorId());
				}else {
					productLocationMap = productLocationMappingDAO.getLocationsMappedToDistributorAndProduct(addProductLocationMappingRequest.getRequest().getDistributorId(),addProductLocationMappingRequest.getRequest().getProductId());
				}
				productLocationResponse.setProductLocation(productLocationMap);
			}
		}catch(Exception e){
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return productLocationResponse;
	}
	
	@Override
	public ProductLocationResponse getLocationsMappedToProduct(String productId) {
		ProductLocationResponse productLocationResponse = new ProductLocationResponse();
		try {
			List<ProductLocationMapping> productLocationMap = productLocationMappingDAO.getLocationsMappedToProduct(productId);
			productLocationResponse.setProductLocation(productLocationMap);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return productLocationResponse;
	}
	
	@Override
	public ProductLocationResponse clearAndAddProductLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest) {
		ProductLocationResponse productLocationResponse = new ProductLocationResponse();
		try {
			try {
				Map<String, Object> queryParameters = new HashMap<String, Object>();
				queryParameters.put("product_id",addProductLocationMappingRequest.getRequest().getProductId());
				SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
		        		.withFunctionName("delete_locations_for_product")
		        		.withSchemaName("public")
		                .withoutProcedureColumnMetaDataAccess();

				simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.VARCHAR));
				Map<String, Object> result = simpleJdbcCall.execute(queryParameters);
			} catch (Exception e) {
				return null;
			} catch (Throwable e) {
				return null;
			}
			List<ProductLocationMapping>ProductLocationMapping = productLocationMappingDAO.addProductLocationMappings(addProductLocationMappingRequest);
			productLocationResponse.setProductLocation(ProductLocationMapping);
		} catch (Exception e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return productLocationResponse;
	}
	
	public String checkProductLocationAvailability(CheckProductAvailabilityRequest checkProductAvailabilityRequest){
		try {
			ProductDetails productDetails = productServiceDAO.getProductDetails(checkProductAvailabilityRequest.getRequest().getProductId());
			if(productDetails == null) {
				return null;
			}else {
				List<ServicePincode> servicePincodeList = servicePincodeDAO.getPincodeDetails(checkProductAvailabilityRequest.getRequest().getPincode());
				if(servicePincodeList.size() == 0) {
					return null;
				}else {
					Map<String, Object> queryParameters = new HashMap<String, Object>();
					queryParameters.put("product_id",checkProductAvailabilityRequest.getRequest().getProductId());
					queryParameters.put("distributor_id",productDetails.getDistributorId());
					queryParameters.put("pincode",checkProductAvailabilityRequest.getRequest().getPincode());
					queryParameters.put("district_id",servicePincodeList.get(0).getDistrictId());
					queryParameters.put("state_id",servicePincodeList.get(0).getStateId());
				    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
			        		.withFunctionName("check_product_location_availability")
			        		.withSchemaName("public")
			                .withoutProcedureColumnMetaDataAccess();
				    
				    simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
				    simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));
				    simpleJdbcCall.addDeclaredParameter(new SqlParameter("pincode", Types.VARCHAR));
				    simpleJdbcCall.addDeclaredParameter(new SqlParameter("district_id", Types.VARCHAR));
				    simpleJdbcCall.addDeclaredParameter(new SqlParameter("state_id", Types.VARCHAR));
				    Map<String, Object> result = simpleJdbcCall.execute(queryParameters);
					List<Map<String, Object>> productLocation = (List<Map<String, Object>>) result.get("#result-set-1");
					String response = productLocation.get(0).values().toString();	
					System.out.println("response = "+response);
					if(response.equalsIgnoreCase("[Yes]")) {
						return "Product is Available in this location";
					}else {
						return "Product is Not Available in this location";
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public ServiceDistrictResponse getAllDistricts() {
		ServiceDistrictResponse serviceDistrictResponse = new ServiceDistrictResponse();
		try {
			List<ServiceDistrict> districtList = serviceDistrictDAO.getAllDistricts();
			serviceDistrictResponse.setDistrictDetails(districtList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceDistrictResponse;
	}
	
	@Override
	public StateDistrictResponse getAllStatesAndDistrictsList() {
		StateDistrictResponse statesDistrictsResponse = new StateDistrictResponse();
		try {
			//to get list of all states
			List<ServiceState> stateList = serviceStateDAO.getAllStates();
			if(stateList.size() == 0) {
				return null;
			}else {
				List<StateDistrictModel> getStatesDistrictsDetails = new ArrayList<StateDistrictModel>();
				//to districts list that are mapped to each states
				for (int i = 0; i <stateList.size() ; i++)
				{
					List<ServiceDistrict> districtList = serviceDistrictDAO.getDistrictByStateId(stateList.get(i).getOtsStateId());
					getStatesDistrictsDetails.add(AddStatesAndDistrictsIntoResponse(stateList.get(i),districtList));
				}
				statesDistrictsResponse.setStates(getStatesDistrictsDetails);
				return statesDistrictsResponse;
			}
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	private StateDistrictModel AddStatesAndDistrictsIntoResponse(ServiceState statesList, List<ServiceDistrict> districtsList) {
		StateDistrictModel statesDistrictsResponse = new StateDistrictModel();
	
		statesDistrictsResponse.setOtsStateId(statesList.getOtsStateId());
		statesDistrictsResponse.setOtsStateName(statesList.getOtsStateName());
		statesDistrictsResponse.setOtsStateStatus(statesList.getOtsStateStatus());
		statesDistrictsResponse.setDistricts(districtsList);
		return statesDistrictsResponse;
	}
	
	@Override
	public DistributorPaymentDetailsResponse getDistributorPaymentDetails(String distributorId) {
		DistributorPaymentDetailsResponse distributorPaymentDetailsResponse = new DistributorPaymentDetailsResponse();
		try {
			List<DistributorPaymentDetails> distributorPaymentDetails = distributorPaymentDetailsDAO.getDistributorPaymentDetails(distributorId);
			distributorPaymentDetailsResponse.setDistributorPaymentDetails(distributorPaymentDetails);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return distributorPaymentDetailsResponse;
	}
	
	@Override
	public List<ProductDetails> getProductsByDistributorWithReviewAndRating(String distributorId) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try {
			productDetails = productServiceDAO.getProductsByDistributorWithReviewAndRating(distributorId);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetails;
	}
	
	@Override
	public  ServicePincodeResponse  getPincodeByDistrict(String districtId) {
		ServicePincodeResponse servicePincodeResponse = new ServicePincodeResponse();
		try {
			List<ServicePincode> servicePincodeList = servicePincodeDAO.getPincodeByDistrict(districtId);			
			servicePincodeResponse.setPincodeDetails(servicePincodeList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return servicePincodeResponse;
	}
	
	@Override
	public  ServicePincodeResponse  getPincodeDetails(String pincode) {
		ServicePincodeResponse pincodeResponse = new ServicePincodeResponse();
		try {
			List<ServicePincode> servicePincodeList = servicePincodeDAO.getPincodeDetails(pincode);	
			pincodeResponse.setPincodeDetails(servicePincodeList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return pincodeResponse;
	}
	
	@Override
	public ProductLocationResponse getLocationsMappedToDistributorOnly(String distributorId) {
		ProductLocationResponse productLocationResponse = new ProductLocationResponse();
		try {
			List<ProductLocationMapping> productLocationMap = productLocationMappingDAO.getLocationsMappedToDistributorOnly(distributorId);
			productLocationResponse.setProductLocation(productLocationMap);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return productLocationResponse;
	}
	
	@Override
	public ServicePincodeResponse getPincodeByMultipleDistricts(List<String> districtId) {
		ServicePincodeResponse servicePincodeResponse = new ServicePincodeResponse();
		List<ServicePincode> allServicePincodeList = new ArrayList<ServicePincode>();
		try {
			for(int i=0;i<districtId.size();i++) {
				List<ServicePincode> servicePincodeList = servicePincodeDAO.getPincodeByDistrict(districtId.get(i));
				allServicePincodeList.addAll(servicePincodeList);
			}
			servicePincodeResponse.setPincodeDetails((allServicePincodeList));
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return servicePincodeResponse;
	}
	
	@Override
	public ServiceDistrictResponse getDistrictsByMultipleStates(List<String> stateId) {
		ServiceDistrictResponse serviceDistrictResponse = new ServiceDistrictResponse();
		List<ServiceDistrict> allServiceDistrictsList = new ArrayList<ServiceDistrict>();
		try {
			for(int i=0;i<stateId.size();i++) {
				List<ServiceDistrict> districtList = serviceDistrictDAO.getDistrictByStateId(stateId.get(i));
				allServiceDistrictsList.addAll(districtList);
			}
			serviceDistrictResponse.setDistrictDetails(allServiceDistrictsList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceDistrictResponse;
	}
	
	@Override
	public UserDataBOResponse getDistributorsWithActiveProductsByCountry(String countryCode) {
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			List<UserDetails> userDetailList= userServiceDAO.getDistributorsWithActiveProductsByCountry(countryCode);
			userDataBOResponse.setUserDetails(userDetailList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}
	
	@Override
	public String updateUserContact(UpdateUserContactRequest UpdateUserContactRequest) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("contact", UpdateUserContactRequest.getRequest().getContactNo());
	        queryParameters.put("user_id", UUID.fromString(UpdateUserContactRequest.getRequest().getUserId()));

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("update_user_contact")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("contact", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("user_id", Types.OTHER));
	        
	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Exist]")) {
				return "This Phone Number Already Exists";
			}
			else if(response.equalsIgnoreCase("[Updated]")) {
				return "Phone Number Updated Successfully";
			}
			else if(response.equalsIgnoreCase("[Not Updated]")) {
				return "Phone Number Not Updated";
			}
			else {
				return "Unexpected Response";
			}
	    }catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public String updateUserEmailId(UpdateUserEmailIdRequest updateUserEmailIdRequest) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("email_id", updateUserEmailIdRequest.getRequest().getEmailID());
	        queryParameters.put("user_id", UUID.fromString(updateUserEmailIdRequest.getRequest().getUserId()));

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("update_user_email_id")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("email_id", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("user_id", Types.OTHER));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Exist]")) {
				return "This Email Already Exists";
			}
			else if(response.equalsIgnoreCase("[Updated]")) {
				return "Email Updated Successfully";
			}
			else if(response.equalsIgnoreCase("[Not Updated]")) {
				return "Email Not Updated";
			}
			else {
				return "Unexpected Response";
			}
	    }catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	//This Api is to check email & contact exists for any user before adding new user
	@Override
	public String checkEmailContactExistOrNot(AddUserDataBORequest addUserDataBORequest) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("email_id", addUserDataBORequest.getRequestData().getEmailId());
	        queryParameters.put("contact", addUserDataBORequest.getRequestData().getContactNo());

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("check_email_contact_exist_or_not")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("email_id", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("contact", Types.VARCHAR));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Add User]")) {
				return "Add User";
			}
			else if(response.equalsIgnoreCase("[Contact Exist]")) {
				return "Contact Exist";
			}
			else if(response.equalsIgnoreCase("[Email Exist]")) {
				return "Email Exist";
			}
			else if(response.equalsIgnoreCase("[Email And Contact Exist]")) {
				return "Email And Contact Exist";
			}
			else {
				return "Unexpected Response";
			}
	    }catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String checkCartStockAvailability(String customerId) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("customer_id",UUID.fromString(customerId));

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("check_cart_stock_availability")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("customer_id", Types.OTHER));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Stock Available]")) {
				return "Stock Available";
			}
			else if(response.equalsIgnoreCase("[Stock Not Available]")) {
				return "Stock Not Available";
			}
			else if(response.equalsIgnoreCase("[No Data Available]")) {
				return "No Data Available";
			}
			else {
				return "Unexpected Response";
			}
	    }catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	//This Api is to check email & contact exists for other user or not
	@Override
	public String checkEmailContactExistOrNotForUpdate(AddUserDataBORequest addUserDataBORequest) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("email_id", addUserDataBORequest.getRequestData().getEmailId());
	        queryParameters.put("contact", addUserDataBORequest.getRequestData().getContactNo());
	        queryParameters.put("user_id", UUID.fromString(addUserDataBORequest.getRequestData().getUserId()));

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("check_email_contact_exist_or_not_for_update")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("email_id", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("contact", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("user_id", Types.OTHER));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Update User]")) {
				return "Update User";
			}
			else if(response.equalsIgnoreCase("[Contact Exist]")) {
				return "Contact Exist";
			}
			else if(response.equalsIgnoreCase("[Email Exist]")) {
				return "Email Exist";
			}
			else if(response.equalsIgnoreCase("[Email And Contact Exist]")) {
				return "Email And Contact Exist";
			}
			else {
				return "Unexpected Response";
			}
	    }catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<Map<String, Object>> checkDistributorRegistrationSteps(String distributorContactNo)
	{
		try {
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("distributor_contact_no", distributorContactNo);
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("check_distributor_registration_steps")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_contact_no", Types.VARCHAR));
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			
			return outputResult;
		 }catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public UserAccountsResponse getSubAdminByStatus(String accountStatus) {
		UserAccountsResponse userAccountsResponse = new UserAccountsResponse();
		try {
			List<UserAccounts> userList = useraccountsDAO.getSubAdminByStatus(accountStatus);
			userAccountsResponse.setUserAccounts(userList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userAccountsResponse;
	}

	@Override
	public String addSubAdminValidity(AddUpdateSubadminValidity AddUpdateSubadminValidity) {
		String subAdmindetails = null;
		try {
			subAdmindetails = subadminValidityDAO.addSubAdminValidity(AddUpdateSubadminValidity);
		}catch(Exception e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while inserting data into DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
		return subAdmindetails;
	}

	@Override
	public List<SubadminDetailsModel> getApprovedSubAdmins() {
	    List<SubadminDetailsModel> subadminDetailsModel = new ArrayList<>();

	    try {
	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withFunctionName("get_approved_subadmins")
	            .withSchemaName("public")
	            .withoutProcedureColumnMetaDataAccess()
	            .declareParameters(new SqlOutParameter("return", Types.OTHER));
	        
	        Map<String, Object> result = simpleJdbcCall.execute(new HashMap<>());

	        // Extract result from result-set-1
	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return subadminDetailsModel;
	        }

	        String jsonResponse = resultSet.get(0).get("result").toString();
	        System.out.println("Parsed JSON Response: " + jsonResponse);

	        // Parse JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);
	        
	        JsonNode subAdminDetailsNode = rootNode.get("subadmins");
	        if (subAdminDetailsNode != null && subAdminDetailsNode.isArray()) {
	            for (JsonNode subAdminNode : subAdminDetailsNode) {
	                // Convert each JsonNode to Map<String, Object>
	                Map<String, Object> subAdminMap = objectMapper.convertValue(subAdminNode, new TypeReference<Map<String, Object>>() {});
	                
	                // Use your custom method to convert to ProductDetails
	                subadminDetailsModel.add(convertApprovedSubAdminProductDetailsFromProcedureToDomain(subAdminMap));
	            }
	        }
	        return subadminDetailsModel;
	    } catch (BusinessException e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Exception e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException("Unexpected error", e);
	    }
	}

	private SubadminDetailsModel convertApprovedSubAdminProductDetailsFromProcedureToDomain(Map<String, Object> outputResult) {
		SubadminDetailsModel subadminDetailsModel = new SubadminDetailsModel();
		try {
			subadminDetailsModel.setAccountId(outputResult.get("account_id")==null?"":outputResult.get("account_id").toString());
			subadminDetailsModel.setUsername((outputResult.get("user_name")==null?"":outputResult.get("user_name").toString()));
			subadminDetailsModel.setPassword(outputResult.get("password")==null?"":outputResult.get("password").toString());
			subadminDetailsModel.setFirstName(outputResult.get("first_name")==null?"":outputResult.get("first_name").toString());
			subadminDetailsModel.setLastName(outputResult.get("last_name")==null?"":outputResult.get("last_name").toString());
			subadminDetailsModel.setUserRole(outputResult.get("user_role")==null?"":outputResult.get("user_role").toString());
			subadminDetailsModel.setAccountType(outputResult.get("account_type")==null?"":outputResult.get("acount_type").toString());
			subadminDetailsModel.setEmail(outputResult.get("email")==null?"":outputResult.get("email").toString());
			subadminDetailsModel.setPhone(outputResult.get("phone")==null?"":outputResult.get("phone").toString());
			subadminDetailsModel.setDateCreated(outputResult.get("date_created")==null?"":outputResult.get("date_created").toString());
			subadminDetailsModel.setDateModified(outputResult.get("date_modified")==null?"":outputResult.get("date_modified").toString());
			subadminDetailsModel.setUserCreated(outputResult.get("user_created")==null?"":outputResult.get("user_created").toString());
			subadminDetailsModel.setUserModified(outputResult.get("user_modified")==null?"":outputResult.get("user_modified").toString());
			subadminDetailsModel.setExportedFileName(outputResult.get("exported_file_name")==null?"":outputResult.get("exported_file_name").toString());
			subadminDetailsModel.setExportedTime(outputResult.get("exported_time")==null?"":outputResult.get("exported_time").toString());
			subadminDetailsModel.setStatusOfExport(outputResult.get("status_of_export")==null?"":outputResult.get("status_of_export").toString());
			subadminDetailsModel.setCreateduser(outputResult.get("created_user")==null?"":outputResult.get("created_user").toString());
			subadminDetailsModel.setOtsValidityId(outputResult.get("ots_validity_id")==null?"":outputResult.get("ots_validity_id").toString());
			subadminDetailsModel.setOtsAccountId(outputResult.get("ots_account_id")==null?"":outputResult.get("ots_account_id").toString());
			subadminDetailsModel.setOtsValidityStart(outputResult.get("ots_validity_start")==null?"":outputResult.get("ots_validity_start").toString());
			subadminDetailsModel.setOtsValidityEnd(outputResult.get("ots_validity_end")==null?"":outputResult.get("ots_validity_end").toString());
			subadminDetailsModel.setOtsTransactionCharges(outputResult.get("ots_transaction_charges")==null?"":outputResult.get("ots_transaction_charges").toString());
			subadminDetailsModel.setOtsDistributorCount(outputResult.get("ots_distributor_count")==null?"":outputResult.get("ots_distributor_count").toString());
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return subadminDetailsModel;
		
	}
	
	@Override
	public String updateSubAdminValidity(AddUpdateSubadminValidity addUpdateSubadminValidity){
		String subAdmindetails = null;
		try {
			subAdmindetails =subadminValidityDAO.updateSubAdminValidity(addUpdateSubadminValidity);
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return subAdmindetails;
	}
	
	@Override
	public SubadminValidity getSubAdminValidity(String subAdminId) {
		try {
			SubadminValidity subadminValidity = subadminValidityDAO.getSubAdminValidity(subAdminId);
			return subadminValidity;
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String distributorActiveInactive(DistributorActiveInactiveRequest distributorActiveInactiveRequest) {
		//The ExecutorService is used to send emails asynchronously, reducing the main thread's wait time.
		//Increase ThreadPool size to increase execution time if load increases
		ExecutorService executor = Executors.newFixedThreadPool(3);
		try {
			//To fetch Distributor Details
			UserDetails distributorDetails = userServiceDAO.getUserDetails(distributorActiveInactiveRequest.getRequest().getDistributorId());
			
			Map<String, Object> queryParameters = new HashMap<>();
			queryParameters.put("distributor_id",UUID.fromString(distributorActiveInactiveRequest.getRequest().getDistributorId()));
			queryParameters.put("distributor_status", distributorActiveInactiveRequest.getRequest().getDistributorStatus());

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("distributor_active_inactive")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

			simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_status", Types.VARCHAR));

			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);

			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");

			// converting output of procedure to String
			String response = outputResult.get(0).values().toString();
			System.out.println("response = " + response);

			// comparing response of procedure & handling response
			if (response.equalsIgnoreCase("[Not Updated]")) {
				return "Not Updated";
			} else if (response.equalsIgnoreCase("[Inactivated]")) {

				// Creating message content
				String distMsg = "<p>Dear "+distributorDetails.getFirstName()+" "+distributorDetails.getLastName()+",<br><br>" + 
					"Your account has been <b>inactivated by Admin</b>. As a result, all your listed products are now set to inactive.<br><br>" +
			        "Please note:<br>" +
			        "<ul>" +
			        "<li>If you have any <b>pending orders</b>, please continue with the delivery process.</li>" +
			        "<li>You will still have access to <b>order-related features only</b>.</li>" +
			        "<li>If you have already completed all your order processes, kindly ignore this email.</li>" +
			        "</ul><br>" +
			        "Thank you for your cooperation.<br><br>"+
			        "Thanks and Regards,<br>" +
			        companyName + " Support Team</p>";
				
				//Sending distributor mail for Account Inactivated by Admin 
				executor.submit(() -> emailUtil.sendDistributermail(distributorDetails.getEmailId(), "", "Your Seller Account Has Been Inactivated", distMsg));
	
				return "Inactivated";
			}
			else if (response.equalsIgnoreCase("[Activated]")) {
				// Creating message content
				String distMsg = "<p>Dear "+distributorDetails.getFirstName()+" "+distributorDetails.getLastName()+",<br><br>" + 
				        "Your account has been <b>activated by Admin</b>. As a result, all your previously inactive products are now <b>active</b>.<br><br>" +
				        "You can now login to your account and proceed with your business activities.<br><br>" +
				        "Thanks and Regards,<br>" +
				        companyName + " Support Team</p>";
				
				//Sending distributor mail for Account Activated by Admin 
				executor.submit(() -> emailUtil.sendDistributermail(distributorDetails.getEmailId(), "", "Your Seller Account Has Been Activated", distMsg));
				
				return "Activated";
			}
			else {
				return "Unexpected Response";
			}
		} catch (BusinessException e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override 
	public ProductLocationResponse clearAndAddDistributorLocationMapping(AddProductLocationMappingRequest addProductLocationMappingRequest) {
		ProductLocationResponse productLocationResponse = new ProductLocationResponse();
		try {
			try {
				Map<String, Object> queryParameter = new HashMap<String, Object>();
				queryParameter.put("distribitor_id",UUID.fromString(addProductLocationMappingRequest.getRequest().getDistributorId()));
				SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
		        		.withFunctionName("delete_locations_for_distributor")
		        		.withSchemaName("public")
		                .withoutProcedureColumnMetaDataAccess();
			
				simpleJdbcCall.addDeclaredParameter(new SqlParameter("distribitor_id", Types.OTHER)); 
			    Map<String, Object> result = simpleJdbcCall.execute(queryParameter);
			} catch (Exception e) {
				return null;
			} catch (Throwable e) {
				return null;
			}
			List<ProductLocationMapping>ProductLocationMappings = productLocationMappingDAO.addProductLocationMappings(addProductLocationMappingRequest);
			productLocationResponse.setProductLocation(ProductLocationMappings);
		}catch(Exception e){
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return productLocationResponse;
	}
	
	@Override
	public GetServiceableLocationResponse getServiceableLocationByDistributorOnly(DistributorAndProductRequest distributorAndProductRequest) {
	    List<ServiceState> stateList = new ArrayList<>();
	    List<ServiceDistrict> serviceDistrictList = new ArrayList<>();
	    List<ServicePincode> allServicePincodeList = new ArrayList<>();

	    try {
	        String distributorId = distributorAndProductRequest.getRequest().getDistributorId();
	        String productId = distributorAndProductRequest.getRequest().getProductId();

	        SimpleJdbcCall simpleJdbcCall;
	        Map<String, Object> procedureParams = new HashMap<>();

	        // Call procedure for distributor
	        if (distributorId != null) {
	            procedureParams.put("distributor_id", UUID.fromString(distributorId));
	            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                    .withFunctionName("get_serviceable_location_by_distributor")
	                    .withSchemaName("public")
	                    .withoutProcedureColumnMetaDataAccess();
	            simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));

	        } else if (productId != null) {
	            // Call procedure for product
	            procedureParams.put("product_id",productId);
	            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                    .withFunctionName("get_serviceable_location_by_product")
	                    .withSchemaName("public")
	                    .withoutProcedureColumnMetaDataAccess();
	            simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.VARCHAR));

	        } else {
	            throw new BusinessException("Invalid request: Both distributorId and productId are missing.");
	        }

	        // Execute procedure
	        Map<String, Object> resultMap = simpleJdbcCall.execute(procedureParams);
	        System.out.println("result = " + resultMap);

	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) resultMap.get("#result-set-1");
	        if (resultSet != null && !resultSet.isEmpty()) {
	            Object jsonResult = resultSet.get(0).get("result");

	            if (jsonResult instanceof PGobject) {
	                String jsonString = ((PGobject) jsonResult).getValue();

	                // Parse the JSON using Jackson
	                ObjectMapper objectMapper = new ObjectMapper();
	                JsonNode root = objectMapper.readTree(jsonString);

	                JsonNode states = root.path("states");
	                JsonNode districts = root.path("districts");
	                JsonNode pincodes = root.path("pincodes");

	                if (states.isArray()) {
	                    for (JsonNode stateNode : states) {
	                        ServiceState state = convertStateDetailsFromJson(stateNode);
	                        stateList.add(state);
	                    }
	                }

	                if (districts.isArray()) {
	                    for (JsonNode districtNode : districts) {
	                        ServiceDistrict district = convertDistrictDetailsFromJson(districtNode);
	                        serviceDistrictList.add(district);
	                    }
	                }

	                if (pincodes.isArray()) {
	                    for (JsonNode pincodeNode : pincodes) {
	                        ServicePincode pincode = convertPincodeDetailsFromJson(pincodeNode);
	                        allServicePincodeList.add(pincode);
	                    }
	                }
	            }
	        }

	        // Set data into the response object
	        GetServiceableLocationResponse getServiceableLocationResponse = new GetServiceableLocationResponse();
	        GetServiceableLocation getServiceableLocation = new GetServiceableLocation();
	        getServiceableLocation.setStatelist(stateList);
	        getServiceableLocation.setDistrictList(serviceDistrictList);
	        getServiceableLocation.setPincodeList(allServicePincodeList);
	        getServiceableLocationResponse.setServiceableLocation(getServiceableLocation);

	        return getServiceableLocationResponse;

	    } catch (BusinessException e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Throwable e) {
	        logger.error("Unexpected error: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    }
	}

	// Convert state details
	private ServiceState convertStateDetailsFromJson(JsonNode jsonNode) {
	    ServiceState state = new ServiceState();
	    state.setOtsStateId(jsonNode.path("ots_state_id").asText());
	    state.setOtsStateName(jsonNode.path("ots_state_name").asText());
	    return state;
	}

	// Convert district details
	private ServiceDistrict convertDistrictDetailsFromJson(JsonNode jsonNode) {
	    ServiceDistrict district = new ServiceDistrict();
	    district.setOtsStateId(jsonNode.path("ots_state_id").asText());
	    district.setOtsDistrictId(jsonNode.path("ots_district_id").asText());
	    district.setOtsDistrictName(jsonNode.path("ots_district_name").asText());
	    return district;
	}

	// Convert pincode details
	private ServicePincode convertPincodeDetailsFromJson(JsonNode jsonNode) {
	    ServicePincode pincode = new ServicePincode();
	    pincode.setStateId(jsonNode.path("ots_state_id").asText());
	    pincode.setDistrictId(jsonNode.path("ots_district_id").asText());
	    pincode.setDistrictName(jsonNode.path("ots_district_name").asText());
	    pincode.setPincode(jsonNode.path("ots_pincode").asText());
	    pincode.setOfficeName(jsonNode.path("ots_office_name").asText());
	    return pincode;
	}

	@Override
	public String addRegistrationTransactionCancelRecord(AddRegistrationTransactionCancelRecord addRegistrationTransactionCancelRecord){
	    String cancelRecords = null;
		try {
			cancelRecords = registrationTransactionCancelRecordsDAO.addRegistrationTransactionCancelRecords(addRegistrationTransactionCancelRecord);
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return cancelRecords;
	}

	@Override
	public String addHoliday(AddHolidayRequest addHolidayRequest) {
		try {
			String addHoliday = userServiceDAO.addHoliday(addHolidayRequest);
			return addHoliday;
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public GetHolidayListResponse getHolidayListByProviderId(String providerId) {
		try {
			GetHolidayListResponse getHolidayListResponse = userServiceDAO.getHolidayListByProviderId(providerId);
			return getHolidayListResponse;
		} catch (Exception e) {
			logger.error("Exception while Inserting data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public UserDataBOResponse getProviderByCreatedUser(String createdUser) {
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			List<UserDetails> userDetailList= userServiceDAO.getProviderByCreatedUser(createdUser);
			userDataBOResponse.setUserDetails(userDetailList);
		}catch (Exception e) {
			logger.error("Exception while Inserting data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}
	
	@Override
	public UserDataBOResponse getActiveProviderList() {
		try {
			UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
			List<UserDetails> userDetailList = userServiceDAO.getActiveProviderList();
			userDataBOResponse.setUserDetails(userDetailList);
			return userDataBOResponse;
		} catch (BusinessException e) {
			logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override 
	public String addDistributorCountryMapping(AddDistributorCountryMappingRequest addDistributorCountryMappingRequest) {
		try {
			String addDistributorCountryMapping = distributorCountryMappingDAO.addDistributorCountryMapping(addDistributorCountryMappingRequest);
			
			return addDistributorCountryMapping;
		}catch(Exception e){
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public DistributorCountryResponse getCountriesMappedToDistributor(String distributorId) {
		DistributorCountryResponse distributorCountryResponse = new DistributorCountryResponse();
		try {
			List<DistributorCountryMapping> distributorCountryMap = distributorCountryMappingDAO.getCountriesMappedToDistributor(distributorId);
			distributorCountryResponse.setDistributorCountry(distributorCountryMap);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return distributorCountryResponse;
	}
	
	@Override 
	public String clearAndAddDistributorCountryMapping(AddDistributorCountryMappingRequest addDistributorCountryMappingRequest) {
		try {
			//To Delete Countries Mapped to Distributor
			String deleteDistributorCountryMapping = distributorCountryMappingDAO.deleteDistributorCountryMappingByDistributorId(addDistributorCountryMappingRequest.getRequest().getDistributorId());
			System.out.println("deleteDistributorCountryMapping = "+deleteDistributorCountryMapping);
			
			if(deleteDistributorCountryMapping.equalsIgnoreCase("Updated Successfully")) {
				//To Add New Countries to Distributor
				String addDistributorCountryMapping = distributorCountryMappingDAO.addDistributorCountryMapping(addDistributorCountryMappingRequest);	
			}
			return deleteDistributorCountryMapping;
		}catch(Exception e){
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public ServiceCountryResponse getAllCountry() {
		ServiceCountryResponse serviceCountryResponse = new ServiceCountryResponse();
		try {
			List<ServiceCountry> countryList = serviceCountryDAO.getAllCountry();
			serviceCountryResponse.setCountryDetails(countryList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceCountryResponse;
	}
	
	@Override
	public UserDetailsPageloaderResponse getPageloaderDistributorsWithActiveProducts() {
		UserDetailsPageloaderResponse userDetailsResponse = new UserDetailsPageloaderResponse();
		try {
			Map<String, List<UserDetails>> userDetailList = userServiceDAO.getPageloaderDistributorsWithActiveProducts();
			userDetailsResponse.setUserDetails(userDetailList);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userDetailsResponse;
	}
	
	@Override
	public GetDistributorCompleteDetails getDistributorCompleteDetails(String distributorId) {
		GetDistributorCompleteDetails response = new GetDistributorCompleteDetails();
		try {
			//To fetch Distributor Details
			UserDetails distributorDetails = userServiceDAO.getDistributorDetails(distributorId);
			if(distributorDetails == null) {
				return null;
			}else {
				response.setDistributorDetails(distributorDetails);
			}
			
			//To fetch Distributor Company Details
			List<DistributorCompanyDetails> companyDetailsList = distributorCompanyDetailsDAO.getDistributorCompanyDetails(distributorId);
			response.setDistributorCompanyDetails(companyDetailsList);
			
			//To fetch Distributor Payment Details
			List<DistributorPaymentDetails> distributorPaymentDetails = distributorPaymentDetailsDAO.getDistributorPaymentDetails(distributorId);
			response.setDistributorPaymentDetails(distributorPaymentDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return response;
	}
	
	@Override
	public UserDataBOResponse getUserDetailsByRoleAndStatus(GetUserStatusRequest getUserStatusRequest) {
		UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
		try {
			List<UserDetails> userDetailList = userServiceDAO.getUserDetailsByRoleAndStatus((getUserStatusRequest));
			userDataBOResponse.setUserDetails(userDetailList);
		} catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return userDataBOResponse;
	}
	
	@Override
	public ForgotPasswordResponse sendOTPToAdmin(String emailId) {
		ForgotPasswordResponse forgotPasswordResponse = new ForgotPasswordResponse();
		try {
			SecureRandom secureRandom = new SecureRandom();
			int otp = 1000 +secureRandom.nextInt(9000);
			System.out.println(otp);
//			Random rand = new Random(); 
//			int otp = 1000 +rand.nextInt(9000);
			UserAccounts userAccounts = useraccountsDAO.getUseraccountDetailByEmail(emailId);
			if(userAccounts!=null) {
				String adminMsg = "<p>Hi "+userAccounts.getFisrtName()+" "+userAccounts.getLastName()+"<br><br>" + 
                        "Your one-time password (OTP) for accessing "+companyName+" is: "+ otp+".<br>" +
                        "Please do not share this OTP with anyone to keep your account secure<br><br>" +
                        "Thanks And Regards,<br>" + 
                        companyName+" Support Team </p>";
				
				emailUtil.sendOTP(userAccounts.getEmail(), "", companyName+" OTP Verification", adminMsg);
				
				forgotPasswordResponse.setOtp(String.valueOf(otp));
				forgotPasswordResponse.setUserId(userAccounts.getAccountId());

				return  forgotPasswordResponse;
			}else {
				return null;
			}
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage(), e);
    	}
	}

}
