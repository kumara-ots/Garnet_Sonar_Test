package com.fuso.enterprise.ots.srv.api.service.response;

import java.util.List;
import com.fuso.enterprise.ots.srv.api.model.domain.DistributerList;
public class DistributerResponse 
{
	List<DistributerList> DistributerList;

	public List<DistributerList> getDistributerList() {
		return DistributerList;
	}

	public void setDistributerList(List<DistributerList> distributerList) {
		DistributerList = distributerList;
	}
	
}
