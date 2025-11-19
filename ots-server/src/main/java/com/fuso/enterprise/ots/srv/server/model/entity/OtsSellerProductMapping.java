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
@Table(name = "ots_seller_product_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsSellerProductMapping.findAll", query = "SELECT o FROM OtsSellerProductMapping o"),
    @NamedQuery(name = "OtsSellerProductMapping.findByOtsSellerProductMappingId", query = "SELECT o FROM OtsSellerProductMapping o WHERE o.otsSellerProductMappingId = :otsSellerProductMappingId")})
public class OtsSellerProductMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_seller_product_mapping_id")
    private Integer otsSellerProductMappingId;
    @JoinColumn(name = "ots_product_id", referencedColumnName = "ots_product_id")
    @ManyToOne
    private OtsProduct otsProductId;
    @JoinColumn(name = "ots_seller_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsSellerId;
    @JoinColumn(name = "created_user", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts createdUser;

    public OtsSellerProductMapping() {
    }

    public OtsSellerProductMapping(Integer otsSellerProductMappingId) {
        this.otsSellerProductMappingId = otsSellerProductMappingId;
    }

    public Integer getOtsSellerProductMappingId() {
        return otsSellerProductMappingId;
    }

    public void setOtsSellerProductMappingId(Integer otsSellerProductMappingId) {
        this.otsSellerProductMappingId = otsSellerProductMappingId;
    }

    public OtsProduct getOtsProductId() {
        return otsProductId;
    }

    public void setOtsProductId(OtsProduct otsProductId) {
        this.otsProductId = otsProductId;
    }

    public OtsUsers getOtsSellerId() {
        return otsSellerId;
    }

    public void setOtsSellerId(OtsUsers otsSellerId) {
        this.otsSellerId = otsSellerId;
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
        hash += (otsSellerProductMappingId != null ? otsSellerProductMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsSellerProductMapping)) {
            return false;
        }
        OtsSellerProductMapping other = (OtsSellerProductMapping) object;
        if ((this.otsSellerProductMappingId == null && other.otsSellerProductMappingId != null) || (this.otsSellerProductMappingId != null && !this.otsSellerProductMappingId.equals(other.otsSellerProductMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsSellerProductMapping[ otsSellerProductMappingId=" + otsSellerProductMappingId + " ]";
    }
    
}
