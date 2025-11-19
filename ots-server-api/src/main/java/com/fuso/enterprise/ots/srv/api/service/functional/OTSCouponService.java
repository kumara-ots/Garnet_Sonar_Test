package com.fuso.enterprise.ots.srv.api.service.functional;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.Coupon;
import com.fuso.enterprise.ots.srv.api.model.domain.CouponOrder;
import com.fuso.enterprise.ots.srv.api.service.request.ActiveInActiveCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCouponBasedOnRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateCouponRequest;

public interface OTSCouponService {

	String addCoupon(AddCouponRequest addCouponRequest);

	List<Coupon> checkForExistingCoupon(String couponCode);

	List<Coupon> getCouponsBasedOnStatusAndAdminId(GetCouponBasedOnRequest getCouponBasedOnRequest);

	String activeInActiveCoupon(ActiveInActiveCouponRequest activeInActiveCouponRequest);

	Coupon updateCoupon(UpdateCouponRequest updateCouponRequest);

	List<Coupon> getCouponsByStatusOrCategory(GetCouponBasedOnRequest getCouponBasedOnRequest);

	List<CouponOrder> couponUsed(String couponId);


}
