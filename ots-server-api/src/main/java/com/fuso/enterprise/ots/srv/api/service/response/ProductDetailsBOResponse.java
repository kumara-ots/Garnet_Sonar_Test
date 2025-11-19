package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;

public class ProductDetailsBOResponse {
	
	private String userId;
	
	List<ProductDetails> ProductDetails;
	
	private String totalProductsCount;
	
	private String totalPages;

	public List<ProductDetails> getProductDetails() {
		return ProductDetails;
	}

	public void setProductDetails(List<ProductDetails> productDetails) {
		ProductDetails = productDetails;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTotalProductsCount() {
		return totalProductsCount;
	}

	public void setTotalProductsCount(String totalProductsCount) {
		this.totalProductsCount = totalProductsCount;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

}
