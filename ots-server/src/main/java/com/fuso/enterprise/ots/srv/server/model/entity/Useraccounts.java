/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jeevan
 */
@Entity
@Table(name = "useraccounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Useraccounts.findAll", query = "SELECT u FROM Useraccounts u"),
    @NamedQuery(name = "Useraccounts.findByAccountId", query = "SELECT u FROM Useraccounts u WHERE u.accountId = :accountId"),
    @NamedQuery(name = "Useraccounts.findByUsername", query = "SELECT u FROM Useraccounts u WHERE u.username = :username"),
    @NamedQuery(name = "Useraccounts.findByPassword", query = "SELECT u FROM Useraccounts u WHERE u.password = :password"),
    @NamedQuery(name = "Useraccounts.findByFirstName", query = "SELECT u FROM Useraccounts u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "Useraccounts.findByLastName", query = "SELECT u FROM Useraccounts u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "Useraccounts.findByUserRole", query = "SELECT u FROM Useraccounts u WHERE u.userRole = :userRole"),
    @NamedQuery(name = "Useraccounts.findByAccountType", query = "SELECT u FROM Useraccounts u WHERE u.accountType = :accountType"),
    @NamedQuery(name = "Useraccounts.findByEmail", query = "SELECT u FROM Useraccounts u WHERE u.email = :email"),
    @NamedQuery(name = "Useraccounts.findByPhone", query = "SELECT u FROM Useraccounts u WHERE u.phone = :phone"),
    @NamedQuery(name = "Useraccounts.findByDateCreated", query = "SELECT u FROM Useraccounts u WHERE u.dateCreated = :dateCreated"),
    @NamedQuery(name = "Useraccounts.findByDateModified", query = "SELECT u FROM Useraccounts u WHERE u.dateModified = :dateModified"),
    @NamedQuery(name = "Useraccounts.findByUserCreated", query = "SELECT u FROM Useraccounts u WHERE u.userCreated = :userCreated"),
    @NamedQuery(name = "Useraccounts.findByUserModified", query = "SELECT u FROM Useraccounts u WHERE u.userModified = :userModified"),
    @NamedQuery(name = "Useraccounts.findByExportedFileName", query = "SELECT u FROM Useraccounts u WHERE u.exportedFileName = :exportedFileName"),
    @NamedQuery(name = "Useraccounts.findByExportedTime", query = "SELECT u FROM Useraccounts u WHERE u.exportedTime = :exportedTime"),
    @NamedQuery(name = "Useraccounts.findByStatusOfExport", query = "SELECT u FROM Useraccounts u WHERE u.statusOfExport = :statusOfExport"),
    @NamedQuery(name = "Useraccounts.findByCreatedUser", query = "SELECT u FROM Useraccounts u WHERE u.createdUser = :createdUser")})
public class Useraccounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_id")
    private UUID accountId;
    @Size(max = 250)
    @Column(name = "user_name")
    private String username;
    @Size(max = 250)
    @Column(name = "password")
    private String password;
    @Size(max = 250)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 250)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_role")
    private Integer userRole;
    @Size(max = 250)
    @Column(name = "account_type")
    private String accountType;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "phone")
    private String phone;
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @Column(name = "user_created")
    private Integer userCreated;
    @Column(name = "user_modified")
    private Integer userModified;
    @Size(max = 250)
    @Column(name = "exported_file_name")
    private String exportedFileName;
    @Column(name = "exported_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exportedTime;
    @Size(max = 45)
    @Column(name = "status_of_export")
    private String statusOfExport;
    @Size(max = 45)
    @Column(name = "created_user")
    private String createdUser;
    @OneToMany(mappedBy = "createdUser")
    private Collection<OtsProduct> otsProductCollection;
    @OneToMany(mappedBy = "createdUser")
    private Collection<OtsProductCategoryMapping> otsProductCategoryMappingCollection;
    @OneToMany(mappedBy = "accountId")
    private Collection<Accountdetails> accountdetailsCollection;
    @OneToMany(mappedBy = "otsAccountId")
    private Collection<OtsSubadminValidity> otsSubadminValidityCollection;
    @OneToMany(mappedBy = "useraccountsAccountId")
    private Collection<PaymentDetails> paymentDetailsCollection;
    @OneToMany(mappedBy = "adminId")
    private Collection<OtsDistributerPaymentDetails> otsDistributerPaymentDetailsCollection;
    @OneToMany(mappedBy = "accountId")
    private Collection<Accountfooter> accountfooterCollection;
    @OneToMany(mappedBy = "createdUser")
    private Collection<OtsUsers> otsUsersCollection;
    @OneToMany(mappedBy = "createdUser")
    private Collection<OtsOrderProduct> otsOrderProductCollection;
    @OneToMany(mappedBy = "otsAdminId")
    private Collection<OtsCoupon> otsCouponCollection;
    @OneToMany(mappedBy = "accountId")
    private Collection<Accountbannerinfo> accountbannerinfoCollection;

    public Useraccounts() {
    }

    public Useraccounts(UUID accountId) {
        this.accountId = accountId;
    }

    public Useraccounts(UUID accountId, Integer userRole) {
        this.accountId = accountId;
        this.userRole = userRole;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getExportedFileName() {
        return exportedFileName;
    }

    public void setExportedFileName(String exportedFileName) {
        this.exportedFileName = exportedFileName;
    }

    public Date getExportedTime() {
        return exportedTime;
    }

    public void setExportedTime(Date exportedTime) {
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

    

    @XmlTransient
    public Collection<OtsProduct> getOtsProductCollection() {
        return otsProductCollection;
    }

    public void setOtsProductCollection(Collection<OtsProduct> otsProductCollection) {
        this.otsProductCollection = otsProductCollection;
    }

    @XmlTransient
    public Collection<OtsProductCategoryMapping> getOtsProductCategoryMappingCollection() {
        return otsProductCategoryMappingCollection;
    }

    public void setOtsProductCategoryMappingCollection(Collection<OtsProductCategoryMapping> otsProductCategoryMappingCollection) {
        this.otsProductCategoryMappingCollection = otsProductCategoryMappingCollection;
    }

    @XmlTransient
    public Collection<Accountdetails> getAccountdetailsCollection() {
        return accountdetailsCollection;
    }

    public void setAccountdetailsCollection(Collection<Accountdetails> accountdetailsCollection) {
        this.accountdetailsCollection = accountdetailsCollection;
    }

    @XmlTransient
    public Collection<OtsSubadminValidity> getOtsSubadminValidityCollection() {
        return otsSubadminValidityCollection;
    }

    public void setOtsSubadminValidityCollection(Collection<OtsSubadminValidity> otsSubadminValidityCollection) {
        this.otsSubadminValidityCollection = otsSubadminValidityCollection;
    }

   

    @XmlTransient
    public Collection<PaymentDetails> getPaymentDetailsCollection() {
        return paymentDetailsCollection;
    }

    public void setPaymentDetailsCollection(Collection<PaymentDetails> paymentDetailsCollection) {
        this.paymentDetailsCollection = paymentDetailsCollection;
    }

    @XmlTransient
    public Collection<OtsDistributerPaymentDetails> getOtsDistributerPaymentDetailsCollection() {
        return otsDistributerPaymentDetailsCollection;
    }

    public void setOtsDistributerPaymentDetailsCollection(Collection<OtsDistributerPaymentDetails> otsDistributerPaymentDetailsCollection) {
        this.otsDistributerPaymentDetailsCollection = otsDistributerPaymentDetailsCollection;
    }

    @XmlTransient
    public Collection<Accountfooter> getAccountfooterCollection() {
        return accountfooterCollection;
    }

    public void setAccountfooterCollection(Collection<Accountfooter> accountfooterCollection) {
        this.accountfooterCollection = accountfooterCollection;
    }

    @XmlTransient
    public Collection<OtsUsers> getOtsUsersCollection() {
        return otsUsersCollection;
    }

    public void setOtsUsersCollection(Collection<OtsUsers> otsUsersCollection) {
        this.otsUsersCollection = otsUsersCollection;
    }

    @XmlTransient
    public Collection<OtsOrderProduct> getOtsOrderProductCollection() {
        return otsOrderProductCollection;
    }

    public void setOtsOrderProductCollection(Collection<OtsOrderProduct> otsOrderProductCollection) {
        this.otsOrderProductCollection = otsOrderProductCollection;
    }

    @XmlTransient
    public Collection<OtsCoupon> getOtsCouponCollection() {
        return otsCouponCollection;
    }

    public void setOtsCouponCollection(Collection<OtsCoupon> otsCouponCollection) {
        this.otsCouponCollection = otsCouponCollection;
    }

    @XmlTransient
    public Collection<Accountbannerinfo> getAccountbannerinfoCollection() {
        return accountbannerinfoCollection;
    }

    public void setAccountbannerinfoCollection(Collection<Accountbannerinfo> accountbannerinfoCollection) {
        this.accountbannerinfoCollection = accountbannerinfoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Useraccounts)) {
            return false;
        }
        Useraccounts other = (Useraccounts) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.Useraccounts[ accountId=" + accountId + " ]";
    }
    
}
