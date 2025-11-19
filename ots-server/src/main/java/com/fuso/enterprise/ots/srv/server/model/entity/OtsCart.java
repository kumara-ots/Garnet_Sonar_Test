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
@Table(name = "ots_cart")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsCart.findAll", query = "SELECT o FROM OtsCart o"),
    @NamedQuery(name = "OtsCart.findByOtsCartId", query = "SELECT o FROM OtsCart o WHERE o.otsCartId = :otsCartId"),
    @NamedQuery(name = "OtsCart.findByOtsCartQty", query = "SELECT o FROM OtsCart o WHERE o.otsCartQty = :otsCartQty")})
public class OtsCart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_cart_id")
    private Integer otsCartId;
    @Column(name = "ots_cart_qty")
    private Integer otsCartQty;
    @JoinColumn(name = "ots_product_id", referencedColumnName = "ots_product_id")
    @ManyToOne
    private OtsProduct otsProductId;
    @JoinColumn(name = "ots_customer_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsCustomerId;

    public OtsCart() {
    }

    public OtsCart(Integer otsCartId) {
        this.otsCartId = otsCartId;
    }

    public Integer getOtsCartId() {
        return otsCartId;
    }

    public void setOtsCartId(Integer otsCartId) {
        this.otsCartId = otsCartId;
    }

    public Integer getOtsCartQty() {
        return otsCartQty;
    }

    public void setOtsCartQty(Integer otsCartQty) {
        this.otsCartQty = otsCartQty;
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
        hash += (otsCartId != null ? otsCartId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsCart)) {
            return false;
        }
        OtsCart other = (OtsCart) object;
        if ((this.otsCartId == null && other.otsCartId != null) || (this.otsCartId != null && !this.otsCartId.equals(other.otsCartId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsCart[ otsCartId=" + otsCartId + " ]";
    }
    
}
