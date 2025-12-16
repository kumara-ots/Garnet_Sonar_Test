package com.fuso.enterprise.ots.srv.functional.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fuso.enterprise.ots.srv.api.model.domain.AddProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.AddProductStock;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeKey;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValue;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValueName;
import com.fuso.enterprise.ots.srv.api.model.domain.GetAttributeKeysAndAttributeValues;
import com.fuso.enterprise.ots.srv.api.model.domain.GetCatgeorySubcategoryModel;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductAttributesMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductCategoryMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductManufactureDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductManufacturerDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductPolicy;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductPricingDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductStockDetail;
import com.fuso.enterprise.ots.srv.api.model.domain.SubCategoryAttributeMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSProductService;
import com.fuso.enterprise.ots.srv.api.service.functional.OTSUserService;
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
import com.fuso.enterprise.ots.srv.api.service.request.GetSellerForProductRequest;
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
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;
import com.fuso.enterprise.ots.srv.server.dao.AttributeKeyDAO;
import com.fuso.enterprise.ots.srv.server.dao.AttributeValueDAO;
import com.fuso.enterprise.ots.srv.server.dao.OrderProductDAO;
import com.fuso.enterprise.ots.srv.server.dao.OrderServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductAttributesMappingDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductCategoryMappingDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductManufacturerDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductServiceDAO;
import com.fuso.enterprise.ots.srv.server.dao.ProductStockDao;
import com.fuso.enterprise.ots.srv.server.dao.ProductStockHistoryDao;
import com.fuso.enterprise.ots.srv.server.dao.SellerProductMappingDAO;
import com.fuso.enterprise.ots.srv.server.dao.SubcategoryAttributeMappingDAO;
import com.fuso.enterprise.ots.srv.server.dao.UserServiceDAO;
import com.fuso.enterprise.ots.srv.server.util.OTSUtil;

@Service
@Transactional
public class OTSProductServiceImpl implements OTSProductService {
	
	@Value("${ots.excelpath}")
	public String excelPath;
	
	@Value("${ots.imagepath}")
	public String imagePath;
	
	@Autowired
	private OTSUserService otsUserService;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private ProductStockDao productStockDao;
	private ProductStockHistoryDao productStockHistoryDao;
	private ProductServiceDAO productServiceDAO;
	private SellerProductMappingDAO sellerProductMappingDAO;
	private AttributeKeyDAO attributeKeyDAO;
	private AttributeValueDAO attributeValueDAO;
	private ProductAttributesMappingDAO productAttributesMappingDAO; 
	private SubcategoryAttributeMappingDAO subcategoryAttributeMappingDAO;
	private ProductCategoryMappingDAO productCategoryMappingDAO;
	private UserServiceDAO userServiceDAO;
	private ProductManufacturerDAO productManufacturerDAO;
	
	@Inject
	public OTSProductServiceImpl(ProductServiceDAO productServiceDAO,ProductStockDao productStockDao,ProductStockHistoryDao productStockHistoryDao,OrderProductDAO orderProductDAO,
			OrderServiceDAO orderServiceDAO,SellerProductMappingDAO sellerProductMappingDAO,AttributeKeyDAO attributeKeyDAO,AttributeValueDAO attributeValueDAO,
			ProductAttributesMappingDAO productAttributesMappingDAO,SubcategoryAttributeMappingDAO subcategoryAttributeMappingDAO,ProductCategoryMappingDAO productCategoryMappingDAO,
			UserServiceDAO userServiceDAO,ProductManufacturerDAO productManufacturerDAO) {
		this.productServiceDAO=productServiceDAO;
		this.productStockDao = productStockDao;
		this.productStockHistoryDao=productStockHistoryDao;
		this.attributeKeyDAO = attributeKeyDAO;
		this.attributeValueDAO = attributeValueDAO;
		this.productAttributesMappingDAO = productAttributesMappingDAO;
		this.subcategoryAttributeMappingDAO = subcategoryAttributeMappingDAO;
		this.productCategoryMappingDAO = productCategoryMappingDAO;
		this.userServiceDAO = userServiceDAO;
		this.productManufacturerDAO = productManufacturerDAO;
	}
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	//search key details and combinations are added in e-taarana api document in zoho
	@Override
	public ProductDetailsBOResponse getProductList(ProductDetailsBORequest productDetailsBORequest) {
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			if(productDetailsBORequest.getRequestData().getSearchKey().equalsIgnoreCase("levelId")) {
				//---------------------to get list of all category by levelId-------------------------------------
				productDetailsBOResponse = productServiceDAO.getProductByLevelId(productDetailsBORequest.getRequestData().getSearchvalue());
			}else if(productDetailsBORequest.getRequestData().getSearchKey().equalsIgnoreCase("subcategory")){
				//---------------------to get list of sub category mapped to category-------------------------------------
				List<ProductDetails> productDetails = productServiceDAO.getSubCategoryByCategory(productDetailsBORequest.getRequestData().getSearchvalue());
				productDetailsBOResponse.setProductDetails(productDetails);
			}else if(productDetailsBORequest.getRequestData().getSearchKey().equalsIgnoreCase("product")){
				//---------------------to get list of products mapped to Sub category-------------------------------------
				List<ProductDetails> productDetails = productServiceDAO.getProductBySubCategory(productDetailsBORequest.getRequestData().getSearchvalue());
				productDetailsBOResponse.setProductDetails(productDetails);
			}else if(productDetailsBORequest.getRequestData().getSearchKey().equalsIgnoreCase("pagination")){ 
				//---------------------To get the paginated product for the given range-------------------------------------
				productDetailsBOResponse = productServiceDAO.getProductPagination(productDetailsBORequest);
			}else if(productDetailsBORequest.getRequestData().getSearchKey().equalsIgnoreCase("singleProduct")) {
				//---------------------to get single product details-------------------------------------
				List<ProductDetails> ProductDetails = new ArrayList<ProductDetails> ();
				ProductDetails.add(productServiceDAO.getProductDetails(productDetailsBORequest.getRequestData().getSearchvalue()));
				productDetailsBOResponse.setProductDetails(ProductDetails);
			}else if(productDetailsBORequest.getRequestData().getSearchKey().equalsIgnoreCase("All") || productDetailsBORequest.getRequestData().getSearchKey().equalsIgnoreCase("ProductId")
					|| productDetailsBORequest.getRequestData().getSearchKey().equalsIgnoreCase("ProductName") || productDetailsBORequest.getRequestData().getSearchKey().equalsIgnoreCase("Letter")){
				//---------------------to get the all product with our any condition-------------------------------------
				productDetailsBOResponse = productServiceDAO.getProductList(productDetailsBORequest);	
			}else {
				//---------------------setting dummy list for response for invalid search key-------------------------------------
				 productDetailsBOResponse.setProductDetails(new ArrayList<ProductDetails>());
			}
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}	
		return productDetailsBOResponse;
	}

	@Override
	public String addOrUpdateProductStock(AddProductStockBORequest addProductBORequest) {
		String strResponse = null;
		try {
			strResponse = productStockDao.addProductStock(addProductBORequest);
			productStockHistoryDao.addProductStockHistory(addProductBORequest);	
		}catch(Exception e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}catch (Throwable e) {
    		logger.error("Exception while fetching data from DB:"+e.getMessage());
    		throw new BusinessException(e.getMessage(), e);
    	}
		return strResponse;
	}

	@Override 
	public GetProductStockListBOResponse getProductStockList(GetProductStockListRequest getProductStockRequest) {
	    GetProductStockListBOResponse getProductStockListBOResponse = new GetProductStockListBOResponse();
	    List<ProductStockDetail> productStockDetailList = new ArrayList<>();
	    try {
	    	Map<String, Object> productInfo = new HashMap<>();
		    productInfo.put("distributor_id", UUID.fromString(getProductStockRequest.getRequestData().getUserId()));

		    SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("get_product_stock_list")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

		    simpleJdbcCall.addDeclaredParameter(new SqlParameter("distributor_id", Types.OTHER));

		    Map<String, Object> simpleJdbcCallResultForProductData = simpleJdbcCall.execute(productInfo);
		    List<Map<String, Object>> outputResult = (List<Map<String, Object>>) simpleJdbcCallResultForProductData.get("#result-set-1");
		    
		    if(outputResult.size() == 0) {
		    	return null;
		    }else {
		    	 for (Map<String, Object> row : outputResult) {
		    		 productStockDetailList.add(convertPincodeDetailsFromProcedureToDomain(row));
			     }
		    }
		   
		    getProductStockListBOResponse.setProductStockDetail(productStockDetailList);
		    if(getProductStockRequest.getRequestData().getPdf().equalsIgnoreCase("yes") && productStockDetailList.size() != 0) {
				String pdf = productTransactionReportPdf(productStockDetailList);
				getProductStockListBOResponse.setPdf(pdf);
			}
		    return getProductStockListBOResponse;
	    }catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}	
	}
	
	private ProductStockDetail convertPincodeDetailsFromProcedureToDomain(Map<String, Object> outputResult) {
	    ProductStockDetail productDetail = new ProductStockDetail();
	    productDetail.setProductId(outputResult.get("ots_product_id") == null ? "" : outputResult.get("ots_product_id").toString());
	    productDetail.setProductName(outputResult.get("ots_product_name") == null ? "" : outputResult.get("ots_product_name").toString());
	    productDetail.setOtsprodcutStockActQty(outputResult.get("ots_product_stock_act_qty") == null ? "" : outputResult.get("ots_product_stock_act_qty").toString());
	    productDetail.setOtsOrderedQty(outputResult.get("ots_ordered_qty") == null ? "" : outputResult.get("ots_ordered_qty").toString());
	    productDetail.setOtsProductStockHistoryQty(outputResult.get("ots_product_stock_history_qty") == null ? "" : outputResult.get("ots_product_stock_history_qty").toString());
	    
	    return productDetail;
	}

	@Override
	public GetProductBOStockResponse getProductStockByUidAndPid(GetProductStockRequest getProductStockRequest) {
		try {
			GetProductBOStockResponse getProductBOStockResponse = new GetProductBOStockResponse();
			getProductBOStockResponse = productStockDao.getProductStockByUidAndPid(getProductStockRequest);
			return getProductBOStockResponse;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}	
	}

	@Override
	public String updateProductStatus(UpdateProductStatusRequest updateProductStatusRequest) {
		try {
			Map<String, Object> queryParameters = new HashMap<>();
			queryParameters.put("product_id",UUID.fromString(updateProductStatusRequest.getRequest().getProductId()));
			queryParameters.put("product_status",updateProductStatusRequest.getRequest().getStatus());
			
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("update_product_status")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_id", Types.OTHER));
			simpleJdbcCall.addDeclaredParameter(new SqlParameter("product_status", Types.VARCHAR));

			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);

			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");

			// converting output of procedure to String
			String response = outputResult.get(0).values().toString();
			System.out.println("response = " + response);

			// comparing response of procedure & handling response
			if (response.equalsIgnoreCase("[Updated]")) {
				return "Updated";
			} else if (response.equalsIgnoreCase("[Not Updated]")) {
				return "Not Updated";
			} else {
				return "Unexpected Response";
			}
		} catch (BusinessException e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	public String productTransactionReportPdf(List<ProductStockDetail> ProductStockDetailList) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		System.out.println("date = "+date);
		
		String tableValueString ="";
		String reportDetails = "<head ><h2 style='text-align:center;'><u><b>Product Transaction Report</b></u></h2></head>";
		reportDetails += "<head ><h3>Date: "+date+"</h3></head>  <br>";
		
		int slno=0;
		tableValueString = "<table border=\"1\" style='text-align:center;'><tr>\r\n" + 
				"	<th width=\"8%\" style='text-align:center;'>Sl no</th>\r\n" + 
				"	<th width=\"40%\" style='text-align:center;'>Product Name</th>\r\n" + 
				"	<th style='text-align:center;'>Stock Addition</th>\r\n" + 
				"	<th style='text-align:center;'>Ordered Quantity</th>\r\n" + 
				"	<th style='text-align:center;'>Present Stock</th>\r\n" + 
				"</tr>";
		for(ProductStockDetail  productDetails: ProductStockDetailList) {
			slno++;
			tableValueString=tableValueString+"<tr>\r\n" + 
					"	<td width=\"8%\" style='text-align:center;'>"+slno+"</td>\r\n" + 
					"   <td width=\"40%\">"+productDetails.getProductName()+"</td>\r\n" + 
					"	<td style='text-align:center;'>"+productDetails.getOtsProductStockHistoryQty()+"</td>\r\n" + 
					"	<td style='text-align:center;'>"+productDetails.getOtsOrderedQty()+"</td>\r\n" + 
					"	<td style='text-align:center;'>"+productDetails.getOtsprodcutStockActQty()+"</td>\r\n" +
					"</tr>";
		}
		tableValueString =tableValueString+ "</table>";
		String htmlString = "<html>"+reportDetails+tableValueString+"</html>";
		String path = OTSUtil.generateReportPDFFromHTMLPortrait(htmlString,"OrderRepo.pdf");
		byte[] fileContent;
		String encodedString = null;
		try {
			fileContent = FileUtils.readFileToByteArray(new File(path));
			encodedString = Base64.encodeBase64String(fileContent);
		} catch (IOException e) {
		    logger.error("Error while reading file from path: {}", path, e);
		    throw new BusinessException("Failed to read file", e);
		}
		return encodedString;	
	}
	
	public String ImageToBase64(String filePath) throws IOException {
		byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
		byte[] bytesEncoded = Base64.encodeBase64(fileContent);
		compressJPEGFile();
		return new String(bytesEncoded);		
	}
	
	public String compressJPEGFile() throws IOException {
		File input = new File("C:\\product\\data\\img.jpg");
		BufferedImage image = ImageIO.read(input);
		
		File output = new File("C:\\product\\data\\img.jpg");
        OutputStream out = new FileOutputStream(output);
        
        ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);
        
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()){
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.10f);
        }
        
        writer.write(null, new IIOImage(image, null, null), param);

        out.close();
        ios.close();
        writer.dispose();
        
        
		return null;
	}
	
	/*shreekant*/
	@Override
	public ProductDetailsBOResponse getAllProductDetails() {	
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try { 
			productDetails=productServiceDAO.getAllProductDetails();
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}
	
	@Override
	public ProductDetails getProductDetails(String productId) {
		ProductDetails productDetails = new ProductDetails();
		try {
			productDetails = productServiceDAO.getProductDetails(productId);
			return productDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}
	}
	
	
	@Override
	public List<UserDetails> getSellerForProduct(GetSellerForProductRequest getSellerForProductRequest) {
		try {
			return sellerProductMappingDAO.getSellerForProduct(getSellerForProductRequest);
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
		
	}

	@Override
	public ProductDetailsBOResponse getRecentlyAddedProductList(String levelId,String productCountryCode) {	
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
		try { 
			productDetails=productServiceDAO.getRecentlyAddedProductList(levelId,productCountryCode);
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}

	@Override
	public String getDeliveryChargeForProduct(String productId) {
		String deliveryCharge = null;
		try {
			deliveryCharge = productServiceDAO.getDeliveryChargeForProduct(productId);
		} catch (Exception e) {
			throw new BusinessException(e,ErrorEnumeration.GET_PRODUCT_LIST_FAILURE);
		}
		return deliveryCharge;
	}

	@Override
	public ProductDetailsBOResponse getProductListByDistributor(String distributorId) 
	{
		ProductDetailsBOResponse ProductDetailsBOResponse=new ProductDetailsBOResponse();
		try {
			ProductDetailsBOResponse=productServiceDAO.getProductListByDistributor(distributorId);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return ProductDetailsBOResponse;
	}
	
	@Override
	public ProductDetailsBOResponse getAllProductsWithDiscount() {	
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try { 
			List<ProductDetails> productDetails = new ArrayList<ProductDetails>();
			productDetails=productServiceDAO.getAllProductsWithDiscount();
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}	
		return productDetailsBOResponse;
	}
	
	@Override
	public ProductDetailsBOResponse getProductsByDistributorPagination(GetProductsByDistributorPaginationRequest getProductsByDistributorPagination) {
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			productDetailsBOResponse = productServiceDAO.getProductsByDistributorPagination(getProductsByDistributorPagination);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}
	
	@Override
	public String addStockForMultipleProductByDistributor(AddProductStockBORequest addProductBORequest) {
		String strResponse = null;
		try {
			//to get list of active products but distributor
			List<ProductDetails> productList = productServiceDAO.getActiveProductListByDistributor(addProductBORequest.getRequestData().getUsersId());
			if(productList.size()==0) {
				return strResponse = "Products Not Available To Add Stock Details";
			}else {
				for(int i=0; i<productList.size(); i++) {
					//to set product id into addProductStock request
					addProductBORequest.getRequestData().setProductId(productList.get(i).getProductId());
					
					//for adding product stock for all the products of distributor
					strResponse = productStockDao.addProductStock(addProductBORequest);
					productStockHistoryDao.addProductStockHistory(addProductBORequest);
				}
			}
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return strResponse;
	}
	
	@Override
	public ProductDetailsBOResponse getCategoryAndSubCategoryByDistributor(GetCategorySubCategoryByDistributorRequest getCategorySubCategoryByDistributorRequest) 
	{
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			List<ProductDetails> productDetails = productServiceDAO.getCategoryAndSubCategoryByDistributor(getCategorySubCategoryByDistributorRequest);
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}
	
	@Override
	public ProductDetailsBOResponse getCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest) 
	{
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			List<ProductDetails> productDetails = productServiceDAO.getCategoryAndSubCategory(getCatgeorySubcategoryRequest);
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}
	
	@Override
	public ProductDetailsBOResponse getParentAndVariantSiblingProducts(String variantProductId) 
	{
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			List<ProductDetails> productDetails = productServiceDAO.getParentAndVariantSiblingProducts(variantProductId);
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}

	@Override
	public SubCategoryAttributeResponse getAttributeKeyBySubcategory(String subcategoryId) 
	{
		SubCategoryAttributeResponse subcategoryAttributeResponse = new SubCategoryAttributeResponse();
		try {
			List<SubCategoryAttributeMapping> subcategoryAttributeMapping = subcategoryAttributeMappingDAO.getAttributeKeyBySubcategory(subcategoryId);
			subcategoryAttributeResponse.setSubcategoryAttributeDetails(subcategoryAttributeMapping);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return subcategoryAttributeResponse;
	}
	
	@Override
	public AttributeValueResponse getAttributeValueForAttributeKeyId(String attributeKeyId) 
	{
		AttributeValueResponse attributeValueResponse = new AttributeValueResponse();
		try {
			List<AttributeValue> attributeValueDetails = attributeValueDAO.getAttributeValueForAttributeKeyId(attributeKeyId);
			attributeValueResponse.setAttributeValueDetails(attributeValueDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return attributeValueResponse;
	}
	
	@Override
	public AttributeKeyResponse getAllAttributeKey() 
	{
		AttributeKeyResponse attributeKeyResponse = new AttributeKeyResponse();
		try {
			List<AttributeKey> attributeKeyDetails = attributeKeyDAO.getAllAttributeKey();
			attributeKeyResponse.setAttributeKeyDetails(attributeKeyDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return attributeKeyResponse;
	}

	@Override
	public List<AttributeKey> addAttributeKey(AddAttributeKeyRequest addAttributeKeyRequest) {
		try {
			List<AttributeKey> attributeKeys = attributeKeyDAO.addAttributeKey(addAttributeKeyRequest);
			return attributeKeys;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<AttributeValue> addAttributeValue(AddAttributeValueRequest addAttributeValueRequest) {
		try {
			List<AttributeValue> attributevalue = attributeValueDAO.addAttributeValue(addAttributeValueRequest);
			return attributevalue;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String updateAttributeKey(UpdateAttributeKeyRequest updateAttributeKeyRequest) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("attribute_name", updateAttributeKeyRequest.getRequest().getAttributeKeyName());
	        queryParameters.put("attribute_key_id", updateAttributeKeyRequest.getRequest().getAttributeKeyId());
	        queryParameters.put("attribute_description", updateAttributeKeyRequest.getRequest().getAttributeKeyDescription());

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("update_attribute_key")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("attribute_name", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("attribute_key_id", Types.INTEGER));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("attribute_description", Types.VARCHAR));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Exist]")) {
				return "This Attribute Key Name Already Exists";
			}else if(response.equalsIgnoreCase("[Updated]")) {
				return "This Attribute Key Updated Successfully";
				
			}else if(response.equalsIgnoreCase("[NotUpdated]")) {
				return "Not Updated";
			}else {
				return "Unexpected Response";
			}
	    }catch (BusinessException e){
			logger.error("Exception while fetching data from DB:"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
	    }catch (Throwable e) {
	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
	    }
	}
	
	@Override
	public String deleteAttributeKey(String attributeKeyId) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("attribute_key_id", Integer.parseInt(attributeKeyId));

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("delete_attribute_key")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("attribute_key_id", Types.INTEGER));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			
			//comparing response of procedure & handling response
		
			if(response.equalsIgnoreCase("[Cannot Be Deleted]")) {
				return "This Attribute Key Cannot Be Removed Due To Its Association With Mapped Products";
			}
			else if(response.equalsIgnoreCase("[Attribute Key Deleted Successfully]")) {
				return "This Attribute Key Deleted Successfully";
			}
			else if(response.equalsIgnoreCase("[Does Not Exist]")) {
				return "Attribute Key does not exist";
			}
			else {
				return "Unexpected Response";
			}
	    }catch (BusinessException e){
			logger.error("Exception while fetching data from DB:"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
	    }catch (Throwable e) {
	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
	    }
	}

	@Override
	public String updateAttributeValue(UpadteAttributeValueRequest upadteAttributeValueRequest) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("attribute_value_name", upadteAttributeValueRequest.getRequest().getAttributeValueName());
	        queryParameters.put("attribute_value_id", upadteAttributeValueRequest.getRequest().getAttributeValueId());

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	                .withProcedureName("update_attribute_value")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("attribute_value_name", Types.VARCHAR));
	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("attribute_value_id", Types.INTEGER));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
	
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			
			//comparing response of procedure & handling response
			if(response.equalsIgnoreCase("[Exist]")) {
				return "This Attribute Value Name Already Exists";
			}else if(response.equalsIgnoreCase("[Updated]")) {
				return "This Attribute Value Name Updated Successfully";
			}else {
				return "Unexpected Response";
			}
	    }catch (BusinessException e){
			logger.error("Exception while fetching data from DB:"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
	    }catch (Throwable e) {
	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
	    }
	}
	
	@Override
	public List<ProductAttributesMapping> addProductAttributesMapping(AddProductAttributeMappingRequest addProductAttributeMappingRequest) {
		try {
			List<ProductAttributesMapping> productMapping = productAttributesMappingDAO.addProductAttributesMapping(addProductAttributeMappingRequest.getRequest());
			return productMapping;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public ProductDetailsBOResponse getParentAndChildProductsByChildID(String variantProductId) {
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			List<ProductDetails> productDetails = productServiceDAO.getParentAndChildProductsByChildID(variantProductId);
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}
	
	@Override
	public List<AttributeValueName> checkAttributeValue(AddAttributeValueRequest addAttributeValueRequest) {
	    try {
	        List<AttributeValueName> attributevalue = attributeValueDAO.checkAttributeValue(addAttributeValueRequest);

	        return attributevalue;
	    } catch (Exception e) {
	        logger.error("Exception while fetching data from DB  :" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
	    } catch (Throwable e) {
	        logger.error("Exception while fetching data from DB  :" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
	    }
	}
	
	@Override
	public GetAttributeKeysAndAttributeValuesResponse getAllAttributeKeysAndValues() {
		GetAttributeKeysAndAttributeValuesResponse getAttributeKeyAndValue = new  GetAttributeKeysAndAttributeValuesResponse();  
		try {
			List<AttributeKey> attributeKeyDetails = attributeKeyDAO.getAllAttributeKey();
			if(attributeKeyDetails.size()==0){
				return null;
			}
			else {
				List<GetAttributeKeysAndAttributeValues> getAttributeKeysAndAttributeValues = new ArrayList<GetAttributeKeysAndAttributeValues>();
				for(int i=0;i<attributeKeyDetails.size();i++)
				{
					List<AttributeValue> attributeValue= attributeValueDAO.getAttributeValueForAttributeKeyId(attributeKeyDetails.get(i).getAttributeKeyId());
					getAttributeKeysAndAttributeValues.add(addAttributeKeyAndValueResponse(attributeKeyDetails.get(i),attributeValue));
				}
				getAttributeKeyAndValue.setResponse(getAttributeKeysAndAttributeValues);
				return getAttributeKeyAndValue ;
			}
			
		}catch (Exception e) {
	        logger.error("Exception while fetching data from DB  :" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
	    }catch (Throwable e) {
	        logger.error("Exception while fetching data from DB  :" + e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
	    }
	}
	
	private GetAttributeKeysAndAttributeValues addAttributeKeyAndValueResponse(AttributeKey attributeKey,List<AttributeValue> attributeValue ) {
		GetAttributeKeysAndAttributeValues statesDistrictsResponse = new GetAttributeKeysAndAttributeValues();
		statesDistrictsResponse.setAttributeKeyId(attributeKey.getAttributeKeyId());
		statesDistrictsResponse.setAttributeKeyName(attributeKey.getAttributeKeyName());
		statesDistrictsResponse.setAttributeValues(attributeValue);
		
		return statesDistrictsResponse;
	}
	
	@Override
	public String deleteAttributeValue(String attributeValueId) {
	    try {
	        Map<String, Object> queryParameters = new HashMap<>();
	        queryParameters.put("attribute_value_id", Integer.parseInt(attributeValueId));

	        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("delete_attribute_value")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

	        simpleJdbcCall.addDeclaredParameter(new SqlParameter("attribute_value_id", Types.INTEGER));

	        Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);
			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");
			
			//converting output of procedure to String
			String response = outputResult.get(0).values().toString();	
			System.out.println("response = "+response);
			
			//comparing response of procedure & handling response
		
			if(response.equalsIgnoreCase("[Cannot Be Deleted]")) {
				return "This Attribute Value Cannot Be Removed Due To Its Association With Mapped Products";
			}else if(response.equalsIgnoreCase("[Attribute Value Deleted Successfully]")) {
				return "Attribute Value Deleted Successfully";
			 }
			else if(response.equalsIgnoreCase("[Does Not Exist]")) {
				return "Attribute Value does not exist";
			}
			else {
				return "Unexpected Response";
			}
	    }catch (BusinessException e){
			logger.error("Exception while fetching data from DB:"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
	    }catch (Throwable e) {
	    	logger.error("Exception while fetching data from DB:"+e.getMessage());
        	throw new BusinessException(e.getMessage(), e);
	    }
	}
	
	@Override
	public ProductDetailsBOResponse getParentProductforDistributor(String distributerId) {
		ProductDetailsBOResponse ProductDetailsBOResponse=new ProductDetailsBOResponse();
		try {
			ProductDetailsBOResponse=productServiceDAO.getParentProductforDistributor(distributerId);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return ProductDetailsBOResponse;
	}
	
	@Override
	public AttributeKeyResponse getUnMappedAttributeKeyandValuesForSubcategory(String subcategoryId) {
		AttributeKeyResponse attributeKeyResponse = new AttributeKeyResponse();
		try {
			List<AttributeKey> attributeKeyDetails = attributeKeyDAO.getUnMappedAttributeKeyandValuesForSubcategory(subcategoryId);
			attributeKeyResponse.setAttributeKeyDetails(attributeKeyDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return attributeKeyResponse;
	}
	
	@Override
	public List<SubCategoryAttributeMapping> addAttributeKeyForSubcategory(AddAttributeKeyForSubCategoryRequest addAttributeKeyForSubCategoryRequest) {
		try {
			List<SubCategoryAttributeMapping> attributeKeyIdSubcatogory = subcategoryAttributeMappingDAO.addAttributeKeyForSubCategory(addAttributeKeyForSubCategoryRequest);
			return attributeKeyIdSubcatogory;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String deleteAttributeKeyMappedToSubcategory(String subcategoryAttributeMappingId) {
		try {
			Map<String, Object> queryParameters = new HashMap<>();
			queryParameters.put("subcategory_mapping_id", Integer.parseInt(subcategoryAttributeMappingId));

			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        		.withFunctionName("delete_subcategory_attribute_mapping")
	        		.withSchemaName("public")
	                .withoutProcedureColumnMetaDataAccess();

			simpleJdbcCall.addDeclaredParameter(new SqlParameter("subcategory_mapping_id", Types.INTEGER));

			Map<String, Object> queryResult = simpleJdbcCall.execute(queryParameters);

			List<Map<String, Object>> outputResult = (List<Map<String, Object>>) queryResult.get("#result-set-1");

			// converting output of procedure to String
			String response = outputResult.get(0).values().toString();
			System.out.println("response = " + response);

			// comparing response of procedure & handling response
			if (response.equalsIgnoreCase("[DO Not Exist]")) {
				return "Id Does Not Exist";
			} else if (response.equalsIgnoreCase("[Deleted]")) {
				return "Deleted Successfully";
			} else {
				return "Unexpected Response";
			}
		} catch (BusinessException e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB:" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public SubCategoryAttributeResponse getAttributeKeyValueBySubcategory(String subcategoryId) {
		SubCategoryAttributeResponse subcategoryAttributeResponse = new SubCategoryAttributeResponse();
		try {
			List<SubCategoryAttributeMapping> subcategoryAttributeMapping = subcategoryAttributeMappingDAO.getAttributeKeyBySubcategory(subcategoryId);
			if (subcategoryAttributeMapping == null) {
				return null;
			}
			for (int i = 0; i < subcategoryAttributeMapping.size(); i++) {
				List<AttributeValue> attributeValueDetails = attributeValueDAO.getAttributeValueForAttributeKeyId(subcategoryAttributeMapping.get(i).getOtsAttributeKeyId());
				subcategoryAttributeMapping.get(i).setAttributeValue(attributeValueDetails);
			}
			subcategoryAttributeResponse.setSubcategoryAttributeDetails(subcategoryAttributeMapping);

		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}

		return subcategoryAttributeResponse;
	}
	
	@Override
	public List<ProductDetails> getProductAndVarientsByStatus(String status) {
	    List<ProductDetails> productDetails = new ArrayList<>();
	    try {
	        productDetails = productServiceDAO.getProductAndVarientsByStatus(status);
	    } catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	    return productDetails;
	}
	
	@Override
	public List<ProductDetails> getSiblingVariantProductsByPrimaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest) {
	    List<ProductDetails> productDetails = new ArrayList<>();
	    try {
	        productDetails = productServiceDAO.getSiblingVariantProductsByPrimaryKey(getSiblingVariantProductsByAttributeRequest);
	    } catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	    return productDetails;
	}
	
	@Override
	public List<ProductDetails> getSiblingVariantProductsBySecondaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest) {
	    List<ProductDetails> productDetails = new ArrayList<>();
	    try {
	        productDetails = productServiceDAO.getSiblingVariantProductsBySecondaryKey(getSiblingVariantProductsByAttributeRequest);
	    } catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	    return productDetails;
	}
	
	@Override
	public List<ProductDetails> filterProductsByGeneralProperties(FilterProductsByGeneralPropertiesRequest filterProductsByGeneralPropertiesRequest) {
	    List<ProductDetails> productDetails = new ArrayList<>();
	    try {
	        productDetails = productServiceDAO.filterProductsByGeneralProperties(filterProductsByGeneralPropertiesRequest);
	    } catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	    return productDetails;
	}

	@Override
	public ProductDetailsBOResponse getProductsBySubCategoryAndDistributor(GetProductsBySubCategoryAndDistributorRequest getProductsBySubCategoryAndDistributorRequest) {
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			List<ProductDetails> productDetails = productServiceDAO.getProductsBySubCategoryAndDistributor(getProductsBySubCategoryAndDistributorRequest);
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}
	
	@Override
	public List<ProductDetails> getVariantsByProductId(String productID) {
	    List<ProductDetails> productDetails = new ArrayList<>();
	    try {
	        productDetails = productServiceDAO.getVariantsByProductId(productID);
	    } catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	    return productDetails;
	}
	

	@Override
	public List<ProductDetails> getVariantsProductByDistributor(String distributorId) {
	    List<ProductDetails> productDetails = new ArrayList<>();
	    try {
	        productDetails = productServiceDAO.getVariantsProductByDistributor(distributorId);
	    } catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	    return productDetails;
	}
	
	@Override
	public ProductDetailsPageloaderResponse getPageloaderRecentlyAddedProductList(String levelid) {	
		ProductDetailsPageloaderResponse productDetailsResponse = new ProductDetailsPageloaderResponse();
		try { 
			Map<String, List<ProductDetails>> productDetails = productServiceDAO.getPageloaderRecentlyAddedProductList(levelid);
			productDetailsResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data to DB  :"+e.getMessage());
	        throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsResponse;
	}
	
	@Override
	public ProductDetailsPageloaderResponse getPageloaderCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest){
		ProductDetailsPageloaderResponse productDetailsResponse = new ProductDetailsPageloaderResponse();
		try {
			Map<String, List<ProductDetails>> productDetails = productServiceDAO.getPageloaderCategoryAndSubCategory(getCatgeorySubcategoryRequest);
			productDetailsResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsResponse;
	}
	
	@Override
	public String addDuplicateProductByCountry(AddProductByCountryRequest addProductByCountryRequest) {
		try {
			//To fetch Old Product Details
			ProductDetails oldProduct = getProductDetails(addProductByCountryRequest.getRequest().getProductId());
			if(oldProduct == null) {
				return "Invalid Product";
			}
			
			//Add Duplicate Product for Parent Product with Country
			ProductDetails addProduct = productServiceDAO.addDuplicateProductByCountry(addProductByCountryRequest);
			if(addProduct == null) {
				return "Product Not Inserted";
			}
			
			ProductCategoryMapping productCategoryMapping = new ProductCategoryMapping();
			productCategoryMapping.setOtsProductId(addProduct.getProductId());
			productCategoryMapping.setOtsProductCategoryId(oldProduct.getSubCategoryId());
			productCategoryMapping.setCreatedUser(oldProduct.getCreatedUser());
			
			//Add Subcategory for Newly Created Product
			String addSubCategory = productCategoryMappingDAO.addProductCategoryMapping(productCategoryMapping);
			System.out.println("addSubCategory = "+addSubCategory);
			
			System.out.println("attributes = "+oldProduct.getProductAttribute());
			
			//Add Attributes if Parent Product Contains Attribute
			if(oldProduct.getProductAttribute().size() > 0) {
				List<ProductAttributesMapping> productAttributesMappingList = new ArrayList<>();
				for(int i=0; i<oldProduct.getProductAttribute().size(); i++) {
					ProductAttributesMapping productAttributesMapping = new ProductAttributesMapping();
					productAttributesMapping.setProductId(addProduct.getProductId());
					productAttributesMapping.setAttributeKeyId(oldProduct.getProductAttribute().get(i).getOtsAttributeKeyId());
					productAttributesMapping.setAttributeValueId(oldProduct.getProductAttribute().get(i).getOtsAttributeValueId());
					
					productAttributesMappingList.add(productAttributesMapping);
				}
				
				//Add Same Attributes Mapped to Parent Product for Duplicate Product
				List<ProductAttributesMapping> addProductAttribute = productAttributesMappingDAO.addProductAttributesMapping(productAttributesMappingList);
				System.out.println("addProductAttribute = "+addProductAttribute);
			}
			
			AddProductStockBORequest addProductStockBORequest = new AddProductStockBORequest();
			AddProductStock addProductStock = new AddProductStock();
			addProductStock.setProductId(addProduct.getProductId());
			addProductStock.setUsersId(addProduct.getDistributorId());
			addProductStock.setProductStockQty(addProductByCountryRequest.getRequest().getProductStockQuantity());
			addProductStock.setProductStockStatus("active");
			
			addProductStockBORequest.setRequestData(addProductStock);
			
			//Add Stock for Newly Added Product
			String addStock = addOrUpdateProductStock(addProductStockBORequest);
			
			return "Inserted Product Successfully";
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public String addOrUpdateProduct(AddOrUpdateProductRequest addOrUpdateProductRequest) {
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			String productId = null;	
			if (addOrUpdateProductRequest.getRequest().getProductDetails() != null) {
				
				AddProductDetails productDetails = addOrUpdateProductRequest.getRequest().getProductDetails();
				
				//Mandatory Request fields for both Adding & Updating Product
				if (productDetails.getProductName() == null || productDetails.getProductName().equalsIgnoreCase("")
						|| productDetails.getProductLevelId() == null  || productDetails.getProductLevelId().equals("")
						|| productDetails.getProductDescription() == null || productDetails.getProductDescription().equalsIgnoreCase("")
						|| productDetails.getProductDescriptionLong() == null || productDetails.getProductDescriptionLong().equalsIgnoreCase("")
						|| productDetails.getProductImage() == null || productDetails.getProductImage().equalsIgnoreCase("") 
						|| productDetails.getProductHsnSac() == null || productDetails.getProductHsnSac().equalsIgnoreCase("") 
						|| productDetails.getUnitOfMeasurement() == null || productDetails.getUnitOfMeasurement() .equalsIgnoreCase("")
						|| productDetails.getNetQuantity() == null || productDetails.getNetQuantity().equalsIgnoreCase("") 
						|| productDetails.getOtsProductDetailsPdf() == null || productDetails.getOtsProductDetailsPdf().equalsIgnoreCase("")
						|| productDetails.getProductStatus() == null || productDetails.getProductStatus().equalsIgnoreCase("")) {
					  return "Please Enter Required Inputs";
				}
				//Predefined ProductStatus values
				String[] isValidStatus = {"1", "active"};

			    // Validate input Flag
			    String productStatus = productDetails.getProductStatus();
			    boolean isValidProductStatus = Arrays.stream(isValidStatus).anyMatch(flag -> flag.equalsIgnoreCase(productStatus));

			    //If input status not matching predefined status
			    if (!isValidProductStatus) {
			        return "Invalid Product Status";
			    } 
				
				//Predefined LevelID values
				String[] isValidLevelId = {"3","4"};

			    // Validate input Flag
			    String levelId = productDetails.getProductLevelId();
			    boolean isValidProductLevelId = Arrays.stream(isValidLevelId).anyMatch(flag -> flag.equalsIgnoreCase(levelId));

			    //If input status not matching predefined status
			    if (!isValidProductLevelId) {
			        return "Invalid Input for ProductLevelId";
			    } 
				
				//Mandatory Request fields for Adding Product only
				if (productDetails.getProductId() == null || productDetails.getProductId().isEmpty()) {
					if (productDetails.getOtsProductCountry() == null || productDetails.getOtsProductCountry().equalsIgnoreCase("")
							|| productDetails.getOtsProductCountryCode() == null  || productDetails.getOtsProductCountryCode().equals("")
							|| productDetails.getOtsProductCurrencySymbol() == null || productDetails.getOtsProductCurrencySymbol().equalsIgnoreCase("")
							|| productDetails.getOtsProductCurrency() == null || productDetails.getOtsProductCurrency().equalsIgnoreCase("")
							|| productDetails.getCreatedUser() == null || productDetails.getCreatedUser().equalsIgnoreCase("")
							|| productDetails.getDistributorId() == null || productDetails.getDistributorId().equalsIgnoreCase("")
							|| productDetails.getProductSubCategoryId() == null || productDetails.getProductSubCategoryId().equalsIgnoreCase("")
							|| productDetails.getProductStockQuantity() == null || productDetails.getProductStockQuantity().equalsIgnoreCase("")) {
						 return "Please Enter Required Inputs";
					}
				}
				
				//To Add Or Update Product
				productId = productServiceDAO.addProductDetails(productDetails);
				if (productId == null) {
					return null;
				}			
				//To Map SubCategory & Add Stock For New Product
				if (productDetails.getProductId() == null || productDetails.getProductId().isEmpty()) {
					// To Map SubCategory for Product
					ProductCategoryMapping mapping = new ProductCategoryMapping();
					mapping.setOtsProductId(productId);
					mapping.setOtsProductCategoryId(productDetails.getProductSubCategoryId());
					mapping.setCreatedUser(productDetails.getCreatedUser());
					productCategoryMappingDAO.addProductCategoryMapping(mapping);
					
					// To Add Product Stock
					AddProductStock addProductStock = new AddProductStock();
					addProductStock.setProductId(productId);
					addProductStock.setProductStockQty(productDetails.getProductStockQuantity());
					addProductStock.setProductStockStatus("active");
					addProductStock.setUsersId(productDetails.getDistributorId());
					AddProductStockBORequest addProductStockBORequest = new AddProductStockBORequest();
					addProductStockBORequest.setRequestData(addProductStock);
					
					// Add Stock for Newly Added Product
					addOrUpdateProductStock(addProductStockBORequest);
				}
				return productId;
			}
	        
			//To Add or Update Pricing Details For Product
			if (addOrUpdateProductRequest.getRequest().getProductPricingDetails() != null) {

				ProductPricingDetails pricingDetails = addOrUpdateProductRequest.getRequest().getProductPricingDetails();
				if (pricingDetails.getProductId() == null || pricingDetails.getProductId().equalsIgnoreCase("")
					|| pricingDetails.getProductStatus() == null || pricingDetails.getProductStatus().equalsIgnoreCase("")
					|| pricingDetails.getProductBasePrice() == null || pricingDetails.getProductBasePrice().equalsIgnoreCase("")
					|| pricingDetails.getProductDiscountPercentage() == null || pricingDetails.getProductDiscountPercentage().equalsIgnoreCase("")
					|| pricingDetails.getProductDiscountPrice() == null || pricingDetails.getProductDiscountPrice().equalsIgnoreCase("")
					|| pricingDetails.getGst() == null || pricingDetails.getGst().equalsIgnoreCase("")
					|| pricingDetails.getGstPrice() == null || pricingDetails.getGstPrice().equalsIgnoreCase("")
					|| pricingDetails.getProductFinalPriceWithGst() == null || pricingDetails.getProductFinalPriceWithGst().equalsIgnoreCase("")
					|| pricingDetails.getProductPrice() == null || pricingDetails.getProductPrice().equalsIgnoreCase("")
					|| pricingDetails.getProductSellerPrice() == null || pricingDetails.getProductSellerPrice().equalsIgnoreCase("")
					|| pricingDetails.getProductReturnDeliveryCharge() == null || pricingDetails.getProductReturnDeliveryCharge().equalsIgnoreCase("")
					|| pricingDetails.getProductDeliveryCharge() == null || pricingDetails.getProductDeliveryCharge().equalsIgnoreCase("")) {
	       			
					return "Please Enter Required Inputs";
				}
				
				//Predefined ProductStatus values
				String[] isValidStatus = {"2", "active"};

			    // Validate input Flag
			    String productStatus = pricingDetails.getProductStatus();
			    boolean isValidProductStatus = Arrays.stream(isValidStatus).anyMatch(flag -> flag.equalsIgnoreCase(productStatus));

			    //If input status not matching predefined status
			    if (!isValidProductStatus) {
			        return "Invalid Product Status";
			    } 
			    
				//To Add or Update Pricing Details For Product
				productId = productServiceDAO.addProductPricingDetails(pricingDetails);
				
				return productId;	
			}

			//To Add or Update Policy For Product
			if (addOrUpdateProductRequest.getRequest().getProductPolicy() != null) {
				ProductPolicy  policyDetails = addOrUpdateProductRequest.getRequest().getProductPolicy();
				
				if (policyDetails.getProductId() == null ||policyDetails.getProductId().equalsIgnoreCase("") 
					|| policyDetails.getProductStatus() == null || policyDetails.getProductStatus().equalsIgnoreCase("") 
					|| policyDetails.getProductCancellationAvailability() == null || policyDetails.getProductCancellationAvailability().equalsIgnoreCase("")
					|| policyDetails.getProductCancellationPolicy() == null || policyDetails.getProductCancellationPolicy().equalsIgnoreCase("")
					|| policyDetails.getProductDeliveryPolicy() == null || policyDetails.getProductDeliveryPolicy().equalsIgnoreCase("")
					|| policyDetails.getProductReplacementAvailability() == null || policyDetails.getProductReplacementAvailability().equalsIgnoreCase("")
					|| policyDetails.getProductReplacementPolicy() == null || policyDetails.getProductReplacementPolicy().equalsIgnoreCase("")
					|| policyDetails.getProductReplacementDays() == null
					|| policyDetails.getProductReturnAvailability() == null || policyDetails.getProductReturnAvailability().equalsIgnoreCase("")
					|| policyDetails.getProductReturnDays()== null
					|| policyDetails.getProductReturnPolicy() == null || policyDetails.getProductReturnPolicy().equalsIgnoreCase("")
					|| policyDetails.getTimeToDeliver() == null || policyDetails.getTimeToDeliver().equalsIgnoreCase("")
					|| policyDetails.getTimeToShip() == null || policyDetails.getTimeToShip().equalsIgnoreCase("")
					|| policyDetails.getSellerPickupReturn() == null
					|| policyDetails.getCodAvailability() == null){
					
					return "Please Enter Required Inputs";
				}	
				
				//Predefined ProductStatus values
				String[] isValidStatus = {"3", "active"};

			    // Validate input Flag
			    String productStatus = policyDetails.getProductStatus();
			    boolean isValidProductStatus = Arrays.stream(isValidStatus).anyMatch(flag -> flag.equalsIgnoreCase(productStatus));

			    //If input status not matching predefined status
			    if (!isValidProductStatus) {
			        return "Invalid Product Status";
			    } 
			    
				//Validate When ProductReplacementAvailability is true or 1 need to provide replacement day mandatory
				if(policyDetails.getProductReplacementAvailability().equalsIgnoreCase("1") || policyDetails.getProductReplacementAvailability().equalsIgnoreCase("true")) {
					
					if(policyDetails.getProductReplacementDays() == null || policyDetails.getProductReplacementDays().trim().isEmpty()) {
						return "Please Enter ProductReplacementDays Field";
					}		
				}
				
				//Validate When ProductReturnAvailability is true or 1 need to provide return day mandatory
				if(policyDetails.getProductReturnAvailability().equalsIgnoreCase("1") || policyDetails.getProductReturnAvailability().equalsIgnoreCase("true")) {
					if(policyDetails.getProductReturnDays() == null || policyDetails.getProductReturnDays().trim().isEmpty()) {
						return "Please Enter ProductReturnDays Field";
					}
				}
				//To Add or Update Policy For Product
				productId = productServiceDAO.addProductPolicyDetails(policyDetails);
				
				executor.submit(() -> {
					// To Update Policy Details for Variants
				    if(policyDetails.getProductStatus().equalsIgnoreCase("active")) {
				    	//To get List of Variants for Parent Product
				    	List<ProductDetails> variants = productServiceDAO.getVariantsByProductId(policyDetails.getProductId());
					    // To Update Policy Details for Variants
					    if (variants.size() >0) {		    
						    //Update all the Variants
					        for (ProductDetails variant : variants) {
					        	//Setting Variant Product Id as request
					        	policyDetails.setProductId(variant.getProductId());
					            
					            productServiceDAO.addProductPolicyDetails(policyDetails);
					            
					            logger.info("Variant Updated Successfully ={}", variant.getProductId());
					        }
					    }
				    }
				});
				
				return productId;	
			}	
				
			//To Add Manufacturing Details for Product
			if (addOrUpdateProductRequest.getRequest().getProductManufactureDetails() != null) {
			    
				ProductManufactureDetails manufactureDetails = addOrUpdateProductRequest.getRequest().getProductManufactureDetails();   
			    if (manufactureDetails.getProductId() == null || manufactureDetails.getProductId().equalsIgnoreCase("")
			        || manufactureDetails.getProductStatus() == null || manufactureDetails.getProductStatus().equalsIgnoreCase("")
			        || manufactureDetails.getConsumerCareEmail() == null || manufactureDetails.getConsumerCareEmail().equalsIgnoreCase("")
			        || manufactureDetails.getConsumerCareName() == null || manufactureDetails.getConsumerCareName().equalsIgnoreCase("")
			        || manufactureDetails.getConsumerCarePhoneNumber() == null || manufactureDetails.getConsumerCarePhoneNumber().equalsIgnoreCase("")
			        || manufactureDetails.getManufacturerAddress() == null || manufactureDetails.getManufacturerAddress().equalsIgnoreCase("")
			        || manufactureDetails.getManufacturerGenericName() == null || manufactureDetails.getManufacturerGenericName().equalsIgnoreCase("") 
			    	|| manufactureDetails.getManufacturerName() == null || manufactureDetails.getManufacturerName().equalsIgnoreCase("")
			    	|| manufactureDetails.getManufacturerPackingImport() == null || manufactureDetails.getManufacturerPackingImport().equalsIgnoreCase("")
			    	|| manufactureDetails.getOriginCountry() == null || manufactureDetails.getOriginCountry().equalsIgnoreCase("")
			    	|| manufactureDetails.getOtsOemModelNumber() == null || manufactureDetails.getOtsOemModelNumber().equalsIgnoreCase("")
				    || manufactureDetails.getOtsOemPartNumber() == null || manufactureDetails.getOtsOemPartNumber().equalsIgnoreCase("")
				    || manufactureDetails.getOtsOemUom() == null || manufactureDetails.getOtsOemUom().equalsIgnoreCase("")
				    || manufactureDetails.getOtsVendorItemCode() == null || manufactureDetails.getOtsVendorItemCode().equalsIgnoreCase("")) {
			    					 
			        return "Please Enter Required Inputs";
			    }

			    //Predefined ProductStatus values
				String[] isValidStatus = {"4", "active"};

			    // Validate input Flag
			    String productStatus = manufactureDetails.getProductStatus();
			    boolean isValidProductStatus = Arrays.stream(isValidStatus).anyMatch(flag -> flag.equalsIgnoreCase(productStatus));

			    //If input status not matching predefined status
			    if (!isValidProductStatus) {
			        return "Invalid Product Status";
			    } 
			    
			    //To Add or Update Manufacturing Details for Product
			    productId = productServiceDAO.addProductManufactureDetails(manufactureDetails);
			    
			    executor.submit(() -> {
				    // To Update Manufacturing Details for Variants
				    if(manufactureDetails.getProductStatus().equalsIgnoreCase("active")) {
				    	//To get List of Variants for Parent Product
				    	List<ProductDetails> variants = productServiceDAO.getVariantsByProductId(manufactureDetails.getProductId());
					    // To Update Manufacturing Details for Variants
					    if (variants.size() >0) {		    
						    //Update all the Variants
					        for (ProductDetails variant : variants) {
					        	
					        	//Setting Variant Product Id as request
					        	manufactureDetails.setProductId(variant.getProductId());
					            
					            productServiceDAO.addProductManufactureDetails(manufactureDetails);
					            
					            logger.info("Variant Updated Successfully ={}", variant.getProductId());
					        }
					    }
				    }
			    });
			    
			    return productId;	
			}
			
			//To Add Attributes for Product
			if (addOrUpdateProductRequest.getRequest().getAttributeMapping() != null) {
				List<ProductAttributesMapping> attributeDetails = addOrUpdateProductRequest.getRequest().getAttributeMapping();
			     if (attributeDetails.get(0).getProductId() == null || attributeDetails.get(0).getProductId().equalsIgnoreCase("")
						|| attributeDetails.get(0).getAttributeKeyId() == null || attributeDetails.get(0).getAttributeKeyId().equalsIgnoreCase("")
						|| attributeDetails.get(0).getAttributeValueId() == null || attributeDetails.get(0).getAttributeValueId().equalsIgnoreCase("")) {
					
			    	 return "Please Enter Required Inputs";
				}
			
			    //To Add Attributes for Product
				productAttributesMappingDAO.addProductAttributesMapping(attributeDetails);
				
				//To Update Product Status to Active
				ProductDetails updateProductStatus = productServiceDAO.updateProductStatus(attributeDetails.get(0).getProductId(), "active");

				return updateProductStatus.getProductId();
			}
			executor.shutdown();
			
			return productId;	
		} catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public GetPageLoaderResponse getPageLoaderDetails(String countryCode){
		GetPageLoaderResponse getPageLoaderResponse = new GetPageLoaderResponse();
		try {
			//Setting Request to get All Category List 
			GetCatgeorySubcategoryModel getCatgeorySubcategoryModel = new GetCatgeorySubcategoryModel();
			getCatgeorySubcategoryModel.setSearchKey("category");
			getCatgeorySubcategoryModel.setSearchValue("1");
			getCatgeorySubcategoryModel.setCountryCode(countryCode);
			
			GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest = new GetCatgeorySubcategoryRequest();
			getCatgeorySubcategoryRequest.setRequest(getCatgeorySubcategoryModel);
			
			//To get Category based on Product Country Serviceability
			List<ProductDetails> categoryDetails = productServiceDAO.getCategoryAndSubCategory(getCatgeorySubcategoryRequest);
			getPageLoaderResponse.setCategoryList(categoryDetails);
			
			//To get Recently Added Product
			List<ProductDetails> getRecentlyAddedProduct = productServiceDAO.getRecentlyAddedProductList("3",countryCode);
			getPageLoaderResponse.setRecentlyAddedProductsList(getRecentlyAddedProduct);
			
			//To get Active Distributor List
			List<UserDetails> getDistributorList = userServiceDAO.getDistributorsWithActiveProductsByCountry(countryCode);
			getPageLoaderResponse.setDistributorsList(getDistributorList);
			
			//To get All Banner info
			List<Map<String, Object>> getBanner = otsUserService.getAllBannerinfo();
			getPageLoaderResponse.setBannerDetail(getBanner);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return getPageLoaderResponse;
	}
	
	@Override
	public String addOrUpdateCategoryAndSubcategory(AddOrUpdateCategoryRequest addOrUpdateCategoryRequest) {
		try {
			String addOrUpdateCategoryAndSubcategory = productServiceDAO.addOrUpdateCategoryAndSubcategory(addOrUpdateCategoryRequest);
			
			return addOrUpdateCategoryAndSubcategory;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public ProductSearchResponse searchProductByNamePagination(SearchProductByNamePaginationRequest searchProductRequest) {
		try {
			ProductSearchResponse productDetails = productServiceDAO.searchProductByNamePagination(searchProductRequest);
			if(productDetails.getSearchedProduct().isEmpty() && productDetails.getSimilarProduct().isEmpty()) {
				return null;
			}
		
			return productDetails;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public ProductDetailsBOResponse getProductsBySubCategoryWithIntermediateStatus(String subcategoryId) {
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			List<ProductDetails> productDetails = productServiceDAO.getProductsBySubCategoryWithIntermediateStatus(subcategoryId);
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}
	
	@Override
	public ProductDetailsBOResponse getCategoryAndSubCategoryWithAttribute(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest){
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			List<ProductDetails> productDetails = productServiceDAO.getCategoryAndSubCategoryWithAttribute(getCatgeorySubcategoryRequest);
			productDetailsBOResponse.setProductDetails(productDetails);
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productDetailsBOResponse;
	}
	
	@Override
	public ProductDetailsBOResponse getSimilarProducts(GetSimilarProductRequest getSimilarProductRequest) {
		ProductDetailsBOResponse productDetailsBOResponse = new ProductDetailsBOResponse();
		try {
			List<ProductDetails> productDetails = productServiceDAO.getSimilarProducts(getSimilarProductRequest);
			productDetailsBOResponse.setProductDetails(productDetails);
			return productDetailsBOResponse;
		}catch(Exception e){
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String addOrUpdateProductManufacturerDetails(AddProductManufacturerRequest addProductManufacturerRequest) {
		try {
			String result = productManufacturerDAO.addOrUpdateProductManufacturerDetails(addProductManufacturerRequest);
			return result;
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public String deleteManufacturerDetails(String productManufacturerId) {
		try {
			String results = productManufacturerDAO.deleteManufacturerDetails(productManufacturerId);
			
			return results;
		} catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	@Override
	public ProductManufacturersResponse getAllManufacturerDetails() {
		ProductManufacturersResponse productManufacturerResponse = new ProductManufacturersResponse();
		try {
			List<ProductManufacturerDetails> productManufacturer = productManufacturerDAO.getAllManufacturerDetails();
			productManufacturerResponse.setProductManufacturerDetails(productManufacturer);
			
		}catch (BusinessException e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return productManufacturerResponse;
	}
	
	@Override
	public String addVarientProduct(AddVariantProductRequest addVariantProductRequest) {
		ProductDetails productDetails = new ProductDetails();
		try {
			//To Add Variant Product
			productDetails = productServiceDAO.addVarientProduct(addVariantProductRequest);
			if (productDetails == null) {
				return null;
			}
			
			//To Map Variant Product to Parent Product
			ProductCategoryMapping mapping = new ProductCategoryMapping();
			mapping.setOtsProductCategoryId(addVariantProductRequest.getRequest().getProductId());
			mapping.setOtsProductId(productDetails.getProductId());
			mapping.setCreatedUser(productDetails.getCreatedUser().toString());
			productCategoryMappingDAO.addProductCategoryMapping(mapping);

			// To Add Product Stock
			AddProductStock addProductStock = new AddProductStock();
			addProductStock.setProductId(productDetails.getProductId());
			addProductStock.setProductStockQty(addVariantProductRequest.getRequest().getProductStockQuantity());
			addProductStock.setProductStockStatus("active");
			addProductStock.setUsersId(productDetails.getDistributorId().toString());
			AddProductStockBORequest addProductStockBORequest = new AddProductStockBORequest();
			addProductStockBORequest.setRequestData(addProductStock);

			// Add Stock for Newly Added Product
			addOrUpdateProductStock(addProductStockBORequest);

			return productDetails.getProductId();
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB  :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}

}