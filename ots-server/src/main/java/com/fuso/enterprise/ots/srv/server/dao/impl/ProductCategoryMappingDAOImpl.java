package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductCategoryMapping;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ProductCategoryMappingDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProductCategoryMapping;
import com.fuso.enterprise.ots.srv.server.model.entity.Useraccounts;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ProductCategoryMappingDAOImpl extends AbstractIptDao<OtsProductCategoryMapping, String> implements ProductCategoryMappingDAO {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ProductCategoryMappingDAOImpl() {
		super(OtsProductCategoryMapping.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String addProductCategoryMapping(ProductCategoryMapping productCategoryMapping) {
		try {
			OtsProduct otsProduct = new OtsProduct();
			otsProduct.setOtsProductId(UUID.fromString(productCategoryMapping.getOtsProductId()));
			
			OtsProduct otsProductCategory = new OtsProduct();
			otsProductCategory.setOtsProductId(UUID.fromString(productCategoryMapping.getOtsProductCategoryId()));
			
			Useraccounts createdUser = new Useraccounts();
			createdUser.setAccountId(UUID.fromString(productCategoryMapping.getCreatedUser()));
			
			OtsProductCategoryMapping otsProductCategoryMapping = new OtsProductCategoryMapping();
			otsProductCategoryMapping.setOtsProductCategoryId(otsProductCategory);
			otsProductCategoryMapping.setOtsProductId(otsProduct);
			otsProductCategoryMapping.setCreatedUser(createdUser);
			
			save(otsProductCategoryMapping);
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}
}
