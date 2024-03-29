package com.lomholdt.lagerstyring.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class InventoryStatements extends DBMain {
	
	public boolean addInventory(String name, double units, int storageId, double price, double salesPrice) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("INSERT INTO inventory (name, units, storage_id, price, sales_price) VALUES (?, ?, ?, ?, ?);");
			try {
				// Do stuff with the statement
				statement.setString(1, name);
				statement.setDouble(2, units);
				statement.setInt(3, storageId);
				statement.setDouble(4, price);
				statement.setDouble(5, salesPrice);
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
	
	public boolean addCategoryToInventory(int inventoryId, int categoryId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("INSERT INTO inventory_categories (inventory_id, category_id) VALUES (?, ?);");
			try {
				// Do stuff with the statement
				statement.setInt(1, inventoryId);
				statement.setDouble(2, categoryId);
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
	
	public double getInventoryPrice(int inventoryId) throws SQLException{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory.price FROM inventory WHERE inventory.id = ?");
			try {
				statement.setInt(1, inventoryId);
				rs = statement.executeQuery();
				if (rs.next()){
					return rs.getDouble("price");						
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
		return 0.0;
	}
	
	public int getInventoryId(String inventoryName, int storageId) throws SQLException{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory.id "
					+ "FROM inventory "
					+ "JOIN storages ON inventory.storage_id = storages.id "
					+ "WHERE inventory.name = ? "
					+ "AND storages.id = ?");
			try {
				statement.setString(1, inventoryName);
				statement.setInt(2, storageId);
				rs = statement.executeQuery();
				if (rs.next()){
					return rs.getInt("id");						
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
		return 0;
	}
	
	public double getInventorySalesPrice(int inventoryId) throws SQLException{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory.sales_price FROM inventory WHERE inventory.id = ?");
			try {
				statement.setInt(1, inventoryId);
				rs = statement.executeQuery();
				if (rs.next()){
					return rs.getDouble("sales_price");						
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
		return 0.0;
	}
	
	public ArrayList<LogBook> getLogBooks(int storageId, Timestamp from, Timestamp to) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<LogBook> al = new ArrayList<LogBook>();
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT archive_log.storage_id, archive_log.name, archive_log.opened_at, archive_log.closed_at FROM archive_log WHERE archive_log.storage_id = ? AND archive_log.opened_at >= ? AND archive_log.closed_at <= ? ORDER BY archive_log.closed_at DESC");
			try {
				statement.setInt(1, storageId);
				statement.setTimestamp(2, from);
				statement.setTimestamp(3, to);
				rs = statement.executeQuery();
				while (rs.next()){
					if (!rs.getString("closed_at").equals(rs.getString("opened_at"))){
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();;
		try {
			statement = connection.prepareStatement("INSERT INTO archive_log (name, storage_id) VALUES (?, ?)");
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("UPDATE archive_log SET closed_at = NOW() WHERE archive_log.storage_id = ? ORDER BY archive_log.opened_at DESC LIMIT 1;"
					+ "");
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
	
	public int getLatestArchiveLogId(int storageId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT DISTINCT MAX(archive_log.id) AS id FROM archive_log WHERE archive_log.storage_id = ?");
			try {
				// Do stuff with the statement
				statement.setInt(1, storageId);
				rs = statement.executeQuery();
				if(rs.next()){
					return rs.getInt("id");
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
		return 0;
	}
	
	public Double getCurrentInventoryCount(int inventoryId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory.units FROM inventory WHERE inventory.id = ?");
			try {
				// Do stuff with the statement
				statement.setInt(1, inventoryId);
				rs = statement.executeQuery();
				if(rs.next()){
					return rs.getDouble("units");
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
		return 0.0;
	}
	
		
	public void setInventoryAtClose(int storageId, int archiveLogId, Map<Integer, Double> values) throws Exception{
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("UPDATE inventory_snapshot SET units_at_close = (SELECT inventory.units FROM inventory WHERE inventory.id = ?) WHERE inventory_snapshot.archive_log_id = ? AND inventory_snapshot.inventory_id = ?");
			try {
				
				
				for (Entry<Integer, Double> entry : values.entrySet()) {
					statement.setInt(1, entry.getKey());
					statement.setInt(2, archiveLogId);
					statement.setInt(3, entry.getKey());
					statement.addBatch();
				}
				statement.executeBatch();
			} catch(Exception e) {
				e.printStackTrace();
			}				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}
	
	public void setInventoryAtOpen(int storageId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("INSERT INTO inventory_snapshot (inventory_id, name, units_at_open, archive_log_id, price, sales_price) SELECT inventory.id, inventory.name, inventory.units, (SELECT DISTINCT MAX(archive_log.id) AS id FROM archive_log WHERE archive_log.storage_id = ?), inventory.price, inventory.sales_price FROM inventory WHERE storage_id = ?");
			try {
				// Do stuff with the statement
				statement.setInt(1, storageId);
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
	
	public LoggedStation getLoggedItems(Timestamp from, Timestamp to, String inventoryName, int stationId, int storageId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		LoggedStation ls = new LoggedStation();
		ls.setStation(getStation(stationId));
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement(""
					+ "SELECT inventory_log.created_at, inventory_log.name, inventory_log.units, inventory_log.price, inventory_log.performed_action, inventory_log.performed_by "
					+ "FROM inventory_log "
					+ "WHERE inventory_log.created_at >= ? "
					+ "AND inventory_log.created_at <= ? "
					+ "AND inventory_log.station_id = ? AND inventory_log.name = ? AND inventory_log.storage_id = ?");
			statement.setTimestamp(1, from);
			statement.setTimestamp(2, to);
			statement.setInt(3, stationId);
			statement.setString(4, inventoryName);
			statement.setInt(5, storageId);
			
			try {
				rs = statement.executeQuery();
				while (rs.next()){
					LoggedInventory li = new LoggedInventory();
					li.setCreatedAt(rs.getTimestamp("created_at"));
					li.setName(rs.getString("name"));
					li.setUnits(rs.getDouble("units"));
					li.setPerformedAction(rs.getString("performed_action"));
					li.setPrice(rs.getDouble("price"));
					li.setPerformedBy(rs.getString("performed_by"));
					
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		LoggedStation ls = new LoggedStation();
		ls.setStation(getStation(stationId));
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement(""
					+ "SELECT inventory_log.created_at, inventory_log.name, inventory_log.units, inventory_log.price, inventory_log.performed_action, inventory_log.performed_by "
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
					LoggedInventory li = new LoggedInventory();
					li.setCreatedAt(rs.getTimestamp("created_at"));
					li.setName(rs.getString("name"));
					li.setUnits(rs.getDouble("units"));
					li.setPerformedAction(rs.getString("performed_action"));
					li.setPrice(rs.getDouble("price"));
					li.setPerformedBy(rs.getString("performed_by"));
					
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
	
	public Map<String, Double> getCloseCountAt(Timestamp to, int storageId) throws SQLException{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		Map<String, Double> closeMap = new HashMap<>();
		try {
			statement = connection.prepareStatement("SELECT inventory_snapshot.name, inventory_snapshot.units_at_close FROM inventory_snapshot, archive_log WHERE archive_log.id = inventory_snapshot.archive_log_id AND archive_log.closed_at = ? AND archive_log.storage_id = ?;");
			try {
				// Do stuff with the statement
				statement.setTimestamp(1, to);
				statement.setInt(2, storageId);
				rs = statement.executeQuery();
				while(rs.next()){
					closeMap.put(rs.getString("name"), rs.getDouble("units_at_close"));
				}
				
				return closeMap;
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
		return closeMap;
	}
	
	public Map<String, Double> getOpenCountAt(Timestamp from, int storageId) throws SQLException{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		Map<String, Double> closeMap = new HashMap<>();
		try {
			statement = connection.prepareStatement("SELECT inventory_snapshot.name, inventory_snapshot.units_at_open FROM inventory_snapshot, archive_log WHERE archive_log.id = inventory_snapshot.archive_log_id AND archive_log.closed_at = ? AND archive_log.storage_id = ?;");
			try {
				// Do stuff with the statement
				statement.setTimestamp(1, from);
				statement.setInt(2, storageId);
				rs = statement.executeQuery();
				while(rs.next()){
					closeMap.put(rs.getString("name"), rs.getDouble("units_at_open"));
				}
				return closeMap;
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
		return closeMap;
	}
	
	public LoggedSummedStation getLoggedStation(Timestamp from, Timestamp to, int stationId, int storageId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> closeMap = getCloseCountAt(to, storageId);
		Map<String, Double> openMap = getOpenCountAt(to, storageId);
		LoggedSummedStation ls = new LoggedSummedStation();
		ls.setStation(getStation(stationId));
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory_log.name, (SUM(inventory_log.units)) AS total_out_units, inventory_log.price AS unit_price, inventory_log.sales_price AS unit_sales_price, ABS(SUM(inventory_log.price * inventory_log.units)) AS total_out_value "
					+ "FROM inventory_log "
					+ "WHERE inventory_log.created_at >= ?"
					+ "AND inventory_log.created_at <= ?"
					+ "AND inventory_log.storage_id = ? "
					+ "AND inventory_log.station_id = ? GROUP BY name, price, sales_price;");
			statement.setTimestamp(1, from);
			statement.setTimestamp(2, to);
			statement.setInt(3, storageId);
			statement.setInt(4, stationId);
			
			try {
				rs = statement.executeQuery();
				while (rs.next()){
					LoggedSummedInventory li = new LoggedSummedInventory();
					li.setName(rs.getString("name"));
					li.setTotalUnits(rs.getDouble("total_out_units"));
					li.setUnitPrice(rs.getDouble("unit_price"));
					li.setUnitSalesPrice(rs.getDouble("unit_sales_price"));
					li.setTotalValue(rs.getDouble("total_out_value"));
					li.setClosedAt(closeMap.get(rs.getString("name")));
					li.setInventoryStartValue(openMap.get(rs.getString("name")));
					
					ls.addToLoggedInventory(li);
				}
			}
			catch(Exception e){
				System.out.println("Error getting LoggedSumStation.");
				e.printStackTrace();
			}
		}
		catch(Exception e){
			System.out.println("Could not get LoggedSumStation.");
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
	
	public ArrayList<LoggedSummedInventory> getSummedLogResults(Timestamp from, Timestamp to, int storageId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> closeMap = getCloseCountAt(to, storageId);
		Map<String, Double> openMap = getOpenCountAt(to, storageId);
		ArrayList<LoggedSummedInventory> ls = new ArrayList<>();
		Connection connection = ds.getConnection();
		try {
			
			statement = connection.prepareStatement("SELECT inventory_log.name, (SUM(inventory_log.units)) AS total_out_units, inventory_log.price AS unit_price, inventory_log.sales_price AS unit_sales_price, ABS(SUM(inventory_log.price * inventory_log.units)) AS total_out_value, ABS(SUM(inventory_log.sales_price * inventory_log.units)) AS total_out_sales_value "
					+ "FROM inventory_log "
					+ "WHERE inventory_log.created_at >= ?"
					+ "AND inventory_log.created_at <= ?"
					+ "AND inventory_log.storage_id = ? GROUP BY name, price, sales_price;");
			statement.setTimestamp(1, from);
			statement.setTimestamp(2, to);
			statement.setInt(3, storageId);
			
			try {
				rs = statement.executeQuery();
				while (rs.next()){
					LoggedSummedInventory li = new LoggedSummedInventory();
					li.setName(rs.getString("name"));
					li.setTotalUnits(rs.getDouble("total_out_units"));
					
					li.setUnitPrice(rs.getDouble("unit_price"));
					li.setUnitSalesPrice(rs.getDouble("unit_sales_price"));
					li.setTotalValue(rs.getDouble("total_out_value"));
					li.setTotalSalesValue(rs.getDouble("total_out_sales_value"));
					li.setClosedAt(closeMap.get(rs.getString("name")));
					li.setInventoryStartValue(openMap.get(rs.getString("name")));
					
					ls.add(li);
				}
			}
			catch(Exception e){
				System.out.println("Error getting getSummedLogResults.");
				e.printStackTrace();
			}
		}
		catch(Exception e){
			System.out.println("Could not get getSummedLogResults.");
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
	
	public ArrayList<LoggedSummedInventory> getAllSummedLogResults(Timestamp from, Timestamp to, int storageId) throws Exception{
		ArrayList<LoggedSummedInventory> summedLogResults = getSummedLogResults(from, to, storageId);
		PreparedStatement statement = null;
		ResultSet rs = null;
		//Map<String, Integer> closeMap = getCloseCountAt(to, storageId);
		//Map<String, Integer> openMap = getOpenCountAt(to, storageId);
		//ArrayList<LoggedSummedInventory> ls = new ArrayList<>();
		Connection connection = ds.getConnection();
		try {
			String query = "SELECT inventory_snapshot.name, inventory_snapshot.units_at_open, inventory_snapshot.units_at_close, inventory_snapshot.price, inventory_snapshot.sales_price, ((-1) * (inventory_snapshot.units_at_open - inventory_snapshot.units_at_close)) AS total_out_units "
					+ "FROM inventory_snapshot "
					+ "INNER JOIN archive_log "
					+ "ON inventory_snapshot.archive_log_id = archive_log.id "
					+ "WHERE archive_log.opened_at >= ? "
					+ "AND archive_log.closed_at <= ? "
					+ "AND archive_log.storage_id = ? ";
			
			
			for (LoggedSummedInventory loggedSummedInventory : summedLogResults) {
				query += "AND inventory_snapshot.name != ? ";
			}
			query += ";";
			statement = connection.prepareStatement(query);
			int indexCount = 4;
			for (LoggedSummedInventory loggedSummedInventory : summedLogResults) {
				statement.setString(indexCount++, loggedSummedInventory.getName());
			}

			statement.setTimestamp(1, from);
			statement.setTimestamp(2, to);
			statement.setInt(3, storageId);
			
			try {
				rs = statement.executeQuery();
				while (rs.next()){
					LoggedSummedInventory li = new LoggedSummedInventory();
					li.setName(rs.getString("name"));
					li.setTotalUnits(rs.getDouble("total_out_units"));
					li.setUnitPrice(rs.getDouble("price"));
					li.setUnitSalesPrice(rs.getDouble("sales_price"));
					li.setTotalValue(new Double(Math.abs(rs.getDouble("total_out_units") * rs.getDouble("price"))));
					li.setTotalSalesValue(new Double(Math.abs(rs.getDouble("total_out_units") * rs.getDouble("sales_price"))));
					li.setClosedAt(rs.getDouble("units_at_close"));
					li.setInventoryStartValue(rs.getDouble("units_at_open"));
					
					summedLogResults.add(li);
				}
			}
			catch(Exception e){
				System.out.println("Error getting getSummedLogResults.");
				e.printStackTrace();
			}
		}
		catch(Exception e){
			System.out.println("Could not get getSummedLogResults.");
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
		return summedLogResults;
	}
	
	public LoggedSummedStation getLoggedStation(Timestamp from, Timestamp to, int stationId, int storageId, String inventoryName) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		LoggedSummedStation ls = new LoggedSummedStation();
		ls.setStation(getStation(stationId));
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory_log.name, (SUM(inventory_log.units)) AS total_out_units, inventory_log.price AS unit_price, ABS(SUM(inventory_log.price * inventory_log.units)) AS total_out_value "
					+ "FROM inventory_log "
					+ "WHERE inventory_log.crated_at >= ?"
					+ "AND inventory_log.created_at <= ?"
					+ "AND inventory_log.storage_id = ? "
					+ "AND inventory_log.station_id = ? "
					+ "AND inventory_log.name = ? GROUP BY name, unit_price;");
			statement.setTimestamp(1, from);
			statement.setTimestamp(2, to);
			statement.setInt(3, storageId);
			statement.setInt(4, stationId);
			statement.setString(5, inventoryName);
			
			try {
				rs = statement.executeQuery();
				while (rs.next()){
					LoggedSummedInventory li = new LoggedSummedInventory();
					li.setName(rs.getString("name"));
					li.setTotalUnits(rs.getDouble("total_out_units"));
					li.setUnitPrice(rs.getDouble("unit_price"));
					li.setTotalValue(rs.getDouble("total_out_value"));
					li.setMoves(getListOfMoves(from, to, stationId, storageId, rs.getString("name")));
					
					ls.addToLoggedInventory(li);
				}
			}
			catch(Exception e){
				System.out.println("Error getting LoggedSumStation.");
				e.printStackTrace();
			}
		}
		catch(Exception e){
			System.out.println("Could not get LoggedSumStation.");
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
	
	public ArrayList<Double> getListOfMoves(Timestamp from, Timestamp to, int stationId, int storageId, String inventoryName) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Double> al = new ArrayList<>();
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory_log.units FROM inventory_log "
					+ "WHERE inventory_log.created_at >= ?"
					+ "AND inventory_log.created_at <= ?"
					+ "AND inventory_log.storage_id = ? "
					+ "AND inventory_log.station_id = ? AND inventory_log.name = ?");
			statement.setTimestamp(1, from);
			statement.setTimestamp(2, to);
			statement.setInt(3, storageId);
			statement.setInt(4, stationId);
			statement.setString(5, inventoryName);
			
			try {
				rs = statement.executeQuery();
				while (rs.next()){
					al.add(rs.getDouble("units"));
				}
			}
			catch(Exception e){
				System.out.println("Error getting list of moves.");
				e.printStackTrace();
			}
		}
		catch(Exception e){
			System.out.println("Could not get list of moves.");
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
	
	public ArrayList<Double> getListOfMoves(Timestamp from, Timestamp to, int storageId, String inventoryName) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Double> al = new ArrayList<>();
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory_log.units FROM inventory_log "
					+ "WHERE inventory_log.created_at >= ?"
					+ "AND inventory_log.created_at <= ?"
					+ "AND inventory_log.storage_id = ? AND inventory_log.name = ?");
			statement.setTimestamp(1, from);
			statement.setTimestamp(2, to);
			statement.setInt(3, storageId);
			statement.setString(4, inventoryName);
			
			try {
				rs = statement.executeQuery();
				while (rs.next()){
					al.add(rs.getDouble("units"));
				}
			}
			catch(Exception e){
				System.out.println("Error getting list of moves.");
				e.printStackTrace();
			}
		}
		catch(Exception e){
			System.out.println("Could not get list of moves.");
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
	
	
	public boolean addToStorageLog(String name, int storageId, String performed_action) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("INSERT INTO storage_log (name, storage_id, performed_action) VALUES (?, ?, ?)");
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT storages.name FROM storages WHERE storages.id = ?;");
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		ArrayList<Inventory> al = new ArrayList<Inventory>();
		try {
			statement = connection.prepareStatement("SELECT inventory.id, inventory.name, inventory.price, inventory.units, categories.category, inventory.sales_price, storages.is_open, storages.name AS storage_name, storages.id AS storage_id FROM inventory "
					+ "JOIN storages ON storages.id = inventory.storage_id "
					+ "AND storages.company_id = ? "
					+ "LEFT JOIN inventory_categories ON inventory_categories.inventory_id = inventory.id "
					+ "LEFT JOIN categories ON categories.id = inventory_categories.category_id ORDER BY storages.name, inventory.name;");
			try {
				statement.setInt(1, companyId);
				rs = statement.executeQuery();
				while (rs.next()){
					Inventory i = new Inventory();
					i.setId(rs.getInt("id"));
					i.setName(rs.getString("name"));
					i.setUnits(rs.getDouble("units"));
					i.setStorageId(rs.getInt("storage_id"));
					i.setPrice(rs.getDouble("price"));
					i.setSalesPrice(rs.getDouble("sales_price"));
					i.setStorageOpen(rs.getBoolean("is_open"));
					i.setBelongsToStorage(rs.getString("storage_name"));
					i.setCategory(rs.getString("category"));
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
	
	

	public boolean addToInventoryLog(int inventoryId, String name, double units, int storageId, int stationId, String performedAction, double price, double salesPrice, String performedBy) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("INSERT INTO inventory_log (inventory_id, name, units, storage_id, station_id, performed_action, price, sales_price, performed_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setInt(1, inventoryId);
			statement.setString(2, name);
			statement.setDouble(3, units);
			statement.setInt(4, storageId);
			statement.setInt(5, stationId);
			statement.setString(6, performedAction);
			statement.setDouble(7, price);
			statement.setDouble(8, salesPrice);
			statement.setString(9, performedBy);
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory.name FROM inventory WHERE inventory.id = ?;");
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT inventory.name FROM inventory WHERE inventory.name = ? AND inventory.storage_id = ?;");
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("DELETE FROM inventory WHERE inventory.id = ?");
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("SELECT storages.id FROM storages WHERE storages.company_id = ? ORDER BY id ASC LIMIT 1");
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Storage> al = new ArrayList<Storage>();
		Connection connection = ds.getConnection();;
    	try {
    		statement = connection.prepareStatement("SELECT storages.id, storages.name, storages.company_id, storages.is_open, storages.updated_at, storages.created_at, COUNT(inventory.id) AS inventoryCount "
    				+ "FROM storages "
    				+ "LEFT JOIN inventory ON inventory.storage_id = storages.id "
    				+ "WHERE company_id = ? GROUP BY storages.id, storages.name, storages.company_id, storages.is_open, storages.updated_at, storages.created_at;");
    		statement.setInt(1, companyId);
    		rs = statement.executeQuery();
    		while(rs.next()) {
    			Storage st = new Storage();
    			st.setId(rs.getInt("id"));
    			st.setName(rs.getString("name"));
    			st.setCompanyId(rs.getInt("company_id"));
    			st.setIsOpen(rs.getBoolean("is_open"));
    			st.setOpenedAt(rs.getTimestamp("updated_at"));
    			st.setCreatedAt(rs.getTimestamp("created_at"));
    			st.setInventoryCount(rs.getInt("inventoryCount"));

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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		try {
			Storage st = getStorage(storageId);
			Timestamp openedAtTime = st.getOpenedAt();
//			statement = connection.prepareStatement("SELECT inventory.id, inventory.name, inventory.units, inventory.created_at, inventory.updated_at, inventory.storage_id, categories.category "
//					+ "FROM inventory "
//					+ "LEFT JOIN inventory_categories ON inventory_categories.inventory_id = inventory.id "
//					+ "LEFT JOIN categories ON categories.id = inventory_categories.category_id "
//					+ "WHERE inventory.storage_id = ?;");
			
			statement = connection.prepareStatement("SELECT inventory.id, inventory.name, inventory.units, temporary_units.temp_units, SUM(inventory_log.units) AS total_out, inventory_snapshot.units_at_open, inventory.created_at, inventory.updated_at, inventory.storage_id, categories.category "
					+ "FROM inventory "
					+ "LEFT JOIN inventory_categories ON inventory_categories.inventory_id = inventory.id "
					+ "LEFT JOIN categories ON categories.id = inventory_categories.category_id "
					+ "LEFT JOIN archive_log ON archive_log.storage_id = inventory.storage_id AND archive_log.opened_at >= ? "
					+ "LEFT JOIN inventory_snapshot ON inventory_snapshot.archive_log_id = archive_log.id "
					+ "AND inventory_snapshot.inventory_id = inventory.id "
					+ "LEFT JOIN inventory_log ON inventory_log.created_at >= ? "
					+ "AND inventory_log.inventory_id = inventory.id "
					+ "LEFT JOIN temporary_units ON temporary_units.inventory_id = inventory.id "
					+ "AND temporary_units.storage_id = ? "
					+ "WHERE inventory.storage_id = ? "
					+ "GROUP BY inventory.id, inventory.name, inventory.units, temporary_units.temp_units, inventory_snapshot.units_at_open, inventory.created_at, inventory.updated_at, inventory.storage_id, categories.category "
					+ "ORDER BY categories.category, inventory.name;");
			
			statement.setTimestamp(1, openedAtTime);
			statement.setTimestamp(2, openedAtTime);
			statement.setInt(3, storageId);
			statement.setInt(4, storageId);
			rs = statement.executeQuery();
			while(rs.next()){
				Inventory iv = new Inventory();
				iv.setId(rs.getInt("id"));
				iv.setName(rs.getString("name"));
				iv.setUnits(rs.getDouble("units"));
				iv.setCreatedAt(rs.getTimestamp("created_at"));
				iv.setUpdatedAt(rs.getTimestamp("updated_at"));
				iv.setStorageId(rs.getInt("storage_id"));
				iv.setCategory(rs.getString("category"));
				
				iv.setUnitsAtOpen(rs.getDouble("units_at_open"));
				iv.setMovesSoFar(rs.getDouble("total_out"));
				
				double tempUnits = rs.getDouble("temp_units");
				if(!rs.wasNull()){
					iv.setTempUnits(tempUnits);					
				}
				
				
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
	
	public ArrayList<Category> getCompanyCategories(int companyId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
		ArrayList<Category> arr = new ArrayList<>();
    	try {
    		statement = connection.prepareStatement("SELECT categories.id, categories.category FROM categories WHERE categories.company_id = ?");
    		statement.setInt(1, companyId);
    		rs = statement.executeQuery();
    		while(rs.next()) {
    			Category c = new Category();
    			c.setCategory(rs.getString("category"));
    			c.setId(rs.getInt("id"));
    			arr.add(c);
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
    	return arr;
	}
	
	
	
	public boolean updateUnitsAt(Map<Integer, Double> values) throws Exception{ // takes inventory id and newValue
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("UPDATE inventory SET units = ? WHERE inventory.id = ?");
			
			
			for (Entry<Integer, Double> entry : values.entrySet()) {
				statement.setDouble(1, entry.getValue());
				statement.setInt(2, entry.getKey());
				statement.addBatch();
			}
			
			statement.executeBatch();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;	
	}
	
	public boolean incrementUnits(Map<Integer, Double> values) throws Exception{ // integer id and new value
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("UPDATE inventory SET units = units + ? WHERE inventory.id = ?");
			
			for (Entry<Integer, Double> entry : values.entrySet()) {
				statement.setDouble(1, entry.getValue());
				statement.setInt(2, entry.getKey());
				statement.addBatch();
			}
			statement.executeBatch();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;	
	}
	
	public boolean decrementUnits(Map<Integer, Double> values) throws Exception{ // takes inventory id and amount
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();;
		try {
			statement = connection.prepareStatement("UPDATE inventory SET units = units - ? WHERE inventory.id = ?");
			
			for (Entry<Integer, Double> entry : values.entrySet()) {
				statement.setDouble(1, entry.getValue());
				statement.setInt(2, entry.getKey());
				statement.addBatch();				
			}
			statement.executeBatch();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		return false;	
	}
	
	public Double currentInventoryUnits(int inventoryId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
    	try {
    		statement = connection.prepareStatement("SELECT inventory.units FROM inventory WHERE inventory.id = ?;");
    		statement.setInt(1, inventoryId);
    		rs = statement.executeQuery();
    		if(rs.next()) {
    			return rs.getDouble("units");
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
    	return 0.0;	
		
	}
	
	public boolean storageIsOpen(int storageId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
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
	
	public boolean userOwnsStorage(int storageId, int userId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
    	try {
    		statement = connection.prepareStatement("SELECT storages.id FROM storages, users, companies WHERE users.company_id = companies.id AND storages.company_id  = companies.id AND users.id = ? AND storages.id = ?");
    		statement.setInt(1, userId);
    		statement.setInt(2, storageId);
    		rs = statement.executeQuery();
    		if(rs.next()) {
    			return true;
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
	
	public boolean userOwnsInventory(int companyId, int inventoryId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
    	try {
    		statement = connection.prepareStatement("SELECT DISTINCT inventory.id FROM inventory, storages WHERE storages.company_id = ? AND storages.id = inventory.storage_id AND inventory.id = ?");
    		statement.setInt(1, companyId);
    		statement.setInt(2, inventoryId);
    		rs = statement.executeQuery();
    		if(rs.next()) {
    			return true;
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
	
	public boolean inventoriesStorageIsOpen(int inventoryId) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
    	try {
    		statement = connection.prepareStatement("SELECT storages.is_open FROM storages, inventory WHERE inventory.storage_id = storages.id AND inventory.id = ?;");
    		statement.setInt(1, inventoryId);
    		rs = statement.executeQuery();
    		if(rs.next()) {
    			if(rs.getBoolean("is_open")){
    				return true;
    			}
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
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
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
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}
	
	public int getStorageId(int companyId, String storageName) throws Exception{
		PreparedStatement statement = null;
		ResultSet rs = null;
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Station> al = new ArrayList<Station>();
		Connection connection = ds.getConnection();;
    	try {
    		statement = connection.prepareStatement("SELECT stations.id ,stations.name, stations.importance FROM stations WHERE stations.company_id = ? AND stations.importance = ?;");
    		statement.setInt(1, companyId);
    		statement.setString(2, importance);
    		rs = statement.executeQuery();
    		while(rs.next()) {
    			Station s = new Station();
    			s.setId(rs.getInt("id"));
    			s.setName(rs.getString("name"));
    			s.setImportance(rs.getString("importance"));
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
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = ds.getConnection();
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

	public void updateSalesPrice(double updatedSalesPrice, int inventoryId) throws SQLException {
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("UPDATE inventory SET sales_price = ? WHERE inventory.id = ?");
			statement.setDouble(1, updatedSalesPrice);
			statement.setInt(2, inventoryId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		
	}
	
	public void updatePrice(double updatedPrice, int inventoryId) throws SQLException {
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("UPDATE inventory SET price = ? WHERE inventory.id = ?");
			statement.setDouble(1, updatedPrice);
			statement.setInt(2, inventoryId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		
	}

	public void setTempUnits(Map<Integer, Double> tempValues, int storageId) throws SQLException {
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			String query = String.format("INSERT INTO temporary_units (inventory_id, temp_units, storage_id) VALUES (?, ?, ?) "
						+ "ON DUPLICATE KEY UPDATE temp_units = ?; ");
			statement = connection.prepareStatement(query);

			for (Map.Entry inventory : tempValues.entrySet()) {
				statement.setInt(1, (Integer)inventory.getKey());
				statement.setDouble(2, (Double)inventory.getValue());
				statement.setInt(3, storageId);
				statement.setDouble(4, (Double)inventory.getValue());
				statement.addBatch();
			}
			
			statement.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}
	
	public void deleteTempUnits(int storageId) throws SQLException {
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("DELETE FROM temporary_units WHERE temporary_units.storage_id = ?;");
			statement.setDouble(1, storageId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }

	}

	public void deleteEmptyUnits(ArrayList<Integer> deleteTempIds) throws SQLException {
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("DELETE FROM temporary_units WHERE temporary_units.inventory_id = ?;");
			for (Integer integer : deleteTempIds) {
				statement.setInt(1, integer);
				statement.addBatch();
			}

			statement.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		
	}
	
	
	public void updateCategory(int inventoryId, int categoryId) throws SQLException {
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			String query = String.format("INSERT INTO inventory_categories (inventory_id, category_id) VALUES (?, ?) "
					+ "ON DUPLICATE KEY UPDATE category_id = ?;");
			statement = connection.prepareStatement(query);


				statement.setInt(1, inventoryId);
				statement.setDouble(2, categoryId);
				statement.setDouble(3, categoryId);
			
			
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
	}
	
	public void deleteInventoryCategory(int inventoryId) throws SQLException {
		PreparedStatement statement = null;
		Connection connection = ds.getConnection();
		try {
			statement = connection.prepareStatement("DELETE FROM inventory_categories WHERE inventory_categories.inventory_id = ?;");
			statement.setInt(1, inventoryId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
		
	}
}
