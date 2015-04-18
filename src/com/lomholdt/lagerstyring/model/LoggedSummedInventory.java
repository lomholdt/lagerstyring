package com.lomholdt.lagerstyring.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LoggedSummedInventory {
	
	private String name;
	private int totalUnits;
	private double unitPrice;
	private double totalValue;
	private ArrayList<Integer> moves = new ArrayList<>();
	
//	public Calendar getCreatedAt() {
//		return createdAt;
//	}
//	public void setCreatedAt(Timestamp timestamp) {
//		this.createdAt = Calendar.getInstance();
//		this.createdAt.setTime(new Date(timestamp.getTime()));
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalUnits() {
		return totalUnits;
	}
	public void setTotalUnits(int totalUnits) {
		this.totalUnits = totalUnits;
	}
//	public String getPerformedAction() {
//		return performedAction;
//	}
//	public void setPerformedAction(String performedAction) {
//		this.performedAction = performedAction;
//	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	public ArrayList<Integer> getMoves(){
		return moves;
	}
	public void setMoves(ArrayList<Integer> moves){
		this.moves = moves;	
	}
	
}
