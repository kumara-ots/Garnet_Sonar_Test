package com.fuso.enterprise.ots.srv.api.model.domain;

public class UserAccounts 
{
	private String AccountId;
	private String Username;
	private String password;
	private String fisrtName;
	private String lastName;
	private String userRole;
	private String accountType;
	private String Email;
	private String Phone;
	private String dateCreated;
	private String dateModified;
	private String userCreated;
	private String userModified;
	private String exportedFileName;
	private String exportedTime;
	private String statusOfExport;
	private String createdUser;
	public String getAccountId() {
		return AccountId;
	}
	public void setAccountId(String accountId) {
		AccountId = accountId;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFisrtName() {
		return fisrtName;
	}
	public void setFisrtName(String fisrtName) {
		this.fisrtName = fisrtName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDateModified() {
		return dateModified;
	}
	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}
	public String getUserCreated() {
		return userCreated;
	}
	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
	}
	public String getUserModified() {
		return userModified;
	}
	public void setUserModified(String userModified) {
		this.userModified = userModified;
	}
	public String getExportedFileName() {
		return exportedFileName;
	}
	public void setExportedFileName(String exportedFileName) {
		this.exportedFileName = exportedFileName;
	}
	public String getExportedTime() {
		return exportedTime;
	}
	public void setExportedTime(String exportedTime) {
		this.exportedTime = exportedTime;
	}
	public String getStatusOfExport() {
		return statusOfExport;
	}
	public void setStatusOfExport(String statusOfExport) {
		this.statusOfExport = statusOfExport;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	
}
