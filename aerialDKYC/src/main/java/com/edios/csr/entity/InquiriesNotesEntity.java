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
@Table(name = "inquiry_notes")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InquiriesNotesEntity extends AbstractEntity implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "INQUIRY_NOTE_ID")
		private Long inquiryNoteId;
		
		@Column(name = "INQUIRY_ID")
		private Long inquiryId;
		
		@Column(name = "NOTE_DATE_TIME")
		private Date noteDateTime;
		
		@Column(name = "INQUIRY_NOTES")
		private String inquiryNotes;
		
		@Column(name = "TRANSACTION_COUNT")
		private Long transactionCount;
		
		@Column(name = "IP_ADDRESS")
		private String ipAddress;
		
		@Column(name = "RECORD_TYPE")
		private Character recordType;
		
		@Column(name = "CREATED_BY")
		private Long createdBy;
		
		@Column(name = "CREATED_DATE")
		private Date createdDate;
		
		@Column(name = "LAST_MODIFIED_BY")
		private Long lastModifiedBy;
		
		@Column(name = "LAST_MODIFIED_DATE")
		private Date lastModifiedDate;

		@Column(name = "STAFF_ID")
		private Long staffId;
	}



