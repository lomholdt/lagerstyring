package com.lomholdt.lagerstyring.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Inventory {
	
	private int id;
	private String name;
	private int units;
	private Calendar createdAt;
	private Calendar updatedAt;
	private int storageId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Calendar getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = Calendar.getInstance();
		this.createdAt.setTime(new Date(createdAt.getTime()));
	}
	public Calendar getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = Calendar.getInstance();
		this.updatedAt.setTime(new Date(updatedAt.getTime()));
	}
	public int getStorageId() {
		return storageId;
	}
	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}

}
