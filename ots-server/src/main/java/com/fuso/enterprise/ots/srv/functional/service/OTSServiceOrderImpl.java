package com.fuso.enterprise.ots.srv.functional.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fuso.enterprise.ots.srv.api.model.domain.CustomerChangeAddress;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceDetailsData;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceOrder;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceOrderDetails;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSServiceOrder;
import com.fuso.enterprise.ots.srv.api.service.request.AssignServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CancelServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceCustomerOrdersByStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceOrderByProviderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.InsertServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateServiceOrderRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.CustomerChangeAddressDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServiceOrderDAO;
import com.fuso.enterprise.ots.srv.server.dao.ServiceOrderDetailsDAO;
import com.fuso.enterprise.ots.srv.server.util.EmailUtil;

@Service
public class OTSServiceOrderImpl implements OTSServiceOrder {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private EmailUtil emailUtil;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ServiceDAO serviceDAO;
	private ServiceOrderDAO serviceOrderDAO;
	private ServiceOrderDetailsDAO serviceOrderDetailsDAO;
	private CustomerChangeAddressDAO customerChangeAddressDAO;
	
	@Inject
	public OTSServiceOrderImpl(ServiceOrderDAO serviceOrderDAO,ServiceOrderDetailsDAO serviceOrderDetailsDAO,CustomerChangeAddressDAO customerChangeAddressDAO,
			ServiceDAO serviceDAO){
		this.serviceOrderDAO = serviceOrderDAO;
		this.serviceOrderDetailsDAO = serviceOrderDetailsDAO;
		this.customerChangeAddressDAO = customerChangeAddressDAO;
		this.serviceDAO = serviceDAO;
	}
	
	@Override
	public ServiceOrder insertServiceOrder(InsertServiceOrderRequest insertServiceOrderRequest) {
		ServiceOrder serviceOrder = new ServiceOrder();
		ServiceOrderDetails serviceOrderDetails = new ServiceOrderDetails(); 
		try {
			ServiceDetailsData serviceDetails = serviceDAO.getServiceDetailsByServiceId(insertServiceOrderRequest.getRequest().getOtsServiceId());
			
			if(serviceDetails.getOtsServiceMode().equalsIgnoreCase("Online")) {
				serviceOrderDetails.setOtsServiceVirtualLocation(serviceDetails.getOtsServiceVirtualLocation());
			}
			else if(serviceDetails.getOtsServiceMode().equalsIgnoreCase("Customer-Place")) {
				// Set delivery address from secondary address or default
			    List<CustomerChangeAddress> customerSecondaryAddress = customerChangeAddressDAO.getCustomerChangeAddressById(insertServiceOrderRequest.getRequest().getOtsCustomerChangeAddressId());
			    if (!customerSecondaryAddress.isEmpty()) {
			        CustomerChangeAddress address = customerSecondaryAddress.get(0);
			        serviceOrder.setOtsServiceCustomerName(address.getCustomerFirstName()+ " " + address.getCustomerSecondName());
			        serviceOrder.setOtsServiceCustomerContactNumber(address.getCustomerContactNo());
			        serviceOrder.setOtsServiceCustomerHouseNo(address.getOtsHouseNo());
			        serviceOrder.setOtsServiceCustomerBuildingName(address.getOtsBuildingName());
			        serviceOrder.setOtsServiceCustomerStreetName(address.getOtsStreetName());
			        serviceOrder.setOtsServiceCustomerCityName(address.getOtsCityName());
			        serviceOrder.setOtsServiceCustomerPincode(address.getOtsPinCode());
			        serviceOrder.setOtsServiceCustomerState(address.getOtsStateName());
			        serviceOrder.setOtsServiceCustomerDistrict(address.getOtsDistrictName());
			    }
			}
			else {
				serviceOrder.setOtsServiceCompanyName(serviceDetails.getOtsServiceCompanyName());
				serviceOrder.setOtsServiceCompanyAddress(serviceDetails.getOtsServiceCompanyAddress());
				serviceOrder.setOtsServiceCompanyContactNo(serviceDetails.getOtsServiceCompanyContactNo());
				serviceOrder.setOtsServiceCompanyState(serviceDetails.getOtsServiceCompanyState());
				serviceOrder.setOtsServiceCompanyDistrict(serviceDetails.getOtsServiceCompanyDistrict());
				serviceOrder.setOtsServiceCompanyPincode(serviceDetails.getOtsServiceCompanyPincode());
			}
			
			// Set Order Product Status based on payment status, If Order is COD then OP status will be set to "New", if its Online Payment OP status will be set to "Pending"
			if(insertServiceOrderRequest.getRequest().getOtsServiceOrderPaymentStatus() == null) {			
				serviceOrderDetails.setOtsServiceOrderStatus("Pending");
				serviceOrderDetails.setOtsServiceOrderPaymentStatus("Pending");
				serviceOrderDetails.setOtsServiceOrderTransactionId(insertServiceOrderRequest.getRequest().getOtsServiceOrderTransactionId());
			}else if(insertServiceOrderRequest.getRequest().getOtsServiceOrderPaymentStatus().equalsIgnoreCase("COD")){
				serviceOrderDetails.setOtsServiceOrderStatus("New");
				serviceOrderDetails.setOtsServiceOrderPaymentStatus(insertServiceOrderRequest.getRequest().getOtsServiceOrderPaymentStatus());
			}
			serviceOrder.setOtsServiceCustomerId(insertServiceOrderRequest.getRequest().getOtsCustomerId());
			serviceOrder.setOtsServiceImage(serviceDetails.getOtsServiceImage());
			serviceOrder.setOtsServiceName(serviceDetails.getOtsServiceName());
			serviceOrder.setOtsServiceDescription(serviceDetails.getOtsServiceDescription());
			serviceOrder.setOtsServiceDescriptionLong(serviceDetails.getOtsServiceDescriptionLong());
				
			ServiceOrder insertServiceOrder = serviceOrderDAO.insertServiceOrder(serviceOrder);	
			
			serviceOrderDetails.setOtsServiceOrderId(insertServiceOrder.getOtsServiceOrderId());
			serviceOrderDetails.setOtsServiceDuration(serviceDetails.getOtsServiceDuration());
			serviceOrderDetails.setOtsServiceMode(serviceDetails.getOtsServiceMode());
			serviceOrderDetails.setOtsServiceId(serviceDetails.getOtsServiceId());
			serviceOrderDetails.setOtsProviderId(serviceDetails.getOtsServiceProviderId());
			serviceOrderDetails.setOtsServiceDayOfWeek(insertServiceOrderRequest.getRequest().getOtsServiceDayOfWeek());
			serviceOrderDetails.setOtsServiceStartTime(insertServiceOrderRequest.getRequest().getOtsServiceStartTime());
			serviceOrderDetails.setOtsServiceEndTime(insertServiceOrderRequest.getRequest().getOtsServiceEndTime());
			serviceOrderDetails.setOtsServiceBookedDate(insertServiceOrderRequest.getRequest().getOtsServiceBookedDate());
			serviceOrderDetails.setOtsServiceOrderCost(serviceDetails.getOtsServiceFinalPriceWithGst());
			serviceOrderDetails.setOtsServiceBasePrice(serviceDetails.getOtsServiceBasePrice());
			serviceOrderDetails.setOtsEquipmentRentalCost(serviceDetails.getOtsEquipmentRentalCost());
			serviceOrderDetails.setOtsServiceDiscountPercentage(serviceDetails.getOtsServiceDiscountPercentage());
			serviceOrderDetails.setOtsServiceDiscountPrice(serviceDetails.getOtsServiceDiscountPrice());
			serviceOrderDetails.setOtsServiceStrikenPrice(serviceDetails.getOtsServiceStrikenPrice());
			serviceOrderDetails.setOtsServiceFinalPriceWithoutGst(serviceDetails.getOtsServiceFinalPriceWithoutGst());
			serviceOrderDetails.setOtsServiceGstPrice(serviceDetails.getOtsServiceGstPrice());
			serviceOrderDetails.setOtsServiceGstPercentage(serviceDetails.getOtsServiceGstPercentage());
			serviceOrderDetails.setOtsServiceFinalPriceWithGst(serviceDetails.getOtsServiceFinalPriceWithGst());
			
			serviceOrderDetails.setOtsServiceCancellationAvailability(serviceDetails.getOtsServiceCancellationAvailability());
			serviceOrderDetails.setOtsServiceCancellationBefore(serviceDetails.getOtsServiceCancellationBefore());
			serviceOrderDetails.setOtsServiceCancellationFees(serviceDetails.getOtsServiceCancellationFees());
			serviceOrderDetails.setOtsServiceRescheduleAvailability(serviceDetails.getOtsServiceRescheduleAvailability());
			serviceOrderDetails.setOtsServiceRescheduleBefore(serviceDetails.getOtsServiceRescheduleBefore());
			serviceOrderDetails.setOtsServiceRescheduleFees(serviceDetails.getOtsServiceRescheduleFees());
			
			ServiceOrderDetails  insertServiceOrderdeatils = serviceOrderDetailsDAO.insertServiceOrderDetails(serviceOrderDetails);	
			
			insertServiceOrder.setServiceOrderDetails(insertServiceOrderdeatils);
			
			return insertServiceOrder;
		}catch(Exception e){
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}
	}
			
	@Override
	public ServiceOrder getServiceOrderDetailsbyOrderId(String serviceOrderId) {
		try {
			// Get the main service order
			ServiceOrder orderDetails = serviceOrderDAO.getServiceOrderByOrderId(serviceOrderId);
			if(orderDetails == null) {
				return null;
			}
			// Get the related service order details
			ServiceOrderDetails serviceOrderDetails = serviceOrderDetailsDAO.getServiceOrderDeatilsByServiceOrderId(orderDetails.getOtsServiceOrderId());

			// Set details into the main service order details order object
			orderDetails.setServiceOrderDetails(serviceOrderDetails);

			return orderDetails;
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String updateServiceOrderStatus(UpdateServiceOrderRequest updateServiceOrderRequest) {
		try {
			String updateService = serviceOrderDetailsDAO.updateServiceOrderStatus(updateServiceOrderRequest);
			
			return updateService;	
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String assignServiceOrder(AssignServiceOrderRequest assignServiceOrderRequest) {
		try {
			String assignService = serviceOrderDetailsDAO.assignServiceOrder(assignServiceOrderRequest);
			
			return assignService;	
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String cancelServiceOrder(CancelServiceOrderRequest cancelServiceOrderRequest) {
		try {
			String cancelService = serviceOrderDetailsDAO.cancelServiceOrder(cancelServiceOrderRequest);
			
			return cancelService;	
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<ServiceOrder> getProviderServiceOrderByStatus(GetServiceOrderByProviderRequest getServiceOrderByProviderRequest) {
		try {
			List<ServiceOrder> serviceOrderList = new ArrayList<>();
			//To get Service Order Details by Provider & Service Order Status
			List<ServiceOrderDetails> serviceOrderDetails = serviceOrderDetailsDAO.getServiceOrderDetailsByProviderAndStatus(
					getServiceOrderByProviderRequest.getRequest().getOtsProviderId(),getServiceOrderByProviderRequest.getRequest().getOtsServiceOrderStatus());
			
			if (serviceOrderDetails.size() == 0) {
				return serviceOrderList;
			}
			
			for(int i=0; i<serviceOrderDetails.size(); i++) {
				//To get related service order details
				ServiceOrder serviceOrder = serviceOrderDAO.getServiceOrderByOrderId(serviceOrderDetails.get(i).getOtsServiceOrderId());
				
				// Set details into the main service order details order object
				serviceOrder.setServiceOrderDetails(serviceOrderDetails.get(i));
				
				serviceOrderList.add(serviceOrder);
			}

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
	public List<ServiceOrder> getCustomerServiceOrderByStatus(GetServiceCustomerOrdersByStatusRequest getServiceCustomerOrdersByStatusRequest) {
		try {
			List<ServiceOrder> serviceOrderList = new ArrayList<>();
			//To get Service Order Details by Provider & Service Order Status
			List<ServiceOrderDetails> serviceOrderDetails = serviceOrderDetailsDAO.getServiceOrderDetailsByCustomerAndStatus(
					getServiceCustomerOrdersByStatusRequest.getRequest().getOtsServiceCustomerId(),getServiceCustomerOrdersByStatusRequest.getRequest().getOtsServiceOrderStatus());
			
			if (serviceOrderDetails.size() == 0) {
				return serviceOrderList;
			}
			
			for(int i=0; i<serviceOrderDetails.size(); i++) {
				//To get related service order details
				ServiceOrder serviceOrder = serviceOrderDAO.getServiceOrderByOrderId(serviceOrderDetails.get(i).getOtsServiceOrderId());
				
				// Set details into the main service order details order object
				serviceOrder.setServiceOrderDetails(serviceOrderDetails.get(i));
				
				serviceOrderList.add(serviceOrder);
			}

			return serviceOrderList;
		}catch(Exception e){
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}	
			
	private ServiceOrder convertServiceOrderFromProcedureToDomain(Map<String, Object> out) {
		ServiceOrder serviceDetails = new ServiceOrder();
		
		serviceDetails.setOtsServiceOrderId(out.get("ots_service_order_id") == null ? "" : out.get("ots_service_order_id").toString());
		serviceDetails.setOtsServiceCustomerId(out.get("ots_service_customer_id") == null ? "" : out.get("ots_service_customer_id").toString());
		serviceDetails.setOtsServiceOrderNumber(out.get("ots_service_order_number") == null ? "" : out.get("ots_service_order_number").toString());
		serviceDetails.setOtsServiceCustomerName(out.get("ots_service_customer_name") == null ? "" : out.get("ots_service_customer_name").toString());
		serviceDetails.setOtsServiceCustomerContactNumber(out.get("ots_service_customer_contact_number") == null ? "" : out.get("ots_service_customer_contact_number").toString());
		serviceDetails.setOtsServiceCustomerHouseNo(out.get("ots_service_customer_house_no") == null ? "" : out.get("ots_service_customer_house_no").toString());
		serviceDetails.setOtsServiceCustomerBuildingName(out.get("ots_service_customer_building_name") == null ? "" : out.get("ots_service_customer_building_name").toString());
		serviceDetails.setOtsServiceCustomerStreetName(out.get("ots_service_customer_street_name") == null ? "" : out.get("ots_service_customer_street_name").toString());
		serviceDetails.setOtsServiceCustomerCityName(out.get("ots_service_customer_city_name") == null ? "" : out.get("ots_service_customer_city_name").toString());
		serviceDetails.setOtsServiceCustomerPincode(out.get("ots_service_customer_pincode") == null ? "" : out.get("ots_service_customer_pincode").toString());
		serviceDetails.setOtsServiceCustomerDistrict(out.get("ots_service_customer_district") == null ? "" : out.get("ots_service_customer_district").toString());
		serviceDetails.setOtsServiceCustomerState(out.get("ots_service_customer_state") == null ? "" : out.get("ots_service_customer_state").toString());
		serviceDetails.setOtsServiceCustomerChangeAddressId(out.get("ots_service_customer_change_address_id") == null ? "" : out.get("ots_service_customer_change_address_id").toString());
		serviceDetails.setOtsServiceImage(out.get("ots_service_image") == null ? "" : out.get("ots_service_image").toString());
		serviceDetails.setOtsServiceCompanyEmail(out.get("ots_service_company_email") == null ? "" : out.get("ots_service_company_email").toString());
		serviceDetails.setOtsServiceCompanyName(out.get("ots_service_company_name") == null ? "" : out.get("ots_service_company_name").toString());
		serviceDetails.setOtsServiceCompanyAddress(out.get("ots_service_company_address") == null ? "" : out.get("ots_service_company_address").toString());
		serviceDetails.setOtsServiceCompanyDistrict(out.get("ots_service_company_district") == null ? "" : out.get("ots_service_company_district").toString());
		serviceDetails.setOtsServiceCompanyState(out.get("ots_service_company_state") == null ? "" : out.get("ots_service_company_state").toString());
		serviceDetails.setOtsServiceCompanyPincode(out.get("ots_service_company_pincode") == null ? "" : out.get("ots_service_company_pincode").toString());
		serviceDetails.setOtsServiceCompanyContactNo(out.get("ots_service_company_contact_no") == null ? "" : out.get("ots_service_company_contact_no").toString());
		serviceDetails.setOtsServiceName(out.get("ots_service_name") == null ? "" : out.get("ots_service_name").toString());
		serviceDetails.setOtsServiceDescription(out.get("ots_service_description") == null ? "" : out.get("ots_service_description").toString());
		serviceDetails.setOtsServiceDescriptionLong(out.get("ots_service_description_long") == null ? "" : out.get("ots_service_description_long").toString());
		serviceDetails.setOtsServiceOrderCustomerInvoice(out.get("ots_service_order_customer_invoice") == null ? "" : out.get("ots_service_order_customer_invoice").toString());
		serviceDetails.setCustomerName(out.get("customer_name") == null ? "" : out.get("customer_name").toString());
		serviceDetails.setCustomerContactNo(out.get("customer_contact_no") == null ? "" : out.get("customer_contact_no").toString());
		serviceDetails.setCustomerEmailId(out.get("customer_email_id") == null ? "" : out.get("customer_email_id").toString());
		 return serviceDetails;
	}
	
	private ServiceOrderDetails convertServiceOrderDeatilsFromProcedureToDomain(Map<String, Object> out) {
		ServiceOrderDetails serviceOrderDetails = new ServiceOrderDetails();
		serviceOrderDetails.setOtsServiceOrderDetailsId(out.get("ots_service_order_details_id") == null ? "" : out.get("ots_service_order_details_id").toString());
		serviceOrderDetails.setOtsServiceOrderTransactionId(out.get("ots_service_order_transaction_id") == null ? "" : out.get("ots_service_order_transaction_id").toString());
		serviceOrderDetails.setOtsServiceOrderPaymentStatus(out.get("ots_service_order_payment_status") == null ? "" : out.get("ots_service_order_payment_status").toString());
		serviceOrderDetails.setOtsEmployeeId(out.get("ots_employee_id") == null ? "" : out.get("ots_employee_id").toString());
		serviceOrderDetails.setOtsProviderId(out.get("ots_provider_id") == null ? "" : out.get("ots_provider_id").toString());
		serviceOrderDetails.setOtsServiceOrderId(out.get("ots_service_order_id") == null ? "" : out.get("ots_service_order_id").toString());
		serviceOrderDetails.setOtsServiceId(out.get("ots_service_id") == null ? "" : out.get("ots_service_id").toString());
		serviceOrderDetails.setOtsServiceDayOfWeek(out.get("ots_service_day_of_week") == null ? "" : out.get("ots_service_day_of_week").toString());
		serviceOrderDetails.setOtsServiceStartTime(out.get("ots_service_start_time") == null ? "" : out.get("ots_service_start_time").toString());
		serviceOrderDetails.setOtsServiceEndTime(out.get("ots_service_end_time") == null ? "" : out.get("ots_service_end_time").toString());
		serviceOrderDetails.setOtsServiceBookingStatus(out.get("ots_service_booking_status") == null ? "" : out.get("ots_service_booking_status").toString());
		serviceOrderDetails.setOtsServiceBookedDate(out.get("ots_service_booked_date") == null ? "" : out.get("ots_service_booked_date").toString());
		serviceOrderDetails.setOtsServiceOrderStatus(out.get("ots_service_order_status") == null ? "" : out.get("ots_service_order_status").toString());
		serviceOrderDetails.setOtsServiceOrderedDate(out.get("ots_service_ordered_date") == null ? "" : out.get("ots_service_ordered_date").toString());
		serviceOrderDetails.setOtsServiceOrderAcceptedDate(out.get("ots_service_order_accepted_date") == null ? "" : out.get("ots_service_order_accepted_date").toString());
		serviceOrderDetails.setOtsServiceOrderAssignedDate(out.get("ots_service_order_assigned_date") == null ? "" : out.get("ots_service_order_assigned_date").toString());
		serviceOrderDetails.setOtsserviceorderinProgressdate(out.get("otsserviceorderin_progressdate") == null ? "" : out.get("otsserviceorderin_progressdate").toString());
		serviceOrderDetails.setOtsServiceOrderCompletedDate(out.get("ots_service_order_completed_date") == null ? "" : out.get("ots_service_order_completed_date").toString());
		serviceOrderDetails.setOtsServiceOrderCost(out.get("ots_service_order_cost") == null ? "" : out.get("ots_service_order_cost").toString());
		serviceOrderDetails.setOtsServiceBasePrice(out.get("ots_service_base_price") == null ? "" : out.get("ots_service_base_price").toString());
		serviceOrderDetails.setOtsEquipmentRentalCost(out.get("ots_equipment_rental_cost") == null ? "" : out.get("ots_equipment_rental_cost").toString());
		serviceOrderDetails.setOtsServiceDiscountPercentage(out.get("ots_service_discount_percentage") == null ? "" : out.get("ots_service_discount_percentage").toString());
		serviceOrderDetails.setOtsServiceDiscountPrice(out.get("ots_service_discount_price") == null ? "" : out.get("ots_service_discount_price").toString());
		serviceOrderDetails.setOtsServiceStrikenPrice(out.get("ots_service_striken_price") == null ? "" : out.get("ots_service_striken_price").toString());
		serviceOrderDetails.setOtsServiceFinalPriceWithoutGst(out.get("ots_service_final_price_without_gst") == null ? "" : out.get("ots_service_final_price_without_gst").toString());
		serviceOrderDetails.setOtsServiceGstPercentage(out.get("ots_service_gst_percentage") == null ? "" : out.get("ots_service_gst_percentage").toString());
		serviceOrderDetails.setOtsServiceGstPrice(out.get("ots_service_gst_price") == null ? "" : out.get("ots_service_gst_price").toString());
		serviceOrderDetails.setOtsServiceFinalPriceWithGst(out.get("ots_service_final_price_with_gst") == null ? "" : out.get("ots_service_final_price_with_gst").toString());
		serviceOrderDetails.setOtsServiceDuration(out.get("ots_service_duration") == null ? "" : out.get("ots_service_duration").toString());
		serviceOrderDetails.setOtsServiceMode(out.get("ots_service_mode") == null ? "" : out.get("ots_service_mode").toString());
		serviceOrderDetails.setOtsServiceVirtualLocation(out.get("ots_service_virtual_location") == null ? "" : out.get("ots_service_virtual_location").toString());
		serviceOrderDetails.setOtsServiceRescheduleStartTime(out.get("ots_service_reschedule_start_time") == null ? "" : out.get("ots_service_reschedule_start_time").toString());
		serviceOrderDetails.setOtsServiceRescheduleEndTime(out.get("ots_service_reschedule_end_time") == null ? "" : out.get("ots_service_reschedule_end_time").toString());
		serviceOrderDetails.setOtsServiceRescheduleRequestedBy(out.get("ots_service_reschedule_requested_by") == null ? "" : out.get("ots_service_reschedule_requested_by").toString());
		serviceOrderDetails.setOtsServiceRescheduleApprovedBy(out.get("ots_service_reschedule_approved_by") == null ? "" : out.get("ots_service_reschedule_approved_by").toString());
		serviceOrderDetails.setOtsServiceRescheduleRequestedDate(out.get("ots_service_reschedule_requested_date") == null ? "" : out.get("ots_service_reschedule_requested_date").toString());
		serviceOrderDetails.setOtsServiceRescheduleAcceptedDate(out.get("ots_service_reschedule_accepted_date") == null ? "" : out.get("ots_service_reschedule_accepted_date").toString());
		serviceOrderDetails.setOtsServiceRescheduleRejectedDate(out.get("ots_service_reschedule_rejected_date") == null ? "" : out.get("ots_service_reschedule_rejected_date").toString());
		serviceOrderDetails.setOtsServiceOrderCancelledBy(out.get("ots_service_order_cancelled_by") == null ? "" : out.get("ots_service_order_cancelled_by").toString());
		serviceOrderDetails.setOtsServiceOrderCancelledDate(out.get("ots_service_order_cancelled_date") == null ? "" : out.get("ots_service_order_cancelled_date").toString());
		serviceOrderDetails.setOtsServiceOrderCancellationReason(out.get("ots_service_order_cancellation_reason") == null ? "" : out.get("ots_service_order_cancellation_reason").toString());
		serviceOrderDetails.setOtsServiceCancellationAvailability(out.get("ots_service_cancellation_availability") == null ? "" : out.get("ots_service_cancellation_availability").toString());
		serviceOrderDetails.setOtsServiceCancellationBefore(out.get("ots_service_cancellation_before") == null ? "" : out.get("ots_service_cancellation_before").toString());
		serviceOrderDetails.setOtsServiceCancellationFees(out.get("ots_service_cancellation_fees") == null ? "" : out.get("ots_service_cancellation_fees").toString());
		serviceOrderDetails.setOtsServiceRescheduleAvailability(out.get("ots_service_reschedule_availability") == null ? "" : out.get("ots_service_reschedule_availability").toString());
		serviceOrderDetails.setOtsServiceRescheduleBefore(out.get("ots_service_reschedule_before") == null ? "" : out.get("ots_service_reschedule_before").toString());
		serviceOrderDetails.setOtsServiceRescheduleFees(out.get("ots_service_reschedule_fees") == null ? "" : out.get("ots_service_reschedule_fees").toString());
		serviceOrderDetails.setOtsServiceOrderRefundStatus(out.get("ots_service_order_refund_status") == null ? "" : out.get("ots_service_order_refund_status").toString());
         return serviceOrderDetails;
	}
			
}
