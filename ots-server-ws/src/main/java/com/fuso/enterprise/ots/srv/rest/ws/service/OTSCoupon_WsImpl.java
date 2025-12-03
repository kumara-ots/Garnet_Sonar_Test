package com.fuso.enterprise.ots.srv.rest.ws.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuso.enterprise.ots.srv.api.model.domain.Coupon;
import com.fuso.enterprise.ots.srv.api.model.domain.CouponOrder;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSCouponService;
import com.fuso.enterprise.ots.srv.api.service.request.ActiveInActiveCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCouponBasedOnRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateCouponRequest;
import com.fuso.enterprise.ots.srv.server.util.ResponseWrapper;

public class OTSCoupon_WsImpl implements OTSCoupon_Ws {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	ResponseWrapper responseWrapper ;
	
	@Inject
	private OTSCouponService oTSCouponService;
	
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
	public Response addCoupon(AddCouponRequest addCouponRequest) {
		Response response = null;
		try {	
			if(addCouponRequest.getRequest().getCouponCode() == null || addCouponRequest.getRequest().getCouponCode().equals("") 
					|| addCouponRequest.getRequest().getCouponDescription() == null || addCouponRequest.getRequest().getCouponDescription().equals("")
					|| addCouponRequest.getRequest().getCouponStartDate() == null || addCouponRequest.getRequest().getCouponStartDate().equals("")
					|| addCouponRequest.getRequest().getCouponEndDate() == null || addCouponRequest.getRequest().getCouponEndDate().equals("")
					|| addCouponRequest.getRequest().getCouponBasedOn() == null || addCouponRequest.getRequest().getCouponBasedOn().equals("")) {
				return response = buildResponse(400,"Please Enter required inputs");
			}
			if(addCouponRequest.getRequest().getCouponBasedOn().equals("FirstUser")) {
				if(addCouponRequest.getRequest().getCouponPrice() == null || addCouponRequest.getRequest().getCouponPrice().equals("") 
						|| addCouponRequest.getRequest().getCouponMinPurchasePrice() == null || addCouponRequest.getRequest().getCouponMinPurchasePrice().equals("")) {
					return response = buildResponse(400,"Please Enter required inputs");
				}
			}
			if(addCouponRequest.getRequest().getCouponBasedOn().equals("TotalPrice")) {
				if(addCouponRequest.getRequest().getCouponPercentage() == null || addCouponRequest.getRequest().getCouponPercentage().equals("") 
						|| addCouponRequest.getRequest().getCouponMaxPrice() == null || addCouponRequest.getRequest().getCouponMaxPrice().equals("")
						|| addCouponRequest.getRequest().getCouponMinPurchasePrice() == null || addCouponRequest.getRequest().getCouponMinPurchasePrice().equals("")) {
					return response = buildResponse(400,"Please Enter required inputs");
				}
			}
			List<Coupon> couponDetails = oTSCouponService.checkForExistingCoupon(addCouponRequest.getRequest().getCouponCode());
			if(couponDetails.size()!=0) {
				response = buildResponse(404,"Coupon Code Already Exists");
			}else {
				String ResponseValue = oTSCouponService.addCoupon(addCouponRequest);
				if(ResponseValue != null) {
					response = buildResponse(ResponseValue,"Added Coupon Successfully");
				}else {
					response = buildResponse(ResponseValue,"Coupon Not Added");
				}		
			}	
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getCouponsBasedOnStatusAndAdminId(GetCouponBasedOnRequest getCouponBasedOnRequest) {
		Response response = null;
		try {	
			if(getCouponBasedOnRequest.getRequest().getAdminId() == null || getCouponBasedOnRequest.getRequest().getAdminId().equals("") 
					|| getCouponBasedOnRequest.getRequest().getCouponBasedOn() == null || getCouponBasedOnRequest.getRequest().getCouponBasedOn().equals("")
					|| getCouponBasedOnRequest.getRequest().getCouponStatus() == null || getCouponBasedOnRequest.getRequest().getCouponStatus().equals("")) {
				return response = buildResponse(400,"Please Enter required inputs");
			}	
			List<Coupon> couponDetails = oTSCouponService.getCouponsBasedOnStatusAndAdminId(getCouponBasedOnRequest);
			if(couponDetails.size()==0) {
				response = buildResponse(404,couponDetails,"No Coupons available");
			}else {
				response = buildResponse(couponDetails,"Successful");	
			}	
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response activeInActiveCoupon(ActiveInActiveCouponRequest activeInActiveCouponRequest) {
		Response response = null;
		try {	
			if(activeInActiveCouponRequest.getRequest().getCouponId()==null || activeInActiveCouponRequest.getRequest().getCouponId().equals("")
					|| activeInActiveCouponRequest.getRequest().getCouponStatus()==null || activeInActiveCouponRequest.getRequest().getCouponStatus().equals("")) {
				return response = buildResponse(400,"Please Enter required inputs");
			}	
			if(!activeInActiveCouponRequest.getRequest().getCouponStatus().equalsIgnoreCase("active")
					&& !activeInActiveCouponRequest.getRequest().getCouponStatus().equalsIgnoreCase("inactive")) {
				return response = buildResponse(400,"Coupon status can only be changed to Active/Inactive");
			}	
			String ResponseDate = oTSCouponService.activeInActiveCoupon(activeInActiveCouponRequest);
			if(ResponseDate == null) {
				response = buildResponse(404,"Coupon status could not be updated");
			}else {
				response = buildResponse(200,ResponseDate);
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}catch(Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response checkForExistingCoupon(String couponCode) {
		Response response = null;
		try {	
			List<Coupon> couponDetails = oTSCouponService.checkForExistingCoupon(couponCode);
			if(couponDetails.size() != 0) {
				response = buildResponse(404,"Coupon code already exists");
			}else {
				response = buildResponse(200,"Coupon code is available");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}catch(Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateCoupon(UpdateCouponRequest updateCouponRequest) {
		Response response = null;
		try {	
			if(updateCouponRequest.getRequest().getCouponId()==null || updateCouponRequest.getRequest().getCouponId().equals("")){
				return response = buildResponse(400,"Please Enter required inputs");
			}	
			Coupon couponDetails = oTSCouponService.updateCoupon(updateCouponRequest);
			if(couponDetails == null) {
				response = buildResponse(404,"Coupon is not updated");
			}else {
				response = buildResponse(200,"Coupon updated successfully");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}catch(Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getCouponsByStatusOrCategory(GetCouponBasedOnRequest getCouponBasedOnRequest) {
		Response response = null;
		try {	
			if(getCouponBasedOnRequest.getRequest().getCouponStatus()==null || getCouponBasedOnRequest.getRequest().getCouponStatus().isEmpty()){
				return response = buildResponse(400,"Please Enter required inputs");
			}	
			List<Coupon> couponDetails = oTSCouponService.getCouponsByStatusOrCategory(getCouponBasedOnRequest);
			if(couponDetails.size()==0) {
				response = buildResponse(404,couponDetails,"No Coupons available");
			}else {
				response = buildResponse(couponDetails,"Successful");	
			}	
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}catch(Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response couponUsed(String couponId) {
		Response response = null;
		try {	
			List<CouponOrder> couponOrderList = oTSCouponService.couponUsed(couponId);
			if(couponOrderList.size() == 0) {
				response = buildResponse(404,"CouponId not found");
			}else {
				Integer count = couponOrderList.size();
				response = buildResponse(200,count,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}catch(Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

}
