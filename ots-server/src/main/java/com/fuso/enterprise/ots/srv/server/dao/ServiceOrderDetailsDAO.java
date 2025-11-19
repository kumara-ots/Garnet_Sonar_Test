package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceOrderDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AssignServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CancelServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateServiceOrderRequest;

public interface ServiceOrderDetailsDAO {

	ServiceOrderDetails insertServiceOrderDetails(ServiceOrderDetails serviceOrderDetails);

	ServiceOrderDetails getServiceOrderDeatilsByServiceOrderId(String serviceorderId);

	String updateServiceOrderStatus(UpdateServiceOrderRequest updateServiceOrderRequest);

	String assignServiceOrder(AssignServiceOrderRequest assigneServiceOrderRequest);

	String cancelServiceOrder(CancelServiceOrderRequest cancelServiceOrderRequest);

	List<ServiceOrderDetails> getServiceOrderDetailsByProviderAndStatus(String providerId, String serviceOrderStatus);

	List<ServiceOrderDetails> getServiceOrderDetailsByCustomerAndStatus(String customerId, String serviceOrderStatus);

}
