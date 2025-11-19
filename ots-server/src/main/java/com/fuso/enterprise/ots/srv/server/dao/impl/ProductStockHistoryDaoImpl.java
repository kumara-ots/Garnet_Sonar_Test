package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.service.request.AddProductStockBORequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;
import com.fuso.enterprise.ots.srv.server.dao.ProductStockHistoryDao;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProductStockHistory;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ProductStockHistoryDaoImpl extends AbstractIptDao<OtsProductStockHistory, String> implements ProductStockHistoryDao{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    private JdbcTemplate jdbcTemplate;

	public ProductStockHistoryDaoImpl() {
		super(OtsProductStockHistory.class);
	}

	@Override
	public void addProductStockHistory(AddProductStockBORequest addProductStockBORequest) {
		try{
			OtsProductStockHistory otsProductStockHistory = new OtsProductStockHistory();
				
			 OtsProduct otsProduct = new OtsProduct();
			 otsProduct.setOtsProductId(UUID.fromString(addProductStockBORequest.getRequestData().getProductId()));
			 otsProductStockHistory.setOtsProductId (otsProduct);
			 
			 OtsUsers otsUsers = new OtsUsers();
			 otsUsers.setOtsUsersId(UUID.fromString(addProductStockBORequest.getRequestData().getUsersId()));
			 otsProductStockHistory.setOtsUsersId(otsUsers);
			 otsProductStockHistory.setOtsProductStockAddDate(addProductStockBORequest.getRequestData().getProductStockAddDate());
			 otsProductStockHistory.setOtsProductStockHistoryQty(addProductStockBORequest.getRequestData().getProductStockQty());
			 otsProductStockHistory.setOtsProductStockOrderId(addProductStockBORequest.getRequestData().getOrderId());
			// otsProductStockHistory.setOtsProductStockAddDate(localDate);
			 super.getEntityManager().merge(otsProductStockHistory);
			 logger.info("Inside Event=1014,Class:ProductStockHistoryDaoImpl,Method:addProductStockHistory ");
		}catch (BusinessException e) {
			throw new BusinessException(e, ErrorEnumeration.GET_SALE_VOCHER);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.GET_SALE_VOCHER);
		}
	}
	
}
