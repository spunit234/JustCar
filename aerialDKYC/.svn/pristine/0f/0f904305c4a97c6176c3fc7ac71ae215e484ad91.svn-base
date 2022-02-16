package com.edios.cdf.entity.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.edios.cdf.entity.AbstractEntity;
import com.edios.cdf.entity.common.ApplicationParameterListEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SITES")
@Setter
@Getter
@NoArgsConstructor
public class SiteEntity extends AbstractEntity {

	private static final long serialVersionUID = -7959006084525327336L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SITE_ID")
	private Long siteID;

	@Column(name = "ACCOUNT_ID")
	private Integer accountID;

	@Column(name = "SITE_NAME")
	private String siteName;

	@Column(name = "SITE_CODE")
	private String siteCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SITE_TYPE_LIST_ID", nullable = false)
	private ApplicationParameterListEntity siteTypeListID;

	@Column(name = "SITE_STATUS")
	private String siteStatus;

	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "ZIP_CODE")
	private String zipCode;

	@Column(name = "WORK_PHONE")
	private String workPhone;

	@Column(name = "FAX_PHONE")
	private String faxPhone;

	@Column(name = "EMAIL_ADDRESS")
	private String emailID;

	@Column(name = "PARENT_SITE_ID")
	private Long parentSiteID;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "TRANSACTION_COUNT ")
	private Long transactionCount;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "RECORD_Type")
	private Character recordType;

	@Column(name = "CREATED_BY")
	private Integer createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "LAST_MODIFIED_BY")
	private Integer lastModifiedBy;

	@Column(name = "LAST_MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedOn;

}
