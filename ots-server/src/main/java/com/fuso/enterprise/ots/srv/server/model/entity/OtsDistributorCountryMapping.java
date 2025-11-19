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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_distributor_country_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsDistributorCountryMapping.findAll", query = "SELECT o FROM OtsDistributorCountryMapping o"),
    @NamedQuery(name = "OtsDistributorCountryMapping.findByOtsDistributorCountryMappingId", query = "SELECT o FROM OtsDistributorCountryMapping o WHERE o.otsDistributorCountryMappingId = :otsDistributorCountryMappingId"),
    @NamedQuery(name = "OtsDistributorCountryMapping.findByOtsProductId", query = "SELECT o FROM OtsDistributorCountryMapping o WHERE o.otsProductId = :otsProductId"),
    @NamedQuery(name = "OtsDistributorCountryMapping.findByOtsCountryCode", query = "SELECT o FROM OtsDistributorCountryMapping o WHERE o.otsCountryCode = :otsCountryCode"),
    @NamedQuery(name = "OtsDistributorCountryMapping.findByOtsCountryName", query = "SELECT o FROM OtsDistributorCountryMapping o WHERE o.otsCountryName = :otsCountryName")})
public class OtsDistributorCountryMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_distributor_country_mapping_id")
    private Integer otsDistributorCountryMappingId;
    @Size(max = 45)
    @Column(name = "ots_product_id")
    private String otsProductId;
    @Size(max = 45)
    @Column(name = "ots_country_code")
    private String otsCountryCode;
    @Size(max = 45)
    @Column(name = "ots_country_name")
    private String otsCountryName;
    @JoinColumn(name = "ots_distributor_id", referencedColumnName = "ots_users_id")
    @ManyToOne(optional = false)
    private OtsUsers otsDistributorId;
    
    public OtsDistributorCountryMapping() {
    }
    
    public OtsDistributorCountryMapping(Integer otsDistributorCountryMappingId) {
        this.otsDistributorCountryMappingId = otsDistributorCountryMappingId;
    }

    public Integer getOtsDistributorCountryMappingId() {
		return otsDistributorCountryMappingId;
	}

	public void setOtsDistributorCountryMappingId(Integer otsDistributorCountryMappingId) {
		this.otsDistributorCountryMappingId = otsDistributorCountryMappingId;
	}

	public String getOtsProductId() {
		return otsProductId;
	}

	public void setOtsProductId(String otsProductId) {
		this.otsProductId = otsProductId;
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

	public OtsUsers getOtsDistributorId() {
		return otsDistributorId;
	}

	public void setOtsDistributorId(OtsUsers otsDistributorId) {
		this.otsDistributorId = otsDistributorId;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (otsDistributorCountryMappingId != null ? otsDistributorCountryMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsDistributorCountryMapping)) {
            return false;
        }
        OtsDistributorCountryMapping other = (OtsDistributorCountryMapping) object;
        if ((this.otsDistributorCountryMappingId == null && other.otsDistributorCountryMappingId != null) || (this.otsDistributorCountryMappingId != null && !this.otsDistributorCountryMappingId.equals(other.otsDistributorCountryMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsDistributorCountryMapping[ otsDistributorCountryMappingId=" + otsDistributorCountryMappingId + " ]";
    }
    
}
