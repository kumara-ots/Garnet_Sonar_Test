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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_payment_transaction_cancel_records")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsPaymentTransactionCancelRecords.findAll", query = "SELECT o FROM OtsPaymentTransactionCancelRecords o"),
    @NamedQuery(name = "OtsPaymentTransactionCancelRecords.findByOtsTransactionCancelId", query = "SELECT o FROM OtsPaymentTransactionCancelRecords o WHERE o.otsTransactionCancelId = :otsTransactionCancelId"),
    @NamedQuery(name = "OtsPaymentTransactionCancelRecords.findByOtsTransactionCancelOrderId", query = "SELECT o FROM OtsPaymentTransactionCancelRecords o WHERE o.otsTransactionCancelOrderId = :otsTransactionCancelOrderId"),
    @NamedQuery(name = "OtsPaymentTransactionCancelRecords.findByOtsTransactionCancelTrackingId", query = "SELECT o FROM OtsPaymentTransactionCancelRecords o WHERE o.otsTransactionCancelTrackingId = :otsTransactionCancelTrackingId"),
    @NamedQuery(name = "OtsPaymentTransactionCancelRecords.findByOtsTransactionCancelOrderStatus", query = "SELECT o FROM OtsPaymentTransactionCancelRecords o WHERE o.otsTransactionCancelOrderStatus = :otsTransactionCancelOrderStatus"),
    @NamedQuery(name = "OtsPaymentTransactionCancelRecords.findByOtsTransactionCancelAmount", query = "SELECT o FROM OtsPaymentTransactionCancelRecords o WHERE o.otsTransactionCancelAmount = :otsTransactionCancelAmount")})
public class OtsPaymentTransactionCancelRecords implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_transaction_cancel_id")
    private Integer otsTransactionCancelId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ots_transaction_cancel_order_id")
    private String otsTransactionCancelOrderId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ots_transaction_cancel_tracking_id")
    private String otsTransactionCancelTrackingId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ots_transaction_cancel_order_status")
    private String otsTransactionCancelOrderStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ots_transaction_cancel_amount")
    private String otsTransactionCancelAmount;

    public OtsPaymentTransactionCancelRecords() {
    }

    public OtsPaymentTransactionCancelRecords(Integer otsTransactionCancelId) {
        this.otsTransactionCancelId = otsTransactionCancelId;
    }

    public OtsPaymentTransactionCancelRecords(Integer otsTransactionCancelId, String otsTransactionCancelOrderId, String otsTransactionCancelTrackingId, String otsTransactionCancelOrderStatus, String otsTransactionCancelAmount) {
        this.otsTransactionCancelId = otsTransactionCancelId;
        this.otsTransactionCancelOrderId = otsTransactionCancelOrderId;
        this.otsTransactionCancelTrackingId = otsTransactionCancelTrackingId;
        this.otsTransactionCancelOrderStatus = otsTransactionCancelOrderStatus;
        this.otsTransactionCancelAmount = otsTransactionCancelAmount;
    }

    public Integer getOtsTransactionCancelId() {
        return otsTransactionCancelId;
    }

    public void setOtsTransactionCancelId(Integer otsTransactionCancelId) {
        this.otsTransactionCancelId = otsTransactionCancelId;
    }

    public String getOtsTransactionCancelOrderId() {
        return otsTransactionCancelOrderId;
    }

    public void setOtsTransactionCancelOrderId(String otsTransactionCancelOrderId) {
        this.otsTransactionCancelOrderId = otsTransactionCancelOrderId;
    }

    public String getOtsTransactionCancelTrackingId() {
        return otsTransactionCancelTrackingId;
    }

    public void setOtsTransactionCancelTrackingId(String otsTransactionCancelTrackingId) {
        this.otsTransactionCancelTrackingId = otsTransactionCancelTrackingId;
    }

    public String getOtsTransactionCancelOrderStatus() {
        return otsTransactionCancelOrderStatus;
    }

    public void setOtsTransactionCancelOrderStatus(String otsTransactionCancelOrderStatus) {
        this.otsTransactionCancelOrderStatus = otsTransactionCancelOrderStatus;
    }

    public String getOtsTransactionCancelAmount() {
        return otsTransactionCancelAmount;
    }

    public void setOtsTransactionCancelAmount(String otsTransactionCancelAmount) {
        this.otsTransactionCancelAmount = otsTransactionCancelAmount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsTransactionCancelId != null ? otsTransactionCancelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsPaymentTransactionCancelRecords)) {
            return false;
        }
        OtsPaymentTransactionCancelRecords other = (OtsPaymentTransactionCancelRecords) object;
        if ((this.otsTransactionCancelId == null && other.otsTransactionCancelId != null) || (this.otsTransactionCancelId != null && !this.otsTransactionCancelId.equals(other.otsTransactionCancelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsPaymentTransactionCancelRecords[ otsTransactionCancelId=" + otsTransactionCancelId + " ]";
    }
    
}
