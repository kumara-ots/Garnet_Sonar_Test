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

import com.fuso.enterprise.ots.srv.api.service.request.ActiveInActiveCouponRequest;

import com.fuso.enterprise.ots.srv.api.service.request.AddCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCouponBasedOnRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateCouponRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "OTSCoupon_Ws", description = "This service provides the operations for OTS Coupon")
@Path("coupon")
@CrossOrigin
public interface OTSCoupon_Ws {

	@POST
	@Path("/addCoupon")
	@ApiOperation(value = "addCoupon", notes = "To add New Coupon",response = Response.class)            
    @ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addCoupon(@ApiParam(value = "request", required =true)@NotNull @Valid AddCouponRequest addCouponRequest);
	
	@POST
	@Path("/getCouponsBasedOnStatusAndAdminId")
	@ApiOperation(value = "getCouponDetails", notes = "To get coupons based on status,adminId,category",response = Response.class)            
    @ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCouponsBasedOnStatusAndAdminId(@ApiParam(value = "request", required =true)@NotNull @Valid GetCouponBasedOnRequest getCouponBasedOnRequest);
	
	@POST
	@Path("/activeInActiveCoupon")
	@ApiOperation(value = "activeInActiveCoupon", notes = "To change coupon status to Active/Inactive",response = Response.class)            
    @ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response activeInActiveCoupon(@ApiParam(value = "request", required =true)@NotNull @Valid ActiveInActiveCouponRequest activeInActiveCouponRequest);
	
	@POST
	@Path("/checkForExistingCoupon")
	@ApiOperation(value = "checkForExistingCoupon", notes = "To check wheather the coupon code is availabe or not",response = Response.class)            
    @ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response checkForExistingCoupon(@ApiParam(value = "request", required =true)@NotNull @Valid @QueryParam("couponCode") String couponCode);
	
	@POST
	@Path("/updateCoupon")
	@ApiOperation(value = "updateCoupon", notes = "To Update existing coupon details ",response = Response.class)            
    @ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateCoupon(@ApiParam(value = "request", required =true)@NotNull @Valid UpdateCouponRequest updateCouponRequest);
	
	@POST
	@Path("/getCouponsByStatusOrCategory")
	@ApiOperation(value = "getCouponsByStatusOrCategory", notes = "To get coupons based on status or category",response = Response.class)            
    @ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCouponsByStatusOrCategory(@ApiParam(value = "request", required =true)@NotNull @Valid GetCouponBasedOnRequest getCouponBasedOnRequest);
	
	@POST
	@Path("/couponUsed")
	@ApiOperation(value = "couponUsed", notes = "To get count of counpon used",response = Response.class)            
    @ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response couponUsed(@ApiParam(value = "request", required =true)@NotNull @Valid @QueryParam("couponId") String couponId);
}
