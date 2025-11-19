package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.ArrayList;
import java.util.List;
import com.fuso.enterprise.ots.srv.api.model.domain.RegistorToUserDetails;


public class GetNewRegistrationResponse {

	List<RegistorToUserDetails> registorToUserDetails = new ArrayList<RegistorToUserDetails>();

	public List<RegistorToUserDetails> getRegistorToUserDetails() {
		return registorToUserDetails;
	}

	public void setRegistorToUserDetails(List<RegistorToUserDetails> registorToUserDetails) {
		this.registorToUserDetails = registorToUserDetails;
	}
	
	
}
