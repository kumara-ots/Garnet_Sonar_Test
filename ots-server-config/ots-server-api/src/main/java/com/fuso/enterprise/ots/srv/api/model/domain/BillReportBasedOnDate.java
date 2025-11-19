package com.fuso.enterprise.ots.srv.api.model.domain;

import java.sql.Date;

public class BillReportBasedOnDate {

	Date FromDate;
	Date ToDate;
	String userId;
	String roleId;
	String pdf;

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public Date getFromDate() {
		return FromDate;
	}
	
	public void setFromDate(Date fromDate) {
		FromDate = fromDate;
	}
	
	public Date getToDate() {
		return ToDate;
	}
	
	public void setToDate(Date toDate) {
		ToDate = toDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
}
