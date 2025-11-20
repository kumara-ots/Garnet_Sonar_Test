package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.RegistrationTransactionCancelRecords;
import com.fuso.enterprise.ots.srv.api.service.request.AddRegistrationTransactionCancelRecord;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.RegistrationTransactionCancelRecordsDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsRegistrationTransactionCancelRecords;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class RegistrationTransactionCancelRecordsDAOImpl  extends AbstractIptDao<OtsRegistrationTransactionCancelRecords, String> implements RegistrationTransactionCancelRecordsDAO {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public RegistrationTransactionCancelRecordsDAOImpl() {
		super(OtsRegistrationTransactionCancelRecords.class);
		// TODO Auto-generated constructor stub
	}
	
	private RegistrationTransactionCancelRecords convertRegistrationTransactionCancelRecordFromEntityToDomain(OtsRegistrationTransactionCancelRecords otsRegistrationTransactionCancelRecords) {
		RegistrationTransactionCancelRecords registrationTransactionCancelRecords = new RegistrationTransactionCancelRecords();
		registrationTransactionCancelRecords.setOtsRegistrationTrackingId((otsRegistrationTransactionCancelRecords.getOtsRegistrationTrackingId() == null) ? null :otsRegistrationTransactionCancelRecords.getOtsRegistrationTrackingId().toString());
		registrationTransactionCancelRecords.setOtsRegistrationAmount((otsRegistrationTransactionCancelRecords.getOtsRegistrationTrackingId() == null)? null :otsRegistrationTransactionCancelRecords.getOtsRegistrationAmount());
		registrationTransactionCancelRecords.setOtsRegistrationTransactionStatus((otsRegistrationTransactionCancelRecords.getOtsRegistrationTransactionStatus() == null)? null :otsRegistrationTransactionCancelRecords.getOtsRegistrationTransactionStatus());
		registrationTransactionCancelRecords.setOtsRegistrationTransactionDate((otsRegistrationTransactionCancelRecords.getOtsRegistrationTransactionDate() == null) ? null :otsRegistrationTransactionCancelRecords.getOtsRegistrationTransactionDate().toString());
		registrationTransactionCancelRecords.setOtsUsersId((otsRegistrationTransactionCancelRecords.getOtsUsersId()== null)? null:otsRegistrationTransactionCancelRecords.getOtsUsersId().toString());
		registrationTransactionCancelRecords.setOtsRegistrationTransactionId((otsRegistrationTransactionCancelRecords.getOtsRegistrationTransactionId()== null)? null:otsRegistrationTransactionCancelRecords.getOtsRegistrationTransactionId().toString());
		return registrationTransactionCancelRecords;
		
	}

	@Override
	public String addRegistrationTransactionCancelRecords(AddRegistrationTransactionCancelRecord addRegistrationTransactionCancelRecord) {
		OtsRegistrationTransactionCancelRecords registrationTransactionCancelRecords = new OtsRegistrationTransactionCancelRecords();
		//Generate Current timestamp 
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(utilDate.getTime());
		try {
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(addRegistrationTransactionCancelRecord.getRequest().getOtsUsersId()));
			registrationTransactionCancelRecords.setOtsUsersId(DistributorId);
			
			registrationTransactionCancelRecords.setOtsRegistrationTrackingId(addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationTrackingId());
			registrationTransactionCancelRecords.setOtsRegistrationTransactionStatus(addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationTransactionStatus());
			registrationTransactionCancelRecords.setOtsRegistrationAmount(addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationAmount());
			registrationTransactionCancelRecords.setOtsRegistrationTransactionId(addRegistrationTransactionCancelRecord.getRequest().getOtsRegistrationTransactionId());
			registrationTransactionCancelRecords.setOtsRegistrationTransactionDate(currentDateTime);
			
			save(registrationTransactionCancelRecords);
		
		}catch (Exception e) {
	        logger.error("Exception while fetching data from DB:" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Throwable e) {
	        logger.error("Exception while fetching data from DB:" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
	    }
	    return "Inserted";
	}
}