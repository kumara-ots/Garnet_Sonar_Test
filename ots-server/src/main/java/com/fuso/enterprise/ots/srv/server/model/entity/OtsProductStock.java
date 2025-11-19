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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_product_stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsProductStock.findAll", query = "SELECT o FROM OtsProductStock o"),
    @NamedQuery(name = "OtsProductStock.findByOtsProductStockId", query = "SELECT o FROM OtsProductStock o WHERE o.otsProductStockId = :otsProductStockId"),
    @NamedQuery(name = "OtsProductStock.findByOtsProductStockActQty", query = "SELECT o FROM OtsProductStock o WHERE o.otsProductStockActQty = :otsProductStockActQty"),
    @NamedQuery(name = "OtsProductStock.findByOtsProductStockStatus", query = "SELECT o FROM OtsProductStock o WHERE o.otsProductStockStatus = :otsProductStockStatus"),
    @NamedQuery(name = "OtsProductStock.findByOtsProductStockTimestamp", query = "SELECT o FROM OtsProductStock o WHERE o.otsProductStockTimestamp = :otsProductStockTimestamp"),
    @NamedQuery(name = "OtsProductStock.findByOtsProductStockCreated", query = "SELECT o FROM OtsProductStock o WHERE o.otsProductStockCreated = :otsProductStockCreated")})
public class OtsProductStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_product_stock_id")
    private Integer otsProductStockId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ots_product_stock_act_qty")
    private String otsProductStockActQty;
    @Size(max = 45)
    @Column(name = "ots_product_stock_status")
    private String otsProductStockStatus;
    @Column(name = "ots_product_stock_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsProductStockTimestamp;
    @Column(name = "ots_product_stock_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsProductStockCreated;
    @JoinColumn(name = "ots_product_id", referencedColumnName = "ots_product_id")
    @ManyToOne
    private OtsProduct otsProductId;
    @JoinColumn(name = "ots_users_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsUsersId;

    public OtsProductStock() {
    }

    public Integer getOtsProductStockId() {
		return otsProductStockId;
	}

	public void setOtsProductStockId(Integer otsProductStockId) {
		this.otsProductStockId = otsProductStockId;
	}

	public String getOtsProductStockActQty() {
		return otsProductStockActQty;
	}

	public void setOtsProductStockActQty(String otsProductStockActQty) {
		this.otsProductStockActQty = otsProductStockActQty;
	}

	public String getOtsProductStockStatus() {
		return otsProductStockStatus;
	}

	public void setOtsProductStockStatus(String otsProductStockStatus) {
		this.otsProductStockStatus = otsProductStockStatus;
	}

	public Date getOtsProductStockTimestamp() {
		return otsProductStockTimestamp;
	}

	public void setOtsProductStockTimestamp(Date otsProductStockTimestamp) {
		this.otsProductStockTimestamp = otsProductStockTimestamp;
	}

	public Date getOtsProductStockCreated() {
		return otsProductStockCreated;
	}

	public void setOtsProductStockCreated(Date otsProductStockCreated) {
		this.otsProductStockCreated = otsProductStockCreated;
	}

	public OtsProduct getOtsProductId() {
        return otsProductId;
    }

    public void setOtsProductId(OtsProduct otsProductId) {
        this.otsProductId = otsProductId;
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
        hash += (otsProductStockId != null ? otsProductStockId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsProductStock)) {
            return false;
        }
        OtsProductStock other = (OtsProductStock) object;
        if ((this.otsProductStockId == null && other.otsProductStockId != null) || (this.otsProductStockId != null && !this.otsProductStockId.equals(other.otsProductStockId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsProductStock[ otsProdcutStockId=" + otsProductStockId + " ]";
    }
    
}
