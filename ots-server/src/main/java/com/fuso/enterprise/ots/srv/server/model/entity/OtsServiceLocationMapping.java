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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "ots_service_location_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServiceLocationMapping.findAll", query = "SELECT o FROM OtsServiceLocationMapping o"),
    @NamedQuery(name = "OtsServiceLocationMapping.findByOtsServiceLocationMappingId", query = "SELECT o FROM OtsServiceLocationMapping o WHERE o.otsServiceLocationMappingId = :otsServiceLocationMappingId"),
    @NamedQuery(name = "OtsServiceLocationMapping.findByOtsServiceId", query = "SELECT o FROM OtsServiceLocationMapping o WHERE o.otsServiceId = :otsServiceId"),
    @NamedQuery(name = "OtsServiceLocationMapping.findByOtsServiceLocationId", query = "SELECT o FROM OtsServiceLocationMapping o WHERE o.otsServiceLocationId = :otsServiceLocationId"),
    @NamedQuery(name = "OtsServiceLocationMapping.findByOtsServiceLocationName", query = "SELECT o FROM OtsServiceLocationMapping o WHERE o.otsServiceLocationName = :otsServiceLocationName")})
public class OtsServiceLocationMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_service_location_mapping_id")
    private Integer otsServiceLocationMappingId;
    @Column(name = "ots_service_id")
    private String otsServiceId;
    @Column(name = "ots_service_location_id")
    private String otsServiceLocationId;
    @Column(name = "ots_service_location_name")
    private String otsServiceLocationName;
    @JoinColumn(name = "ots_provider_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsProviderId;

    public OtsServiceLocationMapping() {
    }

    public OtsServiceLocationMapping(Integer otsServiceLocationMappingId) {
        this.otsServiceLocationMappingId = otsServiceLocationMappingId;
    }

    public Integer getOtsServiceLocationMappingId() {
        return otsServiceLocationMappingId;
    }

    public void setOtsServiceLocationMappingId(Integer otsServiceLocationMappingId) {
        this.otsServiceLocationMappingId = otsServiceLocationMappingId;
    }

    public String getOtsServiceId() {
        return otsServiceId;
    }

    public void setOtsServiceId(String otsServiceId) {
        this.otsServiceId = otsServiceId;
    }

    public String getOtsServiceLocationId() {
        return otsServiceLocationId;
    }

    public void setOtsServiceLocationId(String otsServiceLocationId) {
        this.otsServiceLocationId = otsServiceLocationId;
    }

    public String getOtsServiceLocationName() {
        return otsServiceLocationName;
    }

    public void setOtsServiceLocationName(String otsServiceLocationName) {
        this.otsServiceLocationName = otsServiceLocationName;
    }

    public OtsUsers getOtsProviderId() {
        return otsProviderId;
    }

    public void setOtsProviderId(OtsUsers otsProviderId) {
        this.otsProviderId = otsProviderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsServiceLocationMappingId != null ? otsServiceLocationMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServiceLocationMapping)) {
            return false;
        }
        OtsServiceLocationMapping other = (OtsServiceLocationMapping) object;
        if ((this.otsServiceLocationMappingId == null && other.otsServiceLocationMappingId != null) || (this.otsServiceLocationMappingId != null && !this.otsServiceLocationMappingId.equals(other.otsServiceLocationMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceLocationMapping[ otsServiceLocationMappingId=" + otsServiceLocationMappingId + " ]";
    }
    
}
