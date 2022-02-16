package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.csr.entity.DealerExecutiveEntity2;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehiclesBean extends AbstractBean {

	private static final long serialVersionUID = 6342094965674122373L;
	private Long vehicleId;
	private Long inquiryId;
	private Long make;
	private Long model;
	private Long colour;
	private String modelVariant;
	private Long fuelType;
	private Long bodyType;
	private String vehicleType;
	private String loanStatus;

	private String engineCC;
	private String transmission;
	private Long kms;
	private String sunroof;
	private String alloysWheel;
	private Long sittingCapacity;
	private Long vehicleCylinders;
	private String vehicleEngineNo;
	private String vehicleChassisNo;
	private String vehicleRegNo;
	private Date vehicleRegDate;
	private Date taxPaidUpto;
	private Date permitUpto;
	private Date passingUpto;
	private Date vehicleManufDate;
	private String vehicleAge;
	private String odoReading;
	private String ownerSequence;
	private String previousOwner;
	private Date previousRegDate;
	private String rcStatus;
	private String bankName;
	private String keyAvailable;
	private String anyMajorAccidents;
	private String serviceRecords;
	private Date vehicleBuyDate;
	private String hypothecated;
	private String otherName;
	private String vehicleStatus;
	private Long priceMin;
	private Long priceMax;
	private String vehicleDesc;
	private Long transactionCount;
	private String ipAddress;

	private Character recordType;
	private Long createdBy;
	private Date createdDate;
	private Long lastModifiedBy;
	private Date lastModifiedDate;
	private Date vehicleTransactionDate;
	private Double vehicleTransactionAmount;
	private String vehicleTransactionNotes;
	private String finalizedCar;
	private String runningLoan;
	private Double loanAmount;

	private String vehiclePaymentType;
	private String beneficiaryName;
	private String beneficiaryBankName;
	private String beneficiaryAccountNo;
	private Double beneficiaryAmount;
	private String beneficiaryIFSCCode;
	private Double vehicleValue;
	private DealerExecutiveEntity2 dealerExecutiveId;
	private String vehicleStockType;
	private Double ownedInterest;
	private Double ownedRepairCharges;
	private Double ownedSellMargin;
	private Double ownedSaleValue;
	private Long inquiryOpenDays;
	private Date taxValidUpto;
	private Date fitnessUpto;
	private String prevRegAt;
	private String notes;
	private String chalan;
}
