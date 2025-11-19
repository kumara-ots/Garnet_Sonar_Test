package com.fuso.enterprise.ots.srv.api.model.domain;

import java.util.List;

import javax.validation.Valid;

public class ZipBase64Images {
	
	@Valid
    private List<Base64ByteImage> base64Image;
	private String password;
	private String filename;

	public List<Base64ByteImage> getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(List<Base64ByteImage> base64Image) {
		this.base64Image = base64Image;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	

}
