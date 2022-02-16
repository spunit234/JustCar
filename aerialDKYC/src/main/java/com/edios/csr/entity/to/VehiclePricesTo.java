package com.edios.csr.entity.to;

import java.io.Serializable;
import java.util.Date;

import com.edios.csr.entity.VehicleModelEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehiclePricesTo implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private Long vehiclePriceId;
	private VehicleModelEntity modelId;
	private Date priceDate;
	private Long priceTypeId;
	private Double priceValue;
	private String priceTypeStatus;
	private Long userActivityId;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private String parameterListValue;
	private Long parameterListID;
	private Double price;
	private String status;
	private String makeName;
	private String modelName;
}
