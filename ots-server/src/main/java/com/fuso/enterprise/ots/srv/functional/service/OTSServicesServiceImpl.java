package com.fuso.enterprise.ots.srv.functional.service;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuso.enterprise.ots.srv.api.model.domain.AverageServiceReviewRatingModel;
import com.fuso.enterprise.ots.srv.api.model.domain.GetServiceableServiceLocation;
import com.fuso.enterprise.ots.srv.api.model.domain.GetSlot;
import com.fuso.enterprise.ots.srv.api.model.domain.InsertServiceOrder;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDetailsData;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDistrict;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceLocationMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.ServicePincode;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceState;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSServicesService;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateServiceRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddServiceLocationMappingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddServiceReviewAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceCategorySubCategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceReviewsAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceSlotRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServicesByProviderAndStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServicesByProviderPaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServicesBySubCategoryAndProviderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ProviderAndServiceRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateServiceStatusRequestModel;
import com.fuso.enterprise.ots.srv.api.service.response.AverageServiceReviewRatingResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceReviewAndRatingResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceableServiceLocationResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceDetailsBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceDetailsResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceLocationResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServiceLocationMappingDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServiceReviewAndRatingDAO;

@Service
public class OTSServicesServiceImpl implements OTSServicesService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ServiceDAO serviceDao;
    private final ServiceReviewAndRatingDAO serviceReviewRatingDao;
    private final ServiceLocationMappingDAO  serviceLocationMappingDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public OTSServicesServiceImpl(ServiceDAO serviceDao, ServiceReviewAndRatingDAO serviceReviewRatingDao, ServiceLocationMappingDAO  serviceLocationMappingDAO) {
        this.serviceDao = serviceDao;
        this.serviceReviewRatingDao = serviceReviewRatingDao;
        this.serviceLocationMappingDAO=serviceLocationMappingDAO;
    }
	
	@Override
	@Transactional
	public String addOrUpdateService(AddOrUpdateServiceRequest addOrUpdateServiceRequest) {
		try {
			String response=null;
			if(addOrUpdateServiceRequest.getRequestData().getServiceDetails() != null) {
				if(addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceName() == null || addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceName().equalsIgnoreCase("")
					|| addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceLevelId() == null || addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceLevelId().equalsIgnoreCase("")
					|| addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceDescription() == null || addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceDescription().equalsIgnoreCase("")
					|| addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceDescriptionLong()  == null || addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceDescriptionLong().equalsIgnoreCase("")
					|| addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceStatus() == null || addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceStatus().equalsIgnoreCase("")
					|| addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceProviderId() == null || addOrUpdateServiceRequest.getRequestData().getServiceDetails().getOtsServiceLevelId().equalsIgnoreCase("")
					|| addOrUpdateServiceRequest.getRequestData().getServiceDetails().getCreatedUser() == null || addOrUpdateServiceRequest.getRequestData().getServiceDetails().getCreatedUser().equalsIgnoreCase("")) {
					
					return "Please Enter Required Inputs";
				}
				
				response=serviceDao.addOrUpdateServiceDetails(addOrUpdateServiceRequest.getRequestData().getServiceDetails());
			}
			if(addOrUpdateServiceRequest.getRequestData().getServicePriceDetails() != null) {
				if(addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceId() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceId().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceStatus() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceStatus().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceBasePrice() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceBasePrice().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceDiscountPercentage() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceDiscountPercentage().equalsIgnoreCase("")				
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceDiscountPrice() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceDiscountPrice().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceGstPrice() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceGstPrice().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceStrikenPrice() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceStrikenPrice().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceFinalPriceWithoutGst() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceFinalPriceWithoutGst().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceGstPercentage() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceGstPercentage().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceGstPrice() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceGstPrice().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceFinalPriceWithGst() == null || addOrUpdateServiceRequest.getRequestData().getServicePriceDetails().getOtsServiceFinalPriceWithGst().equalsIgnoreCase("")) {
					
					
					return "Please Enter Required Inputs";
				}
				
				response=serviceDao.addOrUpdateServicePricingDetails(addOrUpdateServiceRequest.getRequestData().getServicePriceDetails());
			}
			if(addOrUpdateServiceRequest.getRequestData().getServiceAvailability() !=null) {
				if(addOrUpdateServiceRequest.getRequestData().getServiceAvailability().getOtsServiceId() == null || addOrUpdateServiceRequest.getRequestData().getServiceAvailability().getOtsServiceId().equalsIgnoreCase("")
					|| addOrUpdateServiceRequest.getRequestData().getServiceAvailability().getOtsServiceStatus() == null || addOrUpdateServiceRequest.getRequestData().getServiceAvailability().getOtsServiceStatus().equalsIgnoreCase("")
					|| addOrUpdateServiceRequest.getRequestData().getServiceAvailability().getOtsServiceDuration() == null || addOrUpdateServiceRequest.getRequestData().getServiceAvailability().getOtsServiceDuration().equalsIgnoreCase("")
					|| addOrUpdateServiceRequest.getRequestData().getServiceAvailability().getOtsServiceMode() == null || addOrUpdateServiceRequest.getRequestData().getServiceAvailability().getOtsServiceMode().equalsIgnoreCase(""))
				{
					return "Please Enter Required Inputs";
				}
				response=serviceDao.addOrUpdateServiceAvailability(addOrUpdateServiceRequest.getRequestData().getServiceAvailability());
				//Predefined user status
				String[] VALID_STATUSES = {"Online", "Customer-Place","Provider-Place"};

			    // Validate input user status
			    String userStatus = addOrUpdateServiceRequest.getRequestData().getServiceAvailability().getOtsServiceMode();
			    boolean isValidStatus = Arrays.stream(VALID_STATUSES)
			                                  .anyMatch(status -> status.equalsIgnoreCase(userStatus));

			    //If input status not matching predefined status
			    if (!isValidStatus) {
			        return "Invalid Service Mode";
			    }
			    
			}
			if(addOrUpdateServiceRequest.getRequestData().getServicePolicy() !=null) {
				if(addOrUpdateServiceRequest.getRequestData().getServicePolicy().getOtsServiceId() == null || addOrUpdateServiceRequest.getRequestData().getServicePolicy().getOtsServiceId().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePolicy().getOtsServiceStatus() == null || addOrUpdateServiceRequest.getRequestData().getServicePolicy().getOtsServiceStatus().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServicePolicy().getOtsServiceCancellationAvailability() == null 
						|| addOrUpdateServiceRequest.getRequestData().getServicePolicy().getOtsServiceRescheduleAvailability() == null )
				{
					return "Please Enter Required Inputs";
				}
				response=serviceDao.addOrUpdateServicePolicy(addOrUpdateServiceRequest.getRequestData().getServicePolicy());
			}
			if(addOrUpdateServiceRequest.getRequestData().getServiceSlot() != null) {
				if(addOrUpdateServiceRequest.getRequestData().getServiceSlot().getOtsServiceId() == null || addOrUpdateServiceRequest.getRequestData().getServiceSlot().getOtsServiceId().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServiceSlot().getOtsServiceStatus() == null || addOrUpdateServiceRequest.getRequestData().getServiceSlot().getOtsServiceStatus().equalsIgnoreCase("")
						|| addOrUpdateServiceRequest.getRequestData().getServiceSlot().getOtsServiceSlot() == null ) {
				return	"Please Enter Required Inputs";
				}
				
				response=serviceDao.addServiceSlot(addOrUpdateServiceRequest.getRequestData().getServiceSlot());
			}
				return response;
			} catch(Exception e){
				logger.error("Exception while fetching data from DB  :"+e.getMessage());
				e.printStackTrace();
				throw new BusinessException(e.getMessage(), e);
			} catch (Throwable e) {
				logger.error("Exception while fetching data from DB  :"+e.getMessage());
				e.printStackTrace();
				throw new BusinessException(e.getMessage(), e);
			}
		
	}
	@Override
	public ServiceDetailsData getServiceDetailsByServiceId(String serviceId) {
	    try { 
	    	ServiceDetailsData  serviceDetails = serviceDao.getServiceDetailsByServiceId(serviceId);
	    	  return serviceDetails;
	    } catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	  
	}

	@Override
	public ServiceDetailsResponse getCategoryAndSubCategory(GetServiceCategorySubCategoryRequest getServiceCategorySubCategoryRequest) {
	    try { 
			ServiceDetailsResponse serviceDetailsResponse = new ServiceDetailsResponse();
	    	List<ServiceDetailsData>  serviceDetails = serviceDao.getCategoryAndSubCategory(getServiceCategorySubCategoryRequest);
	    	serviceDetailsResponse.setServiceDetails(serviceDetails);
	    	return serviceDetailsResponse;
	    } catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	   
	}

	@Override
	public GetSlot getSlotsByServiceId(String serviceId) {
		try {
			GetSlot getSlot = serviceDao.getAllSlotsByServiceId(serviceId);

			return getSlot;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public Object getAvailableServiceSlots(GetServiceSlotRequest getServiceSlotRequest) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("service_id", UUID.fromString(getServiceSlotRequest.getRequestData().getServiceId()));
	        queryParameters.put("slot_day", getServiceSlotRequest.getRequestData().getDay());
	        queryParameters.put("slot_date", getServiceSlotRequest.getRequestData().getDate());

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_available_service_slots")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_id", Types.OTHER));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("slot_day", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("slot_date", Types.VARCHAR));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
	        List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");

	        if (outputResult == null || outputResult.isEmpty()) {
	            return null;
	        }

	        // Get the JSON string from the first record
	        String response = outputResult.get(0).values().iterator().next().toString();

	        if (StringUtils.isNotBlank(response)) {
	            ObjectMapper objectMapper = new ObjectMapper();
	            return objectMapper.readValue(response, Object.class);
	        } else {
	            return null;
	        }
	    } catch (BusinessException e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Throwable e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    }
	}
	
	@Override
	public String checkSlotAvailability(InsertServiceOrder insertServiceOrder) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("service_id", UUID.fromString(insertServiceOrder.getOtsServiceId()));
	        queryParameters.put("service_day_of_week", insertServiceOrder.getOtsServiceDayOfWeek());
	        queryParameters.put("service_start_time", insertServiceOrder.getOtsServiceStartTime());
	        queryParameters.put("service_end_time", insertServiceOrder.getOtsServiceEndTime());
	        queryParameters.put("service_booked_date", insertServiceOrder.getOtsServiceBookedDate());

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("check_slot_availability")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_id", Types.OTHER));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_day_of_week", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_start_time", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_end_time", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_booked_date", Types.VARCHAR));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Slot_Not_Configured]")) {
				return "Slot_Not_Configured";
			}
			else if(response.equalsIgnoreCase("[Slot_Available]")) {
				return "Slot_Available";
			}
			else if(response.equalsIgnoreCase("[Slot_Unavailable]")) {
				return "Slot_Unavailable";
			}
			else {
				return "Unexpected Response";
			}
	    } catch (BusinessException e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Throwable e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    }
	}

	@Override
	public ServiceDetailsResponse getAllServices() {
		try {
			ServiceDetailsResponse serviceDetailsResponse = new ServiceDetailsResponse();
			List<ServiceDetailsData> serviceDetails = serviceDao.getAllServices();
			serviceDetailsResponse.setServiceDetails(serviceDetails);
			return serviceDetailsResponse;
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

	public ServiceDetailsResponse getAllServicesByProviderId(String providerId) {
		try {
			ServiceDetailsResponse serviceDetailsResponse = new ServiceDetailsResponse();
			List<ServiceDetailsData> serviceDetails = serviceDao.getAllServicesByProviderId(providerId);
			serviceDetailsResponse.setServiceDetails(serviceDetails);
			return serviceDetailsResponse;
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
	public ServiceDetailsResponse getAllCategoryAndSubCategory(String serviceLevelId) {
		try {
			ServiceDetailsResponse serviceDetailsResponse = new ServiceDetailsResponse();
			List<ServiceDetailsData> serviceDetails = serviceDao.getAllCategoryAndSubCategory(serviceLevelId);	
			serviceDetailsResponse.setServiceDetails(serviceDetails);
			return serviceDetailsResponse;
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
	public String updateServiceStatus(UpdateServiceStatusRequestModel updateServiceStatusRequestModel ) {
		try {
			String data = serviceDao.updateServiceStatus(updateServiceStatusRequestModel);
			return data;
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
	public ServiceDetailsResponse getServicesByProviderAndStatus(GetServicesByProviderAndStatusRequest getServicesByProviderAndStatusRequest) {
		try {
			ServiceDetailsResponse serviceDetailsResponse = new ServiceDetailsResponse();
			List<ServiceDetailsData> serviceDetails = serviceDao.getServicesByProviderAndStatus(getServicesByProviderAndStatusRequest);
			serviceDetailsResponse.setServiceDetails(serviceDetails);
			return serviceDetailsResponse;
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
	public String addServiceRatingAndReview(AddServiceReviewAndRatingRequest reviewAndRatingRequest) {
		try {
			String data = serviceReviewRatingDao.addServiceRatingAndReview(reviewAndRatingRequest);
			return data;
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
	public List<GetServiceReviewAndRatingResponse> getServiceRatingAndReviewDetailsByOrderId(GetServiceReviewsAndRatingRequest getServiceReviewsAndRatingRequest) {
		try {
			List<GetServiceReviewAndRatingResponse> getServiceReviewAndRatingResponses = serviceReviewRatingDao.getServiceRatingAndReviewDetailsByOrderId(getServiceReviewsAndRatingRequest);
			return getServiceReviewAndRatingResponses;
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
	public AverageServiceReviewRatingResponse getAverageRatingOfService(String serviceId) {
	    AverageServiceReviewRatingResponse averageReviewRatingResponse = new AverageServiceReviewRatingResponse();
	    AverageServiceReviewRatingModel averageReviewRatingModel = new AverageServiceReviewRatingModel();
	    try {
	        // Step 1: Count existing reviews
	        List<GetServiceReviewAndRatingResponse> reviewRating = serviceReviewRatingDao.getAverageRatingOfService(serviceId);
	        if (reviewRating.isEmpty()) {
	            return null;
	        }

	        // Step 2: Call the PostgreSQL function that returns a scalar value
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withFunctionName("average_service_rating")
	                .withSchemaName("public")
	                .declareParameters(new SqlParameter("service_id", Types.OTHER)) // UUID = Types.OTHER
	                .withoutProcedureColumnMetaDataAccess();

	        Map<String, Object> paramMap = new HashMap<>();
	        paramMap.put("service_id", UUID.fromString(serviceId));

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
	public ServiceDetailsResponse getServicesBySubCategoryAndProvider(GetServicesBySubCategoryAndProviderRequest getServicesBySubCategoryAndProviderRequest) {
		try {
			ServiceDetailsResponse serviceDetailsResponse = new ServiceDetailsResponse();
			List<ServiceDetailsData> serviceDetails = serviceDao.getServicesBySubCategoryAndProvider(getServicesBySubCategoryAndProviderRequest);
			serviceDetailsResponse.setServiceDetails(serviceDetails);
			return serviceDetailsResponse;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		
	}

	@Override
	public ServiceDetailsResponse getRecentlyAddedServiceList(String levelId) {
		try 
		{
			ServiceDetailsResponse serviceDetailsResponse = new ServiceDetailsResponse();
			List<ServiceDetailsData> serviceDetails = serviceDao.getRecentlyAddedServiceList(levelId);
			serviceDetailsResponse.setServiceDetails(serviceDetails);
			return serviceDetailsResponse;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		
	}

	@Override
	public ServiceDetailsBOResponse getServicesByProviderPagination(GetServicesByProviderPaginationRequest getServicesByProviderPaginationRequest) {
		try {
			ServiceDetailsBOResponse serviceDetailsBOResponse=serviceDao.getServicesByProviderPagination(getServicesByProviderPaginationRequest);
			return serviceDetailsBOResponse;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
	
	}

	@Override
	public ServiceLocationResponse addServiceLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest) {
		ServiceLocationResponse serviceLocationResponse = new ServiceLocationResponse();
		List<ServiceLocationMapping> serviceLocationMapping= new ArrayList<ServiceLocationMapping>();
		try {
			String response=serviceLocationMappingDAO.addServiceLocationMapping(addServiceLocationMappingRequest);
			if(response.equalsIgnoreCase("Inserted")) {
				if(addServiceLocationMappingRequest.getRequest().getServiceId() == null || addServiceLocationMappingRequest.getRequest().getServiceId().equals("")) {
					serviceLocationMapping = serviceLocationMappingDAO.getLocationsMappedToProviderOnly(addServiceLocationMappingRequest.getRequest().getProviderId());
				}else {
					serviceLocationMapping = serviceLocationMappingDAO.getLocationsMappedToProviderAndService(addServiceLocationMappingRequest.getRequest().getProviderId(),addServiceLocationMappingRequest.getRequest().getServiceId());
				}
				serviceLocationResponse.setServiceLocation(serviceLocationMapping);
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
		return serviceLocationResponse;
	}
	
	@Override
	public ServiceLocationResponse clearAndAddServiceLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest) {
		ServiceLocationResponse serviceLocationResponse = new ServiceLocationResponse();
		try {
			try {
				Map<String, Object> inParamMapForDynamicBulkAttribute = new HashMap<String, Object>();
				inParamMapForDynamicBulkAttribute.put("service_id", UUID.fromString(addServiceLocationMappingRequest.getRequest().getServiceId()));

				SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
		        		.withFunctionName("delete_locations_for_service")
		        		.withSchemaName("public")
		                .withoutProcedureColumnMetaDataAccess();

				simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_id", Types.OTHER));
				Map<String, Object> simpleJdbcCallResultForBulkAttribute = simpleJdbcCall.execute(inParamMapForDynamicBulkAttribute);
			} catch (Exception e) {
				return null;
			} catch (Throwable e) {
				return null;
			}
			List<ServiceLocationMapping> serviceLocationMapping = serviceLocationMappingDAO.addServiceLocationMappings(addServiceLocationMappingRequest);
			serviceLocationResponse.setServiceLocation(serviceLocationMapping);
		} catch (Exception e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceLocationResponse;
	}
	
	@Override
	public ServiceLocationResponse clearAndAddProviderLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest) {
		ServiceLocationResponse serviceLocationResponse = new ServiceLocationResponse();
		try {
			try {
				Map<String, Object> inParamMapForDynamicBulkAttribute = new HashMap<String, Object>();
				inParamMapForDynamicBulkAttribute.put("otsProviderId", UUID.fromString(addServiceLocationMappingRequest.getRequest().getServiceId()));

				SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
		        		.withFunctionName("deleteLocationsForProvider")
		        		.withSchemaName("public")
		                .withoutProcedureColumnMetaDataAccess();

				simpleJdbcCall.addDeclaredParameter(new SqlParameter("otsProviderId", Types.OTHER));
				Map<String, Object> simpleJdbcCallResultForBulkAttribute = simpleJdbcCall.execute(inParamMapForDynamicBulkAttribute);
			} catch (Exception e) {
				return null;
			} catch (Throwable e) {
				return null;
			}
			List<ServiceLocationMapping> serviceLocationMapping = serviceLocationMappingDAO.addServiceLocationMappings(addServiceLocationMappingRequest);
			serviceLocationResponse.setServiceLocation(serviceLocationMapping);
		} catch (Exception e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceLocationResponse;
	}
	
	@Override
	public ServiceLocationResponse getLocationsMappedToService(String serviceId) {
		ServiceLocationResponse serviceLocationResponse = new ServiceLocationResponse();
		try {
			List<ServiceLocationMapping> serviceLocationMapping = serviceLocationMappingDAO.getLocationsMappedToService(serviceId);
			serviceLocationResponse.setServiceLocation(serviceLocationMapping);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceLocationResponse;
	}

	@Override
	public ServiceLocationResponse getLocationsMappedToProviderOnly(String providerId) {
		ServiceLocationResponse serviceLocationResponse = new ServiceLocationResponse();
		try {
			List<ServiceLocationMapping> serviceLocationMapping = serviceLocationMappingDAO.getLocationsMappedToProviderOnly(providerId);
			serviceLocationResponse.setServiceLocation(serviceLocationMapping);
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceLocationResponse;
	}
	
	@Override
	public GetServiceableServiceLocationResponse getServiceableLocationByProviderOnly(ProviderAndServiceRequest providerAndServiceRequest) {
	    List<ServiceState> stateList = new ArrayList<ServiceState>();
	    List<ServiceDistrict> serviceDistrictList = new ArrayList<>();
	    List<ServicePincode> allServicePincodeList = new ArrayList<>();
	    try {
			String providerId = providerAndServiceRequest.getRequest().getProviderId();
			String serviceId = providerAndServiceRequest.getRequest().getServiceId();

			SimpleJdbcCall simpleJdbcCall;
			Map<String, Object> procedureParams = new HashMap<>();

			// Call procedure for providerId
			if (providerId != null) {

				procedureParams.put("provider_id", UUID.fromString(providerId));
				simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                    .withFunctionName("get_service_location_mapped_to_provider")
	                    .withSchemaName("public")
	                    .withoutProcedureColumnMetaDataAccess();
				simpleJdbcCall.addDeclaredParameter(new SqlParameter("provider_id", Types.OTHER));

			} else if (serviceId != null) {

				// Call procedure for serviceId
				procedureParams.put("service_id", UUID.fromString(serviceId));
				simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                    .withFunctionName("get_service_location_mapped_to_service")
	                    .withSchemaName("public")
	                    .withoutProcedureColumnMetaDataAccess();
				simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_id", Types.OTHER));

			} else {
				throw new BusinessException("Invalid request: Both ProviderId and ServiceId are missing.");
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

	        // Setting data into the response
            GetServiceableServiceLocationResponse getServiceableLocationResponse = new GetServiceableServiceLocationResponse();
    	    GetServiceableServiceLocation getServiceableLocation = new GetServiceableServiceLocation();
	        getServiceableLocation.setStatelist(stateList);
	        getServiceableLocation.setDistrictList(serviceDistrictList);
	        getServiceableLocation.setPincodeList(allServicePincodeList);

	        getServiceableLocationResponse.setServiceableLocation(getServiceableLocation);
	        
	        return getServiceableLocationResponse;
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
	
}
