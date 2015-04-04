package com.lomholdt.lagerstyring.model;

import java.util.ArrayList;

public class LoggedStation {
	
	Station station;
	ArrayList<LoggedInventory> loggedInventory = new ArrayList<>();
	
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public ArrayList<LoggedInventory> getLoggedInventory() {
		return loggedInventory;
	}
	public void addToLoggedInventory(LoggedInventory inventory) {
		loggedInventory.add(inventory);
	}	
}
