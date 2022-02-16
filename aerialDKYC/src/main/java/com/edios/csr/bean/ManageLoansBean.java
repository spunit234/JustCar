package com.edios.csr.bean;

import java.util.Date;

import com.edios.cdf.bean.AbstractBean;
import com.edios.cdf.entity.common.ApplicationParameterListEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ManageLoansBean extends AbstractBean {
	private static final long serialVersionUID = 1L;

	private  ApplicationParameterListEntity loanStatus;
	private  ApplicationParameterListEntity loanBank;
	private  ApplicationParameterListEntity loanClass;
	private  ApplicationParameterListEntity loanType;
	private String loanNo;
    private StaffBean assignedStaffId;
    private String inquiryNo;
    private  ApplicationParameterListEntity inquiryType;
    private String name;
    private String panNo;
    private String aadharNo;
    private String contactNumber;
    private Date loanToDate;
    private Date loanFromDate;
    private Long accountId;
	private Long siteId;
	private String loanNumberFlag;
}


