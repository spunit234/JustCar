package com.edios.csr.entity.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DealerTo2 implements Serializable {
	private static final long serialVersionUID = 6342094965674122373L;
	private Long dealerId;
	private String dealerName;

}
