/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bharath
 */
@Entity
@Table(name = "ots_registration_transaction_cancel_records")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsRegistrationTransactionCancelRecords.findAll", query = "SELECT o FROM OtsRegistrationTransactionCancelRecords o"),
    @NamedQuery(name = "OtsRegistrationTransactionCancelRecords.findByOtsRegistrationTransactionCancelId", query = "SELECT o FROM OtsRegistrationTransactionCancelRecords o WHERE o.otsRegistrationTransactionCancelId = :otsRegistrationTransactionCancelId"),
    @NamedQuery(name = "OtsRegistrationTransactionCancelRecords.findByOtsRegistrationTransactionId", query = "SELECT o FROM OtsRegistrationTransactionCancelRecords o WHERE o.otsRegistrationTransactionId = :otsRegistrationTransactionId"),
    @NamedQuery(name = "OtsRegistrationTransactionCancelRecords.findByOtsRegistrationTrackingId", query = "SELECT o FROM OtsRegistrationTransactionCancelRecords o WHERE o.otsRegistrationTrackingId = :otsRegistrationTrackingId"),
    @NamedQuery(name = "OtsRegistrationTransactionCancelRecords.findByOtsRegistrationTransactionStatus", query = "SELECT o FROM OtsRegistrationTransactionCancelRecords o WHERE o.otsRegistrationTransactionStatus = :otsRegistrationTransactionStatus"),
    @NamedQuery(name = "OtsRegistrationTransactionCancelRecords.findByOtsRegistrationAmount", query = "SELECT o FROM OtsRegistrationTransactionCancelRecords o WHERE o.otsRegistrationAmount = :otsRegistrationAmount"),
    @NamedQuery(name = "OtsRegistrationTransactionCancelRecords.findByOtsRegistrationTransactionDate", query = "SELECT o FROM OtsRegistrationTransactionCancelRecords o WHERE o.otsRegistrationTransactionDate = :otsRegistrationTransactionDate")})
public class OtsRegistrationTransactionCancelRecords implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_registration_transaction_cancel_id")
    private Integer otsRegistrationTransactionCancelId;
    @Column(name = "ots_registration_transaction_id")
    private String otsRegistrationTransactionId;
    @Column(name = "ots_registration_tracking_id")
    private String otsRegistrationTrackingId;
    @Column(name = "ots_registration_transaction_status")
    private String otsRegistrationTransactionStatus;
    @Column(name = "ots_registration_amount")
    private String otsRegistrationAmount;
    @Column(name = "ots_registration_transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsRegistrationTransactionDate;
    @JoinColumn(name = "ots_users_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsUsersId;

    public OtsRegistrationTransactionCancelRecords() {
    }

    public OtsRegistrationTransactionCancelRecords(Integer otsRegistrationTransactionCancelId) {
        this.otsRegistrationTransactionCancelId = otsRegistrationTransactionCancelId;
    }

    public Integer getOtsRegistrationTransactionCancelId() {
        return otsRegistrationTransactionCancelId;
    }

    public void setOtsRegistrationTransactionCancelId(Integer otsRegistrationTransactionCancelId) {
        this.otsRegistrationTransactionCancelId = otsRegistrationTransactionCancelId;
    }

    public String getOtsRegistrationTransactionId() {
        return otsRegistrationTransactionId;
    }

    public void setOtsRegistrationTransactionId(String otsRegistrationTransactionId) {
        this.otsRegistrationTransactionId = otsRegistrationTransactionId;
    }

    public String getOtsRegistrationTrackingId() {
        return otsRegistrationTrackingId;
    }

    public void setOtsRegistrationTrackingId(String otsRegistrationTrackingId) {
        this.otsRegistrationTrackingId = otsRegistrationTrackingId;
    }

    public String getOtsRegistrationTransactionStatus() {
        return otsRegistrationTransactionStatus;
    }

    public void setOtsRegistrationTransactionStatus(String otsRegistrationTransactionStatus) {
        this.otsRegistrationTransactionStatus = otsRegistrationTransactionStatus;
    }

    public String getOtsRegistrationAmount() {
        return otsRegistrationAmount;
    }

    public void setOtsRegistrationAmount(String otsRegistrationAmount) {
        this.otsRegistrationAmount = otsRegistrationAmount;
    }

    public Date getOtsRegistrationTransactionDate() {
        return otsRegistrationTransactionDate;
    }

    public void setOtsRegistrationTransactionDate(Date otsRegistrationTransactionDate) {
        this.otsRegistrationTransactionDate = otsRegistrationTransactionDate;
    }

    public OtsUsers getOtsUsersId() {
        return otsUsersId;
    }

    public void setOtsUsersId(OtsUsers otsUsersId) {
        this.otsUsersId = otsUsersId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsRegistrationTransactionCancelId != null ? otsRegistrationTransactionCancelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsRegistrationTransactionCancelRecords)) {
            return false;
        }
        OtsRegistrationTransactionCancelRecords other = (OtsRegistrationTransactionCancelRecords) object;
        if ((this.otsRegistrationTransactionCancelId == null && other.otsRegistrationTransactionCancelId != null) || (this.otsRegistrationTransactionCancelId != null && !this.otsRegistrationTransactionCancelId.equals(other.otsRegistrationTransactionCancelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsRegistrationTransactionCancelRecords[ otsRegistrationTransactionCancelId=" + otsRegistrationTransactionCancelId + " ]";
    }
    
}
