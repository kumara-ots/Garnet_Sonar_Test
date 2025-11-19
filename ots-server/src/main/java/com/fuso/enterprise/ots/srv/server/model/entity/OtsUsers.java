/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fuso.enterprise.ots.srv.server.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bharath
 */
@Entity
@Table(name = "ots_users")	
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtsUsers.findAll", query = "SELECT o FROM OtsUsers o"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersId", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersId = :otsUsersId"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersNumber", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersNumber = :otsUsersNumber"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersFirstname", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersFirstname = :otsUsersFirstname"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersLastname", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersLastname = :otsUsersLastname"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersEmailid", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersEmailid = :otsUsersEmailid"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersStatus", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersStatus = :otsUsersStatus"),
    @NamedQuery(name = "OtsUsers.findByOtsServiceProvider", query = "SELECT o FROM OtsUsers o WHERE o.otsServiceProvider = :otsServiceProvider"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersPassword", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersPassword = :otsUsersPassword"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersContactNo", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersContactNo = :otsUsersContactNo"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersLong", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersLong = :otsUsersLong"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersLat", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersLat = :otsUsersLat"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersRegistrationTransactionId", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersRegistrationTransactionId = :otsUsersRegistrationTransactionId"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersRegistrationAmount", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersRegistrationAmount = :otsUsersRegistrationAmount"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersRegistrationPaymentId", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersRegistrationPaymentId = :otsUsersRegistrationPaymentId"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersRegistrationPaymentStatus", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersRegistrationPaymentStatus = :otsUsersRegistrationPaymentStatus"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersRegistrationPaymentDate", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersRegistrationPaymentDate = :otsUsersRegistrationPaymentDate"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersAdminFlag", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersAdminFlag = :otsUsersAdminFlag"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersGoogleId", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersGoogleId = :otsUsersGoogleId"),
    @NamedQuery(name = "OtsUsers.findByOtsUsersFacebookId", query = "SELECT o FROM OtsUsers o WHERE o.otsUsersFacebookId = :otsUsersFacebookId"),
    @NamedQuery(name = "OtsUsers.findByOtsHolidaysList", query = "SELECT o FROM OtsUsers o WHERE o.otsHolidaysList = :otsHolidaysList"),
    @NamedQuery(name = "OtsUsers.findByOtsHouseNo", query = "SELECT o FROM OtsUsers o WHERE o.otsHouseNo = :otsHouseNo"),
    @NamedQuery(name = "OtsUsers.findByOtsBuildingName", query = "SELECT o FROM OtsUsers o WHERE o.otsBuildingName = :otsBuildingName"),
    @NamedQuery(name = "OtsUsers.findByOtsStreetName", query = "SELECT o FROM OtsUsers o WHERE o.otsStreetName = :otsStreetName"),
    @NamedQuery(name = "OtsUsers.findByOtsCityName", query = "SELECT o FROM OtsUsers o WHERE o.otsCityName = :otsCityName"),
    @NamedQuery(name = "OtsUsers.findByOtsPincode", query = "SELECT o FROM OtsUsers o WHERE o.otsPincode = :otsPincode"),
    @NamedQuery(name = "OtsUsers.findByOtsDistrictName", query = "SELECT o FROM OtsUsers o WHERE o.otsDistrictName = :otsDistrictName"),
    @NamedQuery(name = "OtsUsers.findByOtsStateName", query = "SELECT o FROM OtsUsers o WHERE o.otsStateName = :otsStateName"),
    @NamedQuery(name = "OtsUsers.findByOtsCountryName", query = "SELECT o FROM OtsUsers o WHERE o.otsCountryName = :otsCountryName"),
    @NamedQuery(name = "OtsUsers.findByOtsCompanyRegistration", query = "SELECT o FROM OtsUsers o WHERE o.otsCompanyRegistration = :otsCompanyRegistration"),
    @NamedQuery(name = "OtsUsers.findByOtsTaxCard", query = "SELECT o FROM OtsUsers o WHERE o.otsTaxCard = :otsTaxCard"),
    @NamedQuery(name = "OtsUsers.findByOtsComputerCard", query = "SELECT o FROM OtsUsers o WHERE o.otsComputerCard = :otsComputerCard"),
    @NamedQuery(name = "OtsUsers.findByOtsSignatoryIdCard", query = "SELECT o FROM OtsUsers o WHERE o.otsSignatoryIdCard = :otsSignatoryIdCard"),
    @NamedQuery(name = "OtsUsers.findByOtsTradeLicense", query = "SELECT o FROM OtsUsers o WHERE o.otsTradeLicense = :otsTradeLicense")
    })
public class OtsUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ots_users_id")
    private UUID otsUsersId;
    @Column(name = "ots_users_number")
    private String otsUsersNumber;
    @Column(name = "ots_users_firstname")
    private String otsUsersFirstname;
    @Column(name = "ots_users_lastname")
    private String otsUsersLastname;
    @Column(name = "ots_users_emailid")
    private String otsUsersEmailid;
    @Column(name = "ots_users_status")
    private String otsUsersStatus;
    @Basic(optional = false)
    @Column(name = "ots_service_provider", nullable = false)
    private Boolean otsServiceProvider = false;
    @Column(name = "ots_users_password")
    private String otsUsersPassword;
    @Column(name = "ots_users_contact_no")
    private String otsUsersContactNo;
    @Column(name = "ots_users_long")
    private String otsUsersLong;
    @Column(name = "ots_users_lat")
    private String otsUsersLat;
    @Column(name = "ots_users_registration_transaction_id")
    private String otsUsersRegistrationTransactionId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ots_users_registration_amount")
    private BigDecimal otsUsersRegistrationAmount;
    @Column(name = "ots_users_registration_payment_id")
    private String otsUsersRegistrationPaymentId;
    @Column(name = "ots_users_registration_payment_status")
    private String otsUsersRegistrationPaymentStatus;
    @Column(name = "ots_users_registration_payment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsUsersRegistrationPaymentDate;
    @Column(name = "ots_users_registration_invoice")
    private String otsUsersRegistrationInvoice;
    @Column(name = "ots_users_admin_flag")
    private String otsUsersAdminFlag;
    @Column(name = "ots_users_google_id")
    private String otsUsersGoogleId;
    @Column(name = "ots_users_facebook_id")
    private String otsUsersFacebookId;
    @Column(name = "ots_users_image")
    private String otsUsersImage;
    @Column(name = "ots_users_created", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsUsersCreated;
    @Column(name = "ots_users_updated", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date otsUsersUpdated;
    @Column(name = "ots_distributor_id")
    private UUID otsDistributorId;
    @Column(name = "ots_holidays_list")
    private String otsHolidaysList;
    @Column(name = "ots_users_rejection_reason")
    private String otsUsersRejectionReason;
    @Column(name = "ots_users_created_by")
    private String otsUsersCreatedBy;
    @Column(name = "ots_company_name")
    private String otsCompanyName;
    @Column(name = "ots_house_no")
    private String otsHouseNo;
    @Column(name = "ots_building_name")
    private String otsBuildingName;
    @Column(name = "ots_street_name")
    private String otsStreetName;
    @Column(name = "ots_city_name")
    private String otsCityName;
    @Column(name = "ots_pincode")
    private String otsPincode;
    @Column(name = "ots_district_name")
    private String otsDistrictName;
    @Column(name = "ots_state_name")
    private String otsStateName;
    @Column(name = "ots_country_name")
    private String otsCountryName;
    @Column(name = "ots_company_registration")
    private String otsCompanyRegistration;
    @Column(name = "ots_tax_card")
    private String otsTaxCard;
    @Column(name = "ots_computer_card")
    private String otsComputerCard;
    @Column(name = "ots_signatory_id_card")
    private String otsSignatoryIdCard;
    @Column(name = "ots_trade_license")
    private String otsTradeLicense;
    @OneToMany(mappedBy = "otsCustomerId")
    private Collection<OtsServiceRatingReview> otsServiceRatingReviewCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "otsUsersId")
    private OtsUserMapping otsUserMapping;
    @OneToMany(mappedBy = "otsUsersId")
    private Collection<OtsProductStockHistory> otsProductStockHistoryCollection;
    @OneToMany(mappedBy = "otsCustomerId")
    private Collection<OtsCart> otsCartCollection;
    @OneToOne(mappedBy = "distributerId")
    private OtsDistributerPaymentDetails otsDistributerPaymentDetails;
    @OneToMany(mappedBy = "otsDistributorId")
    private Collection<OtsProductLocationMapping> otsProductLocationMappingCollection;
    @OneToMany(mappedBy = "otsUsersId")
    private Collection<OtsProductStock> otsProductStockCollection;
    @OneToMany(mappedBy = "otsDistributorId")
    private Collection<OtsDistributorCompanyDetails> otsDistributorCompanyDetailsCollection;
    @OneToMany(mappedBy = "otsDistributorId")
    private Collection<OtsProduct> otsProductCollection;
    @JoinColumn(name = "ots_user_role_id", referencedColumnName = "ots_user_role_id")
    @ManyToOne
    private OtsUserRole otsUserRoleId;
    @JoinColumn(name = "created_user", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts createdUser;
    @OneToMany(mappedBy = "otsUsersId")
    private Collection<OtsRegistrationTransactionCancelRecords> otsRegistrationTransactionCancelRecordsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsServiceCustomerId")
    private Collection<OtsServiceOrder> otsServiceOrderCollection;
    @OneToMany(mappedBy = "otsAssignedId")
    private Collection<OtsOrderProduct> otsOrderProductCollection;
    @OneToMany(mappedBy = "otsDistributorId")
    private Collection<OtsOrderProduct> otsOrderProductCollection1;
    @OneToMany(mappedBy = "otsCustomerId")
    private Collection<OtsProductWishlist> otsProductWishlistCollection;
    @OneToMany(mappedBy = "otsServiceProviderId")
    private Collection<OtsService> otsServiceCollection;
    @OneToMany(mappedBy = "otsCustomerId")
    private Collection<OtsRatingReview> otsRatingReviewCollection;
    @OneToMany(mappedBy = "otsCustomerId")
    private Collection<OtsOrder> otsOrderCollection;
    @OneToMany(mappedBy = "otsOrderCreatedBy")
    private Collection<OtsOrder> otsOrderCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsEmployeeId")
    private Collection<OtsServiceOrderDetails> otsServiceOrderDetailsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "otsProviderId")
    private Collection<OtsServiceOrderDetails> otsServiceOrderDetailsCollection1;
    @OneToMany(mappedBy = "otsCustomerId")
    private Collection<OtsCustomerChangeAddress> otsCustomerChangeAddressCollection;
    @OneToMany(mappedBy = "otsDistributorId")
    private Collection<OtsDistributorBanner> otsDistributorBannerCollection;

    public OtsUsers() {
    }

    public OtsUsers(UUID otsUsersId) {
        this.otsUsersId = otsUsersId;
    }

    public OtsUsers(UUID otsUsersId, boolean otsServiceProvider) {
        this.otsUsersId = otsUsersId;
        this.otsServiceProvider = otsServiceProvider;
    }

    public UUID getOtsUsersId() {
        return otsUsersId;
    }

    public void setOtsUsersId(UUID otsUsersId) {
        this.otsUsersId = otsUsersId;
    }

    public String getOtsUsersNumber() {
        return otsUsersNumber;
    }

    public void setOtsUsersNumber(String otsUsersNumber) {
        this.otsUsersNumber = otsUsersNumber;
    }

    public String getOtsUsersFirstname() {
        return otsUsersFirstname;
    }

    public void setOtsUsersFirstname(String otsUsersFirstname) {
        this.otsUsersFirstname = otsUsersFirstname;
    }

    public String getOtsUsersLastname() {
        return otsUsersLastname;
    }

    public void setOtsUsersLastname(String otsUsersLastname) {
        this.otsUsersLastname = otsUsersLastname;
    }

    public String getOtsUsersEmailid() {
        return otsUsersEmailid;
    }

    public void setOtsUsersEmailid(String otsUsersEmailid) {
        this.otsUsersEmailid = otsUsersEmailid;
    }

    public String getOtsUsersStatus() {
        return otsUsersStatus;
    }

    public void setOtsUsersStatus(String otsUsersStatus) {
        this.otsUsersStatus = otsUsersStatus;
    }

    public Boolean getOtsServiceProvider() {
        return otsServiceProvider;
    }

    public void setOtsServiceProvider(Boolean otsServiceProvider) {
        this.otsServiceProvider = otsServiceProvider;
    }

    public String getOtsUsersPassword() {
        return otsUsersPassword;
    }

    public void setOtsUsersPassword(String otsUsersPassword) {
        this.otsUsersPassword = otsUsersPassword;
    }

    public String getOtsUsersContactNo() {
        return otsUsersContactNo;
    }

    public void setOtsUsersContactNo(String otsUsersContactNo) {
        this.otsUsersContactNo = otsUsersContactNo;
    }

    public String getOtsUsersLong() {
        return otsUsersLong;
    }

    public void setOtsUsersLong(String otsUsersLong) {
        this.otsUsersLong = otsUsersLong;
    }

    public String getOtsUsersLat() {
        return otsUsersLat;
    }

    public void setOtsUsersLat(String otsUsersLat) {
        this.otsUsersLat = otsUsersLat;
    }

    public String getOtsUsersRegistrationTransactionId() {
        return otsUsersRegistrationTransactionId;
    }

    public void setOtsUsersRegistrationTransactionId(String otsUsersRegistrationTransactionId) {
        this.otsUsersRegistrationTransactionId = otsUsersRegistrationTransactionId;
    }

    public BigDecimal getOtsUsersRegistrationAmount() {
        return otsUsersRegistrationAmount;
    }

    public void setOtsUsersRegistrationAmount(BigDecimal otsUsersRegistrationAmount) {
        this.otsUsersRegistrationAmount = otsUsersRegistrationAmount;
    }

    public String getOtsUsersRegistrationPaymentId() {
        return otsUsersRegistrationPaymentId;
    }

    public void setOtsUsersRegistrationPaymentId(String otsUsersRegistrationPaymentId) {
        this.otsUsersRegistrationPaymentId = otsUsersRegistrationPaymentId;
    }

    public String getOtsUsersRegistrationPaymentStatus() {
        return otsUsersRegistrationPaymentStatus;
    }

    public void setOtsUsersRegistrationPaymentStatus(String otsUsersRegistrationPaymentStatus) {
        this.otsUsersRegistrationPaymentStatus = otsUsersRegistrationPaymentStatus;
    }

    public Date getOtsUsersRegistrationPaymentDate() {
        return otsUsersRegistrationPaymentDate;
    }

    public void setOtsUsersRegistrationPaymentDate(Date otsUsersRegistrationPaymentDate) {
        this.otsUsersRegistrationPaymentDate = otsUsersRegistrationPaymentDate;
    }
    
    public String getOtsUsersRegistrationInvoice() {
        return otsUsersRegistrationInvoice;
    }

    public void setOtsUsersRegistrationInvoice(String otsUsersRegistrationInvoice) {
        this.otsUsersRegistrationInvoice = otsUsersRegistrationInvoice;
    }
    
    public String getOtsUsersAdminFlag() {
        return otsUsersAdminFlag;
    }

    public void setOtsUsersAdminFlag(String otsUsersAdminFlag) {
        this.otsUsersAdminFlag = otsUsersAdminFlag;
    }

    public String getOtsUsersGoogleId() {
		return otsUsersGoogleId;
	}

	public void setOtsUsersGoogleId(String otsUsersGoogleId) {
		this.otsUsersGoogleId = otsUsersGoogleId;
	}

	public String getOtsUsersFacebookId() {
		return otsUsersFacebookId;
	}

	public void setOtsUsersFacebookId(String otsUsersFacebookId) {
		this.otsUsersFacebookId = otsUsersFacebookId;
	}

    public String getOtsUsersImage() {
        return otsUsersImage;
    }

    public void setOtsUsersImage(String otsUsersImage) {
        this.otsUsersImage = otsUsersImage;
    }

    public Date getOtsUsersCreated() {
        return otsUsersCreated;
    }

    public void setOtsUsersCreated(Date otsUsersCreated) {
        this.otsUsersCreated = otsUsersCreated;
    }

    public Date getOtsUsersUpdated() {
		return otsUsersUpdated;
	}

	public void setOtsUsersUpdated(Date otsUsersUpdated) {
		this.otsUsersUpdated = otsUsersUpdated;
	}

	public UUID getOtsDistributorId() {
        return otsDistributorId;
    }

    public void setOtsDistributorId(UUID otsDistributorId) {
        this.otsDistributorId = otsDistributorId;
    }

    public String getOtsHolidaysList() {
        return otsHolidaysList;
    }

    public void setOtsHolidaysList(String otsHolidaysList) {
        this.otsHolidaysList = otsHolidaysList;
    }

    public String getOtsCompanyName() {
		return otsCompanyName;
	}

	public void setOtsCompanyName(String otsCompanyName) {
		this.otsCompanyName = otsCompanyName;
	}

	public String getOtsHouseNo() {
		return otsHouseNo;
	}

	public void setOtsHouseNo(String otsHouseNo) {
		this.otsHouseNo = otsHouseNo;
	}

	public String getOtsBuildingName() {
		return otsBuildingName;
	}

	public void setOtsBuildingName(String otsBuildingName) {
		this.otsBuildingName = otsBuildingName;
	}

	public String getOtsStreetName() {
		return otsStreetName;
	}

	public void setOtsStreetName(String otsStreetName) {
		this.otsStreetName = otsStreetName;
	}

	public String getOtsCityName() {
		return otsCityName;
	}

	public void setOtsCityName(String otsCityName) {
		this.otsCityName = otsCityName;
	}

	public String getOtsPincode() {
		return otsPincode;
	}

	public void setOtsPincode(String otsPincode) {
		this.otsPincode = otsPincode;
	}

	public String getOtsDistrictName() {
		return otsDistrictName;
	}

	public void setOtsDistrictName(String otsDistrictName) {
		this.otsDistrictName = otsDistrictName;
	}

	public String getOtsStateName() {
		return otsStateName;
	}

	public void setOtsStateName(String otsStateName) {
		this.otsStateName = otsStateName;
	}

	public String getOtsCountryName() {
		return otsCountryName;
	}

	public void setOtsCountryName(String otsCountryName) {
		this.otsCountryName = otsCountryName;
	}

	public String getOtsCompanyRegistration() {
		return otsCompanyRegistration;
	}

	public void setOtsCompanyRegistration(String otsCompanyRegistration) {
		this.otsCompanyRegistration = otsCompanyRegistration;
	}

	public String getOtsTaxCard() {
		return otsTaxCard;
	}

	public void setOtsTaxCard(String otsTaxCard) {
		this.otsTaxCard = otsTaxCard;
	}

	public String getOtsComputerCard() {
		return otsComputerCard;
	}

	public void setOtsComputerCard(String otsComputerCard) {
		this.otsComputerCard = otsComputerCard;
	}

	public String getOtsSignatoryIdCard() {
		return otsSignatoryIdCard;
	}

	public void setOtsSignatoryIdCard(String otsSignatoryIdCard) {
		this.otsSignatoryIdCard = otsSignatoryIdCard;
	}

	public String getOtsTradeLicense() {
		return otsTradeLicense;
	}

	public void setOtsTradeLicense(String otsTradeLicense) {
		this.otsTradeLicense = otsTradeLicense;
	}

	public OtsUserMapping getOtsUserMapping() {
        return otsUserMapping;
    }

    public void setOtsUserMapping(OtsUserMapping otsUserMapping) {
        this.otsUserMapping = otsUserMapping;
    }
    
    @XmlTransient
    public Collection<OtsServiceRatingReview> getOtsServiceRatingReviewCollection() {
        return otsServiceRatingReviewCollection;
    }

    public void setOtsServiceRatingReviewCollection(Collection<OtsServiceRatingReview> otsServiceRatingReviewCollection) {
        this.otsServiceRatingReviewCollection = otsServiceRatingReviewCollection;
    }

    @XmlTransient
    public Collection<OtsProductStockHistory> getOtsProductStockHistoryCollection() {
        return otsProductStockHistoryCollection;
    }

    public void setOtsProductStockHistoryCollection(Collection<OtsProductStockHistory> otsProductStockHistoryCollection) {
        this.otsProductStockHistoryCollection = otsProductStockHistoryCollection;
    }

    @XmlTransient
    public Collection<OtsCart> getOtsCartCollection() {
        return otsCartCollection;
    }

    public void setOtsCartCollection(Collection<OtsCart> otsCartCollection) {
        this.otsCartCollection = otsCartCollection;
    }

    public OtsDistributerPaymentDetails getOtsDistributerPaymentDetails() {
        return otsDistributerPaymentDetails;
    }

    public void setOtsDistributerPaymentDetails(OtsDistributerPaymentDetails otsDistributerPaymentDetails) {
        this.otsDistributerPaymentDetails = otsDistributerPaymentDetails;
    }

    @XmlTransient
    public Collection<OtsProductLocationMapping> getOtsProductLocationMappingCollection() {
        return otsProductLocationMappingCollection;
    }

    public void setOtsProductLocationMappingCollection(Collection<OtsProductLocationMapping> otsProductLocationMappingCollection) {
        this.otsProductLocationMappingCollection = otsProductLocationMappingCollection;
    }

    @XmlTransient
    public Collection<OtsProductStock> getOtsProductStockCollection() {
        return otsProductStockCollection;
    }

    public void setOtsProductStockCollection(Collection<OtsProductStock> otsProductStockCollection) {
        this.otsProductStockCollection = otsProductStockCollection;
    }

    @XmlTransient
    public Collection<OtsDistributorCompanyDetails> getOtsDistributorCompanyDetailsCollection() {
        return otsDistributorCompanyDetailsCollection;
    }

    public void setOtsDistributorCompanyDetailsCollection(Collection<OtsDistributorCompanyDetails> otsDistributorCompanyDetailsCollection) {
        this.otsDistributorCompanyDetailsCollection = otsDistributorCompanyDetailsCollection;
    }

    @XmlTransient
    public Collection<OtsProduct> getOtsProductCollection() {
        return otsProductCollection;
    }

    public void setOtsProductCollection(Collection<OtsProduct> otsProductCollection) {
        this.otsProductCollection = otsProductCollection;
    }

    public OtsUserRole getOtsUserRoleId() {
        return otsUserRoleId;
    }

    public void setOtsUserRoleId(OtsUserRole otsUserRoleId) {
        this.otsUserRoleId = otsUserRoleId;
    }

    public Useraccounts getCreatedUser() {
        return createdUser;
    }

    public String getOtsUsersRejectionReason() {
		return otsUsersRejectionReason;
	}

	public void setOtsUsersRejectionReason(String otsUsersRejectionReason) {
		this.otsUsersRejectionReason = otsUsersRejectionReason;
	}

	public String getOtsUsersCreatedBy() {
		return otsUsersCreatedBy;
	}

	public void setOtsUsersCreatedBy(String otsUsersCreatedBy) {
		this.otsUsersCreatedBy = otsUsersCreatedBy;
	}

	public void setCreatedUser(Useraccounts createdUser) {
        this.createdUser = createdUser;
    }

    @XmlTransient
    public Collection<OtsRegistrationTransactionCancelRecords> getOtsRegistrationTransactionCancelRecordsCollection() {
        return otsRegistrationTransactionCancelRecordsCollection;
    }

    public void setOtsRegistrationTransactionCancelRecordsCollection(Collection<OtsRegistrationTransactionCancelRecords> otsRegistrationTransactionCancelRecordsCollection) {
        this.otsRegistrationTransactionCancelRecordsCollection = otsRegistrationTransactionCancelRecordsCollection;
    }

    @XmlTransient
    public Collection<OtsServiceOrder> getOtsServiceOrderCollection() {
        return otsServiceOrderCollection;
    }

    public void setOtsServiceOrderCollection(Collection<OtsServiceOrder> otsServiceOrderCollection) {
        this.otsServiceOrderCollection = otsServiceOrderCollection;
    }

    @XmlTransient
    public Collection<OtsOrderProduct> getOtsOrderProductCollection() {
        return otsOrderProductCollection;
    }

    public void setOtsOrderProductCollection(Collection<OtsOrderProduct> otsOrderProductCollection) {
        this.otsOrderProductCollection = otsOrderProductCollection;
    }

    @XmlTransient
    public Collection<OtsOrderProduct> getOtsOrderProductCollection1() {
        return otsOrderProductCollection1;
    }

    public void setOtsOrderProductCollection1(Collection<OtsOrderProduct> otsOrderProductCollection1) {
        this.otsOrderProductCollection1 = otsOrderProductCollection1;
    }

    @XmlTransient
    public Collection<OtsProductWishlist> getOtsProductWishlistCollection() {
        return otsProductWishlistCollection;
    }

    public void setOtsProductWishlistCollection(Collection<OtsProductWishlist> otsProductWishlistCollection) {
        this.otsProductWishlistCollection = otsProductWishlistCollection;
    }

    @XmlTransient
    public Collection<OtsService> getOtsServiceCollection() {
        return otsServiceCollection;
    }

    public void setOtsServiceCollection(Collection<OtsService> otsServiceCollection) {
        this.otsServiceCollection = otsServiceCollection;
    }

    @XmlTransient
    public Collection<OtsRatingReview> getOtsRatingReviewCollection() {
        return otsRatingReviewCollection;
    }

    public void setOtsRatingReviewCollection(Collection<OtsRatingReview> otsRatingReviewCollection) {
        this.otsRatingReviewCollection = otsRatingReviewCollection;
    }

    @XmlTransient
    public Collection<OtsOrder> getOtsOrderCollection() {
        return otsOrderCollection;
    }

    public void setOtsOrderCollection(Collection<OtsOrder> otsOrderCollection) {
        this.otsOrderCollection = otsOrderCollection;
    }

    @XmlTransient
    public Collection<OtsOrder> getOtsOrderCollection1() {
        return otsOrderCollection1;
    }

    public void setOtsOrderCollection1(Collection<OtsOrder> otsOrderCollection1) {
        this.otsOrderCollection1 = otsOrderCollection1;
    }

    @XmlTransient
    public Collection<OtsServiceOrderDetails> getOtsServiceOrderDetailsCollection() {
        return otsServiceOrderDetailsCollection;
    }

    public void setOtsServiceOrderDetailsCollection(Collection<OtsServiceOrderDetails> otsServiceOrderDetailsCollection) {
        this.otsServiceOrderDetailsCollection = otsServiceOrderDetailsCollection;
    }

    @XmlTransient
    public Collection<OtsServiceOrderDetails> getOtsServiceOrderDetailsCollection1() {
        return otsServiceOrderDetailsCollection1;
    }

    public void setOtsServiceOrderDetailsCollection1(Collection<OtsServiceOrderDetails> otsServiceOrderDetailsCollection1) {
        this.otsServiceOrderDetailsCollection1 = otsServiceOrderDetailsCollection1;
    }

    @XmlTransient
    public Collection<OtsCustomerChangeAddress> getOtsCustomerChangeAddressCollection() {
        return otsCustomerChangeAddressCollection;
    }

    public void setOtsCustomerChangeAddressCollection(Collection<OtsCustomerChangeAddress> otsCustomerChangeAddressCollection) {
        this.otsCustomerChangeAddressCollection = otsCustomerChangeAddressCollection;
    }

    @XmlTransient
    public Collection<OtsDistributorBanner> getOtsDistributorBannerCollection() {
        return otsDistributorBannerCollection;
    }

    public void setOtsDistributorBannerCollection(Collection<OtsDistributorBanner> otsDistributorBannerCollection) {
        this.otsDistributorBannerCollection = otsDistributorBannerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otsUsersId != null ? otsUsersId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OtsUsers)) {
            return false;
        }
        OtsUsers other = (OtsUsers) object;
        if ((this.otsUsersId == null && other.otsUsersId != null) || (this.otsUsersId != null && !this.otsUsersId.equals(other.otsUsersId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.OtsUsers[ otsUsersId=" + otsUsersId + " ]";
    }
    
}
