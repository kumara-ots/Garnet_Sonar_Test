package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.SubCategoryAttributeMapping;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeKeyForSubCategoryRequest;

public interface SubcategoryAttributeMappingDAO {

	List<SubCategoryAttributeMapping> addAttributeKeyForSubCategory(AddAttributeKeyForSubCategoryRequest AddAttributeKeyForSubCategoryRequest);

	List<SubCategoryAttributeMapping> getAttributeKeyBySubcategory(String subcategoryId);

}
