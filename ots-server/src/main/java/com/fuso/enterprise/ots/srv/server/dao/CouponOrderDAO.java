package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.CouponOrder;

public interface CouponOrderDAO {

	String insertCouponToOrder(Integer couponId, String orderId);

	List<CouponOrder> couponUsed(String couponId);

}
