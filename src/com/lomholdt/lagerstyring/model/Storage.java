package com.lomholdt.lagerstyring.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Storage {
	
	private int id;
	private String name;
	private int companyId;
	private boolean isOpen;
	private ArrayList<Inventory> inventory = new ArrayList<Inventory>();
	private Calendar openedAt;
	private Timestamp createdAt;
	
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
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public ArrayList<Inventory> getInventory() {
		return inventory;
	}
	public void addToInventory(Inventory inv) {
		inventory.add(inv);
	}
	public Calendar getOpenedAt() {
		return openedAt;
	}
	public String getOpenedAtHtml(){
		String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(getOpenedAt().getTimeInMillis());
		return time;
	}
	public String getOpenedAtDatePicker(){
		String time = new SimpleDateFormat("yyyy-MM-dd").format(getOpenedAt().getTimeInMillis());
		return time;
	}
	
	public void setOpenedAt(Timestamp updatedAt) {
		openedAt = Calendar.getInstance();
		openedAt.setTime(new Date(updatedAt.getTime()));
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
