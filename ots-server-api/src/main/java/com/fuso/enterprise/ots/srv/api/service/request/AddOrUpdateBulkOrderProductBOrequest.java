package com.fuso.enterprise.ots.srv.api.service.request;

public class AddOrUpdateBulkOrderProductBOrequest 
{

	AddOrUpdateOrderProductBOrequest request;
	String BulkId;
	public AddOrUpdateOrderProductBOrequest getRequest() {
		return request;
	}
	public void setRequest(AddOrUpdateOrderProductBOrequest request) {
		this.request = request;
	}
	public String getBulkId() {
		return BulkId;
	}
	public void setBulkId(String bulkId) {
		BulkId = bulkId;
	}
}
