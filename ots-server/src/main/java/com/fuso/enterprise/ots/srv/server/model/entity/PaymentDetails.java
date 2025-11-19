/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "payment_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentDetails.findAll", query = "SELECT p FROM PaymentDetails p"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsId", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsId = :paymentDetailsId"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsPannumber", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsPannumber = :paymentDetailsPannumber"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsAadhaarNumber", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsAadhaarNumber = :paymentDetailsAadhaarNumber"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsGst", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsGst = :paymentDetailsGst"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsName", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsName = :paymentDetailsName"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsBillableAddress", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsBillableAddress = :paymentDetailsBillableAddress"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsAddress", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsAddress = :paymentDetailsAddress"),
    @NamedQuery(name = "PaymentDetails.findByPaymentEmail", query = "SELECT p FROM PaymentDetails p WHERE p.paymentEmail = :paymentEmail"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsPhonenumber", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsPhonenumber = :paymentDetailsPhonenumber"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsDescription", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsDescription = :paymentDetailsDescription"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsAccountNumber", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsAccountNumber = :paymentDetailsAccountNumber"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsBankname", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsBankname = :paymentDetailsBankname"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsIfseCode", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsIfseCode = :paymentDetailsIfseCode"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsBranchName", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsBranchName = :paymentDetailsBranchName"),
    @NamedQuery(name = "PaymentDetails.findByPaymentDetailsBankDetails", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDetailsBankDetails = :paymentDetailsBankDetails"),
    @NamedQuery(name = "PaymentDetails.findByCodStatus", query = "SELECT p FROM PaymentDetails p WHERE p.codStatus = :codStatus"),
    @NamedQuery(name = "PaymentDetails.findByUseraccountsAccountId", query = "SELECT p FROM PaymentDetails p WHERE p.useraccountsAccountId = :useraccountsAccountId")})
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "payment_details_id")
    private Integer paymentDetailsId;
    @Size(max = 45)
    @Column(name = "payment_details_pannumber")
    private String paymentDetailsPannumber;
    @Size(max = 45)
    @Column(name = "payment_details_aadhaar_number")
    private String paymentDetailsAadhaarNumber;
    @Size(max = 45)
    @Column(name = "payment_details_gst")
    private String paymentDetailsGst;
    @Size(max = 45)
    @Column(name = "payment_details_name")
    private String paymentDetailsName;
    @Size(max = 45)
    @Column(name = "payment_details_billable_address")
    private String paymentDetailsBillableAddress;
    @Size(max = 45)
    @Column(name = "payment_details_address")
    private String paymentDetailsAddress;
    @Size(max = 255)
    @Column(name = "payment_email")
    private String paymentEmail;
    @Size(max = 45)
    @Column(name = "payment_details_phonenumber")
    private String paymentDetailsPhonenumber;
    @Size(max = 45)
    @Column(name = "payment_details_description")
    private String paymentDetailsDescription;
    @Size(max = 45)
    @Column(name = "payment_details_account_number")
    private String paymentDetailsAccountNumber;
    @Size(max = 45)
    @Column(name = "payment_details_bankname")
    private String paymentDetailsBankname;
    @Size(max = 45)
    @Column(name = "payment_details_ifse_code")
    private String paymentDetailsIfseCode;
    @Size(max = 45)
    @Column(name = "payment_details_branch_name")
    private String paymentDetailsBranchName;
    @Size(max = 45)
    @Column(name = "payment_details_bank_details")
    private String paymentDetailsBankDetails;
    @Size(max = 50)
    @Column(name = "cod_Status")
    private String codStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "useraccounts_AccountId")
    private int useraccountsAccountId;

    public PaymentDetails() {
    }

    public PaymentDetails(Integer paymentDetailsId) {
        this.paymentDetailsId = paymentDetailsId;
    }

    public PaymentDetails(Integer paymentDetailsId, int useraccountsAccountId) {
        this.paymentDetailsId = paymentDetailsId;
        this.useraccountsAccountId = useraccountsAccountId;
    }

    public Integer getPaymentDetailsId() {
        return paymentDetailsId;
    }

    public void setPaymentDetailsId(Integer paymentDetailsId) {
        this.paymentDetailsId = paymentDetailsId;
    }

    public String getPaymentDetailsPannumber() {
        return paymentDetailsPannumber;
    }

    public void setPaymentDetailsPannumber(String paymentDetailsPannumber) {
        this.paymentDetailsPannumber = paymentDetailsPannumber;
    }

    public String getPaymentDetailsAadhaarNumber() {
        return paymentDetailsAadhaarNumber;
    }

    public void setPaymentDetailsAadhaarNumber(String paymentDetailsAadhaarNumber) {
        this.paymentDetailsAadhaarNumber = paymentDetailsAadhaarNumber;
    }

    public String getPaymentDetailsGst() {
        return paymentDetailsGst;
    }

    public void setPaymentDetailsGst(String paymentDetailsGst) {
        this.paymentDetailsGst = paymentDetailsGst;
    }

    public String getPaymentDetailsName() {
        return paymentDetailsName;
    }

    public void setPaymentDetailsName(String paymentDetailsName) {
        this.paymentDetailsName = paymentDetailsName;
    }

    public String getPaymentDetailsBillableAddress() {
        return paymentDetailsBillableAddress;
    }

    public void setPaymentDetailsBillableAddress(String paymentDetailsBillableAddress) {
        this.paymentDetailsBillableAddress = paymentDetailsBillableAddress;
    }

    public String getPaymentDetailsAddress() {
        return paymentDetailsAddress;
    }

    public void setPaymentDetailsAddress(String paymentDetailsAddress) {
        this.paymentDetailsAddress = paymentDetailsAddress;
    }

    public String getPaymentEmail() {
        return paymentEmail;
    }

    public void setPaymentEmail(String paymentEmail) {
        this.paymentEmail = paymentEmail;
    }

    public String getPaymentDetailsPhonenumber() {
        return paymentDetailsPhonenumber;
    }

    public void setPaymentDetailsPhonenumber(String paymentDetailsPhonenumber) {
        this.paymentDetailsPhonenumber = paymentDetailsPhonenumber;
    }

    public String getPaymentDetailsDescription() {
        return paymentDetailsDescription;
    }

    public void setPaymentDetailsDescription(String paymentDetailsDescription) {
        this.paymentDetailsDescription = paymentDetailsDescription;
    }

    public String getPaymentDetailsAccountNumber() {
        return paymentDetailsAccountNumber;
    }

    public void setPaymentDetailsAccountNumber(String paymentDetailsAccountNumber) {
        this.paymentDetailsAccountNumber = paymentDetailsAccountNumber;
    }

    public String getPaymentDetailsBankname() {
        return paymentDetailsBankname;
    }

    public void setPaymentDetailsBankname(String paymentDetailsBankname) {
        this.paymentDetailsBankname = paymentDetailsBankname;
    }

    public String getPaymentDetailsIfseCode() {
        return paymentDetailsIfseCode;
    }

    public void setPaymentDetailsIfseCode(String paymentDetailsIfseCode) {
        this.paymentDetailsIfseCode = paymentDetailsIfseCode;
    }

    public String getPaymentDetailsBranchName() {
        return paymentDetailsBranchName;
    }

    public void setPaymentDetailsBranchName(String paymentDetailsBranchName) {
        this.paymentDetailsBranchName = paymentDetailsBranchName;
    }

    public String getPaymentDetailsBankDetails() {
        return paymentDetailsBankDetails;
    }

    public void setPaymentDetailsBankDetails(String paymentDetailsBankDetails) {
        this.paymentDetailsBankDetails = paymentDetailsBankDetails;
    }

    public String getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(String codStatus) {
        this.codStatus = codStatus;
    }

    public int getUseraccountsAccountId() {
        return useraccountsAccountId;
    }

    public void setUseraccountsAccountId(int useraccountsAccountId) {
        this.useraccountsAccountId = useraccountsAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentDetailsId != null ? paymentDetailsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentDetails)) {
            return false;
        }
        PaymentDetails other = (PaymentDetails) object;
        if ((this.paymentDetailsId == null && other.paymentDetailsId != null) || (this.paymentDetailsId != null && !this.paymentDetailsId.equals(other.paymentDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.PaymentDetails[ paymentDetailsId=" + paymentDetailsId + " ]";
    }
    
}
