package com.edios.cdf.entity.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuEntityTO implements Serializable {

	private static final long serialVersionUID = 9100670153171983189L;

	private Integer menuID;

	@JsonProperty("label")
	private String menuDesc;
	
	@JsonProperty("value")
	private String menuValue;

	private Double menuSequence;

	@JsonProperty("routerLink")
	private String pageUrl;

	private Integer parentMenuID;

	@JsonProperty("icon")
	private String menuIcon;

	@JsonProperty("routerLinkActiveOptions")
	private String menuActiveOption;

	@JsonProperty("items")
	private List<MenuEntityTO> items;
	
	@JsonProperty("header")
	 private String gridHeader;

	 @JsonProperty("field")
	 private String gridField = "t";

	public List<MenuEntityTO> getItems() {
		return items == null ? items = new ArrayList<>() : items;
	}

	public void setItems(List<MenuEntityTO> items) {
		this.items = items;
	}

}