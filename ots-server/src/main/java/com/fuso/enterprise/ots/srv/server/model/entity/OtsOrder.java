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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsOrder.findAll", query = "SELECT o FROM OtsOrder o"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderId", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderId = :otsOrderId"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderNumber", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderNumber = :otsOrderNumber"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderTransactionId", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderTransactionId = :otsOrderTransactionId"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderCost", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderCost = :otsOrderCost"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderStatus", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderStatus = :otsOrderStatus"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderDt", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderDt = :otsOrderDt"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderDeliveredDt", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderDeliveredDt = :otsOrderDeliveredDt"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderTimestamp", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderTimestamp = :otsOrderTimestamp"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderCreated", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderCreated = :otsOrderCreated"),
    @NamedQuery(name = "OtsOrder.findByOtsCustomerName", query = "SELECT o FROM OtsOrder o WHERE o.otsCustomerName = :otsCustomerName"),
    @NamedQuery(name = "OtsOrder.findByOtsCustomerContactNo", query = "SELECT o FROM OtsOrder o WHERE o.otsCustomerContactNo = :otsCustomerContactNo"),
    @NamedQuery(name = "OtsOrder.findByOtsHouseNo", query = "SELECT o FROM OtsOrder o WHERE o.otsHouseNo = :otsHouseNo"),
    @NamedQuery(name = "OtsOrder.findByOtsBuildingName", query = "SELECT o FROM OtsOrder o WHERE o.otsBuildingName = :otsBuildingName"),
    @NamedQuery(name = "OtsOrder.findByOtsStreetName", query = "SELECT o FROM OtsOrder o WHERE o.otsStreetName = :otsStreetName"),
    @NamedQuery(name = "OtsOrder.findByOtsCityName", query = "SELECT o FROM OtsOrder o WHERE o.otsCityName = :otsCityName"),
    @NamedQuery(name = "OtsOrder.findByOtsPincode", query = "SELECT o FROM OtsOrder o WHERE o.otsPincode = :otsPincode"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderDistrict", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderDistrict = :otsOrderDistrict"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderState", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderState = :otsOrderState"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderGps", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderGps = :otsOrderGps"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderPaymentId", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderPaymentId = :otsOrderPaymentId"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderPaymentStatus", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderPaymentStatus = :otsOrderPaymentStatus"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderPaymentDate", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderPaymentDate = :otsOrderPaymentDate"),
    @NamedQuery(name = "OtsOrder.findByOtsOrderDeliveryCharge", query = "SELECT o FROM OtsOrder o WHERE o.otsOrderDeliveryCharge = :otsOrderDeliveryCharge"),
    @NamedQuery(name = "OtsOrder.findByOtsBidTransactionId", query = "SELECT o FROM OtsOrder o WHERE o.otsBidTransactionId = :otsBidTransactionId"),
    @NamedQuery(name = "OtsOrder.findByOtsCustomerChangeAddressId", query = "SELECT o FROM OtsOrder o WHERE o.otsCustomerChangeAddressId = :otsCustomerChangeAddressId")})
public class OtsOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ots_order_id")
    private UUID otsOrderId;
    @Size(max = 45)
    @Column(name = "ots_order_number")
    private String otsOrderNumber;
    @Size(max = 45)
    @Column(name = "ots_order_transaction_id")
    private String otsOrderTransactionId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ots_order_cost")
    private BigDecimal otsOrderCost;
    @Size(max = 45)
    @Column(name = "ots_order_status")
    private String otsOrderStatus;
    @Column(name = "ots_order_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsOrderDt;
    @Column(name = "ots_order_delivered_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsOrderDeliveredDt;
    @Column(name = "ots_order_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsOrderTimestamp;
    @Column(name = "ots_order_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsOrderCreated;
    @Size(max = 45)
    @Column(name = "ots_customer_name")
    private String otsCustomerName;
    @Size(max = 45)
    @Column(name = "ots_customer_contact_no")
    private String otsCustomerContactNo;
    @Size(max = 45)
    @Column(name = "ots_house_no")
    private String otsHouseNo;
    @Size(max = 255)
    @Column(name = "ots_building_name")
    private String otsBuildingName;
    @Size(max = 255)
    @Column(name = "ots_street_name")
    private String otsStreetName;
    @Size(max = 45)
    @Column(name = "ots_city_name")
    private String otsCityName;
    @Size(max = 10)
    @Column(name = "ots_pincode")
    private String otsPincode;
    @Size(max = 45)
    @Column(name = "ots_order_district")
    private String otsOrderDistrict;
    @Size(max = 45)
    @Column(name = "ots_order_state")
    private String otsOrderState;
    @Size(max = 45)
    @Column(name = "ots_order_gps")
    private String otsOrderGps;
    @Size(max = 45)
    @Column(name = "ots_order_payment_id")
    private String otsOrderPaymentId;
    @Size(max = 45)
    @Column(name = "ots_order_payment_status")
    private String otsOrderPaymentStatus;
    @Column(name = "ots_order_payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsOrderPaymentDate;
    @Size(max = 45)
    @Column(name = "ots_order_delivery_charge")
    private String otsOrderDeliveryCharge;
    @Size(max = 45)
    @Column(name = "ots_bid_transaction_id")
    private String otsBidTransactionId;
    @Basic(optional = false)
    @Size(max = 45)
    @Column(name = "ots_customer_change_address_id")
    private String otsCustomerChangeAddressId;
    @Column(name = "ots_order_proforma_invoice")
    private String otsOrderProformaInvoice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsOrderId")
    private Collection<OtsCouponOrder> otsCouponOrderCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsOrderId")
    private Collection<OtsRatingReview> otsRatingReviewCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsOrderId")
    private Collection<OtsOrderProduct> otsOrderProductCollection;
    @JoinColumn(name = "ots_customer_id", referencedColumnName = "ots_users_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private OtsUsers otsCustomerId;
    @JoinColumn(name = "ots_order_created_by", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsOrderCreatedBy;

    public OtsOrder() {
    }

    public OtsOrder(UUID otsOrderId) {
        this.otsOrderId = otsOrderId;
    }

    public OtsOrder(UUID otsOrderId, String otsCustomerChangeAddressId) {
        this.otsOrderId = otsOrderId;
        this.otsCustomerChangeAddressId = otsCustomerChangeAddressId;
    }

    public UUID getOtsOrderId() {
        return otsOrderId;
    }

    public void setOtsOrderId(UUID otsOrderId) {
        this.otsOrderId = otsOrderId;
    }

    public String getOtsOrderNumber() {
        return otsOrderNumber;
    }

    public void setOtsOrderNumber(String otsOrderNumber) {
        this.otsOrderNumber = otsOrderNumber;
    }

    public String getOtsOrderTransactionId() {
        return otsOrderTransactionId;
    }

    public void setOtsOrderTransactionId(String otsOrderTransactionId) {
        this.otsOrderTransactionId = otsOrderTransactionId;
    }

    public BigDecimal getOtsOrderCost() {
        return otsOrderCost;
    }

    public void setOtsOrderCost(BigDecimal otsOrderCost) {
        this.otsOrderCost = otsOrderCost;
    }

    public String getOtsOrderStatus() {
        return otsOrderStatus;
    }

    public void setOtsOrderStatus(String otsOrderStatus) {
        this.otsOrderStatus = otsOrderStatus;
    }

    public Date getOtsOrderDt() {
        return otsOrderDt;
    }

    public void setOtsOrderDt(Date otsOrderDt) {
        this.otsOrderDt = otsOrderDt;
    }

    public Date getOtsOrderDeliveredDt() {
        return otsOrderDeliveredDt;
    }

    public void setOtsOrderDeliveredDt(Date otsOrderDeliveredDt) {
        this.otsOrderDeliveredDt = otsOrderDeliveredDt;
    }

    public Date getOtsOrderTimestamp() {
        return otsOrderTimestamp;
    }

    public void setOtsOrderTimestamp(Date otsOrderTimestamp) {
        this.otsOrderTimestamp = otsOrderTimestamp;
    }

    public Date getOtsOrderCreated() {
        return otsOrderCreated;
    }

    public void setOtsOrderCreated(Date otsOrderCreated) {
        this.otsOrderCreated = otsOrderCreated;
    }

    public String getOtsCustomerName() {
		return otsCustomerName;
	}

	public void setOtsCustomerName(String otsCustomerName) {
		this.otsCustomerName = otsCustomerName;
	}

	public String getOtsCustomerContactNo() {
        return otsCustomerContactNo;
    }

    public void setOtsCustomerContactNo(String otsCustomerContactNo) {
        this.otsCustomerContactNo = otsCustomerContactNo;
    }

    public String getOtsHouseNo() {
        return otsHouseNo;
    }

    public void setOtsHouseNo(String otsHouseNo) {
        this.otsHouseNo = otsHouseNo;
    }

    public String getOtsBuildingName() {
        return otsBuildingName;
    }

    public void setOtsBuildingName(String otsBuildingName) {
        this.otsBuildingName = otsBuildingName;
    }

    public String getOtsStreetName() {
        return otsStreetName;
    }

    public void setOtsStreetName(String otsStreetName) {
        this.otsStreetName = otsStreetName;
    }

    public String getOtsCityName() {
        return otsCityName;
    }

    public void setOtsCityName(String otsCityName) {
        this.otsCityName = otsCityName;
    }

    public String getOtsPincode() {
        return otsPincode;
    }

    public void setOtsPincode(String otsPincode) {
        this.otsPincode = otsPincode;
    }

    public String getOtsOrderDistrict() {
        return otsOrderDistrict;
    }

    public void setOtsOrderDistrict(String otsOrderDistrict) {
        this.otsOrderDistrict = otsOrderDistrict;
    }

    public String getOtsOrderState() {
        return otsOrderState;
    }

    public void setOtsOrderState(String otsOrderState) {
        this.otsOrderState = otsOrderState;
    }

    public String getOtsOrderGps() {
        return otsOrderGps;
    }

    public void setOtsOrderGps(String otsOrderGps) {
        this.otsOrderGps = otsOrderGps;
    }

    public String getOtsOrderPaymentId() {
        return otsOrderPaymentId;
    }

    public void setOtsOrderPaymentId(String otsOrderPaymentId) {
        this.otsOrderPaymentId = otsOrderPaymentId;
    }

    public String getOtsOrderPaymentStatus() {
        return otsOrderPaymentStatus;
    }

    public void setOtsOrderPaymentStatus(String otsOrderPaymentStatus) {
        this.otsOrderPaymentStatus = otsOrderPaymentStatus;
    }

    public Date getOtsOrderPaymentDate() {
        return otsOrderPaymentDate;
    }

    public void setOtsOrderPaymentDate(Date otsOrderPaymentDate) {
        this.otsOrderPaymentDate = otsOrderPaymentDate;
    }

    public String getOtsOrderDeliveryCharge() {
        return otsOrderDeliveryCharge;
    }

    public void setOtsOrderDeliveryCharge(String otsOrderDeliveryCharge) {
        this.otsOrderDeliveryCharge = otsOrderDeliveryCharge;
    }
    
    public String getOtsBidTransactionId() {
        return otsBidTransactionId;
    }

    public void setOtsBidTransactionId(String otsBidTransactionId) {
        this.otsBidTransactionId = otsBidTransactionId;
    }

    public String getOtsCustomerChangeAddressId() {
        return otsCustomerChangeAddressId;
    }

    public void setOtsCustomerChangeAddressId(String otsCustomerChangeAddressId) {
        this.otsCustomerChangeAddressId = otsCustomerChangeAddressId;
    }

    public String getOtsOrderProformaInvoice() {
		return otsOrderProformaInvoice;
	}

	public void setOtsOrderProformaInvoice(String otsOrderProformaInvoice) {
		this.otsOrderProformaInvoice = otsOrderProformaInvoice;
	}

	@XmlTransient
    public Collection<OtsCouponOrder> getOtsCouponOrderCollection() {
        return otsCouponOrderCollection;
    }

    public void setOtsCouponOrderCollection(Collection<OtsCouponOrder> otsCouponOrderCollection) {
        this.otsCouponOrderCollection = otsCouponOrderCollection;
    }

    @XmlTransient
    public Collection<OtsRatingReview> getOtsRatingReviewCollection() {
        return otsRatingReviewCollection;
    }

    public void setOtsRatingReviewCollection(Collection<OtsRatingReview> otsRatingReviewCollection) {
        this.otsRatingReviewCollection = otsRatingReviewCollection;
    }

    @XmlTransient
    public Collection<OtsOrderProduct> getOtsOrderProductCollection() {
        return otsOrderProductCollection;
    }

    public void setOtsOrderProductCollection(Collection<OtsOrderProduct> otsOrderProductCollection) {
        this.otsOrderProductCollection = otsOrderProductCollection;
    }

    public OtsUsers getOtsCustomerId() {
        return otsCustomerId;
    }

    public void setOtsCustomerId(OtsUsers otsCustomerId) {
        this.otsCustomerId = otsCustomerId;
    }

//    public OtsBill getOtsBillId() {
//        return otsBillId;
//    }
//
//    public void setOtsBillId(OtsBill otsBillId) {
//        this.otsBillId = otsBillId;
//    }

    public OtsUsers getOtsOrderCreatedBy() {
        return otsOrderCreatedBy;
    }

    public void setOtsOrderCreatedBy(OtsUsers otsOrderCreatedBy) {
        this.otsOrderCreatedBy = otsOrderCreatedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsOrderId != null ? otsOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsOrder)) {
            return false;
        }
        OtsOrder other = (OtsOrder) object;
        if ((this.otsOrderId == null && other.otsOrderId != null) || (this.otsOrderId != null && !this.otsOrderId.equals(other.otsOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsOrder[ otsOrderId=" + otsOrderId + " ]";
    }
    
}
