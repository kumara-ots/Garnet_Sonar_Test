package com.fuso.enterprise.ots.srv.server.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.PaymentTransactionCancelRecords;
import com.fuso.enterprise.ots.srv.api.service.request.AddTransactionCancelRecordsRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.PaymentTransactionCancelDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsPaymentTransactionCancelRecords;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class PaymentTransactionCancelDAOImpl extends AbstractIptDao<OtsPaymentTransactionCancelRecords, String> implements PaymentTransactionCancelDAO{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public PaymentTransactionCancelDAOImpl() {
		super(OtsPaymentTransactionCancelRecords.class);
		// TODO Auto-generated constructor stub
	}
	
	private PaymentTransactionCancelRecords convertPaymentTranscationCancelRecordsFromEntityToDomain(OtsPaymentTransactionCancelRecords otsPaymentTransactionCancelRecords) {
		PaymentTransactionCancelRecords paymentTransactionCancelRecords = new PaymentTransactionCancelRecords();
		
		paymentTransactionCancelRecords.setOtsTransactionCancelId((otsPaymentTransactionCancelRecords.getOtsTransactionCancelId()==null)? null:otsPaymentTransactionCancelRecords.getOtsTransactionCancelId().toString());
		paymentTransactionCancelRecords.setOtsTransactionCancelOrderId((otsPaymentTransactionCancelRecords.getOtsTransactionCancelOrderId()==null)? null:otsPaymentTransactionCancelRecords.getOtsTransactionCancelOrderId());
		paymentTransactionCancelRecords.setOtsTransactionCancelTrackingId((otsPaymentTransactionCancelRecords.getOtsTransactionCancelTrackingId()==null)? null:otsPaymentTransactionCancelRecords.getOtsTransactionCancelTrackingId());
		paymentTransactionCancelRecords.setOtsTransactionCancelOrderStatus((otsPaymentTransactionCancelRecords.getOtsTransactionCancelOrderStatus()==null)? null:otsPaymentTransactionCancelRecords.getOtsTransactionCancelOrderStatus());
		paymentTransactionCancelRecords.setOtsTransactionCancelAmount((otsPaymentTransactionCancelRecords.getOtsTransactionCancelAmount()==null)? null:otsPaymentTransactionCancelRecords.getOtsTransactionCancelAmount());
		
		return paymentTransactionCancelRecords;
	}
	
	@Override
	public String addPaymentTransactionCancelRecords(AddTransactionCancelRecordsRequest addTransactionCancelRecordsRequest) {
		try {
			OtsPaymentTransactionCancelRecords paymentTransactionCancelRecords = new OtsPaymentTransactionCancelRecords();
			paymentTransactionCancelRecords.setOtsTransactionCancelOrderId(addTransactionCancelRecordsRequest.getRequest().getOtsTransactionCancelOrderId());
			paymentTransactionCancelRecords.setOtsTransactionCancelTrackingId(addTransactionCancelRecordsRequest.getRequest().getOtsTransactionCancelTrackingId());
			paymentTransactionCancelRecords.setOtsTransactionCancelOrderStatus(addTransactionCancelRecordsRequest.getRequest().getOtsTransactionCancelOrderStatus());
			paymentTransactionCancelRecords.setOtsTransactionCancelAmount(addTransactionCancelRecordsRequest.getRequest().getOtsTransactionCancelAmount());
			
			save(paymentTransactionCancelRecords);
		} catch (Exception e) {
			logger.error("Exception while inserting data in DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while inserting data in DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return "Inserted";
	}
	
}
