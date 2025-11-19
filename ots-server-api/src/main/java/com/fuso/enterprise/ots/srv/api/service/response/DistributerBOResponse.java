package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;
import java.util.Map;

public class DistributerBOResponse
{
	List<Map<String, Object>> distributerList;

	public List<Map<String, Object>> getDistributerList() {
		return distributerList;
	}

	public void setDistributerList(List<Map<String, Object>> distributerList) {
		this.distributerList = distributerList;
	}

}
