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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_product_location_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsProductLocationMapping.findAll", query = "SELECT o FROM OtsProductLocationMapping o"),
    @NamedQuery(name = "OtsProductLocationMapping.findByOtsProductLocationMappingId", query = "SELECT o FROM OtsProductLocationMapping o WHERE o.otsProductLocationMappingId = :otsProductLocationMappingId"),
    @NamedQuery(name = "OtsProductLocationMapping.findByOtsProductId", query = "SELECT o FROM OtsProductLocationMapping o WHERE o.otsProductId = :otsProductId"),
    @NamedQuery(name = "OtsProductLocationMapping.findByOtsProductLocationId", query = "SELECT o FROM OtsProductLocationMapping o WHERE o.otsProductLocationId = :otsProductLocationId"),
    @NamedQuery(name = "OtsProductLocationMapping.findByOtsProductLocationName", query = "SELECT o FROM OtsProductLocationMapping o WHERE o.otsProductLocationName = :otsProductLocationName")})
public class OtsProductLocationMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_product_location_mapping_id")
    private Integer otsProductLocationMappingId;
    @Size(max = 45)
    @Column(name = "ots_product_id")
    private String otsProductId;
    @Size(max = 45)
    @Column(name = "ots_product_location_id")
    private String otsProductLocationId;
    @Size(max = 45)
    @Column(name = "ots_product_location_name")
    private String otsProductLocationName;
    @JoinColumn(name = "ots_distributor_id", referencedColumnName = "ots_users_id")
    @ManyToOne(optional = false)
    private OtsUsers otsDistributorId;

    public OtsProductLocationMapping() {
    }

    public OtsProductLocationMapping(Integer otsProductLocationMappingId) {
        this.otsProductLocationMappingId = otsProductLocationMappingId;
    }

    public Integer getOtsProductLocationMappingId() {
        return otsProductLocationMappingId;
    }

    public void setOtsProductLocationMappingId(Integer otsProductLocationMappingId) {
        this.otsProductLocationMappingId = otsProductLocationMappingId;
    }

    public String getOtsProductId() {
        return otsProductId;
    }

    public void setOtsProductId(String otsProductId) {
        this.otsProductId = otsProductId;
    }

    public String getOtsProductLocationId() {
        return otsProductLocationId;
    }

    public void setOtsProductLocationId(String otsProductLocationId) {
        this.otsProductLocationId = otsProductLocationId;
    }

    public String getOtsProductLocationName() {
        return otsProductLocationName;
    }

    public void setOtsProductLocationName(String otsProductLocationName) {
        this.otsProductLocationName = otsProductLocationName;
    }

    public OtsUsers getOtsDistributorId() {
        return otsDistributorId;
    }

    public void setOtsDistributorId(OtsUsers otsDistributorId) {
        this.otsDistributorId = otsDistributorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsProductLocationMappingId != null ? otsProductLocationMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsProductLocationMapping)) {
            return false;
        }
        OtsProductLocationMapping other = (OtsProductLocationMapping) object;
        if ((this.otsProductLocationMappingId == null && other.otsProductLocationMappingId != null) || (this.otsProductLocationMappingId != null && !this.otsProductLocationMappingId.equals(other.otsProductLocationMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsProductLocationMapping[ otsProductLocationMappingId=" + otsProductLocationMappingId + " ]";
    }
    
}
