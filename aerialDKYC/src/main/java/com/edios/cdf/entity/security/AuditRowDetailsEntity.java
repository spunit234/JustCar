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
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "audit_row_details")
public class AuditRowDetailsEntity extends AbstractEntity{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUDIT_ROW_ID")
	private Long auditRowID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private UserEntity user ;
	
	@Column(name = "USER_ACTIVITY_ID")
	private Long userActivityID;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUDIT_DATE_TIME")
	private Date auditDateTime;
	
	@Column(name = "TABLE_NAME")
	private String tableName;
	
	@Column(name = "TABLE_PK_COL_NAME")
	private String tablePkColName;
	
	@Column(name = "TABLE_PK_COL_VALUE")
	private Long tablePkColValue;
	
	@Column(name = "ROW_ACTION")
	private Character rowAction;
	
}
