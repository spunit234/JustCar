package com.edios.cdf.util;

import java.util.Date;

public class TransactionData{

   private Long transactionCount;
   
	private Character recordType;
	
	private Long primaryKey;
	
	
	
	
	public Long getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Long getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(Long transactionCount) {
		this.transactionCount = transactionCount;
	}

	public Character getRecordType() {
		return recordType;
	}

	public void setRecordType(Character recordType) {
		this.recordType = recordType;
	}
	
	
}
