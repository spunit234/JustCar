package com.edios.csr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.edios.cdf.entity.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VehicelsEntity extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VEHICLE_ID")
	private Long vehicleId;
	@Column(name = "INQUIRY_ID")
	private Long inquiryId;
	@Column(name = "MAKE_ID")
	private Long make;
	@Column(name = "MODEL_ID")
	private Long model;
	@Column(name = "COLOUR")
	private Long colour;
	@Column(name = "MODEL_VARIANT")
	private String modelVariant;
	@Column(name = "FUEL_TYPE")
	private Long fuelType;
	@Column(name = "BODY_TYPE")
	private Long bodyType;
	@Column(name = "VEHICLE_TYPE")
	private String vehicleType;
	@Column(name = "ENGINE_CC")
	private String engineCC;
	@Column(name = "TRANSMISSION")
	private String transmission;
	@Column(name = "KMS")
	private Long kms;
	@Column(name = "Loan_Status")
	private String loanStatus;
	@Column(name = "SUNROOF")
	private String sunroof;
	@Column(name = "ALLOYS_WHEEL")
	private String alloysWheel;
	@Column(name = "SEATING_CAPACITY")
	private Long sittingCapacity;
	@Column(name = "VEHICLE_CYLINDERS")
	private Long vehicleCylinders;
	@Column(name = "VEHICLE_ENGINE_NO")
	private String vehicleEngineNo;
	@Column(name = "VEHICLE_CHESIS_NO")
	private String vehicleChassisNo;
	@Column(name = "VEHICLE_REG_NO")
	private String vehicleRegNo;
	@Column(name = "VEHICLE_REG_DATE")
	private Date vehicleRegDate;
	@Column(name = "TAX_PAID_UPTO")
	private Date taxPaidUpto;
	@Column(name = "PERMIT_UPTO")
	private Date permitUpto;
	@Column(name = "PASSING_UPTO")
	private Date passingUpto;
	@Column(name = "VEHICLE_MANUF_DATE")
	private Date vehicleManufDate;
	@Column(name = "VEHICLE_AGE")
	private String vehicleAge;
	@Column(name = "ODO_READING")
	private String odoReading;
	@Column(name = "OWNER_SEQUENCE")
	private String ownerSequence;
	@Column(name = "PREV_OWNER")
	private String previousOwner;
	@Column(name = "PREV_REG_DATE")
	private Date previousRegDate;
	@Column(name = "RC_STATUS")
	private String rcStatus;
	@Column(name = "BANK_NAME")
	private String bankName;
	@Column(name = "KEY_AVAILABLE")
	private String keyAvailable;
	@Column(name = "ANY_MAJOR_ACCIDENTS")
	private String anyMajorAccidents;
	@Column(name = "SERVICE_RECORDS")
	private String serviceRecords;
	@Column(name = "VEHICLE_BUY_DATE")
	private Date vehicleBuyDate;
	@Column(name = "HYPOTHECATED")
	private String hypothecated;
	@Column(name = "OTHER_NAME")
	private String otherName;
	@Column(name = "VEHICLE_STATUS")
	private String vehicleStatus;
	@Column(name = "PRICE_MIN")
	private Long priceMin;
	@Column(name = "PRICE_MAX")
	private Long priceMax;
	@Column(name = "VEHICLE_DESC")
	private String vehicleDesc;
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

	@Column(name = "VEHICLE_TRANSACTION_DATE")
	private Date vehicleTransactionDate;

	@Column(name = "VEHICLE_TRANSACTION_AMOUNT")
	private Double vehicleTransactionAmount;

	@Column(name = "VEHICLE_TRANSACTION_NOTES")
	private String vehicleTransactionNotes;
	
	@Column(name = "FINALIZED_CAR")
	private String finalizedCar;

	@Column(name = "RUNNING_LOAN")
	private String  runningLoan;

	@Column(name = "LOAN_AMOUNT")
	private Double loanAmount;
	
	
	@Column(name = "VEHICLE_PAYMENT_TYPE")
	private String vehiclePaymentType;
	
	@Column(name = "BENEFICIARY_NAME")
	private String beneficiaryName;

	@Column(name = "BENEFICIARY_BANK_NAME")
	private String  beneficiaryBankName;

	@Column(name = "BENEFICIARY_ACCOUNT_NO")
	private String beneficiaryAccountNo;
	
	@Column(name = "BENEFICIARY_AMOUNT")
	private Double beneficiaryAmount;

    @Column(name = "BENEFICIARY_IFSC_CODE")
	private String  beneficiaryIFSCCode;

    @JoinColumn(name="DEALER_EXECUTIVE_ID")
	@ManyToOne(fetch=FetchType.EAGER)
	private DealerExecutiveEntity2 dealerExecutiveId; 
    
    @Column(name = "VEHICLE_STOCK_TYPE")
   	private String  vehicleStockType;
    
	@Column(name = "VEHICLE_VALUE")
	private Double vehicleValue; 
	
	@Column(name = "OWNED_INTEREST")
	private Double ownedInterest; 
	
	@Column(name = "OWNED_REPAIR_CHARGES")
	private Double ownedRepairCharges; 
	
	@Column(name = "OWNED_SELL_MARGIN")
	private Double ownedSellMargin; 
	
	@Column(name = "OWNED_SALE_VALUE")
	private Double ownedSaleValue; 
	
	@Column(name = "INQUIRY_OPEN_DAYS")
	private Long inquiryOpenDays; 
	
	@Column(name = "TAX_VALID_UPTO")
	private Date taxValidUpto;
	
	@Column(name = "FITNESS_UPTO")
	private Date fitnessUpto;
	
	@Column(name = "PREV_REGD_AT")
	private String  prevRegAt;
	 
	@Column(name = "NOTES")
	private String  notes;
	
	@Column(name = "CHALAN")
	private String chalan;
	
	@Column(name = "MUSIC_TYPE")
	private String musicType;

}
