package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.VehicleModelEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehicleModelSpecsBean extends AbstractBean {

	private static final long serialVersionUID = 6342094965674122373L;
	private VehicleModelEntity modelId;
	private Long vehModelId;
	private Long vehicleModelSpecId;
	private Date priceDate;
//	private Long priceTypeId;
//	private Double priceValue;
//	private String priceTypeStatus;
	
	private VehicleModelPricesBean vehicleModelPricesBean[];

	private Long bodyType;
	private Long fuelType;
	private Long colour;
	private Long transmission;
	private String sunroof;
	private String musicSystem;
	private String alloysWheel;
	private String engineCC;
	private Integer sittingCapacity; 
	private Integer vehicleCylinders;
	private Long userActivityId;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	
	
	private String modelName;
	private String bodyTypeName;
	private String fuelTypeName;
	private String colourName;
	private String transmissionName;
	private String priceDateStr;
	
	

	
	
}
