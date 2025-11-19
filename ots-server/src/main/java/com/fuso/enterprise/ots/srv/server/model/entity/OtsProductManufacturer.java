package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity for ots_product_manufacturer table
 * 
 * @author
 */
@Entity
@Table(name = "ots_product_manufacturer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsProductManufacturer.findAll", query = "SELECT m FROM OtsProductManufacturer m"),
    @NamedQuery(name = "OtsProductManufacturer.findById", query = "SELECT m FROM OtsProductManufacturer m WHERE m.otsProductManufacturerId = :otsProductManufacturerId"),
    @NamedQuery(name = "OtsProductManufacturer.findByName", query = "SELECT m FROM OtsProductManufacturer m WHERE m.otsProductManufacturerName = :otsProductManufacturerName")
})
public class OtsProductManufacturer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // maps SERIAL in Postgres
    @Basic(optional = false)
    @Column(name = "ots_product_manufacturer_id")
    private Integer otsProductManufacturerId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ots_product_manufacturer_name")
    private String otsProductManufacturerName;

    public OtsProductManufacturer() {
    }

    public OtsProductManufacturer(Integer otsProductManufacturerId) {
        this.otsProductManufacturerId = otsProductManufacturerId;
    }

    public OtsProductManufacturer(Integer otsProductManufacturerId, String otsProductManufacturerName) {
        this.otsProductManufacturerId = otsProductManufacturerId;
        this.otsProductManufacturerName = otsProductManufacturerName;
    }

    public Integer getOtsProductManufacturerId() {
        return otsProductManufacturerId;
    }

    public void setOtsProductManufacturerId(Integer otsProductManufacturerId) {
        this.otsProductManufacturerId = otsProductManufacturerId;
    }

    public String getOtsProductManufacturerName() {
        return otsProductManufacturerName;
    }

    public void setOtsProductManufacturerName(String otsProductManufacturerName) {
        this.otsProductManufacturerName = otsProductManufacturerName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsProductManufacturerId != null ? otsProductManufacturerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OtsProductManufacturer)) {
            return false;
        }
        OtsProductManufacturer other = (OtsProductManufacturer) object;
        if ((this.otsProductManufacturerId == null && other.otsProductManufacturerId != null) || 
            (this.otsProductManufacturerId != null && !this.otsProductManufacturerId.equals(other.otsProductManufacturerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsProductManufacturer[ otsProductManufacturerId=" + otsProductManufacturerId + " ]";
    }
}
