package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;

public class ProductSearchResponse {
    private List<ProductDetails> searchedProduct;
    private List<ProductDetails> similarProduct;
    private String totalProductsCount;
    private String totalPages;
    
	public List<ProductDetails> getSearchedProduct() {
		return searchedProduct;
	}
	public void setSearchedProduct(List<ProductDetails> searchedProduct) {
		this.searchedProduct = searchedProduct;
	}
	public List<ProductDetails> getSimilarProduct() {
		return similarProduct;
	}
	public void setSimilarProduct(List<ProductDetails> similarProduct) {
		this.similarProduct = similarProduct;
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