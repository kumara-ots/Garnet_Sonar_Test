package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;
import java.util.Map;

public class CustomerProductResponse
{
	List<Map<String, Object>> ProductList;

	public List<Map<String, Object>> getProductList() {
		return ProductList;
	}

	public void setProductList(List<Map<String, Object>> productList) {
		ProductList = productList;
	}
	
}
