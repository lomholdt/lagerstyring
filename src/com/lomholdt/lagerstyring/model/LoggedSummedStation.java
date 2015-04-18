package com.lomholdt.lagerstyring.model;

import java.util.ArrayList;

public class LoggedSummedStation {
	
	Station station;
	ArrayList<LoggedSummedInventory> loggedInventory = new ArrayList<>();
	
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public ArrayList<LoggedSummedInventory> getLoggedInventory() {
		return loggedInventory;
	}
	public void addToLoggedInventory(LoggedSummedInventory inventory) {
		loggedInventory.add(inventory);
	}	
}
