package com.edios.csr.entity.to;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExportDetailsTo {

private String loanNo;
	
	private String bankName;
	
	private String customerName;
	
	private String customerName1;
	
	private String makeandmodel;
	
	private String firstName;
	
	private String lastName;
	
	private String makeName;
	 
	private String modelName;
	
	private List<PartnerTo> partnerList = new ArrayList<PartnerTo>();
	
	private String partnerName;
	
	private Date partnershipDate;
	
	private String registrationNo;
	
	private Integer rtoCharges;
	
	private String authorisation1subPath;
	
	private String city;
	
	private String address;
	
	private String district;
	
	private String postalCode;
	
	private Double loanDuration;
	
	private Double benAmount;
	
	private String accountNo;
	 
	private String ifsc;
	
	private String loanAmountinWords;
	

	
	private String engineNo;
	
	private String chassisNo;
	
	private String contactNo;
	
	private String fatherName;
	
	private String panNo;
	
	private String adharNo;
	
	private String refNo;
	
	private String vendorCode;
	
	private Double amount;
	
	private String location;
	
	private Date manufacturingYear;
	
	private String accountCode;
	
	private String bankManager;
	
	private Double installment;
	
	private BigInteger emiDay;
	
	private String emiMonth;
	
	private BigInteger emiYear;
	
	private Double insurence;
	
	private String insurencei;
	
	private String rtoChargesi;
	
	
	
	
	

	@Override
	public String toString() {
		return "ExportDetailsTo [loanNo=" + loanNo + ", bankName=" + bankName + ", customerName=" + customerName
				+ ", makeandmodel=" + makeandmodel + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", makeName=" + makeName + ", modelName=" + modelName + ", registrationNo=" + registrationNo
				+ ", rtoCharges=" + rtoCharges + ", refNo=" + refNo + ", vendorCode=" + vendorCode + ", amount="
				+ amount + ", location=" + location + ", manufacturingYear=" + manufacturingYear
				+ ", accountCode=" + accountCode + ", bankManager=" + bankManager + "]";
	}

}	
	
	