package com.fuso.enterprise.ots.srv.api.model.domain;

import javax.validation.constraints.Size;

public class RegistorToUserDetails {
	
    @Size(max = 10)
    private String userid;

    @Size(max = 10)
    private String registrationId;
    
    @Size(max = 20)
    private String firstName;

    @Size(max = 20)
    private String lastName;

    @Size(max = 40)
    private String emailId;

    @Size(max = 10)
    private String status;

    @Size(max = 15)
    private String phonenumber;

    @Size(max = 100)
    private String address1;

    @Size(max = 5)
    private String createdDate;

    @Size(max = 5)
    private String updatedDate;
    
    @Size(max = 5)
    private String address2;
    
    @Size(max = 5)
    private String mappedTo;
    
    @Size(max = 5)
    private String pincode;
    
    @Size(max = 5)
    private String productId;

    @Size(max = 5)
    private String userRoleId;
    
    @Size(max = 5)
    private String password;

    private String deviceId;
    

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getMappedTo() {
		return mappedTo;
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
