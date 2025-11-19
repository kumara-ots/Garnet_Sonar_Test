package com.fuso.enterprise.ots.srv.api.service.request;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetails;

public class OrderDetailsBORequest {
  List<OrderDetails> orderDetails;

public List<OrderDetails> getOrderDetails() {
	return orderDetails;
}

public void setOrderDetails(List<OrderDetails> orderDetails) {
	this.orderDetails = orderDetails;
}


}
