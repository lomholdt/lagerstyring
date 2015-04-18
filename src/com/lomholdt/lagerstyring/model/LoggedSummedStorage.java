package com.lomholdt.lagerstyring.model;

import java.util.ArrayList;

public class LoggedSummedStorage {
	
	Storage storage;
	ArrayList<LoggedSummedStation> loggedStation;
	
	public Storage getStorage() {
		return storage;
	}
	public void setStorage(Storage s) {
		this.storage = s;
	}
	public ArrayList<LoggedSummedStation> getLoggedStations() {
		return loggedStation;
	}
	public void setLoggedStation(ArrayList<LoggedSummedStation> loggedStation) {
		this.loggedStation = loggedStation;
	}
}