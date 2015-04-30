package com.lomholdt.lagerstyring.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class LogBook {
	
	int storageId;
	String name;
	Timestamp openedAt;
	Timestamp closedAt;
	
	public int getStorageId() {
		return storageId;
	}
	public void setStorageId(int storageId) {
		this.storageId = storageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getOpenedAt() {
		return openedAt;
	}
	public void setOpenedAt(Timestamp openedAt) {
		this.openedAt = openedAt;
	}
	public Timestamp getClosedAt() {
		return closedAt;
	}
	public void setClosedAt(Timestamp closedAt) {
		this.closedAt = closedAt;
	}
	public String getOpenedAtHtml(){
		return formatTimestamp(getOpenedAt());
	}
	public String getClosedAtHtml(){
		return formatTimestamp(getClosedAt());
	}
	private String formatTimestamp(Timestamp t){
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(t.getTime());
	}
	

}
