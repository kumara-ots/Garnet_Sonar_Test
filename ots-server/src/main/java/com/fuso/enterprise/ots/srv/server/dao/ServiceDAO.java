package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.CategoryDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.GetSlot;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceAvailability;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDetailsData;
import com.fuso.enterprise.ots.srv.api.model.domain.ServicePolicy;
import com.fuso.enterprise.ots.srv.api.model.domain.ServicePriceDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceSlot;
import com.fuso.enterprise.ots.srv.api.model.domain.SubCategoryDetails;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceCategorySubCategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServicesByProviderAndStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServicesByProviderPaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServicesBySubCategoryAndProviderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateServiceStatusRequestModel;
import com.fuso.enterprise.ots.srv.api.service.response.ServiceDetailsBOResponse;

public interface ServiceDAO {

	String addOrUpdateServiceDetails(ServiceDetails serviceDetails);

	String addOrUpdateServicePricingDetails(ServicePriceDetails servicePriceDetails);
	
	String addOrUpdateServiceAvailability(ServiceAvailability serviceAvailability);

	String addOrUpdateServicePolicy(ServicePolicy servicePolicyDetails);

	List<ServiceDetailsData> getCategoryAndSubCategory(GetServiceCategorySubCategoryRequest getServiceCategorySubCategoryRequest);

	ServiceDetailsData getServiceDetailsByServiceId(String serviceId);
	
	String addServiceSlot(ServiceSlot serviceSlot);
	
	GetSlot getAllSlotsByServiceId(String serviceId);
	
	List<ServiceDetailsData> getAllServices();
	
	List<ServiceDetailsData> getAllServicesByProviderId(String provicerId);
	
	List<ServiceDetailsData> getAllCategoryAndSubCategory(String serviceLevelId);
	
	String updateServiceStatus(UpdateServiceStatusRequestModel updateServiceStatusRequestModel);
	
	List<ServiceDetailsData> getServicesByProviderAndStatus(GetServicesByProviderAndStatusRequest getServicesByProviderAndStatusRequest);
	
	CategoryDetails getCategoryForServiceId(String serviceId);
	
	SubCategoryDetails getSubCategoryForServiceId(String serviceId);

	List<ServiceDetailsData> getServicesBySubCategoryAndProvider(GetServicesBySubCategoryAndProviderRequest getServicesBySubCategoryAndProviderRequest);

	ServiceDetailsBOResponse getServicesByProviderPagination(GetServicesByProviderPaginationRequest getServicesByProviderPaginationRequest);

	List<ServiceDetailsData> getRecentlyAddedServiceList(String levelId);
}
