package com.edios.cdf.entity.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.edios.cdf.entity.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "MENUS")
@Getter @Setter @NoArgsConstructor
public class MenuEntity extends AbstractEntity {

	private static final long serialVersionUID = 2274827598484051640L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MENU_ID")
	private Integer menuID;

	@Column(name = "MENU_NAME")
	private String menuName;

	@Column(name = "MENU_DESC")
	private String menuDesc;

	@Column(name = "ACCOUNT_ID")
	private Integer accountID;
	
	@Column(name = "INSERT_ACCESS")
	private Integer insertAccess;

	@Column(name = "UPDATE_ACCESS")
	private Integer updateAccess;

	@Column(name = "DELETE_ACCESS")
	private Integer deleteAccess;

	@Column(name = "VIEW_ACCESS")
	private Integer viewAccess;

	@Column(name = "PRINT_ACCESS")
	private Integer printAccess;

	@Column(name = "EXPORT_ACCESS")
	private Integer exportAccess;
	
	@Column(name = "MENU_SEQUENCE")
	private Double menuSequence;

	@Column(name = "MENU_TYPE_LIST_ID")
	private Integer menuTypeListID;

	@Column(name = "MENU_STATUS")
	private String menuStatus;

	@Column(name = "PAGE_URL")
	private String pageUrl;

	@Column(name = "PARENT_MENU_ID")
	private Integer parentMenuID;
	
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

	@Column(name = "RECORD_TYPE")
	private Character recordType;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "TRANSACTION_COUNT")
	private Long transactionCount;
	
	//Other Column for Angular
	@Column(name="MENU_ICON")
	private String menuIcon;
	
	@Column(name="MENU_ACTIVE_OPTION")
	private String menuActiveOption;
	
}