package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceCountry;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ServiceCountryDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceCountry;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Service
@Transactional
public class ServiceCountryDAOImpl extends AbstractIptDao<OtsServiceCountry, String> implements ServiceCountryDAO {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public ServiceCountryDAOImpl() {
		super(OtsServiceCountry.class);
	}
	
	private ServiceCountry convertCountryDetailsFromEntityToDomain(OtsServiceCountry otsServiceCountry) {
		ServiceCountry serviceCountry = new ServiceCountry();
		serviceCountry.setOtsCountryId((otsServiceCountry.getOtsCountryId()== null) ? null: otsServiceCountry.getOtsCountryId().toString());
		serviceCountry.setOtsCountryCode((otsServiceCountry.getOtsCountryCode()== null) ? null: otsServiceCountry.getOtsCountryCode());
		serviceCountry.setOtsCountryName((otsServiceCountry.getOtsCountryName()== null) ? null: otsServiceCountry.getOtsCountryName());
		serviceCountry.setOtsCountryPhone((otsServiceCountry.getOtsCountryPhone()== null) ? null: otsServiceCountry.getOtsCountryPhone().toString());
		serviceCountry.setOtsCountryCurrency((otsServiceCountry.getOtsCountryCurrency()== null) ? null: otsServiceCountry.getOtsCountryCurrency());
		serviceCountry.setOtsCountryCurrencySymbol((otsServiceCountry.getOtsCountryCurrencySymbol()== null) ? null: otsServiceCountry.getOtsCountryCurrencySymbol());
	
		return serviceCountry;
	}
	
	@Override
	public List<ServiceCountry> getAllCountry() {
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			List<OtsServiceCountry> otsServiceCountry = super.getResultListByNamedQuery("OtsServiceCountry.findAll", queryParameter);
			List<ServiceCountry> countryList = otsServiceCountry.stream().map(serviceCountry -> convertCountryDetailsFromEntityToDomain(serviceCountry)).collect(Collectors.toList());
			
			return countryList;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public ServiceCountry getCountryByCountryCode(String countryCode) {
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCountryCode", countryCode);
			
			OtsServiceCountry otsServiceCountry = super.getResultByNamedQuery("OtsServiceCountry.findByOtsCountryCode", queryParameter);
			ServiceCountry serviceCountry = convertCountryDetailsFromEntityToDomain(otsServiceCountry);
			
			return serviceCountry;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
}