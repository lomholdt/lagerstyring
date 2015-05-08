package com.lomholdt.lagerstyring.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Inventory {
	
	private int id;
	private String name;
	private double units;
	private Calendar createdAt;
	private Calendar updatedAt;
	private int storageId;
	private double price;
	private double salesPrice;
	private String category;
	private double movesSoFar;
	private double unitsAtOpen;
	private double tempUnits;
	private boolean tempUnitsSet;
	
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
	public double getUnits() {
		return units;
	}
	public void setUnits(double units) {
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getMovesSoFar() {
		return movesSoFar;
	}
	public void setMovesSoFar(double movesSoFar) {
		this.movesSoFar = movesSoFar;
	}
	public double getUnitsAtOpen() {
		return unitsAtOpen;
	}
	public void setUnitsAtOpen(double unitsAtOpen) {
		this.unitsAtOpen = unitsAtOpen;
	}
	public double getTempUnits() {
		return tempUnits;
	}
	public void setTempUnits(double tempUnits) {
		tempUnitsSet = true;
		this.tempUnits = tempUnits;
	}
	public boolean isTempUnitsSet() {
		return tempUnitsSet;
	}
}
