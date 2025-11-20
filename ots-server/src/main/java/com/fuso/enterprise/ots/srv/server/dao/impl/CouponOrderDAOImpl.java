package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.CouponOrder;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.CouponOrderDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsCoupon;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsCouponOrder;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsOrder;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class CouponOrderDAOImpl extends AbstractIptDao<OtsCouponOrder, String> implements CouponOrderDAO 
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    private JdbcTemplate jdbcTemplate;

	public CouponOrderDAOImpl() {
		super(OtsCouponOrder.class);
		// TODO Auto-generated constructor stub
	}
	
	@Transactional
	@Override
	public String insertCouponToOrder(Integer couponId, String orderId) {
		try {
			OtsCouponOrder couponOrder = new OtsCouponOrder();
			OtsOrder otsOrderId = new OtsOrder();
			otsOrderId.setOtsOrderId(UUID.fromString(orderId));
			couponOrder.setOtsOrderId(otsOrderId);
			OtsCoupon otsCouponId = new OtsCoupon();
			otsCouponId.setOtsCouponId(couponId);
			couponOrder.setOtsCouponId(otsCouponId);
			save(couponOrder);
			return "Inserted";
		}catch(Exception e){
			logger.error("Exception while inserting data in DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data in DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	private CouponOrder convertCouponOrderDetailsFromEntityToDomain(OtsCouponOrder couponOrder) {
		CouponOrder couponDetails = new CouponOrder();
		couponDetails.setCouponId((couponOrder.getOtsCouponId()==null)?null:couponOrder.getOtsCouponId().toString());
		couponDetails.setOrderId((couponOrder.getOtsOrderId()==null)?null:couponOrder.getOtsCouponId().toString());
		couponDetails.setCouponOrderId((couponOrder.getOtsCouponOrderId()==null)?null:couponOrder.getOtsCouponOrderId().toString());
		return couponDetails;
	}
	
	@Override
	public List<CouponOrder> couponUsed(String couponId) {
		List<OtsCouponOrder> couponOrder = new ArrayList<OtsCouponOrder>();
		List<CouponOrder> couponOrderList = new ArrayList<CouponOrder>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCouponId",Integer.parseInt(couponId));	
			couponOrder = super.getResultListByNamedQuery("OtsCouponOrder.getCouponOrderList", queryParameter);
			couponOrderList = couponOrder.stream().map(coupon -> convertCouponOrderDetailsFromEntityToDomain(coupon)).collect(Collectors.toList());
			return couponOrderList;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
}
