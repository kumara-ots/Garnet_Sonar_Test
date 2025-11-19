package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;
import java.util.Map;

public class ProductDetails {

	private String productId;
	
	private String productName;
	
	private String productDescription;
	
	private String productDescriptionLong;
	
	private String productStatus;
	 
	private String productLevel;
	 
	private String productHsnSac;
	 
	private String productSellerPrice;
	 
	private String productBasePrice;
	
	private String productPrice;
	
	private String productDiscountPercentage;
	 
	private String productDiscountPrice;
	 
	private String gst;
	
	private String gstPrice;
	 
	private String productImage;
	 
	private String distributorId;
	 
	private String multiProductImage1;
	
	private String multiProductImage2;
	 
	private String multiProductImage3;
	 
	private String multiProductImage4;
	 
	private String multiProductImage5;
	 
	private String multiProductImage6;
	 
	private String multiProductImage7;
	 
	private String multiProductImage8;
	 
	private String multiProductImage9;
	 
	private String multiProductImage10;
	 
	private String productTotalRatingCount;
	
	private String productAverageRating;
	 
	private String productDeliveryCharge;
	
	private String productReturnDeliveryCharge;
	 
	private String createdUser;
	 
	private String productBulkEligible;
	 
	private String ProductBulkMinQty;
	 
	private String productDeliveryPolicy;
	 
	private String productCancellationAvailability;
		
	private String productCancellationPolicy;
	 
	private String productReplacementAvailability;
	 
	private String productReplacementPolicy;
	 
	private String productReplacementDays;
	 
	private String productReturnAvailability;
	 
	private String productReturnPolicy;
	 
	private String productReturnDays;
	 
	private String unitOfMeasurement;
	
	private String DistributerName;
	
	private String distributorEmailId;
	 
	private List<AttributeDetails> productAttribute;
	
	private String productStockQuantity;
	
	private String productStrikenPrice;
	 
	private String productFinalPrice;
	
	private String categoryId;
	
    private String categoryName;
	
	private String subCategoryId;
	
	private String subCategoryName;
	
	private String productTag;
	
	private String productNetQuantity;
	
    private String otsProductCountry;

    private String otsProductCountryCode;

    private String otsProductCurrency;

    private String otsProductCurrencySymbol;
	
	private String otsOemModelNumber;

    private String otsOemPartNumber;

    private String otsOemShortDescription;

    private String otsOemLongDescription;

    private String otsOemUom;

    private String otsVendorItemCode;
    
    private String otsProductDetailsPdf;
    
    private String variantFlag;

    private String otsProductTagValue;

    private String otsManufacturerName;

    private String otsManufacturerAddress;

    private String otsManufacturerGenericName;

    private String otsManufacturerPackingImport;

    private String otsConsumerCareName;

    private String otsConsumerCareEmail;

    private String otsConsumerCarePhoneNumber;

    private String originCountry;

    private String otsTimeToShip;

    private String otsTimeToDeliver;

    private String otsSellerPickupReturn;

    private String otsCodAvailability;

    private String otsNutritionalFlag;

    private String otsNutritionalInfo;

    private String otsNutritionalAdditivesInfo;

    private String otsNutritionalBrandOwnerFSSAILicenseNo;

    private String otsNutritionalOtherFSSAILicenseNo;

    private String otsNutritionalImporterFSSAILicenseNo;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductDescriptionLong() {
		return productDescriptionLong;
	}

	public void setProductDescriptionLong(String productDescriptionLong) {
		this.productDescriptionLong = productDescriptionLong;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getProductLevel() {
		return productLevel;
	}

	public void setProductLevel(String productLevel) {
		this.productLevel = productLevel;
	}

	public String getProductHsnSac() {
		return productHsnSac;
	}

	public void setProductHsnSac(String productHsnSac) {
		this.productHsnSac = productHsnSac;
	}

	public String getProductSellerPrice() {
		return productSellerPrice;
	}

	public void setProductSellerPrice(String productSellerPrice) {
		this.productSellerPrice = productSellerPrice;
	}

	public String getProductBasePrice() {
		return productBasePrice;
	}

	public void setProductBasePrice(String productBasePrice) {
		this.productBasePrice = productBasePrice;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDiscountPercentage() {
		return productDiscountPercentage;
	}

	public void setProductDiscountPercentage(String productDiscountPercentage) {
		this.productDiscountPercentage = productDiscountPercentage;
	}

	public String getProductDiscountPrice() {
		return productDiscountPrice;
	}

	public void setProductDiscountPrice(String productDiscountPrice) {
		this.productDiscountPrice = productDiscountPrice;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getGstPrice() {
		return gstPrice;
	}

	public void setGstPrice(String gstPrice) {
		this.gstPrice = gstPrice;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public String getMultiProductImage1() {
		return multiProductImage1;
	}

	public void setMultiProductImage1(String multiProductImage1) {
		this.multiProductImage1 = multiProductImage1;
	}

	public String getMultiProductImage2() {
		return multiProductImage2;
	}

	public void setMultiProductImage2(String multiProductImage2) {
		this.multiProductImage2 = multiProductImage2;
	}

	public String getMultiProductImage3() {
		return multiProductImage3;
	}

	public void setMultiProductImage3(String multiProductImage3) {
		this.multiProductImage3 = multiProductImage3;
	}

	public String getMultiProductImage4() {
		return multiProductImage4;
	}

	public void setMultiProductImage4(String multiProductImage4) {
		this.multiProductImage4 = multiProductImage4;
	}

	public String getMultiProductImage5() {
		return multiProductImage5;
	}

	public void setMultiProductImage5(String multiProductImage5) {
		this.multiProductImage5 = multiProductImage5;
	}

	public String getMultiProductImage6() {
		return multiProductImage6;
	}

	public void setMultiProductImage6(String multiProductImage6) {
		this.multiProductImage6 = multiProductImage6;
	}

	public String getMultiProductImage7() {
		return multiProductImage7;
	}

	public void setMultiProductImage7(String multiProductImage7) {
		this.multiProductImage7 = multiProductImage7;
	}

	public String getMultiProductImage8() {
		return multiProductImage8;
	}

	public void setMultiProductImage8(String multiProductImage8) {
		this.multiProductImage8 = multiProductImage8;
	}

	public String getMultiProductImage9() {
		return multiProductImage9;
	}

	public void setMultiProductImage9(String multiProductImage9) {
		this.multiProductImage9 = multiProductImage9;
	}

	public String getMultiProductImage10() {
		return multiProductImage10;
	}

	public void setMultiProductImage10(String multiProductImage10) {
		this.multiProductImage10 = multiProductImage10;
	}

	public String getProductTotalRatingCount() {
		return productTotalRatingCount;
	}

	public void setProductTotalRatingCount(String productTotalRatingCount) {
		this.productTotalRatingCount = productTotalRatingCount;
	}

	public String getProductAverageRating() {
		return productAverageRating;
	}

	public void setProductAverageRating(String productAverageRating) {
		this.productAverageRating = productAverageRating;
	}

	public String getProductDeliveryCharge() {
		return productDeliveryCharge;
	}

	public void setProductDeliveryCharge(String productDeliveryCharge) {
		this.productDeliveryCharge = productDeliveryCharge;
	}

	public String getProductReturnDeliveryCharge() {
		return productReturnDeliveryCharge;
	}

	public void setProductReturnDeliveryCharge(String productReturnDeliveryCharge) {
		this.productReturnDeliveryCharge = productReturnDeliveryCharge;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getProductBulkEligible() {
		return productBulkEligible;
	}

	public void setProductBulkEligible(String productBulkEligible) {
		this.productBulkEligible = productBulkEligible;
	}

	public String getProductBulkMinQty() {
		return ProductBulkMinQty;
	}

	public void setProductBulkMinQty(String productBulkMinQty) {
		ProductBulkMinQty = productBulkMinQty;
	}

	public String getProductDeliveryPolicy() {
		return productDeliveryPolicy;
	}

	public void setProductDeliveryPolicy(String productDeliveryPolicy) {
		this.productDeliveryPolicy = productDeliveryPolicy;
	}

	public String getProductCancellationAvailability() {
		return productCancellationAvailability;
	}

	public void setProductCancellationAvailability(String productCancellationAvailability) {
		this.productCancellationAvailability = productCancellationAvailability;
	}

	public String getProductCancellationPolicy() {
		return productCancellationPolicy;
	}

	public void setProductCancellationPolicy(String productCancellationPolicy) {
		this.productCancellationPolicy = productCancellationPolicy;
	}

	public String getProductReplacementAvailability() {
		return productReplacementAvailability;
	}

	public void setProductReplacementAvailability(String productReplacementAvailability) {
		this.productReplacementAvailability = productReplacementAvailability;
	}

	public String getProductReplacementPolicy() {
		return productReplacementPolicy;
	}

	public void setProductReplacementPolicy(String productReplacementPolicy) {
		this.productReplacementPolicy = productReplacementPolicy;
	}

	public String getProductReplacementDays() {
		return productReplacementDays;
	}

	public void setProductReplacementDays(String productReplacementDays) {
		this.productReplacementDays = productReplacementDays;
	}

	public String getProductReturnAvailability() {
		return productReturnAvailability;
	}

	public void setProductReturnAvailability(String productReturnAvailability) {
		this.productReturnAvailability = productReturnAvailability;
	}

	public String getProductReturnPolicy() {
		return productReturnPolicy;
	}

	public void setProductReturnPolicy(String productReturnPolicy) {
		this.productReturnPolicy = productReturnPolicy;
	}

	public String getProductReturnDays() {
		return productReturnDays;
	}

	public void setProductReturnDays(String productReturnDays) {
		this.productReturnDays = productReturnDays;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getDistributerName() {
		return DistributerName;
	}

	public void setDistributerName(String distributerName) {
		DistributerName = distributerName;
	}

	public String getDistributorEmailId() {
		return distributorEmailId;
	}

	public void setDistributorEmailId(String distributorEmailId) {
		this.distributorEmailId = distributorEmailId;
	}

	public List<AttributeDetails> getProductAttribute() {
		return productAttribute;
	}

	public void setProductAttribute(List<AttributeDetails> productAttribute) {
		this.productAttribute = productAttribute;
	}

	public String getProductStockQuantity() {
		return productStockQuantity;
	}

	public void setProductStockQuantity(String productStockQuantity) {
		this.productStockQuantity = productStockQuantity;
	}

	public String getProductStrikenPrice() {
		return productStrikenPrice;
	}

	public void setProductStrikenPrice(String productStrikenPrice) {
		this.productStrikenPrice = productStrikenPrice;
	}

	public String getProductFinalPrice() {
		return productFinalPrice;
	}

	public void setProductFinalPrice(String productFinalPrice) {
		this.productFinalPrice = productFinalPrice;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public String getProductTag() {
		return productTag;
	}

	public void setProductTag(String productTag) {
		this.productTag = productTag;
	}

	public String getProductNetQuantity() {
		return productNetQuantity;
	}

	public void setProductNetQuantity(String productNetQuantity) {
		this.productNetQuantity = productNetQuantity;
	}

	public String getOtsProductCountry() {
		return otsProductCountry;
	}

	public void setOtsProductCountry(String otsProductCountry) {
		this.otsProductCountry = otsProductCountry;
	}

	public String getOtsProductCountryCode() {
		return otsProductCountryCode;
	}

	public void setOtsProductCountryCode(String otsProductCountryCode) {
		this.otsProductCountryCode = otsProductCountryCode;
	}

	public String getOtsProductCurrency() {
		return otsProductCurrency;
	}

	public void setOtsProductCurrency(String otsProductCurrency) {
		this.otsProductCurrency = otsProductCurrency;
	}

	public String getOtsProductCurrencySymbol() {
		return otsProductCurrencySymbol;
	}

	public void setOtsProductCurrencySymbol(String otsProductCurrencySymbol) {
		this.otsProductCurrencySymbol = otsProductCurrencySymbol;
	}

	public String getOtsOemModelNumber() {
		return otsOemModelNumber;
	}

	public void setOtsOemModelNumber(String otsOemModelNumber) {
		this.otsOemModelNumber = otsOemModelNumber;
	}

	public String getOtsOemPartNumber() {
		return otsOemPartNumber;
	}

	public void setOtsOemPartNumber(String otsOemPartNumber) {
		this.otsOemPartNumber = otsOemPartNumber;
	}

	public String getOtsOemShortDescription() {
		return otsOemShortDescription;
	}

	public void setOtsOemShortDescription(String otsOemShortDescription) {
		this.otsOemShortDescription = otsOemShortDescription;
	}

	public String getOtsOemLongDescription() {
		return otsOemLongDescription;
	}

	public void setOtsOemLongDescription(String otsOemLongDescription) {
		this.otsOemLongDescription = otsOemLongDescription;
	}

	public String getOtsOemUom() {
		return otsOemUom;
	}

	public void setOtsOemUom(String otsOemUom) {
		this.otsOemUom = otsOemUom;
	}

	public String getOtsVendorItemCode() {
		return otsVendorItemCode;
	}

	public void setOtsVendorItemCode(String otsVendorItemCode) {
		this.otsVendorItemCode = otsVendorItemCode;
	}

	public String getOtsProductDetailsPdf() {
		return otsProductDetailsPdf;
	}

	public void setOtsProductDetailsPdf(String otsProductDetailsPdf) {
		this.otsProductDetailsPdf = otsProductDetailsPdf;
	}

	public String getVariantFlag() {
		return variantFlag;
	}

	public void setVariantFlag(String variantFlag) {
		this.variantFlag = variantFlag;
	}

	public String getOtsProductTagValue() {
		return otsProductTagValue;
	}

	public void setOtsProductTagValue(String otsProductTagValue) {
		this.otsProductTagValue = otsProductTagValue;
	}

	public String getOtsManufacturerName() {
		return otsManufacturerName;
	}

	public void setOtsManufacturerName(String otsManufacturerName) {
		this.otsManufacturerName = otsManufacturerName;
	}

	public String getOtsManufacturerAddress() {
		return otsManufacturerAddress;
	}

	public void setOtsManufacturerAddress(String otsManufacturerAddress) {
		this.otsManufacturerAddress = otsManufacturerAddress;
	}

	public String getOtsManufacturerGenericName() {
		return otsManufacturerGenericName;
	}

	public void setOtsManufacturerGenericName(String otsManufacturerGenericName) {
		this.otsManufacturerGenericName = otsManufacturerGenericName;
	}

	public String getOtsManufacturerPackingImport() {
		return otsManufacturerPackingImport;
	}

	public void setOtsManufacturerPackingImport(String otsManufacturerPackingImport) {
		this.otsManufacturerPackingImport = otsManufacturerPackingImport;
	}

	public String getOtsConsumerCareName() {
		return otsConsumerCareName;
	}

	public void setOtsConsumerCareName(String otsConsumerCareName) {
		this.otsConsumerCareName = otsConsumerCareName;
	}

	public String getOtsConsumerCareEmail() {
		return otsConsumerCareEmail;
	}

	public void setOtsConsumerCareEmail(String otsConsumerCareEmail) {
		this.otsConsumerCareEmail = otsConsumerCareEmail;
	}

	public String getOtsConsumerCarePhoneNumber() {
		return otsConsumerCarePhoneNumber;
	}

	public void setOtsConsumerCarePhoneNumber(String otsConsumerCarePhoneNumber) {
		this.otsConsumerCarePhoneNumber = otsConsumerCarePhoneNumber;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public String getOtsTimeToShip() {
		return otsTimeToShip;
	}

	public void setOtsTimeToShip(String otsTimeToShip) {
		this.otsTimeToShip = otsTimeToShip;
	}

	public String getOtsTimeToDeliver() {
		return otsTimeToDeliver;
	}

	public void setOtsTimeToDeliver(String otsTimeToDeliver) {
		this.otsTimeToDeliver = otsTimeToDeliver;
	}

	public String getOtsSellerPickupReturn() {
		return otsSellerPickupReturn;
	}

	public void setOtsSellerPickupReturn(String otsSellerPickupReturn) {
		this.otsSellerPickupReturn = otsSellerPickupReturn;
	}

	public String getOtsCodAvailability() {
		return otsCodAvailability;
	}

	public void setOtsCodAvailability(String otsCodAvailability) {
		this.otsCodAvailability = otsCodAvailability;
	}

	public String getOtsNutritionalFlag() {
		return otsNutritionalFlag;
	}

	public void setOtsNutritionalFlag(String otsNutritionalFlag) {
		this.otsNutritionalFlag = otsNutritionalFlag;
	}

	public String getOtsNutritionalInfo() {
		return otsNutritionalInfo;
	}

	public void setOtsNutritionalInfo(String otsNutritionalInfo) {
		this.otsNutritionalInfo = otsNutritionalInfo;
	}

	public String getOtsNutritionalAdditivesInfo() {
		return otsNutritionalAdditivesInfo;
	}

	public void setOtsNutritionalAdditivesInfo(String otsNutritionalAdditivesInfo) {
		this.otsNutritionalAdditivesInfo = otsNutritionalAdditivesInfo;
	}

	public String getOtsNutritionalBrandOwnerFSSAILicenseNo() {
		return otsNutritionalBrandOwnerFSSAILicenseNo;
	}

	public void setOtsNutritionalBrandOwnerFSSAILicenseNo(String otsNutritionalBrandOwnerFSSAILicenseNo) {
		this.otsNutritionalBrandOwnerFSSAILicenseNo = otsNutritionalBrandOwnerFSSAILicenseNo;
	}

	public String getOtsNutritionalOtherFSSAILicenseNo() {
		return otsNutritionalOtherFSSAILicenseNo;
	}

	public void setOtsNutritionalOtherFSSAILicenseNo(String otsNutritionalOtherFSSAILicenseNo) {
		this.otsNutritionalOtherFSSAILicenseNo = otsNutritionalOtherFSSAILicenseNo;
	}

	public String getOtsNutritionalImporterFSSAILicenseNo() {
		return otsNutritionalImporterFSSAILicenseNo;
	}

	public void setOtsNutritionalImporterFSSAILicenseNo(String otsNutritionalImporterFSSAILicenseNo) {
		this.otsNutritionalImporterFSSAILicenseNo = otsNutritionalImporterFSSAILicenseNo;
	}

}
