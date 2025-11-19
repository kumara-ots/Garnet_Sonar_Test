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
 * @author Jeevan
 */
@Entity
@Table(name = "ots_subcategory_attribute_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsSubcategoryAttributeMapping.findAll", query = "SELECT o FROM OtsSubcategoryAttributeMapping o"),
    @NamedQuery(name = "OtsSubcategoryAttributeMapping.findByOtsSubcategoryAttributeMappingId", query = "SELECT o FROM OtsSubcategoryAttributeMapping o WHERE o.otsSubcategoryAttributeMappingId = :otsSubcategoryAttributeMappingId")})
public class OtsSubcategoryAttributeMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_subcategory_attribute_mapping_id")
    private Integer otsSubcategoryAttributeMappingId;
    @JoinColumn(name = "ots_attribute_key_id", referencedColumnName = "ots_attribute_key_id")
    @ManyToOne(optional = false)
    private OtsAttributeKey otsAttributeKeyId;
    @JoinColumn(name = "ots_subcategory_id", referencedColumnName = "ots_product_id")
    @ManyToOne(optional = false)
    private OtsProduct otsSubcategoryId;

    public OtsSubcategoryAttributeMapping() {
    }

    public OtsSubcategoryAttributeMapping(Integer otsSubcategoryAttributeMappingId) {
        this.otsSubcategoryAttributeMappingId = otsSubcategoryAttributeMappingId;
    }

    public Integer getOtsSubcategoryAttributeMappingId() {
        return otsSubcategoryAttributeMappingId;
    }

    public void setOtsSubcategoryAttributeMappingId(Integer otsSubcategoryAttributeMappingId) {
        this.otsSubcategoryAttributeMappingId = otsSubcategoryAttributeMappingId;
    }

    public OtsAttributeKey getOtsAttributeKeyId() {
        return otsAttributeKeyId;
    }

    public void setOtsAttributeKeyId(OtsAttributeKey otsAttributeKeyId) {
        this.otsAttributeKeyId = otsAttributeKeyId;
    }

    public OtsProduct getOtsSubcategoryId() {
        return otsSubcategoryId;
    }

    public void setOtsSubcategoryId(OtsProduct otsSubcategoryId) {
        this.otsSubcategoryId = otsSubcategoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsSubcategoryAttributeMappingId != null ? otsSubcategoryAttributeMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsSubcategoryAttributeMapping)) {
            return false;
        }
        OtsSubcategoryAttributeMapping other = (OtsSubcategoryAttributeMapping) object;
        if ((this.otsSubcategoryAttributeMappingId == null && other.otsSubcategoryAttributeMappingId != null) || (this.otsSubcategoryAttributeMappingId != null && !this.otsSubcategoryAttributeMappingId.equals(other.otsSubcategoryAttributeMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsSubcategoryAttributeMapping[ otsSubcategoryAttributeMappingId=" + otsSubcategoryAttributeMappingId + " ]";
    }
    
}
