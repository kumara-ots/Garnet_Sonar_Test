package com.fuso.enterprise.ots.srv.api.service.functional;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceOrder;
import com.fuso.enterprise.ots.srv.api.service.request.AssignServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CancelServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceCustomerOrdersByStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceOrderByProviderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.InsertServiceOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateServiceOrderRequest;

public interface OTSServiceOrder {

	ServiceOrder insertServiceOrder(InsertServiceOrderRequest insertServiceOrderRequest);

	ServiceOrder getServiceOrderDetailsbyOrderId(String serviceOrderId);

	String updateServiceOrderStatus(UpdateServiceOrderRequest updateServiceOrderRequest);

	String assignServiceOrder(AssignServiceOrderRequest assignServiceOrderRequest);

	String cancelServiceOrder(CancelServiceOrderRequest cancelServiceOrderRequest);

	List<ServiceOrder> getProviderServiceOrderByStatus(GetServiceOrderByProviderRequest getServiceOrderByProviderRequest);

	List<ServiceOrder> getCustomerServiceOrderByStatus(GetServiceCustomerOrdersByStatusRequest getServiceCustomerOrdersByStatusRequest);

}
