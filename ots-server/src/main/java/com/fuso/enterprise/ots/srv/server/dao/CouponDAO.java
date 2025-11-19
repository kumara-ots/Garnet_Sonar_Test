package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.Coupon;
import com.fuso.enterprise.ots.srv.api.service.request.ActiveInActiveCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCouponBasedOnRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateCouponRequest;

public interface CouponDAO {

	String addCoupon(AddCouponRequest addCouponRequest);

	List<Coupon> checkForExistingCoupon(String couponCode);

	List<Coupon> getCouponsBasedOnStatusAndAdminId(GetCouponBasedOnRequest getCouponBasedOnRequest);

	Coupon activeInActiveCoupon(ActiveInActiveCouponRequest activeInActiveCouponRequest);

	Coupon updateCoupon(UpdateCouponRequest updateCouponRequest);

	List<Coupon> getCouponsByStatusOrCategory(GetCouponBasedOnRequest getCouponBasedOnRequest);

	String updateCouponCount(String couponId);

	Coupon getCouponDetailsByStatus(String couponId, String status);

	
}
