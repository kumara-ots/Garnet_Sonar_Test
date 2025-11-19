/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_service_pincode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServicePincode.findAll", query = "SELECT o FROM OtsServicePincode o"),
    @NamedQuery(name = "OtsServicePincode.findByOtsPincodeId", query = "SELECT o FROM OtsServicePincode o WHERE o.otsPincodeId = :otsPincodeId"),
    @NamedQuery(name = "OtsServicePincode.findByOtsCountryCode", query = "SELECT o FROM OtsServicePincode o WHERE o.otsCountryCode = :otsCountryCode"),
    @NamedQuery(name = "OtsServicePincode.findByOtsPincode", query = "SELECT o FROM OtsServicePincode o WHERE o.otsPincode = :otsPincode"),
    @NamedQuery(name = "OtsServicePincode.findByOtsOfficeName", query = "SELECT o FROM OtsServicePincode o WHERE o.otsOfficeName = :otsOfficeName"),
    @NamedQuery(name = "OtsServicePincode.findByOtsStateName", query = "SELECT o FROM OtsServicePincode o WHERE o.otsStateName = :otsStateName"),
    @NamedQuery(name = "OtsServicePincode.findByOtsDistrictName", query = "SELECT o FROM OtsServicePincode o WHERE o.otsDistrictName = :otsDistrictName"),
    @NamedQuery(name = "OtsServicePincode.findByOtsTalukName", query = "SELECT o FROM OtsServicePincode o WHERE o.otsTalukName = :otsTalukName"),
    @NamedQuery(name = "OtsServicePincode.findByOtsLatitude", query = "SELECT o FROM OtsServicePincode o WHERE o.otsLatitude = :otsLatitude"),
    @NamedQuery(name = "OtsServicePincode.findByOtsLongitude", query = "SELECT o FROM OtsServicePincode o WHERE o.otsLongitude = :otsLongitude"),
    @NamedQuery(name = "OtsServicePincode.findByOtsLocationAccuracy", query = "SELECT o FROM OtsServicePincode o WHERE o.otsLocationAccuracy = :otsLocationAccuracy")})
public class OtsServicePincode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_pincode_id")
    private Integer otsPincodeId;
    @Size(max = 45)
    @Column(name = "ots_country_code")
    private String otsCountryCode;
    @Column(name = "ots_pincode")
    private Integer otsPincode;
    @Size(max = 100)
    @Column(name = "ots_office_name")
    private String otsOfficeName;
    @Size(max = 45)
    @Column(name = "ots_state_name")
    private String otsStateName;
    @Size(max = 45)
    @Column(name = "ots_district_name")
    private String otsDistrictName;
    @Size(max = 45)
    @Column(name = "ots_taluk_name")
    private String otsTalukName;
    @Size(max = 45)
    @Column(name = "ots_latitude")
    private String otsLatitude;
    @Size(max = 45)
    @Column(name = "ots_longitude")
    private String otsLongitude;
    @Column(name = "ots_location_accuracy")
    private Integer otsLocationAccuracy;
    @JoinColumn(name = "ots_district_id", referencedColumnName = "ots_district_id")
    @ManyToOne(optional = false)
    private OtsServiceDistrict otsDistrictId;
    @JoinColumn(name = "ots_state_id", referencedColumnName = "ots_state_id")
    @ManyToOne(optional = false)
    private OtsServiceState otsStateId;

    public OtsServicePincode() {
    }

    public OtsServicePincode(Integer otsPincodeId) {
        this.otsPincodeId = otsPincodeId;
    }

    public Integer getOtsPincodeId() {
        return otsPincodeId;
    }

    public void setOtsPincodeId(Integer otsPincodeId) {
        this.otsPincodeId = otsPincodeId;
    }

    public String getOtsCountryCode() {
        return otsCountryCode;
    }

    public void setOtsCountryCode(String otsCountryCode) {
        this.otsCountryCode = otsCountryCode;
    }

    public Integer getOtsPincode() {
        return otsPincode;
    }

    public void setOtsPincode(Integer otsPincode) {
        this.otsPincode = otsPincode;
    }

    public String getOtsOfficeName() {
        return otsOfficeName;
    }

    public void setOtsOfficeName(String otsOfficeName) {
        this.otsOfficeName = otsOfficeName;
    }

    public String getOtsStateName() {
        return otsStateName;
    }

    public void setOtsStateName(String otsStateName) {
        this.otsStateName = otsStateName;
    }

    public String getOtsDistrictName() {
        return otsDistrictName;
    }

    public void setOtsDistrictName(String otsDistrictName) {
        this.otsDistrictName = otsDistrictName;
    }

    public String getOtsTalukName() {
        return otsTalukName;
    }

    public void setOtsTalukName(String otsTalukName) {
        this.otsTalukName = otsTalukName;
    }

    public String getOtsLatitude() {
        return otsLatitude;
    }

    public void setOtsLatitude(String otsLatitude) {
        this.otsLatitude = otsLatitude;
    }

    public String getOtsLongitude() {
        return otsLongitude;
    }

    public void setOtsLongitude(String otsLongitude) {
        this.otsLongitude = otsLongitude;
    }

    public Integer getOtsLocationAccuracy() {
        return otsLocationAccuracy;
    }

    public void setOtsLocationAccuracy(Integer otsLocationAccuracy) {
        this.otsLocationAccuracy = otsLocationAccuracy;
    }

    public OtsServiceDistrict getOtsDistrictId() {
        return otsDistrictId;
    }

    public void setOtsDistrictId(OtsServiceDistrict otsDistrictId) {
        this.otsDistrictId = otsDistrictId;
    }

    public OtsServiceState getOtsStateId() {
        return otsStateId;
    }

    public void setOtsStateId(OtsServiceState otsStateId) {
        this.otsStateId = otsStateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsPincodeId != null ? otsPincodeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServicePincode)) {
            return false;
        }
        OtsServicePincode other = (OtsServicePincode) object;
        if ((this.otsPincodeId == null && other.otsPincodeId != null) || (this.otsPincodeId != null && !this.otsPincodeId.equals(other.otsPincodeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServicePincode[ otsPincodeId=" + otsPincodeId + " ]";
    }
    
}
