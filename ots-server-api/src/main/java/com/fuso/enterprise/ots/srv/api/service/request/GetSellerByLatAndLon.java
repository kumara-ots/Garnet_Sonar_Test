package com.fuso.enterprise.ots.srv.api.service.request;

public class GetSellerByLatAndLon {

	private Float latitude;
	
	private Float longitude;
	
	private Integer distance;

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	
	
}
