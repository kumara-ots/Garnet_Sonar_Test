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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "accountfooter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accountfooter.findAll", query = "SELECT a FROM Accountfooter a"),
    @NamedQuery(name = "Accountfooter.findByAccountFooterId", query = "SELECT a FROM Accountfooter a WHERE a.accountFooterId = :accountFooterId"),
    @NamedQuery(name = "Accountfooter.findByAgencyCode", query = "SELECT a FROM Accountfooter a WHERE a.agencyCode = :agencyCode"),
    @NamedQuery(name = "Accountfooter.findByRecentCauses", query = "SELECT a FROM Accountfooter a WHERE a.recentCauses = :recentCauses"),
    @NamedQuery(name = "Accountfooter.findByHelp", query = "SELECT a FROM Accountfooter a WHERE a.help = :help"),
    @NamedQuery(name = "Accountfooter.findByGallery", query = "SELECT a FROM Accountfooter a WHERE a.gallery = :gallery"),
    @NamedQuery(name = "Accountfooter.findByMeetOurPartners", query = "SELECT a FROM Accountfooter a WHERE a.meetOurPartners = :meetOurPartners"),
    @NamedQuery(name = "Accountfooter.findByPhoneNumber", query = "SELECT a FROM Accountfooter a WHERE a.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Accountfooter.findByEmailId", query = "SELECT a FROM Accountfooter a WHERE a.emailId = :emailId"),
    @NamedQuery(name = "Accountfooter.findByFBUrl", query = "SELECT a FROM Accountfooter a WHERE a.fBUrl = :fBUrl"),
    @NamedQuery(name = "Accountfooter.findByWhatsAppUrl", query = "SELECT a FROM Accountfooter a WHERE a.whatsAppUrl = :whatsAppUrl"),
    @NamedQuery(name = "Accountfooter.findByInstaUrl", query = "SELECT a FROM Accountfooter a WHERE a.instaUrl = :instaUrl"),
    @NamedQuery(name = "Accountfooter.findByFooterMoreInfo1", query = "SELECT a FROM Accountfooter a WHERE a.footerMoreInfo1 = :footerMoreInfo1"),
    @NamedQuery(name = "Accountfooter.findByFooterMoreInfo2", query = "SELECT a FROM Accountfooter a WHERE a.footerMoreInfo2 = :footerMoreInfo2"),
    @NamedQuery(name = "Accountfooter.findByFooterMoreInfo3", query = "SELECT a FROM Accountfooter a WHERE a.footerMoreInfo3 = :footerMoreInfo3"),
    @NamedQuery(name = "Accountfooter.findByFooterMoreInfo4", query = "SELECT a FROM Accountfooter a WHERE a.footerMoreInfo4 = :footerMoreInfo4"),
    @NamedQuery(name = "Accountfooter.findByFooterMoreInfo5", query = "SELECT a FROM Accountfooter a WHERE a.footerMoreInfo5 = :footerMoreInfo5"),
    @NamedQuery(name = "Accountfooter.findByFooterDataInfo1", query = "SELECT a FROM Accountfooter a WHERE a.footerDataInfo1 = :footerDataInfo1"),
    @NamedQuery(name = "Accountfooter.findByFooterDataInfo2", query = "SELECT a FROM Accountfooter a WHERE a.footerDataInfo2 = :footerDataInfo2"),
    @NamedQuery(name = "Accountfooter.findByFooterDataInfo3", query = "SELECT a FROM Accountfooter a WHERE a.footerDataInfo3 = :footerDataInfo3"),
    @NamedQuery(name = "Accountfooter.findByFooterDataInfo4", query = "SELECT a FROM Accountfooter a WHERE a.footerDataInfo4 = :footerDataInfo4"),
    @NamedQuery(name = "Accountfooter.findByFooterDataInfo5", query = "SELECT a FROM Accountfooter a WHERE a.footerDataInfo5 = :footerDataInfo5")})
public class Accountfooter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "account_footer_id")
    private Integer accountFooterId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "agency_code")
    private String agencyCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "recent_causes")
    private String recentCauses;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "help")
    private String help;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "gallery")
    private String gallery;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "meet_our_partners")
    private String meetOurPartners;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email_id")
    private String emailId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "fb_url")
    private String fBUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "whatsapp_url")
    private String whatsAppUrl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "insta_url")
    private String instaUrl;
    @Size(max = 45)
    @Column(name = "footer_more_info1")
    private String footerMoreInfo1;
    @Size(max = 45)
    @Column(name = "footer_more_info2")
    private String footerMoreInfo2;
    @Size(max = 45)
    @Column(name = "footer_more_info3")
    private String footerMoreInfo3;
    @Size(max = 45)
    @Column(name = "footer_more_info4")
    private String footerMoreInfo4;
    @Size(max = 45)
    @Column(name = "footer_more_info5")
    private String footerMoreInfo5;
    @Size(max = 255)
    @Column(name = "footer_data_info1")
    private String footerDataInfo1;
    @Size(max = 255)
    @Column(name = "footer_data_info2")
    private String footerDataInfo2;
    @Size(max = 255)
    @Column(name = "footer_data_info3")
    private String footerDataInfo3;
    @Size(max = 255)
    @Column(name = "footer_data_info4")
    private String footerDataInfo4;
    @Size(max = 255)
    @Column(name = "footer_data_info5")
    private String footerDataInfo5;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @ManyToOne
    private Useraccounts accountId;

    public Accountfooter() {
    }

    public Accountfooter(Integer accountFooterId) {
        this.accountFooterId = accountFooterId;
    }

    public Accountfooter(Integer accountFooterId, String agencyCode, String recentCauses, String help, String gallery, String meetOurPartners, String phoneNumber, String emailId, String fBUrl, String whatsAppUrl, String instaUrl) {
        this.accountFooterId = accountFooterId;
        this.agencyCode = agencyCode;
        this.recentCauses = recentCauses;
        this.help = help;
        this.gallery = gallery;
        this.meetOurPartners = meetOurPartners;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.fBUrl = fBUrl;
        this.whatsAppUrl = whatsAppUrl;
        this.instaUrl = instaUrl;
    }

    public Integer getAccountFooterId() {
        return accountFooterId;
    }

    public void setAccountFooterId(Integer accountFooterId) {
        this.accountFooterId = accountFooterId;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getRecentCauses() {
        return recentCauses;
    }

    public void setRecentCauses(String recentCauses) {
        this.recentCauses = recentCauses;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getMeetOurPartners() {
        return meetOurPartners;
    }

    public void setMeetOurPartners(String meetOurPartners) {
        this.meetOurPartners = meetOurPartners;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFBUrl() {
        return fBUrl;
    }

    public void setFBUrl(String fBUrl) {
        this.fBUrl = fBUrl;
    }

    public String getWhatsAppUrl() {
        return whatsAppUrl;
    }

    public void setWhatsAppUrl(String whatsAppUrl) {
        this.whatsAppUrl = whatsAppUrl;
    }

    public String getInstaUrl() {
        return instaUrl;
    }

    public void setInstaUrl(String instaUrl) {
        this.instaUrl = instaUrl;
    }

    public String getFooterMoreInfo1() {
        return footerMoreInfo1;
    }

    public void setFooterMoreInfo1(String footerMoreInfo1) {
        this.footerMoreInfo1 = footerMoreInfo1;
    }

    public String getFooterMoreInfo2() {
        return footerMoreInfo2;
    }

    public void setFooterMoreInfo2(String footerMoreInfo2) {
        this.footerMoreInfo2 = footerMoreInfo2;
    }

    public String getFooterMoreInfo3() {
        return footerMoreInfo3;
    }

    public void setFooterMoreInfo3(String footerMoreInfo3) {
        this.footerMoreInfo3 = footerMoreInfo3;
    }

    public String getFooterMoreInfo4() {
        return footerMoreInfo4;
    }

    public void setFooterMoreInfo4(String footerMoreInfo4) {
        this.footerMoreInfo4 = footerMoreInfo4;
    }

    public String getFooterMoreInfo5() {
        return footerMoreInfo5;
    }

    public void setFooterMoreInfo5(String footerMoreInfo5) {
        this.footerMoreInfo5 = footerMoreInfo5;
    }

    public String getFooterDataInfo1() {
        return footerDataInfo1;
    }

    public void setFooterDataInfo1(String footerDataInfo1) {
        this.footerDataInfo1 = footerDataInfo1;
    }

    public String getFooterDataInfo2() {
        return footerDataInfo2;
    }

    public void setFooterDataInfo2(String footerDataInfo2) {
        this.footerDataInfo2 = footerDataInfo2;
    }

    public String getFooterDataInfo3() {
        return footerDataInfo3;
    }

    public void setFooterDataInfo3(String footerDataInfo3) {
        this.footerDataInfo3 = footerDataInfo3;
    }

    public String getFooterDataInfo4() {
        return footerDataInfo4;
    }

    public void setFooterDataInfo4(String footerDataInfo4) {
        this.footerDataInfo4 = footerDataInfo4;
    }

    public String getFooterDataInfo5() {
        return footerDataInfo5;
    }

    public void setFooterDataInfo5(String footerDataInfo5) {
        this.footerDataInfo5 = footerDataInfo5;
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
        hash += (accountFooterId != null ? accountFooterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accountfooter)) {
            return false;
        }
        Accountfooter other = (Accountfooter) object;
        if ((this.accountFooterId == null && other.accountFooterId != null) || (this.accountFooterId != null && !this.accountFooterId.equals(other.accountFooterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fuso.enterprise.ots.srv.server.model.entity.Accountfooter[ accountFooterId=" + accountFooterId + " ]";
    }
    
}
