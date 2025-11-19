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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kumara BL
 */
@Entity
@Table(name = "ots_service_rating_review")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServiceRatingReview.findAll", query = "SELECT o FROM OtsServiceRatingReview o"),
    @NamedQuery(name = "OtsServiceRatingReview.findByOtsServiceRatingReviewId", query = "SELECT o FROM OtsServiceRatingReview o WHERE o.otsServiceRatingReviewId = :otsServiceRatingReviewId"),
    @NamedQuery(name = "OtsServiceRatingReview.findByOtsServiceRatingReviewComment", query = "SELECT o FROM OtsServiceRatingReview o WHERE o.otsServiceRatingReviewComment = :otsServiceRatingReviewComment"),
    @NamedQuery(name = "OtsServiceRatingReview.findByOtsReviewRating", query = "SELECT o FROM OtsServiceRatingReview o WHERE o.otsReviewRating = :otsReviewRating"),
    @NamedQuery(name = "OtsServiceRatingReview.findByOtsRatingReviewTittle", query = "SELECT o FROM OtsServiceRatingReview o WHERE o.otsRatingReviewTittle = :otsRatingReviewTittle"),
    @NamedQuery(name = "OtsServiceRatingReview.findByOtsRatingReviewAddedDate", query = "SELECT o FROM OtsServiceRatingReview o WHERE o.otsRatingReviewAddedDate = :otsRatingReviewAddedDate"),
    @NamedQuery(name = "OtsServiceRatingReview.findByOtsRatingReviewStatus", query = "SELECT o FROM OtsServiceRatingReview o WHERE o.otsRatingReviewStatus = :otsRatingReviewStatus")})
public class OtsServiceRatingReview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_service_rating_review_id")
    private Integer otsServiceRatingReviewId;
    @Column(name = "ots_service_rating_review_comment")
    private String otsServiceRatingReviewComment;
    @Column(name = "ots_review_rating")
    private Integer otsReviewRating;
    @Column(name = "ots_rating_review_tittle")
    private String otsRatingReviewTittle;
    @Column(name = "ots_rating_review_added_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsRatingReviewAddedDate;
    @Column(name = "ots_rating_review_status")
    private String otsRatingReviewStatus;
    @JoinColumn(name = "ots_customer_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsCustomerId;
    @JoinColumn(name = "ots_service_id", referencedColumnName = "ots_service_id")
    @ManyToOne(optional = false)
    private OtsService otsServiceId;
    @JoinColumn(name = "ots_service_order_id", referencedColumnName = "ots_service_order_id")
    @ManyToOne
    private OtsServiceOrder otsServiceOrderId;

    public OtsServiceRatingReview() {
    }

    public OtsServiceRatingReview(Integer otsServiceRatingReviewId) {
        this.otsServiceRatingReviewId = otsServiceRatingReviewId;
    }

    public Integer getOtsServiceRatingReviewId() {
        return otsServiceRatingReviewId;
    }

    public void setOtsServiceRatingReviewId(Integer otsServiceRatingReviewId) {
        this.otsServiceRatingReviewId = otsServiceRatingReviewId;
    }

    public String getOtsServiceRatingReviewComment() {
        return otsServiceRatingReviewComment;
    }

    public void setOtsServiceRatingReviewComment(String otsServiceRatingReviewComment) {
        this.otsServiceRatingReviewComment = otsServiceRatingReviewComment;
    }

    public Integer getOtsReviewRating() {
        return otsReviewRating;
    }

    public void setOtsReviewRating(Integer otsReviewRating) {
        this.otsReviewRating = otsReviewRating;
    }

    public String getOtsRatingReviewTittle() {
        return otsRatingReviewTittle;
    }

    public void setOtsRatingReviewTittle(String otsRatingReviewTittle) {
        this.otsRatingReviewTittle = otsRatingReviewTittle;
    }

    public Date getOtsRatingReviewAddedDate() {
        return otsRatingReviewAddedDate;
    }

    public void setOtsRatingReviewAddedDate(Date otsRatingReviewAddedDate) {
        this.otsRatingReviewAddedDate = otsRatingReviewAddedDate;
    }

    public String getOtsRatingReviewStatus() {
        return otsRatingReviewStatus;
    }

    public void setOtsRatingReviewStatus(String otsRatingReviewStatus) {
        this.otsRatingReviewStatus = otsRatingReviewStatus;
    }

    public OtsUsers getOtsCustomerId() {
        return otsCustomerId;
    }

    public void setOtsCustomerId(OtsUsers otsCustomerId) {
        this.otsCustomerId = otsCustomerId;
    }

    public OtsService getOtsServiceId() {
        return otsServiceId;
    }

    public void setOtsServiceId(OtsService otsServiceId) {
        this.otsServiceId = otsServiceId;
    }

    public OtsServiceOrder getOtsServiceOrderId() {
        return otsServiceOrderId;
    }

    public void setOtsServiceOrderId(OtsServiceOrder otsServiceOrderId) {
        this.otsServiceOrderId = otsServiceOrderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsServiceRatingReviewId != null ? otsServiceRatingReviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServiceRatingReview)) {
            return false;
        }
        OtsServiceRatingReview other = (OtsServiceRatingReview) object;
        if ((this.otsServiceRatingReviewId == null && other.otsServiceRatingReviewId != null) || (this.otsServiceRatingReviewId != null && !this.otsServiceRatingReviewId.equals(other.otsServiceRatingReviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceRatingReview[ otsServiceRatingReviewId=" + otsServiceRatingReviewId + " ]";
    }
    
}
