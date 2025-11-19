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
@Table(name = "ots_coupon_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsCouponOrder.findAll", query = "SELECT o FROM OtsCouponOrder o"),
    @NamedQuery(name = "OtsCouponOrder.findByOtsCouponOrderId", query = "SELECT o FROM OtsCouponOrder o WHERE o.otsCouponOrderId = :otsCouponOrderId")})
public class OtsCouponOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_coupon_order_id")
    private Integer otsCouponOrderId;
    @JoinColumn(name = "ots_coupon_id", referencedColumnName = "ots_coupon_id")
    @ManyToOne(optional = false)
    private OtsCoupon otsCouponId;
    @JoinColumn(name = "ots_order_id", referencedColumnName = "ots_order_id")
    @ManyToOne
    private OtsOrder otsOrderId;

    public OtsCouponOrder() {
    }

    public OtsCouponOrder(Integer otsCouponOrderId) {
        this.otsCouponOrderId = otsCouponOrderId;
    }

    public Integer getOtsCouponOrderId() {
        return otsCouponOrderId;
    }

    public void setOtsCouponOrderId(Integer otsCouponOrderId) {
        this.otsCouponOrderId = otsCouponOrderId;
    }

    public OtsCoupon getOtsCouponId() {
        return otsCouponId;
    }

    public void setOtsCouponId(OtsCoupon otsCouponId) {
        this.otsCouponId = otsCouponId;
    }

    public OtsOrder getOtsOrderId() {
        return otsOrderId;
    }

    public void setOtsOrderId(OtsOrder otsOrderId) {
        this.otsOrderId = otsOrderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsCouponOrderId != null ? otsCouponOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsCouponOrder)) {
            return false;
        }
        OtsCouponOrder other = (OtsCouponOrder) object;
        if ((this.otsCouponOrderId == null && other.otsCouponOrderId != null) || (this.otsCouponOrderId != null && !this.otsCouponOrderId.equals(other.otsCouponOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsCouponOrder[ otsCouponOrderId=" + otsCouponOrderId + " ]";
    }
    
}
