package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;
import java.util.Map;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;

public class GetPageLoaderResponse {
	
	private List<ProductDetails> categoryList;
	private List<ProductDetails> recentlyAddedProductsList;
	private List<Map<String, Object>> bannerDetail;
	private List<UserDetails> distributorsList;
	
	public List<ProductDetails> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<ProductDetails> categoryList) {
		this.categoryList = categoryList;
	}
	public List<ProductDetails> getRecentlyAddedProductsList() {
		return recentlyAddedProductsList;
	}
	public void setRecentlyAddedProductsList(List<ProductDetails> recentlyAddedProductsList) {
		this.recentlyAddedProductsList = recentlyAddedProductsList;
	}
	public List<Map<String, Object>> getBannerDetail() {
		return bannerDetail;
	}
	public void setBannerDetail(List<Map<String, Object>> bannerDetail) {
		this.bannerDetail = bannerDetail;
	}
	public List<UserDetails> getDistributorsList() {
		return distributorsList;
	}
	public void setDistributorsList(List<UserDetails> distributorsList) {
		this.distributorsList = distributorsList;
	}
	
}
