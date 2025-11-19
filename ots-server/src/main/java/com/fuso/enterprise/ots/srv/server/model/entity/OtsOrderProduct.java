/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_order_product")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsOrderProduct.findAll", query = "SELECT o FROM OtsOrderProduct o"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsOrderProductId", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsOrderProductId = :otsOrderProductId"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsSuborderId", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsSuborderId = :otsSuborderId"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsOrderedQty", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsOrderedQty = :otsOrderedQty"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsDeliveredQty", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsDeliveredQty = :otsDeliveredQty"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsOrderProductStatus", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsOrderProductStatus = :otsOrderProductStatus"),
	@NamedQuery(name = "OtsOrderProduct.findByOtsOndcFulfillmentId", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsOndcFulfillmentId = :otsOndcFulfillmentId"),
	@NamedQuery(name = "OtsOrderProduct.findByOtsOndcFulfillmentStatus", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsOndcFulfillmentStatus = :otsOndcFulfillmentStatus"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsOrderProductCost", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsOrderProductCost = :otsOrderProductCost"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductPriceWithoutGst", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductPriceWithoutGst = :otsProductPriceWithoutGst"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductSellerPrice", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductSellerPrice = :otsProductSellerPrice"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductBasePrice", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductBasePrice = :otsProductBasePrice"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductPrice", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductPrice = :otsProductPrice"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductGst", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductGst = :otsProductGst"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductGstPrice", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductGstPrice = :otsProductGstPrice"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductPercentage", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductPercentage = :otsProductPercentage"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductDiscountPrice", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductDiscountPrice = :otsProductDiscountPrice"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductDeliveryCharge", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductDeliveryCharge = :otsProductDeliveryCharge"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductReturnDeliveryCharge", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductReturnDeliveryCharge = :otsProductReturnDeliveryCharge"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsSuborderAssignedDate", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsSuborderAssignedDate = :otsSuborderAssignedDate"),
	@NamedQuery(name = "OtsOrderProduct.findByOtsSuborderPickupDate", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsSuborderPickupDate = :otsSuborderPickupDate"),
	@NamedQuery(name = "OtsOrderProduct.findByOtsSuborderOfdDate", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsSuborderOfdDate = :otsSuborderOfdDate"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsSuborderDeliveredDt", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsSuborderDeliveredDt = :otsSuborderDeliveredDt"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsOrderProductCreated", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsOrderProductCreated = :otsOrderProductCreated"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsOrderProductUpdated", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsOrderProductUpdated = :otsOrderProductUpdated"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsRrcOrderStatus", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsRrcOrderStatus = :otsRrcOrderStatus"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsCustomerRrcInitiatedDate", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsCustomerRrcInitiatedDate = :otsCustomerRrcInitiatedDate"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsDistributorRrcInitiatedDate", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsDistributorRrcInitiatedDate = :otsDistributorRrcInitiatedDate"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsTrackingId", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsTrackingId = :otsTrackingId"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsTrackingUrl", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsTrackingUrl = :otsTrackingUrl"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsTrackingLogistics", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsTrackingLogistics = :otsTrackingLogistics"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductName", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductName = :otsProductName"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductCancellationAvailability", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductCancellationAvailability = :otsProductCancellationAvailability"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductReplacementAvailability", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductReplacementAvailability = :otsProductReplacementAvailability"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductReplacementDays", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductReplacementDays = :otsProductReplacementDays"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductReturnAvailability", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductReturnAvailability = :otsProductReturnAvailability"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductReturnDays", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductReturnDays = :otsProductReturnDays"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsSettlementStatus", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsSettlementStatus = :otsSettlementStatus"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsSettlementDate", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsSettlementDate = :otsSettlementDate"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductCountry", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductCountry = :otsProductCountry"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductCountryCode", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductCountryCode = :otsProductCountryCode"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductCurrency", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductCurrency = :otsProductCurrency"),
    @NamedQuery(name = "OtsOrderProduct.findByOtsProductCurrencySymbol", query = "SELECT o FROM OtsOrderProduct o WHERE o.otsProductCurrencySymbol = :otsProductCurrencySymbol"),})
public class OtsOrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_order_product_id")
    private Integer otsOrderProductId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ots_suborder_id")
    private String otsSuborderId;
    @Column(name = "ots_ordered_qty")
    private Integer otsOrderedQty;
    @Column(name = "ots_delivered_qty")
    private Integer otsDeliveredQty;
    @Column(name = "ots_order_product_status")
    private String otsOrderProductStatus;
	@Size(max = 45)
	@Column(name = "ots_ondc_fulfillment_id")
	private String otsOndcFulfillmentId;
	@Size(max = 45)
	@Column(name = "ots_ondc_fulfillment_status")
	private String otsOndcFulfillmentStatus;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ots_order_product_cost", nullable = false)
    private BigDecimal otsOrderProductCost = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "ots_product_price_without_gst", nullable = false)
    private BigDecimal otsProductPriceWithoutGst = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "ots_product_seller_price", nullable = false)
    private BigDecimal otsProductSellerPrice = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "ots_product_base_price", nullable = false)
    private BigDecimal otsProductBasePrice = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "ots_product_price", nullable = false)
    private BigDecimal otsProductPrice = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "ots_product_gst", nullable = false)
    private String otsProductGst = "0";
    @Basic(optional = false)
    @Column(name = "ots_product_gst_price" , nullable = false)
    private String otsProductGstPrice = "0";
    @Basic(optional = false)
    @Column(name = "ots_product_percentage", nullable = false)
    private String otsProductPercentage = "0";
    @Basic(optional = false)
    @Column(name = "ots_product_discount_price", nullable = false)
    private String otsProductDiscountPrice ="0";
    @Column(name = "ots_product_delivery_charge", nullable = false)
    private String otsProductDeliveryCharge = "0";
    @Column(name = "ots_product_return_delivery_charge", nullable = false)
    private Integer otsProductReturnDeliveryCharge = 0;
    @Column(name = "ots_suborder_assigned_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsSuborderAssignedDate;
	@Column(name = "ots_suborder_pickup_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date otsSuborderPickupDate;
	@Column(name = "ots_suborder_ofd_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date otsSuborderOfdDate;
    @Column(name = "ots_suborder_delivered_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsSuborderDeliveredDt;
    @Column(name = "ots_order_billOfSupply")
    private String otsorderbillOfSupply;
    @Basic(optional = false)
    @Column(name = "ots_order_product_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsOrderProductCreated;
    @Basic(optional = false)
    @Column(name = "ots_order_product_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsOrderProductUpdated;
    @Size(max = 45)
    @Column(name = "ots_rrc_order_status")
    private String otsRrcOrderStatus;
    @Column(name = "ots_customer_rrc_initiated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsCustomerRrcInitiatedDate;
    @Column(name = "ots_distributor_rrc_initiated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsDistributorRrcInitiatedDate;
    @Column(name = "ots_order_product_customer_invoice")
    private String otsOrderProductCustomerInvoice;
    @Column(name = "ots_tracking_id")
    private String otsTrackingId;
    @Column(name = "ots_tracking_url")
    private String otsTrackingUrl;
    @Column(name = "ots_tracking_logistics")
    private String otsTrackingLogistics;
    @Column(name = "ots_product_name")
    private String otsProductName;
    @Column(name = "ots_product_image")
    private String otsProductImage;
    @Column(name = "ots_product_cancellation_availability", nullable = false)
    private Boolean otsProductCancellationAvailability = false;
    @Column(name = "ots_product_replacement_availability", nullable = false)
    private Boolean otsProductReplacementAvailability = false;
    @Basic(optional = false)
    @Column(name = "ots_product_replacement_days", nullable = false)
    private String otsProductReplacementDays = "0";
    @Basic(optional = false)
    @Column(name = "ots_product_return_availability", nullable = false)
    private Boolean otsProductReturnAvailability = false;
    @Basic(optional = false)
    @Column(name = "ots_product_return_days", nullable = false)
    private String otsProductReturnDays ="0";
    @Column(name = "ots_settlement_status")
    private String otsSettlementStatus;
    @Column(name = "ots_settlement_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsSettlementDate;
    @Column(name = "ots_product_country")
    private String otsProductCountry;
    @Column(name = "ots_product_country_code")
    private String otsProductCountryCode;
    @Column(name = "ots_product_currency")
    private String otsProductCurrency;
    @Column(name = "ots_product_currency_symbol")
    private String otsProductCurrencySymbol;
    @JoinColumn(name = "ots_assigned_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsAssignedId;
    @JoinColumn(name = "created_user", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts createdUser;
    @JoinColumn(name = "ots_distributor_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsDistributorId;
    @JoinColumn(name = "ots_order_id", referencedColumnName = "ots_order_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private OtsOrder otsOrderId;
    @JoinColumn(name = "ots_product_id", referencedColumnName = "ots_product_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private OtsProduct otsProductId;

    public OtsOrderProduct() {
    }

    public OtsOrderProduct(Integer otsOrderProductId) {
        this.otsOrderProductId = otsOrderProductId;
    }

    public OtsOrderProduct(Integer otsOrderProductId, String otsSuborderId, BigDecimal otsProductPriceWithoutGst, BigDecimal otsProductSellerPrice, String otsProductGst, String otsProductGstPrice, String otsProductPercentage, String otsProductDiscountPrice, Date otsOrderProductCreated, Date otsOrderProductUpdated, String otsProductReplacementDays, boolean otsProductReturnAvailability, String otsProductReturnDays) {
        this.otsOrderProductId = otsOrderProductId;
        this.otsSuborderId = otsSuborderId;
        this.otsProductPriceWithoutGst = otsProductPriceWithoutGst;
        this.otsProductSellerPrice = otsProductSellerPrice;
        this.otsProductGst = otsProductGst;
        this.otsProductGstPrice = otsProductGstPrice;
        this.otsProductPercentage = otsProductPercentage;
        this.otsProductDiscountPrice = otsProductDiscountPrice;
        this.otsOrderProductCreated = otsOrderProductCreated;
        this.otsOrderProductUpdated = otsOrderProductUpdated;
        this.otsProductReplacementDays = otsProductReplacementDays;
        this.otsProductReturnAvailability = otsProductReturnAvailability;
        this.otsProductReturnDays = otsProductReturnDays;
    }

    public Integer getOtsOrderProductId() {
        return otsOrderProductId;
    }

    public void setOtsOrderProductId(Integer otsOrderProductId) {
        this.otsOrderProductId = otsOrderProductId;
    }

    public String getOtsSuborderId() {
        return otsSuborderId;
    }

    public void setOtsSuborderId(String otsSuborderId) {
        this.otsSuborderId = otsSuborderId;
    }

    public Integer getOtsOrderedQty() {
        return otsOrderedQty;
    }

    public void setOtsOrderedQty(Integer otsOrderedQty) {
        this.otsOrderedQty = otsOrderedQty;
    }

    public Integer getOtsDeliveredQty() {
        return otsDeliveredQty;
    }

    public void setOtsDeliveredQty(Integer otsDeliveredQty) {
        this.otsDeliveredQty = otsDeliveredQty;
    }

    public String getOtsOrderProductStatus() {
        return otsOrderProductStatus;
    }

    public void setOtsOrderProductStatus(String otsOrderProductStatus) {
        this.otsOrderProductStatus = otsOrderProductStatus;
    }

    public String getOtsOndcFulfillmentId() {
		return otsOndcFulfillmentId;
	}

	public void setOtsOndcFulfillmentId(String otsOndcFulfillmentId) {
		this.otsOndcFulfillmentId = otsOndcFulfillmentId;
	}

	public String getOtsOndcFulfillmentStatus() {
		return otsOndcFulfillmentStatus;
	}

	public void setOtsOndcFulfillmentStatus(String otsOndcFulfillmentStatus) {
		this.otsOndcFulfillmentStatus = otsOndcFulfillmentStatus;
	}

	public BigDecimal getOtsOrderProductCost() {
        return otsOrderProductCost;
    }

    public void setOtsOrderProductCost(BigDecimal otsOrderProductCost) {
        this.otsOrderProductCost = otsOrderProductCost;
    }

    public BigDecimal getOtsProductPriceWithoutGst() {
        return otsProductPriceWithoutGst;
    }

    public void setOtsProductPriceWithoutGst(BigDecimal otsProductPriceWithoutGst) {
        this.otsProductPriceWithoutGst = otsProductPriceWithoutGst;
    }

    public BigDecimal getOtsProductSellerPrice() {
        return otsProductSellerPrice;
    }

    public void setOtsProductSellerPrice(BigDecimal otsProductSellerPrice) {
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

    public String getOtsProductPercentage() {
		return otsProductPercentage;
	}

	public void setOtsProductPercentage(String otsProductPercentage) {
		this.otsProductPercentage = otsProductPercentage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOtsProductDiscountPrice() {
        return otsProductDiscountPrice;
    }

    public void setOtsProductDiscountPrice(String otsProductDiscountPrice) {
        this.otsProductDiscountPrice = otsProductDiscountPrice;
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

    public Date getOtsSuborderAssignedDate() {
        return otsSuborderAssignedDate;
    }

    public void setOtsSuborderAssignedDate(Date otsSuborderAssignedDate) {
        this.otsSuborderAssignedDate = otsSuborderAssignedDate;
    }

    public Date getOtsSuborderPickupDate() {
		return otsSuborderPickupDate;
	}

	public void setOtsSuborderPickupDate(Date otsSuborderPickupDate) {
		this.otsSuborderPickupDate = otsSuborderPickupDate;
	}

	public Date getOtsSuborderOfdDate() {
		return otsSuborderOfdDate;
	}

	public void setOtsSuborderOfdDate(Date otsSuborderOfdDate) {
		this.otsSuborderOfdDate = otsSuborderOfdDate;
	}

	public Date getOtsSuborderDeliveredDt() {
        return otsSuborderDeliveredDt;
    }

    public void setOtsSuborderDeliveredDt(Date otsSuborderDeliveredDt) {
        this.otsSuborderDeliveredDt = otsSuborderDeliveredDt;
    }

    public String getOtsorderbillOfSupply() {
        return otsorderbillOfSupply;
    }

    public void setOtsorderbillOfSupply(String otsorderbillOfSupply) {
        this.otsorderbillOfSupply = otsorderbillOfSupply;
    }

    public Date getOtsOrderProductCreated() {
        return otsOrderProductCreated;
    }

    public void setOtsOrderProductCreated(Date otsOrderProductCreated) {
        this.otsOrderProductCreated = otsOrderProductCreated;
    }

    public Date getOtsOrderProductUpdated() {
        return otsOrderProductUpdated;
    }

    public void setOtsOrderProductUpdated(Date otsOrderProductUpdated) {
        this.otsOrderProductUpdated = otsOrderProductUpdated;
    }

    public String getOtsRrcOrderStatus() {
        return otsRrcOrderStatus;
    }

    public void setOtsRrcOrderStatus(String otsRrcOrderStatus) {
        this.otsRrcOrderStatus = otsRrcOrderStatus;
    }

    public Date getOtsCustomerRrcInitiatedDate() {
        return otsCustomerRrcInitiatedDate;
    }

    public void setOtsCustomerRrcInitiatedDate(Date otsCustomerRrcInitiatedDate) {
        this.otsCustomerRrcInitiatedDate = otsCustomerRrcInitiatedDate;
    }

    public Date getOtsDistributorRrcInitiatedDate() {
        return otsDistributorRrcInitiatedDate;
    }

    public void setOtsDistributorRrcInitiatedDate(Date otsDistributorRrcInitiatedDate) {
        this.otsDistributorRrcInitiatedDate = otsDistributorRrcInitiatedDate;
    }

    public String getOtsOrderProductCustomerInvoice() {
        return otsOrderProductCustomerInvoice;
    }

    public void setOtsOrderProductCustomerInvoice(String otsOrderProductCustomerInvoice) {
        this.otsOrderProductCustomerInvoice = otsOrderProductCustomerInvoice;
    }

    public String getOtsTrackingId() {
        return otsTrackingId;
    }

    public void setOtsTrackingId(String otsTrackingId) {
        this.otsTrackingId = otsTrackingId;
    }

    public String getOtsTrackingUrl() {
        return otsTrackingUrl;
    }

    public void setOtsTrackingUrl(String otsTrackingUrl) {
        this.otsTrackingUrl = otsTrackingUrl;
    }

    public String getOtsTrackingLogistics() {
        return otsTrackingLogistics;
    }

    public void setOtsTrackingLogistics(String otsTrackingLogistics) {
        this.otsTrackingLogistics = otsTrackingLogistics;
    }

    public String getOtsProductName() {
        return otsProductName;
    }

    public void setOtsProductName(String otsProductName) {
        this.otsProductName = otsProductName;
    }

    public String getOtsProductImage() {
        return otsProductImage;
    }
    
    public void setOtsProductImage(String otsProductImage) {
        this.otsProductImage = otsProductImage;
    }

    public Boolean getOtsProductCancellationAvailability() {
        return otsProductCancellationAvailability;
    }

    public void setOtsProductCancellationAvailability(Boolean otsProductCancellationAvailability) {
        this.otsProductCancellationAvailability = otsProductCancellationAvailability;
    }

    public Boolean getOtsProductReplacementAvailability() {
        return otsProductReplacementAvailability;
    }

    public void setOtsProductReplacementAvailability(Boolean otsProductReplacementAvailability) {
        this.otsProductReplacementAvailability = otsProductReplacementAvailability;
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

    public String getOtsProductReturnDays() {
        return otsProductReturnDays;
    }

    public void setOtsProductReturnDays(String otsProductReturnDays) {
        this.otsProductReturnDays = otsProductReturnDays;
    }

    public String getOtsSettlementStatus() {
        return otsSettlementStatus;
    }

    public void setOtsSettlementStatus(String otsSettlementStatus) {
        this.otsSettlementStatus = otsSettlementStatus;
    }

    public Date getOtsSettlementDate() {
        return otsSettlementDate;
    }

    public void setOtsSettlementDate(Date otsSettlementDate) {
        this.otsSettlementDate = otsSettlementDate;
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

	public OtsUsers getOtsAssignedId() {
        return otsAssignedId;
    }

    public void setOtsAssignedId(OtsUsers otsAssignedId) {
        this.otsAssignedId = otsAssignedId;
    }

    public Useraccounts getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Useraccounts createdUser) {
        this.createdUser = createdUser;
    }

    public OtsUsers getOtsDistributorId() {
        return otsDistributorId;
    }

    public void setOtsDistributorId(OtsUsers otsDistributorId) {
        this.otsDistributorId = otsDistributorId;
    }

    public OtsOrder getOtsOrderId() {
        return otsOrderId;
    }

    public void setOtsOrderId(OtsOrder otsOrderId) {
        this.otsOrderId = otsOrderId;
    }

    public OtsProduct getOtsProductId() {
        return otsProductId;
    }

    public void setOtsProductId(OtsProduct otsProductId) {
        this.otsProductId = otsProductId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsOrderProductId != null ? otsOrderProductId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsOrderProduct)) {
            return false;
        }
        OtsOrderProduct other = (OtsOrderProduct) object;
        if ((this.otsOrderProductId == null && other.otsOrderProductId != null) || (this.otsOrderProductId != null && !this.otsOrderProductId.equals(other.otsOrderProductId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsOrderProduct[ otsOrderProductId=" + otsOrderProductId + " ]";
    }
    
}
