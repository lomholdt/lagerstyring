package com.lomholdt.lagerstyring.model;

import java.util.ArrayList;

public class LoggedStorage {
	
	Storage storage;
	ArrayList<LoggedStation> loggedStation;
	
	public Storage getStorage() {
		return storage;
	}
	public void setStorage(Storage s) {
		this.storage = s;
	}
	public ArrayList<LoggedStation> getLoggedStations() {
		return loggedStation;
	}
	public void setLoggedStation(ArrayList<LoggedStation> loggedStation) {
		this.loggedStation = loggedStation;
	}
}
