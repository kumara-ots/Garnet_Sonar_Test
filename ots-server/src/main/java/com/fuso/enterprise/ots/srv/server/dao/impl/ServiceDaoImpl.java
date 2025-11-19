package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsService;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceLevel;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.model.entity.Useraccounts;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ServiceDaoImpl extends AbstractIptDao<OtsService, String> implements ServiceDAO {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public ServiceDaoImpl() {
		super(OtsService.class);	
	}

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private String getValueOrNull(String value) {
	    return (value == null || value.equals("")) ? null : value;
	}
	
	private BigDecimal getBigDecimalOrNull(String value) {
	    return (value == null || value.equals("")) ? null : new BigDecimal(value);
	}
	
	private Boolean getBooleanOrNull(String value) {
	    return (value == null || value.equals("")) ? null : new Boolean(value);
	}

	@Override
	@Transactional //adding or updating service details
	public String addOrUpdateServiceDetails(ServiceDetails serviceDetails) {
		try{
			// Ensure OtsServiceId is correctly assigned
		    if (serviceDetails.getOtsServiceId() == null || serviceDetails.getOtsServiceId().isEmpty()) {
		    	OtsService otsService = new OtsService();
				
				OtsServiceLevel serviceLevelId = new OtsServiceLevel();
				serviceLevelId.setOtsServiceLevelId(Integer.parseInt(serviceDetails.getOtsServiceLevelId()));
				
				if (serviceLevelId.getOtsServiceLevelId() == 1) {
				    otsService.setOtsServiceMappingId(null);
				} else {
				    String mappingId = serviceDetails.getOtsServiceMappingId();

				    if (mappingId == null || mappingId.trim().isEmpty()) {
				        throw new BusinessException("Mapping ID must not be null or empty for subcategory services");
				    }

				    OtsService otsServiceMappingId = super.getEntityManager().getReference(OtsService.class, mappingId);

				    if (otsServiceMappingId == null) {
				        throw new BusinessException("No OtsService found with ID: " + mappingId);
				    }

				    otsService.setOtsServiceMappingId(otsServiceMappingId);
				}
				Useraccounts createdUser = new Useraccounts();
				createdUser.setAccountId(UUID.fromString(serviceDetails.getCreatedUser()));

				OtsUsers providerId = new OtsUsers();
				if(serviceDetails.getOtsServiceProviderId() == null ||serviceDetails.getOtsServiceProviderId().trim().isEmpty() ) {
					otsService.setOtsServiceProviderId(null);
				}
				providerId.setOtsUsersId(UUID.fromString(serviceDetails.getOtsServiceProviderId()));
				
				//Setting UUID for serviceId
		        UUID uuid = UUID.randomUUID();
				otsService.setOtsServiceId(uuid);
				
				otsService.setOtsServiceNumber(serviceDetails.getCreatedUser()+"-"+otsService.getOtsServiceId());
				otsService.setOtsServiceName(serviceDetails.getOtsServiceName());
				otsService.setOtsServiceDescription(serviceDetails.getOtsServiceDescription());
				otsService.setOtsServiceDescriptionLong(serviceDetails.getOtsServiceDescriptionLong());
				otsService.setOtsServiceLevelId(serviceLevelId);
				otsService.setOtsServiceStatus(serviceDetails.getOtsServiceStatus());
				otsService.setCreatedUser(createdUser);
				
				otsService.setOtsServiceImage(getValueOrNull(serviceDetails.getOtsServiceImage()));
				otsService.setOtsMultiServiceImage1(getValueOrNull(serviceDetails.getOtsMultiServiceImage1()));
				otsService.setOtsMultiServiceImage2(getValueOrNull(serviceDetails.getOtsMultiServiceImage2()));
				otsService.setOtsMultiServiceImage3(getValueOrNull(serviceDetails.getOtsMultiServiceImage3()));
				otsService.setOtsMultiServiceImage4(getValueOrNull(serviceDetails.getOtsMultiServiceImage4()));
				otsService.setOtsMultiServiceImage5(getValueOrNull(serviceDetails.getOtsMultiServiceImage5()));
				otsService.setOtsMultiServiceImage6(getValueOrNull(serviceDetails.getOtsMultiServiceImage6()));
				otsService.setOtsMultiServiceImage7(getValueOrNull(serviceDetails.getOtsMultiServiceImage7()));
				otsService.setOtsMultiServiceImage8(getValueOrNull(serviceDetails.getOtsMultiServiceImage8()));
				otsService.setOtsMultiServiceImage9(getValueOrNull(serviceDetails.getOtsMultiServiceImage9()));
				otsService.setOtsMultiServiceImage10(getValueOrNull(serviceDetails.getOtsMultiServiceImage10()));

              	System.out.println(otsService);
				save(otsService);
				super.getEntityManager().flush();
				return otsService.getOtsServiceId().toString();
		   }
		   else {
			   	OtsService otsService = new OtsService();
				Map<String, Object> queryParameter = new HashMap<>();
				queryParameter.put("otsServiceId", UUID.fromString(serviceDetails.getOtsServiceId()));
				try {
					otsService = super.getResultByNamedQuery("OtsService.findByOtsServiceId", queryParameter);
				}catch (NoResultException e) {
					return null;
				}
				otsService.setOtsServiceName(serviceDetails.getOtsServiceName());
				otsService.setOtsServiceDescription(serviceDetails.getOtsServiceDescription());
				otsService.setOtsServiceDescriptionLong(serviceDetails.getOtsServiceDescriptionLong());
				otsService.setOtsServiceStatus(serviceDetails.getOtsServiceStatus());
				otsService.setOtsServiceImage(getValueOrNull(serviceDetails.getOtsServiceImage()));
				otsService.setOtsMultiServiceImage1(getValueOrNull(serviceDetails.getOtsMultiServiceImage1()));
				otsService.setOtsMultiServiceImage2(getValueOrNull(serviceDetails.getOtsMultiServiceImage2()));
				otsService.setOtsMultiServiceImage3(getValueOrNull(serviceDetails.getOtsMultiServiceImage3()));
				otsService.setOtsMultiServiceImage4(getValueOrNull(serviceDetails.getOtsMultiServiceImage4()));
				otsService.setOtsMultiServiceImage5(getValueOrNull(serviceDetails.getOtsMultiServiceImage5()));
				otsService.setOtsMultiServiceImage6(getValueOrNull(serviceDetails.getOtsMultiServiceImage6()));
				otsService.setOtsMultiServiceImage7(getValueOrNull(serviceDetails.getOtsMultiServiceImage7()));
				otsService.setOtsMultiServiceImage8(getValueOrNull(serviceDetails.getOtsMultiServiceImage8()));
				otsService.setOtsMultiServiceImage9(getValueOrNull(serviceDetails.getOtsMultiServiceImage9()));
				otsService.setOtsMultiServiceImage10(getValueOrNull(serviceDetails.getOtsMultiServiceImage10()));

				save(otsService);
				return otsService.getOtsServiceId().toString();
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override //adding or updating service pricing details
	public String addOrUpdateServicePricingDetails(ServicePriceDetails servicePriceDetails){
		OtsService serviceEntity = new OtsService();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceId", UUID.fromString(servicePriceDetails.getOtsServiceId()));
			try {
				serviceEntity = super.getResultByNamedQuery("OtsService.findByOtsServiceId", queryParameter);
			}catch (NoResultException e) {
				return null;
			}
			
			if(servicePriceDetails.getOtsServiceStatus().equalsIgnoreCase("Active")) {
				serviceEntity.setOtsServiceStatus(servicePriceDetails.getOtsServiceStatus());
			}else if(Integer.parseInt(serviceEntity.getOtsServiceStatus()) >Integer.parseInt(servicePriceDetails.getOtsServiceStatus()))
			{
				serviceEntity.setOtsServiceStatus(serviceEntity.getOtsServiceStatus());
			}else {
				serviceEntity.setOtsServiceStatus(servicePriceDetails.getOtsServiceStatus());
			}
			serviceEntity.setOtsServiceBasePrice(getBigDecimalOrNull(servicePriceDetails.getOtsServiceBasePrice()));
			serviceEntity.setOtsServiceDiscountPercentage(getValueOrNull(servicePriceDetails.getOtsServiceDiscountPercentage()));
			serviceEntity.setOtsServiceDiscountPrice(getBigDecimalOrNull(servicePriceDetails.getOtsServiceDiscountPrice()));
			serviceEntity.setOtsServiceStrikenPrice(getBigDecimalOrNull(servicePriceDetails.getOtsServiceStrikenPrice()));
			serviceEntity.setOtsServiceFinalPriceWithoutGst(getBigDecimalOrNull(servicePriceDetails.getOtsServiceFinalPriceWithoutGst()));
			serviceEntity.setOtsServiceGstPercentage(getValueOrNull(servicePriceDetails.getOtsServiceGstPercentage()));
			serviceEntity.setOtsServiceGstPrice(getBigDecimalOrNull(servicePriceDetails.getOtsServiceGstPrice()));
			serviceEntity.setOtsServiceFinalPriceWithGst(getBigDecimalOrNull(servicePriceDetails.getOtsServiceFinalPriceWithGst()));
			save(serviceEntity);
			
			return serviceEntity.getOtsServiceId().toString();
		}catch(Exception e){
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		
	}

	@Override
	public String addOrUpdateServiceAvailability(ServiceAvailability serviceAvailability){
		OtsService serviceEntity = new OtsService();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceId", UUID.fromString(serviceAvailability.getOtsServiceId()));
			try {
				serviceEntity = super.getResultByNamedQuery("OtsService.findByOtsServiceId", queryParameter);
			}catch (NoResultException e) {
				return null;
			}
			
			if(serviceAvailability.getOtsServiceStatus().equalsIgnoreCase("Active")) {
				serviceEntity.setOtsServiceStatus(serviceAvailability.getOtsServiceStatus());
			}else if(Integer.parseInt(serviceEntity.getOtsServiceStatus()) > Integer.parseInt(serviceAvailability.getOtsServiceStatus()))
			{
				serviceEntity.setOtsServiceStatus(serviceEntity.getOtsServiceStatus());
			}else {
				serviceEntity.setOtsServiceStatus(serviceAvailability.getOtsServiceStatus());
			}

			serviceEntity.setOtsServiceDuration(getValueOrNull(serviceAvailability.getOtsServiceDuration()));
			serviceEntity.setOtsServiceMode(getValueOrNull(serviceAvailability.getOtsServiceMode()));
			serviceEntity.setOtsServiceVirtualLocation(getValueOrNull(serviceAvailability.getOtsServiceVirtualLocation()));
			serviceEntity.setOtsServiceCompanyName(getValueOrNull(serviceAvailability.getOtsServiceCompanyName()));
			serviceEntity.setOtsServiceCompanyAddress(getValueOrNull(serviceAvailability.getOtsServiceCompanyAddress()));
			serviceEntity.setOtsServiceCompanyState(getValueOrNull(serviceAvailability.getOtsServiceCompanyState()));
			serviceEntity.setOtsServiceCompanyDistrict(getValueOrNull(serviceAvailability.getOtsServiceCompanyDistrict()));
			serviceEntity.setOtsServiceCompanyPincode(getValueOrNull(serviceAvailability.getOtsServiceCompanyPincode()));
			serviceEntity.setOtsServiceCompanyContactNo(getValueOrNull(serviceAvailability.getOtsServiceCompanyContactNo()));
			save(serviceEntity);
			
			return serviceEntity.getOtsServiceId().toString();		
		}catch(Exception e){
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		
	}

	@Override
	public String addOrUpdateServicePolicy(ServicePolicy servicePolicyDetails) {
		OtsService serviceEntity = new OtsService();
	    try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceId", UUID.fromString(servicePolicyDetails.getOtsServiceId()));
			try {
				serviceEntity = super.getResultByNamedQuery("OtsService.findByOtsServiceId", queryParameter);
			}catch (NoResultException e) {
				return null;
			}
			
			if(servicePolicyDetails.getOtsServiceStatus().equalsIgnoreCase("Active")) {
				serviceEntity.setOtsServiceStatus(servicePolicyDetails.getOtsServiceStatus());
			}else if(Integer.parseInt(serviceEntity.getOtsServiceStatus()) > Integer.parseInt(servicePolicyDetails.getOtsServiceStatus()))
			{
				serviceEntity.setOtsServiceStatus(serviceEntity.getOtsServiceStatus());
			}else {
				serviceEntity.setOtsServiceStatus(servicePolicyDetails.getOtsServiceStatus());
			}
			serviceEntity.setOtsServiceStatus(getValueOrNull(servicePolicyDetails.getOtsServiceStatus()));
			serviceEntity.setOtsServiceCancellationAvailability(servicePolicyDetails.getOtsServiceCancellationAvailability());
			serviceEntity.setOtsServiceCancellationPolicy(getValueOrNull(servicePolicyDetails.getOtsServiceCancellationPolicy()));
			serviceEntity.setOtsServiceCancellationBefore(getValueOrNull(servicePolicyDetails.getOtsServiceCancellationBefore()));
			serviceEntity.setOtsServiceCancellationFees(getValueOrNull(servicePolicyDetails.getOtsServiceCancellationFees()));
			serviceEntity.setOtsServiceRescheduleAvailability(servicePolicyDetails.getOtsServiceRescheduleAvailability());
			serviceEntity.setOtsServiceReschedulePolicy(getValueOrNull(servicePolicyDetails.getOtsServiceReschedulePolicy()));
			serviceEntity.setOtsServiceRescheduleBefore(getValueOrNull(servicePolicyDetails.getOtsServiceRescheduleBefore()));
			serviceEntity.setOtsServiceRescheduleFees(getValueOrNull(servicePolicyDetails.getOtsServiceRescheduleFees()));
			save(serviceEntity);
			
			return serviceEntity.getOtsServiceId().toString();
		} catch (Exception e) {
			logger.error("Exception while Inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}

	}
	
	@Override
	@Transactional
	public String addServiceSlot(ServiceSlot serviceSlot) {
		OtsService otsService = new OtsService();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceId", UUID.fromString(serviceSlot.getOtsServiceId()));
			try {
				otsService = super.getResultByNamedQuery("OtsService.findByOtsServiceId", queryParameter);
			}catch(NoResultException ex) {
				return null;
			}
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonData = objectMapper.writeValueAsString(serviceSlot.getOtsServiceSlot());
			otsService.setOtsServiceSlot(jsonData);
			otsService.setOtsServiceStatus(serviceSlot.getOtsServiceStatus());

			save(otsService);
			super.getEntityManager().flush();
			return  otsService.getOtsServiceId().toString();
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
	
	private ServiceDetailsData convertServiceDetailsFromProcedureToDomain(Map<String, Object> out) {
		ServiceDetailsData serviceDetails = new ServiceDetailsData();
	    
		serviceDetails.setOtsServiceId(out.get("ots_service_id") == null ? "" : out.get("ots_service_id").toString());
		serviceDetails.setOtsServiceNumber(out.get("ots_service_number") == null ? "" : out.get("ots_service_number").toString());
		serviceDetails.setOtsServiceName(out.get("ots_service_name") == null ? "" : out.get("ots_service_name").toString());
		serviceDetails.setOtsServiceDescription(out.get("ots_service_description") == null ? "" : out.get("ots_service_description").toString());
		serviceDetails.setOtsServiceDescriptionLong(out.get("ots_service_description_long") == null ? "" : out.get("ots_service_description_long").toString());
		serviceDetails.setOtsServiceLevelId(out.get("ots_service_level_id") == null ? "" : out.get("ots_service_level_id").toString());
		serviceDetails.setOtsServiceBasePrice(out.get("ots_service_base_price") == null ? null : out.get("ots_service_base_price").toString());
		serviceDetails.setOtsEquipmentRentalCost(out.get("ots_equipment_rental_cost") == null ? null : out.get("ots_equipment_rental_cost").toString());
		serviceDetails.setOtsServiceDiscountPercentage(out.get("ots_service_discount_percentage") == null ? "" : out.get("ots_service_discount_percentage").toString());
		serviceDetails.setOtsServiceDiscountPrice(out.get("ots_service_discount_price") == null ? "" : out.get("ots_service_discount_price").toString());
		serviceDetails.setOtsServiceStrikenPrice(out.get("ots_service_striken_price") == null ? null : out.get("ots_service_striken_price").toString());
		serviceDetails.setOtsServiceFinalPriceWithoutGst(out.get("ots_service_final_price_without_gst") == null ? null : out.get("ots_service_final_price_without_gst").toString());
		serviceDetails.setOtsServiceGstPercentage(out.get("ots_service_gst_percentage") == null ? "" : out.get("ots_service_gst_percentage").toString());
		serviceDetails.setOtsServiceGstPrice(out.get("ots_service_gst_price") == null ? null :out.get("ots_service_gst_price").toString());
		serviceDetails.setOtsServiceFinalPriceWithGst(out.get("ots_service_final_price_with_gst") == null ? null : out.get("ots_service_final_price_with_gst").toString());
		serviceDetails.setOtsServiceDuration(out.get("ots_service_duration") == null ? "" : out.get("ots_service_duration").toString());
		serviceDetails.setOtsServiceMode(out.get("ots_service_mode") == null ? "" : out.get("ots_service_mode").toString());
		serviceDetails.setOtsServiceVirtualLocation(out.get("ots_service_virtual_location") == null ? "" : out.get("ots_service_virtual_location").toString());
		serviceDetails.setOtsServiceCustomerLocation(out.get("ots_service_customer_location") == null ? null :out.get("ots_service_customer_location").toString());
		serviceDetails.setOtsServiceCompanyName(out.get("ots_service_company_name") == null ? "" : out.get("ots_service_company_name").toString());
		serviceDetails.setOtsServiceCompanyAddress(out.get("ots_service_company_address") == null ? "" : out.get("ots_service_company_address").toString());
		serviceDetails.setOtsServiceCompanyState(out.get("ots_service_company_state") == null ? "" : out.get("ots_service_company_state").toString());
		serviceDetails.setOtsServiceCompanyDistrict(out.get("ots_service_company_district") == null ? "" : out.get("ots_service_company_district").toString());
		serviceDetails.setOtsServiceCompanyPincode(out.get("ots_service_company_pincode") == null ? "" : out.get("ots_service_company_pincode").toString());
		serviceDetails.setOtsServiceCompanyContactNo(out.get("ots_service_company_contact_no") == null ? "" : out.get("ots_service_company_contact_no").toString());
		serviceDetails.setOtsServiceCancellationAvailability(out.get("ots_service_cancellation_availability") == null ? null : out.get("ots_service_cancellation_availability").toString());
		serviceDetails.setOtsServiceCancellationPolicy(out.get("ots_service_cancellation_policy") == null ? "" : out.get("ots_service_cancellation_policy").toString());
		serviceDetails.setOtsServiceCancellationBefore(out.get("ots_service_cancellation_before") == null ? "" : out.get("ots_service_cancellation_before").toString());
		serviceDetails.setOtsServiceCancellationFees(out.get("ots_service_cancellation_fees") == null ? "" : out.get("ots_service_cancellation_fees").toString());
		serviceDetails.setOtsServiceRescheduleAvailability(out.get("ots_service_reschedule_availability") == null ? null :out.get("ots_service_reschedule_availability").toString());
		serviceDetails.setOtsServiceReschedulePolicy(out.get("ots_service_reschedule_policy") == null ? "" : out.get("ots_service_reschedule_policy").toString());
		serviceDetails.setOtsServiceRescheduleBefore(out.get("ots_service_reschedule_before") == null ? "" : out.get("ots_service_reschedule_before").toString());
		serviceDetails.setOtsServiceRescheduleFees(out.get("ots_service_reschedule_fees") == null ? "" : out.get("ots_service_reschedule_fees").toString());
		serviceDetails.setOtsServiceImage(out.get("ots_service_image") == null ? "" : out.get("ots_service_image").toString());
		serviceDetails.setOtsMultiServiceImage1(out.get("ots_multi_service_image1") == null ? "" : out.get("ots_multi_service_image1").toString());
		serviceDetails.setOtsMultiServiceImage2(out.get("ots_multi_service_image2") == null ? "" : out.get("ots_multi_service_image2").toString());
	    serviceDetails.setOtsMultiServiceImage3(out.get("ots_multi_service_image3") == null ? "" : out.get("ots_multi_service_image3").toString());
	    serviceDetails.setOtsMultiServiceImage4(out.get("ots_multi_service_image4") == null ? "" : out.get("ots_multi_service_image4").toString());
	    serviceDetails.setOtsMultiServiceImage5(out.get("ots_multi_service_image5") == null ? "" : out.get("ots_multi_service_image5").toString());
	    serviceDetails.setOtsMultiServiceImage6(out.get("ots_multi_service_image6") == null ? "" : out.get("ots_multi_service_image6").toString());
	    serviceDetails.setOtsMultiServiceImage7(out.get("ots_multi_service_image7") == null ? "" : out.get("ots_multi_service_image7").toString());
	    serviceDetails.setOtsMultiServiceImage8(out.get("ots_multi_service_image8") == null ? "" : out.get("ots_multi_service_image8").toString());
	    serviceDetails.setOtsMultiServiceImage9(out.get("ots_multi_service_image9") == null ? "" : out.get("ots_multi_service_image9").toString());
	    serviceDetails.setOtsMultiServiceImage10(out.get("ots_multi_service_image10") == null ? "" : out.get("ots_multi_service_image10").toString());
	    serviceDetails.setOtsServiceCreated(out.get("ots_service_created") == null ? "" : out.get("ots_service_created").toString());
	    serviceDetails.setOtsServiceUpdated(out.get("ots_service_updated") == null ? "" : out.get("ots_service_updated").toString());
	    serviceDetails.setCategoryId(out.get("category_id") == null ? "" : out.get("category_id").toString());
	    serviceDetails.setCategoryName(out.get("category_name") == null ? "" : out.get("category_name").toString());
	    serviceDetails.setSubCategoryId(out.get("subcategory_id") == null ? "" : out.get("subcategory_id").toString());
	    serviceDetails.setSubCategoryName(out.get("subcategory_name") == null ? "" : out.get("subcategory_name").toString());
	    serviceDetails.setOtsServiceProviderId(out.get("ots_service_provider_id") == null ? "" : out.get("ots_service_provider_id").toString());
	    serviceDetails.setOtsServiceProviderName(out.get("ots_service_provider_name") == null ? "" : out.get("ots_service_provider_name").toString());
	    
	    return serviceDetails;
	}
	
	private ServiceDetailsData convertServiceDetailsFromEntityToDomain(OtsService otsService)  {
		ServiceDetailsData serviceDetails = new ServiceDetailsData();
		
		serviceDetails.setOtsServiceId(otsService.getOtsServiceId() == null ? "" : otsService.getOtsServiceId().toString());
		serviceDetails.setOtsServiceNumber(otsService.getOtsServiceNumber() == null ? "" : otsService.getOtsServiceNumber());
		serviceDetails.setOtsServiceName(otsService.getOtsServiceName() == null ? "" : otsService.getOtsServiceName());
		serviceDetails.setOtsServiceDescription(otsService.getOtsServiceDescription() == null ? "" : otsService.getOtsServiceDescription());
		serviceDetails.setOtsServiceDescriptionLong(otsService.getOtsServiceDescriptionLong() == null ? "" : otsService.getOtsServiceDescriptionLong());
		serviceDetails.setOtsServiceStatus(otsService.getOtsServiceStatus() == null ? "" :otsService.getOtsServiceStatus());
		serviceDetails.setOtsServiceLevelId(otsService.getOtsServiceLevelId() == null ? "" : otsService.getOtsServiceLevelId().getOtsServiceLevelId().toString());
		serviceDetails.setOtsServiceProviderId(otsService.getOtsServiceProviderId() == null ? "" : otsService.getOtsServiceProviderId().getOtsUsersId().toString());
		serviceDetails.setOtsServiceProviderName(otsService.getOtsServiceProviderId() == null ? "" : otsService.getOtsServiceProviderId().getOtsUsersFirstname()+" "+otsService.getOtsServiceProviderId().getOtsUsersLastname());
		serviceDetails.setCreatedUser(otsService.getCreatedUser() == null ? "" : otsService.getCreatedUser().getAccountId().toString());
		serviceDetails.setOtsServiceBasePrice(otsService.getOtsServiceBasePrice() == null ? "" : otsService.getOtsServiceBasePrice().toString());
		serviceDetails.setOtsServiceDiscountPercentage(otsService.getOtsServiceDiscountPercentage() == null ? "" : otsService.getOtsServiceDiscountPercentage().toString());
		serviceDetails.setOtsServiceDiscountPrice(otsService.getOtsServiceDiscountPrice() == null ? "" : otsService.getOtsServiceDiscountPrice().toString());
		serviceDetails.setOtsServiceStrikenPrice(otsService.getOtsServiceStrikenPrice() == null ? "" : otsService.getOtsServiceStrikenPrice().toString());
		serviceDetails.setOtsServiceFinalPriceWithoutGst(otsService.getOtsServiceFinalPriceWithoutGst() == null ? "" : otsService.getOtsServiceFinalPriceWithoutGst().toString());
		serviceDetails.setOtsServiceGstPercentage(otsService.getOtsServiceGstPercentage() == null ? "" : otsService.getOtsServiceGstPercentage().toString());
		serviceDetails.setOtsServiceGstPrice(otsService.getOtsServiceGstPrice() == null ? "" : otsService.getOtsServiceGstPrice().toString());
		serviceDetails.setOtsServiceFinalPriceWithGst(otsService.getOtsServiceFinalPriceWithGst() == null ? "" : otsService.getOtsServiceFinalPriceWithGst().toString());
		serviceDetails.setOtsServiceDuration(otsService.getOtsServiceDuration() == null ? "" : otsService.getOtsServiceDuration());
		serviceDetails.setOtsServiceMode(otsService.getOtsServiceMode() == null ? "" : otsService.getOtsServiceMode());
		serviceDetails.setOtsServiceVirtualLocation(otsService.getOtsServiceVirtualLocation() == null ? "" : otsService.getOtsServiceVirtualLocation());
		serviceDetails.setOtsServiceCustomerLocation(otsService.getOtsServiceCustomerLocation() == null ? "" : otsService.getOtsServiceCustomerLocation().toString());
		serviceDetails.setOtsServiceCompanyName(otsService.getOtsServiceCompanyName() == null ? "" : otsService.getOtsServiceCompanyName());
		serviceDetails.setOtsServiceCompanyAddress(otsService.getOtsServiceCompanyAddress() == null ? "" : otsService.getOtsServiceCompanyAddress());
		serviceDetails.setOtsServiceCompanyState(otsService.getOtsServiceCompanyState() == null ? "" : otsService.getOtsServiceCompanyState());
		serviceDetails.setOtsServiceCompanyDistrict(otsService.getOtsServiceCompanyDistrict() == null ? "" : otsService.getOtsServiceCompanyDistrict());
		serviceDetails.setOtsServiceCompanyPincode(otsService.getOtsServiceCompanyPincode() == null ? "" : otsService.getOtsServiceCompanyPincode());
		serviceDetails.setOtsServiceCompanyContactNo(otsService.getOtsServiceCompanyContactNo() == null ? "" : otsService.getOtsServiceCompanyContactNo());
		serviceDetails.setOtsServiceCancellationAvailability(otsService.getOtsServiceCancellationAvailability() == null ? "" : otsService.getOtsServiceCancellationAvailability().toString());
		serviceDetails.setOtsServiceCancellationPolicy(otsService.getOtsServiceCancellationPolicy() == null ? "" : otsService.getOtsServiceCancellationPolicy());
		serviceDetails.setOtsServiceCancellationBefore(otsService.getOtsServiceCancellationBefore() == null ? "" : otsService.getOtsServiceCancellationBefore());
		serviceDetails.setOtsServiceCancellationFees(otsService.getOtsServiceCancellationFees() == null ? "" : otsService.getOtsServiceCancellationFees());
		serviceDetails.setOtsServiceRescheduleAvailability(otsService.getOtsServiceRescheduleAvailability() == null ? "" : otsService.getOtsServiceRescheduleAvailability().toString());
		serviceDetails.setOtsServiceReschedulePolicy(otsService.getOtsServiceReschedulePolicy() == null ? "" : otsService.getOtsServiceReschedulePolicy());
		serviceDetails.setOtsServiceRescheduleBefore(otsService.getOtsServiceRescheduleBefore() == null ? "" : otsService.getOtsServiceRescheduleBefore());
		serviceDetails.setOtsServiceRescheduleFees(otsService.getOtsServiceRescheduleFees() == null ? "" : otsService.getOtsServiceRescheduleFees());
		serviceDetails.setOtsServiceImage(otsService.getOtsServiceImage() == null ? "" : otsService.getOtsServiceImage());
		serviceDetails.setOtsMultiServiceImage1(otsService.getOtsMultiServiceImage1() == null ? "" : otsService.getOtsMultiServiceImage1());
		serviceDetails.setOtsMultiServiceImage2(otsService.getOtsMultiServiceImage2() == null ? "" : otsService.getOtsMultiServiceImage2());
		serviceDetails.setOtsMultiServiceImage3(otsService.getOtsMultiServiceImage3() == null ? "" : otsService.getOtsMultiServiceImage3());
		serviceDetails.setOtsMultiServiceImage4(otsService.getOtsMultiServiceImage4() == null ? "" : otsService.getOtsMultiServiceImage4());
		serviceDetails.setOtsMultiServiceImage5(otsService.getOtsMultiServiceImage5() == null ? "" : otsService.getOtsMultiServiceImage5());
		serviceDetails.setOtsMultiServiceImage6(otsService.getOtsMultiServiceImage6() == null ? "" : otsService.getOtsMultiServiceImage6());
		serviceDetails.setOtsMultiServiceImage7(otsService.getOtsMultiServiceImage7() == null ? "" : otsService.getOtsMultiServiceImage7());
		serviceDetails.setOtsMultiServiceImage8(otsService.getOtsMultiServiceImage8() == null ? "" : otsService.getOtsMultiServiceImage8());
		serviceDetails.setOtsMultiServiceImage9(otsService.getOtsMultiServiceImage9() == null ? "" : otsService.getOtsMultiServiceImage9());
		serviceDetails.setOtsMultiServiceImage10(otsService.getOtsMultiServiceImage10() == null ? "" : otsService.getOtsMultiServiceImage10());
		serviceDetails.setOtsServiceCreated(otsService.getOtsServiceCreated() == null ? "" : otsService.getOtsServiceCreated().toString());
		serviceDetails.setOtsServiceUpdated(otsService.getOtsServiceUpdated() == null ? "" : otsService.getOtsServiceUpdated().toString());
		serviceDetails.setOtsServiceSlot(otsService.getOtsServiceSlot() == null ? "" : otsService.getOtsServiceSlot());
	    
	    // Ensure otsServiceSlot is a String before checking isNotBlank
        if (otsService.getOtsServiceSlot() instanceof String 
                && StringUtils.isNotBlank((String) otsService.getOtsServiceSlot())) {
            // Converting String to JSON Object
            ObjectMapper objectMapper = new ObjectMapper();
            Object responseJson = null;
			try {
				responseJson = objectMapper.readValue((String) otsService.getOtsServiceSlot(), Object.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            serviceDetails.setOtsServiceSlot(responseJson);
        } else {
        	serviceDetails.setOtsServiceSlot("");
        }

	    return serviceDetails;
	
	}
	
	@Override
	public ServiceDetailsData getServiceDetailsByServiceId(String serviceId) {
		ServiceDetailsData servicedeatils = new ServiceDetailsData();
		try {
			OtsService otsService = new OtsService();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceId", UUID.fromString(serviceId));
			try {
				otsService = super.getResultByNamedQuery("OtsService.findByOtsServiceId", queryParameter);
			} catch (NoResultException e) {
				return null;
			}
			servicedeatils = convertServiceDetailsFromEntityToDomain(otsService);
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return servicedeatils;
	}
	
	@Override
	public List<ServiceDetailsData> getCategoryAndSubCategory(GetServiceCategorySubCategoryRequest getServiceCategorySubCategoryRequest) {
		List<ServiceDetailsData> serviceList = new ArrayList<ServiceDetailsData>();
		try {
			//To set default value as "1" for key "category"
			if(getServiceCategorySubCategoryRequest.getRequest().getSearchKey().equalsIgnoreCase("category")) {
				getServiceCategorySubCategoryRequest.getRequest().setSearchValue("1");
			}
			
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("search_key", getServiceCategorySubCategoryRequest.getRequest().getSearchKey());
			inParamMap.put("search_value", getServiceCategorySubCategoryRequest.getRequest().getSearchValue());

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_service_category_and_subcategory")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_key", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_value", Types.VARCHAR));
			
			//calling stored procedure and getting response
	        Map<String, Object> result = simpleJdbcCall.execute(inParamMap);
	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return serviceList;
	        }

	        String jsonResponse = resultSet.get(0).get("result").toString();
	        System.out.println("Parsed JSON Response: " + jsonResponse);

	        // Parse JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);
	        
	        // Parse service list
	        JsonNode serviceDetailsNode = rootNode.get("serviceDetails");
	        if (serviceDetailsNode != null && serviceDetailsNode.isArray()) {
	            for (JsonNode serviceNode : serviceDetailsNode) {
	                Map<String, Object> productMap = objectMapper.convertValue(serviceNode, new TypeReference<Map<String, Object>>() {});
	                serviceList.add(convertServiceDetailsFromProcedureToDomain(productMap));
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
		return serviceList;
	}

	private GetSlot convertServiceDetailsFromEntityToSlotDomain(OtsService otsService) throws IOException {
		GetSlot getServiceSlot=new GetSlot();
		getServiceSlot.setOtsServiceSlot(otsService.getOtsServiceSlot() == null ? "" : otsService.getOtsServiceSlot());
		    
	    // Ensure otsServiceSlot is a String before checking isNotBlank
        if (otsService.getOtsServiceSlot() instanceof String 
                && StringUtils.isNotBlank((String) otsService.getOtsServiceSlot())) {
            // Converting String to JSON Object
            ObjectMapper objectMapper = new ObjectMapper();
            Object responseJson = objectMapper.readValue((String) otsService.getOtsServiceSlot(), Object.class);
            getServiceSlot.setOtsServiceSlot(responseJson);
        } else {
        	getServiceSlot.setOtsServiceSlot("");
        }
		return getServiceSlot;
	}

	@Override
	public GetSlot getAllSlotsByServiceId(String serviceId) {
		OtsService otsService = new OtsService();
		GetSlot getSlot = new GetSlot();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceId", UUID.fromString(serviceId));
			try {
				otsService = super.getResultByNamedQuery("OtsService.findByOtsServiceId",queryParameter);
			} catch (NoResultException e) {
				return null;
			}
			getSlot = convertServiceDetailsFromEntityToSlotDomain(otsService);
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return getSlot;
	}

	@Override
	public List<ServiceDetailsData> getAllServices() {
		List<ServiceDetailsData> serviceDetailsDatas = new ArrayList<ServiceDetailsData>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			List<OtsService> otsServicesList = super.getResultListByNamedQuery("OtsService.getAllServices", queryParameter);
			serviceDetailsDatas = otsServicesList.stream()
					.map(otsService -> convertServiceDetailsFromEntityToDomain(otsService))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceDetailsDatas;
	}

	@Override
	public List<ServiceDetailsData> getAllServicesByProviderId(String provicerId) {
		List<ServiceDetailsData> serviceDetailsDatas = new ArrayList<ServiceDetailsData>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProviderId", UUID.fromString(provicerId));

			List<OtsService> otsServicesList = super.getResultListByNamedQuery("OtsService.getAllServicesByProviderId", queryParameter);
			serviceDetailsDatas = otsServicesList.stream()
					.map(otsService -> convertServiceDetailsFromEntityToDomain(otsService))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceDetailsDatas;
	}

	@Override
	public List<ServiceDetailsData> getAllCategoryAndSubCategory(String serviceLevelId) {
		List<ServiceDetailsData> serviceDetailsDatas = new ArrayList<ServiceDetailsData>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsServiceLevelId", Integer.parseInt(serviceLevelId));

			List<OtsService> otsServicesList = super.getResultListByNamedQuery("OtsService.getAllServicesCategoryAndSubcategory", queryParameter);
			serviceDetailsDatas = otsServicesList.stream().map(otsService -> convertServiceDetailsFromEntityToDomain(otsService)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceDetailsDatas;
	}

	@Transactional
	@Override
	public String updateServiceStatus(UpdateServiceStatusRequestModel updateServiceStatusRequestModel) {
		try {
			Query query = super.getEntityManager().createNamedQuery("OtsService.updateServiceStatus");
			query.setParameter("otsServiceId", UUID.fromString(updateServiceStatusRequestModel.getRequest().getServiceId()));
			query.setParameter("otsServiceStatus", updateServiceStatusRequestModel.getRequest().getStatus());

			int updatedCount = query.executeUpdate();

			if (updatedCount > 0) {
				return "Service Updated Successfully";
			} else {
				return "No matching service found to update";
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
	public List<ServiceDetailsData> getServicesByProviderAndStatus(GetServicesByProviderAndStatusRequest getServicesByProviderAndStatusRequest) {
		List<ServiceDetailsData> serviceDetailsDatas = new ArrayList<ServiceDetailsData>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProviderId", UUID.fromString(getServicesByProviderAndStatusRequest.getRequest().getProviderId()));
			queryParameter.put("otsServiceStatus", getServicesByProviderAndStatusRequest.getRequest().getStatus());

			List<OtsService> otsServicesList = super.getResultListByNamedQuery("OtsService.getServicesByProviderAndStatus",queryParameter);
			serviceDetailsDatas = otsServicesList.stream().map(otsService -> convertServiceDetailsFromEntityToDomain(otsService)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return serviceDetailsDatas;
	}
	
	@Override
	public CategoryDetails getCategoryForServiceId(String serviceId) {
		CategoryDetails categoryDetails = new CategoryDetails();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("service_id",serviceId);
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("getCategoryForServiceId")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_id", Types.OTHER));
			
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			if(out.size() == 0) {
				return null;
			}else {
				categoryDetails.setCategoryId(out.get(0).get("category_id").toString());
				categoryDetails.setCategoryName(out.get(0).get("category_name").toString());
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return categoryDetails;
	}
	
	@Override
	public SubCategoryDetails getSubCategoryForServiceId(String serviceId) {
		SubCategoryDetails subcategoryDetails = new SubCategoryDetails();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("service_id",serviceId);
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("getSubCategoryForServiceId")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("service_id", Types.OTHER));
			
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			if(out.size() == 0) {
				return null;
			}else {
				subcategoryDetails.setSubCategoryId((out.get(0).get("subCategory_id").toString()));
				subcategoryDetails.setSubcategoryName((out.get(0).get("subCategory_name").toString()));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return subcategoryDetails;
	}
	
	@Override
	public List<ServiceDetailsData> getServicesBySubCategoryAndProvider(GetServicesBySubCategoryAndProviderRequest getServicesBySubCategoryAndProviderRequest) {
		List<ServiceDetailsData> serviceDetails = new ArrayList<ServiceDetailsData>();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("subcategory_id", UUID.fromString(getServicesBySubCategoryAndProviderRequest.getRequest().getSubcategoryId()));
			inParamMap.put("provider_id", UUID.fromString(getServicesBySubCategoryAndProviderRequest.getRequest().getProviderId()));

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_services_by_subcategory_and_provider")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("subcategory_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("provider_id", Types.OTHER));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			System.out.println("am printing ="+out.size());
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				serviceDetails.add(convertServiceDetailsFromProcedureToDomain(out.get(i)));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return serviceDetails;
	}

	@Override
	public List<ServiceDetailsData> getRecentlyAddedServiceList(String levelId) {
		List<ServiceDetailsData> serviceDetails = new ArrayList<ServiceDetailsData>();
		try 
		{
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("ots_service_level_id", Integer.parseInt(levelId));
			
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_recently_added_service_list")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("ots_service_level_id", Types.INTEGER));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				serviceDetails.add(convertServiceDetailsFromProcedureToDomain(out.get(i)));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return serviceDetails;
	}

	//getting provider services by subCategory for pagination
	@Override
	public ServiceDetailsBOResponse getServicesByProviderPagination(GetServicesByProviderPaginationRequest getServicesByProviderPaginationRequest) {
		ServiceDetailsBOResponse serviceDetailsBOResponse = new ServiceDetailsBOResponse();
		List<ServiceDetailsData> serviceList = new ArrayList<ServiceDetailsData>();
		try {
			// Extract and parse input parameters
	        String providerId = getServicesByProviderPaginationRequest.getRequest().getProviderId();
	        int pageNumber = Integer.parseInt(getServicesByProviderPaginationRequest.getRequest().getPageNumber());
	        int dataSize = Integer.parseInt(getServicesByProviderPaginationRequest.getRequest().getDataSize());

	        // Prepare input map
	        Map<String, Object> inParamMap = new HashMap<>();
	        inParamMap.put("provider_id", UUID.fromString(providerId));
	        inParamMap.put("page_number", pageNumber);
	        inParamMap.put("data_size", dataSize);

	        SimpleJdbcCall simpleJdbcCall;    	
			//To get All the products by distributor
			if(getServicesByProviderPaginationRequest.getRequest().getSubCategoryId()== null || getServicesByProviderPaginationRequest.getRequest().getSubCategoryId().equals("")) {
	            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withSchemaName("public")
	                .withFunctionName("get_services_by_provider_pagination")
	                .withoutProcedureColumnMetaDataAccess()
	                .declareParameters(
	                    new SqlParameter("provider_id", Types.OTHER),
	                    new SqlParameter("page_number", Types.INTEGER),
	                    new SqlParameter("data_size", Types.INTEGER)
	                );
	        } else {
	        	//To get All the Products under Sub Category by distributor
	            inParamMap.put("subcategory_id", UUID.fromString(getServicesByProviderPaginationRequest.getRequest().getSubCategoryId()));
	            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withSchemaName("public")
	                .withFunctionName("get_services_by_subcat_and_provider_pagination")
	                .withoutProcedureColumnMetaDataAccess()
	                .declareParameters(
	                    new SqlParameter("provider_id", Types.OTHER),
	                    new SqlParameter("subcategory_id", Types.OTHER),
	                    new SqlParameter("page_number", Types.INTEGER),
	                    new SqlParameter("data_size", Types.INTEGER)
	                );
	        }
			
			// Execute the function
	        Map<String, Object> result = simpleJdbcCall.execute(inParamMap);

	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return serviceDetailsBOResponse;
	        }

	        String jsonResponse = resultSet.get(0).get("result").toString();
	        System.out.println("Parsed JSON Response: " + jsonResponse);

	        // Parse JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);

	        // Set total count & pages
	        serviceDetailsBOResponse.setTotalServiceCount(rootNode.get("totalServicesCount").asText());
	        serviceDetailsBOResponse.setTotalPages(rootNode.get("totalPages").asText());

	        // Parse service list
	        JsonNode serviceDetailsNode = rootNode.get("serviceDetails");
	        if (serviceDetailsNode != null && serviceDetailsNode.isArray()) {
	            for (JsonNode serviceNode : serviceDetailsNode) {
	                Map<String, Object> productMap = objectMapper.convertValue(serviceNode, new TypeReference<Map<String, Object>>() {});
	                serviceList.add(convertServiceDetailsFromProcedureToDomain(productMap));
	            }
	        }

	        serviceDetailsBOResponse.setServiceDetails(serviceList);
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return serviceDetailsBOResponse;
	}

}
