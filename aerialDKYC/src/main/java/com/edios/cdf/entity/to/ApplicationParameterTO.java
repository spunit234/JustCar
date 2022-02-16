package com.edios.cdf.entity.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationParameterTO implements Serializable{

	private static final long serialVersionUID = -4384953656739838999L;

	private Long parameterID;
	
/*	private Integer accountID;*/	
	
	private String parameterName;	
	
	private String parameterCode;	
	
	private String parameterStatus;
	
	private String parameterType;
	
	private String parameterDataType;	
	
	private Integer parameterStatusID;
	
	private Long transactionCount;
		
}
