package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.Coupon;
import com.fuso.enterprise.ots.srv.api.service.request.ActiveInActiveCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddCouponRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCouponBasedOnRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateCouponRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.CouponDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsCoupon;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.model.entity.Useraccounts;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class CouponDAOImpl extends AbstractIptDao<OtsCoupon, String> implements CouponDAO
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    private JdbcTemplate jdbcTemplate;

	public CouponDAOImpl() {
		super(OtsCoupon.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String addCoupon(AddCouponRequest addCouponRequest) {
		String responseData;
		try {
			OtsCoupon coupons = new OtsCoupon();
			Useraccounts adminId = new Useraccounts();
			adminId.setAccountId(UUID.fromString(addCouponRequest.getRequest().getAdminId()));
			coupons.setOtsAdminId(adminId);
			coupons.setOtsCouponCode(addCouponRequest.getRequest().getCouponCode());
			coupons.setOtsCouponDescription(addCouponRequest.getRequest().getCouponDescription());
			coupons.setOtsCouponDetails(addCouponRequest.getRequest().getCouponDetails());
			coupons.setOtsCouponStartDate(Date.valueOf(addCouponRequest.getRequest().getCouponStartDate()));
			coupons.setOtsCouponEndDate(Date.valueOf(addCouponRequest.getRequest().getCouponEndDate()));
			coupons.setOtsCouponUnit(Integer.parseInt(addCouponRequest.getRequest().getCouponUnit()));
			coupons.setOtsCouponStatus("New");
			coupons.setOtsCouponBasedOn(addCouponRequest.getRequest().getCouponBasedOn());
			if(coupons.getOtsCouponBasedOn().equals("FirstUser")) {
				coupons.setOtsCouponMinPurchasePrice(Integer.parseInt(addCouponRequest.getRequest().getCouponMinPurchasePrice()));
				coupons.setOtsCouponPrice(Integer.parseInt(addCouponRequest.getRequest().getCouponPrice()));
			}
			if(coupons.getOtsCouponBasedOn().equals("TotalPrice")) {
				coupons.setOtsCouponPercentage(Integer.parseInt(addCouponRequest.getRequest().getCouponPercentage()));
				coupons.setOtsCouponMaxPrice(Integer.parseInt(addCouponRequest.getRequest().getCouponMaxPrice()));
				coupons.setOtsCouponMinPurchasePrice(Integer.parseInt(addCouponRequest.getRequest().getCouponMinPurchasePrice()));
			}
			save(coupons);
			responseData="Coupon added successfully";
			logger.info("Inside Event=1011,Class:CouponDAOImpl,Method:addCoupon:"+responseData);
			
		}catch (NoResultException e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return responseData;
	}
	
	@Override
	public Coupon updateCoupon(UpdateCouponRequest updateCouponRequest) {
		String status = "new";
		Coupon couponDetails = new Coupon();
		OtsCoupon couponList = new OtsCoupon();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCouponId",Integer.parseInt(updateCouponRequest.getRequest().getCouponId()));	
			queryParameter.put("otsCouponStatus",status);	
			couponList = super.getResultByNamedQuery("OtsCoupon.getCouponsBasedOnStatus", queryParameter);
			
			couponList.setOtsCouponCode(updateCouponRequest.getRequest().getCouponCode());
			couponList.setOtsCouponDescription(updateCouponRequest.getRequest().getCouponDescription());
			couponList.setOtsCouponDetails(updateCouponRequest.getRequest().getCouponDetails());
			couponList.setOtsCouponStartDate(Date.valueOf(updateCouponRequest.getRequest().getCouponStartDate()));
			couponList.setOtsCouponEndDate(Date.valueOf(updateCouponRequest.getRequest().getCouponEndDate()));
			couponList.setOtsCouponUnit(Integer.parseInt(updateCouponRequest.getRequest().getCouponUnit()));
			if(couponList.getOtsCouponBasedOn().equals("FirstUser")) {
				couponList.setOtsCouponMinPurchasePrice(Integer.parseInt(updateCouponRequest.getRequest().getCouponMinPurchasePrice()));
				couponList.setOtsCouponPrice(Integer.parseInt(updateCouponRequest.getRequest().getCouponPrice()));
			}
			if(couponList.getOtsCouponBasedOn().equals("TotalPrice")) {
				couponList.setOtsCouponPercentage(Integer.parseInt(updateCouponRequest.getRequest().getCouponPercentage()));
				couponList.setOtsCouponMaxPrice(Integer.parseInt(updateCouponRequest.getRequest().getCouponMaxPrice()));
				couponList.setOtsCouponMinPurchasePrice(Integer.parseInt(updateCouponRequest.getRequest().getCouponMinPurchasePrice()));
			}
			save(couponList);
			couponDetails = convertCouponDetailsFromEntityToDomain(couponList);
		}catch (NoResultException e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}
	
	private Coupon convertCouponDetailsFromEntityToDomain(OtsCoupon coupon) {
		Coupon couponDetails = new Coupon();
		couponDetails.setCouponId((coupon.getOtsCouponId()==null)?null:coupon.getOtsCouponId().toString());
		couponDetails.setCouponCode((coupon.getOtsCouponCode()==null)?null:coupon.getOtsCouponCode().toString());
		couponDetails.setCouponDescription((coupon.getOtsCouponDescription()==null)?null:coupon.getOtsCouponDescription().toString());
		couponDetails.setCouponDetails((coupon.getOtsCouponDetails()==null)?null:coupon.getOtsCouponDetails().toString());
		couponDetails.setCouponStartDate((coupon.getOtsCouponStartDate()==null)?null:coupon.getOtsCouponStartDate().toString());
		couponDetails.setCouponEndDate((coupon.getOtsCouponEndDate()==null)?null:coupon.getOtsCouponEndDate().toString());
		couponDetails.setCouponUnit((coupon.getOtsCouponUnit()==null)?null:coupon.getOtsCouponUnit().toString());
		couponDetails.setCouponPercentage((coupon.getOtsCouponPercentage()==null)?null:coupon.getOtsCouponPercentage().toString());
		couponDetails.setCouponMaxPrice((coupon.getOtsCouponMaxPrice()==null)?null:coupon.getOtsCouponMaxPrice().toString());
		couponDetails.setCouponMinPurchasePrice((coupon.getOtsCouponMinPurchasePrice()==null)?null:coupon.getOtsCouponMinPurchasePrice().toString());
		couponDetails.setCouponPrice((coupon.getOtsCouponPrice()==null)?null:coupon.getOtsCouponPrice().toString());
		couponDetails.setCouponStatus((coupon.getOtsCouponStatus()==null)?null:coupon.getOtsCouponStatus().toString());
		couponDetails.setCouponBasedOn((coupon.getOtsCouponBasedOn()==null)?null:coupon.getOtsCouponBasedOn().toString());
		couponDetails.setAdminId((coupon.getOtsAdminId().getAccountId()==null)?null:coupon.getOtsAdminId().getAccountId().toString());
		return couponDetails;
	}
	
	@Override
	public List<Coupon> checkForExistingCoupon(String couponCode) {
		List<Coupon> couponDetails = new ArrayList<Coupon>();
		List<OtsCoupon> couponList = new ArrayList<OtsCoupon>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCouponCode",couponCode);	
			couponList = super.getResultListByNamedQuery("OtsCoupon.checkForExistingCoupon", queryParameter);
			couponDetails =  couponList.stream().map(coupon -> convertCouponDetailsFromEntityToDomain(coupon)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
            logger.error("Exception while fetching data from DB :"+e.getMessage());
            throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}
	
	@Override
	public List<Coupon> getCouponsBasedOnStatusAndAdminId(GetCouponBasedOnRequest getCouponBasedOnRequest) {
		List<Coupon> couponDetails = new ArrayList<Coupon>();
		List<OtsCoupon> couponList = new ArrayList<OtsCoupon>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers AdminId = new OtsUsers();
			AdminId.setOtsUsersId(UUID.fromString(getCouponBasedOnRequest.getRequest().getAdminId()));
			
			queryParameter.put("otsAdminId",AdminId);	
			queryParameter.put("otsCouponStatus",getCouponBasedOnRequest.getRequest().getCouponStatus());
			queryParameter.put("otsCouponBasedOn",getCouponBasedOnRequest.getRequest().getCouponBasedOn());
			couponList = super.getResultListByNamedQuery("OtsCoupon.getCouponsBasedOnStatusAndAdminId", queryParameter);
			couponDetails =  couponList.stream().map(coupon -> convertCouponDetailsFromEntityToDomain(coupon)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
            logger.error("Exception while fetching data from DB :"+e.getMessage());
            throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}
	
	@Override
	public Coupon activeInActiveCoupon(ActiveInActiveCouponRequest activeInActiveCouponRequest) {
		Coupon couponDetails = new Coupon();
		OtsCoupon couponList = new OtsCoupon();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCouponId",Integer.parseInt(activeInActiveCouponRequest.getRequest().getCouponId()));	
			couponList = super.getResultByNamedQuery("OtsCoupon.findByOtsCouponId", queryParameter);
			couponList.setOtsCouponStatus(activeInActiveCouponRequest.getRequest().getCouponStatus());
			couponDetails = convertCouponDetailsFromEntityToDomain(couponList);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
            logger.error("Exception while fetching data from DB :"+e.getMessage());
            throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}
	
	@Override
	public List<Coupon> getCouponsByStatusOrCategory(GetCouponBasedOnRequest getCouponBasedOnRequest) {
		List<Coupon> couponDetails = new ArrayList<Coupon>();
		List<OtsCoupon> couponList = new ArrayList<OtsCoupon>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();

			if(getCouponBasedOnRequest.getRequest().getCouponBasedOn()==null || getCouponBasedOnRequest.getRequest().getCouponBasedOn().isEmpty()) {
				queryParameter.put("otsCouponStatus",getCouponBasedOnRequest.getRequest().getCouponStatus());
				couponList = super.getResultListByNamedQuery("OtsCoupon.findByOtsCouponStatus", queryParameter);
			}else {
				queryParameter.put("otsCouponStatus",getCouponBasedOnRequest.getRequest().getCouponStatus());
				queryParameter.put("otsCouponBasedOn",getCouponBasedOnRequest.getRequest().getCouponBasedOn());
				couponList = super.getResultListByNamedQuery("OtsCoupon.getCouponsByStatusAndCategory", queryParameter);
			}
			couponDetails =  couponList.stream().map(coupon -> convertCouponDetailsFromEntityToDomain(coupon)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
            logger.error("Exception while fetching data from DB :"+e.getMessage());
            throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}
	
	@Override
	public String updateCouponCount(String couponId) {
		String status = "active";
		OtsCoupon couponList = new OtsCoupon();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCouponId",Integer.parseInt(couponId));	
			queryParameter.put("otsCouponStatus",status);	
			couponList = super.getResultByNamedQuery("OtsCoupon.getCouponsBasedOnStatus", queryParameter);
			if(couponList.getOtsCouponUnit()==0) 
			{
				couponList.setOtsCouponStatus("inactive");
				return "Coupon is Empty";
			}else {
				couponList.setOtsCouponUnit(couponList.getOtsCouponUnit()-1);
				couponList = super.getResultByNamedQuery("OtsCoupon.getCouponsBasedOnStatus", queryParameter);
				if(couponList.getOtsCouponUnit()==0) {
					couponList.setOtsCouponStatus("inactive");
				}
			}
			save(couponList);
			return "Coupon count updated successfully";
			
		}catch (NoResultException e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public Coupon getCouponDetailsByStatus(String couponId, String status) {
		Coupon couponDetails = new Coupon();
		OtsCoupon couponList = new OtsCoupon();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCouponId",Integer.parseInt(couponId));	
			queryParameter.put("otsCouponStatus",status);	
			couponList = super.getResultByNamedQuery("OtsCoupon.getCouponsBasedOnStatus", queryParameter);
			couponDetails = convertCouponDetailsFromEntityToDomain(couponList);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
            logger.error("Exception while fetching data from DB :"+e.getMessage());
            throw new BusinessException(e.getMessage(), e);
		}
		return couponDetails;
	}

}
