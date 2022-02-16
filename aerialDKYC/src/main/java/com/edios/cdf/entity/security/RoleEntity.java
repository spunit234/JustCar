package com.edios.cdf.entity.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.edios.cdf.entity.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Setter
@Getter
@NoArgsConstructor
public class RoleEntity extends AbstractEntity {

	private static final long serialVersionUID = -7959006084525327336L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private Long roleID;

	@Column(name = "ROLE_NAME")
	private String roleName;

	@Column(name = "ROLE_DESC")
	private String roleDesc;

	@Column(name = "ROLE_STATUS")
	private String roleStatus;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "roleID")
	private List<RolesRightEntity> roleRight = new ArrayList<>();

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

	/*public void addRoleRights(RolesRightEntity roleRight) {
		this.roleRight.add(roleRight);
	}

	public void removeRoleRights(RolesRightEntity roleRight) {
		this.roleRight.remove(roleRight);
	}*/

}
