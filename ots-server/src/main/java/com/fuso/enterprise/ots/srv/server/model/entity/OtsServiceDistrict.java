/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "ots_service_district")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServiceDistrict.findAll", query = "SELECT o FROM OtsServiceDistrict o"),
    @NamedQuery(name = "OtsServiceDistrict.findByOtsDistrictId", query = "SELECT o FROM OtsServiceDistrict o WHERE o.otsDistrictId = :otsDistrictId"),
    @NamedQuery(name = "OtsServiceDistrict.findByOtsDistrictName", query = "SELECT o FROM OtsServiceDistrict o WHERE o.otsDistrictName = :otsDistrictName"),
    @NamedQuery(name = "OtsServiceDistrict.findByOtsDistrictStatus", query = "SELECT o FROM OtsServiceDistrict o WHERE o.otsDistrictStatus = :otsDistrictStatus")})
public class OtsServiceDistrict implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_district_id")
    private Integer otsDistrictId;
    @Size(max = 45)
    @Column(name = "ots_district_name")
    private String otsDistrictName;
    @Size(max = 45)
    @Column(name = "ots_district_status")
    private String otsDistrictStatus;
    @JoinColumn(name = "ots_state_id", referencedColumnName = "ots_state_id")
    @ManyToOne(optional = false)
    private OtsServiceState otsStateId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsDistrictId")   
    private Collection<OtsServicePincode> otsServicePincodeCollection;
    

    public OtsServiceDistrict() {
    }

    public OtsServiceDistrict(Integer otsDistrictId) {
        this.otsDistrictId = otsDistrictId;
    }

    public Integer getOtsDistrictId() {
        return otsDistrictId;
    }

    public void setOtsDistrictId(Integer otsDistrictId) {
        this.otsDistrictId = otsDistrictId;
    }

    public String getOtsDistrictName() {
        return otsDistrictName;
    }

    public void setOtsDistrictName(String otsDistrictName) {
        this.otsDistrictName = otsDistrictName;
    }

    public String getOtsDistrictStatus() {
        return otsDistrictStatus;
    }

    public void setOtsDistrictStatus(String otsDistrictStatus) {
        this.otsDistrictStatus = otsDistrictStatus;
    }

    public OtsServiceState getOtsStateId() {
        return otsStateId;
    }

    public void setOtsStateId(OtsServiceState otsStateId) {
        this.otsStateId = otsStateId;
    }

    @XmlTransient
    public Collection<OtsServicePincode> getOtsServicePincodeCollection() {
        return otsServicePincodeCollection;
    }

    public void setOtsServicePincodeCollection(Collection<OtsServicePincode> otsServicePincodeCollection) {
        this.otsServicePincodeCollection = otsServicePincodeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsDistrictId != null ? otsDistrictId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServiceDistrict)) {
            return false;
        }
        OtsServiceDistrict other = (OtsServiceDistrict) object;
        if ((this.otsDistrictId == null && other.otsDistrictId != null) || (this.otsDistrictId != null && !this.otsDistrictId.equals(other.otsDistrictId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceDistrict[ otsDistrictId=" + otsDistrictId + " ]";
    }
    
}
