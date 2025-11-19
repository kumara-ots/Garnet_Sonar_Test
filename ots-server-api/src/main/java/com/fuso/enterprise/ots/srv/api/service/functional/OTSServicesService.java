package com.fuso.enterprise.ots.srv.api.service.functional;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.GetSlot;
import com.fuso.enterprise.ots.srv.api.model.domain.InsertServiceOrder;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDetailsData;
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

public interface OTSServicesService {

	String addOrUpdateService(AddOrUpdateServiceRequest addUpdateServiceRequest);

	ServiceDetailsData getServiceDetailsByServiceId(String serviceId);

	ServiceDetailsResponse getCategoryAndSubCategory(GetServiceCategorySubCategoryRequest getServiceCategorySubCategoryRequest);

	GetSlot getSlotsByServiceId(String serviceId);

	Object getAvailableServiceSlots(GetServiceSlotRequest getServiceSlotRequest);

	String checkSlotAvailability(InsertServiceOrder insertServiceOrder);

	ServiceDetailsResponse getAllServices();

	ServiceDetailsResponse getAllServicesByProviderId(String providerId);

	ServiceDetailsResponse getAllCategoryAndSubCategory(String serviceLevelId);

	String updateServiceStatus(UpdateServiceStatusRequestModel updateServiceStatusRequestModel);
	
	ServiceDetailsResponse getServicesByProviderAndStatus(GetServicesByProviderAndStatusRequest getServicesByProviderAndStatusRequest);

	String addServiceRatingAndReview(AddServiceReviewAndRatingRequest reviewAndRatingRequest);
	
	List<GetServiceReviewAndRatingResponse> getServiceRatingAndReviewDetailsByOrderId(GetServiceReviewsAndRatingRequest getServiceReviewsAndRatingRequest);

	AverageServiceReviewRatingResponse getAverageRatingOfService(String serviceId);
	
	ServiceDetailsResponse getServicesBySubCategoryAndProvider(GetServicesBySubCategoryAndProviderRequest getServicesBySubCategoryAndProviderRequest);
	
	ServiceDetailsResponse getRecentlyAddedServiceList(String levelId);
	
	ServiceDetailsBOResponse getServicesByProviderPagination(GetServicesByProviderPaginationRequest getServicesByProviderPaginationRequest) ;

	ServiceLocationResponse addServiceLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest );

	ServiceLocationResponse clearAndAddServiceLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest);
	
	ServiceLocationResponse getLocationsMappedToService(String serviceId);

	ServiceLocationResponse getLocationsMappedToProviderOnly(String providerId);

	ServiceLocationResponse clearAndAddProviderLocationMapping(AddServiceLocationMappingRequest addServiceLocationMappingRequest);

	GetServiceableServiceLocationResponse getServiceableLocationByProviderOnly(ProviderAndServiceRequest providerAndServiceRequest);


}
