/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bharath
 */
@Entity
@Table(name = "ots_service")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsService.findAll", query = "SELECT o FROM OtsService o"),
    @NamedQuery(name = "OtsService.findByOtsServiceId", query = "SELECT o FROM OtsService o WHERE o.otsServiceId = :otsServiceId"),
    @NamedQuery(name = "OtsService.findByOtsServiceNumber", query = "SELECT o FROM OtsService o WHERE o.otsServiceNumber = :otsServiceNumber"),
    @NamedQuery(name = "OtsService.findByOtsServiceStatus", query = "SELECT o FROM OtsService o WHERE o.otsServiceStatus = :otsServiceStatus"),
    @NamedQuery(name = "OtsService.findByOtsServiceBasePrice", query = "SELECT o FROM OtsService o WHERE o.otsServiceBasePrice = :otsServiceBasePrice"),
    @NamedQuery(name = "OtsService.findByOtsServiceDiscountPercentage", query = "SELECT o FROM OtsService o WHERE o.otsServiceDiscountPercentage = :otsServiceDiscountPercentage"),
    @NamedQuery(name = "OtsService.findByOtsServiceDiscountPrice", query = "SELECT o FROM OtsService o WHERE o.otsServiceDiscountPrice = :otsServiceDiscountPrice"),
    @NamedQuery(name = "OtsService.findByOtsServiceStrikenPrice", query = "SELECT o FROM OtsService o WHERE o.otsServiceStrikenPrice = :otsServiceStrikenPrice"),
    @NamedQuery(name = "OtsService.findByOtsServiceFinalPriceWithoutGst", query = "SELECT o FROM OtsService o WHERE o.otsServiceFinalPriceWithoutGst = :otsServiceFinalPriceWithoutGst"),
    @NamedQuery(name = "OtsService.findByOtsServiceGstPercentage", query = "SELECT o FROM OtsService o WHERE o.otsServiceGstPercentage = :otsServiceGstPercentage"),
    @NamedQuery(name = "OtsService.findByOtsServiceGstPrice", query = "SELECT o FROM OtsService o WHERE o.otsServiceGstPrice = :otsServiceGstPrice"),
    @NamedQuery(name = "OtsService.findByOtsServiceFinalPriceWithGst", query = "SELECT o FROM OtsService o WHERE o.otsServiceFinalPriceWithGst = :otsServiceFinalPriceWithGst"),
    @NamedQuery(name = "OtsService.findByOtsServiceDuration", query = "SELECT o FROM OtsService o WHERE o.otsServiceDuration = :otsServiceDuration"),
    @NamedQuery(name = "OtsService.findByOtsServiceMode", query = "SELECT o FROM OtsService o WHERE o.otsServiceMode = :otsServiceMode"),
    @NamedQuery(name = "OtsService.findByOtsServiceCustomerLocation", query = "SELECT o FROM OtsService o WHERE o.otsServiceCustomerLocation = :otsServiceCustomerLocation"),
    @NamedQuery(name = "OtsService.findByOtsServiceCompanyName", query = "SELECT o FROM OtsService o WHERE o.otsServiceCompanyName = :otsServiceCompanyName"),
    @NamedQuery(name = "OtsService.findByOtsServiceCompanyState", query = "SELECT o FROM OtsService o WHERE o.otsServiceCompanyState = :otsServiceCompanyState"),
    @NamedQuery(name = "OtsService.findByOtsServiceCompanyDistrict", query = "SELECT o FROM OtsService o WHERE o.otsServiceCompanyDistrict = :otsServiceCompanyDistrict"),
    @NamedQuery(name = "OtsService.findByOtsServiceCompanyPincode", query = "SELECT o FROM OtsService o WHERE o.otsServiceCompanyPincode = :otsServiceCompanyPincode"),
    @NamedQuery(name = "OtsService.findByOtsServiceCompanyContactNo", query = "SELECT o FROM OtsService o WHERE o.otsServiceCompanyContactNo = :otsServiceCompanyContactNo"),
    @NamedQuery(name = "OtsService.findByOtsServiceCancellationAvailability", query = "SELECT o FROM OtsService o WHERE o.otsServiceCancellationAvailability = :otsServiceCancellationAvailability"),
    @NamedQuery(name = "OtsService.findByOtsServiceCancellationBefore", query = "SELECT o FROM OtsService o WHERE o.otsServiceCancellationBefore = :otsServiceCancellationBefore"),
    @NamedQuery(name = "OtsService.findByOtsServiceCancellationFees", query = "SELECT o FROM OtsService o WHERE o.otsServiceCancellationFees = :otsServiceCancellationFees"),
    @NamedQuery(name = "OtsService.findByOtsServiceRescheduleAvailability", query = "SELECT o FROM OtsService o WHERE o.otsServiceRescheduleAvailability = :otsServiceRescheduleAvailability"),
    @NamedQuery(name = "OtsService.findByOtsServiceRescheduleBefore", query = "SELECT o FROM OtsService o WHERE o.otsServiceRescheduleBefore = :otsServiceRescheduleBefore"),
    @NamedQuery(name = "OtsService.findByOtsServiceRescheduleFees", query = "SELECT o FROM OtsService o WHERE o.otsServiceRescheduleFees = :otsServiceRescheduleFees"),
    @NamedQuery(name = "OtsService.findByOtsServiceCreated", query = "SELECT o FROM OtsService o WHERE o.otsServiceCreated = :otsServiceCreated"),
    @NamedQuery(name = "OtsService.findByOtsServiceUpdated", query = "SELECT o FROM OtsService o WHERE o.otsServiceUpdated = :otsServiceUpdated")})
public class OtsService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ots_service_id")
    private UUID otsServiceId;
    @Column(name = "ots_service_number")
    private String otsServiceNumber;
    @Column(name = "ots_service_name")
    private String otsServiceName;
    @Column(name = "ots_service_description")
    private String otsServiceDescription;
    @Column(name = "ots_service_description_long")
    private String otsServiceDescriptionLong;
    @Column(name = "ots_service_status")
    private String otsServiceStatus;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ots_service_base_price", nullable = false)
    private BigDecimal otsServiceBasePrice = BigDecimal.ZERO;
    @Column(name = "ots_service_discount_percentage", nullable = false)
    private String otsServiceDiscountPercentage = "0";
    @Column(name = "ots_service_discount_price", nullable = false)
    private BigDecimal otsServiceDiscountPrice= BigDecimal.ZERO;
    @Column(name = "ots_service_striken_price", nullable = false)
    private BigDecimal otsServiceStrikenPrice = BigDecimal.ZERO;
    @Column(name = "ots_service_final_price_without_gst", nullable = false)
    private BigDecimal otsServiceFinalPriceWithoutGst = BigDecimal.ZERO;
    @Column(name = "ots_service_gst_percentage", nullable = false)
    private String otsServiceGstPercentage = "0";
    @Column(name = "ots_service_gst_price", nullable = false)
    private BigDecimal otsServiceGstPrice = BigDecimal.ZERO;
    @Column(name = "ots_service_final_price_with_gst", nullable = false)
    private BigDecimal otsServiceFinalPriceWithGst = BigDecimal.ZERO;
    @Column(name = "ots_service_duration")
    private String otsServiceDuration;
    @Column(name = "ots_service_mode")
    private String otsServiceMode;
    @Column(name = "ots_service_virtual_location")
    private String otsServiceVirtualLocation;
    @Column(name = "ots_service_customer_location" , nullable = false)
    private Boolean otsServiceCustomerLocation = false;
    @Column(name = "ots_service_company_name")
    private String otsServiceCompanyName;
    @Column(name = "ots_service_company_address")
    private String otsServiceCompanyAddress;
    @Column(name = "ots_service_company_state")
    private String otsServiceCompanyState;
    @Column(name = "ots_service_company_district")
    private String otsServiceCompanyDistrict;
    @Column(name = "ots_service_company_pincode")
    private String otsServiceCompanyPincode;
    @Column(name = "ots_service_company_contact_no")
    private String otsServiceCompanyContactNo;
    @Column(name = "ots_service_cancellation_availability" , nullable = false)
    private Boolean otsServiceCancellationAvailability = false;
    @Column(name = "ots_service_cancellation_policy")
    private String otsServiceCancellationPolicy;
    @Column(name = "ots_service_cancellation_before")
    private String otsServiceCancellationBefore;
    @Column(name = "ots_service_cancellation_fees", nullable = false)
    private String otsServiceCancellationFees = "0";
    @Column(name = "ots_service_reschedule_availability", nullable = false)
    private Boolean otsServiceRescheduleAvailability = false;
    @Column(name = "ots_service_reschedule_policy")
    private String otsServiceReschedulePolicy;
    @Column(name = "ots_service_reschedule_before")
    private String otsServiceRescheduleBefore;
    @Column(name = "ots_service_reschedule_fees", nullable = false)
    private String otsServiceRescheduleFees = "0";
    @Column(name = "ots_service_image")
    private String otsServiceImage;
    @Column(name = "ots_multi_service_image1")
    private String otsMultiServiceImage1;
    @Column(name = "ots_multi_service_image2")
    private String otsMultiServiceImage2;
    @Column(name = "ots_multi_service_image3")
    private String otsMultiServiceImage3;
    @Column(name = "ots_multi_service_image4")
    private String otsMultiServiceImage4;
    @Column(name = "ots_multi_service_image5")
    private String otsMultiServiceImage5;
    @Column(name = "ots_multi_service_image6")
    private String otsMultiServiceImage6;
    @Column(name = "ots_multi_service_image7")
    private String otsMultiServiceImage7;
    @Column(name = "ots_multi_service_image8")
    private String otsMultiServiceImage8;
    @Column(name = "ots_multi_service_image9")
    private String otsMultiServiceImage9;
    @Column(name = "ots_multi_service_image10")
    private String otsMultiServiceImage10;
    @Column(name = "ots_service_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceCreated;
    @Column(name = "ots_service_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceUpdated;
    @JoinColumn(name = "ots_service_level_id", referencedColumnName = "ots_service_level_id")
	@ManyToOne(optional = false)
	private OtsServiceLevel otsServiceLevelId; 
    @Column(name = "ots_service_slot")
    private String otsServiceSlot;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsServiceId")
    private Collection<OtsServiceRatingReview> otsServiceRatingReviewCollection;
    @JoinColumn(name = "created_user", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts createdUser;
    @JoinColumn(name = "ots_service_provider_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsServiceProviderId;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "otsServiceMappingId")
    private Collection<OtsService> otsServiceCollection;
    @JoinColumn(name = "ots_service_mapping_id", referencedColumnName = "ots_service_id")
    @ManyToOne
    private OtsService otsServiceMappingId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsServiceId")
    private Collection<OtsServiceOrderDetails> otsServiceOrderDetailsCollection;

    public OtsService() {
    }

    public OtsService(UUID otsServiceId) {
        this.otsServiceId = otsServiceId;
    }

    public UUID getOtsServiceId() {
        return otsServiceId;
    }

    public void setOtsServiceId(UUID otsServiceId) {
        this.otsServiceId = otsServiceId;
    }

    public String getOtsServiceNumber() {
        return otsServiceNumber;
    }

    public void setOtsServiceNumber(String otsServiceNumber) {
        this.otsServiceNumber = otsServiceNumber;
    }

    public String getOtsServiceName() {
        return otsServiceName;
    }

    public void setOtsServiceName(String otsServiceName) {
        this.otsServiceName = otsServiceName;
    }

    public String getOtsServiceDescription() {
        return otsServiceDescription;
    }

    public void setOtsServiceDescription(String otsServiceDescription) {
        this.otsServiceDescription = otsServiceDescription;
    }

    public String getOtsServiceDescriptionLong() {
        return otsServiceDescriptionLong;
    }

    public void setOtsServiceDescriptionLong(String otsServiceDescriptionLong) {
        this.otsServiceDescriptionLong = otsServiceDescriptionLong;
    }

    public String getOtsServiceStatus() {
        return otsServiceStatus;
    }

    public void setOtsServiceStatus(String otsServiceStatus) {
        this.otsServiceStatus = otsServiceStatus;
    }

    public BigDecimal getOtsServiceBasePrice() {
        return otsServiceBasePrice;
    }

    public void setOtsServiceBasePrice(BigDecimal otsServiceBasePrice) {
        this.otsServiceBasePrice = otsServiceBasePrice;
    }

    public String getOtsServiceDiscountPercentage() {
        return otsServiceDiscountPercentage;
    }

    public void setOtsServiceDiscountPercentage(String otsServiceDiscountPercentage) {
        this.otsServiceDiscountPercentage = otsServiceDiscountPercentage;
    }

    public BigDecimal getOtsServiceDiscountPrice() {
        return otsServiceDiscountPrice;
    }

    public void setOtsServiceDiscountPrice(BigDecimal otsServiceDiscountPrice) {
        this.otsServiceDiscountPrice = otsServiceDiscountPrice;
    }

    public BigDecimal getOtsServiceStrikenPrice() {
        return otsServiceStrikenPrice;
    }

    public void setOtsServiceStrikenPrice(BigDecimal otsServiceStrikenPrice) {
        this.otsServiceStrikenPrice = otsServiceStrikenPrice;
    }

    public BigDecimal getOtsServiceFinalPriceWithoutGst() {
        return otsServiceFinalPriceWithoutGst;
    }

    public void setOtsServiceFinalPriceWithoutGst(BigDecimal otsServiceFinalPriceWithoutGst) {
        this.otsServiceFinalPriceWithoutGst = otsServiceFinalPriceWithoutGst;
    }

    public String getOtsServiceGstPercentage() {
        return otsServiceGstPercentage;
    }

    public void setOtsServiceGstPercentage(String otsServiceGstPercentage) {
        this.otsServiceGstPercentage = otsServiceGstPercentage;
    }

    public BigDecimal getOtsServiceGstPrice() {
        return otsServiceGstPrice;
    }

    public void setOtsServiceGstPrice(BigDecimal otsServiceGstPrice) {
        this.otsServiceGstPrice = otsServiceGstPrice;
    }

    public BigDecimal getOtsServiceFinalPriceWithGst() {
        return otsServiceFinalPriceWithGst;
    }

    public void setOtsServiceFinalPriceWithGst(BigDecimal otsServiceFinalPriceWithGst) {
        this.otsServiceFinalPriceWithGst = otsServiceFinalPriceWithGst;
    }

    public String getOtsServiceDuration() {
        return otsServiceDuration;
    }

    public void setOtsServiceDuration(String otsServiceDuration) {
        this.otsServiceDuration = otsServiceDuration;
    }

    public String getOtsServiceMode() {
        return otsServiceMode;
    }

    public void setOtsServiceMode(String otsServiceMode) {
        this.otsServiceMode = otsServiceMode;
    }

    public String getOtsServiceVirtualLocation() {
        return otsServiceVirtualLocation;
    }

    public void setOtsServiceVirtualLocation(String otsServiceVirtualLocation) {
        this.otsServiceVirtualLocation = otsServiceVirtualLocation;
    }

    public Boolean getOtsServiceCustomerLocation() {
        return otsServiceCustomerLocation;
    }

    public void setOtsServiceCustomerLocation(Boolean otsServiceCustomerLocation) {
        this.otsServiceCustomerLocation = otsServiceCustomerLocation;
    }

    public String getOtsServiceCompanyName() {
        return otsServiceCompanyName;
    }

    public void setOtsServiceCompanyName(String otsServiceCompanyName) {
        this.otsServiceCompanyName = otsServiceCompanyName;
    }

    public String getOtsServiceCompanyAddress() {
        return otsServiceCompanyAddress;
    }

    public void setOtsServiceCompanyAddress(String otsServiceCompanyAddress) {
        this.otsServiceCompanyAddress = otsServiceCompanyAddress;
    }

    public String getOtsServiceCompanyState() {
        return otsServiceCompanyState;
    }

    public void setOtsServiceCompanyState(String otsServiceCompanyState) {
        this.otsServiceCompanyState = otsServiceCompanyState;
    }

    public String getOtsServiceCompanyDistrict() {
        return otsServiceCompanyDistrict;
    }

    public void setOtsServiceCompanyDistrict(String otsServiceCompanyDistrict) {
        this.otsServiceCompanyDistrict = otsServiceCompanyDistrict;
    }

    public String getOtsServiceCompanyPincode() {
        return otsServiceCompanyPincode;
    }

    public void setOtsServiceCompanyPincode(String otsServiceCompanyPincode) {
        this.otsServiceCompanyPincode = otsServiceCompanyPincode;
    }

    public String getOtsServiceCompanyContactNo() {
        return otsServiceCompanyContactNo;
    }

    public void setOtsServiceCompanyContactNo(String otsServiceCompanyContactNo) {
        this.otsServiceCompanyContactNo = otsServiceCompanyContactNo;
    }

    public Boolean getOtsServiceCancellationAvailability() {
        return otsServiceCancellationAvailability;
    }

    public void setOtsServiceCancellationAvailability(Boolean otsServiceCancellationAvailability) {
        this.otsServiceCancellationAvailability = otsServiceCancellationAvailability;
    }

    public String getOtsServiceCancellationPolicy() {
        return otsServiceCancellationPolicy;
    }

    public void setOtsServiceCancellationPolicy(String otsServiceCancellationPolicy) {
        this.otsServiceCancellationPolicy = otsServiceCancellationPolicy;
    }

    public String getOtsServiceCancellationBefore() {
        return otsServiceCancellationBefore;
    }

    public void setOtsServiceCancellationBefore(String otsServiceCancellationBefore) {
        this.otsServiceCancellationBefore = otsServiceCancellationBefore;
    }

    public String getOtsServiceCancellationFees() {
        return otsServiceCancellationFees;
    }

    public void setOtsServiceCancellationFees(String otsServiceCancellationFees) {
        this.otsServiceCancellationFees = otsServiceCancellationFees;
    }

    public Boolean getOtsServiceRescheduleAvailability() {
        return otsServiceRescheduleAvailability;
    }

    public void setOtsServiceRescheduleAvailability(Boolean otsServiceRescheduleAvailability) {
        this.otsServiceRescheduleAvailability = otsServiceRescheduleAvailability;
    }

    public String getOtsServiceReschedulePolicy() {
        return otsServiceReschedulePolicy;
    }

    public void setOtsServiceReschedulePolicy(String otsServiceReschedulePolicy) {
        this.otsServiceReschedulePolicy = otsServiceReschedulePolicy;
    }

    public String getOtsServiceRescheduleBefore() {
        return otsServiceRescheduleBefore;
    }

    public void setOtsServiceRescheduleBefore(String otsServiceRescheduleBefore) {
        this.otsServiceRescheduleBefore = otsServiceRescheduleBefore;
    }

    public String getOtsServiceRescheduleFees() {
        return otsServiceRescheduleFees;
    }

    public void setOtsServiceRescheduleFees(String otsServiceRescheduleFees) {
        this.otsServiceRescheduleFees = otsServiceRescheduleFees;
    }

    public String getOtsServiceImage() {
        return otsServiceImage;
    }

    public void setOtsServiceImage(String otsServiceImage) {
        this.otsServiceImage = otsServiceImage;
    }

    public String getOtsMultiServiceImage1() {
        return otsMultiServiceImage1;
    }

    public void setOtsMultiServiceImage1(String otsMultiServiceImage1) {
        this.otsMultiServiceImage1 = otsMultiServiceImage1;
    }

    public String getOtsMultiServiceImage2() {
        return otsMultiServiceImage2;
    }

    public void setOtsMultiServiceImage2(String otsMultiServiceImage2) {
        this.otsMultiServiceImage2 = otsMultiServiceImage2;
    }

    public String getOtsMultiServiceImage3() {
        return otsMultiServiceImage3;
    }

    public void setOtsMultiServiceImage3(String otsMultiServiceImage3) {
        this.otsMultiServiceImage3 = otsMultiServiceImage3;
    }

    public String getOtsMultiServiceImage4() {
        return otsMultiServiceImage4;
    }

    public void setOtsMultiServiceImage4(String otsMultiServiceImage4) {
        this.otsMultiServiceImage4 = otsMultiServiceImage4;
    }

    public String getOtsMultiServiceImage5() {
        return otsMultiServiceImage5;
    }

    public void setOtsMultiServiceImage5(String otsMultiServiceImage5) {
        this.otsMultiServiceImage5 = otsMultiServiceImage5;
    }

    public String getOtsMultiServiceImage6() {
        return otsMultiServiceImage6;
    }

    public void setOtsMultiServiceImage6(String otsMultiServiceImage6) {
        this.otsMultiServiceImage6 = otsMultiServiceImage6;
    }

    public String getOtsMultiServiceImage7() {
        return otsMultiServiceImage7;
    }

    public void setOtsMultiServiceImage7(String otsMultiServiceImage7) {
        this.otsMultiServiceImage7 = otsMultiServiceImage7;
    }

    public String getOtsMultiServiceImage8() {
        return otsMultiServiceImage8;
    }

    public void setOtsMultiServiceImage8(String otsMultiServiceImage8) {
        this.otsMultiServiceImage8 = otsMultiServiceImage8;
    }

    public String getOtsMultiServiceImage9() {
        return otsMultiServiceImage9;
    }

    public void setOtsMultiServiceImage9(String otsMultiServiceImage9) {
        this.otsMultiServiceImage9 = otsMultiServiceImage9;
    }

    public String getOtsMultiServiceImage10() {
        return otsMultiServiceImage10;
    }

    public void setOtsMultiServiceImage10(String otsMultiServiceImage10) {
        this.otsMultiServiceImage10 = otsMultiServiceImage10;
    }

    public Date getOtsServiceCreated() {
        return otsServiceCreated;
    }

    public void setOtsServiceCreated(Date otsServiceCreated) {
        this.otsServiceCreated = otsServiceCreated;
    }

    public Date getOtsServiceUpdated() {
        return otsServiceUpdated;
    }

    public void setOtsServiceUpdated(Date otsServiceUpdated) {
        this.otsServiceUpdated = otsServiceUpdated;
    }

    public String getOtsServiceSlot() {
        return otsServiceSlot;
    }

    public void setOtsServiceSlot(String otsServiceSlot) {
        this.otsServiceSlot = otsServiceSlot;
    }

    public OtsServiceLevel getOtsServiceLevelId() {
		return otsServiceLevelId;
	}

	public void setOtsServiceLevelId(OtsServiceLevel otsServiceLevelId) {
		this.otsServiceLevelId = otsServiceLevelId;
	}

	public Useraccounts getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Useraccounts createdUser) {
        this.createdUser = createdUser;
    }

    public OtsUsers getOtsServiceProviderId() {
        return otsServiceProviderId;
    }

    public void setOtsServiceProviderId(OtsUsers otsServiceProviderId) {
        this.otsServiceProviderId = otsServiceProviderId;
    }
    
    @XmlTransient
    public Collection<OtsServiceRatingReview> getOtsServiceRatingReviewCollection() {
        return otsServiceRatingReviewCollection;
    }

    public void setOtsServiceRatingReviewCollection(Collection<OtsServiceRatingReview> otsServiceRatingReviewCollection) {
        this.otsServiceRatingReviewCollection = otsServiceRatingReviewCollection;
    }

    @XmlTransient
    public Collection<OtsService> getOtsServiceCollection() {
        return otsServiceCollection;
    }

    public void setOtsServiceCollection(Collection<OtsService> otsServiceCollection) {
        this.otsServiceCollection = otsServiceCollection;
    }

    public OtsService getOtsServiceMappingId() {
        return otsServiceMappingId;
    }

    public void setOtsServiceMappingId(OtsService otsServiceMappingId) {
        this.otsServiceMappingId = otsServiceMappingId;
    }

    @XmlTransient
    public Collection<OtsServiceOrderDetails> getOtsServiceOrderDetailsCollection() {
        return otsServiceOrderDetailsCollection;
    }

    public void setOtsServiceOrderDetailsCollection(Collection<OtsServiceOrderDetails> otsServiceOrderDetailsCollection) {
        this.otsServiceOrderDetailsCollection = otsServiceOrderDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsServiceId != null ? otsServiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsService)) {
            return false;
        }
        OtsService other = (OtsService) object;
        if ((this.otsServiceId == null && other.otsServiceId != null) || (this.otsServiceId != null && !this.otsServiceId.equals(other.otsServiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsService[ otsServiceId=" + otsServiceId + " ]";
    }
    
}
