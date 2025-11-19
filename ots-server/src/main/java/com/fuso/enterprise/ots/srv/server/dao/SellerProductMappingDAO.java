package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
import com.fuso.enterprise.ots.srv.api.service.request.GetSellerForProductRequest;

public interface SellerProductMappingDAO {
	
	List<UserDetails> getSellerForProduct(GetSellerForProductRequest getSellerForProductRequest);
}
