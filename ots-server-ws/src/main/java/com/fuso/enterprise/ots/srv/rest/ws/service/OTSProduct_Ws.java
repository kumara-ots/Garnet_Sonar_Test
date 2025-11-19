 package com.fuso.enterprise.ots.srv.rest.ws.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeKeyForSubCategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeKeyRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddAttributeValueRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateCategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductAttributeMappingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductByCountryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductManufacturerRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductStockBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.FilterProductsByGeneralPropertiesRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCategorySubCategoryByDistributorRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetCatgeorySubcategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductStockListRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductStockRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductsByDistributerPaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetProductsBySubCategoryAndDistributorRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetSiblingVariantProductsByAttributeRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetSimilarProductRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ProductDetailsBORequest;
import com.fuso.enterprise.ots.srv.api.service.request.SearchProductByNamePaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpadteAttributeValueRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateAttributeKeyRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateProductStatusRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

// Product related API's
@Validated
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "OTSProduct_Ws", description = "This service provides the operations for OTS Product")
@Path("product")
@CrossOrigin
public interface OTSProduct_Ws {
	
	
	/************shreekant*********/
	@POST
    @Path("/getAllProductDetails")
	@ApiOperation(value = "getAllProductDetails", notes = "Getting getAllProductDetails ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllProductDetails();
	
	/*******************************/
	/************shreekant*********/
	@POST
    @Path("/getProductList")
	@ApiOperation(value = "getProductList", notes = "Getting getProductList Request Based On multiple key and values", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductList(@ApiParam(value = "request") ProductDetailsBORequest  productDetailsBORequest);
	
	@POST
    @Path("/getProductDetails")
	@ApiOperation(value = "getProductDetails", notes = "Getting getProductDetailsbyProductId ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductDetails(@ApiParam(value = "productId", required = true) @NotNull @Valid @QueryParam("productId")String productId);

	@POST
	@Path("/addProductStock")
	@ApiOperation(value = "addProductStock", notes = "This operation will add New product stock", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addProductStock(@ApiParam(value = "request", required = true) @NotNull @Valid AddProductStockBORequest addStockProductBORequest);
	
	@POST
	@Path("/getProductStockList")
	@ApiOperation(value = "getProductStockList", notes = "This operation will fetch details about product stock", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductStockList(@ApiParam(value = "request", required = true) @NotNull @Valid GetProductStockListRequest getProductStockListRequest);

	@POST
	@Path("/getProductStock")
	@ApiOperation(value = "getProductStock", notes = "This operation will fetch details about productstock", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductStock(@ApiParam(value = "request", required = true) @NotNull @Valid GetProductStockRequest getProductStockRequest);

	@POST
	@Path("/updateProductStatus")
	@ApiOperation(value = "updateProductStatus", notes = "To update Product Status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateProductStatus(@ApiParam(value = "request", required = true) @NotNull @Valid UpdateProductStatusRequest updateProductStatusRequestModel);

	@POST
	@Path("/getProductListByDistributor")
	@ApiOperation(value = "getProductListByDistributor", notes = "To get List of Products by Distributer Id", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductListByDistributor(@ApiParam(value = "distributorId", required = true) @NotNull @Valid @QueryParam("distributorId") String distributorId);
	
	@GET
    @Path("/getAllProductsWithDiscount")
	@ApiOperation(value = "getAllProductsWithDiscount", notes = "To get all the Product Details that has Discount", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllProductsWithDiscount();

	@POST
	@Path("/getProductsByDistributerPagination")
	@ApiOperation(value = "getProductsByDistributerPagination", notes = "To get all the Products under Distributor by Pagination", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductsByDistributerPagination(@ApiParam(value = "request", required = true) @NotNull @Valid GetProductsByDistributerPaginationRequest getProductsByDistributerPagination);
	
	@POST
	@Path("/addStockForMultipleProductByDistributor")
	@ApiOperation(value = "addStockForMultipleProductByDistributor", notes = "To add Stock for All the Products of Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addStockForMultipleProductByDistributor(@ApiParam(value = "request", required = true) @NotNull @Valid AddProductStockBORequest addStockProductBORequest);
	
	@POST
	@Path("/getCategoryAndSubCategoryByDistributor")
	@ApiOperation(value = "getCategoryAndSubCategoryByDistributorRequest", notes = "To get only Category & Subcategory having products in it under Distributor", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCategoryAndSubCategoryByDistributor(@ApiParam(value = "request", required = true) @NotNull @Valid GetCategorySubCategoryByDistributorRequest getCategorySubCategoryByDistributorRequest);
	
	@POST
	@Path("/getCategoryAndSubCategory")
	@ApiOperation(value = "getCategoryAndSubCategory", notes = "To get only Category & Subcategory having products in it", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCategoryAndSubCategory(@ApiParam(value = "request", required = true) @NotNull @Valid GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest);
	
	@POST
	@Path("/getParentAndVariantSiblingProducts")
	@ApiOperation(value = "getParentAndVariantSiblingProducts", notes = "To get Sibling Variant Products for Variant", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getParentAndVariantSiblingProducts(@ApiParam(value = "variantProductId", required = true) @NotNull @Valid @QueryParam("variantProductId") String variantProductId);
	
	@POST
	@Path("/getAttributeKeyBySubcategory")
	@ApiOperation(value = "getAttributeKeyBySubcategory", notes = "To get Attribute Key Mapped to Subcategory", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAttributeKeyBySubcategory(@ApiParam(value = "subcategoryId", required = true) @NotNull @Valid @QueryParam("subcategoryId") String subcategoryId);
	
	@POST
	@Path("/getAttributeValueForAttributeKeyId")
	@ApiOperation(value = "getAttributeValueForAttributeKeyId", notes = "To get Attribute Value Mapped to AttributeKeyId", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAttributeValueForAttributeKeyId(@ApiParam(value = "attributeKeyId", required = true) @NotNull @Valid @QueryParam("attributeKeyId") String attributeKeyId);
	
	@GET
	@Path("/getAllAttributeKey")
	@ApiOperation(value = "getAllAttributeKey", notes = "To get all Attribute Key Deatils", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllAttributeKey();

	@POST
	@Path("/addAttributeKey")
	@ApiOperation(value = "addAttributeKey", notes = "To add Attribute Keys", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addAttributeKey(@ApiParam(value = "request", required = true) @NotNull @Valid AddAttributeKeyRequest addAttributeKeyRequest);

	@POST
	@Path("/addAttributeValue")
	@ApiOperation(value = "addAttributeValue", notes = "To add Attribute Values for Attribute Key", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addAttributeValue(@ApiParam(value = "request", required = true) @NotNull @Valid AddAttributeValueRequest addAttributeValueRequest);
	
	@POST
    @Path("/updateAttributeKey")
	@ApiOperation(value = "updateAttributeKey", notes = "To Update Attribute Key", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateAttributeKey(@ApiParam(value = "request", required = true) @NotNull @Valid UpdateAttributeKeyRequest updateAttributeKeyRequest);
	
	@POST
    @Path("/deleteAttributeKey")
	@ApiOperation(value = "deleteAttributeKey", notes = "To Delete Attribute Key", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response deleteAttributeKey(@ApiParam(value = "attributeKeyId", required = true) @NotNull @Valid @QueryParam("attributeKeyId") String attributeKeyId);

	@POST
    @Path("/updateAttributeValue")
	@ApiOperation(value = "updateAttributeValue", notes = "To Update Attribute Value", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateAttributeValue(@ApiParam(value = "request", required = true) @NotNull @Valid UpadteAttributeValueRequest upadteAttributeValueRequest);

	@POST
	@Path("/addProductAttributesMapping")
	@ApiOperation(value = "addProductAttributesMapping", notes = "To Add Attributes for Prodduct", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addProductAttributesMapping(@ApiParam(value = "request", required = true) @NotNull @Valid AddProductAttributeMappingRequest addProductAttributeMappingRequest);
	
	@POST
	@Path("/getParentAndChildProductsByChildID")
	@ApiOperation(value = "getParentAndChildProductsByChildID", notes = "To get Sibling Variant Products for Variant without Parent Product", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getParentAndChildProductsByChildID(@ApiParam(value = "variantProductId", required = true) @NotNull @Valid @QueryParam("variantProductId") String variantProductId);

	@GET
	@Path("/getAllAttributeKeysAndValues")
	@ApiOperation(value = "getAllAttributeKeysAndValues", notes = "To get All the Attribute Key and its Attribute Values list", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllAttributeKeysAndValues();
	
	@POST
    @Path("/deleteAttributeValue")
	@ApiOperation(value = "deleteAttributeKey", notes = "To Delete Attribute Values", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response deleteAttributeValue(@ApiParam(value = "attributeValueId", required = true) @NotNull @Valid @QueryParam("attributeValueId") String attributeValueId);
	
	@POST
	@Path("/getParentProductforDistributor")
	@ApiOperation(value = "getParentProductforDistributor", notes = "To get List of Parent Products by Distributer Id", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getParentProductforDistributor(@ApiParam(value = "distributerId", required = true) @NotNull @Valid @QueryParam("distributerId") String distributerId);

	@POST
    @Path("/getUnMappedAttributeKeyandValuesForSubcategory")
    @ApiOperation(value = "getUnMappedAttributeKeyandValuesForSubcategory", notes = "To get UnMapped Attribute Keys And Attribute Values By Subcategory ", response = Response.class)
    @ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
    Response getUnMappedAttributeKeyandValuesForSubcategory(@ApiParam(value = "subcategoryId", required = true) @NotNull @Valid @QueryParam("subcategoryId")String subcategoryId);
	
	@POST
	@Path("/addAttributeKeyForSubcategory")
	@ApiOperation(value = "addAttributeKeyForSubcategory", notes = "To Add Attributes Keys for SubCategory", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addAttributeKeyForSubcategory(@ApiParam(value = "request", required = true) @NotNull @Valid AddAttributeKeyForSubCategoryRequest addAttributeKeyForSubCategoryRequest);
	
	@POST
    @Path("/deleteAttributeKeyMappedToSubcategory")
	@ApiOperation(value = "deleteAttributeKeyMappedToSubcategory", notes = "To Delete Attribute Keys Mapped to Subcategory", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response deleteAttributeKeyMappedToSubcategory(@ApiParam(value = "subcategoryAttributeMappingId", required = true) @NotNull @Valid @QueryParam("subcategoryAttributeMappingId") String subcategoryAttributeMappingId);
	
	@POST
	@Path("/getAttributeKeyValueBySubcategory")
	@ApiOperation(value = "getAttributeKeyValueBySubcategory", notes = "To get Attribute Key And Values Mapped to Subcategory", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAttributeKeyValueBySubcategory(@ApiParam(value = "subcategoryId", required = true) @NotNull @Valid @QueryParam("subcategoryId") String subcategoryId);
	
	@POST
	@Path("/getProductAndVarientsByStatus")
	@ApiOperation(value = "getProductAndVarientsByStatus", notes = "To get Products and Varients By Status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductAndVarientsByStatus(@ApiParam(value = "status", required = true) @NotNull @Valid @QueryParam("status") String status);

	@POST
	@Path("/getSiblingVariantProductsByPrimaryKey")
	@ApiOperation(value = "getSiblingVariantProductsByPrimaryKey", notes = "To get Sibling Variant Products Based on Primary Attribute Key Name", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getSiblingVariantProductsByPrimaryKey(@ApiParam(value = "request", required = true) @NotNull @Valid GetSiblingVariantProductsByAttributeRequest GetSiblingVariantProductsByAttributeRequest);
	
	@POST
	@Path("/getSiblingVariantProductsBySecondaryKey")
	@ApiOperation(value = "getSiblingVariantProductsBySecondaryKey", notes = "To get Sibling Variant Products Based on Primary & Secondary Attribute Key Name", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getSiblingVariantProductsBySecondaryKey(@ApiParam(value = "request", required = true) @NotNull @Valid GetSiblingVariantProductsByAttributeRequest GetSiblingVariantProductsByAttributeRequest);

	@POST
	@Path("/filterProductsByGeneralProperties")
	@ApiOperation(value = "filterProductsByGeneralProperties", notes = "To get Product Deatils By Based on Filters  ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response filterProductsByGeneralProperties(@ApiParam(value = "request", required = true) @NotNull @Valid FilterProductsByGeneralPropertiesRequest filterProductsByGeneralPropertiesRequest);

	@POST
	@Path("/getProductsBySubCategoryAndDistributor")
	@ApiOperation(value = "getProductsBySubCategoryAndDistributor", notes = "To get Product Deatils By Based on Subcategory And DistributorID  ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductsBySubCategoryAndDistributor(@ApiParam(value = "request", required = true) @NotNull @Valid GetProductsBySubCategoryAndDistributorRequest getProductsBySubCategoryAndDistributorRequest);

	@POST
	@Path("/getVariantsByProductId")
	@ApiOperation(value = "getVarientProductsByProduct", notes = "To get Varients Products By products ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getVariantsByProductId(@ApiParam(value = "status", required = true) @NotNull @Valid @QueryParam("productId") String productId);
	
	@POST
	@Path("/getVariantsProductByDistributor")
	@ApiOperation(value = "getVariantsProductByDistributor", notes = "To get products are mapped varients products Based on DistributorID ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getVariantsProductByDistributor(@ApiParam(value = "status", required = true) @NotNull @Valid @QueryParam("distributorId")String distributorId);

	@POST
	@Path("/getPageloaderRecentlyAddedProductList")
	@ApiOperation(value = "getPageloaderRecentlyAddedProductList", notes = "To get List of Recently added products grouped by country code", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getPageloaderRecentlyAddedProductList(@ApiParam(value = "request", required = true) @NotNull @Valid @QueryParam("levelId") String levelid);
	
	@POST
	@Path("/getPageloaderCategoryAndSubCategory")
	@ApiOperation(value = "getPageloaderCategoryAndSubCategory", notes = "To get only Category & Subcategory having products in it & grouped by country code", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getPageloaderCategoryAndSubCategory(@ApiParam(value = "request", required = true) @NotNull @Valid GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest);
	
	@POST
	@Path("/addDuplicateProductByCountry")
	@ApiOperation(value = "addProductByCountry", notes = "To Add Duplicate Product same as Parent Product & Adding Pricing & Policy based on Country", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addDuplicateProductByCountry(@ApiParam(value = "request", required = true) @NotNull @Valid AddProductByCountryRequest addProductByCountryRequest);
	
	@POST
	@Path("/addOrUpdateProduct")
	@ApiOperation(value = "addOrUpdateProduct", notes = "To Add New Product Or Update Existing Product Used for Wizard", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addOrUpdateProduct(@ApiParam(value = "request", required = true) @NotNull  @Valid AddOrUpdateProductRequest addOrUpdateProductRequest);
	
	@POST
	@Path("/getPageLoaderDetails")
	@ApiOperation(value = "getPageLoaderDetails", notes = "To get Page Loader Details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getPageLoaderDetails(@ApiParam(value = "request", required = true) @NotNull @Valid @QueryParam("countryCode") String countryCode);

	@POST
	@Path("/addOrUpdateCategoryAndSubcategory")
	@ApiOperation(value = "addOrUpdateCategoryAndSubcategory", notes = "This Api is to Add or Update category and Subcategory Details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addOrUpdateCategoryAndSubcategory(@ApiParam(value = "request", required = true) @NotNull  @Valid AddOrUpdateCategoryRequest addOrUpdateCategoryRequest);
	
	@POST
	@Path("/searchProductByNamePagination")
	@ApiOperation(value = "searchProductByNamePagination", notes = "To Search Products by Name with Pagination", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response searchProductByNamePagination(@ApiParam(value = "request", required = true) @NotNull @Valid SearchProductByNamePaginationRequest searchProductRequest);
	
	@POST
	@Path("/getProductsBySubCategoryWithIntermediateStatus")
	@ApiOperation(value = "getProductsBySubCategoryWithIntermediateStatus", notes = "To get Products By Subcategory with Intermediate Status (1,2,3,4)", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getProductsBySubCategoryWithIntermediateStatus(@ApiParam(value = "subcategoryId", required = true) @NotNull @Valid @QueryParam("subcategoryId") String subcategoryId);
	
	@POST
	@Path("/getCategoryAndSubCategoryWithAttribute")
	@ApiOperation(value = "getCategoryAndSubCategoryWithAttribute", notes = "To get only Category & Subcategory having Attributes in it", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getCategoryAndSubCategoryWithAttribute(@ApiParam(value = "request", required = true) @NotNull @Valid GetCatgeorySubcategoryRequest getCatgeorySubcategoryRequest);

	@POST
	@Path("/getSimilarProducts")
	@ApiOperation(value = "getSimilarProducts", notes = "To get similar products based on multiple request", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getSimilarProducts(@ApiParam(value = "request", required = true) @NotNull @Valid GetSimilarProductRequest getSimilarProductRequest);
	
	@POST
	@Path("/addOrUpdateProductManufacturerDetails")
	@ApiOperation(value = "addOrUpdateProductManufacturerDetails", notes = "To Add Product Manufacturers Or Update Existing Product Manufacturers Used for Wizard", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addOrUpdateProductManufacturerDetails(@ApiParam(value = "request", required = true) @NotNull  @Valid AddProductManufacturerRequest addProductManufacturerRequest);

	@POST
	@Path("/deleteManufacturerDetails")
	@ApiOperation(value = "deleteManufacturerDetails", notes = "To Delete Existing Product Manufaturer Details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response deleteManufacturerDetails(@ApiParam(value = "productManufacturerId", required = true) @NotNull @Valid @QueryParam("productManufacturerId") String productManufacturerId);
	
	@GET
	@Path("/getAllManufacturerDetails")
	@ApiOperation(value = "getAllManufacturerDetails", notes = "To get list of All Product Manufacturers", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllManufacturerDetails();
	
}
