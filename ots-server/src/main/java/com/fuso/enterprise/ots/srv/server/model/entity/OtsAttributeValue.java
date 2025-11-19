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
 * @author Jeevan
 */
@Entity
@Table(name = "ots_attribute_value")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsAttributeValue.findAll", query = "SELECT o FROM OtsAttributeValue o"),
    @NamedQuery(name = "OtsAttributeValue.findByOtsAttributeValueId", query = "SELECT o FROM OtsAttributeValue o WHERE o.otsAttributeValueId = :otsAttributeValueId"),
    @NamedQuery(name = "OtsAttributeValue.findByOtsAttributeValueName", query = "SELECT o FROM OtsAttributeValue o WHERE o.otsAttributeValueName = :otsAttributeValueName")})
public class OtsAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_attribute_value_id")
    private Integer otsAttributeValueId;
    @Size(max = 45)
    @Column(name = "ots_attribute_value_name")
    private String otsAttributeValueName;
    @JoinColumn(name = "ots_attribute_key_id", referencedColumnName = "ots_attribute_key_id")
    @ManyToOne(optional = false)
    private OtsAttributeKey otsAttributeKeyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsAttributeValueId")
    private Collection<OtsProductAttributeMapping> otsProductAttributeMappingCollection;

    public OtsAttributeValue() {
    }

    public OtsAttributeValue(Integer otsAttributeValueId) {
        this.otsAttributeValueId = otsAttributeValueId;
    }

    public Integer getOtsAttributeValueId() {
        return otsAttributeValueId;
    }

    public void setOtsAttributeValueId(Integer otsAttributeValueId) {
        this.otsAttributeValueId = otsAttributeValueId;
    }

    public String getOtsAttributeValueName() {
        return otsAttributeValueName;
    }

    public void setOtsAttributeValueName(String otsAttributeValueName) {
        this.otsAttributeValueName = otsAttributeValueName;
    }

    public OtsAttributeKey getOtsAttributeKeyId() {
        return otsAttributeKeyId;
    }

    public void setOtsAttributeKeyId(OtsAttributeKey otsAttributeKeyId) {
        this.otsAttributeKeyId = otsAttributeKeyId;
    }

    @XmlTransient
    public Collection<OtsProductAttributeMapping> getOtsProductAttributeMappingCollection() {
        return otsProductAttributeMappingCollection;
    }

    public void setOtsProductAttributeMappingCollection(Collection<OtsProductAttributeMapping> otsProductAttributeMappingCollection) {
        this.otsProductAttributeMappingCollection = otsProductAttributeMappingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsAttributeValueId != null ? otsAttributeValueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsAttributeValue)) {
            return false;
        }
        OtsAttributeValue other = (OtsAttributeValue) object;
        if ((this.otsAttributeValueId == null && other.otsAttributeValueId != null) || (this.otsAttributeValueId != null && !this.otsAttributeValueId.equals(other.otsAttributeValueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsAttributeValue[ otsAttributeValueId=" + otsAttributeValueId + " ]";
    }
    
}
