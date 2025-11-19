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
@Table(name = "ots_rating_review")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsRatingReview.findAll", query = "SELECT o FROM OtsRatingReview o"),
    @NamedQuery(name = "OtsRatingReview.findByOtsRatingReviewId", query = "SELECT o FROM OtsRatingReview o WHERE o.otsRatingReviewId = :otsRatingReviewId"),
    @NamedQuery(name = "OtsRatingReview.findByOtsRatingReviewComment", query = "SELECT o FROM OtsRatingReview o WHERE o.otsRatingReviewComment = :otsRatingReviewComment"),
    @NamedQuery(name = "OtsRatingReview.findByOtsRatingReviewRating", query = "SELECT o FROM OtsRatingReview o WHERE o.otsRatingReviewRating = :otsRatingReviewRating"),
    @NamedQuery(name = "OtsRatingReview.findByOtsRatingReviewTitle", query = "SELECT o FROM OtsRatingReview o WHERE o.otsRatingReviewTitle = :otsRatingReviewTitle"),
    @NamedQuery(name = "OtsRatingReview.findByOtsRatingReviewAddedDate", query = "SELECT o FROM OtsRatingReview o WHERE o.otsRatingReviewAddedDate = :otsRatingReviewAddedDate"),
    @NamedQuery(name = "OtsRatingReview.findByOtsRatingReviewStatus", query = "SELECT o FROM OtsRatingReview o WHERE o.otsRatingReviewStatus = :otsRatingReviewStatus")})
public class OtsRatingReview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_rating_review_id")
    private Integer otsRatingReviewId;
    @Size(max = 500)
    @Column(name = "ots_rating_review_comment")
    private String otsRatingReviewComment;
    @Column(name = "ots_rating_review_rating")
    private Integer otsRatingReviewRating;
    @Size(max = 500)
    @Column(name = "ots_rating_review_title")
    private String otsRatingReviewTitle;
    @Column(name = "ots_rating_review_added_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsRatingReviewAddedDate;
    @Size(max = 45)
    @Column(name = "ots_rating_review_status")
    private String otsRatingReviewStatus;
    @JoinColumn(name = "ots_order_id", referencedColumnName = "ots_order_id")
    @ManyToOne
    private OtsOrder otsOrderId;
    @JoinColumn(name = "ots_product_id", referencedColumnName = "ots_product_id")
    @ManyToOne
    private OtsProduct otsProductId;
    @JoinColumn(name = "ots_customer_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsCustomerId;

    public OtsRatingReview() {
    }

    public OtsRatingReview(Integer otsRatingReviewId) {
        this.otsRatingReviewId = otsRatingReviewId;
    }

    public Integer getOtsRatingReviewId() {
        return otsRatingReviewId;
    }

    public void setOtsRatingReviewId(Integer otsRatingReviewId) {
        this.otsRatingReviewId = otsRatingReviewId;
    }

    public String getOtsRatingReviewComment() {
        return otsRatingReviewComment;
    }

    public void setOtsRatingReviewComment(String otsRatingReviewComment) {
        this.otsRatingReviewComment = otsRatingReviewComment;
    }

    public Integer getOtsRatingReviewRating() {
        return otsRatingReviewRating;
    }

    public void setOtsRatingReviewRating(Integer otsRatingReviewRating) {
        this.otsRatingReviewRating = otsRatingReviewRating;
    }

    public String getOtsRatingReviewTitle() {
        return otsRatingReviewTitle;
    }

    public void setOtsRatingReviewTitle(String otsRatingReviewTitle) {
        this.otsRatingReviewTitle = otsRatingReviewTitle;
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

    public OtsOrder getOtsOrderId() {
        return otsOrderId;
    }

    public void setOtsOrderId(OtsOrder otsOrderId) {
        this.otsOrderId = otsOrderId;
    }

    public OtsProduct getOtsProductId() {
        return otsProductId;
    }

    public void setOtsProductId(OtsProduct otsProductId) {
        this.otsProductId = otsProductId;
    }

    public OtsUsers getOtsCustomerId() {
        return otsCustomerId;
    }

    public void setOtsCustomerId(OtsUsers otsCustomerId) {
        this.otsCustomerId = otsCustomerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsRatingReviewId != null ? otsRatingReviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsRatingReview)) {
            return false;
        }
        OtsRatingReview other = (OtsRatingReview) object;
        if ((this.otsRatingReviewId == null && other.otsRatingReviewId != null) || (this.otsRatingReviewId != null && !this.otsRatingReviewId.equals(other.otsRatingReviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsRatingReview[ otsRatingReviewId=" + otsRatingReviewId + " ]";
    }
    
}
