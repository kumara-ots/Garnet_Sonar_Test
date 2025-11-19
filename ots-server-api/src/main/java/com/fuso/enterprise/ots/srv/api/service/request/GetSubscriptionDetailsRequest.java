package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetSubscriptionDetails;

public class GetSubscriptionDetailsRequest {
	
	private GetSubscriptionDetails subscriptionDetails;

	public GetSubscriptionDetails getSubscriptionDetails() {
		return subscriptionDetails;
	}

	public void setSubscriptionDetails(GetSubscriptionDetails subscriptionDetails) {
		this.subscriptionDetails = subscriptionDetails;
	}
}
