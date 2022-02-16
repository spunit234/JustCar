package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehicleModelPricesBean extends AbstractBean {

	private static final long serialVersionUID = 6342094965674122373L;
	private Long vehicleModelPriceId;
	private VehicleModelSpecsBean vehicleModelSpecId;
	private Long priceTypeId;
	private Double priceValue;
	private String priceTypeStatus;
//	
//	private String parameterListCode;
//	private String parameterListDesc;
	private Long parameterListID;
//	private String parameterListValue;O

	private Long userActivityId;
	private Long transactionCount;
	private String ipAddress;
	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;

	private void setParameterListID(Long priceTypeId) {
		this.priceTypeId = priceTypeId;
	}

	private String priceDateStr;

}
