/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_distributor_company_details")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "OtsDistributorCompanyDetails.findAll", query = "SELECT o FROM OtsDistributorCompanyDetails o"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsDistributorCompanyId", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsDistributorCompanyId = :otsDistributorCompanyId"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsCompanyName", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsCompanyName = :otsCompanyName"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsCompanyAddress", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsCompanyAddress = :otsCompanyAddress"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsCompanyPincode", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsCompanyPincode = :otsCompanyPincode"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsCompanyContactNo", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsCompanyContactNo = :otsCompanyContactNo"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsCompanyEmailid", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsCompanyEmailid = :otsCompanyEmailid"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsTaxAvailability", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsTaxAvailability = :otsTaxAvailability"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsCompanyTaxNumber", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsCompanyTaxNumber = :otsCompanyTaxNumber"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsCreatedDate", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsCreatedDate = :otsCreatedDate"),
		@NamedQuery(name = "OtsDistributorCompanyDetails.findByOtsUpdatedDate", query = "SELECT o FROM OtsDistributorCompanyDetails o WHERE o.otsUpdatedDate = :otsUpdatedDate") })
public class OtsDistributorCompanyDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ots_distributor_company_id")
	private Integer otsDistributorCompanyId;
	@Size(max = 45)
	@Column(name = "ots_company_name")
	private String otsCompanyName;
	@Size(max = 1000)
	@Column(name = "ots_company_address")
	private String otsCompanyAddress;
	@Size(max = 45)
	@Column(name = "ots_company_pincode")
	private String otsCompanyPincode;
	@Size(max = 45)
	@Column(name = "ots_company_contact_no")
	private String otsCompanyContactNo;
	@Size(max = 45)
	@Column(name = "ots_company_emailid")
	private String otsCompanyEmailid;
	@Column(name = "ots_tax_availability")
	private Boolean otsTaxAvailability;
	@Size(max = 45)
	@Column(name = "ots_company_tax_number")
	private String otsCompanyTaxNumber;
	@Column(name = "ots_created_date", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date otsCreatedDate;
	@Column(name = "ots_updated_date", insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date otsUpdatedDate;
	@Column(name = "company_business_registration")
	private String companyBusinessRegistration;
	@Column(name = "authorized_signatory_proof")
	private String authorizedSignatoryProof;
	@Column(name = "bank_confirmation_on_bank_account")
	private String bankConfirmationOnBankAccount;
	@Column(name = "tax_card")
	private String taxCard;
	@JoinColumn(name = "ots_distributor_id", referencedColumnName = "ots_users_id")
	@ManyToOne(optional = false)
	private OtsUsers otsDistributorId;

	public OtsDistributorCompanyDetails() {
	}

	public OtsDistributorCompanyDetails(Integer otsDistributorCompanyId) {
		this.otsDistributorCompanyId = otsDistributorCompanyId;
	}

	public Integer getOtsDistributorCompanyId() {
		return otsDistributorCompanyId;
	}

	public void setOtsDistributorCompanyId(Integer otsDistributorCompanyId) {
		this.otsDistributorCompanyId = otsDistributorCompanyId;
	}

	public String getOtsCompanyName() {
		return otsCompanyName;
	}

	public void setOtsCompanyName(String otsCompanyName) {
		this.otsCompanyName = otsCompanyName;
	}

	public String getOtsCompanyAddress() {
		return otsCompanyAddress;
	}

	public void setOtsCompanyAddress(String otsCompanyAddress) {
		this.otsCompanyAddress = otsCompanyAddress;
	}

	public String getOtsCompanyPincode() {
		return otsCompanyPincode;
	}

	public void setOtsCompanyPincode(String otsCompanyPincode) {
		this.otsCompanyPincode = otsCompanyPincode;
	}

	public String getOtsCompanyContactNo() {
		return otsCompanyContactNo;
	}

	public void setOtsCompanyContactNo(String otsCompanyContactNo) {
		this.otsCompanyContactNo = otsCompanyContactNo;
	}

	public String getOtsCompanyEmailid() {
		return otsCompanyEmailid;
	}

	public void setOtsCompanyEmailid(String otsCompanyEmailid) {
		this.otsCompanyEmailid = otsCompanyEmailid;
	}

	public Boolean getOtsTaxAvailability() {
		return otsTaxAvailability;
	}

	public void setOtsTaxAvailability(Boolean otsTaxAvailability) {
		this.otsTaxAvailability = otsTaxAvailability;
	}

	public String getOtsCompanyTaxNumber() {
		return otsCompanyTaxNumber;
	}

	public void setOtsCompanyTaxNumber(String otsCompanyTaxNumber) {
		this.otsCompanyTaxNumber = otsCompanyTaxNumber;
	}

	public Date getOtsCreatedDate() {
		return otsCreatedDate;
	}

	public void setOtsCreatedDate(Date otsCreatedDate) {
		this.otsCreatedDate = otsCreatedDate;
	}

	public Date getOtsUpdatedDate() {
		return otsUpdatedDate;
	}

	public void setOtsUpdatedDate(Date otsUpdatedDate) {
		this.otsUpdatedDate = otsUpdatedDate;
	}

	public String getCompanyBusinessRegistration() {
		return companyBusinessRegistration;
	}

	public void setCompanyBusinessRegistration(String companyBusinessRegistration) {
		this.companyBusinessRegistration = companyBusinessRegistration;
	}

	public String getAuthorizedSignatoryProof() {
		return authorizedSignatoryProof;
	}

	public void setAuthorizedSignatoryProof(String authorizedSignatoryProof) {
		this.authorizedSignatoryProof = authorizedSignatoryProof;
	}

	public String getBankConfirmationOnBankAccount() {
		return bankConfirmationOnBankAccount;
	}

	public void setBankConfirmationOnBankAccount(String bankConfirmationOnBankAccount) {
		this.bankConfirmationOnBankAccount = bankConfirmationOnBankAccount;
	}

	public String getTaxCard() {
		return taxCard;
	}

	public void setTaxCard(String taxCard) {
		this.taxCard = taxCard;
	}
	
	public OtsUsers getOtsDistributorId() {
		return otsDistributorId;
	}

	public void setOtsDistributorId(OtsUsers otsDistributorId) {
		this.otsDistributorId = otsDistributorId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (otsDistributorCompanyId != null ? otsDistributorCompanyId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof OtsDistributorCompanyDetails)) {
			return false;
		}
		OtsDistributorCompanyDetails other = (OtsDistributorCompanyDetails) object;
		if ((this.otsDistributorCompanyId == null && other.otsDistributorCompanyId != null)
				|| (this.otsDistributorCompanyId != null
						&& !this.otsDistributorCompanyId.equals(other.otsDistributorCompanyId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.fuso.enterprise.ots.srv.server.model.entity.OtsDistributorCompanyDetails[ otsDistributorCompanyId="
				+ otsDistributorCompanyId + " ]";
	}

}
