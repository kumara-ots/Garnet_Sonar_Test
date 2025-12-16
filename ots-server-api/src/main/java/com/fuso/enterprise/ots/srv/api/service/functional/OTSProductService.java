package com.fuso.enterprise.ots.srv.api.service.functional;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.AttributeKey;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValue;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeValueName;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductAttributesMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.SubCategoryAttributeMapping;
import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
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

public interface OTSProductService {

	ProductDetailsBOResponse getProductList(ProductDetailsBORequest productDetailsBORequest);

	String addOrUpdateProductStock(AddProductStockBORequest addStockProductBORequest);
	
	GetProductStockListBOResponse getProductStockList(GetProductStockListRequest getProductStockRequest);
	
	GetProductBOStockResponse getProductStockByUidAndPid(GetProductStockRequest getProductStockRequest );

	String updateProductStatus(UpdateProductStatusRequest updateProductStatusRequest);

	/*shreekant rathod*/
	ProductDetailsBOResponse getAllProductDetails();
	/*************/
	
	ProductDetails getProductDetails(String productId);
	/*************/

	List<UserDetails> getSellerForProduct(GetSellerForProductRequest getSellerForProductRequest);
	
	ProductDetailsBOResponse getRecentlyAddedProductList(String levelId, String productCountryCode);
	
	ProductDetailsBOResponse getProductListByDistributor(String distributorId);

	String getDeliveryChargeForProduct(String productId);

	ProductDetailsBOResponse getProductsByDistributorPagination(GetProductsByDistributorPaginationRequest getProductsByDistributorPagination);

	ProductDetailsBOResponse getAllProductsWithDiscount();

	String addStockForMultipleProductByDistributor(AddProductStockBORequest addProductBORequest);

	ProductDetailsBOResponse getCategoryAndSubCategoryByDistributor(GetCategorySubCategoryByDistributorRequest getCategorySubCategoryByDistributorRequest);

	ProductDetailsBOResponse getCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest);

	List<ProductAttributesMapping> addProductAttributesMapping(AddProductAttributeMappingRequest addProductAttributeMappingRequest);

	ProductDetailsBOResponse getParentAndVariantSiblingProducts(String variantProductId);

	SubCategoryAttributeResponse getAttributeKeyBySubcategory(String subcategoryId);

	AttributeValueResponse getAttributeValueForAttributeKeyId(String attributeKeyId);

	String updateAttributeValue(UpadteAttributeValueRequest upadteAttributeValueRequest);

	String updateAttributeKey(UpdateAttributeKeyRequest updateAttributeKeyRequest);

	String deleteAttributeKey(String attributeKeyId);

	List<AttributeValue> addAttributeValue(AddAttributeValueRequest addAttributeValueRequest);

	List<AttributeKey> addAttributeKey(AddAttributeKeyRequest addAttributeKeyRequest);

	AttributeKeyResponse getAllAttributeKey();

	ProductDetailsBOResponse getParentAndChildProductsByChildID(String variantProductId);

	List<AttributeValueName> checkAttributeValue(AddAttributeValueRequest addAttributeValueRequest);

	GetAttributeKeysAndAttributeValuesResponse getAllAttributeKeysAndValues();

	String deleteAttributeValue(String attributeValueId);

	ProductDetailsBOResponse getParentProductforDistributor(String distributerId);

	AttributeKeyResponse getUnMappedAttributeKeyandValuesForSubcategory(String subcategoryId);

	String deleteAttributeKeyMappedToSubcategory(String subcategoryAttributeMappingId);

	List<SubCategoryAttributeMapping> addAttributeKeyForSubcategory(AddAttributeKeyForSubCategoryRequest addAttributeKeyForSubCategoryRequest);

	SubCategoryAttributeResponse getAttributeKeyValueBySubcategory(String subcategoryId);

	List<ProductDetails> getProductAndVarientsByStatus(String status);

	List<ProductDetails> getSiblingVariantProductsByPrimaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest);

	List<ProductDetails> getSiblingVariantProductsBySecondaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest);

	List<ProductDetails> filterProductsByGeneralProperties(FilterProductsByGeneralPropertiesRequest filterProductsByGeneralPropertiesRequest);

	ProductDetailsBOResponse getProductsBySubCategoryAndDistributor(GetProductsBySubCategoryAndDistributorRequest getProductsBySubCategoryAndDistributorRequest);

	List<ProductDetails> getVariantsByProductId(String productID);

	List<ProductDetails> getVariantsProductByDistributor(String distributorId);

	ProductDetailsPageloaderResponse getPageloaderRecentlyAddedProductList(String levelid);

	ProductDetailsPageloaderResponse getPageloaderCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest);

	String addDuplicateProductByCountry(AddProductByCountryRequest addProductByCountryRequest);

	String addOrUpdateProduct(AddOrUpdateProductRequest addOrUpdateProductRequest);

	GetPageLoaderResponse getPageLoaderDetails(String countryCode);

	String addOrUpdateCategoryAndSubcategory(AddOrUpdateCategoryRequest addOrUpdateCategoryRequest);

	ProductSearchResponse searchProductByNamePagination(SearchProductByNamePaginationRequest searchProductRequest);

	ProductDetailsBOResponse getProductsBySubCategoryWithIntermediateStatus(String subcategoryId);

	ProductDetailsBOResponse getCategoryAndSubCategoryWithAttribute(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest);

	ProductDetailsBOResponse getSimilarProducts(GetSimilarProductRequest getSimilarProductRequest);

	String addOrUpdateProductManufacturerDetails(AddProductManufacturerRequest addProductManufacturerRequest);

	String deleteManufacturerDetails(String productManufacturerId);

	ProductManufacturersResponse getAllManufacturerDetails();

	String addVarientProduct(AddVariantProductRequest addVariantProductRequest);
	
}
