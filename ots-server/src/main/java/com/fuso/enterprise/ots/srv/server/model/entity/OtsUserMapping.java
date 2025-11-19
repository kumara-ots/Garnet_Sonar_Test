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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_user_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsUserMapping.findAll", query = "SELECT o FROM OtsUserMapping o"),
    @NamedQuery(name = "OtsUserMapping.findByOtsUserMappingId", query = "SELECT o FROM OtsUserMapping o WHERE o.otsUserMappingId = :otsUserMappingId"),
    @NamedQuery(name = "OtsUserMapping.findByOtsMappedTo", query = "SELECT o FROM OtsUserMapping o WHERE o.otsMappedTo = :otsMappedTo"),
    @NamedQuery(name = "OtsUserMapping.findByOtsUserMappingTimestamp", query = "SELECT o FROM OtsUserMapping o WHERE o.otsUserMappingTimestamp = :otsUserMappingTimestamp"),
    @NamedQuery(name = "OtsUserMapping.findByOtsUserMappingCreated", query = "SELECT o FROM OtsUserMapping o WHERE o.otsUserMappingCreated = :otsUserMappingCreated")})
public class OtsUserMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_user_mapping_id")
    private Integer otsUserMappingId;
    @Size(max = 36)
    @Column(name = "ots_mapped_to")
    private String otsMappedTo;
    @Column(name = "ots_user_mapping_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsUserMappingTimestamp;
    @Column(name = "ots_user_mapping_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsUserMappingCreated;
    @JoinColumn(name = "ots_users_id", referencedColumnName = "ots_users_id")
    @OneToOne
    private OtsUsers otsUsersId;

    public OtsUserMapping() {
    }

    public OtsUserMapping(Integer otsUserMappingId) {
        this.otsUserMappingId = otsUserMappingId;
    }

    public Integer getOtsUserMappingId() {
        return otsUserMappingId;
    }

    public void setOtsUserMappingId(Integer otsUserMappingId) {
        this.otsUserMappingId = otsUserMappingId;
    }

    public String getOtsMappedTo() {
        return otsMappedTo;
    }

    public void setOtsMappedTo(String otsMappedTo) {
        this.otsMappedTo = otsMappedTo;
    }

    public Date getOtsUserMappingTimestamp() {
        return otsUserMappingTimestamp;
    }

    public void setOtsUserMappingTimestamp(Date otsUserMappingTimestamp) {
        this.otsUserMappingTimestamp = otsUserMappingTimestamp;
    }

    public Date getOtsUserMappingCreated() {
        return otsUserMappingCreated;
    }

    public void setOtsUserMappingCreated(Date otsUserMappingCreated) {
        this.otsUserMappingCreated = otsUserMappingCreated;
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
        hash += (otsUserMappingId != null ? otsUserMappingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsUserMapping)) {
            return false;
        }
        OtsUserMapping other = (OtsUserMapping) object;
        if ((this.otsUserMappingId == null && other.otsUserMappingId != null) || (this.otsUserMappingId != null && !this.otsUserMappingId.equals(other.otsUserMappingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsUserMapping[ otsUserMappingId=" + otsUserMappingId + " ]";
    }
    
}
