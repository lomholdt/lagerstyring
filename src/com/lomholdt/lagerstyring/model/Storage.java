package com.lomholdt.lagerstyring.model;

import java.sql.Timestamp;
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
		String year = Integer.toString(openedAt.get(Calendar.YEAR));
		
		int monthCustom = openedAt.get(Calendar.MONTH)+1;
		String month = monthCustom < 10 ? "0" + Integer.toString(monthCustom) : Integer.toString(monthCustom); 
		
		int dayCustom = openedAt.get(Calendar.DAY_OF_MONTH);
		String day = dayCustom < 10 ? "0" + Integer.toString(dayCustom) : Integer.toString(dayCustom); 
		return String.format("%s-%s-%s", year, month, day);
	}
	
	public void setOpenedAt(Timestamp updatedAt) {
		openedAt = Calendar.getInstance();
		openedAt.setTime(new Date(updatedAt.getTime()));
	}
}
