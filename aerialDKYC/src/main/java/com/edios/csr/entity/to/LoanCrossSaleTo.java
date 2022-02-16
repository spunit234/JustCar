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
public class LoanCrossSaleTo implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private Long loanCrossSaleId;
	private Double itemCharges;
	private String userName;
	private String crossSaleItem;
	private String createdDate;
	private Date loanCSellDate;
	private Long itemId;
	private Long transactionCount;
}
