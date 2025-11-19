/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_user_role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsUserRole.findAll", query = "SELECT o FROM OtsUserRole o"),
    @NamedQuery(name = "OtsUserRole.findByOtsUserRoleId", query = "SELECT o FROM OtsUserRole o WHERE o.otsUserRoleId = :otsUserRoleId"),
    @NamedQuery(name = "OtsUserRole.findByOtsUserRoleCode", query = "SELECT o FROM OtsUserRole o WHERE o.otsUserRoleCode = :otsUserRoleCode"),
    @NamedQuery(name = "OtsUserRole.findByOtsUserRoleName", query = "SELECT o FROM OtsUserRole o WHERE o.otsUserRoleName = :otsUserRoleName"),
    @NamedQuery(name = "OtsUserRole.findByOtsUserRoleStatus", query = "SELECT o FROM OtsUserRole o WHERE o.otsUserRoleStatus = :otsUserRoleStatus"),
    @NamedQuery(name = "OtsUserRole.findByOtsUserRoleTimestamp", query = "SELECT o FROM OtsUserRole o WHERE o.otsUserRoleTimestamp = :otsUserRoleTimestamp"),
    @NamedQuery(name = "OtsUserRole.findByOtsUserRoleCreated", query = "SELECT o FROM OtsUserRole o WHERE o.otsUserRoleCreated = :otsUserRoleCreated")})
public class OtsUserRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_user_role_id")
    private Integer otsUserRoleId;
    @Size(max = 45)
    @Column(name = "ots_user_role_code")
    private String otsUserRoleCode;
    @Size(max = 45)
    @Column(name = "ots_user_role_name")
    private String otsUserRoleName;
    @Size(max = 45)
    @Column(name = "ots_user_role_status")
    private String otsUserRoleStatus;
    @Column(name = "ots_user_role_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsUserRoleTimestamp;
    @Column(name = "ots_user_role_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsUserRoleCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsUserRoleId")
    private Collection<OtsUsers> otsUsersCollection;

    public OtsUserRole() {
    }

    public OtsUserRole(Integer otsUserRoleId) {
        this.otsUserRoleId = otsUserRoleId;
    }

    public Integer getOtsUserRoleId() {
        return otsUserRoleId;
    }

    public void setOtsUserRoleId(Integer otsUserRoleId) {
        this.otsUserRoleId = otsUserRoleId;
    }

    public String getOtsUserRoleCode() {
        return otsUserRoleCode;
    }

    public void setOtsUserRoleCode(String otsUserRoleCode) {
        this.otsUserRoleCode = otsUserRoleCode;
    }

    public String getOtsUserRoleName() {
        return otsUserRoleName;
    }

    public void setOtsUserRoleName(String otsUserRoleName) {
        this.otsUserRoleName = otsUserRoleName;
    }

    public String getOtsUserRoleStatus() {
        return otsUserRoleStatus;
    }

    public void setOtsUserRoleStatus(String otsUserRoleStatus) {
        this.otsUserRoleStatus = otsUserRoleStatus;
    }

    public Date getOtsUserRoleTimestamp() {
        return otsUserRoleTimestamp;
    }

    public void setOtsUserRoleTimestamp(Date otsUserRoleTimestamp) {
        this.otsUserRoleTimestamp = otsUserRoleTimestamp;
    }

    public Date getOtsUserRoleCreated() {
        return otsUserRoleCreated;
    }

    public void setOtsUserRoleCreated(Date otsUserRoleCreated) {
        this.otsUserRoleCreated = otsUserRoleCreated;
    }

    @XmlTransient
    public Collection<OtsUsers> getOtsUsersCollection() {
        return otsUsersCollection;
    }

    public void setOtsUsersCollection(Collection<OtsUsers> otsUsersCollection) {
        this.otsUsersCollection = otsUsersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsUserRoleId != null ? otsUserRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsUserRole)) {
            return false;
        }
        OtsUserRole other = (OtsUserRole) object;
        if ((this.otsUserRoleId == null && other.otsUserRoleId != null) || (this.otsUserRoleId != null && !this.otsUserRoleId.equals(other.otsUserRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsUserRole[ otsUserRoleId=" + otsUserRoleId + " ]";
    }
    
}
