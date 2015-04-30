package com.lomholdt.lagerstyring.model;

import java.util.ArrayList;

public class LoggedSummedInventory {
	
	private static final int ROUND_TO = 2;
	
	private String name;
	private double totalUnits;
	private double unitPrice;
	private double unitSalesPrice;
	private double totalValue;
	private double totalSalesValue;
	private ArrayList<Double> moves = new ArrayList<>();
	private double inventoryStartValue;
	private double closedAt;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTotalUnits() {
		return totalUnits;
	}
	public void setTotalUnits(double totalUnits) {
		this.totalUnits = (-1) * totalUnits;
	}
	public double getTotalUnitsWithDiff(){
		double diff = (moves.size() == 0) ? 0 : getDiff();
		double result = totalUnits + ((-1) * diff);
		return RoundDouble.round(result, ROUND_TO);
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
	public double getTotalValueWithDiff(){
		double result = this.unitPrice * getTotalUnitsWithDiff();
		return RoundDouble.round(result, ROUND_TO);
	}
	public void setTotalValue(double totalValue) {
		this.totalValue = this.unitPrice * this.totalUnits; // double totalValue are not used
	}
	public ArrayList<Double> getMoves(){
		return moves;
	}
	public void setMoves(ArrayList<Double> moves){
		this.moves = moves;	
	}
	public double getInventoryStartValue(){
		return inventoryStartValue;
	}
	public void setInventoryStartValue(double inventoryStartValue){
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
	public void setClosedAt(double closedAt){
		this.closedAt = closedAt;
	}
	public double getClosedAt(){
		return closedAt;
	}
	public double getDiff() {
		double result = closedAt - (inventoryStartValue - sumOfMoves());
		return RoundDouble.round(result, ROUND_TO);
	}
	private int sumOfMoves(){
		int sum = 0;
		for (Double move : moves) {
			if (move < 0){ sum += Math.abs(move); }
			else{ sum -= move; }
		}
		return sum;
	}
	public double getUnitSalesPrice() {
		return unitSalesPrice;
	}
	public void setUnitSalesPrice(double unitSalesPrice) {
		this.unitSalesPrice = unitSalesPrice;
	}
	public double getTotalSalesValue() {
		return totalSalesValue;
	}
	public void setTotalSalesValue(double totalSalesValue) {
		this.totalSalesValue = this.unitSalesPrice * this.totalUnits;
	}
	public double getTotalSalesValueWithDiff(){
		double result = this.unitSalesPrice * getTotalUnitsWithDiff();
		return RoundDouble.round(result, ROUND_TO);
	}
}
