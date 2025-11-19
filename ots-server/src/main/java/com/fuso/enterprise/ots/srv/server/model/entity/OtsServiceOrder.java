/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bharath
 */
@Entity
@Table(name = "ots_service_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServiceOrder.findAll", query = "SELECT o FROM OtsServiceOrder o"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceOrderId", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceOrderId = :otsServiceOrderId"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceOrderNumber", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceOrderNumber = :otsServiceOrderNumber"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCustomerName", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCustomerName = :otsServiceCustomerName"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCustomerContactNo", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCustomerContactNo = :otsServiceCustomerContactNo"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCustomerHouseNo", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCustomerHouseNo = :otsServiceCustomerHouseNo"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCustomerCity", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCustomerCity = :otsServiceCustomerCity"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCustomerPincode", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCustomerPincode = :otsServiceCustomerPincode"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCustomerDistrict", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCustomerDistrict = :otsServiceCustomerDistrict"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCustomerState", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCustomerState = :otsServiceCustomerState"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCustomerChangeAddressId", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCustomerChangeAddressId = :otsServiceCustomerChangeAddressId"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCompanyEmail", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCompanyEmail = :otsServiceCompanyEmail"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCompanyName", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCompanyName = :otsServiceCompanyName"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCompanyDistrict", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCompanyDistrict = :otsServiceCompanyDistrict"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCompanyState", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCompanyState = :otsServiceCompanyState"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCompanyPincode", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCompanyPincode = :otsServiceCompanyPincode"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceCompanyContactNo", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceCompanyContactNo = :otsServiceCompanyContactNo"),
    @NamedQuery(name = "OtsServiceOrder.findByOtsServiceName", query = "SELECT o FROM OtsServiceOrder o WHERE o.otsServiceName = :otsServiceName")})
public class OtsServiceOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ots_service_order_id")
    private UUID otsServiceOrderId;
    @Column(name = "ots_service_order_number")
    private String otsServiceOrderNumber;
    @Column(name = "ots_service_customer_name")
    private String otsServiceCustomerName;
    @Column(name = "ots_service_customer_contact_no")
    private String otsServiceCustomerContactNo;
    @Column(name = "ots_service_customer_house_no")
    private String otsServiceCustomerHouseNo;
    @Column(name = "ots_service_customer_building_name")
    private String otsServiceCustomerBuildingName;
    @Column(name = "ots_service_customer_street")
    private String otsServiceCustomerStreet;
    @Column(name = "ots_service_customer_city")
    private String otsServiceCustomerCity;
    @Column(name = "ots_service_customer_pincode")
    private String otsServiceCustomerPincode;
    @Column(name = "ots_service_customer_district")
    private String otsServiceCustomerDistrict;
    @Column(name = "ots_service_customer_state")
    private String otsServiceCustomerState;
    @Column(name = "ots_service_customer_change_address_id")
    private String otsServiceCustomerChangeAddressId;
    @Column(name = "ots_service_company_email")
    private String otsServiceCompanyEmail;
    @Column(name = "ots_service_company_name")
    private String otsServiceCompanyName;
    @Column(name = "ots_service_company_address")
    private String otsServiceCompanyAddress;
    @Column(name = "ots_service_company_district")
    private String otsServiceCompanyDistrict;
    @Column(name = "ots_service_company_state")
    private String otsServiceCompanyState;
    @Column(name = "ots_service_company_pincode")
    private String otsServiceCompanyPincode;
    @Column(name = "ots_service_company_contact_no")
    private String otsServiceCompanyContactNo;
    @Column(name = "ots_service_name")
    private String otsServiceName;
    @Column(name = "ots_service_image")
    private String otsServiceImage;
    @Column(name = "ots_service_description")
    private String otsServiceDescription;
    @Column(name = "ots_service_description_long")
    private String otsServiceDescriptionLong;
    @Column(name = "ots_service_order_customer_invoice")
    private String otsServiceOrderCustomerInvoice;
    @JoinColumn(name = "ots_service_customer_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsServiceCustomerId;
    @OneToMany(mappedBy = "otsServiceOrderId")
    private Collection<OtsServiceRatingReview> otsServiceRatingReviewCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsServiceOrderId")
    private Collection<OtsServiceOrderDetails> otsServiceOrderDetailsCollection;

    public OtsServiceOrder() {
    }

    public OtsServiceOrder(UUID otsServiceOrderId) {
        this.otsServiceOrderId = otsServiceOrderId;
    }

    public UUID getOtsServiceOrderId() {
        return otsServiceOrderId;
    }

    public void setOtsServiceOrderId(UUID otsServiceOrderId) {
        this.otsServiceOrderId = otsServiceOrderId;
    }

    public String getOtsServiceOrderNumber() {
        return otsServiceOrderNumber;
    }

    public void setOtsServiceOrderNumber(String otsServiceOrderNumber) {
        this.otsServiceOrderNumber = otsServiceOrderNumber;
    }

    public String getOtsServiceCustomerName() {
        return otsServiceCustomerName;
    }

    public void setOtsServiceCustomerName(String otsServiceCustomerName) {
        this.otsServiceCustomerName = otsServiceCustomerName;
    }

    public String getOtsServiceCustomerContactNo() {
        return otsServiceCustomerContactNo;
    }

    public void setOtsServiceCustomerContactNo(String otsServiceCustomerContactNo) {
        this.otsServiceCustomerContactNo = otsServiceCustomerContactNo;
    }

    public String getOtsServiceCustomerHouseNo() {
        return otsServiceCustomerHouseNo;
    }

    public void setOtsServiceCustomerHouseNo(String otsServiceCustomerHouseNo) {
        this.otsServiceCustomerHouseNo = otsServiceCustomerHouseNo;
    }

    public String getOtsServiceCustomerBuildingName() {
        return otsServiceCustomerBuildingName;
    }

    public void setOtsServiceCustomerBuildingName(String otsServiceCustomerBuildingName) {
        this.otsServiceCustomerBuildingName = otsServiceCustomerBuildingName;
    }

    public String getOtsServiceCustomerStreet() {
        return otsServiceCustomerStreet;
    }

    public void setOtsServiceCustomerStreet(String otsServiceCustomerStreet) {
        this.otsServiceCustomerStreet = otsServiceCustomerStreet;
    }

    public String getOtsServiceCustomerCity() {
        return otsServiceCustomerCity;
    }

    public void setOtsServiceCustomerCity(String otsServiceCustomerCity) {
        this.otsServiceCustomerCity = otsServiceCustomerCity;
    }

    public String getOtsServiceCustomerPincode() {
        return otsServiceCustomerPincode;
    }

    public void setOtsServiceCustomerPincode(String otsServiceCustomerPincode) {
        this.otsServiceCustomerPincode = otsServiceCustomerPincode;
    }

    public String getOtsServiceCustomerDistrict() {
        return otsServiceCustomerDistrict;
    }

    public void setOtsServiceCustomerDistrict(String otsServiceCustomerDistrict) {
        this.otsServiceCustomerDistrict = otsServiceCustomerDistrict;
    }

    public String getOtsServiceCustomerState() {
        return otsServiceCustomerState;
    }

    public void setOtsServiceCustomerState(String otsServiceCustomerState) {
        this.otsServiceCustomerState = otsServiceCustomerState;
    }

    public String getOtsServiceCustomerChangeAddressId() {
        return otsServiceCustomerChangeAddressId;
    }

    public void setOtsServiceCustomerChangeAddressId(String otsServiceCustomerChangeAddressId) {
        this.otsServiceCustomerChangeAddressId = otsServiceCustomerChangeAddressId;
    }

    public String getOtsServiceCompanyEmail() {
        return otsServiceCompanyEmail;
    }

    public void setOtsServiceCompanyEmail(String otsServiceCompanyEmail) {
        this.otsServiceCompanyEmail = otsServiceCompanyEmail;
    }

    public String getOtsServiceCompanyName() {
        return otsServiceCompanyName;
    }

    public void setOtsServiceCompanyName(String otsServiceCompanyName) {
        this.otsServiceCompanyName = otsServiceCompanyName;
    }

    public String getOtsServiceCompanyAddress() {
        return otsServiceCompanyAddress;
    }

    public void setOtsServiceCompanyAddress(String otsServiceCompanyAddress) {
        this.otsServiceCompanyAddress = otsServiceCompanyAddress;
    }

    public String getOtsServiceCompanyDistrict() {
        return otsServiceCompanyDistrict;
    }

    public void setOtsServiceCompanyDistrict(String otsServiceCompanyDistrict) {
        this.otsServiceCompanyDistrict = otsServiceCompanyDistrict;
    }

    public String getOtsServiceCompanyState() {
        return otsServiceCompanyState;
    }

    public void setOtsServiceCompanyState(String otsServiceCompanyState) {
        this.otsServiceCompanyState = otsServiceCompanyState;
    }

    public String getOtsServiceCompanyPincode() {
        return otsServiceCompanyPincode;
    }

    public void setOtsServiceCompanyPincode(String otsServiceCompanyPincode) {
        this.otsServiceCompanyPincode = otsServiceCompanyPincode;
    }

    public String getOtsServiceCompanyContactNo() {
        return otsServiceCompanyContactNo;
    }

    public void setOtsServiceCompanyContactNo(String otsServiceCompanyContactNo) {
        this.otsServiceCompanyContactNo = otsServiceCompanyContactNo;
    }

    public String getOtsServiceName() {
        return otsServiceName;
    }

    public void setOtsServiceName(String otsServiceName) {
        this.otsServiceName = otsServiceName;
    }

    public String getOtsServiceImage() {
        return otsServiceImage;
    }

    public void setOtsServiceImage(String otsServiceImage) {
        this.otsServiceImage = otsServiceImage;
    }

    public String getOtsServiceDescription() {
        return otsServiceDescription;
    }

    public void setOtsServiceDescription(String otsServiceDescription) {
        this.otsServiceDescription = otsServiceDescription;
    }

    public String getOtsServiceDescriptionLong() {
        return otsServiceDescriptionLong;
    }

    public void setOtsServiceDescriptionLong(String otsServiceDescriptionLong) {
        this.otsServiceDescriptionLong = otsServiceDescriptionLong;
    }

    public String getOtsServiceOrderCustomerInvoice() {
        return otsServiceOrderCustomerInvoice;
    }

    public void setOtsServiceOrderCustomerInvoice(String otsServiceOrderCustomerInvoice) {
        this.otsServiceOrderCustomerInvoice = otsServiceOrderCustomerInvoice;
    }

    public OtsUsers getOtsServiceCustomerId() {
        return otsServiceCustomerId;
    }

    public void setOtsServiceCustomerId(OtsUsers otsServiceCustomerId) {
        this.otsServiceCustomerId = otsServiceCustomerId;
    }
    
    @XmlTransient
    public Collection<OtsServiceRatingReview> getOtsServiceRatingReviewCollection() {
        return otsServiceRatingReviewCollection;
    }

    public void setOtsServiceRatingReviewCollection(Collection<OtsServiceRatingReview> otsServiceRatingReviewCollection) {
        this.otsServiceRatingReviewCollection = otsServiceRatingReviewCollection;
    }

    @XmlTransient
    public Collection<OtsServiceOrderDetails> getOtsServiceOrderDetailsCollection() {
        return otsServiceOrderDetailsCollection;
    }

    public void setOtsServiceOrderDetailsCollection(Collection<OtsServiceOrderDetails> otsServiceOrderDetailsCollection) {
        this.otsServiceOrderDetailsCollection = otsServiceOrderDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsServiceOrderId != null ? otsServiceOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServiceOrder)) {
            return false;
        }
        OtsServiceOrder other = (OtsServiceOrder) object;
        if ((this.otsServiceOrderId == null && other.otsServiceOrderId != null) || (this.otsServiceOrderId != null && !this.otsServiceOrderId.equals(other.otsServiceOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceOrder[ otsServiceOrderId=" + otsServiceOrderId + " ]";
    }
    
}
