package com.lomholdt.lagerstyring.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class LoggedInventory {
	
	private Calendar createdAt;
	private String name;
	private double units;
	private String performedAction;
	private double price;
	private double salesPrice;
	private String performedBy;
	
	public Calendar getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp timestamp) {
		this.createdAt = Calendar.getInstance();
		this.createdAt.setTime(new Date(timestamp.getTime()));
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getUnits() {
		return units;
	}
	public void setUnits(double units) {
		this.units = units;
	}
	public String getPerformedAction() {
		return performedAction;
	}
	public void setPerformedAction(String performedAction) {
		this.performedAction = performedAction;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public String getPerformedBy() {
		return performedBy;
	}
	public void setPerformedBy(String performedBy) {
		this.performedBy = performedBy;
	}
}
