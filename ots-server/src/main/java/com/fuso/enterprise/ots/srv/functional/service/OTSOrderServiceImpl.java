package com.fuso.enterprise.ots.srv.functional.service;

import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuso.enterprise.ots.srv.api.model.domain.AddProductStock;
import com.fuso.enterprise.ots.srv.api.model.domain.AssignOrderToEmployeeModel;
import com.fuso.enterprise.ots.srv.api.model.domain.CompleteOrderDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.Coupon;
import com.fuso.enterprise.ots.srv.api.model.domain.CustomerChangeAddress;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorDetailsForSettlement;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributorPaymentDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.GetUserDetailsBORequest;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetailsAndProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetailsRequest;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderProductAndOrderDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderedProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.RegisteredDistWeeklySettlementDetailsForExcel;
import com.fuso.enterprise.ots.srv.api.model.domain.UnRegisteredDistWeeklySettlementDetailsForExcel;
import com.fuso.enterprise.ots.srv.api.model.domain.UserAccounts;
import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSOrderService;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSUserService;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateOrderProductBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateOrderTrackingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrderPaymentDetailsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductStockBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddTransactionCancelRecordsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AssignOrderToEmployeeRequest;
import com.fuso.enterprise.ots.srv.api.service.request.CloseEmployeeOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCustomerOrderByStatusBOrequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetDetailsForExcelRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetDistributorSettlementRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetListOfOrderByDateBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetMonthlyDistributorSettlementRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderByOrderIdAndStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetOrderByStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetRRCOrdersByStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.InsertOrderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.RequestBOUserBySearch;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateOrderDetailsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateOrderProductStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateRRCStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.response.CcAvenueOrderDetailsResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetCartResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetDistributorSettlementResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetListOfOrderByDateBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetProductBOStockResponse;
import com.fuso.enterprise.ots.srv.api.service.response.OrderDetailsBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.OrderProductAndOrderResponse;
import com.fuso.enterprise.ots.srv.api.service.response.OrderProductBOResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;
import com.fuso.enterprise.ots.srv.server.dao.CouponDAO;
import com.fuso.enterprise.ots.srv.server.dao.CouponOrderDAO;
import com.fuso.enterprise.ots.srv.server.dao.CustomerChangeAddressDAO;
import com.fuso.enterprise.ots.srv.server.dao.DistributorCompanyDetailsDAO;
import com.fuso.enterprise.ots.srv.server.dao.DistributorPaymentDetailsDAO;
import com.fuso.enterprise.ots.srv.server.dao.OrderProductDAO;
import com.fuso.enterprise.ots.srv.server.dao.OrderServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.PaymentTransactionCancelDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductStockDao;
import com.fuso.enterprise.ots.srv.server.dao.ProductStockHistoryDao;
import com.fuso.enterprise.ots.srv.server.dao.UserMapDAO;
import com.fuso.enterprise.ots.srv.server.dao.UserServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.useraccountsDAO;
import com.fuso.enterprise.ots.srv.server.dao.impl.UserServiceDAOImpl;
import com.fuso.enterprise.ots.srv.server.util.BillOfSupplyPdf;
import com.fuso.enterprise.ots.srv.server.util.DeliveryNote;
import com.fuso.enterprise.ots.srv.server.util.EmailUtil;
import com.fuso.enterprise.ots.srv.server.util.ExcelGenerator;
import com.fuso.enterprise.ots.srv.server.util.FcmPushNotification;
import com.fuso.enterprise.ots.srv.server.util.InvoicePdf;
import com.fuso.enterprise.ots.srv.server.util.OTSUtil;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
@Transactional
public class OTSOrderServiceImpl implements OTSOrderService {
	
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private EmailUtil emailUtil;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final java.sql.Date Date = null;
	private OrderServiceDAO orderServiceDAO;
	private OrderProductDAO orderProductDao;
	private ProductStockHistoryDao productStockHistoryDao;
	private ProductStockDao productStockDao;
	private UserServiceDAO userServiceDAO;
	private FcmPushNotification fcmPushNotification;
	private ProductServiceDAO productServiceDAO;
	private CouponOrderDAO couponOrderDAO;
	private CouponDAO couponDAO;
	private CustomerChangeAddressDAO customerChangeAddressDAO;
	private DistributorCompanyDetailsDAO distributorCompanyDetailsDAO;
	private DistributorPaymentDetailsDAO distributorPaymentDetailsDAO;
	private PaymentTransactionCancelDAO paymentTransactionCancelDAO;
	private OTSUserService otsUserService;
	private useraccountsDAO useraccountsDAO;
	
	@Inject
	public OTSOrderServiceImpl(useraccountsDAO useraccountsDAO,UserMapDAO userMapDAO,OrderServiceDAO orderServiceDAO,OrderProductDAO orderProductDao,ProductStockHistoryDao productStockHistoryDao,
			ProductStockDao productStockDao,UserServiceDAOImpl userServiceDAO,ProductServiceDAO productServiceDAO,CouponOrderDAO couponOrderDAO,CouponDAO couponDAO,CustomerChangeAddressDAO customerChangeAddressDAO,
			DistributorCompanyDetailsDAO distributorCompanyDetailsDAO,DistributorPaymentDetailsDAO distributorPaymentDetailsDAO,PaymentTransactionCancelDAO paymentTransactionCancelDAO,OTSUserService otsUserService)
	{
		this.orderServiceDAO = orderServiceDAO ;
		this.orderProductDao = orderProductDao;
		this.productStockHistoryDao=productStockHistoryDao;
		this.productStockDao=productStockDao;
		this.userServiceDAO = userServiceDAO;
		this.productServiceDAO = productServiceDAO;
		this.couponOrderDAO = couponOrderDAO;
		this.couponDAO = couponDAO;
		this.customerChangeAddressDAO = customerChangeAddressDAO;
		this.distributorCompanyDetailsDAO = distributorCompanyDetailsDAO;
		this.distributorPaymentDetailsDAO = distributorPaymentDetailsDAO;
		this.paymentTransactionCancelDAO = paymentTransactionCancelDAO;
		this.otsUserService = otsUserService;
		this.useraccountsDAO = useraccountsDAO;
	}

	@Value("${ots.donation.razorpaykey}")
	public String donationRazorpayKey;
	
	@Value("${ots.donation.razorpaysignature}")
	public String donationRazorpaySignature;
	
	@Value("${ots.donation.razorpaykey}")
	public String giftRazorpayKey;
	
	@Value("${ots.donation.razorpaykey}")
	public String giftRazorpaySignature;
	
	@Value("${companyforbill}")
	public String companyNameForBill;
	
	@Value("${companyAddress1}")
	public String companyAddress1;
	
	@Value("${gstNo}")
	public String gstNo;
	
	@Value("${ots.findNearestDistributor}")
	public String findNearestDistributor;

	@Value("${ots.adminRoleId}")
	public String adminRoleId;
	
	@Value("${ots.ccAvenue.merchantId}")
	public String ccAvenueMerchantId;
	
	@Value("${ots.ccAvenue.accessCode}")
	public String ccAvenueAccessCode;
	
	@Value("${ots.ccAvenue.workingKey}")
	public String ccAvenueWorkingKey;
	
	@Value("${ots.customer.page.loginLink}")
	public String customerPageLoginLink;
	
	@Value("${ots.distributor.page.loginLink}")
	public String distributorPageLoginLink;
	
	@Value("${ots.company.name}")
	public String companyName;
	
	@Value("${ots.order.number.format}")
	public String orderNoFormat;
	
	@Override
	public OrderDetailsBOResponse getOrderBydate(GetOrderBORequest getOrderBORequest) {
		try {
			OrderDetailsBOResponse orderDetailsBOResponse = new OrderDetailsBOResponse();
			orderDetailsBOResponse.setOrderDetails(orderServiceDAO.getOrderBydate(getOrderBORequest));
			return orderDetailsBOResponse;
		}catch(Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public OrderProductBOResponse getOrderByStatusAndDistributor(GetOrderByStatusRequest getOrderByStatusRequest) {
		try {
			OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
			// getting order details by passing the distributor id
			List<OrderDetails> OrderDetails = orderServiceDAO.getOrderByDistributorOrderProductStatus(getOrderByStatusRequest.getRequest().getDistrubitorId(),getOrderByStatusRequest.getRequest().getStatus());
			System.out.println("order list size = "+OrderDetails.size());
			if(OrderDetails.size() == 0) {
				return null;
			}else {
				List<OrderDetailsAndProductDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();
				//getting order product details
				for (int i = 0; i <OrderDetails.size() ; i++)
				{
					System.out.println("order id"+OrderDetails.get(i).getOrderId());
					List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getOrderByDistributorIdAndStatus(OrderDetails.get(i).getOrderId(),getOrderByStatusRequest.getRequest().getDistrubitorId(),getOrderByStatusRequest.getRequest().getStatus());
					System.out.println("orderProduct size = "+OrderProductDetailsList.size());
					GetOrderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(OrderDetails.get(i),OrderProductDetailsList));
				}
				orderProductBOResponse.setOrderList(GetOrderDetailsAndProductDetails);
				return orderProductBOResponse;
			}
		} catch(Exception e){
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
	public OrderProductAndOrderResponse getOrderByDistributorIdAndSubOrderStatus(GetOrderByStatusRequest getOrderByStatusRequest) {
		try {
			OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
			// getting order product details by passing the distributor id
			List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getOrderForDistributorAndStatus(getOrderByStatusRequest.getRequest().getDistrubitorId(),getOrderByStatusRequest.getRequest().getStatus());
			if(OrderProductDetailsList.size() == 0) {
				return null;
			}else {
				List<OrderProductAndOrderDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderProductAndOrderDetails>();
				//getting order details for the order product
				
				for (int i = 0; i <OrderProductDetailsList.size() ; i++)
				{
					OrderDetails OrderDetails = orderServiceDAO.getOrderDetailsByOrderId(OrderProductDetailsList.get(i).getOtsOrderId());
					GetOrderDetailsAndProductDetails.add(AddOrderProductAndOrderDetailsIntoResponse(OrderDetails,OrderProductDetailsList.get(i)));
				}
				orderProductAndOrderResponse.setOrderProductList(GetOrderDetailsAndProductDetails);
				return orderProductAndOrderResponse;
			}
		} catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	private OrderDetailsAndProductDetails AddProductAndOrderDetailsIntoResponse(OrderDetails orderDetails,List<OrderProductDetails> OrderProductDetails)
	{
		OrderDetailsAndProductDetails orderDetailsAndProductDetails = new OrderDetailsAndProductDetails();
		orderDetailsAndProductDetails.setOrderId(orderDetails.getOrderId());
		orderDetailsAndProductDetails.setCustomerId(orderDetails.getCustomerId());
		orderDetailsAndProductDetails.setOrderNumber(orderDetails.getOrderNumber());
		orderDetailsAndProductDetails.setOrderCost(orderDetails.getOrderCost());
		orderDetailsAndProductDetails.setOrderStatus(orderDetails.getOrderStatus());
		orderDetailsAndProductDetails.setOrderdeliveryCharge(orderDetails.getOrderDeliveryCharge());
		
		orderDetailsAndProductDetails.setOrderdProducts(OrderProductDetails);
		orderDetailsAndProductDetails.setDelivaryDate(orderDetails.getOrderDeliveryDate());
		orderDetailsAndProductDetails.setOrderDate(orderDetails.getOrderDate());
		orderDetailsAndProductDetails.setDelivaredDate(orderDetails.getOrderDeliverdDate());
		orderDetailsAndProductDetails.setCustomerOrderInvoice(orderDetails.getCustomerOrderInvoice());
		orderDetailsAndProductDetails.setCustomerDetails(userServiceDAO.getUserDetails(orderDetails.getCustomerId())); 
		orderDetailsAndProductDetails.setCustomerName(orderDetails.getCustomerName());
		orderDetailsAndProductDetails.setCustomerContactNo(orderDetails.getCustomerContactNo());
		orderDetailsAndProductDetails.setDeliveryAddress(orderDetails.getDeliveryAddress());
		orderDetailsAndProductDetails.setPaymentId(orderDetails.getPaymentId());
		orderDetailsAndProductDetails.setPayementStatus(orderDetails.getPaymentStatus());
		orderDetailsAndProductDetails.setPaymentDate(orderDetails.getPaymentDate());
		orderDetailsAndProductDetails.setDeliveryState(orderDetails.getOrderState());
		orderDetailsAndProductDetails.setDeliveryDistrict(orderDetails.getOrderDistrict());
		orderDetailsAndProductDetails.setPayementStatus(orderDetails.getPaymentStatus());
		return orderDetailsAndProductDetails;
	}

	private OrderDetailsAndProductDetails GetProductAndOrderDetails(OrderDetails orderDetails,List<OrderProductDetails> OrderProductDetails)
	{
		OrderDetailsAndProductDetails orderDetailsAndProductDetails = new OrderDetailsAndProductDetails();
		orderDetailsAndProductDetails.setOrderId(orderDetails.getOrderId());
		orderDetailsAndProductDetails.setCustomerId(orderDetails.getCustomerId());
		orderDetailsAndProductDetails.setOrderNumber(orderDetails.getOrderNumber());
		orderDetailsAndProductDetails.setOrderCost(orderDetails.getOrderCost());
		orderDetailsAndProductDetails.setOrderStatus(orderDetails.getOrderStatus());
		orderDetailsAndProductDetails.setOrderDate(orderDetails.getOrderDate());
		orderDetailsAndProductDetails.setOrderdProducts(OrderProductDetails);
		orderDetailsAndProductDetails.setDelivaryDate(orderDetails.getOrderDeliveryDate());
		orderDetailsAndProductDetails.setOrderDate(orderDetails.getOrderDate());
		orderDetailsAndProductDetails.setDelivaredDate(orderDetails.getOrderDeliverdDate());
		orderDetailsAndProductDetails.setCustomerOrderInvoice(orderDetails.getCustomerOrderInvoice());
		orderDetailsAndProductDetails.setCustomerName(orderDetails.getCustomerName());
		orderDetailsAndProductDetails.setCustomerContactNo(orderDetails.getCustomerContactNo());
		orderDetailsAndProductDetails.setDeliveryAddress(orderDetails.getDeliveryAddress());
		orderDetailsAndProductDetails.setPaymentId(orderDetails.getPaymentId());
		orderDetailsAndProductDetails.setPayementStatus(orderDetails.getPaymentStatus());
		orderDetailsAndProductDetails.setPaymentDate(orderDetails.getPaymentDate());
		orderDetailsAndProductDetails.setDeliveryState(orderDetails.getOrderState());
		orderDetailsAndProductDetails.setDeliveryDistrict(orderDetails.getOrderDistrict());
		
		for(int i=0 ; i<OrderProductDetails.size() ; i++) {
			if(OrderProductDetails.get(i).getAssignedId()!= null) {
				orderDetailsAndProductDetails.setEmployeeDetails(userServiceDAO.getUserDetails(OrderProductDetails.get(i).getAssignedId()));
			}
			if(OrderProductDetails.get(i).getDistributorId()!= null) {
				orderDetailsAndProductDetails.setDistributorDetails(userServiceDAO.getUserDetails(OrderProductDetails.get(0).getDistributorId()));
			}
				
			if(OrderProductDetails.get(i).getOtsOrderProductStatus().equalsIgnoreCase("cancel"))
			{
				orderDetailsAndProductDetails.getOrderdProducts().get(i).setSubOrderDeliveredDate("----------");
			}
			else if(OrderProductDetails.get(i).getSubOrderDeliveredDate() == null || OrderProductDetails.get(i).getSubOrderDeliveredDate().equals("")) {
				orderDetailsAndProductDetails.getOrderdProducts().get(i).setSubOrderDeliveredDate("Yet To Be Delivered");
			}else {		
				orderDetailsAndProductDetails.getOrderdProducts().get(i).setSubOrderDeliveredDate(OrderProductDetails.get(i).getSubOrderDeliveredDate());
			}
		}
		orderDetailsAndProductDetails.setCustomerDetails(userServiceDAO.getUserDetails(orderDetails.getCustomerId()));
		
		return orderDetailsAndProductDetails;
	}

	@Override
	public OrderProductBOResponse insertOrderAndProduct(AddOrUpdateOrderProductBOrequest addOrUpdateOrderProductBOrequest) 
	{
		// commented code from 288 to 299 will check for the near place order distributor code
		OrderDetails otsOrderDetails = new OrderDetails();
		OrderProductDetails orderProductDetails = new OrderProductDetails();
		OrderProductBOResponse Response = new OrderProductBOResponse();
		RequestBOUserBySearch requestBOUserBySearch = new RequestBOUserBySearch();	
		GetUserDetailsBORequest userDetailsBORequest = new GetUserDetailsBORequest();
		UserDetails distributer=new UserDetails();
		ProductDetails productDetails=new ProductDetails();
	
		userDetailsBORequest.setUserLat(addOrUpdateOrderProductBOrequest.getRequest().getUserLat());
		userDetailsBORequest.setUserLong(addOrUpdateOrderProductBOrequest.getRequest().getUserLong());
						
		requestBOUserBySearch.setRequestData(userDetailsBORequest);

		//getting customer details for order
		UserDetails Customer = new UserDetails();
		Customer = userServiceDAO.getUserDetails(addOrUpdateOrderProductBOrequest.getRequest().getCustomerId());
		
		//To set delivery address for Order, if the customer has entered change address or else default address
		List<CustomerChangeAddress> customerSecondaryAddress = new ArrayList<CustomerChangeAddress>();
		
		//getting Secondary Address Details 
		customerSecondaryAddress = customerChangeAddressDAO.getCustomerChangeAddressById(addOrUpdateOrderProductBOrequest.getRequest().getCustomerChangeAddressId());
		if(customerSecondaryAddress.size() != 0) {
			//setting Customer Secondary Address, if the order has Customer Change Address
			addOrUpdateOrderProductBOrequest.getRequest().setCustomerName(customerSecondaryAddress.get(0).getCustomerFirstName()+" "+customerSecondaryAddress.get(0).getCustomerSecondName());
			addOrUpdateOrderProductBOrequest.getRequest().setCustomerContactNo(customerSecondaryAddress.get(0).getCustomerContactNo());
			addOrUpdateOrderProductBOrequest.getRequest().setOtsHouseNo(customerSecondaryAddress.get(0).getOtsHouseNo());
			addOrUpdateOrderProductBOrequest.getRequest().setOtsBuildingName(customerSecondaryAddress.get(0).getOtsBuildingName());
			addOrUpdateOrderProductBOrequest.getRequest().setOtsStreetName(customerSecondaryAddress.get(0).getOtsStreetName());
			addOrUpdateOrderProductBOrequest.getRequest().setOtsCityName(customerSecondaryAddress.get(0).getOtsCityName());
			addOrUpdateOrderProductBOrequest.getRequest().setOtsPinCode(customerSecondaryAddress.get(0).getOtsPinCode());
			addOrUpdateOrderProductBOrequest.getRequest().setOrderState(customerSecondaryAddress.get(0).getOtsStateName());
			addOrUpdateOrderProductBOrequest.getRequest().setOrderDistrict(customerSecondaryAddress.get(0).getOtsDistrictName());	
		}
		
		//To Create table with order details
	    String table = "<table border='1' style='border:2px solid black;border-collapse:collapse;' align='left'>"
                + "<tr width='80%' align='center' style='border:2px solid black'>"
                + "<th><b>SubOrder Id</b></th>"
                + "<th><b>Distributor Name</b></th>"
                + "<th><b>Product Name</b></th>"
                + "<th><b>Ordered Qty</b></th>"
                + "<th><b>Product Cost</b></th>"
                + "</tr>";
		
		try {
			//To insert Order & fetch OrderId from order table
			otsOrderDetails = orderServiceDAO.insertOrderAndGetOrderId(addOrUpdateOrderProductBOrequest);
			try {
				//To get distributor's mapped for ordered products
				for(int i=0 ; i <addOrUpdateOrderProductBOrequest.getRequest().getProductList().size() ; i++)
				{
					productDetails =productServiceDAO.getProductDetails(addOrUpdateOrderProductBOrequest.getRequest().getProductList().get(i).getProductId());
					//setting ots_product_price from product table for each product into ots_product_price_without_gst from order product table
					addOrUpdateOrderProductBOrequest.getRequest().getProductList().get(i).setProductPriceWithoutGst(productDetails.getProductPrice());
					//setting ots_product_seller_price from product table for each product into ots_product_seller_price from order product table
					addOrUpdateOrderProductBOrequest.getRequest().getProductList().get(i).setProductSellerPrice(productDetails.getProductSellerPrice());
					
					//to set order product status "New" for COD, "Pending" for Online Payment
					if(addOrUpdateOrderProductBOrequest.getRequest().getPaymentStatus() == null) {
						addOrUpdateOrderProductBOrequest.getRequest().getProductList().get(i).setProductStatus("Pending");
					}else if(addOrUpdateOrderProductBOrequest.getRequest().getPaymentStatus().equalsIgnoreCase("COD")) {
						addOrUpdateOrderProductBOrequest.getRequest().getProductList().get(i).setProductStatus("New");
					}
					
					//getting Distributor details for Order
					distributer  =userServiceDAO.getUserDetails(productDetails.getDistributorId());	
					//To insert Products for Order in orderProduct table 
					orderProductDetails = orderProductDao.insertOrdrerProductByOrderId(otsOrderDetails.getOrderId(),distributer.getUserId(), addOrUpdateOrderProductBOrequest.getRequest().getProductList().get(i));
				
					//To minus product stock when order gets inserted 
					AddProductStockBORequest addProductStockBORequest = new AddProductStockBORequest();
					AddProductStock addProductStock = new AddProductStock();
					//setting request for update product stock
					addProductStock.setProductId(productDetails.getProductId());
					addProductStock.setUsersId(distributer.getUserId());
					addProductStock.setProductStockQty(addOrUpdateOrderProductBOrequest.getRequest().getProductList().get(i).getOrderedQty());
					addProductStockBORequest.setRequestData(addProductStock);
					//To update product stock quantity
					GetProductBOStockResponse getProductStock = productStockDao.updateProductStockQuantity(addProductStockBORequest);
					
					//To send notification mail to distributor when product stock quantity is <= 10
					if(Integer.parseInt(getProductStock.getStockQuantity()) <= 10) {
						//Creating message content
						String distMsg="<p>Hi,<br><br>" + 
								"Your Product "+productDetails.getProductName()+" stock is less than 10. <br>"+ 
								"Please add the stock to get uninterrupted orders. <br>"+
								"Thanks And Regards,<br>" + 
								companyName+" Support Team </p>";
						//sending mail for distributor for receiving order
						emailUtil.sendDistributermail(distributer.getEmailId(), "", "Add Product Stock", distMsg);
					}
					
					//Link for distributor login page
					String distPageLink = "<a href= '"+distributorPageLoginLink+"'>Login</a> </p>";
					
					//Creating message content
					String distMsg="<p>Hi,<br><br>" + 
							"You have received a New Order placed by Customer" + Customer.getFirstName()+" "+Customer.getLastName()+"<br>"+
							"Please Approve Order No: "+otsOrderDetails.getOrderNumber()+" with Sub OrderId: "+orderProductDetails.getOtsSubOrderId()+"<br>" +
							"Please Login to know the Order details. Click here to "+distPageLink+"</br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team</p>";
					//sending mail for distributor for receiving order
					emailUtil.sendDistributermail(distributer.getEmailId(), "", "New Order Received", distMsg);
				    
				    table = table+"<tr width='80%' align='center' style='border:2px solid black'>"
				    		+ "<td>" + orderProductDetails.getOtsSubOrderId() + "</td>"
			                + "<td>" + distributer.getFirstName()+" "+distributer.getLastName()+ "</td>"
			    			+ "<td>" + productDetails.getProductName()+"</td>"
			    			+ "<td>" + orderProductDetails.getOtsOrderedQty()+"</td>"
			    			+ "<td>" + orderProductDetails.getOtsOrderProductCost()+"</td>"
			    			+"</tr>";
				}
				table = table+"</table>";
				
				//To generate response data after inserting order in DB
				Response = getOrderDetailsForOrderId(otsOrderDetails.getOrderId());
				
				try {
					//for adding coupons to order
					if(addOrUpdateOrderProductBOrequest.getCouponId().isEmpty() || addOrUpdateOrderProductBOrequest.getCouponId() == null) {
						Response.setCouponStatus("Coupon not applied");	
					}else {
						//to validate coupon 
						Coupon couponDetails = couponDAO.getCouponDetailsByStatus(addOrUpdateOrderProductBOrequest.getCouponId(),"active");
						
						if(couponDetails==null) {
							Response.setCouponStatus("Invalid Coupon");
						}else {
							if(Integer.parseInt(couponDetails.getCouponUnit())==0) {
								Response.setCouponStatus("Coupon unavailable");
							}else {
								//for inserting orderId & couponId into couponOrder table
								couponOrderDAO.insertCouponToOrder((Integer.parseInt(addOrUpdateOrderProductBOrequest.getCouponId())),otsOrderDetails.getOrderId());
								//to minus the coupon unit count to 1 when ever the coupon gets added to the order
							    couponDAO.updateCouponCount(addOrUpdateOrderProductBOrequest.getCouponId());
							    Response.setCouponStatus("Coupon is added");
							}
						}	
					}
				}catch(Exception e){
					logger.error("Exception while inserting data into DB :"+e.getMessage());
					e.printStackTrace();
					throw new BusinessException(e.getMessage(), e);
				}
				catch (Throwable e) {
		            logger.error("Exception while inserting data into DB :"+e.getMessage());
		            e.printStackTrace();
		            throw new BusinessException(e.getMessage(), e);
				}

				try {
					String custPageLink = "<a href= '"+customerPageLoginLink+"'>Login</a> </p>";
					
					//Creating message content
					String custMsg="<p>Hi "+Customer.getFirstName()+" "+Customer.getLastName()+", <br><br>" + 
							"Order Successfully Placed.<br>"+
							"We are pleased to confirm your Order No: "+otsOrderDetails.getOrderNumber()+"<br>" +
							"Please Login to know the Order details. Click here to "+custPageLink+"</br>"+
							"Thank you for shopping with "+companyName+".</br></br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team</p>";
					//sending mail for Customer for inserting order
					emailUtil.sendCustomermail(Customer.getEmailId(), "", "Order Confirmation", custMsg);
				    
				    //Creating message content
					String adminMsg="<p>Hi,<br>" + 
							"Order Id: "+otsOrderDetails.getOrderId()+" placed by Customer Id: "+Customer.getUserId()+" , "+Customer.getFirstName()+" "+Customer.getLastName()+
							" with order Amount Rs."+otsOrderDetails.getOrderCost()+"<br>"+
							"Order Details are mentioned below. <br>"+table+"<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					UserAccounts adminDetails =  useraccountsDAO.getUseraccountDetail(distributer.getCreatedUser());
					//sending mail order details for Admin
					emailUtil.sendAdminMail(adminDetails.getEmail(), "", "Order Placed By Customer", adminMsg);

				}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
				}
				
				// push notification code for sending order notification to Customer, this code can be used in future when Device Id for all the users are inserted
//				try {
//					String notification = otsOrderDetails.getOrderNumber() + " had been placed by " + Customer.getFirstName()+" "+Customer.getLastName()+" and requested delivery date is "+addOrUpdateOrderProductBOrequest.getRequest().getDelivaryDate()+" please click here to assign the Employee for order";
//					fcmPushNotification.sendPushNotification(distributer.getDeviceId(),"etaarana Apps" ,notification);
//					notification = "Order Placed : Your order "+otsOrderDetails.getOrderNumber()+" had been placed";
//					fcmPushNotification.sendPushNotification(Customer.getDeviceId(),"etaarana Apps" ,notification);
//				}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
//				}
				
				return Response;
			}catch(Exception e){
				throw new BusinessException(e,ErrorEnumeration.ERROR_IN_STOCK);
			} catch (Throwable e) {
				throw new BusinessException(e, ErrorEnumeration.ERROR_IN_STOCK);
			}
		}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_STOCK);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_STOCK);
		}
	}
	
	@Override
	public OrderProductBOResponse insertOrder(InsertOrderRequest insertOrderRequest) 
	{
		OrderProductBOResponse response = new OrderProductBOResponse();
	    List<OrderProductDetails> orderProductList = new ArrayList<>();

	    // Get cart details for customer
	    GetCartResponse cartDetails = otsUserService.getCartList(insertOrderRequest.getRequest().getCustomerId());

	    // Set order details request
	    OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();
	    orderDetailsRequest.setCustomerId(insertOrderRequest.getRequest().getCustomerId());
	    orderDetailsRequest.setCustomerChangeAddressId(insertOrderRequest.getRequest().getCustomerChangeAddressId());
	    orderDetailsRequest.setOrderCost(cartDetails.getFinalPrice());
	    orderDetailsRequest.setOrderTransactionId(insertOrderRequest.getRequest().getOrderTransactionId());
	    orderDetailsRequest.setPaymentStatus(insertOrderRequest.getRequest().getPaymentStatus());

	    // Prepare product details for order
	    List<OrderedProductDetails> orderProductDetails = cartDetails.getCartList().stream()
	            .map(cart -> {
	                OrderedProductDetails details = new OrderedProductDetails();
	                details.setProductId(cart.getProductId());
	                details.setProductCost(cart.getTotalPrice());
	                details.setOrderedQty(cart.getOtsCartQty().toString());
	                return details;
	            })
	            .collect(Collectors.toList());

	    orderDetailsRequest.setProductList(orderProductDetails);

	    // Set delivery address from secondary address or default
	    List<CustomerChangeAddress> customerSecondaryAddress = customerChangeAddressDAO.getCustomerChangeAddressById(insertOrderRequest.getRequest().getCustomerChangeAddressId());
	    if (!customerSecondaryAddress.isEmpty()) {
	        CustomerChangeAddress address = customerSecondaryAddress.get(0);
	        orderDetailsRequest.setCustomerName(address.getCustomerFirstName() + " " + address.getCustomerSecondName());
	        orderDetailsRequest.setCustomerContactNo(address.getCustomerContactNo());
	        orderDetailsRequest.setOtsHouseNo(address.getOtsHouseNo());
	        orderDetailsRequest.setOtsBuildingName(address.getOtsBuildingName());
	        orderDetailsRequest.setOtsStreetName(address.getOtsStreetName());
	        orderDetailsRequest.setOtsCityName(address.getOtsCityName());
	        orderDetailsRequest.setOtsPinCode(address.getOtsPinCode());
	        orderDetailsRequest.setOrderState(address.getOtsStateName());
	        orderDetailsRequest.setOrderDistrict(address.getOtsDistrictName());
	    }

	    // Prepare request for adding/updating order and getting order details
	    AddOrUpdateOrderProductBOrequest addOrUpdateOrderProductBOrequest = new AddOrUpdateOrderProductBOrequest();
	    addOrUpdateOrderProductBOrequest.setRequest(orderDetailsRequest);
	    
	    //To Create table with order details for sending mail
	    String table = "<table border='1' style='border:2px solid black;border-collapse:collapse;' align='left'>"
                + "<tr width='80%' align='center' style='border:2px solid black'>"
                + "<th><b>SubOrder Id</b></th>"
                + "<th><b>Distributor Name</b></th>"
                + "<th><b>Product Name</b></th>"
                + "<th><b>Ordered Qty</b></th>"
                + "<th><b>Product Cost</b></th>"
                + "</tr>";
		
	    String adminId = null;
		try {
			//To insert Order & fetch OrderId from order table
	        OrderDetails otsOrderDetails = orderServiceDAO.insertOrderAndGetOrderId(addOrUpdateOrderProductBOrequest);
	        
	        // Executor for concurrent tasks
	        ExecutorService executor = Executors.newFixedThreadPool(10);
			
			//To get distributor's mapped for ordered products
        	for (int i = 0; i < cartDetails.getCartList().size(); i++) {
        		final int index = i;
				// Fetch product details
        		ProductDetails productDetails = productServiceDAO.getProductDetails(cartDetails.getCartList().get(index).getProductId());
                
                //Fetching adminId from Product table
                adminId = productDetails.getCreatedUser();
                
				// Update order product details
                orderProductDetails.get(index).setProductPriceWithoutGst(productDetails.getProductPrice());
                orderProductDetails.get(index).setProductSellerPrice(productDetails.getProductSellerPrice());
                orderProductDetails.get(index).setProductBasePrice(productDetails.getProductBasePrice());
                orderProductDetails.get(index).setProductPrice(productDetails.getProductPrice());
                orderProductDetails.get(index).setProductGst(productDetails.getGst());
                orderProductDetails.get(index).setProductGstPrice(productDetails.getGstPrice());
                orderProductDetails.get(index).setProductPercentage(productDetails.getProductDiscountPercentage());
                orderProductDetails.get(index).setProductDiscountPrice(productDetails.getProductDiscountPrice());
                orderProductDetails.get(index).setProductReturnDeliveryCharge(productDetails.getProductReturnDeliveryCharge());
                orderProductDetails.get(index).setProductDeliveryCharge(productDetails.getProductDeliveryCharge());    
                orderProductDetails.get(index).setProductName(productDetails.getProductName());
                orderProductDetails.get(index).setProductImage(productDetails.getProductImage());
                orderProductDetails.get(index).setProductCancellationAvailability(productDetails.getProductCancellationAvailability());
                orderProductDetails.get(index).setProductReplacementAvailability(productDetails.getProductReplacementAvailability());
                orderProductDetails.get(index).setProductReplacementDays(productDetails.getProductReplacementDays());
                orderProductDetails.get(index).setPoductReturnAvailability(productDetails.getProductReturnAvailability());
                orderProductDetails.get(index).setProductReturnDays(productDetails.getProductReturnDays()); 
                orderProductDetails.get(index).setOtsProductCountry(productDetails.getOtsProductCountry());
                orderProductDetails.get(index).setOtsProductCountryCode(productDetails.getOtsProductCountryCode());
                orderProductDetails.get(index).setOtsProductCurrency(productDetails.getOtsProductCurrency());
                orderProductDetails.get(index).setOtsProductCurrencySymbol(productDetails.getOtsProductCurrencySymbol());

                // Set Order Product Status based on payment status, If Order is COD then OP status will be set to "New", if its Online Payment OP status will be set to "Pending"
                if (insertOrderRequest.getRequest().getPaymentStatus() == null) {
                    orderProductDetails.get(index).setProductStatus("Pending");
                } else if (insertOrderRequest.getRequest().getPaymentStatus().equalsIgnoreCase("COD")) {
                    orderProductDetails.get(index).setProductStatus("New");
                }
				
				// Insert order product by order ID
                OrderProductDetails orderProduct = orderProductDao.insertOrdrerProductByOrderId(otsOrderDetails.getOrderId(),productDetails.getDistributorId(),orderProductDetails.get(index));
                orderProductList.add(orderProduct);
                	
                //Minus Stock only when Order is COD placed
                if(orderProduct.getOtsOrderProductStatus().equalsIgnoreCase("New")) {
                	// Update product stock and notify asynchronously
    				executor.submit(() -> updateProductStockAndNotify(productDetails, orderProduct, otsOrderDetails, orderDetailsRequest.getCustomerName()));
                }
				
				//To set Data's into table 
				table = table+"<tr width='80%' align='center' style='border:2px solid black'>"
			    		+ "<td>" + orderProduct.getOtsSubOrderId() + "</td>"
		                + "<td>" + productDetails.getDistributerName()+ "</td>"
		    			+ "<td>" + productDetails.getProductName()+"</td>"
		    			+ "<td>" + orderProduct.getOtsOrderedQty()+"</td>"
		    			+ "<td>" + orderProduct.getOtsOrderProductCost()+"</td>"
		    			+"</tr>";
			}
        	table = table+"</table>";
        	
        	//To generate response data after inserting order in DB
			List<OrderDetailsAndProductDetails> orderDetailsAndProductDetails = new ArrayList<>();
			orderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(otsOrderDetails,orderProductList)); 
			response.setOrderList(orderDetailsAndProductDetails);
		
			//Notify Customer & Admin for Successful Order Placed only for COD Orders
			if(otsOrderDetails.getOrderStatus().equalsIgnoreCase("New")) {
				try {
		        	String customerMailId = response.getOrderList().get(0).getCustomerDetails().getEmailId();
		        	
		        	//Clickable Link for customer page
					String custPageLink = "<a href= '"+customerPageLoginLink+"'>Login</a> </p>";
					
					//Creating message content
					String custMsg="<p>Hi "+orderDetailsRequest.getCustomerName()+", <br><br>" + 
							"Order Successfully Placed.<br>"+
							"We are pleased to confirm your Order No: "+otsOrderDetails.getOrderNumber()+"<br>" +
							"Please Login to know the Order details. Click here to "+custPageLink+"<br>"+
							"Thank you for shopping with "+companyName+".<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team</p>";
					//sending mail for Customer for inserting order
					executor.submit(() -> emailUtil.sendCustomermail(customerMailId, "", "Order Confirmation", custMsg));
				    
				    //Creating message content
					String adminMsg="<p>Hi,<br>" + 
							"Order Id: "+otsOrderDetails.getOrderId()+" placed by Customer Id: "+orderDetailsRequest.getCustomerId()+" , "+orderDetailsRequest.getCustomerName()+
							" with order Amount Rs."+otsOrderDetails.getOrderCost()+"<br>"+
							"Order Details are mentioned below. <br>"+table+"<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					UserAccounts adminDetails =  useraccountsDAO.getUseraccountDetail(adminId);
					
					//sending mail order details for Admin
					executor.submit(() -> emailUtil.sendAdminMail(adminDetails.getEmail(), "", "Order Placed By Customer", adminMsg));

				}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
				}
			}

	        executor.shutdown();
			
	        // Apply coupon if provided
	        if (insertOrderRequest.getRequest().getCouponId() != null && !insertOrderRequest.getRequest().getCouponId().isEmpty()) {
	            applyCoupon(insertOrderRequest, otsOrderDetails, response);
	        }
			
			return response;

		}catch(Exception e){
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void updateProductStockAndNotify(ProductDetails productDetails, OrderProductDetails orderProductDetails, OrderDetails otsOrderDetails, String customerName) {
	    try {
	    	ExecutorService executor = Executors.newFixedThreadPool(10);
	    	
	        AddProductStockBORequest addProductStockBORequest = new AddProductStockBORequest();
	        AddProductStock addProductStock = new AddProductStock();
	        addProductStock.setProductId(productDetails.getProductId());
	        addProductStock.setUsersId(productDetails.getDistributorId());
	        addProductStock.setProductStockQty(orderProductDetails.getOtsOrderedQty());
	        addProductStockBORequest.setRequestData(addProductStock);
	        GetProductBOStockResponse getProductStock = productStockDao.updateProductStockQuantity(addProductStockBORequest);

	        if (Integer.parseInt(getProductStock.getStockQuantity()) <= 10) {
	            String distMsg = "<p>Hi,<br><br>" +
	                    "Your Product " + productDetails.getProductName() + " stock is less than 10. <br>" +
	                    "Please add the stock to get uninterrupted orders. <br>" +
	                    "Thanks And Regards,<br>" +
	                    companyName + " Support Team </p>";
	            executor.submit(() -> emailUtil.sendDistributermail(productDetails.getDistributorEmailId(), "", "Add Product Stock", distMsg));
	        }
          
            //Clickable Link for Distributor Login page
	        String distPageLink = "<a href= '"+distributorPageLoginLink+"'>Login</a> </p>";
			
			//Creating message content
			String distMsg="<p>Hi,<br>" + 
					"You have received a New Order placed by Customer " + customerName+"<br>"+
					"Please Approve Order No: "+otsOrderDetails.getOrderNumber()+" with Sub OrderId: "+orderProductDetails.getOtsSubOrderId()+"<br>" +
					"Please Login to know the Order details. Click here to "+distPageLink+"</br><br>"+
					"Thanks And Regards,<br>" + 
					companyName + " Support Team </p>";
	        executor.submit(() -> emailUtil.sendDistributermail(productDetails.getDistributorEmailId(), "", "New Order Received", distMsg));
	        
	        executor.shutdown();
	    }catch(Exception e){
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
	}

	private void applyCoupon(InsertOrderRequest insertOrderRequest, OrderDetails otsOrderDetails, OrderProductBOResponse response) {
	    try {
	        Coupon couponDetails = couponDAO.getCouponDetailsByStatus(insertOrderRequest.getRequest().getCouponId(), "active");

	        if (couponDetails == null) {
	            response.setCouponStatus("Invalid Coupon");
	        } else {
	            if (Integer.parseInt(couponDetails.getCouponUnit()) == 0) {
	                response.setCouponStatus("Coupon unavailable");
	            } else {
	                couponOrderDAO.insertCouponToOrder(Integer.parseInt(insertOrderRequest.getRequest().getCouponId()), otsOrderDetails.getOrderId());
	                couponDAO.updateCouponCount(insertOrderRequest.getRequest().getCouponId());
	                response.setCouponStatus("Coupon is added");
	            }
	        }
	    }catch(Exception e){
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
	}
	
	//schedulerOrder to order
	@Override
	public String schedulerOrder(AddOrUpdateOrderProductBOrequest addOrUpdateOrderProductBOrequest) {
		OrderDetails otsOrderDetails = new OrderDetails();
		UserDetails distributer=new UserDetails();
		ProductDetails productDetails=new ProductDetails();
	
		String Response;
		try {
			otsOrderDetails = orderServiceDAO.insertOrderAndGetOrderId(addOrUpdateOrderProductBOrequest);
			try {
				for(int i=0 ; i <addOrUpdateOrderProductBOrequest.getRequest().getProductList().size() ; i++)
				{
					productDetails =productServiceDAO.getProductDetails(addOrUpdateOrderProductBOrequest.getRequest().getProductList().get(i).getProductId());
					distributer  =userServiceDAO.getUserDetails(productDetails.getDistributorId());	
					orderProductDao.insertOrdrerProductByOrderId(otsOrderDetails.getOrderId(),distributer.getUserId(), addOrUpdateOrderProductBOrequest.getRequest().getProductList().get(i));
				}
				Response = "Order Placed and OrderId Is "+otsOrderDetails.getOrderId();
				return Response;
			}catch(Exception e){
				throw new BusinessException(e, ErrorEnumeration.INPUT_PARAMETER_INCORRECT);
			} catch (Throwable e) {
				throw new BusinessException(e, ErrorEnumeration.INPUT_PARAMETER_INCORRECT);
			}
		}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.INPUT_PARAMETER_INCORRECT);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.INPUT_PARAMETER_INCORRECT);
		}

	}
	
	@Override
	public String assignOrderToEmployee(AssignOrderToEmployeeRequest assignOrderToEmployeeRequest) {
	    String response = null;
	    ExecutorService executor = Executors.newCachedThreadPool();
	    try {
	        // Check whether the distributor has registered Company Details or not
	        List<DistributorCompanyDetails> distCompanyDetails = distributorCompanyDetailsDAO.getDistributorCompanyDetails(assignOrderToEmployeeRequest.getRequest().getDistributorId());
	        if (distCompanyDetails.isEmpty()) {
	            return "Distributor Not Added Company Details";
	        }
	        
	        // Fetch customer details in parallel
	        Future<UserDetails> customerFuture = executor.submit(() -> userServiceDAO.getUserDetails(assignOrderToEmployeeRequest.getRequest().getCustomerId()));
	        UserDetails customer = customerFuture.get();
	        if(customer == null) {
	        	return null;
	        }

	        //updating the Assign id for orders in ots_order_product table
	        response = orderProductDao.assignOrderToEmployee(assignOrderToEmployeeRequest);
	        if(response.equalsIgnoreCase("Updated")) {
	        	response = "Order Has Been Assigned Successfully";
	        }else {
	        	return null;
	        }
	        
	        //Getting order product details
	        OrderProductDetails orderProduct = orderProductDao.getOrderProductByOrderIdProductId(assignOrderToEmployeeRequest.getRequest().getOrderId(), assignOrderToEmployeeRequest.getRequest().getProductId());

	        // Send email notifications in parallel for Customer & Employee
	        //To send email notification to employee for Assigned order
	        executor.submit(() -> {
	        	//Creating message content
				String empMsg ="Hi,\n\n" + 
						"SubOrder number "+orderProduct.getOtsSubOrderId()+" from Order Number: "+orderProduct.getOtsOrderNumber()+" is Assigned to you.\n\n"+
						"Thanks And Regards,\n" + 
						companyName + " Support Team";

				emailUtil.sendEmployeemail(orderProduct.getEmployeeEmailId(), "", "Regarding Order Assignment", empMsg);
	        });

	        //To send mail to customer for every Accepted Products by Distributor
	        executor.submit(() -> {
	        	//Creating message content
	            String custMsg ="<p>Hi,<br><br>" + 
	            		orderProduct.getProductName()+" from your Order Number: "+orderProduct.getOtsOrderNumber()+" has been Accepted by the Seller. <br><br>"+
						"Thanks And Regards,<br>" + 
						companyName+" Support Team</p>";
	            emailUtil.sendCustomermail(customer.getEmailId(), "", "Regarding Order Update", custMsg);
	        });

	        // Generate PDF tasks in parallel
	        //To send Bill of Supply only for Tax unregistered distributor
	        if (!distCompanyDetails.get(0).getTaxAvailability()) {
	        	//To generate Bill Of Supply for LLP From Distributor to save in OrderProduct Table, when SubOrder is Assigned to Employee under Unregistered Distributor
	            executor.submit(() -> generateBillOfSupplyPdf(assignOrderToEmployeeRequest));
	        }
	        //To generate Order Product Invoice for Customer and gets saved in DB
	        executor.submit(() -> generateOrderProductInvoicePdf(assignOrderToEmployeeRequest.getRequest().getOrderId(), orderProduct.getOtsProductId()));
	    } catch (Exception e) {
	        e.printStackTrace();
	        logger.error("Error in inserting order in order table" + e.getMessage());
	        throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
	    } catch (Throwable e) {
	        e.printStackTrace();
	        logger.error("Error in inserting order in order table" + e.getMessage());
	        throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
	    } finally {
	        executor.shutdown();
	    }
	    return response;
	}
	
	//get customer order by status of order
	@Override
	public OrderProductBOResponse getCustomerOrderStatus(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		List<OrderDetailsAndProductDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();
		try {
			//get customer order details
			List<OrderDetails> OrderDetailsList = orderServiceDAO.getCustomerOrderStatus(getCustomerOrderByStatusBOrequest);
			if(OrderDetailsList.size() == 0) {
				return null;
			}else {
				for (int i = 0; i <OrderDetailsList.size() ; i++)
				{
					//get product details for customer's order 
					List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getProductListByOrderId(OrderDetailsList.get(i).getOrderId());
					GetOrderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(OrderDetailsList.get(i),OrderProductDetailsList));
				}
				orderProductBOResponse.setOrderList(GetOrderDetailsAndProductDetails);	
			}
			return orderProductBOResponse;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	//get order details for the different date
	@Override
	public OrderProductBOResponse getOrderDetailsByDate(GetOrderBORequest getOrderBORequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		List<OrderDetailsAndProductDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();
		try {
			List<OrderDetails> OrderDetailsList = orderServiceDAO.getOrderBydate(getOrderBORequest);
			if(OrderDetailsList.size() == 0) {
				return null;
			}
			else {
				for (int i = 0; i <OrderDetailsList.size() ; i++)
				{
					List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getProductListByOrderId(OrderDetailsList.get(i).getOrderId());
					GetOrderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(OrderDetailsList.get(i),OrderProductDetailsList));
				}
				orderProductBOResponse.setOrderList(GetOrderDetailsAndProductDetails);
			}
			return orderProductBOResponse;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	//get order details for the different date(duplicate)
	@Override
	public GetListOfOrderByDateBOResponse getListOfOrderByDate(GetListOfOrderByDateBORequest getListOfOrderByDateBORequest) {
		try{
			GetListOfOrderByDateBOResponse getListOfOrderByDateBOResponse = new GetListOfOrderByDateBOResponse();
			//To order details based on multiple requests
			List<CompleteOrderDetails> orderDetails = orderServiceDAO.getListOfOrderByDate(getListOfOrderByDateBORequest);
			if(orderDetails.size()== 0) {
				return null;
			}else {
				//To get order product details based on distributor
				if(getListOfOrderByDateBORequest.getRequest().getRole().equalsIgnoreCase("Distributor")) {
					for(int i = 0 ; i<orderDetails.size() ; i++) {
						//To get order product details based on orderId, distributor Id & orderProductStatus (if required)
						orderDetails.get(i).setOrderProductDetails(orderProductDao.getOrderByDistributorIdAndStatus(orderDetails.get(i).getOrderId(),getListOfOrderByDateBORequest.getRequest().getUserId(),getListOfOrderByDateBORequest.getRequest().getStatus()));
					}
					getListOfOrderByDateBOResponse.setCompleteOrderDetails(orderDetails);
				}
				else {
					//To get order product details based on orderId
					for(int i = 0 ; i<orderDetails.size() ; i++) {
						orderDetails.get(i).setOrderProductDetails(orderProductDao.getProductListByOrderId(orderDetails.get(i).getOrderId()));
					}
					getListOfOrderByDateBOResponse.setCompleteOrderDetails(orderDetails);
				}
				
				//To generate order report PDF if requested & order details is not null
				if(getListOfOrderByDateBORequest.getRequest().getPdf().equalsIgnoreCase("YES") && orderDetails.size() != 0)
				{
					String pdf = orderReportPDF(orderDetails);
					getListOfOrderByDateBOResponse.setPdf(pdf);
				}
			}
			return getListOfOrderByDateBOResponse;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	
	//getting order report for the date
	@Override
	public OrderProductBOResponse orderReportByDate(GetOrderBORequest getOrderBORequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		List<OrderDetailsAndProductDetails> getOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();	
		try {	
			//To get order details based on distributor, customer & product (if required)
			List<OrderDetails> OrderDetailsList = orderServiceDAO.getOrderReportByDistributorAndCustomer(getOrderBORequest);
			if(OrderDetailsList.size() == 0) {
				return null;
			}
			else {
				//To get order product details based on orderId, distributorId & orderProductStatus (if required)
				if(getOrderBORequest.getRequest().getProductId() == null || getOrderBORequest.getRequest().getProductId().equals("") ) {
					for (int i = 0; i <OrderDetailsList.size(); i++)
					{
						List<OrderProductDetails> orderProductDetailsList = orderProductDao.getOrderByDistributorIdAndStatus(OrderDetailsList.get(i).getOrderId(),getOrderBORequest.getRequest().getDistributorsId(),getOrderBORequest.getRequest().getStatus());
						getOrderDetailsAndProductDetails.add(GetProductAndOrderDetails(OrderDetailsList.get(i),orderProductDetailsList));     
				    }
				}else {
					//To get order product details based on orderId, productId & orderProductStatus (if required)
					for (int i = 0; i <OrderDetailsList.size(); i++)
					{
						List<OrderProductDetails> orderProductDetailsList = orderProductDao.getOrderProductByOrderIdProductIdOPStatus(OrderDetailsList.get(i).getOrderId(),getOrderBORequest.getRequest().getProductId(),getOrderBORequest.getRequest().getStatus());
						getOrderDetailsAndProductDetails.add(GetProductAndOrderDetails(OrderDetailsList.get(i),orderProductDetailsList));     
				    }
				}
				
				//To generate order report PDF if requested & order details is not null
				if(getOrderBORequest.getRequest(). getPdf().equalsIgnoreCase("YES") && getOrderDetailsAndProductDetails.size() != 0)
				{
					String pdf = orderLedgureReportPDF(getOrderDetailsAndProductDetails);                                                                                               	
					orderProductBOResponse.setPdf(pdf);
				}	
				orderProductBOResponse.setOrderList(getOrderDetailsAndProductDetails);
			}
			return orderProductBOResponse;
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


	
	@Override	//To Check all the sub orders are not in New state 
	public boolean checkForOrderAssigned(String orderId) {
		int count = 0;
		try {
			//To get sub order details in an Order 
			List<OrderProductDetails> productList = orderProductDao.getProductListByOrderId(orderId);
			//To count when order product not New
			for(int i=0;i<productList.size(); i++) {
				if(!productList.get(i).getOtsOrderProductStatus().equalsIgnoreCase("New")) {
					count++;
				}
			}
			//When count of Not New is equal to Total order product list count, then the function return True or else False
			return productList.size() == count;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}
	
	@Override	//To Check all the sub orders are not in New state & if any one sub order product is Assigned, then main order should be set to Assigned
	public boolean checkForOrderCancel(String orderId) {
		int count = 0;
		try {
			//To get sub order details in an Order 
			List<OrderProductDetails> productList = orderProductDao.getProductListByOrderId(orderId);
			for(int i=0;i<productList.size(); i++) {
				//if all the sub orders status are not New, then it should count for Assigned & Cancel
				if(!productList.get(i).getOtsOrderProductStatus().equalsIgnoreCase("New") && !productList.get(i).getOtsOrderProductStatus().equalsIgnoreCase("Assigned")) {
					if(productList.get(i).getOtsOrderProductStatus().equalsIgnoreCase("Cancel")) {
						count++;
					}
					System.out.println("Cancel count = "+count);
				}
			}
			System.out.println("Poduct list count = "+productList.size());
			//When addition of both the count's of Assigned & Cancel is equal to Total order product list count, then the function return true else false
			return productList.size() == count;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}
	
	//Alternate codes for Optimizing Api 
//	@Override // To Check all the sub orders are not in New state
//	public boolean checkForOrderAssigned(String orderId) {
//	    try {
//	        // Get sub order details in an Order
//	        List<OrderProductDetails> productList = orderProductDao.getProductListByOrderId(orderId);
//	        // Check if all sub orders are not in "New" state
//	        long count = productList.stream()
//                    .filter(product -> !product.getOtsOrderProductStatus().equalsIgnoreCase("New"))
//                    .count();
//	        
//	        return productList.size() == count;
////	        return productList.stream().allMatch(product -> !product.getOtsOrderProductStatus().equalsIgnoreCase("New"));
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
//	    } catch (Throwable e) {
//	        e.printStackTrace();
//	        throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
//	    }
//	}
	
//	@Override // To Check all the sub orders are not in New state & if any one sub order product is Assigned, then main order should be set to Assigned
//	public boolean checkForOrderCancel(String orderId) {
//	    try {
//	        // Get sub order details in an Order
//	        List<OrderProductDetails> productList = orderProductDao.getProductListByOrderId(orderId);
//	        
//	        long cancelCount = productList.stream()
//	                                      .filter(product -> !product.getOtsOrderProductStatus().equalsIgnoreCase("New"))
//	                                      .filter(product -> product.getOtsOrderProductStatus().equalsIgnoreCase("Cancel"))
//	                                      .count();
//
//	        System.out.println("cancelCount = "+cancelCount);
//	        // When the count of Cancelled products is equal to the total product list size, return true, else false
//	        return productList.size() == cancelCount;
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
//	    } catch (Throwable e) {
//	        e.printStackTrace();
//	        throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
//	    }
//	}
	
	@Override	//To Check all the sub orders status are Closed 
	public boolean checkForOrderClosed(String orderId) {
		int closeCount = 0;
		int cancelCount = 0;
		try { 
			//To get sub order details in an Order 
			List<OrderProductDetails> productList = orderProductDao.getProductListByOrderId(orderId);
			//To count when order product not New
			for(int i=0;i<productList.size(); i++) {
				if(!productList.get(i).getOtsOrderProductStatus().equalsIgnoreCase("New") && !productList.get(i).getOtsOrderProductStatus().equalsIgnoreCase("Assigned")) {
					if(productList.get(i).getOtsOrderProductStatus().equalsIgnoreCase("Close")) {
						closeCount++;
					}
					System.out.println("Close count = "+closeCount);
					if(productList.get(i).getOtsOrderProductStatus().equalsIgnoreCase("Cancel")) {
						cancelCount++;
					}
					System.out.println("Cancel count = "+cancelCount);
				}
			}
			//When count of Not New is equal to Total order product list count, then the function return True or else False
			return productList.size() == closeCount+cancelCount;
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}
	
	@Override //update order status to Assigned or Cancel
	public String cancelMainAndSubOrder(AssignOrderToEmployeeRequest assignOrderToEmployeeRequest) {
		String response = null;
		boolean subOrder;
		boolean assignOrder;
		try {
			if(assignOrderToEmployeeRequest.getRequest().getOrderStatus().equalsIgnoreCase("Cancel") 
					&& assignOrderToEmployeeRequest.getRequest().getProductId()==null ) 
			{
				//To update order status to Cancel for main Order in ots_order table
				orderServiceDAO.UpdateOrderStatus(assignOrderToEmployeeRequest.getRequest().getOrderId(),assignOrderToEmployeeRequest.getRequest().getOrderStatus());
				response = "Main Order Has Been Cancelled Successfully!";
				
				//To check if the Order has multiple Products in it, if it has then need to add product stock when order gets cancelled 
				List<OrderProductDetails> orderProductDetails = orderProductDao.getProductListByOrderId(assignOrderToEmployeeRequest.getRequest().getOrderId());
				if(orderProductDetails.size() > 0) {
					for(int i=0; i<orderProductDetails.size();  i++) {
						//To add product stock when order gets cancelled 
						AddProductStockBORequest addProductStockBORequest = new AddProductStockBORequest();
						AddProductStock addProductStock = new AddProductStock();
						//setting request for add product stock
						addProductStock.setProductId(orderProductDetails.get(i).getOtsProductId());
						addProductStock.setUsersId(orderProductDetails.get(i).getDistributorId());
						addProductStock.setProductStockQty(orderProductDetails.get(i).getOtsOrderedQty());
						addProductStockBORequest.setRequestData(addProductStock);
						//To add product stock quantity with old quantity
						productStockDao.addProductStock(addProductStockBORequest); 
					}
				}
				
				//To send Email to customer for Order Cancel
				UserDetails customer;
				customer = userServiceDAO.getUserDetails(assignOrderToEmployeeRequest.getRequest().getCustomerId());
				String custNotification ="<p>Hi "+customer.getFirstName()+" "+customer.getLastName()+", <br><br>" + 
						"We're writing to let you know that your Order No: "+orderNoFormat+assignOrderToEmployeeRequest.getRequest().getOrderId()+" has been Cancelled by Seller. <br>"+
						"Continue shopping with "+companyName+".<br><br>"+
						"Thanks And Regards,<br>" + 
						companyName+" Support Team </p>";
				emailUtil.sendCustomermail(customer.getEmailId(), "", "Order Cancellation",custNotification);	
				try {
					fcmPushNotification.sendPushNotification(customer.getDeviceId(),"etaarana app" , "Your Order has been Cancelled");
				}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
				}
			}else
			{
				//To update order status to Cancel in ots_order_product table
				orderProductDao.updateSubOrder(assignOrderToEmployeeRequest);
				response = "Order Has Been Cancelled Successfully!";

				//To check whether any one of the order is Assigned or Not, if its Assigned then main order status will be set to Assigned
				subOrder = checkForOrderCancel(assignOrderToEmployeeRequest.getRequest().getOrderId());
				if(subOrder == true) {
					orderServiceDAO.UpdateOrderStatus(assignOrderToEmployeeRequest.getRequest().getOrderId(),"Cancel");
				}
				else {
					assignOrder = checkForOrderAssigned(assignOrderToEmployeeRequest.getRequest().getOrderId());
					if(assignOrder == true) {
						orderServiceDAO.UpdateOrderStatus(assignOrderToEmployeeRequest.getRequest().getOrderId(),"Assigned");
					}
				}
				
				//To fetch order product details
				OrderProductDetails orderDetails = orderProductDao.getOrderProductByOrderIdProductId(assignOrderToEmployeeRequest.getRequest().getOrderId(),assignOrderToEmployeeRequest.getRequest().getProductId());
				//To add product stock when order gets cancelled 
				AddProductStockBORequest addProductStockBORequest = new AddProductStockBORequest();
				AddProductStock addProductStock = new AddProductStock();
				//setting request for add product stock
				addProductStock.setProductId(orderDetails.getOtsProductId());
				addProductStock.setUsersId(orderDetails.getDistributorId());
				addProductStock.setProductStockQty(orderDetails.getOtsOrderedQty());
				addProductStockBORequest.setRequestData(addProductStock);
				//To add product stock quantity with old quantity
				productStockDao.addProductStock(addProductStockBORequest); 
				
				//To send Email to Customer for Order Cancel
				UserDetails customer;
				customer = userServiceDAO.getUserDetails(assignOrderToEmployeeRequest.getRequest().getCustomerId());
				String custNotification ="<p>Hi "+customer.getFirstName()+" "+customer.getLastName()+", <br><br>" + 
						"We're writing to let you know that your Order Number: "+orderNoFormat+assignOrderToEmployeeRequest.getRequest().getOrderId()+" for Product "+orderDetails.getProductName()+" has been Cancelled by Seller. <br>"+
						"Continue shopping with "+companyName+".<br><br>"+
						"Thanks And Regards,<br>" + 
						companyName+" Support Team </p>";
				emailUtil.sendCustomermail(customer.getEmailId(), "", "Order Cancellation",custNotification);	
				try {
						fcmPushNotification.sendPushNotification(customer.getDeviceId(),"etaarana app" , "Your Sub Order has been Cancelled");
				}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
				}
			}
			return response;
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

	public String orderLedgureReportPDF(List<OrderDetailsAndProductDetails> orderDetailsList) {
 	    String encodedString = null;
 	    LocalDate now = LocalDate.now();
 	    String dateFormat = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
 	    
 	    try {
 	        StringBuilder reportDetails = new StringBuilder();
 	        reportDetails.append("<body>")
 	                     .append("<h3 style='text-align:center;'><u><b>Buyer Ledger Report</b></u></h3><br>");
 	        
 	        if (!orderDetailsList.isEmpty()) {
 	            OrderDetailsAndProductDetails firstOrder = orderDetailsList.get(0);
 	            if (!firstOrder.getOrderdProducts().isEmpty()) {
 	                OrderProductDetails firstProduct = firstOrder.getOrderdProducts().get(0);
 	                reportDetails.append("<b>Seller Name: ")
 	                             .append(firstProduct.getDistributorFirstName()).append(" ")
 	                             .append(firstProduct.getDistributorLastName()).append("</b><br>")
 	                             .append("<b>Buyer Name: ")
 	                             .append(firstOrder.getCustomerName()).append("</b><br>")
 	                             .append("<b>Date: ")
 	                             .append(dateFormat).append("</b><br><br>");
 	            }
 	        }
 
 	        StringBuilder tableValueString = new StringBuilder();
 	        tableValueString.append("<table border='1' style='border-collapse:collapse; text-align:center; width:100%;'>")
 	                         .append("<tr style='background-color:#f2f2f2; text-align:center;'>")
 	                         .append("<th width='5%'>Sl No</th>")
 	                         .append("<th>Order No</th>")
 	                         .append("<th>SubOrder ID</th>")
 	                         .append("<th>Ordered Qty</th>")
 	                         .append("<th>SubOrder Cost</th>")
 	                         .append("<th>Delivered Date</th>")
 	                         .append("<th>Employee</th>")
 	                         .append("<th>Status</th>")
 	                         .append("</tr>");
 	        
 	        int slno = 0;
 	        for (OrderDetailsAndProductDetails orderDetails : orderDetailsList) {
 	            for (OrderProductDetails product : orderDetails.getOrderdProducts()) {
 	                slno++;
 	                tableValueString.append("<tr>")
                     .append("<td style='text-align:center;'>").append(slno).append("</td>")
                     .append("<td style='text-align:center;'>").append(orderDetails.getOrderNumber()).append("</td>")
                     .append("<td style='text-align:center;'>").append(product.getOtsSubOrderId()).append("</td>")
                     .append("<td style='text-align:center;'>").append(product.getOtsOrderedQty()).append("</td>")
                     .append("<td style='text-align:center;'>").append(product.getOtsOrderProductCost()).append("</td>")
                     .append("<td style='text-align:center;'>").append(product.getSubOrderDeliveredDate()).append("</td>")
                     .append("<td style='text-align:center;'>").append(product.getEmployeeFirstName()).append(" ")
                     .append(product.getEmployeeSecondName()).append("</td>")
                     .append("<td style='text-align:center;'>").append(product.getOtsOrderProductStatus()).append("</td>")
                     .append("</tr>");
 	            }
 	        }
 	        tableValueString.append("</table></body>");
 	        
 	        String htmlString = "<html>" + reportDetails.toString() + tableValueString.toString() + "</html>";
 	        String path = OTSUtil.generateReportPDFFromHTMLLandscape(htmlString, "CustomerLedgerReport.pdf");
 	        
 	        try {
 	            byte[] fileContent = FileUtils.readFileToByteArray(new File(path));
 	            encodedString = Base64.getEncoder().encodeToString(fileContent);
 	        } catch (IOException e) {
 	            logger.error("Error reading PDF file: " + e.getMessage());
 	            e.printStackTrace();
 	        }
 	    } catch (Exception e) {
 	        logger.error("Exception while generating ledger report: " + e.getMessage());
 	        e.printStackTrace();
 	        throw new BusinessException(e.getMessage(), e);
 	    }
 	    return encodedString;
	}
	
	//ledger report generation for order and distributor
	public String  orderReportPDF(List<CompleteOrderDetails> getOrderDetailsAndProductDetails) {
		String encodedString = null;
		LocalDate now = LocalDate.now();
		String dateFormat = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		try {
			String tableValueString ="";
			String reportDetails = "<body><h3 style='text-align:center;'><u><b>Order Report</b></u></h3>";
			reportDetails += "<b>Seller Name:"+getOrderDetailsAndProductDetails.get(0).getOrderProductDetails().get(0).getDistributorFirstName()+" "+getOrderDetailsAndProductDetails.get(0).getOrderProductDetails().get(0).getDistributorLastName()+"</b><br>";
			reportDetails += "<b>Date:"+dateFormat+"</b> <br><br>";
	
			int slno=0;
			tableValueString = "<table border=\"1\" style='text-align:center;'><tr width='15%' style='text-align:center;'><strong>" + 
					"<th width='5%'>Sl no</th>" + 
					"<th>Order No</th>" + 
					"<th>SubOrder No</th>" + 
					"<th>Order Date</th>" + 
					"<th width='20%'>Buyer Name</th>" + 
					"<th width='20%'>Product Name</th>" + 
					"<th>Ordered Qty</th>" + 
					"<th>SubOrder Cost</th>" +
					"<th>Delivered Date</th>" + 
					"<th width='20%'>Delivered By</th>" +
					"<th>Status</th>" +
					"</strong></tr>";
	
			for(CompleteOrderDetails orderDetailsAndProductDetailed:getOrderDetailsAndProductDetails) {
				for(int i=0; i<orderDetailsAndProductDetailed.getOrderProductDetails().size(); i++) {
					slno++;
					tableValueString=tableValueString+"<tr width='15%' style='text-align:center;'>" + 
							"<td width='5%'>"+slno+"</td>" + 
							"<td>"+orderDetailsAndProductDetailed.getOrderNumber()+"</td>" + 
							"<td>"+orderDetailsAndProductDetailed.getOrderProductDetails().get(i).getOtsSubOrderId()+"</td>" + 
							"<td>"+orderDetailsAndProductDetailed.getOrderDate()+"</td>" + 
							"<td width='20%'>"+orderDetailsAndProductDetailed.getCustomerName()+"</td>" +
							"<td width='20%'>"+orderDetailsAndProductDetailed.getOrderProductDetails().get(i).getProductName()+"</td>" +
							"<td>"+orderDetailsAndProductDetailed.getOrderProductDetails().get(i).getOtsOrderedQty()+"</td>" +
							"<td>"+orderDetailsAndProductDetailed.getOrderProductDetails().get(i).getOtsOrderProductCost()+"</td>" +
							"<td>"+orderDetailsAndProductDetailed.getOrderProductDetails().get(i).getSubOrderDeliveredDate()+"</td>" + 
							"<td width='20%'>"+orderDetailsAndProductDetailed.getOrderProductDetails().get(i).getEmployeeFirstName()+" "+orderDetailsAndProductDetailed.getOrderProductDetails().get(i).getEmployeeSecondName()+"</td>" + 
							"<td>"+orderDetailsAndProductDetailed.getOrderProductDetails().get(i).getOtsOrderProductStatus()+"</td>" + 
							"</tr>";
				}
	
			}
			tableValueString =tableValueString+ "</table></body>";
			
			String htmlString = "<html>"+reportDetails+tableValueString+"</html>";
			
			String path = OTSUtil.generateReportPDFFromHTMLLandscape(htmlString,"OrderDetailsReport.pdf");
			byte[] fileContent;
			
			try {
				fileContent = FileUtils.readFileToByteArray(new File(path));
				encodedString = Base64.getEncoder().encodeToString(fileContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return encodedString;		
	}

	@Override
	public OrderDetailsBOResponse getRazorPayOrder(UpdateOrderDetailsRequest updateOrderDetailsRequest) throws JSONException {
		// TODO Auto-generated method stub
		OrderDetailsBOResponse orderDetailsBOResponse = new OrderDetailsBOResponse();
		try {
			RazorpayClient razorpay = null;
			if(updateOrderDetailsRequest.getRequest().getPaymentFlowStatus().equalsIgnoreCase("gift")) {
				System.out.println("key "+donationRazorpayKey+"donationRazorpaySignature "+donationRazorpaySignature);
				razorpay = new RazorpayClient(donationRazorpayKey,donationRazorpaySignature);
				orderDetailsBOResponse.setRazorPayKey(donationRazorpayKey);
				//	orderDetails.setRazorPayKey();
			}else {
				//ots account
				System.out.println("key "+donationRazorpayKey+"donationRazorpaySignature "+donationRazorpaySignature);
				razorpay = new RazorpayClient(donationRazorpayKey,donationRazorpaySignature);
			//	orderDetails.setRazorPayKey();
				orderDetailsBOResponse.setRazorPayKey(donationRazorpayKey);
			}
			
			JSONObject orderRequest = new JSONObject();
			  try {
				orderRequest.put("amount", updateOrderDetailsRequest.getRequest().getOrderCost());
				orderRequest.put("currency", "INR");
				orderRequest.put("receipt", updateOrderDetailsRequest.getRequest().getOrderId());
				orderRequest.put("payment_capture", true);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
				e.printStackTrace();
			} // amount in the smallest currency unit
			 Order order = razorpay.Orders.create(orderRequest);
			 System.out.println(order);
			 System.out.println(order.toJson().get("amount"));
			 OrderDetails orderDetails = new OrderDetails();
			 orderDetails.setOrderId(order.toJson().get("id").toString());
			 orderDetails.setReceipt(order.toJson().get("receipt").toString());
			 List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
			 orderDetailsList.add(orderDetails);
			 orderDetailsBOResponse.setOrderDetails(orderDetailsList);
		} catch (RazorpayException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return orderDetailsBOResponse;
	}
	
	@Override
	public CcAvenueOrderDetailsResponse getCCAvenueCredentials() {
		CcAvenueOrderDetailsResponse ccAvenueOrderDetailsResponse = new CcAvenueOrderDetailsResponse();
		try {
			System.out.println("merchantId "+ccAvenueMerchantId+" accessCode "+ccAvenueAccessCode+" workingKey "+ccAvenueWorkingKey);
			ccAvenueOrderDetailsResponse.setCcAvenueMerchantId(ccAvenueMerchantId);
			ccAvenueOrderDetailsResponse.setCcAvenueAccessCode(ccAvenueAccessCode);
			ccAvenueOrderDetailsResponse.setCcAvenueWorkingKey(ccAvenueWorkingKey);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return ccAvenueOrderDetailsResponse;
	}

	@Override
	public JSONObject fetchPaymentDetailsByPaymetId(String paymentId) {
		Payment payment = null;
		RazorpayClient razorpay;
		JSONObject paymentDetails = null;
		try {
			razorpay = new RazorpayClient("rzp_test_oGbRsq49chxYFD", "LBC49xder65RbkAysqN0hv3j");
			payment = razorpay.Payments.fetch(paymentId);
			paymentDetails = new JSONObject(payment);
		} catch (RazorpayException e1) {
			EmailUtil.sendDonationMail("manoj.vg@ortusolis.com","","Etaarana Error in donation", 
					paymentId
					+ "fetchPaymentDetailsByPaymetId");
			e1.printStackTrace();
		}
		System.out.print(payment);
		return paymentDetails;
	}

	@Override
	public OrderProductBOResponse getOrderDetailsForOrderId(String OrderId) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		List<OrderDetailsAndProductDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();
		try {
			List<OrderDetails> OrderDetailsList = orderServiceDAO.getOrderDetailsForOrderId(OrderId);
			if(OrderDetailsList.size() == 0) {
				return null;
			}
			else {
				for (int i = 0; i <OrderDetailsList.size() ; i++)
				{
					List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getProductListByOrderId(OrderDetailsList.get(i).getOrderId());
					GetOrderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(OrderDetailsList.get(i),OrderProductDetailsList));
				}
				orderProductBOResponse.setOrderList(GetOrderDetailsAndProductDetails);
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return orderProductBOResponse;
	}

	@Override
	public List<List<String>> getOrderDetailsForInvoice(String orderId) {
		try {
			Map<String, Object> queryParameter = new HashMap<String, Object>();
			queryParameter.put("order_id", UUID.fromString(orderId));
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_order_details_for_invoice")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("order_id", Types.OTHER));
			
			Map<String, Object> result = simpleJdbcCall.execute(queryParameter);
			List<Map<String, String>> orderDetails = (List<Map<String, String>>) result.get("#result-set-1");
	
			//To Add Sl.no at beginning of the map as Key Value pair to existing map & adding numbers incrementing from 1
			int sl = 1;
			for(Map<String,String> map : orderDetails) {
			    Map<String,String> mapcopy = new LinkedHashMap<String,String>(map);
			    map.clear();
			    map.put("Sl.no",String.valueOf(sl++));
			    map.putAll(mapcopy);
			}
		
			//Converting List<Map<String, String>> into List<List<String>> List of values
			List<String> valueList = null;
			List<List<String>> order = new ArrayList<>();
			for(int index = 0 ; index < orderDetails.size() ; index++){
				Map<String, String> listItem = orderDetails.get(index);
				for(int j=0; j<listItem.size();j++) {
				    valueList = new ArrayList<String>(listItem.values());
				}
				System.out.println("valueList = "+valueList);
				order.add(valueList);
			} 
			return order;
		}catch(Exception e){
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
	public List<List<String>> getOrderDetailsForBillOfSupply(String orderId,String distributorId) {
		try {
			Map<String, Object> queryParameter = new HashMap<String, Object>();
			queryParameter.put("order_id",UUID.fromString(orderId));
			queryParameter.put("distributor_id",UUID.fromString(distributorId));
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_order_details_for_bill_of_supply")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("order_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));
			
			Map<String, Object> result = simpleJdbcCall.execute(queryParameter);
			List<Map<String, String>> orderDetails = (List<Map<String, String>>) result.get("#result-set-1");
	
			//To Add Sl.no at beginning of the map as Key Value pair to existing map & adding numbers incrementing from 1
			int sl = 1;
			for(Map<String,String> map : orderDetails) {
			    Map<String,String> mapcopy = new LinkedHashMap<String,String>(map);
			    map.clear();
			    map.put("Sl.no",String.valueOf(sl++));
			    map.putAll(mapcopy);
			}
		
			//Converting List<Map<String, String>> into List<List<String>> List of values
			List<String> valueList = null;
			List<List<String>> order = new ArrayList<>();
			for(int index = 0 ; index < orderDetails.size() ; index++){
				Map<String, String> listItem = orderDetails.get(index);
				for(int j=0; j<listItem.size();j++) {
				    valueList = new ArrayList<String>(listItem.values());
				}
				System.out.println("valueList = "+valueList);
				order.add(valueList);
			} 

			return order;
		}catch(Exception e){
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
	public List<List<String>> getDistributorForOrderInvoice(String orderId) {
		try {
			Map<String, Object> queryParameter = new HashMap<String, Object>();
			queryParameter.put("order_id",UUID.fromString(orderId));
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_distributor_for_order_invoice")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("order_id", Types.OTHER));
			
			Map<String, Object> result = simpleJdbcCall.execute(queryParameter);
			List<Map<String, String>> orderDetails = (List<Map<String, String>>) result.get("#result-set-1");
			
			//To Add Sl.no at beginning of the map as Key Value pair to existing map & adding numbers incrementing from 1
			int sl = 1;
			for(Map<String,String> map : orderDetails) {
			    Map<String,String> mapcopy = new LinkedHashMap<String,String>(map);
			    map.clear();
			    map.put("Sl.no",String.valueOf(sl++));
			    map.putAll(mapcopy);
			}
		
			//Converting List<Map<String, String>> into List<List<String>> List of values
			List<String> valueList = null;
			List<List<String>> order = new ArrayList<>();
			for(int index = 0 ; index < orderDetails.size() ; index++){
				Map<String, String> listItem = orderDetails.get(index);
				for(int j=0; j<listItem.size();j++) {
				    valueList = new ArrayList<String>(listItem.values());
				}
				System.out.println("valueList = "+valueList);
				order.add(valueList);
			} 
			
			return order;
		}catch(Exception e){
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
	public String closeMainOrder(String OrderId) {
		OrderDetails orderDetails = new OrderDetails();
		String Response;
		boolean subOrder;
		//To get the order list for requested orderId
		try {
			subOrder = checkForOrderClosed(OrderId);
			if(subOrder == true)
			{
				/*Update the Status As close in Order Table*/
				orderDetails = orderServiceDAO.closeOrder(OrderId);
				Response = "Order Id "+OrderId+" Has Been Closed Successfully";
				
				//To generate Order Invoice for Customer(from LLP to Customer), when Main order is Closed 
				generateOrderInvoicePdf(OrderId,orderDetails.getCustomerId());
				
				//Send Notification to Distributor for Order close
				try {
					//Notification message for Customer for Main Order Closed
					//Creating message content
					String custNotification ="<p>Hi "+orderDetails.getCustomerName()+", <br><br>" + 
							"Order Delivered Successfuly.<br>"+
							"All the Products from your order list Order Number: "+orderNoFormat+orderDetails.getOrderNumber()+" has been delivered. <br>"+
							"Thank you for shopping with "+companyName+".<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendCustomermail(orderDetails.getCustomerEmailId(), "", "Order Delivered",custNotification);

					//Add below code for sending Push Notification to Customer when DeviceId is available in otsUser table
//						fcmPushNotification.sendPushNotification(customer.getDeviceId(),"etaarana app" , custNotification);
//						System.out.println("order delivered date = "+otsOrderDetails.getOrderDeliverdDate());
						
				}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
				}
			}
			else {
				Response = null;
			}
			return Response;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);}
		catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}

	//Update Order Product Status to Close & if all the SubOrders are Closed then, Update main Order to Close
	@Override
	public String closeEmployeeOrder(CloseEmployeeOrderRequest closeEmployeeOrderRequest) {
		String Response = null;
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			String closeOrder = orderProductDao.closeEmployeeOrder(closeEmployeeOrderRequest);
			if(closeOrder.equalsIgnoreCase("Updated")) {
				Response = "Order Id "+ closeEmployeeOrderRequest.getRequest().getOrderId() +" Has Beed Closed Successfully";
			}else {
				return null;
			}

			//To get Order product Details 
			Future<OrderProductDetails> productDetailsFuture = executor.submit(() -> orderProductDao.getOrderProductByOrderIdProductId(closeEmployeeOrderRequest.getRequest().getOrderId(),closeEmployeeOrderRequest.getRequest().getProductId()));
			OrderProductDetails productDetails = productDetailsFuture.get();
			
			// Send email notifications in parallel for Distributor & Customer, When subOrder closed by Employee
			try {
				//To send email notification to Distributor for Close order
				executor.submit(() -> {
					//Creating message content
					String distNotification ="<p>Hi,\r\n\r\n" + 
							"SubOrder ID: "+productDetails.getOtsSubOrderId()+" for Order number ORD-"+productDetails.getOtsOrderId()+" has been Closed. <br><br>"+
							"Thanks And Regards, <br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendDistributermail(productDetails.getDistributorEmailId(), "", "Regarding Order Update",distNotification);
				});

				//To send email notification to Customer for Close order
				executor.submit(() -> {
					//To get Order details by orderId
					OrderDetails otsOrderDetails = orderServiceDAO.getOrderDetailsByOrderId(closeEmployeeOrderRequest.getRequest().getOrderId());
					
					String custNotification ="<p>Hi,\r\n\r\n" + 
							"Your product "+ productDetails.getProductName()+" has been delivered on "+productDetails.getSubOrderDeliveredDate()+"<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendCustomermail(otsOrderDetails.getCustomerEmailId(), "", "Regarding Order Update",custNotification);		
				});
			}catch(Exception e) {
				return Response;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
		return Response;
	}
	
	@Override
	public List<String> getSubOrderIdsWithNewStatusLessThanCurrentDate() {
	    try {
	    	//Procedure to get the subOrderId that are in New status & Addition of 24 hours with orderDate is Less than Current Date Time
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withFunctionName("get_order_by_new_status_less_than_current_date")
	            .withSchemaName("public")
	            .withoutProcedureColumnMetaDataAccess()
	            .declareParameters(new SqlOutParameter("return", Types.VARCHAR)); // JSON is treated as VARCHAR

	        Map<String, Object> result = jdbcCall.execute(new HashMap<>());
	        String json = (String) result.get("return");

	        // Convert JSON array to List<String>
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.readValue(json, new TypeReference<List<String>>() {});
	    }catch(Exception e){
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
	public String autoCancelOrderByDistributor() {
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			//Procedure to get the subOrderId that are in New Status till current date with 24 hrs Buffer time
			List<String> subOrderId = getSubOrderIdsWithNewStatusLessThanCurrentDate();
			
			//To notify Customer & Distributor about Order Cancellation for all the Cancelled Orders
			if(subOrderId.size() == 0) {
				return "No Orders Found";
			}else {
				executor.submit(() -> {
					for(int i=0; i<subOrderId.size(); i++) {
						//To get order Details by subOrderId
						OrderProductDetails orderDetails = orderProductDao.getOrderProductBySubOrderId(subOrderId.get(i));
						
						//Creating message content
						String distMsg ="<p>Hi,<br><br>" + 
								orderDetails.getProductName()+"from Order Number: "+orderNoFormat+orderDetails.getOtsOrderId()+" has been Cancelled due to delayed response. <br><br>"+
								"Thanks And Regards,<br>" + 
								companyName+" Support Team </p>";
						//To send Email to distributor for Order Cancel
						emailUtil.sendDistributermail(orderDetails.getDistributorEmailId(), "", "Regarding Order Cancellation", distMsg);
						
						//Creating message content
						String custMsg ="<p>Hi,<br><br>" + 
								orderDetails.getProductName()+"from your Order Number: "+orderNoFormat+orderDetails.getOtsOrderId()+" has been Cancelled due to delayed response from distributor. <br><br>"+
								"Thanks And Regards,<br>" + 
								companyName+" Support Team </p>";
						//To send Email to customer for Order Cancel
						UserDetails customer = userServiceDAO.getUserDetails(orderDetails.getCustomerId());
						emailUtil.sendCustomermail(customer.getEmailId(), "", "Regarding Order Cancellation", custMsg);
					}
				});
			}
			
			//Procedure to Fetch Orders that are in New Status till current date & Cancel those Order, Update Stocks for Product, Update Main Order Status to Cancel if needed.
			String sql = "SELECT public.auto_cancel_order_by_distributor()";
			String response = jdbcTemplate.queryForObject(sql, String.class); // Procedure response are "Updated" or "Not Updated" 
			
			return response;
		}catch(Exception e){
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
	public OrderDetails addPaymentDetailsForOrder(AddOrderPaymentDetailsRequest addOrderPaymentDetailsRequest) {
		OrderDetails orderDetails = new OrderDetails();
		String adminId = null;
		
		// Executor for concurrent tasks
        ExecutorService executor = Executors.newFixedThreadPool(10);
		try {
			List<OrderDetails> otsOrderDetails = orderServiceDAO.addPaymentDetailsForOrder(addOrderPaymentDetailsRequest);
			if(otsOrderDetails.size() == 0) {
				return null;
			}else {
				List<OrderProductDetails> orderProductDetails = orderProductDao.updateSubOrderStatus(otsOrderDetails.get(0).getOrderId(),"New");
				orderDetails = otsOrderDetails.get(0);
				
				if(orderProductDetails.size() == 0){
					return null;
				}else {
					//To Create table with order details for sending mail
				    String table = "<table border='1' style='border:2px solid black;border-collapse:collapse;' align='left'>"
			                + "<tr width='80%' align='center' style='border:2px solid black'>"
			                + "<th><b>SubOrder Id</b></th>"
			                + "<th><b>Distributor Name</b></th>"
			                + "<th><b>Product Name</b></th>"
			                + "<th><b>Ordered Qty</b></th>"
			                + "<th><b>Product Cost</b></th>"
			                + "</tr>";

					for(int i=0; i<orderProductDetails.size(); i++) {
						final int index = i;
						
						// Fetch product details
						Future<ProductDetails> productDetails = executor.submit(() -> productServiceDAO.getProductDetails(orderProductDetails.get(index).getOtsProductId()));
						
						//Fetching adminId from Product table
		                adminId = productDetails.get().getCreatedUser();
		        		
						//To minus product stock when order gets inserted 
						AddProductStockBORequest addProductStockBORequest = new AddProductStockBORequest();
						AddProductStock addProductStock = new AddProductStock();
						//setting request for update product stock
						addProductStock.setProductId(orderProductDetails.get(i).getOtsProductId());
						addProductStock.setUsersId(orderProductDetails.get(i).getDistributorId());
						addProductStock.setProductStockQty(orderProductDetails.get(i).getOtsOrderedQty());
						addProductStockBORequest.setRequestData(addProductStock);
						//To update product stock quantity
						Future<GetProductBOStockResponse> getProductStock = executor.submit(() -> productStockDao.updateProductStockQuantity(addProductStockBORequest));
						
						executor.submit(() ->{
							//To send notification mail to distributor when product stock quantity is <= 10
							try {
								if(Integer.parseInt(getProductStock.get().getStockQuantity()) <= 10) {
									//Creating message content
									String distMsg="<p>Hi,<br><br>" + 
											"Your Product "+orderProductDetails.get(index).getProductName()+" stock is less than 10. <br>"+ 
											"Please add the stock to get uninterrupted orders. <br>"+
											"Thanks And Regards,<br>" + 
											companyName+" Support Team </p>";
									//sending mail for distributor for receiving order
									emailUtil.sendDistributermail(orderProductDetails.get(index).getDistributorEmailId(), "", "Add Product Stock", distMsg);
								}
							
								//To send notification mail to Distributor when order gets inserted
								//Creating message content
								String distMsg="<p>Hi,<br><br>" + 
										"You have received a New Order placed by " + otsOrderDetails.get(0).getCustomerName()+"<br>"+
										"Please Approve Order No: "+otsOrderDetails.get(0).getOrderNumber()+" with SubOrder No: "+orderProductDetails.get(index).getOtsSubOrderId()+"<br><br>" +
										"Please Login to know the Order details.<br>"+
										"Thanks And Regards,<br>" + 
										companyName+" Support Team </p>";
								//sending mail for distributor for receiving order
								emailUtil.sendDistributermail(orderProductDetails.get(index).getDistributorEmailId(), "", "New Order Received", distMsg);
							}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
							}
						});
						
						//To set Data's into table 
						table = table+"<tr width='80%' align='center' style='border:2px solid black'>"
					    		+ "<td>" + orderProductDetails.get(i).getOtsSubOrderId() + "</td>"
				                + "<td>" + orderProductDetails.get(i).getDistributorFirstName()+" "+orderProductDetails.get(i).getDistributorLastName()+ "</td>"
				    			+ "<td>" + orderProductDetails.get(i).getProductName()+"</td>"
				    			+ "<td>" + orderProductDetails.get(i).getOtsOrderedQty()+"</td>"
				    			+ "<td>" + orderProductDetails.get(i).getOtsOrderProductCost()+"</td>"
				    			+"</tr>";	
					}
					table = table+"</table>";
					
					try {
			        	//Clickable Link for customer page
						String custPageLink = "<a href= '"+customerPageLoginLink+"'>Login</a> </p>";
						
						//Creating message content
						String custMsg="<p>Hi "+otsOrderDetails.get(0).getCustomerName()+", <br><br>" + 
								"Order Successfully Placed.<br>"+
								"We are pleased to confirm your Order No: "+otsOrderDetails.get(0).getOrderNumber()+"<br>" +
								"Please Login to know the Order details. Click here to "+custPageLink+"</br>"+
								"Thank you for shopping with "+companyName+".</br></br>"+
								"Thanks And Regards,<br>" + 
								companyName+" Support Team</p>";
						//sending mail for Customer for inserting order
						executor.submit(() -> emailUtil.sendCustomermail(otsOrderDetails.get(0).getCustomerEmailId(), "", "Order Confirmation", custMsg));
					    
					    //Creating message content
						String adminMsg="<p>Hi,<br>" + 
								"Order Id: "+otsOrderDetails.get(0).getOrderId()+" placed by Customer Id: "+otsOrderDetails.get(0).getCustomerId()+" , "+otsOrderDetails.get(0).getCustomerName()+
								" with order Amount Rs."+otsOrderDetails.get(0).getOrderCost()+"<br>"+
								"Order Details are mentioned below. <br>"+table+"<br><br>"+
								"Thanks And Regards,<br>" + 
								companyName+" Support Team </p>";
						UserAccounts adminDetails =  useraccountsDAO.getUseraccountDetail(adminId);
						
						//sending mail order details for Admin
						executor.submit(() -> emailUtil.sendAdminMail(adminDetails.getEmail(), "", "Order Placed By Customer", adminMsg));

					}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
					}
					
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("ERROR IN INSERTING DATA IN DB"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);}
		catch (Throwable e) {
			e.printStackTrace();
			logger.error("ERROR IN INSERTING DATA IN DB"+e.getMessage());
			throw new BusinessException(e, ErrorEnumeration.ORDER_CLOSE);
		}
		return orderDetails;
	}
	
	@Override
	public OrderProductBOResponse getOrdersByStatus(String orderStatus) {
		try {
			OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
			// getting order details by passing the distributor id
			List<OrderDetails> OrderDetailsList = orderServiceDAO.getOrdersByStatus(orderStatus);
			if(OrderDetailsList.size() ==0) {
				return null;
			}else {
				List<OrderDetailsAndProductDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();
				//getting order product details
				for (int i = 0; i <OrderDetailsList.size() ; i++)
				{
					System.out.println("order id"+OrderDetailsList.get(i).getOrderId());
					List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getProductListByOrderId(OrderDetailsList.get(i).getOrderId());
					GetOrderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(OrderDetailsList.get(i),OrderProductDetailsList));
				}
				orderProductBOResponse.setOrderList(GetOrderDetailsAndProductDetails);
				return orderProductBOResponse;
			}
		}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}

	@Transactional
	@Override
	public String generateOrderInvoicePdf(String orderId,String customerId) {
		try {
			List<OrderDetails> orderList = orderServiceDAO.getOrderDetailsForOrderId(orderId);
//			List<UserDetails> custDetails = userServiceDAO.getUserIdUsers(customerId);
			List<List<String>> distDetails = getDistributorForOrderInvoice(orderId);
			List<List<String>> orderDetails = getOrderDetailsForInvoice(orderId);
			
			//To generate order Invoice pdf
			byte[] pdfPath = InvoicePdf.generateOrderInvoiceCopy(orderList, distDetails, orderDetails);
			
			//To convert pdfPath(String) to byte[]
//			byte[] encodedBytes = Base64.getEncoder().encode(pdfPath.getBytes());
			
			//To encode byte[] to String
//			String encodedString = Base64.getEncoder().encodeToString(encodedBytes);
			String encodedString = Base64.getEncoder().encodeToString(pdfPath);
			System.out.println(encodedString);
			//To add encoded Invoice path into order table(DB)
			String addInvoice = orderServiceDAO.addCustomerOrderInvoiceToDB(orderId,encodedString);
			
			return addInvoice;
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
	public String generateBillOfSupplyPdf(AssignOrderToEmployeeRequest assignOrderToEmployeeRequest) {
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			List<DistributorCompanyDetails> distcompanyDetailsList = distributorCompanyDetailsDAO.getDistributorCompanyDetails(assignOrderToEmployeeRequest.getRequest().getDistributorId());
			List<List<String>> orderDetails = getOrderDetailsForBillOfSupply(assignOrderToEmployeeRequest.getRequest().getOrderId(),assignOrderToEmployeeRequest.getRequest().getDistributorId());
			List<OrderProductDetails> orderProductList = orderProductDao.getProductListByOrderId(assignOrderToEmployeeRequest.getRequest().getOrderId());
			
			//To generate Bill OF Supply pdf
			byte[] pdfPath = BillOfSupplyPdf.generateBillOfSupply(distcompanyDetailsList, orderDetails,orderProductList.get(0).getOtsOrderNumber());
			
			String encodedString = Base64.getEncoder().encodeToString(pdfPath);
			System.out.println(encodedString);
			assignOrderToEmployeeRequest.getRequest().setBillOfSupply(encodedString);
			
			//To add encoded Bill Of Supply path into orderProduct table(DB)
			String addBOS = orderProductDao.addBillOfSupplyToDB(assignOrderToEmployeeRequest);
			
            executor.submit(() -> {
				  //To send BOS pdf to ortusolis finance email as attachment
				  try {
				      //To Add PDF Attachment file to mail
				      String pdfName = "BOS_"+orderProductList.get(0).getOtsSubOrderId()+".pdf";	//Recreating pdf with same name
				
				      //Creating message content
				      String financeMsg="Hi,\r\n\r\n" + 
				              "Distributor "+distcompanyDetailsList.get(0).getDistributorFirstName()+" "+distcompanyDetailsList.get(0).getDistributorLastName()+" has accepted sub order "+orderProductList.get(0).getOtsSubOrderId()+" and Bill Of Supply for this order is sent to you.\r\n" + 
				              "Please Download the Attached Bill OF Supply Pdf.\r\n\r\n" +
				              "Thanks And Regards,\r\n" + 
				              "ORTUSOLIS TECHNOLOGIES PVT LTD";
				
				      //To send Email to OTS Finance team for Successful Registration with attached Invoice
				      emailUtil.sendFinanceMailWithAttachment("", "Order Accepted by Distributor", financeMsg,pdfPath,pdfName);
				  }
				  catch (Throwable t) {		//added try catch block to pass the exception & continue processing
				  }
            });
				
			return addBOS;
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
	
	@Override	//To get order product details in Assigned status for Gst unavailable distributors
	public List<OrderProductDetails> getOrderByStatusOfUnregisteredDistributors(String SubOrderStatus) {
		try {
			List<OrderProductDetails> orderProductDetails = new ArrayList<OrderProductDetails>();
			List<OrderProductDetails> orderProductDetailsList = new ArrayList<OrderProductDetails>();
			// getting order details by passing the distributor id
			List<DistributorCompanyDetails> distributorDetails = distributorCompanyDetailsDAO.getGSTUnRegisteredDistributor();
			if(distributorDetails.size() == 0) {
				return null;
			}else {
				//getting order product details
				for (int i = 0; i <distributorDetails.size() ; i++)
				{
					System.out.println("distributor id"+distributorDetails.get(i).getDistributorId());
					orderProductDetails = orderProductDao.getOrderForDistributorAndStatus(distributorDetails.get(i).getDistributorId(),SubOrderStatus);
					//To add data from one list to previous list
					if(orderProductDetails.size() != 0 ) {
						orderProductDetailsList.addAll(orderProductDetails);
					}
				}
				return orderProductDetailsList;
			}
		}catch(Exception e){
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		} catch (Throwable e) {
			throw new BusinessException(e, ErrorEnumeration.ERROR_IN_ORDER_INSERTION);
		}
	}
	
	@Override 
	public String updateRRCOrderStatus(UpdateRRCStatusRequest updateRRCStatusRequest) {
		String Response;
		boolean subOrder;
		boolean assignOrder;
		try {
			Response = orderProductDao.updateRRCOrderStatus(updateRRCStatusRequest);
			
			//To send Email to Customer for Return, Replace, Cancel
			//To get Order Details 
			OrderDetails orderDetails = orderServiceDAO.getOrderDetailsByOrderId(updateRRCStatusRequest.getRequest().getOrderId());
			//To get Customer Details
			UserDetails customer = userServiceDAO.getUserDetails(orderDetails.getCustomerId());
			//To get Order Product Details 
			OrderProductDetails orderproduct = orderProductDao.getOrderProductByOrderIdProductId(updateRRCStatusRequest.getRequest().getOrderId(),updateRRCStatusRequest.getRequest().getProductId());
			//To get Distributor Details
			UserDetails distributor = userServiceDAO.getUserDetails(orderproduct.getDistributorId());
			String custNotification = null;
			String distNotification = null;
			try {
				//Message content for Return 
				if(updateRRCStatusRequest.getRequest().getRRCOrderStatus().equalsIgnoreCase("Return")) {
					custNotification ="<p>Hi "+customer.getFirstName()+" "+customer.getLastName()+", <br><br>" + 
							"We're writing to let you know that your Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" for Product "+orderproduct.getProductName()+" has been placed for Return. <br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendCustomermail(customer.getEmailId(), "", "Order Initiated For Return",custNotification);
					
					distNotification ="<p>Hi,<br><br>" + 
							"SubOrder ID: "+orderproduct.getOtsSubOrderId()+" for Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" has been Intitiated for Return by Customer.<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendDistributermail(distributor.getEmailId(), "", "Order Initiated For Return",distNotification);
					return Response;
				}
				
				//Message content for Replacement
				if(updateRRCStatusRequest.getRequest().getRRCOrderStatus().equalsIgnoreCase("Replacement")) {
					custNotification ="<p>Hi "+customer.getFirstName()+" "+customer.getLastName()+", <br><br>" + 
							"We're writing to let you know that your Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" for Product "+orderproduct.getProductName()+" has been placed for Replacement. <br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendCustomermail(customer.getEmailId(), "", "Order Initiated For Replacement",custNotification);
					
					distNotification ="<p>Hi,<br><br>" + 
							"SubOrder ID: "+orderproduct.getOtsSubOrderId()+" for Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" has been Intitiated for Replacement by Customer.<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendDistributermail(distributor.getEmailId(), "", "Order Initiated For Replacement",distNotification);
					return Response;
				}
				
				//Message content for Return Close
				if(updateRRCStatusRequest.getRequest().getRRCOrderStatus().equalsIgnoreCase("ReturnClose")) {
					custNotification ="<p>Hi "+customer.getFirstName()+" "+customer.getLastName()+", <br><br>" + 
							orderproduct.getProductName()+" from Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" has been Returned and the order amount is refunded. <br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendCustomermail(customer.getEmailId(), "", "Return Order Closed",custNotification);
					
					distNotification ="<p>Hi,<br><br>" + 
							"SubOrder ID: "+orderproduct.getOtsSubOrderId()+" for Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" has been Intitiated for Return by Customer.<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendDistributermail(distributor.getEmailId(), "", "Return Order Closed",distNotification);
					return Response;
				}
				
				//Message content for Replace Close
				if(updateRRCStatusRequest.getRequest().getRRCOrderStatus().equalsIgnoreCase("ReplaceClose")) {
					custNotification ="<p>Hi "+customer.getFirstName()+" "+customer.getLastName()+", <br><br>" + 
							orderproduct.getProductName()+" from Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" has been Replaced as requested. <br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendCustomermail(customer.getEmailId(), "", "Replacement Order Closed",custNotification);
					
					distNotification ="<p>Hi,\r\n\r\n" + 
							"SubOrder ID: "+orderproduct.getOtsSubOrderId()+" for Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" has been Closed for Replacement request.<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendDistributermail(distributor.getEmailId(), "", "Replacement Order Closed",distNotification);
					return Response;
				}
			}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
			}
		
			//When customer cancels order need to update rrcOrderStatus to Cancel, orderProductStatus to Cancel &  main orderStatus to Cancel only when all the subOrder are Cancelled
			if(updateRRCStatusRequest.getRequest().getRRCOrderStatus().equalsIgnoreCase("Cancel")) {
				AssignOrderToEmployeeRequest assignOrderToEmployeeRequest = new AssignOrderToEmployeeRequest();
				AssignOrderToEmployeeModel assignOrderToEmployeeModel = new AssignOrderToEmployeeModel();
				
				//Setting request for cancelMainAndSubOrder 
				assignOrderToEmployeeModel.setOrderId(orderproduct.getOtsOrderId());
				assignOrderToEmployeeModel.setDistributorId(orderproduct.getDistributorId());
				assignOrderToEmployeeModel.setProductId(orderproduct.getOtsProductId());
				assignOrderToEmployeeModel.setCustomerId(orderDetails.getCustomerId());
				assignOrderToEmployeeModel.setOrderStatus("Cancel");
				assignOrderToEmployeeRequest.setRequest(assignOrderToEmployeeModel);
				
				//To update order status to Cancel in ots_order_product table
				orderProductDao.updateSubOrder(assignOrderToEmployeeRequest);
				
				//To add product stock when order gets cancelled 
				AddProductStockBORequest addProductStockBORequest = new AddProductStockBORequest();
				AddProductStock addProductStock = new AddProductStock();
				//setting request for add product stock
				addProductStock.setProductId(orderproduct.getOtsProductId());
				addProductStock.setUsersId(orderproduct.getDistributorId());
				addProductStock.setProductStockQty(orderproduct.getOtsOrderedQty());
				addProductStockBORequest.setRequestData(addProductStock);
				//To add product stock quantity with old quantity
				productStockDao.addProductStock(addProductStockBORequest);
				
				//To check whether any one of the order is Assigned or Not, if its Assigned then main order status will be set to Assigned
				subOrder = checkForOrderCancel(updateRRCStatusRequest.getRequest().getOrderId());
				if(subOrder == true) {
					orderServiceDAO.UpdateOrderStatus(updateRRCStatusRequest.getRequest().getOrderId(),"Cancel");
				}
				else {
					assignOrder = checkForOrderAssigned(updateRRCStatusRequest.getRequest().getOrderId());
					if(assignOrder == true) {
						orderServiceDAO.UpdateOrderStatus(updateRRCStatusRequest.getRequest().getOrderId(),"Assigned");
					}
				}
				
				try {
					custNotification ="<p>Hi "+customer.getFirstName()+" "+customer.getLastName()+", <br><br>" + 
							"We're writing to let you know that your Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" for Product "+orderproduct.getProductName()+" has been Cancelled. <br>"+
							"Continue shopping with "+companyName+".<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendCustomermail(customer.getEmailId(), "", "Order Cancellation",custNotification);
					
					distNotification ="<p>Hi,<br><br>" + 
							"SubOrder ID: "+orderproduct.getOtsSubOrderId()+" for Order Number: "+orderNoFormat+updateRRCStatusRequest.getRequest().getOrderId()+" has been Cancelled by Customer.<br><br>"+
							"Thanks And Regards,<br>" + 
							companyName+" Support Team </p>";
					emailUtil.sendDistributermail(distributor.getEmailId(), "", "Order Cancelled",distNotification);
				}catch (Throwable t) {		//added try catch block to pass the exception & continue processing
				}
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return Response;
	}
	
	@Override	//To get settlement details for Distributor based on weeks
	public GetDistributorSettlementResponse getDistributorWeeklySettlemetDetails(GetDistributorSettlementRequest getDistributorSettlementRequest) {
		try {
			GetDistributorSettlementResponse getDistributorSettlementResponse = new GetDistributorSettlementResponse();
			GetDetailsForExcelRequest getDetailsForExcelRequest = new GetDetailsForExcelRequest();
			DistributorDetailsForSettlement distributorDetailsForSettlement = new DistributorDetailsForSettlement();
			
			Double totalSettlemetAmount = 0.0;
			Double totalPendingAmount = 0.0;
			Double totalTenPercTrans = 0.0;
			Double totalEighteenPercGstOnTenPercTrans = 0.0;
			Double totalOnePercTdsOnProductPrice = 0.0;
			Double totalLLPPayablePrice = 0.0;
			Double totalPvtPayablePrice = 0.0;
			Double totalEighteenPercGstOnProductPrice = 0.0;
			Double totalOnePercTds = 0.0;
			Double totalThreePercProfitPvt = 0.0;
			
			//To get distributors payment details to insert in excel sheet
			List<DistributorPaymentDetails> distributorPaymentDetails = distributorPaymentDetailsDAO.getDistributorPaymentDetails(getDistributorSettlementRequest.getRequest().getDistributorId());
			//setting distributor payment details in distributor details object because we are passing this object to the excel sheet
			distributorDetailsForSettlement.setDistributorPaymentDetails(distributorPaymentDetails);
			
			// getting weekly order product details for distributor
			List<OrderProductDetails> orderProductDetailsList = orderProductDao.getDistributorWeeklySettlemetDetails(getDistributorSettlementRequest);
			if(orderProductDetailsList.size() == 0) {
				return null;
			}else {
				//To get distributors company details to insert in excel sheet
				//Payment settlement calculation for GST registered Distributor
				if(getDistributorSettlementRequest.getRequest().getGstAvailability() == true)
				{
					//For setting Excel title 
					String excelTitle = "Distributor Settlement Details from Date "+getDistributorSettlementRequest.getRequest().getFromDate()+" to "+getDistributorSettlementRequest.getRequest().getToDate()+" for Registered Distributor";
					
//					String fromToDate = getDistributorSettlementRequest.getRequest().getFromDate()+"_"+getDistributorSettlementRequest.getRequest().getToDate();
					String fromToDate = "Week";
					distributorDetailsForSettlement.setFromToDate(fromToDate);	
					
					//setting Excel title detail in distributor details object because we are passing this object to the excel sheet
					distributorDetailsForSettlement.setExcelTitle(excelTitle);
					
					//To initialize List size same as that of response data size
					List<RegisteredDistWeeklySettlementDetailsForExcel> registeredDistWeeklySettlementDetailsForExcel = Stream.generate(RegisteredDistWeeklySettlementDetailsForExcel::new)
			                .limit(orderProductDetailsList.size())
			                .collect(Collectors.toList());
					//calculating Total Settlement Amount
					for (int i = 0; i <orderProductDetailsList.size() ; i++)
					{
						System.out.println("product price without gst = "+orderProductDetailsList.get(i).getProductPriceWithoutGst());
						//Calculating 10% transaction on product price (order product price without GST)
						Double tenPercTranscation = Math.round((Double.parseDouble(orderProductDetailsList.get(i).getProductSellerPrice()) * (10/100.0)) * Math.pow(10, 3))/ Math.pow(10, 3);
						orderProductDetailsList.get(i).setTenPercTranscation(tenPercTranscation);
						System.out.println("10% trans = "+tenPercTranscation);
						
						//Calculating 18% GST on 10% transaction amount
						Double EighteenPercGstOnTenPercTranscation = Math.round((tenPercTranscation * (18/100.0)) * Math.pow(10, 3))/ Math.pow(10, 3);
						orderProductDetailsList.get(i).setEighteenPercGstOnTenPercTranscation(EighteenPercGstOnTenPercTranscation);
						System.out.println("18% on 10% trans = "+EighteenPercGstOnTenPercTranscation);
						
						//Calculating 1% TDS on product price (order product price without GST)
						Double onePercTdsOnProductPrice = Math.round((Double.parseDouble(orderProductDetailsList.get(i).getProductSellerPrice()) * (1/100.0)) * Math.pow(10, 3))/ Math.pow(10, 3);
						orderProductDetailsList.get(i).setOnePerTdsOnProductPrice(onePercTdsOnProductPrice);
						System.out.println("1% Tds on product price = "+onePercTdsOnProductPrice);
						
						//Subtracting 10% transaction amount from order product cost
						Double minusTenPercTrans =  Math.round((Double.parseDouble(orderProductDetailsList.get(i).getOtsOrderProductCost()) - tenPercTranscation) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("minus 10% trans = "+minusTenPercTrans);
						
						//Subtracting 18% GST amount from above calculated price
						Double minusEighteenPercGst = Math.round((minusTenPercTrans - EighteenPercGstOnTenPercTranscation) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("minus 18% Gst = "+minusEighteenPercGst);
						
						//Subtracting 1% TDS amount from above calculated price
						Double minusOnePercTds =  Math.round((minusEighteenPercGst - onePercTdsOnProductPrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("minus 1% Tds = "+minusOnePercTds);
						
						Double distributorPayablePrice = minusOnePercTds;
						System.out.println("distributor payable price = "+distributorPayablePrice);
						
						//Calculating Pvt Ltd Payable price by adding 10% transaction, 18% on 10% trns, 1% TDS on product price
						Double pvtPayablePrice = Math.round((tenPercTranscation + EighteenPercGstOnTenPercTranscation + onePercTdsOnProductPrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("pvtPayablePrice = "+pvtPayablePrice);
					
						//Summing total distributorPayablePrice
						totalSettlemetAmount = Math.round((totalSettlemetAmount + distributorPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total settlement amount = "+totalSettlemetAmount);
						
						//Summing total PvtPayablePrice
						totalPvtPayablePrice = Math.round((totalPvtPayablePrice + pvtPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total PvtPayablePrice = "+totalPvtPayablePrice);
						
						//Summing total tenPercTranscation
						totalTenPercTrans = Math.round((totalTenPercTrans + tenPercTranscation) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total tenPercTranscation = "+totalTenPercTrans);
						
						//Summing total EighteenPercGstOnTenPercTranscation
						totalEighteenPercGstOnTenPercTrans = Math.round((totalEighteenPercGstOnTenPercTrans + EighteenPercGstOnTenPercTranscation) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total EighteenPercGstOnTenPercTranscation = "+totalEighteenPercGstOnTenPercTrans);
						
						//Summing total onePercTdsOnProductPrice
						totalOnePercTdsOnProductPrice = Math.round((totalOnePercTdsOnProductPrice + onePercTdsOnProductPrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total onePercTdsOnProductPrice = "+totalOnePercTdsOnProductPrice);
						
						System.out.println("i value = "+i);
						registeredDistWeeklySettlementDetailsForExcel.get(i).setSlno(String.valueOf(i+1));
						System.out.println("order number "+orderProductDetailsList.get(i).getOtsOrderNumber());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setOrderNumber(orderProductDetailsList.get(i).getOtsOrderNumber());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setSubOrderNumber(orderProductDetailsList.get(i).getOtsSubOrderId());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setDistributorName(orderProductDetailsList.get(i).getDistributorFirstName()+" "+orderProductDetailsList.get(i).getDistributorLastName());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setProductName(orderProductDetailsList.get(i).getProductName());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setOrderedQty(orderProductDetailsList.get(i).getOtsOrderedQty());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setOrderDeliveredDate(orderProductDetailsList.get(i).getSubOrderDeliveredDate());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setOrderProductPrice(orderProductDetailsList.get(i).getOtsOrderProductCost());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setProductPrice(orderProductDetailsList.get(i).getProductPriceWithoutGst());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setSellerPrice(orderProductDetailsList.get(i).getProductSellerPrice());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setTenPercTranscation(tenPercTranscation.toString());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setEighteenPercGstOnTenPercTranscation(EighteenPercGstOnTenPercTranscation.toString());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setOnePercTdsOnProductPrice(onePercTdsOnProductPrice.toString());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setDistributorPayablePrice(distributorPayablePrice.toString());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setPvtPayablePrice(pvtPayablePrice.toString());
						registeredDistWeeklySettlementDetailsForExcel.get(i).setPaymentSettlementStatus(orderProductDetailsList.get(i).getOtsSettlementStatus());
						
						//To calculate pending amount if Payment Settlement Status is Pending
						if(orderProductDetailsList.get(i).getOtsSettlementStatus().equalsIgnoreCase("Pending")) {
							//Summing total distributorPayablePrice only for Pending status
							totalPendingAmount = Math.round((totalPendingAmount + distributorPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
							System.out.println("total pending amount = "+totalPendingAmount);
						}
						getDetailsForExcelRequest.setRegisteredDist(registeredDistWeeklySettlementDetailsForExcel);
						System.out.println("after set response = "+getDetailsForExcelRequest);
					}
					
					System.out.println("final settlement amount = "+totalSettlemetAmount);
					System.out.println("final pending amount = "+totalPendingAmount);
					getDistributorSettlementResponse.setOrderProductDetails(orderProductDetailsList);
					getDistributorSettlementResponse.setTotalOrderCount(orderProductDetailsList.size());
					getDistributorSettlementResponse.setTotalSettlementAmount(totalSettlemetAmount);
					getDistributorSettlementResponse.setTotalPendingAmount(totalPendingAmount);
//					getDistributorSettlementResponse.setTotalTenPercTrans(totalTenPercTrans);
//					getDistributorSettlementResponse.setTotalOnePercTdsOnProductPrice(totalOnePercTdsOnProductPrice);
//					getDistributorSettlementResponse.setTotalEighteenPercGstOnTenPercTrans(totalEighteenPercGstOnTenPercTrans);
//					getDistributorSettlementResponse.setTotalPvtPayablePrice(totalPvtPayablePrice);
//					getDistributorSettlementResponse.setTotalOnePercTds(totalOnePercTds);

					getDetailsForExcelRequest.setTotalPendingAmount(totalPendingAmount);
					getDetailsForExcelRequest.setTotalTenPercTrans(totalTenPercTrans);
					getDetailsForExcelRequest.setTotalOnePercTdsOnProductPrice(totalOnePercTdsOnProductPrice);
					getDetailsForExcelRequest.setTotalEighteenPercGstOnTenPercTrans(totalEighteenPercGstOnTenPercTrans);
					getDetailsForExcelRequest.setTotalPvtPayablePrice(totalPvtPayablePrice);
					getDetailsForExcelRequest.setTotalOnePercTds(totalOnePercTds);
					
//					getDistributorSettlementResponse.setGetDetailsForExcelRequest(getDetailsForExcelRequest);
					
				}else {
					//Payment settlement calculation for GST unregistered Distributor
					
					//For setting Excel title 
					String excelTitle = "Distributor Settlement Details from Date "+getDistributorSettlementRequest.getRequest().getFromDate()+" to "+getDistributorSettlementRequest.getRequest().getToDate()+" for UnRegistered Distributor";
					
//					String fromToDate = getDistributorSettlementRequest.getRequest().getFromDate()+"_"+getDistributorSettlementRequest.getRequest().getToDate();
					String fromToDate = "Week";
					distributorDetailsForSettlement.setFromToDate(fromToDate);	
					
					//setting Excel title detail in distributor details object because we are passing this object to the excel sheet
					distributorDetailsForSettlement.setExcelTitle(excelTitle);
					
					//To initialize List size same as that of response data size
					List<UnRegisteredDistWeeklySettlementDetailsForExcel> unRegisteredDistWeeklySettlementDetailsForExcel = Stream.generate(UnRegisteredDistWeeklySettlementDetailsForExcel::new)
			                .limit(orderProductDetailsList.size())
			                .collect(Collectors.toList());
					//calculating Total Settlement Amount
					for (int i = 0; i <orderProductDetailsList.size() ; i++)
					{
						System.out.println("product price without gst = "+orderProductDetailsList.get(i).getProductPriceWithoutGst());
						//Calculating 3% profit for Pvt Ltd on product price (order product price without GST)
						Double threePercProfit = Math.round((Double.parseDouble(orderProductDetailsList.get(i).getProductPriceWithoutGst()) * (3/100.0)) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("3% profit = "+threePercProfit);
						
						//Calculating 18% GST on product price (order product price without GST)
						Double EighteenPercGstOnProductPrice = Math.round((Double.parseDouble(orderProductDetailsList.get(i).getProductPriceWithoutGst()) * (18/100.0)) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("18% on product price = "+EighteenPercGstOnProductPrice);
						
						//Subtracting 3% profit from product price (order product price without GST)
						Double minusThreePercProfit =  Math.round((Double.parseDouble(orderProductDetailsList.get(i).getProductPriceWithoutGst()) - threePercProfit) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("minus 10% trans = "+minusThreePercProfit);
						
						//Calculating 1% TDS on product price (order product price without GST)
						Double onePercTds = minusThreePercProfit * (1/100.0);
						System.out.println("1% Tds on product price = "+onePercTds);
						
						//Subtracting 1% TDS amount from above calculated price
						Double minusOnePercTds =  Math.round((minusThreePercProfit - onePercTds) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("minus 1% Tds = "+minusOnePercTds);
						
						//Adding 18% GST amount for above calculated price
						Double addEighteenPercGst = Math.round((minusOnePercTds + EighteenPercGstOnProductPrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("add 18% Gst = "+addEighteenPercGst);
						
						Double llpPayablePrice = addEighteenPercGst;
						System.out.println("llp payable price = "+llpPayablePrice);
						
						//Calculating Distributor payable price by subtracting LLP Payable price from Order Cost
						Double distributorPayablePrice = Math.round((Double.parseDouble(orderProductDetailsList.get(i).getProductSellerPrice())) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("distributorPayablePrice = "+distributorPayablePrice);
						
						//Calculating Pvt Ltd Payable price by adding 3% profit amount and 1% TDS
						Double pvtPayablePrice = threePercProfit + onePercTds;
						System.out.println("pvtPayablePrice = "+pvtPayablePrice);
					
						//Summing total settlement Amount
						totalSettlemetAmount = Math.round((totalSettlemetAmount + distributorPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total settlement amount = "+totalSettlemetAmount);
						
						//Summing total LLPPayablePrice
						totalLLPPayablePrice = Math.round((totalLLPPayablePrice + llpPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total LLP Payable Price = "+totalLLPPayablePrice);
						
						//Summing total threePercProfit
						totalThreePercProfitPvt = Math.round((totalThreePercProfitPvt + threePercProfit) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total ThreePercProfitPvt = "+totalThreePercProfitPvt);
						
						//Summing total onePercTds
						totalOnePercTds = Math.round((totalOnePercTds + onePercTds) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total 1% Tds = "+totalOnePercTds);
						
						//Summing total PvtPayablePrice
						totalPvtPayablePrice = Math.round((totalPvtPayablePrice + pvtPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total PvtPayablePrice = "+totalPvtPayablePrice);
						
						//Summing total EighteenPercGstOnProductPrice
						totalEighteenPercGstOnProductPrice = Math.round((totalEighteenPercGstOnProductPrice + EighteenPercGstOnProductPrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total EighteenPercGstOnProductPrice = "+totalEighteenPercGstOnProductPrice);

						System.out.println("i value = "+i);
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setSlno(String.valueOf(i+1));
						System.out.println("order number "+orderProductDetailsList.get(i).getOtsOrderNumber());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setOrderNumber(orderProductDetailsList.get(i).getOtsOrderNumber());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setSubOrderNumber(orderProductDetailsList.get(i).getOtsSubOrderId());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setDistributorName(orderProductDetailsList.get(i).getDistributorFirstName()+" "+orderProductDetailsList.get(i).getDistributorLastName());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setProductName(orderProductDetailsList.get(i).getProductName());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setOrderedQty(orderProductDetailsList.get(i).getOtsOrderedQty());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setOrderDeliveredDate(orderProductDetailsList.get(i).getSubOrderDeliveredDate());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setOrderProductPrice(orderProductDetailsList.get(i).getOtsOrderProductCost());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setProductPrice(orderProductDetailsList.get(i).getProductPriceWithoutGst());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setSellerPrice(orderProductDetailsList.get(i).getProductSellerPrice());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setThreePercProfit(threePercProfit.toString());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setEighteenPercGst(EighteenPercGstOnProductPrice.toString());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setOnePercTds(onePercTds.toString());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setLlpPayablePrice(llpPayablePrice.toString());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setDistributorPayablePrice(distributorPayablePrice.toString());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setPvtPayablePrice(pvtPayablePrice.toString());
						unRegisteredDistWeeklySettlementDetailsForExcel.get(i).setPaymentSettlementStatus(orderProductDetailsList.get(i).getOtsSettlementStatus());
						
						//To calculate pending amount if Payment Settlement Status is Pending
						if(orderProductDetailsList.get(i).getOtsSettlementStatus().equalsIgnoreCase("Pending")) {
							//Summing total distributorPayablePrice only for Pending status
							totalPendingAmount = Math.round((totalPendingAmount + distributorPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
							System.out.println("total pending amount = "+totalPendingAmount);
						}
					}
					getDetailsForExcelRequest.setUnregisteredDist(unRegisteredDistWeeklySettlementDetailsForExcel);
					System.out.println("after set response = "+getDetailsForExcelRequest);
				}
				
				System.out.println("final settlement amount = "+totalSettlemetAmount);
				System.out.println("final pending amount = "+totalPendingAmount);
				getDistributorSettlementResponse.setOrderProductDetails(orderProductDetailsList);
				getDistributorSettlementResponse.setTotalOrderCount(orderProductDetailsList.size());
				getDistributorSettlementResponse.setTotalSettlementAmount(totalSettlemetAmount);
				getDistributorSettlementResponse.setTotalPendingAmount(totalPendingAmount);
//				getDistributorSettlementResponse.setTotalOnePercTdsOnProductPrice(totalOnePercTdsOnProductPrice);
//				getDistributorSettlementResponse.setTotalLLPPayablePrice(totalLLPPayablePrice);
//				getDistributorSettlementResponse.setTotalPvtPayablePrice(totalPvtPayablePrice);
//				getDistributorSettlementResponse.setTotalEighteenPercGstOnProductPrice(totalEighteenPercGstOnProductPrice);
//				getDistributorSettlementResponse.setTotalThreePercProfitPvt(totalThreePercProfitPvt);
				
				getDetailsForExcelRequest.setTotalPendingAmount(totalPendingAmount);
				getDetailsForExcelRequest.setTotalOnePercTds(totalOnePercTds);
				getDetailsForExcelRequest.setTotalLLPPayablePrice(totalLLPPayablePrice);
				getDetailsForExcelRequest.setTotalPvtPayablePrice(totalPvtPayablePrice);
				getDetailsForExcelRequest.setTotalEighteenPercGstOnProductPrice(totalEighteenPercGstOnProductPrice);
				getDetailsForExcelRequest.setTotalThreePercProfitPvt(totalThreePercProfitPvt);
				
//				getDistributorSettlementResponse.setGetDetailsForExcelRequest(getDetailsForExcelRequest);
				
				if(getDistributorSettlementRequest.getRequest(). getPdf().equalsIgnoreCase("YES") && orderProductDetailsList.size() != 0)
				{
					//To generate Excel Sheet
					byte[] pdfPath = ExcelGenerator.generateExcel(getDetailsForExcelRequest,distributorDetailsForSettlement);
					
					//To convert byte code to string
					String encodedString = Base64.getEncoder().encodeToString(pdfPath);
					System.out.println(encodedString);
					getDistributorSettlementResponse.setExcelFile(encodedString);
				}	
				
				return getDistributorSettlementResponse;
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override	//To get Current week settlement details for Distributor 
	public GetDistributorSettlementResponse getDistributorCurrentWeekSettlemetDetails(String distributorId) {
		try {
			GetDistributorSettlementResponse getDistributorSettlementResponse = new GetDistributorSettlementResponse();
			Double totalSettlemetAmount = 0.0;
			// getting order product details for distributor based on current week
			List<OrderProductDetails> orderProductDetailsList = orderProductDao.getDistributorCurrentWeekSettlemetDetails(distributorId);
			if(orderProductDetailsList.size() == 0) {
				return null;
			}else {
				//calculating Total Settlement Amount
				for (int i = 0; i <orderProductDetailsList.size() ; i++)
				{
					System.out.println("product price without gst = "+orderProductDetailsList.get(i).getProductPriceWithoutGst());
					//Calculating 10% transaction on product price (order product price without GST)
					Double tenPercTranscation = Double.parseDouble(orderProductDetailsList.get(i).getProductPriceWithoutGst()) * (10/100.0);
					System.out.println("10% trsc = "+tenPercTranscation);
					
					//Calculating 18% GST on 10% transaction amount
					Double EighteenPercGstOnTenPercTranscation = tenPercTranscation * (18/100.0);
					System.out.println("1% on 10% trsc = "+EighteenPercGstOnTenPercTranscation);
					
					//Calculating 1% TDS on product price (order product price without GST)
					Double onePerTdsOnProductPrice = Double.parseDouble(orderProductDetailsList.get(i).getProductPriceWithoutGst()) * (1/100.0);
					System.out.println("1% Tds on product price = "+onePerTdsOnProductPrice);
					
					//Subtracting 10% transaction amount from order product cost
					Double minusTenPercTrans =  Math.round((Double.parseDouble(orderProductDetailsList.get(i).getOtsOrderProductCost()) - tenPercTranscation) * Math.pow(10, 3))/ Math.pow(10, 3);
					System.out.println("minus 10% trans = "+minusTenPercTrans);
					
					//Subtracting 18% GST amount from above calculated price
					Double minusEighteenPercGst = Math.round((minusTenPercTrans - EighteenPercGstOnTenPercTranscation) * Math.pow(10, 3))/ Math.pow(10, 3);
					System.out.println("minus 18% Gst = "+minusEighteenPercGst);
					
					//Subtracting 1% TDS amount from above calculated price
					Double minusOnePercTds =  Math.round((minusEighteenPercGst - onePerTdsOnProductPrice) * Math.pow(10, 3))/ Math.pow(10, 3);
					System.out.println("minus 1% Tds = "+minusOnePercTds);
					
					Double distributorPayablePrice = minusOnePercTds;
					System.out.println("distributor payable price = "+distributorPayablePrice);
				
					//Summing total distributorPayablePrice
					totalSettlemetAmount = Math.round((totalSettlemetAmount + distributorPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
					System.out.println("total settlement amount = "+totalSettlemetAmount);
			
				}
				System.out.println("final settlement amount = "+totalSettlemetAmount);
				getDistributorSettlementResponse.setOrderProductDetails(orderProductDetailsList);
				getDistributorSettlementResponse.setTotalOrderCount(orderProductDetailsList.size());
				getDistributorSettlementResponse.setTotalSettlementAmount(totalSettlemetAmount);
				return getDistributorSettlementResponse;
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace(); 
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override	//To get settlement details for Distributor based on month with year (03 - 2023)
	public GetDistributorSettlementResponse getDistributorMonthlySettlemetDetails(GetMonthlyDistributorSettlementRequest getDistributorSettlementRequest) {
		try {
			GetDistributorSettlementResponse getDistributorSettlementResponse = new GetDistributorSettlementResponse();
			Double totalSettlemetAmount = 0.0;
			Double totalPendingAmount = 0.0;
			// getting weekly order product details for distributor
			List<OrderProductDetails> orderProductDetailsList = orderProductDao.getDistributorMonthlySettlemetDetails(getDistributorSettlementRequest);
			if(orderProductDetailsList.size() == 0) {
				return null;
			}else {
				//calculating Total Settlement Amount
				for (int i = 0; i <orderProductDetailsList.size() ; i++)
				{
					System.out.println("product price without gst = "+orderProductDetailsList.get(i).getProductPriceWithoutGst());
					//Calculating 10% transaction on product price (order product price without GST)
					Double tenPercTranscation = Double.parseDouble(orderProductDetailsList.get(i).getProductPriceWithoutGst()) * (10/100.0);
					System.out.println("10% trsc = "+tenPercTranscation);
					
					//Calculating 18% GST on 10% transaction amount
					Double EighteenPercGstOnTenPercTranscation = tenPercTranscation * (18/100.0);
					System.out.println("1% on 10% trsc = "+EighteenPercGstOnTenPercTranscation);
					
					//Calculating 1% TDS on product price (order product price without GST)
					Double onePerTdsOnProductPrice = Double.parseDouble(orderProductDetailsList.get(i).getProductPriceWithoutGst()) * (1/100.0);
					System.out.println("1% Tds on product price = "+onePerTdsOnProductPrice);
					
					//Subtracting 10% transaction amount from order product cost
					Double minusTenPercTrans =  Math.round((Double.parseDouble(orderProductDetailsList.get(i).getOtsOrderProductCost()) - tenPercTranscation) * Math.pow(10, 3))/ Math.pow(10, 3);
					System.out.println("minus 10% trans = "+minusTenPercTrans);
					
					//Subtracting 18% GST amount from above calculated price
					Double minusEighteenPercGst = Math.round((minusTenPercTrans - EighteenPercGstOnTenPercTranscation) * Math.pow(10, 3))/ Math.pow(10, 3);
					System.out.println("minus 18% Gst = "+minusEighteenPercGst);
					
					//Subtracting 1% TDS amount from above calculated price
					Double minusOnePercTds =  Math.round((minusEighteenPercGst - onePerTdsOnProductPrice) * Math.pow(10, 3))/ Math.pow(10, 3);
					System.out.println("minus 1% Tds = "+minusOnePercTds);
					
					Double distributorPayablePrice = minusOnePercTds;
					System.out.println("distributor payable price = "+distributorPayablePrice);
				
					//Summing total distributorPayablePrice
					totalSettlemetAmount = Math.round((totalSettlemetAmount + distributorPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
					System.out.println("total settlement amount = "+totalSettlemetAmount);
					
					//To calculate pending amount if Payment Settlement Status is Pending
					if(orderProductDetailsList.get(i).getOtsSettlementStatus().equalsIgnoreCase("Pending")) {
						//Summing total distributorPayablePrice only for Pending status
						totalPendingAmount = Math.round((totalPendingAmount + distributorPayablePrice) * Math.pow(10, 3))/ Math.pow(10, 3);
						System.out.println("total pending amount = "+totalPendingAmount);
					}
				}
				System.out.println("final settlement amount = "+totalSettlemetAmount);
				System.out.println("final pending amount = "+totalPendingAmount);
				getDistributorSettlementResponse.setOrderProductDetails(orderProductDetailsList);
				getDistributorSettlementResponse.setTotalOrderCount(orderProductDetailsList.size());
				getDistributorSettlementResponse.setTotalSettlementAmount(totalSettlemetAmount);
				getDistributorSettlementResponse.setTotalPendingAmount(totalPendingAmount);
				return getDistributorSettlementResponse;
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	private OrderProductAndOrderDetails AddOrderProductAndOrderDetailsIntoResponse(OrderDetails orderDetails,OrderProductDetails OrderProductDetails)
	{
		OrderProductAndOrderDetails orderProductAndOrderDetails = new OrderProductAndOrderDetails();
		orderProductAndOrderDetails.setOtsOrderId(OrderProductDetails.getOtsOrderId());
		orderProductAndOrderDetails.setOtsOrderNumber(OrderProductDetails.getOtsOrderNumber());
		orderProductAndOrderDetails.setOtsDeliveredQty(OrderProductDetails.getOtsDeliveredQty());
		orderProductAndOrderDetails.setProductName(OrderProductDetails.getProductName());
		orderProductAndOrderDetails.setOtsOrderProductCost(OrderProductDetails.getOtsOrderProductCost());
		orderProductAndOrderDetails.setOtsOrderedQty(OrderProductDetails.getOtsOrderedQty()); 
		orderProductAndOrderDetails.setOtsOrderProductStatus(OrderProductDetails.getOtsOrderProductStatus());
		orderProductAndOrderDetails.setOtsOrderProductId(OrderProductDetails.getOtsOrderProductId());
		orderProductAndOrderDetails.setOtsProductId(OrderProductDetails.getOtsProductId());
		orderProductAndOrderDetails.setOtsSubOrderId(OrderProductDetails.getOtsSubOrderId());
		orderProductAndOrderDetails.setProductImage(OrderProductDetails.getProductImage());
		orderProductAndOrderDetails.setDistributorId(OrderProductDetails.getDistributorId());
		orderProductAndOrderDetails.setAssignedId(OrderProductDetails.getAssignedId());	
		orderProductAndOrderDetails.setSubOrderDeliveredDate(OrderProductDetails.getSubOrderDeliveredDate());
		orderProductAndOrderDetails.setSubOrderOfdDate(OrderProductDetails.getSubOrderOfdDate());
		orderProductAndOrderDetails.setSubOrderPickupDate(OrderProductDetails.getSubOrderPickupDate());
		orderProductAndOrderDetails.setSubOrderAssignedDate(OrderProductDetails.getSubOrderAssignedDate());
		orderProductAndOrderDetails.setOtsOrderDate(OrderProductDetails.getOtsOrderDate());
		orderProductAndOrderDetails.setDistributorFirstName(OrderProductDetails.getDistributorFirstName());
		orderProductAndOrderDetails.setDistributorLastName(OrderProductDetails.getDistributorLastName());
		orderProductAndOrderDetails.setDistributorEmailId(OrderProductDetails.getDistributorEmailId());
		orderProductAndOrderDetails.setBillOfSupply(OrderProductDetails.getBillOfSupply());
		orderProductAndOrderDetails.setProductBasePrice(OrderProductDetails.getProductBasePrice());
		orderProductAndOrderDetails.setOtsProductCancellationAvailability(OrderProductDetails.getOtsProductCancellationAvailability());
		orderProductAndOrderDetails.setOtsProductReplacementAvailability(OrderProductDetails.getOtsProductReplacementAvailability());
		orderProductAndOrderDetails.setOtsProductReplacementDays(OrderProductDetails.getOtsProductReplacementDays());
		orderProductAndOrderDetails.setOtsProductReturnAvailability(OrderProductDetails.getOtsProductReturnAvailability());
		orderProductAndOrderDetails.setOtsProductReturnDays(OrderProductDetails.getOtsProductReturnDays());
		orderProductAndOrderDetails.setEmployeeFirstName(OrderProductDetails.getEmployeeFirstName());
		orderProductAndOrderDetails.setEmployeeSecondName(OrderProductDetails.getEmployeeSecondName());
		orderProductAndOrderDetails.setRrcOrderStatus(OrderProductDetails.getRrcOrderStatus());
		orderProductAndOrderDetails.setRrcCustomerInitiatedDate(OrderProductDetails.getRrcCustomerInitiatedDate());
		orderProductAndOrderDetails.setRrcDistributorInitiatedDate(OrderProductDetails.getRrcDistributorInitiatedDate());
		orderProductAndOrderDetails.setProductSellerPrice(OrderProductDetails.getProductSellerPrice());
		orderProductAndOrderDetails.setProductPriceWithoutGst(OrderProductDetails.getProductPriceWithoutGst());
		orderProductAndOrderDetails.setCustomerId(OrderProductDetails.getCustomerId());
		orderProductAndOrderDetails.setCustomerName(OrderProductDetails.getCustomerName());	
		orderProductAndOrderDetails.setDeliveryAddress(OrderProductDetails.getDeliveryAddress());
		orderProductAndOrderDetails.setCustomerContactNo(OrderProductDetails.getCustomerContactNo());
		orderProductAndOrderDetails.setOrderProductCustomerInvoice(OrderProductDetails.getOrderProductCustomerInvoice());
		orderProductAndOrderDetails.setOtsTrackingId(OrderProductDetails.getOtsTrackingId());
		orderProductAndOrderDetails.setOtsTrackingUrl(OrderProductDetails.getOtsTrackingUrl());
		orderProductAndOrderDetails.setOtsTrackingLogistics(OrderProductDetails.getOtsTrackingLogistics());
		orderProductAndOrderDetails.setOtsProductGst(OrderProductDetails.getOtsProductGst());
		orderProductAndOrderDetails.setOtsProductGstPrice(OrderProductDetails.getOtsProductGstPrice());
		orderProductAndOrderDetails.setOtsProductPercentage(OrderProductDetails.getOtsProductPercentage());
		orderProductAndOrderDetails.setOtsProductDiscountPrice(OrderProductDetails.getOtsProductDiscountPrice());
		orderProductAndOrderDetails.setOtsProductDeliveryCharge(OrderProductDetails.getOtsProductDeliveryCharge());

		orderProductAndOrderDetails.setOrderDetails(orderDetails);
		
		System.out.println("customerChangeAddressId = "+orderDetails.getCustomerChangeAddressId());
		//for adding Customers Secondary Address to Order Response if customerChangeAddressId is present
		List<CustomerChangeAddress> customerSecondaryAddress = new ArrayList<CustomerChangeAddress>();
		//getting Secondary Address Details 
		customerSecondaryAddress = customerChangeAddressDAO.getCustomerChangeAddressById(orderDetails.getCustomerChangeAddressId());
		if(customerSecondaryAddress.size() != 0) {
			//setting Customer Secondary Address & customer details if the order have Secondary Address
			orderProductAndOrderDetails.getOrderDetails().setCustomerSecondaryAddress(customerSecondaryAddress);
			orderProductAndOrderDetails.getOrderDetails().setCustomerName(customerSecondaryAddress.get(0).getCustomerFirstName()+" "+customerSecondaryAddress.get(0).getCustomerSecondName());
			orderProductAndOrderDetails.getOrderDetails().setCustomerContactNo(customerSecondaryAddress.get(0).getCustomerContactNo());
			orderProductAndOrderDetails.getOrderDetails().setDeliveryAddress(customerSecondaryAddress.get(0).getOtsHouseNo()+" "+customerSecondaryAddress.get(0).getOtsBuildingName()
					+" "+customerSecondaryAddress.get(0).getOtsStreetName()+" "+customerSecondaryAddress.get(0).getOtsCityName()+"-"+customerSecondaryAddress.get(0).getOtsPinCode());
		}

		return orderProductAndOrderDetails;
	}
	
	@Override
	public OrderProductAndOrderResponse getCancelledOrdersByDistributor(String distributorId) {
		try {
			OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
			List<OrderProductDetails> OrderProductDetailsList = new ArrayList<OrderProductDetails>();
			//getting orders Cancelled by Distributor
			List<OrderProductDetails> distCancelledOrders = orderProductDao.getCancelledOrdersByDistributor(distributorId);
			//adding data into a List
			OrderProductDetailsList.addAll(distCancelledOrders);
			//getting orders Cancelled by Customer
			List<OrderProductDetails> custCancelledOrders = orderProductDao.getDistributorsOrderCancelledByCustomer(distributorId);
			//adding data into a previous List
			OrderProductDetailsList.addAll(custCancelledOrders);
			
			//sorting the list in descending order based on OrderID
			Collections.sort(OrderProductDetailsList, (s1, s2) ->
			    Integer.compare(Integer.parseInt(s2.getOtsOrderProductId()), Integer.parseInt(s1.getOtsOrderProductId())));
			
			if(OrderProductDetailsList.size() == 0) {
				return null;
			}else {
				List<OrderProductAndOrderDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderProductAndOrderDetails>();
				//getting order details for the order product
				for (int i = 0; i <OrderProductDetailsList.size() ; i++)
				{
					OrderDetails OrderDetails = orderServiceDAO.getOrderDetailsByOrderId(OrderProductDetailsList.get(i).getOtsOrderId());
					GetOrderDetailsAndProductDetails.add(AddOrderProductAndOrderDetailsIntoResponse(OrderDetails,OrderProductDetailsList.get(i)));
				}
				orderProductAndOrderResponse.setOrderProductList(GetOrderDetailsAndProductDetails);
				return orderProductAndOrderResponse;
			}
		} catch(Exception e){
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
	public OrderProductAndOrderResponse getReturnReplacementOrdersForDistributor(String distributorId) {
		try {
			OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
			// getting order product details by passing the distributor id
			List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getReturnReplacementOrdersForDistributor(distributorId);
			if(OrderProductDetailsList.size() == 0) {
				return null;
			}else {
				List<OrderProductAndOrderDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderProductAndOrderDetails>();
				//getting order details for the order product
				
				for (int i = 0; i <OrderProductDetailsList.size() ; i++)
				{
					OrderDetails OrderDetails = orderServiceDAO.getOrderDetailsByOrderId(OrderProductDetailsList.get(i).getOtsOrderId());
					GetOrderDetailsAndProductDetails.add(AddOrderProductAndOrderDetailsIntoResponse(OrderDetails,OrderProductDetailsList.get(i)));
				}
				orderProductAndOrderResponse.setOrderProductList(GetOrderDetailsAndProductDetails);
				return orderProductAndOrderResponse;
			}
		} catch(Exception e){
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
	public OrderProductAndOrderResponse getRRCOrdersByDistributor(GetRRCOrdersByStatusRequest getRRCOrdersByStatusRequest) {
		try {
			OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
			// getting order product details by passing the distributor id
			List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getRRCOrdersByDistributor(getRRCOrdersByStatusRequest.getRequest().getDistributorId(),getRRCOrdersByStatusRequest.getRequest().getRrcOrderStatus());
			if(OrderProductDetailsList.size() == 0) {
				return null;
			}else {
				List<OrderProductAndOrderDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderProductAndOrderDetails>();
				//getting order details for the order product
				
				for (int i = 0; i <OrderProductDetailsList.size() ; i++)
				{
					OrderDetails OrderDetails = orderServiceDAO.getOrderDetailsByOrderId(OrderProductDetailsList.get(i).getOtsOrderId());
					GetOrderDetailsAndProductDetails.add(AddOrderProductAndOrderDetailsIntoResponse(OrderDetails,OrderProductDetailsList.get(i)));
				}
				orderProductAndOrderResponse.setOrderProductList(GetOrderDetailsAndProductDetails);
				return orderProductAndOrderResponse;
			}
		} catch(Exception e){
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
	public OrderProductAndOrderResponse getRRClosedOrdersByDistributor(String distributorId) {
		try {
			OrderProductAndOrderResponse orderProductAndOrderResponse = new OrderProductAndOrderResponse();
			// getting order product details by passing the distributor id
			List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getRRClosedOrdersByDistributor(distributorId);
			if(OrderProductDetailsList.size() == 0) {
				return null;
			}else {
				List<OrderProductAndOrderDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderProductAndOrderDetails>();
				//getting order details for the order product
				
				for (int i = 0; i <OrderProductDetailsList.size() ; i++)
				{
					OrderDetails OrderDetails = orderServiceDAO.getOrderDetailsByOrderId(OrderProductDetailsList.get(i).getOtsOrderId());
					GetOrderDetailsAndProductDetails.add(AddOrderProductAndOrderDetailsIntoResponse(OrderDetails,OrderProductDetailsList.get(i)));
				}
				orderProductAndOrderResponse.setOrderProductList(GetOrderDetailsAndProductDetails);
				return orderProductAndOrderResponse;
			}
		} catch(Exception e){
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
	public String addPaymentTransactionCancelRecords(AddTransactionCancelRecordsRequest addPaymentTransactionCancelRecords) {
		String Response;
		try {
			Response = paymentTransactionCancelDAO.addPaymentTransactionCancelRecords(addPaymentTransactionCancelRecords);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return Response;
	}
	
	@Override
	public String generateDeliveryNote(String orderProductId) {
		String encodedString = null;
		try {
			List<OrderProductDetails> orderProduct = orderProductDao.getSubOrderDetailsByOrderProductId(orderProductId);
			if (orderProduct.size() == 0) {
				return null;
			} else {
				List<DistributorCompanyDetails> companyDetails = distributorCompanyDetailsDAO.getDistributorCompanyDetails(orderProduct.get(0).getDistributorId());
				if(companyDetails.size()==0){
					return null;
				}else {
					byte[] deliveryNote = DeliveryNote.getDeliveryNotePdf(orderProduct,companyDetails);

					encodedString = Base64.getEncoder().encodeToString(deliveryNote);
					System.out.println(encodedString);
				}
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return encodedString;
	}
	
	@Override 
	public List<OrderProductDetails> getSubOrderDetailsByOrderProductId(String orderProductId) {
		List<OrderProductDetails> orderProduct = new ArrayList<OrderProductDetails>();
		try {
			orderProduct = orderProductDao.getSubOrderDetailsByOrderProductId(orderProductId);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return orderProduct;
	}
	
	//get customer order by rrcOrderStatus
	@Override
	public OrderProductBOResponse getRRCOrdersByCustomer(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		List<OrderDetailsAndProductDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();
		try {
			//get customer order details based on customerId & orderProductStatus
			List<OrderDetails> OrderDetailsList = orderServiceDAO.getRRCOrdersByCustomer(getCustomerOrderByStatusBOrequest);
			if(OrderDetailsList.size() == 0) {
				return null;
			}else {
				for (int i = 0; i <OrderDetailsList.size() ; i++)
				{
					//get order product details by orderId & orderProductStatus
					List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getOrderProductByOrderIdAndRRCOrderStatus(OrderDetailsList.get(i).getOrderId(),getCustomerOrderByStatusBOrequest.getRequest().getStatus());
					GetOrderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(OrderDetailsList.get(i),OrderProductDetailsList));
				}
				orderProductBOResponse.setOrderList(GetOrderDetailsAndProductDetails);
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return orderProductBOResponse;
	}
	
	//get customer order by orderPrductStatus
	@Override
	public OrderProductBOResponse getCustomerOrderByOrderProductStatus(GetCustomerOrderByStatusBOrequest getCustomerOrderByStatusBOrequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		List<OrderDetailsAndProductDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();
		try {
			//get customer order details based on customerId & orderProductStatus
			List<OrderDetails> OrderDetailsList = orderServiceDAO.getCustomerOrderByOrderProductStatus(getCustomerOrderByStatusBOrequest);
			if(OrderDetailsList.size() == 0) {
				return null;
			}else {
				for (int i = 0; i <OrderDetailsList.size() ; i++)
				{
					//get order product details by orderId & orderProductStatus
					List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getOrderProductByOrderIdOrderProductStatus(OrderDetailsList.get(i).getOrderId(),getCustomerOrderByStatusBOrequest.getRequest().getStatus());
					GetOrderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(OrderDetailsList.get(i),OrderProductDetailsList));
				}
				orderProductBOResponse.setOrderList(GetOrderDetailsAndProductDetails);
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return orderProductBOResponse;
	}
	
	//get order details by orderId And orderPrductStatus
	@Override
	public OrderProductBOResponse getOrderByOrderIdOrderProductStatus(GetOrderByOrderIdAndStatusRequest getOrderByOrderIdAndStatusRequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		List<OrderDetailsAndProductDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();
		try {
			//get order details based on orderId & orderProductStatus
			List<OrderDetails> OrderDetailsList = orderServiceDAO.getOrderByOrderIdOrderProductStatus(getOrderByOrderIdAndStatusRequest.getRequest().getOrderId(),getOrderByOrderIdAndStatusRequest.getRequest().getStatus());
			if(OrderDetailsList.size() == 0) {
				return null;
			}else {
				for (int i = 0; i <OrderDetailsList.size() ; i++)
				{
					//get order product details by orderId & orderProductStatus
					List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getOrderProductByOrderIdOrderProductStatus(getOrderByOrderIdAndStatusRequest.getRequest().getOrderId(),getOrderByOrderIdAndStatusRequest.getRequest().getStatus());
					GetOrderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(OrderDetailsList.get(i),OrderProductDetailsList));
				}
				orderProductBOResponse.setOrderList(GetOrderDetailsAndProductDetails);
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return orderProductBOResponse;
	}
	
	//get order details by orderId And orderPrductStatus
	@Override
	public OrderProductBOResponse getOrderByOrderIdRRCOrderStatus(GetOrderByOrderIdAndStatusRequest getOrderByOrderIdAndStatusRequest) {
		OrderProductBOResponse orderProductBOResponse = new OrderProductBOResponse();
		List<OrderDetailsAndProductDetails> GetOrderDetailsAndProductDetails = new ArrayList<OrderDetailsAndProductDetails>();
		try {
			//get order details based on orderId & orderProductStatus
			List<OrderDetails> OrderDetailsList = orderServiceDAO.getOrderByOrderIdRRCOrderStatus(getOrderByOrderIdAndStatusRequest.getRequest().getOrderId(),getOrderByOrderIdAndStatusRequest.getRequest().getStatus());
			if(OrderDetailsList.size() == 0) {
				return null;
			}else {
				for (int i = 0; i <OrderDetailsList.size() ; i++)
				{
					//get order product details by orderId & orderProductStatus
					List<OrderProductDetails> OrderProductDetailsList = orderProductDao.getOrderProductByOrderIdAndRRCOrderStatus(getOrderByOrderIdAndStatusRequest.getRequest().getOrderId(),getOrderByOrderIdAndStatusRequest.getRequest().getStatus());
					GetOrderDetailsAndProductDetails.add(AddProductAndOrderDetailsIntoResponse(OrderDetailsList.get(i),OrderProductDetailsList));
				}
				orderProductBOResponse.setOrderList(GetOrderDetailsAndProductDetails);
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return orderProductBOResponse;
	}
	
	@Override 
	public List<OrderDetails> getOrderByOrderTransactionId(String orderTransactionId) {
		List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
		try {
			orderDetails = orderServiceDAO.getOrderByOrderTransactionId(orderTransactionId);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return orderDetails;
	}
	
	@Override
	public List<List<String>> getOrderProductDetailsForInvoice(String orderId,String productId) {
		try {
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("order_id",UUID.fromString(orderId));
			queryParameters.put("product_id",UUID.fromString(productId));
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_order_product_details_for_invoice")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("order_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, String>> orderDetails = (List<Map<String, String>>) queryResult.get("#result-set-1");

			//To Add Sl.no at beginning of the map as Key Value pair to existing map & adding numbers incrementing from 1
			int sl = 1;
			for(Map<String,String> map : orderDetails) {
			    Map<String,String> mapcopy = new LinkedHashMap<String,String>(map);
			    map.clear();
			    map.put("Sl.no",String.valueOf(sl++));
			    map.putAll(mapcopy);
			}
		
			//Converting List<Map<String, String>> into List<List<String>> List of values
			List<String> valueList = null;
			List<List<String>> order = new ArrayList<>();
			for(int index = 0 ; index < orderDetails.size() ; index++){
				Map<String, String> listItem = orderDetails.get(index);
				for(int j=0; j<listItem.size();j++) {
				    valueList = new ArrayList<String>(listItem.values());
				}
				System.out.println("valueList = "+valueList);
				order.add(valueList);
			} 
			return order;
		}catch(Exception e){
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
	public List<List<String>> getDistributorForOrderProductInvoice(String orderId, String productId) {
		try {
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("order_id",UUID.fromString(orderId));
			queryParameters.put("product_id",UUID.fromString(productId));
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_distributor_for_order_product_invoice")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("order_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, String>> orderDetails = (List<Map<String, String>>) queryResult.get("#result-set-1");
			
			//To Add Sl.no at beginning of the map as Key Value pair to existing map & adding numbers incrementing from 1
			int sl = 1;
			for(Map<String,String> map : orderDetails) {
			    Map<String,String> mapcopy = new LinkedHashMap<String,String>(map);
			    map.clear();
			    map.put("Sl.no",String.valueOf(sl++));
			    map.putAll(mapcopy);
			}
		
			//Converting List<Map<String, String>> into List<List<String>> List of values
			List<String> valueList = null;
			List<List<String>> order = new ArrayList<>();
			for(int index = 0 ; index < orderDetails.size() ; index++){
				Map<String, String> listItem = orderDetails.get(index);
				for(int j=0; j<listItem.size();j++) {
				    valueList = new ArrayList<String>(listItem.values());
				}
				System.out.println("valueList = "+valueList);
				order.add(valueList);
			} 
			
			return order;
		}catch(Exception e){
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
	public String generateOrderProductInvoicePdf(String orderId,String productId) {
		try {
			List<OrderDetails> orderList = orderServiceDAO.getOrderDetailsForOrderId(orderId);
			if(orderList.size()==0) {
				return null;
			}else {
				List<List<String>> distDetails = getDistributorForOrderProductInvoice(orderId,productId);
				if(distDetails.size()==0) {
					return null;
				}
				List<List<String>> orderDetails = getOrderProductDetailsForInvoice(orderId,productId);
				
				//To generate order Invoice pdf
				byte[] pdfPath = InvoicePdf.generateOrderInvoiceCopy(orderList, distDetails, orderDetails);
				
				//To encode byte[] to String
				String encodedString = Base64.getEncoder().encodeToString(pdfPath);
				System.out.println(encodedString);
				//To add encoded Invoice path into order table(DB)
				String addInvoice = orderProductDao.addCustomerOrderProductInvoiceToDB(orderId,productId,encodedString);
				
				return encodedString;
			}
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
	public String addOrUpdateOrderTracking(AddOrUpdateOrderTrackingRequest addOrUpdateOrderTrackingRequest) {	
		try {
			String orderprodcutraking = orderProductDao.addOrUpdateOrderTracking(addOrUpdateOrderTrackingRequest);
			return orderprodcutraking;
		}catch(Exception e){
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
	public OrderProductDetails getOrderProductByOrderIdProductId(String orderId,String productId) {
		try {
			OrderProductDetails orderProduct = orderProductDao.getOrderProductByOrderIdProductId(orderId,productId);
			return orderProduct;
		}catch(Exception e){
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
	public List<OrderProductDetails> checkPendingOrdersOfInactiveDistributor(String distributorId){
		try {
			List<OrderProductDetails> distributorOrders = orderProductDao.checkPendingOrdersOfInactiveDistributor(distributorId);
			return distributorOrders;
		}catch(Exception e){
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
	public String updateOrderProductStatus(UpdateOrderProductStatusRequest updateOrderProductStatusRequest) {
		try {
			//To update Order Product Status as "Order-picked-up","Out-for-delivery"
			String orderProductDetail = orderProductDao.updateOrderProductStatus(
					updateOrderProductStatusRequest.getRequest().getOrderProductId(), 
					updateOrderProductStatusRequest.getRequest().getOrderProductStatus());
			
			return orderProductDetail;
		} catch (BusinessException e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}	
	}


}
