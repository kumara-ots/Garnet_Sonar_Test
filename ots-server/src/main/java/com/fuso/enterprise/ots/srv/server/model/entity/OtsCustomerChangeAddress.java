/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "ots_customer_change_address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsCustomerChangeAddress.findAll", query = "SELECT o FROM OtsCustomerChangeAddress o"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsCustomerChangeAddressId", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsCustomerChangeAddressId = :otsCustomerChangeAddressId"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsCustomerFirstName", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsCustomerFirstName = :otsCustomerFirstName"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsCustomerSecondName", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsCustomerSecondName = :otsCustomerSecondName"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsCustomerContactNo", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsCustomerContactNo = :otsCustomerContactNo"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsHouseNo", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsHouseNo = :otsHouseNo"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsBuildingName", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsBuildingName = :otsBuildingName"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsStreetName", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsStreetName = :otsStreetName"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsCityName", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsCityName = :otsCityName"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsPincode", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsPincode = :otsPincode"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsDistrictName", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsDistrictName = :otsDistrictName"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsStateName", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsStateName = :otsStateName"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsCountryName", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsCountryName = :otsCountryName"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsCustomerAccountStatus", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsCustomerAccountStatus = :otsCustomerAccountStatus"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsCreatedDate", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsCreatedDate = :otsCreatedDate"),
    @NamedQuery(name = "OtsCustomerChangeAddress.findByOtsUpdatedDate", query = "SELECT o FROM OtsCustomerChangeAddress o WHERE o.otsUpdatedDate = :otsUpdatedDate")})
public class OtsCustomerChangeAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ots_customer_change_address_id")
    private UUID otsCustomerChangeAddressId;
    @Size(max = 45)
    @Column(name = "ots_customer_first_name")
    private String otsCustomerFirstName;
    @Size(max = 45)
    @Column(name = "ots_customer_second_name")
    private String otsCustomerSecondName;
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
    @Size(max = 45)
    @Column(name = "ots_pincode")
    private String otsPincode;
    @Size(max = 45)
    @Column(name = "ots_district_name")
    private String otsDistrictName;
    @Size(max = 45)
    @Column(name = "ots_state_name")
    private String otsStateName;
    @Size(max = 45)
    @Column(name = "ots_country_name")
    private String otsCountryName;
    @Size(max = 45)
    @Column(name = "ots_customer_account_status")
    private String otsCustomerAccountStatus;
    @Column(name = "ots_created_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsCreatedDate;
    @Column(name = "ots_updated_date", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsUpdatedDate;
    @JoinColumn(name = "ots_customer_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsCustomerId;

    public OtsCustomerChangeAddress() {
    }

    public OtsCustomerChangeAddress(UUID otsCustomerChangeAddressId) {
        this.otsCustomerChangeAddressId = otsCustomerChangeAddressId;
    }

    public OtsCustomerChangeAddress(UUID otsCustomerChangeAddressId, Date otsCreatedDate) {
        this.otsCustomerChangeAddressId = otsCustomerChangeAddressId;
        this.otsCreatedDate = otsCreatedDate;
    }

    public UUID getOtsCustomerChangeAddressId() {
        return otsCustomerChangeAddressId;
    }

    public void setOtsCustomerChangeAddressId(UUID otsCustomerChangeAddressId) {
        this.otsCustomerChangeAddressId = otsCustomerChangeAddressId;
    }

    public String getOtsCustomerFirstName() {
        return otsCustomerFirstName;
    }

    public void setOtsCustomerFirstName(String otsCustomerFirstName) {
        this.otsCustomerFirstName = otsCustomerFirstName;
    }

    public String getOtsCustomerSecondName() {
        return otsCustomerSecondName;
    }

    public void setOtsCustomerSecondName(String otsCustomerSecondName) {
        this.otsCustomerSecondName = otsCustomerSecondName;
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

    public String getOtsDistrictName() {
        return otsDistrictName;
    }

    public void setOtsDistrictName(String otsDistrictName) {
        this.otsDistrictName = otsDistrictName;
    }

    public String getOtsStateName() {
        return otsStateName;
    }

    public void setOtsStateName(String otsStateName) {
        this.otsStateName = otsStateName;
    }

    public String getOtsCountryName() {
		return otsCountryName;
	}

	public void setOtsCountryName(String otsCountryName) {
		this.otsCountryName = otsCountryName;
	}

	public String getOtsCustomerAccountStatus() {
        return otsCustomerAccountStatus;
    }

    public void setOtsCustomerAccountStatus(String otsCustomerAccountStatus) {
        this.otsCustomerAccountStatus = otsCustomerAccountStatus;
    }

    public Date getOtsCreatedDate() {
        return otsCreatedDate;
    }

    public void setOtsCreatedDate(Date otsCreatedDate) {
        this.otsCreatedDate = otsCreatedDate;
    }

    public Date getOtsUpdatedDate() {
        return otsUpdatedDate;
    }

    public void setOtsUpdatedDate(Date otsUpdatedDate) {
        this.otsUpdatedDate = otsUpdatedDate;
    }

    public OtsUsers getOtsCustomerId() {
        return otsCustomerId;
    }

    public void setOtsCustomerId(OtsUsers otsCustomerId) {
        this.otsCustomerId = otsCustomerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsCustomerChangeAddressId != null ? otsCustomerChangeAddressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsCustomerChangeAddress)) {
            return false;
        }
        OtsCustomerChangeAddress other = (OtsCustomerChangeAddress) object;
        if ((this.otsCustomerChangeAddressId == null && other.otsCustomerChangeAddressId != null) || (this.otsCustomerChangeAddressId != null && !this.otsCustomerChangeAddressId.equals(other.otsCustomerChangeAddressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsCustomerChangeAddress[ otsCustomerChangeAddressId=" + otsCustomerChangeAddressId + " ]";
    }
    
}
