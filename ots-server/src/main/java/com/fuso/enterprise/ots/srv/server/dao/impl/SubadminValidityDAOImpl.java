package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.math.BigDecimal;
import java.sql.Date;
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

import com.fuso.enterprise.ots.srv.api.model.domain.SubadminValidity;
import com.fuso.enterprise.ots.srv.api.service.request.AddUpdateSubadminValidity;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.SubadminValidityDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsSubadminValidity;
import com.fuso.enterprise.ots.srv.server.model.entity.Useraccounts;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class SubadminValidityDAOImpl extends AbstractIptDao<OtsSubadminValidity, String> implements SubadminValidityDAO 
{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public SubadminValidityDAOImpl() {
        super(OtsSubadminValidity.class);
    }
    
	@Override
	public String addSubAdminValidity(AddUpdateSubadminValidity addUpdateSubadminValidity) {
	    OtsSubadminValidity otsSubadminValidity = new OtsSubadminValidity();
	    try {
	        Useraccounts userAccounts = new Useraccounts();
	        userAccounts.setAccountId(UUID.fromString(addUpdateSubadminValidity.getRequest().getOtsAccountId()));
	        otsSubadminValidity.setOtsAccountId(userAccounts);
	        
	        otsSubadminValidity.setOtsDistributorCount(Integer.parseInt(addUpdateSubadminValidity.getRequest().getOtsDistributorCount()));
	        otsSubadminValidity.setOtsValidityEnd(java.sql.Date.valueOf(addUpdateSubadminValidity.getRequest().getOtsValidityEnd()));
	        otsSubadminValidity.setOtsValidityStart(java.sql.Date.valueOf(addUpdateSubadminValidity.getRequest().getOtsValidityStart()));
	        BigDecimal transactioncharges = new BigDecimal(addUpdateSubadminValidity.getRequest().getOtsTransactionCharges());
	        otsSubadminValidity.setOtsTransactionCharges(transactioncharges);
	        
	        save(otsSubadminValidity);
	    } catch (Exception e) {
	        logger.error("Exception while fetching data from DB:" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Throwable e) {
	        logger.error("Exception while fetching data from DB:" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
	    }
	    return "Validity Added Successfully";
	}

	@Override
	public String updateSubAdminValidity(AddUpdateSubadminValidity addUpdateSubadminValidity) {
		OtsSubadminValidity subadminValidity = new OtsSubadminValidity();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsValidityId", Integer.parseInt(addUpdateSubadminValidity.getRequest().getOtsValidityId()));
			subadminValidity = super.getResultByNamedQuery("OtsSubadminValidity.findByOtsValidityId", queryParameter);
			
			subadminValidity.setOtsDistributorCount(Integer.parseInt(addUpdateSubadminValidity.getRequest().getOtsDistributorCount()));
			subadminValidity.setOtsValidityStart((Date.valueOf(addUpdateSubadminValidity.getRequest().getOtsValidityStart())));
			subadminValidity.setOtsValidityEnd(Date.valueOf(addUpdateSubadminValidity.getRequest().getOtsValidityEnd()));
			BigDecimal transactioncharges = new BigDecimal(addUpdateSubadminValidity.getRequest().getOtsTransactionCharges());
			subadminValidity.setOtsTransactionCharges(transactioncharges);
	
			save(subadminValidity);
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return "Validity Updated Succesfully";
	}

	@Override
	public SubadminValidity getSubAdminValidity(String subAdminId) {
    	List<SubadminValidity> subadminValidity = new ArrayList<SubadminValidity>();
    	List<OtsSubadminValidity> otsSubadminValidity = new ArrayList<OtsSubadminValidity>();
    	try {
    	
        	Map<String, Object> queryParameter = new HashMap<>();
        	Useraccounts userAccountId = new Useraccounts();
        	userAccountId.setAccountId(UUID.fromString(subAdminId));
        	
			queryParameter.put("otsAccountId",userAccountId);
			otsSubadminValidity = super.getResultListByNamedQuery("OtsSubadminValidity.getSubAdminValidity", queryParameter);	;
			subadminValidity =  otsSubadminValidity.stream().map(OtsSubadminValidity -> convertSubAdminValidityFromEntityToDomain(OtsSubadminValidity)).collect(Collectors.toList());  
			if(subadminValidity.size() != 0) {
				return subadminValidity.get(0);
			}else {
				return null;
			}
    	}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
	}
	
	private SubadminValidity convertSubAdminValidityFromEntityToDomain(OtsSubadminValidity otsSubadminValidity)
	{
		SubadminValidity subadminValidity = new SubadminValidity();
		subadminValidity.setOtsValidityId(otsSubadminValidity.getOtsValidityId()==null?null:otsSubadminValidity.getOtsValidityId().toString());	
		subadminValidity.setOtsAccountId(otsSubadminValidity.getOtsAccountId()==null?null:otsSubadminValidity.getOtsAccountId().toString());		
		subadminValidity.setOtsValidityStart(otsSubadminValidity.getOtsValidityStart()==null?null:otsSubadminValidity.getOtsValidityStart().toString());	
		subadminValidity.setOtsValidityEnd(otsSubadminValidity.getOtsValidityEnd()==null?null:otsSubadminValidity.getOtsValidityEnd().toString());	
		subadminValidity.setOtsDistributorCount(otsSubadminValidity.getOtsDistributorCount()==null?null:otsSubadminValidity.getOtsDistributorCount().toString());	
		subadminValidity.setOtsTransactionCharges(otsSubadminValidity.getOtsTransactionCharges()==null?null:otsSubadminValidity.getOtsTransactionCharges().toString());	
		
        return subadminValidity;
    }
	
}
