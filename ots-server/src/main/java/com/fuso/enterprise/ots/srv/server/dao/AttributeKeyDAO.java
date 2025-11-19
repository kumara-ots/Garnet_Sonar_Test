package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.AttributeKey;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeKeyRequest;

public interface AttributeKeyDAO {

	List<AttributeKey> getAllAttributeKey();

	List<AttributeKey> addAttributeKey(AddAttributeKeyRequest addAttributeKeyRequest);

	List<AttributeKey> getUnMappedAttributeKeyandValuesForSubcategory(String subcategoryId);

}
