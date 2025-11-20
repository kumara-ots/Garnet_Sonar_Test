package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.TemporalType;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.CompleteOrderDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateOrderProductBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrderPaymentDetailsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCustomerOrderByStatusBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetListOfOrderByDateBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderByStatusRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;
import com.fuso.enterprise.ots.srv.server.dao.OrderServiceDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsOrder;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Repository
public class OrderServiceDAOImpl extends AbstractIptDao<OtsOrder, String> implements OrderServiceDAO {
	
	@Value("${ots.order.number.format}")
	public String orderNoFormat;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	LocalDateTime now = LocalDateTime.now();  
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public OrderServiceDAOImpl() {
		super(OtsOrder.class);
	}

	@Override
	public List<OrderDetails> getOrderBydate(GetOrderBORequest getOrderBORequest) {
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
    	try {
    		List<OtsOrder> OrderList = new ArrayList<OtsOrder>() ;
            try {
            	Map<String, Object> queryParameter = new HashMap<>();
    			queryParameter.put("FromDate", getOrderBORequest.getRequest().getFromTime());
    			queryParameter.put("ToDate", getOrderBORequest.getRequest().getToTime());
            	
    			if(getOrderBORequest.getRequest().getStatus()!=null) {
            		queryParameter.put("status", getOrderBORequest.getRequest().getStatus());
            		if(getOrderBORequest.getRequest().getCustomerId()==null) {
            			OtsUsers DistrubutorId = new OtsUsers();
                		DistrubutorId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getDistributorsId()));
                		queryParameter.put("DistributorsId", DistrubutorId);
                		OrderList  = super.getResultListByNamedQuery("OtsOrder.GetOrderListByTimeAndStatusForDistributor", queryParameter);
            		}else if(getOrderBORequest.getRequest().getDistributorsId()==null) {
            			OtsUsers CustomerId = new OtsUsers();
            			CustomerId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getCustomerId()));
                		queryParameter.put("CustomerId", CustomerId);
                		OrderList  = super.getResultListByNamedQuery("OtsOrder.GetOrderListByTimeAndStatusForCustomerId", queryParameter);
            		}
            	}else {
            		if(getOrderBORequest.getRequest().getCustomerId()!=null) {
            			OtsUsers CustomerId = new OtsUsers();
            			CustomerId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getCustomerId()));
                		queryParameter.put("otsCustomerId", CustomerId);
                		OrderList  = super.getResultListByNamedQuery("OtsOrder.GetListOfOrderByDateforCustomer", queryParameter);          
            		}else {
            			OtsUsers DistrubutorId = new OtsUsers();
	                	DistrubutorId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getDistributorsId()));
	            		queryParameter.put("DistributorsId", DistrubutorId);
	            		OrderList  = super.getResultListByNamedQuery("OtsOrder.GetOrderListByTime", queryParameter);
            		}
	            		
            	}
            } catch (NoResultException e) {
            	logger.error("Exception while fetching data from DB :"+e.getMessage());
            	throw new BusinessException(e.getMessage(), e);
            }
            otsOrderDetails =  OrderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
    	}catch(Exception e) {
			logger.error("Error in  order table"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
    	return otsOrderDetails;
	}

	private OrderDetails convertOrderDetailsFromEntityToDomain(OtsOrder otsOrder) {
		OrderDetails orderDetails =  new OrderDetails();
		
		orderDetails.setOrderId((otsOrder.getOtsOrderId()==null)?null:otsOrder.getOtsOrderId().toString());
		orderDetails.setOrderDate((otsOrder.getOtsOrderDt()==null)?null:otsOrder.getOtsOrderDt().toString());
		orderDetails.setOrderDeliverdDate((otsOrder.getOtsOrderDeliveredDt()==null)?null:otsOrder.getOtsOrderDeliveredDt().toString());
		orderDetails.setOrderCost(otsOrder.getOtsOrderCost()==null?null:otsOrder.getOtsOrderCost().toString());
		orderDetails.setOrderStatus(otsOrder.getOtsOrderStatus()==null?null:otsOrder.getOtsOrderStatus());
		orderDetails.setOrderNumber(otsOrder.getOtsOrderNumber()==null?null:otsOrder.getOtsOrderNumber());
		orderDetails.setDeliveryAddress((otsOrder.getOtsHouseNo()==null?"":otsOrder.getOtsHouseNo())+", "+(otsOrder.getOtsBuildingName()==null?"":otsOrder.getOtsBuildingName())
				+", "+(otsOrder.getOtsStreetName()==null?"":otsOrder.getOtsStreetName())+", "+(otsOrder.getOtsCityName()==null?"":otsOrder.getOtsCityName())
				+"- "+(otsOrder.getOtsPincode()==null?"":otsOrder.getOtsPincode()));		
        orderDetails.setPaymentStatus(otsOrder.getOtsOrderPaymentStatus()==null?"":otsOrder.getOtsOrderPaymentStatus());
		orderDetails.setPaymentId(otsOrder.getOtsOrderPaymentId()==null?"":otsOrder.getOtsOrderPaymentId());
		orderDetails.setPaymentDate(otsOrder.getOtsOrderPaymentDate()==null?"":otsOrder.getOtsOrderPaymentDate().toString());	
		orderDetails.setCustomerChangeAddressId((otsOrder.getOtsCustomerChangeAddressId()==null)?"":otsOrder.getOtsCustomerChangeAddressId());
		orderDetails.setCustomerOrderInvoice((otsOrder.getOtsOrderCustomerInvoice()==null)?"":otsOrder.getOtsOrderCustomerInvoice());
		orderDetails.setCustomerId(otsOrder.getOtsCustomerId()==null?"":otsOrder.getOtsCustomerId().getOtsUsersId().toString());
		orderDetails.setCustomerName(otsOrder.getOtsCustomerName()==null?"":otsOrder.getOtsCustomerName().toString());	
		orderDetails.setCustomerContactNo(otsOrder.getOtsCustomerContactNo()==null?"":otsOrder.getOtsCustomerContactNo().toString());
		orderDetails.setCustomerEmailId(otsOrder.getOtsCustomerId().getOtsUsersEmailid()==null?"":otsOrder.getOtsCustomerId().getOtsUsersEmailid());
		orderDetails.setOrderState(otsOrder.getOtsOrderState()==null?"":otsOrder.getOtsOrderState());
		orderDetails.setOrderDistrict(otsOrder.getOtsOrderDistrict()==null?"":otsOrder.getOtsOrderDistrict());
		
		return orderDetails;
	}
	
	@Transactional
	@Override
	public String UpdateOrderStatus(String orderId,String orderStatus) {
		OtsOrder otsOrder = new OtsOrder();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderId",UUID.fromString(orderId));
			otsOrder = super.getResultByNamedQuery("OtsOrder.findByOtsOrderId", queryParameter);
			
			otsOrder.setOtsOrderStatus(orderStatus);
			super.getEntityManager().merge(otsOrder);
		}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
		return "updated";
	}

	@Override
	public List<OrderDetails> getOrderIdByDistributorId(GetOrderByStatusRequest getOrderByStatusRequest){
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
	  try {
	    	List<OtsOrder> OrderList = new ArrayList<OtsOrder>() ;
        	Map<String, Object> queryParameter = new HashMap<>();
        	OtsUsers DistrubutorId = new OtsUsers();
        	DistrubutorId.setOtsUsersId(UUID.fromString(getOrderByStatusRequest.getRequest().getDistrubitorId()));
        	queryParameter.put("otsDistributorId", DistrubutorId);
			queryParameter.put("Status", getOrderByStatusRequest.getRequest().getStatus());
			OrderList  = super.getResultListByNamedQuery("OtsOrder.getOrderIdByDistrubitorId", queryParameter);
			System.out.println("order size = "+OrderList.size());
            if(OrderList.size() != 0) {
            	otsOrderDetails =  OrderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
            }else {
            	otsOrderDetails = null;
            }
    	} catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
    	return otsOrderDetails;
	}
	
	@Override
	public List<OrderDetails> getOrderByDistributorOrderProductStatus(String distributorId, String orderProductStatus){
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
	  try {
		  	List<OtsOrder> OrderList = new ArrayList<OtsOrder>() ;
        	Map<String, Object> queryParameter = new HashMap<>();
        	OtsUsers DistrubutorId = new OtsUsers();
        	DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
        	queryParameter.put("otsDistributorId", DistrubutorId);
			queryParameter.put("otsOrderProductStatus", orderProductStatus);
			OrderList  = super.getResultListByNamedQuery("OtsOrder.getOrderByDistributorOrderProductStatus", queryParameter);
			otsOrderDetails =  OrderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
    	} catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
    	return otsOrderDetails;
	}

	@Override
	public OrderDetails insertOrderAndGetOrderId(AddOrUpdateOrderProductBOrequest addOrUpdateOrderProductBOrequest) {
		OrderDetails orderDetails = new OrderDetails();
		try {
			OtsOrder otsOrder = convertDomainToOrderEntity(addOrUpdateOrderProductBOrequest);
			save(otsOrder);
			super.getEntityManager().flush();
			
			//To generate 10 digit Random number for OrderId
			SecureRandom secureRandom = new SecureRandom();        
       	 	long randomNumber = 1000000000L  + secureRandom.nextInt(900000000);
            System.out.println("Random No : " + randomNumber); 
            
			String OrderNumber = orderNoFormat+randomNumber;
			otsOrder.setOtsOrderNumber(OrderNumber);
			super.getEntityManager().merge(otsOrder);
			orderDetails = convertOrderDetailsFromEntityToDomain(otsOrder);
			return orderDetails;
		}catch(Exception e){
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}

	private OtsOrder convertDomainToOrderEntity(AddOrUpdateOrderProductBOrequest addOrUpdateOrderProductBOrequest)
	{
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(utilDate.getTime());
		System.out.println("current date = "+currentDateTime);
 		OtsOrder otsOrder = new OtsOrder();
 		
 		//To generate random UUID & insert into table   
		UUID uuid=UUID.randomUUID();
		otsOrder.setOtsOrderId(uuid);
		
		otsOrder.setOtsOrderGps("0");
		
		OtsUsers CustomerId = new OtsUsers();
		CustomerId.setOtsUsersId(UUID.fromString(addOrUpdateOrderProductBOrequest.getRequest().getCustomerId()));
		otsOrder.setOtsCustomerId(CustomerId);
		
		BigDecimal costData=new BigDecimal(addOrUpdateOrderProductBOrequest.getRequest().getOrderCost());
		otsOrder.setOtsOrderCost(costData);
		
		//To insert order status "New" for COD & "Pending" for online Payment
		if(addOrUpdateOrderProductBOrequest.getRequest().getPaymentStatus() == null)
		{
			otsOrder.setOtsOrderStatus("Pending");
		}else if(addOrUpdateOrderProductBOrequest.getRequest().getPaymentStatus().equalsIgnoreCase("COD"))
		{
			otsOrder.setOtsOrderStatus("New");
		}

		try {
			otsOrder.setOtsOrderDt(currentDateTime);
		}catch(Exception e) {
			System.out.println(e);
		}
		if(addOrUpdateOrderProductBOrequest.getRequest().getDeliverdDate()==null)
		{
			otsOrder.setOtsOrderDeliveredDt(null);
		}else
		{
			otsOrder.setOtsOrderDeliveredDt(Date.valueOf(addOrUpdateOrderProductBOrequest.getRequest().getDeliverdDate()));
		}
		if(addOrUpdateOrderProductBOrequest.getRequest().getPaymentId()==null)
		{
			otsOrder.setOtsOrderPaymentId(null);
		}else
		{
			otsOrder.setOtsOrderPaymentId(addOrUpdateOrderProductBOrequest.getRequest().getPaymentId());
		}
		if(addOrUpdateOrderProductBOrequest.getRequest().getPaymentStatus()==null)
		{
			otsOrder.setOtsOrderPaymentStatus("Pending");
		}else
		{
			otsOrder.setOtsOrderPaymentStatus(addOrUpdateOrderProductBOrequest.getRequest().getPaymentStatus());
		}
		if(addOrUpdateOrderProductBOrequest.getRequest().getCustomerChangeAddressId()==null || addOrUpdateOrderProductBOrequest.getRequest().getCustomerChangeAddressId().equals(""))
		{
			otsOrder.setOtsCustomerChangeAddressId(null);
		}else
		{
			otsOrder.setOtsCustomerChangeAddressId(addOrUpdateOrderProductBOrequest.getRequest().getCustomerChangeAddressId());
		}
		if(addOrUpdateOrderProductBOrequest.getRequest().getCustomerName()==null || addOrUpdateOrderProductBOrequest.getRequest().getCustomerName().equals(""))
		{
			otsOrder.setOtsCustomerName(null);
		}else
		{
			otsOrder.setOtsCustomerName(addOrUpdateOrderProductBOrequest.getRequest().getCustomerName());
		}
		if(addOrUpdateOrderProductBOrequest.getRequest().getCustomerContactNo()==null || addOrUpdateOrderProductBOrequest.getRequest().getCustomerContactNo().equals(""))
		{
			otsOrder.setOtsCustomerContactNo(null);
		}else
		{
			otsOrder.setOtsCustomerContactNo(addOrUpdateOrderProductBOrequest.getRequest().getCustomerContactNo());
		}
		if(addOrUpdateOrderProductBOrequest.getRequest().getOrderTransactionId()==null || addOrUpdateOrderProductBOrequest.getRequest().getOrderTransactionId().equals("")) 
		{
			otsOrder.setOtsOrderTransactionId(null);
		}
		else {
			otsOrder.setOtsOrderTransactionId(addOrUpdateOrderProductBOrequest.getRequest().getOrderTransactionId());
		}
		otsOrder.setOtsHouseNo(addOrUpdateOrderProductBOrequest.getRequest().getOtsHouseNo()==null?null:addOrUpdateOrderProductBOrequest.getRequest().getOtsHouseNo());
		otsOrder.setOtsBuildingName(addOrUpdateOrderProductBOrequest.getRequest().getOtsBuildingName()==null?null:addOrUpdateOrderProductBOrequest.getRequest().getOtsBuildingName());
		otsOrder.setOtsStreetName(addOrUpdateOrderProductBOrequest.getRequest().getOtsStreetName()==null?null:addOrUpdateOrderProductBOrequest.getRequest().getOtsStreetName());
		otsOrder.setOtsCityName(addOrUpdateOrderProductBOrequest.getRequest().getOtsCityName()==null?null:addOrUpdateOrderProductBOrequest.getRequest().getOtsCityName());
		otsOrder.setOtsPincode(addOrUpdateOrderProductBOrequest.getRequest().getOtsPinCode()==null?null:addOrUpdateOrderProductBOrequest.getRequest().getOtsPinCode());
		otsOrder.setOtsOrderState(addOrUpdateOrderProductBOrequest.getRequest().getOrderState()==null?null:addOrUpdateOrderProductBOrequest.getRequest().getOrderState());
		otsOrder.setOtsOrderDistrict(addOrUpdateOrderProductBOrequest.getRequest().getOrderDistrict()==null?null:addOrUpdateOrderProductBOrequest.getRequest().getOrderDistrict());
	
		return otsOrder;
	}

	@Override
	public List<OrderDetails> getCustomerOrderStatus(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest) {
		logger.info("Inside Event=1030,Class:OrderServiceDAOImpl,Method:getCustomerOrderStatus,getCustomerOrderByStatusBOrequest " + getCustomerOrderByStatusBOrequest);
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
    	try {
    		List<OtsOrder> OrderList = new ArrayList<OtsOrder>() ;
            try {
            	Map<String, Object> queryParameter = new HashMap<>();
            	OtsUsers CustomerId = new OtsUsers();
            	CustomerId.setOtsUsersId(UUID.fromString(getCustomerOrderByStatusBOrequest.getRequest().getCustomerId()));

            	queryParameter.put("otsCustomerId", CustomerId);
    			if(getCustomerOrderByStatusBOrequest.getRequest().getStatus().equalsIgnoreCase("All")) {
    				OrderList  = super.getResultListByNamedQuery("OtsOrder.getAllOrdersByCustomer", queryParameter);
    			}else {
    				queryParameter.put("Status", getCustomerOrderByStatusBOrequest.getRequest().getStatus());
    				OrderList  = super.getResultListByNamedQuery("OtsOrder.getOrderByCustomerIdAndStatus", queryParameter);
    			}
            } catch (NoResultException e) {
            	logger.error("Exception while fetching data from DB :"+e.getMessage());
            	throw new BusinessException(e.getMessage(), e);}
            otsOrderDetails =  OrderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
    	}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
    	return otsOrderDetails;
	}

	@Override
	public List<CompleteOrderDetails> getListOfOrderByDate(GetListOfOrderByDateBORequest getListOfOrderByDateBORequest) {
		try {
			List<CompleteOrderDetails> otsOrderDetails = new ArrayList<CompleteOrderDetails>();
			List<OtsOrder> orderList = new ArrayList<OtsOrder>();
			Map<String, Object> queryParameter = new HashMap<>();
	    	OtsUsers userId = new OtsUsers();
	    	userId.setOtsUsersId(UUID.fromString(getListOfOrderByDateBORequest.getRequest().getUserId()));
	    	queryParameter.put("FromDate",getListOfOrderByDateBORequest.getRequest().getStartDate());
			queryParameter.put("ToDate",getListOfOrderByDateBORequest.getRequest().getEndDate());
		
			switch(getListOfOrderByDateBORequest.getRequest().getRole())
			{
			   case "All":
				if(getListOfOrderByDateBORequest.getRequest().getStatus()== "" || getListOfOrderByDateBORequest.getRequest().getStatus().equalsIgnoreCase("All")) {   		
				orderList=  super.getEntityManager()
				.createQuery("from OtsOrder where otsOrderDt between ?1 and ?2", OtsOrder.class)
				.setParameter(1,getListOfOrderByDateBORequest.getRequest().getStartDate(),TemporalType.DATE)
				.setParameter(2,getListOfOrderByDateBORequest.getRequest().getEndDate(), TemporalType.DATE)
				.getResultList();
				}else {
				queryParameter.put("otsOrderStatus",getListOfOrderByDateBORequest.getRequest().getStatus());
				orderList  = super.getResultListByNamedQuery("OtsOrder.GetListOfOrderByDateforAllByStatus", queryParameter);
				}
				break;

				case "Customer":
					queryParameter.put("otsCustomerId", userId);
					if(getListOfOrderByDateBORequest.getRequest().getStatus()== "" || getListOfOrderByDateBORequest.getRequest().getStatus().equalsIgnoreCase("All")) {   		
						//Neha
						orderList=  super.getEntityManager()
						.createQuery("from OtsOrder where  otsCustomerId= ?1 and (otsOrderDt between ?2 and ?3)", OtsOrder.class)
						.setParameter(1,userId)
						.setParameter(2,getListOfOrderByDateBORequest.getRequest().getStartDate(),TemporalType.DATE)
						.setParameter(3,getListOfOrderByDateBORequest.getRequest().getEndDate(), TemporalType.DATE)
						.getResultList();
					}else {
						queryParameter.put("otsOrderStatus", getListOfOrderByDateBORequest.getRequest().getStatus());
						orderList  = super.getResultListByNamedQuery("OtsOrder.GetListOfOrderByDateforCustomerByStatus", queryParameter);
					}
					break;
				case "Distributor":
					queryParameter.put("otsDistributorId", userId);
					if(getListOfOrderByDateBORequest.getRequest().getStatus()== "" || getListOfOrderByDateBORequest.getRequest().getStatus().equalsIgnoreCase("All")) {
						orderList  = super.getResultListByNamedQuery("OtsOrder.GetListOfOrderByDateforDistrbutor", queryParameter);
					}else {
						queryParameter.put("otsOrderProductStatus", getListOfOrderByDateBORequest.getRequest().getStatus());
						orderList  = super.getResultListByNamedQuery("OtsOrder.GetListOfOrderByDateforDistrbutorByStatus", queryParameter);
					}
					break;
				case "Employee":
					queryParameter.put("otsAssignedId", userId);
					if(getListOfOrderByDateBORequest.getRequest().getStatus()== "" || getListOfOrderByDateBORequest.getRequest().getStatus().equalsIgnoreCase("All")) {
		    			orderList  = super.getResultListByNamedQuery("OtsOrder.GetListOfOrderByDateforEmployee", queryParameter);
					}else {
						queryParameter.put("otsOrderProductStatus", getListOfOrderByDateBORequest.getRequest().getStatus());
						orderList  = super.getResultListByNamedQuery("OtsOrder.GetListOfOrderByDateforEmployeeByStatus", queryParameter);
					}
					break;
				default:
					return null;
	        }
	        otsOrderDetails =  orderList.stream().map(OtsOrder -> convertCompleteOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());

			return otsOrderDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}

	private CompleteOrderDetails convertCompleteOrderDetailsFromEntityToDomain(OtsOrder otsOrder) 
	{
		CompleteOrderDetails orderDetails =  new CompleteOrderDetails() ;
		orderDetails.setOrderId((otsOrder.getOtsOrderId()==null)?null:otsOrder.getOtsOrderId().toString());
		orderDetails.setOrderDate((otsOrder.getOtsOrderDt()==null)?null:otsOrder.getOtsOrderDt().toString());
		orderDetails.setOrderDeliverdDate((otsOrder.getOtsOrderDeliveredDt()==null)?null:otsOrder.getOtsOrderDeliveredDt().toString());
		orderDetails.setOrderCost(otsOrder.getOtsOrderCost()==null?null:otsOrder.getOtsOrderCost().toString());
		orderDetails.setOrderStatus(otsOrder.getOtsOrderStatus()==null?null:otsOrder.getOtsOrderStatus());
		orderDetails.setOrderNumber(otsOrder.getOtsOrderNumber()==null?null:otsOrder.getOtsOrderNumber());
		orderDetails.setDeliveryAddress((otsOrder.getOtsHouseNo()==null?"":otsOrder.getOtsHouseNo())+" "+(otsOrder.getOtsBuildingName()==null?"":otsOrder.getOtsBuildingName())
				+" "+(otsOrder.getOtsStreetName()==null?"":otsOrder.getOtsStreetName())+" "+(otsOrder.getOtsCityName()==null?"":otsOrder.getOtsCityName())
				+" "+(otsOrder.getOtsPincode()==null?"":otsOrder.getOtsPincode()));
		orderDetails.setPaymentStatus(otsOrder.getOtsOrderPaymentStatus()==null?"":otsOrder.getOtsOrderPaymentStatus());
		orderDetails.setPaymentId(otsOrder.getOtsOrderPaymentId()==null?"":otsOrder.getOtsOrderPaymentId());
		orderDetails.setPaymentDate(otsOrder.getOtsOrderPaymentDate()==null?"":otsOrder.getOtsOrderPaymentDate().toString());	
		orderDetails.setCustomerChangeAddressId((otsOrder.getOtsCustomerChangeAddressId()==null)?"":otsOrder.getOtsCustomerChangeAddressId());
		orderDetails.setCustomerOrderInvoice((otsOrder.getOtsOrderCustomerInvoice()==null)?"":otsOrder.getOtsOrderCustomerInvoice());
		orderDetails.setCustomerId(otsOrder.getOtsCustomerId()==null?"":otsOrder.getOtsCustomerId().getOtsUsersId().toString());
		orderDetails.setCustomerName(otsOrder.getOtsCustomerName()==null?"":otsOrder.getOtsCustomerName().toString());	
		orderDetails.setCustomerContactNo(otsOrder.getOtsCustomerContactNo()==null?"":otsOrder.getOtsCustomerContactNo().toString());
		orderDetails.setOrderState(otsOrder.getOtsOrderState()==null?"":otsOrder.getOtsOrderState());
		orderDetails.setOrderDistrict(otsOrder.getOtsOrderDistrict()==null?"":otsOrder.getOtsOrderDistrict());
		orderDetails.setCustomerContactNo(otsOrder.getOtsCustomerContactNo()==null?"":otsOrder.getOtsCustomerContactNo().toString());	
		
		return orderDetails;
	}
	
	@Override
	public OrderDetails getOrderDetailsByOrderId(String orderId) {
		OtsOrder otsOrder = new OtsOrder();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderId",UUID.fromString(orderId));
			try {
				otsOrder = super.getResultByNamedQuery("OtsOrder.findByOtsOrderId", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
			OrderDetails otsOrderDetails = convertOrderDetailsFromEntityToDomain(otsOrder);
			return otsOrderDetails;
		}catch(Exception e){
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}

	@Override
	public List<OrderDetails> getOrderReportByDate(GetOrderBORequest getOrderBORequest) {
		try {
			List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
			List<OtsOrder> orderList = new ArrayList<OtsOrder>();
			Map<String, Object> queryParameter = new HashMap<>();
	    	
	    	queryParameter.put("startDate",getOrderBORequest.getRequest().getFromTime());
	    	queryParameter.put("endDate",getOrderBORequest.getRequest().getToTime());
	      	if(getOrderBORequest.getRequest().getCustomerId()==null || getOrderBORequest.getRequest().getCustomerId()== "") {
	    		if(getOrderBORequest.getRequest().getStatus()== null || getOrderBORequest.getRequest().getStatus()== "" || getOrderBORequest.getRequest().getStatus().equals("All")){
	    			OtsUsers distributorId = new OtsUsers();
					distributorId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getDistributorsId()));
					queryParameter.put("DistributorId", distributorId);
					orderList = super.getResultListByNamedQuery("OtsOrder.GetOrderListForDistributorByDate",queryParameter);
					System.out.println("orderlist size" + orderList.size());
				} else {

					OtsUsers distributorId = new OtsUsers();
					distributorId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getDistributorsId()));
					queryParameter.put("DistributorId", distributorId);
					queryParameter.put("Status", getOrderBORequest.getRequest().getStatus());
					orderList = super.getResultListByNamedQuery("OtsOrder.GetOrderListForDistributorByDateAndStatus",queryParameter);
					System.out.println("orderlist size" + orderList.size());
	    		}
	    	}else if(getOrderBORequest.getRequest().getStatus()== null || getOrderBORequest.getRequest().getStatus()== "" || getOrderBORequest.getRequest().getStatus().equals("All")){
				OtsUsers customerId = new OtsUsers();
				customerId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getCustomerId()));
				queryParameter.put("customerId", customerId);
				orderList = super.getResultListByNamedQuery("OtsOrder.GetOrderListForCustomerByDate", queryParameter);
				System.out.println("orderlist size" + orderList.size());
	        }else {

	    		OtsUsers customerId = new OtsUsers();
	    		customerId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getCustomerId()));
				queryParameter.put("customerId", customerId);
				queryParameter.put("Status",getOrderBORequest.getRequest().getStatus());
				  
	    		orderList  = super.getResultListByNamedQuery("OtsOrder.GetOrderListForCustomerByDateAndStatus", queryParameter);
	    		System.out.println("orderlist size"+orderList.size());
	    	}
	    	
	    	orderDetails =  orderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
			return orderDetails;
		}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
	}

	@Override
	public OrderDetails directSalesVoucher(AddOrUpdateOrderProductBOrequest addOrUpdateOrderProductBOrequest) {
		OrderDetails orderDetails = new OrderDetails();
		try {
			OtsOrder otsOrder = convertDomainToOrderEntity(addOrUpdateOrderProductBOrequest);
			save(otsOrder);
			super.getEntityManager().flush();
			String OrderNumber = "ORD-"+otsOrder.getOtsOrderId().toString();
			//int OrderId = otsOrder.getOtsOrderId();
			otsOrder.setOtsOrderNumber(OrderNumber);
			super.getEntityManager().merge(otsOrder);
			orderDetails = convertOrderDetailsFromEntityToDomain(otsOrder);
			return orderDetails;
		}catch(Exception e){
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			logger.error("Error in inserting order in order table"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}
	
	public void razorPay(String amount , String paymentId) throws RazorpayException, JSONException {
		RazorpayClient razorpay = new RazorpayClient("rzp_test_i50l0d4Cja32Mc", "Hv0j2n4laXp9SQa3eXDv36i3");
		try {
			  JSONObject captureRequest = new JSONObject();
			  captureRequest.put("amount", amount);
			  captureRequest.put("currency", "INR");

			  Payment payment = razorpay.Payments.capture("pay_Et08nx2YlPUzAE", captureRequest);
			} catch (RazorpayException e) {
			  // Handle Exception
			  System.out.println(e.getMessage());
			}
	}
	
	@Override
	public List<OrderDetails> getOrderReportByDistributorAndCustomer(GetOrderBORequest getOrderBORequest) {
		try {
			List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
			List<OtsOrder> orderList = new ArrayList<OtsOrder>();
			Map<String, Object> queryParameter = new HashMap<>();
		    	
		    queryParameter.put("startDate",getOrderBORequest.getRequest().getFromTime());
		    queryParameter.put("endDate",getOrderBORequest.getRequest().getToTime());

		    OtsUsers distributorId = new OtsUsers();
			distributorId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getDistributorsId()));			
			queryParameter.put("otsDistributorId", distributorId);
			
			OtsUsers customerId = new OtsUsers();
			customerId.setOtsUsersId(UUID.fromString(getOrderBORequest.getRequest().getCustomerId()));
	    	queryParameter.put("otsCustomerId", customerId);
	    	
	    	if(getOrderBORequest.getRequest().getProductId() == null || getOrderBORequest.getRequest().getProductId().equals("")) {
	    		//To get order details based on distributor, customer
    		   if(getOrderBORequest.getRequest().getStatus()== null || getOrderBORequest.getRequest().getStatus()== "" || getOrderBORequest.getRequest().getStatus().equalsIgnoreCase("All")){
		    		orderList  = super.getResultListByNamedQuery("OtsOrder.GetOrderListForDistributorAndCustomerByDate", queryParameter);
	 		   }else {	
	 			//To get order details based on distributor, customer & orderProductStatus
			    	queryParameter.put("otsOrderProductStatus",getOrderBORequest.getRequest().getStatus());
			    	orderList  = super.getResultListByNamedQuery("OtsOrder.GetOrderListForDistributorAndCustomerByDateAndState", queryParameter);
	 		   }
	    	}else {
	    		OtsProduct productId = new OtsProduct();
	 		    productId.setOtsProductId(UUID.fromString(getOrderBORequest.getRequest().getProductId()));
	 		    queryParameter.put("otsProductId", productId);
	 		    
	 		    //To get order details based on distributor, customer & productId
	    		if(getOrderBORequest.getRequest().getStatus()== null || getOrderBORequest.getRequest().getStatus()== "" || getOrderBORequest.getRequest().getStatus().equalsIgnoreCase("All")){
		    		orderList  = super.getResultListByNamedQuery("OtsOrder.GetOrderListForCustomerAndProductByDate", queryParameter);
		    	}else {	
		    	//To get order details based on distributor, customer, productId & orderProductStatus
			    	queryParameter.put("otsOrderProductStatus",getOrderBORequest.getRequest().getStatus());
			    	orderList  = super.getResultListByNamedQuery("OtsOrder.GetOrderListForCustomerAndProductByDateAndStatus", queryParameter);
		    	}	
	    	}
	    	orderDetails =  orderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
			return orderDetails;
		}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
	}

	@Override
	public List<OrderDetails> getOrderDetailsForOrderId(String OrderId) {
		List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
		List<OtsOrder> otsOrder = new ArrayList<OtsOrder>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderId", UUID.fromString(OrderId));
			otsOrder = super.getResultListByNamedQuery("OtsOrder.findByOtsOrderId", queryParameter);
			orderDetails =  otsOrder.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
			return orderDetails;
		}
		catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public OrderDetails closeOrder(String OrderId) {
		 java.util.Date utilDate = new java.util.Date();
		 java.sql.Timestamp sqlTS = new java.sql.Timestamp(utilDate.getTime());
		 System.out.println("Current date = "+sqlTS);
		try {
			OtsOrder otsOrder = new OtsOrder();
			OrderDetails otsOrderDetails = new OrderDetails();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderId",UUID.fromString(OrderId));
			otsOrder = super.getResultByNamedQuery("OtsOrder.findByOtsOrderId", queryParameter);
			otsOrder.setOtsOrderDeliveredDt(sqlTS);
			otsOrder.setOtsOrderStatus("Close");
	        otsOrderDetails = convertOrderDetailsFromEntityToDomain(otsOrder);
	        return otsOrderDetails;
		}catch(Exception e){
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);}
		catch (Throwable e) {
			logger.error("ERROR IN INSERTING PRODUCT TO ORDER-PRODUCT TABLE"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);
		}
	}
	
	@Override
	public OrderDetails addPaymentDetailsForOrder(AddOrderPaymentDetailsRequest addOrderPaymentDetailsRequest) {
		OtsOrder otsOrder = new OtsOrder();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderTransactionId",addOrderPaymentDetailsRequest.getRequest().getOrderTransactionId());
			try {
				otsOrder = super.getResultByNamedQuery("OtsOrder.findByOtsOrderTransactionId", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
			
			otsOrder.setOtsOrderPaymentId(addOrderPaymentDetailsRequest.getRequest().getPaymentId());
			otsOrder.setOtsOrderPaymentStatus("Online Paid");
			otsOrder.setOtsOrderPaymentDate(addOrderPaymentDetailsRequest.getRequest().getPaymentDate());
			otsOrder.setOtsOrderStatus("New");
			OrderDetails otsOrderDetails = convertOrderDetailsFromEntityToDomain(otsOrder);
			return otsOrderDetails;
		}catch(Exception e){
			logger.error("ERROR IN INSERTING DATA IN DB"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);
	    }catch (Throwable e) {
			logger.error("ERROR IN INSERTING DATA IN DB"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);
		}
	}
	
	@Override
	public String addCustomerOrderInvoiceToDB(String orderId,String invoice) {
		OtsOrder otsOrder = new OtsOrder();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderId",UUID.fromString(orderId));
			otsOrder = super.getResultByNamedQuery("OtsOrder.findByOtsOrderId", queryParameter);
			
			otsOrder.setOtsOrderCustomerInvoice(invoice);
			super.getEntityManager().merge(otsOrder);
		}catch(Exception e){
			logger.error("ERROR IN INSERTING DATA IN DB"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);}
		catch (Throwable e) {
			logger.error("ERROR IN INSERTING DATA IN DB"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);
		}
		return "Invoice Added";
	}
	
	@Override
	public List<OrderDetails> checkForFirstOrderByCustomer(String CustomerId) {
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
		List<OtsOrder> otsOrder = new ArrayList<OtsOrder>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsUsers customerId = new OtsUsers();
    		customerId.setOtsUsersId(UUID.fromString(CustomerId));
			queryParameter.put("otsCustomerId", customerId);
			otsOrder = super.getResultListByNamedQuery("OtsOrder.checkForFirstOrderByCustomer", queryParameter);
			otsOrderDetails = otsOrder.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
			return otsOrderDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<OrderDetails> getOrdersByStatus(String orderStatus) {
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
		List<OtsOrder> otsOrder = new ArrayList<OtsOrder>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderStatus",orderStatus);
			otsOrder = super.getResultListByNamedQuery("OtsOrder.findByOtsOrderStatus", queryParameter);
			otsOrderDetails = otsOrder.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
			return otsOrderDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String UpdateOrderCostWhenSubOrderCancelled(String orderId,String orderProductCost) {
		OtsOrder otsOrder = new OtsOrder();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderId",UUID.fromString(orderId));
			otsOrder = super.getResultByNamedQuery("OtsOrder.findByOtsOrderId", queryParameter);
			
			Double minusOrderCost = Double.parseDouble(otsOrder.getOtsOrderCost().toString()) - Double.parseDouble(orderProductCost);
			BigDecimal finalOrderCost = new BigDecimal(minusOrderCost);
			otsOrder.setOtsOrderCost(finalOrderCost);
			super.getEntityManager().merge(otsOrder);
		}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
		return "updated";
	}
	
	@Override
	public List<OrderDetails> getRRCOrdersByCustomer(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest) {
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
		List<OtsOrder> OrderList = new ArrayList<OtsOrder>() ;
    	try {
        	Map<String, Object> queryParameter = new HashMap<>();
        	OtsUsers CustomerId = new OtsUsers();
        	CustomerId.setOtsUsersId(UUID.fromString(getCustomerOrderByStatusBOrequest.getRequest().getCustomerId()));

        	queryParameter.put("otsCustomerId", CustomerId);
			queryParameter.put("otsRrcOrderStatus", getCustomerOrderByStatusBOrequest.getRequest().getStatus());
			OrderList  = super.getResultListByNamedQuery("OtsOrder.getRRCOrdersByCustomer", queryParameter);
            otsOrderDetails =  OrderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
    	}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
    	return otsOrderDetails;
	}
	
	@Override
	public List<OrderDetails> getCustomerOrderByOrderProductStatus(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest) {
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
		List<OtsOrder> OrderList = new ArrayList<OtsOrder>() ;
    	try {
        	Map<String, Object> queryParameter = new HashMap<>();
        	OtsUsers CustomerId = new OtsUsers();
        	CustomerId.setOtsUsersId(UUID.fromString(getCustomerOrderByStatusBOrequest.getRequest().getCustomerId()));

        	queryParameter.put("otsCustomerId", CustomerId);
			if(getCustomerOrderByStatusBOrequest.getRequest().getStatus().equalsIgnoreCase("All")) {
				OrderList  = super.getResultListByNamedQuery("OtsOrder.getAllOrdersByCustomer", queryParameter);
			}else {
				queryParameter.put("otsOrderProductStatus", getCustomerOrderByStatusBOrequest.getRequest().getStatus());
				OrderList  = super.getResultListByNamedQuery("OtsOrder.getCustomerOrderByOrderProductStatus", queryParameter);
			}
            otsOrderDetails =  OrderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
    	}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
    	return otsOrderDetails;
	}
	
	@Override
	public List<OrderDetails> getOrderByOrderIdOrderProductStatus(String orderId, String orderProductStatus) {
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
		List<OtsOrder> OrderList = new ArrayList<OtsOrder>() ;
    	try {
        	Map<String, Object> queryParameter = new HashMap<>();
        	queryParameter.put("otsOrderId", UUID.fromString(orderId));
			if(orderProductStatus.equalsIgnoreCase("All")) {
				OrderList  = super.getResultListByNamedQuery("OtsOrder.findByOtsOrderId", queryParameter);
			}else {
				queryParameter.put("otsOrderProductStatus", orderProductStatus);
				OrderList  = super.getResultListByNamedQuery("OtsOrder.getOrderByOrderIdOrderProductStatus", queryParameter);
			}
            otsOrderDetails =  OrderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
    	}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
    	return otsOrderDetails;
	}
	
	@Override
	public List<OrderDetails> getOrderByOrderIdRRCOrderStatus(String orderId, String rrcOrderStatus) {
		List<OrderDetails> otsOrderDetails = new ArrayList<OrderDetails>();
		List<OtsOrder> OrderList = new ArrayList<OtsOrder>() ;
    	try {
        	Map<String, Object> queryParameter = new HashMap<>();
        	queryParameter.put("otsOrderId", UUID.fromString(orderId));
			queryParameter.put("otsRrcOrderStatus", rrcOrderStatus);
			OrderList  = super.getResultListByNamedQuery("OtsOrder.getOrderByOrderIdRRCOrderStatus", queryParameter);
            otsOrderDetails =  OrderList.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
    	}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.FAILURE_ORDER_GET);
		}
    	return otsOrderDetails;
	}
	
	@Override
	public List<OrderDetails> getOrderByOrderTransactionId(String orderTransactionId) {
		List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
		List<OtsOrder> otsOrder = new ArrayList<OtsOrder>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsOrderTransactionId", orderTransactionId);
			otsOrder = super.getResultListByNamedQuery("OtsOrder.findByOtsOrderTransactionId", queryParameter);
			orderDetails =  otsOrder.stream().map(OtsOrder -> convertOrderDetailsFromEntityToDomain(OtsOrder)).collect(Collectors.toList());
			return orderDetails;
		}
		catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
