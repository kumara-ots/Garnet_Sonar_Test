package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;


import com.fuso.enterprise.ots.srv.api.model.domain.ProductManufacturerDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductManufacturerRequest;

public interface ProductManufacturerDAO {

	String addOrUpdateProductManufacturerDetails(AddProductManufacturerRequest addProductManufacturerRequest);

	String deleteManufacturerDetails(String productManufacturerId);

	List<ProductManufacturerDetails> getAllManufacturerDetails();

}
