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
 * @author Jeevan
 */
@Entity
@Table(name = "ots_product_level")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsProductLevel.findAll", query = "SELECT o FROM OtsProductLevel o"),
    @NamedQuery(name = "OtsProductLevel.findByOtsProductLevelId", query = "SELECT o FROM OtsProductLevel o WHERE o.otsProductLevelId = :otsProductLevelId"),
    @NamedQuery(name = "OtsProductLevel.findByOtsProductName", query = "SELECT o FROM OtsProductLevel o WHERE o.otsProductName = :otsProductName")})
public class OtsProductLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_product_level_id")
    private Integer otsProductLevelId;
    @Size(max = 45)
    @Column(name = "ots_product_name")
    private String otsProductName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProductLevelId")
    private Collection<OtsProduct> otsProductCollection;

    public OtsProductLevel() {
    }

    public OtsProductLevel(Integer otsProductLevelId) {
        this.otsProductLevelId = otsProductLevelId;
    }

    public Integer getOtsProductLevelId() {
        return otsProductLevelId;
    }

    public void setOtsProductLevelId(Integer otsProductLevelId) {
        this.otsProductLevelId = otsProductLevelId;
    }

    public String getOtsProductName() {
        return otsProductName;
    }

    public void setOtsProductName(String otsProductName) {
        this.otsProductName = otsProductName;
    }

    @XmlTransient
    public Collection<OtsProduct> getOtsProductCollection() {
        return otsProductCollection;
    }

    public void setOtsProductCollection(Collection<OtsProduct> otsProductCollection) {
        this.otsProductCollection = otsProductCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsProductLevelId != null ? otsProductLevelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsProductLevel)) {
            return false;
        }
        OtsProductLevel other = (OtsProductLevel) object;
        if ((this.otsProductLevelId == null && other.otsProductLevelId != null) || (this.otsProductLevelId != null && !this.otsProductLevelId.equals(other.otsProductLevelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsProductLevel[ otsProductLevelId=" + otsProductLevelId + " ]";
    }
    
}
