package com.edios.cdf.util;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeleteRecords implements Serializable {
	private static final long serialVersionUID = -4406381028759245136L;
	private Long id;
	private Integer modifiedBy;
	private Long transactionCount;
	private List<Long> employeeID; 
	private String customValue;
	
	Long loginDetailId;
	String screenName;
	String activityName;
	Boolean activityFlag;

}
