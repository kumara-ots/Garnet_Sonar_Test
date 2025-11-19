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
@Table(name = "ots_product_attribute_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsProductAttributeMapping.findAll", query = "SELECT o FROM OtsProductAttributeMapping o"),
    @NamedQuery(name = "OtsProductAttributeMapping.findByOtsProductAttributeMappingId", query = "SELECT o FROM OtsProductAttributeMapping o WHERE o.otsProductAttributeMappingId = :otsProductAttributeMappingId")})
public class OtsProductAttributeMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_product_attribute_mapping_id")
    private Integer otsProductAttributeMappingId;
    @JoinColumn(name = "ots_attribute_key_id", referencedColumnName = "ots_attribute_key_id")
    @ManyToOne(optional = false)
    private OtsAttributeKey otsAttributeKeyId;
    @JoinColumn(name = "ots_attribute_value_id", referencedColumnName = "ots_attribute_value_id")
    @ManyToOne(optional = false)
    private OtsAttributeValue otsAttributeValueId;
    @JoinColumn(name = "ots_product_id", referencedColumnName = "ots_product_id")
    @ManyToOne(optional = false)
    private OtsProduct otsProductId;

    public OtsProductAttributeMapping() {
    }

    public OtsProductAttributeMapping(Integer otsProductAttributeMappingId) {
        this.otsProductAttributeMappingId = otsProductAttributeMappingId;
    }

    public Integer getOtsProductAttributeMappingId() {
        return otsProductAttributeMappingId;
    }

    public void setOtsProductAttributeMappingId(Integer otsProductAttributeMappingId) {
        this.otsProductAttributeMappingId = otsProductAttributeMappingId;
    }

    public OtsAttributeKey getOtsAttributeKeyId() {
        return otsAttributeKeyId;
    }

    public void setOtsAttributeKeyId(OtsAttributeKey otsAttributeKeyId) {
        this.otsAttributeKeyId = otsAttributeKeyId;
    }

    public OtsAttributeValue getOtsAttributeValueId() {
        return otsAttributeValueId;
    }

    public void setOtsAttributeValueId(OtsAttributeValue otsAttributeValueId) {
        this.otsAttributeValueId = otsAttributeValueId;
    }

    public OtsProduct getOtsProductId() {
        return otsProductId;
    }

    public void setOtsProductId(OtsProduct otsProductId) {
        this.otsProductId = otsProductId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsProductAttributeMappingId != null ? otsProductAttributeMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsProductAttributeMapping)) {
            return false;
        }
        OtsProductAttributeMapping other = (OtsProductAttributeMapping) object;
        if ((this.otsProductAttributeMappingId == null && other.otsProductAttributeMappingId != null) || (this.otsProductAttributeMappingId != null && !this.otsProductAttributeMappingId.equals(other.otsProductAttributeMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsProductAttributeMapping[ otsProductAttributeMappingId=" + otsProductAttributeMappingId + " ]";
    }
    
}
