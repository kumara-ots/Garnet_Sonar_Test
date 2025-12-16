package com.fuso.enterprise.ots.srv.rest.ws.service;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fuso.enterprise.ots.srv.api.model.domain.AttributeKey;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValue;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValueName;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductAttributesMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.SubCategoryAttributeMapping;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSProductService;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeKeyForSubCategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeKeyRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeValueRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateCategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductAttributeMappingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductByCountryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductManufacturerRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductStockBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddVariantProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.FilterProductsByGeneralPropertiesRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetActiveCountryCodeProductsRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCategorySubCategoryByDistributorRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCatgeorySubcategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductStockListRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductStockRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductsByDistributorPaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductsBySubCategoryAndDistributorRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetSiblingVariantProductsByAttributeRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetSimilarProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ProductDetailsBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.SearchProductByNamePaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpadteAttributeValueRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateAttributeKeyRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateProductStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.response.AttributeKeyResponse;
import com.fuso.enterprise.ots.srv.api.service.response.AttributeValueResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetAttributeKeysAndAttributeValuesResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetPageLoaderResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetProductBOStockResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetProductStockListBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ProductDetailsBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ProductDetailsPageloaderResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ProductManufacturersResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ProductSearchResponse;
import com.fuso.enterprise.ots.srv.api.service.response.SubCategoryAttributeResponse;
import com.fuso.enterprise.ots.srv.server.util.GeoLocationUtil;
import com.fuso.enterprise.ots.srv.server.util.ResponseWrapper;

public class OTSProduct_WsImpl implements OTSProduct_Ws {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    ResponseWrapper responseWrapper;
    
    @Inject
    private OTSProductService otsProductService;
    
    @Value("${product.percentage.price}")
	public String productPercentage;
    
    @Autowired
    private GeoLocationUtil geoLocationUtil;

    private Response buildResponse(int code, String description) {
        ResponseWrapper wrapper = new ResponseWrapper(code, description);
        return Response.ok(wrapper).build();
    }
    
    @Override
    public Response addProductStock(AddProductStockBORequest addStockProductBORequest) {
        Response response = null;
        try {
        	if (addStockProductBORequest.getRequestData().getProductStockQty() == null || addStockProductBORequest.getRequestData().getProductStockQty().equals("")
            		|| addStockProductBORequest.getRequestData().getUsersId() == null || addStockProductBORequest.getRequestData().getUsersId().equals("")
            		|| addStockProductBORequest.getRequestData().getProductId() == null || addStockProductBORequest.getRequestData().getProductId().equals("")) {
            	return response = buildResponse(400,"Please Enter Required Inputs");
            }
        	String addStock = otsProductService.addOrUpdateProductStock(addStockProductBORequest);
            if (addStock == null) {
            	response = responseWrapper.buildResponse(404,"Unable To Add Stock Details");
            }else {
            	response = responseWrapper.buildResponse(200,addStock, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
    }
    
    @Override
    public Response getProductStockList(GetProductStockListRequest getProductStockListRequest) {
        Response response = null;
        GetProductStockListBOResponse GetProductStockListBOResponse = new GetProductStockListBOResponse();
        try {
        	if (getProductStockListRequest.getRequestData().getUserId() == null || getProductStockListRequest.getRequestData().getUserId().equals("")) {
            	return response = buildResponse(400,"Please Enter Required Inputs");
            }
            GetProductStockListBOResponse = otsProductService.getProductStockList(getProductStockListRequest);
            if (GetProductStockListBOResponse == null) {
            	response = responseWrapper.buildResponse(404,"No Data Found");
            }else {
            	response = responseWrapper.buildResponse(200,GetProductStockListBOResponse, "Successful");
            }
        }catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		return response = buildResponse(500,"Something Went Wrong");
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		return response = buildResponse(500,"Something Went Wrong");
    	}
        return response;
    }

    @Override
    public Response getProductStock(GetProductStockRequest getProductStockRequest) {
        Response response = null;
        GetProductBOStockResponse getProductBOStockResponse = new GetProductBOStockResponse();
        try {
            getProductBOStockResponse = otsProductService.getProductStockByUidAndPid(getProductStockRequest);
            if (getProductBOStockResponse == null) {
                response = responseWrapper.buildResponse(404,"No Data Found");
            } else {
                response = responseWrapper.buildResponse(200,getProductBOStockResponse,"Successful");
         }
        }catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		}	
        return response;
    }

	@Override
	public Response updateProductStatus(UpdateProductStatusRequest updateProductStatusRequest) {
		Response response = null;
		try {
			if (updateProductStatusRequest.getRequest().getProductId() == null || updateProductStatusRequest.getRequest().getProductId().equals("")
				|| updateProductStatusRequest.getRequest().getStatus() == null || updateProductStatusRequest.getRequest().getStatus().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			//Predefined user status
			String[] VALID_STATUSES = {"delete", "inactive","active"};

		    // Validate input user status
		    String productstatus = updateProductStatusRequest.getRequest().getStatus();
		    boolean isValidStatus = Arrays.stream(VALID_STATUSES)
		                                  .anyMatch(status -> status.equalsIgnoreCase(productstatus));

		    //If input status not matching predefined status
		    if (!isValidStatus) {
		        return response = responseWrapper.buildResponse(400, "Invalid Product Status");
		    }  

			String productUpdate = otsProductService.updateProductStatus(updateProductStatusRequest);
			if (productUpdate.equalsIgnoreCase("Updated")) {
				response = responseWrapper.buildResponse(200, "Updated");
			} else if (productUpdate.equalsIgnoreCase("Not Updated")) {
				response = responseWrapper.buildResponse(404, "Not Updated");
			} else {
				response = responseWrapper.buildResponse(500, "Something Went Wrong");
			}
			return response;
		} catch (Exception e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		}
	}

	 /***************shreekant rathod************/
	@Override
	public Response getAllProductDetails() {
		Response response = null;
		try {
            ProductDetailsBOResponse productDetailsBOResponse = otsProductService.getAllProductDetails();
            if(productDetailsBOResponse.getProductDetails().size() == 0) {
            	response = responseWrapper.buildResponse(404,"No Products Available");
            }else {
            	response = responseWrapper.buildResponse(200,productDetailsBOResponse,"Successful");
            }
            return response;
	  	}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		}
	}
	
	@Override
    public Response getProductList(ProductDetailsBORequest productDetailsBORequest) {
    	Response response = null;
        ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
        try {
	        if (productDetailsBORequest.getRequestData().getSearchKey() == null || productDetailsBORequest.getRequestData().getSearchKey().equals("") 
	        		|| productDetailsBORequest.getRequestData().getSearchvalue() == null || productDetailsBORequest.getRequestData().getSearchvalue().equals("")) {
	        	return response = buildResponse(400,"Search Key and Search Value Should Not Be Empty"); 
	        }
            productDetailsBOResponse = otsProductService.getProductList(productDetailsBORequest);
			if (productDetailsBOResponse == null || productDetailsBOResponse.getProductDetails().contains(null) ||productDetailsBOResponse.getProductDetails().size() == 0) {
            	response = responseWrapper.buildResponse(404,"Product Details Not Available");
            } else {
                response = responseWrapper.buildResponse(200,productDetailsBOResponse, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
    }

	@Override
	public Response getProductDetails(String productId) {
		Response response = null;
		try {
			ProductDetails productDetails = otsProductService.getProductDetails(productId);
			if(productDetails == null) {
	            response = responseWrapper.buildResponse(404,"No Products Available");
            }else {
            	response = responseWrapper.buildResponse(200,productDetails,"Successful");
            }
	        return response;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		}
	}

	@Override
	public Response getProductListByDistributor(String distributorId) 
	{
		Response response = null;
		ProductDetailsBOResponse ProductDetailsBOResponse = new ProductDetailsBOResponse();
        try {
        	ProductDetailsBOResponse = otsProductService.getProductListByDistributor(distributorId);
            if (ProductDetailsBOResponse.getProductDetails().size()== 0 ) {
                response = responseWrapper.buildResponse(404, "No Products Available For This Distributor");
            } else {
            	 response = responseWrapper.buildResponse(ProductDetailsBOResponse, "Successful");
            }

        }catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
	}
	
	@Override
	public Response getAllProductsWithDiscount()
	{
		Response response = null;
		try {
			ProductDetailsBOResponse productList = otsProductService.getAllProductsWithDiscount();
			if(productList.getProductDetails().size() == 0) {			
				response = responseWrapper.buildResponse(404,"No Products Available");
			}else {
				response = responseWrapper.buildResponse(productList,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}	
		return response;
	}
	
	@Override
	public Response getProductsByDistributorPagination(GetProductsByDistributorPaginationRequest getProductsByDistributorPagination) {
		Response response = null;
		try{
			if(getProductsByDistributorPagination.getRequest().getSearchKey()==null || getProductsByDistributorPagination.getRequest().getSearchKey().equals("")
					|| getProductsByDistributorPagination.getRequest().getSearchValue()==null || getProductsByDistributorPagination.getRequest().getSearchValue().equals("")
					|| getProductsByDistributorPagination.getRequest().getDistributorId()==null || getProductsByDistributorPagination.getRequest().getDistributorId().equals("")
					|| getProductsByDistributorPagination.getRequest().getPageNumber() == null || getProductsByDistributorPagination.getRequest().getPageNumber().equals("")
					|| getProductsByDistributorPagination.getRequest().getDataSize() == null || getProductsByDistributorPagination.getRequest().getDataSize().equals("")
					|| getProductsByDistributorPagination.getRequest().getProductCountryCode()==null || getProductsByDistributorPagination.getRequest().getProductCountryCode().equals("")) {
				return response = buildResponse(400,"Please Enter Required Inputs");
			}
			
			//Predefined user status
			String[] VALID_STATUSES = {"all","category", "subcategory"};

		    // Validate input user status
		    String searchKey = getProductsByDistributorPagination.getRequest().getSearchKey();
		    boolean isValidStatus = Arrays.stream(VALID_STATUSES)
		                                  .anyMatch(status -> status.equalsIgnoreCase(searchKey));

		    //If input status not matching predefined status
		    if (!isValidStatus) {
		        return response = responseWrapper.buildResponse(400, "Invalid SearchKey");
		    } 
		    
			ProductDetailsBOResponse productDetailsBOResponse = otsProductService.getProductsByDistributorPagination(getProductsByDistributorPagination);
			if(productDetailsBOResponse.getProductDetails().size() == 0) {			
				response = responseWrapper.buildResponse(404,"No Products Available");
			}else {
				response = responseWrapper.buildResponse(productDetailsBOResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}	
		return response;
	}
	
	@Override
    public Response addStockForMultipleProductByDistributor(AddProductStockBORequest addStockProductBORequest) {
        Response response = null;
        if (addStockProductBORequest.getRequestData().getProductStockQty() == null || addStockProductBORequest.getRequestData().getProductStockQty().equals("")
        		|| addStockProductBORequest.getRequestData().getUsersId() == null || addStockProductBORequest.getRequestData().getUsersId().equals("")) {
        	return response = buildResponse(400,"Please Enter Required Inputs");
        }
        try {
            String addStock = otsProductService.addStockForMultipleProductByDistributor(addStockProductBORequest);
            if (addStock == null) {
            	response = responseWrapper.buildResponse(404,"Unable To Add Stock Details");
            }
            else if(addStock.equalsIgnoreCase("Products Not Available To Add Stock Details"))
            {
            	response = responseWrapper.buildResponse(404,"Products Not Available To Add Stock Details");
            }else {
            	response = responseWrapper.buildResponse(200,addStock, "Stock Updated Scuccessfully");
            }
        }catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		return response = buildResponse(500,"Something Went Wrong");
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		return response = buildResponse(500,"Something Went Wrong");
    	}
        return response;
    }
	
	@Override
	public Response getCategoryAndSubCategoryByDistributor(GetCategorySubCategoryByDistributorRequest getCategorySubCategoryByDistributorRequest) {
		Response response = null;
		ProductDetailsBOResponse ProductDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			if (getCategorySubCategoryByDistributorRequest.getRequest().getSearchKey() == null || getCategorySubCategoryByDistributorRequest.getRequest().getSearchKey().equals("")
					|| getCategorySubCategoryByDistributorRequest.getRequest().getSearchValue() == null || getCategorySubCategoryByDistributorRequest.getRequest().getSearchValue().equals("")
					|| getCategorySubCategoryByDistributorRequest.getRequest().getDistributorId() == null || getCategorySubCategoryByDistributorRequest.getRequest().getDistributorId().equals("") 
					|| getCategorySubCategoryByDistributorRequest.getRequest().getCountryCode() == null || getCategorySubCategoryByDistributorRequest.getRequest().getCountryCode().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			
			//Predefined user status
			String[] VALID_STATUSES = {"category", "subcategory"};

		    // Validate input user status
		    String searchKey = getCategorySubCategoryByDistributorRequest.getRequest().getSearchKey();
		    boolean isValidStatus = Arrays.stream(VALID_STATUSES)
		                                  .anyMatch(status -> status.equalsIgnoreCase(searchKey));

		    //If input status not matching predefined status
		    if (!isValidStatus) {
		        return response = responseWrapper.buildResponse(400, "Invalid SearchKey");
		    } 
			   
			ProductDetailsBOResponse = otsProductService.getCategoryAndSubCategoryByDistributor(getCategorySubCategoryByDistributorRequest);
			if (ProductDetailsBOResponse.getProductDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Available");
			} else {
				response = responseWrapper.buildResponse(200,ProductDetailsBOResponse, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest) {
		Response response = null;
		try {
			if (getCatgeorySubcategoryRequest.getRequest().getSearchKey() == null || getCatgeorySubcategoryRequest.getRequest().getSearchKey().equals("")
					|| getCatgeorySubcategoryRequest.getRequest().getSearchValue() == null || getCatgeorySubcategoryRequest.getRequest().getSearchValue().equals("")
					|| getCatgeorySubcategoryRequest.getRequest().getCountryCode() == null || getCatgeorySubcategoryRequest.getRequest().getCountryCode().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			
			//Predefined user status
			String[] VALID_STATUSES = {"category", "subcategory"};

		    // Validate input user status
		    String SearchKey = getCatgeorySubcategoryRequest.getRequest().getSearchKey();
		    boolean isValidStatus = Arrays.stream(VALID_STATUSES)
		                                  .anyMatch(status -> status.equalsIgnoreCase(SearchKey));

		    //If input status not matching predefined status
		    if (!isValidStatus) {
		        return response = responseWrapper.buildResponse(400, "Invalid SearchKey");
		    } 
			
		    ProductDetailsBOResponse productDetailsBOResponse = otsProductService.getCategoryAndSubCategory(getCatgeorySubcategoryRequest);
			if (productDetailsBOResponse.getProductDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Available");
			} else {
				response = responseWrapper.buildResponse(200,productDetailsBOResponse, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

//	Working Code To get category & subcategory based on country with implicit input 
//	@Override
//	public Response getCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest) {
//		Response response = null;
//		ProductDetailsBOResponse ProductDetailsBOResponse = new ProductDetailsBOResponse();
//		try {
//			if (getCatgeorySubcategoryRequest.getRequest().getSearchKey() == null || getCatgeorySubcategoryRequest.getRequest().getSearchKey().equals("")
//					|| getCatgeorySubcategoryRequest.getRequest().getSearchValue() == null || getCatgeorySubcategoryRequest.getRequest().getSearchValue().equals("")) {
//				return response = buildResponse(400, "Please Enter Required Inputs");
//			}
//			
//			//Predefined user status
//			String[] VALID_STATUSES = {"Category", "Subcategory"};
//
//		    // Validate input user status
//		    String SearchKey = getCatgeorySubcategoryRequest.getRequest().getSearchKey();
//		    boolean isValidStatus = Arrays.stream(VALID_STATUSES)
//		                                  .anyMatch(status -> status.equalsIgnoreCase(SearchKey));
//
//		    //If input status not matching predefined status
//		    if (!isValidStatus) {
//		        return response = responseWrapper.buildResponse(400, "Invalid SearchKey");
//		    } 
//		    
//		    HttpServletRequest request = getCurrentHttpRequest();
//		    // Step 1: Get client IP address from request
//            String clientIp = geoLocationUtil.getClientIpAddress(request);
//            System.out.println("clientIp = "+clientIp);
//
//            // Step 2: Resolve country code using GeoLocationUtil (with caching)
//            String resolvedCountryCode = geoLocationUtil.getCountryCodeFromIp(clientIp);
//            System.out.println("resolvedCountryCode = "+resolvedCountryCode);
//
//            // Step 3: Inject country code into requestData object
//            getCatgeorySubcategoryRequest.getRequest().setCountryCode(resolvedCountryCode);
//			
//			ProductDetailsBOResponse = otsProductService.getCategoryAndSubCategory(getCatgeorySubcategoryRequest);
//			if (ProductDetailsBOResponse.getProductDetails().size() == 0) {
//				response = responseWrapper.buildResponse(404, "No Data Available");
//			} else {
//				response = responseWrapper.buildResponse(200,ProductDetailsBOResponse, "Successful");
//			}
//		} catch (Exception e) {
//			logger.error("Exception while fetching data from DB  :" + e.getMessage());
//			e.printStackTrace();
//			return response = buildResponse(500,"Something Went Wrong");
//		} catch (Throwable e) {
//			logger.error("Exception while fetching data from DB  :" + e.getMessage());
//			e.printStackTrace();
//			return response = buildResponse(500,"Something Went Wrong");
//		}
//		return response;
//	}
	
	@Override
	public Response getParentAndVariantSiblingProducts(String variantProductId) 
	{
		Response response = null;
		ProductDetailsBOResponse ProductDetailsBOResponse = new ProductDetailsBOResponse();
        try {
        	ProductDetailsBOResponse = otsProductService.getParentAndVariantSiblingProducts(variantProductId);
            if (ProductDetailsBOResponse.getProductDetails().size()== 0) {
                response = responseWrapper.buildResponse(404, "No Products Available");
            } else {
            	 response = responseWrapper.buildResponse(200,ProductDetailsBOResponse, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
	}
	
	@Override
	public Response getAttributeKeyBySubcategory(String subcategoryId) 
	{
		Response response = null;
		SubCategoryAttributeResponse subCategoryAttributeResponse = new SubCategoryAttributeResponse();
        try {
        	subCategoryAttributeResponse = otsProductService.getAttributeKeyBySubcategory(subcategoryId);
            if (subCategoryAttributeResponse.getSubcategoryAttributeDetails().size()== 0) {
                response = responseWrapper.buildResponse(404, "No Data Available");
            } else {
            	 response = responseWrapper.buildResponse(200,subCategoryAttributeResponse, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
	}
	
	@Override
	public Response getAttributeValueForAttributeKeyId(String attributeKeyId) 
	{
		Response response = null;
		AttributeValueResponse attributeValueResponse = new AttributeValueResponse();
        try {
        	attributeValueResponse = otsProductService.getAttributeValueForAttributeKeyId(attributeKeyId);
            if (attributeValueResponse.getAttributeValueDetails().size()== 0) {
                response = responseWrapper.buildResponse(404, "No Data Available");
            } else {
            	 response = responseWrapper.buildResponse(200,attributeValueResponse, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
	}
	
	@Override
	public Response getAllAttributeKey() 
	{
		Response response = null;
		AttributeKeyResponse attributeKeyResponse = new AttributeKeyResponse();
        try {
        	attributeKeyResponse = otsProductService.getAllAttributeKey();
            if (attributeKeyResponse.getAttributeKeyDetails().size()== 0) {
                response = responseWrapper.buildResponse(404, "No Data Available");
            } else {
            	 response = responseWrapper.buildResponse(200,attributeKeyResponse, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
	}
	
	@Override
	public Response addAttributeKey(AddAttributeKeyRequest addAttributeKeyRequest) {
		Response response = null;
		try {		
			//Added request validation to check empty string in list
			List<String> names = addAttributeKeyRequest.getRequest().getAttributeKeyName();
			List<String> descriptions = addAttributeKeyRequest.getRequest().getAttributeKeyDiscription();

			if (names == null || descriptions == null ||
			    names.isEmpty() || descriptions.isEmpty() ||
			    names.size() != descriptions.size() ||
			    names.stream().anyMatch(s -> s == null || s.trim().isEmpty()) ||
			    descriptions.stream().anyMatch(s -> s == null || s.trim().isEmpty())) {

			    return buildResponse(400, "Please Enter Required Inputs");
			}

			List<AttributeKey> attributeKey = otsProductService.addAttributeKey(addAttributeKeyRequest);
			if (attributeKey.size() == 0) {
			    response = responseWrapper.buildResponse(404,"Unable to Add Data");
            } else {
            	 response = responseWrapper.buildResponse(200,"Attribute Key Added Successfully", "Successful");
            }  
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addAttributeValue(AddAttributeValueRequest addAttributeValueRequest) {
		Response response = null;
		try {
			if(addAttributeValueRequest.getRequest().get(0).getAttributeValueName() == null || addAttributeValueRequest.getRequest().get(0).getAttributeValueName().equals("")
			  ||addAttributeValueRequest.getRequest().get(0).getAttributeKeyId() == null || addAttributeValueRequest.getRequest().get(0).getAttributeKeyId().equals(""))
			{
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			List<AttributeValueName> checkAttributeValue = otsProductService.checkAttributeValue(addAttributeValueRequest);
			if(checkAttributeValue.size() != 0) {
				return response = responseWrapper.buildResponse(206,checkAttributeValue, "Attribute Value Name Already Exists");
			}else {
				List<AttributeValue> attributeValue = otsProductService.addAttributeValue(addAttributeValueRequest);
				if (attributeValue.size() == 0 ) {
	                response = responseWrapper.buildResponse(404, "Unable to Add Data");
	            } else {
	            	 response = responseWrapper.buildResponse(200,"Attribute Value Added Successfully", "Successful");
	            }
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
		
	}
	
	@Override
	public Response updateAttributeKey(UpdateAttributeKeyRequest updateAttributeKeyRequest) {
		Response response = null;
		try {
			if (updateAttributeKeyRequest.getRequest().getAttributeKeyId() == null|| updateAttributeKeyRequest.getRequest().getAttributeKeyId().equals("")
				|| updateAttributeKeyRequest.getRequest().getAttributeKeyName()== null|| updateAttributeKeyRequest.getRequest().getAttributeKeyName().equals("")
				|| updateAttributeKeyRequest.getRequest().getAttributeKeyDescription()== null|| updateAttributeKeyRequest.getRequest().getAttributeKeyName().equals("")) {
				return response = responseWrapper.buildResponse(400, "Please Enter Required Inputs");
			}
			String Responsevalue = otsProductService.updateAttributeKey(updateAttributeKeyRequest);
			
			if (Responsevalue.equalsIgnoreCase("This Attribute Key Name Already Exists")) {
				response = buildResponse(206, "This Attribute Key Name Already Exists");
			}
			else if(Responsevalue.equalsIgnoreCase("This Attribute Key Updated Successfully")) {
				response = buildResponse(200, "This Attribute Key Updated Successfully");
			}
			else if(Responsevalue.equalsIgnoreCase("NotUpdated")) {
				response = buildResponse(205, "Not Updated");
			}
			else {
				response = buildResponse(408,"Unexpected Response");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response deleteAttributeKey(String attributeKeyId) {
		Response response = null;
		try {
			String Responsevalue = otsProductService.deleteAttributeKey(attributeKeyId);

			if (Responsevalue.equalsIgnoreCase("This Attribute Key Cannot Be Removed Due To Its Association With Mapped Products")) {
				response = buildResponse(206,"This Attribute Key Cannot Be Removed Due To Its Association With Mapped Products");
			} 
			else if (Responsevalue.equalsIgnoreCase("This Attribute Key Deleted Successfully")) {
				response = buildResponse(200, "This Attribute Key Deleted Successfully");
			}
			else if (Responsevalue.equalsIgnoreCase("Attribute Key does not exist")) {
			    response = buildResponse(206, "Attribute Key does not exist");
			}
			else {
				response = buildResponse(408, "Unexpected Response");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response updateAttributeValue(UpadteAttributeValueRequest upadteAttributeValueRequest) {
		Response response = null;
		try {
			if (upadteAttributeValueRequest.getRequest().getAttributeValueId() == null|| upadteAttributeValueRequest.getRequest().getAttributeValueId().equals("")
				|| upadteAttributeValueRequest.getRequest().getAttributeValueName()== null|| upadteAttributeValueRequest.getRequest().getAttributeValueName().equals("")) {
				return response = responseWrapper.buildResponse(400, "Please Enter Required Inputs");
			}
			String Responsevalue = otsProductService.updateAttributeValue(upadteAttributeValueRequest);
			
			if (Responsevalue.equalsIgnoreCase("This Attribute Value Name Already Exists")) {
				response = buildResponse(206, "This Attribute Value Name Already Exists");
			}else if(Responsevalue.equalsIgnoreCase("This Attribute Value Name Updated Successfully")) {
				response = buildResponse(200, "This Attribute Value Name Updated Successfully");
				
			}else if(Responsevalue.equalsIgnoreCase("NotUpdated")) {
				response = buildResponse(205, "Not Updated");
			}else {
				response = buildResponse(408,"Unexpected Response");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addProductAttributesMapping(AddProductAttributeMappingRequest addProductAttributeMappingRequest) {
		Response response= null;
		try {
			if(addProductAttributeMappingRequest.getRequest().get(0).getAttributeKeyId()== null ||addProductAttributeMappingRequest.getRequest().get(0).getAttributeKeyId().equals("")
			|| addProductAttributeMappingRequest.getRequest().get(0).getAttributeValueId()== null || addProductAttributeMappingRequest.getRequest().get(0).getAttributeValueId().equals("")
			|| addProductAttributeMappingRequest.getRequest().get(0).getProductId() == null ||addProductAttributeMappingRequest.getRequest().get(0).getProductId().equals("") ) {
				return response = responseWrapper.buildResponse(400, "Please Enter Required Inputs");
			}
			List<ProductAttributesMapping> productmapping = otsProductService.addProductAttributesMapping(addProductAttributeMappingRequest);
			if (productmapping.size() == 0) {
			    response = responseWrapper.buildResponse(404,"Unable To Add Data");
	        } else {
	        	 response = responseWrapper.buildResponse(200,"Data Inserted Successfully", "Successful");
	        }  	
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
    }
	
	@Override
	public Response getParentAndChildProductsByChildID(String variantProductId) {
		Response response = null;
		ProductDetailsBOResponse ProductDetailsBOResponse = new ProductDetailsBOResponse();
        try {
        	ProductDetailsBOResponse = otsProductService.getParentAndChildProductsByChildID(variantProductId);
            if (ProductDetailsBOResponse.getProductDetails().size()== 0 ) {
                response = responseWrapper.buildResponse(404, "No Products Available");
            }else {
            	response = responseWrapper.buildResponse(200,ProductDetailsBOResponse, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
	}
	
	
	@Override
	public Response getAllAttributeKeysAndValues() {
		Response response =null;
		GetAttributeKeysAndAttributeValuesResponse getAttributeKeyAndValue = new GetAttributeKeysAndAttributeValuesResponse();
		try {
			getAttributeKeyAndValue = otsProductService.getAllAttributeKeysAndValues();
			if(getAttributeKeyAndValue == null) {
				response = responseWrapper.buildResponse(404,"No data available");
			}
			else{
				response = responseWrapper.buildResponse(200,getAttributeKeyAndValue,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response deleteAttributeValue(String attributeValueId) {
		Response response = null;
		try {
			String Responsevalue = otsProductService.deleteAttributeValue(attributeValueId);

			if (Responsevalue.equalsIgnoreCase("This Attribute Value Cannot Be Removed Due To Its Association With Mapped Products")) {
				response = buildResponse(206,"This Attribute Value Cannot Be Removed Due To Its Association With Mapped Products");
			} 
			else if (Responsevalue.equalsIgnoreCase("Attribute Value Deleted Successfully")) {
				response = buildResponse(200, "Attribute Value Deleted Successfully");
		    }
		    else if (Responsevalue.equalsIgnoreCase("Attribute Value does not exist")) {
		    	response = buildResponse(206, "Attribute Value does not exist");
		    }
			else {
				response = buildResponse(408, "Unexpected Response");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getParentProductforDistributor(String distributerId) {
		Response response = null;
		ProductDetailsBOResponse ProductDetailsBOResponse = new ProductDetailsBOResponse();
        try {
        	ProductDetailsBOResponse = otsProductService.getParentProductforDistributor(distributerId);
            if (ProductDetailsBOResponse.getProductDetails().size()== 0 ) {
                response = responseWrapper.buildResponse(404, "No Products Available For This Distributor");
            } else {
            	 response = responseWrapper.buildResponse(ProductDetailsBOResponse, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
	}
	
	@Override
	public Response getUnMappedAttributeKeyandValuesForSubcategory(String subcategoryId) {
		Response response = null;
		AttributeKeyResponse attributeKeyResponse = new AttributeKeyResponse();
        try {
        	attributeKeyResponse = otsProductService.getUnMappedAttributeKeyandValuesForSubcategory(subcategoryId);
            if (attributeKeyResponse.getAttributeKeyDetails().size()== 0) {
                response = responseWrapper.buildResponse(404, "No Data Available");
            } else {
            	response = responseWrapper.buildResponse(200,attributeKeyResponse, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
	}
	
	@Override
	public Response addAttributeKeyForSubcategory(AddAttributeKeyForSubCategoryRequest addAttributeKeyForSubCategoryRequest) {
		Response response = null;
		try {
			if(addAttributeKeyForSubCategoryRequest.getRequest().getOtsAttributeKeyId().size() == 0
			  || addAttributeKeyForSubCategoryRequest.getRequest().getOtsSubcategoryId() == null || addAttributeKeyForSubCategoryRequest.getRequest().getOtsSubcategoryId().equals("")) 
			{
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			List<SubCategoryAttributeMapping> addAttributeKeyForSubcategory = otsProductService.addAttributeKeyForSubcategory(addAttributeKeyForSubCategoryRequest);
			if (addAttributeKeyForSubcategory.size() == 0) {
			    response = responseWrapper.buildResponse(404,"Unable to Add Data");
            } else {
            	 response = responseWrapper.buildResponse(200,"Attribute Key Added For SubCategory Successfully", "Successful");
            }  
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response deleteAttributeKeyMappedToSubcategory(String subcategoryAttributeMappingId) {
		Response response = null;
		try {
			String responseValue = otsProductService.deleteAttributeKeyMappedToSubcategory(subcategoryAttributeMappingId);

			if (responseValue.equalsIgnoreCase("Id Does Not Exist")) {
				response = responseWrapper.buildResponse(404,responseValue);
			} else if (responseValue.equalsIgnoreCase("Deleted Successfully")) {
				response = responseWrapper.buildResponse(200, responseValue,"Successful");
			} else {
				response = responseWrapper.buildResponse(404, "Unexpected Response");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}

	@Override
	public Response getAttributeKeyValueBySubcategory(String subcategoryId) 
	{
		Response response = null;
		SubCategoryAttributeResponse subCategoryAttributeResponse = new SubCategoryAttributeResponse();
        try {
        	subCategoryAttributeResponse = otsProductService.getAttributeKeyValueBySubcategory(subcategoryId);
            if (subCategoryAttributeResponse.getSubcategoryAttributeDetails().size()== 0) {
                response = responseWrapper.buildResponse(404, "No Data Available");
            } else {
            	 response = responseWrapper.buildResponse(200,subCategoryAttributeResponse, "Successful");
            }
        }catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
        return response;
	}
	
	@Override
	public Response getProductAndVarientsByStatus(String status) {
		Response response = null;
		try {
			List<ProductDetails> ProductDetails = otsProductService.getProductAndVarientsByStatus(status);
			if(ProductDetails.size()==0) {
	            response = responseWrapper.buildResponse(404,"No Products Available");
            }else {
            	response = responseWrapper.buildResponse(200,ProductDetails,"Successful");
            }
	        return response;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		}
	}
	
	@Override
	public Response getSiblingVariantProductsByPrimaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest) {
		Response response = null;
		try {
			if(getSiblingVariantProductsByAttributeRequest.getRequest().getPrimaryAttributeKey()==null ||getSiblingVariantProductsByAttributeRequest.getRequest().getPrimaryAttributeKey().equals("")
				||getSiblingVariantProductsByAttributeRequest.getRequest().getProductId()== null || getSiblingVariantProductsByAttributeRequest.getRequest().getProductId().equals(""))
			{
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			List<ProductDetails> ProductDetails = otsProductService.getSiblingVariantProductsByPrimaryKey(getSiblingVariantProductsByAttributeRequest);
			if(ProductDetails.size()==0) {
	            response = responseWrapper.buildResponse(404,"No Products Available");
            }else {
            	response = responseWrapper.buildResponse(200,ProductDetails,"Successful");
            }
	        return response;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		}
	}
	
	@Override
	public Response getSiblingVariantProductsBySecondaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest) {
		Response response = null;
		try {
			if(getSiblingVariantProductsByAttributeRequest.getRequest().getPrimaryAttributeKey()==null ||getSiblingVariantProductsByAttributeRequest.getRequest().getPrimaryAttributeKey().equals("")
					||getSiblingVariantProductsByAttributeRequest.getRequest().getProductId()== null || getSiblingVariantProductsByAttributeRequest.getRequest().getProductId().equals("")
					||getSiblingVariantProductsByAttributeRequest.getRequest().getPrimaryAttributeValue()== null || getSiblingVariantProductsByAttributeRequest.getRequest().getPrimaryAttributeValue().equals("")
					||getSiblingVariantProductsByAttributeRequest.getRequest().getSecondaryAttributeKey()== null || getSiblingVariantProductsByAttributeRequest.getRequest().getSecondaryAttributeKey().equals(""))
			{
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
	
	        ProductDetails productDetails = otsProductService.getProductDetails(getSiblingVariantProductsByAttributeRequest.getRequest().getProductId());
	        if (productDetails == null || productDetails.getProductId() == null) {
	            return responseWrapper.buildResponse(404, "Invalid Product ID");
	        }
			List<ProductDetails> ProductDetails = otsProductService.getSiblingVariantProductsBySecondaryKey(getSiblingVariantProductsByAttributeRequest);
			if(ProductDetails.size()==0) {
	            response = responseWrapper.buildResponse(404,"No Products Available");
            }else {
            	response = responseWrapper.buildResponse(200,ProductDetails,"Successful");
            }
	        return response;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        return response = buildResponse(500,"Something Went Wrong");
		}
	}
	
	@Override
	public Response filterProductsByGeneralProperties(FilterProductsByGeneralPropertiesRequest filterProductsByGeneralPropertiesRequest) {
		Response response = null;
		try {
			List<ProductDetails> productDetails = otsProductService.filterProductsByGeneralProperties(filterProductsByGeneralPropertiesRequest);

			if (productDetails.size() == 0) {
				response = responseWrapper.buildResponse(404, "No Products Available");
			} else {
				response = responseWrapper.buildResponse(200, productDetails, "Successful");
			}
			return response;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB: " + e.getMessage());
			return buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB: " + e.getMessage());
			return buildResponse(500, "Something Went Wrong");
		}
	}

	@Override
	public Response getProductsBySubCategoryAndDistributor(GetProductsBySubCategoryAndDistributorRequest getProductsBySubCategoryAndDistributorRequest) {
		Response response = null;
		try {
			if (getProductsBySubCategoryAndDistributorRequest.getRequest().getSubcategoryId() ==null ||getProductsBySubCategoryAndDistributorRequest.getRequest().getSubcategoryId().equals("")
				|| getProductsBySubCategoryAndDistributorRequest.getRequest().getDistributorId()== null || getProductsBySubCategoryAndDistributorRequest.getRequest().getDistributorId().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			ProductDetailsBOResponse productDetailsBOResponse = otsProductService.getProductsBySubCategoryAndDistributor(getProductsBySubCategoryAndDistributorRequest);
			if (productDetailsBOResponse.getProductDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Available");
			} else {
				response = responseWrapper.buildResponse(200,productDetailsBOResponse, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getVariantsByProductId(String productId) {
		Response response = null;
		try {
			List<ProductDetails> productDetails = otsProductService.getVariantsByProductId(productId);

			if (productDetails.size() == 0) {
				response = responseWrapper.buildResponse(404, "No Products Available");
			} else {
				response = responseWrapper.buildResponse(200, productDetails, "Successful");
			}
			return response;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB: " + e.getMessage());
			return buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB: " + e.getMessage());
			return buildResponse(500, "Something Went Wrong");
		}
	}
	
	
	@Override
	public Response getVariantsProductByDistributor(String distributorId) {
		Response response = null;
		try {
			List<ProductDetails> productDetails = otsProductService.getVariantsProductByDistributor(distributorId);

			if (productDetails.size() == 0) {
				response = responseWrapper.buildResponse(404, "No Products Available");
			} else {
				response = responseWrapper.buildResponse(200, productDetails, "Successful");
			}
			return response;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB: " + e.getMessage());
			return buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB: " + e.getMessage());
			return buildResponse(500, "Something Went Wrong");
		}
	}
	
	@Override
	public Response getPageloaderRecentlyAddedProductList(String levelid) {
		Response response = null;
		try {
			ProductDetailsPageloaderResponse productList = otsProductService.getPageloaderRecentlyAddedProductList(levelid);
			if(productList.getProductDetails().isEmpty()) {			
				response = responseWrapper.buildResponse(404,"No Products Available");
			}else {
				response = responseWrapper.buildResponse(200,productList,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}	
		return response;
	}
	
	@Override
	public Response getPageloaderCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest){
		Response response = null;
		ProductDetailsPageloaderResponse productDetailsResponse = new ProductDetailsPageloaderResponse();
		try {
			if (getCatgeorySubcategoryRequest.getRequest().getSearchKey() == null || getCatgeorySubcategoryRequest.getRequest().getSearchKey().equals("")
					|| getCatgeorySubcategoryRequest.getRequest().getSearchValue() == null || getCatgeorySubcategoryRequest.getRequest().getSearchValue().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			
			//Predefined user status
			String[] VALID_STATUSES = {"category", "subcategory"};

		    // Validate input user status
		    String SearchKey = getCatgeorySubcategoryRequest.getRequest().getSearchKey();
		    boolean isValidStatus = Arrays.stream(VALID_STATUSES)
		                                  .anyMatch(status -> status.equalsIgnoreCase(SearchKey));

		    //If input status not matching predefined status
		    if (!isValidStatus) {
		        return response = responseWrapper.buildResponse(400, "Invalid SearchKey");
		    } 
			
		    productDetailsResponse = otsProductService.getPageloaderCategoryAndSubCategory(getCatgeorySubcategoryRequest);
			if (productDetailsResponse.getProductDetails().isEmpty()) {
				response = responseWrapper.buildResponse(404, "No Data Available");
			} else {
				response = responseWrapper.buildResponse(200,productDetailsResponse, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addDuplicateProductByCountry(AddProductByCountryRequest addProductByCountryRequest) {
		Response response = null;
		try {
			if(addProductByCountryRequest.getRequest().getProductId()==null ||addProductByCountryRequest.getRequest().getProductId().equals("")
				|| addProductByCountryRequest.getRequest().getProductSellerPrice()== null || addProductByCountryRequest.getRequest().getProductSellerPrice().equals("")
				|| addProductByCountryRequest.getRequest().getProductDiscountPercentage()== null || addProductByCountryRequest.getRequest().getProductDiscountPercentage().equals("")
				|| addProductByCountryRequest.getRequest().getProductDiscountPrice()== null || addProductByCountryRequest.getRequest().getProductDiscountPrice().equals("")
				|| addProductByCountryRequest.getRequest().getProductBasePrice()== null || addProductByCountryRequest.getRequest().getProductBasePrice().equals("")
				|| addProductByCountryRequest.getRequest().getGst()== null || addProductByCountryRequest.getRequest().getGst().equals("")
				|| addProductByCountryRequest.getRequest().getGstPrice()== null || addProductByCountryRequest.getRequest().getGstPrice().equals("")
				|| addProductByCountryRequest.getRequest().getProductDeliveryCharge()== null || addProductByCountryRequest.getRequest().getProductDeliveryCharge().equals("")
				|| addProductByCountryRequest.getRequest().getProductReturnDeliveryCharge()== null || addProductByCountryRequest.getRequest().getProductReturnDeliveryCharge().equals("")
				|| addProductByCountryRequest.getRequest().getProductFinalPrice()== null || addProductByCountryRequest.getRequest().getProductFinalPrice().equals("")
				|| addProductByCountryRequest.getRequest().getProductPrice()== null || addProductByCountryRequest.getRequest().getProductPrice().equals("")
				|| addProductByCountryRequest.getRequest().getProductCancellationAvailability()== null || addProductByCountryRequest.getRequest().getProductCancellationAvailability().equals("")
				|| addProductByCountryRequest.getRequest().getProductCancellationPolicy()== null || addProductByCountryRequest.getRequest().getProductCancellationPolicy().equals("")
				|| addProductByCountryRequest.getRequest().getProductReplacementAvailability()== null || addProductByCountryRequest.getRequest().getProductReplacementAvailability().equals("")
				|| addProductByCountryRequest.getRequest().getProductReplacementPolicy()== null || addProductByCountryRequest.getRequest().getProductReplacementPolicy().equals("")
				|| addProductByCountryRequest.getRequest().getProductReplacementDays()== null
				|| addProductByCountryRequest.getRequest().getProductReturnAvailability()== null || addProductByCountryRequest.getRequest().getProductReturnAvailability().equals("")
				|| addProductByCountryRequest.getRequest().getProductReturnPolicy()== null || addProductByCountryRequest.getRequest().getProductReturnPolicy().equals("")
				|| addProductByCountryRequest.getRequest().getProductReturnDays()== null
				|| addProductByCountryRequest.getRequest().getOtsTimeToShip()== null || addProductByCountryRequest.getRequest().getOtsTimeToShip().equals("")
				|| addProductByCountryRequest.getRequest().getOtsTimeToDeliver()== null || addProductByCountryRequest.getRequest().getOtsTimeToDeliver().equals("")
				|| addProductByCountryRequest.getRequest().getOtsSellerPickupReturn()== null
				|| addProductByCountryRequest.getRequest().getOtsCodAvailability()== null
				|| addProductByCountryRequest.getRequest().getOtsProductCountry()== null || addProductByCountryRequest.getRequest().getOtsProductCountry().equals("")
				|| addProductByCountryRequest.getRequest().getOtsProductCountryCode()== null || addProductByCountryRequest.getRequest().getOtsProductCountryCode().equals("")
				|| addProductByCountryRequest.getRequest().getOtsProductCurrency()== null || addProductByCountryRequest.getRequest().getOtsProductCurrency().equals("")
				|| addProductByCountryRequest.getRequest().getOtsProductCurrencySymbol()== null || addProductByCountryRequest.getRequest().getOtsProductCurrencySymbol().equals("")
				|| addProductByCountryRequest.getRequest().getProductStockQuantity()== null || addProductByCountryRequest.getRequest().getProductStockQuantity().equals(""))
			{
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			String addProduct = otsProductService.addDuplicateProductByCountry(addProductByCountryRequest);
			if(addProduct.equalsIgnoreCase("Inserted Product Successfully")) {			
				response = responseWrapper.buildResponse(200,addProduct,"Successful");
			}else if(addProduct.equalsIgnoreCase("Invalid Product")) {
				response = responseWrapper.buildResponse(404,addProduct);
			}
			else {
				response = responseWrapper.buildResponse(404,"Product Not Inserted");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}	
		return response;
	}
	
	@Override
    public Response addOrUpdateProduct(AddOrUpdateProductRequest addOrUpdateProductRequest) {
    	Response response = null;
        try {
        	if (addOrUpdateProductRequest.getRequest() == null) {
    			return response = buildResponse(400, "Please Enter Required Inputs");
    		}
            String addOrUpdateProduct = otsProductService.addOrUpdateProduct(addOrUpdateProductRequest);

            if (addOrUpdateProduct == null) {
            	response = responseWrapper.buildResponse(404, "Data Not Updated");
			} else if (addOrUpdateProduct.equalsIgnoreCase("Please Enter Required Inputs")) {
				response = responseWrapper.buildResponse(400, "Please Enter Required Inputs");
			}  else if (addOrUpdateProduct.equalsIgnoreCase("Invalid Input for ProductLevelId")) {
				response = responseWrapper.buildResponse(400, "Invalid Input for ProductLevelId");
			}  else if (addOrUpdateProduct.equalsIgnoreCase("Please Enter ProductReplacementDays Field")) {
				response = responseWrapper.buildResponse(400, "Please Enter ProductReplacementDays Field");
			}  else if (addOrUpdateProduct.equalsIgnoreCase("Please Enter ProductReturnDays Field")) {
				response = responseWrapper.buildResponse(400, "Please Enter ProductReturnDays Field");
			} else if (addOrUpdateProduct.equalsIgnoreCase("Invalid Product Status")) {
				response = responseWrapper.buildResponse(400, "Invalid Product Status");
			} else {
				response = responseWrapper.buildResponse(200, addOrUpdateProduct, "Successful");
			}
            return response;
		} catch (Exception e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		}
	}
	
	@Override
    public Response getPageLoaderDetails(String countryCode) {
    	Response response = null;
        try {
        	GetPageLoaderResponse getPageLoaderResponse = otsProductService.getPageLoaderDetails(countryCode);
            if (getPageLoaderResponse == null) {
            	response = responseWrapper.buildResponse(404, "No Data Found");
			}else {
				response = responseWrapper.buildResponse(200, getPageLoaderResponse, "Successful");
			}
            return response;
		} catch (Exception e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while inserting data into DB :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		}
	}
	
	@Override
	public Response addOrUpdateCategoryAndSubcategory(AddOrUpdateCategoryRequest addOrUpdateCategoryRequest) {
		Response response = null;
		try {
			if(addOrUpdateCategoryRequest.getRequest().getProductId() == null || addOrUpdateCategoryRequest.getRequest().getProductId().isEmpty()) {
				// ADD Flow
				if (addOrUpdateCategoryRequest.getRequest().getProductName() == null || addOrUpdateCategoryRequest.getRequest().getProductName().trim().isEmpty()
					|| addOrUpdateCategoryRequest.getRequest().getProductLevelId() == null  || addOrUpdateCategoryRequest.getRequest().getProductLevelId().trim().isEmpty()
					|| addOrUpdateCategoryRequest.getRequest().getCreatedUser() == null || addOrUpdateCategoryRequest.getRequest().getCreatedUser().trim().isEmpty()
					|| addOrUpdateCategoryRequest.getRequest().getProductImage() == null || addOrUpdateCategoryRequest.getRequest().getProductImage().trim().isEmpty()) {
					return response = buildResponse(400, "Please Enter Required Inputs");
				}	
				
				//Need to provide Category Id while Adding SubCategory
				if(addOrUpdateCategoryRequest.getRequest().getProductLevelId().equalsIgnoreCase("2")) {
					if(addOrUpdateCategoryRequest.getRequest().getCategoryId() == null || addOrUpdateCategoryRequest.getRequest().getCategoryId().trim().isEmpty()){
						return response = buildResponse(400, "Please Provide Category ID");
					}
				}
				
				//Request validation for adding Nutritional flag only for Category
				if(addOrUpdateCategoryRequest.getRequest().getProductLevelId().equalsIgnoreCase("1")) {
					//Predefined Nutritional Flag values
					String[] VALID_FLAG = {"true", "false", "1","0"};

				    // Validate input Flag
				    String nutritionalFlag = addOrUpdateCategoryRequest.getRequest().getNutritionalFlag();
				    boolean isValidnutritionalFlag = Arrays.stream(VALID_FLAG).anyMatch(flag -> flag.equalsIgnoreCase(nutritionalFlag));

				    //If input status not matching predefined status
				    if (!isValidnutritionalFlag) {
				        return response = responseWrapper.buildResponse(400, "Invalid Input for Nutritional Flag");
				    } 
				}
				
			}else {
				// UPDATE Flow
				if (addOrUpdateCategoryRequest.getRequest().getProductName() == null || addOrUpdateCategoryRequest.getRequest().getProductName().trim().isEmpty()
					|| addOrUpdateCategoryRequest.getRequest().getProductImage() == null || addOrUpdateCategoryRequest.getRequest().getProductImage().trim().isEmpty()
					|| addOrUpdateCategoryRequest.getRequest().getProductId() == null || addOrUpdateCategoryRequest.getRequest().getProductId().trim().isEmpty()) {
					return response = buildResponse(400, "Please Enter Required Inputs");
				}
			}
			
			String addOrUpdateCategoryAndSubcategory = otsProductService.addOrUpdateCategoryAndSubcategory(addOrUpdateCategoryRequest);
			if (addOrUpdateCategoryAndSubcategory == null) {
				return response = responseWrapper.buildResponse(404, "Data Not Updated");
			}else if(addOrUpdateCategoryAndSubcategory.equalsIgnoreCase("Invalid Admin Id")){
				return response = responseWrapper.buildResponse(400, "Invalid Admin Id");
			}else {
				return response = responseWrapper.buildResponse(200, addOrUpdateCategoryAndSubcategory, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while inserting data into DB :", e);
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Unexpected error while inserting data into DB :", e);
			return response = buildResponse(500, "Something Went Wrong");
		}
	}

	@Override
	public Response searchProductByNamePagination(SearchProductByNamePaginationRequest searchProductRequest) {
		Response response = null;
		try {
			if (searchProductRequest.getRequest().getProductName() ==null ||searchProductRequest.getRequest().getProductName().equals("")
					|| searchProductRequest.getRequest().getDataSize()== null || searchProductRequest.getRequest().getDataSize().equals("")
					|| searchProductRequest.getRequest().getPageNumber()== null || searchProductRequest.getRequest().getPageNumber().equals("")
					|| searchProductRequest.getRequest().getProductCountryCode()== null || searchProductRequest.getRequest().getProductCountryCode().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			
			ProductSearchResponse productList = otsProductService.searchProductByNamePagination(searchProductRequest);
			if(productList == null) {			
				response = responseWrapper.buildResponse(404,"No Products Available");
			}else {
				response = responseWrapper.buildResponse(200,productList,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}	
		return response;
	}
	
	@Override
	public Response getProductsBySubCategoryWithIntermediateStatus(String subcategoryId) {
		Response response = null;
		try {
			ProductDetailsBOResponse productDetailsBOResponse = otsProductService.getProductsBySubCategoryWithIntermediateStatus(subcategoryId);
			if (productDetailsBOResponse.getProductDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Available");
			} else {
				response = responseWrapper.buildResponse(200,productDetailsBOResponse, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getCategoryAndSubCategoryWithAttribute(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest) {
		Response response = null;
		try {
			if (getCatgeorySubcategoryRequest.getRequest().getSearchKey() == null || getCatgeorySubcategoryRequest.getRequest().getSearchKey().equals("")
					|| getCatgeorySubcategoryRequest.getRequest().getSearchValue() == null || getCatgeorySubcategoryRequest.getRequest().getSearchValue().equals("")) {
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			
			//Predefined user status
			String[] VALID_STATUSES = {"category", "subcategory"};

		    // Validate input user status
		    String SearchKey = getCatgeorySubcategoryRequest.getRequest().getSearchKey();
		    boolean isValidStatus = Arrays.stream(VALID_STATUSES)
		                                  .anyMatch(status -> status.equalsIgnoreCase(SearchKey));

		    //If input status not matching predefined status
		    if (!isValidStatus) {
		        return response = responseWrapper.buildResponse(400, "Invalid SearchKey");
		    } 
			
		    ProductDetailsBOResponse productDetailsBOResponse = otsProductService.getCategoryAndSubCategoryWithAttribute(getCatgeorySubcategoryRequest);
			if (productDetailsBOResponse.getProductDetails().size() == 0) {
				response = responseWrapper.buildResponse(404, "No Data Available");
			} else {
				response = responseWrapper.buildResponse(200,productDetailsBOResponse, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getSimilarProducts(GetSimilarProductRequest getSimilarProductRequest) {
		Response response = null;
		try {
			if(getSimilarProductRequest.getRequest().getSearchKey() == null || getSimilarProductRequest.getRequest().getSearchKey().trim().isEmpty()
					|| getSimilarProductRequest.getRequest().getSearchValue() == null || getSimilarProductRequest.getRequest().getSearchValue().trim().isEmpty()) {
				
				return response =buildResponse(400, "Please Enter Required Inputs");
			}
			//Predefined Search Keys
			String[] VALID_KEYS = {"manufacturerName", "oemPartNumber"};
	
		    // Validate input user status
		    String SearchKey = getSimilarProductRequest.getRequest().getSearchKey();
		    boolean isValidKey = Arrays.stream(VALID_KEYS)
		                                  .anyMatch(status -> status.equalsIgnoreCase(SearchKey));
	
		    //If input status not matching predefined status
		    if (!isValidKey) {
		        return response = responseWrapper.buildResponse(400, "Invalid SearchKey");
		    } 
			
		    ProductDetailsBOResponse getSimilarProductResponse = otsProductService.getSimilarProducts(getSimilarProductRequest);
			if(getSimilarProductResponse.getProductDetails().size() <= 0) {
				return response = responseWrapper.buildResponse(404, "No Data Available");
			}else {
				return response = responseWrapper.buildResponse(200,getSimilarProductResponse, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
	}
	
	@Override
	public Response addOrUpdateProductManufacturerDetails(AddProductManufacturerRequest addProductManufacturerRequest) {
		Response response = null;
		try {
			if(addProductManufacturerRequest.getRequest().getManufacturerName() == null || addProductManufacturerRequest.getRequest().getManufacturerName().equals("")){
				return response = responseWrapper.buildResponse(400,"Please Enter Manufacturer Name");
			}
			String responseValue = otsProductService.addOrUpdateProductManufacturerDetails(addProductManufacturerRequest);
			if(responseValue==null) {
				response = responseWrapper.buildResponse(404,"Not Inserted");
			}else if(responseValue.equalsIgnoreCase("Manufacturer Name Already Exists")){
				response = responseWrapper.buildResponse(404,"Manufacturer Name Already Exists");
			}else {
				response = responseWrapper.buildResponse(200,responseValue,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response deleteManufacturerDetails(String productManufacturerId) {
		Response response = null;
		try {
			String deleteManufacturers = otsProductService.deleteManufacturerDetails(productManufacturerId);
			if(deleteManufacturers == null ) {
				response = buildResponse(404,"Manufacturers Details Not Deleted");
			}else {
				response = buildResponse(200,"Manufacturers Details Deleted");
			}
		}catch(Exception e) {
			logger.error("Exception while fetching data from DB:"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}catch (Throwable e) {
			logger.error("Exception while fetching data from DB:"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response getAllManufacturerDetails() {
		Response response =null;
		try {
			ProductManufacturersResponse productManufacturersResponse = otsProductService.getAllManufacturerDetails();
			if(productManufacturersResponse.getProductManufacturerDetails().size() == 0) {
				response = responseWrapper.buildResponse(404,"No Data Available");
			}else
			{
				response = responseWrapper.buildResponse(200,productManufacturersResponse,"Successful");
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			return response = buildResponse(500,"Something Went Wrong");
		}
		return response;
	}
	
	@Override
	public Response addVariantProduct(AddVariantProductRequest addVariantProductRequest) {
		Response response = null;
		try {
			if (addVariantProductRequest.getRequest().getProductId() == null || addVariantProductRequest.getRequest().getProductId().equals("")
					|| addVariantProductRequest.getRequest().getProductStockQuantity() == null || addVariantProductRequest.getRequest().getProductStockQuantity().equals("")
					|| addVariantProductRequest.getRequest().getProductName() == null || addVariantProductRequest.getRequest().getProductName().equals("")
					|| addVariantProductRequest.getRequest().getProductBasePrice() == null || addVariantProductRequest.getRequest().getProductBasePrice().equals("")
					|| addVariantProductRequest.getRequest().getProductSellerPrice() == null || addVariantProductRequest.getRequest().getProductSellerPrice().equals("")					
					|| addVariantProductRequest.getRequest().getProductDiscountPercentage() == null || addVariantProductRequest.getRequest().getProductDiscountPercentage().equals("")
					|| addVariantProductRequest.getRequest().getProductDiscountPrice()== null || addVariantProductRequest.getRequest().getProductDiscountPrice().equals("")
					|| addVariantProductRequest.getRequest().getProductImage() == null || addVariantProductRequest.getRequest().getProductImage().equals("")
					|| addVariantProductRequest.getRequest().getProductPrice()== null || addVariantProductRequest.getRequest().getProductPrice().equals("")
					|| addVariantProductRequest.getRequest().getGst()== null || addVariantProductRequest.getRequest().getGst().equals("")
					|| addVariantProductRequest.getRequest().getGstPrice() == null || addVariantProductRequest.getRequest().getGstPrice().equals("")
					|| addVariantProductRequest.getRequest().getProductPercentagePrice()== null || addVariantProductRequest.getRequest().getProductPercentagePrice().equals("")					
				    || addVariantProductRequest.getRequest().getProductFinalPriceWithGst() == null || addVariantProductRequest.getRequest().getProductFinalPriceWithGst().equals("") 				
				    || addVariantProductRequest.getRequest().getProductDeliveryCharge()== null || addVariantProductRequest.getRequest().getProductDeliveryCharge().equals("")		
					|| addVariantProductRequest.getRequest().getProductReturnDeliveryCharge()== null || addVariantProductRequest.getRequest().getProductReturnDeliveryCharge().equals("")) {
						
				return response = buildResponse(400, "Please Enter Required Inputs");
			}
			String variantId = otsProductService.addVarientProduct(addVariantProductRequest);
			if (variantId == null) {
				response = responseWrapper.buildResponse(404, "Variant Product Not Added");
			} else {
				response = responseWrapper.buildResponse(200, variantId, "Successful");
			}
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			return response = buildResponse(500, "Something Went Wrong");
		}
		return response;
	}

	
	
}

