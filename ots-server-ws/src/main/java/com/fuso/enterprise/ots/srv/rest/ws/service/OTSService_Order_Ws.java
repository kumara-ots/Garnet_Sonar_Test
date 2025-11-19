package com.fuso.enterprise.ots.srv.rest.ws.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fuso.enterprise.ots.srv.api.service.request.AssignServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CancelServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceCustomerOrdersByStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceOrderByProviderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.InsertServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateServiceOrderRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "OTSService_Order_Ws", description = "This service provides the operations for OTS Service Order")
@Path("serviceOrder")
@CrossOrigin
public interface OTSService_Order_Ws {
	
    @POST
	@Path("/insertServiceOrder")
	@ApiOperation(value = "insertServiceOrder", notes = "To Insert Service Order and Service Order Details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response insertServiceOrder(@ApiParam(value = "request", required = true) @NotNull  @Valid InsertServiceOrderRequest insertServiceOrderRequest);

    @POST
	@Path("/getServiceOrderDetailsbyOrderId")
    @ApiOperation(value = "getServiceOrderDetailsbyOrderId", notes = "To get Service Order Details By Service OrderId ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getServiceOrderDetailsbyOrderId(@ApiParam(value = "serviceOrderId", required = true) @NotNull @Valid @QueryParam("serviceOrderId")String serviceOrderId);

    @POST
  	@Path("/updateServiceOrderStatus")
  	@ApiOperation(value = "updateServiceOrderStatus", notes = "To Update Service Order Status To Accepted, InProgress, Closed  ", response = Response.class)
  	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateServiceOrderStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid UpdateServiceOrderRequest updateServiceOrderRequest);

    @POST
  	@Path("/assignServiceOrder")
  	@ApiOperation(value = "assignServiceOrder", notes = "To Assign Service Order to Employee/Self", response = Response.class)
  	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response assignServiceOrder(@ApiParam(value = "request", required = true) @NotNull  @Valid AssignServiceOrderRequest assignServiceOrderRequest);

    @POST
  	@Path("/cancelServiceOrder")
  	@ApiOperation(value = "cancelServiceOrder", notes = "To Cancel Service Order by Customer/Provider", response = Response.class)
  	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response cancelServiceOrder(@ApiParam(value = "request", required = true) @NotNull  @Valid CancelServiceOrderRequest cancelServiceOrderRequest);

    @POST
  	@Path("/getProviderServiceOrderByStatus")
  	@ApiOperation(value = "getProviderServiceOrderByStatus", notes = "To get Service Order And Service Order Deatils based on ProviderId and ServiceOrderStatus ", response = Response.class)
  	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProviderServiceOrderByStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid GetServiceOrderByProviderRequest getServiceOrderByProviderRequest);
 
    @POST
  	@Path("/getCustomerServiceOrderByStatus")
  	@ApiOperation(value = "getServiceCustomerOrdersByStatus", notes = "To get Service Order And Service Order Deatils based on CustomerId and ServiceOrderStatus ", response = Response.class)
  	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCustomerServiceOrderByStatus(@ApiParam(value = "request", required = true) @NotNull  @Valid GetServiceCustomerOrdersByStatusRequest getServiceCustomerOrdersByStatusRequest);

}
