package com.fuso.enterprise.ots.srv.rest.ws.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.fuso.enterprise.ots.srv.api.model.domain.GetSlot;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDetailsData;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceLocationIDName;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSServicesService;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSUserService;
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
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceableLocationResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetServiceableServiceLocationResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceDetailsBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceDetailsResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceLocationResponse;
import com.fuso.enterprise.ots.srv.api.service.response.UserDataBOResponse;
import com.fuso.enterprise.ots.srv.server.util.ResponseWrapper;

@Configuration
public class OTSService_WsImpl implements OTSService_Ws {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	ResponseWrapper responseWrapper;

	@Inject
	OTSServicesService otsService;
	
	@Inject
	private OTSUserService otsUserService;
	

	public Response buildResponse(Object data, String description) {
		ResponseWrapper wrapper = new ResponseWrapper(200, description, data);
		return Response.ok(wrapper).build();
	}

	public Response buildResponse(int code, String description) {
		ResponseWrapper wrapper = new ResponseWrapper(code, description);
		return Response.ok(wrapper).build();
	}

	public Response buildResponse(int code, Object data, String description) {
		ResponseWrapper wrapper = new ResponseWrapper(code, description, data);
		return Response.ok(wrapper).build();
	}

	@Override
	public Response addorUpdateService(AddOrUpdateServiceRequest addUpdateServiceRequest) {
		Response response = null;
		try {
			String addOrUpdateService = otsService.addOrUpdateService(addUpdateServiceRequest);
			System.out.println(addOrUpdateService);
			if (addOrUpdateService == null) {
				response = responseWrapper.buildResponse(404, "Data Not Updated");
			} else if (addOrUpdateService.equalsIgnoreCase("Please Enter Required Inputs")) {
				response = responseWrapper.buildResponse(400, "Please Enter Required Inputs");
			} else if (addOrUpdateService.equalsIgnoreCase("Invalid Service Mode")) {
				response = responseWrapper.buildResponse(400, "Invalid Service Mode");
			} else {
				response = responseWrapper.buildResponse(200, addOrUpdateService, "Successful");
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
	public Response getServiceDetailsByServiceId(String serviceId) {
		Response response = null;
		try {
			ServiceDetailsData serviceDeatils = otsService.getServiceDetailsByServiceId(serviceId);
			if (serviceDeatils == null) {
				response = responseWrapper.buildResponse(404, "No Service Available");
			} else {
				response = responseWrapper.buildResponse(200, serviceDeatils, "Successful");
			}
			return response;
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
	}

	@Override
	public Response getServiceCategoryAndSubCategory(GetServiceCategorySubCategoryRequest getServiceCategorySubCategoryRequest) {
		Response response = null;
		try {
			if(getServiceCategorySubCategoryRequest.getRequest().getSearchKey() == null || getServiceCategorySubCategoryRequest.getRequest().getSearchKey() .equalsIgnoreCase("")
					|| getServiceCategorySubCategoryRequest.getRequest().getSearchValue() == null || getServiceCategorySubCategoryRequest.getRequest().getSearchValue().equalsIgnoreCase("")) {
				
				response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			ServiceDetailsResponse serviceDeatils = otsService.getCategoryAndSubCategory(getServiceCategorySubCategoryRequest);
			if (serviceDeatils.getServiceDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Available");
			} else {
				response = responseWrapper.buildResponse(200, serviceDeatils, "Successful");
			}
			return response;
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
	}

	@Override
	public Response getSlotsDetailsByServiceId(String serviceId) {
		Response response = null;
		try {

			GetSlot getSlot = otsService.getSlotsByServiceId(serviceId);
			if (getSlot == null) {
				response = responseWrapper.buildResponse(404, "No Slot Data");
			}
			response = responseWrapper.buildResponse(200, getSlot, "Successful");

		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getAvailableServiceSlots(GetServiceSlotRequest getServiceSlotRequest) {
		Response response = null;
		try {
			if(getServiceSlotRequest.getRequestData().getDate() == null || getServiceSlotRequest.getRequestData().getDate().equalsIgnoreCase("")
					|| getServiceSlotRequest.getRequestData().getDay() == null || getServiceSlotRequest.getRequestData().getDay().equalsIgnoreCase("")
					|| getServiceSlotRequest.getRequestData().getServiceId() == null || getServiceSlotRequest.getRequestData().getServiceId().equalsIgnoreCase("")) {
				
				response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
				
			Object availableSlot = otsService.getAvailableServiceSlots(getServiceSlotRequest);
			if (availableSlot == null) {
				response = responseWrapper.buildResponse(404, "No Slot Data");
			} else {
				response = responseWrapper.buildResponse(200, availableSlot, "Successful");
			}
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getAllServiceDetails() {
		Response response = null;
		try {
			ServiceDetailsResponse serviceDeatils = otsService.getAllServices();
			if (serviceDeatils.getServiceDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Services Available");
			} else {
				response = responseWrapper.buildResponse(200, serviceDeatils, "Successful");
			}
			return response;
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
	}

	@Override
	public Response getAllServicesByProviderId(String providerId) {
		Response response = null;
		try {
			ServiceDetailsResponse serviceDeatils = otsService.getAllServicesByProviderId(providerId);
			if (serviceDeatils.getServiceDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Service Available");
			} else {
				response = responseWrapper.buildResponse(200, serviceDeatils, "Successful");
			}
			return response;
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
	}

	@Override
	public Response getAllServiceCategoryAndSubCategory(String serviceLevelId) {
		Response response = null;
		try {
			ServiceDetailsResponse serviceDeatils = otsService.getAllCategoryAndSubCategory(serviceLevelId);
			if (serviceDeatils.getServiceDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Found");
			} else {
				response = responseWrapper.buildResponse(200, serviceDeatils, "Successful");
			}
			return response;
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
	}

	@Override
	public Response updateServiceStatus(UpdateServiceStatusRequestModel updateServiceStatusRequestModel ) {
		Response response = null;
		try {
			if(updateServiceStatusRequestModel.getRequest().getServiceId() == null || updateServiceStatusRequestModel.getRequest().getServiceId().equalsIgnoreCase("")
					|| updateServiceStatusRequestModel.getRequest().getStatus() == null || updateServiceStatusRequestModel.getRequest().getStatus().equalsIgnoreCase("")) {
				response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			String serviceDeatils = otsService.updateServiceStatus(updateServiceStatusRequestModel);
			if (serviceDeatils == null) {
				response = responseWrapper.buildResponse(404, "No Data Available");
			} else {
				response = responseWrapper.buildResponse(200, serviceDeatils, "Successful");
			}
			return response;
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
	}

	@Override
	public Response getServicesByProviderAndStatus(GetServicesByProviderAndStatusRequest getServicesByProviderAndStatusRequest) {
		Response response = null;
		try {
			if(getServicesByProviderAndStatusRequest.getRequest().getProviderId() == null || getServicesByProviderAndStatusRequest.getRequest().getProviderId().equalsIgnoreCase("") 
				|| getServicesByProviderAndStatusRequest.getRequest().getStatus() == null || getServicesByProviderAndStatusRequest.getRequest().getStatus().equalsIgnoreCase("")) {
				response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			ServiceDetailsResponse serviceDeatils = otsService.getServicesByProviderAndStatus(getServicesByProviderAndStatusRequest);
			if (serviceDeatils.getServiceDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Found");
			} else {
				response = responseWrapper.buildResponse(200, serviceDeatils, "Successful");
			}
			return response;
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
	}

	@Override
	public Response addServiceRatingAndReview(AddServiceReviewAndRatingRequest reviewAndRatingRequest) {
		Response response = null;
		try {
			if(reviewAndRatingRequest.getRequest().getOtsServiceId() == null || reviewAndRatingRequest.getRequest().getOtsServiceId().equalsIgnoreCase("") 
					|| reviewAndRatingRequest.getRequest().getOtsServiceOrderId() == null || reviewAndRatingRequest.getRequest().getOtsServiceOrderId().equalsIgnoreCase("")
					|| reviewAndRatingRequest.getRequest().getOtsCustomerId() == null || reviewAndRatingRequest.getRequest().getOtsCustomerId().equalsIgnoreCase("")
					|| reviewAndRatingRequest.getRequest().getOtsRatingReviewTittle() == null || reviewAndRatingRequest.getRequest().getOtsRatingReviewTittle().equalsIgnoreCase("")
					|| reviewAndRatingRequest.getRequest().getOtsReviewRating() == null) {
				
				response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			String addOrUpdateServiceReviewRating = otsService.addServiceRatingAndReview(reviewAndRatingRequest);
			if (addOrUpdateServiceReviewRating == null) {
				response = responseWrapper.buildResponse(404, "Data Not Inserted");
			}else {
				response = responseWrapper.buildResponse(200, addOrUpdateServiceReviewRating, "Successful");
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
	public Response getServiceRatingAndReviewDetailsByOrderId(GetServiceReviewsAndRatingRequest getServiceReviewsAndRatingRequest) {
		Response response = null;
		try {
			if(getServiceReviewsAndRatingRequest.getRequest().getServiceOrderId() == null || getServiceReviewsAndRatingRequest.getRequest().getServiceOrderId().equalsIgnoreCase("")
					|| getServiceReviewsAndRatingRequest.getRequest().getServiceId() == null || getServiceReviewsAndRatingRequest.getRequest().getServiceId().equalsIgnoreCase("")
					|| getServiceReviewsAndRatingRequest.getRequest().getCustomerId() == null || getServiceReviewsAndRatingRequest.getRequest().getCustomerId().equalsIgnoreCase("")) {
				response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			List<GetServiceReviewAndRatingResponse> serviceRatingAndReview = otsService.getServiceRatingAndReviewDetailsByOrderId(getServiceReviewsAndRatingRequest);
			if (serviceRatingAndReview.size() == 0) {
				response = responseWrapper.buildResponse(404, "No Review And Ratings");
			} else {
				response = responseWrapper.buildResponse(200, serviceRatingAndReview, "Successful");
			}
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getAverageRatingOfService(String serviceId) {
		Response response = null;
		try {
			AverageServiceReviewRatingResponse reviewRatingResponse = otsService.getAverageRatingOfService(serviceId);
			if (reviewRatingResponse == null) {

				response = responseWrapper.buildResponse(404, "No Review And Ratings For This Service");
			} else {
				response = responseWrapper.buildResponse(200, reviewRatingResponse, "Successful");
			}
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getServicesBySubCategoryAndProvider(GetServicesBySubCategoryAndProviderRequest getServicesBySubCategoryAndProviderRequest) {
		Response response = null;
		try {
			if(getServicesBySubCategoryAndProviderRequest.getRequest().getProviderId() == null || getServicesBySubCategoryAndProviderRequest.getRequest().getProviderId().equalsIgnoreCase("")
					|| getServicesBySubCategoryAndProviderRequest.getRequest().getSubcategoryId() == null || getServicesBySubCategoryAndProviderRequest.getRequest().getSubcategoryId().equalsIgnoreCase("")) 
			{
				response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			ServiceDetailsResponse serviceDetailsData= otsService.getServicesBySubCategoryAndProvider(getServicesBySubCategoryAndProviderRequest);
			if(serviceDetailsData.getServiceDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Services Available");
			} else {
				response = responseWrapper.buildResponse(200, serviceDetailsData, "Successful");
			}
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getRecentlyAddedServiceList(String levelId) {
		Response response = null;
		try	{
			ServiceDetailsResponse serviceDetailsData= otsService.getRecentlyAddedServiceList(levelId);
			if(serviceDetailsData.getServiceDetails().size()==0) {
				response = responseWrapper.buildResponse(404, "No Services Available");
			} else {
				response = responseWrapper.buildResponse(200, serviceDetailsData, "Successful");
			}
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getServicesByProviderPagination(GetServicesByProviderPaginationRequest getServicesByProviderPaginationRequest) {
		Response response = null;
		try {
			if(getServicesByProviderPaginationRequest.getRequest().getProviderId() == null || getServicesByProviderPaginationRequest.getRequest().getProviderId().equalsIgnoreCase("")
					|| getServicesByProviderPaginationRequest.getRequest().getDataSize() == null || getServicesByProviderPaginationRequest.getRequest().getDataSize().equalsIgnoreCase("")
					|| getServicesByProviderPaginationRequest.getRequest().getPageNumber() == null || getServicesByProviderPaginationRequest.getRequest().getPageNumber().equalsIgnoreCase("")) {
				response = responseWrapper.buildResponse(400,"Please Enter Required Inputs");
			}
			ServiceDetailsBOResponse serviceDetailsBOResponse=otsService.getServicesByProviderPagination(getServicesByProviderPaginationRequest);
			if(serviceDetailsBOResponse.getServiceDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,"No Services Available For This Provider");
			}else {
				response = responseWrapper.buildResponse(200,serviceDetailsBOResponse,"Successful");
			}
			
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response addServiceLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest) {
		Response response = null;
		try {
			if(addServiceLocationMappingRequest.getRequest().getProviderId() == null || addServiceLocationMappingRequest.getRequest().getProviderId().equalsIgnoreCase("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			if(addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().size() == 0) {
				return response = buildResponse(400,"Please Enter locationId");
			}
			for (int i = 0; i < addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().size(); i++) {
	            ServiceLocationIDName serviceLocationIDName = addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(i);

	            if (serviceLocationIDName.getLocationId() == null || serviceLocationIDName.getLocationId().isEmpty()) {
	                return buildResponse(400, "Please Enter locationId");
	            }

	            if (serviceLocationIDName.getLocationName() == null || serviceLocationIDName.getLocationName().isEmpty()) {
	                return buildResponse(400, "Please Enter locationName");
	            }
	        }
	        
	        //To Check if data already Added for Distributor level or Product level based on request parameters
	        if(addServiceLocationMappingRequest.getRequest().getServiceId() == null || addServiceLocationMappingRequest.getRequest().getServiceId().equalsIgnoreCase("")) {
	        	ServiceLocationResponse existingProviderMappings = otsService.getLocationsMappedToService(addServiceLocationMappingRequest.getRequest().getServiceId());
	        	if(existingProviderMappings.getServiceLocation().size() != 0) {
	        		return responseWrapper.buildResponse(400, "Locations Have Already Been Added For This Provider. Please Try To Update.");
	        	}
	        }else {
	        	ServiceLocationResponse existingProviderMappings= otsService.getLocationsMappedToProviderOnly(addServiceLocationMappingRequest.getRequest().getProviderId());
	        	if(existingProviderMappings.getServiceLocation().size() != 0) {
	        		return responseWrapper.buildResponse(400, "Locations Have Already Been Added For This Provider. Please Try To Update.");
	        	}
	        }
	        
	        //To add Service availability for provider role or service level
	        ServiceLocationResponse serviceLocationResponse = otsService.addServiceLocationMapping(addServiceLocationMappingRequest);
	        if (serviceLocationResponse.getServiceLocation().size()== 0) {
	            response = responseWrapper.buildResponse(404, "Not Inserted");
	        } else {
	            response = responseWrapper.buildResponse(200,serviceLocationResponse, "Successful");
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
	public Response clearAndAddServiceLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest) {
		Response response = null;
		try {
			if(addServiceLocationMappingRequest.getRequest().getProviderId() == null || addServiceLocationMappingRequest.getRequest().getProviderId().equalsIgnoreCase("")
					|| addServiceLocationMappingRequest.getRequest().getServiceId() == null || addServiceLocationMappingRequest.getRequest().getServiceId().equalsIgnoreCase("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			if(addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().size() == 0) {
				return response = buildResponse(400,"Please Enter locationId");
			}
			if(addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationId() == null || addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationId().equalsIgnoreCase("") || addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationId().equalsIgnoreCase("string")
					|| addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationName() == null || addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationName().equalsIgnoreCase("") || addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationName().equalsIgnoreCase("string")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			
			ServiceLocationResponse serviceLocationResponse = otsService.clearAndAddServiceLocationMapping(addServiceLocationMappingRequest);
			
			if(serviceLocationResponse.getServiceLocation().size() == 0 || serviceLocationResponse == null) {
				response = responseWrapper.buildResponse(404,"Not Inserted");		
			}else{
				response = responseWrapper.buildResponse(200,serviceLocationResponse,"Successful");
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
	public Response clearAndAddProviderLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest) {
		Response response = null;
		try {
			if(addServiceLocationMappingRequest.getRequest().getProviderId() == null || addServiceLocationMappingRequest.getRequest().getProviderId().equalsIgnoreCase("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			if(addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().size() == 0) {
				return response = buildResponse(400,"Please Enter locationId");
			}
			if(addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationId() == null || addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationId().equalsIgnoreCase("") || addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationId().equalsIgnoreCase("string")
					|| addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationName() == null || addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationName().equalsIgnoreCase("") || addServiceLocationMappingRequest.getRequest().getServiceLocationIDName().get(0).getLocationName().equalsIgnoreCase("string")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			
			ServiceLocationResponse serviceLocationResponse = otsService.clearAndAddProviderLocationMapping(addServiceLocationMappingRequest);
			
			if(serviceLocationResponse.getServiceLocation().size() == 0 || serviceLocationResponse == null) {
				response = responseWrapper.buildResponse(404,"Not Inserted");		
			}else{
				response = responseWrapper.buildResponse(200,serviceLocationResponse,"Successful");
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
	public Response getServiceableLocationByProviderOnly(ProviderAndServiceRequest providerAndServiceRequest) {
		Response response = null;
		try {
			String providerId = providerAndServiceRequest.getRequest().getProviderId();
			//Validating Distributor Id from Request
			if(providerId != null) {
				UserDataBOResponse provider = otsUserService.getUserIDUsers(providerAndServiceRequest.getRequest().getProviderId());
				if(provider.getUserDetails().size() == 0) {
					return response = responseWrapper.buildResponse(400, "Invalid Provider");
				}	
			}
			
			// Fetch serviceable location details
			GetServiceableServiceLocationResponse getServiceableLocationResponse = otsService.getServiceableLocationByProviderOnly(providerAndServiceRequest);
			if (getServiceableLocationResponse.getServiceableLocation().getStatelist().size() == 0 
					&& getServiceableLocationResponse.getServiceableLocation().getDistrictList().size() == 0
					&& getServiceableLocationResponse.getServiceableLocation().getPincodeList().size() == 0) {
					return response = responseWrapper.buildResponse(404, getServiceableLocationResponse, "No Serviceable Location Has Been Added");
				} else {
					return response = responseWrapper.buildResponse(200, getServiceableLocationResponse, "Successful");
				}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500, "Something Went Wrong");
		}
	}
	
}
