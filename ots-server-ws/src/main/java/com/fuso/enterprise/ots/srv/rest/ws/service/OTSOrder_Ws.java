package com.fuso.enterprise.ots.srv.rest.ws.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "OTSOrder_Ws", description = "This service provides the operations for OTS Order")
@Path("order")
@CrossOrigin
public interface OTSOrder_Ws {

	@POST
    @Path("/getOrder")
	@ApiOperation(value = "getOrder", notes = "Getting getorder Request Based On userid and interval of time", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrderList(@ApiParam(value = "request", required = true) @NotNull  @Valid GetOrderBORequest  getOrderBORequest);
	
	@POST
    @Path("/getOrderByStatusAndDistributor")
	@ApiOperation(value = "getOrderByStatusAndDistributor", notes = "Getting getorder By Status and DistubutorId", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrderByStatusAndDistributor(@ApiParam(value = "request", required = true) @NotNull  @Valid GetOrderByStatusRequest  getOrderByStatusRequest);
	
	@POST
    @Path("/getOrderByDistributorIdAndSubOrderStatus")
	@ApiOperation(value = "getOrderByDistributorIdAndSubOrderStatus", notes = "To get Order by DistributorId and SubOrder Status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrderByDistributorIdAndSubOrderStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid GetOrderByStatusRequest  getOrderByStatusRequest);

	@POST
    @Path("/insertOrderAndProduct")
	@ApiOperation(value = "addOrderAndProduct", notes = "inserting Order And Product as a order", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response insertOrderAndProduct(@ApiParam(value = "request", required = true) @NotNull  @Valid AddOrUpdateOrderProductBOrequest  addOrUpdateOrderProductBOrequest);	
	
	@POST
    @Path("/insertOrder")
	@ApiOperation(value = "insertOrder", notes = "inserting Order And Product", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response insertOrder(@ApiParam(value = "request", required = true) @NotNull  @Valid InsertOrderRequest insertOrderRequest);

	@POST
    @Path("/getCustomerOrderStatus")
	@ApiOperation(value = "getCustomerOrderStatus", notes = "get Order For Customer By status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCustomerOrderStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest);
	
	@POST
    @Path("/getOrderDetailsByDate")
	@ApiOperation(value = "getOrderDetailsByDate", notes = "get Order For Customer By status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrderDetailsByDate(@ApiParam(value = "request", required = true) @NotNull  @Valid GetOrderBORequest  getOrderBORequest);
	
	@POST
    @Path("/getListOfOrderByDate")
	@ApiOperation(value = "getListOfOrderByDateRequest", notes = "get Order For Customer By status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getListOfOrderByDateRequest(@ApiParam(value = "request", required = true) @NotNull  @Valid GetListOfOrderByDateBORequest  getListOfOrderByDateBORequest);
	
	@POST
    @Path("/orderReportByDate")
	@ApiOperation(value = "orderReportByDate", notes = "Inserting the data when closing order or derliverd the product to customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response orderReportByDate(@ApiParam(value = "request", required = true) @NotNull  @Valid GetOrderBORequest  getOrderBORequest);
		
	@POST
    @Path("/getRazorPayOrder")
	@ApiOperation(value = "getRazorPayOrder", notes = "to Get Donation Report By Date", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getRazorPayOrder(UpdateOrderDetailsRequest  updateOrderDetailsRequest);
	
	@GET
    @Path("/fetch-razorpay-details")
	@ApiOperation(value = "getPaymentDetailsBypaymentId", notes = "This operation will give the paymentDetails by payment id", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getPaymentDetailsBypaymentId(@ApiParam(value = "paymentId", required = true) @NotNull @Valid @QueryParam("paymentId") String userId);
	
	@POST
    @Path("/getOrderDetailsForOrderId")
	@ApiOperation(value = "getOrderDetailsForOrderId", notes = "get order details for orderId", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrderDetailsForOrderId(@ApiParam(value = "OrderId", required = true) @NotNull @Valid @QueryParam("OrderId") String OrderId);
	
	@POST
	@Path("/closeMainOrder")
	@ApiOperation(value = "closeMainOrder", notes = "Closeing Order", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response closeMainOrder(@ApiParam(value = "OrderId", required = true) @NotNull @Valid @QueryParam("OrderId") String OrderId);

	@POST
	@Path("/closeEmployeeOrder")
	@ApiOperation(value = "closeOrderByEmployee", notes = "close order by employee", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response closeEmployeeOrder(@ApiParam(value = "request", required = true) @NotNull  @Valid CloseEmployeeOrderRequest  closeEmployeeOrderRequest);

	@POST
    @Path("/AssignOrderToEmployee")
	@ApiOperation(value = "AssignOrderToEmployee", notes = "To Assign order to employee", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response assignOrderToEmployee(@ApiParam(value = "request", required = true) @NotNull  @Valid AssignOrderToEmployeeRequest assignOrderToEmployeeRequest);	

	@POST
    @Path("/cancelMainAndSubOrder")
	@ApiOperation(value = "cancelMainAndSubOrder", notes = "To cancel main order or Sub order", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response cancelMainAndSubOrder(@ApiParam(value = "request", required = true) @NotNull  @Valid CancelOrderRequest cancelOrderRequest);

	@GET
    @Path("/autoCancelOrderByDistributor")
	@ApiOperation(value = "autoCancelOrderByDistributor", notes = "To Auto Cancel order when Distributor doesnt show any action on order, within 24 hours of order date", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response autoCancelOrderByDistributor();
	
	@GET
    @Path("/getCCAvenueCredentials")
	@ApiOperation(value = "getCCAvenueCredentials", notes = "To fetch ccAvenue Credentials for Payment Gatway", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCCAvenueCredentials();
	
	@POST
	@Path("/addPaymentDetailsForOrder")
	@ApiOperation(value = "addPaymentDetailsForOrder", notes = "To add Payment details for Order", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addPaymentDetailsForOrder(@ApiParam(value = "request", required = true) @NotNull  @Valid AddOrderPaymentDetailsRequest addOrderPaymentDetailsRequest);
	
	@POST
	@Path("/getOrderDetailsForInvoice")
	@ApiOperation(value = "getOrderDetailsForInvoice", notes = "To get required order details for Invoice", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrderDetailsForInvoice(@ApiParam(value = "orderId", required = true) @NotNull @Valid @QueryParam("orderId") String orderId);

	@POST
	@Path("/getOrdersByStatus")
	@ApiOperation(value = "getOrdersByStatus", notes = "To get Order details by Status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrdersByStatus(@ApiParam(value = "orderStatus", required = true) @NotNull @Valid @QueryParam("orderStatus") String orderStatus);
	
	@POST
    @Path("/getOrderByStatusOfUnregisteredDistributors")
	@ApiOperation(value = "getOrderByStatusOfUnregisteredDistributors", notes = "To get order product details of GST Unregistered Distributor based on SubOrder Status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrderByStatusOfUnregisteredDistributors(@ApiParam(value = "SubOrderStatus", required = true) @NotNull @Valid @QueryParam("SubOrderStatus") String SubOrderStatus);
	
	@POST
    @Path("/updateRRCOrderStatus")
	@ApiOperation(value = "updateRRCOrderStatus", notes = "To update RRC order status by customer & distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateRRCOrderStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid UpdateRRCStatusRequest updateRRCStatusRequest);	
	
	@POST
    @Path("/getDistributorWeeklySettlemetDetails")
	@ApiOperation(value = "getDistributorWeeklySettlemetDetails", notes = "To get weekly settlement details for distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributorWeeklySettlemetDetails(@ApiParam(value = "request", required = true) @NotNull  @Valid GetDistributorSettlementRequest getDistributorSettlementRequest);
	
	@POST
    @Path("/getDistributorCurrentWeekSettlemetDetails")
	@ApiOperation(value = "getDistributorCurrentWeekSettlemetDetails", notes = "To get current week settlemet details for distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributorCurrentWeekSettlemetDetails(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@POST
    @Path("/getDistributorMonthlySettlemetDetails")
	@ApiOperation(value = "getDistributorMonthlySettlemetDetails", notes = "To get monthly settlement details for distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getDistributorMonthlySettlemetDetails(@ApiParam(value = "request", required = true) @NotNull  @Valid GetMonthlyDistributorSettlementRequest getDistributorSettlementRequest);
	
	@POST
    @Path("/getCancelledOrdersByDistributor")
	@ApiOperation(value = "getCancelledOrdersByDistributor", notes = "To get orders Cancelled by distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCancelledOrdersByDistributor(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@POST
    @Path("/getReturnReplacementOrdersForDistributor")
	@ApiOperation(value = "getReturnReplacementOrdersForDistributor", notes = "To get Return and Replacement initiated orders for distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getReturnReplacementOrdersForDistributor(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@POST
    @Path("/getRRCOrdersByDistributor")
	@ApiOperation(value = "getRRCOrdersByDistributor", notes = "To get RRC Order details by RRC order status for Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getRRCOrdersByDistributor(@ApiParam(value = "request", required = true) @NotNull  @Valid GetRRCOrdersByStatusRequest getRRCOrdersByStatusRequest);
	
	@POST
    @Path("/getRRClosedOrdersByDistributor")
	@ApiOperation(value = "getRRClosedOrdersByDistributor", notes = "To get Return and Replacement Closed  orders for distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getRRClosedOrdersByDistributor(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);

	@POST
    @Path("/addPaymentTransactionCancelRecords")
	@ApiOperation(value = "addPaymentTransactionCancelRecords", notes = "To Add Payment Transaction Cancelled Records for Order payment from Bank", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addPaymentTransactionCancelRecords(@ApiParam(value = "request", required = true) @NotNull  @Valid AddTransactionCancelRecordsRequest addPaymentTransactionCancelRecords);
	
	@POST
    @Path("/generateDeliveryNote")
	@ApiOperation(value = "generateDeliveryNote", notes = "To Generate DeliveryNote For the Delivery product", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response generateDeliveryNote(@ApiParam(value = "orderProductId", required = true) @NotNull @Valid @QueryParam("orderProductId") String orderProductId);
	
	@POST
    @Path("/getSubOrderDetailsByOrderProductId")
	@ApiOperation(value = "getSubOrderDetailsByOrderProductId", notes = "To Get Sub Order details by orderProductId", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getSubOrderDetailsByOrderProductId(@ApiParam(value = "orderProductId", required = true) @NotNull @Valid @QueryParam("orderProductId") String orderProductId);
	
	@POST
    @Path("/getRRCOrdersByCustomer")
	@ApiOperation(value = "getRRCOrdersByCustomer", notes = "To get RRC Order details by RRC order status for Customer", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getRRCOrdersByCustomer(@ApiParam(value = "request", required = true) @NotNull  @Valid GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest);
	
	@POST
    @Path("/getCustomerOrderByOrderProductStatus")
	@ApiOperation(value = "getCustomerOrderByOrderProductStatus", notes = "To get Order Details For Customer By orderProductStatus", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCustomerOrderByOrderProductStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest);
	
	@POST
    @Path("/getOrderByOrderIdOrderProductStatus")
	@ApiOperation(value = "getOrderByOrderIdOrderProductStatus", notes = "To get Order Details By orderId And orderProductStatus", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrderByOrderIdOrderProductStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid GetOrderByOrderIdAndStatusRequest getOrderByOrderIdAndStatusRequest);
	
	@POST
    @Path("/getOrderByOrderIdRRCOrderStatus")
	@ApiOperation(value = "getOrderByOrderIdRRCOrderStatus", notes = "To get Order Details By orderId And RRCOrderStatus", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getOrderByOrderIdRRCOrderStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid GetOrderByOrderIdAndStatusRequest getOrderByOrderIdAndStatusRequest);
	
	@POST
    @Path("/addOrUpdateOrderTracking")
	@ApiOperation(value = "addOrUpdateOrderTracking", notes = "To get Order Details By orderId And RRCOrderStatus", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addOrUpdateOrderTracking(@ApiParam(value = "request", required = true) @NotNull  @Valid AddOrUpdateOrderTrackingRequest addOrUpdateOrderTrackingRequest);
	
	@POST
    @Path("/generateOrderProductInvoicePdf")
	@ApiOperation(value = "generateOrderProductInvoicePdf", notes = "To generate Order Product Invoice", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response generateOrderProductInvoicePdf(@ApiParam(value = "request", required = true) @NotNull  @Valid GenerateOrderProductInvoiceRequest generateOrderProductInvoiceRequest);

	@POST
    @Path("/checkPendingOrdersOfInactiveDistributor")
	@ApiOperation(value = "checkPendingOrdersOfInactiveDistributor", notes = "To Check Pending Orders of Inactive Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response checkPendingOrdersOfInactiveDistributor(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@POST
    @Path("/updateOrderProductStatus")
	@ApiOperation(value = "updateOrderProductStatus", notes = "To update sub-order status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateOrderProductStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid UpdateOrderProductStatusRequest updateOrderProductStatusRequest);
	
	@GET
    @Path("/autoCancelOrderByCustomer")
	@ApiOperation(value = "autoCancelOrderByCustomer", notes = "To Auto Cancel Order when Order Payment Details not updated by Customer, within 3 days of order date", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response autoCancelOrderByCustomer();
}
