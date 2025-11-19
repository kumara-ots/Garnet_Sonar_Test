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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "accountdetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accountdetails.findAll", query = "SELECT a FROM Accountdetails a"),
    @NamedQuery(name = "Accountdetails.findByAccountDetailsId", query = "SELECT a FROM Accountdetails a WHERE a.accountDetailsId = :accountDetailsId"),
    @NamedQuery(name = "Accountdetails.findByCompanyName", query = "SELECT a FROM Accountdetails a WHERE a.companyName = :companyName"),
    @NamedQuery(name = "Accountdetails.findByCompanyUrl", query = "SELECT a FROM Accountdetails a WHERE a.companyUrl = :companyUrl"),
    @NamedQuery(name = "Accountdetails.findByCompanyLogo", query = "SELECT a FROM Accountdetails a WHERE a.companyLogo = :companyLogo"),
    @NamedQuery(name = "Accountdetails.findByAPIBaseURL", query = "SELECT a FROM Accountdetails a WHERE a.aPIBaseURL = :aPIBaseURL"),
    @NamedQuery(name = "Accountdetails.findByThemeId", query = "SELECT a FROM Accountdetails a WHERE a.themeId = :themeId"),
    @NamedQuery(name = "Accountdetails.findByInvoiceFormatId", query = "SELECT a FROM Accountdetails a WHERE a.invoiceFormatId = :invoiceFormatId"),
    @NamedQuery(name = "Accountdetails.findByDateCreated", query = "SELECT a FROM Accountdetails a WHERE a.dateCreated = :dateCreated"),
    @NamedQuery(name = "Accountdetails.findByDateModified", query = "SELECT a FROM Accountdetails a WHERE a.dateModified = :dateModified"),
    @NamedQuery(name = "Accountdetails.findByUserCreated", query = "SELECT a FROM Accountdetails a WHERE a.userCreated = :userCreated"),
    @NamedQuery(name = "Accountdetails.findByUserModified", query = "SELECT a FROM Accountdetails a WHERE a.userModified = :userModified")})
public class Accountdetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "account_details_id")
    private Integer accountDetailsId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "company_name")
    private String companyName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "company_url")
    private String companyUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "company_logo")
    private String companyLogo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "api_base_url")
    private String aPIBaseURL;
    @Basic(optional = false)
    @NotNull
    @Column(name = "theme_id")
    private int themeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "invoice_format_id")
    private int invoiceFormatId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @Column(name = "user_created")
    private Integer userCreated;
    @Column(name = "user_modified")
    private Integer userModified;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts accountId;

    public Accountdetails() {
    }

    public Accountdetails(Integer accountDetailsId) {
        this.accountDetailsId = accountDetailsId;
    }

    public Accountdetails(Integer accountDetailsId, String companyName, String companyUrl, String companyLogo, String aPIBaseURL, int themeId, int invoiceFormatId, Date dateCreated, Date dateModified) {
        this.accountDetailsId = accountDetailsId;
        this.companyName = companyName;
        this.companyUrl = companyUrl;
        this.companyLogo = companyLogo;
        this.aPIBaseURL = aPIBaseURL;
        this.themeId = themeId;
        this.invoiceFormatId = invoiceFormatId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public Integer getAccountDetailsId() {
        return accountDetailsId;
    }

    public void setAccountDetailsId(Integer accountDetailsId) {
        this.accountDetailsId = accountDetailsId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getAPIBaseURL() {
        return aPIBaseURL;
    }

    public void setAPIBaseURL(String aPIBaseURL) {
        this.aPIBaseURL = aPIBaseURL;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getInvoiceFormatId() {
        return invoiceFormatId;
    }

    public void setInvoiceFormatId(int invoiceFormatId) {
        this.invoiceFormatId = invoiceFormatId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Integer userCreated) {
        this.userCreated = userCreated;
    }

    public Integer getUserModified() {
        return userModified;
    }

    public void setUserModified(Integer userModified) {
        this.userModified = userModified;
    }

    public Useraccounts getAccountId() {
        return accountId;
    }

    public void setAccountId(Useraccounts accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountDetailsId != null ? accountDetailsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accountdetails)) {
            return false;
        }
        Accountdetails other = (Accountdetails) object;
        if ((this.accountDetailsId == null && other.accountDetailsId != null) || (this.accountDetailsId != null && !this.accountDetailsId.equals(other.accountDetailsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.Accountdetails[ accountDetailsId=" + accountDetailsId + " ]";
    }
    
}
