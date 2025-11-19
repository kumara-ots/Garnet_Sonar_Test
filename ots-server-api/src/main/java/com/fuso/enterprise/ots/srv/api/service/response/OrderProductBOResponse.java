package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetailsAndProductDetails;

public class OrderProductBOResponse {
	
	private List<OrderDetailsAndProductDetails> OrderList;
	
	String pdf;
	
	String Order;
	
	String couponStatus;
	
	public String getCouponStatus() {
		return couponStatus;
	}
	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus;
	}
		
	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public List<OrderDetailsAndProductDetails> getOrderList() {
		return OrderList;
	}

	public void setOrderList(List<OrderDetailsAndProductDetails> orderList) {
		OrderList = orderList;
	}
	
	public String getOrder() {
		return Order;
	}

	public void setOrder(String order) {
		Order = order;
	}

}
