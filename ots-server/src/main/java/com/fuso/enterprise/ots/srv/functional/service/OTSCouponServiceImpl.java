package com.fuso.enterprise.ots.srv.functional.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.Coupon;
import com.fuso.enterprise.ots.srv.api.model.domain.CouponOrder;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSCouponService;
import com.fuso.enterprise.ots.srv.api.service.request.ActiveInActiveCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCouponBasedOnRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateCouponRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.CouponDAO;
import com.fuso.enterprise.ots.srv.server.dao.CouponOrderDAO;

@Service
@Transactional
public class OTSCouponServiceImpl implements OTSCouponService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private CouponDAO couponDAO;
	private CouponOrderDAO couponOrderDAO;
	
	@Inject
	public OTSCouponServiceImpl(CouponDAO couponDAO,CouponOrderDAO couponOrderDAO)
	{
		this.couponDAO = couponDAO;
		this.couponOrderDAO = couponOrderDAO;
	}

	@Override
	public String addCoupon(AddCouponRequest addCouponRequest) {
		try {
			couponDAO.addCoupon(addCouponRequest);
		}catch (Exception e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}
	
	@Override
	public List<Coupon> checkForExistingCoupon(String couponCode) {
		List<Coupon> couponDetails = new ArrayList<Coupon>();
		try {
			couponDetails = couponDAO.checkForExistingCoupon(couponCode);
		}catch (Exception e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}
	
	@Override
	public List<Coupon> getCouponsBasedOnStatusAndAdminId(GetCouponBasedOnRequest getCouponBasedOnRequest) {
		List<Coupon> couponDetails = new ArrayList<Coupon>();
		try {
			couponDetails = couponDAO.getCouponsBasedOnStatusAndAdminId(getCouponBasedOnRequest);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}
	
	@Override
	public String activeInActiveCoupon(ActiveInActiveCouponRequest activeInActiveCouponRequest) {
		Coupon couponDetails = new Coupon();
		String Response;
		try {
			couponDetails = couponDAO.activeInActiveCoupon(activeInActiveCouponRequest);
			if(couponDetails != null) {
				 Response = "Coupon is set to "+activeInActiveCouponRequest.getRequest().getCouponStatus();
			}else {
				Response = null;
			}
		}catch(Exception e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return Response;
	}
	
	@Override
	public Coupon updateCoupon(UpdateCouponRequest updateCouponRequest) {
		Coupon couponDetails = new Coupon();
		try {
			couponDetails = couponDAO.updateCoupon(updateCouponRequest);
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}
	
	@Override
	public List<Coupon> getCouponsByStatusOrCategory(GetCouponBasedOnRequest getCouponBasedOnRequest) {
		List<Coupon> couponDetails = new ArrayList<Coupon>();
		try {
			couponDetails = couponDAO.getCouponsByStatusOrCategory(getCouponBasedOnRequest);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}
	
	@Override
	public List<CouponOrder> couponUsed(String couponId) {
		List<CouponOrder> couponOrderList = new ArrayList<CouponOrder>();
		try {
			couponOrderList = couponOrderDAO.couponUsed(couponId);
			
		}catch(Exception e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return couponOrderList;
	}

}
