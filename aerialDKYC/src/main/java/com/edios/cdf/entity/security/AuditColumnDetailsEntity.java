package com.edios.cdf.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edios.cdf.entity.AbstractEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "audit_column_details")
public class AuditColumnDetailsEntity extends AbstractEntity{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUDIT_COLUMN_ID")
	private Long auditColID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AUDIT_ROW_ID")
	private AuditRowDetailsEntity auditRowDetail ;
	
	@Column(name = "COLUMN_NAME")
	private String columnName;
	
	@Column(name = "OLD_VALUE")
	private String oldValue;
	
	@Column(name = "NEW_VALUE")
	private String newValue;
	
}
