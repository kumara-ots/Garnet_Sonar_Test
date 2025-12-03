package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;
import java.util.Map;

import com.fuso.enterprise.ots.srv.api.model.domain.AddProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.AttributeDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.CategoryDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductManufactureDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductPolicy;
import com.fuso.enterprise.ots.srv.api.model.domain.ProductPricingDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.SubCategoryDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateCategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductByCountryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddVariantProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.FilterProductsByGeneralPropertiesRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCategorySubCategoryByDistributorRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCatgeorySubcategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductsByDistributorPaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductsBySubCategoryAndDistributorRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetSiblingVariantProductsByAttributeRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetSimilarProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ProductDetailsBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.SearchProductByNamePaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.response.ProductDetailsBOResponse;
import com.fuso.enterprise.ots.srv.api.service.response.ProductSearchResponse;

public interface ProductServiceDAO {

	ProductDetailsBOResponse getProductList(ProductDetailsBORequest productDetailsBORequest);

	ProductDetails getProductDetails(String productId);

	List<ProductDetails> getProductDetailswithStock(String ditributorId);

	ProductDetails updateProductStatus(String productId, String productStatus);
	
	ProductDetailsBOResponse getProductByLevelId(String levelId);
	
	List<ProductDetails> getProductDetailsByName(String productName);
	
	ProductDetailsBOResponse getProductPagination(ProductDetailsBORequest productDetailsBORequest);

	/*********shreekant****/
	List<ProductDetails>  getAllProductDetails();
	/*******************/
	
	String getDeliveryChargeForProduct(String productId);
	
	ProductDetailsBOResponse getProductListByDistributor(String distributorId);
	
	List<ProductDetails> getActiveProductListByDistributor(String distributorId);
	
	CategoryDetails getCategoryForProductId(String productId);
    
    List<ProductDetails> getAllProductsWithDiscount();
    
	List<ProductDetails> getProductsByDistributorWithReviewAndRating(String distributorId);
	
	ProductDetailsBOResponse getProductsForSubCategory(String subCategoryId);
	
	ProductDetailsBOResponse getProductsByDistributorPagination(GetProductsByDistributorPaginationRequest getProductsByDistributorPagination);
	
	SubCategoryDetails getSubCategoryForProductId(String productId);
	
	List<ProductDetails> getProductBySubCategory(String subCategoryId);

	List<ProductDetails> getSubCategoryByCategory(String categoryId);

	List<ProductDetails> getCategoryAndSubCategoryByDistributor(GetCategorySubCategoryByDistributorRequest getCategorySubCategoryByDistributorRequest);

	List<ProductDetails> getCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest);

	List<AttributeDetails> getProductAttribute(String productId);

	List<ProductDetails> getParentAndVariantSiblingProducts(String variantProductId);

	List<ProductDetails> getParentAndChildProductsByChildID(String productId);

	ProductDetailsBOResponse getParentProductforDistributor(String distributerId);

	List<ProductDetails> getRecentlyAddedProductList(String levelid, String productCountryCode);

	List<ProductDetails> getProductAndVarientsByStatus(String status);
	
	List<ProductDetails> getSiblingVariantProductsByPrimaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest);

	List<ProductDetails> getSiblingVariantProductsBySecondaryKey(GetSiblingVariantProductsByAttributeRequest getSiblingVariantProductsByAttributeRequest);

	List<ProductDetails> filterProductsByGeneralProperties(FilterProductsByGeneralPropertiesRequest filterProductsByGeneralPropertiesRequest);

	List<ProductDetails> getProductsBySubCategoryAndDistributor(GetProductsBySubCategoryAndDistributorRequest getProductsBySubCategoryAndDistributorRequest);

	List<ProductDetails> getVariantsByProductId(String productId);

	List<ProductDetails> getVariantsProductByDistributor(String distributorId);

	Map<String, List<ProductDetails>> getPageloaderRecentlyAddedProductList(String levelid);

	Map<String, List<ProductDetails>> getPageloaderCategoryAndSubCategory(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest);

	ProductDetails addDuplicateProductByCountry(AddProductByCountryRequest addProductByCountryRequest);

	String addProductDetails(AddProductDetails addProductDetails);

	String addProductPricingDetails(ProductPricingDetails productPricingDetails);

	String addProductPolicyDetails(ProductPolicy productPolicy);

	String addProductManufactureDetails(ProductManufactureDetails productManufactureDetails);

	String addOrUpdateCategoryAndSubcategory(AddOrUpdateCategoryRequest addOrUpdateCategoryRequest);

	ProductSearchResponse searchProductByNamePagination(SearchProductByNamePaginationRequest searchProductRequest);

	List<ProductDetails> getProductsBySubCategoryWithIntermediateStatus(String subcategoryId);

	List<ProductDetails> getCategoryAndSubCategoryWithAttribute(GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest);

	List<ProductDetails> getSimilarProducts(GetSimilarProductRequest getSimilarProductRequest);

	ProductDetails addVarientProduct(AddVariantProductRequest addVariantProductRequest);

}
