package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.service.request.AddProductStockBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductStockRequest;
import com.fuso.enterprise.ots.srv.api.service.response.GetProductBOStockResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;
import com.fuso.enterprise.ots.srv.server.dao.ProductStockDao;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProductStock;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ProductStockDaoImpl extends AbstractIptDao<OtsProductStock, String> implements ProductStockDao{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ProductStockDaoImpl() {
		super(OtsProductStock.class);
	}
 
	//Inserting and Updating the ProductStock Table
	@Override
	public String addProductStock(AddProductStockBORequest addProductStockBORequest) {
		OtsProductStock otsProductStock = new OtsProductStock();		
		OtsProduct otsProduct = new OtsProduct();
		otsProduct.setOtsProductId(UUID.fromString(addProductStockBORequest.getRequestData().getProductId()));				
		OtsUsers otsUsers = new OtsUsers();
		otsUsers.setOtsUsersId(UUID.fromString(addProductStockBORequest.getRequestData().getUsersId()));				
	 	Map<String, Object> queryParameter = new HashMap<>();		
	 	queryParameter.put("otsUsersId",otsUsers );		
		queryParameter.put("otsProductId", otsProduct);		
		try {
			otsProductStock = super.getResultByNamedQuery("OtsProductStock.findByOtsProductIdAndUserId", queryParameter) ;			
			Integer stock = Integer.valueOf(addProductStockBORequest.getRequestData().getProductStockQty());
			stock = stock + Integer.valueOf(otsProductStock.getOtsProductStockActQty());
			otsProductStock.setOtsProductStockId(otsProductStock.getOtsProductStockId());
			otsProductStock.setOtsProductStockActQty(stock.toString());
			super.getEntityManager().merge(otsProductStock);
			
		}catch (NoResultException e) {
			otsUsers.setOtsUsersId(UUID.fromString(addProductStockBORequest.getRequestData().getUsersId()));
			otsProductStock.setOtsUsersId(otsUsers);		 
			otsProductStock.setOtsProductStockActQty(addProductStockBORequest.getRequestData().getProductStockQty());
			otsProductStock.setOtsProductStockStatus(addProductStockBORequest.getRequestData().getProductStockStatus());
			otsProduct.setOtsProductId(UUID.fromString(addProductStockBORequest.getRequestData().getProductId()));
			otsProductStock.setOtsProductId (otsProduct);
			super.getEntityManager().merge(otsProductStock);
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
		
		return "Successful";
	}
	
	@Transactional
	@Override
	public GetProductBOStockResponse updateProductStockQuantity(AddProductStockBORequest addProductStockBORequest) {
		GetProductBOStockResponse getProductBOStockResponse = new GetProductBOStockResponse();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("distributor_id", UUID.fromString(addProductStockBORequest.getRequestData().getUsersId()));
			inParamMap.put("product_id", UUID.fromString(addProductStockBORequest.getRequestData().getProductId()));
			inParamMap.put("input_quantity",Integer.parseInt(addProductStockBORequest.getRequestData().getProductStockQty()));

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("update_product_stock_quantity")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("input_quantity", Types.INTEGER));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> productStock = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			
			System.out.println("productStock size = "+productStock.size());

			//to convert procedure output into product details object
			getProductBOStockResponse = convertProductStockFromProcedureToDomain(productStock.get(0));
			
			System.out.println("product stock qty = "+getProductBOStockResponse.getStockQuantity());
			
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}	
		return getProductBOStockResponse;
	}
	
	private GetProductBOStockResponse convertProductStockFromProcedureToDomain(Map<String, Object> out){
		GetProductBOStockResponse getProductBOStockResponse = new GetProductBOStockResponse();
		try{
			
			getProductBOStockResponse.setProductId(out.get("ots_product_id")==null?"":out.get("ots_product_id").toString());
			getProductBOStockResponse.setProductStockId(out.get("ots_product_stock_id")==null?"":out.get("ots_product_stock_id").toString());
			getProductBOStockResponse.setStockQuantity(out.get("ots_product_stock_act_qty")==null?"":out.get("ots_product_stock_act_qty").toString());
			getProductBOStockResponse.setProductStockStatus(out.get("ots_product_stock_status")==null?"":out.get("ots_product_stock_status").toString());
			getProductBOStockResponse.setUserId(out.get("ots_users_id")==null?"":out.get("ots_users_id").toString());
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}
		return getProductBOStockResponse;
	}

	@Override
	public GetProductBOStockResponse getProductStockByUidAndPid(GetProductStockRequest getProductStockRequest) {
		GetProductBOStockResponse getProductBOStockResponse = new GetProductBOStockResponse();
		List<OtsProductStock> otsProductStock = new ArrayList<OtsProductStock>();
		try {
		 	Map<String, Object> queryParameter = new HashMap<>();	
		 	
		 	OtsUsers UserId = new OtsUsers();
		 	UserId.setOtsUsersId(UUID.fromString(getProductStockRequest.getRequestData().getDistributorId()));
		 	
		 	OtsProduct otsProductId = new OtsProduct();
		 	otsProductId.setOtsProductId(UUID.fromString(getProductStockRequest.getRequestData().getProductId()));
		 	
		 	queryParameter.put("DistributorId",UserId);		
			queryParameter.put("ProductId",otsProductId );
			otsProductStock = super.getResultListByNamedQuery("OtsProductStock.getQuantityById", queryParameter);
			if(otsProductStock.size() == 0 ) {
				return null;
			}else {
				getProductBOStockResponse = convertProductStockEntityToModel(otsProductStock.get(0));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}	
    	return getProductBOStockResponse;
	}
	
	private GetProductBOStockResponse convertProductStockEntityToModel(OtsProductStock otsProductStock)
	{
		GetProductBOStockResponse getProductBOStockResponse = new GetProductBOStockResponse();
		getProductBOStockResponse.setProductId(otsProductStock.getOtsProductId().getOtsProductId().toString());
		getProductBOStockResponse.setProductStockId(otsProductStock.getOtsProductStockId().toString());
		getProductBOStockResponse.setStockQuantity(otsProductStock.getOtsProductStockActQty());
		getProductBOStockResponse.setProductStockStatus(otsProductStock.getOtsProductStockStatus());
		getProductBOStockResponse.setUserId(otsProductStock.getOtsUsersId().getOtsUsersId().toString());
		return getProductBOStockResponse;
	}
	
	@Override
	public String removeProductStock(AddProductStockBORequest addProductStockBORequest) {
		OtsProductStock otsProductStock = new OtsProductStock();		
		OtsProduct otsProduct = new OtsProduct();
		otsProduct.setOtsProductId(UUID.fromString(addProductStockBORequest.getRequestData().getProductId()));				
		OtsUsers otsUsers = new OtsUsers();
		otsUsers.setOtsUsersId(UUID.fromString(addProductStockBORequest.getRequestData().getUsersId()));				
	 	Map<String, Object> queryParameter = new HashMap<>();		
	 	queryParameter.put("otsUsersId",otsUsers );		
		queryParameter.put("otsProductId", otsProduct);		
		try {
			otsProductStock = super.getResultByNamedQuery("OtsProductStock.findByOtsProductIdAndUserId", queryParameter) ;			
			Long stock = Long.valueOf(otsProductStock.getOtsProductStockActQty());
			stock = stock - Long.valueOf(addProductStockBORequest.getRequestData().getProductStockQty());
			otsProductStock.setOtsProductStockId(otsProductStock.getOtsProductStockId());
			otsProductStock.setOtsProductStockActQty(stock.toString());
			super.getEntityManager().merge(otsProductStock);
			System.out.println("productId "+addProductStockBORequest.getRequestData().getProductId()+" stock "+addProductStockBORequest.getRequestData().getProductStockQty());
		}catch (NoResultException e) {
			System.out.println("productId "+addProductStockBORequest.getRequestData().getProductId()+" stock "+addProductStockBORequest.getRequestData().getProductStockQty());
			otsUsers.setOtsUsersId(UUID.fromString(addProductStockBORequest.getRequestData().getUsersId()));
			otsProductStock.setOtsUsersId(otsUsers);		 
			otsProductStock.setOtsProductStockActQty(addProductStockBORequest.getRequestData().getProductStockQty());
			otsProductStock.setOtsProductStockStatus(addProductStockBORequest.getRequestData().getProductStockStatus());
			otsProduct.setOtsProductId(UUID.fromString(addProductStockBORequest.getRequestData().getProductId()));
			otsProductStock.setOtsProductId (otsProduct);
			super.getEntityManager().merge(otsProductStock);
		}catch (Exception e) {
	    	throw new BusinessException(e, ErrorEnumeration.User_Not_exists);
		}
		logger.info("Inside Event=1014,Class:OTSProduct_WsImpl,Method:removeProductStock");
		return "Stock Updated Scuccessfully";
	}

	@Override
	public List<GetProductBOStockResponse> getProductStockByUid(String distributorId) {
		List<GetProductBOStockResponse> getProductBOStockResponse = new ArrayList<GetProductBOStockResponse>();
		List<OtsProductStock> otsProductStockList = new ArrayList<OtsProductStock>();
		try {
		 	Map<String, Object> queryParameter = new HashMap<>();	
		 	
		 	OtsUsers UserId = new OtsUsers();
		 	UserId.setOtsUsersId(UUID.fromString(distributorId)); 	
		 	queryParameter.put("DistributorId",UserId);		
		 	
		 	otsProductStockList = super.getResultListByNamedQuery("OtsProductStock.DistributorId", queryParameter);
			getProductBOStockResponse = otsProductStockList.stream().map(OtsProductStock -> convertProductStockEntityToModel(OtsProductStock)).collect(Collectors.toList());
		} catch (BusinessException e) {
			return null;
	    } catch (Throwable e) {
	    	return null;
	    }
    	return getProductBOStockResponse;
	}
	
	
	@Override
	public String addAirtableStock(AddProductStockBORequest addProductStockBORequest) {
		OtsProductStock otsProductStock = new OtsProductStock();		
		OtsProduct otsProduct = new OtsProduct();
		otsProduct.setOtsProductId(UUID.fromString(addProductStockBORequest.getRequestData().getProductId()));				
		OtsUsers otsUsers = new OtsUsers();
		otsUsers.setOtsUsersId(UUID.fromString(addProductStockBORequest.getRequestData().getUsersId()));				
	 	Map<String, Object> queryParameter = new HashMap<>();		
	 	queryParameter.put("otsUsersId",otsUsers );		
		queryParameter.put("otsProductId", otsProduct);	
		try {
			otsProductStock = super.getResultByNamedQuery("OtsProductStock.findByOtsProductIdAndUserId", queryParameter) ;			
			otsProductStock.setOtsProductStockId(otsProductStock.getOtsProductStockId());
			otsProductStock.setOtsProductStockActQty(addProductStockBORequest.getRequestData().getProductStockQty());
			super.getEntityManager().merge(otsProductStock);
		}catch (NoResultException e) {
			System.out.print(e);
			otsUsers.setOtsUsersId(UUID.fromString(addProductStockBORequest.getRequestData().getUsersId()));
			otsProductStock.setOtsUsersId(otsUsers);		 
			otsProductStock.setOtsProductStockActQty(addProductStockBORequest.getRequestData().getProductStockQty());
			otsProductStock.setOtsProductStockStatus(addProductStockBORequest.getRequestData().getProductStockStatus());
			otsProduct.setOtsProductId(UUID.fromString(addProductStockBORequest.getRequestData().getProductId()));
			otsProductStock.setOtsProductId (otsProduct);
			super.getEntityManager().merge(otsProductStock);
		}catch (Exception e) {
			System.out.print(e);
	    	throw new BusinessException(e, ErrorEnumeration.User_Not_exists);
		}
		logger.info("Inside Event=1014,Class:OTSProduct_WsImpl,Method:ProductStockDaoImpl ");
		return "Stock Updated Scuccessfully";
	}
	
	@Override
	public List<GetProductBOStockResponse> getProductStockByProductId(String productId) {
		List<GetProductBOStockResponse> getProductBOStockResponse = new ArrayList<GetProductBOStockResponse>();
		List<OtsProductStock> otsProductStock = new ArrayList<OtsProductStock>();
		try {
		 	Map<String, Object> queryParameter = new HashMap<>();	
		 	
		 	OtsProduct otsProductId = new OtsProduct();
		 	otsProductId.setOtsProductId(UUID.fromString(productId));
		 	
			queryParameter.put("otsProductId",otsProductId );
			otsProductStock = super.getResultListByNamedQuery("OtsProductStock.getProductStockByProductId", queryParameter);
			getProductBOStockResponse = otsProductStock.stream().map(OtsProductStock -> convertProductStockEntityToModel(OtsProductStock)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}	
    	return getProductBOStockResponse;
	}

}
