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
	private Timestamp openedAt;
	private Timestamp createdAt;
	private int inventoryCount;
	
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
	public Timestamp getOpenedAt() {
		return openedAt;
	}
	public String getOpenedAtHtml(){
		String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(getOpenedAt().getTime());
		return time;
	}
	public String getOpenedAtDatePicker(){
		String time = new SimpleDateFormat("yyyy-MM-dd").format(getOpenedAt().getTime());
		return time;
	}
	
	public void setOpenedAt(Timestamp updatedAt) {
		openedAt = updatedAt;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public int getInventoryCount() {
		return inventoryCount;
	}
	public void setInventoryCount(int inventoryCount) {
		this.inventoryCount = inventoryCount;
	}
}
