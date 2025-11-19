package com.fuso.enterprise.ots.srv.server.dao;

import com.fuso.enterprise.ots.srv.api.service.request.AddTransactionCancelRecordsRequest;

public interface PaymentTransactionCancelDAO {

	String addPaymentTransactionCancelRecords(AddTransactionCancelRecordsRequest addTransactionCancelRecordsRequest);

}
