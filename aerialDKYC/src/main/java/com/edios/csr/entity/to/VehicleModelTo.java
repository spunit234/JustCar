package com.edios.csr.entity.to;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehicleModelTo implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private Long modelId;
	private String modelName;
	private String modelStatus;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
}
