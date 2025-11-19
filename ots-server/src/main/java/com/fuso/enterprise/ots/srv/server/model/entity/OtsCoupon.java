/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "ots_coupon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsCoupon.findAll", query = "SELECT o FROM OtsCoupon o"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponId", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponId = :otsCouponId"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponCode", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponCode = :otsCouponCode"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponDescription", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponDescription = :otsCouponDescription"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponDetails", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponDetails = :otsCouponDetails"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponStartDate", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponStartDate = :otsCouponStartDate"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponEndDate", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponEndDate = :otsCouponEndDate"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponUnit", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponUnit = :otsCouponUnit"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponPercentage", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponPercentage = :otsCouponPercentage"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponMaxPrice", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponMaxPrice = :otsCouponMaxPrice"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponMinPurchasePrice", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponMinPurchasePrice = :otsCouponMinPurchasePrice"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponPrice", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponPrice = :otsCouponPrice"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponStatus", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponStatus = :otsCouponStatus"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponBasedOn", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponBasedOn = :otsCouponBasedOn"),
    @NamedQuery(name = "OtsCoupon.findByOtsCouponCreated", query = "SELECT o FROM OtsCoupon o WHERE o.otsCouponCreated = :otsCouponCreated")})
public class OtsCoupon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_coupon_id")
    private Integer otsCouponId;
    @Size(max = 45)
    @Column(name = "ots_coupon_code")
    private String otsCouponCode;
    @Size(max = 100)
    @Column(name = "ots_coupon_description")
    private String otsCouponDescription;
    @Size(max = 1000)
    @Column(name = "ots_coupon_details")
    private String otsCouponDetails;
    @Column(name = "ots_coupon_start_date")
    @Temporal(TemporalType.DATE)
    private Date otsCouponStartDate;
    @Column(name = "ots_coupon_end_date")
    @Temporal(TemporalType.DATE)
    private Date otsCouponEndDate;
    @Column(name = "ots_coupon_unit")
    private Integer otsCouponUnit;
    @Column(name = "ots_coupon_percentage")
    private Integer otsCouponPercentage;
    @Column(name = "ots_coupon_max_price")
    private Integer otsCouponMaxPrice;
    @Column(name = "ots_coupon_min_purchase_price")
    private Integer otsCouponMinPurchasePrice;
    @Column(name = "ots_coupon_price")
    private Integer otsCouponPrice;
    @Size(max = 45)
    @Column(name = "ots_coupon_status")
    private String otsCouponStatus;
    @Size(max = 45)
    @Column(name = "ots_coupon_based_on")
    private String otsCouponBasedOn;
    @Column(name = "ots_coupon_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsCouponCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsCouponId")
    private Collection<OtsCouponOrder> otsCouponOrderCollection;
    @JoinColumn(name = "ots_admin_id", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts otsAdminId;

    public OtsCoupon() {
    }

    public OtsCoupon(Integer otsCouponId) {
        this.otsCouponId = otsCouponId;
    }

    public Integer getOtsCouponId() {
        return otsCouponId;
    }

    public void setOtsCouponId(Integer otsCouponId) {
        this.otsCouponId = otsCouponId;
    }

    public String getOtsCouponCode() {
        return otsCouponCode;
    }

    public void setOtsCouponCode(String otsCouponCode) {
        this.otsCouponCode = otsCouponCode;
    }

    public String getOtsCouponDescription() {
        return otsCouponDescription;
    }

    public void setOtsCouponDescription(String otsCouponDescription) {
        this.otsCouponDescription = otsCouponDescription;
    }

    public String getOtsCouponDetails() {
        return otsCouponDetails;
    }

    public void setOtsCouponDetails(String otsCouponDetails) {
        this.otsCouponDetails = otsCouponDetails;
    }

    public Date getOtsCouponStartDate() {
        return otsCouponStartDate;
    }

    public void setOtsCouponStartDate(Date otsCouponStartDate) {
        this.otsCouponStartDate = otsCouponStartDate;
    }

    public Date getOtsCouponEndDate() {
        return otsCouponEndDate;
    }

    public void setOtsCouponEndDate(Date otsCouponEndDate) {
        this.otsCouponEndDate = otsCouponEndDate;
    }

    public Integer getOtsCouponUnit() {
        return otsCouponUnit;
    }

    public void setOtsCouponUnit(Integer otsCouponUnit) {
        this.otsCouponUnit = otsCouponUnit;
    }

    public Integer getOtsCouponPercentage() {
        return otsCouponPercentage;
    }

    public void setOtsCouponPercentage(Integer otsCouponPercentage) {
        this.otsCouponPercentage = otsCouponPercentage;
    }

    public Integer getOtsCouponMaxPrice() {
        return otsCouponMaxPrice;
    }

    public void setOtsCouponMaxPrice(Integer otsCouponMaxPrice) {
        this.otsCouponMaxPrice = otsCouponMaxPrice;
    }

    public Integer getOtsCouponMinPurchasePrice() {
        return otsCouponMinPurchasePrice;
    }

    public void setOtsCouponMinPurchasePrice(Integer otsCouponMinPurchasePrice) {
        this.otsCouponMinPurchasePrice = otsCouponMinPurchasePrice;
    }

    public Integer getOtsCouponPrice() {
        return otsCouponPrice;
    }

    public void setOtsCouponPrice(Integer otsCouponPrice) {
        this.otsCouponPrice = otsCouponPrice;
    }

    public String getOtsCouponStatus() {
        return otsCouponStatus;
    }

    public void setOtsCouponStatus(String otsCouponStatus) {
        this.otsCouponStatus = otsCouponStatus;
    }

    public String getOtsCouponBasedOn() {
        return otsCouponBasedOn;
    }

    public void setOtsCouponBasedOn(String otsCouponBasedOn) {
        this.otsCouponBasedOn = otsCouponBasedOn;
    }

    public Date getOtsCouponCreated() {
        return otsCouponCreated;
    }

    public void setOtsCouponCreated(Date otsCouponCreated) {
        this.otsCouponCreated = otsCouponCreated;
    }

    @XmlTransient
    public Collection<OtsCouponOrder> getOtsCouponOrderCollection() {
        return otsCouponOrderCollection;
    }

    public void setOtsCouponOrderCollection(Collection<OtsCouponOrder> otsCouponOrderCollection) {
        this.otsCouponOrderCollection = otsCouponOrderCollection;
    }

    public Useraccounts getOtsAdminId() {
        return otsAdminId;
    }

    public void setOtsAdminId(Useraccounts otsAdminId) {
        this.otsAdminId = otsAdminId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsCouponId != null ? otsCouponId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsCoupon)) {
            return false;
        }
        OtsCoupon other = (OtsCoupon) object;
        if ((this.otsCouponId == null && other.otsCouponId != null) || (this.otsCouponId != null && !this.otsCouponId.equals(other.otsCouponId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsCoupon[ otsCouponId=" + otsCouponId + " ]";
    }
    
}
