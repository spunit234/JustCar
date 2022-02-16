package com.edios.cdf.util;

public class MenuItem {
	private String label;
	private String icon;
	private String url;
	private boolean disabled;
	private MenuItem items;
	private Object routerLink;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public MenuItem getItems() {
		return items;
	}
	public void setItems(MenuItem items) {
		this.items = items;
	}
	public Object getRouterLink() {
		return routerLink;
	}
	public void setRouterLink(Object routerLink) {
		this.routerLink = routerLink;
	}
}
