package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;
import java.util.Map;

public class ProductSellerDataResponse
{

	List<Map<String, Object>> SellerDetails;
	List<Map<String, Object>> ProductDetails;
	public List<Map<String, Object>> getSellerDetails() {
		return SellerDetails;
	}
	public void setSellerDetails(List<Map<String, Object>> sellerDetails) {
		SellerDetails = sellerDetails;
	}
	public List<Map<String, Object>> getProductDetails() {
		return ProductDetails;
	}
	public void setProductDetails(List<Map<String, Object>> productDetails) {
		ProductDetails = productDetails;
	}
}
