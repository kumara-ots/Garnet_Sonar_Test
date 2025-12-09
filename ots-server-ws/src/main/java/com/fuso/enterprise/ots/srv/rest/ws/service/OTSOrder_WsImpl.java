package com.fuso.enterprise.ots.srv.rest.ws.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fuso.enterprise.ots.srv.api.model.domain.CustomerChangeAddress;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderProductDetails;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSOrderService;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSUserService;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateOrderProductBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateOrderTrackingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrderPaymentDetailsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddTransactionCancelRecordsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AssignOrderToEmployeeRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CancelOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CloseEmployeeOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GenerateOrderProductInvoiceRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCustomerOrderByStatusBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetDistributorSettlementRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetListOfOrderByDateBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetMonthlyDistributorSettlementRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderByOrderIdAndStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderByStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetRRCOrdersByStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.InsertOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateOrderDetailsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateOrderProductStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateRRCStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.response.CcAvenueOrderDetailsResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetDistributorSettlementResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetListOfOrderByDateBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.OrderDetailsBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.OrderProductAndOrderResponse;
import com.fuso.enterprise.ots.srv.api.service.response.OrderProductBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.UserDataBOResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.util.ResponseWrapper;

@Configuration
@EnableScheduling
public class OTSOrder_WsImpl implements OTSOrder_Ws{
	
    final String DB_ERROR_MSG = "Exception while fetching data from DB";
	
	@Inject
	OTSOrderService oTSOrderService;
	
	@Inject
	OTSUserService oTSUserService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	ResponseWrapper responseWrapper ;
	
	@Value("${product.percentage.price}")
	public String productPercentage;
	
	public Response buildResponse(Object data,String description) {
		ResponseWrapper wrapper = new ResponseWrapper(200,description, data);
		return Response.ok(wrapper).build();
	}
	
	public Response buildResponse(int code,String description) {
		ResponseWrapper wrapper = new ResponseWrapper(code,description);
		return Response.ok(wrapper).build();
	}
	
	
	@Override
	public Response getOrderList(GetOrderBORequest getOrderBORequest) {
		OrderDetailsBOResponse orderDetailsBOResponse = new OrderDetailsBOResponse();
		Response response = null;
		try {
			if(getOrderBORequest.getRequest().getDistributorsId() == null || getOrderBORequest.getRequest().getDistributorsId().equals("") 
					|| getOrderBORequest.getRequest().getFromTime() == null || getOrderBORequest.getRequest().getFromTime().equals("") 
					|| getOrderBORequest.getRequest().getToTime() == null || getOrderBORequest.getRequest().getToTime().equals("")){	
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
		    orderDetailsBOResponse = oTSOrderService.getOrderBydate(getOrderBORequest);
			if (orderDetailsBOResponse == null) {
				response = buildResponse(404,"No order from"+getOrderBORequest.getRequest().getFromTime()+"To"+getOrderBORequest.getRequest().getToTime());
			}else{
				response = buildResponse(orderDetailsBOResponse,"Successful");
			}
		} catch(Exception e){
			logger.error(DB_ERROR_MSG +e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getOrderByStatusAndDistributor(GetOrderByStatusRequest  getOrderByStatusRequest) {	
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		Response response = null;
		try {
			if(getOrderByStatusRequest.getRequest().getDistrubitorId() == null || getOrderByStatusRequest.getRequest().getDistrubitorId().equals("")
					|| getOrderByStatusRequest.getRequest().getStatus() == null || getOrderByStatusRequest.getRequest().getStatus().equals(""))  
			{	
				response = responseWrapper.buildResponse(400,"Please Insert required Input");
			}
			orderProductBOResponse = oTSOrderService.getOrderByStatusAndDistributor(getOrderByStatusRequest);
			if (orderProductBOResponse != null) {
				response = responseWrapper.buildResponse(orderProductBOResponse,"Successful");
			}else{
				response = responseWrapper.buildResponse(404,"No Order For You");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getOrderByDistributorIdAndSubOrderStatus(GetOrderByStatusRequest  getOrderByStatusRequest) {	
		OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
		Response response = null;
		try {
			if(getOrderByStatusRequest.getRequest().getDistrubitorId() == null || getOrderByStatusRequest.getRequest().getDistrubitorId().equals("")
					|| getOrderByStatusRequest.getRequest().getStatus() == null || getOrderByStatusRequest.getRequest().getStatus().equals(""))  
			{	
				response = responseWrapper.buildResponse(400,"Please Insert required Input");
			}
			orderProductAndOrderResponse = oTSOrderService.getOrderByDistributorIdAndSubOrderStatus(getOrderByStatusRequest);
			if (orderProductAndOrderResponse != null) {
				response = responseWrapper.buildResponse(orderProductAndOrderResponse,"Successful");
			}else{
				response = responseWrapper.buildResponse(404,"No Order For You");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response insertOrderAndProduct(AddOrUpdateOrderProductBOrequest addOrUpdateOrderProductBOrequest) {
		Response response = null;
		try {	
			if(addOrUpdateOrderProductBOrequest.getRequest().getCustomerId() == null || addOrUpdateOrderProductBOrequest.getRequest().getCustomerId().equals("") ||
				addOrUpdateOrderProductBOrequest.getRequest().getCustomerChangeAddressId() == null ||addOrUpdateOrderProductBOrequest.getRequest().getCustomerChangeAddressId().equals("")) {
				return response = buildResponse(400,"Please Enter Required Input");
			}
			if(addOrUpdateOrderProductBOrequest.getRequest().getUserLat() == null ||addOrUpdateOrderProductBOrequest.getRequest().getUserLat().equals("")
					||addOrUpdateOrderProductBOrequest.getRequest().getUserLong().equals("")||addOrUpdateOrderProductBOrequest.getRequest().getUserLong() == null) {
				return response = buildResponse(400,"Please Turn On Location To Place The Order");
			}
			UserDataBOResponse customerDetails = oTSUserService.getUserIDUsers(addOrUpdateOrderProductBOrequest.getRequest().getCustomerId());
			if(customerDetails.getUserDetails().size() == 0) {
				return response = buildResponse(401,"Invalid Customer ID");
			}
			OrderProductBOResponse ResponseValue = oTSOrderService.insertOrderAndProduct(addOrUpdateOrderProductBOrequest);
			if(ResponseValue == null) {
				response = buildResponse(404,"Order Not Inserted");
			}else {
				response = buildResponse(ResponseValue,"Inserted Order");
			}
		}catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
            return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
            return response = buildResponse(500,"Something Went Wrong");
		}
		 return response;
	}
	

	@Override
	public Response insertOrder(InsertOrderRequest insertOrderRequest) {
	    Response response = null;
	    ExecutorService executor = Executors.newCachedThreadPool();

	    try {

	        // Validate request
	        if (insertOrderRequest.getRequest().getCustomerChangeAddressId() == null
	                || insertOrderRequest.getRequest().getCustomerChangeAddressId().isEmpty()
	                || insertOrderRequest.getRequest().getCustomerId() == null
	                || insertOrderRequest.getRequest().getCustomerId().isEmpty()) {
	            return buildResponse(400, "Please Enter Required Input");
	        }

	        // Validate customer
	        UserDataBOResponse customerDetails =
	                oTSUserService.getUserIDUsers(insertOrderRequest.getRequest().getCustomerId());
	        if (customerDetails.getUserDetails().isEmpty()) {
	            return buildResponse(401, "Invalid Customer ID");
	        }

	        // Future tasks
	        Future<List<CustomerChangeAddress>> addressFuture =
	                executor.submit(() -> oTSUserService.getCustomerChangeAddressById(
	                        insertOrderRequest.getRequest().getCustomerChangeAddressId()));

	        Future<List<OrderDetails>> transactionFuture = null;
	        if (insertOrderRequest.getRequest().getOrderTransactionId() != null) {
	            transactionFuture =
	                    executor.submit(() -> oTSOrderService.getOrderByOrderTransactionId(
	                            insertOrderRequest.getRequest().getOrderTransactionId()));
	        }

	        // Get results
	        List<CustomerChangeAddress> customerChangeAddress = addressFuture.get();
	        List<OrderDetails> checkTransactionId =
	                (transactionFuture != null) ? transactionFuture.get() : Collections.emptyList();

	        if (customerChangeAddress.isEmpty()) {
	            return buildResponse(401, "Invalid Customer Address ID");
	        }

	        if (!checkTransactionId.isEmpty()) {
	            return buildResponse(401, "Duplicate Transaction ID");
	        }

	        // Insert order
	        Future<OrderProductBOResponse> insertOrderFuture =
	                executor.submit(() -> oTSOrderService.insertOrder(insertOrderRequest));

	        OrderProductBOResponse responseValue = insertOrderFuture.get();
	        if (responseValue == null) {
	            response = buildResponse(404, "Order Not Inserted");
	        } else {
	            response = buildResponse(responseValue, "Inserted Order");
	        }

	    } catch (InterruptedException e) {
	        // ✔ Sonar fix: restore interrupt state
	        Thread.currentThread().interrupt();
	        logger.error("Thread was interrupted: " + e.getMessage());
	        return buildResponse(500, "Request Interrupted");

	    } catch (ExecutionException e) {
	        logger.error("Execution error while processing request: " + e.getMessage());
	        return buildResponse(500, "Something Went Wrong");

	    } catch (Exception e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage());
	        return buildResponse(500, "Something Went Wrong");

	    } finally {
	        executor.shutdown();
	    }

	    return response;
	}


	@Override
	public Response getCustomerOrderStatus(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest) {
		logger.info("Inside Event=1030,Class:OTSOrder_WsImpl,Method:getCustomerOrderStatus,getCustomerOrderByStatusBOrequest " + getCustomerOrderByStatusBOrequest);
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		Response response = null;	
		try {
			orderProductBOResponse = oTSOrderService.getCustomerOrderStatus(getCustomerOrderByStatusBOrequest);
			if(orderProductBOResponse == null) {
				response = buildResponse(400,"No Order For You");
			}else {
				response = buildResponse(orderProductBOResponse,"Successfull");
			}
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getOrderDetailsByDate(GetOrderBORequest getOrderBORequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		Response response = null;
		try {
			if(getOrderBORequest.getRequest().getFromTime() == null || getOrderBORequest.getRequest().getFromTime().equals("")
					|| getOrderBORequest.getRequest().getToTime() == null || getOrderBORequest.getRequest().getToTime().equals("")){
				return response = buildResponse(400,"Please Enter Dates");
			}
			orderProductBOResponse = oTSOrderService.getOrderDetailsByDate(getOrderBORequest);
			if(orderProductBOResponse == null) {
				response = buildResponse(404,"No order from "+getOrderBORequest.getRequest().getFromTime()+" to "+getOrderBORequest.getRequest().getToTime());
			}else{
				response = buildResponse(orderProductBOResponse,"Successfull");
			}
		} catch (BusinessException e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getListOfOrderByDateRequest(GetListOfOrderByDateBORequest getListOfOrderByDateBORequest) {
		Response response = null;
		GetListOfOrderByDateBOResponse getListOfOrderByDateBOResponse = new GetListOfOrderByDateBOResponse();
		try {
			if(getListOfOrderByDateBORequest.getRequest().getStartDate() == null ||getListOfOrderByDateBORequest.getRequest().getStartDate().equals("")
					||getListOfOrderByDateBORequest.getRequest().getEndDate() == null ||getListOfOrderByDateBORequest.getRequest().getEndDate().equals("")) {
				return response = buildResponse(404,"Please Enter Dates");
			}
			getListOfOrderByDateBOResponse=oTSOrderService.getListOfOrderByDate(getListOfOrderByDateBORequest);
			if(getListOfOrderByDateBOResponse == null) {
				response = responseWrapper.buildResponse(404,"No Customer Order Details Available from "+getListOfOrderByDateBORequest.getRequest().getStartDate()+" to "+getListOfOrderByDateBORequest.getRequest().getEndDate());
			}else {	
				response = responseWrapper.buildResponse(getListOfOrderByDateBOResponse,"Successful");
			}
		}catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response orderReportByDate(GetOrderBORequest getOrderBORequest) {
		Response response = null;
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		try {
			if(getOrderBORequest.getRequest().getFromTime() == null || getOrderBORequest.getRequest().getFromTime().equals("")
					|| getOrderBORequest.getRequest().getToTime() == null || getOrderBORequest.getRequest().getToTime().equals("")
					|| getOrderBORequest.getRequest().getDistributorsId() == null || getOrderBORequest.getRequest().getDistributorsId().equals("")
					|| getOrderBORequest.getRequest().getStatus() == null || getOrderBORequest.getRequest().getStatus().equals("")
					|| getOrderBORequest.getRequest().getCustomerId() == null || getOrderBORequest.getRequest().getCustomerId().equals("")) {
				return response = buildResponse(404,"Please Enter Required Inputs");
			}
			orderProductBOResponse = oTSOrderService.orderReportByDate(getOrderBORequest);
			if(orderProductBOResponse == null) {
				response = buildResponse(404,"No orders for you");
			}else {
				response = buildResponse(orderProductBOResponse,"Successful");
			}	
		}catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getRazorPayOrder(UpdateOrderDetailsRequest updateOrderDetailsRequest) {
		Response response = null;
		try {
			try {
				response = buildResponse(oTSOrderService.getRazorPayOrder(updateOrderDetailsRequest),"Successful");
			}catch (JSONException e) {
				System.out.print(e);
			}
		} catch(Exception e) {
			System.out.print(e);
		}
		return response;
	}
	
	@Override
	public Response getCCAvenueCredentials() {
		Response response = null;
		CcAvenueOrderDetailsResponse ccAvenueOrderDetailsResponse = new CcAvenueOrderDetailsResponse();
		try {
			ccAvenueOrderDetailsResponse = oTSOrderService.getCCAvenueCredentials();
			if(ccAvenueOrderDetailsResponse != null) {
				response = responseWrapper.buildResponse(ccAvenueOrderDetailsResponse,"Successful");
			}else {
				response = responseWrapper.buildResponse(404,"Data Not Found");
			}
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getPaymentDetailsBypaymentId(String paymentId) {
		Response response = null;
		response = buildResponse(oTSOrderService.fetchPaymentDetailsByPaymetId(paymentId),"Successful");
		return response;
	}

	@Override
	public Response getOrderDetailsForOrderId(String OrderId) {
		Response response = null;
		try {
			OrderProductBOResponse orderDetails = oTSOrderService.getOrderDetailsForOrderId(OrderId);
			if(orderDetails == null) {
				response = responseWrapper.buildResponse(404,"No Data Found");
			}else {
				response = responseWrapper.buildResponse(orderDetails,"Successful");
			}
		}catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response closeMainOrder(String OrderId) {
		Response response = null;
		try {	
			String ResponseDate = oTSOrderService.closeMainOrder(OrderId);
			if(ResponseDate == null) {
				response = buildResponse(404,"Order is not closed for OrderId "+OrderId);
			}else {
				response = buildResponse(200,ResponseDate);
			}
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		 return response;
	}

	@Override
	public Response closeEmployeeOrder(CloseEmployeeOrderRequest  closeEmployeeOrderRequest) {
		Response response = null;
		logger.info("Inside Event=1018,Class:OTSOrder_WsImpl,Method:closeEmployeeOrder,CloseEmployeeOrderRequest " + closeEmployeeOrderRequest);
		try {	
			if(closeEmployeeOrderRequest.getRequest().getOrderId() == null || closeEmployeeOrderRequest.getRequest().getOrderId().equals("")
					|| closeEmployeeOrderRequest.getRequest().getProductId() == null || closeEmployeeOrderRequest.getRequest().getProductId().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			
			//To check whether distributor has entered tracking details before closing the order or not, Order can be closed only when all tracking details are filled for an Order Product 
			OrderProductDetails orderProductDetails = oTSOrderService.getOrderProductByOrderIdProductId(closeEmployeeOrderRequest.getRequest().getOrderId(),closeEmployeeOrderRequest.getRequest().getProductId());
			if(orderProductDetails.getOtsTrackingId().isEmpty() || orderProductDetails.getOtsTrackingLogistics().isEmpty()  ||orderProductDetails.getOtsTrackingUrl().isEmpty())
			{
				response = responseWrapper.buildResponse(404,"Please Enter Tracking Details");
			}
			else {
				//To Close Order Product by Distributor
				String ResponseValue = oTSOrderService.closeEmployeeOrder(closeEmployeeOrderRequest);
				if(ResponseValue == null) {
					response = responseWrapper.buildResponse(404,"Order Has Not Been Closed");
				}else {
					response = responseWrapper.buildResponse(200,ResponseValue);
				}
			}
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		 return response;
	}
	
	@Override
	public Response assignOrderToEmployee(AssignOrderToEmployeeRequest assignOrderToEmployeeRequest) {
		try {
			if(assignOrderToEmployeeRequest.getRequest().getOrderId() == null || assignOrderToEmployeeRequest.getRequest().getOrderId().equals("")
					|| assignOrderToEmployeeRequest.getRequest().getCustomerId() == null || assignOrderToEmployeeRequest.getRequest().getCustomerId().equals("")
					|| assignOrderToEmployeeRequest.getRequest().getOrderStatus() == null || assignOrderToEmployeeRequest.getRequest().getOrderStatus().equals("")
					|| assignOrderToEmployeeRequest.getRequest().getAssignedId() == null || assignOrderToEmployeeRequest.getRequest().getAssignedId().equals("")
					|| assignOrderToEmployeeRequest.getRequest().getProductId() == null || assignOrderToEmployeeRequest.getRequest().getProductId().equals("")
					|| assignOrderToEmployeeRequest.getRequest().getDistributorId() == null || assignOrderToEmployeeRequest.getRequest().getDistributorId().equals("")
					|| assignOrderToEmployeeRequest.getRequest().getExpectedDeliveryDate() == null || assignOrderToEmployeeRequest.getRequest().getExpectedDeliveryDate().equals("")) {
				return buildResponse(400,"Please Enter Required Inputs");
			}
			if(!assignOrderToEmployeeRequest.getRequest().getOrderStatus().equalsIgnoreCase("Assigned")) {
				return buildResponse(400,"Order Status Should Be Assigned");
			}

			String responseValue = oTSOrderService.assignOrderToEmployee(assignOrderToEmployeeRequest);
			if(responseValue == null || responseValue.equalsIgnoreCase("Not Updated")) {
				return buildResponse(404,"Order Has Not Been Assigned");
			}else if(responseValue.equalsIgnoreCase("Order Has Been Assigned Successfully")) {
				return buildResponse(200,"Order Has Been Assigned Successfully");
			}else if(responseValue.equalsIgnoreCase("Insufficient Stock")){
				return buildResponse(404,"Insufficient Stock. Add Stock Before Assigning Order");
			}else {
				return buildResponse(404,"Order Has Not Been Assigned");
			}
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return  buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return  buildResponse(500,"Something Went Wrong");
		}
	}
	
	@Override
	public Response cancelMainAndSubOrder(CancelOrderRequest cancelOrderRequest) {
		try {
			if(cancelOrderRequest.getRequest().getOrderId() == null || cancelOrderRequest.getRequest().getOrderId().equals("")
					|| cancelOrderRequest.getRequest().getCustomerId() == null || cancelOrderRequest.getRequest().getCustomerId().equals("")
					|| cancelOrderRequest.getRequest().getCancelReason() == null
					|| cancelOrderRequest.getRequest().getCancelledBy() == null || cancelOrderRequest.getRequest().getCancelledBy().equals("")) {
				return  buildResponse(400,"Please Enter Required Inputs");
			}
			
			String cancelledBy = cancelOrderRequest.getRequest().getCancelledBy();
            if(cancelledBy == null ||cancelledBy.trim().isEmpty() || (!cancelledBy.equalsIgnoreCase("Buyer") && !cancelledBy.equalsIgnoreCase("Seller"))) {
                return buildResponse(400,"Invalid value for cancelledBy, Must be 'Buyer' or 'Seller'");
            } 
            
            if(cancelledBy.equalsIgnoreCase("Seller") && cancelOrderRequest.getRequest().getCancelReason().trim().isEmpty()) {
            	return  buildResponse(400,"Please Enter Required Inputs");
            }
            
			String responseDate = oTSOrderService.cancelMainAndSubOrder(cancelOrderRequest);
			if(responseDate == null) {
				return buildResponse(404,"Order Has Not Been Cancelled");
			}
			else {
				return buildResponse(200,responseDate);
			}
		}catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
	        return  buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
	        return  buildResponse(500,"Something Went Wrong");
		}
	}
	
	@Override
	public Response autoCancelOrderByDistributor() {
		try {
			String responseDate = oTSOrderService.autoCancelOrderByDistributor();
			if (responseDate.equalsIgnoreCase("Updated")) {
				return buildResponse("Order Cancelled Successfully", "Successful");
			} else if (responseDate.equalsIgnoreCase("No Orders Found")) {
				return buildResponse(404, "No Orders Found");
			} else {
				return buildResponse(404, "Order Not Cancelled");
			}
		} catch (Exception e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return buildResponse(500, "Something Went Wrong");
		}
	}

	@Override
	public Response addPaymentDetailsForOrder(AddOrderPaymentDetailsRequest addOrderPaymentDetailsRequest) {
		OrderDetails otsOrderDetails = new OrderDetails();
		try {	
			if(addOrderPaymentDetailsRequest.getRequest().getOrderTransactionId() == null || addOrderPaymentDetailsRequest.getRequest().getOrderTransactionId().equals("")
					|| addOrderPaymentDetailsRequest.getRequest().getPaymentId() == null || addOrderPaymentDetailsRequest.getRequest().getPaymentId().equals("")) {
				return buildResponse(400,"Please Enter required inputs");
			}
			otsOrderDetails = oTSOrderService.addPaymentDetailsForOrder(addOrderPaymentDetailsRequest);
			if(otsOrderDetails == null) {
				return responseWrapper.buildResponse(404,"Payment Details Not Added");
			}else {
				return responseWrapper.buildResponse(otsOrderDetails,"Successful");
			}
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return  buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return  buildResponse(500,"Something Went Wrong");
		}
	}
	
	@Override
	public Response getOrderDetailsForInvoice(String orderId) {
		Response response = null;
		try {
			List<List<String>> orderDetails = oTSOrderService.getOrderDetailsForInvoice(orderId);
			if(orderDetails.size() == 0) {
				response = responseWrapper.buildResponse(404,"No Data Found");
			}else{
				response = responseWrapper.buildResponse(orderDetails,"Successfull");
			}
		} catch (BusinessException e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	 @Override
	public Response getOrdersByStatus(String orderStatus) {	
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		Response response = null;
		List<Response> list = new ArrayList<Response>();
		try {	
			orderProductBOResponse = oTSOrderService.getOrdersByStatus(orderStatus);
			if (orderProductBOResponse != null) {
				response = responseWrapper.buildResponse(orderProductBOResponse,"Successful");
			}else{
				response = responseWrapper.buildResponse(404,"No Orders Available");
			}
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	 
	@Override
	public Response getOrderByStatusOfUnregisteredDistributors(String SubOrderStatus) {	
		List<OrderProductDetails> orderProductDetails = new ArrayList<OrderProductDetails>();
		Response response = null;
		try {
			orderProductDetails = oTSOrderService.getOrderByStatusOfUnregisteredDistributors(SubOrderStatus);
			if (orderProductDetails == null) {
				response = responseWrapper.buildResponse(404,"No Order For You");
			}else{
				response = responseWrapper.buildResponse(orderProductDetails,"Successful");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateRRCOrderStatus(UpdateRRCStatusRequest updateRRCStatusRequest) {
		Response response = null;
		try {
			if(updateRRCStatusRequest.getRequest().getOrderId() == null || updateRRCStatusRequest.getRequest().getOrderId().equals("")
					|| updateRRCStatusRequest.getRequest().getProductId() == null || updateRRCStatusRequest.getRequest().getProductId().equals("")
					|| updateRRCStatusRequest.getRequest().getRRCOrderStatus() == null || updateRRCStatusRequest.getRequest().getRRCOrderStatus().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			String ResponseValue = oTSOrderService.updateRRCOrderStatus(updateRRCStatusRequest);
			if(ResponseValue == null || ResponseValue.equalsIgnoreCase("Not Updated")) {
				response = responseWrapper.buildResponse(404,"Not Updated");
			}else{
				response = responseWrapper.buildResponse(ResponseValue,"Successful");
			}
		}catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		 return response;
	}
	
	@Override
	public Response getDistributorWeeklySettlemetDetails(GetDistributorSettlementRequest getDistributorSettlementRequest) {	
		GetDistributorSettlementResponse getDistributorSettlementResponse = new GetDistributorSettlementResponse();
		Response response = null;
		try {
			if(getDistributorSettlementRequest.getRequest().getDistributorId() == null || getDistributorSettlementRequest.getRequest().getDistributorId().equals("")
					|| getDistributorSettlementRequest.getRequest().getFromDate() == null || getDistributorSettlementRequest.getRequest().getFromDate().equals("")
					|| getDistributorSettlementRequest.getRequest().getToDate() == null || getDistributorSettlementRequest.getRequest().getToDate().equals("")
					|| getDistributorSettlementRequest.getRequest().getPdf() == null || getDistributorSettlementRequest.getRequest().getPdf().equals("")) 
			{
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			
			List<DistributorCompanyDetails> distributorCompany = oTSUserService.getDistributorCompanyDetails(getDistributorSettlementRequest.getRequest().getDistributorId());
			if(distributorCompany.size()==0) {
				return response = responseWrapper.buildResponse(404,"Company Details Not Added");
			}else {
				getDistributorSettlementRequest.getRequest().setGstAvailability(distributorCompany.get(0).getTaxAvailability());
			}
			
			getDistributorSettlementResponse = oTSOrderService.getDistributorWeeklySettlemetDetails(getDistributorSettlementRequest);
			if (getDistributorSettlementResponse == null) {
				response = responseWrapper.buildResponse(404,"No Order For You");
			}else{
				response = responseWrapper.buildResponse(getDistributorSettlementResponse,"Successful");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getDistributorCurrentWeekSettlemetDetails(String distributorId) {	
		GetDistributorSettlementResponse getDistributorSettlementResponse = new GetDistributorSettlementResponse();
		Response response = null;
		try {
			getDistributorSettlementResponse = oTSOrderService.getDistributorCurrentWeekSettlemetDetails(distributorId);
			if (getDistributorSettlementResponse == null) {
				response = responseWrapper.buildResponse(404,"No Order For You");
			}else{
				response = responseWrapper.buildResponse(getDistributorSettlementResponse,"Successful");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getDistributorMonthlySettlemetDetails(GetMonthlyDistributorSettlementRequest getDistributorSettlementRequest) {	
		GetDistributorSettlementResponse getDistributorSettlementResponse = new GetDistributorSettlementResponse();
		Response response = null;
		try {
			if(getDistributorSettlementRequest.getRequest().getDistributorId() == null || getDistributorSettlementRequest.getRequest().getDistributorId().equals("")
					|| getDistributorSettlementRequest.getRequest().getMonth() == null || getDistributorSettlementRequest.getRequest().getMonth().equals("")
					|| getDistributorSettlementRequest.getRequest().getYear() == null || getDistributorSettlementRequest.getRequest().getYear().equals("")) 
			{
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			getDistributorSettlementResponse = oTSOrderService.getDistributorMonthlySettlemetDetails(getDistributorSettlementRequest);
			if (getDistributorSettlementResponse == null) {
				response = responseWrapper.buildResponse(404,"No Order For You");
			}else{
				response = responseWrapper.buildResponse(getDistributorSettlementResponse,"Successful");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getCancelledOrdersByDistributor(String distributorId) {	
		OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
		Response response = null;
		try {
			orderProductAndOrderResponse = oTSOrderService.getCancelledOrdersByDistributor(distributorId);
			if (orderProductAndOrderResponse != null) {
				response = responseWrapper.buildResponse(orderProductAndOrderResponse,"Successful");
			}else{
				response = responseWrapper.buildResponse(404,"No Order For You");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getReturnReplacementOrdersForDistributor(String distributorId) {	
		OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
		Response response = null;
		try {
			orderProductAndOrderResponse = oTSOrderService.getReturnReplacementOrdersForDistributor(distributorId);
			if (orderProductAndOrderResponse != null) {
				response = responseWrapper.buildResponse(orderProductAndOrderResponse,"Successful");
			}else{
				response = responseWrapper.buildResponse(404,"No Order For You");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getRRCOrdersByDistributor(GetRRCOrdersByStatusRequest getRRCOrdersByStatusRequest) {	
		OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
		Response response = null;
		try {
			if(getRRCOrdersByStatusRequest.getRequest().getDistributorId() == null || getRRCOrdersByStatusRequest.getRequest().getDistributorId().equals("")
					|| getRRCOrdersByStatusRequest.getRequest().getRrcOrderStatus() == null || getRRCOrdersByStatusRequest.getRequest().getRrcOrderStatus().equals(""))
			{
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			orderProductAndOrderResponse = oTSOrderService.getRRCOrdersByDistributor(getRRCOrdersByStatusRequest);
			if (orderProductAndOrderResponse != null) {
				response = responseWrapper.buildResponse(orderProductAndOrderResponse,"Successful");
			}else{
				response = responseWrapper.buildResponse(404,"No Order For You");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getRRClosedOrdersByDistributor(String distributorId) {	
		OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
		Response response = null;
		try {
			orderProductAndOrderResponse = oTSOrderService.getRRClosedOrdersByDistributor(distributorId);
			if (orderProductAndOrderResponse != null) {
				response = responseWrapper.buildResponse(orderProductAndOrderResponse,"Successful");
			}else{
				response = responseWrapper.buildResponse(404,"No Order For You");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addPaymentTransactionCancelRecords(AddTransactionCancelRecordsRequest addPaymentTransactionCancelRecords) {
		Response response = null;
		try {
			List<OrderDetails> orderDetails = oTSOrderService.getOrderByOrderTransactionId(addPaymentTransactionCancelRecords.getRequest().getOtsTransactionCancelOrderId());
			if(orderDetails.size() == 0) {
				return response = responseWrapper.buildResponse(401,"Invalid Order Transaction Id");
			}
			String ResponseValue = oTSOrderService.addPaymentTransactionCancelRecords(addPaymentTransactionCancelRecords);
			if(ResponseValue == null) {
				response = responseWrapper.buildResponse(404,"Not Inserted");
			}
			else{
				response = responseWrapper.buildResponse(ResponseValue,"Successful");
			}
		}catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		 return response;
	}
	
	@Override
	public Response generateDeliveryNote(String orderProductId) {
		Response response = null;
		try {
			String generatedDeliveryNoteresponse = oTSOrderService.generateDeliveryNote(orderProductId);
			if (generatedDeliveryNoteresponse == null) {
				response = responseWrapper.buildResponse(404, "No Data Found");
			} else {
				response = responseWrapper.buildResponse(generatedDeliveryNoteresponse, "Successful");
			}
		} catch (Exception e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getSubOrderDetailsByOrderProductId(String orderProductId) {
		Response response = null;
		try {
			List<OrderProductDetails> orderProduct = oTSOrderService.getSubOrderDetailsByOrderProductId(orderProductId);
			if (orderProduct.size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Found");
			} else {
				response = responseWrapper.buildResponse(orderProduct, "Successful");
			}
		} catch (Exception e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addOrUpdateOrderTracking(AddOrUpdateOrderTrackingRequest addOrUpdateOrderTrackingRequest) {
		Response response = null;
		try {
			if(addOrUpdateOrderTrackingRequest.getRequest().getOrderProductId() == null || addOrUpdateOrderTrackingRequest.getRequest().getOrderProductId().equals("")
					|| addOrUpdateOrderTrackingRequest.getRequest().getOtsTrackingId() == null || addOrUpdateOrderTrackingRequest.getRequest().getOtsTrackingId().equals("")
					|| addOrUpdateOrderTrackingRequest.getRequest().getOtsTrackingLogistics() == null || addOrUpdateOrderTrackingRequest.getRequest().getOtsTrackingLogistics().equals("")
					|| addOrUpdateOrderTrackingRequest.getRequest().getOtsTrackingUrl() == null || addOrUpdateOrderTrackingRequest.getRequest().getOtsTrackingUrl().equals("")) {
				return response = buildResponse(400,"Please Enter All The Details");
			}
			String ResponseValue = oTSOrderService.addOrUpdateOrderTracking(addOrUpdateOrderTrackingRequest);
			if (ResponseValue == null) {
				response = responseWrapper.buildResponse(404,"Can't Able To Add Tracking Details. Please Contact Support For Assistance.");
			} else {
				response = responseWrapper.buildResponse(ResponseValue, "Successful");
			}
		} catch (Exception e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getRRCOrdersByCustomer(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest) {	
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		Response response = null;
		try {
			if(getCustomerOrderByStatusBOrequest.getRequest().getCustomerId() == null || getCustomerOrderByStatusBOrequest.getRequest().getCustomerId().equals("")
					|| getCustomerOrderByStatusBOrequest.getRequest().getStatus() == null || getCustomerOrderByStatusBOrequest.getRequest().getStatus().equals(""))
			{
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			orderProductBOResponse = oTSOrderService.getRRCOrdersByCustomer(getCustomerOrderByStatusBOrequest);
			if (orderProductBOResponse == null) {
				response = responseWrapper.buildResponse(404,"No Order For You");
			}else{
				response = responseWrapper.buildResponse(orderProductBOResponse,"Successful");
			}	
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getCustomerOrderByOrderProductStatus(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		Response response = null;	
		try {
			orderProductBOResponse = oTSOrderService.getCustomerOrderByOrderProductStatus(getCustomerOrderByStatusBOrequest);
			if(orderProductBOResponse == null) {
				response = buildResponse(404,"No Order For You");
			}else {
				response = buildResponse(orderProductBOResponse,"Successful");
			}
		} catch (BusinessException e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getOrderByOrderIdOrderProductStatus(GetOrderByOrderIdAndStatusRequest getOrderByOrderIdAndStatusRequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		Response response = null;	
		try {
			orderProductBOResponse = oTSOrderService.getOrderByOrderIdOrderProductStatus(getOrderByOrderIdAndStatusRequest);
			if(orderProductBOResponse == null) {
				response = buildResponse(404,"No Order For You");
			}else {
				response = buildResponse(orderProductBOResponse,"Successful");
			}
		} catch (BusinessException e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getOrderByOrderIdRRCOrderStatus(GetOrderByOrderIdAndStatusRequest getOrderByOrderIdAndStatusRequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		Response response = null;	
		try {
			orderProductBOResponse = oTSOrderService.getOrderByOrderIdRRCOrderStatus(getOrderByOrderIdAndStatusRequest);
			if(orderProductBOResponse == null) {
				response = buildResponse(404,"No Order For You");
			}else {
				response = buildResponse(orderProductBOResponse,"Successful");
			}
		} catch (BusinessException e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response generateOrderProductInvoicePdf(GenerateOrderProductInvoiceRequest generateOrderProductInvoiceRequest) {
		Response response = null;
		try {
			if(generateOrderProductInvoiceRequest.getRequest().getOrderId() == null || generateOrderProductInvoiceRequest.getRequest().getOrderId().equals("")
					|| generateOrderProductInvoiceRequest.getRequest().getProductId() == null || generateOrderProductInvoiceRequest.getRequest().getProductId().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			String invoice = oTSOrderService.generateOrderProductInvoicePdf(generateOrderProductInvoiceRequest.getRequest().getOrderId(),generateOrderProductInvoiceRequest.getRequest().getProductId());
			if(invoice == null) {
				response = buildResponse(404,"No Orders Found");
			}
			else {
				response = buildResponse(invoice,"Successful");
			}	
			return response;
		}catch (BusinessException e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override 
	public Response checkPendingOrdersOfInactiveDistributor(String distributorId) {
		Response response = null;
		try {
			List<OrderProductDetails> OrderProductDetails = oTSOrderService.checkPendingOrdersOfInactiveDistributor(distributorId);
		    if(OrderProductDetails.size()== 0) {
		    	response = responseWrapper.buildResponse(404, "No Pending Order For Distributor");
			} else {
				response = responseWrapper.buildResponse(200,"Distributor Has Pending Orders", "Successful");
			}	
		}catch (Exception e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateOrderProductStatus(UpdateOrderProductStatusRequest updateOrderProductStatusRequest) {
		Response response = null;
		try {
			String responseData = oTSOrderService.updateOrderProductStatus(updateOrderProductStatusRequest);
			if(responseData.equalsIgnoreCase("Updated")) {
				response = buildResponse(responseData,"Successful");
			}
			else {
				response = buildResponse(404,"Not Updated");
			}
			return response;
		}catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		}
	}
	
	@Override
	public Response autoCancelOrderByCustomer() {
		Response response = null;
		try {
			String responseDate = oTSOrderService.autoCancelOrderByCustomer();
			if(responseDate.equalsIgnoreCase("Updated")) {
				response = buildResponse("Order Cancelled Successfully","Successful");
			}
			else if(responseDate.equalsIgnoreCase("No Orders Found")) {
				response = buildResponse(404,"No Orders Found");
			}
			else {
				response = buildResponse(404,"Order Not Cancelled");
			}
			return response;
		} catch(Exception e){
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error(DB_ERROR_MSG+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
	}


}
 