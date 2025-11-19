/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.UUID;

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
@Table(name = "ots_seller")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsSeller.findAll", query = "SELECT o FROM OtsSeller o"),
    @NamedQuery(name = "OtsSeller.findByOtsSellerId", query = "SELECT o FROM OtsSeller o WHERE o.otsSellerId = :otsSellerId"),
    @NamedQuery(name = "OtsSeller.findByOtsSellerName", query = "SELECT o FROM OtsSeller o WHERE o.otsSellerName = :otsSellerName"),
    @NamedQuery(name = "OtsSeller.findByOtsSellerAddress", query = "SELECT o FROM OtsSeller o WHERE o.otsSellerAddress = :otsSellerAddress"),
    @NamedQuery(name = "OtsSeller.findByOtsSellerContactNumber", query = "SELECT o FROM OtsSeller o WHERE o.otsSellerContactNumber = :otsSellerContactNumber"),
    @NamedQuery(name = "OtsSeller.findByOtsSellerEmail", query = "SELECT o FROM OtsSeller o WHERE o.otsSellerEmail = :otsSellerEmail"),
    @NamedQuery(name = "OtsSeller.findByOtsSellerPincode", query = "SELECT o FROM OtsSeller o WHERE o.otsSellerPincode = :otsSellerPincode"),
    @NamedQuery(name = "OtsSeller.findByOtsSellerLat", query = "SELECT o FROM OtsSeller o WHERE o.otsSellerLat = :otsSellerLat"),
    @NamedQuery(name = "OtsSeller.findByOtsSellerLong", query = "SELECT o FROM OtsSeller o WHERE o.otsSellerLong = :otsSellerLong"),
    @NamedQuery(name = "OtsSeller.findByCreatedUser", query = "SELECT o FROM OtsSeller o WHERE o.createdUser = :createdUser")})
public class OtsSeller implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ots_seller_id")
    private UUID otsSellerId;
    @Size(max = 45)
    @Column(name = "ots_seller_name")
    private String otsSellerName;
    @Size(max = 500)
    @Column(name = "ots_seller_address")
    private String otsSellerAddress;
    @Size(max = 45)
    @Column(name = "ots_seller_contact_number")
    private String otsSellerContactNumber;
    @Size(max = 255)
    @Column(name = "ots_seller_email")
    private String otsSellerEmail;
    @Size(max = 45)
    @Column(name = "ots_seller_pincode")
    private String otsSellerPincode;
    @Size(max = 45)
    @Column(name = "ots_seller_lat")
    private String otsSellerLat;
    @Size(max = 45)
    @Column(name = "ots_seller_long")
    private String otsSellerLong;
    @Column(name = "created_user")
    private Integer createdUser;
    @JoinColumn(name = "ots_distributor_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsDistributorId;

    public OtsSeller() {
    }

    public OtsSeller(UUID otsSellerId) {
        this.otsSellerId = otsSellerId;
    }

    public UUID getOtsSellerId() {
        return otsSellerId;
    }

    public void setOtsSellerId(UUID otsSellerId) {
        this.otsSellerId = otsSellerId;
    }

    public String getOtsSellerName() {
        return otsSellerName;
    }

    public void setOtsSellerName(String otsSellerName) {
        this.otsSellerName = otsSellerName;
    }

    public String getOtsSellerAddress() {
        return otsSellerAddress;
    }

    public void setOtsSellerAddress(String otsSellerAddress) {
        this.otsSellerAddress = otsSellerAddress;
    }

    public String getOtsSellerContactNumber() {
        return otsSellerContactNumber;
    }

    public void setOtsSellerContactNumber(String otsSellerContactNumber) {
        this.otsSellerContactNumber = otsSellerContactNumber;
    }

    public String getOtsSellerEmail() {
        return otsSellerEmail;
    }

    public void setOtsSellerEmail(String otsSellerEmail) {
        this.otsSellerEmail = otsSellerEmail;
    }

    public String getOtsSellerPincode() {
        return otsSellerPincode;
    }

    public void setOtsSellerPincode(String otsSellerPincode) {
        this.otsSellerPincode = otsSellerPincode;
    }

    public String getOtsSellerLat() {
        return otsSellerLat;
    }

    public void setOtsSellerLat(String otsSellerLat) {
        this.otsSellerLat = otsSellerLat;
    }

    public String getOtsSellerLong() {
        return otsSellerLong;
    }

    public void setOtsSellerLong(String otsSellerLong) {
        this.otsSellerLong = otsSellerLong;
    }

    public Integer getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
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
        hash += (otsSellerId != null ? otsSellerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsSeller)) {
            return false;
        }
        OtsSeller other = (OtsSeller) object;
        if ((this.otsSellerId == null && other.otsSellerId != null) || (this.otsSellerId != null && !this.otsSellerId.equals(other.otsSellerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsSeller[ otsSellerId=" + otsSellerId + " ]";
    }
    
}
