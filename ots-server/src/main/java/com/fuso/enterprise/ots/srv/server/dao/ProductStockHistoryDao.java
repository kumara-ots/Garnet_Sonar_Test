package com.fuso.enterprise.ots.srv.server.dao;

import com.fuso.enterprise.ots.srv.api.service.request.AddProductStockBORequest;

public interface ProductStockHistoryDao  {

	void addProductStockHistory(AddProductStockBORequest addProductStockBORequest);
	
}
