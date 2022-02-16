package com.edios.csr.bean;

import com.edios.cdf.bean.AbstractBean;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReportsBean extends AbstractBean {
	
	private static final long serialVersionUID = 1L;
	
}


class InquiriesPendingTasksBean extends ReportsBean {
	
	private static final long serialVersionUID = 1L;
	
}
