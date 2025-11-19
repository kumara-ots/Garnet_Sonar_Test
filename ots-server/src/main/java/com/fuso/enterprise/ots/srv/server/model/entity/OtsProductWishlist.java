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
@Table(name = "ots_product_wishlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsProductWishlist.findAll", query = "SELECT o FROM OtsProductWishlist o"),
    @NamedQuery(name = "OtsProductWishlist.findByOtsProductWishlistId", query = "SELECT o FROM OtsProductWishlist o WHERE o.otsProductWishlistId = :otsProductWishlistId")})
public class OtsProductWishlist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_product_wishlist_id")
    private Integer otsProductWishlistId;
    @JoinColumn(name = "ots_product_id", referencedColumnName = "ots_product_id")
    @ManyToOne
    private OtsProduct otsProductId;
    @JoinColumn(name = "ots_customer_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsCustomerId;

    public OtsProductWishlist() {
    }

    public OtsProductWishlist(Integer otsProductWishlistId) {
        this.otsProductWishlistId = otsProductWishlistId;
    }

    public Integer getOtsProductWishlistId() {
        return otsProductWishlistId;
    }

    public void setOtsProductWishlistId(Integer otsProductWishlistId) {
        this.otsProductWishlistId = otsProductWishlistId;
    }

    public OtsProduct getOtsProductId() {
        return otsProductId;
    }

    public void setOtsProductId(OtsProduct otsProductId) {
        this.otsProductId = otsProductId;
    }

    public OtsUsers getOtsCustomerId() {
        return otsCustomerId;
    }

    public void setOtsCustomerId(OtsUsers otsCustomerId) {
        this.otsCustomerId = otsCustomerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsProductWishlistId != null ? otsProductWishlistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsProductWishlist)) {
            return false;
        }
        OtsProductWishlist other = (OtsProductWishlist) object;
        if ((this.otsProductWishlistId == null && other.otsProductWishlistId != null) || (this.otsProductWishlistId != null && !this.otsProductWishlistId.equals(other.otsProductWishlistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsProductWishlist[ otsProductWishlistId=" + otsProductWishlistId + " ]";
    }
    
}
