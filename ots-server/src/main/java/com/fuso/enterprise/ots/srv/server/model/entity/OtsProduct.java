/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsProduct.findAll", query = "SELECT o FROM OtsProduct o"),
    @NamedQuery(name = "OtsProduct.findByOtsProductId", query = "SELECT o FROM OtsProduct o WHERE o.otsProductId = :otsProductId"),
    @NamedQuery(name = "OtsProduct.findByOtsProductNumber", query = "SELECT o FROM OtsProduct o WHERE o.otsProductNumber = :otsProductNumber"),
    @NamedQuery(name = "OtsProduct.findByOtsProductName", query = "SELECT o FROM OtsProduct o WHERE o.otsProductName = :otsProductName"),
    @NamedQuery(name = "OtsProduct.findByOtsProductDescription", query = "SELECT o FROM OtsProduct o WHERE o.otsProductDescription = :otsProductDescription"),
    @NamedQuery(name = "OtsProduct.findByOtsProductDescriptionLong", query = "SELECT o FROM OtsProduct o WHERE o.otsProductDescriptionLong = :otsProductDescriptionLong"),
    @NamedQuery(name = "OtsProduct.findByOtsProductStatus", query = "SELECT o FROM OtsProduct o WHERE o.otsProductStatus = :otsProductStatus"),
    @NamedQuery(name = "OtsProduct.findByOtsProductCreated", query = "SELECT o FROM OtsProduct o WHERE o.otsProductCreated = :otsProductCreated"),
    @NamedQuery(name = "OtsProduct.findByOtsProductUpdated", query = "SELECT o FROM OtsProduct o WHERE o.otsProductUpdated = :otsProductUpdated"),
    @NamedQuery(name = "OtsProduct.findByOtsProductHsnSac", query = "SELECT o FROM OtsProduct o WHERE o.otsProductHsnSac = :otsProductHsnSac"),
    @NamedQuery(name = "OtsProduct.findByOtsProductSellerPrice", query = "SELECT o FROM OtsProduct o WHERE o.otsProductSellerPrice = :otsProductSellerPrice"),
    @NamedQuery(name = "OtsProduct.findByOtsProductBasePrice", query = "SELECT o FROM OtsProduct o WHERE o.otsProductBasePrice = :otsProductBasePrice"),
    @NamedQuery(name = "OtsProduct.findByOtsProductPrice", query = "SELECT o FROM OtsProduct o WHERE o.otsProductPrice = :otsProductPrice"),
    @NamedQuery(name = "OtsProduct.findByOtsProductDiscountPercentage", query = "SELECT o FROM OtsProduct o WHERE o.otsProductDiscountPercentage = :otsProductDiscountPercentage"),
    @NamedQuery(name = "OtsProduct.findByOtsProductDiscountPrice", query = "SELECT o FROM OtsProduct o WHERE o.otsProductDiscountPrice = :otsProductDiscountPrice"),
    @NamedQuery(name = "OtsProduct.findByOtsProductGst", query = "SELECT o FROM OtsProduct o WHERE o.otsProductGst = :otsProductGst"),
    @NamedQuery(name = "OtsProduct.findByOtsProductGstPrice", query = "SELECT o FROM OtsProduct o WHERE o.otsProductGstPrice = :otsProductGstPrice"),
    @NamedQuery(name = "OtsProduct.findByOtsProductFinalPriceWithGst", query = "SELECT o FROM OtsProduct o WHERE o.otsProductFinalPriceWithGst = :otsProductFinalPriceWithGst"),
    @NamedQuery(name = "OtsProduct.findByOtsProductDeliveryCharge", query = "SELECT o FROM OtsProduct o WHERE o.otsProductDeliveryCharge = :otsProductDeliveryCharge"),
    @NamedQuery(name = "OtsProduct.findByOtsProductReturnDeliveryCharge", query = "SELECT o FROM OtsProduct o WHERE o.otsProductReturnDeliveryCharge = :otsProductReturnDeliveryCharge"),
    @NamedQuery(name = "OtsProduct.findByOtsProductBulkEligible", query = "SELECT o FROM OtsProduct o WHERE o.otsProductBulkEligible = :otsProductBulkEligible"),
    @NamedQuery(name = "OtsProduct.findByOtsProductBulkMinQty", query = "SELECT o FROM OtsProduct o WHERE o.otsProductBulkMinQty = :otsProductBulkMinQty"),
    @NamedQuery(name = "OtsProduct.findByOtsProductCancellationAvailability", query = "SELECT o FROM OtsProduct o WHERE o.otsProductCancellationAvailability = :otsProductCancellationAvailability"),
    @NamedQuery(name = "OtsProduct.findByOtsProductReplacementAvailability", query = "SELECT o FROM OtsProduct o WHERE o.otsProductReplacementAvailability = :otsProductReplacementAvailability"),
    @NamedQuery(name = "OtsProduct.findByOtsProductReplacementDays", query = "SELECT o FROM OtsProduct o WHERE o.otsProductReplacementDays = :otsProductReplacementDays"),
    @NamedQuery(name = "OtsProduct.findByOtsProductReturnAvailability", query = "SELECT o FROM OtsProduct o WHERE o.otsProductReturnAvailability = :otsProductReturnAvailability"),
    @NamedQuery(name = "OtsProduct.findByOtsProductReturnDays", query = "SELECT o FROM OtsProduct o WHERE o.otsProductReturnDays = :otsProductReturnDays"),
    @NamedQuery(name = "OtsProduct.findByUnitOfMeasurement", query = "SELECT o FROM OtsProduct o WHERE o.unitOfMeasurement = :unitOfMeasurement"),
    @NamedQuery(name = "OtsProduct.findByBulkAvailability", query = "SELECT o FROM OtsProduct o WHERE o.bulkAvailability = :bulkAvailability"),
    @NamedQuery(name = "OtsProduct.findByOtsProductTag", query = "SELECT o FROM OtsProduct o WHERE o.otsProductTag = :otsProductTag"),
    @NamedQuery(name = "OtsProduct.findByOtsNetQuantity", query = "SELECT o FROM OtsProduct o WHERE o.otsNetQuantity = :otsNetQuantity"),
    @NamedQuery(name = "OtsProduct.findByVariantFlag", query = "SELECT o FROM OtsProduct o WHERE o.variantFlag = :variantFlag"),
    @NamedQuery(name = "OtsProduct.findByOtsProductCountry", query = "SELECT o FROM OtsProduct o WHERE o.otsProductCountry = :otsProductCountry"),
    @NamedQuery(name = "OtsProduct.findByOtsProductCountryCode", query = "SELECT o FROM OtsProduct o WHERE o.otsProductCountryCode = :otsProductCountryCode"),
    @NamedQuery(name = "OtsProduct.findByOtsProductCurrency", query = "SELECT o FROM OtsProduct o WHERE o.otsProductCurrency = :otsProductCurrency"),
    @NamedQuery(name = "OtsProduct.findByOtsProductCurrencySymbol", query = "SELECT o FROM OtsProduct o WHERE o.otsProductCurrencySymbol = :otsProductCurrencySymbol"),
    @NamedQuery(name = "OtsProduct.findByOtsProductTagValue", query = "SELECT o FROM OtsProduct o WHERE o.otsProductTagValue = :otsProductTagValue"),
    @NamedQuery(name = "OtsProduct.findByOtsManufacturerName", query = "SELECT o FROM OtsProduct o WHERE o.otsManufacturerName = :otsManufacturerName"),
    @NamedQuery(name = "OtsProduct.findByOtsManufacturerAddress", query = "SELECT o FROM OtsProduct o WHERE o.otsManufacturerAddress = :otsManufacturerAddress"),
    @NamedQuery(name = "OtsProduct.findByOtsManufacturerGenericName", query = "SELECT o FROM OtsProduct o WHERE o.otsManufacturerGenericName = :otsManufacturerGenericName"),
    @NamedQuery(name = "OtsProduct.findByOtsManufacturerPackingImport", query = "SELECT o FROM OtsProduct o WHERE o.otsManufacturerPackingImport = :otsManufacturerPackingImport"),
    @NamedQuery(name = "OtsProduct.findByOtsConsumerCareName", query = "SELECT o FROM OtsProduct o WHERE o.otsConsumerCareName = :otsConsumerCareName"),
    @NamedQuery(name = "OtsProduct.findByOtsConsumerCareEmail", query = "SELECT o FROM OtsProduct o WHERE o.otsConsumerCareEmail = :otsConsumerCareEmail"),
    @NamedQuery(name = "OtsProduct.findByOtsConsumerCarePhoneNumber", query = "SELECT o FROM OtsProduct o WHERE o.otsConsumerCarePhoneNumber = :otsConsumerCarePhoneNumber"),
    @NamedQuery(name = "OtsProduct.findByOriginCountry", query = "SELECT o FROM OtsProduct o WHERE o.originCountry = :originCountry"),
    @NamedQuery(name = "OtsProduct.findByOtsTimeToShip", query = "SELECT o FROM OtsProduct o WHERE o.otsTimeToShip = :otsTimeToShip"),
    @NamedQuery(name = "OtsProduct.findByOtsTimeToDeliver", query = "SELECT o FROM OtsProduct o WHERE o.otsTimeToDeliver = :otsTimeToDeliver"),
    @NamedQuery(name = "OtsProduct.findByOtsSellerPickupReturn", query = "SELECT o FROM OtsProduct o WHERE o.otsSellerPickupReturn = :otsSellerPickupReturn"),
    @NamedQuery(name = "OtsProduct.findByOtsCodAvailability", query = "SELECT o FROM OtsProduct o WHERE o.otsCodAvailability = :otsCodAvailability"),
    @NamedQuery(name = "OtsProduct.findByOtsNutritionalFlag", query = "SELECT o FROM OtsProduct o WHERE o.otsNutritionalFlag = :otsNutritionalFlag"),
    @NamedQuery(name = "OtsProduct.findByOtsNutritionalInfo", query = "SELECT o FROM OtsProduct o WHERE o.otsNutritionalInfo = :otsNutritionalInfo"),
    @NamedQuery(name = "OtsProduct.findByOtsNutritionalAdditivesInfo", query = "SELECT o FROM OtsProduct o WHERE o.otsNutritionalAdditivesInfo = :otsNutritionalAdditivesInfo"),
    @NamedQuery(name = "OtsProduct.findByOtsNutritionalBrandOwnerFSSAILicenseNo", query = "SELECT o FROM OtsProduct o WHERE o.otsNutritionalBrandOwnerFSSAILicenseNo = :otsNutritionalBrandOwnerFSSAILicenseNo"),
    @NamedQuery(name = "OtsProduct.findByOtsNutritionalOtherFSSAILicenseNo", query = "SELECT o FROM OtsProduct o WHERE o.otsNutritionalOtherFSSAILicenseNo = :otsNutritionalOtherFSSAILicenseNo"),
    @NamedQuery(name = "OtsProduct.findByOtsNutritionalImporterFSSAILicenseNo", query = "SELECT o FROM OtsProduct o WHERE o.otsNutritionalImporterFSSAILicenseNo = :otsNutritionalImporterFSSAILicenseNo"),
    @NamedQuery(name = "OtsProduct.findByOtsOemModelNumber", query = "SELECT o FROM OtsProduct o WHERE o.otsOemModelNumber = :otsOemModelNumber"),
    @NamedQuery(name = "OtsProduct.findByOtsOemPartNumber", query = "SELECT o FROM OtsProduct o WHERE o.otsOemPartNumber = :otsOemPartNumber"),
    @NamedQuery(name = "OtsProduct.findByOtsOemShortDescription", query = "SELECT o FROM OtsProduct o WHERE o.otsOemShortDescription = :otsOemShortDescription"),
    @NamedQuery(name = "OtsProduct.findByOtsOemLongDescription", query = "SELECT o FROM OtsProduct o WHERE o.otsOemLongDescription = :otsOemLongDescription"),
    @NamedQuery(name = "OtsProduct.findByOtsOemUom", query = "SELECT o FROM OtsProduct o WHERE o.otsOemUom = :otsOemUom"),
    @NamedQuery(name = "OtsProduct.findByOtsVendorItemCode", query = "SELECT o FROM OtsProduct o WHERE o.otsVendorItemCode = :otsVendorItemCode")
    })
public class OtsProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ots_product_id")
    private UUID otsProductId;
    @Column(name = "ots_product_number")
    private String otsProductNumber;
    @Column(name = "ots_product_name")
    private String otsProductName;
    @Column(name = "ots_product_description")
    private String otsProductDescription;
    @Column(name = "ots_product_description_long")
    private String otsProductDescriptionLong;
    @Size(max = 45)
    @Column(name = "ots_product_status")
    private String otsProductStatus;
    @Column(name = "ots_product_created", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsProductCreated;
    @Column(name = "ots_product_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsProductUpdated;
    @Size(max = 45)
    @Column(name = "ots_product_hsn_sac", nullable = false)
    private String otsProductHsnSac = "0";
    @Size(max = 45)
    @Column(name = "ots_product_seller_price", nullable = false)
    private String otsProductSellerPrice = "0";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ots_product_base_price", nullable = false)
    private BigDecimal otsProductBasePrice = BigDecimal.ZERO;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ots_product_price", nullable = false)
    private BigDecimal otsProductPrice = BigDecimal.ZERO;
	@Size(max = 45)
	@Column(name = "ots_product_discount_percentage", nullable = false)
	private String otsProductDiscountPercentage  = "0";
    @Size(max = 45)
    @Column(name = "ots_product_discount_price", nullable = false)
    private String otsProductDiscountPrice = "0";
    @Basic(optional = false)
    @Size(max = 45)
    @Column(name = "ots_product_gst", nullable = false)
    private String otsProductGst = "0";
    @Size(max = 45)
    @Column(name = "ots_product_gst_price", nullable = false)
    private String otsProductGstPrice = "0";
    @Basic(optional = false)
    @Column(name = "ots_product_final_price_with_gst", nullable = false)
    private String otsProductFinalPriceWithGst = "0";
    @Size(max = 2147483647)
    @Column(name = "ots_product_image")
    private String otsProductImage;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image1")
    private String otsMultiProductImage1;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image2")
    private String otsMultiProductImage2;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image3")
    private String otsMultiProductImage3;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image4")
    private String otsMultiProductImage4;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image5")
    private String otsMultiProductImage5;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image6")
    private String otsMultiProductImage6;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image7")
    private String otsMultiProductImage7;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image8")
    private String otsMultiProductImage8;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image9")
    private String otsMultiProductImage9;
    @Size(max = 2147483647)
    @Column(name = "ots_multi_product_image10")
    private String otsMultiProductImage10;
    @Column(name = "ots_product_country")
    private String otsProductCountry;
    @Column(name = "ots_product_country_code")
    private String otsProductCountryCode;
    @Column(name = "ots_product_currency")
    private String otsProductCurrency;
    @Column(name = "ots_product_currency_symbol")
    private String otsProductCurrencySymbol;
    @Size(max = 45)
    @Column(name = "ots_product_delivery_charge", nullable = false)
    private String otsProductDeliveryCharge = "0";
    @Column(name = "ots_product_return_delivery_charge", nullable = false)
    private Integer otsProductReturnDeliveryCharge = 0;
    @Column(name = "ots_product_bulk_eligible")
    private Boolean otsProductBulkEligible;
    @Size(max = 45)
    @Column(name = "ots_product_bulk_min_qty")
    private String otsProductBulkMinQty;
    @Size(max = 2147483647)
    @Column(name = "ots_product_delivery_policy")
    private String otsProductDeliveryPolicy;
    @Basic(optional = false)
    @Column(name = "ots_product_cancellation_availability", nullable = false)
    private Boolean otsProductCancellationAvailability = false;
    @Size(max = 2147483647)
    @Column(name = "ots_product_cancellation_policy")
    private String otsProductCancellationPolicy;
    @Basic(optional = false)
    @Column(name = "ots_product_replacement_availability", nullable = false)
    private Boolean otsProductReplacementAvailability = false;
    @Size(max = 2147483647)
    @Column(name = "ots_product_replacement_policy")
    private String otsProductReplacementPolicy;
    @Basic(optional = false)
    @Size(max = 45)
    @Column(name = "ots_product_replacement_days", nullable = false)
    private String otsProductReplacementDays = "0";
    @Basic(optional = false)
    @Column(name = "ots_product_return_availability", nullable = false)
    private Boolean otsProductReturnAvailability = false;
    @Size(max = 2147483647)
    @Column(name = "ots_product_return_policy")
    private String otsProductReturnPolicy;
    @Basic(optional = false)
    @Size(max = 45)
    @Column(name = "ots_product_return_days", nullable = false)
    private String otsProductReturnDays = "0";
    @Size(max = 100)
    @Column(name = "unit_of_measurement")
    private String unitOfMeasurement;
    @Size(max = 10)
    @Column(name = "bulk_availability")
    private String bulkAvailability;
    @Size(max = 2147483647)
    @Column(name = "ots_product_tag")
    private String otsProductTag;
    @Size(max = 45)
    @Column(name = "ots_net_quantity")
    private String otsNetQuantity;
    @Basic(optional = false)
    @Column(name = "variant_flag", nullable = false)
    private Boolean variantFlag = false;
    @Size(max = 255)
    @Column(name = "ots_product_tag_value")
    private String otsProductTagValue;
    @Size(max = 255)
    @Column(name = "ots_manufacturer_name")
    private String otsManufacturerName;
    @Size(max = 255)
    @Column(name = "ots_manufacturer_address")
    private String otsManufacturerAddress;
    @Size(max = 255)
    @Column(name = "ots_manufacturer_generic_name")
    private String otsManufacturerGenericName;
    @Size(max = 255)
    @Column(name = "ots_manufacturer_packing_import")
    private String otsManufacturerPackingImport;
    @Size(max = 255)
    @Column(name = "ots_consumer_care_name")
    private String otsConsumerCareName;
    @Size(max = 255)
    @Column(name = "ots_consumer_care_email")
    private String otsConsumerCareEmail;
    @Size(max = 255)
    @Column(name = "ots_consumer_care_phone_number")
    private String otsConsumerCarePhoneNumber;
    @Size(max = 255)
    @Column(name = "origin_country")
    private String originCountry;
    @Size(max = 255)
    @Column(name = "ots_time_to_ship")
    private String otsTimeToShip;
    @Column(name = "ots_time_to_deliver")
    private String otsTimeToDeliver;
    @Column(name = "ots_seller_pickup_return")
    private String otsSellerPickupReturn;
    @Column(name = "ots_cod_Availability")
    private String otsCodAvailability;
    @Basic(optional = false)
    @Column(name = "ots_nutritional_flag", nullable = false)
    private Boolean otsNutritionalFlag = false;
    @Column(name = "ots_nutritional_info")
    private String otsNutritionalInfo;
    @Column(name = "ots_nutritional_additives_info")
    private String otsNutritionalAdditivesInfo;
    @Column(name = "ots_nutritional_brand_owner_FSSAI_license_no")
    private String otsNutritionalBrandOwnerFSSAILicenseNo;
    @Column(name = "ots_nutritional_other_FSSAI_license_no")
    private String otsNutritionalOtherFSSAILicenseNo;
    @Column(name = "ots_nutritional_Importer_FSSAI_license_no")
    private String otsNutritionalImporterFSSAILicenseNo;
    @Column(name = "ots_oem_model_number")
    private String otsOemModelNumber;
    @Column(name = "ots_oem_part_number")
    private String otsOemPartNumber;
    @Column(name = "ots_oem_short_description")
    private String otsOemShortDescription;
    @Column(name = "ots_oem_long_description")
    private String otsOemLongDescription;
    @Column(name = "ots_oem_uom")
    private String otsOemUom;
    @Column(name = "ots_vendor_item_code")
    private String otsVendorItemCode;
    @Column(name = "ots_product_details_pdf")
    private String otsProductDetailsPdf;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductId")
    private Collection<OtsCart> otsCartCollection;
    @JoinColumn(name = "ots_product_level_id", referencedColumnName = "ots_product_level_id")
    @ManyToOne(optional = false)
    private OtsProductLevel otsProductLevelId;
    @JoinColumn(name = "ots_distributor_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsDistributorId;
    @JoinColumn(name = "created_user", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts createdUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductCategoryId")
    private Collection<OtsProductCategoryMapping> otsProductCategoryMappingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductId")
    private Collection<OtsProductCategoryMapping> otsProductCategoryMappingCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductId")
    private Collection<OtsRatingReview> otsRatingReviewCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductId")
    private Collection<OtsProductStockHistory> otsProductStockHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductId")
    private Collection<OtsProductStock> otsProductStockCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductId")
    private Collection<OtsOrderProduct> otsOrderProductCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductId")
    private Collection<OtsProductWishlist> otsProductWishlistCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductId")
    private Collection<OtsSellerProductMapping> otsSellerProductMappingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductId")
    private Collection<OtsProductAttributeMapping> otsProductAttributeMappingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsSubcategoryId")
    private Collection<OtsSubcategoryAttributeMapping> otsSubcategoryAttributeMappingCollection;

    public OtsProduct() {
    }

    public OtsProduct(UUID otsProductId) {
        this.otsProductId = otsProductId;
    }

    public OtsProduct(UUID otsProductId, String otsProductHsnSac, String otsProductGst, String otsProductDeliveryCharge, Boolean otsProductCancellationAvailability, boolean otsProductReplacementAvailability, String otsProductReplacementDays, boolean otsProductReturnAvailability, String otsProductReturnDays) {
        this.otsProductId = otsProductId;
        this.otsProductHsnSac = otsProductHsnSac;
        this.otsProductGst = otsProductGst;
        this.otsProductDeliveryCharge = otsProductDeliveryCharge;
        this.otsProductCancellationAvailability = otsProductCancellationAvailability;
        this.otsProductReplacementAvailability = otsProductReplacementAvailability;
        this.otsProductReplacementDays = otsProductReplacementDays;
        this.otsProductReturnAvailability = otsProductReturnAvailability;
        this.otsProductReturnDays = otsProductReturnDays;
    }

    public UUID getOtsProductId() {
        return otsProductId;
    }

    public void setOtsProductId(UUID otsProductId) {
        this.otsProductId = otsProductId;
    }

    public String getOtsProductNumber() {
        return otsProductNumber;
    }

    public void setOtsProductNumber(String otsProductNumber) {
        this.otsProductNumber = otsProductNumber;
    }

    public String getOtsProductName() {
        return otsProductName;
    }

    public void setOtsProductName(String otsProductName) {
        this.otsProductName = otsProductName;
    }

    public String getOtsProductDescription() {
        return otsProductDescription;
    }

    public void setOtsProductDescription(String otsProductDescription) {
        this.otsProductDescription = otsProductDescription;
    }

    public String getOtsProductDescriptionLong() {
		return otsProductDescriptionLong;
	}

	public void setOtsProductDescriptionLong(String otsProductDescriptionLong) {
		this.otsProductDescriptionLong = otsProductDescriptionLong;
	}

	public String getOtsProductStatus() {
        return otsProductStatus;
    }

    public void setOtsProductStatus(String otsProductStatus) {
        this.otsProductStatus = otsProductStatus;
    }

    public Date getOtsProductUpdated() {
		return otsProductUpdated;
	}

	public void setOtsProductUpdated(Date otsProductUpdated) {
		this.otsProductUpdated = otsProductUpdated;
	}

	public Date getOtsProductCreated() {
        return otsProductCreated;
    }

    public void setOtsProductCreated(Date otsProductCreated) {
        this.otsProductCreated = otsProductCreated;
    }

    public String getOtsProductHsnSac() {
        return otsProductHsnSac;
    }

    public void setOtsProductHsnSac(String otsProductHsnSac) {
        this.otsProductHsnSac = otsProductHsnSac;
    }

    public String getOtsProductSellerPrice() {
        return otsProductSellerPrice;
    }

    public void setOtsProductSellerPrice(String otsProductSellerPrice) {
        this.otsProductSellerPrice = otsProductSellerPrice;
    }

    public BigDecimal getOtsProductBasePrice() {
        return otsProductBasePrice;
    }

    public void setOtsProductBasePrice(BigDecimal otsProductBasePrice) {
        this.otsProductBasePrice = otsProductBasePrice;
    }

    public BigDecimal getOtsProductPrice() {
        return otsProductPrice;
    }

    public void setOtsProductPrice(BigDecimal otsProductPrice) {
        this.otsProductPrice = otsProductPrice;
    }

    public String getOtsProductDiscountPercentage() {
		return otsProductDiscountPercentage;
	}

	public void setOtsProductDiscountPercentage(String otsProductDiscountPercentage) {
		this.otsProductDiscountPercentage = otsProductDiscountPercentage;
	}
    
    public String getOtsProductDiscountPrice() {
        return otsProductDiscountPrice;
    }

	public void setOtsProductDiscountPrice(String otsProductDiscountPrice) {
        this.otsProductDiscountPrice = otsProductDiscountPrice;
    }

    public String getOtsProductGst() {
        return otsProductGst;
    }

    public void setOtsProductGst(String otsProductGst) {
        this.otsProductGst = otsProductGst;
    }

    public String getOtsProductGstPrice() {
		return otsProductGstPrice;
	}

	public void setOtsProductGstPrice(String otsProductGstPrice) {
		this.otsProductGstPrice = otsProductGstPrice;
	}

	public String getOtsProductFinalPriceWithGst() {
		return otsProductFinalPriceWithGst;
	}

	public void setOtsProductFinalPriceWithGst(String otsProductFinalPriceWithGst) {
		this.otsProductFinalPriceWithGst = otsProductFinalPriceWithGst;
	}

	public String getOtsProductImage() {
        return otsProductImage;
    }

    public void setOtsProductImage(String otsProductImage) {
        this.otsProductImage = otsProductImage;
    }

    public String getOtsMultiProductImage1() {
        return otsMultiProductImage1;
    }

    public void setOtsMultiProductImage1(String otsMultiProductImage1) {
        this.otsMultiProductImage1 = otsMultiProductImage1;
    }

    public String getOtsMultiProductImage2() {
        return otsMultiProductImage2;
    }

    public void setOtsMultiProductImage2(String otsMultiProductImage2) {
        this.otsMultiProductImage2 = otsMultiProductImage2;
    }

    public String getOtsMultiProductImage3() {
        return otsMultiProductImage3;
    }

    public void setOtsMultiProductImage3(String otsMultiProductImage3) {
        this.otsMultiProductImage3 = otsMultiProductImage3;
    }

    public String getOtsMultiProductImage4() {
        return otsMultiProductImage4;
    }

    public void setOtsMultiProductImage4(String otsMultiProductImage4) {
        this.otsMultiProductImage4 = otsMultiProductImage4;
    }

    public String getOtsMultiProductImage5() {
        return otsMultiProductImage5;
    }

    public void setOtsMultiProductImage5(String otsMultiProductImage5) {
        this.otsMultiProductImage5 = otsMultiProductImage5;
    }

    public String getOtsMultiProductImage6() {
        return otsMultiProductImage6;
    }

    public void setOtsMultiProductImage6(String otsMultiProductImage6) {
        this.otsMultiProductImage6 = otsMultiProductImage6;
    }

    public String getOtsMultiProductImage7() {
        return otsMultiProductImage7;
    }

    public void setOtsMultiProductImage7(String otsMultiProductImage7) {
        this.otsMultiProductImage7 = otsMultiProductImage7;
    }

    public String getOtsMultiProductImage8() {
        return otsMultiProductImage8;
    }

    public void setOtsMultiProductImage8(String otsMultiProductImage8) {
        this.otsMultiProductImage8 = otsMultiProductImage8;
    }

    public String getOtsMultiProductImage9() {
        return otsMultiProductImage9;
    }

    public void setOtsMultiProductImage9(String otsMultiProductImage9) {
        this.otsMultiProductImage9 = otsMultiProductImage9;
    }

    public String getOtsMultiProductImage10() {
        return otsMultiProductImage10;
    }

    public void setOtsMultiProductImage10(String otsMultiProductImage10) {
        this.otsMultiProductImage10 = otsMultiProductImage10;
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

	public String getOtsProductDeliveryCharge() {
        return otsProductDeliveryCharge;
    }

    public void setOtsProductDeliveryCharge(String otsProductDeliveryCharge) {
        this.otsProductDeliveryCharge = otsProductDeliveryCharge;
    }
    
    public Integer getOtsProductReturnDeliveryCharge() {
		return otsProductReturnDeliveryCharge;
	}

	public void setOtsProductReturnDeliveryCharge(Integer otsProductReturnDeliveryCharge) {
		this.otsProductReturnDeliveryCharge = otsProductReturnDeliveryCharge;
	}

    public Boolean getOtsProductBulkEligible() {
        return otsProductBulkEligible;
    }

    public void setOtsProductBulkEligible(Boolean otsProductBulkEligible) {
        this.otsProductBulkEligible = otsProductBulkEligible;
    }

    public String getOtsProductBulkMinQty() {
        return otsProductBulkMinQty;
    }

    public void setOtsProductBulkMinQty(String otsProductBulkMinQty) {
        this.otsProductBulkMinQty = otsProductBulkMinQty;
    }

    public String getOtsProductDeliveryPolicy() {
        return otsProductDeliveryPolicy;
    }

    public void setOtsProductDeliveryPolicy(String otsProductDeliveryPolicy) {
        this.otsProductDeliveryPolicy = otsProductDeliveryPolicy;
    }

    public Boolean getOtsProductCancellationAvailability() {
        return otsProductCancellationAvailability;
    }

    public void setOtsProductCancellationAvailability(Boolean otsProductCancellationAvailability) {
        this.otsProductCancellationAvailability = otsProductCancellationAvailability;
    }

    public String getOtsProductCancellationPolicy() {
        return otsProductCancellationPolicy;
    }

    public void setOtsProductCancellationPolicy(String otsProductCancellationPolicy) {
        this.otsProductCancellationPolicy = otsProductCancellationPolicy;
    }

    public Boolean getOtsProductReplacementAvailability() {
        return otsProductReplacementAvailability;
    }

    public void setOtsProductReplacementAvailability(Boolean otsProductReplacementAvailability) {
        this.otsProductReplacementAvailability = otsProductReplacementAvailability;
    }

    public String getOtsProductReplacementPolicy() {
        return otsProductReplacementPolicy;
    }

    public void setOtsProductReplacementPolicy(String otsProductReplacementPolicy) {
        this.otsProductReplacementPolicy = otsProductReplacementPolicy;
    }

    public String getOtsProductReplacementDays() {
        return otsProductReplacementDays;
    }

    public void setOtsProductReplacementDays(String otsProductReplacementDays) {
        this.otsProductReplacementDays = otsProductReplacementDays;
    }

    public Boolean getOtsProductReturnAvailability() {
        return otsProductReturnAvailability;
    }

    public void setOtsProductReturnAvailability(Boolean otsProductReturnAvailability) {
        this.otsProductReturnAvailability = otsProductReturnAvailability;
    }

    public String getOtsProductReturnPolicy() {
        return otsProductReturnPolicy;
    }

    public void setOtsProductReturnPolicy(String otsProductReturnPolicy) {
        this.otsProductReturnPolicy = otsProductReturnPolicy;
    }

    public String getOtsProductReturnDays() {
        return otsProductReturnDays;
    }

    public void setOtsProductReturnDays(String otsProductReturnDays) {
        this.otsProductReturnDays = otsProductReturnDays;
    }
    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getBulkAvailability() {
        return bulkAvailability;
    }

    public void setBulkAvailability(String bulkAvailability) {
        this.bulkAvailability = bulkAvailability;
    }

    public String getOtsProductTag() {
        return otsProductTag;
    }

    public void setOtsProductTag(String otsProductTag) {
        this.otsProductTag = otsProductTag;
    }

    public String getOtsNetQuantity() {
		return otsNetQuantity;
	}

	public void setOtsNetQuantity(String otsNetQuantity) {
		this.otsNetQuantity = otsNetQuantity;
	}
	
    public Boolean getVariantFlag() {
        return variantFlag;
    }

    public void setVariantFlag(Boolean variantFlag) {
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

	public Boolean getOtsNutritionalFlag() {
		return otsNutritionalFlag;
	}

	public void setOtsNutritionalFlag(Boolean otsNutritionalFlag) {
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

	@XmlTransient
    public Collection<OtsCart> getOtsCartCollection() {
        return otsCartCollection;
    }

    public void setOtsCartCollection(Collection<OtsCart> otsCartCollection) {
        this.otsCartCollection = otsCartCollection;
    }
    public OtsProductLevel getOtsProductLevelId() {
        return otsProductLevelId;
    }

    public void setOtsProductLevelId(OtsProductLevel otsProductLevelId) {
        this.otsProductLevelId = otsProductLevelId;
    }

    public OtsUsers getOtsDistributorId() {
        return otsDistributorId;
    }

    public void setOtsDistributorId(OtsUsers otsDistributorId) {
        this.otsDistributorId = otsDistributorId;
    }
    
    public Useraccounts getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Useraccounts createdUser) {
		this.createdUser = createdUser;
	}

	@XmlTransient
    public Collection<OtsProductAttributeMapping> getOtsProductAttributeMappingCollection() {
        return otsProductAttributeMappingCollection;
    }

    public void setOtsProductAttributeMappingCollection(Collection<OtsProductAttributeMapping> otsProductAttributeMappingCollection) {
        this.otsProductAttributeMappingCollection = otsProductAttributeMappingCollection;
    }

    @XmlTransient
    public Collection<OtsProductCategoryMapping> getOtsProductCategoryMappingCollection() {
        return otsProductCategoryMappingCollection;
    }

    public void setOtsProductCategoryMappingCollection(Collection<OtsProductCategoryMapping> otsProductCategoryMappingCollection) {
        this.otsProductCategoryMappingCollection = otsProductCategoryMappingCollection;
    }

    @XmlTransient
    public Collection<OtsProductCategoryMapping> getOtsProductCategoryMappingCollection1() {
        return otsProductCategoryMappingCollection1;
    }

    public void setOtsProductCategoryMappingCollection1(Collection<OtsProductCategoryMapping> otsProductCategoryMappingCollection1) {
        this.otsProductCategoryMappingCollection1 = otsProductCategoryMappingCollection1;
    }

    @XmlTransient
    public Collection<OtsSubcategoryAttributeMapping> getOtsSubcategoryAttributeMappingCollection() {
        return otsSubcategoryAttributeMappingCollection;
    }

    public void setOtsSubcategoryAttributeMappingCollection(Collection<OtsSubcategoryAttributeMapping> otsSubcategoryAttributeMappingCollection) {
        this.otsSubcategoryAttributeMappingCollection = otsSubcategoryAttributeMappingCollection;
    }

    @XmlTransient
    public Collection<OtsRatingReview> getOtsRatingReviewCollection() {
        return otsRatingReviewCollection;
    }

    public void setOtsRatingReviewCollection(Collection<OtsRatingReview> otsRatingReviewCollection) {
        this.otsRatingReviewCollection = otsRatingReviewCollection;
    }

    @XmlTransient
    public Collection<OtsProductStockHistory> getOtsProductStockHistoryCollection() {
        return otsProductStockHistoryCollection;
    }

    public void setOtsProductStockHistoryCollection(Collection<OtsProductStockHistory> otsProductStockHistoryCollection) {
        this.otsProductStockHistoryCollection = otsProductStockHistoryCollection;
    }


    @XmlTransient
    public Collection<OtsProductStock> getOtsProductStockCollection() {
        return otsProductStockCollection;
    }

    public void setOtsProductStockCollection(Collection<OtsProductStock> otsProductStockCollection) {
        this.otsProductStockCollection = otsProductStockCollection;
    }
    @XmlTransient
    public Collection<OtsOrderProduct> getOtsOrderProductCollection() {
        return otsOrderProductCollection;
    }

    public void setOtsOrderProductCollection(Collection<OtsOrderProduct> otsOrderProductCollection) {
        this.otsOrderProductCollection = otsOrderProductCollection;
    }

    @XmlTransient
    public Collection<OtsProductWishlist> getOtsProductWishlistCollection() {
        return otsProductWishlistCollection;
    }

    public void setOtsProductWishlistCollection(Collection<OtsProductWishlist> otsProductWishlistCollection) {
        this.otsProductWishlistCollection = otsProductWishlistCollection;
    }

    @XmlTransient
    public Collection<OtsSellerProductMapping> getOtsSellerProductMappingCollection() {
        return otsSellerProductMappingCollection;
    }

    public void setOtsSellerProductMappingCollection(Collection<OtsSellerProductMapping> otsSellerProductMappingCollection) {
        this.otsSellerProductMappingCollection = otsSellerProductMappingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsProductId != null ? otsProductId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsProduct)) {
            return false;
        }
        OtsProduct other = (OtsProduct) object;
        if ((this.otsProductId == null && other.otsProductId != null) || (this.otsProductId != null && !this.otsProductId.equals(other.otsProductId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsProduct[ otsProductId=" + otsProductId + " ]";
    }
    
}
