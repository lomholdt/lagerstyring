package com.lomholdt.lagerstyring.model;

import java.util.ArrayList;

public class LoggedSummedInventory {
	
	private String name;
	private int totalUnits;
	private double unitPrice;
	private double totalValue;
	private ArrayList<Integer> moves = new ArrayList<>();
	private int inventoryStartValue;
	private int closedAt;
	private int diff;
	
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
		this.totalUnits = (-1) * totalUnits;
	}
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
		this.totalValue = this.unitPrice * this.totalUnits; // double totalValue are not used
		
	}
	public ArrayList<Integer> getMoves(){
		return moves;
	}
	public void setMoves(ArrayList<Integer> moves){
		this.moves = moves;	
	}
	public int getInventoryStartValue(){
		return inventoryStartValue;
	}
	public void setInventoryStartValue(int inventoryStartValue){
		this.inventoryStartValue = inventoryStartValue;
	}
//	public int calculateStart(){
//		int closedAtCopy = closedAt;
//		for (Integer move : moves) {
//			if(move < 0){
//				closedAtCopy += Math.abs(move);
//			}
//			else{
//				closedAtCopy -= move;
//			}	
//		}
//		inventoryStartValue = closedAtCopy;
//		return inventoryStartValue;
//	}
	public void setClosedAt(int closedAt){
		this.closedAt = closedAt;
	}
	public int getClosedAt(){
		return closedAt;
	}
	public int getDiff() {
		return (inventoryStartValue - sumOfMoves()) - closedAt;
	}
	private int sumOfMoves(){
		int sum = 0;
		for (Integer move : moves) {
			if (move < 0){
				sum += Math.abs(move);
			}
			else{
				sum += move;
			}
		}
		return sum;
	}
}
