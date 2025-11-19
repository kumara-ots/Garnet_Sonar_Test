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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "ots_distributer_payment_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsDistributerPaymentDetails.findAll", query = "SELECT o FROM OtsDistributerPaymentDetails o"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsId", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsId = :paymentDetailsId"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsPannumber", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsPannumber = :paymentDetailsPannumber"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsAadhaarNumber", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsAadhaarNumber = :paymentDetailsAadhaarNumber"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsGst", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsGst = :paymentDetailsGst"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsName", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsName = :paymentDetailsName"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsBillableAddress", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsBillableAddress = :paymentDetailsBillableAddress"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsAddress", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsAddress = :paymentDetailsAddress"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentEmail", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentEmail = :paymentEmail"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsPhonenumber", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsPhonenumber = :paymentDetailsPhonenumber"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsDescription", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsDescription = :paymentDetailsDescription"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsAccountNumber", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsAccountNumber = :paymentDetailsAccountNumber"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsBankname", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsBankname = :paymentDetailsBankname"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsIfseCode", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsIfseCode = :paymentDetailsIfseCode"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsBranchName", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsBranchName = :paymentDetailsBranchName"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsBankDetails", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsBankDetails = :paymentDetailsBankDetails"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByCodStatus", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.codStatus = :codStatus"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsCreatedDate", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsCreatedDate = :paymentDetailsCreatedDate"),
    @NamedQuery(name = "OtsDistributerPaymentDetails.findByPaymentDetailsUpdatedDate", query = "SELECT o FROM OtsDistributerPaymentDetails o WHERE o.paymentDetailsUpdatedDate = :paymentDetailsUpdatedDate")})
public class OtsDistributerPaymentDetails implements Serializable {

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
    @Column(name = "payment_details_created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDetailsCreatedDate;
    @Column(name = "payment_details_updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDetailsUpdatedDate;
    @JoinColumn(name = "admin_id", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts adminId;
    @JoinColumn(name = "distributer_id", referencedColumnName = "ots_users_id")
    @OneToOne
    private OtsUsers distributerId;

    public OtsDistributerPaymentDetails() {
    }

    public OtsDistributerPaymentDetails(Integer paymentDetailsId) {
        this.paymentDetailsId = paymentDetailsId;
    }

    public OtsDistributerPaymentDetails(Integer paymentDetailsId, Date paymentDetailsCreatedDate) {
        this.paymentDetailsId = paymentDetailsId;
        this.paymentDetailsCreatedDate = paymentDetailsCreatedDate;
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

    public Date getPaymentDetailsCreatedDate() {
        return paymentDetailsCreatedDate;
    }

    public void setPaymentDetailsCreatedDate(Date paymentDetailsCreatedDate) {
        this.paymentDetailsCreatedDate = paymentDetailsCreatedDate;
    }

    public Date getPaymentDetailsUpdatedDate() {
        return paymentDetailsUpdatedDate;
    }

    public void setPaymentDetailsUpdatedDate(Date paymentDetailsUpdatedDate) {
        this.paymentDetailsUpdatedDate = paymentDetailsUpdatedDate;
    }

    public Useraccounts getAdminId() {
        return adminId;
    }

    public void setAdminId(Useraccounts adminId) {
        this.adminId = adminId;
    }

    public OtsUsers getDistributerId() {
        return distributerId;
    }

    public void setDistributerId(OtsUsers distributerId) {
        this.distributerId = distributerId;
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
        if (!(object instanceof OtsDistributerPaymentDetails)) {
            return false;
        }
        OtsDistributerPaymentDetails other = (OtsDistributerPaymentDetails) object;
        if ((this.paymentDetailsId == null && other.paymentDetailsId != null) || (this.paymentDetailsId != null && !this.paymentDetailsId.equals(other.paymentDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsDistributerPaymentDetails[ paymentDetailsId=" + paymentDetailsId + " ]";
    }
    
}
