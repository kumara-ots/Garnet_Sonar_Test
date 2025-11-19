package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class InputAddBuyerstoGroup {
	
	@Size(max = 10)
	private String buyerGroupName;
	
	@Valid
	private List<String> userIds;

	public String getBuyerGroupName() {
		return buyerGroupName;
	}

	public void setBuyerGroupName(String buyerGroupName) {
		this.buyerGroupName = buyerGroupName;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

}
