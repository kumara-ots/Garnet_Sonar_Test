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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_service_city")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServiceCity.findAll", query = "SELECT o FROM OtsServiceCity o"),
    @NamedQuery(name = "OtsServiceCity.findByOtsCityId", query = "SELECT o FROM OtsServiceCity o WHERE o.otsCityId = :otsCityId"),
    @NamedQuery(name = "OtsServiceCity.findByOtsCityName", query = "SELECT o FROM OtsServiceCity o WHERE o.otsCityName = :otsCityName"),
    @NamedQuery(name = "OtsServiceCity.findByOtsDistrictId", query = "SELECT o FROM OtsServiceCity o WHERE o.otsDistrictId = :otsDistrictId"),
    @NamedQuery(name = "OtsServiceCity.findByOtsStateId", query = "SELECT o FROM OtsServiceCity o WHERE o.otsStateId = :otsStateId"),
    @NamedQuery(name = "OtsServiceCity.findByOtsCityStatus", query = "SELECT o FROM OtsServiceCity o WHERE o.otsCityStatus = :otsCityStatus")})
public class OtsServiceCity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_city_id")
    private Integer otsCityId;
    @Size(max = 45)
    @Column(name = "ots_city_name")
    private String otsCityName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ots_district_id")
    private int otsDistrictId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ots_state_id")
    private int otsStateId;
    @Size(max = 45)
    @Column(name = "ots_city_status")
    private String otsCityStatus;

    public OtsServiceCity() {
    }

    public OtsServiceCity(Integer otsCityId) {
        this.otsCityId = otsCityId;
    }

    public OtsServiceCity(Integer otsCityId, int otsDistrictId, int otsStateId) {
        this.otsCityId = otsCityId;
        this.otsDistrictId = otsDistrictId;
        this.otsStateId = otsStateId;
    }

    public Integer getOtsCityId() {
        return otsCityId;
    }

    public void setOtsCityId(Integer otsCityId) {
        this.otsCityId = otsCityId;
    }

    public String getOtsCityName() {
        return otsCityName;
    }

    public void setOtsCityName(String otsCityName) {
        this.otsCityName = otsCityName;
    }

    public int getOtsDistrictId() {
        return otsDistrictId;
    }

    public void setOtsDistrictId(int otsDistrictId) {
        this.otsDistrictId = otsDistrictId;
    }

    public int getOtsStateId() {
        return otsStateId;
    }

    public void setOtsStateId(int otsStateId) {
        this.otsStateId = otsStateId;
    }

    public String getOtsCityStatus() {
        return otsCityStatus;
    }

    public void setOtsCityStatus(String otsCityStatus) {
        this.otsCityStatus = otsCityStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsCityId != null ? otsCityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServiceCity)) {
            return false;
        }
        OtsServiceCity other = (OtsServiceCity) object;
        if ((this.otsCityId == null && other.otsCityId != null) || (this.otsCityId != null && !this.otsCityId.equals(other.otsCityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceCity[ otsCityId=" + otsCityId + " ]";
    }
    
}
