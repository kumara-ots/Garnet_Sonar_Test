package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.service.request.AddProductStockBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductStockRequest;
import com.fuso.enterprise.ots.srv.api.service.response.GetProductBOStockResponse;

public interface ProductStockDao  {
	String addProductStock(AddProductStockBORequest addProductStockBORequest);
	GetProductBOStockResponse getProductStockByUidAndPid(GetProductStockRequest getProductStockRequest );
	GetProductBOStockResponse updateProductStockQuantity(AddProductStockBORequest addProductStockBORequest);
	String removeProductStock(AddProductStockBORequest addProductStockBORequest);
	List<GetProductBOStockResponse> getProductStockByUid(String distributorId);
	String addAirtableStock(AddProductStockBORequest addProductStockBORequest);
	List<GetProductBOStockResponse> getProductStockByProductId(String productId);
}
