package com.fuso.enterprise.ots.srv.api.service.request;

import java.util.List;

public class GetPincodeByMultipleDistricts {
   private List<String> districtId;

	public List<String> getDistrictId() {
		return districtId;
	}
	
	public void setDistrictId(List<String> districtId) {
		this.districtId = districtId;
	}

}

