package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.CompleteOrderDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateOrderProductBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrderPaymentDetailsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCustomerOrderByStatusBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetListOfOrderByDateBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderByStatusRequest;

public interface OrderServiceDAO {

	List<OrderDetails> getOrderBydate(GetOrderBORequest getOrderBORequest);
	List<OrderDetails> getOrderIdByDistributorId(GetOrderByStatusRequest getOrderByStatusRequest);
	OrderDetails insertOrderAndGetOrderId(AddOrUpdateOrderProductBOrequest addOrUpdateOrderProductBOrequest);
	List<OrderDetails> getCustomerOrderStatus(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest);
	List<CompleteOrderDetails> getListOfOrderByDate(GetListOfOrderByDateBORequest getListOfOrderByDateBORequest);
	OrderDetails getOrderDetailsByOrderId(String orderId);
	List<OrderDetails> getOrderReportByDate(GetOrderBORequest getOrderBORequest);
	OrderDetails directSalesVoucher(AddOrUpdateOrderProductBOrequest addOrUpdateOrderProductBOrequest);
	List<OrderDetails> getOrderDetailsForOrderId(String OrderId);
	OrderDetails closeOrder(String OrderId);
	List<OrderDetails> getOrderReportByDistributorAndCustomer(GetOrderBORequest getOrderBORequest);
	List<OrderDetails> checkForFirstOrderByCustomer(String CustomerId);
	String UpdateOrderStatus(String orderId, String orderStatus);
	OrderDetails addPaymentDetailsForOrder(AddOrderPaymentDetailsRequest addOrderPaymentDetailsRequest);
	List<OrderDetails> getOrdersByStatus(String orderStatus);
	List<OrderDetails> getOrderByDistributorOrderProductStatus(String distributorId, String orderProductStatus);
	List<OrderDetails> getRRCOrdersByCustomer(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest);
	List<OrderDetails> getCustomerOrderByOrderProductStatus(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest);
	List<OrderDetails> getOrderByOrderIdOrderProductStatus(String orderId, String orderProductStatus);
	List<OrderDetails> getOrderByOrderIdRRCOrderStatus(String orderId, String rrcOrderStatus);
	List<OrderDetails> getOrderByOrderTransactionId(String orderTransactionId);
	String addProformaInvoiceToDB(String orderId, String invoice);
	
}
