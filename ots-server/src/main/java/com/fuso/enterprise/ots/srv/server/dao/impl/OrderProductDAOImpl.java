package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
import com.fuso.enterprise.ots.srv.api.service.response.GetReviewAndRatingResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;
import com.fuso.enterprise.ots.srv.server.dao.OrderProductDAO;
import com.fuso.enterprise.ots.srv.server.dao.ReviewAndRatingDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsOrder;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsOrderProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class OrderProductDAOImpl extends AbstractIptDao<OtsOrderProduct, String> implements OrderProductDAO {
	
	@Value("${ots.subOrder.number.format}")
	public String subOrderNoFormat;

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ReviewAndRatingDAO reviewAndRatingDAO;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public OrderProductDAOImpl() {
		super(OtsOrderProduct.class);
	}

	@Override
	public long getListOfDeliverdQuantityOfDay(List<OtsOrder> orderList, String otsProductId) {
		int orderProductList = 0;
		try {
			System.out.println("");
			logger.info("Inside Event=1015,Class:OrderProductDAOImpl, Method:getListOfDeliverdQuantityOfDay, orderList:"
					+ orderList + "otsProductId: " + otsProductId);
			OtsProduct otsProduct = new OtsProduct();
			otsProduct.setOtsProductId(UUID.fromString(otsProductId));
			for (int i = 0; i < orderList.size(); i++) {
				Map<String, Object> queryParameter = new HashMap<>();
				queryParameter.put("otsOrderId", orderList.get(i));
				queryParameter.put("otsProductId", otsProduct);
				OtsOrderProduct otsOrderProduct = getQuantity(queryParameter);
				if (otsOrderProduct != null) {
				    orderProductList += otsOrderProduct.getOtsOrderedQty();
				} else {
				    logger.warn("No order product found for Order ID: " + orderList.get(i).getOtsOrderId());
				}
			}
		} catch (Exception e) {
			return 0;
		}
		return orderProductList;

	}
	  
	public OtsOrderProduct getQuantity(Map<String, Object> queryParameter) {
		OtsOrderProduct otsOrderProduct = new OtsOrderProduct();
		try {
			otsOrderProduct = super.getResultByNamedQuery("OtsOrder.fetchOtsSoldProducts", queryParameter);
			return otsOrderProduct;
		} catch (NoResultException e) {
			otsOrderProduct.setOtsOrderedQty(0);
			return otsOrderProduct;
		} catch (Exception e) {
			otsOrderProduct.setOtsOrderedQty(0);
			return otsOrderProduct;
		}
	}

	@Override
	public List<OrderProductDetails> getUserByStatusAndDistributorId(OrderDetails orderDetails, String DistributerId) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(DistributerId));
			queryParameter.put("otsOrderId", orderDetails.getOrderId());
			queryParameter.put("otsDistributorId", DistributorId);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.GetOrderByDistrubutorIdAndStatus1",queryParameter);
			otsOrderDetails = OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE" + e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE" + e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
		return otsOrderDetails;
	}
		
	@Override
	public List<OrderProductDetails> getOrderByDistributorIdAndStatus(String orderId,String distributorId, String orderProductStatus) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			OtsOrder otsOrderId = new OtsOrder();
			otsOrderId.setOtsOrderId(UUID.fromString(orderId));
			queryParameter.put("otsOrderId",otsOrderId);
			queryParameter.put("otsDistributorId", DistrubutorId);
			
			//To get order product details based on orderId & distributor Id
			if(orderProductStatus == null || orderProductStatus.equals("") || orderProductStatus.equals("All")) {
				OrderList = super.getResultListByNamedQuery("OtsOrderProduct.getOrderProductByDistributorAndOrderId", queryParameter);
			}else {
				//To get order product details based on orderId, distributor Id & orderProductStatus
				queryParameter.put("otsOrderProductStatus", orderProductStatus);
				OrderList = super.getResultListByNamedQuery("OtsOrderProduct.getOrderByDistrubutorIdAndOrderProductStatus", queryParameter);
			}
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
		catch (Throwable e) {
			e.printStackTrace();
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
		return otsOrderDetails;
	}

	private OrderProductDetails convertOrderDetailsFromEntityToDomain(OtsOrderProduct otsOrderProduct) {
		OrderProductDetails orderDetails =  new OrderProductDetails();
		orderDetails.setOtsOrderId((otsOrderProduct.getOtsOrderId().getOtsOrderId()==null?"":otsOrderProduct.getOtsOrderId().getOtsOrderId().toString()));
		orderDetails.setOtsOrderNumber((otsOrderProduct.getOtsOrderId().getOtsOrderNumber()==null?"":otsOrderProduct.getOtsOrderId().getOtsOrderNumber()));
		orderDetails.setOtsDeliveredQty(otsOrderProduct.getOtsDeliveredQty()==null?"":otsOrderProduct.getOtsDeliveredQty().toString());
		orderDetails.setProductName(otsOrderProduct.getOtsProductName()==null?"":otsOrderProduct.getOtsProductName());
		orderDetails.setOtsProductImage(otsOrderProduct.getOtsProductImage() == null ?"" :otsOrderProduct.getOtsProductImage());
		orderDetails.setOtsOrderProductCost(otsOrderProduct.getOtsOrderProductCost()==null?"":otsOrderProduct.getOtsOrderProductCost().toString());
		orderDetails.setOtsOrderedQty(otsOrderProduct.getOtsOrderedQty()==null?"":otsOrderProduct.getOtsOrderedQty().toString()); 
		orderDetails.setOtsOrderProductStatus(otsOrderProduct.getOtsOrderProductStatus()==null?"":otsOrderProduct.getOtsOrderProductStatus().toString());
		orderDetails.setOtsOrderProductId(otsOrderProduct.getOtsOrderProductId()==null?"":otsOrderProduct.getOtsOrderProductId().toString());
		orderDetails.setOtsProductId(otsOrderProduct.getOtsProductId().getOtsProductId()==null?"":otsOrderProduct.getOtsProductId().getOtsProductId().toString());
		orderDetails.setOtsSubOrderId(otsOrderProduct.getOtsSuborderId()==null?"":otsOrderProduct.getOtsSuborderId().toString());
		orderDetails.setProductImage(otsOrderProduct.getOtsProductImage()==null?"":otsOrderProduct.getOtsProductImage());
		orderDetails.setDistributorId(otsOrderProduct.getOtsDistributorId()==null?null:otsOrderProduct.getOtsDistributorId().getOtsUsersId().toString());	//G1
		orderDetails.setAssignedId(otsOrderProduct.getOtsAssignedId()==null?null:otsOrderProduct.getOtsAssignedId().getOtsUsersId().toString());	//G1
		orderDetails.setSubOrderDeliveredDate(otsOrderProduct.getOtsSuborderDeliveredDt()==null?"":otsOrderProduct.getOtsSuborderDeliveredDt().toString());
		orderDetails.setSubOrderAssignedDate(otsOrderProduct.getOtsSuborderAssignedDate()==null?"":otsOrderProduct.getOtsSuborderAssignedDate().toString());
		orderDetails.setSubOrderOfdDate(otsOrderProduct.getOtsSuborderOfdDate()==null?"":otsOrderProduct.getOtsSuborderOfdDate().toString());
		orderDetails.setSubOrderPickupDate(otsOrderProduct.getOtsSuborderPickupDate()==null?"":otsOrderProduct.getOtsSuborderPickupDate().toString());
		orderDetails.setOtsOrderDate(otsOrderProduct.getOtsOrderId().getOtsOrderDt()==null?"":otsOrderProduct.getOtsOrderId().getOtsOrderDt().toString());
		orderDetails.setDistributorFirstName(otsOrderProduct.getOtsDistributorId().getOtsUsersFirstname()==null?"":otsOrderProduct.getOtsDistributorId().getOtsUsersFirstname().toString());
		orderDetails.setDistributorLastName(otsOrderProduct.getOtsDistributorId().getOtsUsersLastname()==null?"":otsOrderProduct.getOtsDistributorId().getOtsUsersLastname().toString());
		orderDetails.setDistributorEmailId(otsOrderProduct.getOtsDistributorId().getOtsUsersEmailid()==null?"":otsOrderProduct.getOtsDistributorId().getOtsUsersEmailid());
		orderDetails.setOtsProductCancellationAvailability(otsOrderProduct.getOtsProductCancellationAvailability()==null?null:otsOrderProduct.getOtsProductCancellationAvailability());
		orderDetails.setOtsProductReplacementAvailability(otsOrderProduct.getOtsProductReplacementAvailability()==null?null:otsOrderProduct.getOtsProductReplacementAvailability());
		orderDetails.setOtsProductReplacementDays(otsOrderProduct.getOtsProductReplacementDays()==null?"":otsOrderProduct.getOtsProductReplacementDays().toString());
		orderDetails.setOtsProductReturnAvailability(otsOrderProduct.getOtsProductReturnAvailability()==null?null:otsOrderProduct.getOtsProductReturnAvailability());
		orderDetails.setOtsProductReturnDays(otsOrderProduct.getOtsProductReturnDays()==null?"":otsOrderProduct.getOtsProductReturnDays().toString());
		orderDetails.setEmployeeFirstName(otsOrderProduct.getOtsAssignedId()==null?"":otsOrderProduct.getOtsAssignedId().getOtsUsersFirstname());
		orderDetails.setEmployeeSecondName(otsOrderProduct.getOtsAssignedId()==null?"":otsOrderProduct.getOtsAssignedId().getOtsUsersLastname());
		orderDetails.setEmployeeEmailId(otsOrderProduct.getOtsAssignedId()==null?"":otsOrderProduct.getOtsAssignedId().getOtsUsersEmailid());
		orderDetails.setRrcOrderStatus(otsOrderProduct.getOtsRrcOrderStatus()==null?"":otsOrderProduct.getOtsRrcOrderStatus());
		orderDetails.setRrcCustomerInitiatedDate(otsOrderProduct.getOtsCustomerRrcInitiatedDate()==null?"":otsOrderProduct.getOtsCustomerRrcInitiatedDate().toString());
		orderDetails.setRrcDistributorInitiatedDate(otsOrderProduct.getOtsDistributorRrcInitiatedDate()==null?"":otsOrderProduct.getOtsDistributorRrcInitiatedDate().toString());
		orderDetails.setProductSellerPrice(otsOrderProduct.getOtsProductSellerPrice()==null?null:otsOrderProduct.getOtsProductSellerPrice().toString());
		orderDetails.setProductBasePrice(otsOrderProduct.getOtsProductBasePrice()==null?"":otsOrderProduct.getOtsProductBasePrice().toString());
		orderDetails.setProductPrice(otsOrderProduct.getOtsProductPrice()==null?"":otsOrderProduct.getOtsProductPrice().toString());
		orderDetails.setProductPriceWithoutGst(otsOrderProduct.getOtsProductPriceWithoutGst()==null?"":otsOrderProduct.getOtsProductPriceWithoutGst().toString());
		orderDetails.setCustomerId(otsOrderProduct.getOtsOrderId().getOtsCustomerId()==null?null:otsOrderProduct.getOtsOrderId().getOtsCustomerId().getOtsUsersId().toString());
		orderDetails.setCustomerName(otsOrderProduct.getOtsOrderId().getOtsCustomerName()==null?"":otsOrderProduct.getOtsOrderId().getOtsCustomerName());
		orderDetails.setDeliveryAddress((otsOrderProduct.getOtsOrderId().getOtsHouseNo()==null?"":otsOrderProduct.getOtsOrderId().getOtsHouseNo())
				+" "+(otsOrderProduct.getOtsOrderId().getOtsBuildingName()==null?"":otsOrderProduct.getOtsOrderId().getOtsBuildingName())
				+" "+(otsOrderProduct.getOtsOrderId().getOtsStreetName()==null?"":otsOrderProduct.getOtsOrderId().getOtsStreetName())
				+" "+(otsOrderProduct.getOtsOrderId().getOtsCityName()==null?"":otsOrderProduct.getOtsOrderId().getOtsCityName())
				+" "+(otsOrderProduct.getOtsOrderId().getOtsPincode()==null?"":otsOrderProduct.getOtsOrderId().getOtsPincode()));
		orderDetails.setCustomerContactNo(otsOrderProduct.getOtsOrderId().getOtsCustomerContactNo()==null?"":otsOrderProduct.getOtsOrderId().getOtsCustomerContactNo());
		orderDetails.setOrderProductCustomerInvoice(otsOrderProduct.getOtsOrderProductCustomerInvoice()==null?"":otsOrderProduct.getOtsOrderProductCustomerInvoice());
		orderDetails.setOtsTrackingId(otsOrderProduct.getOtsTrackingId()==null?"":otsOrderProduct.getOtsTrackingId());
		orderDetails.setOtsTrackingUrl(otsOrderProduct.getOtsTrackingUrl()==null?"":otsOrderProduct.getOtsTrackingUrl());
		orderDetails.setOtsTrackingLogistics(otsOrderProduct.getOtsTrackingLogistics()==null?"":otsOrderProduct.getOtsTrackingLogistics());
		orderDetails.setOtsOndcFulfillmentId(otsOrderProduct.getOtsOndcFulfillmentId()==null?"":otsOrderProduct.getOtsOndcFulfillmentId());
		orderDetails.setOtsOndcFulfillmentStatus(otsOrderProduct.getOtsOndcFulfillmentStatus()==null?"":otsOrderProduct.getOtsOndcFulfillmentStatus());
		orderDetails.setOtsProductReturnDeliveryCharge(otsOrderProduct.getOtsProductReturnDeliveryCharge() == null?"":otsOrderProduct.getOtsProductReturnDeliveryCharge().toString());
		orderDetails.setOtsProductDeliveryCharge(otsOrderProduct.getOtsProductDeliveryCharge() == null? "":otsOrderProduct.getOtsProductDeliveryCharge());
		orderDetails.setOtsProductGst(otsOrderProduct.getOtsProductGst() == null? "":otsOrderProduct.getOtsProductGst());
		orderDetails.setOtsProductGstPrice(otsOrderProduct.getOtsProductGstPrice() == null? "":otsOrderProduct.getOtsProductGstPrice());
		orderDetails.setOtsProductPercentage(otsOrderProduct.getOtsProductPercentage()== null? "":otsOrderProduct.getOtsProductPercentage());
		orderDetails.setOtsProductDiscountPrice(otsOrderProduct.getOtsProductDiscountPrice() == null ? "" :otsOrderProduct.getOtsProductDiscountPrice());
		orderDetails.setOtsSettlementDate(otsOrderProduct.getOtsSettlementDate() == null ? "" :otsOrderProduct.getOtsSettlementDate().toString());
		orderDetails.setOtsSettlementStatus(otsOrderProduct.getOtsSettlementStatus() == null ? "" :otsOrderProduct.getOtsSettlementStatus().toString());	
		orderDetails.setOtsProductCountry(otsOrderProduct.getOtsProductCountry()== null? "":otsOrderProduct.getOtsProductCountry());
		orderDetails.setOtsProductCountryCode(otsOrderProduct.getOtsProductCountryCode() == null ? "" :otsOrderProduct.getOtsProductCountryCode());
		orderDetails.setOtsProductCurrency(otsOrderProduct.getOtsProductCurrency() == null ? "" :otsOrderProduct.getOtsProductCurrency());
		orderDetails.setOtsProductCurrencySymbol(otsOrderProduct.getOtsProductCurrencySymbol() == null ? "" :otsOrderProduct.getOtsProductCurrencySymbol());
		
		//we will get review rating only when customerId is present for order
		if(otsOrderProduct.getOtsOrderId().getOtsCustomerId() != null) {
			//setting Review Rating Details for Order
			List<GetReviewAndRatingResponse> productRating = reviewAndRatingDAO.getReviewAndRatingByOrderId(otsOrderProduct.getOtsOrderId().getOtsOrderId().toString(),otsOrderProduct.getOtsProductId().getOtsProductId().toString(),otsOrderProduct.getOtsOrderId().getOtsCustomerId().getOtsUsersId().toString());
			orderDetails.setProductReviewRating(productRating);
		}
		
		return orderDetails;		
	}

	@Transactional
	@Override	
	public OrderProductDetails insertOrdrerProductByOrderId(String orderId,String distributer,OrderedProductDetails orderedProductDetails) {
		OrderProductDetails orderProductDetails =  new OrderProductDetails();
		OtsOrderProduct otsOrderProduct = new OtsOrderProduct();
		try {		
			OtsOrder otsOrderId = new OtsOrder();
			otsOrderId.setOtsOrderId(UUID.fromString(orderId));
			
			OtsUsers distributorId =new OtsUsers();
			distributorId.setOtsUsersId(UUID.fromString(distributer));
			
			OtsProduct productId= new OtsProduct();
			productId.setOtsProductId(UUID.fromString(orderedProductDetails.getProductId()));
			
			otsOrderProduct.setOtsOrderId(otsOrderId);
			otsOrderProduct.setOtsDistributorId(distributorId);
			otsOrderProduct.setOtsProductId(productId);
			otsOrderProduct.setOtsOrderedQty(Integer.parseInt(orderedProductDetails.getOrderedQty()));
			otsOrderProduct.setOtsOrderProductStatus(orderedProductDetails.getProductStatus());
			otsOrderProduct.setOtsOrderProductCost(new BigDecimal(orderedProductDetails.getProductCost()));
			otsOrderProduct.setOtsProductPriceWithoutGst(new BigDecimal(orderedProductDetails.getProductPriceWithoutGst()));
			otsOrderProduct.setOtsProductSellerPrice(new BigDecimal(orderedProductDetails.getProductSellerPrice()));
			otsOrderProduct.setOtsProductBasePrice(new BigDecimal(orderedProductDetails.getProductBasePrice()));
			otsOrderProduct.setOtsProductPrice(new BigDecimal(orderedProductDetails.getProductPrice()));
			otsOrderProduct.setOtsProductGst(orderedProductDetails.getProductGst());
			otsOrderProduct.setOtsProductGstPrice(orderedProductDetails.getProductGstPrice());
			otsOrderProduct.setOtsProductDiscountPrice(orderedProductDetails.getProductDiscountPrice());
			otsOrderProduct.setOtsProductPercentage(orderedProductDetails.getProductPercentage());
			otsOrderProduct.setOtsProductDeliveryCharge(orderedProductDetails.getProductDeliveryCharge());
			otsOrderProduct.setOtsProductReturnDeliveryCharge(Integer.parseInt(orderedProductDetails.getProductReturnDeliveryCharge()));
			otsOrderProduct.setOtsProductName(orderedProductDetails.getProductName());
			otsOrderProduct.setOtsProductImage(orderedProductDetails.getProductImage());
			otsOrderProduct.setOtsProductCancellationAvailability(Boolean.valueOf(orderedProductDetails.getProductCancellationAvailability()));
			otsOrderProduct.setOtsProductReplacementAvailability(Boolean.valueOf(orderedProductDetails.getProductReplacementAvailability()));
			otsOrderProduct.setOtsProductReplacementDays(orderedProductDetails.getProductReplacementDays());
			otsOrderProduct.setOtsProductReturnAvailability(Boolean.valueOf(orderedProductDetails.getPoductReturnAvailability()));
			otsOrderProduct.setOtsProductReturnDays(orderedProductDetails.getProductReturnDays());			
			otsOrderProduct.setOtsProductCountry(orderedProductDetails.getOtsProductCountry());
			otsOrderProduct.setOtsProductCountryCode(orderedProductDetails.getOtsProductCountryCode());
			otsOrderProduct.setOtsProductCurrency(orderedProductDetails.getOtsProductCurrency());
			otsOrderProduct.setOtsProductCurrencySymbol(orderedProductDetails.getOtsProductCurrencySymbol());
			
			//To generate 10 digit Random number for SubOrder Id
//			Random objGenerator = new Random();  
			SecureRandom secureRandom = new SecureRandom();
	   	 	long randomNumber = 1000000000L  + secureRandom.nextInt(900000000);
	   	 	String SubOrderNumber = subOrderNoFormat+randomNumber;
			otsOrderProduct.setOtsSuborderId(SubOrderNumber);
			otsOrderProduct.setOtsSettlementStatus("Pending");
			save(otsOrderProduct);
			
			orderProductDetails = convertOrderDetailsFromEntityToDomain(otsOrderProduct);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_PRODUCT_INSERTION);
		}catch (Throwable e) {
			e.printStackTrace();
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_PRODUCT_INSERTION);
		}	
		return orderProductDetails;
	}
		
	@Override
	public List<OrderProductDetails> getProductListByOrderId(String orderId) {
		try {
			List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
			List<OtsOrderProduct> ProductList = new ArrayList<>();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsOrder otsOrder = new OtsOrder();
			otsOrder.setOtsOrderId(UUID.fromString(orderId));
			queryParameter.put("otsOrderId", otsOrder);
			ProductList = super.getResultListByNamedQuery("OtsOrderProduct.findByotsOrderId", queryParameter);
			otsOrderDetails = ProductList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
			return otsOrderDetails;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE" + e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE" + e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);
		}
	}
	
	//To Assign order to Employee in ots_order_product table
	@Override
	public String assignOrderToEmployee(AssignOrderToEmployeeRequest assignOrderToEmployeeRequest) {
		String assginOrderResponse = null;
		try {
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("order_id",UUID.fromString(assignOrderToEmployeeRequest.getRequest().getOrderId()));
			queryParameters.put("assign_id",UUID.fromString(assignOrderToEmployeeRequest.getRequest().getAssignedId()));
			queryParameters.put("distributor_id",UUID.fromString(assignOrderToEmployeeRequest.getRequest().getDistributorId()));
			queryParameters.put("product_id",UUID.fromString(assignOrderToEmployeeRequest.getRequest().getProductId()));	

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("assign_order")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("order_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("assign_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Updated]")) {
				assginOrderResponse = "Updated";
			}
			else {
				assginOrderResponse = "Not Updated";
			}
			return assginOrderResponse;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);}
		catch (Throwable e) {
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}

//	This API isn't in use, make sure to update it if it's in need in future.
	@Override
	public String employeeTransferOrder(EmployeeOrderTransferRequest employeeOrderTransferRequest) {
		// OtsOrder otsOrder = new OtsOrder();
		OtsOrderProduct orderProduct = new OtsOrderProduct();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderId", UUID.fromString(employeeOrderTransferRequest.getRequest().getOrderId()));
			orderProduct = super.getResultByNamedQuery("OtsOrderProduct.GetOrderByOrderId", queryParameter);

			OtsUsers userId = new OtsUsers();
			userId.setOtsUsersId(UUID.fromString(employeeOrderTransferRequest.getRequest().getEmployeeId()));
			orderProduct.setOtsAssignedId(userId);
			return "Updated";
		} catch (Exception e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
	}
	
	@Override
	public OrderProductDetails getOrderProductByOrderIdProductId(String orderId, String productId) {
		try {
			OtsOrderProduct otsOrderProduct = new OtsOrderProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			
			OtsOrder otsorderId = new OtsOrder();
			otsorderId.setOtsOrderId(UUID.fromString(orderId));
			
			OtsProduct otsProductId = new OtsProduct();
			otsProductId.setOtsProductId(UUID.fromString(productId));

			queryParameter.put("otsOrderId",otsorderId);
			queryParameter.put("otsProductId",otsProductId);
			try {
				otsOrderProduct = super.getResultByNamedQuery("OtsOrderProduct.getproductDetailsByorderIdandProductId", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
			OrderProductDetails orderProductDetails =  convertOrderDetailsFromEntityToDomain(otsOrderProduct);
			return orderProductDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}		

	@Override
	public List<OrderProductDetails> checkForOrderClose(List<OrderProductDetails> ProductList) {
		try {
			List<OtsOrderProduct> orderList = new ArrayList<OtsOrderProduct>();
			List<OrderProductDetails> otsOrderDetails = new ArrayList<OrderProductDetails>();
			for (int i = 0; i < ProductList.size(); i++) {
				Map<String, Object> queryParameter = new HashMap<>();
				OtsOrder OtsorderId = new OtsOrder();
				OtsorderId.setOtsOrderId(UUID.fromString(ProductList.get(i).getOtsOrderId()));

				OtsProduct ProductId = new OtsProduct();
				ProductId.setOtsProductId(UUID.fromString(ProductList.get(i).getOtsProductId()));

				queryParameter.put("otsOrderId", OtsorderId);
				queryParameter.put("otsProductId", ProductId);
				orderList = super.getResultListByNamedQuery("OtsOrderProduct.CheckForCloseOrder", queryParameter);
				otsOrderDetails = orderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
			}
			return otsOrderDetails;
		} catch (Exception e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String updateSubOrder(AssignOrderToEmployeeRequest assignOrderToEmployeeRequest) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(utilDate.getTime());
		System.out.println("current date = "+currentDateTime);
		try {
			OtsOrderProduct otsOrderProduct = new OtsOrderProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			
			OtsOrder OtsorderId = new OtsOrder();
			OtsorderId.setOtsOrderId(UUID.fromString(assignOrderToEmployeeRequest.getRequest().getOrderId()));
			
			OtsProduct  ProductId = new OtsProduct();
			ProductId.setOtsProductId(UUID.fromString(assignOrderToEmployeeRequest.getRequest().getProductId()));

			queryParameter.put("otsOrderId",OtsorderId);
			queryParameter.put("otsProductId",ProductId);
			otsOrderProduct = super.getResultByNamedQuery("OtsOrderProduct.getproductDetailsByorderIdandProductId", queryParameter);
		
			otsOrderProduct.setOtsOrderProductStatus(assignOrderToEmployeeRequest.getRequest().getOrderStatus());
			if(assignOrderToEmployeeRequest.getRequest().getOrderStatus().equalsIgnoreCase("Cancel")) {
				otsOrderProduct.setOtsDistributorRrcInitiatedDate(currentDateTime);
			}
			super.getEntityManager().merge(otsOrderProduct);
			String Response = "Updated";
			System.out.println("executed updateSubOrder");
			return Response;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}
	
	@Override
	public String addBillOfSupplyToDB(AssignOrderToEmployeeRequest assignOrderToEmployeeRequest) {
		try {
			OtsOrderProduct otsOrderProduct = new OtsOrderProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers AssginedId = new OtsUsers();
			AssginedId.setOtsUsersId(UUID.fromString(assignOrderToEmployeeRequest.getRequest().getAssignedId()));
			
			OtsOrder OtsorderId = new OtsOrder();
			OtsorderId.setOtsOrderId(UUID.fromString(assignOrderToEmployeeRequest.getRequest().getOrderId()));
			
			OtsProduct  ProductId = new OtsProduct();
			ProductId.setOtsProductId(UUID.fromString(assignOrderToEmployeeRequest.getRequest().getProductId()));

			queryParameter.put("otsOrderId",OtsorderId);
			queryParameter.put("otsProductId",ProductId);
			queryParameter.put("otsAssignedId",AssginedId);
			otsOrderProduct = super.getResultByNamedQuery("OtsOrderProduct.getEmployeeOrderDetails", queryParameter);
		
			otsOrderProduct.setOtsorderbillOfSupply(assignOrderToEmployeeRequest.getRequest().getBillOfSupply());

			String Response = "Bill Of Supply Added";
			return Response;
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<OrderProductDetails> getOrderForDistributorAndStatus(String distributorId, String subOrderStatus) {
		try {
			List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
			List<OtsOrderProduct> ProductList = new ArrayList<>();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(distributorId));
			queryParameter.put("otsDistributorId", DistributorId);
			queryParameter.put("otsOrderProductStatus", subOrderStatus);
			ProductList = super.getResultListByNamedQuery("OtsOrderProduct.getOrderForDistributorAndStatus",queryParameter);
			otsOrderDetails = ProductList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
			System.out.println("otsOrderDetails size = " + otsOrderDetails.size());
			return otsOrderDetails;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String updateRRCOrderStatus(UpdateRRCStatusRequest updateRRCStatusRequest) {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(utilDate.getTime());
		System.out.println("current date = "+currentDateTime);
		try {
			OtsOrderProduct otsOrderProduct = new OtsOrderProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			
			OtsOrder OtsorderId = new OtsOrder();
			OtsorderId.setOtsOrderId(UUID.fromString(updateRRCStatusRequest.getRequest().getOrderId()));
			
			OtsProduct  ProductId = new OtsProduct();
			ProductId.setOtsProductId(UUID.fromString(updateRRCStatusRequest.getRequest().getProductId()));

			queryParameter.put("otsOrderId",OtsorderId);
			queryParameter.put("otsProductId",ProductId);
			otsOrderProduct = super.getResultByNamedQuery("OtsOrderProduct.getproductDetailsByorderIdandProductId", queryParameter);
			
			if(updateRRCStatusRequest.getRequest().getRRCOrderStatus().equalsIgnoreCase("ReturnClose") || updateRRCStatusRequest.getRequest().getRRCOrderStatus().equalsIgnoreCase("ReplaceClose")) {
				otsOrderProduct.setOtsRrcOrderStatus(updateRRCStatusRequest.getRequest().getRRCOrderStatus());
				otsOrderProduct.setOtsDistributorRrcInitiatedDate(currentDateTime);
			}
			if(updateRRCStatusRequest.getRequest().getRRCOrderStatus().equalsIgnoreCase("Cancel")){
				if(otsOrderProduct.getOtsOrderProductStatus().equalsIgnoreCase("New")) {
					otsOrderProduct.setOtsRrcOrderStatus(updateRRCStatusRequest.getRequest().getRRCOrderStatus());
					otsOrderProduct.setOtsCustomerRrcInitiatedDate(currentDateTime);
					otsOrderProduct.setOtsOrderProductStatus("Cancel");
				}
				else {
					return "Cannot Cancel Order now";
				}
			}
			else {
				otsOrderProduct.setOtsRrcOrderStatus(updateRRCStatusRequest.getRequest().getRRCOrderStatus());
				otsOrderProduct.setOtsCustomerRrcInitiatedDate(currentDateTime);
			}
			super.getEntityManager().merge(otsOrderProduct);
	
			return "Updated";
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}
	
	@Override
	public List<OrderProductDetails> getDistributorWeeklySettlemetDetails(GetDistributorSettlementRequest getDistributorSettlementRequest) {
		try {
			List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
			List<OtsOrderProduct> ProductList = new ArrayList<>();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(getDistributorSettlementRequest.getRequest().getDistributorId()));
			queryParameter.put("otsDistributorId",DistributorId);
			queryParameter.put("FromDate",getDistributorSettlementRequest.getRequest().getFromDate());
			queryParameter.put("ToDate",getDistributorSettlementRequest.getRequest().getToDate());
			ProductList = super.getResultListByNamedQuery("OtsOrderProduct.getDistributorWeeklySettlemetDetails", queryParameter);
			otsOrderDetails = ProductList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
			System.out.println("otsOrderDetails size = "+otsOrderDetails.size());
			return otsOrderDetails;
		}
		catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<OrderProductDetails> getDistributorCurrentWeekSettlemetDetails(String distributorId) {
		try {
			List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
			List<OtsOrderProduct> ProductList = new ArrayList<>();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(distributorId));
			queryParameter.put("otsDistributorId",DistributorId);
			ProductList = super.getResultListByNamedQuery("OtsOrderProduct.getDistributorCurrentWeekSettlemetDetails", queryParameter);
			otsOrderDetails = ProductList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
			System.out.println("otsOrderDetails size = "+otsOrderDetails.size());
			return otsOrderDetails;
		}
		catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<OrderProductDetails> getDistributorMonthlySettlemetDetails(GetMonthlyDistributorSettlementRequest getDistributorSettlementRequest) {
		try {
			List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
			List<OtsOrderProduct> ProductList = new ArrayList<>();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(getDistributorSettlementRequest.getRequest().getDistributorId()));
			queryParameter.put("otsDistributorId",DistributorId);
			queryParameter.put("month",Integer.parseInt(getDistributorSettlementRequest.getRequest().getMonth()));
			queryParameter.put("year",Integer.parseInt(getDistributorSettlementRequest.getRequest().getYear()));
			ProductList = super.getResultListByNamedQuery("OtsOrderProduct.getDistributorMonthlySettlemetDetails", queryParameter);
			otsOrderDetails = ProductList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
			System.out.println("otsOrderDetails size = "+otsOrderDetails.size());
			return otsOrderDetails;
		}
		catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<OrderProductDetails> getCancelledOrdersByDistributor(String distributorId) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			
			queryParameter.put("otsDistributorId", DistrubutorId);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.getCancelledOrdersByDistributor", queryParameter);
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
			if(otsOrderDetails.size() != 0) {
				for(int i=0; i<otsOrderDetails.size(); i++) {
					otsOrderDetails.get(i).setOrderCancelledBy("Distributor");
				}
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Override
	public List<OrderProductDetails> getDistributorsOrderCancelledByCustomer(String distributorId) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			
			queryParameter.put("otsDistributorId", DistrubutorId);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.getDistributorsOrderCancelledByCustomer", queryParameter);
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
			if(otsOrderDetails.size() != 0) {
				for(int i=0; i<otsOrderDetails.size(); i++) {
					otsOrderDetails.get(i).setOrderCancelledBy("Customer");
				}
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Override
	public List<OrderProductDetails> getReturnReplacementOrdersForDistributor(String distributorId) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			
			queryParameter.put("otsDistributorId", DistrubutorId);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.getReturnReplacementOrdersForDistributor", queryParameter);
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Override
	public List<OrderProductDetails> getRRCOrdersByDistributor(String distributorId,String rrcOrderStatus) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			
			queryParameter.put("otsDistributorId", DistrubutorId);
			queryParameter.put("otsRrcOrderStatus", rrcOrderStatus);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.getRRCOrdersByDistributor", queryParameter);
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Override
	public List<OrderProductDetails> getRRClosedOrdersByDistributor(String distributorId) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			
			queryParameter.put("otsDistributorId", DistrubutorId);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.getRRClosedOrdersByDistributor", queryParameter);
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Override
	public List<OrderProductDetails> updateSubOrderStatus(String orderId, String subOrderStatus) {
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		List<OrderProductDetails> orderProductDetails = new ArrayList<OrderProductDetails>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsOrder OtsorderId = new OtsOrder();
			OtsorderId.setOtsOrderId(UUID.fromString(orderId));

			queryParameter.put("otsOrderId",OtsorderId);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.GetOrderByOrderId", queryParameter);
			
			for(int i=0; i<OrderList.size(); i++) {
				//To update subOrderStatus for all the products in an Order
				OrderList.get(i).setOtsOrderProductStatus(subOrderStatus);
				super.getEntityManager().merge(OrderList.get(i));
				
				orderProductDetails = OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
			}
//			String Response = "Updated";
			return orderProductDetails;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}
	
	@Override
	public OrderProductDetails getOrderProductBySubOrderId(String suborderId) {
		try {
			OtsOrderProduct otsOrderProduct = new OtsOrderProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsSuborderId",suborderId);
			try {
				otsOrderProduct = super.getResultByNamedQuery("OtsOrderProduct.findByOtsSuborderId", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
			OrderProductDetails orderProductDetails =  convertOrderDetailsFromEntityToDomain(otsOrderProduct);
			return orderProductDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}		
	
	@Override
	public List<OrderProductDetails> checkOrderStatusToInactiveDistributor(String distributorId) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			
			queryParameter.put("otsDistributorId", DistrubutorId);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.checkOrderStatusToInactiveDistributor", queryParameter);
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Override
	public List<OrderProductDetails> getSubOrderDetailsByOrderProductId(String orderProductId) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<OrderProductDetails>();
		List<OtsOrderProduct> ProductList = new ArrayList<OtsOrderProduct>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderProductId",Integer.parseInt(orderProductId));
			ProductList = super.getResultListByNamedQuery("OtsOrderProduct.findByOtsOrderProductId", queryParameter);
			otsOrderDetails = ProductList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Override
	public List<OrderProductDetails> getOrderProductByOrderIdAndRRCOrderStatus(String orderId,String rrcOrderStatus) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsOrder OtsorderId = new OtsOrder();
			OtsorderId.setOtsOrderId(UUID.fromString(orderId));
			
			queryParameter.put("otsOrderId", OtsorderId);
			queryParameter.put("otsRrcOrderStatus", rrcOrderStatus);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.getOrderProductByOrderIdAndRRCOrderStatus", queryParameter);
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Override
	public List<OrderProductDetails> getOrderProductByOrderIdOrderProductStatus(String orderId,String orderProductStatus) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsOrder OtsorderId = new OtsOrder();
			OtsorderId.setOtsOrderId(UUID.fromString(orderId));
			
			queryParameter.put("otsOrderId", OtsorderId);
			if(orderProductStatus.equalsIgnoreCase("All")) {
				OrderList = super.getResultListByNamedQuery("OtsOrderProduct.GetOrderByOrderId", queryParameter);
			}else {
				queryParameter.put("otsOrderProductStatus", orderProductStatus);
				OrderList = super.getResultListByNamedQuery("OtsOrderProduct.getOrderProductByOrderIdOrderProductStatus", queryParameter);
			}
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Transactional
	@Override
	public String addCustomerOrderProductInvoiceToDB(String orderId,String productId, String invoice) {
		OtsOrderProduct orderProduct = new OtsOrderProduct();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsOrder OtsorderId = new OtsOrder();
			OtsorderId.setOtsOrderId(UUID.fromString(orderId));
			
			OtsProduct  ProductId = new OtsProduct();
			ProductId.setOtsProductId(UUID.fromString(productId));

			queryParameter.put("otsOrderId",OtsorderId);
			queryParameter.put("otsProductId",ProductId);
			orderProduct = super.getResultByNamedQuery("OtsOrderProduct.getproductDetailsByorderIdandProductId", queryParameter);
			
			orderProduct.setOtsOrderProductCustomerInvoice(invoice);
			super.getEntityManager().merge(orderProduct);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
		return "Invoice Added";
	}
	
	@Override
	public List<OrderProductDetails> getOrderProductByOrderIdProductIdOPStatus(String orderId, String productId, String orderProductStatus) {
		try {
			List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
			List<OtsOrderProduct> ProductList = new ArrayList<>();
			Map<String, Object> queryParameter = new HashMap<>();
			
			OtsOrder OtsorderId = new OtsOrder();
			OtsorderId.setOtsOrderId(UUID.fromString(orderId));
			
			OtsProduct  ProductId = new OtsProduct();
			ProductId.setOtsProductId(UUID.fromString(productId));

			queryParameter.put("otsOrderId",OtsorderId);
			queryParameter.put("otsProductId",ProductId);
			
			//To get order product details based on orderId, productId
			if(orderProductStatus == null || orderProductStatus.equals("") || orderProductStatus.equals("All")) {
				ProductList = super.getResultListByNamedQuery("OtsOrderProduct.getproductDetailsByorderIdandProductId", queryParameter);
			}else {
				//To get order product details based on orderId, productId & orderProductStatus
				queryParameter.put("otsOrderProductStatus", orderProductStatus);
				ProductList = super.getResultListByNamedQuery("OtsOrderProduct.getOrderProductByOrderIdProductIdOPStatus", queryParameter);
			}
			
			otsOrderDetails = ProductList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
			return otsOrderDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}	
	
	@Override
	public String addOrUpdateOrderTracking(AddOrUpdateOrderTrackingRequest addOrUpdateOrderTrackingRequest) {
		OtsOrderProduct otsOrderProduct = new OtsOrderProduct();
		Map<String, Object> queryParameter = new HashMap<>();
		try {
			queryParameter.put("otsOrderProductId", Integer.parseInt(addOrUpdateOrderTrackingRequest.getRequest().getOrderProductId()));
			otsOrderProduct = super.getResultByNamedQuery("OtsOrderProduct.findByOtsOrderProductId", queryParameter);

			otsOrderProduct.setOtsTrackingId(addOrUpdateOrderTrackingRequest.getRequest().getOtsTrackingId());
			otsOrderProduct.setOtsTrackingUrl(addOrUpdateOrderTrackingRequest.getRequest().getOtsTrackingUrl());
			otsOrderProduct.setOtsTrackingLogistics(addOrUpdateOrderTrackingRequest.getRequest().getOtsTrackingLogistics());
			save(otsOrderProduct);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return "Tracking Details Updated Successfully";
	}
	
	@Override
	public List<OrderProductDetails> checkPendingOrdersOfInactiveDistributor(String distributorId) {
		List<OrderProductDetails> otsOrderDetails = new ArrayList<>();
		List<OtsOrderProduct> OrderList = new ArrayList<>();
		try {  
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			
			queryParameter.put("otsDistributorId", DistrubutorId);
			OrderList = super.getResultListByNamedQuery("OtsOrderProduct.checkPendingOrdersOfInactiveDistributor", queryParameter);
			otsOrderDetails =  OrderList.stream().map(OtsOrderProduct -> convertOrderDetailsFromEntityToDomain(OtsOrderProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return otsOrderDetails;
	}
	
	@Transactional
	@Override
	public String updateOrderProductStatus(String orderProductId, String orderProductStatus) {
		OtsOrderProduct orderProduct = new OtsOrderProduct();
		//Generate Current timestamp 
		java.util.Date utilDate = new java.util.Date();
		
		// Truncate milliseconds using formatting
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(utilDate);

        // Convert back to Timestamp without milliseconds
        Timestamp currentDateTime = Timestamp.valueOf(formattedDate);
		System.out.println("currentDate = "+currentDateTime);
		try {
			Map<String, Object> queryParameter = new HashMap<>();			
			orderProduct.setOtsOrderProductId(Integer.parseInt(orderProductId));

			queryParameter.put("otsOrderProductId",Integer.parseInt(orderProductId));
			orderProduct = super.getResultByNamedQuery("OtsOrderProduct.findByOtsOrderProductId", queryParameter);

			//Updating Fulfillment Status based on Order Product Status
			if(orderProductStatus.equalsIgnoreCase("Order-picked-up")) {
				orderProduct.setOtsOndcFulfillmentStatus("Order-picked-up");
				orderProduct.setOtsSuborderPickupDate(currentDateTime);
			}
			if(orderProductStatus.equalsIgnoreCase("Out-for-delivery")) {
				orderProduct.setOtsOndcFulfillmentStatus("Out-for-delivery");
				orderProduct.setOtsSuborderOfdDate(currentDateTime);
			}
			
			//To update subOrderStatus for all the products in an Order
			orderProduct.setOtsOrderProductStatus(orderProductStatus);
			super.getEntityManager().merge(orderProduct);
			
			return "Updated"; 
		}catch(Exception e){
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			return "Not Updated";
		}catch (Throwable e) {
			logger.error("Exception while inserting data into DB :"+e.getMessage());
			e.printStackTrace();
			return "Not Updated";
		}
	}
	
	//To Close SubOrder & if all the SubOrders are Closed, Main Order gets Close 
	@Override
	public String closeEmployeeOrder(CloseEmployeeOrderRequest closeEmployeeOrderRequest) {
		String closeOrderResponse = null;
		try {
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("order_id",UUID.fromString(closeEmployeeOrderRequest.getRequest().getOrderId()));
			queryParameters.put("product_id",UUID.fromString(closeEmployeeOrderRequest.getRequest().getProductId()));
			
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("close_employee_order")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("order_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Updated]")) {
				closeOrderResponse = "Updated";
			}
			else {
				closeOrderResponse = "Not Updated";
			}	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);}
		catch (Throwable e) {
			e.printStackTrace();
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
		return closeOrderResponse;
	}

}
