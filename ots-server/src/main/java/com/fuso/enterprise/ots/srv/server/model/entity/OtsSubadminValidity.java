/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "ots_subadmin_validity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsSubadminValidity.findAll", query = "SELECT o FROM OtsSubadminValidity o"),
    @NamedQuery(name = "OtsSubadminValidity.findByOtsValidityId", query = "SELECT o FROM OtsSubadminValidity o WHERE o.otsValidityId = :otsValidityId"),
    @NamedQuery(name = "OtsSubadminValidity.findByOtsValidityStart", query = "SELECT o FROM OtsSubadminValidity o WHERE o.otsValidityStart = :otsValidityStart"),
    @NamedQuery(name = "OtsSubadminValidity.findByOtsValidityEnd", query = "SELECT o FROM OtsSubadminValidity o WHERE o.otsValidityEnd = :otsValidityEnd"),
    @NamedQuery(name = "OtsSubadminValidity.findByOtsDistributorCount", query = "SELECT o FROM OtsSubadminValidity o WHERE o.otsDistributorCount = :otsDistributorCount"),
    @NamedQuery(name = "OtsSubadminValidity.findByOtsTransactionCharges", query = "SELECT o FROM OtsSubadminValidity o WHERE o.otsTransactionCharges = :otsTransactionCharges")})
public class OtsSubadminValidity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_validity_id")
    private Integer otsValidityId;
    @Column(name = "ots_validity_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsValidityStart;
    @Column(name = "ots_validity_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsValidityEnd;
    @Column(name = "ots_distributor_count")
    private Integer otsDistributorCount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ots_transaction_charges")
    private BigDecimal otsTransactionCharges;
    @JoinColumn(name = "ots_account_id", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts otsAccountId;

    public OtsSubadminValidity() {
    }

    public OtsSubadminValidity(Integer otsValidityId) {
        this.otsValidityId = otsValidityId;
    }

    public Integer getOtsValidityId() {
        return otsValidityId;
    }

    public void setOtsValidityId(Integer otsValidityId) {
        this.otsValidityId = otsValidityId;
    }

    public Date getOtsValidityStart() {
        return otsValidityStart;
    }

    public void setOtsValidityStart(Date otsValidityStart) {
        this.otsValidityStart = otsValidityStart;
    }

    public Date getOtsValidityEnd() {
        return otsValidityEnd;
    }

    public void setOtsValidityEnd(Date otsValidityEnd) {
        this.otsValidityEnd = otsValidityEnd;
    }

    public Integer getOtsDistributorCount() {
        return otsDistributorCount;
    }

    public void setOtsDistributorCount(Integer otsDistributorCount) {
        this.otsDistributorCount = otsDistributorCount;
    }

    public BigDecimal getOtsTransactionCharges() {
        return otsTransactionCharges;
    }

    public void setOtsTransactionCharges(BigDecimal otsTransactionCharges) {
        this.otsTransactionCharges = otsTransactionCharges;
    }

    public Useraccounts getOtsAccountId() {
        return otsAccountId;
    }

    public void setOtsAccountId(Useraccounts otsAccountId) {
        this.otsAccountId = otsAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsValidityId != null ? otsValidityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsSubadminValidity)) {
            return false;
        }
        OtsSubadminValidity other = (OtsSubadminValidity) object;
        if ((this.otsValidityId == null && other.otsValidityId != null) || (this.otsValidityId != null && !this.otsValidityId.equals(other.otsValidityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsSubadminValidity[ otsValidityId=" + otsValidityId + " ]";
    }
    
}
