/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Bharath
 */
@Entity
@Table(name = "ots_service_order_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsServiceOrderDetails.findAll", query = "SELECT o FROM OtsServiceOrderDetails o"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderDetailsId", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderDetailsId = :otsServiceOrderDetailsId"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderTransactionId", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderTransactionId = :otsServiceOrderTransactionId"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderPaymentStatus", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderPaymentStatus = :otsServiceOrderPaymentStatus"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceDayOfWeek", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceDayOfWeek = :otsServiceDayOfWeek"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceStartTime", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceStartTime = :otsServiceStartTime"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceEndTime", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceEndTime = :otsServiceEndTime"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceBookingStatus", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceBookingStatus = :otsServiceBookingStatus"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceBookedDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceBookedDate = :otsServiceBookedDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderStatus", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderStatus = :otsServiceOrderStatus"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderedDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderedDate = :otsServiceOrderedDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderAcceptedDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderAcceptedDate = :otsServiceOrderAcceptedDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderAssignedDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderAssignedDate = :otsServiceOrderAssignedDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderInProgressDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderInProgressDate = :otsServiceOrderInProgressDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderCompletedDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderCompletedDate = :otsServiceOrderCompletedDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderCost", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderCost = :otsServiceOrderCost"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceBasePrice", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceBasePrice = :otsServiceBasePrice"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceDiscountPercentage", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceDiscountPercentage = :otsServiceDiscountPercentage"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceDiscountPrice", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceDiscountPrice = :otsServiceDiscountPrice"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceStrikenPrice", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceStrikenPrice = :otsServiceStrikenPrice"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceFinalPriceWithoutGst", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceFinalPriceWithoutGst = :otsServiceFinalPriceWithoutGst"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceGstPercentage", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceGstPercentage = :otsServiceGstPercentage"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceGstPrice", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceGstPrice = :otsServiceGstPrice"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceFinalPriceWithGst", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceFinalPriceWithGst = :otsServiceFinalPriceWithGst"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceDuration", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceDuration = :otsServiceDuration"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceMode", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceMode = :otsServiceMode"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleStartTime", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleStartTime = :otsServiceRescheduleStartTime"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleEndTime", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleEndTime = :otsServiceRescheduleEndTime"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleRequestedBy", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleRequestedBy = :otsServiceRescheduleRequestedBy"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleApprovedBy", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleApprovedBy = :otsServiceRescheduleApprovedBy"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleRequestedDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleRequestedDate = :otsServiceRescheduleRequestedDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleAcceptedDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleAcceptedDate = :otsServiceRescheduleAcceptedDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleRejectedDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleRejectedDate = :otsServiceRescheduleRejectedDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderCancelledBy", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderCancelledBy = :otsServiceOrderCancelledBy"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderCancelledDate", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderCancelledDate = :otsServiceOrderCancelledDate"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderCancellationReason", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderCancellationReason = :otsServiceOrderCancellationReason"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceCancellationAvailability", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceCancellationAvailability = :otsServiceCancellationAvailability"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceCancellationBefore", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceCancellationBefore = :otsServiceCancellationBefore"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceCancellationFees", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceCancellationFees = :otsServiceCancellationFees"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleAvailability", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleAvailability = :otsServiceRescheduleAvailability"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleBefore", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleBefore = :otsServiceRescheduleBefore"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceRescheduleFees", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceRescheduleFees = :otsServiceRescheduleFees"),
    @NamedQuery(name = "OtsServiceOrderDetails.findByOtsServiceOrderRefundStatus", query = "SELECT o FROM OtsServiceOrderDetails o WHERE o.otsServiceOrderRefundStatus = :otsServiceOrderRefundStatus")})
public class OtsServiceOrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ots_service_order_details_id")
    private Integer otsServiceOrderDetailsId;
    @Column(name = "ots_service_order_transaction_id")
    private String otsServiceOrderTransactionId;
    @Column(name = "ots_service_order_payment_status")
    private String otsServiceOrderPaymentStatus;
    @Column(name = "ots_service_day_of_week")
    private String otsServiceDayOfWeek;
    @Column(name = "ots_service_start_time")
    private String otsServiceStartTime;
    @Column(name = "ots_service_end_time")
    private String otsServiceEndTime;
    @Column(name = "ots_service_booking_status")
    private String otsServiceBookingStatus;
    @Column(name = "ots_service_booked_date")
    private String otsServiceBookedDate;
    @Column(name = "ots_service_order_status")
    private String otsServiceOrderStatus;
    @Column(name = "ots_service_ordered_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceOrderedDate;
    @Column(name = "ots_service_order_accepted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceOrderAcceptedDate;
    @Column(name = "ots_service_order_assigned_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceOrderAssignedDate;
    @Column(name = "ots_service_order_inProgress_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceOrderInProgressDate;
    @Column(name = "ots_service_order_completed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceOrderCompletedDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ots_service_order_cost", nullable = false)
    private BigDecimal otsServiceOrderCost = BigDecimal.ZERO;
    @Column(name = "ots_service_base_price", nullable = false)
    private BigDecimal otsServiceBasePrice = BigDecimal.ZERO;
    @Column(name = "ots_service_discount_percentage", nullable = false)
    private String otsServiceDiscountPercentage ="0";
    @Column(name = "ots_service_discount_price", nullable = false)
    private BigDecimal otsServiceDiscountPrice = BigDecimal.ZERO;
    @Column(name = "ots_service_striken_price", nullable = false)
    private BigDecimal otsServiceStrikenPrice = BigDecimal.ZERO;
    @Column(name = "ots_service_final_price_without_gst", nullable = false)
    private BigDecimal otsServiceFinalPriceWithoutGst = BigDecimal.ZERO;
    @Column(name = "ots_service_gst_percentage", nullable = false)
    private String otsServiceGstPercentage = "0";
    @Column(name = "ots_service_gst_price", nullable = false)
    private BigDecimal otsServiceGstPrice = BigDecimal.ZERO;
    @Column(name = "ots_service_final_price_with_gst", nullable = false)
    private BigDecimal otsServiceFinalPriceWithGst;
    @Column(name = "ots_service_duration")
    private String otsServiceDuration;
    @Column(name = "ots_service_mode")
    private String otsServiceMode;
    @Column(name = "ots_service_virtual_location")
    private String otsServiceVirtualLocation;
    @Column(name = "ots_service_reschedule_start_time")
    private String otsServiceRescheduleStartTime;
    @Column(name = "ots_service_reschedule_end_time")
    private String otsServiceRescheduleEndTime;
    @Column(name = "ots_service_reschedule_requested_by")
    private String otsServiceRescheduleRequestedBy;
    @Column(name = "ots_service_reschedule_approved_by")
    private String otsServiceRescheduleApprovedBy;
    @Column(name = "ots_service_reschedule_requested_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceRescheduleRequestedDate;
    @Column(name = "ots_service_reschedule_accepted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceRescheduleAcceptedDate;
    @Column(name = "ots_service_reschedule_rejected_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceRescheduleRejectedDate;
    @Column(name = "ots_service_order_cancelled_by")
    private String otsServiceOrderCancelledBy;
    @Column(name = "ots_service_order_cancelled_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsServiceOrderCancelledDate;
    @Column(name = "ots_service_order_cancellation_reason")
    private String otsServiceOrderCancellationReason;
    @Column(name = "ots_service_cancellation_availability", nullable = false)
    private Boolean otsServiceCancellationAvailability = false;
    @Column(name = "ots_service_cancellation_before")
    private String otsServiceCancellationBefore;
    @Column(name = "ots_service_cancellation_fees", nullable = false)
    private BigDecimal otsServiceCancellationFees = BigDecimal.ZERO;
    @Column(name = "ots_service_reschedule_availability", nullable = false)
    private Boolean otsServiceRescheduleAvailability = false;
    @Column(name = "ots_service_reschedule_before")
    private String otsServiceRescheduleBefore;
    @Column(name = "ots_service_reschedule_fees", nullable = false)
    private BigDecimal otsServiceRescheduleFees = BigDecimal.ZERO;
    @Basic(optional = false)
    @Column(name = "ots_service_order_refund_status")
    private String otsServiceOrderRefundStatus;
    @JoinColumn(name = "ots_employee_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsEmployeeId;
    @JoinColumn(name = "ots_provider_id", referencedColumnName = "ots_users_id")
    @ManyToOne
    private OtsUsers otsProviderId;
    @JoinColumn(name = "ots_service_order_id", referencedColumnName = "ots_service_order_id")
    @ManyToOne
    private OtsServiceOrder otsServiceOrderId;
    @JoinColumn(name = "ots_service_id", referencedColumnName = "ots_service_id")
    @ManyToOne
    private OtsService otsServiceId;

    public OtsServiceOrderDetails() {
    }

    public OtsServiceOrderDetails(Integer otsServiceOrderDetailsId) {
        this.otsServiceOrderDetailsId = otsServiceOrderDetailsId;
    }

    public OtsServiceOrderDetails(Integer otsServiceOrderDetailsId, String otsServiceOrderRefundStatus) {
        this.otsServiceOrderDetailsId = otsServiceOrderDetailsId;
        this.otsServiceOrderRefundStatus = otsServiceOrderRefundStatus;
    }

    public Integer getOtsServiceOrderDetailsId() {
        return otsServiceOrderDetailsId;
    }

    public void setOtsServiceOrderDetailsId(Integer otsServiceOrderDetailsId) {
        this.otsServiceOrderDetailsId = otsServiceOrderDetailsId;
    }

    public String getOtsServiceOrderTransactionId() {
        return otsServiceOrderTransactionId;
    }

    public void setOtsServiceOrderTransactionId(String otsServiceOrderTransactionId) {
        this.otsServiceOrderTransactionId = otsServiceOrderTransactionId;
    }

    public String getOtsServiceOrderPaymentStatus() {
        return otsServiceOrderPaymentStatus;
    }

    public void setOtsServiceOrderPaymentStatus(String otsServiceOrderPaymentStatus) {
        this.otsServiceOrderPaymentStatus = otsServiceOrderPaymentStatus;
    }

    public String getOtsServiceDayOfWeek() {
        return otsServiceDayOfWeek;
    }

    public void setOtsServiceDayOfWeek(String otsServiceDayOfWeek) {
        this.otsServiceDayOfWeek = otsServiceDayOfWeek;
    }

    public String getOtsServiceStartTime() {
        return otsServiceStartTime;
    }

    public void setOtsServiceStartTime(String otsServiceStartTime) {
        this.otsServiceStartTime = otsServiceStartTime;
    }

    public String getOtsServiceEndTime() {
        return otsServiceEndTime;
    }

    public void setOtsServiceEndTime(String otsServiceEndTime) {
        this.otsServiceEndTime = otsServiceEndTime;
    }

    public String getOtsServiceBookingStatus() {
        return otsServiceBookingStatus;
    }

    public void setOtsServiceBookingStatus(String otsServiceBookingStatus) {
        this.otsServiceBookingStatus = otsServiceBookingStatus;
    }

    public String getOtsServiceBookedDate() {
        return otsServiceBookedDate;
    }

    public void setOtsServiceBookedDate(String otsServiceBookedDate) {
        this.otsServiceBookedDate = otsServiceBookedDate;
    }

    public String getOtsServiceOrderStatus() {
        return otsServiceOrderStatus;
    }

    public void setOtsServiceOrderStatus(String otsServiceOrderStatus) {
        this.otsServiceOrderStatus = otsServiceOrderStatus;
    }

    public Date getOtsServiceOrderedDate() {
        return otsServiceOrderedDate;
    }

    public void setOtsServiceOrderedDate(Date otsServiceOrderedDate) {
        this.otsServiceOrderedDate = otsServiceOrderedDate;
    }

    public Date getOtsServiceOrderAcceptedDate() {
        return otsServiceOrderAcceptedDate;
    }

    public void setOtsServiceOrderAcceptedDate(Date otsServiceOrderAcceptedDate) {
        this.otsServiceOrderAcceptedDate = otsServiceOrderAcceptedDate;
    }

    public Date getOtsServiceOrderAssignedDate() {
        return otsServiceOrderAssignedDate;
    }

    public void setOtsServiceOrderAssignedDate(Date otsServiceOrderAssignedDate) {
        this.otsServiceOrderAssignedDate = otsServiceOrderAssignedDate;
    }

    public Date getOtsServiceOrderInProgressDate() {
		return otsServiceOrderInProgressDate;
	}

	public void setOtsServiceOrderInProgressDate(Date otsServiceOrderInProgressDate) {
		this.otsServiceOrderInProgressDate = otsServiceOrderInProgressDate;
	}

	public Date getOtsServiceOrderCompletedDate() {
        return otsServiceOrderCompletedDate;
    }

    public void setOtsServiceOrderCompletedDate(Date otsServiceOrderCompletedDate) {
        this.otsServiceOrderCompletedDate = otsServiceOrderCompletedDate;
    }

    public BigDecimal getOtsServiceOrderCost() {
        return otsServiceOrderCost;
    }

    public void setOtsServiceOrderCost(BigDecimal otsServiceOrderCost) {
        this.otsServiceOrderCost = otsServiceOrderCost;
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

    public String getOtsServiceRescheduleStartTime() {
        return otsServiceRescheduleStartTime;
    }

    public void setOtsServiceRescheduleStartTime(String otsServiceRescheduleStartTime) {
        this.otsServiceRescheduleStartTime = otsServiceRescheduleStartTime;
    }

    public String getOtsServiceRescheduleEndTime() {
        return otsServiceRescheduleEndTime;
    }

    public void setOtsServiceRescheduleEndTime(String otsServiceRescheduleEndTime) {
        this.otsServiceRescheduleEndTime = otsServiceRescheduleEndTime;
    }

    public String getOtsServiceRescheduleRequestedBy() {
        return otsServiceRescheduleRequestedBy;
    }

    public void setOtsServiceRescheduleRequestedBy(String otsServiceRescheduleRequestedBy) {
        this.otsServiceRescheduleRequestedBy = otsServiceRescheduleRequestedBy;
    }

    public String getOtsServiceRescheduleApprovedBy() {
        return otsServiceRescheduleApprovedBy;
    }

    public void setOtsServiceRescheduleApprovedBy(String otsServiceRescheduleApprovedBy) {
        this.otsServiceRescheduleApprovedBy = otsServiceRescheduleApprovedBy;
    }

    public Date getOtsServiceRescheduleRequestedDate() {
        return otsServiceRescheduleRequestedDate;
    }

    public void setOtsServiceRescheduleRequestedDate(Date otsServiceRescheduleRequestedDate) {
        this.otsServiceRescheduleRequestedDate = otsServiceRescheduleRequestedDate;
    }

    public Date getOtsServiceRescheduleAcceptedDate() {
        return otsServiceRescheduleAcceptedDate;
    }

    public void setOtsServiceRescheduleAcceptedDate(Date otsServiceRescheduleAcceptedDate) {
        this.otsServiceRescheduleAcceptedDate = otsServiceRescheduleAcceptedDate;
    }

    public Date getOtsServiceRescheduleRejectedDate() {
        return otsServiceRescheduleRejectedDate;
    }

    public void setOtsServiceRescheduleRejectedDate(Date otsServiceRescheduleRejectedDate) {
        this.otsServiceRescheduleRejectedDate = otsServiceRescheduleRejectedDate;
    }

    public String getOtsServiceOrderCancelledBy() {
        return otsServiceOrderCancelledBy;
    }

    public void setOtsServiceOrderCancelledBy(String otsServiceOrderCancelledBy) {
        this.otsServiceOrderCancelledBy = otsServiceOrderCancelledBy;
    }

    public Date getOtsServiceOrderCancelledDate() {
        return otsServiceOrderCancelledDate;
    }

    public void setOtsServiceOrderCancelledDate(Date otsServiceOrderCancelledDate) {
        this.otsServiceOrderCancelledDate = otsServiceOrderCancelledDate;
    }

    public String getOtsServiceOrderCancellationReason() {
        return otsServiceOrderCancellationReason;
    }

    public void setOtsServiceOrderCancellationReason(String otsServiceOrderCancellationReason) {
        this.otsServiceOrderCancellationReason = otsServiceOrderCancellationReason;
    }

    public Boolean getOtsServiceCancellationAvailability() {
        return otsServiceCancellationAvailability;
    }

    public void setOtsServiceCancellationAvailability(Boolean otsServiceCancellationAvailability) {
        this.otsServiceCancellationAvailability = otsServiceCancellationAvailability;
    }

    public String getOtsServiceCancellationBefore() {
        return otsServiceCancellationBefore;
    }

    public void setOtsServiceCancellationBefore(String otsServiceCancellationBefore) {
        this.otsServiceCancellationBefore = otsServiceCancellationBefore;
    }

    public BigDecimal getOtsServiceCancellationFees() {
        return otsServiceCancellationFees;
    }

    public void setOtsServiceCancellationFees(BigDecimal otsServiceCancellationFees) {
        this.otsServiceCancellationFees = otsServiceCancellationFees;
    }

    public Boolean getOtsServiceRescheduleAvailability() {
        return otsServiceRescheduleAvailability;
    }

    public void setOtsServiceRescheduleAvailability(Boolean otsServiceRescheduleAvailability) {
        this.otsServiceRescheduleAvailability = otsServiceRescheduleAvailability;
    }

    public String getOtsServiceRescheduleBefore() {
        return otsServiceRescheduleBefore;
    }

    public void setOtsServiceRescheduleBefore(String otsServiceRescheduleBefore) {
        this.otsServiceRescheduleBefore = otsServiceRescheduleBefore;
    }

    public BigDecimal getOtsServiceRescheduleFees() {
        return otsServiceRescheduleFees;
    }

    public void setOtsServiceRescheduleFees(BigDecimal otsServiceRescheduleFees) {
        this.otsServiceRescheduleFees = otsServiceRescheduleFees;
    }

    public String getOtsServiceOrderRefundStatus() {
        return otsServiceOrderRefundStatus;
    }

    public void setOtsServiceOrderRefundStatus(String otsServiceOrderRefundStatus) {
        this.otsServiceOrderRefundStatus = otsServiceOrderRefundStatus;
    }

    public OtsUsers getOtsEmployeeId() {
        return otsEmployeeId;
    }

    public void setOtsEmployeeId(OtsUsers otsEmployeeId) {
        this.otsEmployeeId = otsEmployeeId;
    }

    public OtsUsers getOtsProviderId() {
        return otsProviderId;
    }

    public void setOtsProviderId(OtsUsers otsProviderId) {
        this.otsProviderId = otsProviderId;
    }

    public OtsServiceOrder getOtsServiceOrderId() {
        return otsServiceOrderId;
    }

    public void setOtsServiceOrderId(OtsServiceOrder otsServiceOrderId) {
        this.otsServiceOrderId = otsServiceOrderId;
    }

    public OtsService getOtsServiceId() {
        return otsServiceId;
    }

    public void setOtsServiceId(OtsService otsServiceId) {
        this.otsServiceId = otsServiceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsServiceOrderDetailsId != null ? otsServiceOrderDetailsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsServiceOrderDetails)) {
            return false;
        }
        OtsServiceOrderDetails other = (OtsServiceOrderDetails) object;
        if ((this.otsServiceOrderDetailsId == null && other.otsServiceOrderDetailsId != null) || (this.otsServiceOrderDetailsId != null && !this.otsServiceOrderDetailsId.equals(other.otsServiceOrderDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsServiceOrderDetails[ otsServiceOrderDetailsId=" + otsServiceOrderDetailsId + " ]";
    }
    
}
