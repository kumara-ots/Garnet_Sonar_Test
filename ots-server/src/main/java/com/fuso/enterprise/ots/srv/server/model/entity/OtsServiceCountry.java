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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "ots_service_country")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServiceCountry.findAll", query = "SELECT o FROM OtsServiceCountry o"),
    @NamedQuery(name = "OtsServiceCountry.findByOtsCountryId", query = "SELECT o FROM OtsServiceCountry o WHERE o.otsCountryId = :otsCountryId"),
    @NamedQuery(name = "OtsServiceCountry.findByOtsCountryCode", query = "SELECT o FROM OtsServiceCountry o WHERE o.otsCountryCode = :otsCountryCode"),
    @NamedQuery(name = "OtsServiceCountry.findByOtsCountryPhone", query = "SELECT o FROM OtsServiceCountry o WHERE o.otsCountryPhone = :otsCountryPhone"),
    @NamedQuery(name = "OtsServiceCountry.findByOtsCountryCurrencySymbol", query = "SELECT o FROM OtsServiceCountry o WHERE o.otsCountryCurrencySymbol = :otsCountryCurrencySymbol"),
    @NamedQuery(name = "OtsServiceCountry.findByOtsCountryCurrency", query = "SELECT o FROM OtsServiceCountry o WHERE o.otsCountryCurrency = :otsCountryCurrency")})
public class OtsServiceCountry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_country_id")
    private Integer otsCountryId;
    @Size(max = 45)
    @Column(name = "ots_country_code")
    private String otsCountryCode;
    @Size(max = 45)
    @Column(name = "ots_country_name")
    private String otsCountryName;
    @Column(name = "ots_country_phone")
    private Integer otsCountryPhone;
    @Size(max = 45)
    @Column(name = "ots_country_currency_symbol")
    private String otsCountryCurrencySymbol;
    @Size(max = 45)
    @Column(name = "ots_country_currency")
    private String otsCountryCurrency;

    public OtsServiceCountry() {
    }

    public OtsServiceCountry(Integer otsCountryId) {
        this.otsCountryId = otsCountryId;
    }

    public Integer getOtsCountryId() {
		return otsCountryId;
	}

	public void setOtsCountryId(Integer otsCountryId) {
		this.otsCountryId = otsCountryId;
	}

	public String getOtsCountryCode() {
		return otsCountryCode;
	}

	public void setOtsCountryCode(String otsCountryCode) {
		this.otsCountryCode = otsCountryCode;
	}

	public String getOtsCountryName() {
		return otsCountryName;
	}

	public void setOtsCountryName(String otsCountryName) {
		this.otsCountryName = otsCountryName;
	}

	public Integer getOtsCountryPhone() {
		return otsCountryPhone;
	}

	public void setOtsCountryPhone(Integer otsCountryPhone) {
		this.otsCountryPhone = otsCountryPhone;
	}

	public String getOtsCountryCurrencySymbol() {
		return otsCountryCurrencySymbol;
	}

	public void setOtsCountryCurrencySymbol(String otsCountryCurrencySymbol) {
		this.otsCountryCurrencySymbol = otsCountryCurrencySymbol;
	}

	public String getOtsCountryCurrency() {
		return otsCountryCurrency;
	}

	public void setOtsCountryCurrency(String otsCountryCurrency) {
		this.otsCountryCurrency = otsCountryCurrency;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (otsCountryId != null ? otsCountryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServiceCountry)) {
            return false;
        }
        OtsServiceCountry other = (OtsServiceCountry) object;
        if ((this.otsCountryId == null && other.otsCountryId != null) || (this.otsCountryId != null && !this.otsCountryId.equals(other.otsCountryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceCountry[ otsCountryId=" + otsCountryId + " ]";
    }
    
}
