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
@Table(name = "ots_attribute_key")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsAttributeKey.findAll", query = "SELECT o FROM OtsAttributeKey o"),
    @NamedQuery(name = "OtsAttributeKey.findByOtsAttributeKeyId", query = "SELECT o FROM OtsAttributeKey o WHERE o.otsAttributeKeyId = :otsAttributeKeyId"),
    @NamedQuery(name = "OtsAttributeKey.findByOtsAttributeKeyName", query = "SELECT o FROM OtsAttributeKey o WHERE o.otsAttributeKeyName = :otsAttributeKeyName"),
    @NamedQuery(name = "OtsAttributeKey.findByOtsAttributeKeyDescription", query = "SELECT o FROM OtsAttributeKey o WHERE o.otsAttributeKeyDescription = :otsAttributeKeyDescription")})
public class OtsAttributeKey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_attribute_key_id")
    private Integer otsAttributeKeyId;
    @Size(max = 45)
    @Column(name = "ots_attribute_key_name")
    private String otsAttributeKeyName;
    @Size(max = 45)
    @Column(name = "ots_attribute_key_description")
    private String otsAttributeKeyDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsAttributeKeyId")
    private Collection<OtsAttributeValue> otsAttributeValueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsAttributeKeyId")
    private Collection<OtsSubcategoryAttributeMapping> otsSubcategoryAttributeMappingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsAttributeKeyId")
    private Collection<OtsProductAttributeMapping> otsProductAttributeMappingCollection;

    public OtsAttributeKey() {
    }

    public OtsAttributeKey(Integer otsAttributeKeyId) {
        this.otsAttributeKeyId = otsAttributeKeyId;
    }

    public Integer getOtsAttributeKeyId() {
        return otsAttributeKeyId;
    }

    public void setOtsAttributeKeyId(Integer otsAttributeKeyId) {
        this.otsAttributeKeyId = otsAttributeKeyId;
    }

    public String getOtsAttributeKeyName() {
        return otsAttributeKeyName;
    }

    public void setOtsAttributeKeyName(String otsAttributeKeyName) {
        this.otsAttributeKeyName = otsAttributeKeyName;
    }

    public String getOtsAttributeKeyDescription() {
        return otsAttributeKeyDescription;
    }

    public void setOtsAttributeKeyDescription(String otsAttributeKeyDescription) {
        this.otsAttributeKeyDescription = otsAttributeKeyDescription;
    }

    @XmlTransient
    public Collection<OtsAttributeValue> getOtsAttributeValueCollection() {
        return otsAttributeValueCollection;
    }

    public void setOtsAttributeValueCollection(Collection<OtsAttributeValue> otsAttributeValueCollection) {
        this.otsAttributeValueCollection = otsAttributeValueCollection;
    }

    @XmlTransient
    public Collection<OtsSubcategoryAttributeMapping> getOtsSubcategoryAttributeMappingCollection() {
        return otsSubcategoryAttributeMappingCollection;
    }

    public void setOtsSubcategoryAttributeMappingCollection(Collection<OtsSubcategoryAttributeMapping> otsSubcategoryAttributeMappingCollection) {
        this.otsSubcategoryAttributeMappingCollection = otsSubcategoryAttributeMappingCollection;
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
        hash += (otsAttributeKeyId != null ? otsAttributeKeyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsAttributeKey)) {
            return false;
        }
        OtsAttributeKey other = (OtsAttributeKey) object;
        if ((this.otsAttributeKeyId == null && other.otsAttributeKeyId != null) || (this.otsAttributeKeyId != null && !this.otsAttributeKeyId.equals(other.otsAttributeKeyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsAttributeKey[ otsAttributeKeyId=" + otsAttributeKeyId + " ]";
    }
    
}
