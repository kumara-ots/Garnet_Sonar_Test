package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.service.request.MapUsersDataBORequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.UserMapDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUserMapping;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class UserMapDAOImpl extends AbstractIptDao<OtsUserMapping, String> implements UserMapDAO {
private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	public UserMapDAOImpl() {
		super(OtsUserMapping.class);
	}
	
	@Override
	public String mappUser(MapUsersDataBORequest mapUsersDataBORequest) {
		String responseData;
		try{
			OtsUserMapping userMappEntity=new OtsUserMapping();
			OtsUsers otsUsers = new OtsUsers();
			otsUsers.setOtsUsersId(UUID.fromString(mapUsersDataBORequest.getRequestData().getUserId()));
			userMappEntity.setOtsUsersId(otsUsers);
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId", otsUsers);
			
			try {
				userMappEntity = super.getResultByNamedQuery("OtsUserMapping.getDistributorId", queryParameter);
				userMappEntity.setOtsMappedTo(mapUsersDataBORequest.getRequestData().getMappedTo());
				try {
					super.getEntityManager().merge(userMappEntity);
				}catch (NoResultException e) {
					logger.error("Exception while Inserting data to DB :"+e.getMessage());
		        	throw new BusinessException(e.getMessage(), e);
				}
			}catch(Exception e){
				try {
					userMappEntity.setOtsMappedTo(mapUsersDataBORequest.getRequestData().getMappedTo());
					super.getEntityManager().merge(userMappEntity);
				}catch (NoResultException e1) {
					logger.error("Exception while Inserting data to DB :"+e.getMessage());
		        	throw new BusinessException(e.getMessage(), e);
				}
			}
			responseData="User Mapped Successfully";
			logger.info("Inside Event=1005,Class:UserMapDAOImpl,Method:mappUser");
		}catch (NoResultException e) {
        	logger.error("Exception while Inserting data to DB  :"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
        }
		return  responseData;
	}
		
	@Override
	public String getMappedDistributor(String userId) {
		String responseData;
		try{
			OtsUserMapping userMappEntity=new OtsUserMapping();
			OtsUsers otsUsers = new OtsUsers();
			otsUsers.setOtsUsersId(UUID.fromString(userId));
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsUsersId", otsUsers);
			userMappEntity = super.getResultByNamedQuery("OtsUserMapping.getDistributorId", queryParameter);
			responseData = userMappEntity.getOtsMappedTo().toString();
			logger.info("Inside Event=1005,Class:UserMapDAOImpl,Method:mappUser");
		}catch (NoResultException e) {
        	logger.error("Exception while Inserting data to DB  :"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
        }
		return  responseData;
	}

	@Override
	public List<OtsUserMapping> getUserForDistributor(String userId) {
		List<OtsUserMapping> userList = new ArrayList<OtsUserMapping>();
		try{
			OtsUsers otsUsers = new OtsUsers();
			otsUsers.setOtsUsersId(UUID.fromString(userId));
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsMappedTo", Integer.parseInt(userId));
			userList = super.getResultListByNamedQuery("OtsUserMapping.findByOtsMappedTo", queryParameter);
			logger.info("Inside Event=1005,Class:UserMapDAOImpl,Method:mappUser");
		}catch (NoResultException e) {
        	logger.error("Exception while Inserting data to DB  :"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
        }
		return  userList;
	}

}
