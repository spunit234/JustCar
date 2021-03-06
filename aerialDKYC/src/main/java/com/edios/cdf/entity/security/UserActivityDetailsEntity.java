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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_activity_details")
@Setter
@Getter
@NoArgsConstructor
public class UserActivityDetailsEntity extends AbstractEntity {
	 
	private static final long serialVersionUID = 8975386519985416321L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ACTIVITY_ID")
	private Long userActivityId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_LOGIN_ID")
	private UserLoginDetailsEntity userLoginDetails;

	@Column(name = "ACTIVITY_DATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date activityDateTime;	
	
	@Column(name = "SCREEN_NAME")
	private String screenName;

	@Column(name = "ACTIVITY_NAME")
	private String activityName;
	
	@Column(name = "SEARCH_CRITERIA")
	private String searchCriteria;
	
	@Column(name = "SEARCH_RECORD_COUNT")
	private Long searchedRecordCount;
	
	@Column(name = "TABLE_NAME")
	private String tableName;
	
	@Column(name = "TABLE_PK_COL_NAME")
	private String pkColName;
	
	@Column(name = "TABLE_PK_COL_VALUE")
	private Long pkColValue;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "Imp_Field_Value")
	private String impFieldValue;
	
	
	
}
