package com.fuso.enterprise.ots.srv.api.service.request;

import com.fuso.enterprise.ots.srv.api.model.domain.GetOrderList;

public class GetOrderBORequest {
	
		private GetOrderList request;

		public GetOrderList getRequest() {
			return request;
		}

		public void setRequest(GetOrderList request) {
			this.request = request;
		}
		
}
