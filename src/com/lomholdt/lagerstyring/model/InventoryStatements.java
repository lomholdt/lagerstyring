package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class InventoryStatements extends DBMain {
	
	public boolean addInventory(String name, int units, int storageId) throws Exception{
		Connection connection = ds.getConnection();
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
				statement.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;
	}
	
	public ArrayList<LogBook> getLogBooks(int storageId, Timestamp from, Timestamp to) throws Exception{
		ArrayList<LogBook> al = new ArrayList<LogBook>();
		Connection connection = ds.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT archive_log.storage_id, archive_log.name, archive_log.opened_at, archive_log.closed_at FROM archive_log WHERE archive_log.storage_id = ? AND archive_log.opened_at >= ? AND archive_log.closed_at <= ? ORDER BY archive_log.closed_at DESC");
			try {
				statement.setInt(1, storageId);
				statement.setTimestamp(2, from);
				statement.setTimestamp(3, to);
				rs = statement.executeQuery();
				while (rs.next()){
					if (!rs.getString("closed_at").equals("0000-00-00 00:00:00")){
						LogBook lb = new LogBook();
						lb.setName(rs.getString("name"));
						lb.setStorageId(rs.getInt("storage_id"));
						lb.setOpenedAt(rs.getTimestamp("opened_at"));
						lb.setClosedAt(rs.getTimestamp("closed_at"));
						al.add(lb);						
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return al;
	}
	
	public void openArchiveLog(String storageName, int storageId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO archive_log (name, storage_id) VALUES (?, ?)");
			try {
				// Do stuff with the statement
				statement.setString(1, storageName);
				statement.setInt(2, storageId);
				statement.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			}				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}
	
	public void closeArchiveLog(int storageId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE archive_log SET closed_at = NOW() WHERE archive_log.storage_id = ? ORDER BY archive_log.opened_at DESC LIMIT 1;");
			try {
				// Do stuff with the statement
				statement.setInt(1, storageId);
				statement.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			}				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		
	}
	
	public LoggedStation getLoggedItems(Timestamp from, Timestamp to, String inventoryName, int stationId, int storageId) throws Exception{
		System.out.println("Fired inventory specifik query");
		
		LoggedStation ls = new LoggedStation();
		ls.setStation(getStation(stationId));
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement(""
					+ "SELECT inventory_log.created_at, inventory_log.name, inventory_log.units, inventory_log.performed_action "
					+ "FROM inventory_log "
					+ "WHERE inventory_log.created_at >= ? "
					+ "AND inventory_log.created_at <= ? "
					+ "AND inventory_log.station_id = ? AND inventory_log.name = ? AND inventory_log.storage_id = ?");
			statement.setTimestamp(1, from);
			statement.setTimestamp(2, to);
			statement.setInt(3, stationId);
			statement.setString(4, inventoryName);
			statement.setInt(5, storageId);
			// TODO add the rest of the parameters
			
			try {
				rs = statement.executeQuery();
				while (rs.next()){
					LoggedInventory li = new LoggedInventory();
					li.setCreatedAt(rs.getTimestamp("created_at"));
					li.setName(rs.getString("name"));
					li.setUnits(rs.getInt("units"));
					li.setPerformedAction(rs.getString("performed_action"));
					
					ls.addToLoggedInventory(li);
				}
			}
			catch(Exception e){
				System.out.println("Error getting logged items.");
				e.printStackTrace();
			}
		}
		catch(Exception e){
			System.out.println("Could not get logged items.");
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return ls;
		
	}
	
	public LoggedStation getLoggedItems(Timestamp from, Timestamp to, int stationId, int storageId) throws Exception{
		LoggedStation ls = new LoggedStation();
		ls.setStation(getStation(stationId));
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement(""
					+ "SELECT inventory_log.created_at, inventory_log.name, inventory_log.units, inventory_log.performed_action "
					+ "FROM inventory_log "
					+ "WHERE inventory_log.created_at >= ? "
					+ "AND inventory_log.created_at <= ? "
					+ "AND inventory_log.station_id = ? AND inventory_log.storage_id = ?");
			statement.setTimestamp(1, from);
			statement.setTimestamp(2, to);
			statement.setInt(3, stationId);
			statement.setInt(4, storageId);
			
			try {
				rs = statement.executeQuery();
				while (rs.next()){
					System.out.println("Adding inventory");
					LoggedInventory li = new LoggedInventory();
					li.setCreatedAt(rs.getTimestamp("created_at"));
					li.setName(rs.getString("name"));
					li.setUnits(rs.getInt("units"));
					li.setPerformedAction(rs.getString("performed_action"));
					
					ls.addToLoggedInventory(li);
				}
			}
			catch(Exception e){
				System.out.println("Error getting logged items.");
				e.printStackTrace();
			}
		}
		catch(Exception e){
			System.out.println("Could not get logged items.");
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return ls;
		
	}
	
	public boolean addToStorageLog(String name, int storageId, String performed_action) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO storage_log (name, storage_id, performed_action) VALUES (?, ?, ?)");
			statement.setString(1, name);
			statement.setInt(2, storageId);
			statement.setString(3, performed_action);
			statement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;
		
	}
	
	public String getStorageName(int storageId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT storages.name FROM storages WHERE storages.id = ?;");
			try {
				statement.setInt(1, storageId);
				rs = statement.executeQuery();
				if (rs.next()){
					return rs.getString("name");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return null;
	}
	
	public ArrayList<Inventory> getAllInventory(int companyId) throws Exception{
		Connection connection = ds.getConnection();;
		ArrayList<Inventory> al = new ArrayList<Inventory>();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT inventory.id, inventory.name FROM inventory, storages WHERE storages.company_id = ? AND storages.id = inventory.storage_id;");
			try {
				statement.setInt(1, companyId);
				rs = statement.executeQuery();
				while (rs.next()){
					Inventory i = new Inventory();
					i.setId(rs.getInt("id"));
					i.setName(rs.getString("name"));
					al.add(i);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return al;
	}
	
	

	public boolean addToInventoryLog(String name, int units, int storageId, int stationId, String performed_action) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO inventory_log (name, units, storage_id, station_id, performed_action) VALUES (?, ?, ?, ?, ?)");
			statement.setString(1, name);
			statement.setInt(2, units);
			statement.setInt(3, storageId);
			statement.setInt(4, stationId);
			statement.setString(5, performed_action);
			statement.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;
	}
	
	public String getInventoryName(int inventoryId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT inventory.name FROM inventory WHERE inventory.id = ?;");
			try {
				statement.setInt(1, inventoryId);
				rs = statement.executeQuery();
				if (rs.next()){
					return rs.getString("name");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return null;
	}
	
	public boolean inventoryExists(String inventory, int storageId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT inventory.name FROM inventory WHERE inventory.name = ? AND inventory.storage_id = ?;");
			statement.setString(1, inventory);
			statement.setInt(2, storageId);
			rs = statement.executeQuery();
			if (rs.next()) return true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;
		
	}
	
	public void deleteInventory(int inventoryId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM inventory WHERE inventory.id = ?");
			statement.setInt(1, inventoryId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}
	
	public int getFirstStorageId(int companyId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT storages.id FROM storages WHERE storages.company_id = ? ORDER BY id ASC LIMIT 1");
			try {
				statement.setInt(1, companyId);
				rs = statement.executeQuery();
				if (rs.next()){
					return rs.getInt("id");
				}
			} catch (Exception e) {
				statement.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return 0;
	}
	
	public ArrayList<Storage> getStorages(int companyId) throws Exception{
		ArrayList<Storage> al = new ArrayList<Storage>();
		Connection connection = ds.getConnection();;
    	try {
    		statement = connection.prepareStatement("SELECT storages.id, storages.name, storages.company_id, storages.is_open, storages.updated_at FROM storages WHERE company_id = ?;");
    		statement.setInt(1, companyId);
    		rs = statement.executeQuery();
    		while(rs.next()) {
    			Storage st = new Storage();
    			st.setId(rs.getInt("id"));
    			st.setName(rs.getString("name"));
    			st.setCompanyId(rs.getInt("company_id"));
    			st.setIsOpen(rs.getBoolean("is_open"));
    			st.setOpenedAt(rs.getTimestamp("updated_at"));

    			al.add(st);
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}
    	finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
    	return al;
    }
	
	public Storage getStorage(int storageId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			statement = connection.prepareStatement("SELECT storages.id, storages.name, storages.company_id, storages.is_open, storages.updated_at FROM storages WHERE storages.id = ?;");
    		statement.setInt(1, storageId);
    		rs = statement.executeQuery();
    		if(rs.next()){
    			Storage st = new Storage();
    			st.setId(rs.getInt("id"));
    			st.setName(rs.getString("name"));
    			st.setCompanyId(rs.getInt("company_id"));
    			st.setIsOpen(rs.getBoolean("is_open"));
    			st.setOpenedAt(rs.getTimestamp("updated_at"));
    			
    			return st;
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return null;
	}
	
	public Storage getStorageWithInventory(int storageId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			Storage st = getStorage(storageId);
			statement = connection.prepareStatement("SELECT inventory.id, inventory.name, inventory.units, inventory.created_at, inventory.updated_at, inventory.storage_id FROM inventory WHERE inventory.storage_id = ?");
			statement.setInt(1, storageId);
			rs = statement.executeQuery();
			while(rs.next()){
				Inventory iv = new Inventory();
				iv.setId(rs.getInt("id"));
				iv.setName(rs.getString("name"));
				iv.setUnits(rs.getInt("units"));
				iv.setCreatedAt(rs.getTimestamp("created_at"));
				iv.setUpdatedAt(rs.getTimestamp("updated_at"));
				iv.setStorageId(rs.getInt("storage_id"));
				
				st.addToInventory(iv);
			}
			return st;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return null;
	}
	
	public boolean updateUnitsAt(int inventoryId, int newAmount) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			statement = connection.prepareStatement("UPDATE inventory SET units = ? WHERE inventory.id = ?");
			statement.setInt(1, newAmount);
			statement.setInt(2, inventoryId);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;	
	}
	
	public boolean incrementUnits(int inventoryId, int amount) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			statement = connection.prepareStatement("UPDATE inventory SET units = units + ? WHERE inventory.id = ?");
			statement.setInt(1, amount);
			statement.setInt(2, inventoryId);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;	
	}
	
	public boolean decrementUnits(int inventoryId, int amount) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			statement = connection.prepareStatement("UPDATE inventory SET units = units - ? WHERE inventory.id = ?");
			statement.setInt(1, amount);
			statement.setInt(2, inventoryId);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;	
	}
	
	public int currentInventoryUnits(int inventoryId) throws Exception{
		Connection connection = ds.getConnection();;
    	try {
    		statement = connection.prepareStatement("SELECT inventory.units FROM inventory WHERE inventory.id = ?;");
    		statement.setInt(1, inventoryId);
    		rs = statement.executeQuery();
    		if(rs.next()) {
    			return rs.getInt("units");
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	} finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
    	return 0;	
		
	}
	
	public boolean storageIsOpen(int storageId) throws Exception{
		Connection connection = ds.getConnection();;
    	try {
    		statement = connection.prepareStatement("SELECT storages.is_open FROM storages WHERE storages.id = ?;");
    		statement.setInt(1, storageId);
    		rs = statement.executeQuery();
    		if(rs.next()) {
    			return rs.getBoolean("is_open");
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	} finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
    	return false;	
	}
	
	public void changeStorageStatus(int storageId) throws Exception{
		Connection connection = ds.getConnection();;
		try {
			boolean status =  (storageIsOpen(storageId)) ? false : true;
			statement = connection.prepareStatement("UPDATE storages SET is_open = ? WHERE storages.id = ?");
			statement.setBoolean(1, status);
			statement.setInt(2, storageId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}
	
	public int getStorageId(int companyId, String storageName) throws Exception{
		Connection connection = ds.getConnection();;
    	try {
    		statement = connection.prepareStatement("SELECT storages.id FROM storages WHERE company_id = ? AND storages.name = ?;");
    		statement.setInt(1, companyId);
    		statement.setString(2, storageName);
    		rs = statement.executeQuery();
    		if(rs.next()) {
    			return rs.getInt("id");
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
    	return 0;	
	}
	
	public ArrayList<Station> getStations(int companyId, String importance) throws Exception{
		ArrayList<Station> al = new ArrayList<Station>();
		Connection connection = ds.getConnection();;
    	try {
    		statement = connection.prepareStatement("SELECT stations.id ,stations.name FROM stations WHERE stations.company_id = ? AND stations.importance = ?;");
    		statement.setInt(1, companyId);
    		statement.setString(2, importance);
    		rs = statement.executeQuery();
    		while(rs.next()) {
    			Station s = new Station();
    			s.setId(rs.getInt("id"));
    			s.setName(rs.getString("name"));
    			al.add(s);
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	} finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
    	return al;
	}
	
	public Station getStation(int stationId) throws Exception{
		Connection connection = ds.getConnection();;
    	try {
    		statement = connection.prepareStatement("SELECT stations.id ,stations.name FROM stations WHERE stations.id = ?;");
    		statement.setInt(1, stationId);
    		rs = statement.executeQuery();
    		if(rs.next()) {
    			Station s = new Station();
    			s.setId(rs.getInt("id"));
    			s.setName(rs.getString("name"));
    			return s;
    		}
    	}
    	catch(Exception e1) {
    		e1.printStackTrace();
    	}finally {
            try { if(null!=rs)rs.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
    	return null;
	}
}
