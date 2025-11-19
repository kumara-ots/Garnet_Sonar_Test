package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCountryMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.ServiceCountry;
import com.fuso.enterprise.ots.srv.api.service.request.AddDistributorCountryMappingRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.DistributorCountryMappingDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductStockDao;
import com.fuso.enterprise.ots.srv.server.dao.ServiceCountryDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsDistributorCountryMapping;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class DistributorCountryMappingDAOImpl extends AbstractIptDao<OtsDistributorCountryMapping, String> implements DistributorCountryMappingDAO {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ServiceCountryDAO serviceCountryDAO;
	
	public DistributorCountryMappingDAOImpl() {
		super(OtsDistributorCountryMapping.class);
		// TODO Auto-generated constructor stub
	}
	
	private DistributorCountryMapping convertDistributorCountryMappingFromEntityToDomain(OtsDistributorCountryMapping otsDistributorCountryMapping) {
		DistributorCountryMapping distributorCountryMapping = new DistributorCountryMapping();
		distributorCountryMapping.setDistributorCountryMappingId((otsDistributorCountryMapping.getOtsDistributorCountryMappingId()== null) ? null: otsDistributorCountryMapping.getOtsDistributorCountryMappingId().toString());
		distributorCountryMapping.setDistributorId((otsDistributorCountryMapping.getOtsDistributorId()== null) ? null: otsDistributorCountryMapping.getOtsDistributorId().getOtsUsersId().toString());
		distributorCountryMapping.setCountryCode((otsDistributorCountryMapping.getOtsCountryCode()== null) ? null: otsDistributorCountryMapping.getOtsCountryCode());
		distributorCountryMapping.setCountryName((otsDistributorCountryMapping.getOtsCountryName()== null) ? null: otsDistributorCountryMapping.getOtsCountryName().toString());
		distributorCountryMapping.setProductId((otsDistributorCountryMapping.getOtsProductId()== null) ? null: otsDistributorCountryMapping.getOtsProductId());
		
		ServiceCountry serviceCountry = serviceCountryDAO.getCountryByCountryCode(otsDistributorCountryMapping.getOtsCountryCode());
		distributorCountryMapping.setCountryCurrency((serviceCountry == null) ? null: serviceCountry.getOtsCountryCurrency());
		distributorCountryMapping.setCountryCurrencySymbol((serviceCountry == null) ? null: serviceCountry.getOtsCountryCurrencySymbol());

		return distributorCountryMapping;
	}
	
	@Override
	public String addDistributorCountryMapping(AddDistributorCountryMappingRequest addDistributorCountryMappingRequest) {
		try {
			for(int i=0; i<addDistributorCountryMappingRequest.getRequest().getDistributorCountryCodeName().size(); i++) {
				OtsDistributorCountryMapping otsDistributorCountryMapping = new OtsDistributorCountryMapping();
				
				OtsUsers DistributorId = new OtsUsers();
				DistributorId.setOtsUsersId(UUID.fromString(addDistributorCountryMappingRequest.getRequest().getDistributorId()));
				
				otsDistributorCountryMapping.setOtsDistributorId(DistributorId);
				otsDistributorCountryMapping.setOtsCountryCode(addDistributorCountryMappingRequest.getRequest().getDistributorCountryCodeName().get(i).getCountryCode());
				otsDistributorCountryMapping.setOtsCountryName(addDistributorCountryMappingRequest.getRequest().getDistributorCountryCodeName().get(i).getCountryName());
		     	save(otsDistributorCountryMapping);
			}
		}catch(Exception e){
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}
	
	@Transactional
	@Override
	public List<DistributorCountryMapping> getCountriesMappedToDistributor(String distributorId) {
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers otsDistributorId = new OtsUsers();
			otsDistributorId.setOtsUsersId(UUID.fromString(distributorId));
			queryParameter.put("otsDistributorId", otsDistributorId);
			
			List<OtsDistributorCountryMapping> otsDistributorCountryMapping = super.getResultListByNamedQuery("OtsDistributorCountryMapping.getCountriesMappedToDistributor", queryParameter);
			List<DistributorCountryMapping> distributorCountryMapping = otsDistributorCountryMapping.stream().map(distributorCountryMap -> convertDistributorCountryMappingFromEntityToDomain(distributorCountryMap)).collect(Collectors.toList());
			
			return distributorCountryMapping;
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
	
	@Transactional
	@Override
	public String deleteDistributorCountryMappingByDistributorId(String distributorId) {
		try {
			OtsUsers otsDistributorId = new OtsUsers();
			otsDistributorId.setOtsUsersId(UUID.fromString(distributorId));
			
			Query query = super.getEntityManager().createNamedQuery("OtsDistributorCountryMapping.deleteDistributorCountryMappingByDistributorId");
			query.setParameter("otsDistributorId", otsDistributorId);

			int deletedCount = query.executeUpdate();

			if (deletedCount > 0) {
				return "Updated Successfully";
			} else {
				return "No Data Found";
			}
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