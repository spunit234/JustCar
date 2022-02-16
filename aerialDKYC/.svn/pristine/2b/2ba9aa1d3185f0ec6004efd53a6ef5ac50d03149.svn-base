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
import javax.validation.constraints.NotNull;

import com.edios.cdf.entity.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ROLE_RIGHTS")
@Setter
@Getter
@NoArgsConstructor
public class RolesRightEntity extends AbstractEntity {

	private static final long serialVersionUID = 6817234723426629976L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_RIGHT_ID")
	private Long roleRightID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private RoleEntity roleID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MENU_ID")
	private MenuEntity menuID= new MenuEntity();

	@Column(name = "VIEW_ACCESS")
	private boolean viewAccess;

	@Column(name = "INSERT_ACCESS")
	private boolean insertAccess;

	@Column(name = "UPDATE_ACCESS")
	private boolean updateAccess;

	@Column(name = "DELETE_ACCESS")
	private boolean deleteAccess;

	@Column(name = "PRINT_ACCESS")
	private boolean printAccess;

	@Column(name = "EXPORT_ACCESS")
	private boolean exportAccess;

	@Column(name = "TRANSACTION_COUNT")
	private Long transactionCount;

	@Column(name = "IP_ADDRESS")
	private String userIPAddress;

	@Column(name = "RECORD_TYPE")
	private Character recordType;

	@Column(name = "CREATED_BY")
	private Integer createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "LAST_MODIFIED_BY")
	private Integer lastModifiedBy;

	@Column(name = "LAST_MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

}
