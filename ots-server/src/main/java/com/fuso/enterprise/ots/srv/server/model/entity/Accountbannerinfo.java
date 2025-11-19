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
@Table(name = "accountbannerinfo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accountbannerinfo.findAll", query = "SELECT a FROM Accountbannerinfo a"),
    @NamedQuery(name = "Accountbannerinfo.findByAccountBannerId", query = "SELECT a FROM Accountbannerinfo a WHERE a.accountBannerId = :accountBannerId"),
    @NamedQuery(name = "Accountbannerinfo.findByBannerImage", query = "SELECT a FROM Accountbannerinfo a WHERE a.bannerImage = :bannerImage"),
    @NamedQuery(name = "Accountbannerinfo.findByBannerImageLink", query = "SELECT a FROM Accountbannerinfo a WHERE a.bannerImageLink = :bannerImageLink"),
    @NamedQuery(name = "Accountbannerinfo.findByBannerContent", query = "SELECT a FROM Accountbannerinfo a WHERE a.bannerContent = :bannerContent"),
    @NamedQuery(name = "Accountbannerinfo.findBySort", query = "SELECT a FROM Accountbannerinfo a WHERE a.sort = :sort"),
    @NamedQuery(name = "Accountbannerinfo.findByDateCreated", query = "SELECT a FROM Accountbannerinfo a WHERE a.dateCreated = :dateCreated"),
    @NamedQuery(name = "Accountbannerinfo.findByDateModified", query = "SELECT a FROM Accountbannerinfo a WHERE a.dateModified = :dateModified"),
    @NamedQuery(name = "Accountbannerinfo.findByUserCreated", query = "SELECT a FROM Accountbannerinfo a WHERE a.userCreated = :userCreated"),
    @NamedQuery(name = "Accountbannerinfo.findByUserModified", query = "SELECT a FROM Accountbannerinfo a WHERE a.userModified = :userModified")})
public class Accountbannerinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "account_banner_id")
    private Integer accountBannerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "banner_image")
    private String bannerImage;
    @Size(max = 255)
    @Column(name = "banner_image_link")
    private String bannerImageLink;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "banner_content")
    private String bannerContent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "sort")
    private String sort;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @Column(name = "user_created")
    private Integer userCreated;
    @Column(name = "user_modified")
    private Integer userModified;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts accountId;

    public Accountbannerinfo() {
    }

    public Accountbannerinfo(Integer accountBannerId) {
        this.accountBannerId = accountBannerId;
    }

    public Accountbannerinfo(Integer accountBannerId, String bannerImage, String bannerContent, String sort, Date dateCreated, Date dateModified) {
        this.accountBannerId = accountBannerId;
        this.bannerImage = bannerImage;
        this.bannerContent = bannerContent;
        this.sort = sort;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public Integer getAccountBannerId() {
        return accountBannerId;
    }

    public void setAccountBannerId(Integer accountBannerId) {
        this.accountBannerId = accountBannerId;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getBannerImageLink() {
        return bannerImageLink;
    }

    public void setBannerImageLink(String bannerImageLink) {
        this.bannerImageLink = bannerImageLink;
    }

    public String getBannerContent() {
        return bannerContent;
    }

    public void setBannerContent(String bannerContent) {
        this.bannerContent = bannerContent;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Integer userCreated) {
        this.userCreated = userCreated;
    }

    public Integer getUserModified() {
        return userModified;
    }

    public void setUserModified(Integer userModified) {
        this.userModified = userModified;
    }

    public Useraccounts getAccountId() {
        return accountId;
    }

    public void setAccountId(Useraccounts accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountBannerId != null ? accountBannerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accountbannerinfo)) {
            return false;
        }
        Accountbannerinfo other = (Accountbannerinfo) object;
        if ((this.accountBannerId == null && other.accountBannerId != null) || (this.accountBannerId != null && !this.accountBannerId.equals(other.accountBannerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.Accountbannerinfo[ accountBannerId=" + accountBannerId + " ]";
    }

}
