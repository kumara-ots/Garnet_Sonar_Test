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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_distributor_banner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsDistributorBanner.findAll", query = "SELECT o FROM OtsDistributorBanner o"),
    @NamedQuery(name = "OtsDistributorBanner.findByOtsBannerId", query = "SELECT o FROM OtsDistributorBanner o WHERE o.otsBannerId = :otsBannerId"),
    @NamedQuery(name = "OtsDistributorBanner.findByOtsBannerImage", query = "SELECT o FROM OtsDistributorBanner o WHERE o.otsBannerImage = :otsBannerImage"),
    @NamedQuery(name = "OtsDistributorBanner.findByOtsBannerImageLink", query = "SELECT o FROM OtsDistributorBanner o WHERE o.otsBannerImageLink = :otsBannerImageLink"),
    @NamedQuery(name = "OtsDistributorBanner.findByOtsBannerContent", query = "SELECT o FROM OtsDistributorBanner o WHERE o.otsBannerContent = :otsBannerContent"),
    @NamedQuery(name = "OtsDistributorBanner.findByOtsBannerCreated", query = "SELECT o FROM OtsDistributorBanner o WHERE o.otsBannerCreated = :otsBannerCreated"),
    @NamedQuery(name = "OtsDistributorBanner.findByOtsBannerModified", query = "SELECT o FROM OtsDistributorBanner o WHERE o.otsBannerModified = :otsBannerModified")})
public class OtsDistributorBanner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_banner_id")
    private Integer otsBannerId;
    @Size(max = 250)
    @Column(name = "ots_banner_image")
    private String otsBannerImage;
    @Size(max = 250)
    @Column(name = "ots_banner_image_link")
    private String otsBannerImageLink;
    @Size(max = 250)
    @Column(name = "ots_banner_content")
    private String otsBannerContent;
    @Column(name = "ots_banner_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsBannerCreated;
    @Column(name = "ots_banner_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsBannerModified;
    @JoinColumn(name = "ots_distributor_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsDistributorId;

    public OtsDistributorBanner() {
    }

    public OtsDistributorBanner(Integer otsBannerId) {
        this.otsBannerId = otsBannerId;
    }

    public Integer getOtsBannerId() {
        return otsBannerId;
    }

    public void setOtsBannerId(Integer otsBannerId) {
        this.otsBannerId = otsBannerId;
    }

    public String getOtsBannerImage() {
        return otsBannerImage;
    }

    public void setOtsBannerImage(String otsBannerImage) {
        this.otsBannerImage = otsBannerImage;
    }

    public String getOtsBannerImageLink() {
        return otsBannerImageLink;
    }

    public void setOtsBannerImageLink(String otsBannerImageLink) {
        this.otsBannerImageLink = otsBannerImageLink;
    }

    public String getOtsBannerContent() {
        return otsBannerContent;
    }

    public void setOtsBannerContent(String otsBannerContent) {
        this.otsBannerContent = otsBannerContent;
    }

    public Date getOtsBannerCreated() {
        return otsBannerCreated;
    }

    public void setOtsBannerCreated(Date otsBannerCreated) {
        this.otsBannerCreated = otsBannerCreated;
    }

    public Date getOtsBannerModified() {
        return otsBannerModified;
    }

    public void setOtsBannerModified(Date otsBannerModified) {
        this.otsBannerModified = otsBannerModified;
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
        hash += (otsBannerId != null ? otsBannerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsDistributorBanner)) {
            return false;
        }
        OtsDistributorBanner other = (OtsDistributorBanner) object;
        if ((this.otsBannerId == null && other.otsBannerId != null) || (this.otsBannerId != null && !this.otsBannerId.equals(other.otsBannerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsDistributorBanner[ otsBannerId=" + otsBannerId + " ]";
    }
    
}
