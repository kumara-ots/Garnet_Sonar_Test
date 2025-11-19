package com.fuso.enterprise.ots.srv.server.dao;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceOrder;

public interface ServiceOrderDAO {

	ServiceOrder insertServiceOrder(ServiceOrder serviceOrder);

	ServiceOrder getServiceOrderByOrderId(String serviceOrderId);

}
