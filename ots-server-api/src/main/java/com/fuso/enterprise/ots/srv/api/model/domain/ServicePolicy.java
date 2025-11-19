package com.fuso.enterprise.ots.srv.api.model.domain;

public class ServicePolicy {
	private String otsServiceId;
	private String otsServiceStatus;
	private Boolean otsServiceCancellationAvailability;
	private String otsServiceCancellationPolicy;
	private String otsServiceCancellationBefore;
	private String otsServiceCancellationFees;
	private Boolean otsServiceRescheduleAvailability;
	private String otsServiceRescheduleBefore;
	private String otsServiceReschedulePolicy;
	private String otsServiceRescheduleFees;

	public String getOtsServiceId() {
		return otsServiceId;
	}

	public void setOtsServiceId(String otsServiceId) {
		this.otsServiceId = otsServiceId;
	}

	public String getOtsServiceStatus() {
		return otsServiceStatus;
	}

	public void setOtsServiceStatus(String otsServiceStatus) {
		this.otsServiceStatus = otsServiceStatus;
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

}
