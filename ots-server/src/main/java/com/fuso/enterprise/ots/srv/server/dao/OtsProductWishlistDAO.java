package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.service.request.AddWishListRequest;
import com.fuso.enterprise.ots.srv.api.service.response.GetwishListResponse;

public interface OtsProductWishlistDAO {

	String addWishList(AddWishListRequest addWishListRequest);
	
	List<GetwishListResponse> getWishList(String customerId);
	
	String removeFromWishList(AddWishListRequest addWishListRequest);

}
