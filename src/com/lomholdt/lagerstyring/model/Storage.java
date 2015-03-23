package com.lomholdt.lagerstyring.model;

import java.util.ArrayList;

public class Storage {
	
	private int id;
	private String name;
	private int companyId;
	private boolean isOpen;
	private ArrayList<Inventory> inventory = new ArrayList<Inventory>();
	
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
}
