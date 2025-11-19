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
@Table(name = "ots_service_state")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServiceState.findAll", query = "SELECT o FROM OtsServiceState o"),
    @NamedQuery(name = "OtsServiceState.findByOtsStateId", query = "SELECT o FROM OtsServiceState o WHERE o.otsStateId = :otsStateId"),
    @NamedQuery(name = "OtsServiceState.findByOtsStateName", query = "SELECT o FROM OtsServiceState o WHERE o.otsStateName = :otsStateName"),
    @NamedQuery(name = "OtsServiceState.findByOtsStateStatus", query = "SELECT o FROM OtsServiceState o WHERE o.otsStateStatus = :otsStateStatus")})
public class OtsServiceState implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_state_id")
    private Integer otsStateId;
    @Size(max = 45)
    @Column(name = "ots_state_name")
    private String otsStateName;
    @Size(max = 45)
    @Column(name = "ots_state_status")
    private String otsStateStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsStateId")
    private Collection<OtsServiceDistrict> otsServiceDistrictCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsStateId")
    private Collection<OtsServicePincode> otsServicePincodeCollection;

    public OtsServiceState() {
    }

    public OtsServiceState(Integer otsStateId) {
        this.otsStateId = otsStateId;
    }

    public Integer getOtsStateId() {
        return otsStateId;
    }

    public void setOtsStateId(Integer otsStateId) {
        this.otsStateId = otsStateId;
    }

    public String getOtsStateName() {
        return otsStateName;
    }

    public void setOtsStateName(String otsStateName) {
        this.otsStateName = otsStateName;
    }

    public String getOtsStateStatus() {
        return otsStateStatus;
    }

    public void setOtsStateStatus(String otsStateStatus) {
        this.otsStateStatus = otsStateStatus;
    }

    @XmlTransient
    public Collection<OtsServiceDistrict> getOtsServiceDistrictCollection() {
        return otsServiceDistrictCollection;
    }

    public void setOtsServiceDistrictCollection(Collection<OtsServiceDistrict> otsServiceDistrictCollection) {
        this.otsServiceDistrictCollection = otsServiceDistrictCollection;
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
        hash += (otsStateId != null ? otsStateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServiceState)) {
            return false;
        }
        OtsServiceState other = (OtsServiceState) object;
        if ((this.otsStateId == null && other.otsStateId != null) || (this.otsStateId != null && !this.otsStateId.equals(other.otsStateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceState[ otsStateId=" + otsStateId + " ]";
    }
    
}
