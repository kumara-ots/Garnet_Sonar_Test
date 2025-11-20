package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceOrderDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AssignServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CancelServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateServiceOrderRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceOrderDetailsDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsService;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceOrder;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceOrderDetails;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ServiceOrderDetailsDAOImpl extends AbstractIptDao<OtsServiceOrderDetails, String> implements ServiceOrderDetailsDAO{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public ServiceOrderDetailsDAOImpl() {
		super(OtsServiceOrderDetails.class);	
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
	
	private ServiceOrderDetails convertServiceOrderDetailsFromEntityToDomain(OtsServiceOrderDetails otsServiceOrderDetails) {
		ServiceOrderDetails orderProductModel = new ServiceOrderDetails();
        
		orderProductModel.setOtsServiceOrderDetailsId(otsServiceOrderDetails.getOtsServiceOrderDetailsId() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderDetailsId().toString());
	    orderProductModel.setOtsServiceOrderId(otsServiceOrderDetails.getOtsServiceOrderId() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderId().getOtsServiceOrderId().toString());
	    orderProductModel.setOtsServiceOrderTransactionId(otsServiceOrderDetails.getOtsServiceOrderTransactionId() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderTransactionId());
	    orderProductModel.setOtsServiceOrderPaymentStatus(otsServiceOrderDetails.getOtsServiceOrderPaymentStatus() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderPaymentStatus()); 
	    orderProductModel.setOtsProviderId(otsServiceOrderDetails.getOtsProviderId() == null ? "" : otsServiceOrderDetails.getOtsProviderId().getOtsUsersId().toString());
	    orderProductModel.setOtsServiceId(otsServiceOrderDetails.getOtsServiceId() == null ? "" : otsServiceOrderDetails.getOtsServiceId().getOtsServiceId().toString());
	    orderProductModel.setOtsServiceDayOfWeek(otsServiceOrderDetails.getOtsServiceDayOfWeek() == null ? "" : otsServiceOrderDetails.getOtsServiceDayOfWeek());
	    orderProductModel.setOtsServiceStartTime(otsServiceOrderDetails.getOtsServiceStartTime() == null ? "" : otsServiceOrderDetails.getOtsServiceStartTime());
	    orderProductModel.setOtsServiceEndTime(otsServiceOrderDetails.getOtsServiceEndTime() == null ? "" : otsServiceOrderDetails.getOtsServiceEndTime());
	    orderProductModel.setOtsServiceBookingStatus(otsServiceOrderDetails.getOtsServiceBookingStatus() == null ? "" : otsServiceOrderDetails.getOtsServiceBookingStatus());
	    orderProductModel.setOtsServiceBookedDate(otsServiceOrderDetails.getOtsServiceBookedDate() == null ? "" : otsServiceOrderDetails.getOtsServiceBookedDate());
	    orderProductModel.setOtsServiceOrderStatus(otsServiceOrderDetails.getOtsServiceOrderStatus() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderStatus());
	    orderProductModel.setOtsServiceOrderedDate(otsServiceOrderDetails.getOtsServiceOrderedDate() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderedDate().toString());
	    orderProductModel.setOtsServiceOrderAcceptedDate(otsServiceOrderDetails.getOtsServiceOrderAcceptedDate() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderAcceptedDate().toString());
	    orderProductModel.setOtsServiceOrderAssignedDate(otsServiceOrderDetails.getOtsServiceOrderAssignedDate() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderAssignedDate().toString());
	    orderProductModel.setOtsserviceorderinProgressdate(otsServiceOrderDetails.getOtsServiceOrderInProgressDate() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderInProgressDate().toString());
	    orderProductModel.setOtsServiceOrderCompletedDate(otsServiceOrderDetails.getOtsServiceOrderCompletedDate() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderCompletedDate().toString());
	    orderProductModel.setOtsServiceOrderCost(otsServiceOrderDetails.getOtsServiceOrderCost() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderCost().toString());
	    orderProductModel.setOtsServiceBasePrice(otsServiceOrderDetails.getOtsServiceBasePrice() == null ? "" : otsServiceOrderDetails.getOtsServiceBasePrice().toString());
	    orderProductModel.setOtsServiceDiscountPercentage(otsServiceOrderDetails.getOtsServiceDiscountPercentage() == null ? "" : otsServiceOrderDetails.getOtsServiceDiscountPercentage());
	    orderProductModel.setOtsServiceDiscountPrice(otsServiceOrderDetails.getOtsServiceDiscountPrice() == null ? "" : otsServiceOrderDetails.getOtsServiceDiscountPrice().toString());
	    orderProductModel.setOtsServiceStrikenPrice(otsServiceOrderDetails.getOtsServiceStrikenPrice() == null ? "" : otsServiceOrderDetails.getOtsServiceStrikenPrice().toString());
	    orderProductModel.setOtsServiceFinalPriceWithoutGst(otsServiceOrderDetails.getOtsServiceFinalPriceWithoutGst() == null ? "" : otsServiceOrderDetails.getOtsServiceFinalPriceWithoutGst().toString());
	    orderProductModel.setOtsServiceGstPercentage(otsServiceOrderDetails.getOtsServiceGstPercentage() == null ? "" : otsServiceOrderDetails.getOtsServiceGstPercentage());
	    orderProductModel.setOtsServiceGstPrice(otsServiceOrderDetails.getOtsServiceGstPrice() == null ? "" : otsServiceOrderDetails.getOtsServiceGstPrice().toString());
	    orderProductModel.setOtsServiceFinalPriceWithGst(otsServiceOrderDetails.getOtsServiceFinalPriceWithGst() == null ? "" : otsServiceOrderDetails.getOtsServiceFinalPriceWithGst().toString());
	    orderProductModel.setOtsServiceDuration(otsServiceOrderDetails.getOtsServiceDuration() == null ? "" : otsServiceOrderDetails.getOtsServiceDuration());
	    orderProductModel.setOtsServiceMode(otsServiceOrderDetails.getOtsServiceMode() == null ? "" : otsServiceOrderDetails.getOtsServiceMode());
	    orderProductModel.setOtsServiceVirtualLocation(otsServiceOrderDetails.getOtsServiceVirtualLocation() == null ? "" : otsServiceOrderDetails.getOtsServiceVirtualLocation());
	    orderProductModel.setOtsServiceRescheduleStartTime(otsServiceOrderDetails.getOtsServiceRescheduleStartTime() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleStartTime());
	    orderProductModel.setOtsServiceRescheduleEndTime(otsServiceOrderDetails.getOtsServiceRescheduleEndTime() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleEndTime());
	    orderProductModel.setOtsServiceRescheduleRequestedBy(otsServiceOrderDetails.getOtsServiceRescheduleRequestedBy() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleRequestedBy());
	    orderProductModel.setOtsServiceRescheduleApprovedBy(otsServiceOrderDetails.getOtsServiceRescheduleApprovedBy() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleApprovedBy());
        orderProductModel.setOtsServiceRescheduleRequestedDate(otsServiceOrderDetails.getOtsServiceRescheduleRequestedDate() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleRequestedDate().toString());
	    orderProductModel.setOtsServiceRescheduleAcceptedDate(otsServiceOrderDetails.getOtsServiceRescheduleAcceptedDate() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleAcceptedDate().toString());
	    orderProductModel.setOtsServiceRescheduleRejectedDate(otsServiceOrderDetails.getOtsServiceRescheduleRejectedDate() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleRejectedDate().toString());
        orderProductModel.setOtsServiceOrderCancelledBy(otsServiceOrderDetails.getOtsServiceOrderCancelledBy() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderCancelledBy());
        orderProductModel.setOtsServiceOrderCancelledDate(otsServiceOrderDetails.getOtsServiceOrderCancelledDate() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderCancelledDate().toString());
	    orderProductModel.setOtsServiceOrderCancellationReason(otsServiceOrderDetails.getOtsServiceOrderCancellationReason() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderCancellationReason());
	    orderProductModel.setOtsServiceCancellationAvailability(otsServiceOrderDetails.getOtsServiceCancellationAvailability() == null ? "" : otsServiceOrderDetails.getOtsServiceCancellationAvailability().toString());
	    orderProductModel.setOtsServiceCancellationBefore(otsServiceOrderDetails.getOtsServiceCancellationBefore() == null ? "" : otsServiceOrderDetails.getOtsServiceCancellationBefore());
	    orderProductModel.setOtsServiceCancellationFees(otsServiceOrderDetails.getOtsServiceCancellationFees() == null ? "" : otsServiceOrderDetails.getOtsServiceCancellationFees().toString());
	    orderProductModel.setOtsServiceRescheduleAvailability(otsServiceOrderDetails.getOtsServiceRescheduleAvailability() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleAvailability().toString());
	    orderProductModel.setOtsServiceRescheduleBefore(otsServiceOrderDetails.getOtsServiceRescheduleBefore() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleBefore());
	    orderProductModel.setOtsServiceRescheduleFees(otsServiceOrderDetails.getOtsServiceRescheduleFees() == null ? "" : otsServiceOrderDetails.getOtsServiceRescheduleFees().toString());
	    orderProductModel.setOtsServiceOrderRefundStatus(otsServiceOrderDetails.getOtsServiceOrderRefundStatus() == null ? "" : otsServiceOrderDetails.getOtsServiceOrderRefundStatus());

	    return orderProductModel;
	}
	
	@Transactional
	@Override	
	public ServiceOrderDetails insertServiceOrderDetails(ServiceOrderDetails serviceOrderDetails) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(utilDate.getTime());
		
		OtsServiceOrderDetails otsServiceOrderDetails = new OtsServiceOrderDetails();

		try {
			System.out.println("providerId = "+serviceOrderDetails.getOtsProviderId());
			OtsUsers providerId = new OtsUsers();
			providerId.setOtsUsersId(UUID.fromString(serviceOrderDetails.getOtsProviderId()));
			otsServiceOrderDetails.setOtsProviderId(providerId);
	
			System.out.println("OtsServiceOrderId = "+serviceOrderDetails.getOtsServiceOrderId());
			OtsServiceOrder serviceOrderId = new OtsServiceOrder();
			serviceOrderId.setOtsServiceOrderId(UUID.fromString(serviceOrderDetails.getOtsServiceOrderId()));
			otsServiceOrderDetails.setOtsServiceOrderId(serviceOrderId);
			
			OtsService serviceId = new OtsService ();
			serviceId.setOtsServiceId(UUID.fromString(serviceOrderDetails.getOtsServiceId()));
			otsServiceOrderDetails.setOtsServiceId(serviceId);
		  
			otsServiceOrderDetails.setOtsServiceOrderTransactionId(getValueOrNull(serviceOrderDetails.getOtsServiceOrderTransactionId()));
			otsServiceOrderDetails.setOtsServiceOrderPaymentStatus(getValueOrNull(serviceOrderDetails.getOtsServiceOrderPaymentStatus()));
			otsServiceOrderDetails.setOtsServiceDayOfWeek(getValueOrNull(serviceOrderDetails.getOtsServiceDayOfWeek()));
			otsServiceOrderDetails.setOtsServiceStartTime(getValueOrNull(serviceOrderDetails.getOtsServiceStartTime()));
			otsServiceOrderDetails.setOtsServiceEndTime(getValueOrNull(serviceOrderDetails.getOtsServiceEndTime()));
			otsServiceOrderDetails.setOtsServiceBookedDate(getValueOrNull(serviceOrderDetails.getOtsServiceBookedDate()));
			otsServiceOrderDetails.setOtsServiceBookingStatus("Booked");
			otsServiceOrderDetails.setOtsServiceOrderStatus(getValueOrNull(serviceOrderDetails.getOtsServiceOrderStatus()));
			otsServiceOrderDetails.setOtsServiceOrderedDate(currentDateTime);
			otsServiceOrderDetails.setOtsServiceOrderCost(getBigDecimalOrNull(serviceOrderDetails.getOtsServiceOrderCost()));
			otsServiceOrderDetails.setOtsServiceBasePrice(getBigDecimalOrNull(serviceOrderDetails.getOtsServiceBasePrice()));
			otsServiceOrderDetails.setOtsServiceDiscountPercentage(getValueOrNull(serviceOrderDetails.getOtsServiceDiscountPercentage()));
			otsServiceOrderDetails.setOtsServiceDiscountPrice(getBigDecimalOrNull(serviceOrderDetails.getOtsServiceDiscountPrice()));
			otsServiceOrderDetails.setOtsServiceStrikenPrice(getBigDecimalOrNull(serviceOrderDetails.getOtsServiceStrikenPrice()));
			otsServiceOrderDetails.setOtsServiceFinalPriceWithoutGst(getBigDecimalOrNull(serviceOrderDetails.getOtsServiceFinalPriceWithoutGst()));
			otsServiceOrderDetails.setOtsServiceGstPercentage(getValueOrNull(serviceOrderDetails.getOtsServiceGstPercentage()));
			otsServiceOrderDetails.setOtsServiceGstPrice(getBigDecimalOrNull(serviceOrderDetails.getOtsServiceGstPrice()));
			otsServiceOrderDetails.setOtsServiceFinalPriceWithGst(getBigDecimalOrNull(serviceOrderDetails.getOtsServiceFinalPriceWithGst()));
			otsServiceOrderDetails.setOtsServiceDuration(getValueOrNull(serviceOrderDetails.getOtsServiceDuration()));
			otsServiceOrderDetails.setOtsServiceMode(getValueOrNull(serviceOrderDetails.getOtsServiceMode()));
			otsServiceOrderDetails.setOtsServiceVirtualLocation(getValueOrNull(serviceOrderDetails.getOtsServiceVirtualLocation()));
			otsServiceOrderDetails.setOtsServiceCancellationAvailability(getBooleanOrNull(serviceOrderDetails.getOtsServiceCancellationAvailability()));
			otsServiceOrderDetails.setOtsServiceCancellationBefore(getValueOrNull(serviceOrderDetails.getOtsServiceCancellationBefore()));
			otsServiceOrderDetails.setOtsServiceCancellationFees(getBigDecimalOrNull(serviceOrderDetails.getOtsServiceCancellationFees()));
			otsServiceOrderDetails.setOtsServiceRescheduleAvailability(getBooleanOrNull(serviceOrderDetails.getOtsServiceRescheduleAvailability()));
			otsServiceOrderDetails.setOtsServiceRescheduleBefore(getValueOrNull(serviceOrderDetails.getOtsServiceRescheduleBefore()));
			otsServiceOrderDetails.setOtsServiceRescheduleFees(getBigDecimalOrNull(serviceOrderDetails.getOtsServiceRescheduleFees()));

			save(otsServiceOrderDetails);
			ServiceOrderDetails ServiceOrderDetails = convertServiceOrderDetailsFromEntityToDomain(otsServiceOrderDetails);
			return ServiceOrderDetails;
		}catch(Exception e){
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public ServiceOrderDetails getServiceOrderDeatilsByServiceOrderId(String serviceorderId) {
		try {
			ServiceOrderDetails serviceOrderDetails = new ServiceOrderDetails();
			OtsServiceOrderDetails otsServiceOrderDetails = new OtsServiceOrderDetails();
			Map<String, Object> queryParameter = new HashMap<>();	
			OtsServiceOrder otsServiceOrderId = new OtsServiceOrder();
			otsServiceOrderId.setOtsServiceOrderId(UUID.fromString(serviceorderId));
			queryParameter.put("otsServiceOrderId", otsServiceOrderId);
			otsServiceOrderDetails = super.getResultByNamedQuery("OtsServiceOrderDetails.findByotsServiceOrderDetailsbyOrderId", queryParameter);
			serviceOrderDetails = convertServiceOrderDetailsFromEntityToDomain(otsServiceOrderDetails);
			return serviceOrderDetails;
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Transactional
	@Override
	public String updateServiceOrderStatus(UpdateServiceOrderRequest updateServiceOrderRequest) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(utilDate.getTime());
		try {
			OtsServiceOrderDetails otsServiceOrderDetails = new OtsServiceOrderDetails();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsServiceOrder otsServiceOrderId = new OtsServiceOrder();
			otsServiceOrderId.setOtsServiceOrderId(UUID.fromString(updateServiceOrderRequest.getRequest().getOtsServiceOrderId()));
			queryParameter.put("otsServiceOrderId", otsServiceOrderId);

			try {
				otsServiceOrderDetails = super.getResultByNamedQuery("OtsServiceOrderDetails.getServiceOrderDetailsByOrderId", queryParameter);
			} catch (NoResultException e) {
				return null;
			}
			if (updateServiceOrderRequest.getRequest().getOtsServiceOrderStatus().equalsIgnoreCase("Accepted")) {
				otsServiceOrderDetails.setOtsServiceOrderStatus("Accepted");
				otsServiceOrderDetails.setOtsServiceOrderAcceptedDate(currentDateTime);
			}
			
			if (updateServiceOrderRequest.getRequest().getOtsServiceOrderStatus().equalsIgnoreCase("InProgress")) {
				otsServiceOrderDetails.setOtsServiceOrderStatus("InProgress");
				otsServiceOrderDetails.setOtsServiceOrderInProgressDate(currentDateTime);
			}
			
			if((updateServiceOrderRequest.getRequest().getOtsServiceOrderStatus().equalsIgnoreCase("Closed"))) {
				otsServiceOrderDetails.setOtsServiceOrderStatus("Closed");
				otsServiceOrderDetails.setOtsServiceOrderCompletedDate(currentDateTime);
			}
			save(otsServiceOrderDetails);

			return "Updated";
		} catch (Exception e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			return "Not Updated";
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			return "Not Updated";
		}
	}

	@Transactional
	@Override
	public String assignServiceOrder(AssignServiceOrderRequest assignServiceOrderRequest) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(utilDate.getTime());
		try {
			OtsServiceOrderDetails otsServiceOrderDetails = new OtsServiceOrderDetails();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsServiceOrder otsServiceOrderId = new OtsServiceOrder();
			otsServiceOrderId.setOtsServiceOrderId(UUID.fromString(assignServiceOrderRequest.getRequest().getOtsServiceOrderId()));
			queryParameter.put("otsServiceOrderId", otsServiceOrderId);
			try {
				otsServiceOrderDetails = super.getResultByNamedQuery("OtsServiceOrderDetails.getServiceOrderDetailsByOrderId", queryParameter);
			} catch (NoResultException e) {
				return null;
			}
			OtsUsers otsEmployeeId = new OtsUsers();
			otsEmployeeId.setOtsUsersId(UUID.fromString(assignServiceOrderRequest.getRequest().getOtsEmployeeId()));
			
			otsServiceOrderDetails.setOtsEmployeeId(otsEmployeeId);
			otsServiceOrderDetails.setOtsServiceOrderAssignedDate(currentDateTime);
			otsServiceOrderDetails.setOtsServiceOrderStatus("Assigned");
			save(otsServiceOrderDetails);

			return "Updated";
		} catch (Exception e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			return "Not Updated";
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			return "Not Updated";
		}
	}
	
	@Transactional
	@Override
	public String cancelServiceOrder(CancelServiceOrderRequest cancelServiceOrderRequest) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(utilDate.getTime());
		try {
			OtsServiceOrderDetails otsServiceOrderDetails = new OtsServiceOrderDetails();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsServiceOrder otsServiceOrderId = new OtsServiceOrder();
			otsServiceOrderId.setOtsServiceOrderId(UUID.fromString(cancelServiceOrderRequest.getRequest().getOtsServiceOrderId()));
			queryParameter.put("otsServiceOrderId", otsServiceOrderId);

			try {
				otsServiceOrderDetails = super.getResultByNamedQuery("OtsServiceOrderDetails.getServiceOrderDetailsByOrderId", queryParameter);
			} catch (NoResultException e) {
				return null;
			}
			otsServiceOrderDetails.setOtsServiceOrderCancelledBy(cancelServiceOrderRequest.getRequest().getOtsServiceOrderCancelledBy());
			otsServiceOrderDetails.setOtsServiceOrderCancellationReason(cancelServiceOrderRequest.getRequest().getOtsServiceOrderCancellationReason());
			otsServiceOrderDetails.setOtsServiceOrderCancelledDate(currentDateTime);
			otsServiceOrderDetails.setOtsServiceOrderStatus("Cancelled");
			
			save(otsServiceOrderDetails);

			return "Updated";
		} catch (Exception e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			return "Not Updated";
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			return "Not Updated";
		}
	}
	
	@Override
	public List<ServiceOrderDetails> getServiceOrderDetailsByProviderAndStatus(String providerId, String serviceOrderStatus) {
		List<ServiceOrderDetails> serviceOrderList = new ArrayList<>();
		try {
			OtsUsers otsProviderId = new OtsUsers();
			otsProviderId.setOtsUsersId(UUID.fromString(providerId));
			
			Map<String, Object> queryParameter = new HashMap<>();	
			queryParameter.put("otsProviderId", otsProviderId);
			queryParameter.put("otsServiceOrderStatus", serviceOrderStatus);
			List<OtsServiceOrderDetails> otsServiceOrderDetails = super.getResultListByNamedQuery("OtsServiceOrderDetails.getServiceOrderDetailsByProviderAndStatus", queryParameter);
			serviceOrderList = otsServiceOrderDetails.stream().map(serviceOrderDetails -> convertServiceOrderDetailsFromEntityToDomain(serviceOrderDetails)).collect(Collectors.toList());
			
			return serviceOrderList;
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<ServiceOrderDetails> getServiceOrderDetailsByCustomerAndStatus(String customerId, String serviceOrderStatus) {
		List<ServiceOrderDetails> serviceOrderList = new ArrayList<>();
		try {
			OtsUsers otsCustomerId = new OtsUsers();
			otsCustomerId.setOtsUsersId(UUID.fromString(customerId));
			
			Map<String, Object> queryParameter = new HashMap<>();	
			queryParameter.put("otsServiceCustomerId", otsCustomerId);
			queryParameter.put("otsServiceOrderStatus", serviceOrderStatus);
			List<OtsServiceOrderDetails> otsServiceOrderDetails = super.getResultListByNamedQuery("OtsServiceOrderDetails.getServiceOrderDetailsByCustomerAndStatus", queryParameter);
			serviceOrderList = otsServiceOrderDetails.stream().map(serviceOrderDetails -> convertServiceOrderDetailsFromEntityToDomain(serviceOrderDetails)).collect(Collectors.toList());
			
			return serviceOrderList;
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
}


