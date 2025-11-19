package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

public class GetCartResponse {
	
	private List<GetcartListResponse> cartList;
	private String finalPrice;
	private String firstOrder;
	private String totalGstPrice;
	private String totalPriceWithoutGst;
	
	public List<GetcartListResponse> getCartList() {
		return cartList;
	}
	public void setCartList(List<GetcartListResponse> cartList) {
		this.cartList = cartList;
	}
	public String getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getFirstOrder() {
		return firstOrder;
	}
	public void setFirstOrder(String firstOrder) {
		this.firstOrder = firstOrder;
	}
	public String getTotalGstPrice() {
		return totalGstPrice;
	}
	public void setTotalGstPrice(String totalGstPrice) {
		this.totalGstPrice = totalGstPrice;
	}
	public String getTotalPriceWithoutGst() {
		return totalPriceWithoutGst;
	}
	public void setTotalPriceWithoutGst(String totalPriceWithoutGst) {
		this.totalPriceWithoutGst = totalPriceWithoutGst;
	}
	
}
