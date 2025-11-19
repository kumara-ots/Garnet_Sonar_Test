package com.fuso.enterprise.ots.srv.rest.ws.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceOrder;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSServiceOrder;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSServicesService;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSUserService;
import com.fuso.enterprise.ots.srv.api.service.request.AssignServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CancelServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceCustomerOrdersByStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceOrderByProviderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.InsertServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.response.UserDataBOResponse;
import com.fuso.enterprise.ots.srv.server.util.ResponseWrapper;

@Configuration
public class OTSService_Order_WsImpl implements OTSService_Order_Ws{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
    ResponseWrapper responseWrapper;
    
    @Inject
    OTSServicesService otsService;
    
    @Inject
    OTSServiceOrder otsServiceOrder;
    
    @Inject
    OTSUserService otsUserService;
    
    public Response buildResponse(Object data,String description) {
		ResponseWrapper wrapper = new ResponseWrapper(200,description, data);
		return Response.ok(wrapper).build();
	}
	
	public Response buildResponse(int code,String description) {
		ResponseWrapper wrapper = new ResponseWrapper(code,description);
		return Response.ok(wrapper).build();
	}
	
	public Response buildResponse(int code,Object data,String description) {
		ResponseWrapper wrapper = new ResponseWrapper(code,description, data);
		return Response.ok(wrapper).build();
	}
	
	@Override
	public Response insertServiceOrder(InsertServiceOrderRequest insertServiceOrderRequest) {
		Response response = null;
		try {
			// To Validate request
	        if (insertServiceOrderRequest.getRequest().getOtsCustomerChangeAddressId() == null || insertServiceOrderRequest.getRequest().getOtsCustomerChangeAddressId().equals("")
	                || insertServiceOrderRequest.getRequest().getOtsCustomerId() == null || insertServiceOrderRequest.getRequest().getOtsCustomerId().equals("")) {
	            return buildResponse(400, "Please Enter Required Input");
	        }
			
			// Validate Customer
	        UserDataBOResponse customerDetails = otsUserService.getUserIDUsers(insertServiceOrderRequest.getRequest().getOtsCustomerId());
	        if (customerDetails.getUserDetails().isEmpty()) {
	            return buildResponse(401, "Invalid Customer ID");
	        }
	        
			String checkSlotAvailability = otsService.checkSlotAvailability(insertServiceOrderRequest.getRequest());
			if(checkSlotAvailability.equalsIgnoreCase("Slot_Available")) {
			
				ServiceOrder orderdeatils  = otsServiceOrder.insertServiceOrder(insertServiceOrderRequest);
				if(orderdeatils == null ) {
					response = responseWrapper.buildResponse(404, "Order Not Inserted");
				}
				else {
					response = responseWrapper.buildResponse(200, orderdeatils, "Successful");
				}
			}else if(checkSlotAvailability.equalsIgnoreCase("Slot_Unavailable")) {
				response = responseWrapper.buildResponse(404, "Slot Unavailable");
			}else if(checkSlotAvailability.equalsIgnoreCase("Slot_Not_Configured")) {
				response = responseWrapper.buildResponse(404, "Slot Not Configured");
			}else {
				response = responseWrapper.buildResponse(404, "Order Not Inserted");
			}
		} catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		 return response;
	}
	
	@Override
	public Response getServiceOrderDetailsbyOrderId(String serviceOrderId) {
		Response response = null;
		try {
			ServiceOrder serviceorderDetails = otsServiceOrder.getServiceOrderDetailsbyOrderId(serviceOrderId);
			if(serviceorderDetails == null) {
				response = responseWrapper.buildResponse(404,"No Data Found");
			}else {
				response = responseWrapper.buildResponse(200,serviceorderDetails,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateServiceOrderStatus(UpdateServiceOrderRequest updateServiceOrderRequest) {
		Response response = null;
		try {
			if(updateServiceOrderRequest.getRequest().getOtsServiceOrderId() == null || updateServiceOrderRequest.getRequest().getOtsServiceOrderId().equals("")
				||updateServiceOrderRequest.getRequest().getOtsServiceOrderStatus() == null || updateServiceOrderRequest.getRequest().getOtsServiceOrderStatus().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
		
			//Predefined user status
			String[] VALID_STATUSES = {"Accepted", "InProgress","Closed"};

		    // Validate input user status
		    String orderStatus  = updateServiceOrderRequest.getRequest().getOtsServiceOrderStatus();
		    boolean isValidStatus = Arrays.stream(VALID_STATUSES)
		                                  .anyMatch(status -> status.equalsIgnoreCase(orderStatus));

		    //If input status not matching predefined status
		    if (!isValidStatus) {
		        return response = responseWrapper.buildResponse(400, "Invalid Order Status");
		    } 
			
			String updateStatus = otsServiceOrder.updateServiceOrderStatus(updateServiceOrderRequest);
			if(updateStatus == null) {
				response = responseWrapper.buildResponse(404,"Invalid OrderId");
			}
			else if(updateStatus.equalsIgnoreCase("Updated")) {
				response = responseWrapper.buildResponse(200,updateStatus,"Successful");
			}
			else {
				response = responseWrapper.buildResponse(404,"Not Updated");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response assignServiceOrder(AssignServiceOrderRequest assignServiceOrderRequest) {
		Response response = null;
		try {
			
			if(assignServiceOrderRequest.getRequest().getOtsServiceOrderId() == null || assignServiceOrderRequest.getRequest().getOtsServiceOrderId().equals("")
			   || assignServiceOrderRequest.getRequest().getOtsEmployeeId() == null ||  assignServiceOrderRequest.getRequest().getOtsEmployeeId().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			String assignStatus = otsServiceOrder.assignServiceOrder(assignServiceOrderRequest);
			if (assignStatus == null) {
				response = responseWrapper.buildResponse(404, "Invalid OrderId");
			} else if (assignStatus.equalsIgnoreCase("Updated")) {
				response = responseWrapper.buildResponse(200, assignStatus, "Successful");
			} else {
				response = responseWrapper.buildResponse(404, "Not Updated");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response cancelServiceOrder(CancelServiceOrderRequest cancelServiceOrderRequest) {
		Response response = null;
		try {
			 if(cancelServiceOrderRequest.getRequest().getOtsServiceOrderId() == null || cancelServiceOrderRequest.getRequest().getOtsServiceOrderId().equals("")
				 ||	cancelServiceOrderRequest.getRequest().getOtsServiceOrderCancelledBy() == null || cancelServiceOrderRequest.getRequest().getOtsServiceOrderCancelledBy().equals("")
				 || cancelServiceOrderRequest.getRequest().getOtsServiceOrderCancellationReason() == null || cancelServiceOrderRequest.getRequest().getOtsServiceOrderCancellationReason().equals("")) {
				 return response =  buildResponse(400,"Please Enter Required Inputs");
			}
			String cancelStatus = otsServiceOrder.cancelServiceOrder(cancelServiceOrderRequest);
			if(cancelStatus == null) {
				response = responseWrapper.buildResponse(404,"Invalid OrderId");
			}
			else if(cancelStatus.equalsIgnoreCase("Updated")) {
				response = responseWrapper.buildResponse(200,cancelStatus,"Successful");
			}else {
				response = responseWrapper.buildResponse(404,"Not Updated");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getProviderServiceOrderByStatus(GetServiceOrderByProviderRequest getServiceOrderByProviderRequest) {	
		Response response = null;
		try {
			if(getServiceOrderByProviderRequest.getRequest().getOtsProviderId() == null || getServiceOrderByProviderRequest.getRequest().getOtsProviderId().equals("")
					|| getServiceOrderByProviderRequest.getRequest().getOtsServiceOrderStatus() == null || getServiceOrderByProviderRequest.getRequest().getOtsServiceOrderStatus().equals(""))  
			{	
				return response = responseWrapper.buildResponse(400,"Please Insert Required Input");
			}
			List<ServiceOrder> serviceOrderDetails = otsServiceOrder.getProviderServiceOrderByStatus(getServiceOrderByProviderRequest);
			if (serviceOrderDetails.size() == 0) {
			    response = responseWrapper.buildResponse(404, "No Order For You");
			}else{
				response = responseWrapper.buildResponse(200, serviceOrderDetails,"Successful");
			}	
		} catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	
	@Override
	public Response getCustomerServiceOrderByStatus(GetServiceCustomerOrdersByStatusRequest getServiceCustomerOrdersByStatusRequest) {	
		Response response = null;
		try {
			if(getServiceCustomerOrdersByStatusRequest.getRequest().getOtsServiceCustomerId() == null || getServiceCustomerOrdersByStatusRequest.getRequest().getOtsServiceCustomerId().equals("")
					|| getServiceCustomerOrdersByStatusRequest.getRequest().getOtsServiceOrderStatus() == null || getServiceCustomerOrdersByStatusRequest.getRequest().getOtsServiceOrderStatus().equals(""))  
			{	
				return response = responseWrapper.buildResponse(400,"Please Insert Required Input");
			}
			List<ServiceOrder> serviceOrderDetails = otsServiceOrder.getCustomerServiceOrderByStatus(getServiceCustomerOrdersByStatusRequest);
			if (serviceOrderDetails.size() == 0) {
			    response = responseWrapper.buildResponse(404, "No Order For You");
			}
			else{
				response = responseWrapper.buildResponse(200, serviceOrderDetails,"Successful");
			}	
		} catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
}
