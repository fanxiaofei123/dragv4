package org.com.drag.model;

import java.util.Date;

public class Vegetable {
	
	private String name;
	private String market;
	private String value;
	private String empty;
	private Date time;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String price) {
		this.value = price;
	}
	public String getEmpty() {
		return empty;
	}
	public void setEmpty(String empty) {
		this.empty = empty;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

}
