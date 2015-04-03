package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class InventoryStatements extends DBMain {
	
//	public boolean addInventory(String name, int units, int storageId){
//		try {
//			PreparedStatement pstmt = c.preparedStatement("INSERT INTO inventory (name, units, storage_id) VALUES (?, ?, ?)");
//			pstmt.setString(1, name);
//			pstmt.setInt(2, units);
//			pstmt.setInt(3, storageId);
//			pstmt.executeUpdate();
//			
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
	
	
	
	public boolean addInventory(String name, int units, int storageId) throws Exception{
		Connection connection = c.getCon();
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO inventory (name, units, storage_id) VALUES (?, ?, ?)");
			try {
				// Do stuff with the statement
				statement.setString(1, name);
				statement.setInt(2, units);
				statement.setInt(3, storageId);
				statement.executeUpdate();
				return true;
			} finally {
				System.out.println("Closing statement");
				statement.close();
			}
		} finally {
			System.out.println("Closing connection");
//			connection.close();
		}
	}
	
	
	
	
	
	
	
	
	
	
	public boolean addToLog(String name, int units, int storageId, int stationId){
		try {
			PreparedStatement pstmt = c.preparedStatement("INSERT INTO log (name, units, storage_id, station_id) VALUES (?, ?, ?, ?)");
			pstmt.setString(1, name);
			pstmt.setInt(2, units);
			pstmt.setInt(3, storageId);
			pstmt.setInt(4, stationId);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean inventoryExists(String inventory, int storageId){
		try {
			PreparedStatement pstmt = c.preparedStatement("SELECT inventory.name FROM inventory WHERE inventory.name = ? AND inventory.storage_id = ?;");
			pstmt.setString(1, inventory);
			pstmt.setInt(2, storageId);
			rs = pstmt.executeQuery();
			if (rs.next()) return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public ArrayList<Storage> getStorages(int companyId){
		ArrayList<Storage> al = new ArrayList<Storage>();
    	try {
    		pstmt = c.preparedStatement("SELECT storages.id, storages.name, storages.company_id, storages.is_open FROM storages WHERE company_id = ?;");
    		pstmt.setInt(1, companyId);
    		rs = pstmt.executeQuery();
    		while(rs.next()) {
    			Storage st = new Storage();
    			st.setId(rs.getInt("id"));
    			st.setName(rs.getString("name"));
    			st.setCompanyId(rs.getInt("company_id"));
    			st.setIsOpen(rs.getBoolean("is_open"));

    			al.add(st);
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}
    	return al;
    }
	
	public Storage getStorage(int storageId){
		try {
			pstmt = c.preparedStatement("SELECT storages.id, storages.name, storages.company_id, storages.is_open FROM storages WHERE storages.id = ?;");
    		pstmt.setInt(1, storageId);
    		rs = pstmt.executeQuery();
    		if(rs.next()){
    			Storage st = new Storage();
    			st.setId(rs.getInt("id"));
    			st.setName(rs.getString("name"));
    			st.setCompanyId(rs.getInt("company_id"));
    			st.setIsOpen(rs.getBoolean("is_open"));

    			return st;
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Storage getStorageWithInventory(int storageId){
		try {
			Storage st = getStorage(storageId);
			pstmt = c.preparedStatement("SELECT inventory.id, inventory.name, inventory.units, inventory.created_at, inventory.updated_at, inventory.storage_id FROM inventory WHERE inventory.storage_id = ?");
			pstmt.setInt(1, storageId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Inventory iv = new Inventory();
				iv.setId(rs.getInt("id"));
				iv.setName(rs.getString("name"));
				iv.setUnits(rs.getInt("units"));
				
				// Add date later
				
				iv.setStorage_id(rs.getInt("storage_id"));				
				st.addToInventory(iv);
			}
			return st;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateUnitsAt(int inventoryId, int newAmount){
		try {
			pstmt = c.preparedStatement("UPDATE inventory SET units = ? WHERE inventory.id = ?");
			pstmt.setInt(1, newAmount);
			pstmt.setInt(2, inventoryId);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;	
	}
	
	public boolean incrementUnits(int inventoryId, int amount){
		try {
			pstmt = c.preparedStatement("UPDATE inventory SET units = units + ? WHERE inventory.id = ?");
			pstmt.setInt(1, amount);
			pstmt.setInt(2, inventoryId);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	public boolean decrementUnits(int inventoryId, int amount){
		try {
			pstmt = c.preparedStatement("UPDATE inventory SET units = units - ? WHERE inventory.id = ?");
			pstmt.setInt(1, amount);
			pstmt.setInt(2, inventoryId);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	public int currentInventoryUnits(int inventoryId){
    	try {
    		pstmt = c.preparedStatement("SELECT inventory.units FROM inventory WHERE inventory.id = ?;");
    		pstmt.setInt(1, inventoryId);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			return rs.getInt("units");
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}
    	return 0;	
		
	}
	
	public boolean storageIsOpen(int storageId){
    	try {
    		pstmt = c.preparedStatement("SELECT storages.is_open FROM storages WHERE storages.id = ?;");
    		pstmt.setInt(1, storageId);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			return rs.getBoolean("is_open");
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}
    	return false;	
	}
	
	public void changeStorageStatus(int storageId){
		try {
			boolean status =  (storageIsOpen(storageId)) ? false : true;
			pstmt = c.preparedStatement("UPDATE storages SET is_open = ? WHERE storages.id = ?");
			pstmt.setBoolean(1, status);
			pstmt.setInt(2, storageId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getStorageId(int companyId, String storageName){
    	try {
    		pstmt = c.preparedStatement("SELECT storages.id FROM storages WHERE company_id = ? AND storages.name = ?;");
    		pstmt.setInt(1, companyId);
    		pstmt.setString(2, storageName);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			return rs.getInt("id");
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}
    	return 0;	
	}
	
	public ArrayList<Station> getStations(int companyId){
		ArrayList<Station> al = new ArrayList<Station>();
    	try {
    		pstmt = c.preparedStatement("SELECT stations.id ,stations.name FROM stations WHERE stations.company_id = ?;");
    		pstmt.setInt(1, companyId);
    		rs = pstmt.executeQuery();
    		while(rs.next()) {
    			Station s = new Station();
    			s.setId(rs.getInt("id"));
    			s.setName(rs.getString("name"));
    			al.add(s);
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}
    	return al;
	}
	
	public Station getStation(int stationId){
    	try {
    		pstmt = c.preparedStatement("SELECT stations.id ,stations.name FROM stations WHERE stations.id = ?;");
    		pstmt.setInt(1, stationId);
    		rs = pstmt.executeQuery();
    		if(rs.next()) {
    			Station s = new Station();
    			s.setId(rs.getInt("id"));
    			s.setName(rs.getString("name"));
    			return s;
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}
    	return null;
	}
}
