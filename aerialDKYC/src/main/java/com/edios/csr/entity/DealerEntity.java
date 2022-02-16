package com.edios.csr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edios.cdf.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dealers")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class DealerEntity extends AbstractEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DEALER_ID")
	private Long dealerId;
	
	@Column(name = "MAKE_ID")
	private Long makeId;
	
	@Column(name ="DEALER_NAME")
	private String dealerName;
	
	@Column(name ="DEALER_TYPE")
	private String dealerType;
	
	@Column(name ="ADDRESS")
	private String address;
	
	@Column(name ="CITY")
	private String city;
	
	@Column(name ="POSTAL_CODE")
	private String postalCode;
	
	@Column(name ="District")
	private String district;
	
	@Column(name ="STATE_ID")
	private Long state;
	
	@Column(name ="CONTACT_NUMBER")
	private String contactNumber;
		
	@Column(name ="ALT_CONTACT_NUMBER")
	private String altContactNumber;
	
	@Column(name ="EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name ="DEALER_STATUS")
	private String dealerStatus;
	
	
	@Column(name ="TRANSACTION_COUNT")
	private Long transactionCount;
	
	@Column(name ="IP_ADDRESS")
	private String ipAddress;
	
	@Column(name ="RECORD_TYPE")
	private Character recordType;
	
	@Column(name ="CREATED_BY")
	private Long createdBy;
	
	@Column(name ="CREATED_DATE")
	private Date createdDate;
	
	@Column(name ="LAST_MODIFIED_BY")
	private Long lastModifiedBy;
	
	
	@Column(name ="LAST_MODIFIED_DATE")
	private Date lastModifiedDate;
	
	

}
