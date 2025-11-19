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
@Table(name = "ots_product_category_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsProductCategoryMapping.findAll", query = "SELECT o FROM OtsProductCategoryMapping o"),
    @NamedQuery(name = "OtsProductCategoryMapping.findByOtsProductCategorytMappingId", query = "SELECT o FROM OtsProductCategoryMapping o WHERE o.otsProductCategorytMappingId = :otsProductCategorytMappingId")})
public class OtsProductCategoryMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_product_categoryt_mapping_id")
    private Integer otsProductCategorytMappingId;
    @JoinColumn(name = "ots_product_id", referencedColumnName = "ots_product_id")
    @ManyToOne
    private OtsProduct otsProductId;
    @JoinColumn(name = "ots_product_category_id", referencedColumnName = "ots_product_id")
    @ManyToOne
    private OtsProduct otsProductCategoryId;
    @JoinColumn(name = "created_user", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts createdUser;

    public OtsProductCategoryMapping() {
    }

    public OtsProductCategoryMapping(Integer otsProductCategorytMappingId) {
        this.otsProductCategorytMappingId = otsProductCategorytMappingId;
    }

    public Integer getOtsProductCategorytMappingId() {
        return otsProductCategorytMappingId;
    }

    public void setOtsProductCategorytMappingId(Integer otsProductCategorytMappingId) {
        this.otsProductCategorytMappingId = otsProductCategorytMappingId;
    }

    public OtsProduct getOtsProductId() {
        return otsProductId;
    }

    public void setOtsProductId(OtsProduct otsProductId) {
        this.otsProductId = otsProductId;
    }

    public OtsProduct getOtsProductCategoryId() {
        return otsProductCategoryId;
    }

    public void setOtsProductCategoryId(OtsProduct otsProductCategoryId) {
        this.otsProductCategoryId = otsProductCategoryId;
    }

    public Useraccounts getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Useraccounts createdUser) {
        this.createdUser = createdUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsProductCategorytMappingId != null ? otsProductCategorytMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsProductCategoryMapping)) {
            return false;
        }
        OtsProductCategoryMapping other = (OtsProductCategoryMapping) object;
        if ((this.otsProductCategorytMappingId == null && other.otsProductCategorytMappingId != null) || (this.otsProductCategorytMappingId != null && !this.otsProductCategorytMappingId.equals(other.otsProductCategorytMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsProductCategoryMapping[ otsProductCategorytMappingId=" + otsProductCategorytMappingId + " ]";
    }
    
}
