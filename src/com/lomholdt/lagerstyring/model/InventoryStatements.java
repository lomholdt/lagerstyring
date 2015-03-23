package com.lomholdt.lagerstyring.model;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryStatements extends DBMain {
	
	public boolean addInventory(String name, int units, int storageId){
		try {
			PreparedStatement pstmt = c.preparedStatement("INSERT INTO inventory (name, units, storage_id) VALUES (?, ?, ?)");
			pstmt.setString(1, name);
			pstmt.setInt(2, units);
			pstmt.setInt(3, storageId);
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
		
}
