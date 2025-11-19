package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuso.enterprise.ots.srv.api.model.domain.AddProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.CategoryDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductCategoryMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductManufactureDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductPolicy;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductPricingDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.SubCategoryDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSUserService;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateCategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductByCountryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.FilterProductsByGeneralPropertiesRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCategorySubCategoryByDistributorRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCatgeorySubcategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductsByDistributerPaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductsBySubCategoryAndDistributorRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetSiblingVariantProductsByAttributeRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetSimilarProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ProductDetailsBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.SearchProductByNamePaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.response.AverageReviewRatingResponse;
import com.fuso.enterprise.ots.srv.api.service.response.GetProductBOStockResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ProductDetailsBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ProductSearchResponse;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ProductCategoryMappingDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductStockDao;
import com.fuso.enterprise.ots.srv.server.dao.UserServiceDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProductLevel;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers;
import com.fuso.enterprise.ots.srv.server.model.entity.Useraccounts;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ProductServiceDAOImpl extends AbstractIptDao<OtsProduct, String> implements ProductServiceDAO {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${ots.product.number.format}")
	public String productNoFormat;

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private OTSUserService otsUserService;
	
	@Autowired
	private ProductStockDao productStockDao;
	
	@Autowired
	private UserServiceDAO userServiceDAO;
	
	@Autowired
	private ProductCategoryMappingDAO productCategoryMappingDAO;
	
    public ProductServiceDAOImpl() {
		super(OtsProduct.class);
    }
    
    private String getValueOrNull(String value) {
	    return (value == null || value.equals("")) ? null : value;
	}
	
	private BigDecimal getBigDecimalOrNull(String value) {
	    return (value == null || value.equals("")) ? null : new BigDecimal(value);
	}
	
	private Boolean getBooleanOrNull(String value) {
	    return (value == null || value.equals("")) ? null : new Boolean(value);
	}
	
	private boolean convertToBoolean(String value) {
	    return value != null && (value.equalsIgnoreCase("true") || value.equals("1"));
	}
	
	@Override
	public ProductDetailsBOResponse getProductList(ProductDetailsBORequest productDetailsBORequest) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		String searchKey=productDetailsBORequest.getRequestData().getSearchKey();
		String seachValue=productDetailsBORequest.getRequestData().getSearchvalue();
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		Map<String, Object> queryParameter = new HashMap<>();
		List<OtsProduct> productList = null;
		//getting details of product on this basis of search key
		try{
            switch(searchKey){
	            case "ProductId":
	            					queryParameter.put("otsProductId", UUID.fromString(seachValue));
	            					productList  = super.getResultListByNamedQuery("OtsProduct.findByOtsProductId", queryParameter);
	            				    break;
	            				    
	            case "ProductName":
								 	queryParameter.put("otsProductName", seachValue);
									productList  = super.getResultListByNamedQuery("OtsProduct.findByOtsProductName", queryParameter);
								    break;
				
	            case "All":
	            					OtsProductLevel productLevelId = new OtsProductLevel();
	            					productLevelId.setOtsProductLevelId(Integer.parseInt(productDetailsBORequest.getRequestData().getProductLevel()));
	            					queryParameter.put("status",productDetailsBORequest.getRequestData().getStatus());
	            					queryParameter.put("otsProductLevelId", productLevelId);
					                productList  = super.getResultListByNamedQuery("OtsProduct.findAllProduct",queryParameter);
				                    break;
				                    
	            case "Letter":
	            	                OtsProductLevel OtsProductLevel = new OtsProductLevel();
	            	                OtsProductLevel.setOtsProductLevelId(Integer.parseInt(productDetailsBORequest.getRequestData().getProductLevel()));
	            	                queryParameter.put("Letter", "%"+seachValue+"%");
	            	                queryParameter.put("otsProductLevelId", OtsProductLevel);
	            	                
	            	                productList  = super.getResultListByNamedQuery("OtsProduct.findByPattrenMatching",queryParameter);
	                                break;                    
	            
	            default:
	            					return null;

            }

            productDetails =  productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
            productDetailsBOResponse.setProductDetails(productDetails);
            return productDetailsBOResponse;
		}catch (NoResultException e) {
	    	logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
	    	throw new BusinessException(e.getMessage(), e);
	    }
	}
	
	private ProductDetails convertProductDetailsFromEntityToDomain(OtsProduct otsProduct) {
		ProductDetails productDetails = new ProductDetails();
		productDetails.setProductId(otsProduct.getOtsProductId()==null?"":otsProduct.getOtsProductId().toString());
		productDetails.setProductName(otsProduct.getOtsProductName()==null?"":otsProduct.getOtsProductName());
		productDetails.setProductDescription(otsProduct.getOtsProductDescription()==null?"":otsProduct.getOtsProductDescription());
		productDetails.setProductDescriptionLong(otsProduct.getOtsProductDescriptionLong()==null?"":otsProduct.getOtsProductDescriptionLong());
		productDetails.setProductStatus(otsProduct.getOtsProductStatus()==null?"":otsProduct.getOtsProductStatus());
		productDetails.setProductLevel(otsProduct.getOtsProductLevelId()==null?"":otsProduct.getOtsProductLevelId().getOtsProductLevelId().toString());
		productDetails.setProductHsnSac(otsProduct.getOtsProductHsnSac()==null?"":otsProduct.getOtsProductHsnSac());
		productDetails.setProductSellerPrice(otsProduct.getOtsProductSellerPrice()==null?"0":otsProduct.getOtsProductSellerPrice());
		productDetails.setProductBasePrice(otsProduct.getOtsProductBasePrice()==null?"0":otsProduct.getOtsProductBasePrice().toString());
		productDetails.setProductPrice(otsProduct.getOtsProductPrice()==null?"0":otsProduct.getOtsProductPrice().toString());
		productDetails.setProductDiscountPercentage(otsProduct.getOtsProductDiscountPercentage()==null?"0":otsProduct.getOtsProductDiscountPercentage());
		productDetails.setProductDiscountPrice(otsProduct.getOtsProductDiscountPrice()==null?"0":otsProduct.getOtsProductDiscountPrice());
		productDetails.setGst(otsProduct.getOtsProductGst()==null?"0":otsProduct.getOtsProductGst());
		productDetails.setGstPrice(otsProduct.getOtsProductGstPrice()==null?"0":otsProduct.getOtsProductGstPrice());
		productDetails.setProductFinalPrice(otsProduct.getOtsProductFinalPriceWithGst()==null?"0":otsProduct.getOtsProductFinalPriceWithGst());
		productDetails.setProductImage(otsProduct.getOtsProductImage()==null?"":otsProduct.getOtsProductImage());
		if(productDetails.getProductLevel().equalsIgnoreCase("3") || productDetails.getProductLevel().equalsIgnoreCase("4")) {
			productDetails.setDistributorId(otsProduct.getOtsDistributorId()==null?"":otsProduct.getOtsDistributorId().getOtsUsersId().toString());
			productDetails.setDistributerName(otsProduct.getOtsDistributorId()==null?"":otsProduct.getOtsDistributorId().getOtsUsersFirstname()+" "+otsProduct.getOtsDistributorId().getOtsUsersLastname());
			productDetails.setDistributorEmailId(otsProduct.getOtsDistributorId().getOtsUsersEmailid()==null?"":otsProduct.getOtsDistributorId().getOtsUsersEmailid());
		}
		productDetails.setMultiProductImage1(otsProduct.getOtsMultiProductImage1()==null?"":otsProduct.getOtsMultiProductImage1());
		productDetails.setMultiProductImage2(otsProduct.getOtsMultiProductImage2()==null?"":otsProduct.getOtsMultiProductImage2());
		productDetails.setMultiProductImage3(otsProduct.getOtsMultiProductImage3()==null?"":otsProduct.getOtsMultiProductImage3());
		productDetails.setMultiProductImage4(otsProduct.getOtsMultiProductImage4()==null?"":otsProduct.getOtsMultiProductImage4());
		productDetails.setMultiProductImage5(otsProduct.getOtsMultiProductImage5()==null?"":otsProduct.getOtsMultiProductImage5());
		productDetails.setMultiProductImage6(otsProduct.getOtsMultiProductImage6()==null?"":otsProduct.getOtsMultiProductImage6());
		productDetails.setMultiProductImage7(otsProduct.getOtsMultiProductImage7()==null?"":otsProduct.getOtsMultiProductImage7());
		productDetails.setMultiProductImage8(otsProduct.getOtsMultiProductImage8()==null?"":otsProduct.getOtsMultiProductImage8());
		productDetails.setMultiProductImage9(otsProduct.getOtsMultiProductImage9()==null?"":otsProduct.getOtsMultiProductImage9());
		productDetails.setMultiProductImage10(otsProduct.getOtsMultiProductImage10()==null?"":otsProduct.getOtsMultiProductImage10());
		productDetails.setProductDeliveryCharge(otsProduct.getOtsProductDeliveryCharge()==null?"0":otsProduct.getOtsProductDeliveryCharge());
		productDetails.setProductReturnDeliveryCharge(otsProduct.getOtsProductReturnDeliveryCharge()==null?"0":otsProduct.getOtsProductReturnDeliveryCharge().toString());
		productDetails.setProductBulkEligible(otsProduct.getOtsProductBulkEligible()==null?"":otsProduct.getOtsProductBulkEligible().toString());
		productDetails.setProductBulkMinQty(otsProduct.getOtsProductBulkMinQty()==null?null:otsProduct.getOtsProductBulkMinQty());
		productDetails.setProductDeliveryPolicy(otsProduct.getOtsProductDeliveryPolicy()==null?"":otsProduct.getOtsProductDeliveryPolicy());
		productDetails.setProductCancellationAvailability(otsProduct.getOtsProductCancellationAvailability()==null?"":otsProduct.getOtsProductCancellationAvailability().toString());
		productDetails.setProductCancellationPolicy(otsProduct.getOtsProductCancellationPolicy()==null?"":otsProduct.getOtsProductCancellationPolicy());	
		productDetails.setProductReplacementAvailability(otsProduct.getOtsProductReplacementAvailability()==null?"":otsProduct.getOtsProductReplacementAvailability().toString());
		productDetails.setProductReplacementPolicy(otsProduct.getOtsProductReplacementPolicy()==null?"":otsProduct.getOtsProductReplacementPolicy().toString());
		productDetails.setProductReplacementDays(otsProduct.getOtsProductReplacementDays()==null?"":otsProduct.getOtsProductReplacementDays().toString());
		productDetails.setProductReturnAvailability(otsProduct.getOtsProductReturnAvailability()==null?"":otsProduct.getOtsProductReturnAvailability().toString());
		productDetails.setProductReturnPolicy(otsProduct.getOtsProductReturnPolicy()==null?"":otsProduct.getOtsProductReturnPolicy().toString());
		productDetails.setProductReturnDays(otsProduct.getOtsProductReturnDays()==null?"":otsProduct.getOtsProductReturnDays().toString());
		productDetails.setProductTag(otsProduct.getOtsProductTag()==null?"":otsProduct.getOtsProductTag());
		productDetails.setProductNetQuantity(otsProduct.getOtsNetQuantity()==null?"":otsProduct.getOtsNetQuantity());
		productDetails.setUnitOfMeasurement(otsProduct.getUnitOfMeasurement()==null?"":otsProduct.getUnitOfMeasurement());
		productDetails.setCreatedUser(otsProduct.getCreatedUser()==null?null:otsProduct.getCreatedUser().getAccountId().toString());	
		productDetails.setOtsProductCountry(otsProduct.getOtsProductCountry()==null?"":otsProduct.getOtsProductCountry());
		productDetails.setOtsProductCountryCode(otsProduct.getOtsProductCountryCode()==null?"":otsProduct.getOtsProductCountryCode());
		productDetails.setOtsProductCurrency(otsProduct.getOtsProductCurrency()==null?"":otsProduct.getOtsProductCurrency());
		productDetails.setOtsProductCurrencySymbol(otsProduct.getOtsProductCurrencySymbol()==null?"":otsProduct.getOtsProductCurrencySymbol());
		productDetails.setOtsOemModelNumber(otsProduct.getOtsOemModelNumber()==null?"":otsProduct.getOtsOemModelNumber());
		productDetails.setOtsOemPartNumber(otsProduct.getOtsOemPartNumber()==null?"":otsProduct.getOtsOemPartNumber());
		productDetails.setOtsOemShortDescription(otsProduct.getOtsOemShortDescription()==null?"":otsProduct.getOtsOemShortDescription());
		productDetails.setOtsOemLongDescription(otsProduct.getOtsOemLongDescription()==null?"":otsProduct.getOtsOemLongDescription());
		productDetails.setOtsOemUom(otsProduct.getOtsOemUom()==null?"":otsProduct.getOtsOemUom());
		productDetails.setOtsVendorItemCode(otsProduct.getOtsVendorItemCode()==null?"":otsProduct.getOtsVendorItemCode());
		productDetails.setOtsProductDetailsPdf(otsProduct.getOtsProductDetailsPdf()==null?"":otsProduct.getOtsProductDetailsPdf());
		productDetails.setVariantFlag(otsProduct.getVariantFlag()==null?"":otsProduct.getVariantFlag().toString());
		productDetails.setOtsProductTagValue(otsProduct.getOtsProductTagValue()==null?"":otsProduct.getOtsProductTagValue());
		productDetails.setOtsManufacturerName(otsProduct.getOtsManufacturerName()==null?"":otsProduct.getOtsManufacturerName());
		productDetails.setOtsManufacturerAddress(otsProduct.getOtsManufacturerAddress()==null?"":otsProduct.getOtsManufacturerAddress());
		productDetails.setOtsManufacturerGenericName(otsProduct.getOtsManufacturerGenericName()==null?"":otsProduct.getOtsManufacturerGenericName());
		productDetails.setOtsManufacturerPackingImport(otsProduct.getOtsManufacturerPackingImport()==null?"":otsProduct.getOtsManufacturerPackingImport());
		productDetails.setOtsConsumerCareName(otsProduct.getOtsConsumerCareName()==null?"":otsProduct.getOtsConsumerCareName());
		productDetails.setOtsConsumerCareEmail(otsProduct.getOtsConsumerCareEmail()==null?"":otsProduct.getOtsConsumerCareEmail());
		productDetails.setOtsConsumerCarePhoneNumber(otsProduct.getOtsConsumerCarePhoneNumber()==null?"":otsProduct.getOtsConsumerCarePhoneNumber());
		productDetails.setOriginCountry(otsProduct.getOriginCountry()==null?"":otsProduct.getOriginCountry());
		productDetails.setOtsTimeToShip(otsProduct.getOtsTimeToShip()==null?"":otsProduct.getOtsTimeToShip());
		productDetails.setOtsTimeToDeliver(otsProduct.getOtsTimeToDeliver()==null?"":otsProduct.getOtsTimeToDeliver());
		productDetails.setOtsSellerPickupReturn(otsProduct.getOtsSellerPickupReturn()==null?"":otsProduct.getOtsSellerPickupReturn());
		productDetails.setOtsCodAvailability(otsProduct.getOtsCodAvailability()==null?"":otsProduct.getOtsCodAvailability());
		productDetails.setOtsNutritionalFlag(otsProduct.getOtsNutritionalFlag()==null?"":otsProduct.getOtsNutritionalFlag().toString());
		productDetails.setOtsNutritionalInfo(otsProduct.getOtsNutritionalInfo()==null?"":otsProduct.getOtsNutritionalInfo());
		productDetails.setOtsNutritionalAdditivesInfo(otsProduct.getOtsNutritionalAdditivesInfo()==null?"":otsProduct.getOtsNutritionalAdditivesInfo());
		productDetails.setOtsNutritionalBrandOwnerFSSAILicenseNo(otsProduct.getOtsNutritionalBrandOwnerFSSAILicenseNo()==null?"":otsProduct.getOtsNutritionalBrandOwnerFSSAILicenseNo());	
		productDetails.setOtsNutritionalOtherFSSAILicenseNo(otsProduct.getOtsNutritionalOtherFSSAILicenseNo()==null?"":otsProduct.getOtsNutritionalOtherFSSAILicenseNo());
		productDetails.setOtsNutritionalImporterFSSAILicenseNo(otsProduct.getOtsNutritionalImporterFSSAILicenseNo()==null?"":otsProduct.getOtsNutritionalImporterFSSAILicenseNo());
		
		//setting category Id and name  for product in domain
		CategoryDetails category = getCategoryForProductId(otsProduct.getOtsProductId().toString());
		productDetails.setCategoryId(category==null?"":category.getCategoryId().toString());	
		productDetails.setCategoryName(category==null?"":category.getCategoryName().toString());
					
	    //setting Sub-category Id and name  for product in domain
		SubCategoryDetails subcategory = getSubCategoryForProductId(otsProduct.getOtsProductId().toString());
		productDetails.setSubCategoryId(subcategory==null?"":subcategory.getSubCategoryId().toString());	
		productDetails.setSubCategoryName(subcategory==null?"":subcategory.getSubcategoryName().toString());
		
		//To fetch Average Rating, Product Stock & Attributes Only for Products & Variants
		if(productDetails.getProductLevel().equalsIgnoreCase("3") || productDetails.getProductLevel().equalsIgnoreCase("4")) {
			//setting Average Rating & Total Rating Count for Products
			AverageReviewRatingResponse averageReviewRatingResponse = otsUserService.getAverageRatingOfProduct(otsProduct.getOtsProductId().toString());
			productDetails.setProductAverageRating(averageReviewRatingResponse== null?"":averageReviewRatingResponse.getAverageReviewRating().getAverageRating().toString());
			productDetails.setProductTotalRatingCount(averageReviewRatingResponse== null?"":averageReviewRatingResponse.getAverageReviewRating().getTotalReviewRating().toString());
		
			//setting Product Stock Quantity for Products
			List<GetProductBOStockResponse> productStock = productStockDao.getProductStockByProductId(otsProduct.getOtsProductId().toString());
			productDetails.setProductStockQuantity(productStock.size() == 0? "": productStock.get(0).getStockQuantity());
			
			//setting Attributes for Products
			List<AttributeDetails> productAttribute = getProductAttribute(productDetails.getProductId());
			productDetails.setProductAttribute(productAttribute);
		}
		return productDetails;
	}

	//getting product details for product ID
	@Override
	public ProductDetails getProductDetails(String productId) {
		ProductDetails productDetails = new ProductDetails();
		try {
			OtsProduct otsProduct = new OtsProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductId", UUID.fromString(productId));
			try {
				otsProduct = super.getResultByNamedQuery("OtsProduct.findActiveproductByOtsProductId", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
			productDetails = convertProductDetailsFromEntityToDomain(otsProduct);	
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return productDetails;
	}
	
	@Override //getting product list for mapped distributor id
	public List<ProductDetails> getProductDetailswithStock(String  distributorIdValue) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try {
			OtsUsers distributorId = new OtsUsers();
			distributorId.setOtsUsersId(UUID.fromString(distributorIdValue));
			OtsProduct otsProduct = new OtsProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("distributorId", distributorId);
			List<OtsProduct> productList = super.getResultListByNamedQuery("OtsProduct.findByProductDetailsStock", queryParameter);
			productDetails =  productList.stream().map(tsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());	
		}catch(Exception e) {
			return null;
		}
		return productDetails;
	}
	
	@Override   // updating the status of the product
	public ProductDetails updateProductStatus(String productId, String productStatus) {
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsProduct otsProduct = new OtsProduct();
			queryParameter.put("otsProductId",UUID.fromString(productId));
			otsProduct  = super.getResultByNamedQuery("OtsProduct.findByOtsProductId", queryParameter);
			otsProduct.setOtsProductStatus(productStatus);
			save(otsProduct);
			ProductDetails productDetails = convertProductDetailsFromEntityToDomain(otsProduct);	
			
			return productDetails;
		}catch(Exception e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override   // getting the product category and sub category and product list by level id of the product
	public ProductDetailsBOResponse getProductByLevelId(String levelId) {
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		Map<String, Object> queryParameter = new HashMap<>();
		List<OtsProduct> productList = null;
		OtsProductLevel productLevel = new OtsProductLevel();
		try {
			productLevel.setOtsProductLevelId(Integer.parseInt(levelId));
			queryParameter.put("otsProductLevelId",productLevel );
			productList  = super.getResultListByNamedQuery("OtsProduct.otsProductLevelId", queryParameter);
			productDetails =  productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}

	@Override //get product by it's name
	public List<ProductDetails> getProductDetailsByName(String productName) {
		try {
			List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
			List<OtsProduct> productList = new ArrayList<OtsProduct>();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductName",productName);
			productList = super.getResultListByNamedQuery("OtsProduct.findByOtsProductName", queryParameter);
			productDetails =  productList.stream().map(OtsProduct -> convertProductDetailsFromEntityToDomain(OtsProduct)).collect(Collectors.toList());	
			System.out.println(productDetails.get(0).getProductId());
			return productDetails;
		}catch(Exception e) {
			System.out.print(e);
		}
		return null;
	}
	
	@Override 
	public ProductDetailsBOResponse getProductPagination(ProductDetailsBORequest productDetailsBORequest) {
	    ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
	    List<ProductDetails> productList = new ArrayList<>();

	    try {
	        String searchValue = productDetailsBORequest.getRequestData().getSearchvalue();

	        // Prepare input parameters
	        Map<String, Object> inParamMap = new HashMap<>();
	        inParamMap.put("page_number", Integer.parseInt(productDetailsBORequest.getRequestData().getPageNumber()));
	        inParamMap.put("data_size", Integer.parseInt(productDetailsBORequest.getRequestData().getDataSize()));
	        inParamMap.put("product_level", Integer.parseInt(productDetailsBORequest.getRequestData().getProductLevel()));

	        UUID categoryId = "1".equalsIgnoreCase(searchValue) ? null : UUID.fromString(searchValue);
	        inParamMap.put("product_category_id", categoryId);
	        inParamMap.put("product_type", productDetailsBORequest.getRequestData().getProductType());
	        inParamMap.put("product_country_code", productDetailsBORequest.getRequestData().getProductCountryCode());

	        // Call the function
	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withSchemaName("public")
	            .withFunctionName("get_product_pagination")
	            .withoutProcedureColumnMetaDataAccess()
	            .declareParameters(
	                new SqlParameter("page_number", Types.INTEGER),
	                new SqlParameter("data_size", Types.INTEGER),
	                new SqlParameter("product_level", Types.INTEGER),
	                new SqlParameter("product_category_id", Types.OTHER),
	                new SqlParameter("product_type", Types.VARCHAR),
	                new SqlParameter("product_country_code", Types.VARCHAR)
	            );

	        Map<String, Object> result = simpleJdbcCall.execute(inParamMap);

	        // Extract result from result-set-1
	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return productDetailsBOResponse;
	        }

	        String jsonResponse = resultSet.get(0).get("result").toString();
	        System.out.println("Parsed JSON Response: " + jsonResponse);

	        // Parse JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);

	        productDetailsBOResponse.setTotalProductsCount(rootNode.get("totalProductsCount").asText());
	        productDetailsBOResponse.setTotalPages(rootNode.get("totalPages").asText());

	        JsonNode productDetailsNode = rootNode.get("productDetails");
	        if (productDetailsNode != null && productDetailsNode.isArray()) {
	            for (JsonNode productNode : productDetailsNode) {
	                // Convert each JsonNode to Map<String, Object>
	                Map<String, Object> productMap = objectMapper.convertValue(productNode, new TypeReference<Map<String, Object>>() {});
	                productList.add(convertProductDetailsFromProcedureToDomain(productMap));
	            }
	        }

	        productDetailsBOResponse.setProductDetails(productList);
	    }catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
	    return productDetailsBOResponse;
	}

	//shreekant
	@Override
	public List<ProductDetails> getAllProductDetails() {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try {
			List<OtsProduct> productList = new ArrayList<OtsProduct>();
			Map<String, Object> queryParameter = new HashMap<>();
			OtsProductLevel productLevelId = new OtsProductLevel();
			productLevelId.setOtsProductLevelId(3);
			queryParameter.put("status","active");
			queryParameter.put("otsProductLevelId", productLevelId);
			productList = super.getResultListByNamedQuery("OtsProduct.findAllProduct", queryParameter);
			productDetails =  productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
			System.out.println(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productDetails;
	}
	/***************************/

	@Override
	public String getDeliveryChargeForProduct(String productId) {
		String deliveryCharge = null;
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			OtsProduct otsProduct = new OtsProduct();
			queryParameter.put("otsProductId", UUID.fromString(productId));
			otsProduct  = super.getResultByNamedQuery("OtsProduct.findByOtsProductId", queryParameter);
			deliveryCharge = otsProduct.getOtsProductDeliveryCharge();
					
		}catch(Exception e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
		    	e.printStackTrace();
		      throw new BusinessException(e.getMessage(), e);
		}
		return deliveryCharge;
	}

	@Override
	public ProductDetailsBOResponse getProductListByDistributor(String distributerId)
	{
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(distributerId));
			
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("distributorId", DistributorId);
			List<OtsProduct> productList  = super.getResultListByNamedQuery("OtsProduct.getProductListbyDistributor", queryParameter);
					
			List<ProductDetails> productDetails =  productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
		    productDetailsBOResponse.setProductDetails(productDetails);
		}catch (NoResultException e) {
	    	logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
	    	throw new BusinessException(e.getMessage(), e);
	    }
		return productDetailsBOResponse;
	} 
	
	@Override
	public CategoryDetails getCategoryForProductId(String productId) {
		CategoryDetails categoryDetails = new CategoryDetails();
		try {
			System.out.println("productId = "+productId);
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("product_id",UUID.fromString(productId));
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_category_for_product_id")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			
			List<Map<String, Object>> productDetails = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			System.out.println("productDetails size = "+productDetails.size());
			if(productDetails.size() == 0) {
				return null;
			}else {
				categoryDetails.setCategoryId(productDetails.get(0).get("category_id").toString());
				categoryDetails.setCategoryName(productDetails.get(0).get("category_name").toString());
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return categoryDetails;
	}
	
	@Override
	public SubCategoryDetails getSubCategoryForProductId(String productId) {
		SubCategoryDetails subcategoryDetails = new SubCategoryDetails();
		try {
			Map<String, Object> queryParameters = new HashMap<String, Object>();
			queryParameters.put("product_id",UUID.fromString(productId));
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_sub_category_for_product_id")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			
			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			
			List<Map<String, Object>> productDetails = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			if(productDetails.size() == 0) {
				return null;
			}else {
				subcategoryDetails.setSubCategoryId((productDetails.get(0).get("subCategory_id").toString()));
				subcategoryDetails.setSubcategoryName((productDetails.get(0).get("subCategory_name").toString()));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return subcategoryDetails;
	}
	
	@Override
	public List<ProductDetails> getActiveProductListByDistributor(String distributorId) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		Map<String, Object> queryParameter = new HashMap<>();
		List<OtsProduct> productList = null;
		try {
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			
			queryParameter.put("otsDistributorId", DistrubutorId);
			productList  = super.getResultListByNamedQuery("OtsProduct.getActiveProductListByDistributor", queryParameter);
			
			productDetails =  productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
		}catch (NoResultException e) {
	    	logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
	    	throw new BusinessException(e.getMessage(), e);
	    }
		return productDetails;
	}
	
	@Override
	public List<ProductDetails> getAllProductsWithDiscount() {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try {
			List<OtsProduct> otsProductList = new ArrayList<OtsProduct>();
			Map<String, Object> queryParameter = new HashMap<>();
			otsProductList = super.getResultListByNamedQuery("OtsProduct.getAllProductsWithDiscount", queryParameter);
			productDetails = otsProductList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return productDetails;
	}
	
	@Override
	public List<ProductDetails> getProductsByDistributorWithReviewAndRating(String distributorId) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		Map<String, Object> queryParameter = new HashMap<>();
		List<OtsProduct> productList = null;
		try {
			OtsUsers DistrubutorId = new OtsUsers();
			DistrubutorId.setOtsUsersId(UUID.fromString(distributorId));
			
			queryParameter.put("otsDistributorId", DistrubutorId);
			productList  = super.getResultListByNamedQuery("OtsProduct.getProductsByDistributorWithReviewAndRating", queryParameter);
			
			productDetails =  productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
		}catch (NoResultException e) {
	    	logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
	    	throw new BusinessException(e.getMessage(), e);
	    }
		return productDetails;
	}
	
	@Override
	public ProductDetailsBOResponse getProductsForSubCategory(String subCategoryId) {		
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		Map<String, Object> queryParameter = new HashMap<>();
		List<OtsProduct> productList = null;
		try {
			OtsProduct OtsProduct = new OtsProduct();
			OtsProduct.setOtsProductId(UUID.fromString(subCategoryId));
			
			queryParameter.put("otsProductCategoryId", OtsProduct);
			productList  = super.getResultListByNamedQuery("OtsProduct.getProductsForSubCategory", queryParameter);
					
			productDetails =  productList.stream().map(product -> convertProductDetailsFromEntityToDomain(product)).collect(Collectors.toList());
		    productDetailsBOResponse.setProductDetails(productDetails);
		}catch (NoResultException e) {
	    	logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
	    	throw new BusinessException(e.getMessage(), e);
	    }
		return productDetailsBOResponse;
	}
	
	//getting distributor products by subCategory for pagination
	@Override
	public ProductDetailsBOResponse getProductsByDistributerPagination(GetProductsByDistributerPaginationRequest getProductsByDistributerPagination) {
	    ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
	    List<ProductDetails> productList = new ArrayList<>();

	    try {
	        // Extract and parse input parameters
	        String distributorId = getProductsByDistributerPagination.getRequest().getDistributerId();
	        int pageNumber = Integer.parseInt(getProductsByDistributerPagination.getRequest().getPageNumber());
	        int dataSize = Integer.parseInt(getProductsByDistributerPagination.getRequest().getDataSize());
	        String productCountryCode = getProductsByDistributerPagination.getRequest().getProductCountryCode();

	        // Prepare input map
	        Map<String, Object> inParamMap = new HashMap<>();
	        inParamMap.put("distributor_id", UUID.fromString(distributorId));
	        inParamMap.put("page_number", pageNumber);
	        inParamMap.put("data_size", dataSize);
	        inParamMap.put("product_country_code", productCountryCode);

	        SimpleJdbcCall simpleJdbcCall;    	
			//To get All the products by distributor
			if(getProductsByDistributerPagination.getRequest().getSubCategoryId()== null || getProductsByDistributerPagination.getRequest().getSubCategoryId().equals("")) {
	            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withSchemaName("public")
	                .withFunctionName("get_products_by_distributor_pagination")
	                .withoutProcedureColumnMetaDataAccess()
	                .declareParameters(
	                    new SqlParameter("distributor_id", Types.OTHER),
	                    new SqlParameter("page_number", Types.INTEGER),
	                    new SqlParameter("data_size", Types.INTEGER),
	                    new SqlParameter("product_country_code", Types.VARCHAR)
	                );
	        } else {
	        	//To get All the Products under Sub Category by distributor
	            inParamMap.put("subcategory_id", UUID.fromString(getProductsByDistributerPagination.getRequest().getSubCategoryId()));
	            simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withSchemaName("public")
	                .withFunctionName("get_products_by_subcat_and_distributor_pagination")
	                .withoutProcedureColumnMetaDataAccess()
	                .declareParameters(
	                    new SqlParameter("distributor_id", Types.OTHER),
	                    new SqlParameter("subcategory_id", Types.OTHER),
	                    new SqlParameter("page_number", Types.INTEGER),
	                    new SqlParameter("data_size", Types.INTEGER),
	                    new SqlParameter("product_country_code", Types.VARCHAR)
	                );
	        }

	        // Execute the function
	        Map<String, Object> result = simpleJdbcCall.execute(inParamMap);

	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");

	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return productDetailsBOResponse;
	        }

	        String jsonResponse = resultSet.get(0).get("result").toString();
	        System.out.println("Parsed JSON Response: " + jsonResponse);

	        // Parse JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);

	        // Set total count & pages
	        productDetailsBOResponse.setTotalProductsCount(rootNode.get("totalProductsCount").asText());
	        productDetailsBOResponse.setTotalPages(rootNode.get("totalPages").asText());

	        // Parse product list
	        JsonNode productDetailsNode = rootNode.get("productDetails");
	        if (productDetailsNode != null && productDetailsNode.isArray()) {
	            for (JsonNode productNode : productDetailsNode) {
	                Map<String, Object> productMap = objectMapper.convertValue(productNode, new TypeReference<Map<String, Object>>() {});
	                productList.add(convertProductDetailsFromProcedureToDomain(productMap));
	            }
	        }

	        productDetailsBOResponse.setProductDetails(productList);
	    }catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
	    return productDetailsBOResponse;
	}
	
	@Override  
	public List<ProductDetails> getProductBySubCategory(String subCategoryId) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		Map<String, Object> queryParameter = new HashMap<>();
		List<OtsProduct> productList = new ArrayList<OtsProduct>();
		try {
			OtsProduct OtsProduct = new OtsProduct();
			OtsProduct.setOtsProductId(UUID.fromString(subCategoryId));
			
			queryParameter.put("otsProductCategoryId",OtsProduct);
			productList  = super.getResultListByNamedQuery("OtsProduct.getProductBySubCategory", queryParameter);
			productDetails =  productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productDetails;
	}
	
	@Override  
	public List<ProductDetails> getSubCategoryByCategory(String categoryId) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try {
			OtsProduct OtsProduct = new OtsProduct();
			OtsProduct.setOtsProductId(UUID.fromString(categoryId));
			
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductCategoryId",OtsProduct);
			List<OtsProduct> productList  = super.getResultListByNamedQuery("OtsProduct.getSubCategoryByCategory", queryParameter);
			productDetails =  productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productDetails;
	}
	
	private ProductDetails convertPageloaderProductDetailsFromProcedureToDomain(Map<String, Object> out) {
		ProductDetails productDetails = new ProductDetails();
		try {
			productDetails.setProductId(out.get("ots_product_id")==null?"":out.get("ots_product_id").toString());
			productDetails.setProductName(out.get("ots_product_name")==null?"":out.get("ots_product_name").toString());
			productDetails.setProductStatus(out.get("ots_product_status")==null?"":out.get("ots_product_status").toString());
			productDetails.setProductLevel(out.get("ots_product_level_id")==null?"":out.get("ots_product_level_id").toString());
			productDetails.setProductSellerPrice(out.get("ots_product_seller_price")==null?"":out.get("ots_product_seller_price").toString());
			productDetails.setProductBasePrice(out.get("ots_product_base_price")==null?"":out.get("ots_product_base_price").toString());
			productDetails.setProductPrice(out.get("ots_product_price")==null?"":out.get("ots_product_price").toString());
			productDetails.setProductDiscountPrice(out.get("ots_product_discount_price")==null?"":out.get("ots_product_discount_price").toString());
			productDetails.setProductDiscountPercentage(out.get("ots_product_discount_percentage")==null?"":out.get("ots_product_discount_percentage").toString());
			productDetails.setGst(out.get("ots_product_gst")==null?"":out.get("ots_product_gst").toString());
			productDetails.setGstPrice(out.get("ots_product_gst_price")==null?"":out.get("ots_product_gst_price").toString());
			productDetails.setProductFinalPrice(out.get("ots_product_final_price_with_gst")==null?"":out.get("ots_product_final_price_with_gst").toString());
			productDetails.setProductImage(out.get("ots_product_image")==null?"":out.get("ots_product_image").toString());
			productDetails.setDistributorId(out.get("ots_distributor_id")==null?null:out.get("ots_distributor_id").toString());
			productDetails.setCreatedUser(out.get("created_user")==null?"":out.get("created_user").toString());
			productDetails.setOtsProductCountry(out.get("ots_product_country")==null?"":out.get("ots_product_country").toString());
			productDetails.setOtsProductCountryCode(out.get("ots_product_country_code")==null?"":out.get("ots_product_country_code").toString());
			productDetails.setOtsProductCurrency(out.get("ots_product_currency")==null?"":out.get("ots_product_currency").toString());
			productDetails.setOtsProductCurrencySymbol(out.get("ots_product_currency_symbol")==null?"":out.get("ots_product_currency_symbol").toString());
			
			// setting Distributor Name
			if (productDetails.getDistributorId() != null && !productDetails.getDistributorId().isEmpty()) {
			    UserDetails distributorDetails = userServiceDAO.getUserDetails(productDetails.getDistributorId());
			    productDetails.setDistributerName(distributorDetails == null ? "" : distributorDetails.getFirstName() + " " + distributorDetails.getLastName());
			}
			
			//setting Product Stock Quantity
			List<GetProductBOStockResponse> productStock = productStockDao.getProductStockByProductId(productDetails.getProductId());
			productDetails.setProductStockQuantity(productStock.size() == 0? "": productStock.get(0).getStockQuantity());
			
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productDetails;
	}
	
	private ProductDetails convertProductDetailsFromProcedureToDomain(Map<String, Object> out) {
		ProductDetails productDetails = new ProductDetails();
		try {
			productDetails.setProductId(out.get("ots_product_id")==null?"":out.get("ots_product_id").toString());
			productDetails.setProductName(out.get("ots_product_name")==null?"":out.get("ots_product_name").toString());
			productDetails.setProductDescription(out.get("ots_product_description")==null?"":out.get("ots_product_description").toString());
			productDetails.setProductDescriptionLong(out.get("ots_product_description_long")==null?"":out.get("ots_product_description_long").toString());
			productDetails.setProductStatus(out.get("ots_product_status")==null?"":out.get("ots_product_status").toString());
			productDetails.setProductLevel(out.get("ots_product_level_id")==null?"":out.get("ots_product_level_id").toString());
			productDetails.setProductHsnSac(out.get("ots_product_hsn_sac")==null?"":out.get("ots_product_hsn_sac").toString());
			productDetails.setProductSellerPrice(out.get("ots_product_seller_price")==null?"":out.get("ots_product_seller_price").toString());
			productDetails.setProductBasePrice(out.get("ots_product_base_price")==null?"":out.get("ots_product_base_price").toString());
			productDetails.setProductPrice(out.get("ots_product_price")==null?"":out.get("ots_product_price").toString());
			productDetails.setProductDiscountPrice(out.get("ots_product_discount_price")==null?"":out.get("ots_product_discount_price").toString());
			productDetails.setProductDiscountPercentage(out.get("ots_product_discount_percentage")==null?"":out.get("ots_product_discount_percentage").toString());
			productDetails.setGst(out.get("ots_product_gst")==null?"":out.get("ots_product_gst").toString());
			productDetails.setGstPrice(out.get("ots_product_gst_price")==null?"":out.get("ots_product_gst_price").toString());
			productDetails.setProductFinalPrice(out.get("ots_product_final_price_with_gst")==null?"":out.get("ots_product_final_price_with_gst").toString());
			productDetails.setProductImage(out.get("ots_product_image")==null?"":out.get("ots_product_image").toString());
			productDetails.setDistributorId(out.get("ots_distributor_id")==null?null:out.get("ots_distributor_id").toString());
			productDetails.setMultiProductImage1(out.get("ots_multi_product_image1")==null?"":out.get("ots_multi_product_image1").toString());
			productDetails.setMultiProductImage2(out.get("ots_multi_product_image2")==null?"":out.get("ots_multi_product_image2").toString());
			productDetails.setMultiProductImage3(out.get("ots_multi_product_image3")==null?"":out.get("ots_multi_product_image3").toString());
			productDetails.setMultiProductImage4(out.get("ots_multi_product_image4")==null?"":out.get("ots_multi_product_image4").toString());
			productDetails.setMultiProductImage5(out.get("ots_multi_product_image5")==null?"":out.get("ots_multi_product_image5").toString());
			productDetails.setMultiProductImage6(out.get("ots_multi_product_image6")==null?"":out.get("ots_multi_product_image6").toString());
			productDetails.setMultiProductImage7(out.get("ots_multi_product_image7")==null?"":out.get("ots_multi_product_image7").toString());
			productDetails.setMultiProductImage8(out.get("ots_multi_product_image8")==null?"":out.get("ots_multi_product_image8").toString());
			productDetails.setMultiProductImage9(out.get("ots_multi_product_image9")==null?"":out.get("ots_multi_product_image9").toString());
			productDetails.setMultiProductImage10(out.get("ots_multi_product_image10")==null?"":out.get("ots_multi_product_image10").toString());
			productDetails.setCreatedUser(out.get("created_user")==null?"":out.get("created_user").toString());
			productDetails.setProductDeliveryCharge(out.get("ots_product_delivery_charge")==null?"":out.get("ots_product_delivery_charge").toString());
			productDetails.setProductDeliveryPolicy(out.get("ots_product_delivery_policy")==null?"":out.get("ots_product_delivery_policy").toString());
			productDetails.setProductReturnDeliveryCharge(out.get("ots_product_return_delivery_charge")==null?"":out.get("ots_product_return_delivery_charge").toString());
			productDetails.setProductCancellationAvailability(out.get("ots_product_cancellation_availability")==null?"":out.get("ots_product_cancellation_availability").toString());
			productDetails.setProductCancellationPolicy(out.get("ots_product_cancellation_policy")==null?"":out.get("ots_product_cancellation_policy").toString());	
			productDetails.setProductReplacementAvailability(out.get("ots_product_replacement_availability")==null?null:out.get("ots_product_replacement_availability").toString());
			productDetails.setProductReplacementPolicy(out.get("ots_product_replacement_policy")==null?"":out.get("ots_product_replacement_policy").toString());
			productDetails.setProductReplacementDays(out.get("ots_product_replacement_days")==null?"":out.get("ots_product_replacement_days").toString());
			productDetails.setProductReturnAvailability(out.get("ots_product_return_availability")==null?null:out.get("ots_product_return_availability").toString());
			productDetails.setProductReturnPolicy(out.get("ots_product_return_policy")==null?"":out.get("ots_product_return_policy").toString());
			productDetails.setProductReturnDays(out.get("ots_product_return_days")==null?"":out.get("ots_product_return_days").toString());
			productDetails.setProductTag(out.get("ots_product_tag")==null?"":out.get("ots_product_tag").toString());
			productDetails.setUnitOfMeasurement(out.get("unit_of_measurement")==null?"":out.get("unit_of_measurement").toString());
			productDetails.setProductNetQuantity(out.get("ots_net_quantity")==null?"":out.get("ots_net_quantity").toString());
			productDetails.setOtsProductCountry(out.get("ots_product_country")==null?"":out.get("ots_product_country").toString());
			productDetails.setOtsProductCountryCode(out.get("ots_product_country_code")==null?"":out.get("ots_product_country_code").toString());
			productDetails.setOtsProductCurrency(out.get("ots_product_currency")==null?"":out.get("ots_product_currency").toString());
			productDetails.setOtsProductCurrencySymbol(out.get("ots_product_currency_symbol")==null?"":out.get("ots_product_currency_symbol").toString());
			productDetails.setOtsOemModelNumber(out.get("ots_oem_model_number")==null?"":out.get("ots_oem_model_number").toString());
			productDetails.setOtsOemPartNumber(out.get("ots_oem_part_number")==null?"":out.get("ots_oem_part_number").toString());
			productDetails.setOtsOemShortDescription(out.get("ots_oem_short_description")==null?"":out.get("ots_oem_short_description").toString());
			productDetails.setOtsOemLongDescription(out.get("ots_oem_long_description")==null?"":out.get("ots_oem_long_description").toString());
			productDetails.setOtsOemUom(out.get("ots_oem_uom")==null?"":out.get("ots_oem_uom").toString());
			productDetails.setOtsVendorItemCode(out.get("ots_vendor_item_code")==null?"":out.get("ots_vendor_item_code").toString());
			productDetails.setOtsProductDetailsPdf(out.get("ots_product_details_pdf")==null?"":out.get("ots_product_details_pdf").toString());		
			productDetails.setVariantFlag(out.get("variant_flag")==null?"":out.get("variant_flag").toString());
			productDetails.setOtsProductTagValue(out.get("ots_product_tag_value")==null?"":out.get("ots_product_tag_value").toString());
			productDetails.setOtsManufacturerName(out.get("ots_manufacturer_name")==null?"":out.get("ots_manufacturer_name").toString());
			productDetails.setOtsManufacturerAddress(out.get("ots_manufacturer_address")==null?"":out.get("ots_manufacturer_address").toString());
			productDetails.setOtsManufacturerGenericName(out.get("ots_manufacturer_generic_name")==null?"":out.get("ots_manufacturer_generic_name").toString());
			productDetails.setOtsManufacturerPackingImport(out.get("ots_manufacturer_packing_import")==null?"":out.get("ots_manufacturer_packing_import").toString());
			productDetails.setOtsConsumerCareName(out.get("ots_consumer_care_name")==null?"":out.get("ots_consumer_care_name").toString());
			productDetails.setOtsConsumerCareEmail(out.get("ots_consumer_care_email")==null?"":out.get("ots_consumer_care_email").toString());
			productDetails.setOtsConsumerCarePhoneNumber(out.get("ots_consumer_care_phone_number")==null?"":out.get("ots_consumer_care_phone_number").toString());
			productDetails.setOriginCountry(out.get("origin_country")==null?"":out.get("origin_country").toString());
			productDetails.setOtsTimeToShip(out.get("ots_time_to_ship")==null?"":out.get("ots_time_to_ship").toString());
			productDetails.setOtsTimeToDeliver(out.get("ots_time_to_deliver")==null?"":out.get("ots_time_to_deliver").toString());
			productDetails.setOtsSellerPickupReturn(out.get("ots_seller_pickup_return")==null?"":out.get("ots_seller_pickup_return").toString());
			productDetails.setOtsCodAvailability(out.get("ots_cod_availability")==null?"":out.get("ots_cod_availability").toString());
			productDetails.setOtsNutritionalFlag(out.get("ots_nutritional_flag")==null?"":out.get("ots_nutritional_flag").toString());
			productDetails.setOtsNutritionalInfo(out.get("ots_nutritional_info")==null?"":out.get("ots_nutritional_info").toString());
			productDetails.setOtsNutritionalAdditivesInfo(out.get("ots_nutritional_additives_info")==null?"":out.get("ots_nutritional_additives_info").toString());
			productDetails.setOtsNutritionalBrandOwnerFSSAILicenseNo(out.get("ots_nutritional_brand_owner_fssai_license_no")==null?"":out.get("ots_nutritional_brand_owner_fssai_license_no").toString());	
			productDetails.setOtsNutritionalOtherFSSAILicenseNo(out.get("ots_nutritional_other_fssai_license_no")==null?"":out.get("ots_nutritional_other_fssai_license_no").toString());
			productDetails.setOtsNutritionalImporterFSSAILicenseNo(out.get("ots_nutritional_importer_fssai_license_no")==null?"":out.get("ots_nutritional_importer_fssai_license_no").toString());

			//To fetch Distributor Details, Average Rating, Product Stock & Attributes Only for Products & Variants
			if(productDetails.getProductLevel().equalsIgnoreCase("3") || productDetails.getProductLevel().equalsIgnoreCase("4")) {
				//setting Distributor Details only for Product & Variants
				UserDetails distributorDetails = userServiceDAO.getUserDetails(productDetails.getDistributorId());
				productDetails.setDistributerName(distributorDetails == null?"":distributorDetails.getFirstName()+" "+distributorDetails.getLastName());
				productDetails.setDistributorEmailId(distributorDetails == null?"":distributorDetails.getEmailId());
				
				//setting Average Rating & Total Rating Count for Products
				AverageReviewRatingResponse averageReviewRatingResponse = otsUserService.getAverageRatingOfProduct(productDetails.getProductId());
				productDetails.setProductAverageRating(averageReviewRatingResponse== null?"":averageReviewRatingResponse.getAverageReviewRating().getAverageRating().toString());
				productDetails.setProductTotalRatingCount(averageReviewRatingResponse== null?"":averageReviewRatingResponse.getAverageReviewRating().getTotalReviewRating().toString());
			
				//setting Product Stock Quantity for Products
				List<GetProductBOStockResponse> productStock = productStockDao.getProductStockByProductId(productDetails.getProductId());
				productDetails.setProductStockQuantity(productStock.size() == 0? "": productStock.get(0).getStockQuantity());
				
				//setting Attributes for Products
				List<AttributeDetails> productAttribute = getProductAttribute(productDetails.getProductId());
				productDetails.setProductAttribute(productAttribute);
			}
			
			//setting category Id and name  for product in domain
			CategoryDetails category = getCategoryForProductId(productDetails.getProductId());
			productDetails.setCategoryId(category == null?"":category.getCategoryId().toString());	
			productDetails.setCategoryName(category == null?"":category.getCategoryName().toString());
			
			//setting Sub-category Id and name  for product in domain
			SubCategoryDetails subcategory = getSubCategoryForProductId(productDetails.getProductId());
			productDetails.setSubCategoryId(subcategory == null?"":subcategory.getSubCategoryId().toString());	
			productDetails.setSubCategoryName(subcategory == null?"":subcategory.getSubcategoryName().toString());
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productDetails;
	}
	
	@Override
	public List<ProductDetails> getCategoryAndSubCategoryByDistributor(GetCategorySubCategoryByDistributorRequest getCategorySubCategoryByDistributorRequest) {
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try {
			//To set default value as "1" for key "category"
			if(getCategorySubCategoryByDistributorRequest.getRequest().getSearchKey().equalsIgnoreCase("category")) {
				getCategorySubCategoryByDistributorRequest.getRequest().setSearchValue("1");
			}
			
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("search_key", getCategorySubCategoryByDistributorRequest.getRequest().getSearchKey());
			inParamMap.put("search_value", getCategorySubCategoryByDistributorRequest.getRequest().getSearchValue());
			inParamMap.put("distributor_id",UUID.fromString(getCategorySubCategoryByDistributorRequest.getRequest().getDistributorId()));

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_category_and_subcategory_by_distributor")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_key", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_value", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				productList.add(convertProductDetailsFromProcedureToDomain(out.get(i)));
			}
			
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productList;
	}
	
	@Override
	public List<ProductDetails> getCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest) {
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try {
			//To set default value as "1" for key "category"
			if(getCatgeorySubcategoryRequest.getRequest().getSearchKey().equalsIgnoreCase("category")) {
				getCatgeorySubcategoryRequest.getRequest().setSearchValue("1");
			}
			
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("search_key", getCatgeorySubcategoryRequest.getRequest().getSearchKey());
			inParamMap.put("search_value", getCatgeorySubcategoryRequest.getRequest().getSearchValue());
			inParamMap.put("product_country_code", getCatgeorySubcategoryRequest.getRequest().getCountryCode());

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_category_and_subcategory")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_key", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_value", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_country_code", Types.VARCHAR));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				productList.add(convertPageloaderProductDetailsFromProcedureToDomain(out.get(i)));
			}
			
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productList;
	}
	
	@Override
	public List<ProductDetails> getParentAndVariantSiblingProducts(String variantProductId) {		
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("variant_product_id", UUID.fromString(variantProductId));

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_parent_and_variant_sibling_products")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("variant_product_id", Types.OTHER));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				productList.add(convertProductDetailsFromProcedureToDomain(out.get(i)));
			}
			
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productList;
	}
	
	@Override
	public List<AttributeDetails> getProductAttribute(String productId){
		List<AttributeDetails> attributeDetails = new ArrayList<>();
		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_product_attribute")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			
			Map<String, Object> productInfo=new HashMap<String, Object>();
			productInfo.put("product_id",UUID.fromString(productId));
			Map<String, Object> simpleJdbcCallResultForProductData = simpleJdbcCall.execute(productInfo);

			List<Map<String, Object>> attributeDetailsResultSet = (List<Map<String, Object>>) simpleJdbcCallResultForProductData.get("#result-set-1");
			
			for(int i=0; i<attributeDetailsResultSet.size(); i++) {
				attributeDetails.add(convertAttributeDetailsFromProcedureToDomain(attributeDetailsResultSet.get(i)));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return attributeDetails;
	}
	
	private AttributeDetails convertAttributeDetailsFromProcedureToDomain(Map<String, Object> out) {
		AttributeDetails attributeDetails = new AttributeDetails();
		try {
			attributeDetails.setOtsProductAttributeMappingId(out.get("ots_product_attribute_mapping_id")==null?"":out.get("ots_product_attribute_mapping_id").toString());
			attributeDetails.setOtsProductId(out.get("ots_product_id")==null?"":out.get("ots_product_id").toString());
			attributeDetails.setOtsAttributeKeyId(out.get("ots_attribute_key_id")==null?"":out.get("ots_attribute_key_id").toString());
			attributeDetails.setOtsAttributeValueId(out.get("ots_attribute_value_id")==null?"":out.get("ots_attribute_value_id").toString());
			attributeDetails.setOtsAttributeKeyName(out.get("ots_attribute_key_name")==null?"":out.get("ots_attribute_key_name").toString());
			attributeDetails.setOtsAttributeValueName(out.get("ots_attribute_value_name")==null?"":out.get("ots_attribute_value_name").toString());
			attributeDetails.setOtsProductName(out.get("ots_product_name")==null?"":out.get("ots_product_name").toString());
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return attributeDetails;
	}
	
	@Override
	public List<ProductDetails> getParentAndChildProductsByChildID(String productId) {
	    List<ProductDetails> productDetails = new ArrayList<>();
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("product_id", UUID.fromString(productId));
	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_parent_and_child_products_by_child_id")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
	        List<Map<String, Object>> productDetailsResultSet = (List<Map<String, Object>>) queryResult.get("#result-set-1");
	        for(int i=0; i<productDetailsResultSet.size(); i++) {
	        	productDetails.add(convertProductDetailsFromProcedureToDomain(productDetailsResultSet.get(i)));
			}
	    } catch (Exception e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage());
	        e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
	    }
	    return productDetails;
	}

	@Override
	public ProductDetailsBOResponse getParentProductforDistributor(String distributerId)
	{
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			OtsUsers DistributorId = new OtsUsers();
			DistributorId.setOtsUsersId(UUID.fromString(distributerId));
			
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("distributorId", DistributorId);
			List<OtsProduct> productList  = super.getResultListByNamedQuery("OtsProduct.getParentProductListByDistributor", queryParameter);
					
			List<ProductDetails> productDetails =  productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
		    productDetailsBOResponse.setProductDetails(productDetails);
		}catch (NoResultException e) {
	    	logger.error("Exception while fetching data from DB :"+e.getMessage());
			e.printStackTrace();
	    	throw new BusinessException(e.getMessage(), e);
	    }
		return productDetailsBOResponse;
	}
	
	@Override
	public List<ProductDetails> getRecentlyAddedProductList(String levelId,String productCountryCode){
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try 
		{
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("level_id", Integer.parseInt(levelId));
			inParamMap.put("product_country_code",productCountryCode);

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
			    	.withFunctionName("get_recently_added_product_list")
					.withSchemaName("public")
			        .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("level_id", Types.INTEGER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_country_code", Types.VARCHAR));
			
			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				productList.add(convertPageloaderProductDetailsFromProcedureToDomain(out.get(i)));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productList;
	}
	
	@Override
	public List<ProductDetails> getProductAndVarientsByStatus(String status) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("status", status);
			List<OtsProduct> productList = super.getResultListByNamedQuery("OtsProduct.getProductAndVarientsByStatus", queryParameter);
			productDetails = productList.stream().map(otsProduct -> convertProductDetailsFromEntityToDomain(otsProduct)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetails;
	}
	
	@Override
	public List<ProductDetails> getSiblingVariantProductsByPrimaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("product_id", getSiblingVariantProductsByAttributeRequest.getRequest().getProductId());
			inParamMap.put("primary_attribute_key", getSiblingVariantProductsByAttributeRequest.getRequest().getPrimaryAttributeKey());
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_sibling_variant_products_by_primary_key")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("primary_attribute_key", Types.VARCHAR));
			
			Map<String, Object> result = simpleJdbcCall.execute(inParamMap);
			
			List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return productDetails;
	        }

	        String jsonResponse = resultSet.get(0).get("result").toString();
	        System.out.println("Parsed JSON Response: " + jsonResponse);

	        // Parse JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);

	        // Parse product list
	        JsonNode productDetailsNode = rootNode.get("productDetails");
	        if (productDetailsNode != null && productDetailsNode.isArray()) {
	            for (JsonNode productNode : productDetailsNode) {
	                Map<String, Object> productMap = objectMapper.convertValue(productNode, new TypeReference<Map<String, Object>>() {});
	                productDetails.add(convertProductDetailsFromProcedureToDomain(productMap));
	            }
	        }
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return productDetails;
	}

	@Override
	public List<ProductDetails> getSiblingVariantProductsBySecondaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("product_id", getSiblingVariantProductsByAttributeRequest.getRequest().getProductId());
			inParamMap.put("primary_attribute_key", getSiblingVariantProductsByAttributeRequest.getRequest().getPrimaryAttributeKey());
			inParamMap.put("primary_attribute_value", getSiblingVariantProductsByAttributeRequest.getRequest().getPrimaryAttributeValue());
			inParamMap.put("secondary_attribute_key", getSiblingVariantProductsByAttributeRequest.getRequest().getSecondaryAttributeKey());
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_sibling_variant_products_by_secondary_key")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("primary_attribute_key", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("primary_attribute_value", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("secondary_attribute_key", Types.VARCHAR));
			
			Map<String, Object> result = simpleJdbcCall.execute(inParamMap);
			
			List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return productDetails;
	        }

	        String jsonResponse = resultSet.get(0).get("result").toString();
	        System.out.println("Parsed JSON Response: " + jsonResponse);

	        // Parse JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);

	        // Parse product list
	        JsonNode productDetailsNode = rootNode.get("productDetails");
	        if (productDetailsNode != null && productDetailsNode.isArray()) {
	            for (JsonNode productNode : productDetailsNode) {
	                Map<String, Object> productMap = objectMapper.convertValue(productNode, new TypeReference<Map<String, Object>>() {});
	                productDetails.add(convertProductDetailsFromProcedureToDomain(productMap));
	            }
	        }
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return productDetails;
	}
	
	@Override
	public List<ProductDetails> filterProductsByGeneralProperties(FilterProductsByGeneralPropertiesRequest filterProductsByGeneralPropertiesRequest) {
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try {
		
			Map<String, Object> inParamMapForDynamicProductAttribute = new HashMap<String, Object>();
			inParamMapForDynamicProductAttribute.put("price_min", filterProductsByGeneralPropertiesRequest.getRequest().getPricemin()==""?null:filterProductsByGeneralPropertiesRequest.getRequest().getPricemin());
			inParamMapForDynamicProductAttribute.put("price_max", filterProductsByGeneralPropertiesRequest.getRequest().getPricemax()==""? null:filterProductsByGeneralPropertiesRequest.getRequest().getPricemax());
			inParamMapForDynamicProductAttribute.put("discount_min", filterProductsByGeneralPropertiesRequest.getRequest().getDiscountmin()==""?null:filterProductsByGeneralPropertiesRequest.getRequest().getDiscountmin());
			inParamMapForDynamicProductAttribute.put("discount_threshold", filterProductsByGeneralPropertiesRequest.getRequest().getDiscountthreshold()==""?null:filterProductsByGeneralPropertiesRequest.getRequest().getDiscountthreshold());
			inParamMapForDynamicProductAttribute.put("rating_min", filterProductsByGeneralPropertiesRequest.getRequest().getRatingmin()==""?null:filterProductsByGeneralPropertiesRequest.getRequest().getRatingmin());
			inParamMapForDynamicProductAttribute.put("rating_threshold", filterProductsByGeneralPropertiesRequest.getRequest().getRatingthreshold()==""?null:filterProductsByGeneralPropertiesRequest.getRequest().getRatingthreshold());
			inParamMapForDynamicProductAttribute.put("stock_qty_min", filterProductsByGeneralPropertiesRequest.getRequest().getStockqtymin()==""?null:filterProductsByGeneralPropertiesRequest.getRequest().getStockqtymin());
			inParamMapForDynamicProductAttribute.put("stock_qty_threshold", filterProductsByGeneralPropertiesRequest.getRequest().getStockqtythreshold()==""?null:filterProductsByGeneralPropertiesRequest.getRequest().getStockqtythreshold());
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("filter_products_by_general_properties")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("price_min", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("price_max", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("discount_min", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("discount_threshold", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("rating_min", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("rating_threshold", Types.VARCHAR)); 
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("stock_qty_min", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("stock_qty_threshold", Types.VARCHAR));
			
			Map<String, Object> simpleJdbcCallResultForProductAttribute = simpleJdbcCall.execute(inParamMapForDynamicProductAttribute);
			
			List<Map<String, Object>> result = (List<Map<String, Object>>) simpleJdbcCallResultForProductAttribute.get("#result-set-1");
			System.out.println("am printing my output  ="+result);
			 for(int i=0; i<result.size(); i++) {
		        	productDetails.add(convertProductDetailsFromProcedureToDomain(result.get(i)));
			}

		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}	
		return productDetails;
	}
	
	@Override
	public List<ProductDetails> getProductsBySubCategoryAndDistributor(GetProductsBySubCategoryAndDistributorRequest getProductsBySubCategoryAndDistributorRequest) {		
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("sub_category_id", UUID.fromString(getProductsBySubCategoryAndDistributorRequest.getRequest().getSubcategoryId()));
			inParamMap.put("distributor_id", UUID.fromString(getProductsBySubCategoryAndDistributorRequest.getRequest().getDistributorId()));

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_products_by_subcategory_and_distributor")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("sub_category_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			System.out.println("am printing ="+out.size());
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				productList.add(convertProductDetailsFromProcedureToDomain(out.get(i)));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productList;
	}
	
	@Override
	public List<ProductDetails> getVariantsByProductId(String productId) {		
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			inParamMap.put("product_id",UUID.fromString(productId)); 

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_variants_by_product_id")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			System.out.println("am printing ="+out.size());
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				productList.add(convertProductDetailsFromProcedureToDomain(out.get(i)));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productList;
	}
	
	
	@Override
	public List<ProductDetails> getVariantsProductByDistributor(String distributorId) {		
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("distributor_id",UUID.fromString(distributorId)); 

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_products_mapped_to_variant_products")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			System.out.println("am printing ="+out.size());
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				productList.add(convertProductDetailsFromProcedureToDomain(out.get(i)));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productList;
	}
	
	@Override
	public Map<String, List<ProductDetails>> getPageloaderRecentlyAddedProductList(String levelid) {
	    Map<String, List<ProductDetails>> countryProductMap = new HashMap<>();
	    try {
	        Map<String, Object> inParamMap = new HashMap<>();
	        inParamMap.put("level_id", Integer.parseInt(levelid));

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withFunctionName("get_pageloader_recently_added_product_list") // update if name differs
	            .withSchemaName("public")
	            .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("level_id", Types.INTEGER));
	      
	        //calling stored procedure and getting response
	        Map<String, Object> result = simpleJdbcCall.execute(inParamMap);

	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return countryProductMap;
	        }

	        String jsonResponse = resultSet.get(0).get("result").toString();

	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);

	        // Iterate through each country code key
	        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
	        while (fields.hasNext()) {
	            Map.Entry<String, JsonNode> entry = fields.next();
	            String countryCode = entry.getKey();
	            JsonNode productArray = entry.getValue();

	            List<ProductDetails> productList = new ArrayList<>();
	            if (productArray.isArray()) {
	                for (JsonNode productNode : productArray) {
	                    Map<String, Object> productMap = objectMapper.convertValue(
	                        productNode,
	                        new TypeReference<Map<String, Object>>() {}
	                    );
	                    productList.add(convertProductDetailsFromProcedureToDomain(productMap));
	                }
	            }
	            countryProductMap.put(countryCode, productList);
	        }

	    } catch (Exception e) {
	        logger.error("Exception while fetching data from DB: " + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    }

	    return countryProductMap;
	}
	
	@Override
	public Map<String, List<ProductDetails>> getPageloaderCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest) {
		Map<String, List<ProductDetails>> countryProductMap = new HashMap<>();
		try {
			//To set default value as "1" for key "category"
			if(getCatgeorySubcategoryRequest.getRequest().getSearchKey().equalsIgnoreCase("category")) {
				getCatgeorySubcategoryRequest.getRequest().setSearchValue("1");
			}
			
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("search_key", getCatgeorySubcategoryRequest.getRequest().getSearchKey());
			inParamMap.put("search_value", getCatgeorySubcategoryRequest.getRequest().getSearchValue());

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_pageloader_category_and_subcategory")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_key", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_value", Types.VARCHAR));

			//calling stored procedure and getting response
			Map<String, Object> result = simpleJdbcCall.execute(inParamMap);

	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return countryProductMap;
	        }

	        String jsonResponse = resultSet.get(0).get("result").toString();

	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResponse);

	        // Iterate through each country code key
	        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
	        while (fields.hasNext()) {
	            Map.Entry<String, JsonNode> entry = fields.next();
	            String countryCode = entry.getKey();
	            JsonNode productArray = entry.getValue();

	            List<ProductDetails> productList = new ArrayList<>();
	            if (productArray.isArray()) {
	                for (JsonNode productNode : productArray) {
	                    Map<String, Object> productMap = objectMapper.convertValue(
	                        productNode,
	                        new TypeReference<Map<String, Object>>() {}
	                    );
	                    productList.add(convertProductDetailsFromProcedureToDomain(productMap));
	                }
	            }
	            countryProductMap.put(countryCode, productList);
	        }
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return countryProductMap;
	}
	
	@Override
	public ProductDetails addDuplicateProductByCountry(AddProductByCountryRequest addProductByCountryRequest) {
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductId", UUID.fromString(addProductByCountryRequest.getRequest().getProductId()));
			OtsProduct oldProduct = super.getResultByNamedQuery("OtsProduct.findByOtsProductId", queryParameter);
			
			//To generate random
//			Random random = new Random();
			SecureRandom secureRandom = new SecureRandom();
	        int eightDigitNumber = 10000000 + secureRandom.nextInt(90000000); // Ensures the number is 8 digits
	        
	        //To generate random UUID & insert into table   
			UUID uuid=UUID.randomUUID();
			
			OtsProduct newProduct = new OtsProduct();
			newProduct.setOtsProductId(uuid);
			newProduct.setOtsProductNumber(productNoFormat+eightDigitNumber);
			newProduct.setOtsProductName(oldProduct.getOtsProductName());
			newProduct.setOtsProductDescription(oldProduct.getOtsProductDescription());
			newProduct.setOtsProductDescriptionLong(oldProduct.getOtsProductDescriptionLong());
			newProduct.setOtsProductStatus(oldProduct.getOtsProductStatus());
			newProduct.setOtsProductLevelId(oldProduct.getOtsProductLevelId());
			newProduct.setOtsProductHsnSac(oldProduct.getOtsProductHsnSac());
			newProduct.setOtsProductSellerPrice(oldProduct.getOtsProductSellerPrice());
			newProduct.setOtsProductBasePrice(new BigDecimal(addProductByCountryRequest.getRequest().getProductBasePrice()));
			newProduct.setOtsProductPrice(new BigDecimal(addProductByCountryRequest.getRequest().getProductPrice()));
			newProduct.setOtsProductDiscountPercentage(addProductByCountryRequest.getRequest().getProductDiscountPercentage());
			newProduct.setOtsProductDiscountPrice(addProductByCountryRequest.getRequest().getProductDiscountPrice());
			newProduct.setOtsProductGst(addProductByCountryRequest.getRequest().getGst());
			newProduct.setOtsProductGstPrice(addProductByCountryRequest.getRequest().getGstPrice());
			newProduct.setOtsProductFinalPriceWithGst(addProductByCountryRequest.getRequest().getProductFinalPrice());
			newProduct.setOtsProductImage(oldProduct.getOtsProductImage());
			newProduct.setOtsDistributorId(oldProduct.getOtsDistributorId());
			newProduct.setOtsMultiProductImage1(oldProduct.getOtsMultiProductImage1());
			newProduct.setOtsMultiProductImage2(oldProduct.getOtsMultiProductImage2());
			newProduct.setOtsMultiProductImage3(oldProduct.getOtsMultiProductImage3());
			newProduct.setOtsMultiProductImage4(oldProduct.getOtsMultiProductImage4());
			newProduct.setOtsMultiProductImage5(oldProduct.getOtsMultiProductImage5());
			newProduct.setOtsMultiProductImage6(oldProduct.getOtsMultiProductImage6());
			newProduct.setOtsMultiProductImage7(oldProduct.getOtsMultiProductImage7());
			newProduct.setOtsMultiProductImage8(oldProduct.getOtsMultiProductImage8());
			newProduct.setOtsMultiProductImage9(oldProduct.getOtsMultiProductImage9());
			newProduct.setOtsMultiProductImage10(oldProduct.getOtsMultiProductImage10());
			newProduct.setOtsProductCountry(addProductByCountryRequest.getRequest().getOtsProductCountry());
			newProduct.setOtsProductCountryCode(addProductByCountryRequest.getRequest().getOtsProductCountryCode());
			newProduct.setOtsProductCurrency(addProductByCountryRequest.getRequest().getOtsProductCurrency());
			newProduct.setOtsProductCurrencySymbol(addProductByCountryRequest.getRequest().getOtsProductCurrencySymbol());
			newProduct.setOtsProductDeliveryCharge(addProductByCountryRequest.getRequest().getProductDeliveryCharge());
			newProduct.setOtsProductReturnDeliveryCharge(new Integer(addProductByCountryRequest.getRequest().getProductReturnDeliveryCharge()));
			newProduct.setCreatedUser(oldProduct.getCreatedUser());
			newProduct.setOtsProductBulkEligible(oldProduct.getOtsProductBulkEligible());
			newProduct.setOtsProductBulkMinQty(oldProduct.getOtsProductBulkMinQty());
			newProduct.setOtsProductDeliveryPolicy(addProductByCountryRequest.getRequest().getProductDeliveryPolicy());
			newProduct.setOtsProductCancellationAvailability(new Boolean(addProductByCountryRequest.getRequest().getProductCancellationAvailability()));
			newProduct.setOtsProductCancellationPolicy(addProductByCountryRequest.getRequest().getProductCancellationPolicy());
			newProduct.setOtsProductReplacementAvailability(new Boolean(addProductByCountryRequest.getRequest().getProductReplacementAvailability()));
			newProduct.setOtsProductReplacementPolicy(addProductByCountryRequest.getRequest().getProductReplacementPolicy());
			newProduct.setOtsProductReplacementDays(addProductByCountryRequest.getRequest().getProductReplacementDays().equalsIgnoreCase("")?"0":addProductByCountryRequest.getRequest().getProductReplacementDays());
			newProduct.setOtsProductReturnAvailability(new Boolean(addProductByCountryRequest.getRequest().getProductReturnAvailability()));
			newProduct.setOtsProductReturnPolicy(addProductByCountryRequest.getRequest().getProductReturnPolicy());
			newProduct.setOtsProductReturnDays(addProductByCountryRequest.getRequest().getProductReturnDays().equalsIgnoreCase("")?"0":addProductByCountryRequest.getRequest().getProductReturnDays());
			newProduct.setUnitOfMeasurement(oldProduct.getUnitOfMeasurement());
			newProduct.setBulkAvailability(oldProduct.getBulkAvailability());
			newProduct.setOtsProductTag(oldProduct.getOtsProductTag());
			newProduct.setOtsNetQuantity(oldProduct.getOtsNetQuantity());
			newProduct.setVariantFlag(oldProduct.getVariantFlag());
			newProduct.setOtsProductTagValue(oldProduct.getOtsProductTagValue());
			newProduct.setOtsManufacturerName(oldProduct.getOtsManufacturerName());
			newProduct.setOtsManufacturerAddress(oldProduct.getOtsManufacturerAddress());
			newProduct.setOtsManufacturerGenericName(oldProduct.getOtsManufacturerGenericName());
			newProduct.setOtsManufacturerPackingImport(oldProduct.getOtsManufacturerPackingImport());
			newProduct.setOtsConsumerCareName(oldProduct.getOtsConsumerCareName());
			newProduct.setOtsConsumerCareEmail(oldProduct.getOtsConsumerCareEmail());
			newProduct.setOtsConsumerCarePhoneNumber(oldProduct.getOtsConsumerCarePhoneNumber());
			newProduct.setOriginCountry(oldProduct.getOriginCountry());
			newProduct.setOtsTimeToShip(addProductByCountryRequest.getRequest().getOtsTimeToShip());
			newProduct.setOtsTimeToDeliver(addProductByCountryRequest.getRequest().getOtsTimeToDeliver());
			newProduct.setOtsSellerPickupReturn(addProductByCountryRequest.getRequest().getOtsSellerPickupReturn().equalsIgnoreCase("")?null:addProductByCountryRequest.getRequest().getOtsSellerPickupReturn());
			newProduct.setOtsCodAvailability(addProductByCountryRequest.getRequest().getOtsCodAvailability().equalsIgnoreCase("")?null:addProductByCountryRequest.getRequest().getOtsCodAvailability());
			newProduct.setOtsNutritionalFlag(oldProduct.getOtsNutritionalFlag());
			newProduct.setOtsNutritionalInfo(oldProduct.getOtsNutritionalInfo());
			newProduct.setOtsNutritionalAdditivesInfo(oldProduct.getOtsNutritionalAdditivesInfo());
			newProduct.setOtsNutritionalBrandOwnerFSSAILicenseNo(oldProduct.getOtsNutritionalBrandOwnerFSSAILicenseNo());
			newProduct.setOtsNutritionalOtherFSSAILicenseNo(oldProduct.getOtsNutritionalOtherFSSAILicenseNo());
			newProduct.setOtsNutritionalImporterFSSAILicenseNo(oldProduct.getOtsNutritionalImporterFSSAILicenseNo());
			newProduct.setOtsOemModelNumber(oldProduct.getOtsOemModelNumber());
			newProduct.setOtsOemPartNumber(oldProduct.getOtsOemPartNumber());
			newProduct.setOtsOemShortDescription(oldProduct.getOtsOemShortDescription());
			newProduct.setOtsOemLongDescription(oldProduct.getOtsOemLongDescription());
			newProduct.setOtsOemUom(oldProduct.getOtsOemUom());
			newProduct.setOtsVendorItemCode(oldProduct.getOtsVendorItemCode());
			newProduct.setOtsProductDetailsPdf(oldProduct.getOtsProductDetailsPdf());
			
			save(newProduct);
			
			ProductDetails productDetails = convertProductDetailsFromEntityToDomain(newProduct);
			
			return productDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        return null;
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	    	return null;
		}	
	}
	
	@Override
	public String addProductDetails(AddProductDetails addProductDetails) {
		try {
			//To Add New Product
			if (addProductDetails.getProductId() == null || addProductDetails.getProductId().isEmpty()) {
				OtsProduct otsProduct = new OtsProduct();

				//Setting Created User Id for Product
				Useraccounts createdUser = new Useraccounts();
				createdUser.setAccountId(UUID.fromString(addProductDetails.getCreatedUser()));
				otsProduct.setCreatedUser(createdUser);
				
				//Setting Distributor Id for Product
				OtsUsers distributorId = new OtsUsers();
				distributorId.setOtsUsersId(UUID.fromString(addProductDetails.getDistributorId()));
				otsProduct.setOtsDistributorId(distributorId);

				//Setting Product Level ID for Product
				OtsProductLevel productLevel = new OtsProductLevel();
				productLevel.setOtsProductLevelId(Integer.parseInt(addProductDetails.getProductLevelId()));
				otsProduct.setOtsProductLevelId(productLevel);
				
				//To generate random
				Random random = new Random();
				SecureRandom secureRandom = new SecureRandom();
				int eightDigitNumber = 10000000 + secureRandom.nextInt(90000000); // Ensures the number is 8 digits
				
				UUID uuid = UUID.randomUUID();
				otsProduct.setOtsProductId(uuid);
				otsProduct.setOtsProductNumber(productNoFormat+eightDigitNumber);
				otsProduct.setOtsProductName(addProductDetails.getProductName());
				otsProduct.setOtsProductDescription(addProductDetails.getProductDescription());
				otsProduct.setOtsProductDescriptionLong(addProductDetails.getProductDescriptionLong());
				otsProduct.setOtsProductStatus(addProductDetails.getProductStatus());
				otsProduct.setUnitOfMeasurement(addProductDetails.getUnitOfMeasurement());
				otsProduct.setOtsProductHsnSac(addProductDetails.getProductHsnSac());
				otsProduct.setOtsProductImage(getValueOrNull(addProductDetails.getProductImage()));
				otsProduct.setOtsMultiProductImage1(getValueOrNull(addProductDetails.getMultiProductImage1()));
				otsProduct.setOtsMultiProductImage2(getValueOrNull(addProductDetails.getMultiProductImage2()));
				otsProduct.setOtsMultiProductImage3(getValueOrNull(addProductDetails.getMultiProductImage3()));
				otsProduct.setOtsMultiProductImage4(getValueOrNull(addProductDetails.getMultiProductImage4()));
				otsProduct.setOtsMultiProductImage5(getValueOrNull(addProductDetails.getMultiProductImage5()));
				otsProduct.setOtsMultiProductImage6(getValueOrNull(addProductDetails.getMultiProductImage6()));
				otsProduct.setOtsMultiProductImage7(getValueOrNull(addProductDetails.getMultiProductImage7()));
				otsProduct.setOtsMultiProductImage8(getValueOrNull(addProductDetails.getMultiProductImage8()));
				otsProduct.setOtsMultiProductImage9(getValueOrNull(addProductDetails.getMultiProductImage9()));
				otsProduct.setOtsMultiProductImage10(getValueOrNull(addProductDetails.getMultiProductImage10()));
				otsProduct.setOtsProductCountry(getValueOrNull(addProductDetails.getOtsProductCountry()));
				otsProduct.setOtsProductCountryCode(getValueOrNull(addProductDetails.getOtsProductCountryCode()));
				otsProduct.setOtsProductCurrency(getValueOrNull(addProductDetails.getOtsProductCurrency()));
				otsProduct.setOtsProductCurrencySymbol(getValueOrNull(addProductDetails.getOtsProductCurrencySymbol()));
				otsProduct.setOtsProductDetailsPdf(getValueOrNull(addProductDetails.getOtsProductDetailsPdf()));
				
				save(otsProduct);
				System.out.println(otsProduct);
				super.getEntityManager().flush();
				return otsProduct.getOtsProductId().toString();
			}
			else {
				//To Update Existing Product
				OtsProduct otsProduct = new OtsProduct();
				Map<String, Object> queryParameter = new HashMap<>();
				queryParameter.put("otsProductId", UUID.fromString(addProductDetails.getProductId()));
				
				try {
					otsProduct = super.getResultByNamedQuery("OtsProduct.findByOtsProductId", queryParameter);
				}catch (NoResultException e) {
					return null;
				}
				System.out.println("old status = "+otsProduct.getOtsProductStatus());
				//If Product status = Active then set Active else check if the Request Status is > Previous Status else set the Request Status
				if(addProductDetails.getProductStatus().equalsIgnoreCase("active")) {
					otsProduct.setOtsProductStatus(addProductDetails.getProductStatus());
				}else if(Integer.parseInt(otsProduct.getOtsProductStatus()) >Integer.parseInt(addProductDetails.getProductStatus()))
				{
					otsProduct.setOtsProductStatus(otsProduct.getOtsProductStatus());
				}else {
					otsProduct.setOtsProductStatus(addProductDetails.getProductStatus());
				}
				
				otsProduct.setOtsProductName(addProductDetails.getProductName());
				otsProduct.setOtsProductDescription(addProductDetails.getProductDescription());
				otsProduct.setOtsProductDescriptionLong(addProductDetails.getProductDescriptionLong());
				otsProduct.setOtsProductHsnSac(addProductDetails.getProductHsnSac());
				otsProduct.setUnitOfMeasurement(addProductDetails.getUnitOfMeasurement());
				otsProduct.setOtsProductImage(getValueOrNull(addProductDetails.getProductImage()));
				otsProduct.setOtsMultiProductImage1(getValueOrNull(addProductDetails.getMultiProductImage1()));
				otsProduct.setOtsMultiProductImage2(getValueOrNull(addProductDetails.getMultiProductImage2()));
				otsProduct.setOtsMultiProductImage3(getValueOrNull(addProductDetails.getMultiProductImage3()));
				otsProduct.setOtsMultiProductImage4(getValueOrNull(addProductDetails.getMultiProductImage4()));
				otsProduct.setOtsMultiProductImage5(getValueOrNull(addProductDetails.getMultiProductImage5()));
				otsProduct.setOtsMultiProductImage6(getValueOrNull(addProductDetails.getMultiProductImage6()));
				otsProduct.setOtsMultiProductImage7(getValueOrNull(addProductDetails.getMultiProductImage7()));
				otsProduct.setOtsMultiProductImage8(getValueOrNull(addProductDetails.getMultiProductImage8()));
				otsProduct.setOtsMultiProductImage9(getValueOrNull(addProductDetails.getMultiProductImage9()));
				otsProduct.setOtsMultiProductImage10(getValueOrNull(addProductDetails.getMultiProductImage10()));
				otsProduct.setOtsProductDetailsPdf(addProductDetails.getOtsProductDetailsPdf());
				save(otsProduct);
				System.out.println(otsProduct);
				return otsProduct.getOtsProductId().toString();
			}
		} catch (Exception e) {
			logger.error("Exception while Inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data into DB :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override 
	public String addProductPricingDetails(ProductPricingDetails productPricingDetails){
		try {
			OtsProduct otsProduct = new OtsProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductId",UUID.fromString(productPricingDetails.getProductId()));
			try {
				otsProduct = super.getResultByNamedQuery("OtsProduct.findByOtsProductId", queryParameter);
			}catch (NoResultException e) {
				return null;
			}
			
			//If Product status = Active then set Active else check if the Request Status is > Previous Status else set the Request Status
			if(productPricingDetails.getProductStatus().equalsIgnoreCase("active")) {
				otsProduct.setOtsProductStatus(productPricingDetails.getProductStatus());
			}else if(Integer.parseInt(otsProduct.getOtsProductStatus()) >Integer.parseInt(productPricingDetails.getProductStatus()))
			{
				otsProduct.setOtsProductStatus(otsProduct.getOtsProductStatus());
			}else {
				otsProduct.setOtsProductStatus(productPricingDetails.getProductStatus());
			}
			
			otsProduct.setOtsProductSellerPrice(productPricingDetails.getProductSellerPrice());
			otsProduct.setOtsProductBasePrice(getBigDecimalOrNull(productPricingDetails.getProductBasePrice()));
			otsProduct.setOtsProductPrice(getBigDecimalOrNull(productPricingDetails.getProductPrice()));
			otsProduct.setOtsProductDiscountPercentage(productPricingDetails.getProductDiscountPercentage());	
			otsProduct.setOtsProductDiscountPrice(productPricingDetails.getProductDiscountPrice());	
			otsProduct.setOtsProductGst(productPricingDetails.getGst());
			otsProduct.setOtsProductGstPrice(productPricingDetails.getGstPrice().toString());
			otsProduct.setOtsProductFinalPriceWithGst(productPricingDetails.getProductFinalPriceWithGst());
			otsProduct.setOtsProductDeliveryCharge(productPricingDetails.getProductDeliveryCharge());
			otsProduct.setOtsProductReturnDeliveryCharge(Integer.parseInt(productPricingDetails.getProductReturnDeliveryCharge()));	
			save(otsProduct);
			return otsProduct.getOtsProductId().toString();
		} catch (Exception e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override 	
	public String addProductPolicyDetails(ProductPolicy productPolicy){
	    try {
			OtsProduct otsProduct = new OtsProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductId", UUID.fromString(productPolicy.getProductId()));
			try {
				otsProduct = super.getResultByNamedQuery("OtsProduct.findByOtsProductId", queryParameter);
			}catch (NoResultException e) {
				return null;
			}
			
			//If Product status = Active then set Active else check if the Request Status is > Previous Status else set the Request Status
			if(productPolicy.getProductStatus().equalsIgnoreCase("active")) {
				otsProduct.setOtsProductStatus(productPolicy.getProductStatus());
			}else if(Integer.parseInt(otsProduct.getOtsProductStatus()) >Integer.parseInt(productPolicy.getProductStatus()))
			{
				otsProduct.setOtsProductStatus(otsProduct.getOtsProductStatus());
			}else {
				otsProduct.setOtsProductStatus(productPolicy.getProductStatus());
			}
			
			otsProduct.setOtsProductDeliveryPolicy(productPolicy.getProductDeliveryPolicy());
			otsProduct.setOtsProductCancellationAvailability(convertToBoolean(productPolicy.getProductCancellationAvailability()));
			otsProduct.setOtsProductCancellationPolicy(productPolicy.getProductCancellationPolicy());
			otsProduct.setOtsProductReplacementAvailability(convertToBoolean(productPolicy.getProductReplacementAvailability()));
			otsProduct.setOtsProductReplacementPolicy(productPolicy.getProductReplacementPolicy());
			otsProduct.setOtsProductReplacementDays(productPolicy.getProductReplacementDays().equalsIgnoreCase("")?"0":productPolicy.getProductReplacementDays());
			otsProduct.setOtsProductReturnAvailability(convertToBoolean(productPolicy.getProductReturnAvailability()));
			otsProduct.setOtsProductReturnPolicy(productPolicy.getProductReturnPolicy());
			otsProduct.setOtsProductReturnDays(productPolicy.getProductReturnDays().equalsIgnoreCase("")?"0":productPolicy.getProductReturnDays());
			otsProduct.setOtsTimeToShip(productPolicy.getTimeToShip());
			otsProduct.setOtsTimeToDeliver(productPolicy.getTimeToDeliver());
			otsProduct.setOtsSellerPickupReturn(getValueOrNull(productPolicy.getSellerPickupReturn()));
			otsProduct.setOtsCodAvailability(getValueOrNull(productPolicy.getCodAvailability()));
			save(otsProduct);
			return otsProduct.getOtsProductId().toString();
		} catch (Exception e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}	
	}
	
	@Override 	
	public String addProductManufactureDetails(ProductManufactureDetails productManufactureDetails){
		try {
			OtsProduct otsProduct = new OtsProduct();
			Map<String, Object> queryParameter = new HashMap<>();
			queryParameter.put("otsProductId", UUID.fromString(productManufactureDetails.getProductId()));
			try {
				otsProduct = super.getResultByNamedQuery("OtsProduct.findByOtsProductId", queryParameter);
			}catch (NoResultException e) {
				return null;
			}
			//If Product status = Active then set Active else check if the Request Status is > Previous Status else set the Request Status
			if(productManufactureDetails.getProductStatus().equalsIgnoreCase("active")) {
				otsProduct.setOtsProductStatus(productManufactureDetails.getProductStatus());
			}else if(Integer.parseInt(otsProduct.getOtsProductStatus()) >Integer.parseInt(productManufactureDetails.getProductStatus()))
			{
				otsProduct.setOtsProductStatus(otsProduct.getOtsProductStatus());
			}else {
				otsProduct.setOtsProductStatus(productManufactureDetails.getProductStatus());
			}
			
			otsProduct.setOtsManufacturerName(productManufactureDetails.getManufacturerName());
			otsProduct.setOtsManufacturerAddress(productManufactureDetails.getManufacturerAddress());
			otsProduct.setOtsManufacturerGenericName(productManufactureDetails.getManufacturerGenericName());
			otsProduct.setOtsManufacturerPackingImport(productManufactureDetails.getManufacturerPackingImport());
			otsProduct.setOtsConsumerCareName(productManufactureDetails.getConsumerCareName());
			otsProduct.setOtsConsumerCareEmail(productManufactureDetails.getConsumerCareEmail());
			otsProduct.setOtsConsumerCarePhoneNumber(productManufactureDetails.getConsumerCarePhoneNumber());
			otsProduct.setOriginCountry(productManufactureDetails.getOriginCountry());
			otsProduct.setOtsOemModelNumber(productManufactureDetails.getOtsOemModelNumber());
			otsProduct.setOtsOemPartNumber(productManufactureDetails.getOtsOemPartNumber());
			otsProduct.setOtsOemUom(productManufactureDetails.getOtsOemUom());
			otsProduct.setOtsOemShortDescription(getValueOrNull(productManufactureDetails.getOtsOemShortDescription()));
			otsProduct.setOtsOemLongDescription(getValueOrNull(productManufactureDetails.getOtsOemLongDescription()));
			otsProduct.setOtsVendorItemCode(productManufactureDetails.getOtsVendorItemCode());
			otsProduct.setOtsNutritionalInfo(getValueOrNull(productManufactureDetails.getOtsNutritionalInfo()));
			otsProduct.setOtsNutritionalAdditivesInfo(getValueOrNull(productManufactureDetails.getOtsNutritionalAdditivesInfo()));
			otsProduct.setOtsNutritionalBrandOwnerFSSAILicenseNo(getValueOrNull(productManufactureDetails.getOtsNutritionalBrandOwnerFSSAILicenseNo()));
			otsProduct.setOtsNutritionalOtherFSSAILicenseNo(getValueOrNull(productManufactureDetails.getOtsNutritionalOtherFSSAILicenseNo()));
			otsProduct.setOtsNutritionalImporterFSSAILicenseNo(getValueOrNull(productManufactureDetails.getOtsNutritionalImporterFSSAILicenseNo()));
		
			save(otsProduct);
			return otsProduct.getOtsProductId().toString();
		} catch (Exception e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :" + e.getMessage());
			e.printStackTrace();
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String addOrUpdateCategoryAndSubcategory(AddOrUpdateCategoryRequest addOrUpdateCategoryRequest) {
	    try {
	        // To Add New Category Or SubCategory
	        if (addOrUpdateCategoryRequest.getRequest().getProductId() == null 
	                || addOrUpdateCategoryRequest.getRequest().getProductId().isEmpty()) {

	            OtsProduct otsProduct = new OtsProduct();

	            // Setting Created User Id for Product
	            Useraccounts createdUser;
	            try {
	                createdUser = super.getEntityManager().find(
	                        Useraccounts.class, 
	                        UUID.fromString(addOrUpdateCategoryRequest.getRequest().getCreatedUser())
	                );
	                if (createdUser == null) {
	                    return "Invalid Admin Id";
	                }
	            } catch (Exception e) {
	                return "Invalid Admin Id";
	            }
	            otsProduct.setCreatedUser(createdUser);

	            // Setting Product Level ID for Product
	            OtsProductLevel productLevel = new OtsProductLevel();
	            productLevel.setOtsProductLevelId(
	                    Integer.parseInt(addOrUpdateCategoryRequest.getRequest().getProductLevelId())
	            );
	            otsProduct.setOtsProductLevelId(productLevel);

	            // Creating Id
	            UUID uuid = UUID.randomUUID();
	            otsProduct.setOtsProductId(uuid);
	            otsProduct.setOtsProductName(addOrUpdateCategoryRequest.getRequest().getProductName());
	            otsProduct.setOtsProductImage(addOrUpdateCategoryRequest.getRequest().getProductImage());

	            // Nutritional flag only for Category
	            if (productLevel.getOtsProductLevelId() == 1) {
	                otsProduct.setOtsNutritionalFlag(
	                        Boolean.parseBoolean(addOrUpdateCategoryRequest.getRequest().getNutritionalFlag())
	                );
	            } else {
	                otsProduct.setOtsNutritionalFlag(false);
	            }
	            otsProduct.setOtsProductStatus("active");

	            save(otsProduct);

	            // Add Category mapping if SubCategory
	            if (addOrUpdateCategoryRequest.getRequest().getProductLevelId().equalsIgnoreCase("2")) {
	                ProductCategoryMapping productCategoryMapping = new ProductCategoryMapping();
	                productCategoryMapping.setOtsProductId(otsProduct.getOtsProductId().toString());
	                productCategoryMapping.setOtsProductCategoryId(addOrUpdateCategoryRequest.getRequest().getCategoryId());
	                productCategoryMapping.setCreatedUser(addOrUpdateCategoryRequest.getRequest().getCreatedUser());
	                productCategoryMappingDAO.addProductCategoryMapping(productCategoryMapping);
	            }

	            return "Inserted Successfully";

	        } else {
	            // To Update Existing Category Or SubCategory
	            Map<String, Object> queryParameter = new HashMap<>();
	            queryParameter.put("otsProductId", UUID.fromString(addOrUpdateCategoryRequest.getRequest().getProductId()));

	            OtsProduct otsProduct;
	            try {
	                otsProduct = super.getResultByNamedQuery("OtsProduct.findByOtsProductId", queryParameter);
	            } catch (NoResultException e) {
	                return null;
	            }
	            // Update fields
	            otsProduct.setOtsProductName(addOrUpdateCategoryRequest.getRequest().getProductName());
	            otsProduct.setOtsProductImage(addOrUpdateCategoryRequest.getRequest().getProductImage());

	            save(otsProduct); 
	            return "Updated Successfully";
	        }

	    } catch (Exception e) {
	        logger.error("Exception while fetching data to DB  :" + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Throwable e) {
	        logger.error("Unexpected error while fetching data to DB  :" + e.getMessage(), e);
	        throw new BusinessException(e.getMessage(), e);
	    }
	}

	@Override
	public ProductSearchResponse searchProductByNamePagination(SearchProductByNamePaginationRequest searchProductRequest) {
	    ProductSearchResponse response = new ProductSearchResponse();
	    try {
	    	// Step 1: Prepare input parameters
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			inParamMap.put("page_number", searchProductRequest.getRequest().getPageNumber());
			inParamMap.put("data_size", searchProductRequest.getRequest().getDataSize());
			inParamMap.put("search_term", searchProductRequest.getRequest().getProductName());
			inParamMap.put("product_country_code", searchProductRequest.getRequest().getProductCountryCode());

	        // Step 2: Set up JDBC function call
	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	            .withFunctionName("search_product_by_name_pagination")
	            .withSchemaName("public")
	            .withoutProcedureColumnMetaDataAccess();

	        // Step 3: Declare function parameters
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("page_number", Types.INTEGER));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("data_size", Types.INTEGER));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_term", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_country_code", Types.VARCHAR));

	        // Step 4: Execute the function
	        Map<String, Object> result = simpleJdbcCall.execute(inParamMap);

	        // Step 5: Extract result JSON
	        List<Map<String, Object>> resultSet = (List<Map<String, Object>>) result.get("#result-set-1");
	        if (resultSet == null || resultSet.isEmpty() || resultSet.get(0).get("result") == null) {
	            return response;
	        }

	        String jsonResult = resultSet.get(0).get("result").toString();

	        // Step 6: Parse the JSON result
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode rootNode = objectMapper.readTree(jsonResult);

	        // Step 7: Extract searchedProduct
	        JsonNode searchedNode = rootNode.get("searchedProduct");
	        List<ProductDetails> searchedList = new ArrayList<>();
	        if (searchedNode != null && searchedNode.isArray()) {
	            for (JsonNode productNode : searchedNode) {
	                Map<String, Object> productMap = objectMapper.convertValue(
	                    productNode,
	                    new TypeReference<Map<String, Object>>() {}
	                );
	                searchedList.add(convertPageloaderProductDetailsFromProcedureToDomain(productMap));
	            }
	        }

	        // Step 8: Extract similarProduct
	        JsonNode similarNode = rootNode.get("similarProduct");
	        List<ProductDetails> similarList = new ArrayList<>();
	        if (similarNode != null && similarNode.isArray()) {
	            for (JsonNode productNode : similarNode) {
	                Map<String, Object> productMap = objectMapper.convertValue(
	                    productNode,
	                    new TypeReference<Map<String, Object>>() {}
	                );
	                similarList.add(convertPageloaderProductDetailsFromProcedureToDomain(productMap));
	            }
	        }

	        // Step 9: Extract totalProductsCount and totalPages
	        String totalCount = rootNode.has("totalProductsCount") ? rootNode.get("totalProductsCount").toString() : "0";
	        String totalPages = rootNode.has("totalPages") ? rootNode.get("totalPages").toString() : "0";

	        // Step 10: Set final response
	        response.setSearchedProduct(searchedList);
	        response.setSimilarProduct(similarList);
	        response.setTotalProductsCount(totalCount);
	        response.setTotalPages(totalPages);

	    }catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
	    return response;
	}
	
	@Override
	public List<ProductDetails> getProductsBySubCategoryWithIntermediateStatus(String subcategoryId) {		
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try {
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("sub_category_id", UUID.fromString(subcategoryId));

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_products_by_subcategory_with_intermediate_status")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("sub_category_id", Types.OTHER));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			System.out.println("am printing ="+out.size());
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				productList.add(convertProductDetailsFromProcedureToDomain(out.get(i)));
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productList;
	}
	
	@Override
	public List<ProductDetails> getCategoryAndSubCategoryWithAttribute(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest) {
		List<ProductDetails> productList = new ArrayList<ProductDetails>();
		try {
			//To set default value as "1" for key "category"
			if(getCatgeorySubcategoryRequest.getRequest().getSearchKey().equalsIgnoreCase("category")) {
				getCatgeorySubcategoryRequest.getRequest().setSearchValue("1");
			}
			
			Map<String, Object> inParamMap = new HashMap<String, Object>();				
			//setting up parameter for the pagination variable
			inParamMap.put("search_key", getCatgeorySubcategoryRequest.getRequest().getSearchKey());
			inParamMap.put("search_value", getCatgeorySubcategoryRequest.getRequest().getSearchValue());

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_category_and_subcategory_with_attribute")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();
			
			//setting up the data type for the JDBC call
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_key", Types.VARCHAR));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("search_value", Types.VARCHAR));

			//calling stored procedure and getting response
			Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(inParamMap);
			List<Map<String, Object>> out = (List<Map<String, Object>>) simpleJdbcCallResult.get("#result-set-1");
			
			//to convert procedure output into product details object
			for(int i=0; i<out.size(); i++) {
				productList.add(convertProductDetailsFromProcedureToDomain(out.get(i)));
			}
			
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
		return productList;
	}
	
	@Override
	public List<ProductDetails> getSimilarProducts(GetSimilarProductRequest getSimilarProductRequest) {
		List<ProductDetails> productDetails = new ArrayList<>();
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			String namedQuery = null;

			switch (getSimilarProductRequest.getRequest().getSearchKey()) {
				case "manufacturerName":
					namedQuery = "OtsProduct.getProductByOemManufaturerName";
					queryParameter.put("otsManufacturerName", getSimilarProductRequest.getRequest().getSearchValue());
					break;
	
				case "oemPartNumber":
					namedQuery = "OtsProduct.getProductByOemPartNumber";
					queryParameter.put("otsOemPartNumber", getSimilarProductRequest.getRequest().getSearchValue());
					break;
	
				default:
					return productDetails;
			}

			List<OtsProduct> productList = super.getResultListByNamedQuery(namedQuery, queryParameter);
			if(productList.size() == 0) {
				return productDetails;
			}else {
				productDetails = productList.stream().map(this::convertProductDetailsFromEntityToDomain)
						.collect(Collectors.toList());
			}
			return productDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	    	e.printStackTrace();
	        throw new BusinessException(e.getMessage(), e);
		}
	}

}
