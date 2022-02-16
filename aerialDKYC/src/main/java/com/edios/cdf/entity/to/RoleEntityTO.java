package com.edios.cdf.entity.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleEntityTO implements Serializable {

	private static final long serialVersionUID = -7959006084525327336L;

	private Long roleID;

	private String roleName;

	private String roleDesc;

	private String roleStatus;

	private Long transactionCount;

}