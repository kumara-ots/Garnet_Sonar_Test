package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.service.request.AddToCartRequest;
import com.fuso.enterprise.ots.srv.api.service.response.GetcartListResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.CartDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsCart;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class CartDAOImpl extends AbstractIptDao<OtsCart, String> implements CartDAO {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public CartDAOImpl() {
		super(OtsCart.class);
	}
	
	@Override
	public String addToCart(AddToCartRequest addToCartRequest) {
		Map<String, Object> queryParameter = new HashMap<>();
		
		OtsProduct productId = new OtsProduct();
		productId.setOtsProductId(UUID.fromString(addToCartRequest.getRequestData().getProductId()));
		queryParameter.put("otsProductId",productId);
		
		OtsUsers customerId = new OtsUsers();
		customerId.setOtsUsersId(UUID.fromString(addToCartRequest.getRequestData().getCustomerId()));
		queryParameter.put("otsCustomerId",customerId);
		
		OtsCart cart = new OtsCart();
		cart.setOtsCartQty(addToCartRequest.getRequestData().getOtsCartQty());

		try{
			cart = super.getResultByNamedQuery("OtsCart.getCartListByCustomerIdAndProductId", queryParameter);
			if(addToCartRequest.getRequestData().getOtsCartQty()>=1){
				int oldQuantity=cart.getOtsCartQty();
				int TotalQuantity=oldQuantity+(addToCartRequest.getRequestData().getOtsCartQty());
				cart.setOtsCartQty(TotalQuantity);
				super.getEntityManager().merge(cart);
				return "Product Added To Cart";
			}else{
				super.getEntityManager().remove(cart);
				return "Product Added To Cart";
			}	
		}catch (Exception e) {
			if(addToCartRequest.getRequestData().getOtsCartQty()>=1){
				cart.setOtsProductId(productId);
				cart.setOtsCustomerId(customerId);
				super.getEntityManager().merge(cart);
			}
			return "Product Added To Cart";
		}
	}
	
	@Override
	public List<GetcartListResponse> getCartList(String customerId) {
		try {
			OtsUsers otsCustomerId = new OtsUsers();
			otsCustomerId.setOtsUsersId(UUID.fromString(customerId));
			
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCustomerId",otsCustomerId);
	
			List<OtsCart> cart = super.getResultListByNamedQuery("OtsCart.getCartListByCustomerId", queryParameter);
			List<GetcartListResponse> getcartListResponseList = cart.stream().map(cartlist -> convertCartDetailsFromEntityToDomain(cartlist)).collect(Collectors.toList());
			
			return getcartListResponseList;
		}catch (NoResultException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        	throw new BusinessException("cart list is empty", e);
        }
	}
	
	GetcartListResponse convertCartDetailsFromEntityToDomain(OtsCart cartlist)
	{
		GetcartListResponse getCartListResponse = new GetcartListResponse();
		getCartListResponse.setProductId(cartlist.getOtsProductId().getOtsProductId()==null?"":cartlist.getOtsProductId().getOtsProductId().toString());
		getCartListResponse.setProductName(cartlist.getOtsProductId().getOtsProductName()==null?"":cartlist.getOtsProductId().getOtsProductName());
		getCartListResponse.setProductImage(cartlist.getOtsProductId().getOtsProductImage()==null?"":cartlist.getOtsProductId().getOtsProductImage());
		getCartListResponse.setProductPrice(cartlist.getOtsProductId().getOtsProductPrice().toString()==null?"":cartlist.getOtsProductId().getOtsProductPrice().toString());
		getCartListResponse.setOtsCartQty(cartlist.getOtsCartQty()==null?null:cartlist.getOtsCartQty());
		getCartListResponse.setProductGST(cartlist.getOtsProductId().getOtsProductGst()==null?"":cartlist.getOtsProductId().getOtsProductGst());
		getCartListResponse.setCustomerId(cartlist.getOtsCustomerId().getOtsUsersId()==null?"":cartlist.getOtsCustomerId().getOtsUsersId().toString());
		getCartListResponse.setOtsProductCountry(cartlist.getOtsProductId().getOtsProductCountry()== null?"":cartlist.getOtsProductId().getOtsProductCountry());
		getCartListResponse.setOtsProductCountryCode(cartlist.getOtsProductId().getOtsProductCountryCode() == null?"":cartlist.getOtsProductId().getOtsProductCountryCode());
		getCartListResponse.setOtsProductCurrency(cartlist.getOtsProductId().getOtsProductCurrency() == null?"":cartlist.getOtsProductId().getOtsProductCurrency());
		getCartListResponse.setOtsProductCurrencySymbol(cartlist.getOtsProductId().getOtsProductCurrencySymbol()== null?"":cartlist.getOtsProductId().getOtsProductCurrencySymbol());

		return getCartListResponse;
	}

	@Override
	public String removeFromCart(AddToCartRequest addToCartRequest) {
		Map<String, Object> queryParameter = new HashMap<>();
		OtsCart cart = new OtsCart();

		OtsProduct productId = new OtsProduct();
		productId.setOtsProductId(UUID.fromString(addToCartRequest.getRequestData().getProductId()));
		queryParameter.put("otsProductId",productId);
		
		OtsUsers customerId = new OtsUsers();
		customerId.setOtsUsersId(UUID.fromString(addToCartRequest.getRequestData().getCustomerId()));
		queryParameter.put("otsCustomerId",customerId);
		try {
			int TotalQuantity=0;
			cart = super.getResultByNamedQuery("OtsCart.getCartListByCustomerIdAndProductId", queryParameter);
				int oldQuantity = cart.getOtsCartQty();
				 TotalQuantity = oldQuantity-(addToCartRequest.getRequestData().getOtsCartQty());
				cart.setOtsCartQty(TotalQuantity);
				
			 if(addToCartRequest.getRequestData().getOtsCartQty()==0){
				super.getEntityManager().remove(cart);
				return "Product Removed Successfully";
			 }else if(TotalQuantity >= 1){
				super.getEntityManager().merge(cart);
				return "Product Removed Successfully";
			}else{
				super.getEntityManager().remove(cart);
				return "Product Removed Successfully";
			}
		}catch(Exception e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
			return "No Data Found";
		}
	}

	@Override
	public String emptyCart(AddToCartRequest addToCartRequest) {
		List<OtsCart> cart = new ArrayList<OtsCart>();
		try {
			OtsUsers customerId = new OtsUsers();
			customerId.setOtsUsersId(UUID.fromString(addToCartRequest.getRequestData().getCustomerId()));

			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCustomerId", customerId);
			cart = super.getResultListByNamedQuery("OtsCart.getCartListByCustomerId", queryParameter);
			if (cart.size() == 0) {
				return "No Data Found";
			} else {
				for (OtsCart otsCart : cart)
					super.getEntityManager().remove(otsCart);
			}
		} catch (NoResultException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        	throw new BusinessException("Successfully", e);
        }
		return "Success";
	}

	@Override
	public GetcartListResponse getCartByCustomerProduct(String customerId, String productId) {
		OtsCart cartList = new OtsCart();
		try {
			OtsUsers customer = new OtsUsers();
			customer.setOtsUsersId(UUID.fromString(customerId));

			OtsProduct product = new OtsProduct();
			product.setOtsProductId(UUID.fromString(productId));

			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsCustomerId", customer);
			queryParameter.put("otsProductId", product);

			try {
				cartList = super.getResultByNamedQuery("OtsCart.getCartListByCustomerIdAndProductId", queryParameter);
			}catch(NoResultException exception){
				return null;
			}
			
			GetcartListResponse getcartListResponse = convertCartDetailsFromEntityToDomain(cartList);
			return getcartListResponse;
		}catch (BusinessException e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Throwable e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    }
	}
	
}
	
