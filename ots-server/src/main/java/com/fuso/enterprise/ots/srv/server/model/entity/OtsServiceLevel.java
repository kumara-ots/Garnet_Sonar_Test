/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kumara BL
 */
@Entity
@Table(name = "ots_service_level")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServiceLevel.findAll", query = "SELECT o FROM OtsServiceLevel o"),
    @NamedQuery(name = "OtsServiceLevel.findByOtsServiceLevelId", query = "SELECT o FROM OtsServiceLevel o WHERE o.otsServiceLevelId = :otsServiceLevelId"),
    @NamedQuery(name = "OtsServiceLevel.findByOtsServiceLevelName", query = "SELECT o FROM OtsServiceLevel o WHERE o.otsServiceLevelName = :otsServiceLevelName")})
public class OtsServiceLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_service_level_id")
    private Integer otsServiceLevelId;
    @Column(name = "ots_service_level_name")
    private String otsServiceLevelName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsServiceLevelId")
    private Collection<OtsService> otsServiceCollection;

    public OtsServiceLevel() {
    }

    public OtsServiceLevel(Integer otsServiceLevelId) {
        this.otsServiceLevelId = otsServiceLevelId;
    }

    public Integer getOtsServiceLevelId() {
        return otsServiceLevelId;
    }

    public void setOtsServiceLevelId(Integer otsServiceLevelId) {
        this.otsServiceLevelId = otsServiceLevelId;
    }

    public String getOtsServiceLevelName() {
        return otsServiceLevelName;
    }

    public void setOtsServiceLevelName(String otsServiceLevelName) {
        this.otsServiceLevelName = otsServiceLevelName;
    }

    @XmlTransient
    public Collection<OtsService> getOtsServiceCollection() {
        return otsServiceCollection;
    }

    public void setOtsServiceCollection(Collection<OtsService> otsServiceCollection) {
        this.otsServiceCollection = otsServiceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsServiceLevelId != null ? otsServiceLevelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServiceLevel)) {
            return false;
        }
        OtsServiceLevel other = (OtsServiceLevel) object;
        if ((this.otsServiceLevelId == null && other.otsServiceLevelId != null) || (this.otsServiceLevelId != null && !this.otsServiceLevelId.equals(other.otsServiceLevelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceLevel[ otsServiceLevelId=" + otsServiceLevelId + " ]";
    }
    
}
