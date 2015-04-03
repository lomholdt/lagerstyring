package com.lomholdt.lagerstyring.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class LoggedInventory {
	
	private Calendar createdAt;
	private String name;
	private int units;
	private String performedAction;
	
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
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public String getPerformedAction() {
		return performedAction;
	}
	public void setPerformedAction(String performedAction) {
		this.performedAction = performedAction;
	}
	
	

}
