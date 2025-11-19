package com.fuso.enterprise.ots.srv.api.model.domain;

public class UpdateUserStatusModel {

	private String usersId;

	private String usersStatus;

	private String usersRejectionReason;

	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	public String getUsersStatus() {
		return usersStatus;
	}

	public void setUsersStatus(String usersStatus) {
		this.usersStatus = usersStatus;
	}

	public String getUsersRejectionReason() {
		return usersRejectionReason;
	}

	public void setUsersRejectionReason(String usersRejectionReason) {
		this.usersRejectionReason = usersRejectionReason;
	}
}
