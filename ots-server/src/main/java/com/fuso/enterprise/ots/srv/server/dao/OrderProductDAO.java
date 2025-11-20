package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderedProductDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateOrderTrackingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AssignOrderToEmployeeRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CloseEmployeeOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.EmployeeOrderTransferRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetDistributorSettlementRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetMonthlyDistributorSettlementRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateRRCStatusRequest;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsOrder;

public interface OrderProductDAO {

	List<OrderProductDetails> getUserByStatusAndDistributorId(OrderDetails orderDetails,String DistributerId);
	String assignOrderToEmployee(AssignOrderToEmployeeRequest assignOrderToEmployeeRequest);
	String updateSubOrder(AssignOrderToEmployeeRequest assignOrderToEmployeeRequest);
	OrderProductDetails insertOrdrerProductByOrderId(String orderId,String distributer,OrderedProductDetails orderedProductDetails);
	List<OrderProductDetails> getProductListByOrderId(String orderId);
	String employeeTransferOrder(EmployeeOrderTransferRequest employeeOrderTransferRequest);	//G1
	OrderProductDetails getOrderProductByOrderIdProductId(String orderId,String productId);
	List<OrderProductDetails> checkForOrderClose(List<OrderProductDetails> ProductList);
	List<OrderProductDetails> getOrderForDistributorAndStatus(String distributorId, String subOrderStatus);
	String updateRRCOrderStatus(UpdateRRCStatusRequest updateRRCStatusRequest);
	List<OrderProductDetails> getOrderByDistributorIdAndStatus(String orderId,String distributorId, String orderProductStatus);
	List<OrderProductDetails> getDistributorWeeklySettlemetDetails(GetDistributorSettlementRequest getDistributorSettlementRequest);
	List<OrderProductDetails> getDistributorCurrentWeekSettlemetDetails(String distributorId);
	List<OrderProductDetails> getDistributorMonthlySettlemetDetails(GetMonthlyDistributorSettlementRequest getDistributorSettlementRequest);
	List<OrderProductDetails> getCancelledOrdersByDistributor(String distributorId);
	List<OrderProductDetails> getReturnReplacementOrdersForDistributor(String distributorId);
	List<OrderProductDetails> getRRCOrdersByDistributor(String distributorId, String rrcOrderStatus);
	List<OrderProductDetails> getRRClosedOrdersByDistributor(String distributorId);
	List<OrderProductDetails> updateSubOrderStatus(String orderId, String subOrderStatus);
	OrderProductDetails getOrderProductBySubOrderId(String suborderId);
	List<OrderProductDetails> checkOrderStatusToInactiveDistributor(String distributorId);
	List<OrderProductDetails> getSubOrderDetailsByOrderProductId(String orderProductId);
	List<OrderProductDetails> getOrderProductByOrderIdAndRRCOrderStatus(String orderId, String rrcOrderStatus);
	List<OrderProductDetails> getOrderProductByOrderIdOrderProductStatus(String orderId, String orderProductStatus);
	String addCustomerOrderProductInvoiceToDB(String orderId, String productId, String invoice);
	List<OrderProductDetails> getOrderProductByOrderIdProductIdOPStatus(String orderId,String productId,String orderProductStatus);
	String addOrUpdateOrderTracking(AddOrUpdateOrderTrackingRequest addOrUpdateOrderTrackingRequest);
	List<OrderProductDetails> checkPendingOrdersOfInactiveDistributor(String distributorId);
	String updateOrderProductStatus(String orderProductId, String orderProductStatus);
	String closeEmployeeOrder(CloseEmployeeOrderRequest closeEmployeeOrderRequest);
	
}